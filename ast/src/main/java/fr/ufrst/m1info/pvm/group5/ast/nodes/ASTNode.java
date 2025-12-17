package fr.ufrst.m1info.pvm.group5.ast.nodes;

import fr.ufrst.m1info.pvm.group5.ast.*;
import fr.ufrst.m1info.pvm.group5.memory.Event;
import fr.ufrst.m1info.pvm.group5.memory.Memory;

import java.util.List;
import java.util.Map;

public abstract class ASTNode implements LocatedElement {
    /**
     * Line of the token in the file
     */
    private int line;

    private ASTNode root = null;

    private static InterpretationMode interpretationMode = InterpretationMode.DIRECT;

    private Event<InterpretationStoppedData> InterpretationStoppedEvent = null;

    // Root relative methods

    public ASTNode getRoot(){
        return root;
    }

    public void setAsRoot(){
        this.root = null;
        this.InterpretationStoppedEvent = new Event<>();
        for(ASTNode child : getChildren()){
            child.setRoot(this);
        }
    }

    private void setRoot(ASTNode root){
        this.root = root;
        this.InterpretationStoppedEvent = null;
        for(ASTNode child : getChildren()){
            child.setRoot(root);
        }
    }

    /**
     * Getter for interpretationStoppedEvent of the root of the tree the node is part of
     * If no root has been set, an exception is thrown
     * @return current tree interpretation stopped event
     */
    public Event<InterpretationStoppedData> interpretationStoppedEvent(){
        if(this.InterpretationStoppedEvent == null){
            if(this.root == null){
                throw new RuntimeException("line "+this.line + " - " + this.getClass().getSimpleName() + " : No root node has been set for the current tree");
            }
            return root.interpretationStoppedEvent();
        }
        return this.InterpretationStoppedEvent;
    }

    // Interpretation

    /**
     * Defines the way the interpretation of the program should be done
     * @param interpretationMode interpretation mode of the program
     */
    public void setInterpretationMode(InterpretationMode interpretationMode) {
        ASTNode.interpretationMode = interpretationMode;
    }

    // Getters/setter for the line attribute
    public void setLine(int line) {
        this.line = line;
    }

    public int getLine() {
        return line;
    }

    /**
     * Compile the node and its descendants
     * @return List of JaJaCodes compiled from it
     */
    public abstract List<String> compile(int address);

    /**
     * Interpret the node and it's descendants using a memory
     * @param m Memory used for the interpretation
     * @throws Exception throws an exception when an error occurs while trying to evaluate the node
     */
    public abstract void interpret(Memory m) throws ASTInvalidOperationException, ASTInvalidMemoryException;

    /**
     * Dynamically checks the type of this node and returns its type.
     * @return the evaluated type of the node (e.g., int, bool, string, void, etc.)
     * @throws InterpretationInvalidTypeException if the type is invalid
     */
    public abstract String checkType(Memory m) throws InterpretationInvalidTypeException;

    /**
     * Get the children of the node within a list
     * @return List of the children of the node
     */
    public abstract List<ASTNode>getChildren();

    /**
     * Prints non-children properties of the node
     * @param depth depth at which to print the properties of the node
     * @return properties of the node in the JSON format
     */
    protected String dumpProperties(int depth){
        Map<String,String> props = getProperties();
        StringBuilder sb = new StringBuilder();
        addTabDepth(sb,depth);
        sb.append("\"line\" : ").append(getLine());
        if(props!=null)sb.append(",\n");
        if(props==null) return sb.toString();
        var iterator = props.entrySet().iterator();
        while(iterator.hasNext()){
            var e = iterator.next();
            if (iterator.hasNext())
                sb.append(",");
            addTabDepth(sb, depth);
            sb.append("\"").append(e.getKey()).append("\" : \"").append(e.getValue()).append("\"");
        }
        return sb.toString();
    }

    /**
     * Gets the non-children properties of the node
     * @return non-children properties of the node
     */
    protected Map<String, String> getProperties(){
        return null;
    }

    /**
     * Utility method. Add depth \t at the end of the stringBuilder
     * @param sb stringBuilder to add the \t to
     * @param depth number of \t to add
     */
    protected void addTabDepth(StringBuilder sb,int depth){
        sb.append("\t".repeat(Math.max(0, depth)));
    }

    /**
     * Creates a version of the node in the JSON format starting at a certain depth
     * @return node formatted in JSON
     */
    public String dump(){
        StringBuilder sb = new StringBuilder();
        sb.append("{\n\t");
        sb.append("\"").append(getClass().getSimpleName()).append("\" : ");
        sb.append(dump(1));
        sb.append("\n}");
        return sb.toString();
    }

    /**
     * Creates a version of the node in the JSON format starting at a certain depth
     * @param depth depth to start at
     * @return node formatted in JSON
     */
    protected String dump(int depth){
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        var children = getChildren();
        var prop = dumpProperties(depth+1);
        if(!prop.isEmpty()) {
            sb.append(prop);
            if(children != null && !children.isEmpty()) sb.append(",");
            sb.append("\n");
        }
        if(children != null) {
            var itr = children.iterator();
            while(itr.hasNext()){
                ASTNode node = itr.next();
                addTabDepth(sb, depth + 1);
                sb.append("\"").append(node.getClass().getSimpleName()).append("\" : ");
                sb.append(node.dump(depth + 1));
                if(itr.hasNext()) sb.append(",");
                sb.append("\n");
            }
        }
        if(depth>0)
            addTabDepth(sb,depth);
        sb.append("}");
        return sb.toString();
    }

    /**
     * Utility function to call "wait" without having to use the "try/catch" everytime
     */
    private synchronized void doWait(){
        try{
            wait();
        }
        catch (InterruptedException e){
            Thread.currentThread().interrupt();
            throw new RuntimeException("Program interpretation interrupted");
        }
    }

    public synchronized void resumeInterpretation(){
        notifyAll();
    }

    /**
     * Halts the program or not depending on the interpretation mode :
     *  - Direct : the program is not interrupted
     *  - Breakpoints : the program is interrupted if a breakpoint is present on the current line
     *  - Step by step : the program is interrupted
     * Tiggers the [InterpretationStoppedEvent] if the program has stopped
     * @param m Memory the program is being executed on
     */
    protected synchronized void halt(Memory m){
        switch (interpretationMode){
            case DIRECT:
                return;
            case BREAKPOINTS:
                if(!m.isBreakpoint(this.line))
                    return;
            case STEP_BY_STEP:
                interpretationStoppedEvent().triggerAsync(new InterpretationStoppedData(line, false, this));
                doWait();
        }
    }
}
