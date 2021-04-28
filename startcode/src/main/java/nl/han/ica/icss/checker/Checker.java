package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.SymbolTable;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.ArrayList;


public class Checker {

    private final SymbolTable<String, ExpressionType> variableTypes;
    private final CheckExpression checkExpression;
    private final CheckIfClauseExpression checkIfClauseExpression;
    private final CheckVariable checkVariable;

    public Checker() {
        this.variableTypes = new SymbolTable<>();
        this.checkVariable = new CheckVariable(variableTypes);
        this.checkExpression = new CheckExpression(checkVariable);
        this.checkIfClauseExpression = new CheckIfClauseExpression(checkExpression);
    }

    public void check(AST ast) {
        this.checkStylesheet(ast.root);
    }

    private void checkStylesheet(ASTNode astNode) {
        Stylesheet stylesheet = (Stylesheet) astNode;

        variableTypes.pushScope();

        for (ASTNode child : stylesheet.getChildren()) {
            if (child instanceof VariableAssignment) {
                this.checkVariable.checkVariableAssignment(child);
                continue;
            }

            if (child instanceof Stylerule) {
                variableTypes.pushScope();
                this.checkStylerule(child);
                variableTypes.popScope();
            }

        }

        variableTypes.popScope();
    }

    private void checkStylerule(ASTNode astNode) {
        Stylerule stylerule = (Stylerule) astNode;
        this.checkRuleBody(stylerule.body);
    }

    private void checkRuleBody(ArrayList<ASTNode> astNodes) {
        for (ASTNode astNode : astNodes) {
            if (astNode instanceof Declaration) {
                this.checkDeclaration(astNode);
                continue;
            }

            if (astNode instanceof IfClause) {
                this.checkIfClause(astNode);
                continue;
            }

            if (astNode instanceof VariableAssignment) {
                this.checkVariable.checkVariableAssignment(astNode);
            }
        }
    }

    private void checkIfClause(ASTNode astNode) {
        IfClause ifClause = (IfClause) astNode;
        this.variableTypes.pushScope();
        this.checkIfClauseExpression.check(ifClause);
        this.checkRuleBody(ifClause.body);
        this.variableTypes.popScope();

        if (ifClause.elseClause != null) {
            this.variableTypes.pushScope();
            this.checkElseClause(ifClause.elseClause);
            this.variableTypes.popScope();
        }
    }

    private void checkElseClause(ASTNode astNode) {
        ElseClause elseClause = (ElseClause) astNode;
        this.checkRuleBody(elseClause.body);
    }

    private void checkDeclaration(ASTNode astNode) {
        Declaration declaration = (Declaration) astNode;
        ExpressionType expressionType = this.checkExpression.checkExpression(declaration.expression);

        switch (declaration.property.name) {
            case "color":
                if (expressionType != ExpressionType.COLOR) {
                    astNode.setError("Color value can only be a color literal.");
                }
                break;
            case "background-color":
                if (expressionType != ExpressionType.COLOR) {
                    astNode.setError("Background-color value can only be a color literal.");
                }
                break;
            case "width":
                if (expressionType != ExpressionType.PIXEL && expressionType != ExpressionType.PERCENTAGE) {
                    astNode.setError("Width value can only be a pixel or percentage literal.");
                }
                break;
            case "height":
                if (expressionType != ExpressionType.PIXEL && expressionType != ExpressionType.PERCENTAGE) {
                    astNode.setError("Height value can only be a pixel or percentage literal.");
                }
                break;
            default:
                astNode.setError("The only properties allowed are height, width, background-color and color.");
                break;
        }
    }
}