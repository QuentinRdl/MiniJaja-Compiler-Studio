grammar MiniJaJa;

@header{
import fr.ufrst.m1info.gl.compGL.Nodes.*;
import fr.ufrst.m1info.gl.compGL.ValueType;
}

classe returns [ClassNode node]
    : 'class' ident '{' (decls)? methmain '}' {$node = new ClassNode($ident.node, $decls.node, $methmain.node);}
    ;

ident returns [ASTNode node]
    : id=IDENTIFIER {$node = new IdentNode($id.text);}
    ;

decls returns [ASTNode node]
    : decl ';' (decls)?  {$node = new DeclarationsNode($decl.node, $decls.node);}
    ;

methmain returns [MainNode node]
    : 'main' '{' (vars)? (instrs)? '}' {$node = new MainNode($vars.node, $instrs.node);}
    ;

instrs returns [InstructionsNode node]
    : instr ';' (instrs)? {$node = new InstructionsNode($instr.node, $instrs.node);}
    ;

instr returns [ASTNode node]
    : 'while' '(' exp ')' '{' (instrs)? '}' {$node = new WhileNode($exp.node, $instrs.node);}
    | 'return' exp {$node = new ReturnNode($exp.node);}
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

decl returns [ASTNode node]
    : var {$node = $var.node;}
    ;

vars returns [ASTNode node]
    : var ';' (vars)? {$node = new VariablesNode($var.node, $vars.node);}
    ;

var returns [ASTNode node]
    : typemeth ident varPrime {$node = new VariableNode($typemeth.node, $ident.node, $varPrime.node);}
    | 'final' type ident (vexp)? {$node = new FinalNode($type.node, $ident.node, $vexp.node);}
    ;

varPrime returns [ASTNode node]
     : (vexp)? {$node = $vexp.node;}
     ;

typemeth returns [TypeNode node]
     : type {$node = $type.node;}
     | 'void' {$node = new TypeNode(ValueType.VOID);}
     ;

type returns [TypeNode node]
     : 'int' {$node = new TypeNode(ValueType.INT);}
     | 'boolean' {$node = new TypeNode(ValueType.BOOL);}
     ;

vexp returns [ASTNode node]
     : '=' exp {$node = $exp.node;}
     ;

IDENTIFIER
    : ('_' | 'a'..'z' | 'A'..'Z')+ ('_' | 'a'..'z' | 'A'..'Z' | '0'..'9')*
    ;

NOMBRE
    : ('0'..'9')+
    ;
