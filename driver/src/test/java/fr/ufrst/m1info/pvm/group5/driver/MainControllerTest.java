package fr.ufrst.m1info.pvm.group5.driver;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.*;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.matcher.control.LabeledMatchers;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import static org.testfx.util.NodeQueryUtils.isVisible;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;
import static org.testfx.matcher.base.NodeMatchers.*;

/**
 * Unit tests for the MainController class
 */
@ExtendWith(ApplicationExtension.class)
public class MainControllerTest extends ApplicationTest {

    //Temporary directory for test files
    @TempDir
    static Path tempDir;

    private MainController controller;

    /**
     * Starts the JavaFX application before running the tests
     * Load the main FXML layout, initializes the controller, and displays the stage
     *
     * @param stage the primary JavaFX stage used for testing
     * @throws Exception if the FXML file cannot be loaded
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/ufrst/m1info/pvm/group5/driver/main-view.fxml"));
        Scene scene = new Scene(loader.load(), 800, 600);
        controller = loader.getController();

        stage.setScene(scene);
        stage.show();
    }


    /**
     * Creates a temporary test file in the temporary directory
     *
     * The file will contain the given lines of text, each followed by a newline character.
     * This method is typically used to simulate MiniJaja or JajaCode source files for testing file-loading features.
     *
     * @param filename the name of the file to create
     * @param lines the lines of text to write into the file
     * @return the created File object
     * @throws IOException if an error occurs while writing to the file
     */
    public static File createTestFile(String filename, String... lines) throws IOException {
        File testFile = tempDir.resolve(filename).toFile();

        try (FileWriter writer = new FileWriter(testFile)){
            for (String line : lines){
                writer.write(line + "\n");
            }
        }
        return testFile;
    }


    @Test
    public void testInitialState(){
        FxAssert.verifyThat("#fileLabel", LabeledMatchers.hasText("No file selected"));

        assertTrue(controller.getCodeLines().isEmpty(), "The ListView should be empty at the start");
    }

    @Test
    public void testButtonsAreVisible(){
        FxAssert.verifyThat("#btnOpen", isVisible());
        FxAssert.verifyThat("#btnSave", isVisible());
        FxAssert.verifyThat("#btnRun", isVisible());
        FxAssert.verifyThat("#btnCompile", isVisible());
        FxAssert.verifyThat("#btnRunCompile", isVisible());
    }

    @Test
    public void testLoadFileSuccess() throws IOException{
        File testFile = createTestFile("test.mjj", "int x = 10;", "x = x + 1;");

        interact(() -> {
            boolean success = controller.loadFile(testFile);
            assertTrue(success);
        });

        assertEquals("test.mjj", controller.getFileLabel().getText());
        assertEquals(2, controller.getCodeLines().size());
        assertEquals("int x = 10;", controller.getCodeLines().getFirst().getCode());
        assertEquals("x = x + 1;", controller.getCodeLines().get(1).getCode());
    }

    @Test
    public void testLoadFileNull(){
        interact(() -> {
            boolean success = controller.loadFile(null);
            assertFalse(success);
        });

        assertEquals("No file selected", controller.getFileLabel().getText());
        assertTrue(controller.getCodeLines().isEmpty());
    }

    @Test
    public void testLoadFileNoExistent(){
        File nonExistent = tempDir.resolve("does_not_exist.mjj").toFile();

        assertFalse(nonExistent.exists());

        interact(() -> {
            boolean success = controller.loadFile(nonExistent);
            assertFalse(success);
        });
    }

    @Test
    public void testLoadEmptyFile() throws IOException {
        File emptyFile = createTestFile("empty.mjj");

        assertTrue(emptyFile.exists());

        interact(() -> {
            boolean success = controller.loadFile(emptyFile);
            assertTrue(success);
        });

        assertEquals("empty.mjj", controller.getFileLabel().getText());
        assertTrue(controller.getCodeLines().isEmpty());
    }

