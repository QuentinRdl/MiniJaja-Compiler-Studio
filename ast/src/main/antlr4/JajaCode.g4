grammar JajaCode;

@header{
package fr.ufrst.m1info.pvm.group5;
import fr.ufrst.m1info.pvm.group5.ast.instructions.*;
import fr.ufrst.m1info.pvm.group5.memory.Value;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.DataType;
import fr.ufrst.m1info.pvm.group5.memory.symbol_table.EntryKind;
import java.util.ArrayList;
import java.util.List;
}

@members{
List<Instruction> instrList = new ArrayList();
}

eval returns [List<Instruction> instructions]
    @init{$instructions = instrList;}
    : (instr)+ EOF
    ;

instr
    : instruction {instrList.add($instruction.inst); $instruction.inst.setLine($instruction.start.getLine());}
    ;

instruction returns [Instruction inst]
    : 'push' '(' valeur ')' {$inst = new PushInstruction($valeur.v);}
    | 'pop' {$inst = new PopInstruction();}
    | 'swap' {$inst = new SwapInstruction();}
    | 'return' {$inst = new ReturnInstruction();}
    | 'goto' '(' n=NOMBRE ')' {$inst = new GotoInstruction(Integer.parseInt($n.text));}
    | 'nop' {$inst = new NopInstruction();}
    | 'load' '(' i=IDENTIFIER ')' {$inst = new LoadInstruction($i.text);}
    | 'store' '(' i=IDENTIFIER ')' {$inst = new StoreInstruction($i.text);}
    | 'add' {$inst = new AddInstruction();}
    | 'mul' {$inst = new MulInstruction();}
    | 'sub' {$inst = new SubInstruction();}
    | 'div' {$inst = new DivInstruction();}
    | 'or' {$inst = new OrInstruction();}
    | 'and' {$inst = new AndInstruction();}
    | 'sup' {$inst = new SupInstruction();}
    | 'cmp' {$inst = new CmpInstruction();}
    | 'neg' {$inst = new NegInstruction();}
    | 'not' {$inst = new NotInstruction();}
    | 'inc' '(' i=IDENTIFIER ')' {$inst = new IncInstruction($i.text);}
    | 'invoke' '(' i=IDENTIFIER ')' {$inst = new InvokeInstruction($i.text);}
    | 'write' {$inst = new WriteInstruction();}
    | 'writeln' {$inst = new WritelnInstruction();}
    | 'init' {$inst = new InitInstruction();}
    | 'jcstop' {$inst = new JcstopInstruction();}
    | 'if' '(' n=NOMBRE ')' {$inst = new IfInstruction(Integer.parseInt($n.text));}
    | 'new' '(' id=IDENTIFIER ',' type ',' entrykind ',' scope=NOMBRE ')' {$inst = new NewInstruction($id.text,$type.dt,$entrykind.ek,Integer.parseInt($scope.text));}
    | 'newarray' '(' id=IDENTIFIER ',' type ')' {$inst = new NewarrayInstruction($id.text,$type.dt);}
    ;

valeur returns [Value v]
    : nombre  {$v = new Value($nombre.i);}
    | string  {$v = new Value($string.str);}
    | jcboolean  {$v = new Value($jcboolean.b);}
    ;

nombre returns [int i]
    : n=NOMBRE {$i = Integer.parseInt($n.text);}
    ;

string returns [String str]
    : t=STRING {$str = $t.text.substring(1, $t.text.length() - 1);}
    ;

jcboolean returns [boolean b]
    : 'jcvrai' {$b = true;}
    | 'jcfaux' {$b = false;}
    ;

type returns [DataType dt]
     : 'INT' {$dt = DataType.INT;}
     | 'BOOL' {$dt = DataType.BOOL;}
     ;

entrykind returns [EntryKind ek]
     : 'var' {$ek = EntryKind.VARIABLE;}
     | 'cst' {$ek = EntryKind.CONSTANT;}
     | 'meth' {$ek = EntryKind.METHOD;}
     ;

IDENTIFIER
    : ('_' | 'a'..'z' | 'A'..'Z')+ ('_' | 'a'..'z' | 'A'..'Z' | '0'..'9')*
    ;

NOMBRE
    : ('0'..'9')+
    ;

STRING
    : '"' .+? '"'
    ;

WS
    :   (' ' | '\t' | '\r'| '\n') -> skip
    ;
