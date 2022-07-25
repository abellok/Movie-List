package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MovieListTest {
    private MovieList testList;
    private Movie movie1;
    private Movie movie2;
    private Movie movie3;

    @BeforeEach
    void runBefore() {
        testList = new MovieList();
        movie1 = new Movie("Dark Shadows", "Tim Burton", "Comedy");
        movie2 = new Movie("Corpse Bride", "Tim Burton", "Fantasy");
        movie3 = new Movie("Spirited Away", "Hayao Miyazaki", "Fantasy");
    }

    @Test
    void testConstructor() {
        assertEquals(0, testList.listSize());
    }

    @Test
    void testAddMovie() {
        testList.addMovie(movie1);
        assertEquals(1, testList.listSize());
        testList.addMovie(movie2);
        assertEquals(2, testList.listSize());
    }

    @Test
    void testDeleteMovie() {
       testList.addMovie(movie1);
       testList.addMovie(movie2);
       assertEquals(2, testList.listSize());
       testList.deleteMovie(movie2);
       assertEquals(1, testList.listSize());
    }

    @Test
    void testFindMoviesByDirector() {
        testList.addMovie(movie1);
        testList.addMovie(movie3);
        testList.addMovie(movie2);
        assertEquals(2, testList.findMoviesByDirector("Tim Burton").size());
        assertEquals(1, testList.findMoviesByDirector("Hayao Miyazaki").size());
    }

    @Test
    void testFindMoviesByDirectorNonExistent() {
        testList.addMovie(movie1);
        testList.addMovie(movie2);
        testList.addMovie(movie3);
        assertEquals(0, testList.findMoviesByDirector("Christopher Nolan").size());
    }

    @Test
    void testFindMovieByTitle() {
        testList.addMovie(movie1);
        testList.addMovie(movie2);
        testList.addMovie(movie3);
        assertEquals(movie1, testList.findMovieByTitle("Dark Shadows"));
        assertEquals(movie3, testList.findMovieByTitle("Spirited Away"));
        assertEquals(movie2, testList.findMovieByTitle("Corpse Bride"));
        assertEquals(null, testList.findMovieByTitle("Cinderella"));
    }

    @Test
    void testListSize() {
        testList.addMovie(movie1);
        testList.addMovie(movie2);
        testList.addMovie(movie3);
        assertEquals(3, testList.listSize());
    }



}
