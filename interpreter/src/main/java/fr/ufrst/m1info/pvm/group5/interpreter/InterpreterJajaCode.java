package fr.ufrst.m1info.pvm.group5.interpreter;


import fr.ufrst.m1info.pvm.group5.ast.Jajacode;
import fr.ufrst.m1info.pvm.group5.ast.instructions.Instruction;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.Writer;

import java.util.List;

public class InterpreterJajaCode implements Interpreter{
    Writer output;

    protected InterpreterJajaCode(){
        output = new Writer();
    }

    public InterpreterJajaCode(Writer output) {
        this.output = output;
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
            try{
                adress = instructions.get(adress-1).execute(adress,mem);
            } catch (Exception e) {
                throw new RuntimeException("Line "+adress);
            }

            if (adress>size){
                throw new IndexOutOfBoundsException("Line "+adress+" not found");
            }
        }
    }
}
