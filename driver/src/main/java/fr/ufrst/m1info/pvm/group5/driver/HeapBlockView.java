package fr.ufrst.m1info.pvm.group5.driver;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Visual representation of a heap memory block in the memory visualisation
 * Each block shows its address, allocation status, size, reference count, and content bytes
 */
public class HeapBlockView extends VBox {
    private TextArea dataLabel;

    /**
     * Constructs a visual block for a heap memory segment
     *
     * @param address the memory address of the block
     * @param size the size of the block
     * @param allocatedString a string describing the allocation state ("Allocated" or "Free")
     * @param refs the number of references pointing to this block
     * @param bytes the content of the block (can be empty initially)
     */
    public HeapBlockView(int address, int size, String allocatedString, int refs, String bytes){
        super();
        setPadding(new Insets(8));
        setSpacing(4);
        getStyleClass().add("heap-block");

        boolean allocated = allocatedString.contains("Allocated");

        if (allocated){
            setStyle(" -fx-border-color: #FFD270;");
        } else {
            setStyle(" -fx-border-color: #CC8B00;");
        }

        Label addressLabel = new Label("Block @" + address + "  -  " + allocatedString.toUpperCase());
        addressLabel.setStyle("-fx-font-weight: bold;");

        Label sizeLabel = new Label("Size : " + size);

        Label refsLabel = new Label("Refs : " + refs);

        dataLabel = new TextArea();
        dataLabel.getStyleClass().add("data-area");
        dataLabel.setEditable(false);
        dataLabel.setWrapText(true);

        dataLabel.setPrefRowCount(3);
        dataLabel.setMinHeight(Region.USE_PREF_SIZE);
        VBox.setVgrow(dataLabel, Priority.NEVER);

        getChildren().addAll(addressLabel, sizeLabel, refsLabel, dataLabel);
    }

    /**
     * Updates the data content displayed in this heap block
     *
     * @param data the string representating the block's bytes
     */
    public void setDataLabel(String data){
        dataLabel.setText("Data : " + data );

    }
}
