package fr.ufrst.m1info.pvm.group5.driver;

import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


/**
 * The CodeLineCell class defines how each CodeLine is displayed inside the ListView.
 * It is responsible for creating and updating the visual cells that contain code lines.
 * Each cell allows the user to view and edit the code for a specific line in the program.
 */
public class CodeLineCell extends ListCell<CodeLine> {
    private HBox container;
    private StackPane lineNumberContainer;
    private Label lineNumberLabel;
    private TextField codeField;
    private Circle breakpointCircle;


    /**
     * Creates a new CodeLineCell and initializes its layout.
     * The cell contains a label for the line number and a text field for the code.
     */
    public CodeLineCell(){
        super();

        lineNumberLabel = new Label();
        lineNumberLabel.setPrefWidth(50);
        lineNumberLabel.setAlignment(Pos.CENTER);
        lineNumberLabel.getStyleClass().add("line-number");

        breakpointCircle = new Circle(6);
        breakpointCircle.setFill(Color.web("#850606"));
        breakpointCircle.setVisible(false);

        lineNumberContainer = new StackPane();
        lineNumberContainer.setPrefWidth(50);
        lineNumberContainer.setAlignment(Pos.CENTER);
        lineNumberContainer.getStyleClass().add("line-number-container");
        lineNumberContainer.getChildren().addAll(breakpointCircle, lineNumberLabel);

        codeField = new TextField();
        codeField.getStyleClass().add("code-field");
        HBox.setHgrow(codeField, Priority.ALWAYS);

        //TODO : make the code editable by listening for changes

        container = new HBox();
        container.getStyleClass().add("code-line");
        container.getChildren().addAll(lineNumberContainer, codeField);

        lineNumberContainer.setOnMouseClicked(event -> handleBreakpointClick());
    }

    public Label getLineNumberLabel() {
        return lineNumberLabel;
    }

    public StackPane getLineNumberContainer(){
        return lineNumberContainer;
    }

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
     * Otherwise, it displays the line number and the corresponding code text.
     *
     * @param  item  the CodeLine object to display in this cell
     * @param empty true if the cell should be empty, false otherwise
     */
    @Override
    protected void updateItem(CodeLine item, boolean empty){
        super.updateItem(item, empty);

        if (empty || item == null){
            setGraphic(null);
        } else {
            codeField.setText(item.getCode());

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
    }
}
