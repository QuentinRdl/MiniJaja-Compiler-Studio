grammar JajaCode;

@header{
package fr.ufrst.m1info.pvm.group5;
import fr.ufrst.m1info.pvm.group5.ast.Instructions.*;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import java.util.ArrayList;
import java.util.List;
}

@members{
List<Instruction> instrList = new ArrayList();
}

eval returns [List<Instruction> instrs]
    @init{$instrs = instrList;}
    : instr
    ;

instr
    : 'push' '(' valeur ')' {instrList.add(new PushInstruction($valeur.v));}
    ;

valeur returns [Value v]
    :
    ;
