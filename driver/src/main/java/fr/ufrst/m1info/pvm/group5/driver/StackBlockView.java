package fr.ufrst.m1info.pvm.group5.driver;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Visual representation of a stack variable block in the memory visualisation
 * Each block shows the variable's name, scope, kind, type and current value
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
    public StackBlockView(String name, String kind, int scope, String type, String value){
        super();
        setPadding(new Insets(8));
        getStyleClass().add("stack-block");
        setSpacing(8);
        setPrefWidth(200);

        getChildren().addAll(
                new Label("Name : " + name + "   (Scope = " + scope + ")"),
                new Label("Kind : " + kind),
                new Label("Type : " + type),
                new Label("Value : " + value)
        );
    }
}
