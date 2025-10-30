package fr.ufrst.m1info.pvm.group5.driver;

import fr.ufrst.m1info.pvm.group5.interpreter.InterpreterMiniJaja;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.control.skin.VirtualFlow;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Controller class for managing interactions in the main application interface
 * It handles file loading, displaying and editing code lines in a ListView,
 * and saving modifications back to a file
 */
public class MainController {
    @FXML
    private Label fileLabel;

    @FXML
    private ListView<CodeLine> codeListView;

    @FXML
    private TextArea output;

    private Console console;

    private ObservableList<CodeLine> codeLines;

    @FXML
    private SplitPane splitPane;

    private File currentFile;

    /**
     * Initializes the controller components after the FXML has been loaded.
     */
    @FXML
    public void initialize(){
        console = new Console(output);

        splitPane.setDividerPositions(0.75);
        splitPane.setOrientation(Orientation.VERTICAL);

        // initialize listView
        codeLines = FXCollections.observableArrayList();
        codeListView.setItems(codeLines);

        // allows ListView to know how to create its cells
        codeListView.setCellFactory(lv -> {
            CodeLineCell cell = new CodeLineCell();
            cell.setListener(new CodeLineCellListener() {
                @Override
                public void onEnterPressed(CodeLine codeLine) {
                    handleEnterPressed(codeLine);
                }

                @Override
                public void onDeletePressed(CodeLine codeLine) {
                    handleDeleteEmptyLine(codeLine);
                }
            });
            return cell;
        });

    }

