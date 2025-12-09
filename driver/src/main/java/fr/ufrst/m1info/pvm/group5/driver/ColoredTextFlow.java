package fr.ufrst.m1info.pvm.group5.driver;

import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * A custom TextFlow that provides a getText() method for compatibility with tests
 * This class extends TextFlow to add colored console output capabilities while maintaining
 * the same getText() interface as TextArea
 */
public class ColoredTextFlow extends TextFlow {

    private ContextMenu contextMenu;

    /**
     * Returns all text content from this TextFlow as a single string
     * This method provides compatibility with TextArea.getText() for testing
     *
     * @return the complete text content
     */
    public String getText() {
        StringBuilder sb = new StringBuilder();
        for (Node node : getChildren()) {
            if (node instanceof Text text) {
                sb.append(text.getText());
            }
        }
        return sb.toString();
    }

    /**
     * Clears all text content from this TextFlow
     */
    public void clear() {
        getChildren().clear();
    }

    /**
     * Sets the context menu for this TextFlow
     * This method provides compatibility with TextArea.setContextMenu()
     *
     * @param contextMenu the context menu to set
     */
    public void setContextMenu(ContextMenu contextMenu) {
        this.contextMenu = contextMenu;

        // Add mouse event handler to show context menu on right click
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getButton() == MouseButton.SECONDARY && this.contextMenu != null) {
                this.contextMenu.show(this, event.getScreenX(), event.getScreenY());
                event.consume();
            }
        });
    }
}
