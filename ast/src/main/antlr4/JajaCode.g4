grammar JajaCode;

@header{
package fr.ufrst.m1info.pvm.group5;
import fr.ufrst.m1info.pvm.group5.ast.Instructions.*;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.DataType;
import fr.ufrst.m1info.pvm.group5.memory.SymbolTable.EntryKind;
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
    | 'write' {instrList.add(new WriteInstruction());}
    | 'writeln' {instrList.add(new WritelnInstruction());}
    | 'init' {instrList.add(new InitInstruction());}
    | 'jcstop' {instrList.add(new JcstopInstruction());}
    | 'if' '(' n=NOMBRE ')' {instrList.add(new IfInstruction(Integer.parseInt($n.text)));}
    | 'new' '(' id=IDENTIFIER ',' type ',' entrykind ',' scope=NOMBRE ')' {instrList.add(new NewInstruction($id.text,$type.dt,$entrykind.ek,Integer.parseInt($scope.text)));}
    ;

valeur returns [Value v]
    :
    ;

type returns [DataType dt]
     : 'INT' {$dt = DataType.INT;}
     | 'BOOL' {$dt = DataType.BOOL;}
     ;

entrykind returns [EntryKind ek]
     : 'var' {$ek = EntryKind.VARIABLE;}
     | 'cst' {$ek = EntryKind.CONSTANT;}
     ;

IDENTIFIER
    : ('_' | 'a'..'z' | 'A'..'Z')+ ('_' | 'a'..'z' | 'A'..'Z' | '0'..'9')*
    ;

NOMBRE
    : ('0'..'9')+
    ;
