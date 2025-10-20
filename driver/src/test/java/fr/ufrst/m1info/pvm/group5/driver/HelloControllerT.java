package fr.ufrst.m1info.pvm.group5.driver;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.stage.Stage;
import javafx.scene.Scene;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

public class HelloControllerT extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fr/ufrst/m1info/pvm/group5/driver/hello-view.fxml"));
        Parent root = fxmlLoader.load();
        if (root == null) {
            System.out.println("Root is null! FXML not loaded.");
        } else {
            System.out.println("FXML loaded successfully: " + root);
        }
        stage.setScene(new Scene(root));
        stage.show();
    }

    /*
     * Verifies that the label with id fileLabel displays "No files selected" when the application starts.
     */
    @Test
    public void testLabelInitial(){
        verifyThat("#fileLabel", hasText("No files selected"));
    }

}
