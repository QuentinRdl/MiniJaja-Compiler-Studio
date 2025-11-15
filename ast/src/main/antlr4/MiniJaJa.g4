grammar MiniJaJa;

@header{
package fr.ufrst.m1info.pvm.group5;
import fr.ufrst.m1info.pvm.group5.ast.Nodes.*;
import fr.ufrst.m1info.pvm.group5.memory.ValueType;
}

classe returns [ClassNode node]
    @init{boolean declsflag = false;}
    : 'class' ident '{'
    (decls {declsflag = true;}
    )?
    methmain '}' {$node = new ClassNode($ident.node, (declsflag)?$decls.node:null, $methmain.node);}
    ;

ident returns [IdentNode node]
    : id=IDENTIFIER {$node = new IdentNode($id.text);}
    ;

decls returns [ASTNode node]
    : decl ';' {$node = new DeclarationsNode($decl.node, null);}
    (decls {$node = new DeclarationsNode($decl.node, $decls.node);}
    )?
    ;

decl returns [ASTNode node]
    : var {$node = $var.node;}
    ;

vars returns [ASTNode node]
    : var ';' {$node = new VariablesNode($var.node, null);}
    (vars {$node = new VariablesNode($var.node, $vars.node);}
    )?
    ;

var returns [ASTNode node]
    : typemeth ident {$node = new VariableNode($typemeth.node, $ident.node, null);}
    (vexp {$node = new VariableNode($typemeth.node, $ident.node, $vexp.node);}
    )?
    | 'final' type ident {$node = new FinalNode($type.node, $ident.node, null);}
    (vexp {$node = new FinalNode($type.node, $ident.node, $vexp.node);}
    )?
    ;

vexp returns [ASTNode node]
     : '=' exp {$node = $exp.node;}
     ;

methode returns [ASTNode node]
    @init {
        boolean entetesFlag = false;
        boolean varsFlag = false;
        boolean instrsFlag = false;
    }
    : typemeth ident '('
        (entetes { entetesFlag = true; })?
      ')' '{'
        (vars { varsFlag = true; })?
        (instrs { instrsFlag = true; })?
      '}'
      {
          $node = new MethodeNode(
              $typemeth.node,
              $ident.node,
              (entetesFlag)?$entetes.node:null,
              (varsFlag)?$vars.node:null,
              (instrsFlag)?$instrs.node:null
          );
      }
    ;


methmain returns [MainNode node]
    @init{boolean varsflag = false; boolean instrsflag = false;}
    : 'main' '{'
    (vars {varsflag = true;}
    )?
    (instrs {instrsflag = true;}
    )?
    '}' {$node = new MainNode((varsflag)?$vars.node:null, (instrsflag)?$instrs.node:null);}
    ;

entete returns [ASTNode node]
    : type ident
      {
          $node = new ParamNode($type.node, $ident.node);
      }
    ;


entetes returns [ParamListNode node]
    : e1=entete {$node = new ParamListNode((ParamNode)$e1.node, null);}
      ( ',' e2=entetes {$node = new ParamListNode((ParamNode)$e1.node, $e2.node);} )?
    ;
instrs returns [InstructionsNode node]
    : instr ';' {$node = new InstructionsNode($instr.node, null);}
    (instrs {$node = new InstructionsNode($instr.node, $instrs.node);}
    )?
    ;

