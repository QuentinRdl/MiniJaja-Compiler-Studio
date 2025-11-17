package fr.ufrst.m1info.pvm.group5.ast.instructions;

import fr.ufrst.m1info.pvm.group5.ast.ASTInvalidTypeException;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.DataType;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.EntryKind;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;

public class SubInstruction extends Instruction{
    @Override
    public int execute(int address, Memory m) {
        Value v2 = (Value) m.pop(); //first pop => right operand
        Value v1 = (Value) m.pop(); //second pop => left operand
        if (v1.Type != ValueType.INT || v2.Type != ValueType.INT){
            throw new ASTInvalidTypeException("Type error: sub instruction expects two INT operands, but received " + v1.Type + " and " + v2.Type + ".");
        }
        int res = v1.valueInt - v2.valueInt;
        Value vres = new Value(res);
        m.push(".", vres, DataType.INT, EntryKind.CONSTANT);
        return address+1;
    }
}
