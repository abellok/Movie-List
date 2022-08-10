package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

// represents a list of watched movies
// NOTE: borrows code from the JsonSerializationDemo
public class MovieList extends Observable implements Writable {
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
        EventLog.getInstance().logEvent(new Event("New movie added"));
    }

    // REQUIRES: specified movie exists in list
    // MODIFIES: this
    // EFFECTS: deletes a movie from a list
    public void deleteMovie(Movie movie) {
        movieList.remove(movie);
        EventLog.getInstance().logEvent(new Event("Movie deleted"));
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
    public int checkListSize() {
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

    // EFFECTS: returns an unmodifiable list of movies in the list
    public List<Movie> getUnmodifiableMovies() {
        return Collections.unmodifiableList(movieList);
    }

    // EFFECTS: returns a basic list of movies
    public List<Movie> getMovies() {
        return movieList;
    }

    // EFFECTS: assigns movies to JSON list
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("movies", moviesToJson());
        return json;
    }

    // EFFECTS: returns movies in this movie list as a JSON array
    private JSONArray moviesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Movie m : movieList) {
            jsonArray.put(m.toJson());
        }

        return jsonArray;
    }
}
