package fr.ufrst.m1info.pvm.group5.driver;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;
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

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Unit tests for the MainController class
 */
@ExtendWith(ApplicationExtension.class)
class MainControllerTest extends ApplicationTest {

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

    /**
     * Retrieves the TextField corresponding to a specific line index in the editor
     * Iterates through all  nodes styled as .code-field and returns the one whose text
     * matches the CodeLine at the given index. Returns null if no match is found
     *
     * @param lineIndex index of the code line to look for
     * @return the matching TextField, or null if none is found
     */
    private TextField getTextFieldForLine(int lineIndex) {
        for (Object node : controller.getCodeListView().lookupAll(".code-field")) {
            if (node instanceof TextField tf) {
                // Checks if this TextField corresponds to the target line
                CodeLine codeLine = controller.getCodeLines().get(lineIndex);
                if (codeLine.getCode().equals(tf.getText())) {
                    return tf;
                }
            }
        }
        return null;
    }


    @Test
    void testInitialState(){
        assertEquals("Untitled", controller.getSourceTab().getText());
        assertEquals(1, controller.getCodeLines().size(), "The ListView should contains one empty line at the start");
        assertTrue(controller.getCodeLines().getFirst().getCode().isEmpty(), "The first line should be empty");
    }

    @Test
    void testButtonsAreVisible(){
        FxAssert.verifyThat("#btnOpen", isVisible());
        FxAssert.verifyThat("#btnSave", isVisible());
        FxAssert.verifyThat("#btnRun", isVisible());
        FxAssert.verifyThat("#btnCompile", isVisible());
        FxAssert.verifyThat("#btnRunCompile", isVisible());
    }

    @Test
    void testLoadFileSuccess() throws IOException{
        File testFile = createTestFile("test.mjj", "int x = 10;", "x = x + 1;");

        interact(() -> {
            boolean success = controller.loadFile(testFile);
            assertTrue(success);
        });
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals("test.mjj", controller.getSourceTab().getText());
        assertEquals(2, controller.getCodeLines().size());
        assertEquals("int x = 10;", controller.getCodeLines().getFirst().getCode());
        assertEquals("x = x + 1;", controller.getCodeLines().get(1).getCode());
    }

    @Test
    void testLoadFileNull(){
        interact(() -> {
            boolean success = controller.loadFile(null);
            assertFalse(success);
        });
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals("Untitled", controller.getSourceTab().getText());
        assertEquals(1, controller.getCodeLines().size());
    }

    @Test
    void testLoadFileNoExistent(){
        File nonExistent = tempDir.resolve("does_not_exist.mjj").toFile();

        assertFalse(nonExistent.exists());

        interact(() -> {
            boolean success = controller.loadFile(nonExistent);
            assertFalse(success);
        });
        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(controller.output.getText().contains("[ERROR] File doesn't exist : does_not_exist.mjj"));
    }

    @Test
    void testLoadEmptyFile() throws IOException {
        File emptyFile = createTestFile("empty.mjj");

        assertTrue(emptyFile.exists());

        interact(() -> {
            boolean success = controller.loadFile(emptyFile);
            assertTrue(success);
        });
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals("empty.mjj", controller.getSourceTab().getText());
        assertTrue(controller.getCodeLines().isEmpty());
    }

    @Test
    void testLoadMultipleLines() throws IOException {
        File multipleLines = createTestFile("multiple.mjj", "line 1", "line 2", "line 3", "line 4", "line 5");

        interact(() -> {
            boolean success = controller.loadFile(multipleLines);
            assertTrue(success);
        });
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals("multiple.mjj", controller.getSourceTab().getText());
        assertEquals(5, controller.getCodeLines().size());
        for (int i = 0; i < 5; i++){
            assertEquals(i + 1, controller.getCodeLines().get(i).getLineNumber());
            assertEquals("line " + (i + 1), controller.getCodeLines().get(i).getCode());
        }
    }

    @Test
    void testLoadFileWithSpecialCharacter() throws IOException {
        File testFile = createTestFile("special.mjj", "String message = \"L'été est reposant !\";", "// Ceci est un commentaire");

        interact(() -> {
            boolean success = controller.loadFile(testFile);
            assertTrue(success);
        });
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals("special.mjj", controller.getSourceTab().getText());
        assertEquals(2,controller.getCodeLines().size());
        assertEquals("String message = \"L'été est reposant !\";", controller.getCodeLines().getFirst().getCode());
        assertEquals("// Ceci est un commentaire", controller.getCodeLines().get(1).getCode());
    }

    @Test
    void testLoadMultipleFilesSuccessively() throws IOException {
        File firstFile = createTestFile("first.mjj", "int x = 1;", "int y = x + 1;");

        interact(() -> {
            boolean success = controller.loadFile(firstFile);
            assertTrue(success);
        });
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals("first.mjj", controller.getSourceTab().getText());
        assertEquals(2,controller.getCodeLines().size());
        assertEquals("int x = 1;", controller.getCodeLines().getFirst().getCode());
        assertEquals("int y = x + 1;", controller.getCodeLines().get(1).getCode());

        File secondFile = createTestFile("second.mjj", "final int a = 2000;");

        interact(() -> {
            boolean success = controller.loadFile(secondFile);
            assertTrue(success);
        });

        assertEquals("second.mjj", controller.getSourceTab().getText());
        assertEquals(1,controller.getCodeLines().size());
        assertEquals("final int a = 2000;", controller.getCodeLines().getFirst().getCode());
    }

