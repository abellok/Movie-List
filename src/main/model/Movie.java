package model;

import org.json.JSONObject;
import persistence.Writable;

// represents a single movie, with a title, director, genre, and movie
public class Movie implements Writable {
    private String title;
    private String director;
    private String genre;
    private int rating;

    // REQUIRES: genre must be one of: action, fantasy, comedy, drama,
    //           mystery, horror, or romance
    // EFFECTS: constructs a movie with a title, director, and genre
    public Movie(String title, String director, String genre) {
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.rating = 0;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDirector() {
        return director;
    }

    public String getGenre() {
        return this.genre;
    }

    public int getRating() {
        return this.rating;
    }

    // REQUIRES: rating must be between 1-5, inclusive
    // MODIFIES: this
    // EFFECTS: changes
    public void changeRating(int rating) {
        this.rating = rating;
    }

    // assigns variables of movie to JSON keys
    @Override
    public JSONObject toJson() {
        org.json.JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("director", director);
        json.put("genre", genre);
        json.put("rating", rating);
        return json;
    }
}
