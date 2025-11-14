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
    public void compilatorWorks() throws Exception {
        String content = String.join("\n",
                "class C {",
                "    int x;",
                "    main {",
                "        x = 3 + 4;",
                "        x++;",
                "    }",
                "}"
        );

        String consoleText = createFileLoadCompileAndGetConsole("test.mjj", content);
        assertTrue(consoleText.contains("[INFO] Compilation successful!"));
    }


    @Test
    public void compilatorButtonWorks() throws Exception {
        String content = String.join("\n",
                "class C {",
                "    int x;",
                "    main {",
                "        x = 3 + 4;",
                "        x++;",
                "    }",
                "}"
        );

        String consoleText = createFileLoadCompileAndGetConsoleByButton("test.mjj", content);
        assertTrue(consoleText.contains("[INFO] Compilation successful!"));
    }


    @Test
    public void compilerDoesNotWork() throws Exception {
        String content = String.join("\n",
                "class C {",
                "    int x;",
                "    main {",
                "        x = 3 + 4;",
                "        x++;",
                "    }"
        );

        String consoleText = createFileLoadCompileAndGetConsole("test.mjj", content);
        assertTrue(consoleText.contains("[ERROR]"));
        assertTrue(consoleText.contains("line 6:5 missing '}' at '<EOF>'"));
    }


    @Test
    public void compilerDoesNotWorkActualBtn() throws Exception {
        String content = String.join("\n",
                "class C {",
                "    int x;",
                "    main {",
                "        x = 3 + 4;",
                "        x++;",
                "    }"
        );

        String consoleText = createFileLoadCompileAndGetConsoleByButton("test.mjj", content);
        assertTrue(consoleText.contains("[ERROR]"));
        assertTrue(consoleText.contains("line 6:5 missing '}' at '<EOF>'"));
    }
}
