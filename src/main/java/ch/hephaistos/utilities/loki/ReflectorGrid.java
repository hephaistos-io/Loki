/**

 Loki is an easy to use library that transforms simple annotations into a powerful GUI
 Copyright (C) 2018 Ricardo Simoes

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU Affero General Public License as published
 by the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Affero General Public License for more details.

 You should have received a copy of the GNU Affero General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.

 */

package ch.hephaistos.utilities.loki;

import ch.hephaistos.utilities.loki.util.*;
import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import ch.hephaistos.utilities.loki.util.annotations.TransferMethod;
import ch.hephaistos.utilities.loki.util.interfaces.ChangeListener;
import ch.hephaistos.utilities.loki.util.statics.ReflectionHelper;
import ch.hephaistos.utilities.loki.util.statics.ReflectionNodeCollection;
import ch.hephaistos.utilities.loki.util.statics.TypeHelper;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Objects;

/**
 * This class is used in conjunction with TransferGrid.java and enables a user
 * to generate a GridPane with labels and TextFields/ComboBoxes/TextAreas for
 * variables used in an Object. This also supports variables from inhereted
 * classes, aswell as private variables.
 *
 * Please make sure you know the possibilities that @TransferGrid enables,
 * before letting the end user change variables he is not supposed to.
 * <p>
 * This class uses the JavaFX GridPane and due to the way it is built, you only
 * need one class to generate a Grid. If the object changes to another object
 * you want to show the Variables of, you can just call turnObjectIntoGrid()
 * again. Due to the nature of JavaFX it will automaticly update the Grid on
 * your GUI.
 *
 * @author Ricardo Daniel Monteiro Simoes
 * <b>Autho of some nifty little tricks:</b> I-Al-Istannen, : https://github.com/I-Al-Istannen
 */
public class ReflectorGrid extends GridPane{

    /**
     * This object is the one we give with turnObjectIntoGrid(). It is needed to
     * set the field when using the TextFields etc.
     */
    private Object gridObject = new Object();

    /**
     * This is used to determine the format to which the grid is created.
     * {@link LabelDisplayOrder} for more information
     */
    private LabelDisplayOrder displayOrder = LabelDisplayOrder.SIDE_BY_SIDE;

    /**
     * This variable is used to determine the max width of the editable fields.
     * Standard is set to 300.
     */
    private double NODE_WIDTH_LIMIT = 300;

    /**
     * This variable is used to set the way a Label is named. If it is set to
     * <b>VERBATIM</b>, the label will keep the same name as the variable. If it
     * is set to <b>SPLIT_TO_CAPITALIZED_WORDS</b>, it will work as in the
     * following example:
     * <p>
     * portToSendTo -> Port To Send To
     * <p>
     * {@link FieldNamingStrategy} for more information
     */
    private FieldNamingStrategy fieldNamingConvention = DefaultFieldNamingStrategy.SPLIT_TO_CAPITALIZED_WORDS;

    /**
     * WRITE STUFF HERE
     * This variable is used to set the way a Label is named. If it is set to
     * <b>VERBATIM</b>, the label will keep the same name as the variable. If it
     * is set to <b>SPLIT_TO_CAPITALIZED_WORDS</b>, it will work as in the
     * following example:
     * <p>
     * portToSendTo -> Port To Send To
     * <p>
     * {@link MethodNamingStrategy} for more information
     */
    private MethodNamingStrategy methodNamingConvention = DefaultMethodNamingStrategy.SPLIT_TO_CAPITALIZED_WORDS;

    /**
     * Sets some normal formatting for the grid.
     */
    public ReflectorGrid() {
        this.setHgap(5);
        this.setVgap(5);
        this.setPadding(new Insets(10, 10, 10, 10));
    }

    public ReflectorGrid(ReflectorGrid refGrid) {
        this.setHgap(refGrid.getHgap());
        this.setVgap(refGrid.getVgap());
        this.setPadding(refGrid.getPadding());
        this.NODE_WIDTH_LIMIT = refGrid.getNodeWidth();
        this.displayOrder = refGrid.getDisplayOrder();
        this.fieldNamingConvention = refGrid.getFieldNamingConvention();
    }

