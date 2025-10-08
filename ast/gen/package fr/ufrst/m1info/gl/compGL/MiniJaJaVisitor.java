// Generated from /home/AD/kjelic/DossierPartage/Projet/groupe-5/ast/src/main/antlr4/MiniJaJa.g4 by ANTLR 4.13.2
package package fr.ufrst.m1info.gl.compGL;

import fr.ufrst.m1info.gl.compGL.Nodes.*;
import fr.ufrst.m1info.gl.compGL.ValueType;

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
	/**
	 * Visit a parse tree produced by {@link MiniJaJaParser#instrs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstrs(MiniJaJaParser.InstrsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJaJaParser#instr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstr(MiniJaJaParser.InstrContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJaJaParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExp(MiniJaJaParser.ExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJaJaParser#exp1}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExp1(MiniJaJaParser.Exp1Context ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJaJaParser#decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecl(MiniJaJaParser.DeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJaJaParser#vars}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVars(MiniJaJaParser.VarsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJaJaParser#var}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar(MiniJaJaParser.VarContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJaJaParser#varPrime}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarPrime(MiniJaJaParser.VarPrimeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJaJaParser#typemeth}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypemeth(MiniJaJaParser.TypemethContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJaJaParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(MiniJaJaParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJaJaParser#vexp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVexp(MiniJaJaParser.VexpContext ctx);
}