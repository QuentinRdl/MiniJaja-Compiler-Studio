package fr.ufrst.m1info.pvm.group5.interpreter;

import fr.ufrst.m1info.pvm.group5.ast.InterpretationMode;
import fr.ufrst.m1info.pvm.group5.ast.nodes.ASTNode;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.ast.AbstractSyntaxTree;
import fr.ufrst.m1info.pvm.group5.memory.Writer;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;


public class InterpreterMiniJaja extends Interpreter{
    Writer output;
    AbstractSyntaxTree ast;
    Thread interpretationThread;
    ASTNode currentNode;
    private Memory mem;

    protected InterpreterMiniJaja(){
        output = new Writer();
        mem = new Memory(output);
    }

    public InterpreterMiniJaja(Writer output) {
        this.output = output;
        mem = new Memory(this.output);
    }

    @Override
    public void setBreakpoints(List<Integer> breakpoints) {
        mem.setBreakpoints(breakpoints);
    }

    /**
     * Interpret the given code
     *
     * @param code the code we want to interpret
     * @return the error message
     */
    @Override
    public String interpretCode(String code) {
        String errMessage= null;
        try{
            AbstractSyntaxTree ast = AbstractSyntaxTree.fromString(code);
            ast.interpret(mem);
        } catch (Exception e) {
            errMessage=e.getClass()+" : "+e.getMessage();
        }
        return errMessage;
    }

    @Override
    public String startCodeInterpretation(String code, InterpretationMode mode) {
        String errMessage= null;

        // Building the AST
        try {
             this.ast = AbstractSyntaxTree.fromString(code);
        } catch (Exception e){
            errMessage=e.getClass()+" : "+e.getMessage();
        }

        // Subscribing to the event of the ast
        ast.interpretationStoppedEvent.subscribe(d -> {
            this.currentNode = d.node();
            interpretationHaltedEvent.triggerAsync(new InterpretationHaltedData(true, null, (currentNode == null) ? -1 : currentNode.getLine()));
        });

        // Creating a secondary thread that will trigger an event once the interpretation stops
        Thread secondaryThread = new Thread(() -> {
            try {
                interpretationThread.join();
            } catch (InterruptedException _) { return; }
            interpretationHaltedEvent.triggerAsync(new InterpretationHaltedData(false, null, (currentNode == null) ? -1 : currentNode.getLine()));
        });

        // Creating the main thread
        interpretationThread = new Thread(() -> {
            try {
                ast.interpret(mem, mode);
            } catch (Exception e) {
                secondaryThread.interrupt();
                interpretationHaltedEvent.triggerAsync(
                        new InterpretationHaltedData(
                                false,
                                e.getMessage(),
                                (currentNode == null)?-1:currentNode.getLine())
                );
            }
        });


        // Starting the Thread
        interpretationThread.start();
        secondaryThread.start();
        return errMessage;
    }

    /**
     * Interpret the given file
     *
     * @param path the path of the file we want to interpret
     * @return the error message
     */
    @Override
    public String interpretFile(String path)  {
        String errMessage= null;
        try{
            AbstractSyntaxTree ast = AbstractSyntaxTree.fromFile(path);
            ast.interpret(mem);
        } catch (Exception e) {
            errMessage=e.getClass()+" : "+e.getMessage();
        }
        return errMessage;
    }

    public Memory getMemory(){
        return this.mem;
    }

    @Override
    public String startFileInterpretation(String path, InterpretationMode mode) {
        String fileContent;
        try {
            fileContent = FileUtils.readFileToString(new File(path), Charset.defaultCharset());
        } catch (IOException e) {
            return e.getMessage();
        }
        return startCodeInterpretation(fileContent, mode);
    }

    @Override
    public void stopInterpretation() {
        if(interpretationThread == null || !interpretationThread.isAlive())
            return;
        interpretationThread.interrupt();
    }

    @Override
    public void resumeInterpretation(InterpretationMode mode) {
        if(interpretationThread == null || !interpretationThread.isAlive())
            return;
        currentNode.resumeInterpretation();
    }

    @Override
    public void waitInterpretation() {
        if(interpretationThread == null || !interpretationThread.isAlive())
            return;
        try {
            interpretationThread.join();
        }catch (InterruptedException _){}
    }
}
