package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.SymbolTable;
import nl.han.ica.icss.ast.Operation;
import nl.han.ica.icss.ast.VariableReference;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.DivideOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.ast.types.ExpressionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckOperationTest {

    CheckOperation sut;
    SymbolTable<String, ExpressionType> variableTypes;
    CheckVariable checkVariable;
    CheckExpression checkExpression;

    @BeforeEach
    void setUp() {
        this.variableTypes = new SymbolTable<>();
        this.checkVariable = new CheckVariable(variableTypes);
        this.checkExpression = new CheckExpression(checkVariable);
        this.sut = new CheckOperation(checkExpression);
    }

    @Test
    void thatOperationWithTwoScalerLiteralsIsValid() {
        // Arrange
        Operation operation = new AddOperation();
        operation.addChild(new ScalarLiteral(100));
        operation.addChild(new ScalarLiteral(100));

        // Act
        ExpressionType expressionType = sut.check(operation);

        // Assert
        assertEquals(ExpressionType.SCALAR, expressionType);
    }

    @Test
    void thatOperationWithOneBoolLiteralIsInvalid() {
        // Arrange
        Operation operation = new AddOperation();
        operation.addChild(new ScalarLiteral(100));
        operation.addChild(new BoolLiteral(true));

        // Act
        ExpressionType expressionType = sut.check(operation);

        // Assert
        assertEquals(ExpressionType.UNDEFINED, expressionType);
    }

    @Test
    void thatAddOperationWithOneBoolLiteralIsInvalid() {
        // Arrange
        Operation operation = new AddOperation();
        operation.addChild(new ScalarLiteral(1));
        operation.addChild(new BoolLiteral(true));

        // Act
        ExpressionType expressionType = sut.check(operation);

        // Assert
        assertEquals(ExpressionType.UNDEFINED, expressionType);
    }

    @Test
    void thatAddOperationWithTwoVariableReferencesIsValid() {
        // Arrange
        this.variableTypes.pushScope();
        this.variableTypes.putVariable("number1", ExpressionType.SCALAR);
        this.variableTypes.putVariable("number2", ExpressionType.SCALAR);
        Operation operation = new AddOperation();
        operation.addChild(new VariableReference("number1"));
        operation.addChild(new VariableReference("number2"));

        // Act
        ExpressionType expressionType = sut.check(operation);
        this.variableTypes.popScope();

        // Assert
        assertEquals(ExpressionType.SCALAR, expressionType);
    }

    @Test
    void thatAddOperationWithTwoVariableReferencesIsInvalid() {
        // Arrange
        this.variableTypes.pushScope();
        this.variableTypes.putVariable("color1", ExpressionType.COLOR);
        this.variableTypes.putVariable("color2", ExpressionType.COLOR);
        Operation operation = new AddOperation();
        operation.addChild(new VariableReference("color1"));
        operation.addChild(new VariableReference("color2"));

        // Act
        ExpressionType expressionType = sut.check(operation);
        this.variableTypes.popScope();

        // Assert
        assertEquals(ExpressionType.UNDEFINED, expressionType);
    }

    @Test
    void thatAddOperationWithAnotherOperationIsValid() {
        // Arrange
        Operation operation1 = new AddOperation();
        Operation operation2 = new AddOperation();
        operation2.addChild(new ScalarLiteral(1));
        operation2.addChild(new ScalarLiteral(1));

        operation1.addChild(new ScalarLiteral(1));
        operation1.addChild(operation2);

        // Act
        ExpressionType expressionType = sut.check(operation1);

        // Assert
        assertEquals(ExpressionType.SCALAR, expressionType);
    }

    @Test
    void thatAddOperationWithAnotherOperationIsInvalid() {
        // Arrange
        Operation operation1 = new AddOperation();
        Operation operation2 = new AddOperation();
        operation2.addChild(new ScalarLiteral(1));
        operation2.addChild(new ScalarLiteral(1));

        operation1.addChild(operation2);
        operation1.addChild(new BoolLiteral(true)); // This is not allowed.

        // Act
        ExpressionType expressionType = sut.check(operation1);

        // Assert
        assertEquals(ExpressionType.UNDEFINED, expressionType);
    }

    @Test
    void thatAddOperationWithNestedOperationLiteralIsInvalid() {
        // Arrange
        Operation operation1 = new AddOperation();
        Operation operation2 = new AddOperation();
        operation2.addChild(new ScalarLiteral(1));
        operation2.addChild(new BoolLiteral(true));// This nested literal is not allowed.

        operation1.addChild(new ScalarLiteral(1));
        operation1.addChild(operation2);

        // Act
        ExpressionType expressionType = sut.check(operation1);

        // Assert
        assertEquals(ExpressionType.UNDEFINED, expressionType);
    }

    @Test
    void thatAddOperationWithNestedOperationThatHasAVariableReferenceIsValid() {
        // Arrange
        this.variableTypes.pushScope();
        this.variableTypes.putVariable("number1", ExpressionType.SCALAR);

        Operation operation1 = new AddOperation();
        Operation operation2 = new AddOperation();
        operation2.addChild(new VariableReference("number1"));
        operation2.addChild(new VariableReference("number1"));

        operation1.addChild(new ScalarLiteral(1));
        operation1.addChild(operation2);

        // Act
        ExpressionType expressionType = sut.check(operation1);
        this.variableTypes.popScope();

        // Assert
        assertEquals(ExpressionType.SCALAR, expressionType);
    }

    @Test
    void thatAddOperationWithNestedOperationThatHasAVariableReferenceIsInvalid() {
        // Arrange
        this.variableTypes.pushScope();
        this.variableTypes.putVariable("color1", ExpressionType.COLOR);

        Operation operation1 = new AddOperation();
        Operation operation2 = new AddOperation();
        operation2.addChild(new VariableReference("color1"));
        operation2.addChild(new VariableReference("color1"));

        operation1.addChild(new VariableReference("color1"));
        operation1.addChild(operation2);

        // Act
        ExpressionType expressionType = sut.check(operation1);
        this.variableTypes.popScope();

        // Assert
        assertEquals(ExpressionType.UNDEFINED, expressionType);
    }

    @Test
    void thatAddOperationWithDifferentTypesIsInvalid() {
        // Arrange
        Operation operation = new AddOperation();
        operation.addChild(new ScalarLiteral(100));
        operation.addChild(new PercentageLiteral(100));

        // Act
        ExpressionType expressionType = sut.check(operation);

        // Assert
        assertEquals(ExpressionType.UNDEFINED, expressionType);
    }

    @Test
    void thatSubtractOperationWithDifferentTypesIsInvalid() {
        // Arrange
        Operation operation = new SubtractOperation();
        operation.addChild(new ScalarLiteral(100));
        operation.addChild(new PercentageLiteral(100));

        // Act
        ExpressionType expressionType = sut.check(operation);

        // Assert
        assertEquals(ExpressionType.UNDEFINED, expressionType);
    }

    @Test
    void thatMultiplyOperationWithOneScalarLiteralIsValid() {
        // Arrange
        Operation operation = new MultiplyOperation();
        operation.addChild(new ScalarLiteral(100));
        operation.addChild(new PixelLiteral(10));

        // Act
        ExpressionType expressionType = sut.check(operation);

        // Assert
        assertEquals(ExpressionType.PIXEL, expressionType);
    }

    @Test
    void thatMultiplyOperationWithTwoNonScalarLiteralsIsInvalid() {
        // Arrange
        Operation operation = new MultiplyOperation();
        operation.addChild(new PercentageLiteral(10));
        operation.addChild(new PercentageLiteral(10));

        // Act
        ExpressionType expressionType = sut.check(operation);

        // Assert
        assertEquals(ExpressionType.UNDEFINED, expressionType);
    }

    @Test
    void thatMultiplyOperationWithTwoVariableNonScalarLiteralsIsInvalid() {
        // Arrange
        this.variableTypes.pushScope();
        this.variableTypes.putVariable("percentage1", ExpressionType.PIXEL);
        this.variableTypes.putVariable("percentage2", ExpressionType.PIXEL);
        Operation operation = new MultiplyOperation();
        operation.addChild(new VariableReference("percentage1"));
        operation.addChild(new VariableReference("percentage2"));

        // Act
        ExpressionType expressionType = sut.check(operation);
        this.variableTypes.popScope();

        // Assert
        assertEquals(ExpressionType.UNDEFINED, expressionType);
    }

    @Test
    void thatDivideOperationIsInvalid() {
        // Arrange
        Operation operation = new DivideOperation();
        operation.addChild(new ScalarLiteral(100));
        operation.addChild(new PercentageLiteral(100));

        // Act
        ExpressionType expressionType = sut.check(operation);

        // Assert
        assertEquals(ExpressionType.UNDEFINED, expressionType);
    }

    @Test
    void thatDivideOperationNeedsScalarOnRightSide() {
        // Arrange
        Operation operation = new DivideOperation();
        operation.addChild(new PercentageLiteral(100));
        operation.addChild(new PercentageLiteral(100));

        // Act
        ExpressionType expressionType = sut.check(operation);

        // Assert
        assertEquals(ExpressionType.UNDEFINED, expressionType);
    }

}