// Generated from /media/kilian/Data/Utilitaires/IDEA projects/groupe-5/ast/src/main/antlr4/MiniJaJa.g4 by ANTLR 4.13.2
package package fr.ufrst.m1info.gl.compGL;;

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
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, IDENTIFIER=30, NOMBRE=31;
	public static final int
		RULE_classe = 0, RULE_ident = 1, RULE_decls = 2, RULE_methmain = 3, RULE_instrs = 4, 
		RULE_instr = 5, RULE_exp = 6, RULE_exp1 = 7, RULE_exp2 = 8, RULE_terme = 9, 
		RULE_fact = 10, RULE_ident1 = 11, RULE_decl = 12, RULE_vars = 13, RULE_var = 14, 
		RULE_varPrime = 15, RULE_typemeth = 16, RULE_type = 17, RULE_vexp = 18;
	private static String[] makeRuleNames() {
		return new String[] {
			"classe", "ident", "decls", "methmain", "instrs", "instr", "exp", "exp1", 
			"exp2", "terme", "fact", "ident1", "decl", "vars", "var", "varPrime", 
			"typemeth", "type", "vexp"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'class'", "'{'", "'}'", "';'", "'main'", "'while'", "'('", "')'", 
			"'return'", "'='", "'+='", "'++'", "'if'", "'else'", "'!'", "'&&'", "'||'", 
			"'=='", "'>'", "'-'", "'+'", "'*'", "'/'", "'true'", "'false'", "'final'", 
			"'void'", "'int'", "'boolean'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, "IDENTIFIER", "NOMBRE"
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
			setState(38);
			match(T__0);
			setState(39);
			((ClasseContext)_localctx).ident = ident();
			setState(40);
			match(T__1);
			setState(42);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1006632960L) != 0)) {
				{
				setState(41);
				((ClasseContext)_localctx).decls = decls();
				}
			}

			setState(44);
			((ClasseContext)_localctx).methmain = methmain();
			setState(45);
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
		public IdentNode node;
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
			setState(48);
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
			setState(51);
			((DeclsContext)_localctx).decl = decl();
			setState(52);
			match(T__3);
			setState(54);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1006632960L) != 0)) {
				{
				setState(53);
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
			setState(58);
			match(T__4);
			setState(59);
			match(T__1);
			setState(61);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1006632960L) != 0)) {
				{
				setState(60);
				((MethmainContext)_localctx).vars = vars();
				}
			}

			setState(64);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1073750592L) != 0)) {
				{
				setState(63);
				((MethmainContext)_localctx).instrs = instrs();
				}
			}

			setState(66);
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
			setState(69);
			((InstrsContext)_localctx).instr = instr();
			setState(70);
			match(T__3);
			setState(72);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1073750592L) != 0)) {
				{
				setState(71);
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
		public Ident1Context ident1;
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public List<InstrsContext> instrs() {
			return getRuleContexts(InstrsContext.class);
		}
		public InstrsContext instrs(int i) {
			return getRuleContext(InstrsContext.class,i);
		}
		public Ident1Context ident1() {
			return getRuleContext(Ident1Context.class,0);
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
			setState(124);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__5:
				enterOuterAlt(_localctx, 1);
				{
				setState(76);
				match(T__5);
				setState(77);
				match(T__6);
				setState(78);
				((InstrContext)_localctx).exp = exp();
				setState(79);
				match(T__7);
				setState(80);
				match(T__1);
				setState(82);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1073750592L) != 0)) {
					{
					setState(81);
					((InstrContext)_localctx).instrs = instrs();
					}
				}

				setState(84);
				match(T__2);
				((InstrContext)_localctx).node =  new WhileNode(((InstrContext)_localctx).exp.node, ((InstrContext)_localctx).instrs.node);
				}
				break;
			case T__8:
				enterOuterAlt(_localctx, 2);
				{
				setState(87);
				match(T__8);
				setState(88);
				((InstrContext)_localctx).exp = exp();
				((InstrContext)_localctx).node =  new ReturnNode(((InstrContext)_localctx).exp.node);
				}
				break;
			case IDENTIFIER:
				enterOuterAlt(_localctx, 3);
				{
				setState(91);
				((InstrContext)_localctx).ident1 = ident1();
				((InstrContext)_localctx).node =  ((InstrContext)_localctx).ident1.node;
				setState(103);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__9:
					{
					setState(93);
					match(T__9);
					setState(94);
					((InstrContext)_localctx).exp = exp();
					((InstrContext)_localctx).node =  new AffectationNode((IdentNode)_localctx.node, ((InstrContext)_localctx).exp.node);
					}
					break;
				case T__10:
					{
					setState(97);
					match(T__10);
					setState(98);
					((InstrContext)_localctx).exp = exp();
					((InstrContext)_localctx).node =  new SommeNode((IdentNode)_localctx.node, ((InstrContext)_localctx).exp.node);
					}
					break;
				case T__11:
					{
					setState(101);
					match(T__11);
					((InstrContext)_localctx).node =  new IncNode((IdentNode)_localctx.node);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			case T__12:
				enterOuterAlt(_localctx, 4);
				{
				setState(105);
				match(T__12);
				setState(106);
				match(T__6);
				setState(107);
				exp();
				setState(108);
				match(T__7);
				setState(109);
				match(T__1);
				setState(111);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1073750592L) != 0)) {
					{
					setState(110);
					instrs();
					}
				}

				setState(113);
				match(T__2);
				setState(120);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__13) {
					{
					setState(114);
					match(T__13);
					setState(115);
					match(T__1);
					setState(117);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1073750592L) != 0)) {
						{
						setState(116);
						instrs();
						}
					}

					setState(119);
					match(T__2);
					}
				}


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
			setState(157);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__14:
				enterOuterAlt(_localctx, 1);
				{
				setState(126);
				match(T__14);
				setState(127);
				((ExpContext)_localctx).exp1 = exp1();
				((ExpContext)_localctx).node =  new NotNode(((ExpContext)_localctx).exp1.node);
				setState(139);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__15 || _la==T__16) {
					{
					setState(137);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case T__15:
						{
						setState(129);
						match(T__15);
						setState(130);
						((ExpContext)_localctx).exp1 = exp1();
						((ExpContext)_localctx).node =  new AndNode(_localctx.node, ((ExpContext)_localctx).exp1.node);
						}
						break;
					case T__16:
						{
						setState(133);
						match(T__16);
						setState(134);
						((ExpContext)_localctx).exp1 = exp1();
						((ExpContext)_localctx).node =  new OrNode(_localctx.node, ((ExpContext)_localctx).exp1.node);
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					}
					setState(141);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case T__6:
			case T__19:
			case T__23:
			case T__24:
			case IDENTIFIER:
			case NOMBRE:
				enterOuterAlt(_localctx, 2);
				{
				setState(142);
				((ExpContext)_localctx).exp1 = exp1();
				((ExpContext)_localctx).node =  ((ExpContext)_localctx).exp1.node;
				setState(154);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__15 || _la==T__16) {
					{
					setState(152);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case T__15:
						{
						setState(144);
						match(T__15);
						setState(145);
						((ExpContext)_localctx).exp1 = exp1();
						((ExpContext)_localctx).node =  new AndNode(_localctx.node, ((ExpContext)_localctx).exp1.node);
						}
						break;
					case T__16:
						{
						setState(148);
						match(T__16);
						setState(149);
						((ExpContext)_localctx).exp1 = exp1();
						((ExpContext)_localctx).node =  new OrNode(_localctx.node, ((ExpContext)_localctx).exp1.node);
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					}
					setState(156);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
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
	public static class Exp1Context extends ParserRuleContext {
		public ASTNode node;
		public Exp2Context exp2;
		public List<Exp2Context> exp2() {
			return getRuleContexts(Exp2Context.class);
		}
		public Exp2Context exp2(int i) {
			return getRuleContext(Exp2Context.class,i);
		}
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
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(159);
			((Exp1Context)_localctx).exp2 = exp2();
			((Exp1Context)_localctx).node =  ((Exp1Context)_localctx).exp2.node;
			setState(171);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__17 || _la==T__18) {
				{
				setState(169);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__17:
					{
					setState(161);
					match(T__17);
					setState(162);
					((Exp1Context)_localctx).exp2 = exp2();
					((Exp1Context)_localctx).node =  new EqualNode(_localctx.node, ((Exp1Context)_localctx).exp2.node);
					}
					break;
				case T__18:
					{
					setState(165);
					match(T__18);
					setState(166);
					((Exp1Context)_localctx).exp2 = exp2();
					((Exp1Context)_localctx).node =  new SupNode(_localctx.node, ((Exp1Context)_localctx).exp2.node);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(173);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
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
	public static class Exp2Context extends ParserRuleContext {
		public ASTNode node;
		public TermeContext terme;
		public List<TermeContext> terme() {
			return getRuleContexts(TermeContext.class);
		}
		public TermeContext terme(int i) {
			return getRuleContext(TermeContext.class,i);
		}
		public Exp2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exp2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).enterExp2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).exitExp2(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJaJaVisitor ) return ((MiniJaJaVisitor<? extends T>)visitor).visitExp2(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Exp2Context exp2() throws RecognitionException {
		Exp2Context _localctx = new Exp2Context(_ctx, getState());
		enterRule(_localctx, 16, RULE_exp2);
		int _la;
		try {
			setState(205);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__19:
				enterOuterAlt(_localctx, 1);
				{
				setState(174);
				match(T__19);
				setState(175);
				((Exp2Context)_localctx).terme = terme();
				((Exp2Context)_localctx).node =  new UnMinusNode(((Exp2Context)_localctx).terme.node);
				setState(187);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__19 || _la==T__20) {
					{
					setState(185);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case T__20:
						{
						setState(177);
						match(T__20);
						setState(178);
						((Exp2Context)_localctx).terme = terme();
						((Exp2Context)_localctx).node =  new AddNode(_localctx.node, ((Exp2Context)_localctx).terme.node);
						}
						break;
					case T__19:
						{
						setState(181);
						match(T__19);
						setState(182);
						((Exp2Context)_localctx).terme = terme();
						((Exp2Context)_localctx).node =  new BinMinusNode(_localctx.node, ((Exp2Context)_localctx).terme.node);
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					}
					setState(189);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case T__6:
			case T__23:
			case T__24:
			case IDENTIFIER:
			case NOMBRE:
				enterOuterAlt(_localctx, 2);
				{
				setState(190);
				((Exp2Context)_localctx).terme = terme();
				((Exp2Context)_localctx).node =  ((Exp2Context)_localctx).terme.node;
				setState(202);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__19 || _la==T__20) {
					{
					setState(200);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case T__20:
						{
						setState(192);
						match(T__20);
						setState(193);
						((Exp2Context)_localctx).terme = terme();
						((Exp2Context)_localctx).node =  new AddNode(_localctx.node, ((Exp2Context)_localctx).terme.node);
						}
						break;
					case T__19:
						{
						setState(196);
						match(T__19);
						setState(197);
						((Exp2Context)_localctx).terme = terme();
						((Exp2Context)_localctx).node =  new BinMinusNode(_localctx.node, ((Exp2Context)_localctx).terme.node);
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					}
					setState(204);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
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
	public static class TermeContext extends ParserRuleContext {
		public ASTNode node;
		public FactContext fact;
		public List<FactContext> fact() {
			return getRuleContexts(FactContext.class);
		}
		public FactContext fact(int i) {
			return getRuleContext(FactContext.class,i);
		}
		public TermeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_terme; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).enterTerme(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).exitTerme(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJaJaVisitor ) return ((MiniJaJaVisitor<? extends T>)visitor).visitTerme(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TermeContext terme() throws RecognitionException {
		TermeContext _localctx = new TermeContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_terme);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(207);
			((TermeContext)_localctx).fact = fact();
			((TermeContext)_localctx).node =  ((TermeContext)_localctx).fact.node;
			setState(219);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__21 || _la==T__22) {
				{
				setState(217);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__21:
					{
					setState(209);
					match(T__21);
					setState(210);
					((TermeContext)_localctx).fact = fact();
					((TermeContext)_localctx).node =  new MulNode(_localctx.node, ((TermeContext)_localctx).fact.node);
					}
					break;
				case T__22:
					{
					setState(213);
					match(T__22);
					setState(214);
					((TermeContext)_localctx).fact = fact();
					((TermeContext)_localctx).node =  new DivNode(_localctx.node, ((TermeContext)_localctx).fact.node);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(221);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
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
	public static class FactContext extends ParserRuleContext {
		public ASTNode node;
		public Ident1Context ident1;
		public Token n;
		public ExpContext exp;
		public Ident1Context ident1() {
			return getRuleContext(Ident1Context.class,0);
		}
		public TerminalNode NOMBRE() { return getToken(MiniJaJaParser.NOMBRE, 0); }
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public FactContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fact; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).enterFact(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).exitFact(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJaJaVisitor ) return ((MiniJaJaVisitor<? extends T>)visitor).visitFact(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FactContext fact() throws RecognitionException {
		FactContext _localctx = new FactContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_fact);
		try {
			setState(236);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(222);
				((FactContext)_localctx).ident1 = ident1();
				((FactContext)_localctx).node =  ((FactContext)_localctx).ident1.node;
				}
				break;
			case T__23:
				enterOuterAlt(_localctx, 2);
				{
				setState(225);
				match(T__23);
				((FactContext)_localctx).node =  new BooleanNode(true);
				}
				break;
			case T__24:
				enterOuterAlt(_localctx, 3);
				{
				setState(227);
				match(T__24);
				((FactContext)_localctx).node =  new BooleanNode(false);
				}
				break;
			case NOMBRE:
				enterOuterAlt(_localctx, 4);
				{
				setState(229);
				((FactContext)_localctx).n = match(NOMBRE);
				((FactContext)_localctx).node =  new NumberNode(Integer.parseInt((((FactContext)_localctx).n!=null?((FactContext)_localctx).n.getText():null)));
				}
				break;
			case T__6:
				enterOuterAlt(_localctx, 5);
				{
				setState(231);
				match(T__6);
				setState(232);
				((FactContext)_localctx).exp = exp();
				setState(233);
				match(T__7);
				((FactContext)_localctx).node =  ((FactContext)_localctx).exp.node;
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
	public static class Ident1Context extends ParserRuleContext {
		public ASTNode node;
		public IdentContext ident;
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public Ident1Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ident1; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).enterIdent1(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).exitIdent1(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJaJaVisitor ) return ((MiniJaJaVisitor<? extends T>)visitor).visitIdent1(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Ident1Context ident1() throws RecognitionException {
		Ident1Context _localctx = new Ident1Context(_ctx, getState());
		enterRule(_localctx, 22, RULE_ident1);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(238);
			((Ident1Context)_localctx).ident = ident();
			((Ident1Context)_localctx).node =  ((Ident1Context)_localctx).ident.node;
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
		enterRule(_localctx, 24, RULE_decl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(241);
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
		enterRule(_localctx, 26, RULE_vars);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(244);
			((VarsContext)_localctx).var = var();
			setState(245);
			match(T__3);
			setState(247);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1006632960L) != 0)) {
				{
				setState(246);
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
		enterRule(_localctx, 28, RULE_var);
		int _la;
		try {
			setState(264);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__26:
			case T__27:
			case T__28:
				enterOuterAlt(_localctx, 1);
				{
				setState(251);
				((VarContext)_localctx).typemeth = typemeth();
				setState(252);
				((VarContext)_localctx).ident = ident();
				setState(253);
				((VarContext)_localctx).varPrime = varPrime();
				((VarContext)_localctx).node =  new VariableNode(((VarContext)_localctx).typemeth.node, ((VarContext)_localctx).ident.node, ((VarContext)_localctx).varPrime.node);
				}
				break;
			case T__25:
				enterOuterAlt(_localctx, 2);
				{
				setState(256);
				match(T__25);
				setState(257);
				((VarContext)_localctx).type = type();
				setState(258);
				((VarContext)_localctx).ident = ident();
				setState(260);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__9) {
					{
					setState(259);
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
		enterRule(_localctx, 30, RULE_varPrime);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(267);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__9) {
				{
				setState(266);
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
		enterRule(_localctx, 32, RULE_typemeth);
		try {
			setState(276);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__27:
			case T__28:
				enterOuterAlt(_localctx, 1);
				{
				setState(271);
				((TypemethContext)_localctx).type = type();
				((TypemethContext)_localctx).node =  ((TypemethContext)_localctx).type.node;
				}
				break;
			case T__26:
				enterOuterAlt(_localctx, 2);
				{
				setState(274);
				match(T__26);
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
		enterRule(_localctx, 34, RULE_type);
		try {
			setState(282);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__27:
				enterOuterAlt(_localctx, 1);
				{
				setState(278);
				match(T__27);
				((TypeContext)_localctx).node =  new TypeNode(ValueType.INT);
				}
				break;
			case T__28:
				enterOuterAlt(_localctx, 2);
				{
				setState(280);
				match(T__28);
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
		enterRule(_localctx, 36, RULE_vexp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(284);
			match(T__9);
			setState(285);
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
		"\u0004\u0001\u001f\u0121\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001"+
		"\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004"+
		"\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007"+
		"\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b"+
		"\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007"+
		"\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007"+
		"\u0012\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0003\u0000+\b"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0003\u00027\b"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0003"+
		"\u0003>\b\u0003\u0001\u0003\u0003\u0003A\b\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0003\u0004I\b\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0003\u0005S\b\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0003\u0005"+
		"h\b\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0003\u0005p\b\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0003\u0005v\b\u0005\u0001\u0005\u0003\u0005y\b\u0005\u0001"+
		"\u0005\u0001\u0005\u0003\u0005}\b\u0005\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0005\u0006\u008a\b\u0006\n\u0006\f\u0006"+
		"\u008d\t\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0005\u0006"+
		"\u0099\b\u0006\n\u0006\f\u0006\u009c\t\u0006\u0003\u0006\u009e\b\u0006"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0005\u0007\u00aa\b\u0007"+
		"\n\u0007\f\u0007\u00ad\t\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b"+
		"\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0005\b\u00ba\b\b\n\b"+
		"\f\b\u00bd\t\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b"+
		"\u0001\b\u0001\b\u0001\b\u0005\b\u00c9\b\b\n\b\f\b\u00cc\t\b\u0003\b\u00ce"+
		"\b\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0005\t\u00da\b\t\n\t\f\t\u00dd\t\t\u0001\n\u0001\n\u0001\n"+
		"\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0003\n\u00ed\b\n\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001\r\u0003\r\u00f8\b\r\u0001"+
		"\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0003\u000e\u0105\b\u000e"+
		"\u0001\u000e\u0001\u000e\u0003\u000e\u0109\b\u000e\u0001\u000f\u0003\u000f"+
		"\u010c\b\u000f\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0003\u0010\u0115\b\u0010\u0001\u0011\u0001\u0011"+
		"\u0001\u0011\u0001\u0011\u0003\u0011\u011b\b\u0011\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0000\u0000\u0013\u0000\u0002\u0004"+
		"\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e \""+
		"$\u0000\u0000\u0133\u0000&\u0001\u0000\u0000\u0000\u00020\u0001\u0000"+
		"\u0000\u0000\u00043\u0001\u0000\u0000\u0000\u0006:\u0001\u0000\u0000\u0000"+
		"\bE\u0001\u0000\u0000\u0000\n|\u0001\u0000\u0000\u0000\f\u009d\u0001\u0000"+
		"\u0000\u0000\u000e\u009f\u0001\u0000\u0000\u0000\u0010\u00cd\u0001\u0000"+
		"\u0000\u0000\u0012\u00cf\u0001\u0000\u0000\u0000\u0014\u00ec\u0001\u0000"+
		"\u0000\u0000\u0016\u00ee\u0001\u0000\u0000\u0000\u0018\u00f1\u0001\u0000"+
		"\u0000\u0000\u001a\u00f4\u0001\u0000\u0000\u0000\u001c\u0108\u0001\u0000"+
		"\u0000\u0000\u001e\u010b\u0001\u0000\u0000\u0000 \u0114\u0001\u0000\u0000"+
		"\u0000\"\u011a\u0001\u0000\u0000\u0000$\u011c\u0001\u0000\u0000\u0000"+
		"&\'\u0005\u0001\u0000\u0000\'(\u0003\u0002\u0001\u0000(*\u0005\u0002\u0000"+
		"\u0000)+\u0003\u0004\u0002\u0000*)\u0001\u0000\u0000\u0000*+\u0001\u0000"+
		"\u0000\u0000+,\u0001\u0000\u0000\u0000,-\u0003\u0006\u0003\u0000-.\u0005"+
		"\u0003\u0000\u0000./\u0006\u0000\uffff\uffff\u0000/\u0001\u0001\u0000"+
		"\u0000\u000001\u0005\u001e\u0000\u000012\u0006\u0001\uffff\uffff\u0000"+
		"2\u0003\u0001\u0000\u0000\u000034\u0003\u0018\f\u000046\u0005\u0004\u0000"+
		"\u000057\u0003\u0004\u0002\u000065\u0001\u0000\u0000\u000067\u0001\u0000"+
		"\u0000\u000078\u0001\u0000\u0000\u000089\u0006\u0002\uffff\uffff\u0000"+
		"9\u0005\u0001\u0000\u0000\u0000:;\u0005\u0005\u0000\u0000;=\u0005\u0002"+
		"\u0000\u0000<>\u0003\u001a\r\u0000=<\u0001\u0000\u0000\u0000=>\u0001\u0000"+
		"\u0000\u0000>@\u0001\u0000\u0000\u0000?A\u0003\b\u0004\u0000@?\u0001\u0000"+
		"\u0000\u0000@A\u0001\u0000\u0000\u0000AB\u0001\u0000\u0000\u0000BC\u0005"+
		"\u0003\u0000\u0000CD\u0006\u0003\uffff\uffff\u0000D\u0007\u0001\u0000"+
		"\u0000\u0000EF\u0003\n\u0005\u0000FH\u0005\u0004\u0000\u0000GI\u0003\b"+
		"\u0004\u0000HG\u0001\u0000\u0000\u0000HI\u0001\u0000\u0000\u0000IJ\u0001"+
		"\u0000\u0000\u0000JK\u0006\u0004\uffff\uffff\u0000K\t\u0001\u0000\u0000"+
		"\u0000LM\u0005\u0006\u0000\u0000MN\u0005\u0007\u0000\u0000NO\u0003\f\u0006"+
		"\u0000OP\u0005\b\u0000\u0000PR\u0005\u0002\u0000\u0000QS\u0003\b\u0004"+
		"\u0000RQ\u0001\u0000\u0000\u0000RS\u0001\u0000\u0000\u0000ST\u0001\u0000"+
		"\u0000\u0000TU\u0005\u0003\u0000\u0000UV\u0006\u0005\uffff\uffff\u0000"+
		"V}\u0001\u0000\u0000\u0000WX\u0005\t\u0000\u0000XY\u0003\f\u0006\u0000"+
		"YZ\u0006\u0005\uffff\uffff\u0000Z}\u0001\u0000\u0000\u0000[\\\u0003\u0016"+
		"\u000b\u0000\\g\u0006\u0005\uffff\uffff\u0000]^\u0005\n\u0000\u0000^_"+
		"\u0003\f\u0006\u0000_`\u0006\u0005\uffff\uffff\u0000`h\u0001\u0000\u0000"+
		"\u0000ab\u0005\u000b\u0000\u0000bc\u0003\f\u0006\u0000cd\u0006\u0005\uffff"+
		"\uffff\u0000dh\u0001\u0000\u0000\u0000ef\u0005\f\u0000\u0000fh\u0006\u0005"+
		"\uffff\uffff\u0000g]\u0001\u0000\u0000\u0000ga\u0001\u0000\u0000\u0000"+
		"ge\u0001\u0000\u0000\u0000h}\u0001\u0000\u0000\u0000ij\u0005\r\u0000\u0000"+
		"jk\u0005\u0007\u0000\u0000kl\u0003\f\u0006\u0000lm\u0005\b\u0000\u0000"+
		"mo\u0005\u0002\u0000\u0000np\u0003\b\u0004\u0000on\u0001\u0000\u0000\u0000"+
		"op\u0001\u0000\u0000\u0000pq\u0001\u0000\u0000\u0000qx\u0005\u0003\u0000"+
		"\u0000rs\u0005\u000e\u0000\u0000su\u0005\u0002\u0000\u0000tv\u0003\b\u0004"+
		"\u0000ut\u0001\u0000\u0000\u0000uv\u0001\u0000\u0000\u0000vw\u0001\u0000"+
		"\u0000\u0000wy\u0005\u0003\u0000\u0000xr\u0001\u0000\u0000\u0000xy\u0001"+
		"\u0000\u0000\u0000yz\u0001\u0000\u0000\u0000z{\u0006\u0005\uffff\uffff"+
		"\u0000{}\u0001\u0000\u0000\u0000|L\u0001\u0000\u0000\u0000|W\u0001\u0000"+
		"\u0000\u0000|[\u0001\u0000\u0000\u0000|i\u0001\u0000\u0000\u0000}\u000b"+
		"\u0001\u0000\u0000\u0000~\u007f\u0005\u000f\u0000\u0000\u007f\u0080\u0003"+
		"\u000e\u0007\u0000\u0080\u008b\u0006\u0006\uffff\uffff\u0000\u0081\u0082"+
		"\u0005\u0010\u0000\u0000\u0082\u0083\u0003\u000e\u0007\u0000\u0083\u0084"+
		"\u0006\u0006\uffff\uffff\u0000\u0084\u008a\u0001\u0000\u0000\u0000\u0085"+
		"\u0086\u0005\u0011\u0000\u0000\u0086\u0087\u0003\u000e\u0007\u0000\u0087"+
		"\u0088\u0006\u0006\uffff\uffff\u0000\u0088\u008a\u0001\u0000\u0000\u0000"+
		"\u0089\u0081\u0001\u0000\u0000\u0000\u0089\u0085\u0001\u0000\u0000\u0000"+
		"\u008a\u008d\u0001\u0000\u0000\u0000\u008b\u0089\u0001\u0000\u0000\u0000"+
		"\u008b\u008c\u0001\u0000\u0000\u0000\u008c\u009e\u0001\u0000\u0000\u0000"+
		"\u008d\u008b\u0001\u0000\u0000\u0000\u008e\u008f\u0003\u000e\u0007\u0000"+
		"\u008f\u009a\u0006\u0006\uffff\uffff\u0000\u0090\u0091\u0005\u0010\u0000"+
		"\u0000\u0091\u0092\u0003\u000e\u0007\u0000\u0092\u0093\u0006\u0006\uffff"+
		"\uffff\u0000\u0093\u0099\u0001\u0000\u0000\u0000\u0094\u0095\u0005\u0011"+
		"\u0000\u0000\u0095\u0096\u0003\u000e\u0007\u0000\u0096\u0097\u0006\u0006"+
		"\uffff\uffff\u0000\u0097\u0099\u0001\u0000\u0000\u0000\u0098\u0090\u0001"+
		"\u0000\u0000\u0000\u0098\u0094\u0001\u0000\u0000\u0000\u0099\u009c\u0001"+
		"\u0000\u0000\u0000\u009a\u0098\u0001\u0000\u0000\u0000\u009a\u009b\u0001"+
		"\u0000\u0000\u0000\u009b\u009e\u0001\u0000\u0000\u0000\u009c\u009a\u0001"+
		"\u0000\u0000\u0000\u009d~\u0001\u0000\u0000\u0000\u009d\u008e\u0001\u0000"+
		"\u0000\u0000\u009e\r\u0001\u0000\u0000\u0000\u009f\u00a0\u0003\u0010\b"+
		"\u0000\u00a0\u00ab\u0006\u0007\uffff\uffff\u0000\u00a1\u00a2\u0005\u0012"+
		"\u0000\u0000\u00a2\u00a3\u0003\u0010\b\u0000\u00a3\u00a4\u0006\u0007\uffff"+
		"\uffff\u0000\u00a4\u00aa\u0001\u0000\u0000\u0000\u00a5\u00a6\u0005\u0013"+
		"\u0000\u0000\u00a6\u00a7\u0003\u0010\b\u0000\u00a7\u00a8\u0006\u0007\uffff"+
		"\uffff\u0000\u00a8\u00aa\u0001\u0000\u0000\u0000\u00a9\u00a1\u0001\u0000"+
		"\u0000\u0000\u00a9\u00a5\u0001\u0000\u0000\u0000\u00aa\u00ad\u0001\u0000"+
		"\u0000\u0000\u00ab\u00a9\u0001\u0000\u0000\u0000\u00ab\u00ac\u0001\u0000"+
		"\u0000\u0000\u00ac\u000f\u0001\u0000\u0000\u0000\u00ad\u00ab\u0001\u0000"+
		"\u0000\u0000\u00ae\u00af\u0005\u0014\u0000\u0000\u00af\u00b0\u0003\u0012"+
		"\t\u0000\u00b0\u00bb\u0006\b\uffff\uffff\u0000\u00b1\u00b2\u0005\u0015"+
		"\u0000\u0000\u00b2\u00b3\u0003\u0012\t\u0000\u00b3\u00b4\u0006\b\uffff"+
		"\uffff\u0000\u00b4\u00ba\u0001\u0000\u0000\u0000\u00b5\u00b6\u0005\u0014"+
		"\u0000\u0000\u00b6\u00b7\u0003\u0012\t\u0000\u00b7\u00b8\u0006\b\uffff"+
		"\uffff\u0000\u00b8\u00ba\u0001\u0000\u0000\u0000\u00b9\u00b1\u0001\u0000"+
		"\u0000\u0000\u00b9\u00b5\u0001\u0000\u0000\u0000\u00ba\u00bd\u0001\u0000"+
		"\u0000\u0000\u00bb\u00b9\u0001\u0000\u0000\u0000\u00bb\u00bc\u0001\u0000"+
		"\u0000\u0000\u00bc\u00ce\u0001\u0000\u0000\u0000\u00bd\u00bb\u0001\u0000"+
		"\u0000\u0000\u00be\u00bf\u0003\u0012\t\u0000\u00bf\u00ca\u0006\b\uffff"+
		"\uffff\u0000\u00c0\u00c1\u0005\u0015\u0000\u0000\u00c1\u00c2\u0003\u0012"+
		"\t\u0000\u00c2\u00c3\u0006\b\uffff\uffff\u0000\u00c3\u00c9\u0001\u0000"+
		"\u0000\u0000\u00c4\u00c5\u0005\u0014\u0000\u0000\u00c5\u00c6\u0003\u0012"+
		"\t\u0000\u00c6\u00c7\u0006\b\uffff\uffff\u0000\u00c7\u00c9\u0001\u0000"+
		"\u0000\u0000\u00c8\u00c0\u0001\u0000\u0000\u0000\u00c8\u00c4\u0001\u0000"+
		"\u0000\u0000\u00c9\u00cc\u0001\u0000\u0000\u0000\u00ca\u00c8\u0001\u0000"+
		"\u0000\u0000\u00ca\u00cb\u0001\u0000\u0000\u0000\u00cb\u00ce\u0001\u0000"+
		"\u0000\u0000\u00cc\u00ca\u0001\u0000\u0000\u0000\u00cd\u00ae\u0001\u0000"+
		"\u0000\u0000\u00cd\u00be\u0001\u0000\u0000\u0000\u00ce\u0011\u0001\u0000"+
		"\u0000\u0000\u00cf\u00d0\u0003\u0014\n\u0000\u00d0\u00db\u0006\t\uffff"+
		"\uffff\u0000\u00d1\u00d2\u0005\u0016\u0000\u0000\u00d2\u00d3\u0003\u0014"+
		"\n\u0000\u00d3\u00d4\u0006\t\uffff\uffff\u0000\u00d4\u00da\u0001\u0000"+
		"\u0000\u0000\u00d5\u00d6\u0005\u0017\u0000\u0000\u00d6\u00d7\u0003\u0014"+
		"\n\u0000\u00d7\u00d8\u0006\t\uffff\uffff\u0000\u00d8\u00da\u0001\u0000"+
		"\u0000\u0000\u00d9\u00d1\u0001\u0000\u0000\u0000\u00d9\u00d5\u0001\u0000"+
		"\u0000\u0000\u00da\u00dd\u0001\u0000\u0000\u0000\u00db\u00d9\u0001\u0000"+
		"\u0000\u0000\u00db\u00dc\u0001\u0000\u0000\u0000\u00dc\u0013\u0001\u0000"+
		"\u0000\u0000\u00dd\u00db\u0001\u0000\u0000\u0000\u00de\u00df\u0003\u0016"+
		"\u000b\u0000\u00df\u00e0\u0006\n\uffff\uffff\u0000\u00e0\u00ed\u0001\u0000"+
		"\u0000\u0000\u00e1\u00e2\u0005\u0018\u0000\u0000\u00e2\u00ed\u0006\n\uffff"+
		"\uffff\u0000\u00e3\u00e4\u0005\u0019\u0000\u0000\u00e4\u00ed\u0006\n\uffff"+
		"\uffff\u0000\u00e5\u00e6\u0005\u001f\u0000\u0000\u00e6\u00ed\u0006\n\uffff"+
		"\uffff\u0000\u00e7\u00e8\u0005\u0007\u0000\u0000\u00e8\u00e9\u0003\f\u0006"+
		"\u0000\u00e9\u00ea\u0005\b\u0000\u0000\u00ea\u00eb\u0006\n\uffff\uffff"+
		"\u0000\u00eb\u00ed\u0001\u0000\u0000\u0000\u00ec\u00de\u0001\u0000\u0000"+
		"\u0000\u00ec\u00e1\u0001\u0000\u0000\u0000\u00ec\u00e3\u0001\u0000\u0000"+
		"\u0000\u00ec\u00e5\u0001\u0000\u0000\u0000\u00ec\u00e7\u0001\u0000\u0000"+
		"\u0000\u00ed\u0015\u0001\u0000\u0000\u0000\u00ee\u00ef\u0003\u0002\u0001"+
		"\u0000\u00ef\u00f0\u0006\u000b\uffff\uffff\u0000\u00f0\u0017\u0001\u0000"+
		"\u0000\u0000\u00f1\u00f2\u0003\u001c\u000e\u0000\u00f2\u00f3\u0006\f\uffff"+
		"\uffff\u0000\u00f3\u0019\u0001\u0000\u0000\u0000\u00f4\u00f5\u0003\u001c"+
		"\u000e\u0000\u00f5\u00f7\u0005\u0004\u0000\u0000\u00f6\u00f8\u0003\u001a"+
		"\r\u0000\u00f7\u00f6\u0001\u0000\u0000\u0000\u00f7\u00f8\u0001\u0000\u0000"+
		"\u0000\u00f8\u00f9\u0001\u0000\u0000\u0000\u00f9\u00fa\u0006\r\uffff\uffff"+
		"\u0000\u00fa\u001b\u0001\u0000\u0000\u0000\u00fb\u00fc\u0003 \u0010\u0000"+
		"\u00fc\u00fd\u0003\u0002\u0001\u0000\u00fd\u00fe\u0003\u001e\u000f\u0000"+
		"\u00fe\u00ff\u0006\u000e\uffff\uffff\u0000\u00ff\u0109\u0001\u0000\u0000"+
		"\u0000\u0100\u0101\u0005\u001a\u0000\u0000\u0101\u0102\u0003\"\u0011\u0000"+
		"\u0102\u0104\u0003\u0002\u0001\u0000\u0103\u0105\u0003$\u0012\u0000\u0104"+
		"\u0103\u0001\u0000\u0000\u0000\u0104\u0105\u0001\u0000\u0000\u0000\u0105"+
		"\u0106\u0001\u0000\u0000\u0000\u0106\u0107\u0006\u000e\uffff\uffff\u0000"+
		"\u0107\u0109\u0001\u0000\u0000\u0000\u0108\u00fb\u0001\u0000\u0000\u0000"+
		"\u0108\u0100\u0001\u0000\u0000\u0000\u0109\u001d\u0001\u0000\u0000\u0000"+
		"\u010a\u010c\u0003$\u0012\u0000\u010b\u010a\u0001\u0000\u0000\u0000\u010b"+
		"\u010c\u0001\u0000\u0000\u0000\u010c\u010d\u0001\u0000\u0000\u0000\u010d"+
		"\u010e\u0006\u000f\uffff\uffff\u0000\u010e\u001f\u0001\u0000\u0000\u0000"+
		"\u010f\u0110\u0003\"\u0011\u0000\u0110\u0111\u0006\u0010\uffff\uffff\u0000"+
		"\u0111\u0115\u0001\u0000\u0000\u0000\u0112\u0113\u0005\u001b\u0000\u0000"+
		"\u0113\u0115\u0006\u0010\uffff\uffff\u0000\u0114\u010f\u0001\u0000\u0000"+
		"\u0000\u0114\u0112\u0001\u0000\u0000\u0000\u0115!\u0001\u0000\u0000\u0000"+
		"\u0116\u0117\u0005\u001c\u0000\u0000\u0117\u011b\u0006\u0011\uffff\uffff"+
		"\u0000\u0118\u0119\u0005\u001d\u0000\u0000\u0119\u011b\u0006\u0011\uffff"+
		"\uffff\u0000\u011a\u0116\u0001\u0000\u0000\u0000\u011a\u0118\u0001\u0000"+
		"\u0000\u0000\u011b#\u0001\u0000\u0000\u0000\u011c\u011d\u0005\n\u0000"+
		"\u0000\u011d\u011e\u0003\f\u0006\u0000\u011e\u011f\u0006\u0012\uffff\uffff"+
		"\u0000\u011f%\u0001\u0000\u0000\u0000 *6=@HRgoux|\u0089\u008b\u0098\u009a"+
		"\u009d\u00a9\u00ab\u00b9\u00bb\u00c8\u00ca\u00cd\u00d9\u00db\u00ec\u00f7"+
		"\u0104\u0108\u010b\u0114\u011a";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}