    @Test
    public void testLoadMultipleLines() throws IOException {
        File multipleLines = createTestFile("multiple.mjj", "line 1", "line 2", "line 3", "line 4", "line 5");

        interact(() -> {
            boolean success = controller.loadFile(multipleLines);
            assertTrue(success);
        });

        assertEquals("multiple.mjj", controller.getFileLabel().getText());
        assertEquals(5, controller.getCodeLines().size());
        for (int i = 0; i < 5; i++){
            assertEquals(i + 1, controller.getCodeLines().get(i).getLineNumber());
            assertEquals("line " + (i + 1), controller.getCodeLines().get(i).getCode());
        }
    }

    @Test
    public void testLoadFileWithSpecialCharacter() throws IOException {
        File testFile = createTestFile("special.mjj", "String message = \"L'été est reposant !\";", "// Ceci est un commentaire");

        interact(() -> {
            boolean success = controller.loadFile(testFile);
            assertTrue(success);
        });

        assertEquals("special.mjj", controller.getFileLabel().getText());
        assertEquals(2,controller.getCodeLines().size());
        assertEquals("String message = \"L'été est reposant !\";", controller.getCodeLines().getFirst().getCode());
        assertEquals("// Ceci est un commentaire", controller.getCodeLines().get(1).getCode());
    }

    @Test
    public void testLoadMultipleFilesSuccessively() throws IOException {
        File firstFile = createTestFile("first.mjj", "int x = 1;", "int y = x + 1;");

        interact(() -> {
            boolean success = controller.loadFile(firstFile);
            assertTrue(success);
        });

        assertEquals("first.mjj", controller.getFileLabel().getText());
        assertEquals(2,controller.getCodeLines().size());
        assertEquals("int x = 1;", controller.getCodeLines().getFirst().getCode());
        assertEquals("int y = x + 1;", controller.getCodeLines().get(1).getCode());

        File secondFile = createTestFile("second.mjj", "final int a = 2000;");

        interact(() -> {
            boolean success = controller.loadFile(secondFile);
            assertTrue(success);
        });

        assertEquals("second.mjj", controller.getFileLabel().getText());
        assertEquals(1,controller.getCodeLines().size());
        assertEquals("final int a = 2000;", controller.getCodeLines().getFirst().getCode());
    }

    @Test
    public void testGetModifiedCode() throws Exception{
        File testFile = createTestFile("test.mjj", "int x = 10;", "x++;");

        interact(() -> {
            boolean success = controller.loadFile(testFile);
            assertTrue(success);
        });

        ObservableList<CodeLine> codeLines = controller.getCodeLines();
        ListView<CodeLine> codeListView = controller.getCodeListView();
        interact(() -> {
            codeListView.scrollTo(0); // wait until the list view displays the first cell
        });
        WaitForAsyncUtils.waitForFxEvents();

        TextField codeField = (TextField) codeListView.lookup(".code-field");
        assertNotNull(codeField);

        clickOn(codeField).eraseText(codeLines.get(0).getCode().length()).write("int y = 12;");

        WaitForAsyncUtils.waitForFxEvents();

        assertEquals("int y = 12;", codeLines.get(0).getCode());

        assertEquals("int y = 12;\nx++;", controller.getModifiedCode());
    }

