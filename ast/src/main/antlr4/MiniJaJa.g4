grammar MiniJaJa;

@header{
import fr.ufrst.m1info.pvm.group5.Nodes.*;
import fr.ufrst.m1info.pvm.group5.ValueType;
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
    : . {$node = null;}
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
