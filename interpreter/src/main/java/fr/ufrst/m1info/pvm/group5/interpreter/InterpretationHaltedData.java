package fr.ufrst.m1info.pvm.group5.interpreter;

/**
 * Record serving as a data storage called by the InterpretationHalted event
 * @param isPursuable Indicates if the interpretation can be resumed. True if it can be, false otherwise
 *                    The interpretation cannot be able to be resumed if there is no more code to interpret,
 *                    or if an error occurred.
 * @param error Text of the error that occurred. Is null if no error occurred.
 * @param line Line at which the interpretation stopped.
 */
public record InterpretationHaltedData(boolean isPursuable, String error, int line) { }