    @Test
    public void testSingleEnterAddsLineBelow() throws Exception{
        File testFile = createTestFile("test.mjj", "main () {", "int x = 10;", "}");

        interact(() -> {
            boolean success = controller.loadFile(testFile);
            assertTrue(success);
        });

        assertEquals(3, controller.getCodeLines().size());

        // force the display of the first cell
        interact(() -> {
            controller.getCodeListView().scrollTo(0);
        });
        WaitForAsyncUtils.waitForFxEvents();

        TextField firstField = (TextField) controller.getCodeListView().lookup(".code-field");
        assertNotNull(firstField, "The first TextField should exist");

        clickOn(firstField);
        WaitForAsyncUtils.waitForFxEvents();

        type(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(4, controller.getCodeLines().size());
        assertEquals(1, controller.getCodeListView().getSelectionModel().getSelectedIndex(), "Line 2 (index 1) should be selected");
    }


    @Test
    public void testMultipleEnterPresses() throws Exception {
        File testFile = createTestFile("test.mjj", "main () {", "int x = 10;", "}");

        interact(() -> {
            boolean success = controller.loadFile(testFile);
            assertTrue(success);
        });
        assertEquals(3, controller.getCodeLines().size());

        // force the display of the first cell
        interact(() -> {
            controller.getCodeListView().scrollTo(0);
        });
        WaitForAsyncUtils.waitForFxEvents();

        TextField field = (TextField) controller.getCodeListView().lookup(".code-field");
        assertNotNull(field);

        clickOn(field);
        WaitForAsyncUtils.waitForFxEvents();

        for (int i = 0; i < 10; i++){
            type(KeyCode.ENTER);
            WaitForAsyncUtils.waitForFxEvents();
        }

        assertEquals(13, controller.getCodeLines().size());
        assertEquals(10, controller.getCodeListView().getSelectionModel().getSelectedIndex(), "Line 11 (index 10) should be selected");
    }


    @Test
    public void testSaveButtonCurrentFileExisting() throws Exception {
        File testFile = createTestFile("test.mjj", "int x = 10;", "x++");

        interact(() -> {
            boolean success = controller.loadFile(testFile);
            assertTrue(success);
        });

        assertEquals(testFile, controller.getCurrentFile());
        assertEquals(2, controller.getCodeLines().size());

        interact(() -> {
            controller.getCodeListView().scrollTo(0);
        });
        WaitForAsyncUtils.waitForFxEvents();

        TextField firstField = (TextField) controller.getCodeListView().lookup(".code-field");
        assertNotNull(firstField);

        clickOn(firstField).eraseText(controller.getCodeLines().get(0).getCode().length()).write("boolean x = true;");
        WaitForAsyncUtils.waitForFxEvents();

        interact(() -> {
            controller.saveButton();
        });
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(testFile, controller.getCurrentFile());
        List<String> savedLines = Files.readAllLines(testFile.toPath(), StandardCharsets.UTF_8);
        assertEquals(2, controller.getCodeLines().size());
        assertEquals("boolean x = true;", savedLines.get(0));
        assertEquals("x++", savedLines.get(1));
    }

    @Test
    public void testSavePreservesModifications() throws Exception {
        File testFile = createTestFile("test.mjj", "line 1", "line 2", "line 3");

        interact(() -> {
            boolean success = controller.loadFile(testFile);
            assertTrue(success);
        });

        assertEquals(testFile, controller.getCurrentFile());

        controller.getCodeLines().get(0).setCode("modified line 1");
        controller.getCodeLines().get(1).setCode("modified line 2");
        controller.getCodeLines().get(2).setCode("modified line 3");

        interact(() -> {
            controller.saveButton();
        });
        WaitForAsyncUtils.waitForFxEvents();

        interact(() -> {
            controller.loadFile(testFile);
        });

        assertEquals("modified line 1", controller.getCodeLines().get(0).getCode());
        assertEquals("modified line 2", controller.getCodeLines().get(1).getCode());
        assertEquals("modified line 3", controller.getCodeLines().get(2).getCode());
    }

    @Test
    public void testSaveAfterAddingLine() throws Exception {
        File testFile = createTestFile("test.mjj", "line 1", "line 2");

        interact(() -> {
            controller.loadFile(testFile);
        });

        assertEquals(testFile, controller.getCurrentFile());

        interact(() -> {
            controller.getCodeListView().scrollTo(0);
        });
        WaitForAsyncUtils.waitForFxEvents();

        TextField firstField = (TextField) controller.getCodeListView().lookup(".code-field");
        assertNotNull(firstField);

        clickOn(firstField).type(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        interact(() -> {
            controller.saveButton();
        });
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(testFile, controller.getCurrentFile());
        List<String> savedLines = Files.readAllLines(testFile.toPath(), StandardCharsets.UTF_8);
        assertEquals(3, savedLines.size());
        assertEquals("line 1", savedLines.get(0));
        assertEquals("", savedLines.get(1));
        assertEquals("line 2", savedLines.get(2));
    }

    @Test
    public void testSaveEmptyFile() throws Exception {
        File emptyFile = createTestFile("empty.mjj");

        interact(() -> {
            controller.loadFile(emptyFile);
        });

        assertEquals(emptyFile, controller.getCurrentFile());
        assertTrue(controller.getCodeLines().isEmpty());

        interact(() -> {
            controller.saveButton();
        });
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(emptyFile, controller.getCurrentFile());
        List<String> savedLines = Files.readAllLines(emptyFile.toPath(), StandardCharsets.UTF_8);
        assertTrue(savedLines.isEmpty());
    }

    @Test
    public void testSaveButtonWhenCurrentFileNull() throws Exception {
        controller.getCodeLines().add(new CodeLine(1, "int x = 10;"));
        controller.getCodeLines().add(new CodeLine(2, "x++;"));

        assertNull(controller.getCurrentFile());

        // We cannot test saveButton() directly because it opens a FileChooser
        // But we can simulate the behaviour: saveAs with a file
        File newFile = tempDir.resolve("newFile1.mjj").toFile();

        interact(() -> {
            controller.saveAs(newFile);
        });
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(newFile, controller.getCurrentFile());
        assertTrue(newFile.exists());

        List<String> savedLines = Files.readAllLines(newFile.toPath(), StandardCharsets.UTF_8);
        assertEquals(2, savedLines.size());
        assertEquals("int x = 10;", savedLines.get(0));
        assertEquals("x++;", savedLines.get(1));
    }

    @Test
    public void testSaveAsWithNullFile() throws Exception {
        File initialFile = createTestFile("initial.mjj", "line 1", "line 2");

        interact(() -> {
            controller.loadFile(initialFile);
        });

        assertEquals(initialFile, controller.getCurrentFile());
        assertEquals("initial.mjj", controller.getFileLabel().getText());

        interact(() -> {
            controller.saveAs(null);
        });
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(initialFile, controller.getCurrentFile());
        assertEquals("initial.mjj", controller.getFileLabel().getText());
    }

    @Test
    public void testSaveAsWithNewFile() throws Exception {
        File initialFile = createTestFile("initial.mjj", "line 1", "line 2");

        interact(() -> {
            controller.loadFile(initialFile);
        });

        assertEquals(initialFile, controller.getCurrentFile());
        assertEquals("initial.mjj", controller.getFileLabel().getText());

        controller.getCodeLines().get(0).setCode("modified line 1");

        File newFile = tempDir.resolve("newFile2.mjj").toFile();
        assertFalse(newFile.exists());

        interact(() -> {
            controller.saveAs(newFile);
        });
        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(newFile.exists());
        assertEquals(newFile, controller.getCurrentFile());
        assertEquals("newFile2.mjj", controller.getFileLabel().getText());

        List<String> savedLines = Files.readAllLines(newFile.toPath(), StandardCharsets.UTF_8);
        assertEquals(2, savedLines.size());
        assertEquals("modified line 1", savedLines.get(0));
        assertEquals("line 2", savedLines.get(1));
    }

    //TODO: tests when the backspace key is pressed
    @Test
    public void testEnterThenBackspaceDeleteEmptyLine() throws Exception {
        File testFile = createTestFile("test.mjj", "int x = 10;", "x++;");

        interact(() -> {
            controller.loadFile(testFile);
        });

        interact(() -> {
            controller.getCodeListView().scrollTo(0);
        });
        WaitForAsyncUtils.waitForFxEvents();

        TextField firstField = (TextField) controller.getCodeListView().lookup(".code-field");
        assertNotNull(firstField);

        clickOn(firstField).type(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(3, controller.getCodeLines().size());
        assertEquals("int x = 10;", controller.getCodeLines().get(0).getCode());
        assertEquals("", controller.getCodeLines().get(1).getCode());
        assertEquals("x++;", controller.getCodeLines().get(2).getCode());
        assertEquals(1, controller.getCodeListView().getSelectionModel().getSelectedIndex());

        type(KeyCode.BACK_SPACE);
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(2, controller.getCodeLines().size());
        assertEquals(0, controller.getCodeListView().getSelectionModel().getSelectedIndex());
        assertEquals("int x = 10;", controller.getCodeLines().get(0).getCode());
        assertEquals("x++;", controller.getCodeLines().get(1).getCode());
    }

    @Test
    public void testDeleteTextThenBackspaceDeletesEmptyLines() throws Exception {
        File testFile = createTestFile("test.mjj", "main () {", "abc", "}");
        interact(() -> {
            controller.loadFile(testFile);
        });

        interact(() -> {
            controller.getCodeListView().scrollTo(1);
            controller.getCodeListView().getSelectionModel().select(1);
        });
        WaitForAsyncUtils.waitForFxEvents();
        Thread.sleep(100);
        assertEquals(1, controller.getCodeListView().getSelectionModel().getSelectedIndex());

        // Find the TextField of the selected row
        TextField field = null;
        for (Object node : controller.getCodeListView().lookupAll(".code-field")) {
            if (node instanceof TextField tf) {
                // Check the content to ensure that you have the correct TextField
                if ("abc".equals(tf.getText())) {
                    field = tf;
                    break;
                }
            }
        }
        assertNotNull(field);

        clickOn(field);
        WaitForAsyncUtils.waitForFxEvents();

        interact(field::end);
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals("abc", controller.getCodeLines().get(1).getCode());

        type(KeyCode.BACK_SPACE); //c
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals("ab", controller.getCodeLines().get(1).getCode());

        type(KeyCode.BACK_SPACE); //b
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals("a", controller.getCodeLines().get(1).getCode());

        type(KeyCode.BACK_SPACE); //a
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals("", controller.getCodeLines().get(1).getCode());
        assertEquals(3, controller.getCodeLines().size());

        type(KeyCode.BACK_SPACE);
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(2, controller.getCodeLines().size());
        assertEquals(0, controller.getCodeListView().getSelectionModel().getSelectedIndex());
    }


    @Test
    public void testSelectEmptyLineThenBackspaceDeletesImmediately() throws Exception {
        File testFile = createTestFile("test.mjj", "main () {", "", "}");
        interact(() -> controller.loadFile(testFile));

        assertTrue(controller.getCodeLines().get(1).getCode().isEmpty());
        assertEquals(3, controller.getCodeLines().size());

        interact(() -> {
            controller.getCodeListView().scrollTo(1);
            controller.getCodeListView().getSelectionModel().select(1);
        });
        WaitForAsyncUtils.waitForFxEvents();
        Thread.sleep(100);
        assertEquals(1, controller.getCodeListView().getSelectionModel().getSelectedIndex());

        // Find the TextField of the selected row
        TextField field = null;
        for (Object node : controller.getCodeListView().lookupAll(".code-field")) {
            if (node instanceof TextField tf) {
                // Check the content to ensure that you have the correct TextField
                if ("".equals(tf.getText())) {
                    field = tf;
                    break;
                }
            }
        }
        assertNotNull(field);

        clickOn(field);
        WaitForAsyncUtils.waitForFxEvents();

        type(KeyCode.BACK_SPACE);
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(2, controller.getCodeLines().size());
        assertEquals("main () {", controller.getCodeLines().get(0).getCode());
        assertEquals("}", controller.getCodeLines().get(1).getCode());
    }

    @Test
    public void testCannotDeleteLastLine() throws Exception {
        File testFile = createTestFile("test.mjj", "");
        interact(() -> controller.loadFile(testFile));

        assertEquals(1, controller.getCodeLines().size());
        assertTrue(controller.getCodeLines().get(0).getCode().isEmpty());

        interact(() -> {
            controller.getCodeListView().scrollTo(0);
        });
        WaitForAsyncUtils.waitForFxEvents();

        TextField field = (TextField) controller.getCodeListView().lookup(".code-field");
        assertNotNull(field);

        clickOn(field);
        WaitForAsyncUtils.waitForFxEvents();

        type(KeyCode.BACK_SPACE);
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(1, controller.getCodeLines().size());
        assertTrue(controller.getCodeLines().get(0).getCode().isEmpty());
    }

    @Test
    public void testBackspaceInMiddleOfTextDoesNotDeleteLine() throws Exception {
        File testFile = createTestFile("test.mjj", "int x = 10;", "x++;");
        interact(() -> controller.loadFile(testFile));

        assertEquals(2, controller.getCodeLines().size());

        interact(() -> {
            controller.getCodeListView().scrollTo(0);
        });
        WaitForAsyncUtils.waitForFxEvents();

        TextField field = (TextField) controller.getCodeListView().lookup(".code-field");
        assertNotNull(field);

        clickOn(field);
        WaitForAsyncUtils.waitForFxEvents();

        interact(() -> field.positionCaret(2));
        WaitForAsyncUtils.waitForFxEvents();

        type(KeyCode.BACK_SPACE);
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(2, controller.getCodeLines().size());
        assertEquals("it x = 10;", controller.getCodeLines().get(0).getCode());
        assertEquals("x++;", controller.getCodeLines().get(1).getCode());
    }

    @Test
    public void testTypingAfterEmptyingLineResetFlag() throws Exception {
        File testFile = createTestFile("test.mjj", "main () {", "x", "}");
        interact(() -> {
            controller.loadFile(testFile);
        });

        assertEquals(3, controller.getCodeLines().size());
        assertEquals("x", controller.getCodeLines().get(1).getCode());

        interact(() -> {
            controller.getCodeListView().scrollTo(1);
            controller.getCodeListView().getSelectionModel().select(1);
        });
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(1, controller.getCodeListView().getSelectionModel().getSelectedIndex());

        // Find the TextField of the selected row
        TextField field = null;
        for (Object node : controller.getCodeListView().lookupAll(".code-field")) {
            if (node instanceof TextField tf) {
                // Check the content to ensure that you have the correct TextField
                if ("x".equals(tf.getText())) {
                    field = tf;
                    break;
                }
            }
        }
        assertNotNull(field);

        clickOn(field);
        WaitForAsyncUtils.waitForFxEvents();

        interact(field::end);
        WaitForAsyncUtils.waitForFxEvents();

        type(KeyCode.BACK_SPACE);
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(3, controller.getCodeLines().size());
        assertEquals("", controller.getCodeLines().get(1).getCode());

        type(KeyCode.Y);
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(3, controller.getCodeLines().size());
        assertEquals("y", controller.getCodeLines().get(1).getCode());

        type(KeyCode.BACK_SPACE);
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(3, controller.getCodeLines().size());
        assertEquals("", controller.getCodeLines().get(1).getCode());

        type(KeyCode.BACK_SPACE);
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(2, controller.getCodeLines().size());
    }

    @Test
    public void testMultipleEmptyLinesCreationAndDeletion() throws Exception {
        File testFile = createTestFile("test.mjj", "line 1");
        interact(() -> controller.loadFile(testFile));

        assertEquals(1, controller.getCodeLines().size());

        interact(() -> {
            controller.getCodeListView().scrollTo(0);
        });
        WaitForAsyncUtils.waitForFxEvents();

        TextField field = (TextField) controller.getCodeListView().lookup(".code-field");
        assertNotNull(field);

        clickOn(field);
        WaitForAsyncUtils.waitForFxEvents();

        for (int i = 0; i < 5; i++){
            type(KeyCode.ENTER);
            WaitForAsyncUtils.waitForFxEvents();
        }

        assertEquals(6, controller.getCodeLines().size());

        for (int i = 0; i < 5; i++){
            type(KeyCode.BACK_SPACE);
            WaitForAsyncUtils.waitForFxEvents();
        }

        assertEquals(1, controller.getCodeLines().size());
    }

    @Test
    public void getBaseFileNameNullFileReturnsNull(){
        assertNull(controller.getBaseFileName(null));
    }

    @Test
    public void getBaseFileNameReturnsNameWithoutExtension(){
        assertEquals("test", controller.getBaseFileName("test.mjj"));
    }

    @Test
    public void getBaseFileNameNoExtensionReturnsFullName(){
        assertEquals("test", controller.getBaseFileName("test"));
    }

    @Test
    public void getBaseFileNameMultipleExtensionsReturnsBeforeLastDot(){
        assertEquals("test.jjc", controller.getBaseFileName("test.jjc.mjj"));
    }

    @Test
    public void testNewFileCreatesEmptyDocument(){
        assertTrue(controller.getCodeLines().isEmpty());

        clickOn("#btnNew");
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(1, controller.getCodeLines().size());
        assertTrue(controller.getCodeLines().getFirst().getCode().isEmpty());
        assertEquals(0, controller.getCodeListView().getSelectionModel().getSelectedIndex());
        assertNull(controller.getCurrentFile());
        assertEquals("New file", controller.getFileLabel().getText());
    }

    @Test
    public void testNewFileReplacesExistingLoadedFile() throws Exception {
        File testFile = createTestFile("test.mjj", "main () {", "int x = 10;", "}");
        interact(() -> controller.loadFile(testFile));

        assertEquals(3, controller.getCodeLines().size());
        assertEquals(testFile, controller.getCurrentFile());
        assertEquals("main () {", controller.getCodeLines().get(0).getCode());
        assertEquals("test.mjj", controller.getFileLabel().getText());

        interact(() -> controller.createNewFile());
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(1, controller.getCodeLines().size());
        assertTrue(controller.getCodeLines().getFirst().getCode().isEmpty());
        assertNull(controller.getCurrentFile());
        assertEquals("New file", controller.getFileLabel().getText());
    }

    @Test
    public void testArrowUpSelectsPreviousLine() throws Exception {
        File testFile = createTestFile("test.mjj", "line 1", "line 2", "line 3", "line 4");
        interact(() -> controller.loadFile(testFile));

        assertEquals(4, controller.getCodeLines().size());

        interact(() -> {
            controller.getCodeListView().scrollTo(1);
            controller.getCodeListView().getSelectionModel().select(1);
        });
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(1, controller.getCodeListView().getSelectionModel().getSelectedIndex());

        // Find the TextField of the selected row
        TextField field = null;
        for (Object node : controller.getCodeListView().lookupAll(".code-field")) {
            if (node instanceof TextField tf) {
                // Check the content to ensure that you have the correct TextField
                if ("line 2".equals(tf.getText())) {
                    field = tf;
                    break;
                }
            }
        }
        assertNotNull(field);

        clickOn(field);
        WaitForAsyncUtils.waitForFxEvents();

        type(KeyCode.UP);
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(4, controller.getCodeLines().size());
        assertEquals(0, controller.getCodeListView().getSelectionModel().getSelectedIndex());
    }

    @Test
    public void testArrowUpOnFirstLineDoesNothing() throws Exception {
        File testFile = createTestFile("test.mjj", "line 1", "line 2", "line 3");
        interact(() -> controller.loadFile(testFile));

        interact(() -> {
            controller.getCodeListView().scrollTo(0);
            controller.getCodeListView().getSelectionModel().select(0);
        });
        WaitForAsyncUtils.waitForFxEvents();

        TextField field = (TextField) controller.getCodeListView().lookup(".code-field");
        assertNotNull(field);

        clickOn(field);
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(0, controller.getCodeListView().getSelectionModel().getSelectedIndex());

        type(KeyCode.UP);
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(0, controller.getCodeListView().getSelectionModel().getSelectedIndex());
    }

    @Test
    public void testArrowDownSelectsNextLine() throws Exception {
        File testFile = createTestFile("test.mjj", "line 1", "line 2", "line 3");
        interact(() -> controller.loadFile(testFile));

        interact(() -> {
            controller.getCodeListView().scrollTo(0);
        });
        WaitForAsyncUtils.waitForFxEvents();

        TextField field = (TextField) controller.getCodeListView().lookup(".code-field");
        assertNotNull(field);

        clickOn(field);
        WaitForAsyncUtils.waitForFxEvents();

        type(KeyCode.DOWN);
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(1, controller.getCodeListView().getSelectionModel().getSelectedIndex());
    }

    @Test
    public void testArrowDownOnLastLineDoesNothing() throws Exception {
        File testFile = createTestFile("test.mjj", "line 1", "line 2", "line 3");
        interact(() -> controller.loadFile(testFile));

        interact(() -> {
            controller.getCodeListView().scrollTo(2);
            controller.getCodeListView().getSelectionModel().select(2);
        });
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(2, controller.getCodeListView().getSelectionModel().getSelectedIndex());

        // Find the TextField of the selected row
        TextField field = null;
        for (Object node : controller.getCodeListView().lookupAll(".code-field")) {
            if (node instanceof TextField tf) {
                // Check the content to ensure that you have the correct TextField
                if ("line 3".equals(tf.getText())) {
                    field = tf;
                    break;
                }
            }
        }
        assertNotNull(field);

        clickOn(field);
        WaitForAsyncUtils.waitForFxEvents();

        type(KeyCode.DOWN);
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(2, controller.getCodeListView().getSelectionModel().getSelectedIndex());
    }

    @Test
    public void testButtonsActivatedAfterClickNew(){
        interact(() ->  controller.deactiveButtons());

        verifyThat("#btnSave", isDisabled());
        verifyThat("#btnSaveAs", isDisabled());
        verifyThat("#btnRun", isDisabled());
        verifyThat("#btnCompile", isDisabled());
        verifyThat("#btnRunCompile", isDisabled());

        clickOn("#btnNew");
        WaitForAsyncUtils.waitForFxEvents();

        verifyThat("#btnSave", isEnabled());
        verifyThat("#btnSaveAs", isEnabled());
        verifyThat("#btnRun", isEnabled());
        verifyThat("#btnCompile", isEnabled());
        verifyThat("#btnRunCompile", isEnabled());
    }

    @Test
    public void testButtonsActivatedAfterLoadedFile() throws Exception {
        File testFile = createTestFile("test.mjj", "line 1", "line 2");

        verifyThat("#btnSave", isDisabled());
        verifyThat("#btnSaveAs", isDisabled());
        verifyThat("#btnRun", isDisabled());
        verifyThat("#btnCompile", isDisabled());
        verifyThat("#btnRunCompile", isDisabled());

        interact(() -> controller.loadFile(testFile));

        verifyThat("#btnSave", isEnabled());
        verifyThat("#btnSaveAs", isEnabled());
        verifyThat("#btnRun", isEnabled());
        verifyThat("#btnCompile", isEnabled());
        verifyThat("#btnRunCompile", isEnabled());
    }

    @Test
    public void testIsMinijajaFileWithNullFile(){
        assertNull(controller.getCurrentFile());
        assertFalse(controller.isMinijajaFile());
    }

    @Test
    public void testIsMinijajaFileWithMinijajaFile() throws Exception {
        File testFile = createTestFile("test.mjj", "line 1", "line 2");
        interact(() -> controller.loadFile(testFile));
        assertEquals(testFile, controller.getCurrentFile());
        assertTrue(controller.isMinijajaFile());

    }

    @Test
    public void testIsMinijajaFileWithJajacodeFile() throws Exception {
        File testFile = createTestFile("test.jjc", "line 1", "line 2");
        interact(() -> controller.loadFile(testFile));
        assertEquals(testFile, controller.getCurrentFile());
        assertFalse(controller.isMinijajaFile());
    }



}
