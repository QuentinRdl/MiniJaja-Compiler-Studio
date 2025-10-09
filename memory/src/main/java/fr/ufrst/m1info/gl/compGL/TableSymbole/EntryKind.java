package fr.ufrst.m1info.gl.compGL.TableSymbole;

/**
 * Enumeration representing the kind of entry in the symbol table.
 * An entry can represent different elements such as a variable,
 * method, constant, class, or any other kind of symbol.
 */
public enum EntryKind {
    /** A variable entry */
    VARIABLE,

    /** A method entry */
    METHODE,

    /** A constant entry */
    CONSTANTE,

    /** A class entry */
    CLASSE,

    /** Any other kind of symbol */
    AUTRE
}
