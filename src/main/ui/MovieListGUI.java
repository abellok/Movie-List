package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.BoxLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

// Movie List GUI
// NOTE: borrows code from LabelChanger application, the JSONSerializationDemo, and other demos from Java Tutorials
public class MovieListGUI extends JFrame implements ActionListener, ListSelectionListener {
    private boolean alreadyEnabled = false;
    private static final String JSON_STORE = "./data/library.json";

    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private JButton addMovieButton;
    private JButton deleteMovieButton;
    private JButton loadListButton;
    private JButton saveListButton;
    private ImageIcon image;
    private JList list;
    private DefaultListModel listModel;
    private MovieList movieList;

    public MovieListGUI() {
        super("Movie List App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        createHomeScreen(this.getContentPane());
        createButtonListeners(this.getContentPane());
        movieList = new MovieList();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
    }

    // EFFECTS: creates the homescreen window
    public void createHomeScreen(Container window) {
        window.setLayout(new BoxLayout(window, BoxLayout.Y_AXIS));
        window.add(displayTitle());

        createListScroller(window);

        createButtonPanel(window);
    }

    // EFFECTS: creates title label
    public JLabel displayTitle() {
        JLabel title = new JLabel("This is your movie list!");
        title.setFont(new Font("Serif", Font.BOLD, 25));
        title.setAlignmentX(CENTER_ALIGNMENT);
        return title;
    }

    // EFFECTS: creates buttons and adds them to a panel
    public void createButtons(Container panel) {
        addMovieButton = new JButton("Add");
        deleteMovieButton = new JButton("Delete");
        loadListButton = new JButton("Load");
        saveListButton = new JButton("Save");

        addMovieButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(addMovieButton);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        deleteMovieButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(deleteMovieButton);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        loadListButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(loadListButton);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        saveListButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(saveListButton);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
    }

    // EFFECTS: adds button panel to window
    public void createButtonPanel(Container window) {
        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
        buttons.setAlignmentX(CENTER_ALIGNMENT);
        createButtons(buttons);
        window.add(buttons);
    }

    // EFFECTS: creates action listeners for each button
    public void createButtonListeners(Container window) {
        addMovieButton.addActionListener(this);
        addMovieButton.setActionCommand("Add Movie");
        addMovieButton.setEnabled(true);

        deleteMovieButton.addActionListener(this);
        deleteMovieButton.setActionCommand("Delete Movie");
        deleteMovieButton.setEnabled(false);

        loadListButton.addActionListener(this);
        loadListButton.setActionCommand("Load File");

        saveListButton.addActionListener(this);
        saveListButton.setActionCommand("Save File");
    }

    // EFFECTS: implements an action when a button is clicked
    @SuppressWarnings("methodlength")
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Add Movie")) {
            PopoutWindow popoutWindow = new PopoutWindow(this);
        }

        if (e.getActionCommand().equals("Delete Movie")) {
            int index = list.getSelectedIndex();
            listModel.remove(index);
            movieList.getMovies().remove(index);

            int size = listModel.getSize();

            // if no movies left, disable delete button
            if (size == 0) {
                deleteMovieButton.setEnabled(false);

            } else { // Select an index.
                if (index == listModel.getSize()) {
                    // removed item in last position
                    index--;
                }
                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
        }

        if (e.getActionCommand().equals("Load File")) {
            try {
                movieList = jsonReader.read();
            } catch (IOException f) {
                Toolkit.getDefaultToolkit().beep();
            }
            addSavedMovies(movieList.getUnmodifiableMovies());
        }

        if (e.getActionCommand().equals("Save File")) {
            try {
                jsonWriter.open();
                jsonWriter.write(movieList);
                jsonWriter.close();
            } catch (FileNotFoundException f) {
                Toolkit.getDefaultToolkit().beep();
            }
        }
    }

    // EFFECTS: creates a visible and scrollable JPanel
    public void createListScroller(Container window) {
        listModel = new DefaultListModel();
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(7);
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setAlignmentX(CENTER_ALIGNMENT);

        createMovieListPanel(window, listScroller);
    }

    // EFFECTS: adds subtitle and scrollable panel to window
    public void createMovieListPanel(Container window, JScrollPane scroller) {
        JPanel movieList = new JPanel();
        movieList.setLayout(new BoxLayout(movieList, BoxLayout.Y_AXIS));
        JLabel label = new JLabel("Movies currently in list:");
        label.setFont(new Font("Serif", Font.PLAIN, 20));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        movieList.add(label);
        movieList.add(Box.createRigidArea(new Dimension(0, 5)));
        movieList.add(scroller);
        movieList.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        window.add(movieList);
    }

    // EFFECTS: adds saved movies to scroller panel
    public void addSavedMovies(List<Movie> movies) {
        for (Movie m : movies) {
            listModel.addElement(m.getTitle());
        }
    }

