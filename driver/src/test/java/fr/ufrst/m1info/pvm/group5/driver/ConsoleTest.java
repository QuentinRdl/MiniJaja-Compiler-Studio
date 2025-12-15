package fr.ufrst.m1info.pvm.group5.driver;

import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Console class
 */
@ExtendWith(ApplicationExtension.class)
class ConsoleTest extends ApplicationTest {

    @Test
    void testDefaultConstructor() {
        interact(() -> {
            Console console = new Console();
            assertNotNull(console.textArea);
            assertFalse(console.textArea.isEditable());
            assertNotNull(console.getWriter());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testConstructorWithTextArea() {
        interact(() -> {
            TextArea textArea = new TextArea();
            Console console = new Console(textArea);

            assertNotNull(console.textArea);
            assertEquals(textArea, console.textArea);
            assertFalse(console.textArea.isEditable());
            assertNotNull(console.getWriter());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testConstructorWithTextFlow() {
        interact(() -> {
            TextFlow textFlow = new TextFlow();
            Console console = new Console(textFlow);

            assertNotNull(console.getWriter());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testGetWriter() {
        interact(() -> {
            Console console = new Console();
            assertNotNull(console.getWriter());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testWriteWithTextArea() {
        interact(() -> {
            Console console = new Console();
            console.write("Hello");
            assertEquals("Hello", console.textArea.getText());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testWriteLineWithTextArea() {
        interact(() -> {
            Console console = new Console();
            console.writeLine("Hello");
            assertEquals("Hello\n", console.textArea.getText());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testMultipleWritesWithTextArea() {
        interact(() -> {
            Console console = new Console();
            console.write("Hello");
            console.write(" ");
            console.write("World");
            assertEquals("Hello World", console.textArea.getText());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testMultipleWriteLinesWithTextArea() {
        interact(() -> {
            Console console = new Console();
            console.writeLine("Line 1");
            console.writeLine("Line 2");
            console.writeLine("Line 3");
            assertEquals("Line 1\nLine 2\nLine 3\n", console.textArea.getText());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testClearWithTextArea() {
        interact(() -> {
            Console console = new Console();
            console.write("Hello World");
            assertEquals("Hello World", console.textArea.getText());

            console.clear();
            assertEquals("", console.textArea.getText());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testGetTextWithTextArea() {
        interact(() -> {
            Console console = new Console();
            console.write("Test content");
            assertEquals("Test content", console.getText());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testWriterIntegrationWithTextArea() throws InterruptedException {
        Console console = new Console();

        interact(() -> {
            console.getWriter().write("From writer");
        });

        Thread.sleep(100);
        WaitForAsyncUtils.waitForFxEvents();

        interact(() -> {
            assertEquals("From writer", console.textArea.getText());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testWriteWithTextFlow() {
        interact(() -> {
            ColoredTextFlow textFlow = new ColoredTextFlow();
            Console console = new Console(textFlow);

            console.write("Hello");

            assertEquals("Hello", textFlow.getText());
            assertEquals(1, textFlow.getChildren().size());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testWriteLineWithTextFlow() {
        interact(() -> {
            ColoredTextFlow textFlow = new ColoredTextFlow();
            Console console = new Console(textFlow);

            console.writeLine("Hello");

            assertEquals("Hello\n", textFlow.getText());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testMultipleWritesWithTextFlow() {
        interact(() -> {
            ColoredTextFlow textFlow = new ColoredTextFlow();
            Console console = new Console(textFlow);

            console.write("Hello");
            console.write(" ");
            console.write("World");

            assertEquals("Hello World", textFlow.getText());
            assertEquals(3, textFlow.getChildren().size());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testClearWithTextFlow() {
        interact(() -> {
            ColoredTextFlow textFlow = new ColoredTextFlow();
            Console console = new Console(textFlow);

            console.write("Hello World");
            assertEquals("Hello World", textFlow.getText());

            console.clear();
            assertEquals("", textFlow.getText());
            assertEquals(0, textFlow.getChildren().size());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testGetTextWithTextFlow() {
        interact(() -> {
            ColoredTextFlow textFlow = new ColoredTextFlow();
            Console console = new Console(textFlow);

            console.write("Test content");
            assertEquals("Test content", console.getText());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testErrorColoringWithTextFlow() {
        interact(() -> {
            ColoredTextFlow textFlow = new ColoredTextFlow();
            Console console = new Console(textFlow);

            console.write("[ERROR] Something went wrong");

            assertEquals("[ERROR] Something went wrong", textFlow.getText());
            assertEquals(1, textFlow.getChildren().size());

            Text textNode = (Text) textFlow.getChildren().get(0);
            assertEquals("[ERROR] Something went wrong", textNode.getText());
            assertEquals(javafx.scene.paint.Color.web("#D0253C"), textNode.getFill());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testNormalColoringWithTextFlow() {
        interact(() -> {
            ColoredTextFlow textFlow = new ColoredTextFlow();
            Console console = new Console(textFlow);

            console.write("Normal message");

            assertEquals("Normal message", textFlow.getText());
            assertEquals(1, textFlow.getChildren().size());

            Text textNode = (Text) textFlow.getChildren().get(0);
            assertEquals("Normal message", textNode.getText());
            assertEquals(javafx.scene.paint.Color.WHITE, textNode.getFill());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testMixedMessagesWithTextFlow() {
        interact(() -> {
            ColoredTextFlow textFlow = new ColoredTextFlow();
            Console console = new Console(textFlow);

            console.writeLine("Normal message");
            console.writeLine("[ERROR] Error message");
            console.writeLine("Another normal message");

            assertEquals("Normal message\n[ERROR] Error message\nAnother normal message\n",
                         textFlow.getText());
            assertEquals(3, textFlow.getChildren().size());

            Text firstNode = (Text) textFlow.getChildren().get(0);
            assertEquals(javafx.scene.paint.Color.WHITE, firstNode.getFill());

            Text errorNode = (Text) textFlow.getChildren().get(1);
            assertEquals(javafx.scene.paint.Color.web("#D0253C"), errorNode.getFill());

            Text lastNode = (Text) textFlow.getChildren().get(2);
            assertEquals(javafx.scene.paint.Color.WHITE, lastNode.getFill());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testSetTextFlow() {
        interact(() -> {
            Console console = new Console();
            ColoredTextFlow textFlow = new ColoredTextFlow();

            console.setTextFlow(textFlow);
            console.write("Test");

            assertEquals("Test", textFlow.getText());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testWriterIntegrationWithTextFlow() throws InterruptedException {
        ColoredTextFlow textFlow = new ColoredTextFlow();
        Console console = new Console(textFlow);

        interact(() -> {
            console.getWriter().write("From writer");
        });

        Thread.sleep(100);
        WaitForAsyncUtils.waitForFxEvents();

        interact(() -> {
            assertEquals("From writer", textFlow.getText());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testWriteEmptyString() {
        interact(() -> {
            Console console = new Console();
            console.write("");
            assertEquals("", console.textArea.getText());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testWriteLineEmptyString() {
        interact(() -> {
            Console console = new Console();
            console.writeLine("");
            assertEquals("\n", console.textArea.getText());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testClearEmptyConsole() {
        interact(() -> {
            Console console = new Console();
            console.clear();
            assertEquals("", console.textArea.getText());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testGetTextFromEmptyConsole() {
        interact(() -> {
            Console console = new Console();
            assertEquals("", console.getText());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testGetTextWithNonColoredTextFlow() {
        interact(() -> {
            TextFlow textFlow = new TextFlow();
            Console console = new Console(textFlow);

            assertEquals("", console.getText());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testMultipleClearOperations() {
        interact(() -> {
            Console console = new Console();
            console.write("Text 1");
            console.clear();
            console.write("Text 2");
            console.clear();
            console.write("Text 3");

            assertEquals("Text 3", console.textArea.getText());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testWriterClearedOnConsoleClear() throws InterruptedException {
        Console console = new Console();

        interact(() -> {
            console.getWriter().write("Test");
        });

        Thread.sleep(100);
        WaitForAsyncUtils.waitForFxEvents();

        interact(() -> {
            assertEquals("Test", console.textArea.getText());
            console.clear();
            assertEquals("", console.textArea.getText());
        });
        WaitForAsyncUtils.waitForFxEvents();

        // Write again to verify writer is still functional after clear
        interact(() -> {
            console.getWriter().write("After clear");
        });

        Thread.sleep(100);
        WaitForAsyncUtils.waitForFxEvents();

        interact(() -> {
            assertEquals("After clear", console.textArea.getText());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }
}