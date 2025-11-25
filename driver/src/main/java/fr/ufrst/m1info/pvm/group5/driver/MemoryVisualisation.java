package fr.ufrst.m1info.pvm.group5.driver;

import javafx.geometry.Insets;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class MemoryVisualisation extends HBox {

    private TextArea stackView;
    private TextArea heapView;
    private Label stackLabel;
    private Label heapLabel;


    public MemoryVisualisation() {
        super(20);
        setPadding(new Insets(15));

        // Stack
        VBox stackSection = new VBox(10);

        stackLabel = new Label("Stack (empty)");
        stackLabel.setFont(Font.font("System", 14));
        stackLabel.setStyle("-fx-font-weight: bold;");

        stackView = new TextArea("(empty)");
        stackView.setEditable(false);
        stackView.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12px;");
        stackView.setWrapText(false);
        VBox.setVgrow(stackView, Priority.ALWAYS);

        stackSection.getChildren().addAll(stackLabel, stackView);


        // Heap
        VBox heapSection = new VBox(10);

        heapLabel = new Label("Heap (empty)");
        heapLabel.setFont(Font.font("System", 14));
        heapLabel.setStyle("-fx-font-weight: bold;");

        heapView = new TextArea("(empty)");
        heapView.setEditable(false);
        heapView.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12px;");
        heapView.setWrapText(false);
        VBox.setVgrow(heapView, Priority.ALWAYS);

        heapSection.getChildren().addAll(heapLabel, heapView);

        getChildren().addAll(stackSection, new Separator(), heapSection);
    }

    /**
     * Updates the memory visualization with current state
     * @param memory array
     */
    public void updateMemory(String[] memory){
        if(memory == null || memory.length < 2) return;
    }
}
