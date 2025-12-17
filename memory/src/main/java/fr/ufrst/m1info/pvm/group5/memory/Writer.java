package fr.ufrst.m1info.pvm.group5.memory;

/**
 * Class interacting with memory allowing to have IO from the memory.
 * The class takes the role of publisher in a publish-subscribe like pattern.
 */
public class Writer {
    private String innerText;
    public final Event<TextData> textChangedEvent = new Event<>();
    public final Event<TextAddedData> textAddedEvent = new Event<>();
    public final Event<TextRemovedData> textRemovedEvent = new Event<>();

    /**
     * Types of events that can be triggered from this class
     */
    public enum TextChangeEvent{
        /**
         * Text has been written
         */
        TEXT_ADDED,
        /**
         * Text has been removed
         */
        TEXT_REMOVED
    }

    /**
     * Record of data of a text update
     * @param oldText State of the text before the update
     * @param newText State of the text before the update
     * @param diff Text added or removed by the update. The content will depend on the type of event
     * @param nbAdded number of characters added by the update. Is negative if characters have been removed
     * @param change Type of change that occurred
     */
    public record TextData(String oldText, String newText, String diff, int nbAdded, TextChangeEvent change) {}

    /**
     * Record of data of a text added
     * @param oldText State of the text before text is added to it
     * @param newText State of the text after text is added to it
     * @param diff Text added
     * @param nbAdded Number of characters added to the Text
     */
    public record TextAddedData(String oldText, String newText, String diff, int nbAdded) {}

    /**
     * Record of data of text removal
     * @param oldText State of the text before text is removed from it
     * @param newText State of the text after text is removed from it
     * @param diff Text removed
     * @param nbRemoved Number of characters removed
     */
    public  record TextRemovedData(String oldText, String newText, String diff, int nbRemoved) {}

    public Writer(){
        innerText = "";
    }


    /**
     * Triggers the events corresponding to when text is added
     *
     * @param data data of the added text
     */
    private void onTextAdded(TextAddedData data){
        textChangedEvent.trigger(new TextData(
                data.oldText,
                data.newText,
                data.diff,
                data.nbAdded,
                TextChangeEvent.TEXT_ADDED
                ));
        textAddedEvent.trigger(data);
    }

    /**
     * Triggers the events corresponding to when text is removed
     *
     * @param data data of the removed text
     */
    private void onTextRemoved(TextRemovedData data){
        textChangedEvent.trigger(new TextData(
                data.oldText,
                data.newText,
                data.diff,
                -data.nbRemoved,
                TextChangeEvent.TEXT_REMOVED
        ));
        textRemovedEvent.trigger(data);
    }

    /**
     * Append text at the end of the text
     *
     * @param text text to append
     */
    public void write(String text){
        String oldText = innerText;
        innerText += text;
        onTextAdded(new TextAddedData(
                oldText,
                innerText,
                text,
                text.length()
        ));
    }

    /**
     * Append a line of text at the end of the text
     *
     * @param line line of text to append
     */
    public void writeLine(String line){
        write(line + "\n");
    }

    /**
     * erase a given number of characters at the end of the text
     *
     * @param nbChars number of characters to erase
     */
    public void erase(int nbChars){
        String oldText = innerText;
        int erased;
        String removedText;
        if(nbChars>= innerText.length()){ // Special case
            removedText = innerText;
            innerText = "";
            erased = oldText.length();
        }
        else {
            innerText = innerText.substring(0, oldText.length() - nbChars);
            erased = nbChars;
            removedText = oldText.substring(erased+1);
        }
        onTextRemoved(new TextRemovedData(
                oldText,
                innerText,
                removedText,
                erased
        ));
    }

    /**
     * erase the last line of the text
     *
     */
    public void eraseLineAsync(){
        String oldText = innerText;
        int lineIndex = innerText.lastIndexOf("\n");
        int removed = 0;
        String removedText;
        if(lineIndex == -1){
            removedText = innerText;
            innerText = "";
            removed = oldText.length();
        }
        else{
            innerText = innerText.substring(0, lineIndex);
            removed = oldText.length() - lineIndex;
            removedText = oldText.substring(lineIndex);
        }
        onTextRemoved(new TextRemovedData(
                oldText,
                innerText,
                removedText,
                removed
        ));
    }

    /**
     * Removes all the text of the writer
     */
    public void clear(){
        String oldText = innerText;
        innerText = "";
        onTextRemoved(new TextRemovedData(
                oldText,
                "",
                oldText,
                oldText.length()
        ));
    }

    /**
     * Clears the inner text of the writer without triggering any events.
     */
    public void reset(){
        innerText = "";
    }
}
