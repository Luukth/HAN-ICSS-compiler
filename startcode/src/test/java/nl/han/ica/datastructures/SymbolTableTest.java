package nl.han.ica.datastructures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SymbolTableTest {

    SymbolTable<String, Integer> sut;

    @BeforeEach
    void setUp() {
        this.sut = new SymbolTable<>();
    }

    @Test
    void thatItCreatesMultipleScopes() {
        // Arrange

        // Act
        sut.pushScope();
        sut.pushScope();
        sut.pushScope();

        // Assert
        assertEquals(3, sut.getTotalAmountOfScopes());
    }

    @Test
    void thatItRemovesScopes() {
        // Arrange

        // Act
        sut.pushScope();
        sut.pushScope();
        sut.popScope();

        // Assert
        assertEquals(1, sut.getTotalAmountOfScopes());
    }

    @Test
    void thatItReturnsVariableInCurrentScope() {
        // Arrange

        // Act
        sut.pushScope();
        sut.putVariable("test", 100);
        Integer result = sut.getVariable("test");

        // Assert
        assertEquals(100, result);
    }

    @Test
    void thatItReturnsVariableFromOneScopeAboveCurrent() {
        // Arrange

        // Act
        sut.pushScope();
        sut.putVariable("global", 100);
        sut.pushScope();
        Integer result = sut.getVariable("global");

        // Assert
        assertEquals(100, result);
    }

    @Test
    void thatItReturnsNullWhenVariableIsOutsideCurrentScope() {
        // Arrange

        // Act
        sut.pushScope();
        sut.pushScope();
        sut.putVariable("scope1", 100);
        sut.popScope();
        Integer result = sut.getVariable("scope1");

        // Assert
        assertNull(result);
    }
}