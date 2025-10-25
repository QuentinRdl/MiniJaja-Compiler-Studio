package fr.ufrst.m1info.pvm.group5.memory;

import java.io.Serial;

/**
 * Thrown when attempting to construct a Stack_Object using the generic constructor
 * for kinds that require a specialized constructor (rn only vars and csts)
 */
public class InvalidStackObjectConstructionException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidStackObjectConstructionException(String message) {
        super(message);
    }

    /*
    public InvalidStackObjectConstructionException(String message, Throwable cause) {
        super(message, cause);
    }
    TODO : can we remove ?
     */
}

