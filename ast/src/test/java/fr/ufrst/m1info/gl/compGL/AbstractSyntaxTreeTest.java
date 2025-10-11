package fr.ufrst.m1info.gl.compGL;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AbstractSyntaxTreeTest {

    @Test
    @DisplayName("Construction - Simple")
    public void SimpleTree(){
        String input = "class C {"
                     + "  main{}"
                     + "}";
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromString(input);
    }

    @Test
    @DisplayName("Construction - From file, simple")
    public void FromFile() throws IOException {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromFile("src/test/resources/Simple.mjj");
    }

    @Test
    @DisplayName("Construction - Complex")
    public void ComplexTree() throws IOException {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromFile("src/test/resources/Complex.mjj");

    }
}
