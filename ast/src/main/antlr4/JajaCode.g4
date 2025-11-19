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
    : (instr)+
    ;

instr
    : 'push' '(' valeur ')' {instrList.add(new PushInstruction($valeur.v));}
    | 'pop' {instrList.add(new PopInstruction());}
    | 'swap' {instrList.add(new SwapInstruction());}
    | 'return' {instrList.add(new ReturnInstruction());}
    | 'goto' '(' n=NOMBRE ')' {instrList.add(new GotoInstruction(Integer.parseInt($n.text)));}
    | 'nop' {instrList.add(new NopInstruction());}
    | 'load' '(' i=IDENTIFIER ')' {instrList.add(new LoadInstruction($i.text));}
    | 'store' '(' i=IDENTIFIER ')' {instrList.add(new StoreInstruction($i.text));}
    | 'add' {instrList.add(new AddInstruction());}
    | 'mul' {instrList.add(new MulInstruction());}
    | 'sub' {instrList.add(new SubInstruction());}
    | 'div' {instrList.add(new DivInstruction());}
    | 'or' {instrList.add(new OrInstruction());}
    | 'and' {instrList.add(new AndInstruction());}
    | 'sup' {instrList.add(new SupInstruction());}
    | 'cmp' {instrList.add(new CmpInstruction());}
    | 'neg' {instrList.add(new NegInstruction());}
    | 'not' {instrList.add(new NotInstruction());}
    | 'inc' '(' i=IDENTIFIER ')' {instrList.add(new IncInstruction($i.text));}{}
    | 'write' {instrList.add(new WriteInstruction());}
    | 'writeln' {instrList.add(new WritelnInstruction());}
    | 'init' {instrList.add(new InitInstruction());}
    | 'jcstop' {instrList.add(new JcstopInstruction());}
    | 'if' '(' n=NOMBRE ')' {instrList.add(new IfInstruction(Integer.parseInt($n.text)));}
    | 'new' '(' id=IDENTIFIER ',' type ',' entrykind ',' scope=NOMBRE ')' {instrList.add(new NewInstruction($id.text,$type.dt,$entrykind.ek,Integer.parseInt($scope.text)));}
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
