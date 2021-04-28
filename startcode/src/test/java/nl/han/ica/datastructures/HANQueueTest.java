package nl.han.ica.datastructures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HANQueueTest {

    IHANQueue<Integer> sut;

    @BeforeEach
    void setUp() {
        sut = new HANQueue<>();
    }

    @Test
    void clear() {
        // Arrange
        sut.enqueue(1);

        // Act
        assertEquals(1, sut.getSize());
        sut.clear();

        // Assert
        assertEquals(0, sut.getSize());
    }

    @Test
    void isEmpty() {
        // Arrange
        sut.enqueue(1);

        // Act
        assertFalse(sut.isEmpty());
        sut.clear();

        // Assert
        assertTrue(sut.isEmpty());
    }

    @Test
    void dequeue() {
        // Arrange
        sut.enqueue(1);
        sut.enqueue(2);
        sut.enqueue(3);

        // Act
        Integer result = sut.dequeue();

        // Assert
        assertEquals(Integer.valueOf(1), result);
        assertEquals(2, sut.getSize());
    }

    @Test
    void peek() {
        // Arrange
        sut.enqueue(999);
        sut.enqueue(888);

        // Act
        Integer result = sut.peek();

        // Assert
        assertEquals(Integer.valueOf(999), result);
    }
}