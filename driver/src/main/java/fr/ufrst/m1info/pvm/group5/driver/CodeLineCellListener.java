package fr.ufrst.m1info.pvm.group5.driver;

/**
 * Listener interface for handling user actions on a CodeLineCell
 * This interface defines callbacks that are triggered when the user
 * performs specific actions such as pressing the Enter ou Delete keys
 * on a line within the editor
 */
public interface CodeLineCellListener {

    /**
     * Called when the user presses the Enter key while editing a code line
     *
     * @param codeLine the CodeLine associated with the Enter key event
     */
    void onEnterPressed(CodeLine codeLine);

    /**
     * Called when the user presses the Delete key on a code line
     *
     * @param codeLine the CodeLine associated with the Delete key event
     */
    void onDeletePressed(CodeLine codeLine);

    /**
     * Called when the Up arrow key is pressed on a code line
     *
     * @param index the index of the currently selected line
     */
    void onUpPressed(int index);

    /**
     * Called when the Down arrow key is pressed on a code line
     *
     * @param index the index of the currently selected line
     */
    void onDownPressed(int index);

    /**
     * Called when the code editor content is modified
     */
    void onModified();

}
