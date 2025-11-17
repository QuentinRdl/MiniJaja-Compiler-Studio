package fr.ufrst.m1info.pvm.group5.ast;

import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ASTDumpTest {

    @Disabled
    @Test
    void dumpToConsole() throws IOException {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromFile("src/test/resources/0.mjj");
        System.out.println(AST.dump());
    }

    @Test
    void Mjj0() throws IOException {
        AbstractSyntaxTree AST = AbstractSyntaxTree.fromFile("src/test/resources/0.mjj");
        AST.dumpToFile("out.txt");
        //assertTrue(FileUtils.contentEquals(new File("out.txt"), new File("src/test/resources/Oracles/0.json")));
    }

}
