grammar MiniJaJa;

@header{
import fr.ufrst.m1info.gl.compGL.Nodes.*;
}

classe returns [ClassNode node]
    : 'class' ident '{' (decls)? methmain '}' {$node = new ClassNode($ident.node, $decls.node, $methmain.node);}
    ;

ident returns [IdentNode node]
    : id=IDENTIFIER {$node = new IdentNode($id.text);}
    ;

decls returns [ASTNode node]
    : . {$node = null;}
    ;

methmain returns [MainNode node]
    : 'main' '{' (vars)? (instrs)? '}' {$node = new MainNode($vars.node, $instrs.node);}
    ;

vars returns [ASTNode node]
    : . {$node = null;}
    ;

instrs returns [InstructionsNode node]
    : instr ';' (instrs)? {$node = new InstructionsNode($instr.node, $instrs.node);}
    ;

instr returns [ASTNode node]
    : 'while' '(' exp ')' '{' (instrs)? '}' {$node = new WhileNode($exp.node, $instrs.node);}
    | 'return' exp {$node = new ReturnNode($exp.node);}
    | ident1 {$node = $ident1.node;}
    ( '=' exp {$node = new AffectationNode((IdentNode)$node, $exp.node);}
    | '+=' exp {$node = new SommeNode((IdentNode)$node, $exp.node);}
    | '++' {$node = new IncNode((IdentNode)$node);}
    )
    ;

ident1 returns [IdentNode node]
    : . {$node = null;}
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

andorexp returns [ASTNode node]
    : '&&' exp1 (andorexp)* {$node = null;}
    ;

exp1 returns [ASTNode node]
    : . {$node = null;}
    ;

IDENTIFIER
    : ('_' | 'a'..'z' | 'A'..'Z')+ ('_' | 'a'..'z' | 'A'..'Z' | '0'..'9')*
    ;
