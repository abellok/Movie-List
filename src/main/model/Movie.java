package model;

public class Movie {
    private String title;
    private String director;
    private String genre;
    private int rating;

    // REQUIRES: genre must be one of: action, fantasy, comedy, drama,
    //           mystery, horror, or romance
    // EFFECTS: constructs a movie with a title, director, and genre
    public Movie(String title, String director, String genre) {
        //stub
    }

    public String getTitle() {
        return ""; //stub
    }

    public String getDirector() {
        return ""; //stub
    }

    public String getGenre() {
        return ""; //stub
    }

    public boolean getRating() {
        return false; //stub
    }

    // REQUIRES: rating must be between 1-5, inclusive
    // MODIFIES: this
    // EFFECTS: changes
    public void changeRating(int rating) {
        //stub
    }

}
