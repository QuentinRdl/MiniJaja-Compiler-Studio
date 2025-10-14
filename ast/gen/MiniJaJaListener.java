// Generated from /home/utilisateur/Documents/Master 1/M1 - Semestre 7/Projet COMP/groupe-5/ast/src/main/antlr4/MiniJaJa.g4 by ANTLR 4.13.2

package fr.ufrst.m1info.pvm.group5;
import fr.ufrst.m1info.pvm.group5.Nodes.*;
import fr.ufrst.m1info.pvm.group5.ValueType;

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
	 * Enter a parse tree produced by {@link MiniJaJaParser#decl}.
	 * @param ctx the parse tree
	 */
	void enterDecl(MiniJaJaParser.DeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJaJaParser#decl}.
	 * @param ctx the parse tree
	 */
	void exitDecl(MiniJaJaParser.DeclContext ctx);
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
	 * Enter a parse tree produced by {@link MiniJaJaParser#var}.
	 * @param ctx the parse tree
	 */
	void enterVar(MiniJaJaParser.VarContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJaJaParser#var}.
	 * @param ctx the parse tree
	 */
	void exitVar(MiniJaJaParser.VarContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJaJaParser#vexp}.
	 * @param ctx the parse tree
	 */
	void enterVexp(MiniJaJaParser.VexpContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJaJaParser#vexp}.
	 * @param ctx the parse tree
	 */
	void exitVexp(MiniJaJaParser.VexpContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJaJaParser#methode}.
	 * @param ctx the parse tree
	 */
	void enterMethode(MiniJaJaParser.MethodeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJaJaParser#methode}.
	 * @param ctx the parse tree
	 */
	void exitMethode(MiniJaJaParser.MethodeContext ctx);
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
	 * Enter a parse tree produced by {@link MiniJaJaParser#entetes}.
	 * @param ctx the parse tree
	 */
	void enterEntetes(MiniJaJaParser.EntetesContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJaJaParser#entetes}.
	 * @param ctx the parse tree
	 */
	void exitEntetes(MiniJaJaParser.EntetesContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJaJaParser#entete}.
	 * @param ctx the parse tree
	 */
	void enterEntete(MiniJaJaParser.EnteteContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJaJaParser#entete}.
	 * @param ctx the parse tree
	 */
	void exitEntete(MiniJaJaParser.EnteteContext ctx);
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
	 * Enter a parse tree produced by {@link MiniJaJaParser#listexp}.
	 * @param ctx the parse tree
	 */
	void enterListexp(MiniJaJaParser.ListexpContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJaJaParser#listexp}.
	 * @param ctx the parse tree
	 */
	void exitListexp(MiniJaJaParser.ListexpContext ctx);
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
	 * Enter a parse tree produced by {@link MiniJaJaParser#exp1}.
	 * @param ctx the parse tree
	 */
	void enterExp1(MiniJaJaParser.Exp1Context ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJaJaParser#exp1}.
	 * @param ctx the parse tree
	 */
	void exitExp1(MiniJaJaParser.Exp1Context ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJaJaParser#exp2}.
	 * @param ctx the parse tree
	 */
	void enterExp2(MiniJaJaParser.Exp2Context ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJaJaParser#exp2}.
	 * @param ctx the parse tree
	 */
	void exitExp2(MiniJaJaParser.Exp2Context ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJaJaParser#terme}.
	 * @param ctx the parse tree
	 */
	void enterTerme(MiniJaJaParser.TermeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJaJaParser#terme}.
	 * @param ctx the parse tree
	 */
	void exitTerme(MiniJaJaParser.TermeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJaJaParser#fact}.
	 * @param ctx the parse tree
	 */
	void enterFact(MiniJaJaParser.FactContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJaJaParser#fact}.
	 * @param ctx the parse tree
	 */
	void exitFact(MiniJaJaParser.FactContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJaJaParser#ident1}.
	 * @param ctx the parse tree
	 */
	void enterIdent1(MiniJaJaParser.Ident1Context ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJaJaParser#ident1}.
	 * @param ctx the parse tree
	 */
	void exitIdent1(MiniJaJaParser.Ident1Context ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJaJaParser#typemeth}.
	 * @param ctx the parse tree
	 */
	void enterTypemeth(MiniJaJaParser.TypemethContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJaJaParser#typemeth}.
	 * @param ctx the parse tree
	 */
	void exitTypemeth(MiniJaJaParser.TypemethContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJaJaParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(MiniJaJaParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJaJaParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(MiniJaJaParser.TypeContext ctx);
}