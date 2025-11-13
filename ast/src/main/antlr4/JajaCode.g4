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
    | 'pop' {instrList.add(new PopInstruction());}
    | 'swap' {instrList.add(new SwapInstruction());}
    | 'return' {instrList.add(new ReturnInstruction());}
    | 'goto' '(' n=NOMBRE ')' {instrList.add(new GotoInstruction(Integer.parseInt($n.text)));}
    | 'nop' {instrList.add(new NopInstruction());}
    | 'load' '(' string ')' {instrList.add(new LoadInstruction($string.str));}
    | 'store' '(' string ')' {instrList.add(new StoreInstruction($string.str));}
    | 'add' {instrList.add(new AddInstruction());}{}
    ;

valeur returns [Value v]
    :
    ;

string returns [String str]
    : t=STRING {$str = $t.text.substring(1, $t.text.length() - 1);}
    ;

NOMBRE
    : ('0'..'9')+
    ;

STRING
    : '"' .+? '"'
    ;
