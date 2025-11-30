package fr.ufrst.m1info.pvm.group5.driver;

import fr.ufrst.m1info.pvm.group5.interpreter.InterpreterJajaCode;
import fr.ufrst.m1info.pvm.group5.interpreter.InterpreterMiniJaja;
import fr.ufrst.m1info.pvm.group5.compiler.Compiler;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.control.skin.VirtualFlow;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import org.kordamp.ikonli.fontawesome5.FontAwesomeRegular;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.materialdesign.MaterialDesign;


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
    private ObservableList<CodeLine> codeLines;

    @FXML
    private ListView<CodeLine> compiledCodeListView;
    private ObservableList<CodeLine> compiledCodeLines;

    @FXML
    TextArea output;

    private Console console;

    @FXML
    private SplitPane splitPane;

    private File currentFile;

    @FXML
    private TabPane editorTabPane;

    @FXML
    private Tab compiledTab;

    @FXML
    private Tab memoryTabMinijaja;

    @FXML
    private Tab memoryTabJajacode;

    private MemoryVisualisation memoryVisualisationMiniJaja;

    private MemoryVisualisation memoryVisualisationJajaCode;

    @FXML
    private Button btnSave;
    @FXML
    private Button btnSaveAs;
    @FXML
    private Button btnRun;
    @FXML
    private Button btnCompile;
    @FXML
    private Button btnRunCompile;
    @FXML
    private Button btnDebugRun;
    @FXML
    private Button btnDebugStop;
    @FXML
    private Button btnDebugNext;


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

                @Override
                public void onUpPressed(int index) { handleUpPressed(index);}

                @Override
                public void onDownPressed(int index) { handleDownPressed(index);}
            });
            return cell;
        });

        compiledCodeLines = FXCollections.observableArrayList();
        compiledCodeListView.setItems(compiledCodeLines);
        compiledCodeListView.setCellFactory(lv -> {
            CodeLineCell cell = new CodeLineCell();
            cell.setCodeEditable(false);
            return cell;
        });

        editorTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            //TODO: disable buttons when viewing memory tabs
            if(isCompiledTab()){
                btnCompile.setDisable(true);
                btnRunCompile.setDisable(true);
            } else {
                activeButtons();
            }
        });

        memoryVisualisationMiniJaja = new MemoryVisualisation();
        if(memoryTabMinijaja != null){
            memoryTabMinijaja.setContent(memoryVisualisationMiniJaja);
        }
        hideMemoryTab(memoryTabMinijaja);

        memoryVisualisationJajaCode = new MemoryVisualisation();
        if(memoryTabJajacode != null){
            memoryTabJajacode.setContent(memoryVisualisationJajaCode);
        }
        hideMemoryTab(memoryTabJajacode);

        FontIcon playIcon = new FontIcon(FontAwesomeSolid.PLAY);
        playIcon.setIconColor(Color.DARKBLUE);
        playIcon.setIconSize(12);

        FontIcon stopIcon = new FontIcon(FontAwesomeSolid.STOP);
        stopIcon.setIconColor(Color.DARKRED);
        stopIcon.setIconSize(12);

        FontIcon nextIcon = new FontIcon(FontAwesomeSolid.ARROW_RIGHT);
        nextIcon.setIconColor(Color.DARKGREEN);
        nextIcon.setIconSize(12);

        btnDebugRun.setGraphic(playIcon);
        btnDebugRun.setContentDisplay(javafx.scene.control.ContentDisplay.LEFT);

        btnDebugStop.setGraphic(stopIcon);
        btnDebugStop.setContentDisplay(javafx.scene.control.ContentDisplay.LEFT);

        btnDebugNext.setGraphic(nextIcon);
        btnDebugNext.setContentDisplay(javafx.scene.control.ContentDisplay.LEFT);


        hideCompileTab();
        deactiveButtons();
        setupOutputContextMenu();
        setupKeyboardShortcuts();

    }


    /**
     * Register keyboard shortcuts with the Scene so we can use
     * shortcuts like Ctrl + S (save)
     */
    private void setupKeyboardShortcuts() {
        Platform.runLater(() -> {
            if (splitPane == null) return;
            Scene scene = splitPane.getScene();
            if (scene == null) return;

            // Ctrl + S -> Save
            scene.getAccelerators().put(
                    new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN),
                    this::saveButton
            );

            // Ctrl + Shift + S -> Save As
            scene.getAccelerators().put(
                    new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN),
                    this::saveAsButton
            );

            // Ctrl + R -> Run
            scene.getAccelerators().put(
                    new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN),
                    this::onRunClicked
            );

            // Ctrl + K -> Compile
            scene.getAccelerators().put(
                    new KeyCodeCombination(KeyCode.K, KeyCombination.CONTROL_DOWN),
                    this::onCompileClicked
            );

        });
    }


    /**
     * Called when the user clicks the "Open" button
     * Opens a file chooser allowing a MiniJaja file,
     * then loads its content into the ListView
     */
    @FXML
    protected void selectFileButton() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new ExtensionFilter("MiniJaja file", "*.mjj")
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
            return false;
        }

        if(!selectedFile.exists()){
            console.getWriter().writeLine("[ERROR] File doesn't exist : " + selectedFile.getName());
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
            activeButtons();

            compiledCodeLines.clear();
            hideCompileTab();
            clearMemoryVisualisation(memoryVisualisationMiniJaja);
            hideMemoryTab(memoryTabMinijaja);
            clearMemoryVisualisation(memoryVisualisationJajaCode);
            hideMemoryTab(memoryTabJajacode);

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
     * Returns the main TabPane that contains the editor tabs
     *
     * @return the TabPane managing open editor tabs
     */
    public TabPane getEditorTabPane(){
        return editorTabPane;
    }

    /**
     * Returns the tab displaying the compiled code
     *
     * @return the Tab that shows the compiled code
     */
    public Tab getCompiledTab(){
        return compiledTab;
    }

    /**
     * Returns the ListView component used to display compiled code lines
     *
     * @return the ListView containing compiled CodeLine objects
     */
    public ListView<CodeLine> getCompiledCodeListView(){
        return compiledCodeListView;
    }

    /**
     * Returns the observable list representing the compiled code lines
     *
     * @return the ObservableList of compiled CodeLine objects
     */
    public ObservableList<CodeLine> getCompiledCodeLines(){
        return compiledCodeLines;
    }

    /**
     * Returns the compiled code as a single string,
     * joining all CodeLine entries with newline characters
     *
     * @return the compiled JajaCode as text
     */
    public String getCompiledCode(){
        return compiledCodeLines.stream().map(CodeLine::getCode).collect(Collectors.joining("\n"));
    }

    /**
     * Saves the current code to the currently loaded file
     * If no file is loaded, it triggers the "Save As" dialog instead
     */
    public void saveButton(){
        if(isCompiledTab()){
            saveCompiledCodeAs();
        } else {
            if(currentFile != null){
                saveToFile(currentFile);
            } else {
                saveAsButton();
            }
        }
    }

    /**
     * Opens a "Save As" dialog to choose a destination and save the current code as a new file
     */
    public void saveAsButton(){
        FileChooser fc = new FileChooser();
        fc.setTitle("Save a file as");
        fc.getExtensionFilters().addAll(
                new ExtensionFilter( "MiniJaja file", "*.mjj")
        );

        // Default name if file exists
        if (currentFile != null){
            fc.setInitialFileName(getBaseFileName(currentFile.getName()));
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
            console.getWriter().writeLine("[ERROR] Error during saving : " + e.getMessage());
        }
    }

    /**
     * Opens a "Save As" dialog to save the compiled code as a JajaCode file
     * The default filename and directory are based on the currently opened file
     */
    public void saveCompiledCodeAs(){
        FileChooser fc = new FileChooser();
        fc.setTitle("Save compiled file as ");
        fc.getExtensionFilters().addAll(
                new ExtensionFilter( "JajaCode file", "*.jjc")
        );

        fc.setInitialFileName(getBaseFileName(currentFile.getName()));
        fc.setInitialDirectory(currentFile.getParentFile());

        File fileToSave = fc.showSaveDialog(splitPane.getScene().getWindow());
        saveAs(fileToSave);
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

        renumberAllLines();

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
     * Returns the index of the last visible cell in the ListView
     * Used to maintain proper scrolling behavior during edits
     *
     * @return the index of the last visible cell, or the last item index if unavailable
     */
    private int getLastVisibleIndex(){
        VirtualFlow<?> virtualFlow = (VirtualFlow<?>) codeListView.lookup(".virtual-flow");
        if(virtualFlow != null && virtualFlow.getLastVisibleCell() != null) {
            return virtualFlow.getLastVisibleCell().getIndex();
        }
        return codeListView.getItems().size() - 1;
    }


    /**
     * This func checks if the code we gave (as a String), is just empty chars or not
     * @param code the given code as a String
     * @return true if the code is just empty chars, false otherwise
     */
    private boolean isCodeEmptyChars(String code) {
        String justBlank = code.trim();
        // If the code we got is just empty chars, do not run it
        return justBlank.isEmpty();
    }


    /**
     * Handles the "Run" button click
     * Determines the file type and launches the appropriate interpretation (MiniJaja or JajaCode)
     * Displays an error if interpretation is not allowed
     */
    public void onRunClicked() {
        boolean miniJaja = isMinijajaFile();
        boolean jajaCode = isJajaCode();

        if(!miniJaja && !jajaCode) {
            console.getWriter().writeLine("[ERROR] Interpretation is only available for MiniJaja files and JajaCode files (.mjj & .jjc)");
            return;
        } else if (miniJaja && jajaCode) {
            // Should never be called in actual code but still check for tests purpose
            console.getWriter().writeLine("[INTERNAL ERROR] current file is marked as jjc and mjj");
            return;
        }

        if(!isCompiledTab()){
            interpretationMinijaja();
        } else {
            interpretationJajacode();
        }

    }

    /**
     * Interprets the current MiniJaja code
     * Retrieves edited code, checks validity, runs the MiniJaja interpreter,
     * and prints either a success or an error message
     */
    public void interpretationMinijaja(){
        String code = getModifiedCode();

        // If the code is just empty chars, do not run it
        if (code.isEmpty() || isCodeEmptyChars(code)){
            console.getWriter().writeLine("[ERROR] No code to interpret !");
            return;
        }

        String err = null;
        InterpreterMiniJaja interpreterMiniJaja = new InterpreterMiniJaja(console.getWriter());
        err = interpreterMiniJaja.interpretCode(code);

        if(err == null){
            showMemoryTab(memoryTabMinijaja);
            Platform.runLater(() -> memoryVisualisationMiniJaja.updateMemory(interpreterMiniJaja.getMemory().toStringTab()));
            console.getWriter().writeLine("[INFO] MiniJaja interpretation successfully completed");
        } else {
            console.getWriter().writeLine("[ERROR] " + err);
        }
    }

    /**
     * Interprets the current JajaCode code
     * Retrieves compiled code, checks validity, runs the JajaCode interpreter,
     * and prints either a success or an error message
     */
    public void interpretationJajacode(){
        String compiledCode = getCompiledCode();

        if(compiledCode.isEmpty() || isCodeEmptyChars(compiledCode)){
            console.getWriter().writeLine("[ERROR] No code to interpret !");
            return;
        }

        String err = null;
        InterpreterJajaCode interpreterJajaCode = new InterpreterJajaCode(console.getWriter());
        err = interpreterJajaCode.interpretCode(compiledCode);

        if(err == null){
            showMemoryTab(memoryTabJajacode);
            Platform.runLater(() -> memoryVisualisationJajaCode.updateMemory(interpreterJajaCode.getMemory().toStringTab()));
            console.getWriter().writeLine("[INFO] JajaCode interpretation successfully completed");
        } else {
            console.getWriter().writeLine("[ERROR] " + err);
        }
    }

    /**
     * Handles the "Up" arrow key press event
     * Moves the selection to the previous line in the ListView if it exists,
     * updates scrolling to keep the selected line visible, and focuses the text field
     *
     * @param index the index of the currently selected line
     */
    private void handleUpPressed(int index){
        if (index > 0){

            codeListView.getSelectionModel().clearAndSelect(index - 1);

            int firstCellVisibleIndex = getFirstVisibleIndex();
            if (index <= firstCellVisibleIndex){
                codeListView.scrollTo(Math.max(0, firstCellVisibleIndex - 1));
            }

            Platform.runLater(this::focusSelectedCell);
        }
    }

    /**
     * Handles the "Down" arrow key press event
     * Moves the selection to the next line in the ListView if it exists,
     * updates scrolling to keep the selected line visible, and focuses the text field
     *
     * @param index the index of the currently selected line
     */
    private void handleDownPressed(int index){
        if (index < codeLines.size() -1 ){
            codeListView.getSelectionModel().clearAndSelect(index + 1);

            int lastCellVisibleIndex = getLastVisibleIndex();
            int firstCellVisibleIndex = getFirstVisibleIndex();
            if (index + 1 > lastCellVisibleIndex){
                codeListView.scrollTo(Math.min(codeLines.size() - 1, firstCellVisibleIndex + 1));
            }

            Platform.runLater(this::focusSelectedCell);
        }
    }

    /**
     * Extracts the base name of a file (without its extension)
     * Used to suggest a default name when saving files with "Save As"
     *
     * @param fileName the complete file name
     * @return the file name without extension, null if input is null
     */
    public String getBaseFileName(String fileName){
        if (fileName == null) return null;

        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == - 1) ? fileName : fileName.substring(0, dotIndex);
    }

    /**
     * Creates a new empty file in the editor
     * Clears the current content, adds a first blank line,
     * activates the buttons, and updates the label to indicate a new file
     */
    public void createNewFile(){
        codeLines.clear();
        codeLines.add(new CodeLine(1, ""));
        codeListView.getSelectionModel().select(0);
        activeButtons();
        currentFile = null;
        fileLabel.setText("New file");

        compiledCodeLines.clear();
        hideCompileTab();

        clearMemoryVisualisation(memoryVisualisationMiniJaja);
        hideMemoryTab(memoryTabMinijaja);
        clearMemoryVisualisation(memoryVisualisationJajaCode);
        hideMemoryTab(memoryTabJajacode);
    }

    /**
     * Enables all main buttons in the interface (Save, Run, Compile...)
     * Typically called after loading or creating a file
     */
    private void activeButtons(){
        btnSave.setDisable(false);
        btnSaveAs.setDisable(false);
        btnRun.setDisable(false);
        btnCompile.setDisable(false);
        btnRunCompile.setDisable(false);
    }

    /**
     * Disables all main buttons in the interface (Save, Run, Compile...)
     * Typically called when no file is loaded or available
     */
    public void deactiveButtons(){
        btnSave.setDisable(true);
        btnSaveAs.setDisable(true);
        btnRun.setDisable(true);
        btnCompile.setDisable(true);
        btnRunCompile.setDisable(true);
    }

    /**
     * Handles the action triggered when the user clicks the "Compile" button
     *
     * This method verifies that the currently loaded file is a MiniJaja file,
     * then retrieves the modified code from the editor and, if not empty, sends it
     * to the Compiler for compilation.
     * Upon success, the compiled JajaCode is printed in the console along with a confirmation message.
     */
    public void onCompileClicked(){
        String code = getModifiedCode();

        if(code.isEmpty() || isCodeEmptyChars(code)){
            console.getWriter().writeLine("[ERROR] No code to compile !");
            return;
        }

        if(!isMinijajaFile()){
            console.getWriter().writeLine("[ERROR] Compilation is only available for MiniJaja files (.mjj)");
            return;
        }

        // Disable compile button while compilation is in progress
        // if (btnCompile != null) btnCompile.setDisable(true);

        Task<String> compileTask = new Task<>(){
            @Override
            protected String call() {
                Compiler compiler = new Compiler(console.getWriter());
                return compiler.compileCode(code);
            }
        };

        compileTask.setOnSucceeded(e -> {
            String res = compileTask.getValue();
            if (res != null){
                showCompiledTab();
                loadCompiledCodeToListView(res);
                console.getWriter().writeLine("[INFO] Compilation successful!");
            }
            // if (btnCompile != null) btnCompile.setDisable(false);
        });

        compileTask.setOnFailed(e -> {
            Throwable ex = compileTask.getException();
            console.getWriter().writeLine("[ERROR] Compilation failed: " + (ex != null ? ex.getMessage() : "Unknown error"));
            // if (btnCompile != null) btnCompile.setDisable(false);
        });

        Thread t = new Thread(compileTask, "Compiler-Thread");
        t.setDaemon(true);
        t.start();
    }

    /**
     * Compiles and immediately runs the current MiniJaja code
     * Validates the code, compiles it, loads the compiled result into the compiled tab,
     * then interprets it and logs either success or error message
     */
    public void onCompileAndRunClicked(){
        String code = getModifiedCode();
        if(code.isEmpty() || isCodeEmptyChars(code)){
            console.getWriter().writeLine("[ERROR] No code to compile and run !");
            return;
        }

        if(!isMinijajaFile()){
            console.getWriter().writeLine("[ERROR] Compilation and interpretation is only available for MiniJaja (.mjj) files");
            return;
        }

        // Disable compiler and run buttons while compilation is in progress
        // if (btnCompile != null) btnCompile.setDisable(true);
        // if (btnRunCompile != null) btnRunCompile.setDisable(true);

        // Create task to call later
        Task<String> compileAndRunTask = new Task<>() {
            @Override
            protected String call() {
                Compiler compiler = new Compiler(console.getWriter());
                return compiler.compileCode(code);
            }
        };

        compileAndRunTask.setOnSucceeded(e -> {
            String compiledCode = compileAndRunTask.getValue();
            if(compiledCode != null) {
                showCompiledTab();
                loadCompiledCodeToListView(compiledCode);

                // Interpret compiled code
                String err = null;
                InterpreterJajaCode interpreterJajaCode = new InterpreterJajaCode(console.getWriter());
                err = interpreterJajaCode.interpretCode(compiledCode);

                if(err == null){
                    console.getWriter().writeLine("[INFO] Compilation and interpretation successfully completed");
                } else {
                    console.getWriter().writeLine("[ERROR] " + err);
                }
            }

            if (btnCompile != null) btnCompile.setDisable(false);
            if (btnRunCompile != null) btnRunCompile.setDisable(false);
        });

        compileAndRunTask.setOnFailed(e -> {
            Throwable ex = compileAndRunTask.getException();
            console.getWriter().writeLine("[ERROR] Compilation failed: " + (ex != null ? ex.getMessage() : "Unknown error"));
            //if (btnCompile != null) btnCompile.setDisable(false);
            //if (btnRunCompile != null) btnRunCompile.setDisable(false);
        });

        Thread t = new Thread(compileAndRunTask, "Compiler-Run-Thread");
        t.setDaemon(true);
        t.start();
    }

    /**
     * Checks whether the currently opened file is a MiniJaja source file
     *
     * @return true if the file exists and its name ends with ".mjj", false otherwise
     */
    public boolean isMinijajaFile(){
        return fileExtensionMatches(".mjj");
    }


    /**
     * Checks whether the currently opened file is a JajaCode source file
     *
     * @return true if the file exists and its name ends with ".jjc", false otherwise
     */
    public boolean isJajaCode(){
        return fileExtensionMatches(".jjc");
    }


    /**
     * Checks that the current file extension matches with the parameter we give it
     * @param extension the extension we want to match
     * @return true if extension == actual extension, false otherwise
     */
    public boolean fileExtensionMatches(String extension) {
        if(currentFile == null){
            return false;
        }
        String fileName = currentFile.getName().toLowerCase();
        return fileName.endsWith(extension);
    }


    /**
     * Initializes a context menu for the output console
     *
     * This menu currently provides a single option, "Clear All", allowing
     * the user to clear the console output area.
     */
    private void setupOutputContextMenu(){
        if (output == null){
            return;
        }

        ContextMenu contextMenu = new ContextMenu();

        MenuItem clearOutput = new MenuItem("Clear All");
        clearOutput.setOnAction(e -> console.clear());

        contextMenu.getItems().addAll(clearOutput);

        output.setContextMenu(contextMenu);
    }

    /**
     * Checks whether the currently selected tab is the compiled code tab
     *
     * @return true if the compiled tab is selected, false otherwise
     */
    public boolean isCompiledTab(){
        return editorTabPane != null && editorTabPane.getSelectionModel().getSelectedItem() == compiledTab;
    }

    /**
     * Loads compiled code into the compiled code ListView
     * Clears any existing content before filling it with new code lines
     *
     * @param compiledCode the compiled code to display in the ListView
     */
    public void loadCompiledCodeToListView(String compiledCode){
        compiledCodeLines.clear();

        String[] lines = compiledCode.split("\n");
        int lineNumber = 1;
        for(String line : lines){
            compiledCodeLines.add(new CodeLine(lineNumber++, line));
        }
    }

    //TODO: replace the functions with showTab and hideTab (more generic functions)

    /**
     * Hides the compiled code tab from the editor tab pane
     */
    public void hideCompileTab(){
        if(editorTabPane != null && compiledTab != null){
            editorTabPane.getTabs().remove(compiledTab);
        }
    }

    /**
     * Displays the compiled code tab in the editor tab pane
     * Adds it if it is not already visible, and selects it
     */
    public void showCompiledTab(){
        if(editorTabPane != null && compiledTab != null){
            if(!editorTabPane.getTabs().contains(compiledTab)){
                editorTabPane.getTabs().add(compiledTab);
            }
            editorTabPane.getSelectionModel().select(compiledTab);
        }
    }

    /**
     * Displays a memory tab in the editor if it is not already visible
     *
     * @param memoryTab the Tab representing the memory view
     */
    public void showMemoryTab(Tab memoryTab){
        if(editorTabPane != null && memoryTab != null){
            if(!editorTabPane.getTabs().contains(memoryTab)){
                editorTabPane.getTabs().add(memoryTab);
            }
        }
    }

    /**
     * Hides the memory tab from the editor tab pane
     *
     * @param memoryTab the Tab representing the memory view
     */
    public void hideMemoryTab(Tab memoryTab){
        if(editorTabPane != null && memoryTab != null){
            editorTabPane.getTabs().remove(memoryTab);
        }
    }

    /**
     * Clears the contents of the memory visualisation
     *
     * @param memoryVisualisation the MemoryVisualisation instance to clear
     */
    private void clearMemoryVisualisation(MemoryVisualisation memoryVisualisation){
        if(memoryVisualisation != null){
            memoryVisualisation.clear();
        }
    }

    /**
     * Starts debugging the current code when the "Run" debug button is clicked
     */
    public void onClickRunDebug(){
        //TODO
    }

    /**
     * Stops the current debugging session when the "Stop" debug button is clicked
     */
    public void onClickStopDebug(){
        //TODO
    }

    /**
     * Executes the next instruction in the debugging session when the "Next" debug button is clicked
     */
    public void onClickNextDebug(){
        //TODO
    }
}
