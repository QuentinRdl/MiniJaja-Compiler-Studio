package fr.ufrst.m1info.gl.compGL;
import java.util.*;

/**
 * Represents a singleexecution frame in the interpreter.
 * Each frame contains the different local variables, and a reference to the parent frame (if it exists)
 */

public class Frame {
    private Map<String, Object> localVariables; // Local variables on this scope // TODO : Replace w/ theo's hashmap
    private Frame parent; // Parent of the current frame

    private final String funcName; // Name of the func // TODO : Keep it ?
    private final int callLine; // Line of code that called the func // TODO : Keep it ?


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
    public void setVar(String name, Object value) {
        // TODO : Should we check the Object type ?
       localVariables.put(name, value);
    }

    /**
     * @param name Name of the Object
     * @return Object if found, null otherwise
     */
    public Object getVar(String name) {
        // TODO : Should we check the Object type ?
        if(localVariables.containsKey(name)) {
           return localVariables.get(name);
        }

        // Checks the parent frame, to see if the Object is found there // TODO : Is it needed ? Check the name of what it is actually called
        if(parent != null) {
            return parent.getVar(name);
        }

        return null; // No Object with the given name was found
    }

    public boolean hasVar(String name) {
        boolean ret = false;
        ret = localVariables.containsKey(name);

        if(!ret) {
            // Child does not have it, check the parent
            if(parent != null) ret = parent.hasVar(name);
        }

        return ret;
    }

    public boolean hasLocalVar(String name) {
        return localVariables.containsKey(name);
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

    /**
     * @param name Name of the var
     * @param value new value of the var
     *
     * @return true if var was found and updated, false otherwise
     */
    public boolean updateVar(String name, Object value) {
        if(localVariables.containsKey(name)) {
            // Check in self
            localVariables.put(name, value);
            return true;
        }

        // Check in parent
        if(parent != null) return parent.updateVar(name, value);

        // No var with the name 'name' found
        return false;
    }

    // TODO : ToString func
}
