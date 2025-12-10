package fr.ufrst.m1info.pvm.group5.driver;

import fr.ufrst.m1info.pvm.group5.memory.Writer;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class Console {
    /**
     * Textarea to add to the window (deprecated, use TextFlow instead)
     */
    public TextArea textArea;

    /**
     * TextFlow for colored output
     */
    private TextFlow textFlow;

    /**
     * Writer associated to the output. Anything written to the writer will be added to the output.
     */
    private final Writer writer;

    public Console(){
        textArea = new TextArea();
        textArea.setEditable(false);
        writer = new Writer();
        writer.textAddedEvent.subscribe(e -> appendText(e.diff()));
    }

    public Console(TextArea textArea){
        this.textArea = textArea;
        this.textArea.setEditable(false);
        writer = new Writer();
        writer.textAddedEvent.subscribe(e -> Platform.runLater(() -> appendText(e.diff())));
    }

    public Console(TextFlow textFlow){
        this.textFlow = textFlow;
        writer = new Writer();
        writer.textAddedEvent.subscribe(e -> Platform.runLater(() -> appendText(e.diff())));
    }

    /**
     * Sets the TextFlow to be used for colored output
     * @param textFlow the TextFlow component
     */
    public void setTextFlow(TextFlow textFlow) {
        this.textFlow = textFlow;
    }

    /**
     * Get a reference to the writer used by the console to be used somewhere else
     * @return writer used by the console.
     */
    public Writer getWriter() {
        return writer;
    }

    /**
     * Removes all the text from the console
     */
    public void clear(){
        writer.clear();
        if (textFlow != null) {
            textFlow.getChildren().clear();
        } else if (textArea != null) {
            textArea.clear();
        }
    }

    /**
     * Writes a line to the console without using the writer
     * @param content text to write to the console
     */
    public void write(String content){
        appendText(content);
    }

    /**
     * Writes a line to the console without using the writer and adds a new line at the end
     *
     * @param content text to write to the console
     */
    public void writeLine(String content) {
        appendText(content + "\n");
    }

    /**
     * Appends text to the console with appropriate coloring
     * @param content the text to append
     */
    private void appendText(String content) {
        if (textFlow != null) {
            // Create colored text node
            Text text = new Text(content);

            // Apply red color to error messages
            if (content.contains("[ERROR]")) {
                text.setFill(Color.web("#D0253C"));
            } else {
                text.setFill(Color.WHITE);
            }

            textFlow.getChildren().add(text);
        } else if (textArea != null) {
            // Fallback to TextArea if TextFlow is not available
            textArea.appendText(content);
        }
    }

    /**
     * Gets all the text content from the console
     * This method maintains compatibility with tests that use getText()
     * @return the complete text content
     */
    public String getText() {
        if (textFlow instanceof ColoredTextFlow) {
            return ((ColoredTextFlow) textFlow).getText();
        } else if (textArea != null) {
            return textArea.getText();
        }
        return "";
    }
}
