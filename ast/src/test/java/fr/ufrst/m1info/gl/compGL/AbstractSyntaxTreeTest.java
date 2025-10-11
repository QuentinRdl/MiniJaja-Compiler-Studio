package fr.ufrst.m1info.gl.compGL;

import fr.ufrst.m1info.gl.compGL.Memory.Memory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.ArgumentMatchers;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

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

    @Test
    @DisplayName("Evaluation - BasicOperations")
    public void BasicOperations() throws Exception {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromFile("src/test/resources/BasicOperations.mjj");
        Memory mock = mock();
        List<Integer> values =  new ArrayList<>();
        doAnswer(invocation -> {
                Value value = invocation.getArgument(1);
                values.add(value.valueInt);
                return null;
            }
        ).when(mock).affectValue(any(String.class), any(Object.class));

        AST.interpret(mock);

        assertEquals(1, values.size());
        assertEquals(7, values.get(0));
    }
}