    /**
     * Called when the user clicks the "Open" button
     * Opens a file chooser allowing selection of a MiniJaja or JajaCode file,
     * then loads its content into the ListView
     */
    @FXML
    protected void selectFileButton() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new ExtensionFilter("MiniJaja and JajaCode file", "*.mjj", "*.jcc")
        );
        File selectedFile = fc.showOpenDialog(splitPane.getScene().getWindow());
        loadFile(selectedFile);
    }


    /**
     * Load the selected file, reads it line by line, and displays each line
     * in the ListView with line numbering
     *
     * @param selectedFile the file to be loaded
     * @return true if the file was successfully loaded, false otherwise
     */
    public boolean loadFile(File selectedFile){
        if(selectedFile == null){
            System.out.println("No file selected");
            return false;
        }

        if(!selectedFile.exists()){
            console.getWriter().writeLine("[ERROR] File doesn't exist :" + selectedFile.getName());
            System.err.println("File doesn't exist : " + selectedFile.getName());
            return false;
        }

        try {
            // Delete old cells
            codeLines.clear();

            //Read file
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(selectedFile))) {
                int nbLines = 1;
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    codeLines.add(new CodeLine(nbLines++, line));
                }
            }

            fileLabel.setText(selectedFile.getName());
            currentFile = selectedFile;

            console.getWriter().writeLine("[INFO] File loaded : " + selectedFile.getName());
            return true;

        } catch (IOException e){
            System.err.println("Error reading file : " + e.getMessage());
            console.getWriter().writeLine("[ERROR] " + e.getMessage());
            return false;
        }
    }

    /**
     * Returns the label that displays the name of the currently loaded file
     *
     * @return the Label showing the selected file name
     */
    public Label getFileLabel(){
        return fileLabel;
    }

    /**
     * Returns the observable list containing all lines of code currently loaded
     *
     * @return an ObservableList of Codeline objects
     */
    public ObservableList<CodeLine> getCodeLines(){
        return codeLines;
    }

    /**
     * Returns the ListView component that display the lines of code in the user interface
     *
     * @return the ListView showing CodeLine elements
     */
    public ListView<CodeLine> getCodeListView(){
        return codeListView;
    }

    /**
     * Returns the full code currently displayed in the editor,
     * combining all lines into a signe string separated by newlines
     *
     * @return a String representing the modified code
     */
    public String getModifiedCode(){
        return codeLines.stream().map(CodeLine::getCode).collect(Collectors.joining("\n"));
    }

    /**
     * Returns the currently opened file in the editor
     *
     * @return the current File object, or null if none is loaded
     */
    public File getCurrentFile() {
        return currentFile;
    }

    /**
     * Saves the current code to the currently loaded file
     * If no file is loaded, it triggers the "Save As" dialog instead
     */
    public void saveButton(){
        if(currentFile != null){
            saveToFile(currentFile);
        } else {
            saveAsButton();
        }
    }

    /**
     * Opens a "Save As" dialog to choose a destination and save the current code as a new file
     */
    public void saveAsButton(){
        FileChooser fc = new FileChooser();
        fc.setTitle("Save a file as");
        fc.getExtensionFilters().addAll(
                new ExtensionFilter( "MiniJaja file", "*.mjj"),
                new ExtensionFilter("JajaCode file", "*.jcc")
        );

        // Default name if file exists
        if (currentFile != null){
            fc.setInitialFileName(currentFile.getName());
            fc.setInitialDirectory(currentFile.getParentFile());
        }

        File fileToSave = fc.showSaveDialog(splitPane.getScene().getWindow());
        saveAs(fileToSave);
    }

    /**
     * Saves the current code to the specified file
     * It successful, updates the file label and sets it as the current file
     *
     * @param file the file to save to
     */
    public void saveAs(File file){
        if (file != null){
          saveToFile(file);
          currentFile = file;
          fileLabel.setText(currentFile.getName());
        }
    }

    /**
     * Writes the content of the code editor to a file on disk
     * Displays messages in the output area if available
     *
     * @param file the file to write to
     */
    private void saveToFile(File file){
        try {
            List<String> lines = codeLines.stream().map(CodeLine::getCode).toList();
            Files.write(file.toPath(), lines , StandardCharsets.UTF_8);

            console.getWriter().writeLine("[INFO] File saved " + file.getName());

        } catch (IOException e){
            System.err.println("Error during saving : " + e.getMessage());
            console.getWriter().writeLine("[ERROR] Error during saving : " + e.getMessage());
        }
    }

    /**
     * Handles the Enter key press event
     * Inserts a new empty lin after the current one and renumbers all lines
     *
     * @param codeLine the CodeLine where Enter was pressed
     */
    private void handleEnterPressed(CodeLine codeLine){
        int index = codeLines.indexOf(codeLine);
        if (index >= 0 && index < codeLines.size()){
            // Add a new line of code
            CodeLine newLine = new CodeLine(index + 2, "");
            codeLines.add(index + 1, newLine);

            // Renumber all lines
            renumberAllLines();


            // Select and focus on the new line
            final int targetIndex = index + 1;
            Platform.runLater(() -> {
                codeListView.layout(); //force refresh of the ListView
                codeListView.getSelectionModel().clearAndSelect(targetIndex);

                int firstCellVisibleIndex = getFirstVisibleIndex();
                codeListView.scrollTo(firstCellVisibleIndex + 1);

                // Give focus after updating the selection
                Platform.runLater(this::focusSelectedCell);

            });
        }
    }

    /**
     * Handles the deletion of an empty code line when Backspace is pressed
     * Removes the line, renumbers the remaining lines, and manages focus
     *
     * @param codeLine the CodeLine to delete
     */
    private void handleDeleteEmptyLine(CodeLine codeLine){
        int index = codeLines.indexOf(codeLine);
        if (codeLines.isEmpty() || index < 0 || index >= codeLines.size()){
            return;
        }

        if(codeLines.size() == 1){
            return;
        }

        codeLines.remove(index);

        renumberAllLines();;

        int targetIndex = Math.max(0, index - 1);
        Platform.runLater(() -> {
            codeListView.layout();
            codeListView.getSelectionModel().clearAndSelect(targetIndex);

            int firstCellVisibleIndex = getFirstVisibleIndex();
            if (index <= firstCellVisibleIndex && firstCellVisibleIndex > 0){
                codeListView.scrollTo(firstCellVisibleIndex - 1);
            }

            Platform.runLater(this::focusSelectedCell);
        });
    }

    /**
     * Updates the numbering of all lines to ensure consistency after adding or removing a line
     */
    private void renumberAllLines(){
        for (int i = 0; i < codeLines.size(); i++){
            codeLines.get(i).setLineNumber(i + 1);
        }
        codeListView.refresh();
    }

    /**
     * Sets focus on the currently selected cell in the ListView
     * If no cell is selected, nothing happens
     */
    private void focusSelectedCell(){
        int selectedIndex = codeListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0){
            return;
        }

        for (Object node : codeListView.lookupAll(".list-cell")) {
            if (node instanceof CodeLineCell cell) {
                if (cell.getIndex() == selectedIndex) {
                    cell.focusTextField();
                    break;
                }
            }
        }
    }

    /**
     * Returns the index of the first visible cell in the ListView
     * Used to maintain proper scrolling behavior during edits
     *
     * @return the index of the first visible cell, or 0 if unavailable
     */
    private int getFirstVisibleIndex() {
        VirtualFlow<?> virtualFlow = (VirtualFlow<?>) codeListView.lookup(".virtual-flow");
        if(virtualFlow != null && virtualFlow.getFirstVisibleCell() != null) {
            return virtualFlow.getFirstVisibleCell().getIndex();
        }
        return 0;
    }

    /**
     * Executes the current code when the "Run" button is clicked
     * Retrieves the code from the editor and passes it to the InterpreterMiniJaja for interpretation
     * After interpretation, logs either a success message or an error message to the console
     */
    public void onRunClicked(){
        String code = getModifiedCode();

        if (code.isEmpty()){
            return;
        }

        InterpreterMiniJaja interpreterMiniJaja = new InterpreterMiniJaja(console.getWriter());

        String err = interpreterMiniJaja.interpretCode(code);

        if(err == null){
            console.getWriter().writeLine("[INFO] Interpretation successfully completed");
        } else {
            console.getWriter().writeLine("[ERROR] " + err);
        }

    }
}