    @Test
    void testGetModifiedCode() throws Exception{
        File testFile = createTestFile("test.mjj", "int x = 10;", "x++;");

        interact(() -> {
            boolean success = controller.loadFile(testFile);
            assertTrue(success);
        });
        WaitForAsyncUtils.waitForFxEvents();

        ObservableList<CodeLine> codeLines = controller.getCodeLines();
        ListView<CodeLine> codeListView = controller.getCodeListView();
        interact(() -> {
            codeListView.scrollTo(0); // wait until the list view displays the first cell
        });
        WaitForAsyncUtils.waitForFxEvents();

        TextField codeField = getTextFieldForLine(0);
        assertNotNull(codeField);

        clickOn(codeField).eraseText(codeLines.getFirst().getCode().length()).write("int y = 12;");

        WaitForAsyncUtils.waitForFxEvents();

        assertEquals("int y = 12;", codeLines.getFirst().getCode());

        assertEquals("int y = 12;\nx++;", controller.getModifiedCode());
    }

    @Test
    void testSingleEnterAddsLineBelow() throws Exception{
        File testFile = createTestFile("test.mjj", "main () {", "int x = 10;", "}");

        interact(() -> {
            boolean success = controller.loadFile(testFile);
            assertTrue(success);
        });
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(3, controller.getCodeLines().size());

        // force the display of the first cell
        interact(() -> {
            controller.getCodeListView().scrollTo(0);
        });
        WaitForAsyncUtils.waitForFxEvents();

        TextField firstField = getTextFieldForLine(0);
        assertNotNull(firstField, "The first TextField should exist");

        clickOn(firstField);
        WaitForAsyncUtils.waitForFxEvents();

        type(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(4, controller.getCodeLines().size());
        assertEquals(1, controller.getCodeListView().getSelectionModel().getSelectedIndex(), "Line 2 (index 1) should be selected");
    }


    @Test
    void testMultipleEnterPresses() throws Exception {
        File testFile = createTestFile("test.mjj", "main () {", "int x = 10;", "}");

        interact(() -> {
            boolean success = controller.loadFile(testFile);
            assertTrue(success);
        });
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(3, controller.getCodeLines().size());

        // force the display of the first cell
        interact(() -> {
            controller.getCodeListView().scrollTo(0);
        });
        WaitForAsyncUtils.waitForFxEvents();

        TextField field = getTextFieldForLine(0);
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
    void testSaveButtonCurrentFileExisting() throws Exception {
        File testFile = createTestFile("test.mjj", "int x = 10;", "x++");

        interact(() -> {
            boolean success = controller.loadFile(testFile);
            assertTrue(success);
        });
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(testFile, controller.getCurrentFile());
        assertEquals(2, controller.getCodeLines().size());

        interact(() -> {
            controller.getCodeListView().scrollTo(0);
        });
        WaitForAsyncUtils.waitForFxEvents();

        TextField firstField = getTextFieldForLine(0);
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
    void testSavePreservesModifications() throws Exception {
        File testFile = createTestFile("test.mjj", "line 1", "line 2", "line 3");

        interact(() -> {
            boolean success = controller.loadFile(testFile);
            assertTrue(success);
        });
        WaitForAsyncUtils.waitForFxEvents();

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
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals("modified line 1", controller.getCodeLines().get(0).getCode());
        assertEquals("modified line 2", controller.getCodeLines().get(1).getCode());
        assertEquals("modified line 3", controller.getCodeLines().get(2).getCode());
    }

    @Test
    void testSaveAfterAddingLine() throws Exception {
        File testFile = createTestFile("test.mjj", "line 1", "line 2");

        interact(() -> {
            controller.loadFile(testFile);
        });
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(testFile, controller.getCurrentFile());

        interact(() -> {
            controller.getCodeListView().scrollTo(0);
        });
        WaitForAsyncUtils.waitForFxEvents();

        TextField firstField = getTextFieldForLine(0);
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
    void testSaveEmptyFile() throws Exception {
        File emptyFile = createTestFile("empty.mjj");

        interact(() -> {
            controller.loadFile(emptyFile);
        });
        WaitForAsyncUtils.waitForFxEvents();

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
    void testSaveButtonWhenCurrentFileNull() throws Exception {
        interact(() -> {
            controller.getCodeLines().clear();
            controller.getCodeLines().add(new CodeLine(1, "int x = 10;"));
            controller.getCodeLines().add(new CodeLine(2, "x++;"));
        });
        WaitForAsyncUtils.waitForFxEvents();

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
    void testSaveAsWithNullFile() throws Exception {
        File initialFile = createTestFile("initial.mjj", "line 1", "line 2");

        interact(() -> {
            controller.loadFile(initialFile);
        });
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(initialFile, controller.getCurrentFile());
        assertEquals("initial.mjj", controller.getSourceTab().getText());

        interact(() -> {
            controller.saveAs(null);
        });
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(initialFile, controller.getCurrentFile());
        assertEquals("initial.mjj", controller.getSourceTab().getText());
    }

    @Test
    void testSaveAsWithNewFile() throws Exception {
        File initialFile = createTestFile("initial.mjj", "line 1", "line 2");

        interact(() -> {
            controller.loadFile(initialFile);
        });
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(initialFile, controller.getCurrentFile());
        assertEquals("initial.mjj", controller.getSourceTab().getText());

        controller.getCodeLines().get(0).setCode("modified line 1");

        File newFile = tempDir.resolve("newFile2.mjj").toFile();
        assertFalse(newFile.exists());

        interact(() -> {
            controller.saveAs(newFile);
        });
        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(newFile.exists());
        assertEquals(newFile, controller.getCurrentFile());
        assertEquals("newFile2.mjj", controller.getSourceTab().getText());

        List<String> savedLines = Files.readAllLines(newFile.toPath(), StandardCharsets.UTF_8);
        assertEquals(2, savedLines.size());
        assertEquals("modified line 1", savedLines.get(0));
        assertEquals("line 2", savedLines.get(1));
    }

    @Test
    void testEnterThenBackspaceDeleteEmptyLine() throws Exception {
        File testFile = createTestFile("test.mjj", "int x = 10;", "x++;");

        interact(() -> {
            controller.loadFile(testFile);
        });
        WaitForAsyncUtils.waitForFxEvents();

        interact(() -> {
            controller.getCodeListView().scrollTo(0);
        });
        WaitForAsyncUtils.waitForFxEvents();

        TextField firstField = getTextFieldForLine(0);
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
    void testDeleteTextThenBackspaceDeletesEmptyLines() throws Exception {
        File testFile = createTestFile("test.mjj", "main () {", "abc", "}");
        interact(() -> {
            controller.loadFile(testFile);
        });
        WaitForAsyncUtils.waitForFxEvents();

        interact(() -> {
            controller.getCodeListView().scrollTo(1);
            controller.getCodeListView().getSelectionModel().select(1);
        });
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(1, controller.getCodeListView().getSelectionModel().getSelectedIndex());

        // Find the TextField of the selected row
        TextField field = getTextFieldForLine(1);
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
    void testSelectEmptyLineThenBackspaceDeletesImmediately() throws Exception {
        File testFile = createTestFile("test.mjj", "main () {", "", "}");
        interact(() -> controller.loadFile(testFile));
        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(controller.getCodeLines().get(1).getCode().isEmpty());
        assertEquals(3, controller.getCodeLines().size());

        interact(() -> {
            controller.getCodeListView().scrollTo(1);
            controller.getCodeListView().getSelectionModel().select(1);
        });
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(1, controller.getCodeListView().getSelectionModel().getSelectedIndex());

        // Find the TextField of the selected row
        TextField field = getTextFieldForLine(1);
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
    void testCannotDeleteLastLine() throws Exception {
        File testFile = createTestFile("test.mjj", "");
        interact(() -> controller.loadFile(testFile));
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(1, controller.getCodeLines().size());
        assertTrue(controller.getCodeLines().get(0).getCode().isEmpty());

        interact(() -> {
            controller.getCodeListView().scrollTo(0);
        });
        WaitForAsyncUtils.waitForFxEvents();

        TextField field = getTextFieldForLine(0);
        assertNotNull(field);

        clickOn(field);
        WaitForAsyncUtils.waitForFxEvents();

        type(KeyCode.BACK_SPACE);
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(1, controller.getCodeLines().size());
        assertTrue(controller.getCodeLines().get(0).getCode().isEmpty());
    }

    @Test
    void testBackspaceInMiddleOfTextDoesNotDeleteLine() throws Exception {
        File testFile = createTestFile("test.mjj", "int x = 10;", "x++;");
        interact(() -> controller.loadFile(testFile));
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(2, controller.getCodeLines().size());

        interact(() -> {
            controller.getCodeListView().scrollTo(0);
        });
        WaitForAsyncUtils.waitForFxEvents();

        TextField field = getTextFieldForLine(0);
        assertNotNull(field);

        clickOn(field);
        WaitForAsyncUtils.waitForFxEvents();

        interact(() -> {
            field.positionCaret(2);
            field.requestFocus();
        });
        WaitForAsyncUtils.waitForFxEvents();

        type(KeyCode.BACK_SPACE);
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(2, controller.getCodeLines().size());
        assertEquals("it x = 10;", controller.getCodeLines().get(0).getCode());
        assertEquals("x++;", controller.getCodeLines().get(1).getCode());
    }

    @Test
    void testTypingAfterEmptyingLineResetFlag() throws Exception {
        File testFile = createTestFile("test.mjj", "main () {", "x", "}");
        interact(() -> {
            controller.loadFile(testFile);
        });
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(3, controller.getCodeLines().size());
        assertEquals("x", controller.getCodeLines().get(1).getCode());

        interact(() -> {
            controller.getCodeListView().scrollTo(1);
            controller.getCodeListView().getSelectionModel().select(1);
        });
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(1, controller.getCodeListView().getSelectionModel().getSelectedIndex());

        TextField field = getTextFieldForLine(1);
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
    void testMultipleEmptyLinesCreationAndDeletion() throws Exception {
        File testFile = createTestFile("test.mjj", "line 1");
        interact(() -> controller.loadFile(testFile));
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(1, controller.getCodeLines().size());

        interact(() -> {
            controller.getCodeListView().scrollTo(0);
        });
        WaitForAsyncUtils.waitForFxEvents();

        TextField field = getTextFieldForLine(0);
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
    void getBaseFileNameNullFileReturnsNull(){
        assertNull(controller.getBaseFileName(null));
    }

    @Test
    void getBaseFileNameReturnsNameWithoutExtension(){
        assertEquals("test", controller.getBaseFileName("test.mjj"));
    }

    @Test
    void getBaseFileNameNoExtensionReturnsFullName(){
        assertEquals("test", controller.getBaseFileName("test"));
    }

    @Test
    void getBaseFileNameMultipleExtensionsReturnsBeforeLastDot(){
        assertEquals("test.jjc", controller.getBaseFileName("test.jjc.mjj"));
    }

    @Test
    void testNewFileCreatesEmptyDocument(){
        assertEquals(1, controller.getCodeLines().size());

        clickOn("#btnNew");
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(1, controller.getCodeLines().size());
        assertTrue(controller.getCodeLines().getFirst().getCode().isEmpty());
        assertEquals(0, controller.getCodeListView().getSelectionModel().getSelectedIndex());
        assertNull(controller.getCurrentFile());
        assertEquals("Untitled", controller.getSourceTab().getText());
    }

    @Test
    void testNewFileReplacesExistingLoadedFile() throws Exception {
        File testFile = createTestFile("test.mjj", "main () {", "int x = 10;", "}");
        interact(() -> controller.loadFile(testFile));
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(3, controller.getCodeLines().size());
        assertEquals(testFile, controller.getCurrentFile());
        assertEquals("main () {", controller.getCodeLines().get(0).getCode());
        assertEquals("test.mjj", controller.getSourceTab().getText());

        interact(() -> controller.createNewFile());
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(1, controller.getCodeLines().size());
        assertTrue(controller.getCodeLines().getFirst().getCode().isEmpty());
        assertNull(controller.getCurrentFile());
        assertEquals("Untitled", controller.getSourceTab().getText());
    }

    @Test
    void testArrowUpSelectsPreviousLine() throws Exception {
        File testFile = createTestFile("test.mjj", "line 1", "line 2", "line 3", "line 4");
        interact(() -> controller.loadFile(testFile));
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(4, controller.getCodeLines().size());

        interact(() -> {
            controller.getCodeListView().scrollTo(1);
            controller.getCodeListView().getSelectionModel().select(1);
        });
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(1, controller.getCodeListView().getSelectionModel().getSelectedIndex());

        // Find the TextField of the selected row
        TextField field = getTextFieldForLine(1);
        assertNotNull(field);

        clickOn(field);
        WaitForAsyncUtils.waitForFxEvents();

        type(KeyCode.UP);
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(4, controller.getCodeLines().size());
        assertEquals(0, controller.getCodeListView().getSelectionModel().getSelectedIndex());
    }

    @Test
    void testArrowUpOnFirstLineDoesNothing() throws Exception {
        File testFile = createTestFile("test.mjj", "line 1", "line 2", "line 3");
        interact(() -> controller.loadFile(testFile));
        WaitForAsyncUtils.waitForFxEvents();

        interact(() -> {
            controller.getCodeListView().scrollTo(0);
            controller.getCodeListView().getSelectionModel().select(0);
        });
        WaitForAsyncUtils.waitForFxEvents();

        TextField field = getTextFieldForLine(0);
        assertNotNull(field);

        clickOn(field);
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(0, controller.getCodeListView().getSelectionModel().getSelectedIndex());

        type(KeyCode.UP);
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(0, controller.getCodeListView().getSelectionModel().getSelectedIndex());
    }

    @Test
    void testArrowDownSelectsNextLine() throws Exception {
        File testFile = createTestFile("test.mjj", "line 1", "line 2", "line 3");
        interact(() -> controller.loadFile(testFile));
        WaitForAsyncUtils.waitForFxEvents();

        interact(() -> {
            controller.getCodeListView().scrollTo(0);
        });
        WaitForAsyncUtils.waitForFxEvents();

        TextField field = getTextFieldForLine(0);
        assertNotNull(field);

        clickOn(field);
        WaitForAsyncUtils.waitForFxEvents();

        type(KeyCode.DOWN);
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(1, controller.getCodeListView().getSelectionModel().getSelectedIndex());
    }

    @Test
    void testArrowDownOnLastLineDoesNothing() throws Exception {
        File testFile = createTestFile("test.mjj", "line 1", "line 2", "line 3");
        interact(() -> controller.loadFile(testFile));
        WaitForAsyncUtils.waitForFxEvents();

        interact(() -> {
            controller.getCodeListView().scrollTo(2);
            controller.getCodeListView().getSelectionModel().select(2);
        });
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(2, controller.getCodeListView().getSelectionModel().getSelectedIndex());

        TextField field = getTextFieldForLine(2);
        assertNotNull(field);

        clickOn(field);
        WaitForAsyncUtils.waitForFxEvents();

        type(KeyCode.DOWN);
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(2, controller.getCodeListView().getSelectionModel().getSelectedIndex());
    }

    @Disabled
    @Test
    void testButtonsActivatedAfterClickNew(){
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

    @Disabled
    @Test
    void testButtonsActivatedAfterLoadedFile() throws Exception {
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
    void testIsMinijajaFileWithNullFile(){
        assertNull(controller.getCurrentFile());
        assertFalse(controller.isMinijajaFile());
    }

    @Test
    void testIsMinijajaFileWithMinijajaFile() throws Exception {
        File testFile = createTestFile("test.mjj", "line 1", "line 2");
        interact(() -> controller.loadFile(testFile));
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(testFile, controller.getCurrentFile());
        assertTrue(controller.isMinijajaFile());
    }

    @Test
    void testIsMinijajaFileWithJajacodeFile() throws Exception {
        File testFile = createTestFile("test.jjc", "line 1", "line 2");
        interact(() -> controller.loadFile(testFile));
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(testFile, controller.getCurrentFile());
        assertFalse(controller.isMinijajaFile());
    }

    /*
     */
    @Test
    void confirmationRunEmptyFile() throws Exception {
        File blankFile = createTestFile("blank.mjj", "   ", "");

        interact(() -> {
            controller.loadFile(blankFile);
        });
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(blankFile, controller.getCurrentFile());

        interact(() -> {
            controller.onRunClicked();
        });
        WaitForAsyncUtils.waitForFxEvents();

        // Check that the console prints the right error
        // We want :
        // - "[INFO] No code to compile !" <- for compiler
        // - "[INFO] No code to interpret !" <- for interpreter
        assertTrue(controller.output.getText().contains("[INFO] No code to interpret !"));
    }


    /**
     * Provides test cases for running or compiling files when they are only made of empty chars
     * Confirmation tests -> Before we could run files with blank chars (Spaces, tabs etc.)
     */
    static Stream<Arguments> notVisibleCharsTests() {
        return Stream.of(
                Arguments.of("just_line_return", "\n", (Consumer<MainController>) (MainController::onRunClicked), "[INFO] No code to interpret !"),
                Arguments.of("blank_run", "   \n   ", (Consumer<MainController>) (MainController::onRunClicked), "[INFO] No code to interpret !"),
                Arguments.of("blank_run_oneSpace", " ", (Consumer<MainController>) (MainController::onRunClicked), "[INFO] No code to interpret !"),
                Arguments.of("blank_run_tab", "\t   \t", (Consumer<MainController>) (MainController::onRunClicked), "[INFO] No code to interpret !"),
                Arguments.of("blank_compile", " ", (Consumer<MainController>) (MainController::onCompileClicked), "[INFO] No code to compile !"),
                Arguments.of("blank_compile", "   \n    ", (Consumer<MainController>) (MainController::onCompileClicked), "[INFO] No code to compile !"),
                Arguments.of("blank_compile_tab", "\t   \t", (Consumer<MainController>) (MainController::onCompileClicked), "[INFO] No code to compile !"),
                Arguments.of("valid_run", "class C { main { int x = 1; }}", (Consumer<MainController>) (MainController::onRunClicked), ""),
                Arguments.of("valid_compile", "class C { main { int x = 1; }}", (Consumer<MainController>) (MainController::onCompileClicked), "")
        );
    }

    @ParameterizedTest
    @MethodSource("notVisibleCharsTests")
    void testNotVisibleChars(String fileName, String fileContent, Consumer<MainController> action, String expectedMessage) throws Exception {
        File file = createTestFile(fileName + ".mjj", fileContent, "");

        interact(() -> controller.loadFile(file));
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(file, controller.getCurrentFile());

        interact(() -> action.accept(controller)); // Either run or compile
        WaitForAsyncUtils.waitForFxEvents();

        String output = controller.output.getText();

        if (expectedMessage == null || expectedMessage.isEmpty()) {
            assertFalse(output.contains("[ERROR]"), "Expected no error, but got : " + output);
        } else {
            assertTrue(output.contains(expectedMessage));
        }
    }

    @Test
    void testCompiledTabHiddenByDefault(){
        assertFalse(controller.getEditorTabPane().getTabs().contains(controller.getCompiledTab()));
    }

    @Test
    void testCompileShowsCompiledTab() throws Exception {
        File testFile = createTestFile("test.mjj", "class C {", "main {", "}", "}");
        interact(() -> controller.loadFile(testFile));
        WaitForAsyncUtils.waitForFxEvents();

        assertFalse(controller.getEditorTabPane().getTabs().contains(controller.getCompiledTab()));

        interact(() -> controller.onCompileClicked());
        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(controller.getEditorTabPane().getTabs().contains(controller.getCompiledTab()));
        assertEquals(controller.getCompiledTab(), controller.getEditorTabPane().getSelectionModel().getSelectedItem());
    }

    @Test
    void testCompiledTabHiddenAfterLoadingNewFile() throws Exception {
        File testFile = createTestFile("test.mjj", "class C {", "main {", "}", "}" );
        interact(() -> controller.loadFile(testFile));
        WaitForAsyncUtils.waitForFxEvents();

        interact(() -> controller.onCompileClicked());
        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(controller.getEditorTabPane().getTabs().contains(controller.getCompiledTab()));

        File otherFile = createTestFile("other.mjj", "int y = 1;");
        interact(() -> controller.loadFile(otherFile));
        WaitForAsyncUtils.waitForFxEvents();

        assertFalse(controller.getEditorTabPane().getTabs().contains(controller.getCompiledTab()));
    }

    @Test
    void testCompiledTabHiddenAfterCreatingNewFile() throws Exception {
        File testFile = createTestFile("test.mjj", "class C {", "main {", "}", "}" );
        interact(() -> controller.loadFile(testFile));
        WaitForAsyncUtils.waitForFxEvents();

        interact(() -> controller.onCompileClicked());
        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(controller.getEditorTabPane().getTabs().contains(controller.getCompiledTab()));
        interact(() -> controller.createNewFile());
        WaitForAsyncUtils.waitForFxEvents();

        assertFalse(controller.getEditorTabPane().getTabs().contains(controller.getCompiledTab()));
    }

    @Test
    void testCompiledCodeHasLineNumber() throws Exception {
        File testFile = createTestFile("test.mjj", "class C {", "main {", "}", "}" );
        interact(() -> controller.loadFile(testFile));
        WaitForAsyncUtils.waitForFxEvents();

        interact(() -> controller.onCompileClicked());
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(controller.getCompiledTab(), controller.getEditorTabPane().getSelectionModel().getSelectedItem());

        for(int i = 0; i < controller.getCompiledCodeLines().size(); i++){
            assertEquals(i + 1, controller.getCompiledCodeLines().get(i).getLineNumber());
        }
    }

    @Test
    void testCompiledCodeIsReadOnly() throws Exception {
        File testFile = createTestFile("test.mjj", "class C {", "main {", "}", "}" );
        interact(() -> controller.loadFile(testFile));
        WaitForAsyncUtils.waitForFxEvents();

        interact(() -> controller.onCompileClicked());
        WaitForAsyncUtils.waitForFxEvents();

        interact(() -> {
            controller.getCompiledCodeListView().scrollTo(0);
        });
        WaitForAsyncUtils.waitForFxEvents();

        TextField field = (TextField) controller.getCompiledCodeListView().lookup(".code-field");
        assertNotNull(field);

        assertFalse(field.isEditable());
    }

    @Test
    void testCompiledJajacodeFile() throws Exception {
        File testFile = createTestFile("test.jcc", "init", "push(0)", "pop", "jcstop");
        interact(() -> controller.loadFile(testFile));
        WaitForAsyncUtils.waitForFxEvents();

        interact(() -> controller.onCompileClicked());
        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(controller.output.getText().contains("[ERROR] Compilation is only available for MiniJaja files (.mjj)"));
    }

    @Test
    void testCompileErrorDoesNotShowCompiledTab() throws Exception {
        File testFile = createTestFile("test.mjj", "class C {", "main {", "}");
        interact(() -> controller.loadFile(testFile));
        WaitForAsyncUtils.waitForFxEvents();

        assertFalse(controller.getEditorTabPane().getTabs().contains(controller.getCompiledTab()));

        interact(() -> controller.onCompileClicked());
        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(controller.output.getText().contains("[ERROR] "));
        assertFalse(controller.getEditorTabPane().getTabs().contains(controller.getCompiledTab()));
    }

    @Test
    void testCompileButtonDisabledOnCompiledTab() throws Exception {
        File testFile = createTestFile("test.mjj", "class C {", "main {", "}", "}" );
        interact(() -> controller.loadFile(testFile));
        WaitForAsyncUtils.waitForFxEvents();

        verifyThat("#btnCompile", isEnabled());
        verifyThat("#btnRunCompile", isEnabled());

        interact(() -> controller.onCompileClicked());
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(controller.getCompiledTab(), controller.getEditorTabPane().getSelectionModel().getSelectedItem());
        verifyThat("#btnCompile", isDisabled());
        verifyThat("#btnRunCompile", isDisabled());
    }

    @Test
    void testCompiledTabIsClosable() throws Exception {
        File testFile = createTestFile("test.mjj", "class C {", "main {", "}", "}" );
        interact(() -> controller.loadFile(testFile));
        WaitForAsyncUtils.waitForFxEvents();

        interact(() -> controller.onCompileClicked());
        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(controller.getEditorTabPane().getTabs().contains(controller.getCompiledTab()));
        assertTrue(controller.getCompiledTab().isClosable());

        interact(() -> controller.getEditorTabPane().getTabs().remove(controller.getCompiledTab()));
        WaitForAsyncUtils.waitForFxEvents();

        assertFalse(controller.getEditorTabPane().getTabs().contains(controller.getCompiledTab()));
    }

    @Test
    void testIsCompiledTabReturnsFalseWhenOnSourceTab() throws Exception {
        File testFile = createTestFile("test.mjj", "class C {", "main {", "}", "}" );
        interact(() -> controller.loadFile(testFile));
        WaitForAsyncUtils.waitForFxEvents();

        assertFalse(controller.isCompiledTab());
    }

    @Test
    void testIsCompiledTabReturnsTrueWhenOnCompiledTab() throws Exception {
        File testFile = createTestFile("test.mjj", "class C {", "main {", "}", "}" );
        interact(() -> controller.loadFile(testFile));
        WaitForAsyncUtils.waitForFxEvents();

        interact(() -> controller.onCompileClicked());
        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(controller.isCompiledTab());
    }

    @Test
    void testLoadCompiledCodeToListViewSplitsLineCorrectly() throws Exception {
        String compiledCode = "init\npush(0)\npop\njcstop";
        interact(() -> controller.loadCompiledCodeToListView(compiledCode));
        WaitForAsyncUtils.waitForFxEvents();

        ObservableList<CodeLine> compiledLines = controller.getCompiledCodeLines();
        assertEquals(4, compiledLines.size());
        assertEquals(1, compiledLines.get(0).getLineNumber());
        assertEquals("init", compiledLines.get(0).getCode());
        assertEquals(2, compiledLines.get(1).getLineNumber());
        assertEquals("push(0)", compiledLines.get(1).getCode());
        assertEquals(3, compiledLines.get(2).getLineNumber());
        assertEquals("pop", compiledLines.get(2).getCode());
        assertEquals(4, compiledLines.get(3).getLineNumber());
        assertEquals("jcstop", compiledLines.get(3).getCode());
    }

    @Test
    void testHideCompiledTabWhenAlreadyHidden() throws Exception {
        assertFalse(controller.getEditorTabPane().getTabs().contains(controller.getCompiledTab()));

        interact(() -> controller.hideCompileTab());
        WaitForAsyncUtils.waitForFxEvents();
        assertFalse(controller.getEditorTabPane().getTabs().contains(controller.getCompiledTab()));
    }

    @Test
    void testShowCompiledTabWhenAlreadyShown() throws Exception {
        File testFile = createTestFile("test.mjj", "class C {", "main {", "}", "}" );
        interact(() -> controller.loadFile(testFile));
        WaitForAsyncUtils.waitForFxEvents();

        interact(() -> controller.onCompileClicked());
        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(controller.getEditorTabPane().getTabs().contains(controller.getCompiledTab()));

        interact(() -> controller.showCompiledTab());
        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(controller.getEditorTabPane().getTabs().contains(controller.getCompiledTab()));

        long count = controller.getEditorTabPane().getTabs().stream().filter(tab -> tab == controller.getCompiledTab()).count();
        assertEquals(1, count);
    }

    @Test
    void testShowInternalErrorWhenBothMiniJajaAndJajaCode() throws Exception {
        MainController realController = this.controller;
        class FakeController extends MainController {
            @Override
            public boolean isMinijajaFile() { return true; }
            @Override
            public boolean isJajaCode() { return true; }
        }

        FakeController fake = new FakeController();

        // Copy FXML / initialized fields from the real controller to the fake one
        // So the fake one has an output/console etc.
        java.lang.reflect.Field[] fields = MainController.class.getDeclaredFields();
        for (java.lang.reflect.Field f : fields) {
            f.setAccessible(true);
            Object val = f.get(realController);
            f.set(fake, val);
        }

        interact(() -> fake.onRunClicked());

        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(fake.output.getText().contains("[INTERNAL ERROR] current file is marked as jjc and mjj"));
    }

    @Test
    public void testSaveShortcutCtrlS() throws Exception {
        File testFile = createTestFile("shortcut_save.mjj", "int x = 10;", "x++");

        interact(() -> {
            controller.loadFile(testFile);
        });
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(testFile, controller.getCurrentFile());

        interact(() -> controller.getCodeListView().scrollTo(0));
        WaitForAsyncUtils.waitForFxEvents();

        TextField firstField = getTextFieldForLine(0);
        assertNotNull(firstField);

        clickOn(firstField).eraseText(controller.getCodeLines().get(0).getCode().length()).write("boolean x = true;");
        WaitForAsyncUtils.waitForFxEvents();

        // Press Ctrl+S
        press(KeyCode.CONTROL);
        type(KeyCode.S);
        release(KeyCode.CONTROL);

        WaitForAsyncUtils.waitForFxEvents();

        List<String> savedLines = Files.readAllLines(testFile.toPath(), StandardCharsets.UTF_8);
        assertEquals("boolean x = true;", savedLines.get(0));
        assertEquals("x++", savedLines.get(1));
    }

    @Test
    void testRunShortcutCtrlR() throws Exception {
        File testFile = createTestFile("shortcut_run.mjj", "class C { main { int x = 1; }}");

        interact(() -> controller.loadFile(testFile));
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(testFile, controller.getCurrentFile());

        // Press Ctrl+R
        press(KeyCode.CONTROL);
        type(KeyCode.R);
        release(KeyCode.CONTROL);

        WaitForAsyncUtils.waitForFxEvents();

        String out = controller.output.getText();
        assertTrue(out.contains("[INFO] MiniJaja interpretation successfully completed"));
    }

    @Test
    void testCompileShortcutCtrlKShowsCompiledTab() throws Exception {
        File testFile = createTestFile("shortcut_compile.mjj", "class C {", "main {", "}", "}");

        interact(() -> controller.loadFile(testFile));
        WaitForAsyncUtils.waitForFxEvents();

        assertFalse(controller.getEditorTabPane().getTabs().contains(controller.getCompiledTab()));

        // Press Ctrl+K
        press(KeyCode.CONTROL);
        type(KeyCode.K);
        release(KeyCode.CONTROL);

        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(controller.getEditorTabPane().getTabs().contains(controller.getCompiledTab()));
        assertEquals(controller.getCompiledTab(), controller.getEditorTabPane().getSelectionModel().getSelectedItem());
    }

    @Test
    void getCompiledCode() throws Exception {
        File testFile = createTestFile("test.mjj", "class C {", "main {", "}", "}" );
        interact(() -> controller.loadFile(testFile));
        WaitForAsyncUtils.waitForFxEvents();

        interact(() -> controller.onCompileClicked());
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals("init\npush(0)\npop\njcstop", controller.getCompiledCode());
    }

    @Test
    void getCompiledCodeEmpty() throws Exception {
        File testFile = createTestFile("empty.mjj", "");
        interact(() -> controller.loadFile(testFile));
        WaitForAsyncUtils.waitForFxEvents();

        interact(() -> controller.onCompileClicked());
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals("", controller.getCompiledCode());
    }

    @Test
    void testFileNotMarkedAsModifiedInitially() {
        assertEquals("Untitled", controller.getSourceTab().getText());
        assertFalse(controller.getSourceTab().getText().contains("•"));
    }

    @Test
    void testFileNotMarkedAsModifiedAfterLoadingFile() throws Exception{
        File testFile = createTestFile("test.mjj", "int x = 10;", "x++;");
        interact(() -> controller.loadFile(testFile));
        WaitForAsyncUtils.waitForFxEvents();

        assertFalse(controller.getSourceTab().getText().contains("•"));
    }

    @Test
    void testNewFileNotMarkedAsModified(){
        interact(() -> controller.createNewFile());
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals("Untitled", controller.getSourceTab().getText());
        assertFalse(controller.getSourceTab().getText().contains("•"));
    }

    @Test
    void testFileMarkedAsModifiedWhenTyping() throws Exception {
        File testFile = createTestFile("test.mjj", "int x = 10;", "x++;");
        interact(() -> controller.loadFile(testFile));
        WaitForAsyncUtils.waitForFxEvents();

        assertFalse(controller.getSourceTab().getText().contains("•"));

        TextField firstField = getTextFieldForLine(0);
        assertNotNull(firstField);

        clickOn(firstField);
        WaitForAsyncUtils.waitForFxEvents();

        type(KeyCode.BACK_SPACE);
        type(KeyCode.K);
        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(controller.getSourceTab().getText().contains("•"));
    }

    @Test
    void testFileMarkedAsModifiedWhenAddingLine() throws Exception {
        File testFile = createTestFile("test.mjj", "int x = 10;");
        interact(() -> controller.loadFile(testFile));
        WaitForAsyncUtils.waitForFxEvents();

        assertFalse(controller.getSourceTab().getText().contains("•"));
        assertEquals(1, controller.getCodeLines().size());

        TextField firstField = getTextFieldForLine(0);
        assertNotNull(firstField);

        clickOn(firstField);
        WaitForAsyncUtils.waitForFxEvents();

        type(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(2, controller.getCodeLines().size());
        assertTrue(controller.getSourceTab().getText().contains("•"));
    }

    @Test
    void testFileMarkedAsModifiedWhenDeletingLine() throws Exception {
        File testFile = createTestFile("test.mjj", "", "int x = 10;");
        interact(() -> controller.loadFile(testFile));
        WaitForAsyncUtils.waitForFxEvents();

        assertFalse(controller.getSourceTab().getText().contains("•"));
        assertEquals(2, controller.getCodeLines().size());

        TextField firstField = getTextFieldForLine(0);
        assertNotNull(firstField);

        clickOn(firstField);
        WaitForAsyncUtils.waitForFxEvents();

        type(KeyCode.BACK_SPACE);
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(1, controller.getCodeLines().size());
        assertTrue(controller.getSourceTab().getText().contains("•"));
    }

    @Test
    void testFileNotMarkedAsModifiedAfterSave() throws Exception {
        File testFile = createTestFile("test.mjj", "int x = 10;", "x++;");
        interact(() -> controller.loadFile(testFile));
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(2, controller.getCodeLines().size());
        assertFalse(controller.getSourceTab().getText().contains("•"));

        TextField firstField = getTextFieldForLine(0);
        assertNotNull(firstField);

        clickOn(firstField);
        WaitForAsyncUtils.waitForFxEvents();

        type(KeyCode.BACK_SPACE);
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(2, controller.getCodeLines().size());
        assertTrue(controller.getSourceTab().getText().contains("•"));

        clickOn("#btnSave");
        WaitForAsyncUtils.waitForFxEvents();

        assertFalse(controller.getSourceTab().getText().contains("•"));
    }

    @Test
    void testFileNotMarkedAsModifiedAfterResizing() throws Exception {
        File testFile = createTestFile("test.mjj", "int x = 10;", "x++;");
        interact(() -> controller.loadFile(testFile));
        WaitForAsyncUtils.waitForFxEvents();

        assertFalse(controller.getSourceTab().getText().contains("•"));

        // Force refresh to recreate the list view cells
        interact(() -> controller.getCodeListView().refresh());
        WaitForAsyncUtils.waitForFxEvents();

        assertFalse(controller.getSourceTab().getText().contains("•"));
    }

    @Test
    void testSaveAsRemovesModificationMark() {
        assertEquals(1, controller.getCodeLines().size());

        TextField firstField = getTextFieldForLine(0);
        assertNotNull(firstField);

        clickOn(firstField);
        WaitForAsyncUtils.waitForFxEvents();

        type(KeyCode.A);
        type(KeyCode.B);
        type(KeyCode.C);
        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(controller.getSourceTab().getText().contains("•"));

        File newFile = tempDir.resolve("test.mjj").toFile();

        interact(() -> controller.saveAs(newFile));
        WaitForAsyncUtils.waitForFxEvents();

        assertFalse(controller.getSourceTab().getText().contains("•"));
        assertEquals("test.mjj", controller.getSourceTab().getText());
        assertEquals(1, controller.getCodeLines().size());
        assertEquals("abc", controller.getCodeLines().getFirst().getCode());
    }




}