instr returns [ASTNode node]
    @init{boolean instrsflag1 = false; boolean instrsflag2 = false;}
    : 'while' '(' exp ')' '{'
    (instrs {instrsflag1 = true;}
    )?
    '}' {$node = new WhileNode($exp.node, (instrsflag1)?$instrs.node:null);}
    | 'return' exp {$node = new ReturnNode($exp.node);}
    | ident1 {$node = $ident1.node;}
    ( '=' exp {$node = new AffectationNode((IdentNode)$node, $exp.node);}
    | '+=' exp {$node = new SumNode((IdentNode)$node, $exp.node);}
    | '++' {$node = new IncNode((IdentNode)$node);}
    )
    | 'if' '(' exp ')' '{'
    (i1=instrs {instrsflag1 = true;}
    )?
    '}' {$node = new IfNode($exp.node,(instrsflag1)?$i1.node:null,null);}
    ('else' '{'
    (i2=instrs {instrsflag2 = true;}
    )?
    '}' {$node = new IfNode($exp.node,(instrsflag1)?$i1.node:null,(instrsflag2)?$i2.node:null);}
    )?
    | 'write' '('
    ( ident {$node = new WriteNode($ident.node);}
    | e=string {$node = new WriteNode($e.str);}
    ) ')'
    | 'writeln' '('
    ( ident {$node = new WriteLineNode($ident.node);}
    | ident '(' listexp ')' {$node = new AppelINode($ident.node, $listexp.node);}
    | e=string {$node = new WriteLineNode($e.str);}
    ) ')'
    ;

listexp returns [ASTNode node]
    : exp {$node = $exp.node;}
    (',' exp {$node = new ExpListNode($node, $exp.node);}
    )*
    ;

exp returns [ASTNode node]
    : '!' exp1 {$node = new NotNode($exp1.node);}
    ( '&&' exp1 {$node = new AndNode($node, $exp1.node);}
    | '||' exp1 {$node = new OrNode($node, $exp1.node);}
    )*
    | exp1 {$node = $exp1.node;}
    ( '&&' exp1 {$node = new AndNode($node, $exp1.node);}
    | '||' exp1 {$node = new OrNode($node, $exp1.node);}
    )*
    ;


exp1 returns [ASTNode node]
    : exp2 {$node = $exp2.node;}
    ( '==' exp2 {$node = new EqualNode($node, $exp2.node);}
    | '>' exp2 {$node = new SupNode($node, $exp2.node);}
    )*
    ;

exp2 returns [ASTNode node]
    : '-' terme {$node = new UnMinusNode($terme.node);}
    ( '+' terme {$node = new AddNode($node, $terme.node);}
    | '-' terme {$node = new BinMinusNode($node, $terme.node);}
    )*
    | terme {$node = $terme.node;}
    ( '+' terme {$node = new AddNode($node, $terme.node);}
    | '-' terme {$node = new BinMinusNode($node, $terme.node);}
    )*
    ;

terme returns [ASTNode node]
    : fact {$node = $fact.node;}
    ( '*' fact {$node = new MulNode($node, $fact.node);}
    | '/' fact {$node = new DivNode($node, $fact.node);}
    )*
    ;


fact returns [ASTNode node]
    : ident1 {$node = $ident1.node;}
    | 'true' {$node = new BooleanNode(true);}
    | 'false' {$node = new BooleanNode(false);}
    | n=NOMBRE {$node = new NumberNode(Integer.parseInt($n.text));}
    | '(' exp ')' {$node = $exp.node;}
    ;

ident1 returns [ASTNode node]
    : ident {$node = $ident.node;}
    ;

typemeth returns [TypeNode node]
     : type {$node = $type.node;}
     | 'void' {$node = new TypeNode(ValueType.VOID);}
     ;

type returns [TypeNode node]
     : 'int' {$node = new TypeNode(ValueType.INT);}
     | 'boolean' {$node = new TypeNode(ValueType.BOOL);}
     ;

string returns [String str]
    : t=STRING {$str = $t.text.substring(1, $t.text.length() - 1);}
    ;

IDENTIFIER
    : ('_' | 'a'..'z' | 'A'..'Z')+ ('_' | 'a'..'z' | 'A'..'Z' | '0'..'9')*
    ;

NOMBRE
    : ('0'..'9')+
    ;

WS
    :   (' ' | '\t' | '\r'| '\n') -> skip
    ;

STRING
    : '"' .+? '"'
    ;