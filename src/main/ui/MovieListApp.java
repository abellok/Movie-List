package ui;

import model.MovieList;
import model.Movie;

import java.util.ArrayList;
import java.util.Scanner;

// Movie list application
// NOTE: borrows code from the Teller application
public class MovieListApp {
    private MovieList movieList;
    private Scanner input;

    // runs the movie list application
    public MovieListApp() {
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    public void runApp() {
        boolean keepGoing = true;
        String command = null;

        initialize();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nStay golden Ponyboy!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    public void processCommand(String command) {
        if (command.equals("a")) {
            doAddMovie();
        } else if (command.equals("p")) {
            doPrintMovieList(movieList.getMovieTitles());
        } else if (command.equals("d")) {
            doDeleteMovie();
        } else if (command.equals("s")) {
            doSearchDirector();
        } else if (command.equals("r")) {
            doRateMovie();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes movie list
    public void initialize() {
        movieList = new MovieList();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    public void displayMenu() {
        System.out.println("\nTHIS IS YOUR MOVIE LIST!");
        System.out.println("\tPlease select an option:");
        System.out.println("\tp -> print current movie list");
        System.out.println("\ta -> add movie");
        System.out.println("\td -> delete movie");
        System.out.println("\ts -> search movies made by a specific director");
        System.out.println("\tr -> rate a movie in the list");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: prints current movie list to the screen
    public void doPrintMovieList(ArrayList<String> list) {
        for (String s : list) {
            System.out.println(s);
        }
        System.out.println("");
    }

    // MODIFIES: this
    // EFFECTS: adds a movie to the list
    public void doAddMovie() {
        Movie movie = new Movie(inputTitle(), inputDirector(), inputGenre());
        movieList.addMovie(movie);
        System.out.println("Movie added successfully.");
        System.out.println("");
    }

    // EFFECTS: prompts user to input new movie title
    public String inputTitle() {
        System.out.println("What is the movie title?");
        String newTitle = input.next();
        if (newTitle.length() <= 0) {
            System.out.println("Invalid. Please try again.");
            inputTitle();
        }
        return newTitle;
    }

    // EFFECTS: prompts user to input new director of movie
    public String inputDirector() {
        System.out.println("Who is the director?");
        String newDirector = input.next();
        if (newDirector.length() <= 0) {
            System.out.println("Invalid. Please try again.");
            inputDirector();
        }
        return newDirector;
    }

    // EFFECTS: prompts user to input new genre of movie
    public String inputGenre() {
        System.out.println("What is the movie genre?");
        String newGenre = input.next();
        if (newGenre.length() <= 0) {
            System.out.println("Invalid. Please try again.");
            inputGenre();
        }
        return newGenre;
    }

    // MODIFIES: this
    // EFFECTS: deletes a movie from the list
    public void doDeleteMovie() {
        System.out.println("What is the title of the movie you want to delete?");
        doPrintMovieList(movieList.getMovieTitles());
        String title = input.next();
        if (title.length() <= 0) {
            System.out.println("Invalid. Please try again.");
            doDeleteMovie();
        } else {
            if (null == movieList.findMovieByTitle(title)) {
                System.out.println("Movie not in list");
            } else {
                movieList.deleteMovie(movieList.findMovieByTitle(title));
                System.out.println("Movie deleted successfully");
            }
        }
    }

    // EFFECTS: conducts a search of movies by a specified director
    public void doSearchDirector() {
        System.out.println("Who is the director of movies want to search for?");
        String director = input.next();
        if (director.length() <= 0) {
            System.out.println("Invalid. Please try again.");
            doSearchDirector();
        }

        ArrayList<String> list = movieList.findMoviesByDirector(director);
        if (list.size() == 0) {
            System.out.println("No movies by that director were found");
        } else {
            doPrintMovieList(list);
        }
    }

    // EFFECTS: conducts adding or changing a rating of a movie in the list
    public void doRateMovie() {
        System.out.println("What movie do you want to change the rating of?");
        doPrintMovieList(movieList.getMovieTitles());
        System.out.println("");
        System.out.println("Movie title:");
        String title = input.next();
        if (title.length() <= 0) {
            System.out.println("Invalid. Please try again.");
            doRateMovie();
        }

        Movie foundMovie = movieList.findMovieByTitle(title);
        if (foundMovie == null) {
            System.out.println("Movie not in list");
        } else {
            System.out.println("What rating would you like to give this movie? (1-5)");
            int rating = input.nextInt();
            foundMovie.changeRating(rating);
            System.out.println("Movie rating changed successfully.");
        }
        System.out.println("");
    }

}

