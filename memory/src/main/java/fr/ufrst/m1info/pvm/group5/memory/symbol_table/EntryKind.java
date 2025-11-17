package fr.ufrst.m1info.pvm.group5.memory.symbol_table;

/**
 * Enumeration representing the kind of entry in the symbol table.
 * An entry can represent different elements such as a variable,
 * method, constant, class, or any other kind of symbol.
 */
public enum EntryKind {
    /** A variable entry */
    VARIABLE,

    /** A method entry */
    METHOD,

    /** A constant entry */
    CONSTANT,

    /** A class entry */
    CLASS,

    /** Any other kind of symbol */
    OTHER
}
