package nl.han.ica.datastructures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class HANLinkedListTest {

    IHANLinkedList<Integer> sut;

    @BeforeEach
    void setUp() {
        sut = new HANLinkedList<>();
    }

    @Test
    void addFirst() {
        // Arrange
        sut.addFirst(1);
        sut.addFirst(999);

        // Act
        sut.addFirst(1000);

        // Assert
        assertEquals(1000, sut.get(0));
    }

    @Test
    void insertIntoEmptyLinkedList() {
        // Arrange

        // Act
        sut.insert(0, 1);

        // Assert
        assertEquals(1, sut.get(0));
    }

    @Test
    void insertAtFirstIndex() {
        // Arrange
        sut.addFirst(3);
        sut.addFirst(2);
        sut.addFirst(1);

        // Act
        sut.insert(0, 999);

        // Assert
        assertEquals(999, sut.get(0));
        assertEquals(1, sut.get(1));
    }

    @Test
    void insertInMiddleOfList() {
        // Arrange
        sut.insert(0, 1);
        sut.insert(1, 2);
        sut.insert(2, 3);

        // Act
        sut.insert(1, 999);

        // Assert
        assertEquals(1, sut.get(0));
        assertEquals(999, sut.get(1));
        assertEquals(2, sut.get(2));
    }

    @Test
    void insertAtEndOfList() {
        // Arrange
        sut.addFirst(3);
        sut.addFirst(2);
        sut.addFirst(1);

        // Act
        sut.insert(3, 4);

        // Assert
        assertEquals(4, sut.get(3)); // last index
        assertEquals(3, sut.get(2)); // last index-1
    }

    @Test
    void insertAtUnknownIndex() {
        // Arrange
        sut.addFirst(1);

        // Act
        sut.insert(9, 2);

        // Assert
        assertEquals(1, sut.getSize());
    }

    @Test
    void removeFirst() {
        // Arrange
        sut.addFirst(2);
        sut.addFirst(1);

        // Act
        sut.removeFirst();

        // Assert
        assertEquals(2, sut.getFirst());
    }

    @Test
    void clear() {
        // Arrange
        sut.addFirst(1);
        sut.addFirst(2);
        sut.addFirst(3);

        // Act
        assertEquals(3, sut.getSize());
        sut.clear();

        // Assert
        assertEquals(0, sut.getSize());
    }

    @Test
    void deleteFirstItem() {
        // Arrange
        sut.addFirst(3);
        sut.addFirst(2);
        sut.addFirst(1);

        // Act
        sut.delete(0);

        // Assert
        assertEquals(2, sut.get(0));
        assertEquals(3, sut.get(1));
        assertEquals(2, sut.getSize());
    }

    @Test
    void deleteLastItem() {
        // Arrange
        sut.addFirst(3);
        sut.addFirst(2);
        sut.addFirst(1);

        // Act
        sut.delete(2);

        // Assert
        assertThrows(NoSuchElementException.class, () -> sut.get(2));
        assertEquals(2, sut.get(1));
    }

    @Test
    void deleteItemInMiddleOfLinkedList() {
        // Arrange
        sut.addFirst(3);
        sut.addFirst(2);
        sut.addFirst(1);

        // Act
        sut.delete(1);

        // Assert
        assertEquals(1, sut.get(0));
        assertEquals(3, sut.get(1));
        assertEquals(2, sut.getSize());
    }

    @Test
    void deleteItemAtUnknownIndex() {
        // Arrange
        sut.addFirst(1);
        sut.addFirst(2);
        sut.addFirst(3);

        // Act
        sut.delete(999);

        // Assert
        assertEquals(3, sut.getSize());
    }

    @Test
    void iterator() {
        // Arrange
        List<Integer> result = new ArrayList<>();
        sut.addFirst(1);
        sut.addFirst(2);
        sut.addFirst(3);

        // Act
        for (Integer number : sut) {
            result.add(number);
        }

        // Assert
        List<Integer> expected = Arrays.asList(3, 2, 1);
        assertEquals(expected, result);
    }
}