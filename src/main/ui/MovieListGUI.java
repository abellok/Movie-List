package ui;

import model.*;
import model.Event;
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
// NOTE: borrows code from LabelChanger application, JSONSerializationDemo, Alarm System application,
//       and other demos from Java Tutorials
public class MovieListGUI extends JFrame implements ActionListener, ListSelectionListener, LogPrinter {
    private static final String JSON_STORE = "./data/movieList.json";

    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private JButton addMovieButton;
    private JButton deleteMovieButton;
    private JButton loadListButton;
    private JButton saveListButton;
    private ImageIcon image;
    private JLabel label;
    private JList list;
    private DefaultListModel listModel;
    private MovieList movieList;
    private PopoutWindow popoutWindow;

    public MovieListGUI() {
        super("Movie List App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        createHomeScreen(this.getContentPane());
        createButtonListeners();
        movieList = new MovieList();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    printLog(EventLog.getInstance());
                } catch (LogException ex) {
                    System.out.println("Issues printing log");
                    ex.printStackTrace();
                }
                dispose();
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
    }


    // EFFECTS: creates the homescreen window
    public void createHomeScreen(Container window) {
        window.setLayout(new BoxLayout(window, BoxLayout.Y_AXIS));
        window.add(displayTitle());

        createGraphic(window);

        createListScroller(window);

        createButtonPanel(window);
    }

    // EFFECTS: creates graphic and displays it in window
    public void createGraphic(Container window) {
        this.image = new ImageIcon("./data/movie-excited.gif");
        this.label = new JLabel(image);
        label.setAlignmentX(CENTER_ALIGNMENT);
        window.add(label);
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
    public void createButtonListeners() {
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
            popoutWindow = new PopoutWindow(this);
        }

        if (e.getActionCommand().equals("Delete Movie")) {
            int index = list.getSelectedIndex();
            listModel.remove(index);
            movieList.deleteMovie(movieList.getMovies().get(index));

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
            addSavedMovies(movieList.getMovies());
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

    // EFFECTS: enables the delete button if something in the list is selected,
    //          otherwise keeps delete button disabled
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {

            if (list.getSelectedIndex() == -1) {
                deleteMovieButton.setEnabled(false);

            } else {
                //Selection, enable the delete button.
                deleteMovieButton.setEnabled(true);
            }
        }
    }

    // EFFECTS: Prints events from a log to the console
    @Override
    public void printLog(EventLog el) throws LogException {
        for (Event next : el) {
            System.out.println(next.getDescription());
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