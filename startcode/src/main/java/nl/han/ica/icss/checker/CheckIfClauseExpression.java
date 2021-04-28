package nl.han.ica.icss.checker;

import nl.han.ica.icss.ast.Expression;
import nl.han.ica.icss.ast.IfClause;
import nl.han.ica.icss.ast.types.ExpressionType;

public class CheckIfClauseExpression {

    private final CheckExpression checkExpression;

    public CheckIfClauseExpression(CheckExpression checkExpression) {
        this.checkExpression = checkExpression;
    }

    public ExpressionType check(IfClause ifClause) {
        Expression conditionalExpression = ifClause.conditionalExpression;
        ExpressionType expressionType = this.checkExpression.checkExpressionType(conditionalExpression);

        if (expressionType != ExpressionType.BOOL) {
            ifClause.setError("ConditionalExpression should be a boolean literal.");
            return ExpressionType.UNDEFINED;
        }

        return ExpressionType.BOOL;
    }
}
