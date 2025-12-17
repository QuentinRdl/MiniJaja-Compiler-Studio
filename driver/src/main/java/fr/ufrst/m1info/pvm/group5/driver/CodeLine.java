package fr.ufrst.m1info.pvm.group5.driver;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * The CodeLine class represents a single line of code in the program.
 * It stores both the line number and the actual code written on that line.
 * This class is mainly used to model each line that appears in the code editor or ListView
 */
public class CodeLine {
    private int lineNumber;
    private String code;
    private boolean breakpoint;
    private BooleanProperty currentDebugLine; //observable property that detects changes

    /**
     * Creates a new CodeLine with a specific line number and code content.
     *
     * @param lineNumber the number of the line in the source code
     * @param code       the text content of the line
     */
    public CodeLine(int lineNumber, String code){
        this.lineNumber = lineNumber;
        this.code = code;
        breakpoint = false;
        this.currentDebugLine = new SimpleBooleanProperty(false);
    }

    /**
     * Returns the line number of this code line.
     *
     * @return the line number
     */
    public int getLineNumber(){
        return lineNumber;
    }

    /**
     * Returns the text of this code line.
     *
     * @return the code content
     */
    public String getCode(){
        return code;
    }

    /**
     * Checks whether a breakpoint is set on this code line
     *
     * @return true if a breakpoint is set, false otherwise
     */
    public boolean isBreakpoint(){
        return breakpoint;
    }

    /**
     * Returns whether this code line is currently marked as the active debug line
     *
     * @return true if this line is the current debug line, false otherwise
     */
    public boolean isCurrentDebugLine() {
        return currentDebugLine.get();
    }

    /**
     * Sets the line number for this code line.
     *
     * @param lineNumber the new line number to assign to this code line
     */
    public void setLineNumber(int lineNumber){
        this.lineNumber = lineNumber;
    }

    /**
     * Updates the text content of this code line.
     *
     * @param code the new code to set
     */
    public void setCode(String code){
        this.code = code;
    }

    /**
     * Sets or clears a breakpoint on this code line
     *
     * @param breakpoint true to set a breakpoint, false to remove it
     */
    public void setBreakpoint(boolean breakpoint){
        this.breakpoint = breakpoint;
    }

    /**
     * Marks this code line as the current debug line or removes the marks
     *
     * @param currentDebugLine true to highlight this line during debugging, false to clear the highlight
     */
    public void setCurrentDebugLine(boolean currentDebugLine) { this.currentDebugLine.set(currentDebugLine); }

    /**
     * Returns the BooleanProperty indicating whether this line is the current line being executed in debug mode
     * This property can be observed to update the UI when the current debug line changes
     *
     * @return the currentDebugLine BooleanProperty
     */
    public BooleanProperty currentDebugLineProperty(){
        return currentDebugLine;
    }
}
