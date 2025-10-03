package fr.ufrst.m1info.gl.compgl.driver;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Example_TestFX extends Application {
    @Override
    public void start(Stage stage) {
        Label label = new Label("Hello TestFX!");
        Button button = new Button("Click me");
        button.setOnAction(e -> label.setText("Button clicked!"));

        VBox root = new VBox(10, label, button);
        Scene scene = new Scene(root, 300, 200);
        stage.setScene(scene);
        stage.setTitle("TestFX Demo");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}