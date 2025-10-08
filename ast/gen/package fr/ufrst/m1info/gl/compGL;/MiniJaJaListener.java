// Generated from /media/kilian/Data/Utilitaires/IDEA projects/groupe-5/ast/src/main/antlr4/MiniJaJa.g4 by ANTLR 4.13.2
package package fr.ufrst.m1info.gl.compGL;;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MiniJaJaParser}.
 */
public interface MiniJaJaListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MiniJaJaParser#eval}.
	 * @param ctx the parse tree
	 */
	void enterEval(MiniJaJaParser.EvalContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJaJaParser#eval}.
	 * @param ctx the parse tree
	 */
	void exitEval(MiniJaJaParser.EvalContext ctx);
}