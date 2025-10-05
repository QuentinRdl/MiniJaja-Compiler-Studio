package fr.ufrst.m1info.gl.compGL;
import java.util.*;

/**
 * Represents a singleexecution frame in the interpreter.
 * Each frame contains the different local variables, and a reference to the parent frame (if it exists)
 */

public class Frame {
    private Map<String, Object> localVariables; // Local variables on this scope
    private Frame parent; // Parent of the current frame

    private String funcName; // Name of the func // TODO : Keep it ?
    private int callLine; // Line of code that called the func // TODO : Keep it ?


    /**
     * Constructor for a Frame object
     * @param funcName Name of the func
     * @param callLine Line number where the frame was created
     * @param parent Parent frame
     */
    public Frame(String funcName, int callLine, Frame parent) {
       this.funcName = funcName;
       this.callLine = callLine;
       this.parent = parent;
       this.localVariables = new HashMap<>();
    }

    /**
     * @param name Name of the Object
     * @param value The actual Object
     */
    public void setVariable(String name, Object value) {
        // TODO : Should we check the Object type ?
       localVariables.put(name, value);
    }

    /**
     * @param name Name of the Object
     * @return Object if found, null otherwise
     */
    public Object getVariable(String name) {
        // TODO : Should we check the Object type ?
        if(localVariables.containsKey(name)) {
           return localVariables.get(name);
        }

        // Checks the parent frame, to see if the Object is found there // TODO : Is it needed ? Check the name of what it is actually called
        if(parent != null) {
            return parent.getVariable(name);
        }

        return null; // No Object with the given name was found
    }

    // All the following funcs do not contain a set func, because they mustn't be changed after their creation

    /**
     * @return the parent of the current Frame (null if not any)
     */
   public Frame getParent()  {
       return parent;
   }

    /**
     * @return name of the current func name
     */
   public String getFuncName() {
      return funcName;
   }

    /**
     * @return line number where the frame was created
     */
   public int getCallLine() {
      return callLine;
   }

    /**
     * @return the map of the localVariables
     */
   public Map<String, Object> getLocalVariables() {
       return new HashMap<>(localVariables); // TODO : Replace w/ the HashMap of Theo
   }

}
