package fr.ufrst.m1info.pvm.group5.driver;

import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;


/**
 * The CodeLineCell class defines how each CodeLine is displayed inside the ListView.
 * It is responsible for creating and updating the visual cells that contain code lines.
 * Each cell allows the user to view and edit the code for a specific line in the program.
 */
public class CodeLineCell extends ListCell<CodeLine> {
    private HBox container;
    private Label lineNumberLabel;
    private TextField codeField;


    /**
     * Creates a new CodeLineCell and initializes its layout.
     * The cell contains a label for the line number and a text field for the code.
     */
    public CodeLineCell(){
        super();

        lineNumberLabel = new Label();
        lineNumberLabel.setPrefWidth(50);
        lineNumberLabel.setAlignment(Pos.CENTER_LEFT);
        lineNumberLabel.getStyleClass().add("line-number");

        codeField = new TextField();
        codeField.getStyleClass().add("code-field");
        HBox.setHgrow(codeField, Priority.ALWAYS);

        //TODO : make the code editable by listening for changes

        container = new HBox();
        container.getStyleClass().add("code-line");
        container.getChildren().addAll(lineNumberLabel, codeField);
    }

    /**
     * Updates the content of this cell to display a specific CodeLine.
     * If the cell is empty or the provided item is null, the cell content is cleared.
     * Otherwise, it displays the line number and the corresponding code text.
     *
     * @param  item  the CodeLine object to display in this cell
     * @param empty true if the cell should be empty, false otherwise
     */
    @Override
    protected void updateItem(CodeLine item, boolean empty){
        super.updateItem(item, empty);

        if (empty | item == null){
            setGraphic(null);
        } else {
            lineNumberLabel.setText(String.valueOf(item.getLineNumber()));
            codeField.setText(item.getCode());
            setGraphic(container);
        }
    }
}
