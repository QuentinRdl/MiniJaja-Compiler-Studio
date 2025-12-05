package fr.ufrst.m1info.pvm.group5.driver;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Visual representation of a heap memory block in the memory visualisation
 * Each block shows its address, allocation status, size, reference count, and content bytes
 */
public class HeapBlockView extends VBox {
    private Label bytesLabel;

    /**
     * Constructs a visual block for a heap memory segment
     *
     * @param address the memory address of the block
     * @param size the size of the block
     * @param allocated true if the block is allocated, false if free
     * @param refs the number of references pointing to this block
     * @param bytes the content of the block (can be empty initially)
     */
    public HeapBlockView(int address, int size, boolean allocated, int refs, String bytes){
        super();
        setPadding(new Insets(8));
        setSpacing(4);
        getStyleClass().add("heap-block");

        if (allocated){
            setStyle(" -fx-border-color: #FFD270;");
        } else {
            setStyle(" -fx-border-color: #CC8B00;");
        }

        Label addressLabel = new Label("Block @" + address + (allocated ? " - ALLOCATED" : " - FREE"));
        addressLabel.setStyle("-fx-font-weight: bold;");

        Label sizeLabel = new Label("Size : " + size);

        Label refsLabel = new Label("Refs : " + refs);

        bytesLabel = new Label();
        bytesLabel.setWrapText(true);
        bytesLabel.setMaxWidth(260);

        getChildren().addAll(addressLabel, sizeLabel, refsLabel, bytesLabel);
    }

    /**
     * Updates the bytes content displayed in this heap block
     *
     * @param bytes the string representating the block's bytes
     */
    public void setBytesLabel(String bytes){
        bytesLabel.setText("Bytes : " + bytes);
    }
}
