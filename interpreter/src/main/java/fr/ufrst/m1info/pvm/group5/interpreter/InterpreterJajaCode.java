package fr.ufrst.m1info.pvm.group5.interpreter;


import fr.ufrst.m1info.pvm.group5.ast.InterpretationMode;
import fr.ufrst.m1info.pvm.group5.ast.Jajacode;
import fr.ufrst.m1info.pvm.group5.ast.instructions.Instruction;
import fr.ufrst.m1info.pvm.group5.ast.instructions.JcstopInstruction;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Writer;

import java.util.ArrayList;
import java.util.List;

public class InterpreterJajaCode extends Interpreter{
    Writer output;
    List<Integer> breakpoints = new ArrayList<>();
    Thread interpretationThread;
    Instruction currentInstruction;
    InterpretationMode mode;

    protected InterpreterJajaCode(){
        output = new Writer();
    }

    public InterpreterJajaCode(Writer output) {
        this.output = output;
    }

    @Override
    public void setBreakpoints(List<Integer> breakpoints) {
        this.breakpoints = breakpoints;
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
            List<Instruction> jjc = Jajacode.fromString(code);
            interpretJajaCode(jjc);
        } catch (Exception e) {
            errMessage=e.getClass()+" : "+e.getMessage();
        }
        return errMessage;
    }

    @Override
    public String startCodeInterpretation(String code, InterpretationMode mode) {
        String errMessage= null;
        List<Instruction> jjc = new ArrayList<>();
        // Generating instructions list
        try{
            jjc = Jajacode.fromString(code);
        } catch (Exception e) {
            errMessage=e.getClass()+" : "+e.getMessage();
        }

        // Starting the thread
        startInterpretation(jjc);

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
            List<Instruction> jjc = Jajacode.fromFile(path);
            interpretJajaCode(jjc);
        } catch (Exception e) {
            errMessage=e.getClass()+" : "+e.getMessage();
        }
        return errMessage;
    }

    @Override
    public String startFileInterpretation(String path, InterpretationMode mode) {
        String errMessage= null;
        List<Instruction> jjc = new ArrayList<>();
        // Generating instructions list
        try{
            jjc = Jajacode.fromFile(path);
        } catch (Exception e) {
            errMessage=e.getClass()+" : "+e.getMessage();
        }

        // Starting the thread
        startInterpretation(jjc);

        return errMessage;
    }

    @Override
    public void stopInterpretation() {
        if(interpretationThread==null || !interpretationThread.isAlive())
            return;
        interpretationThread.interrupt();
    }

    @Override
    public void resumeInterpretation(InterpretationMode mode) {
        if(interpretationThread==null || !interpretationThread.isAlive())
            return;
        currentInstruction.notify();
    }

    @Override
    public void waitInterpretation() {
        if(interpretationThread==null || !interpretationThread.isAlive())
            return;
        try {
            interpretationThread.join();
        }catch (InterruptedException _){}
    }

    /**
     * Interpret a list of JajaCode instruction
     *
     * @param instructions the list of JajaCode instruction
     */
    public void interpretJajaCode(List<Instruction> instructions){
        Memory mem = new Memory(output);
        int adress=1;
        int size=instructions.size();
        while (adress>0){
            adress = instructions.get(adress-1).execute(adress,mem);
            if (adress>size || adress==0){
                throw new IndexOutOfBoundsException("Line "+adress+" not found");
            }
        }
    }

    private void startInterpretation(List<Instruction> instructions){
        Memory mem = new Memory(output);
        int[] address = {1}; // It is an array because Java.
        mem.setBreakpoints(breakpoints);

        // Creating the Thread
        interpretationThread = new Thread(() -> {
           while(currentInstruction==null || (!(currentInstruction instanceof JcstopInstruction))){
               if(address[0]>instructions.size() || address[0]<=0){
                    interpretationHaltedEvent.triggerAsync(new InterpretationHaltedData(false, "Address "+address[0]+" not found"));
                    return;
               }
               currentInstruction = instructions.get(address[0]);
               if(mode == InterpretationMode.STEP_BY_STEP || (
                       mode == InterpretationMode.BREAKPOINTS && mem.isBreakpoint(currentInstruction.getLine()))){
                   interpretationHaltedEvent.triggerAsync(new InterpretationHaltedData(true, null));
                   try{
                       currentInstruction.wait();
                   }catch (InterruptedException e){
                       interpretationHaltedEvent.triggerAsync(new InterpretationHaltedData(false, e.getMessage()));
                       return;
                   }
               }
               address[0] = currentInstruction.execute(address[0], mem);
           }
           interpretationHaltedEvent.triggerAsync(new InterpretationHaltedData(false, null));
        });

        // Starting the thread
        interpretationThread.start();
    }
}
