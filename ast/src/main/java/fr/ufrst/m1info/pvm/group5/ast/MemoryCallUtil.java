package fr.ufrst.m1info.pvm.group5.ast;

import fr.ufrst.m1info.pvm.group5.ast.instructions.Instruction;
import fr.ufrst.m1info.pvm.group5.ast.nodes.ASTNode;

import java.lang.reflect.Executable;
import java.util.function.Supplier;

public class MemoryCallUtil {
    private MemoryCallUtil() {}

    public static void safeCall(Runnable call, Instruction caller){
        try{
            call.run();
        }
        catch (Exception e){
            throw ASTInvalidMemoryException.InvalidMemoryOperation(caller, e.getMessage());
        }
    }

    public static void safeCall(Runnable call, ASTNode caller){
        try{
            call.run();
        }
        catch (Exception e){
            throw ASTInvalidMemoryException.InvalidMemoryOperation(caller, e.getMessage());
        }
    }

    public static <T> T safeCall(Supplier<T> call, ASTNode caller){
        T res;
        try{
            res = call.get();
        }
        catch (Exception e){
            throw ASTInvalidMemoryException.InvalidMemoryOperation(caller, e.getMessage());
        }
        return res;
    }

    public static <T> T safeCall(Supplier<T> call, Instruction caller){
        T res;
        try{
            res = call.get();
        }
        catch (Exception e){
            throw ASTInvalidMemoryException.InvalidMemoryOperation(caller, e.getMessage());
        }
        return res;
    }
}
