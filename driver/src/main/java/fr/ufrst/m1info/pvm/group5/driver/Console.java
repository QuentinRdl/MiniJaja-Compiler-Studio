package fr.ufrst.m1info.pvm.group5.driver;

import fr.ufrst.m1info.pvm.group5.memory.Writer;
import javafx.application.Platform;
import javafx.scene.control.TextArea;

public class Console {
    /**
     * Textarea to add to the window
     */
    public TextArea textArea;
    /**
     * Writer associated to the textarea. Anything written to the writer will be added to the textarea.
     */
    private final Writer writer;

    public Console(){
        textArea = new TextArea();
        textArea.setEditable(false);
        writer = new Writer();
        writer.TextAddedEvent.subscribe(e -> textArea.appendText(e.diff()));
    }

    public Console(TextArea textArea){
        this.textArea = textArea;
        this.textArea.setEditable(false);
        writer = new Writer();
        writer.TextAddedEvent.subscribe(e -> Platform.runLater(() -> textArea.appendText(e.diff())));
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
        textArea.clear();
    }

    /**
     * Writes a line to the console without using the writer
     * @param content text to write to the console
     */
    public void write(String content){
        textArea.appendText(content);
    }

    /**
     * Writes a line to the console without using the writer and adds a new line at the end
     *
     * @param content text to write to the console
     */
    public void writeLine(String content) {
        textArea.appendText(content + "\n");
    }
}