    // EFFECTS: creates a popout to add a movie to the list
//    @SuppressWarnings("methodlength")
//    public void createPopout() {
//        JFrame popoutWindow = new JFrame();
//        JLabel instructions = new JLabel("Please input movie information, then press 'add'");
//        instructions.setFont(new Font("Serif", Font.BOLD, 20));
//        instructions.setAlignmentX(CENTER_ALIGNMENT);
//        popoutWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        popoutWindow.setLayout(new BoxLayout(popoutWindow, BoxLayout.Y_AXIS));
//        popoutWindow.setVisible(true);
//        popoutWindow.add(instructions);
//
//        createTextFields(popoutWindow);
//
//        String title = movieTitle.getText();
//        String director = movieDirector.getText();
//        String genre = movieGenre.getText();
//
//        if (title.equals("") || director.equals("") || genre.equals("")) {
//            Toolkit.getDefaultToolkit().beep();
//            return;
//        }
//
//        int index = list.getSelectedIndex(); //get selected index
//        if (index == -1) { //no selection, so insert at beginning
//            index = 0;
//        } else {           //add after the selected item
//            index++;
//        }
//        listModel.addElement(title);
//
//        movieList.addMovie(new Movie(movieTitle.getText(), movieDirector.getText(), movieGenre.getText()));
//
//        movieTitle.requestFocusInWindow();
//        movieTitle.setText("");
//        movieDirector.requestFocusInWindow();
//        movieDirector.setText("");
//        movieGenre.requestFocusInWindow();
//        movieGenre.setText("");
//
//        list.setSelectedIndex(index);
//        list.ensureIndexIsVisible(index);
//    }
//
//    // EFFECTS: creates texts fields
//    @SuppressWarnings("methodlength")
//    public void createTextFields(Container window) {
//        movieTitle = new JTextField(5);
//        movieDirector = new JTextField(5);
//        movieGenre = new JTextField(5);
//
//        movieTitle.getDocument().addDocumentListener(this);
//        movieDirector.getDocument().addDocumentListener(this);
//        movieGenre.getDocument().addDocumentListener(this);
//
//        JPanel newMovie = new JPanel();
//        newMovie.setLayout(new BoxLayout(newMovie, BoxLayout.X_AXIS));
//        JLabel newTitle = new JLabel("Title:");
//        newTitle.setFont(new Font("Serif", Font.PLAIN, 15));
//        JLabel newDirector = new JLabel("Director:");
//        newDirector.setFont(new Font("Serif", Font.PLAIN, 15));
//        JLabel newGenre = new JLabel("Genre:");
//        newGenre.setFont(new Font("Serif", Font.PLAIN, 15));
//
//        newMovie.add(newTitle);
//        newMovie.add(Box.createHorizontalStrut(5));
//        newMovie.add(movieTitle);
//        newMovie.add(Box.createHorizontalStrut(5));
//
//        newMovie.add(newDirector);
//        newMovie.add(Box.createHorizontalStrut(5));
//        newMovie.add(movieDirector);
//        newMovie.add(Box.createHorizontalStrut(5));
//
//        newMovie.add(newGenre);
//        newMovie.add(Box.createHorizontalStrut(5));
//        newMovie.add(movieGenre);
//
//        window.add(newMovie);
//    }
//
//    // EFFECTS: checks if text fields are empty. Required by DocumentListener.
//    public void removeUpdate(DocumentEvent e) {
//        handleEmptyTextField(e);
//    }
//
//    // EFFECTS: enables add movie button. Required by DocumentListener.
//    public void insertUpdate(DocumentEvent e) {
//        if (!alreadyEnabled) {
//            popoutAddMoviebutton.setEnabled(true);
//        }
//    }
//
//    // EFFECTS: enables add movie button if text fields are not empty and if button is not already enabled.
//    //          Required by DocumentListener.
//    public void changedUpdate(DocumentEvent e) {
//        if (!handleEmptyTextField(e)) {
//            if (!alreadyEnabled) {
//                popoutAddMoviebutton.setEnabled(true);
//            }
//        }
//    }
//
//    // EFFECTS: when text field is empty, disables add movie button and returns true. Otherwise, returns false.
//    private boolean handleEmptyTextField(DocumentEvent e) {
//        if (e.getDocument().getLength() <= 0) {
//            popoutAddMoviebutton.setEnabled(false);
//            alreadyEnabled = false;
//            return true;
//        }
//        return false;
//    }

    // EFFECTS: enables the delete button if something in the list is selected,
    //          otherwise keeps delete button disabled
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
                deleteMovieButton.setEnabled(false);

            } else {
                //Selection, enable the delete button.
                deleteMovieButton.setEnabled(true);
            }
        }
    }

    public JList getJList() {
        return list;
    }

    public DefaultListModel getListModel() {
        return listModel;
    }

    public MovieList getMovieList() {
        return movieList;
    }
}