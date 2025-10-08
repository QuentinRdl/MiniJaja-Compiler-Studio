// Generated from /home/AD/kjelic/DossierPartage/Projet/groupe-5/ast/src/main/antlr4/MiniJaJa.g4 by ANTLR 4.13.2
package package fr.ufrst.m1info.gl.compGL;

import fr.ufrst.m1info.gl.compGL.Nodes.*;
import fr.ufrst.m1info.gl.compGL.ValueType;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class MiniJaJaParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		IDENTIFIER=18;
	public static final int
		RULE_classe = 0, RULE_ident = 1, RULE_decls = 2, RULE_methmain = 3, RULE_instrs = 4, 
		RULE_instr = 5, RULE_exp = 6, RULE_exp1 = 7, RULE_decl = 8, RULE_vars = 9, 
		RULE_var = 10, RULE_varPrime = 11, RULE_typemeth = 12, RULE_type = 13, 
		RULE_vexp = 14;
	private static String[] makeRuleNames() {
		return new String[] {
			"classe", "ident", "decls", "methmain", "instrs", "instr", "exp", "exp1", 
			"decl", "vars", "var", "varPrime", "typemeth", "type", "vexp"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'class'", "'{'", "'}'", "';'", "'main'", "'while'", "'('", "')'", 
			"'return'", "'!'", "'&&'", "'||'", "'final'", "'void'", "'int'", "'boolean'", 
			"'='"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, "IDENTIFIER"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "MiniJaJa.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public MiniJaJaParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ClasseContext extends ParserRuleContext {
		public ClassNode node;
		public IdentContext ident;
		public DeclsContext decls;
		public MethmainContext methmain;
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public MethmainContext methmain() {
			return getRuleContext(MethmainContext.class,0);
		}
		public DeclsContext decls() {
			return getRuleContext(DeclsContext.class,0);
		}
		public ClasseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classe; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).enterClasse(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).exitClasse(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJaJaVisitor ) return ((MiniJaJaVisitor<? extends T>)visitor).visitClasse(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClasseContext classe() throws RecognitionException {
		ClasseContext _localctx = new ClasseContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_classe);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(30);
			match(T__0);
			setState(31);
			((ClasseContext)_localctx).ident = ident();
			setState(32);
			match(T__1);
			setState(34);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 122880L) != 0)) {
				{
				setState(33);
				((ClasseContext)_localctx).decls = decls();
				}
			}

			setState(36);
			((ClasseContext)_localctx).methmain = methmain();
			setState(37);
			match(T__2);
			((ClasseContext)_localctx).node =  new ClassNode(((ClasseContext)_localctx).ident.node, ((ClasseContext)_localctx).decls.node, ((ClasseContext)_localctx).methmain.node);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IdentContext extends ParserRuleContext {
		public ASTNode node;
		public Token id;
		public TerminalNode IDENTIFIER() { return getToken(MiniJaJaParser.IDENTIFIER, 0); }
		public IdentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ident; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).enterIdent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).exitIdent(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJaJaVisitor ) return ((MiniJaJaVisitor<? extends T>)visitor).visitIdent(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdentContext ident() throws RecognitionException {
		IdentContext _localctx = new IdentContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_ident);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(40);
			((IdentContext)_localctx).id = match(IDENTIFIER);
			((IdentContext)_localctx).node =  new IdentNode((((IdentContext)_localctx).id!=null?((IdentContext)_localctx).id.getText():null));
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DeclsContext extends ParserRuleContext {
		public ASTNode node;
		public DeclContext decl;
		public DeclsContext decls;
		public DeclContext decl() {
			return getRuleContext(DeclContext.class,0);
		}
		public DeclsContext decls() {
			return getRuleContext(DeclsContext.class,0);
		}
		public DeclsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decls; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).enterDecls(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).exitDecls(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJaJaVisitor ) return ((MiniJaJaVisitor<? extends T>)visitor).visitDecls(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclsContext decls() throws RecognitionException {
		DeclsContext _localctx = new DeclsContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_decls);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(43);
			((DeclsContext)_localctx).decl = decl();
			setState(44);
			match(T__3);
			setState(46);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 122880L) != 0)) {
				{
				setState(45);
				((DeclsContext)_localctx).decls = decls();
				}
			}

			((DeclsContext)_localctx).node =  new DeclarationsNode(((DeclsContext)_localctx).decl.node, ((DeclsContext)_localctx).decls.node);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MethmainContext extends ParserRuleContext {
		public MainNode node;
		public VarsContext vars;
		public InstrsContext instrs;
		public VarsContext vars() {
			return getRuleContext(VarsContext.class,0);
		}
		public InstrsContext instrs() {
			return getRuleContext(InstrsContext.class,0);
		}
		public MethmainContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methmain; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).enterMethmain(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).exitMethmain(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJaJaVisitor ) return ((MiniJaJaVisitor<? extends T>)visitor).visitMethmain(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethmainContext methmain() throws RecognitionException {
		MethmainContext _localctx = new MethmainContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_methmain);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(50);
			match(T__4);
			setState(51);
			match(T__1);
			setState(53);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 122880L) != 0)) {
				{
				setState(52);
				((MethmainContext)_localctx).vars = vars();
				}
			}

			setState(56);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__5 || _la==T__8) {
				{
				setState(55);
				((MethmainContext)_localctx).instrs = instrs();
				}
			}

			setState(58);
			match(T__2);
			((MethmainContext)_localctx).node =  new MainNode(((MethmainContext)_localctx).vars.node, ((MethmainContext)_localctx).instrs.node);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class InstrsContext extends ParserRuleContext {
		public InstructionsNode node;
		public InstrContext instr;
		public InstrsContext instrs;
		public InstrContext instr() {
			return getRuleContext(InstrContext.class,0);
		}
		public InstrsContext instrs() {
			return getRuleContext(InstrsContext.class,0);
		}
		public InstrsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_instrs; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).enterInstrs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).exitInstrs(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJaJaVisitor ) return ((MiniJaJaVisitor<? extends T>)visitor).visitInstrs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InstrsContext instrs() throws RecognitionException {
		InstrsContext _localctx = new InstrsContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_instrs);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(61);
			((InstrsContext)_localctx).instr = instr();
			setState(62);
			match(T__3);
			setState(64);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__5 || _la==T__8) {
				{
				setState(63);
				((InstrsContext)_localctx).instrs = instrs();
				}
			}

			((InstrsContext)_localctx).node =  new InstructionsNode(((InstrsContext)_localctx).instr.node, ((InstrsContext)_localctx).instrs.node);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class InstrContext extends ParserRuleContext {
		public ASTNode node;
		public ExpContext exp;
		public InstrsContext instrs;
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public InstrsContext instrs() {
			return getRuleContext(InstrsContext.class,0);
		}
		public InstrContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_instr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).enterInstr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).exitInstr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJaJaVisitor ) return ((MiniJaJaVisitor<? extends T>)visitor).visitInstr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InstrContext instr() throws RecognitionException {
		InstrContext _localctx = new InstrContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_instr);
		int _la;
		try {
			setState(83);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__5:
				enterOuterAlt(_localctx, 1);
				{
				setState(68);
				match(T__5);
				setState(69);
				match(T__6);
				setState(70);
				((InstrContext)_localctx).exp = exp();
				setState(71);
				match(T__7);
				setState(72);
				match(T__1);
				setState(74);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__5 || _la==T__8) {
					{
					setState(73);
					((InstrContext)_localctx).instrs = instrs();
					}
				}

				setState(76);
				match(T__2);
				((InstrContext)_localctx).node =  new WhileNode(((InstrContext)_localctx).exp.node, ((InstrContext)_localctx).instrs.node);
				}
				break;
			case T__8:
				enterOuterAlt(_localctx, 2);
				{
				setState(79);
				match(T__8);
				setState(80);
				((InstrContext)_localctx).exp = exp();
				((InstrContext)_localctx).node =  new ReturnNode(((InstrContext)_localctx).exp.node);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpContext extends ParserRuleContext {
		public ASTNode node;
		public Exp1Context exp1;
		public List<Exp1Context> exp1() {
			return getRuleContexts(Exp1Context.class);
		}
		public Exp1Context exp1(int i) {
			return getRuleContext(Exp1Context.class,i);
		}
		public ExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).enterExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).exitExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJaJaVisitor ) return ((MiniJaJaVisitor<? extends T>)visitor).visitExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpContext exp() throws RecognitionException {
		ExpContext _localctx = new ExpContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_exp);
		int _la;
		try {
			setState(116);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(85);
				match(T__9);
				setState(86);
				((ExpContext)_localctx).exp1 = exp1();
				((ExpContext)_localctx).node =  new NotNode(((ExpContext)_localctx).exp1.node);
				setState(98);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__10 || _la==T__11) {
					{
					setState(96);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case T__10:
						{
						setState(88);
						match(T__10);
						setState(89);
						((ExpContext)_localctx).exp1 = exp1();
						((ExpContext)_localctx).node =  new AndNode(_localctx.node, ((ExpContext)_localctx).exp1.node);
						}
						break;
					case T__11:
						{
						setState(92);
						match(T__11);
						setState(93);
						((ExpContext)_localctx).exp1 = exp1();
						((ExpContext)_localctx).node =  new OrNode(_localctx.node, ((ExpContext)_localctx).exp1.node);
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					}
					setState(100);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(101);
				((ExpContext)_localctx).exp1 = exp1();
				((ExpContext)_localctx).node =  ((ExpContext)_localctx).exp1.node;
				setState(113);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__10 || _la==T__11) {
					{
					setState(111);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case T__10:
						{
						setState(103);
						match(T__10);
						setState(104);
						((ExpContext)_localctx).exp1 = exp1();
						((ExpContext)_localctx).node =  new AndNode(_localctx.node, ((ExpContext)_localctx).exp1.node);
						}
						break;
					case T__11:
						{
						setState(107);
						match(T__11);
						setState(108);
						((ExpContext)_localctx).exp1 = exp1();
						((ExpContext)_localctx).node =  new OrNode(_localctx.node, ((ExpContext)_localctx).exp1.node);
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					}
					setState(115);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Exp1Context extends ParserRuleContext {
		public ASTNode node;
		public Exp1Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exp1; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).enterExp1(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).exitExp1(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJaJaVisitor ) return ((MiniJaJaVisitor<? extends T>)visitor).visitExp1(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Exp1Context exp1() throws RecognitionException {
		Exp1Context _localctx = new Exp1Context(_ctx, getState());
		enterRule(_localctx, 14, RULE_exp1);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(118);
			matchWildcard();
			((Exp1Context)_localctx).node =  null;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DeclContext extends ParserRuleContext {
		public ASTNode node;
		public VarContext var;
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public DeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).enterDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).exitDecl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJaJaVisitor ) return ((MiniJaJaVisitor<? extends T>)visitor).visitDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclContext decl() throws RecognitionException {
		DeclContext _localctx = new DeclContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_decl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(121);
			((DeclContext)_localctx).var = var();
			((DeclContext)_localctx).node =  ((DeclContext)_localctx).var.node;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VarsContext extends ParserRuleContext {
		public ASTNode node;
		public VarContext var;
		public VarsContext vars;
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public VarsContext vars() {
			return getRuleContext(VarsContext.class,0);
		}
		public VarsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vars; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).enterVars(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).exitVars(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJaJaVisitor ) return ((MiniJaJaVisitor<? extends T>)visitor).visitVars(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarsContext vars() throws RecognitionException {
		VarsContext _localctx = new VarsContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_vars);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(124);
			((VarsContext)_localctx).var = var();
			setState(125);
			match(T__3);
			setState(127);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 122880L) != 0)) {
				{
				setState(126);
				((VarsContext)_localctx).vars = vars();
				}
			}

			((VarsContext)_localctx).node =  new VariablesNode(((VarsContext)_localctx).var.node, ((VarsContext)_localctx).vars.node);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VarContext extends ParserRuleContext {
		public ASTNode node;
		public TypemethContext typemeth;
		public IdentContext ident;
		public VarPrimeContext varPrime;
		public TypeContext type;
		public VexpContext vexp;
		public TypemethContext typemeth() {
			return getRuleContext(TypemethContext.class,0);
		}
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public VarPrimeContext varPrime() {
			return getRuleContext(VarPrimeContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public VexpContext vexp() {
			return getRuleContext(VexpContext.class,0);
		}
		public VarContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).enterVar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).exitVar(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJaJaVisitor ) return ((MiniJaJaVisitor<? extends T>)visitor).visitVar(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarContext var() throws RecognitionException {
		VarContext _localctx = new VarContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_var);
		int _la;
		try {
			setState(144);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__13:
			case T__14:
			case T__15:
				enterOuterAlt(_localctx, 1);
				{
				setState(131);
				((VarContext)_localctx).typemeth = typemeth();
				setState(132);
				((VarContext)_localctx).ident = ident();
				setState(133);
				((VarContext)_localctx).varPrime = varPrime();
				((VarContext)_localctx).node =  new VariableNode(((VarContext)_localctx).typemeth.node, ((VarContext)_localctx).ident.node, ((VarContext)_localctx).varPrime.node);
				}
				break;
			case T__12:
				enterOuterAlt(_localctx, 2);
				{
				setState(136);
				match(T__12);
				setState(137);
				((VarContext)_localctx).type = type();
				setState(138);
				((VarContext)_localctx).ident = ident();
				setState(140);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__16) {
					{
					setState(139);
					((VarContext)_localctx).vexp = vexp();
					}
				}

				((VarContext)_localctx).node =  new FinalNode(((VarContext)_localctx).type.node, ((VarContext)_localctx).ident.node, ((VarContext)_localctx).vexp.node);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VarPrimeContext extends ParserRuleContext {
		public ASTNode node;
		public VexpContext vexp;
		public VexpContext vexp() {
			return getRuleContext(VexpContext.class,0);
		}
		public VarPrimeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varPrime; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).enterVarPrime(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).exitVarPrime(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJaJaVisitor ) return ((MiniJaJaVisitor<? extends T>)visitor).visitVarPrime(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarPrimeContext varPrime() throws RecognitionException {
		VarPrimeContext _localctx = new VarPrimeContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_varPrime);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(147);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__16) {
				{
				setState(146);
				((VarPrimeContext)_localctx).vexp = vexp();
				}
			}

			((VarPrimeContext)_localctx).node =  ((VarPrimeContext)_localctx).vexp.node;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypemethContext extends ParserRuleContext {
		public TypeNode node;
		public TypeContext type;
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TypemethContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typemeth; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).enterTypemeth(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).exitTypemeth(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJaJaVisitor ) return ((MiniJaJaVisitor<? extends T>)visitor).visitTypemeth(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypemethContext typemeth() throws RecognitionException {
		TypemethContext _localctx = new TypemethContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_typemeth);
		try {
			setState(156);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__14:
			case T__15:
				enterOuterAlt(_localctx, 1);
				{
				setState(151);
				((TypemethContext)_localctx).type = type();
				((TypemethContext)_localctx).node =  ((TypemethContext)_localctx).type.node;
				}
				break;
			case T__13:
				enterOuterAlt(_localctx, 2);
				{
				setState(154);
				match(T__13);
				((TypemethContext)_localctx).node =  new TypeNode(ValueType.VOID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeContext extends ParserRuleContext {
		public TypeNode node;
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).exitType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJaJaVisitor ) return ((MiniJaJaVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_type);
		try {
			setState(162);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__14:
				enterOuterAlt(_localctx, 1);
				{
				setState(158);
				match(T__14);
				((TypeContext)_localctx).node =  new TypeNode(ValueType.INT);
				}
				break;
			case T__15:
				enterOuterAlt(_localctx, 2);
				{
				setState(160);
				match(T__15);
				((TypeContext)_localctx).node =  new TypeNode(ValueType.BOOL);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VexpContext extends ParserRuleContext {
		public ASTNode node;
		public ExpContext exp;
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public VexpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vexp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).enterVexp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).exitVexp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJaJaVisitor ) return ((MiniJaJaVisitor<? extends T>)visitor).visitVexp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VexpContext vexp() throws RecognitionException {
		VexpContext _localctx = new VexpContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_vexp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(164);
			match(T__16);
			setState(165);
			((VexpContext)_localctx).exp = exp();
			((VexpContext)_localctx).node =  ((VexpContext)_localctx).exp.node;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u0012\u00a9\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001"+
		"\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004"+
		"\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007"+
		"\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b"+
		"\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0003\u0000#\b\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0003\u0002/\b\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0003\u00036\b\u0003\u0001"+
		"\u0003\u0003\u00039\b\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0003\u0004A\b\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0003\u0005K\b\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0003\u0005T\b\u0005\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0005\u0006a\b"+
		"\u0006\n\u0006\f\u0006d\t\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0005\u0006p\b\u0006\n\u0006\f\u0006s\t\u0006\u0003\u0006u\b\u0006"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\b\u0001\t\u0001"+
		"\t\u0001\t\u0003\t\u0080\b\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0003\n\u008d\b\n\u0001\n\u0001"+
		"\n\u0003\n\u0091\b\n\u0001\u000b\u0003\u000b\u0094\b\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0003\f\u009d\b\f"+
		"\u0001\r\u0001\r\u0001\r\u0001\r\u0003\r\u00a3\b\r\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0000\u0000\u000f\u0000\u0002\u0004"+
		"\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u0000\u0000"+
		"\u00ab\u0000\u001e\u0001\u0000\u0000\u0000\u0002(\u0001\u0000\u0000\u0000"+
		"\u0004+\u0001\u0000\u0000\u0000\u00062\u0001\u0000\u0000\u0000\b=\u0001"+
		"\u0000\u0000\u0000\nS\u0001\u0000\u0000\u0000\ft\u0001\u0000\u0000\u0000"+
		"\u000ev\u0001\u0000\u0000\u0000\u0010y\u0001\u0000\u0000\u0000\u0012|"+
		"\u0001\u0000\u0000\u0000\u0014\u0090\u0001\u0000\u0000\u0000\u0016\u0093"+
		"\u0001\u0000\u0000\u0000\u0018\u009c\u0001\u0000\u0000\u0000\u001a\u00a2"+
		"\u0001\u0000\u0000\u0000\u001c\u00a4\u0001\u0000\u0000\u0000\u001e\u001f"+
		"\u0005\u0001\u0000\u0000\u001f \u0003\u0002\u0001\u0000 \"\u0005\u0002"+
		"\u0000\u0000!#\u0003\u0004\u0002\u0000\"!\u0001\u0000\u0000\u0000\"#\u0001"+
		"\u0000\u0000\u0000#$\u0001\u0000\u0000\u0000$%\u0003\u0006\u0003\u0000"+
		"%&\u0005\u0003\u0000\u0000&\'\u0006\u0000\uffff\uffff\u0000\'\u0001\u0001"+
		"\u0000\u0000\u0000()\u0005\u0012\u0000\u0000)*\u0006\u0001\uffff\uffff"+
		"\u0000*\u0003\u0001\u0000\u0000\u0000+,\u0003\u0010\b\u0000,.\u0005\u0004"+
		"\u0000\u0000-/\u0003\u0004\u0002\u0000.-\u0001\u0000\u0000\u0000./\u0001"+
		"\u0000\u0000\u0000/0\u0001\u0000\u0000\u000001\u0006\u0002\uffff\uffff"+
		"\u00001\u0005\u0001\u0000\u0000\u000023\u0005\u0005\u0000\u000035\u0005"+
		"\u0002\u0000\u000046\u0003\u0012\t\u000054\u0001\u0000\u0000\u000056\u0001"+
		"\u0000\u0000\u000068\u0001\u0000\u0000\u000079\u0003\b\u0004\u000087\u0001"+
		"\u0000\u0000\u000089\u0001\u0000\u0000\u00009:\u0001\u0000\u0000\u0000"+
		":;\u0005\u0003\u0000\u0000;<\u0006\u0003\uffff\uffff\u0000<\u0007\u0001"+
		"\u0000\u0000\u0000=>\u0003\n\u0005\u0000>@\u0005\u0004\u0000\u0000?A\u0003"+
		"\b\u0004\u0000@?\u0001\u0000\u0000\u0000@A\u0001\u0000\u0000\u0000AB\u0001"+
		"\u0000\u0000\u0000BC\u0006\u0004\uffff\uffff\u0000C\t\u0001\u0000\u0000"+
		"\u0000DE\u0005\u0006\u0000\u0000EF\u0005\u0007\u0000\u0000FG\u0003\f\u0006"+
		"\u0000GH\u0005\b\u0000\u0000HJ\u0005\u0002\u0000\u0000IK\u0003\b\u0004"+
		"\u0000JI\u0001\u0000\u0000\u0000JK\u0001\u0000\u0000\u0000KL\u0001\u0000"+
		"\u0000\u0000LM\u0005\u0003\u0000\u0000MN\u0006\u0005\uffff\uffff\u0000"+
		"NT\u0001\u0000\u0000\u0000OP\u0005\t\u0000\u0000PQ\u0003\f\u0006\u0000"+
		"QR\u0006\u0005\uffff\uffff\u0000RT\u0001\u0000\u0000\u0000SD\u0001\u0000"+
		"\u0000\u0000SO\u0001\u0000\u0000\u0000T\u000b\u0001\u0000\u0000\u0000"+
		"UV\u0005\n\u0000\u0000VW\u0003\u000e\u0007\u0000Wb\u0006\u0006\uffff\uffff"+
		"\u0000XY\u0005\u000b\u0000\u0000YZ\u0003\u000e\u0007\u0000Z[\u0006\u0006"+
		"\uffff\uffff\u0000[a\u0001\u0000\u0000\u0000\\]\u0005\f\u0000\u0000]^"+
		"\u0003\u000e\u0007\u0000^_\u0006\u0006\uffff\uffff\u0000_a\u0001\u0000"+
		"\u0000\u0000`X\u0001\u0000\u0000\u0000`\\\u0001\u0000\u0000\u0000ad\u0001"+
		"\u0000\u0000\u0000b`\u0001\u0000\u0000\u0000bc\u0001\u0000\u0000\u0000"+
		"cu\u0001\u0000\u0000\u0000db\u0001\u0000\u0000\u0000ef\u0003\u000e\u0007"+
		"\u0000fq\u0006\u0006\uffff\uffff\u0000gh\u0005\u000b\u0000\u0000hi\u0003"+
		"\u000e\u0007\u0000ij\u0006\u0006\uffff\uffff\u0000jp\u0001\u0000\u0000"+
		"\u0000kl\u0005\f\u0000\u0000lm\u0003\u000e\u0007\u0000mn\u0006\u0006\uffff"+
		"\uffff\u0000np\u0001\u0000\u0000\u0000og\u0001\u0000\u0000\u0000ok\u0001"+
		"\u0000\u0000\u0000ps\u0001\u0000\u0000\u0000qo\u0001\u0000\u0000\u0000"+
		"qr\u0001\u0000\u0000\u0000ru\u0001\u0000\u0000\u0000sq\u0001\u0000\u0000"+
		"\u0000tU\u0001\u0000\u0000\u0000te\u0001\u0000\u0000\u0000u\r\u0001\u0000"+
		"\u0000\u0000vw\t\u0000\u0000\u0000wx\u0006\u0007\uffff\uffff\u0000x\u000f"+
		"\u0001\u0000\u0000\u0000yz\u0003\u0014\n\u0000z{\u0006\b\uffff\uffff\u0000"+
		"{\u0011\u0001\u0000\u0000\u0000|}\u0003\u0014\n\u0000}\u007f\u0005\u0004"+
		"\u0000\u0000~\u0080\u0003\u0012\t\u0000\u007f~\u0001\u0000\u0000\u0000"+
		"\u007f\u0080\u0001\u0000\u0000\u0000\u0080\u0081\u0001\u0000\u0000\u0000"+
		"\u0081\u0082\u0006\t\uffff\uffff\u0000\u0082\u0013\u0001\u0000\u0000\u0000"+
		"\u0083\u0084\u0003\u0018\f\u0000\u0084\u0085\u0003\u0002\u0001\u0000\u0085"+
		"\u0086\u0003\u0016\u000b\u0000\u0086\u0087\u0006\n\uffff\uffff\u0000\u0087"+
		"\u0091\u0001\u0000\u0000\u0000\u0088\u0089\u0005\r\u0000\u0000\u0089\u008a"+
		"\u0003\u001a\r\u0000\u008a\u008c\u0003\u0002\u0001\u0000\u008b\u008d\u0003"+
		"\u001c\u000e\u0000\u008c\u008b\u0001\u0000\u0000\u0000\u008c\u008d\u0001"+
		"\u0000\u0000\u0000\u008d\u008e\u0001\u0000\u0000\u0000\u008e\u008f\u0006"+
		"\n\uffff\uffff\u0000\u008f\u0091\u0001\u0000\u0000\u0000\u0090\u0083\u0001"+
		"\u0000\u0000\u0000\u0090\u0088\u0001\u0000\u0000\u0000\u0091\u0015\u0001"+
		"\u0000\u0000\u0000\u0092\u0094\u0003\u001c\u000e\u0000\u0093\u0092\u0001"+
		"\u0000\u0000\u0000\u0093\u0094\u0001\u0000\u0000\u0000\u0094\u0095\u0001"+
		"\u0000\u0000\u0000\u0095\u0096\u0006\u000b\uffff\uffff\u0000\u0096\u0017"+
		"\u0001\u0000\u0000\u0000\u0097\u0098\u0003\u001a\r\u0000\u0098\u0099\u0006"+
		"\f\uffff\uffff\u0000\u0099\u009d\u0001\u0000\u0000\u0000\u009a\u009b\u0005"+
		"\u000e\u0000\u0000\u009b\u009d\u0006\f\uffff\uffff\u0000\u009c\u0097\u0001"+
		"\u0000\u0000\u0000\u009c\u009a\u0001\u0000\u0000\u0000\u009d\u0019\u0001"+
		"\u0000\u0000\u0000\u009e\u009f\u0005\u000f\u0000\u0000\u009f\u00a3\u0006"+
		"\r\uffff\uffff\u0000\u00a0\u00a1\u0005\u0010\u0000\u0000\u00a1\u00a3\u0006"+
		"\r\uffff\uffff\u0000\u00a2\u009e\u0001\u0000\u0000\u0000\u00a2\u00a0\u0001"+
		"\u0000\u0000\u0000\u00a3\u001b\u0001\u0000\u0000\u0000\u00a4\u00a5\u0005"+
		"\u0011\u0000\u0000\u00a5\u00a6\u0003\f\u0006\u0000\u00a6\u00a7\u0006\u000e"+
		"\uffff\uffff\u0000\u00a7\u001d\u0001\u0000\u0000\u0000\u0012\".58@JS`"+
		"boqt\u007f\u008c\u0090\u0093\u009c\u00a2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}