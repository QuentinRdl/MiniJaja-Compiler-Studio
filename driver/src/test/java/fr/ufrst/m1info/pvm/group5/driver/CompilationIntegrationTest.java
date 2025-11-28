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
class CompilationIntegrationTest extends ApplicationTest {

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
        WaitForAsyncUtils.waitForFxEvents();

        return controller.output.getText();
    }

    /**
     * This method creates a file, loads it into the GUI,
     * compiles and interprets it, and retrieves the console from the GUI.
     *
     * @param filename name of the file to create in the test temp directory
     * @param content full file content
     * @return the text currently present in the controller's output TextArea
     * @throws Exception if fails
     */
    private String createFileLoadCompileAndRunAndGetConsole(String filename, String content) throws Exception {
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
            controller.onCompileAndRunClicked();
        });
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

        WaitForAsyncUtils.waitForFxEvents();

        return controller.output.getText();
    }

    /**
     * Just like createFileLoadCompileAndRunAndGetConsole but triggers the actual Compile button (#btnRunCompile)
     *
     * @param filename name of the file to create in the test temp directory
     * @param content full file content
     * @return the text currently present in the controller's output TextArea (console)
     * @throws Exception Exception
     */
    private String createFileLoadCompileAndRunAndGetConsoleByButton(String filename, String content) throws Exception {
        String[] lines;
        if (content == null || content.isEmpty()){
            lines = new String[0];
        } else {
            lines = content.split("\\R", -1);
        }

        File testFile = createTestFile(filename, lines);

        interact(() -> controller.loadFile(testFile));
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#btnRunCompile");

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

    @Test
    void compileAndRunWorks() throws Exception {
        String content = String.join("\n",
                "class C {",
                "   int x;",
                "   main {",
                "       x = 3 + 4;",
                "       x++;",
                "       writeln(x);",
                "   }",
                "}");
        String consoleText = createFileLoadCompileAndRunAndGetConsole("test.mjj", content);
        assertTrue(consoleText.contains("8"));
        assertTrue(consoleText.contains("[INFO] Compilation and interpretation successfully completed"));
    }

    @Test
    void compileAndRunWorksByButton() throws Exception {
        String content = String.join("\n",
                "class C {",
                "   int x;",
                "   main {",
                "       x = 3 + 4;",
                "       x++;",
                "       writeln(x);",
                "   }",
                "}");
        String consoleText = createFileLoadCompileAndRunAndGetConsoleByButton("test.mjj", content);
        assertTrue(consoleText.contains("8"));
        assertTrue(consoleText.contains("[INFO] Compilation and interpretation successfully completed"));
    }

    @Test
    void compileAndRunDoesNotWork() throws Exception {
        String content = String.join("\n",
                "class C {",
                "   main {",
                "       x = 3 + 4;",
                "       x++;",
                "   }",
                "}");
        String consoleText = createFileLoadCompileAndRunAndGetConsole("test.mjj", content);
        assertTrue(consoleText.contains("[ERROR]"));
        assertTrue(consoleText.contains("Symbol not found: x"));
    }

    @Test
    void compileAndRunDoesNotWorkByButton() throws Exception {
        String content = String.join("\n",
                "class C {",
                "   main {",
                "       x = 3 + 4;",
                "       x++;",
                "   }",
                "}");
        String consoleText = createFileLoadCompileAndRunAndGetConsoleByButton("test.mjj", content);
        assertTrue(consoleText.contains("[ERROR]"));
        assertTrue(consoleText.contains("Symbol not found: x"));
    }

    @Test
    void compileAndRunEmptyFile() throws Exception {
        String consoleText = createFileLoadCompileAndRunAndGetConsole("empty.mjj", "");
        assertTrue(consoleText.contains("[ERROR] No code to compile and run !"));
    }

    @Test
    void compileAndRunEmptyFileByButton() throws Exception {
        String consoleText = createFileLoadCompileAndRunAndGetConsoleByButton("empty.mjj", " \n\t ");
        assertTrue(consoleText.contains("[ERROR] No code to compile and run !"));
    }

    @Test
    void compileAndRunJajaCodeFile() throws Exception {
        String content = String.join("\n",
                "init",
                "push(0)",
                "pop",
                "jcstop");
        String consoleText = createFileLoadCompileAndRunAndGetConsole("test.jcc", content);
        assertTrue(consoleText.contains("[ERROR] Compilation and interpretation is only available for MiniJaja (.mjj) files"));
    }
}
