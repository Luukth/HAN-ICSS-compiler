package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.SymbolTable;
import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.VariableAssignment;
import nl.han.ica.icss.ast.VariableReference;
import nl.han.ica.icss.ast.types.ExpressionType;

public class CheckVariable {

    private final SymbolTable<String, ExpressionType> variableTypes;
    private final CheckExpression checkExpression;

    public CheckVariable(SymbolTable<String, ExpressionType> variableTypes) {
        this.variableTypes = variableTypes;
        this.checkExpression = new CheckExpression(this);
    }

    public void checkVariableAssignment(ASTNode astNode) {
        VariableAssignment variableAssignment = (VariableAssignment) astNode;
        VariableReference variableReference = variableAssignment.name;
        ExpressionType expressionType = this.checkExpression.checkExpression(variableAssignment.expression);

        if (expressionType == null || expressionType == ExpressionType.UNDEFINED) {
            astNode.setError("Variable assignment is invalid because of faulty expression.");
            return;
        }

        ExpressionType previousExpressionType = this.variableTypes.getVariable(variableReference.name);
        if (iHaveSeenThisVariableBeforeAndItsTypeIsDifferent(expressionType, previousExpressionType)) {
            astNode.setError("A variable can't change from type " + previousExpressionType.toString() + " to " + expressionType.toString());
        }

        this.variableTypes.putVariable(variableReference.name, expressionType);
    }

    public ExpressionType checkVariableReference(VariableReference variableReference) {
        ExpressionType expressionType = variableTypes.getVariable((variableReference).name);
        if (expressionType == null) {
            variableReference.setError("Variable not yet declared or in scope.");
            return null;
        }
        return expressionType;
    }

    private boolean iHaveSeenThisVariableBeforeAndItsTypeIsDifferent(ExpressionType expressionType, ExpressionType previousExpressionType) {
        return (previousExpressionType != null) && expressionType != previousExpressionType;
    }
}
