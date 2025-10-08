// Generated from /home/AD/kjelic/DossierPartage/Projet/groupe-5/src/main/antlr4/MiniJaJa.g4 by ANTLR 4.13.2
package fr.ufrst.m1info.gl.compgl.compgl;
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
	 * Visit a parse tree produced by {@link MiniJaJaParser#eval}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEval(MiniJaJaParser.EvalContext ctx);
}