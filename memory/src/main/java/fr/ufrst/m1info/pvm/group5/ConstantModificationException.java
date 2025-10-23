package fr.ufrst.m1info.pvm.group5;

/**
 * Thrown when attempting to modify a Stack_Object whose entry kind is CONSTANT.
 */
public class ConstantModificationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ConstantModificationException(String message) {
        super(message);
    }
}

