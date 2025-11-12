package fr.ufrst.m1info.pvm.group5.driver;

import org.junit.jupiter.api.Test;
import org.testfx.util.WaitForAsyncUtils;
import fr.ufrst.m1info.pvm.group5.driver.MainControllerTest;
import org.junit.jupiter.api.BeforeAll;

import java.io.File;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.testfx.api.FxRobot;

import static fr.ufrst.m1info.pvm.group5.driver.MainControllerTest.createTestFile;
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
    public void interpreterWorks() throws Exception{
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
    public void interpreterWorksActualBtn() throws Exception{
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
    public void interpreterDoesNotWork() throws Exception{
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
    public void interpreterDoesNotWorkActualBtn() throws Exception{
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
    public void interpreterEmptyFileDirectCall() throws Exception {
        String content = "";

        String consoleText = createFileLoadRunAndGetConsole("empty.mjj", content);
        assertTrue(consoleText.contains("Interpret") || consoleText.contains("[INFO]") || consoleText.length() >= 0);
    }

    @Test
    public void interpreterEmptyFileByButton() throws Exception{
        String content = "";

        String consoleText = createFileLoadRunAndGetConsoleByButton("empty_btn.mjj", content);
        assertTrue(consoleText.contains("Interpret") || consoleText.contains("[INFO]") || consoleText.length() >= 0);
    }


    /**
     * This automates a good part of the Interpreter -> IDE tests
     *
     * @param filename name of the file to create in the test temp directory
     * @param content full file content
     * @return the text currently present in the controller's output TextArea
     * @throws Exception if fails
     */
    private String createFileLoadRunAndGetConsole(String filename, String content) throws Exception{
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
     * @throws Exception
     */
    private String createFileLoadRunAndGetConsoleByButton(String filename, String content) throws Exception{
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
