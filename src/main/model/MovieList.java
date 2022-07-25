package model;

import java.util.ArrayList;

public class MovieList {
    private ArrayList<Movie> movieList;

    // EFFECTS: constructs an empty movie list
    public MovieList() {
        this.movieList = new ArrayList();
    }

    // REQUIRES: specified movie does not already exist in list
    // MODIFIES: this
    // EFFECTS: adds a movie to the list
    public void addMovie(Movie movie) {
        movieList.add(movie);
    }

    // REQUIRES: specified movie exists in list
    // MODIFIES: this
    // EFFECTS: deletes a movie from a list
    public void deleteMovie(Movie movie) {
        movieList.remove(movie);
    }

    // EFFECTS: returns a list of movies directed by a specific director;
    //          if no movies exist in the list, returns an empty list
    public ArrayList<Movie> findMoviesByDirector(String director) {
        ArrayList<Movie> moviesByDirector = new ArrayList<>();
        for (Movie m : movieList) {
            if (m.getDirector() == director) {
                moviesByDirector.add(m);
            }
        }
        return moviesByDirector;
    }

    // EFFECTS: returns the current size of the movie list
    public int listSize() {
        int size = 0;
        for (Movie m : movieList) {
            size = size + 1;
        }
        return size;
    }
}
