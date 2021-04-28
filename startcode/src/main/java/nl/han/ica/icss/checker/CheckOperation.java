package nl.han.ica.icss.checker;

import nl.han.ica.icss.ast.Operation;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.DivideOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.ast.types.ExpressionType;

public class CheckOperation {

    private final CheckExpression checkExpression;

    public CheckOperation(CheckExpression checkExpression) {
        this.checkExpression = checkExpression;
    }

    public ExpressionType check(Operation operation) {

        ExpressionType left;
        ExpressionType right;

        if (operation.lhs instanceof Operation) {
            left = this.check((Operation) operation.lhs);
        } else {
            left = this.checkExpression.checkExpressionType(operation.lhs);
        }

        if (operation.rhs instanceof Operation) {
            right = this.check((Operation) operation.rhs);
        } else {
            right = this.checkExpression.checkExpressionType(operation.rhs);
        }

        if (left == ExpressionType.COLOR || right == ExpressionType.COLOR || left == ExpressionType.BOOL || right == ExpressionType.BOOL) {
            operation.setError("Colors and booleans are not allowed in operations.");
            return ExpressionType.UNDEFINED;
        }

        if (operation instanceof DivideOperation) {
            if (left == ExpressionType.SCALAR) {
                operation.setError("Division of a scalar literal is not allowed.");
                return ExpressionType.UNDEFINED;
            }

            if (right != ExpressionType.SCALAR) {
                operation.setError("The right side of a division should be a literal value.");
                return ExpressionType.UNDEFINED;
            }
        }

        if (operation instanceof MultiplyOperation) {
            if (left != ExpressionType.SCALAR && right != ExpressionType.SCALAR) {
                operation.setError("Multiply is only allowed with at least one scalar literal.");
                return ExpressionType.UNDEFINED;
            }
            return right != ExpressionType.SCALAR ? right : left;
        } else if ((operation instanceof SubtractOperation || operation instanceof AddOperation) && left != right) {
            operation.setError("You can only do add and subtract operations with the same literal.");
            return ExpressionType.UNDEFINED;
        }

        return left;
    }
}
