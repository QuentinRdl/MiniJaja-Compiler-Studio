package fr.ufrst.m1info.gl.compgl.driver;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

import java.io.IOException;

/*
* Main entry point of the JavaFX application.
*/
public class HelloApplication extends Application {
    private final int WINDOW_HEIGHT = 720;
    private final int WINDOW_WIDTH = 1280;

    private final String WINDOW_TITLE = "Projet COMP-GL groupe 5";

    /**
     * Starts the JavaFX application by loading the main interface and displaying the window.
     *
     * @param stage the primary stage for this application
     * @throws IOException if the FXML file or stylesheet cannot be loaded
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setTitle(WINDOW_TITLE);
        //stage.setFullScreen(true); //go full screen
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Launches the JavaFX application.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        launch();
    }
}