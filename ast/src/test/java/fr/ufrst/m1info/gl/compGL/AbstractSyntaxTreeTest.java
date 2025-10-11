package fr.ufrst.m1info.gl.compGL;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
}
