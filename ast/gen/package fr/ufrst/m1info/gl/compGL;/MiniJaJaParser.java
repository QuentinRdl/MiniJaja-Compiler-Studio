// Generated from /media/kilian/Data/Utilitaires/IDEA projects/groupe-5/ast/src/main/antlr4/MiniJaJa.g4 by ANTLR 4.13.2
package package fr.ufrst.m1info.gl.compGL;;

import fr.ufrst.m1info.gl.compGL.Nodes.*;

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
		T__9=10, T__10=11, T__11=12, IDENTIFIER=13;
	public static final int
		RULE_classe = 0, RULE_ident = 1, RULE_decls = 2, RULE_methmain = 3, RULE_vars = 4, 
		RULE_instrs = 5, RULE_instr = 6, RULE_exp = 7, RULE_andorexp = 8, RULE_exp1 = 9;
	private static String[] makeRuleNames() {
		return new String[] {
			"classe", "ident", "decls", "methmain", "vars", "instrs", "instr", "exp", 
			"andorexp", "exp1"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'class'", "'{'", "'}'", "'main'", "';'", "'while'", "'('", "')'", 
			"'return'", "'!'", "'&&'", "'||'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, "IDENTIFIER"
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
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(20);
			match(T__0);
			setState(21);
			((ClasseContext)_localctx).ident = ident();
			setState(22);
			match(T__1);
			setState(24);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				{
				setState(23);
				((ClasseContext)_localctx).decls = decls();
				}
				break;
			}
			setState(26);
			((ClasseContext)_localctx).methmain = methmain();
			setState(27);
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
			setState(30);
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
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(33);
			matchWildcard();
			((DeclsContext)_localctx).node =  null;
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
			setState(36);
			match(T__3);
			setState(37);
			match(T__1);
			setState(39);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				{
				setState(38);
				((MethmainContext)_localctx).vars = vars();
				}
				break;
			}
			setState(42);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__5 || _la==T__8) {
				{
				setState(41);
				((MethmainContext)_localctx).instrs = instrs();
				}
			}

			setState(44);
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
	public static class VarsContext extends ParserRuleContext {
		public ASTNode node;
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
		enterRule(_localctx, 8, RULE_vars);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(47);
			matchWildcard();
			((VarsContext)_localctx).node =  null;
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
		enterRule(_localctx, 10, RULE_instrs);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(50);
			((InstrsContext)_localctx).instr = instr();
			setState(51);
			match(T__4);
			setState(53);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__5 || _la==T__8) {
				{
				setState(52);
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
		enterRule(_localctx, 12, RULE_instr);
		int _la;
		try {
			setState(72);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__5:
				enterOuterAlt(_localctx, 1);
				{
				setState(57);
				match(T__5);
				setState(58);
				match(T__6);
				setState(59);
				((InstrContext)_localctx).exp = exp();
				setState(60);
				match(T__7);
				setState(61);
				match(T__1);
				setState(63);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__5 || _la==T__8) {
					{
					setState(62);
					((InstrContext)_localctx).instrs = instrs();
					}
				}

				setState(65);
				match(T__2);
				((InstrContext)_localctx).node =  new WhileNode(((InstrContext)_localctx).exp.node, ((InstrContext)_localctx).instrs.node);
				}
				break;
			case T__8:
				enterOuterAlt(_localctx, 2);
				{
				setState(68);
				match(T__8);
				setState(69);
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
		enterRule(_localctx, 14, RULE_exp);
		int _la;
		try {
			setState(105);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(74);
				match(T__9);
				setState(75);
				((ExpContext)_localctx).exp1 = exp1();
				((ExpContext)_localctx).node =  new NotNode(((ExpContext)_localctx).exp1.node);
				setState(87);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__10 || _la==T__11) {
					{
					setState(85);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case T__10:
						{
						setState(77);
						match(T__10);
						setState(78);
						((ExpContext)_localctx).exp1 = exp1();
						((ExpContext)_localctx).node =  new AndNode(_localctx.node, ((ExpContext)_localctx).exp1.node);
						}
						break;
					case T__11:
						{
						setState(81);
						match(T__11);
						setState(82);
						((ExpContext)_localctx).exp1 = exp1();
						((ExpContext)_localctx).node =  new OrNode(_localctx.node, ((ExpContext)_localctx).exp1.node);
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					}
					setState(89);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(90);
				((ExpContext)_localctx).exp1 = exp1();
				((ExpContext)_localctx).node =  ((ExpContext)_localctx).exp1.node;
				setState(102);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__10 || _la==T__11) {
					{
					setState(100);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case T__10:
						{
						setState(92);
						match(T__10);
						setState(93);
						((ExpContext)_localctx).exp1 = exp1();
						((ExpContext)_localctx).node =  new AndNode(_localctx.node, ((ExpContext)_localctx).exp1.node);
						}
						break;
					case T__11:
						{
						setState(96);
						match(T__11);
						setState(97);
						((ExpContext)_localctx).exp1 = exp1();
						((ExpContext)_localctx).node =  new OrNode(_localctx.node, ((ExpContext)_localctx).exp1.node);
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					}
					setState(104);
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
	public static class AndorexpContext extends ParserRuleContext {
		public ASTNode node;
		public Exp1Context exp1() {
			return getRuleContext(Exp1Context.class,0);
		}
		public List<AndorexpContext> andorexp() {
			return getRuleContexts(AndorexpContext.class);
		}
		public AndorexpContext andorexp(int i) {
			return getRuleContext(AndorexpContext.class,i);
		}
		public AndorexpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_andorexp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).enterAndorexp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).exitAndorexp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJaJaVisitor ) return ((MiniJaJaVisitor<? extends T>)visitor).visitAndorexp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AndorexpContext andorexp() throws RecognitionException {
		AndorexpContext _localctx = new AndorexpContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_andorexp);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(107);
			match(T__10);
			setState(108);
			exp1();
			setState(112);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(109);
					andorexp();
					}
					} 
				}
				setState(114);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			}
			((AndorexpContext)_localctx).node =  null;
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
		enterRule(_localctx, 18, RULE_exp1);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(117);
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

	public static final String _serializedATN =
		"\u0004\u0001\ry\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0003\u0000\u0019\b\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0003\u0003(\b\u0003\u0001\u0003"+
		"\u0003\u0003+\b\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0003\u0005"+
		"6\b\u0005\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0003\u0006@\b\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0003\u0006I\b\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0005\u0007V\b\u0007\n\u0007\f\u0007Y\t\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0005\u0007e\b\u0007\n\u0007\f\u0007"+
		"h\t\u0007\u0003\u0007j\b\u0007\u0001\b\u0001\b\u0001\b\u0005\bo\b\b\n"+
		"\b\f\br\t\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001\t\u0001\t\u0000\u0000"+
		"\n\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0000\u0000z\u0000"+
		"\u0014\u0001\u0000\u0000\u0000\u0002\u001e\u0001\u0000\u0000\u0000\u0004"+
		"!\u0001\u0000\u0000\u0000\u0006$\u0001\u0000\u0000\u0000\b/\u0001\u0000"+
		"\u0000\u0000\n2\u0001\u0000\u0000\u0000\fH\u0001\u0000\u0000\u0000\u000e"+
		"i\u0001\u0000\u0000\u0000\u0010k\u0001\u0000\u0000\u0000\u0012u\u0001"+
		"\u0000\u0000\u0000\u0014\u0015\u0005\u0001\u0000\u0000\u0015\u0016\u0003"+
		"\u0002\u0001\u0000\u0016\u0018\u0005\u0002\u0000\u0000\u0017\u0019\u0003"+
		"\u0004\u0002\u0000\u0018\u0017\u0001\u0000\u0000\u0000\u0018\u0019\u0001"+
		"\u0000\u0000\u0000\u0019\u001a\u0001\u0000\u0000\u0000\u001a\u001b\u0003"+
		"\u0006\u0003\u0000\u001b\u001c\u0005\u0003\u0000\u0000\u001c\u001d\u0006"+
		"\u0000\uffff\uffff\u0000\u001d\u0001\u0001\u0000\u0000\u0000\u001e\u001f"+
		"\u0005\r\u0000\u0000\u001f \u0006\u0001\uffff\uffff\u0000 \u0003\u0001"+
		"\u0000\u0000\u0000!\"\t\u0000\u0000\u0000\"#\u0006\u0002\uffff\uffff\u0000"+
		"#\u0005\u0001\u0000\u0000\u0000$%\u0005\u0004\u0000\u0000%\'\u0005\u0002"+
		"\u0000\u0000&(\u0003\b\u0004\u0000\'&\u0001\u0000\u0000\u0000\'(\u0001"+
		"\u0000\u0000\u0000(*\u0001\u0000\u0000\u0000)+\u0003\n\u0005\u0000*)\u0001"+
		"\u0000\u0000\u0000*+\u0001\u0000\u0000\u0000+,\u0001\u0000\u0000\u0000"+
		",-\u0005\u0003\u0000\u0000-.\u0006\u0003\uffff\uffff\u0000.\u0007\u0001"+
		"\u0000\u0000\u0000/0\t\u0000\u0000\u000001\u0006\u0004\uffff\uffff\u0000"+
		"1\t\u0001\u0000\u0000\u000023\u0003\f\u0006\u000035\u0005\u0005\u0000"+
		"\u000046\u0003\n\u0005\u000054\u0001\u0000\u0000\u000056\u0001\u0000\u0000"+
		"\u000067\u0001\u0000\u0000\u000078\u0006\u0005\uffff\uffff\u00008\u000b"+
		"\u0001\u0000\u0000\u00009:\u0005\u0006\u0000\u0000:;\u0005\u0007\u0000"+
		"\u0000;<\u0003\u000e\u0007\u0000<=\u0005\b\u0000\u0000=?\u0005\u0002\u0000"+
		"\u0000>@\u0003\n\u0005\u0000?>\u0001\u0000\u0000\u0000?@\u0001\u0000\u0000"+
		"\u0000@A\u0001\u0000\u0000\u0000AB\u0005\u0003\u0000\u0000BC\u0006\u0006"+
		"\uffff\uffff\u0000CI\u0001\u0000\u0000\u0000DE\u0005\t\u0000\u0000EF\u0003"+
		"\u000e\u0007\u0000FG\u0006\u0006\uffff\uffff\u0000GI\u0001\u0000\u0000"+
		"\u0000H9\u0001\u0000\u0000\u0000HD\u0001\u0000\u0000\u0000I\r\u0001\u0000"+
		"\u0000\u0000JK\u0005\n\u0000\u0000KL\u0003\u0012\t\u0000LW\u0006\u0007"+
		"\uffff\uffff\u0000MN\u0005\u000b\u0000\u0000NO\u0003\u0012\t\u0000OP\u0006"+
		"\u0007\uffff\uffff\u0000PV\u0001\u0000\u0000\u0000QR\u0005\f\u0000\u0000"+
		"RS\u0003\u0012\t\u0000ST\u0006\u0007\uffff\uffff\u0000TV\u0001\u0000\u0000"+
		"\u0000UM\u0001\u0000\u0000\u0000UQ\u0001\u0000\u0000\u0000VY\u0001\u0000"+
		"\u0000\u0000WU\u0001\u0000\u0000\u0000WX\u0001\u0000\u0000\u0000Xj\u0001"+
		"\u0000\u0000\u0000YW\u0001\u0000\u0000\u0000Z[\u0003\u0012\t\u0000[f\u0006"+
		"\u0007\uffff\uffff\u0000\\]\u0005\u000b\u0000\u0000]^\u0003\u0012\t\u0000"+
		"^_\u0006\u0007\uffff\uffff\u0000_e\u0001\u0000\u0000\u0000`a\u0005\f\u0000"+
		"\u0000ab\u0003\u0012\t\u0000bc\u0006\u0007\uffff\uffff\u0000ce\u0001\u0000"+
		"\u0000\u0000d\\\u0001\u0000\u0000\u0000d`\u0001\u0000\u0000\u0000eh\u0001"+
		"\u0000\u0000\u0000fd\u0001\u0000\u0000\u0000fg\u0001\u0000\u0000\u0000"+
		"gj\u0001\u0000\u0000\u0000hf\u0001\u0000\u0000\u0000iJ\u0001\u0000\u0000"+
		"\u0000iZ\u0001\u0000\u0000\u0000j\u000f\u0001\u0000\u0000\u0000kl\u0005"+
		"\u000b\u0000\u0000lp\u0003\u0012\t\u0000mo\u0003\u0010\b\u0000nm\u0001"+
		"\u0000\u0000\u0000or\u0001\u0000\u0000\u0000pn\u0001\u0000\u0000\u0000"+
		"pq\u0001\u0000\u0000\u0000qs\u0001\u0000\u0000\u0000rp\u0001\u0000\u0000"+
		"\u0000st\u0006\b\uffff\uffff\u0000t\u0011\u0001\u0000\u0000\u0000uv\t"+
		"\u0000\u0000\u0000vw\u0006\t\uffff\uffff\u0000w\u0013\u0001\u0000\u0000"+
		"\u0000\f\u0018\'*5?HUWdfip";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}