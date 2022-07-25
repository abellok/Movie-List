package model;

import java.util.ArrayList;
import java.util.LinkedList;

public class MovieList {
    private LinkedList<Movie> movieList;

    // EFFECTS: constructs an empty movie list
    public MovieList() {
        this.movieList = new LinkedList();
    }

    // REQUIRES: specified movie does not already exist in list
    // MODIFIES: this
    // EFFECTS: adds a movie to the list
    public void addMovie(Movie movie) {
        //stub
    }

    // REQUIRES: specified movie exists in list
    // MODIFIES: this
    // EFFECTS: deletes a movie from a list
    public void deleteMovie(Movie movie) {
        //stub
    }

    // EFFECTS: returns a list of movies directed by a specific director;
    //          if no movies exist in the list, returns an empty list
    public ArrayList<Movie> findMoviesByDirector(String director) {
        return new ArrayList<>(); //stub
    }

    // EFFECTS: returns the current size of the movie list
    public int listSize() {
        return 0; //stub
    }
}
