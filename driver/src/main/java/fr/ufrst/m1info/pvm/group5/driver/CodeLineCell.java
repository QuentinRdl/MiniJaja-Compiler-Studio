package fr.ufrst.m1info.pvm.group5.driver;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.fxmisc.richtext.InlineCssTextArea;


/**
 * The CodeLineCell class defines how each CodeLine is displayed inside the ListView.
 * It is responsible for creating and updating the visual cells that contain code lines.
 * Each cell allows the user to view and edit the code for a specific line in the program.
 *
 * This class also manages user input, such as adding or deleting lines using keyboard shortcuts,
 * toggling breakpoints by clicking on the line number area ans applying syntax highlighting and debug-related styles.
 */
public class CodeLineCell extends ListCell<CodeLine> {
    private HBox container;
    private StackPane lineNumberContainer;
    private Label lineNumberLabel;
    private InlineCssTextArea codeField;
    private Circle breakpointCircle;
    private IndentationGuideCanvas indentationCanvas;

    // Listener used to handle user interactions (Enter ou Delete key events)
    private CodeLineCellListener listener;

    // Flag indicating whether the line was empty before the last Backspace press
    private boolean wasEmptyOnLastBackspace = false;

    // Indicates whether the code line is editable
    private boolean editable = true;

    private boolean isUpdating = false; // Indicates that the cell is being updated

    private boolean isMiniJaja = true; // Track whether this is MiniJaja or JajaCode for syntax highlighting

    // Listener used to react when the debug state of a line changes
    private ChangeListener<Boolean> debugLineListener;
    private CodeLine currentItem; // Currently displayed / associated code line

