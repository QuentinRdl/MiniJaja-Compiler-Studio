package fr.ufrst.m1info.pvm.group5.driver;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Visual representation of a stack variable block in the memory visualisation
 * Each block shows the variable's name, kind, type and current value
 */
public class StackBlockView extends VBox {

    /**
     * Constructs a visual block for a stack variable
     *
     * @param name the variable name
     * @param kind the kind of variable (VARIABLE, CONSTANT...)
     * @param type the date type of the variable (INT, STRING...)
     * @param value the current value of the variable
     */
    public StackBlockView(String name, String kind, String type, String value){
        super();
        setPadding(new Insets(8));
        setSpacing(8);
        setStyle("-fx-background-color: #e8e8e8; -fx-border-color: #1a1a1a; -fx-border-radius: 6; -fx-background-radius: 6;");
        setPrefWidth(200);

        getChildren().addAll(
                new Label("Name : " + name),
                new Label("Kind : " + kind),
                new Label("Type : " + type),
                new Label("Value : " + value)
        );
    }
}
