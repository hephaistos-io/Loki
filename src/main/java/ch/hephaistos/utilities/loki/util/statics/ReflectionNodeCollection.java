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

package ch.hephaistos.utilities.loki.util.statics;

import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import ch.hephaistos.utilities.loki.util.NumberSpinnerValueFactory;
import javafx.collections.FXCollections;
import javafx.scene.control.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;

/**
 * This "Collection" contains different functions to create Nodes that are connected to a
 * {@link java.lang.reflect.Field} and contains the needed Listeners for changes in the node.
 * Created by Ricardo on 15.11.2017.
 */
public class ReflectionNodeCollection {

    public static ComboBox<String> createComboBox(TransferGrid annotation, Field field,
                                                  Object handle, Object masterObject) {

        ComboBox<String> comboBox = new ComboBox<>(
                FXCollections.observableArrayList(annotation.options())
        );

        comboBox.getSelectionModel().select(
                objectToString(ReflectionHelper.getFieldValue(field, handle))
        );
        comboBox.getSelectionModel().selectedItemProperty()
                .addListener((obs, ov, newValue)
                        -> ListenerCollection.getStringListener(field, handle, masterObject).accept(newValue));

        return comboBox;
    }

    public static <T extends Enum<T>> ComboBox<T> createEnumComboBox(Class<T> clazz, Field field,
                                                                      Object handle, Object masterObject) {

        ComboBox<T> comboBox = new ComboBox<>(FXCollections.observableArrayList(clazz.getEnumConstants()));
        comboBox.getSelectionModel().select(
                (ReflectionHelper.getFieldValue(field, handle))
        );
        comboBox.getSelectionModel().selectedItemProperty()
                .addListener((obs, ov, newValue)
                        -> ListenerCollection.getEnumListener(field, handle, masterObject).accept(newValue));

        return comboBox;
    }

    public static TextField createTextField(Field field, Object handle, Object masterObject) {
        TextField textField = new TextField(
                objectToString(ReflectionHelper.getFieldValue(field, handle))
        );

        textField.textProperty().addListener((obs, ov, newValue)
                -> ListenerCollection.getStringListener(field, handle, masterObject).accept(newValue));

        return textField;
    }

    public static TextArea createTextArea(Field field, Object handle, Object masterObject) {
        TextArea textArea = new TextArea(objectToString(ReflectionHelper.getFieldValue(field, handle)));

        textArea.textProperty().addListener((obs, ov, newValue)
                -> ListenerCollection.getStringListener(field, handle, masterObject).accept(newValue));

        return textArea;
    }

    /**
     * This Functions creates a Spinner with the {@link NumberSpinnerValueFactory} so that it can fit
     * any Numeric field.
     * @param field The field itself that gets connected to the spinner
     * @param handle the object in which the field is connected
     * @param masterObject the masterObject of the field
     * @return a Spinner with the {@link NumberSpinnerValueFactory} built in aswell as all needed Listeners.
     */
    public static Spinner createSpinner(Field field, Object handle, Object masterObject) {

        Spinner<BigDecimal> spinner = new Spinner<BigDecimal>();
        SpinnerValueFactory<BigDecimal> valueFactory
                = new NumberSpinnerValueFactory(Integer.MIN_VALUE, Integer.MAX_VALUE,
                BigDecimal.valueOf(
                        ReflectionHelper.<Number>getFieldValue(field, handle).doubleValue()), field.getType());

        spinner.setValueFactory(valueFactory);
        spinner.valueProperty().addListener((obs, ov, newValue)
                -> ListenerCollection.getObjectListener(field, handle, masterObject).accept(newValue));
        return spinner;

    }

    private static String objectToString(Object object) {
        return object == null ? "" : object.toString();
    }

}
