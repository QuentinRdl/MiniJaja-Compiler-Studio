package fr.ufrst.m1info.pvm.group5.interpreter;

public interface InterpretationIterator {
    /**
     * Interpret the rest of the code, without stopping at any breakpoint
     */
    void fullyInterpret();

    /**
     * Interpret the code until a breakpoint is met
     */
    void nextBreakPoint();

    /**
     * Interprets the next instruction of the code
     */
    void nextStep();

    /**
     * Tests if any kind of interpretation can be done using the iterator
     * @return true if there is still code to interpret, false otherwise
     */
    boolean hasNextStep();

    /**
     * Waits for the current interpretation to complete
     */
    void waitInterpretation();
}