    /**
     * Creates a new CodeLineCell and initializes its layout.
     * The cell includes a line number label, an optional breakpoint indicator,
     * and a text field for editing the code content. It also listens for key events
     * to manage adding or deleting lines dynamically
     */
    public CodeLineCell(){
        super();

        lineNumberLabel = new Label();
        lineNumberLabel.setPrefWidth(50);
        lineNumberLabel.setAlignment(Pos.CENTER);
        lineNumberLabel.getStyleClass().add("line-number");

        breakpointCircle = new Circle(6);
        breakpointCircle.setFill(Color.web("#BF2237"));
        breakpointCircle.setVisible(false);

        lineNumberContainer = new StackPane();
        lineNumberContainer.setPrefWidth(50);
        lineNumberContainer.setAlignment(Pos.CENTER);
        lineNumberContainer.getStyleClass().add("line-number-container");
        lineNumberContainer.getChildren().addAll(breakpointCircle, lineNumberLabel);

        codeField = new InlineCssTextArea();
        codeField.getStyleClass().addAll("styled-text-area", "code-field");
        codeField.setWrapText(false);
        codeField.setPrefHeight(30);
        codeField.setPrefWidth(1000);
        HBox.setHgrow(codeField, Priority.ALWAYS);


        // Track focus changes to detect Backspace on empty lines
        codeField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Platform.runLater(() -> {
                    if (codeField.getText().isEmpty()){
                        wasEmptyOnLastBackspace = true;
                    }
                });
            }
        });

        // Listens for changes in the text field to synchronise the changes and apply syntax highlighting
        codeField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (isUpdating) return; // prevent reacting to programmatic updates

            // Normalize the text: remove any newline characters that may have been inserted
            String cleaned = newValue;
            if (cleaned != null && (cleaned.contains("\n") || cleaned.contains("\r"))) {
                cleaned = cleaned.replace("\r", "").replace("\n", "");
                // update the text area and avoid re-entering the listener
                isUpdating = true;
                int caret = codeField.getCaretPosition();
                codeField.replaceText(cleaned);
                // restore caret position (clamped)
                final int pos = Math.min(caret, cleaned.length());
                codeField.moveTo(pos);
                isUpdating = false;
            }

            if(getItem() != null){
                getItem().setCode(cleaned);
            }

            if (cleaned != null && !cleaned.isEmpty()){
                wasEmptyOnLastBackspace = false;
            }

            if(listener != null && !isUpdating && oldValue != null && !oldValue.equals(cleaned)) {
                listener.onModified();
            }

            // Apply syntax highlighting
            if (!isUpdating && cleaned != null) {
                applySyntaxHighlighting(cleaned);
            }

            // Update indentation lines
            if (indentationCanvas != null) {
                indentationCanvas.setText(cleaned);
            }
        });

        // if the Enter key is pressed, a new line is added to the list view
        // when Backspace is pressed, remove the line only if it's empty and was already empty before
        // this reproduces the typical IDE behavior when deleting empty lines
        codeField.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();

            if(listener == null) return;

            switch (code) {
                case KeyCode.ENTER -> {
                    listener.onEnterPressed(getItem());
                    event.consume(); // Prevent InlineCssTextArea from adding newline
                }
                case KeyCode.BACK_SPACE -> {
                    String currentText = codeField.getText();
                    int caretPos = codeField.getCaretPosition();

                    if (currentText.isEmpty() && caretPos == 0 && wasEmptyOnLastBackspace){
                        listener.onDeletePressed(getItem());
                        event.consume();
                        wasEmptyOnLastBackspace = false;
                    }
                    else if (currentText.isEmpty() && caretPos == 0){
                        wasEmptyOnLastBackspace = true;
                        event.consume();
                    }
                    else if (currentText.length() == 1 && caretPos == 1){
                        wasEmptyOnLastBackspace = false;
                    }
                    else {
                        wasEmptyOnLastBackspace = false;
                    }
                }
                case KeyCode.UP -> listener.onUpPressed(getIndex());
                case KeyCode.DOWN -> listener.onDownPressed(getIndex());
            }
        });

        // Also consume Enter in KEY_TYPED phase to prevent newline insertion in InlineCssTextArea
        codeField.setOnKeyTyped(event -> {
            if (event.getCharacter().equals("\r") || event.getCharacter().equals("\n")) {
                event.consume();
            }
        });

        // Create indentation canvas
        indentationCanvas = new IndentationGuideCanvas();
        indentationCanvas.setMouseTransparent(true); // Allow clicks through to codeField

        // Wrap codeField and canvas in StackPane for overlay
        StackPane codeFieldContainer = new StackPane();
        codeFieldContainer.getChildren().addAll(indentationCanvas, codeField);
        StackPane.setAlignment(indentationCanvas, Pos.TOP_LEFT);
        StackPane.setAlignment(codeField, Pos.TOP_LEFT);

        // Bind canvas size to codeField size
        indentationCanvas.widthProperty().bind(codeField.widthProperty());
        indentationCanvas.heightProperty().bind(codeField.heightProperty());

        // Create container with new structure
        container = new HBox();
        container.getStyleClass().add("code-line");
        container.getChildren().addAll(lineNumberContainer, codeFieldContainer);

        lineNumberContainer.setOnMouseClicked(event -> handleBreakpointClick());

        debugLineListener = (obs, oldVal, newVal) -> updateDebugLineStyle(newVal);
    }

    /**
     * Returns the label displaying the line number of this code line
     *
     * @return the Label showing the line number
     */
    public Label getLineNumberLabel() {
        return lineNumberLabel;
    }

    /**
     * Returns the container that holds the line number and breakpoint indicator
     *
     * @return the StackPane used as the container for the line number
     */
    public StackPane getLineNumberContainer(){
        return lineNumberContainer;
    }

    /**
     * Returns the text field where the code for this line is displayed and edited
     *
     * @return the InlineCssTextArea for editing the code
     */
    public InlineCssTextArea getCodeField(){
        return codeField;
    }

    /**
     * Sets the listener used to handle user actions such as pressing Enter or Delete
     *
     * @param listener the CodeLineCellListener to associate with this cell
     */
    public void setListener(CodeLineCellListener listener){
        this.listener = listener;
    }

    /**
     * Sets whether this cell should be editable or read-only
     *
     * @param editable true for editable mode, false for read-only mode
     */
    public void setCodeEditable(boolean editable){
        this.editable = editable;
        if(codeField != null){
            codeField.setEditable(editable);
        }
    }


    /**
     * Handles a mouse click on the breakpoint area
     * Toggles the breakpoint state of the current CodeLine
     * If a breakpoint is set, it is removed, otherwise, it is added
     */
    private void handleBreakpointClick(){
        CodeLine item = getItem();
        if (item != null){
            item.setBreakpoint(!item.isBreakpoint());
            updateItem(item, false);
        }
    }

    /**
     * Updates the content of this cell to display a specific CodeLine.
     * If the cell is empty or the provided item is null, the cell content is cleared.
     * Otherwise, it displays the line number or breakpoint icon and the corresponding code text.
     *
     * @param item  the CodeLine object to display in this cell
     * @param empty true if the cell should be empty, false otherwise
     */
    @Override
    protected void updateItem(CodeLine item, boolean empty){
        super.updateItem(item, empty);

        isUpdating = true;

        if(currentItem != null && currentItem != item){
            currentItem.currentDebugLineProperty().removeListener(debugLineListener);
        }

        if (empty || item == null){
            getStyleClass().remove("debug-current-line");
            setGraphic(null);
            currentItem = null;
        } else {
            currentItem = item;

            codeField.replaceText(item.getCode());

            // Update indentation canvas
            if (indentationCanvas != null) {
                indentationCanvas.setText(item.getCode());
            }

            updateDebugLineStyle(item.isCurrentDebugLine());
            item.currentDebugLineProperty().addListener(debugLineListener);

            // Display breakpoint
            if (item.isBreakpoint()){
                lineNumberLabel.setVisible(false);
                breakpointCircle.setVisible(true);
            } else {
                lineNumberLabel.setText(String.valueOf(item.getLineNumber()));
                lineNumberLabel.setVisible(true);
                breakpointCircle.setVisible(false);
            }

            setGraphic(container);
        }

        isUpdating = false;

        // Apply syntax highlighting after updating (important for loaded files)
        if (!empty && item != null && !item.getCode().isEmpty()) {
            applySyntaxHighlighting(item.getCode());
        }
    }

    /**
     * Gives focus to the text field of this cell and places the cursor at the end of the text
     * It also updates the internal state used to detect Backspace deletions on empty lines
     */
    public void focusTextField(){
        if (codeField != null){
            codeField.requestFocus();
            Platform.runLater(() -> {
                codeField.moveTo(codeField.getText().length());
                if (codeField.getText().isEmpty()){
                    wasEmptyOnLastBackspace = true;
                }
            });
        }
    }

    /**
     * Sets whether this cell is for MiniJaja or JajaCode
     * This determines which syntax highlighting rules to apply
     *
     * @param isMiniJaja true for MiniJaja, false for JajaCode
     */
    public void setMiniJaja(boolean isMiniJaja) {
        this.isMiniJaja = isMiniJaja;
        // Reapply syntax highlighting with new language mode
        if (codeField != null && !codeField.getText().isEmpty()) {
            applySyntaxHighlighting(codeField.getText());
        }
    }

    /**
     * Applies syntax highlighting to the current text
     *
     * @param text the text to highlight
     */
    private void applySyntaxHighlighting(String text) {
        if (text == null || text.isEmpty()) {
            return;
        }

        SyntaxHighlighter.applySyntaxHighlighting(codeField, text, isMiniJaja);
    }

    /**
     * Applies or removes the debug highlight style on this line
     *
     * @param isDebugLine true if this line is the current debug line
     */
    private void updateDebugLineStyle(boolean isDebugLine){
        if(isDebugLine){
            if(!getStyleClass().contains("debug-current-line")){
                getStyleClass().add("debug-current-line");
            }
        } else {
            getStyleClass().remove("debug-current-line");
        }
    }
}
