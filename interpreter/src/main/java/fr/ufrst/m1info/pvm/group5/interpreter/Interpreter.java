package fr.ufrst.m1info.pvm.group5.interpreter;

import fr.ufrst.m1info.pvm.group5.ast.InterpretationMode;
import fr.ufrst.m1info.pvm.group5.memory.Event;

import java.util.List;

public interface Interpreter {
    public Event<InterpretationHaltedData> interpretationHaltedEvent = new Event<>();

    /**
     * Defines the breakpoints for the interpretation
     *
     * @param breakpoints list of breakpoints to use
     */
    void setBreakpoints(List<Integer> breakpoints);

    /**
     * Interpret the given code
     *
     * @param code the code we want to interpret
     * @return the error message
     */
    String interpretCode(String code);

    /**
     * Asynchronously starts the interpretation in a given mode
     *
     * @param code the code we want to interpret
     * @param mode interpretation mode
     * @return error messages
     */
    String startCodeInterpretation(String code, InterpretationMode mode);

    /**
     * Interpret the given file
     *
     * @param path the path of the file we want to interpret
     * @return the error message
     */
    String interpretFile(String path);

    /**
     * Asynchronously starts the interpretation the given file in a given mode
     *
     * @param path path to the file we want to interpret
     * @param mode interpretation mode
     * @return error messages
     */
     String startFileInterpretation(String path, InterpretationMode mode);

    /**
     * Stops the current interpretation
     */
    void stopInterpretation();

    /**
     * Resumes the current interpretation in the given mode
     *
     * @param mode interpretation mode to resume the intrepretation into
     */
    void resumeInterpretation(InterpretationMode mode);

    /**
     * Waits for the current interpretation to halt
     */
    void waitInterpretation();
}
