package Example;

import ch.rs.reflectorgrid.util.annotations.TransferGrid;
import ch.rs.reflectorgrid.util.interfaces.ObjectChangeListener;

import java.lang.reflect.Field;

/**
 *
 */
public class ExampleObject extends ExampleParentObject implements ObjectChangeListener {

    @TransferGrid
    private String functionName = "testFunction";

    @TransferGrid
    private int number = 20;

    @TransferGrid(options = {"you", "are", "nice"})
    private String choiceForYou = "you";

    @TransferGrid(fieldtype = TransferGrid.Fieldtype.TEXT_AREA)
    private String textArea;

    @TransferGrid(tooltip = "Defines the runtime of this application")
    private ExampleEnum option = ExampleEnum.FULL;
    
    @TransferGrid
    private ExampleDataObject data;

    @TransferGrid(editable = false)
    private String uneditableForYou = "blocked";

    public ExampleObject() {
        super("ExampleName");
        data = new ExampleDataObject(8090, "127.0.0.1", "hello\n");
    }

    @Override
    public void onFieldValueChanged(Field field) {
        System.out.println(field.getName() + " was updated!");
    }
}
