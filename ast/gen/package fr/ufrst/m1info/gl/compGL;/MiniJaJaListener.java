// Generated from /media/kilian/Data/Utilitaires/IDEA projects/groupe-5/ast/src/main/antlr4/MiniJaJa.g4 by ANTLR 4.13.2
package package fr.ufrst.m1info.gl.compGL;;

import fr.ufrst.m1info.gl.compGL.Nodes.*;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MiniJaJaParser}.
 */
public interface MiniJaJaListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MiniJaJaParser#classe}.
	 * @param ctx the parse tree
	 */
	void enterClasse(MiniJaJaParser.ClasseContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJaJaParser#classe}.
	 * @param ctx the parse tree
	 */
	void exitClasse(MiniJaJaParser.ClasseContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJaJaParser#ident}.
	 * @param ctx the parse tree
	 */
	void enterIdent(MiniJaJaParser.IdentContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJaJaParser#ident}.
	 * @param ctx the parse tree
	 */
	void exitIdent(MiniJaJaParser.IdentContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJaJaParser#decls}.
	 * @param ctx the parse tree
	 */
	void enterDecls(MiniJaJaParser.DeclsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJaJaParser#decls}.
	 * @param ctx the parse tree
	 */
	void exitDecls(MiniJaJaParser.DeclsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJaJaParser#methmain}.
	 * @param ctx the parse tree
	 */
	void enterMethmain(MiniJaJaParser.MethmainContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJaJaParser#methmain}.
	 * @param ctx the parse tree
	 */
	void exitMethmain(MiniJaJaParser.MethmainContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJaJaParser#vars}.
	 * @param ctx the parse tree
	 */
	void enterVars(MiniJaJaParser.VarsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJaJaParser#vars}.
	 * @param ctx the parse tree
	 */
	void exitVars(MiniJaJaParser.VarsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJaJaParser#instrs}.
	 * @param ctx the parse tree
	 */
	void enterInstrs(MiniJaJaParser.InstrsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJaJaParser#instrs}.
	 * @param ctx the parse tree
	 */
	void exitInstrs(MiniJaJaParser.InstrsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJaJaParser#instr}.
	 * @param ctx the parse tree
	 */
	void enterInstr(MiniJaJaParser.InstrContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJaJaParser#instr}.
	 * @param ctx the parse tree
	 */
	void exitInstr(MiniJaJaParser.InstrContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJaJaParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterExp(MiniJaJaParser.ExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJaJaParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitExp(MiniJaJaParser.ExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJaJaParser#andorexp}.
	 * @param ctx the parse tree
	 */
	void enterAndorexp(MiniJaJaParser.AndorexpContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJaJaParser#andorexp}.
	 * @param ctx the parse tree
	 */
	void exitAndorexp(MiniJaJaParser.AndorexpContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJaJaParser#exp1}.
	 * @param ctx the parse tree
	 */
	void enterExp1(MiniJaJaParser.Exp1Context ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJaJaParser#exp1}.
	 * @param ctx the parse tree
	 */
	void exitExp1(MiniJaJaParser.Exp1Context ctx);
}