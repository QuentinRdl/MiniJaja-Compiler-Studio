package fr.ufrst.m1info.pvm.group5.driver;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.base.NodeMatchers.isInvisible;

/**
 * Unit tests for the CodeLineCell class
 */
@ExtendWith(ApplicationExtension.class)
public class CodeLineCellTest extends ApplicationTest {

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

        StackPane lineNumberContainer = (StackPane) container.getChildren().get(0);
        Label lineNumber = (Label) cell.getLineNumberLabel();
        TextField codeField = (TextField) container.getChildren().get(1);

        assertEquals("1", lineNumber.getText());
        assertEquals("x = 10;", codeField.getText());
    }

    @Test
    public void testBreakpointShouldBeVisible(){
        CodeLineCell cell = new CodeLineCell();
        CodeLine codeLine = new CodeLine(1, "int x = 10;");
        codeLine.setBreakpoint(true);
        assertTrue(codeLine.isBreakpoint());

        cell.updateItem(codeLine, false);
        StackPane lineNumberContainer = cell.getLineNumberContainer();

        FxAssert.verifyThat(lineNumberContainer.getChildren().get(0), isVisible()); //breakpoint
        FxAssert.verifyThat(lineNumberContainer.getChildren().get(1), isInvisible()); //lineNumber
    }

    @Test
    public void testBreakpointShouldNotBeVisible(){
        CodeLineCell cell = new CodeLineCell();
        CodeLine codeLine = new CodeLine(1, "int x = 10;");
        assertFalse(codeLine.isBreakpoint());

        cell.updateItem(codeLine, false);
        StackPane lineNumberContainer = cell.getLineNumberContainer();

        FxAssert.verifyThat(lineNumberContainer.getChildren().get(0), isInvisible()); //breakpoint
        FxAssert.verifyThat(lineNumberContainer.getChildren().get(1), isVisible()); //lineNumber
    }

    @Test
    public void testBreakpointDisplayWhenLineNumberContainerIsClicked(){
        CodeLineCell cell = new CodeLineCell();
        CodeLine codeLine = new CodeLine(1, "int x = 10;");
        assertFalse(codeLine.isBreakpoint());

        Platform.runLater(() -> {
            cell.updateItem(codeLine, false);

            StackPane root = new StackPane(cell.getGraphic());
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 400, 300));
            stage.show();
        });

        WaitForAsyncUtils.waitForFxEvents();

        StackPane lineNumberContainer = cell.getLineNumberContainer();

        FxAssert.verifyThat(lineNumberContainer.getChildren().get(0), isInvisible()); //breakpoint
        FxAssert.verifyThat(lineNumberContainer.getChildren().get(1), isVisible()); //lineNumber

        clickOn(lineNumberContainer);

        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(codeLine.isBreakpoint());

        FxAssert.verifyThat(lineNumberContainer.getChildren().get(0), isVisible()); //breakpoint
        FxAssert.verifyThat(lineNumberContainer.getChildren().get(1), isInvisible()); //lineNumber
    }

    @Test
    public void testRemoveBreakpointWhenLineNumberContainerIsClicked(){
        CodeLineCell cell = new CodeLineCell();
        CodeLine codeLine = new CodeLine(1, "int x = 10;");
        codeLine.setBreakpoint(true);
        assertTrue(codeLine.isBreakpoint());

        Platform.runLater(() -> {
            cell.updateItem(codeLine, false);

            StackPane root = new StackPane(cell.getGraphic());
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 400, 300));
            stage.show();
        });

        WaitForAsyncUtils.waitForFxEvents();

        StackPane lineNumberContainer = cell.getLineNumberContainer();

        FxAssert.verifyThat(lineNumberContainer.getChildren().get(0), isVisible()); //breakpoint
        FxAssert.verifyThat(lineNumberContainer.getChildren().get(1), isInvisible()); //lineNumber

        clickOn(lineNumberContainer);

        WaitForAsyncUtils.waitForFxEvents();

        assertFalse(codeLine.isBreakpoint());

        FxAssert.verifyThat(lineNumberContainer.getChildren().get(0), isInvisible()); //breakpoint
        FxAssert.verifyThat(lineNumberContainer.getChildren().get(1), isVisible()); //lineNumber
    }

}
