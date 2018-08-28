package ch.hephaistos.utilities.loki.util.example;

import ch.hephaistos.utilities.loki.ReflectorGrid;
import ch.hephaistos.utilities.loki.util.DefaultFieldNamingStrategy;
import ch.hephaistos.utilities.loki.util.LabelDisplayOrder;
import ch.hephaistos.utilities.loki.util.interfaces.ChangeListener;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.lang.reflect.Field;

/**
 * A small Example main class...
 */
public class ExampleMain extends Application implements ChangeListener {


    @Override
    public void start(Stage primaryStage) throws Exception {
        ExampleObject exampleObject = new ExampleObject();
        ReflectorGrid reflectorGrid = new ReflectorGrid();
        reflectorGrid.setFieldNamingStrategy(DefaultFieldNamingStrategy.SPLIT_TO_CAPITALIZED_WORDS);
        reflectorGrid.setLabelDisplayOrder(LabelDisplayOrder.SIDE_BY_SIDE);

        reflectorGrid.transfromIntoGrid(exampleObject);

        reflectorGrid.addChangeListener(this);


        primaryStage.setScene(new Scene(reflectorGrid));
        primaryStage.sizeToScene();
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> System.exit(0));
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void onObjectValueChanged(Field field, Object object) {
        System.out.println(field.getName() + " of object " + object.getClass().getName() + " was updated ");
    }
}
