package fr.ufrst.m1info.pvm.group5.driver;

import fr.ufrst.m1info.pvm.group5.memory.Writer;
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

    /**
     * Get a reference to the writer used by the console to be used somewhere else
     * @return writer used by the console.
     */
    public Writer getWriter() {
        return writer;
    }
}
