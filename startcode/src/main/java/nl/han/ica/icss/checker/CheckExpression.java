package nl.han.ica.icss.checker;

import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.Expression;
import nl.han.ica.icss.ast.Operation;
import nl.han.ica.icss.ast.VariableReference;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.types.ExpressionType;

public class CheckExpression {

    private final CheckOperation checkOperation;
    private final CheckVariable checkVariable;

    public CheckExpression(CheckVariable checkVariable) {
        this.checkVariable = checkVariable;
        this.checkOperation = new CheckOperation(this);
    }

    public ExpressionType checkExpression(ASTNode astNode) {
        Expression expression = (Expression) astNode;

        if (expression instanceof Operation) {
            return this.checkOperation.check((Operation) expression);
        }

        return this.checkExpressionType(expression);
    }

    public ExpressionType checkExpressionType(Expression expression) {

        if (expression instanceof VariableReference) {
            return this.checkVariable.checkVariableReference((VariableReference) expression);
        } else {
            if (expression instanceof PercentageLiteral) {
                return ExpressionType.PERCENTAGE;
            } else if (expression instanceof PixelLiteral) {
                return ExpressionType.PIXEL;
            } else if (expression instanceof ColorLiteral) {
                return ExpressionType.COLOR;
            } else if (expression instanceof ScalarLiteral) {
                return ExpressionType.SCALAR;
            } else if (expression instanceof BoolLiteral) {
                return ExpressionType.BOOL;
            }
        }

        return ExpressionType.UNDEFINED;
    }
}
