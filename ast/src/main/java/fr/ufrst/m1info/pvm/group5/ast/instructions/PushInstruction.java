package fr.ufrst.m1info.pvm.group5.ast.instructions;

import fr.ufrst.m1info.pvm.group5.ast.MemoryCallUtil;
import fr.ufrst.m1info.pvm.group5.memory.Memory;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.EntryKind;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;

public class PushInstruction extends Instruction {
    Value v;

    public PushInstruction(Value v){
        this.v = v;
    }

    public Value getV() {
        return v;
    }

    @Override
    public int execute(int address, Memory m) {
        MemoryCallUtil.safeCall(() -> m.push(".", this.v, ValueType.toDataType(v.type), EntryKind.CONSTANT), this);
        return address+1;
    }

    public String toString() { return "push"; }
}
