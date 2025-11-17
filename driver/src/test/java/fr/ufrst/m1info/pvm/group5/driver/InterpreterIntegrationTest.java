package fr.ufrst.m1info.pvm.group5.driver;

import org.junit.jupiter.api.Test;
import org.testfx.util.WaitForAsyncUtils;
import org.junit.jupiter.api.BeforeAll;

import java.io.File;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;

import static fr.ufrst.m1info.pvm.group5.driver.MainControllerTest.createTestFile;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.nio.file.Path;

import org.testfx.framework.junit5.ApplicationExtension;

import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
public class InterpreterIntegrationTest extends ApplicationTest {

    private MainController controller;

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


    @Test
    void interpreterWorks() throws Exception {
        String content = String.join("\n",
                "class C {",
                "    int x;",
                "    main {",
                "        x = 3 + 4;",
                "        x++;",
                "    }",
                "}"
        );

        String consoleText = createFileLoadRunAndGetConsole("test.mjj", content);
        assertTrue(consoleText.contains("[INFO] Interpretation successfully completed"));
    }


    @Test
    void interpreterWorksActualBtn() throws Exception {
        String content = String.join("\n",
                "class C {",
                "    int x;",
                "    main {",
                "        x = 3 + 4;",
                "        x++;",
                "    }",
                "}"
        );

        String consoleText = createFileLoadRunAndGetConsoleByButton("test.mjj", content);
        assertTrue(consoleText.contains("[INFO] Interpretation successfully completed"));
    }


    @Test
    void interpreterDoesNotWork() throws Exception {
        String content = String.join("\n",
                "class C {",
                "    int x;",
                "    main {",
                "        x = 3 + 4;",
                "        x++;",
                "    }"
        );

        String consoleText = createFileLoadRunAndGetConsole("test.mjj", content);
        assertTrue(consoleText.contains("[ERROR]"));
        assertTrue(consoleText.contains("line 6:5 missing '}' at '<EOF>'"));
    }


    @Test
    void interpreterDoesNotWorkActualBtn() throws Exception {
        String content = String.join("\n",
                "class C {",
                "    int x;",
                "    main {",
                "        x = 3 + 4;",
                "        x++;",
                "    }"
        );

        String consoleText = createFileLoadRunAndGetConsoleByButton("test.mjj", content);
        assertTrue(consoleText.contains("[ERROR]"));
        assertTrue(consoleText.contains("line 6:5 missing '}' at '<EOF>'"));
    }


    @Test
    void interpreterEmptyFileDirectCall() throws Exception {
        String content = "";

        String consoleText = createFileLoadRunAndGetConsole("empty.mjj", content);
        assertTrue(consoleText.contains("Interpret") || consoleText.contains("[INFO]"));
    }


    @Test
    void interpreterEmptyFileByButton() throws Exception {
        String content = "";

        String consoleText = createFileLoadRunAndGetConsoleByButton("empty_btn.mjj", content);
        assertTrue(consoleText.contains("Interpret") || consoleText.contains("[INFO]"));
    }


    @Test
    void interpreterWithNoExtension() throws Exception {
        String content = String.join("\n",
                "class C {",
                "    int x;",
                "    main {",
                "        x = 3 + 4;",
                "        x++;",
                "    }"
        );

        String consoleText = createFileLoadRunAndGetConsoleByButton("test", content);
        assertTrue(consoleText.contains("[ERROR] Interpretation is only available for MiniJaja files and JajaCode files (.mjj & .jjc)"));
    }


    @Test
    void interpreterWithIncorrectExtension() throws Exception {
        String content = String.join("\n",
                "class C {",
                "    int x;",
                "    main {",
                "        x = 3 + 4;",
                "        x++;",
                "    }"
        );

        String consoleText = createFileLoadRunAndGetConsoleByButton("test.java", content);
        assertTrue(consoleText.contains("[ERROR] Interpretation is only available for MiniJaja files and JajaCode files (.mjj & .jjc)"));
    }


    @Test
    void interpreterWithMjjExtension() throws Exception {
        String content = String.join("\n",
                "class C {",
                "    int x;",
                "    main {",
                "        x = 3 + 4;",
                "        x++;",
                "    }"
        );

        String consoleText = createFileLoadRunAndGetConsoleByButton("test.mjj", content);
        assertFalse(consoleText.contains("[ERROR] Interpretation is only available for MiniJaja files and JajaCode files (.mjj & .jjc)"));
    }


    @Test
    void interpreterWithJccExtension() throws Exception {
        String content = String.join("\n",
                "class C {",
                "    int x;",
                "    main {",
                "        x = 3 + 4;",
                "        x++;",
                "    }"
        );

        String consoleText = createFileLoadRunAndGetConsoleByButton("test.jjc", content);
        assertFalse(consoleText.contains("[ERROR] Interpretation is only available for MiniJaja files and JajaCode files (.mjj & .jjc)"));
    }



    /**
     * This automates a good part of the Interpreter -> IDE tests
     *
     * @param filename name of the file to create in the test temp directory
     * @param content full file content
     * @return the text currently present in the controller's output TextArea
     * @throws Exception if fails
     */
    private String createFileLoadRunAndGetConsole(String filename, String content) throws Exception {
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
            controller.onRunClicked();
        });
        Thread.sleep(50);
        WaitForAsyncUtils.waitForFxEvents();

        return controller.output.getText();
    }


    /**
     * Just like createFileLoadRunAndGetConsole but triggers the actual Run button (#btnRun)
     *
     * @param filename name of the file to create in the test temp directory
     * @param content full file content
     * @return the text currently present in the controller's output TextArea (console)
     * @throws Exception Exception
     */
    private String createFileLoadRunAndGetConsoleByButton(String filename, String content) throws Exception {
        String[] lines;
        if (content == null || content.isEmpty()){
            lines = new String[0];
        } else {
            lines = content.split("\\R", -1);
        }

        File testFile = createTestFile(filename, lines);

        interact(() -> controller.loadFile(testFile));
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#btnRun");

        Thread.sleep(50);
        WaitForAsyncUtils.waitForFxEvents();

        return controller.output.getText();
    }
}
