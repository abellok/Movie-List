package ui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.BoxLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.List;

// Popout window to add movies to list
public class PopoutWindow extends JFrame implements ActionListener,
        DocumentListener {
    private boolean alreadyEnabled = false;

    private JLabel instructions;
    private JTextField movieTitle;
    private JTextField movieDirector;
    private JTextField movieGenre;
    private JButton popoutAddMoviebutton;
    private MovieListGUI gui;

    PopoutWindow(MovieListGUI gui) {
        super("Add Movie");
        this.gui = gui;
        instructions = new JLabel("Please input movie information, then press 'add'");
        instructions.setFont(new Font("Serif",Font.BOLD, 20));
        instructions.setAlignmentX(CENTER_ALIGNMENT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setVisible(true);
        add(instructions);

        createTextFields(this.getContentPane());

        createAddButton(this.getContentPane());
    }

    public void createAddButton(Container window) {
        popoutAddMoviebutton = new JButton("Add");
        popoutAddMoviebutton.setAlignmentX(Component.CENTER_ALIGNMENT);
        popoutAddMoviebutton.addActionListener(this);
        popoutAddMoviebutton.setActionCommand("Add New Movie");
        popoutAddMoviebutton.setEnabled(true);
        window.add(this.getContentPane());
    }

    // EFFECTS: creates texts fields
    @SuppressWarnings("methodlength")
    public void createTextFields(Container window) {
        movieTitle = new JTextField(5);
        movieDirector = new JTextField(5);
        movieGenre = new JTextField(5);

        movieTitle.getDocument().addDocumentListener(this);
        movieDirector.getDocument().addDocumentListener(this);
        movieGenre.getDocument().addDocumentListener(this);

        JPanel newMovie = new JPanel();
        newMovie.setLayout(new BoxLayout(newMovie, BoxLayout.X_AXIS));
        JLabel newTitle = new JLabel("Title:");
        newTitle.setFont(new Font("Serif", Font.PLAIN, 15));
        JLabel newDirector = new JLabel("Director:");
        newDirector.setFont(new Font("Serif", Font.PLAIN, 15));
        JLabel newGenre = new JLabel("Genre:");
        newGenre.setFont(new Font("Serif", Font.PLAIN, 15));

        newMovie.add(newTitle);
        newMovie.add(Box.createHorizontalStrut(5));
        newMovie.add(movieTitle);
        newMovie.add(Box.createHorizontalStrut(5));

        newMovie.add(newDirector);
        newMovie.add(Box.createHorizontalStrut(5));
        newMovie.add(movieDirector);
        newMovie.add(Box.createHorizontalStrut(5));

        newMovie.add(newGenre);
        newMovie.add(Box.createHorizontalStrut(5));
        newMovie.add(movieGenre);

        window.add(newMovie);
    }


    @SuppressWarnings("methodlength")
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Add New Movie")) {
            String title = movieTitle.getText();
            String director = movieDirector.getText();
            String genre = movieGenre.getText();

            if (title.equals("") || director.equals("") || genre.equals("")) {
                Toolkit.getDefaultToolkit().beep();
                return;
            }

            int index = gui.getJList().getSelectedIndex(); //get selected index
            if (index == -1) { //no selection, so insert at beginning
                index = 0;
            } else {           //add after the selected item
                index++;
            }
            gui.getListModel().addElement(title);

            gui.getMovieList().addMovie(new Movie(movieTitle.getText(), movieDirector.getText(), movieGenre.getText()));

            gui.getJList().setSelectedIndex(index);
            gui.getJList().ensureIndexIsVisible(index);


            movieTitle.requestFocusInWindow();
            movieTitle.setText("");
            movieDirector.requestFocusInWindow();
            movieDirector.setText("");
            movieGenre.requestFocusInWindow();
            movieGenre.setText("");


        }
    }

    // EFFECTS: checks if text fields are empty. Required by DocumentListener.
    public void removeUpdate(DocumentEvent e) {
        handleEmptyTextField(e);
    }

    // EFFECTS: enables add movie button. Required by DocumentListener.
    public void insertUpdate(DocumentEvent e) {
        if (!alreadyEnabled) {
            popoutAddMoviebutton.setEnabled(true);
        }
    }

    // EFFECTS: enables add movie button if text fields are not empty and if button is not already enabled.
    //          Required by DocumentListener.
    public void changedUpdate(DocumentEvent e) {
        if (!handleEmptyTextField(e)) {
            if (!alreadyEnabled) {
                popoutAddMoviebutton.setEnabled(true);
            }
        }
    }

    // EFFECTS: when text field is empty, disables add movie button and returns true. Otherwise, returns false.
    private boolean handleEmptyTextField(DocumentEvent e) {
        if (e.getDocument().getLength() <= 0) {
            popoutAddMoviebutton.setEnabled(false);
            alreadyEnabled = false;
            return true;
        }
        return false;
    }
}
