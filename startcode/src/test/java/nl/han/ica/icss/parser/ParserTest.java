package nl.han.ica.icss.parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import nl.han.ica.icss.ast.*;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;

import java.io.*;

class ParserTest {

	AST parseTestFile(String resource) throws IOException {

		//Open test file to parse
		ClassLoader classLoader = this.getClass().getClassLoader();

    	InputStream inputStream = classLoader.getResourceAsStream(resource);
        CharStream charStream = CharStreams.fromStream(inputStream);
        ICSSLexer lexer = new ICSSLexer(charStream);

	    CommonTokenStream tokens = new CommonTokenStream(lexer);

        ICSSParser parser = new ICSSParser(tokens);
		parser.setErrorHandler(new BailErrorStrategy());

		//Setup collection of the parse error messages
		BaseErrorListener errorListener = new BaseErrorListener() {
			private String message;
			public void syntaxError(Recognizer<?,?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
				message = msg;
			}
			public String toString() {
				return message;
			}
		};
		parser.removeErrorListeners();
		parser.addErrorListener(errorListener);

		//Parse & extract AST
 		ASTListener listener = new ASTListener();
		try {
			ParseTree parseTree = parser.stylesheet();
            ParseTreeWalker walker = new ParseTreeWalker();
            walker.walk(listener, parseTree);
		} catch(ParseCancellationException e) {
			fail(errorListener.toString());
		}

		return listener.getAST();
	}

	@Test
	void testParseLevel0() throws IOException {

		AST sut = parseTestFile("level0.icss");
		AST exp = Fixtures.uncheckedLevel0();
		assertEquals(exp,sut);
	}
	@Test
	void testParseLevel1() throws IOException {

		AST sut = parseTestFile("level1.icss");
		AST exp = Fixtures.uncheckedLevel1();
		assertEquals(exp,sut);
	}
	@Test
	void testParseLevel2() throws IOException {

		AST sut = parseTestFile("level2.icss");
		AST exp = Fixtures.uncheckedLevel2();
		assertEquals(exp,sut);
	}
	@Test
	void testParseLevel3() throws IOException {

		AST sut = parseTestFile("level3.icss");
		AST exp = Fixtures.uncheckedLevel3();
		assertEquals(exp,sut);
	}
}
