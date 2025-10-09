package fr.ufrst.m1info.gl.compgl.driver;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/*
* Controller class for managing interactions in the main application interface.
*/
public class HelloController {
    @FXML
    private Label fileLabel;

    @FXML
    private ListView<CodeLine> codeListView;

    private ObservableList<CodeLine> codeLines;

    @FXML
    private SplitPane splitPane;

    /**
     * Initializes the controller components after the FXML has been loaded.
     */
    @FXML
    public void initialize(){
        splitPane.setDividerPositions(0.75);
        splitPane.setOrientation(Orientation.VERTICAL);

        // initialize listView
        codeLines = FXCollections.observableArrayList();
        codeListView.setItems(codeLines);

        // allows ListView to know how to create its cells
        codeListView.setCellFactory(lv -> new CodeLineCell());

    }

    /*
    * This method is called when the user clicks the Open button.
    * It opens a file chooser that allows the user to select a MiniJaja or JajaCode file, clears the previously displayed content,
    * reads the selected file line by line, and updates the ListView to display each numbered line
    * */
    @FXML
    protected void selectFileButton() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new ExtensionFilter("Fichier MiniJaja et JajaCode", "*.mjj", "*.jcc")
        );
        File selectedFile = fc.showOpenDialog(null);

        if (selectedFile != null) {
            fileLabel.setText(selectedFile.getName());

            // Delete old cells
            codeLines.clear();

            //Read file
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(selectedFile))){
                int nbLines = 1;
                String line;

                while((line = bufferedReader.readLine()) != null){
                    codeLines.add(new CodeLine(nbLines++, line));
                }

            } catch (IOException e){
                System.out.println("Error reading file: " + e.getMessage());
            }
        } else {
            System.out.println("No files selected");
        }
    }
}