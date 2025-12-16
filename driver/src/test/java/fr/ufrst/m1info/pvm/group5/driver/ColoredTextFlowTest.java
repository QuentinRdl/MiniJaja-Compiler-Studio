package fr.ufrst.m1info.pvm.group5.driver;

import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the ColoredTextFlow class
 */
@ExtendWith(ApplicationExtension.class)
class ColoredTextFlowTest extends ApplicationTest {

    @Test
    void testGetTextWithEmptyTextFlow() {
        interact(() -> {
            ColoredTextFlow textFlow = new ColoredTextFlow();
            assertEquals("", textFlow.getText());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testGetTextWithSingleTextNode() {
        interact(() -> {
            ColoredTextFlow textFlow = new ColoredTextFlow();
            Text text = new Text("Hello World");
            textFlow.getChildren().add(text);

            assertEquals("Hello World", textFlow.getText());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testGetTextWithMultipleTextNodes() {
        interact(() -> {
            ColoredTextFlow textFlow = new ColoredTextFlow();
            textFlow.getChildren().add(new Text("Hello"));
            textFlow.getChildren().add(new Text(" "));
            textFlow.getChildren().add(new Text("World"));

            assertEquals("Hello World", textFlow.getText());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testGetTextWithMixedNodes() {
        interact(() -> {
            ColoredTextFlow textFlow = new ColoredTextFlow();
            textFlow.getChildren().add(new Text("Hello"));
            textFlow.getChildren().add(new Rectangle());
            textFlow.getChildren().add(new Text(" World"));

            assertEquals("Hello World", textFlow.getText());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testGetTextWithNonTextNodes() {
        interact(() -> {
            ColoredTextFlow textFlow = new ColoredTextFlow();
            textFlow.getChildren().add(new Rectangle());
            textFlow.getChildren().add(new Rectangle());

            assertEquals("", textFlow.getText());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testClearEmptyTextFlow() {
        interact(() -> {
            ColoredTextFlow textFlow = new ColoredTextFlow();
            textFlow.clear();

            assertEquals(0, textFlow.getChildren().size());
            assertEquals("", textFlow.getText());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testClearWithTextNodes() {
        interact(() -> {
            ColoredTextFlow textFlow = new ColoredTextFlow();
            textFlow.getChildren().add(new Text("Hello"));
            textFlow.getChildren().add(new Text(" World"));

            assertEquals(2, textFlow.getChildren().size());
            assertEquals("Hello World", textFlow.getText());

            textFlow.clear();

            assertEquals(0, textFlow.getChildren().size());
            assertEquals("", textFlow.getText());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testClearWithMixedNodes() {
        interact(() -> {
            ColoredTextFlow textFlow = new ColoredTextFlow();
            textFlow.getChildren().add(new Text("Hello"));
            textFlow.getChildren().add(new Rectangle());
            textFlow.getChildren().add(new Text("World"));

            assertEquals(3, textFlow.getChildren().size());

            textFlow.clear();

            assertEquals(0, textFlow.getChildren().size());
            assertEquals("", textFlow.getText());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testMultipleClearOperations() {
        interact(() -> {
            ColoredTextFlow textFlow = new ColoredTextFlow();

            textFlow.getChildren().add(new Text("First"));
            textFlow.clear();

            textFlow.getChildren().add(new Text("Second"));
            textFlow.clear();

            textFlow.getChildren().add(new Text("Third"));

            assertEquals(1, textFlow.getChildren().size());
            assertEquals("Third", textFlow.getText());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testSetContextMenu() {
        interact(() -> {
            ColoredTextFlow textFlow = new ColoredTextFlow();
            ContextMenu contextMenu = new ContextMenu();
            MenuItem menuItem = new MenuItem("Test Item");
            contextMenu.getItems().add(menuItem);

            textFlow.setContextMenu(contextMenu);

            assertNotNull(textFlow);
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testSetContextMenuWithNull() {
        interact(() -> {
            ColoredTextFlow textFlow = new ColoredTextFlow();
            textFlow.setContextMenu(null);

            assertNotNull(textFlow);
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testGetTextWithNewlines() {
        interact(() -> {
            ColoredTextFlow textFlow = new ColoredTextFlow();
            textFlow.getChildren().add(new Text("Line 1\n"));
            textFlow.getChildren().add(new Text("Line 2\n"));
            textFlow.getChildren().add(new Text("Line 3"));

            assertEquals("Line 1\nLine 2\nLine 3", textFlow.getText());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testGetTextWithEmptyTextNodes() {
        interact(() -> {
            ColoredTextFlow textFlow = new ColoredTextFlow();
            textFlow.getChildren().add(new Text(""));
            textFlow.getChildren().add(new Text("Hello"));
            textFlow.getChildren().add(new Text(""));
            textFlow.getChildren().add(new Text("World"));
            textFlow.getChildren().add(new Text(""));

            assertEquals("HelloWorld", textFlow.getText());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testGetTextWithSpecialCharacters() {
        interact(() -> {
            ColoredTextFlow textFlow = new ColoredTextFlow();
            textFlow.getChildren().add(new Text("Tab:\t"));
            textFlow.getChildren().add(new Text("Quote:\""));
            textFlow.getChildren().add(new Text("Apostrophe:'"));

            assertEquals("Tab:\tQuote:\"Apostrophe:'", textFlow.getText());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }


    @Test
    void testGetTextPreservesTextNodeOrder() {
        interact(() -> {
            ColoredTextFlow textFlow = new ColoredTextFlow();
            textFlow.getChildren().add(new Text("A"));
            textFlow.getChildren().add(new Text("B"));
            textFlow.getChildren().add(new Text("C"));
            textFlow.getChildren().add(new Text("D"));
            textFlow.getChildren().add(new Text("E"));

            assertEquals("ABCDE", textFlow.getText());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testTextFlowWithColoredText() {
        interact(() -> {
            ColoredTextFlow textFlow = new ColoredTextFlow();

            Text redText = new Text("Red");
            redText.setFill(Color.RED);

            Text blueText = new Text("Blue");
            blueText.setFill(Color.BLUE);

            textFlow.getChildren().add(redText);
            textFlow.getChildren().add(blueText);

            assertEquals("RedBlue", textFlow.getText());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testClearAfterAddingManyNodes() {
        interact(() -> {
            ColoredTextFlow textFlow = new ColoredTextFlow();

            for (int i = 0; i < 100; i++) {
                textFlow.getChildren().add(new Text("Text " + i + " "));
            }

            assertEquals(100, textFlow.getChildren().size());

            textFlow.clear();

            assertEquals(0, textFlow.getChildren().size());
            assertEquals("", textFlow.getText());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testGetTextWithLongStrings() {
        interact(() -> {
            ColoredTextFlow textFlow = new ColoredTextFlow();
            String longString = "A".repeat(1000);
            textFlow.getChildren().add(new Text(longString));

            assertEquals(1000, textFlow.getText().length());
            assertEquals(longString, textFlow.getText());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testAddAndRemoveNodes() {
        interact(() -> {
            ColoredTextFlow textFlow = new ColoredTextFlow();

            Text text1 = new Text("First");
            Text text2 = new Text("Second");
            Text text3 = new Text("Third");

            textFlow.getChildren().add(text1);
            textFlow.getChildren().add(text2);
            textFlow.getChildren().add(text3);

            assertEquals("FirstSecondThird", textFlow.getText());

            textFlow.getChildren().remove(text2);

            assertEquals("FirstThird", textFlow.getText());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testContextMenuEventHandling() {
        interact(() -> {
            ColoredTextFlow textFlow = new ColoredTextFlow();
            AtomicBoolean menuShown = new AtomicBoolean(false);

            ContextMenu contextMenu = new ContextMenu();
            MenuItem item = new MenuItem("Test");
            item.setOnAction(e -> menuShown.set(true));
            contextMenu.getItems().add(item);

            textFlow.setContextMenu(contextMenu);

            assertFalse(menuShown.get());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testClearDoesNotAffectContextMenu() {
        interact(() -> {
            ColoredTextFlow textFlow = new ColoredTextFlow();

            ContextMenu contextMenu = new ContextMenu();
            textFlow.setContextMenu(contextMenu);

            textFlow.getChildren().add(new Text("Test"));
            textFlow.clear();

            assertNotNull(textFlow);
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testGetTextAfterModifyingTextNode() {
        interact(() -> {
            ColoredTextFlow textFlow = new ColoredTextFlow();
            Text text = new Text("Original");
            textFlow.getChildren().add(text);

            assertEquals("Original", textFlow.getText());

            text.setText("Modified");

            assertEquals("Modified", textFlow.getText());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testGetTextWithOnlyWhitespace() {
        interact(() -> {
            ColoredTextFlow textFlow = new ColoredTextFlow();
            textFlow.getChildren().add(new Text("   "));
            textFlow.getChildren().add(new Text("\t\t"));
            textFlow.getChildren().add(new Text("\n\n"));

            assertEquals("   \t\t\n\n", textFlow.getText());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }
}