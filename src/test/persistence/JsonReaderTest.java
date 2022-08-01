package persistence;

import model.Movie;
import model.MovieList;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// NOTE: borrows code from the JsonSerializationDemo
public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            MovieList movieList = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyMovieList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyMovieList.json");
        try {
            MovieList movieList = reader.read();
            assertEquals(0, movieList.checkListSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralMovieList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralMovieList.json");
        try {
            MovieList movieList = reader.read();
            List<Movie> movies = movieList.getMovies();
            assertEquals(2, movies.size());
            checkMovie("West Side Story", "Steven Spielberg", "musical",
                    3, movies.get(0));
            checkMovie("My Neighbour Totoro", "Hayao Miyazaki", "animation",
                    4, movies.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }

    }
}
