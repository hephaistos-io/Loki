package Example;

import ch.hephaistos.utilities.loki.util.annotations.TransferMethod;
import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import ch.hephaistos.utilities.loki.util.interfaces.ObjectChangeListener;

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

    @TransferMethod(name = "Press Me!")
    public void testFunction () {
        System.out.println("You pressed a button!");
    }

    @TransferMethod(name = "Disabled!", enabled = false, tooltip = "This is an explanation of what happens when you press the button")
    public void disabledFunction () {
        System.out.println("You pressed a button!");
    }

    @Override
    public void onFieldValueChanged(Field field) {
        System.out.println(field.getName() + " was updated!");
    }
}
