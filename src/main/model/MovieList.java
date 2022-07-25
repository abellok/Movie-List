package model;

import java.util.ArrayList;

// represents a list of watched movies
public class MovieList {
    private ArrayList<Movie> movieList;

    // EFFECTS: constructs an empty movie list
    public MovieList() {
        this.movieList = new ArrayList<Movie>();
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
    public ArrayList<String> findMoviesByDirector(String director) {
        ArrayList<String> moviesByDirector = new ArrayList<>();
        for (Movie m : movieList) {
            if (m.getDirector().equals(director)) {
                moviesByDirector.add(m.getTitle());
            }
        }
        return moviesByDirector;
    }

    // EFFECTS: searches the list for a specified movie by its title, and returns it;
    //          if no such movie exists, returns null
    public Movie findMovieByTitle(String title) {
        for (Movie m : movieList) {
            if (m.getTitle().equals(title)) {
                return m;
            }
        }
        return null;
    }

    // EFFECTS: returns the current size of the movie list
    public int listSize() {
        int size = 0;
        for (Movie m : movieList) {
            size = size + 1;
        }
        return size;
    }

    // EFFECTS: returns a list of titles currently in the list
    public ArrayList<String> getMovieTitles() {
        ArrayList<String> titles = new ArrayList<>();
        for (Movie m : movieList) {
            titles.add(m.getTitle());
        }
        return titles;
    }
}
