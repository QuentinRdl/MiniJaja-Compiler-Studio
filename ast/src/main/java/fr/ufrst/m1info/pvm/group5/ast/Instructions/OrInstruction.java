package fr.ufrst.m1info.pvm.group5.ast.Instructions;
import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidTypeException;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.DataType;
import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.EntryKind;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;

public class OrInstruction extends Instruction {
    @Override
    public int execute(int address, Memory m) {
        Value v2 = (Value) m.pop(); //first pop => right operand
        Value v1 = (Value) m.pop(); //second pop => left operand
        if (v1.Type != ValueType.BOOL || v2.Type != ValueType.BOOL){
            throw new ASTInvalidTypeException("Type error: OR operator expects two BOOL operands, but received " + v1.Type + " and " + v2.Type + ".");
        }
        boolean res = v1.valueBool || v2.valueBool;
        Value vres = new Value(res);
        m.push(".", vres, DataType.BOOL, EntryKind.CONSTANT);
        return address+1;
    }
}