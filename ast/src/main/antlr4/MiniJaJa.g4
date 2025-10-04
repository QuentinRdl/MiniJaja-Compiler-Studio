grammar MiniJaJa;

@header{
import fr.ufrst.m1info.gl.compGL.Nodes.*;
}

classe returns [ClassNode node]
    : 'class' ident '{' decls methmain '}' {$node = new ClassNode($ident.node, $decls.node, $methmain.node);}
    ;

ident returns [ASTNode node]
    : id=IDENTIFIER {$node = new IdentNode($id.text);}
    ;

decls returns [ASTNode node]
    : . {$node = null;}
    ;

/* methmain    -> main "{" vars instrs "}" */

methmain returns [MainNode node]
    : 'main' '{' vars instrs '}' {$node = new MainNode($vars.node, $instrs.node);}
    ;

vars returns [ASTNode node]
    : . {$node = null;}
    ;

instrs returns [ASTNode node]
    : . {$node = null;}
    ;

IDENTIFIER
    : ('_' | 'a'..'z' | 'A'..'Z')+ ('_' | 'a'..'z' | 'A'..'Z' | '0'..'9')*
    ;
