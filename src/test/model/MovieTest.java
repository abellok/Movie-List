package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovieTest {
    private Movie testMovie;

    @BeforeEach
    void runBefore() {
        testMovie = new Movie("Chef", "Jon Favreau", "Drama");
    }

    @Test
    void testConstructor() {
        assertEquals("Chef", testMovie.getTitle());
        assertEquals("Jon Favreau", testMovie.getDirector());
        assertEquals("Drama", testMovie.getGenre());
        assertEquals(0, testMovie.getRating());
    }

    @Test
    void testChangeRating() {
        testMovie.changeRating(4);
        assertEquals(4, testMovie.getRating());
    }
}