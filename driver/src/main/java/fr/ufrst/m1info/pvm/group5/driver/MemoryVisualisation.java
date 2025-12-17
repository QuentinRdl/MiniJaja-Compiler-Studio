package fr.ufrst.m1info.pvm.group5.driver;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

/**
 * Component to visually display the memory state (stack and heap) in the UI
 * Provides methods to update and clear the visualisation
 */
public class MemoryVisualisation extends GridPane {

    private VBox stackContainer;
    private VBox heapContainer;
    private Label stackLabel;
    private Label heapLabel;


    /**
     * Initializes the memory visualisation UI with separate sections for stack and heap
     */
    public MemoryVisualisation() {
        super();
        setPadding(new Insets(15));
        setHgap(20);

        ColumnConstraints colStack = new ColumnConstraints();
        colStack.setHgrow(Priority.ALWAYS);
        colStack.setFillWidth(true);
        colStack.setPercentWidth(50);

        ColumnConstraints colHeap = new ColumnConstraints();
        colHeap.setHgrow(Priority.ALWAYS);
        colHeap.setFillWidth(true);
        colHeap.setPercentWidth(50);

        getColumnConstraints().addAll(colStack, colHeap);

        // Stack
        VBox stackSection = new VBox(10);

        stackLabel = new Label("Stack");
        stackLabel.setFont(Font.font("System", 14));
        stackLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");

        stackContainer = new VBox(10);
        stackContainer.setPadding(new Insets(10));
        stackContainer.setStyle("-fx-background-color: #1d1d1d;");

        ScrollPane stackScrollPane = new ScrollPane(stackContainer);
        stackScrollPane.setFitToWidth(true);
        stackScrollPane.setFitToHeight(true);
        stackScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        stackScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        stackSection.getChildren().addAll(stackLabel, stackScrollPane);
        stackSection.setAlignment(Pos.TOP_CENTER);

        // Heap
        VBox heapSection = new VBox(10);

        heapLabel = new Label("Heap");
        heapLabel.setFont(Font.font("System", 14));
        heapLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");

        heapContainer = new VBox(10);
        heapContainer.setPadding(new Insets(10));
        heapContainer.setStyle("-fx-background-color: #1d1d1d;");

        ScrollPane heapScrollPane = new ScrollPane(heapContainer);
        heapScrollPane.setFitToWidth(true);
        heapScrollPane.setFitToHeight(true);
        heapScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        heapScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        heapSection.getChildren().addAll(heapLabel, heapScrollPane);
        heapSection.setAlignment(Pos.TOP_CENTER);

        VBox.setVgrow(stackScrollPane, Priority.ALWAYS);
        VBox.setVgrow(heapScrollPane, Priority.ALWAYS);

        add(stackSection, 0, 0);
        add(heapSection, 1, 0);

    }

    /**
     * Updates the memory visualization with current stack and heap content
     *
     * @param memory array with heap at index 0 and stack at index 1
     */
    public void updateMemory(String[] memory){
        if(memory == null || memory.length < 2) return;

        Platform.runLater(() -> {
            String heap = memory[0];
            String stack = memory[1];

            //TODO: replace with actual values (step-by-step implementation)

            /*
            String stackExample = "Stack{scopeDepth=0, size=1, contents=\n" +
                    "  [0] arr_0 \tkind=VARIABLE \tdataType=INT \tvalue=Integer(1)\n" +
                    "  [1] alias_0\tkind=VARIABLE \tdataType=INT\tvalue=Integer(10)\n" +
                    "  [2] arr_1 \tkind=VARIABLE \tdataType=INT \tvalue=Integer(1)\n" +
                    "  [1] alias_1\tkind=VARIABLE \tdataType=INT\tvalue=Integer(10)\n" +
                    "}";

            String heapExample = "Heap(total=16, available=11)\n" +
                    "  ext@1 int@0 size=5 Allocated(INT) refs=1\n" +
                    "    bytes: [0, 0, 0, 0, 0]\n" +
                    "* ext@0 int@5 size=11 Free refs=0\n" +
                    "    bytes: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]";
             */
            updateStack(stack);
            updateHeap(heap);

        });
    }

