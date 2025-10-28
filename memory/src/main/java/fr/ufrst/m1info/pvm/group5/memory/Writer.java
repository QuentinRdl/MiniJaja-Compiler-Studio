package fr.ufrst.m1info.pvm.group5.memory;

import java.util.concurrent.CompletableFuture;

/**
 * Class interacting with memory allowing to have IO from the memory.
 * The class takes the role of publisher in a publish-subscribe like pattern.
 */
public class Writer {
    private String _innerText;
    public final Event<TextData> TextChangedEvent = new Event<>();
    public final Event<TextAddedData> TextAddedEvent = new Event<>();
    public final Event<TextRemovedData> TextRemovedEvent = new Event<>();

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
        _innerText = "";
    }


    /**
     * Triggers the events corresponding to when text is added
     *
     * @param data data of the added text
     * @return Completable future to when the task of running the event is over
     */
    private CompletableFuture<Void> onTextAddedAsync(TextAddedData data){
        TextChangedEvent.TriggerAsync(new TextData(
                data.oldText,
                data.newText,
                data.diff,
                data.nbAdded,
                TextChangeEvent.TEXT_ADDED
                ));
        return TextAddedEvent.TriggerAsync(data);
    }

    /**
     * Triggers the events corresponding to when text is removed
     *
     * @param data data of the removed text
     * @return Completable future to when the task of running the event is over
     */
    private CompletableFuture<Void> onTextRemovedAsync(TextRemovedData data){
        TextChangedEvent.TriggerAsync(new TextData(
                data.oldText,
                data.newText,
                data.diff,
                -data.nbRemoved,
                TextChangeEvent.TEXT_REMOVED
        ));
        return TextRemovedEvent.TriggerAsync(data);
    }

    /**
     * Append text at the end of the text
     *
     * @param text text to append
     * @return Completable future to when the task of running the event is over
     */
    public CompletableFuture<Void> writeAsync(String text){
        String oldText = _innerText;
        _innerText += text;
        return onTextAddedAsync(new TextAddedData(
                oldText,
                _innerText,
                text,
                text.length()
        ));
    }

    /**
     * Append a line of text at the end of the text
     *
     * @param line line of text to append
     * @return Completable future to when the task of running the event is over
     */
    public CompletableFuture<Void> writeLineAsync(String line){
        return writeAsync(line+"\n");
    }

    /**
     * eraseAsync a given number of characters at the end of the text
     *
     * @param nbChars number of characters to erase
     * @return Completable future to when the task of running the event is over
     */
    public CompletableFuture<Void> eraseAsync(int nbChars){
        String oldText = _innerText;
        int erased;
        String removedText;
        if(nbChars>=_innerText.length()){ // Special case
            _innerText = "";
            removedText = _innerText;
            erased = oldText.length();
        }
        else {
            _innerText = _innerText.substring(0, oldText.length() - nbChars);
            erased = nbChars;
            removedText = oldText.substring(erased+1);
        }
        return onTextRemovedAsync(new TextRemovedData(
                oldText,
                _innerText,
                removedText,
                erased
        ));
    }

    /**
     * eraseAsync the last line of the text
     *
     * @return Completable future to when the task of running the event is over
     */
    public CompletableFuture<Void> eraseLineAsync(){
        String oldText = _innerText;
        int lineIndex = _innerText.lastIndexOf("\n");
        int removed = 0;
        String removedText;
        if(lineIndex == -1){
            _innerText = "";
            removedText = _innerText;
            removed = oldText.length();
        }
        else{
            _innerText = _innerText.substring(0, lineIndex);
            removed = oldText.length() - lineIndex;
            removedText = _innerText.substring(removed+1);
        }
        return onTextRemovedAsync(new TextRemovedData(
                oldText,
                _innerText,
                removedText,
                removed
        ));
    }
}
