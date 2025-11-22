package fr.ufrst.m1info.pvm.group5.ast.nodes;

import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidDynamicTypeException;
import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidMemoryException;
import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidOperationException;
import fr.ufrst.m1info.pvm.group5.memory.Memory;

import java.util.List;
import java.util.Map;

// Useless comment so i can commit something

public abstract class ASTNode {
    /**
     * Line of the token in the file
     */
    private int line;

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
     * @throws ASTInvalidDynamicTypeException if the type is invalid
     */
    public abstract String checkType(Memory m) throws ASTInvalidDynamicTypeException;

    /**
     * Get the children of the node within a list
     * @return List of the children of the node
     */
    protected abstract List<ASTNode>getChildren();

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
}
