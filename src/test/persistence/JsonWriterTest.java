package persistence;

import model.Movie;
import model.MovieList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// NOTE: borrows code from the JsonSerializationDemo
public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            MovieList movieList = new MovieList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            MovieList movieList = new MovieList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyMovieList.json");
            writer.open();
            writer.write(movieList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyMovieList.json");
            movieList = reader.read();
            assertEquals(0, movieList.checkListSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            MovieList movieList = new MovieList();
            Movie movie1 = new Movie("Spirited Away", "Hayao Miyazaki",
                    "animation");
            Movie movie2 = new Movie("Corpse Bride", "Tim Burton", "fantasy");
            movie1.changeRating(5);
            movieList.addMovie(movie1);
            movie2.changeRating(4);
            movieList.addMovie(movie2);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralMovieList.json");
            writer.open();
            writer.write(movieList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralMovieList.json");
            movieList = reader.read();
            List<Movie> movies = movieList.getMovies();
            assertEquals(2, movies.size());
            checkMovie("Spirited Away", "Hayao Miyazaki", "animation", 5,
                    movies.get(0));
            checkMovie("Corpse Bride", "Tim Burton", "fantasy", 4,
                    movies.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
