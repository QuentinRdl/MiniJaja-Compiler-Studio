package fr.ufrst.m1info.pvm.group5.driver;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;

/**
 * Unit tests for the CodeLineCell class
 */
@ExtendWith(ApplicationExtension.class)
public class CodeLineCellT extends ApplicationTest {

    private ListView<CodeLine> listView;

    /**
     * Starts the JavaFX application before running the tests
     *
     * @param stage the primary JavaFX stage used for testing
     * @throws Exception if the scene setup fails
     */
    @Override
    public void start (Stage stage) throws Exception {
        listView = new ListView<>();
        listView.setCellFactory(lv -> new CodeLineCell());

        listView.getItems().addAll(
                new CodeLine(1, "int x;"),
                new CodeLine(2, "x = 12;"),
                new CodeLine(3, "return x;")
        );

        Scene scene = new Scene(listView, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Verifies that a cell in the ListView is correctly constructed
     * with the expected number of child nodes
     */
    @Test
    public void testCellDisplaysCorrectContent(){
        CodeLineCell cell = (CodeLineCell) listView.lookup(".list-cell"); // Retrieves a cell from the listview
        assertNotNull(cell);

        HBox container = (HBox) cell.getGraphic();
        assertNotNull(container);
        assertEquals(2, container.getChildren().size());
    }

    @Test
    public void testNullCellHasNoGraphic(){
        CodeLineCell cell = new CodeLineCell();
        cell.updateItem(null, false);

        assertNull(cell.getGraphic(), "A null cell should not have graphic");
    }


    @Test
    public void testEmptyCellHasNoGraphic(){
        CodeLineCell cell = new CodeLineCell();
        CodeLine codeLine = new CodeLine(1, "x = 10;");

        cell.updateItem(codeLine, true);

        assertNull(cell.getGraphic(), "An empty cell should not have graphic");
    }

    @Test
    public void testCellUpdate(){
        CodeLineCell cell = new CodeLineCell();
        CodeLine codeLine = new CodeLine(1, "x = 10;");

        cell.updateItem(codeLine, false);

        assertNotNull(cell.getGraphic());
        HBox container = (HBox) cell.getGraphic();
        assertEquals(2, container.getChildren().size());

        Label lineNumber = (Label) container.getChildren().get(0);
        TextField codeField = (TextField) container.getChildren().get(1);

        assertEquals("1", lineNumber.getText());
        assertEquals("x = 10;", codeField.getText());
    }
}
