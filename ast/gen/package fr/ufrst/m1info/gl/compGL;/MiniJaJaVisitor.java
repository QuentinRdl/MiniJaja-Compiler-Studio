// Generated from /media/kilian/Data/Utilitaires/IDEA projects/groupe-5/ast/src/main/antlr4/MiniJaJa.g4 by ANTLR 4.13.2
package package fr.ufrst.m1info.gl.compGL;;

import fr.ufrst.m1info.gl.compGL.Nodes.*;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MiniJaJaParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MiniJaJaVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MiniJaJaParser#classe}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClasse(MiniJaJaParser.ClasseContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJaJaParser#ident}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdent(MiniJaJaParser.IdentContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJaJaParser#decls}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecls(MiniJaJaParser.DeclsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJaJaParser#methmain}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethmain(MiniJaJaParser.MethmainContext ctx);
}