package nl.han.ica.icss;

import nl.han.ica.icss.ast.AST;
import nl.han.ica.icss.checker.Checker;
import nl.han.ica.icss.checker.ICSSError;
import nl.han.ica.icss.generator.Generator;
import nl.han.ica.icss.parser.ASTListener;
import nl.han.ica.icss.parser.ICSSLexer;
import nl.han.ica.icss.parser.ICSSParser;
import nl.han.ica.icss.transforms.Evaluator;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class Pipeline implements ANTLRErrorListener {

    private AST ast;
    private boolean parsed = false;
    private boolean checked = false;
    private boolean transformed = false;
    private List<String> errors;

    public Pipeline() {
        errors = new ArrayList<>();
    }

    public AST getAST() {
        return ast;
    }
    public List<String> getErrors() {
        return errors;
    }
    public boolean isParsed() {
        return parsed;
    }
    public boolean isChecked() {
        return checked;
    }
    public boolean isTransformed() {
        return transformed;
    }

    public void parseString(String input) {

        //Lex (with Antlr's generated lexer)
        CharStream inputStream = CharStreams.fromString(input);
        ICSSLexer lexer = new ICSSLexer(inputStream);
        lexer.removeErrorListeners();
        lexer.addErrorListener(this);
        errors.clear();
        try {
            CommonTokenStream tokens = new CommonTokenStream(lexer);

            //Parse (with Antlr's generated parser)
            ICSSParser parser = new ICSSParser(tokens);
            parser.removeErrorListeners();
            parser.addErrorListener(this);

            ParseTree parseTree = parser.stylesheet();

            //Extract AST from the Antlr parse tree
            ASTListener listener = new ASTListener();
            ParseTreeWalker walker = new ParseTreeWalker();
            walker.walk(listener, parseTree);

            this.ast = listener.getAST();

        } catch (RecognitionException e) {
            this.ast = new AST();
            errors.add(e.getMessage());

        } catch (ParseCancellationException e) {
            this.ast = new AST();
            errors.add("Syntax error");
        }
        parsed = errors.isEmpty();
        checked = transformed = false;
    }
    public boolean check() {
            if(ast == null)
                return false;

           (new Checker()).check(this.ast);

            ArrayList<ICSSError> errors = this.ast.getErrors();
            if (!errors.isEmpty()) {
                for (ICSSError e : errors) {
                    this.errors.add(e.toString());
                }
            }

            checked = errors.isEmpty();
            transformed = false;
            return errors.isEmpty();
    }

    public void clearErrors(){
        errors.clear();
    }

    public void transform() {
        if (ast == null)
            return;

        (new Evaluator()).apply(ast);

        ArrayList<ICSSError> errors = this.ast.getErrors();
        if (!errors.isEmpty()) {
            for (ICSSError e : errors) {
                this.errors.add(e.toString());
            }
        }

        transformed = true;
    }
    public String generate() {
        Generator generator = new Generator();
        return generator.generate(ast);
    }

    //Catch ANTLR errors
    @Override
    public void reportAmbiguity(Parser arg0, DFA arg1, int arg2, int arg3,
                                boolean arg4, BitSet arg5, ATNConfigSet arg6) {
    }

    @Override
    public void reportAttemptingFullContext(Parser arg0, DFA arg1, int arg2,
                                            int arg3, BitSet arg4, ATNConfigSet arg5) {
    }

    @Override
    public void reportContextSensitivity(Parser arg0, DFA arg1, int arg2,
                                         int arg3, int arg4, ATNConfigSet arg5) {
    }

    @Override
    public void syntaxError(Recognizer<?, ?> arg0, Object arg1, int arg2,
                            int arg3, String arg4, RecognitionException arg5) {
        errors.add("Syntax error: " + arg4);
    }
}
