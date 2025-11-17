package fr.ufrst.m1info.pvm.group5.driver;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import java.io.File;
import java.nio.file.Path;

import static fr.ufrst.m1info.pvm.group5.driver.MainControllerTest.createTestFile;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
public class CompilationIntegrationTest extends ApplicationTest {

    private MainController controller;


    private final String contentValid = String.join("\n",
            "class C {",
            "    int x;",
            "    main {",
            "        x = 3 + 4;",
            "        x++;",
            "    }",
            "}"
    );

    // Expected compiled JajaCode lines
    private final String[] expectedCompilOutput = new String[]{
            "init",
            "new(x,INT,var,0)",
            "push(3)",
            "push(4)",
            "add",
            "store(x)",
            "push(1)",
            "inc(x)",
            "push(0)",
            "swap",
            "pop",
            "pop",
            "jcstop"
    };

    private final String contentIncorrect = String.join("\n",
            "class C {",
            "    int x;",
            "    main {",
            "        x = 3 + 4;",
            "        x++;",
            "    }"
    );

    @BeforeAll
    public static void initTempDir(@TempDir Path td) {
        MainControllerTest.tempDir = td;
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/ufrst/m1info/pvm/group5/driver/main-view.fxml"));
        Scene scene = new Scene(loader.load(), 800, 600);
        controller = loader.getController();

        stage.setScene(scene);
        stage.show();
    }


    /**
     * This automates a good part of the Compiler -> IDE tests
     *
     * @param filename name of the file to create in the test temp directory
     * @param content full file content
     * @return the text currently present in the controller's output TextArea
     * @throws Exception if fails
     */
    private String createFileLoadCompileAndGetConsole(String filename, String content) throws Exception {
        String[] lines;
        if (content == null || content.isEmpty()){
            lines = new String[0];
        } else {
            lines = content.split("\\R", -1);
        }

        File testFile = createTestFile(filename, lines);

        interact(() -> {
            controller.loadFile(testFile);
        });
        WaitForAsyncUtils.waitForFxEvents();

        interact(() -> {
            controller.onCompileClicked();
        });
        Thread.sleep(50);
        WaitForAsyncUtils.waitForFxEvents();

        return controller.output.getText();
    }


    /**
     * Just like createFileLoadCompileAndGetConsole but triggers the actual Compile button (#btnCompile)
     *
     * @param filename name of the file to create in the test temp directory
     * @param content full file content
     * @return the text currently present in the controller's output TextArea (console)
     * @throws Exception Exception
     */
    private String createFileLoadCompileAndGetConsoleByButton(String filename, String content) throws Exception {
        String[] lines;
        if (content == null || content.isEmpty()){
            lines = new String[0];
        } else {
            lines = content.split("\\R", -1);
        }

        File testFile = createTestFile(filename, lines);

        interact(() -> controller.loadFile(testFile));
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#btnCompile");

        Thread.sleep(50);
        WaitForAsyncUtils.waitForFxEvents();

        return controller.output.getText();
    }


    @Test
    void compilatorWorks() throws Exception {
        String consoleText = createFileLoadCompileAndGetConsole("test.mjj", contentValid);
        assertTrue(consoleText.contains("[INFO] Compilation successful!"));
    }


    @Test
    void compilatorButtonWorks() throws Exception {
        String consoleText = createFileLoadCompileAndGetConsoleByButton("test.mjj", contentValid);
        assertTrue(consoleText.contains("[INFO] Compilation successful!"));
    }


    @Test
    void compilerDoesNotWork() throws Exception {
        String consoleText = createFileLoadCompileAndGetConsole("test.mjj", contentIncorrect);
        assertTrue(consoleText.contains("[ERROR]"));
        assertTrue(consoleText.contains("line 6:5 missing '}' at '<EOF>'"));
    }


    @Test
    void compilerDoesNotWorkActualBtn() throws Exception {
        String consoleText = createFileLoadCompileAndGetConsoleByButton("test.mjj", contentIncorrect);
        assertTrue(consoleText.contains("[ERROR]"));
        assertTrue(consoleText.contains("line 6:5 missing '}' at '<EOF>'"));
    }


    @Test
    void compilatorOutputsCorrect() throws Exception {

        String consoleText = createFileLoadCompileAndGetConsole("test.mjj", contentValid);
        assertTrue(consoleText.contains("[INFO] Compilation successful!"));

        // Check number of lines
        var compiledLines = controller.getCompiledCodeLines();
        org.junit.jupiter.api.Assertions.assertEquals(expectedCompilOutput.length, compiledLines.size());

        // Check content of lines
        for (int i = 0; i < expectedCompilOutput.length; i++) {
            org.junit.jupiter.api.Assertions.assertEquals(expectedCompilOutput[i], compiledLines.get(i).getCode());
        }
    }


    @Test
    void compilatorButtonOutputsCorrect() throws Exception {
        String consoleText = createFileLoadCompileAndGetConsoleByButton("test.mjj", contentValid);
        assertTrue(consoleText.contains("[INFO] Compilation successful!"));

        // Check number of lines
        var compiledLines = controller.getCompiledCodeLines();
        org.junit.jupiter.api.Assertions.assertEquals(expectedCompilOutput.length, compiledLines.size());

        // Check content of lines
        for (int i = 0; i < expectedCompilOutput.length; i++) {
            org.junit.jupiter.api.Assertions.assertEquals(expectedCompilOutput[i], compiledLines.get(i).getCode());
        }
    }
}
