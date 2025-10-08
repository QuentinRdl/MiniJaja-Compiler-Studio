// Generated from /home/AD/kjelic/DossierPartage/Projet/groupe-5/src/main/antlr4/MiniJaJa.g4 by ANTLR 4.13.2
package fr.ufrst.m1info.gl.compgl.compgl;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

/**
 * This class provides an empty implementation of {@link MiniJaJaVisitor},
 * which can be extended to create a visitor which only needs to handle a subset
 * of the available methods.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
@SuppressWarnings("CheckReturnValue")
public class MiniJaJaBaseVisitor<T> extends AbstractParseTreeVisitor<T> implements MiniJaJaVisitor<T> {
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitEval(MiniJaJaParser.EvalContext ctx) { return visitChildren(ctx); }
}