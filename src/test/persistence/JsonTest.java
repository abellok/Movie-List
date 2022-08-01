package persistence;

import model.Movie;

import static org.junit.jupiter.api.Assertions.assertEquals;

// NOTE: borrows code from the JsonSerializationDemo
public class JsonTest {

    protected void checkMovie(String title, String director, String genre, int rating, Movie movie) {
        assertEquals(title, movie.getTitle());
        assertEquals(director, movie.getDirector());
        assertEquals(genre, movie.getGenre());
        assertEquals(rating, movie.getRating());
    }
}
