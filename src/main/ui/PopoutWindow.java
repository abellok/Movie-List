package ui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.BoxLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

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
    private ImageIcon imageIcon;
    private JLabel label;

    // a popup window that allows users to add a movie to a list
    PopoutWindow(MovieListGUI gui) {
        super("Add Movie");
        this.gui = gui;
        instructions = new JLabel("Please input movie information, then press 'add'");
        instructions.setFont(new Font("Serif",Font.BOLD, 20));
        instructions.setAlignmentX(CENTER_ALIGNMENT);
        setSize(500, 430);
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(20, 20, 20, 20));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        setVisible(true);

        this.getContentPane().add(instructions);
        this.getContentPane().add(Box.createVerticalStrut(20));

        createGraphic(this.getContentPane());
        this.getContentPane().add(Box.createVerticalStrut(20));

        createTextFields(this.getContentPane());
        this.getContentPane().add(Box.createVerticalStrut(20));

        createAddButton(this.getContentPane());
    }

    // EFFECTS: initializes graphic and adds it to window
    public void createGraphic(Container window) {
        this.imageIcon = new ImageIcon("./data/disney-question-720x340.jpg");
        Image image = imageIcon.getImage();
        Image newImg = image.getScaledInstance(400, 200,  java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newImg);
        this.label = new JLabel(imageIcon);
        label.setAlignmentX(CENTER_ALIGNMENT);
        window.add(label);
    }

    // MODIFIES: this
    // EFFECTS: creates add button
    public void createAddButton(Container window) {
        popoutAddMoviebutton = new JButton("Add");
        popoutAddMoviebutton.setAlignmentX(Component.CENTER_ALIGNMENT);
        popoutAddMoviebutton.addActionListener(this);
        popoutAddMoviebutton.setActionCommand("Add New Movie");
        popoutAddMoviebutton.setEnabled(true);
        window.add(popoutAddMoviebutton);
    }

    // MODIFIES: this
    // EFFECTS: creates texts fields
    @SuppressWarnings("methodlength")
    public void createTextFields(Container window) {
        movieTitle = new JTextField(5);
        movieTitle.setBounds(150,100, 80,30);
        movieDirector = new JTextField(5);
        movieDirector.setBounds(300,100, 80,30);
        movieGenre = new JTextField(5);
        movieGenre.setBounds(450,100, 80,30);

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
        newMovie.add(Box.createHorizontalStrut(20));

        newMovie.add(newDirector);
        newMovie.add(Box.createHorizontalStrut(5));
        newMovie.add(movieDirector);
        newMovie.add(Box.createHorizontalStrut(20));

        newMovie.add(newGenre);
        newMovie.add(Box.createHorizontalStrut(5));
        newMovie.add(movieGenre);

        window.add(newMovie);
    }

    // MODIFIES: MovieListGUI
    // EFFECTS: when 'add' button is pressed, adds a movie to the list only if all text boxes are filled;
    //          resets text boxes on click
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
