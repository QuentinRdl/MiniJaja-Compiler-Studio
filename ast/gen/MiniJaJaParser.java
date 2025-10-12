// Generated from /home/utilisateur/Documents/Master 1/M1 - Semestre 7/Projet COMP/groupe-5/ast/src/main/antlr4/MiniJaJa.g4 by ANTLR 4.13.2

package fr.ufrst.m1info.pvm.group5;
import fr.ufrst.m1info.pvm.group5.Nodes.*;
import fr.ufrst.m1info.pvm.group5.ValueType;

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
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, IDENTIFIER=30, NOMBRE=31, 
		WS=32;
	public static final int
		RULE_classe = 0, RULE_ident = 1, RULE_decls = 2, RULE_decl = 3, RULE_vars = 4, 
		RULE_var = 5, RULE_vexp = 6, RULE_methode = 7, RULE_methmain = 8, RULE_entetes = 9, 
		RULE_entete = 10, RULE_instrs = 11, RULE_instr = 12, RULE_listexp = 13, 
		RULE_exp = 14, RULE_exp1 = 15, RULE_exp2 = 16, RULE_terme = 17, RULE_fact = 18, 
		RULE_ident1 = 19, RULE_typemeth = 20, RULE_type = 21;
	private static String[] makeRuleNames() {
		return new String[] {
			"classe", "ident", "decls", "decl", "vars", "var", "vexp", "methode", 
			"methmain", "entetes", "entete", "instrs", "instr", "listexp", "exp", 
			"exp1", "exp2", "terme", "fact", "ident1", "typemeth", "type"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'class'", "'{'", "'}'", "';'", "'final'", "'='", "'main'", "'while'", 
			"'('", "')'", "'return'", "'+='", "'++'", "'if'", "'else'", "'!'", "'&&'", 
			"'||'", "'=='", "'>'", "'-'", "'+'", "'*'", "'/'", "'true'", "'false'", 
			"'void'", "'int'", "'boolean'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, "IDENTIFIER", "NOMBRE", "WS"
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
		boolean declsflag = false;
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(44);
			match(T__0);
			setState(45);
			((ClasseContext)_localctx).ident = ident();
			setState(46);
			match(T__1);
			setState(50);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 939524128L) != 0)) {
				{
				setState(47);
				((ClasseContext)_localctx).decls = decls();
				declsflag = true;
				}
			}

			setState(52);
			((ClasseContext)_localctx).methmain = methmain();
			setState(53);
			match(T__2);
			((ClasseContext)_localctx).node =  new ClassNode(((ClasseContext)_localctx).ident.node, (declsflag)?((ClasseContext)_localctx).decls.node:null, ((ClasseContext)_localctx).methmain.node);
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
			setState(56);
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
			setState(59);
			((DeclsContext)_localctx).decl = decl();
			setState(60);
			match(T__3);
			((DeclsContext)_localctx).node =  new DeclarationsNode(((DeclsContext)_localctx).decl.node, null);
			setState(65);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 939524128L) != 0)) {
				{
				setState(62);
				((DeclsContext)_localctx).decls = decls();
				((DeclsContext)_localctx).node =  new DeclarationsNode(((DeclsContext)_localctx).decl.node, ((DeclsContext)_localctx).decls.node);
				}
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
		enterRule(_localctx, 6, RULE_decl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(67);
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
		enterRule(_localctx, 8, RULE_vars);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(70);
			((VarsContext)_localctx).var = var();
			setState(71);
			match(T__3);
			((VarsContext)_localctx).node =  new VariablesNode(((VarsContext)_localctx).var.node, null);
			setState(76);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 939524128L) != 0)) {
				{
				setState(73);
				((VarsContext)_localctx).vars = vars();
				((VarsContext)_localctx).node =  new VariablesNode(((VarsContext)_localctx).var.node, ((VarsContext)_localctx).vars.node);
				}
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
	public static class VarContext extends ParserRuleContext {
		public ASTNode node;
		public TypemethContext typemeth;
		public IdentContext ident;
		public VexpContext vexp;
		public TypeContext type;
		public TypemethContext typemeth() {
			return getRuleContext(TypemethContext.class,0);
		}
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public VexpContext vexp() {
			return getRuleContext(VexpContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
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
		enterRule(_localctx, 10, RULE_var);
		int _la;
		try {
			setState(95);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__26:
			case T__27:
			case T__28:
				enterOuterAlt(_localctx, 1);
				{
				setState(78);
				((VarContext)_localctx).typemeth = typemeth();
				setState(79);
				((VarContext)_localctx).ident = ident();
				((VarContext)_localctx).node =  new VariableNode(((VarContext)_localctx).typemeth.node, ((VarContext)_localctx).ident.node, null);
				setState(84);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__5) {
					{
					setState(81);
					((VarContext)_localctx).vexp = vexp();
					((VarContext)_localctx).node =  new VariableNode(((VarContext)_localctx).typemeth.node, ((VarContext)_localctx).ident.node, ((VarContext)_localctx).vexp.node);
					}
				}

				}
				break;
			case T__4:
				enterOuterAlt(_localctx, 2);
				{
				setState(86);
				match(T__4);
				setState(87);
				((VarContext)_localctx).type = type();
				setState(88);
				((VarContext)_localctx).ident = ident();
				((VarContext)_localctx).node =  new FinalNode(((VarContext)_localctx).type.node, ((VarContext)_localctx).ident.node, null);
				setState(93);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__5) {
					{
					setState(90);
					((VarContext)_localctx).vexp = vexp();
					((VarContext)_localctx).node =  new FinalNode(((VarContext)_localctx).type.node, ((VarContext)_localctx).ident.node, ((VarContext)_localctx).vexp.node);
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
		enterRule(_localctx, 12, RULE_vexp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(97);
			match(T__5);
			setState(98);
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

	@SuppressWarnings("CheckReturnValue")
	public static class MethodeContext extends ParserRuleContext {
		public ASTNode node;
		public MethodeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methode; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).enterMethode(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).exitMethode(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJaJaVisitor ) return ((MiniJaJaVisitor<? extends T>)visitor).visitMethode(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodeContext methode() throws RecognitionException {
		MethodeContext _localctx = new MethodeContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_methode);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(101);
			matchWildcard();
			((MethodeContext)_localctx).node =  null;
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
		enterRule(_localctx, 16, RULE_methmain);
		boolean varsflag = false; boolean instrsflag = false;
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(104);
			match(T__6);
			setState(105);
			match(T__1);
			setState(109);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 939524128L) != 0)) {
				{
				setState(106);
				((MethmainContext)_localctx).vars = vars();
				varsflag = true;
				}
			}

			setState(114);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1073760512L) != 0)) {
				{
				setState(111);
				((MethmainContext)_localctx).instrs = instrs();
				instrsflag = true;
				}
			}

			setState(116);
			match(T__2);
			((MethmainContext)_localctx).node =  new MainNode((varsflag)?((MethmainContext)_localctx).vars.node:null, (instrsflag)?((MethmainContext)_localctx).instrs.node:null);
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
	public static class EntetesContext extends ParserRuleContext {
		public ASTNode node;
		public EntetesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_entetes; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).enterEntetes(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).exitEntetes(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJaJaVisitor ) return ((MiniJaJaVisitor<? extends T>)visitor).visitEntetes(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EntetesContext entetes() throws RecognitionException {
		EntetesContext _localctx = new EntetesContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_entetes);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(119);
			matchWildcard();
			((EntetesContext)_localctx).node =  null;
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
	public static class EnteteContext extends ParserRuleContext {
		public ASTNode node;
		public EnteteContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_entete; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).enterEntete(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).exitEntete(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJaJaVisitor ) return ((MiniJaJaVisitor<? extends T>)visitor).visitEntete(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EnteteContext entete() throws RecognitionException {
		EnteteContext _localctx = new EnteteContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_entete);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(122);
			matchWildcard();
			((EnteteContext)_localctx).node =  null;
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
		enterRule(_localctx, 22, RULE_instrs);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(125);
			((InstrsContext)_localctx).instr = instr();
			setState(126);
			match(T__3);
			((InstrsContext)_localctx).node =  new InstructionsNode(((InstrsContext)_localctx).instr.node, null);
			setState(131);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1073760512L) != 0)) {
				{
				setState(128);
				((InstrsContext)_localctx).instrs = instrs();
				((InstrsContext)_localctx).node =  new InstructionsNode(((InstrsContext)_localctx).instr.node, ((InstrsContext)_localctx).instrs.node);
				}
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
	public static class InstrContext extends ParserRuleContext {
		public ASTNode node;
		public ExpContext exp;
		public InstrsContext instrs;
		public Ident1Context ident1;
		public InstrsContext i1;
		public InstrsContext i2;
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
		enterRule(_localctx, 24, RULE_instr);
		boolean instrsflag1 = false; boolean instrsflag2 = false;
		int _la;
		try {
			setState(187);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__7:
				enterOuterAlt(_localctx, 1);
				{
				setState(133);
				match(T__7);
				setState(134);
				match(T__8);
				setState(135);
				((InstrContext)_localctx).exp = exp();
				setState(136);
				match(T__9);
				setState(137);
				match(T__1);
				setState(141);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1073760512L) != 0)) {
					{
					setState(138);
					((InstrContext)_localctx).instrs = instrs();
					instrsflag1 = true;
					}
				}

				setState(143);
				match(T__2);
				((InstrContext)_localctx).node =  new WhileNode(((InstrContext)_localctx).exp.node, (instrsflag1)?((InstrContext)_localctx).instrs.node:null);
				}
				break;
			case T__10:
				enterOuterAlt(_localctx, 2);
				{
				setState(146);
				match(T__10);
				setState(147);
				((InstrContext)_localctx).exp = exp();
				((InstrContext)_localctx).node =  new ReturnNode(((InstrContext)_localctx).exp.node);
				}
				break;
			case IDENTIFIER:
				enterOuterAlt(_localctx, 3);
				{
				setState(150);
				((InstrContext)_localctx).ident1 = ident1();
				((InstrContext)_localctx).node =  ((InstrContext)_localctx).ident1.node;
				setState(162);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__5:
					{
					setState(152);
					match(T__5);
					setState(153);
					((InstrContext)_localctx).exp = exp();
					((InstrContext)_localctx).node =  new AffectationNode((IdentNode)_localctx.node, ((InstrContext)_localctx).exp.node);
					}
					break;
				case T__11:
					{
					setState(156);
					match(T__11);
					setState(157);
					((InstrContext)_localctx).exp = exp();
					((InstrContext)_localctx).node =  new SumNode((IdentNode)_localctx.node, ((InstrContext)_localctx).exp.node);
					}
					break;
				case T__12:
					{
					setState(160);
					match(T__12);
					((InstrContext)_localctx).node =  new IncNode((IdentNode)_localctx.node);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			case T__13:
				enterOuterAlt(_localctx, 4);
				{
				setState(164);
				match(T__13);
				setState(165);
				match(T__8);
				setState(166);
				((InstrContext)_localctx).exp = exp();
				setState(167);
				match(T__9);
				setState(168);
				match(T__1);
				setState(172);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1073760512L) != 0)) {
					{
					setState(169);
					((InstrContext)_localctx).i1 = instrs();
					instrsflag1 = true;
					}
				}

				setState(174);
				match(T__2);
				((InstrContext)_localctx).node =  new IfNode(((InstrContext)_localctx).exp.node,(instrsflag1)?((InstrContext)_localctx).i1.node:null,null);
				setState(185);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__14) {
					{
					setState(176);
					match(T__14);
					setState(177);
					match(T__1);
					setState(181);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1073760512L) != 0)) {
						{
						setState(178);
						((InstrContext)_localctx).i2 = instrs();
						instrsflag2 = true;
						}
					}

					setState(183);
					match(T__2);
					((InstrContext)_localctx).node =  new IfNode(((InstrContext)_localctx).exp.node,(instrsflag1)?((InstrContext)_localctx).i1.node:null,(instrsflag2)?((InstrContext)_localctx).i2.node:null);
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
	public static class ListexpContext extends ParserRuleContext {
		public ASTNode node;
		public ListexpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_listexp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).enterListexp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJaJaListener ) ((MiniJaJaListener)listener).exitListexp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJaJaVisitor ) return ((MiniJaJaVisitor<? extends T>)visitor).visitListexp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ListexpContext listexp() throws RecognitionException {
		ListexpContext _localctx = new ListexpContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_listexp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(189);
			matchWildcard();
			((ListexpContext)_localctx).node =  null;
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
		enterRule(_localctx, 28, RULE_exp);
		int _la;
		try {
			setState(223);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__15:
				enterOuterAlt(_localctx, 1);
				{
				setState(192);
				match(T__15);
				setState(193);
				((ExpContext)_localctx).exp1 = exp1();
				((ExpContext)_localctx).node =  new NotNode(((ExpContext)_localctx).exp1.node);
				setState(205);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__16 || _la==T__17) {
					{
					setState(203);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case T__16:
						{
						setState(195);
						match(T__16);
						setState(196);
						((ExpContext)_localctx).exp1 = exp1();
						((ExpContext)_localctx).node =  new AndNode(_localctx.node, ((ExpContext)_localctx).exp1.node);
						}
						break;
					case T__17:
						{
						setState(199);
						match(T__17);
						setState(200);
						((ExpContext)_localctx).exp1 = exp1();
						((ExpContext)_localctx).node =  new OrNode(_localctx.node, ((ExpContext)_localctx).exp1.node);
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					}
					setState(207);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case T__8:
			case T__20:
			case T__24:
			case T__25:
			case IDENTIFIER:
			case NOMBRE:
				enterOuterAlt(_localctx, 2);
				{
				setState(208);
				((ExpContext)_localctx).exp1 = exp1();
				((ExpContext)_localctx).node =  ((ExpContext)_localctx).exp1.node;
				setState(220);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__16 || _la==T__17) {
					{
					setState(218);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case T__16:
						{
						setState(210);
						match(T__16);
						setState(211);
						((ExpContext)_localctx).exp1 = exp1();
						((ExpContext)_localctx).node =  new AndNode(_localctx.node, ((ExpContext)_localctx).exp1.node);
						}
						break;
					case T__17:
						{
						setState(214);
						match(T__17);
						setState(215);
						((ExpContext)_localctx).exp1 = exp1();
						((ExpContext)_localctx).node =  new OrNode(_localctx.node, ((ExpContext)_localctx).exp1.node);
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					}
					setState(222);
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
		enterRule(_localctx, 30, RULE_exp1);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(225);
			((Exp1Context)_localctx).exp2 = exp2();
			((Exp1Context)_localctx).node =  ((Exp1Context)_localctx).exp2.node;
			setState(237);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__18 || _la==T__19) {
				{
				setState(235);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__18:
					{
					setState(227);
					match(T__18);
					setState(228);
					((Exp1Context)_localctx).exp2 = exp2();
					((Exp1Context)_localctx).node =  new EqualNode(_localctx.node, ((Exp1Context)_localctx).exp2.node);
					}
					break;
				case T__19:
					{
					setState(231);
					match(T__19);
					setState(232);
					((Exp1Context)_localctx).exp2 = exp2();
					((Exp1Context)_localctx).node =  new SupNode(_localctx.node, ((Exp1Context)_localctx).exp2.node);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(239);
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
		enterRule(_localctx, 32, RULE_exp2);
		int _la;
		try {
			setState(271);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__20:
				enterOuterAlt(_localctx, 1);
				{
				setState(240);
				match(T__20);
				setState(241);
				((Exp2Context)_localctx).terme = terme();
				((Exp2Context)_localctx).node =  new UnMinusNode(((Exp2Context)_localctx).terme.node);
				setState(253);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__20 || _la==T__21) {
					{
					setState(251);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case T__21:
						{
						setState(243);
						match(T__21);
						setState(244);
						((Exp2Context)_localctx).terme = terme();
						((Exp2Context)_localctx).node =  new AddNode(_localctx.node, ((Exp2Context)_localctx).terme.node);
						}
						break;
					case T__20:
						{
						setState(247);
						match(T__20);
						setState(248);
						((Exp2Context)_localctx).terme = terme();
						((Exp2Context)_localctx).node =  new BinMinusNode(_localctx.node, ((Exp2Context)_localctx).terme.node);
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					}
					setState(255);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case T__8:
			case T__24:
			case T__25:
			case IDENTIFIER:
			case NOMBRE:
				enterOuterAlt(_localctx, 2);
				{
				setState(256);
				((Exp2Context)_localctx).terme = terme();
				((Exp2Context)_localctx).node =  ((Exp2Context)_localctx).terme.node;
				setState(268);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__20 || _la==T__21) {
					{
					setState(266);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case T__21:
						{
						setState(258);
						match(T__21);
						setState(259);
						((Exp2Context)_localctx).terme = terme();
						((Exp2Context)_localctx).node =  new AddNode(_localctx.node, ((Exp2Context)_localctx).terme.node);
						}
						break;
					case T__20:
						{
						setState(262);
						match(T__20);
						setState(263);
						((Exp2Context)_localctx).terme = terme();
						((Exp2Context)_localctx).node =  new BinMinusNode(_localctx.node, ((Exp2Context)_localctx).terme.node);
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					}
					setState(270);
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
		enterRule(_localctx, 34, RULE_terme);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(273);
			((TermeContext)_localctx).fact = fact();
			((TermeContext)_localctx).node =  ((TermeContext)_localctx).fact.node;
			setState(285);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__22 || _la==T__23) {
				{
				setState(283);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__22:
					{
					setState(275);
					match(T__22);
					setState(276);
					((TermeContext)_localctx).fact = fact();
					((TermeContext)_localctx).node =  new MulNode(_localctx.node, ((TermeContext)_localctx).fact.node);
					}
					break;
				case T__23:
					{
					setState(279);
					match(T__23);
					setState(280);
					((TermeContext)_localctx).fact = fact();
					((TermeContext)_localctx).node =  new DivNode(_localctx.node, ((TermeContext)_localctx).fact.node);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(287);
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
		enterRule(_localctx, 36, RULE_fact);
		try {
			setState(302);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(288);
				((FactContext)_localctx).ident1 = ident1();
				((FactContext)_localctx).node =  ((FactContext)_localctx).ident1.node;
				}
				break;
			case T__24:
				enterOuterAlt(_localctx, 2);
				{
				setState(291);
				match(T__24);
				((FactContext)_localctx).node =  new BooleanNode(true);
				}
				break;
			case T__25:
				enterOuterAlt(_localctx, 3);
				{
				setState(293);
				match(T__25);
				((FactContext)_localctx).node =  new BooleanNode(false);
				}
				break;
			case NOMBRE:
				enterOuterAlt(_localctx, 4);
				{
				setState(295);
				((FactContext)_localctx).n = match(NOMBRE);
				((FactContext)_localctx).node =  new NumberNode(Integer.parseInt((((FactContext)_localctx).n!=null?((FactContext)_localctx).n.getText():null)));
				}
				break;
			case T__8:
				enterOuterAlt(_localctx, 5);
				{
				setState(297);
				match(T__8);
				setState(298);
				((FactContext)_localctx).exp = exp();
				setState(299);
				match(T__9);
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
		enterRule(_localctx, 38, RULE_ident1);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(304);
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
		enterRule(_localctx, 40, RULE_typemeth);
		try {
			setState(312);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__27:
			case T__28:
				enterOuterAlt(_localctx, 1);
				{
				setState(307);
				((TypemethContext)_localctx).type = type();
				((TypemethContext)_localctx).node =  ((TypemethContext)_localctx).type.node;
				}
				break;
			case T__26:
				enterOuterAlt(_localctx, 2);
				{
				setState(310);
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
		enterRule(_localctx, 42, RULE_type);
		try {
			setState(318);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__27:
				enterOuterAlt(_localctx, 1);
				{
				setState(314);
				match(T__27);
				((TypeContext)_localctx).node =  new TypeNode(ValueType.INT);
				}
				break;
			case T__28:
				enterOuterAlt(_localctx, 2);
				{
				setState(316);
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

	public static final String _serializedATN =
		"\u0004\u0001 \u0141\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0003\u00003\b\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0003\u0002B\b\u0002\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0003\u0004M\b\u0004\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0003\u0005U\b\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0003\u0005^\b\u0005\u0003\u0005`\b\u0005\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0003\bn\b\b\u0001\b\u0001\b\u0001"+
		"\b\u0003\bs\b\b\u0001\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001\t\u0001"+
		"\n\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0003\u000b\u0084\b\u000b\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\f\u0003\f\u008e\b\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0003\f\u00a3"+
		"\b\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0003"+
		"\f\u00ad\b\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0003"+
		"\f\u00b6\b\f\u0001\f\u0001\f\u0003\f\u00ba\b\f\u0003\f\u00bc\b\f\u0001"+
		"\r\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0005\u000e\u00cc\b\u000e\n\u000e\f\u000e\u00cf\t\u000e\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0005\u000e\u00db\b\u000e\n\u000e"+
		"\f\u000e\u00de\t\u000e\u0003\u000e\u00e0\b\u000e\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0005\u000f\u00ec\b\u000f\n\u000f\f\u000f\u00ef"+
		"\t\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0005"+
		"\u0010\u00fc\b\u0010\n\u0010\f\u0010\u00ff\t\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0005\u0010\u010b\b\u0010\n\u0010\f\u0010\u010e"+
		"\t\u0010\u0003\u0010\u0110\b\u0010\u0001\u0011\u0001\u0011\u0001\u0011"+
		"\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011"+
		"\u0001\u0011\u0005\u0011\u011c\b\u0011\n\u0011\f\u0011\u011f\t\u0011\u0001"+
		"\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0001\u0012\u0003\u0012\u012f\b\u0012\u0001\u0013\u0001\u0013\u0001"+
		"\u0013\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0003"+
		"\u0014\u0139\b\u0014\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0003"+
		"\u0015\u013f\b\u0015\u0001\u0015\u0000\u0000\u0016\u0000\u0002\u0004\u0006"+
		"\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e \"$&(*\u0000"+
		"\u0000\u0150\u0000,\u0001\u0000\u0000\u0000\u00028\u0001\u0000\u0000\u0000"+
		"\u0004;\u0001\u0000\u0000\u0000\u0006C\u0001\u0000\u0000\u0000\bF\u0001"+
		"\u0000\u0000\u0000\n_\u0001\u0000\u0000\u0000\fa\u0001\u0000\u0000\u0000"+
		"\u000ee\u0001\u0000\u0000\u0000\u0010h\u0001\u0000\u0000\u0000\u0012w"+
		"\u0001\u0000\u0000\u0000\u0014z\u0001\u0000\u0000\u0000\u0016}\u0001\u0000"+
		"\u0000\u0000\u0018\u00bb\u0001\u0000\u0000\u0000\u001a\u00bd\u0001\u0000"+
		"\u0000\u0000\u001c\u00df\u0001\u0000\u0000\u0000\u001e\u00e1\u0001\u0000"+
		"\u0000\u0000 \u010f\u0001\u0000\u0000\u0000\"\u0111\u0001\u0000\u0000"+
		"\u0000$\u012e\u0001\u0000\u0000\u0000&\u0130\u0001\u0000\u0000\u0000("+
		"\u0138\u0001\u0000\u0000\u0000*\u013e\u0001\u0000\u0000\u0000,-\u0005"+
		"\u0001\u0000\u0000-.\u0003\u0002\u0001\u0000.2\u0005\u0002\u0000\u0000"+
		"/0\u0003\u0004\u0002\u000001\u0006\u0000\uffff\uffff\u000013\u0001\u0000"+
		"\u0000\u00002/\u0001\u0000\u0000\u000023\u0001\u0000\u0000\u000034\u0001"+
		"\u0000\u0000\u000045\u0003\u0010\b\u000056\u0005\u0003\u0000\u000067\u0006"+
		"\u0000\uffff\uffff\u00007\u0001\u0001\u0000\u0000\u000089\u0005\u001e"+
		"\u0000\u00009:\u0006\u0001\uffff\uffff\u0000:\u0003\u0001\u0000\u0000"+
		"\u0000;<\u0003\u0006\u0003\u0000<=\u0005\u0004\u0000\u0000=A\u0006\u0002"+
		"\uffff\uffff\u0000>?\u0003\u0004\u0002\u0000?@\u0006\u0002\uffff\uffff"+
		"\u0000@B\u0001\u0000\u0000\u0000A>\u0001\u0000\u0000\u0000AB\u0001\u0000"+
		"\u0000\u0000B\u0005\u0001\u0000\u0000\u0000CD\u0003\n\u0005\u0000DE\u0006"+
		"\u0003\uffff\uffff\u0000E\u0007\u0001\u0000\u0000\u0000FG\u0003\n\u0005"+
		"\u0000GH\u0005\u0004\u0000\u0000HL\u0006\u0004\uffff\uffff\u0000IJ\u0003"+
		"\b\u0004\u0000JK\u0006\u0004\uffff\uffff\u0000KM\u0001\u0000\u0000\u0000"+
		"LI\u0001\u0000\u0000\u0000LM\u0001\u0000\u0000\u0000M\t\u0001\u0000\u0000"+
		"\u0000NO\u0003(\u0014\u0000OP\u0003\u0002\u0001\u0000PT\u0006\u0005\uffff"+
		"\uffff\u0000QR\u0003\f\u0006\u0000RS\u0006\u0005\uffff\uffff\u0000SU\u0001"+
		"\u0000\u0000\u0000TQ\u0001\u0000\u0000\u0000TU\u0001\u0000\u0000\u0000"+
		"U`\u0001\u0000\u0000\u0000VW\u0005\u0005\u0000\u0000WX\u0003*\u0015\u0000"+
		"XY\u0003\u0002\u0001\u0000Y]\u0006\u0005\uffff\uffff\u0000Z[\u0003\f\u0006"+
		"\u0000[\\\u0006\u0005\uffff\uffff\u0000\\^\u0001\u0000\u0000\u0000]Z\u0001"+
		"\u0000\u0000\u0000]^\u0001\u0000\u0000\u0000^`\u0001\u0000\u0000\u0000"+
		"_N\u0001\u0000\u0000\u0000_V\u0001\u0000\u0000\u0000`\u000b\u0001\u0000"+
		"\u0000\u0000ab\u0005\u0006\u0000\u0000bc\u0003\u001c\u000e\u0000cd\u0006"+
		"\u0006\uffff\uffff\u0000d\r\u0001\u0000\u0000\u0000ef\t\u0000\u0000\u0000"+
		"fg\u0006\u0007\uffff\uffff\u0000g\u000f\u0001\u0000\u0000\u0000hi\u0005"+
		"\u0007\u0000\u0000im\u0005\u0002\u0000\u0000jk\u0003\b\u0004\u0000kl\u0006"+
		"\b\uffff\uffff\u0000ln\u0001\u0000\u0000\u0000mj\u0001\u0000\u0000\u0000"+
		"mn\u0001\u0000\u0000\u0000nr\u0001\u0000\u0000\u0000op\u0003\u0016\u000b"+
		"\u0000pq\u0006\b\uffff\uffff\u0000qs\u0001\u0000\u0000\u0000ro\u0001\u0000"+
		"\u0000\u0000rs\u0001\u0000\u0000\u0000st\u0001\u0000\u0000\u0000tu\u0005"+
		"\u0003\u0000\u0000uv\u0006\b\uffff\uffff\u0000v\u0011\u0001\u0000\u0000"+
		"\u0000wx\t\u0000\u0000\u0000xy\u0006\t\uffff\uffff\u0000y\u0013\u0001"+
		"\u0000\u0000\u0000z{\t\u0000\u0000\u0000{|\u0006\n\uffff\uffff\u0000|"+
		"\u0015\u0001\u0000\u0000\u0000}~\u0003\u0018\f\u0000~\u007f\u0005\u0004"+
		"\u0000\u0000\u007f\u0083\u0006\u000b\uffff\uffff\u0000\u0080\u0081\u0003"+
		"\u0016\u000b\u0000\u0081\u0082\u0006\u000b\uffff\uffff\u0000\u0082\u0084"+
		"\u0001\u0000\u0000\u0000\u0083\u0080\u0001\u0000\u0000\u0000\u0083\u0084"+
		"\u0001\u0000\u0000\u0000\u0084\u0017\u0001\u0000\u0000\u0000\u0085\u0086"+
		"\u0005\b\u0000\u0000\u0086\u0087\u0005\t\u0000\u0000\u0087\u0088\u0003"+
		"\u001c\u000e\u0000\u0088\u0089\u0005\n\u0000\u0000\u0089\u008d\u0005\u0002"+
		"\u0000\u0000\u008a\u008b\u0003\u0016\u000b\u0000\u008b\u008c\u0006\f\uffff"+
		"\uffff\u0000\u008c\u008e\u0001\u0000\u0000\u0000\u008d\u008a\u0001\u0000"+
		"\u0000\u0000\u008d\u008e\u0001\u0000\u0000\u0000\u008e\u008f\u0001\u0000"+
		"\u0000\u0000\u008f\u0090\u0005\u0003\u0000\u0000\u0090\u0091\u0006\f\uffff"+
		"\uffff\u0000\u0091\u00bc\u0001\u0000\u0000\u0000\u0092\u0093\u0005\u000b"+
		"\u0000\u0000\u0093\u0094\u0003\u001c\u000e\u0000\u0094\u0095\u0006\f\uffff"+
		"\uffff\u0000\u0095\u00bc\u0001\u0000\u0000\u0000\u0096\u0097\u0003&\u0013"+
		"\u0000\u0097\u00a2\u0006\f\uffff\uffff\u0000\u0098\u0099\u0005\u0006\u0000"+
		"\u0000\u0099\u009a\u0003\u001c\u000e\u0000\u009a\u009b\u0006\f\uffff\uffff"+
		"\u0000\u009b\u00a3\u0001\u0000\u0000\u0000\u009c\u009d\u0005\f\u0000\u0000"+
		"\u009d\u009e\u0003\u001c\u000e\u0000\u009e\u009f\u0006\f\uffff\uffff\u0000"+
		"\u009f\u00a3\u0001\u0000\u0000\u0000\u00a0\u00a1\u0005\r\u0000\u0000\u00a1"+
		"\u00a3\u0006\f\uffff\uffff\u0000\u00a2\u0098\u0001\u0000\u0000\u0000\u00a2"+
		"\u009c\u0001\u0000\u0000\u0000\u00a2\u00a0\u0001\u0000\u0000\u0000\u00a3"+
		"\u00bc\u0001\u0000\u0000\u0000\u00a4\u00a5\u0005\u000e\u0000\u0000\u00a5"+
		"\u00a6\u0005\t\u0000\u0000\u00a6\u00a7\u0003\u001c\u000e\u0000\u00a7\u00a8"+
		"\u0005\n\u0000\u0000\u00a8\u00ac\u0005\u0002\u0000\u0000\u00a9\u00aa\u0003"+
		"\u0016\u000b\u0000\u00aa\u00ab\u0006\f\uffff\uffff\u0000\u00ab\u00ad\u0001"+
		"\u0000\u0000\u0000\u00ac\u00a9\u0001\u0000\u0000\u0000\u00ac\u00ad\u0001"+
		"\u0000\u0000\u0000\u00ad\u00ae\u0001\u0000\u0000\u0000\u00ae\u00af\u0005"+
		"\u0003\u0000\u0000\u00af\u00b9\u0006\f\uffff\uffff\u0000\u00b0\u00b1\u0005"+
		"\u000f\u0000\u0000\u00b1\u00b5\u0005\u0002\u0000\u0000\u00b2\u00b3\u0003"+
		"\u0016\u000b\u0000\u00b3\u00b4\u0006\f\uffff\uffff\u0000\u00b4\u00b6\u0001"+
		"\u0000\u0000\u0000\u00b5\u00b2\u0001\u0000\u0000\u0000\u00b5\u00b6\u0001"+
		"\u0000\u0000\u0000\u00b6\u00b7\u0001\u0000\u0000\u0000\u00b7\u00b8\u0005"+
		"\u0003\u0000\u0000\u00b8\u00ba\u0006\f\uffff\uffff\u0000\u00b9\u00b0\u0001"+
		"\u0000\u0000\u0000\u00b9\u00ba\u0001\u0000\u0000\u0000\u00ba\u00bc\u0001"+
		"\u0000\u0000\u0000\u00bb\u0085\u0001\u0000\u0000\u0000\u00bb\u0092\u0001"+
		"\u0000\u0000\u0000\u00bb\u0096\u0001\u0000\u0000\u0000\u00bb\u00a4\u0001"+
		"\u0000\u0000\u0000\u00bc\u0019\u0001\u0000\u0000\u0000\u00bd\u00be\t\u0000"+
		"\u0000\u0000\u00be\u00bf\u0006\r\uffff\uffff\u0000\u00bf\u001b\u0001\u0000"+
		"\u0000\u0000\u00c0\u00c1\u0005\u0010\u0000\u0000\u00c1\u00c2\u0003\u001e"+
		"\u000f\u0000\u00c2\u00cd\u0006\u000e\uffff\uffff\u0000\u00c3\u00c4\u0005"+
		"\u0011\u0000\u0000\u00c4\u00c5\u0003\u001e\u000f\u0000\u00c5\u00c6\u0006"+
		"\u000e\uffff\uffff\u0000\u00c6\u00cc\u0001\u0000\u0000\u0000\u00c7\u00c8"+
		"\u0005\u0012\u0000\u0000\u00c8\u00c9\u0003\u001e\u000f\u0000\u00c9\u00ca"+
		"\u0006\u000e\uffff\uffff\u0000\u00ca\u00cc\u0001\u0000\u0000\u0000\u00cb"+
		"\u00c3\u0001\u0000\u0000\u0000\u00cb\u00c7\u0001\u0000\u0000\u0000\u00cc"+
		"\u00cf\u0001\u0000\u0000\u0000\u00cd\u00cb\u0001\u0000\u0000\u0000\u00cd"+
		"\u00ce\u0001\u0000\u0000\u0000\u00ce\u00e0\u0001\u0000\u0000\u0000\u00cf"+
		"\u00cd\u0001\u0000\u0000\u0000\u00d0\u00d1\u0003\u001e\u000f\u0000\u00d1"+
		"\u00dc\u0006\u000e\uffff\uffff\u0000\u00d2\u00d3\u0005\u0011\u0000\u0000"+
		"\u00d3\u00d4\u0003\u001e\u000f\u0000\u00d4\u00d5\u0006\u000e\uffff\uffff"+
		"\u0000\u00d5\u00db\u0001\u0000\u0000\u0000\u00d6\u00d7\u0005\u0012\u0000"+
		"\u0000\u00d7\u00d8\u0003\u001e\u000f\u0000\u00d8\u00d9\u0006\u000e\uffff"+
		"\uffff\u0000\u00d9\u00db\u0001\u0000\u0000\u0000\u00da\u00d2\u0001\u0000"+
		"\u0000\u0000\u00da\u00d6\u0001\u0000\u0000\u0000\u00db\u00de\u0001\u0000"+
		"\u0000\u0000\u00dc\u00da\u0001\u0000\u0000\u0000\u00dc\u00dd\u0001\u0000"+
		"\u0000\u0000\u00dd\u00e0\u0001\u0000\u0000\u0000\u00de\u00dc\u0001\u0000"+
		"\u0000\u0000\u00df\u00c0\u0001\u0000\u0000\u0000\u00df\u00d0\u0001\u0000"+
		"\u0000\u0000\u00e0\u001d\u0001\u0000\u0000\u0000\u00e1\u00e2\u0003 \u0010"+
		"\u0000\u00e2\u00ed\u0006\u000f\uffff\uffff\u0000\u00e3\u00e4\u0005\u0013"+
		"\u0000\u0000\u00e4\u00e5\u0003 \u0010\u0000\u00e5\u00e6\u0006\u000f\uffff"+
		"\uffff\u0000\u00e6\u00ec\u0001\u0000\u0000\u0000\u00e7\u00e8\u0005\u0014"+
		"\u0000\u0000\u00e8\u00e9\u0003 \u0010\u0000\u00e9\u00ea\u0006\u000f\uffff"+
		"\uffff\u0000\u00ea\u00ec\u0001\u0000\u0000\u0000\u00eb\u00e3\u0001\u0000"+
		"\u0000\u0000\u00eb\u00e7\u0001\u0000\u0000\u0000\u00ec\u00ef\u0001\u0000"+
		"\u0000\u0000\u00ed\u00eb\u0001\u0000\u0000\u0000\u00ed\u00ee\u0001\u0000"+
		"\u0000\u0000\u00ee\u001f\u0001\u0000\u0000\u0000\u00ef\u00ed\u0001\u0000"+
		"\u0000\u0000\u00f0\u00f1\u0005\u0015\u0000\u0000\u00f1\u00f2\u0003\"\u0011"+
		"\u0000\u00f2\u00fd\u0006\u0010\uffff\uffff\u0000\u00f3\u00f4\u0005\u0016"+
		"\u0000\u0000\u00f4\u00f5\u0003\"\u0011\u0000\u00f5\u00f6\u0006\u0010\uffff"+
		"\uffff\u0000\u00f6\u00fc\u0001\u0000\u0000\u0000\u00f7\u00f8\u0005\u0015"+
		"\u0000\u0000\u00f8\u00f9\u0003\"\u0011\u0000\u00f9\u00fa\u0006\u0010\uffff"+
		"\uffff\u0000\u00fa\u00fc\u0001\u0000\u0000\u0000\u00fb\u00f3\u0001\u0000"+
		"\u0000\u0000\u00fb\u00f7\u0001\u0000\u0000\u0000\u00fc\u00ff\u0001\u0000"+
		"\u0000\u0000\u00fd\u00fb\u0001\u0000\u0000\u0000\u00fd\u00fe\u0001\u0000"+
		"\u0000\u0000\u00fe\u0110\u0001\u0000\u0000\u0000\u00ff\u00fd\u0001\u0000"+
		"\u0000\u0000\u0100\u0101\u0003\"\u0011\u0000\u0101\u010c\u0006\u0010\uffff"+
		"\uffff\u0000\u0102\u0103\u0005\u0016\u0000\u0000\u0103\u0104\u0003\"\u0011"+
		"\u0000\u0104\u0105\u0006\u0010\uffff\uffff\u0000\u0105\u010b\u0001\u0000"+
		"\u0000\u0000\u0106\u0107\u0005\u0015\u0000\u0000\u0107\u0108\u0003\"\u0011"+
		"\u0000\u0108\u0109\u0006\u0010\uffff\uffff\u0000\u0109\u010b\u0001\u0000"+
		"\u0000\u0000\u010a\u0102\u0001\u0000\u0000\u0000\u010a\u0106\u0001\u0000"+
		"\u0000\u0000\u010b\u010e\u0001\u0000\u0000\u0000\u010c\u010a\u0001\u0000"+
		"\u0000\u0000\u010c\u010d\u0001\u0000\u0000\u0000\u010d\u0110\u0001\u0000"+
		"\u0000\u0000\u010e\u010c\u0001\u0000\u0000\u0000\u010f\u00f0\u0001\u0000"+
		"\u0000\u0000\u010f\u0100\u0001\u0000\u0000\u0000\u0110!\u0001\u0000\u0000"+
		"\u0000\u0111\u0112\u0003$\u0012\u0000\u0112\u011d\u0006\u0011\uffff\uffff"+
		"\u0000\u0113\u0114\u0005\u0017\u0000\u0000\u0114\u0115\u0003$\u0012\u0000"+
		"\u0115\u0116\u0006\u0011\uffff\uffff\u0000\u0116\u011c\u0001\u0000\u0000"+
		"\u0000\u0117\u0118\u0005\u0018\u0000\u0000\u0118\u0119\u0003$\u0012\u0000"+
		"\u0119\u011a\u0006\u0011\uffff\uffff\u0000\u011a\u011c\u0001\u0000\u0000"+
		"\u0000\u011b\u0113\u0001\u0000\u0000\u0000\u011b\u0117\u0001\u0000\u0000"+
		"\u0000\u011c\u011f\u0001\u0000\u0000\u0000\u011d\u011b\u0001\u0000\u0000"+
		"\u0000\u011d\u011e\u0001\u0000\u0000\u0000\u011e#\u0001\u0000\u0000\u0000"+
		"\u011f\u011d\u0001\u0000\u0000\u0000\u0120\u0121\u0003&\u0013\u0000\u0121"+
		"\u0122\u0006\u0012\uffff\uffff\u0000\u0122\u012f\u0001\u0000\u0000\u0000"+
		"\u0123\u0124\u0005\u0019\u0000\u0000\u0124\u012f\u0006\u0012\uffff\uffff"+
		"\u0000\u0125\u0126\u0005\u001a\u0000\u0000\u0126\u012f\u0006\u0012\uffff"+
		"\uffff\u0000\u0127\u0128\u0005\u001f\u0000\u0000\u0128\u012f\u0006\u0012"+
		"\uffff\uffff\u0000\u0129\u012a\u0005\t\u0000\u0000\u012a\u012b\u0003\u001c"+
		"\u000e\u0000\u012b\u012c\u0005\n\u0000\u0000\u012c\u012d\u0006\u0012\uffff"+
		"\uffff\u0000\u012d\u012f\u0001\u0000\u0000\u0000\u012e\u0120\u0001\u0000"+
		"\u0000\u0000\u012e\u0123\u0001\u0000\u0000\u0000\u012e\u0125\u0001\u0000"+
		"\u0000\u0000\u012e\u0127\u0001\u0000\u0000\u0000\u012e\u0129\u0001\u0000"+
		"\u0000\u0000\u012f%\u0001\u0000\u0000\u0000\u0130\u0131\u0003\u0002\u0001"+
		"\u0000\u0131\u0132\u0006\u0013\uffff\uffff\u0000\u0132\'\u0001\u0000\u0000"+
		"\u0000\u0133\u0134\u0003*\u0015\u0000\u0134\u0135\u0006\u0014\uffff\uffff"+
		"\u0000\u0135\u0139\u0001\u0000\u0000\u0000\u0136\u0137\u0005\u001b\u0000"+
		"\u0000\u0137\u0139\u0006\u0014\uffff\uffff\u0000\u0138\u0133\u0001\u0000"+
		"\u0000\u0000\u0138\u0136\u0001\u0000\u0000\u0000\u0139)\u0001\u0000\u0000"+
		"\u0000\u013a\u013b\u0005\u001c\u0000\u0000\u013b\u013f\u0006\u0015\uffff"+
		"\uffff\u0000\u013c\u013d\u0005\u001d\u0000\u0000\u013d\u013f\u0006\u0015"+
		"\uffff\uffff\u0000\u013e\u013a\u0001\u0000\u0000\u0000\u013e\u013c\u0001"+
		"\u0000\u0000\u0000\u013f+\u0001\u0000\u0000\u0000 2ALT]_mr\u0083\u008d"+
		"\u00a2\u00ac\u00b5\u00b9\u00bb\u00cb\u00cd\u00da\u00dc\u00df\u00eb\u00ed"+
		"\u00fb\u00fd\u010a\u010c\u010f\u011b\u011d\u012e\u0138\u013e";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}