// Generated from /home/AD/kjelic/DossierPartage/Projet/groupe-5/ast/src/main/antlr4/MiniJaJa.g4 by ANTLR 4.13.2
package package fr.ufrst.m1info.gl.compGL;

import fr.ufrst.m1info.gl.compGL.ValueType;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class MiniJaJaLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		IDENTIFIER=18;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16", 
			"IDENTIFIER"
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


	public MiniJaJaLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "MiniJaJa.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\u0012s\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
		"\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002"+
		"\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001"+
		"\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0006\u0001"+
		"\u0006\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b"+
		"\u0001\b\u0001\b\u0001\t\u0001\t\u0001\n\u0001\n\u0001\n\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0011"+
		"\u0004\u0011j\b\u0011\u000b\u0011\f\u0011k\u0001\u0011\u0005\u0011o\b"+
		"\u0011\n\u0011\f\u0011r\t\u0011\u0000\u0000\u0012\u0001\u0001\u0003\u0002"+
		"\u0005\u0003\u0007\u0004\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011\t\u0013"+
		"\n\u0015\u000b\u0017\f\u0019\r\u001b\u000e\u001d\u000f\u001f\u0010!\u0011"+
		"#\u0012\u0001\u0000\u0002\u0003\u0000AZ__az\u0004\u000009AZ__azt\u0000"+
		"\u0001\u0001\u0000\u0000\u0000\u0000\u0003\u0001\u0000\u0000\u0000\u0000"+
		"\u0005\u0001\u0000\u0000\u0000\u0000\u0007\u0001\u0000\u0000\u0000\u0000"+
		"\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001\u0000\u0000\u0000\u0000\r"+
		"\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000\u0000\u0000\u0000\u0011"+
		"\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000\u0000\u0000\u0000\u0015"+
		"\u0001\u0000\u0000\u0000\u0000\u0017\u0001\u0000\u0000\u0000\u0000\u0019"+
		"\u0001\u0000\u0000\u0000\u0000\u001b\u0001\u0000\u0000\u0000\u0000\u001d"+
		"\u0001\u0000\u0000\u0000\u0000\u001f\u0001\u0000\u0000\u0000\u0000!\u0001"+
		"\u0000\u0000\u0000\u0000#\u0001\u0000\u0000\u0000\u0001%\u0001\u0000\u0000"+
		"\u0000\u0003+\u0001\u0000\u0000\u0000\u0005-\u0001\u0000\u0000\u0000\u0007"+
		"/\u0001\u0000\u0000\u0000\t1\u0001\u0000\u0000\u0000\u000b6\u0001\u0000"+
		"\u0000\u0000\r<\u0001\u0000\u0000\u0000\u000f>\u0001\u0000\u0000\u0000"+
		"\u0011@\u0001\u0000\u0000\u0000\u0013G\u0001\u0000\u0000\u0000\u0015I"+
		"\u0001\u0000\u0000\u0000\u0017L\u0001\u0000\u0000\u0000\u0019O\u0001\u0000"+
		"\u0000\u0000\u001bU\u0001\u0000\u0000\u0000\u001dZ\u0001\u0000\u0000\u0000"+
		"\u001f^\u0001\u0000\u0000\u0000!f\u0001\u0000\u0000\u0000#i\u0001\u0000"+
		"\u0000\u0000%&\u0005c\u0000\u0000&\'\u0005l\u0000\u0000\'(\u0005a\u0000"+
		"\u0000()\u0005s\u0000\u0000)*\u0005s\u0000\u0000*\u0002\u0001\u0000\u0000"+
		"\u0000+,\u0005{\u0000\u0000,\u0004\u0001\u0000\u0000\u0000-.\u0005}\u0000"+
		"\u0000.\u0006\u0001\u0000\u0000\u0000/0\u0005;\u0000\u00000\b\u0001\u0000"+
		"\u0000\u000012\u0005m\u0000\u000023\u0005a\u0000\u000034\u0005i\u0000"+
		"\u000045\u0005n\u0000\u00005\n\u0001\u0000\u0000\u000067\u0005w\u0000"+
		"\u000078\u0005h\u0000\u000089\u0005i\u0000\u00009:\u0005l\u0000\u0000"+
		":;\u0005e\u0000\u0000;\f\u0001\u0000\u0000\u0000<=\u0005(\u0000\u0000"+
		"=\u000e\u0001\u0000\u0000\u0000>?\u0005)\u0000\u0000?\u0010\u0001\u0000"+
		"\u0000\u0000@A\u0005r\u0000\u0000AB\u0005e\u0000\u0000BC\u0005t\u0000"+
		"\u0000CD\u0005u\u0000\u0000DE\u0005r\u0000\u0000EF\u0005n\u0000\u0000"+
		"F\u0012\u0001\u0000\u0000\u0000GH\u0005!\u0000\u0000H\u0014\u0001\u0000"+
		"\u0000\u0000IJ\u0005&\u0000\u0000JK\u0005&\u0000\u0000K\u0016\u0001\u0000"+
		"\u0000\u0000LM\u0005|\u0000\u0000MN\u0005|\u0000\u0000N\u0018\u0001\u0000"+
		"\u0000\u0000OP\u0005f\u0000\u0000PQ\u0005i\u0000\u0000QR\u0005n\u0000"+
		"\u0000RS\u0005a\u0000\u0000ST\u0005l\u0000\u0000T\u001a\u0001\u0000\u0000"+
		"\u0000UV\u0005v\u0000\u0000VW\u0005o\u0000\u0000WX\u0005i\u0000\u0000"+
		"XY\u0005d\u0000\u0000Y\u001c\u0001\u0000\u0000\u0000Z[\u0005i\u0000\u0000"+
		"[\\\u0005n\u0000\u0000\\]\u0005t\u0000\u0000]\u001e\u0001\u0000\u0000"+
		"\u0000^_\u0005b\u0000\u0000_`\u0005o\u0000\u0000`a\u0005o\u0000\u0000"+
		"ab\u0005l\u0000\u0000bc\u0005e\u0000\u0000cd\u0005a\u0000\u0000de\u0005"+
		"n\u0000\u0000e \u0001\u0000\u0000\u0000fg\u0005=\u0000\u0000g\"\u0001"+
		"\u0000\u0000\u0000hj\u0007\u0000\u0000\u0000ih\u0001\u0000\u0000\u0000"+
		"jk\u0001\u0000\u0000\u0000ki\u0001\u0000\u0000\u0000kl\u0001\u0000\u0000"+
		"\u0000lp\u0001\u0000\u0000\u0000mo\u0007\u0001\u0000\u0000nm\u0001\u0000"+
		"\u0000\u0000or\u0001\u0000\u0000\u0000pn\u0001\u0000\u0000\u0000pq\u0001"+
		"\u0000\u0000\u0000q$\u0001\u0000\u0000\u0000rp\u0001\u0000\u0000\u0000"+
		"\u0003\u0000kp\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}