    /**
     * Updates the stack section of the memory visualisation
     *
     * @param stack string representation of the stack
     */
    private void updateStack(String stack){
        stackContainer.getChildren().clear();

        if(stack == null || stack.trim().isEmpty()){
            stackContainer.getChildren().add(new Label("Stack is empty"));
            return;
        }

        // Split stack string into individual lines
        String[] lines = stack.split("\n");

        // Find the line that contains "contents=" to identify where stack entries start
        int contentsIndex = -1;
        for(int i = 0; i < lines.length; i++){
            if(lines[i].contains("contents=")){
                contentsIndex = i;
                break;
            }
        }

        // If "contents=" line is missing, the format is invalid
        //TODO: modify to display in the console ?
        if(contentsIndex == -1){
            stackContainer.getChildren().add(new Label("Stack format error"));
            return;
        }

        if(isStackContentEmpty(lines, contentsIndex)){
            stackContainer.getChildren().add(new Label("Stack is empty"));
            return;
        }

        // Parse each line containing a variable and create a visual block
        for(String line : lines){
            if(line.contains("kind=")){ // Only process lines with variable information
                String name = extract(line, "]", "scope=");
                int scope = extractInt(line, "scope=", "dataType=");
                String kind = extract(line, "kind=", "dataType=");
                String type = extract(line, "dataType=", "value=");
                String value = extract(line, "value=", "");
                String valueContent = extractValueContent(value);

                // Add a StackBlockView at the top of the stack container
                stackContainer.getChildren().add(new StackBlockView(name, kind, scope, type, valueContent));
            }
        }
    }

    /**
     * Updates the heap section of the memory visualisation
     *
     * @param heap string representation of the heap
     */
    private void updateHeap(String heap){
        heapContainer.getChildren().clear();

        if(heap == null || heap.trim().isEmpty()){
            heapContainer.getChildren().add(new Label("Heap is empty"));
            return;
        }

        // Split heap string into individual lines
        String[] lines = heap.split("\n");

        // Ignore the first line (general heap info like total/available memory)
        for(int i = 1; i < lines.length; i++){
            String line = lines[i].trim();

            if(line.isEmpty()) continue; // Skip empty lines

            if(line.contains("data:")){
                String data = line.replace("data:", "").trim();
                HeapBlockView lastBlock = (HeapBlockView) heapContainer.getChildren().getLast();
                lastBlock.setDataLabel(data); // Update the data label of the last block
            } else {
                int address = extractInt(line, "ext@", "int@");
                int size = extractInt(line, "size=", (line.contains("Allocated") ? "Allocated" : "Free"));
                String allocated = extract(line, Integer.toString(size), "refs=");
                int refs = extractInt(line, "refs=", "");

                HeapBlockView block = new HeapBlockView(address, size, allocated, refs, "");
                heapContainer.getChildren().add(block);
            }
        }
    }

    /**
     * Extracts a substring between start and end markers from the given text
     *
     * @param text the input string
     * @param start the starting marker
     * @param end the ending marker
     * @return the extracted substring
     */
    private String extract(String text, String start, String end){
        if(text == null || start == null || end == null) return "";
        
        int startIndex = text.indexOf(start);
        if(startIndex == -1) return "";

        startIndex += start.length();

        int endIndex = end.isEmpty() ? -1 : text.indexOf(end, startIndex);
        if(endIndex == -1) return text.substring(startIndex).trim();

        return text.substring(startIndex,endIndex).trim();
    }

    /**
     * Extracts an integer value between start and end markers from the given text
     *
     * @param text the input string
     * @param start the starting marker
     * @param end the ending marker
     * @return the extracted integer
     */
    private int extractInt(String text, String start, String end){
        try{
            return Integer.parseInt(extract(text, start, end));
        }catch (Exception e){
            return 0;
        }
    }

    private String extractValueContent(String text){
        if(text.equals("null")){
            return "null";
        }

        int start = text.indexOf('{');
        int end = text.indexOf('}');

        if(start != -1 && end != -1 && end > start){
            return text.substring(start + 1, end).trim();
        }

        return "";
    }

    /**
     * Checks if the stack content is empty based on the parsed lines
     *
     * @param lines all lines of the stack representation
     * @param contentLineIndex index of the line containing "contents="
     * @return true if the stack has no content, false otherwise
     */
    private boolean isStackContentEmpty(String[] lines, int contentLineIndex){
        for(int i = contentLineIndex + 1; i < lines.length; i++){
            String line = lines[i].trim();
            if(line.equals("}")){
                return true;
            }
            if(!line.isEmpty()){
                return false;
            }
        }
        return true;
    }

    /**
     * Clears both stack and heap visualisations
     */
    public void clear(){
        Platform.runLater(() -> {
            clearStack();
            clearHeap();
        });
    }

    /**
     * Clears the heap visualisation
     */
    private void clearHeap(){
        heapContainer.getChildren().clear();
    }

    /**
     * Clears the stack visualisation
     */
    private void clearStack(){
        stackContainer.getChildren().clear();
    }
}