    public void transfromIntoGrid(Object object) {
        Objects.requireNonNull(object, "The received Object is null!");
        setGridObject(object);
        generateGrid();
        //return this;
    }


    /**
     * This Method can be used externally to regenerate the Grid.
     * This is supposed to be used when you do Layoutchanges after generating the Grid.
     */
    public void redoGrid() {
        generateGrid();
    }

    private void setGridObject(Object object) {
        gridObject = object;
    }

    /**
     * This function starts all necessary functions to generate the grid and returns it.
     *
     * @return The finished Grid for the given Object.
     */
    private void generateGrid() {
        clearGrid();

        LabelDisplayOrder.InsertionPosition insertionPosition = new LabelDisplayOrder.InsertionPosition(0, 0);

        Class clazz = gridObject.getClass();

        while(clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (shouldTransferToGrid(field)) {
                    insertionPosition = handleField(insertionPosition, field, gridObject);
                }
            }
            for (Method method : clazz.getDeclaredMethods()) {
                if (hasMethodAnnotation(method)) {
                    insertionPosition = handleMethod(insertionPosition, method, gridObject);
                }
            }
            clazz = clazz.getSuperclass();
        }
    }

    /**
     * This Function handles the use case, when an Object contains an Object in itself.
     * If annoted correctly, it will create a new TransferGrid for said "sub-object" and then take its Nodes and add them
     * to this GridPane. It also adds in a "separator" with the Name of the object in the parent object.
     *
     * @param insertionPosition
     * @param field
     * @param subObject
     * @return
     */
    private LabelDisplayOrder.InsertionPosition handleSubClassField(LabelDisplayOrder.InsertionPosition insertionPosition, Field field, Object subObject) {
        insertionPosition = addSeparator(insertionPosition, this);
        insertionPosition = displayOrder.addNode(insertionPosition, new Label(fieldNamingConvention.toString(field) + ":"), this);
        ReflectorGrid tempRefGrid = new ReflectorGrid(this);
        Object object = ReflectionHelper.getFieldValue(field, subObject);
        tempRefGrid.transfromIntoGrid(object);
        insertionPosition =  addGridElements(insertionPosition, tempRefGrid);
        return addSeparator(insertionPosition, this);
    }

    private LabelDisplayOrder.InsertionPosition addSeparator(LabelDisplayOrder.InsertionPosition insertionPosition, GridPane pane){
        return displayOrder.addSeparator(insertionPosition, this);
    }

    /**
     * This function takes the Nodes of a subObject and moves them to the main GridPane.
     * Due to the nature of JavaFX we need to cast the subObject GridPane to a List.
     * @param position The starting position for the positioning of the Nodes in
     *                 the main GridPane
     * @param gridToAdd The GridPane of the subObject
     * @return returns the position for the next node in the GridPane
     */
    protected LabelDisplayOrder.InsertionPosition addGridElements(LabelDisplayOrder.InsertionPosition position, GridPane gridToAdd) {
        Label label = null;
        for (Node node : new ArrayList<>(gridToAdd.getChildren())) {
            if (label == null) {
                label = (Label) node;
            } else {
                position = displayOrder.addNode(position, label, node, this);
                label = null;
            }
        }
        return position;
    }

    /**
     * This function handles a single Field. It looks if the field is a normal field or a subObject-
     * @param insertionPosition the position in which the Nodes for this field get inserted
     * @param field the Field itself
     * @param object the object to which the field belongs. This change was needed in case the Field
     *               belongs to a subObject rather than the object itself.
     * @return position for the next Node in the Grid
     */
    private LabelDisplayOrder.InsertionPosition handleField(LabelDisplayOrder.InsertionPosition insertionPosition, Field field, Object object) {
        if (TypeHelper.isNumericType(field.getType()) || TypeHelper.isJavaLang(field.getType()) || TypeHelper.isEnum(field.getType())) {
            Pair<Label, Node> nodes = getNodePairForField(field, object);
            return insertionPosition = displayOrder
                    .addNode(insertionPosition, nodes.getKey(), nodes.getValue(), this);
        }
        return insertionPosition = handleSubClassField(insertionPosition, field, object);
    }

    /**
     * WRITE STUFF HERE
     * @param insertionPosition the position in which the Nodes for this field get inserted
     * @param method the Field itself
     * @param object the object to which the field belongs. This change was needed in case the Field
     *               belongs to a subObject rather than the object itself.
     * @return position for the next Node in the Grid
     */
    private LabelDisplayOrder.InsertionPosition handleMethod(LabelDisplayOrder.InsertionPosition insertionPosition, Method method, Object object) {
        Pair<Label, Node> nodes = getNodePairForMethod(method, object);
        return insertionPosition = displayOrder
                .addNode(insertionPosition, nodes.getKey(), nodes.getValue(), this);
    }


    /**
     * Creates a Pair of a Label as well as an InputField for normal declared Fields.
     * @param field the field itself
     * @param handle the object it belongs to
     * @return a Pair<> consisting of a Label with the Fieldname as well as an InputField
     */
    private Pair<Label, Node> getNodePairForField(Field field, Object handle) {
        Label label = new Label(fieldNamingConvention.toString(field));
        Control node;
        TransferGrid annotation = field.getAnnotation(TransferGrid.class);

        if (annotation.options().length > 0) {
            node = ReflectionNodeCollection.createComboBox(annotation, field, handle, gridObject);
        } else if (TypeHelper.isNumericType(field.getType())) {
            node = ReflectionNodeCollection.createSpinner(field, handle, gridObject);
        } else if (TypeHelper.isEnum(field.getType())) {
            node = ReflectionNodeCollection.createEnumComboBox((Class<? extends Enum>) field.getType(), field, handle, gridObject);
        } else {
            switch (annotation.fieldtype()) {
                case TEXT_FIELD:
                    node = ReflectionNodeCollection.createTextField(field, handle, gridObject);
                    break;
                case TEXT_AREA:
                    node = ReflectionNodeCollection.createTextArea(field, handle, gridObject);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown field type: " + annotation.fieldtype());
            }
        }

        if(!annotation.tooltip().isEmpty()) {
        Tooltip tempTip = new Tooltip(annotation.tooltip());
        tempTip.setWrapText(true);
        label.setTooltip(tempTip);
        }

        adjustNodeProperties(annotation, node);

        return new Pair<>(label, node);

    }

    /**
     * WRITE STUFF HERE
     * @param method the field itself
     * @param handle the object it belongs to
     * @return a Pair<> consisting of a Label with the Fieldname as well as an InputField
     */
    private Pair<Label, Node> getNodePairForMethod(Method method, Object handle) {
        Label label = new Label(methodNamingConvention.toString(method));
        Control node;
        TransferMethod annotation = method.getAnnotation(TransferMethod.class);

        node = new Button(annotation.name());
        node.setDisable(!annotation.enabled());
        ((Button) node).setOnAction( eg -> {
            try{
                if(method.isAccessible()){
                    method.invoke(handle);
                } else {
                    method.setAccessible(true);
                    method.invoke(handle);
                    method.setAccessible(false);
                }
            } catch (IllegalAccessException ieA) {
                System.out.println("Cannot access method: " + method.getName() + " error: " + ieA.getMessage());
            } catch (InvocationTargetException itE) {
                System.out.println("Could not invoke method: " + method.getName() + " error: " + itE.getMessage());
            }
        } );

        if(!annotation.tooltip().isEmpty()) {
            Tooltip tempTip = new Tooltip(annotation.tooltip());
            tempTip.setWrapText(true);
            label.setTooltip(tempTip);
        }

        adjustNodeProperties(annotation, node);

        return new Pair<>(label, node);

    }

    /**
     * This method is used internally to set if a field is editable or not.
     * @param annotation the annotation of said field; is needed to determine if it can be edited or not
     * @param node the node which is supposed to be set according to the annotation
     */
    private void adjustNodeProperties(TransferGrid annotation, Control node) {
        setEditable(node, annotation.editable());
        node.setMouseTransparent(!annotation.editable());
        node.setFocusTraversable(annotation.editable());

        node.setMaxWidth(NODE_WIDTH_LIMIT);
    }

    /**
     * WRITE SOMETHING HERE
     * This method is used internally to set if a field is editable or not.
     * @param annotation the annotation of said field; is needed to determine if it can be edited or not
     * @param node the node which is supposed to be set according to the annotation
     */
    private void adjustNodeProperties(TransferMethod annotation, Control node) {
        node.setMaxWidth(NODE_WIDTH_LIMIT);
    }

    /**
     * This function handles the different .setEditable functions depending on the type of inputField
     * @param node the inputField itself
     * @param editable if it is editable or not
     */
    private void setEditable(Control node, boolean editable) {
        if (node instanceof TextInputControl) {
            ((TextInputControl) node).setEditable(editable);
        } else if (node instanceof ComboBoxBase) {
            ((ComboBoxBase) node).setEditable(false);
        } else if (node instanceof Spinner) {
            ((Spinner) node).setEditable(editable);
        } else {
            throw new IllegalArgumentException("Can't make node uneditable: " + node);
        }
    }

    private void clearGrid() {
        this.getChildren().clear();

    }

    /**
     * Checks to see if given Field has the correct annotation
     * @param field the field itself
     * @return true if it contains the annotation <b>@TransferGrid</b>
     */
    private boolean shouldTransferToGrid(Field field) {
        return field.isAnnotationPresent(TransferGrid.class);
    }

    /**
     * WRITE SOMETHING
     * @param method the field to be checked
     * @return if it is annoted with <b>@TransferMethod</b>
     */
    private boolean hasMethodAnnotation(Method method) {
        return method.isAnnotationPresent(TransferMethod.class);
    }

    private void setMaxWidth(TextInputControl field) {
        field.setMaxWidth(NODE_WIDTH_LIMIT);
    }

    /**
     * This method sets the value to show in the GUI for the ComboBox.
     * @param field the field with the value ot show
     * @param combo the comboBox to set
     */
    private void selectCurrentValue(Field field, ComboBox combo) {
        try {
            field.setAccessible(true);
            combo.getSelectionModel().select(field.get(gridObject));
            field.setAccessible(false);
        } catch (Exception e) {
            System.err.println("Couldnt set the value of the ComboBox " + e.getMessage());
            field.setAccessible(false);
        }

    }

    /**
     * Sets the format for the grid.
     * <p>
     * {@link LabelDisplayOrder} for information about the different avaliable
     * formats.
     *
     * @param labelDisplayOrder the new labelDisplayOrder
     */
    public void setLabelDisplayOrder(LabelDisplayOrder labelDisplayOrder) {
        displayOrder = labelDisplayOrder;
    }

    /**
     * Manually set your own limit to how wide TextInputs can get. Standard is
     * set to 300.
     * <b>This does not change the Width of an already generated Grid! You can
     * call redoGrid() to regenerate the Grid!</b>
     *
     * @param limit the max width to be use for the input fields.
     */
    public void setNodeWidthLimit(double limit) {
        NODE_WIDTH_LIMIT = limit;
    }

    /**
     * @param fieldNamingStrategy The {@link FieldNamingStrategy} to use
     *                            <p>
     *                            {@link FieldNamingStrategy} for information about the different avaliable
     *                            strategies.
     */
    public void setFieldNamingStrategy(FieldNamingStrategy fieldNamingStrategy) {
        fieldNamingConvention = fieldNamingStrategy;
    }

    public void setMethodNamingConvention(MethodNamingStrategy methodNamingStrategy) {
        methodNamingConvention = methodNamingStrategy;
    }

    private double getNodeWidth() {
        return NODE_WIDTH_LIMIT;
    }

    private LabelDisplayOrder getDisplayOrder() {
        return displayOrder;
    }

    private FieldNamingStrategy getFieldNamingConvention() {
        return fieldNamingConvention;
    }

    private MethodNamingStrategy getMethodNamingConvention() {
        return methodNamingConvention;
    }



    /**
     * Use this function to add an object, for example from the GUI, to be called every time a value
     * is updated trough reflection.
     *
     * Your function will be called with the following parameters:
     * <b>field, object</b>
     *
     * <b>field</b>
     * Field will give you the field that was updated.
     *
     * <b>object</b>
     * This will show you the object that was updated.
     *
     *
     * @param object an object that implemented  {@link ChangeListener}
     *
     */
    public void addChangeListener(ChangeListener object){
        ReflectionHelper.addInterfaceToUpdate(object);
    }

}
