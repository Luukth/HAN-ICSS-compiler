package nl.han.ica.datastructures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HANStackTest {

    IHANStack<Integer> sut;

    @BeforeEach
    void setUp() {
        sut = new HANStack<>();
    }

    @Test
    void push() {
        // Arrange

        // Act
        sut.push(1);

        // Assert
        assertEquals(1, sut.peek());
    }

    @Test
    void pop() {
        // Arrange
        sut.push(1);
        sut.push(2);

        // Act
        sut.pop();

        // Assert
        assertEquals(1, sut.peek());
    }

    @Test
    void peek() {
        // Arrange
        sut.push(999);

        // Act

        // Assert
        assertEquals(999, sut.peek());
    }
}