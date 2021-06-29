package ui;

import model.Album;
import model.Artist;
import model.Library;
import model.Song;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.*;
import java.util.List;

// This class creates a new frame, background music, buttons on the frame, and a table to display the library
// from the beginning.
// I credit the oracle site documentation, and AlarmCodeSystem repository from our course

public class MusicManagementProgram {
    private JFrame frame;
    private List<String> songList = new ArrayList<>();
    private JPanel artistPanel;
    private JPanel songPanel;
    private JPanel albumPanel;
    private final MusicApp library = new MusicApp();
    private Library maLibrary = library.library;
    private JTextField name;
    private JComboBox genre;
    private JTextField artist;
    private JTextArea songs;
    private JTable libraryDisplay;
    private JPanel panel;
    private BackgroundMusic music;
    private String[] genreOptions = { "Hip-Hop","Rap", "Pop","Rock","Country","EDM"};
    //private Map<String, Integer> genreTally = new HashMap<>();
    private List<String> maxValues = new ArrayList<>();

    // constructor
    public MusicManagementProgram() throws IOException {
        frame = new JFrame();
        music = new BackgroundMusic();

        setUpMap();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new FlowLayout());


        JButton addArtist = new JButton("Add Artist");
        JButton addSong = new JButton("Add Song");
        JButton addAlbum = new JButton("Add Album");
        JButton saveLibrary = new JButton("Save Library");
        JButton loadLibrary = new JButton("Load Library");


        frame.getContentPane().add(addArtist, new GridBagConstraints());
        frame.getContentPane().add(addSong); // Adds Button to content pane of frame
        frame.getContentPane().add(addAlbum);
        frame.getContentPane().add(loadLibrary);
        frame.getContentPane().add(saveLibrary);
        addJTable();

        frame.setVisible(true);
        actionSetUp(addArtist, addSong, addAlbum, saveLibrary, loadLibrary);
    }

    private void setUpMap() {
        for (String s: genreOptions) {
            maLibrary.addGenreToLibrary(s,0);
        }
//        for (Map.Entry<String, Integer> entry : maLibrary.getGenreMap().entrySet()) {
//            genreTally.put(entry.getKey(), entry.getValue());
//        }
    }


    // MODIFIES: this
    // EFFECT: sets ActionCommands for the buttons and ActionListeners for them too
    private void actionSetUp(JButton addArt, JButton addS, JButton addA, JButton saveL, JButton loadL) {
        addArt.setActionCommand("artist");
        addS.setActionCommand("song");
        addA.setActionCommand("album");
        saveL.setActionCommand("save");
        loadL.setActionCommand("load");
        addArt.addActionListener(this::actionPerformed);
        addS.addActionListener(this::actionPerformed);
        addA.addActionListener(this::actionPerformed);
        loadL.addActionListener(this::actionPerformed);
        saveL.addActionListener(this::actionPerformed);
    }

    // MODIFIES: this
    // EFFECTS: adds a JTable to the frame with library information
    private void addJTable() {
        int artistSize = maLibrary.getArtistLibrary().size();
        int songSize = maLibrary.getSongLibrary().size();
        int albumSize = maLibrary.getAlbumLibrary().size();

        int rows = getRows(artistSize, songSize, albumSize);

        Object[][] data = new Object[rows][4];

        inputArtists(artistSize, data);
        inputSongs(songSize, data);
        inputAlbums(albumSize, data);
        inputFavoriteGenre(data);

        String[] columnNames = {"Albums", "Songs", "Artists", "Recommended Genres"};

        panel = new JPanel();
        panel.setBorder(BorderFactory.createEtchedBorder());
        libraryDisplay = new JTable(data, columnNames);
        panel.add(new JScrollPane(libraryDisplay));
        libraryDisplay.setBorder(BorderFactory.createEtchedBorder());
        libraryDisplay.setBounds(30, 40, 200, 300);
        libraryDisplay.setPreferredScrollableViewportSize(new Dimension(600, 100));
        libraryDisplay.setFillsViewportHeight(true);

        frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }

    private void inputFavoriteGenre(Object[][] data) {
        int i = 0;
        int j = 0;
        maxValues = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : maLibrary.getGenreMap().entrySet()) {
            if (i < entry.getValue()) {
                i = entry.getValue();
            }
        }
        for (Map.Entry<String, Integer> entry : maLibrary.getGenreMap().entrySet()) {
            if (i == entry.getValue()) {
                maxValues.add(entry.getKey());
            }
        }
        System.out.println(maxValues);

        if (i != 0) {
            for (String s : maxValues) {
                data[j][3] = s;
                j++;
            }
        }
    }


    // MODIFIES: this, JTable
    // EFFECTS: inputs Album information into the JTable
    private void inputAlbums(int albumSize, Object[][] data) {
        for (int i = 0; i < albumSize; i++) {
            if (maLibrary.getAlbumLibrary().size() != 0) {
                data[i][0] = maLibrary.getAlbumLibrary().get(i).getAlbumName();
            }
        }
    }

    // MODIFIES: this, JTable
    // EFFECTS: inputs Song information into the JTable
    private void inputSongs(int songSize, Object[][] data) {
        for (int i = 0; i < songSize; i++) {
            if (maLibrary.getSongLibrary().size() != 0) {
                data[i][1] = maLibrary.getSongLibrary().get(i).getSongName();
            }
        }
    }

    // MODIFIES: this, JTable
    // EFFECTS: inputs Artist information into the JTable
    private void inputArtists(int artistSize, Object[][] data) {
        for (int i = 0; i < artistSize; i++) {
            if (maLibrary.getArtistLibrary().size() != 0) {
                data[i][2] = maLibrary.getArtistLibrary().get(i).getArtistName();
            }
        }
    }

    // MODIFIES:
    // EFFECTS: gets the biggest length between the library lists of
    // Artists, Songs, and Albums
    private int getRows(int artistSize, int songSize, int albumSize) {
        int rows;
        if (albumSize > songSize && albumSize > artistSize) {
            rows = albumSize;
        } else if (songSize > albumSize && songSize > artistSize) {
            rows = songSize;
        } else {
            rows = artistSize;
        }
        if (rows >= 6) {
            return rows;
        }
        return 6;
    }

    // MODIFIES: this
    // EFFECTS: catches the ActionEvent from the ActionListener and directs the code
    // according to what the ActionEvent was
    private void actionPerformed(ActionEvent e) {
        String net = e.getActionCommand();
        if (net.equals("album")) {
            albumEntries();
        } else if (net.equals("song")) {
            songEntries();
        } else if (net.equals("artist")) {
            artistEntries();
        } else if (net.equals("save")) {
            saveEntry();
        } else if (net.equals("load")) {
            loadEntry();
        } else if (net.equals("submit artist")) {
            catchSubmitArtist();
        } else if (net.equals("submit song")) {
            catchSubmitSong();
        } else if (net.equals("submit album")) {
            catchSubmitAlbum();
        }

    }

    // MODIFIES:
    // EFFECTS: Helper function for actionPerformed to catch exception
    private void catchSubmitAlbum() {
        try {
            submitAlbum();
        } catch (NotAllLinesFilledOut e) {
            JOptionPane.showMessageDialog(frame,
                    "Not all fields are filled out");
        } catch (DuplicateException e) {
            JOptionPane.showMessageDialog(frame,
                    "You already have this Album in your library");
        }
    }

    // MODIFIES:
    // EFFECTS: Helper function for actionPerformed to catch exception
    private void catchSubmitSong() {
        try {
            submitSong();
        } catch (NotAllLinesFilledOut notAllLinesFilledOut) {
            JOptionPane.showMessageDialog(frame,
                    "Not all fields are filled out");
        } catch (DuplicateException e) {
            JOptionPane.showMessageDialog(frame,
                    "This songs already exists in your library");
        }
    }

    // MODIFIES:
    // EFFECTS: Helper function for actionPerformed to catch exception
    private void catchSubmitArtist() {
        try {
            submitArtist();
        } catch (NotAllLinesFilledOut notAllLinesFilledOut) {
            JOptionPane.showMessageDialog(frame,
                    "Not all fields are filled out");
        }
    }

    // MODIFIES:
    // EFFECTS: Helper function for actionPerformed for saving the library
    private void saveEntry() {
        saveEntries();
        try {
            music.saveButtonMusic();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    // MODIFIES:
    // EFFECTS: Helper function for actionPerformed for loading the library
    private void loadEntry() {
        library.loadLibrary();
        maLibrary = library.library;
        loadGenre();
        frame.remove(panel);
        addJTable();
        try {
            music.saveButtonMusic();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void loadGenre() {
//        for (Map.Entry<String, Integer> entry : maLibrary.getGenreMap().entrySet()) {
//            genreTally.replace(entry.getKey(), 0);
//        }
//        System.out.println(maLibrary.getGenreMap());
//        for (Map.Entry<String, Integer> entry : maLibrary.getGenreMap().entrySet()) {
//            genreTally.put(entry.getKey(), entry.getValue());
//        }
    }

    // MODIFIES:
    // EFFECTS: Helper function for actionPerformed for submitting
    // an Album to Library
    private void submitAlbum() throws NotAllLinesFilledOut, DuplicateException {
        try {
            music.submitButtonMusic();
        } catch (IOException e) {
            e.printStackTrace();
        }

        getSongs(songs.getText());

        if (name.getText().equals("") || artist.getText().equals("")) {
            throw new NotAllLinesFilledOut();
        } else if (songList.size() == 1 && songList.get(0).equals("")) {
            throw new NotAllLinesFilledOut();
        }

        for (Album a : maLibrary.getAlbumLibrary()) {
            if (name.getText().equals(a.getAlbumName())) {
                if (artist.getText().equals(a.getAlbumArtist())) {
                    throw new DuplicateException();
                }
            }
        }
        frame.remove(panel);
        albumToLibrary();
        addJTable();
    }

    // MODIFIES:
    // EFFECTS: Helper function for actionPerformed for submitting
    // a song to the library
    private void submitSong() throws NotAllLinesFilledOut, DuplicateException {
        try {
            music.submitButtonMusic();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (name.getText().equals("") || artist.getText().equals("")) {
            throw new NotAllLinesFilledOut();
        }

        for (Song s : maLibrary.getSongLibrary()) {
            if (name.getText().equals(s.getSongName()) && artist.getText().equals(s.getArtistName())) {
                throw new DuplicateException();
            }
        }

        frame.remove(panel);
        songToLibrary();
        addJTable();
    }

    // MODIFIES:
    // EFFECTS: Helper function for actionedPerformed for submitting
    // an artist to the library
    private void submitArtist() throws NotAllLinesFilledOut {
        try {
            music.submitButtonMusic();
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.remove(panel);
        if (name.getText().equals("")) {
            throw new NotAllLinesFilledOut();
        }
        try {
            artistToLibrary();
        } catch (DuplicateException e) {
            JOptionPane.showMessageDialog(frame,
                    "This Artist is already in your library");
        }
        addJTable();
    }

    // MODIFIES:
    // EFFECTS: Helper function to submitAlbum for getting
    // the users input songs
    private void getSongs(String text) {
        String[] str = text.split("\n");
        songList = Arrays.asList(str);
    }

    // MODIFIES: this
    // EFFECTS: sends the library information to the method saveLibrary
    // in the MusicApp to save to JSON
    private void saveEntries() {
        library.saveLibrary(maLibrary);
    }


    // MODIFIES: this
    // EFFECTS: Creates a new panel displaying a JTextFields for users
    // to input album information and add it to the library
    public void albumEntries() {
        albumPanel = new JPanel();
        setUpAP(albumPanel);
        JButton submit = albumPanelSetUp();

        submit.setActionCommand("submit album");
        submit.addActionListener(this::actionPerformed);
        frame.add(albumPanel);
        frame.repaint();
        frame.revalidate();
    }

    // MODIFIES: albumPanel
    // EFFECTS: Helper function that adds fields to AlbumPanel
    private JButton albumPanelSetUp() {
        JLabel title = new JLabel("Album Information");
        JLabel albumName = new JLabel("Album Name");
        name = new JTextField();
        JLabel albumArtist = new JLabel("Album Artist");
        artist = new JTextField();
        JLabel albumGenre = new JLabel("Album Genre");
        genre = new JComboBox<>(genreOptions);
        JLabel albumSongs = new JLabel("Album Songs");
        songs = new JTextArea("(Note): after each song, go to\n new line delete this and begin");
        songs.setBorder(BorderFactory.createEtchedBorder());
        JButton submit = new JButton("submit");

        albumPanel.add(title);
        albumPanel.add(albumName);
        albumPanel.add(name);
        albumPanel.add(albumArtist);
        albumPanel.add(artist);
        albumPanel.add(albumGenre);
        albumPanel.add(genre);
        albumPanel.add(albumSongs);
        albumPanel.add(songs);
        albumPanel.add(submit);
        return submit;
    }


    // MODIFIES: this
    // EFFECTS: Creates a new panel displaying a JTextFields for users
    // to input song information and add it to the library
    public void songEntries() {
        songPanel = new JPanel();
        setUpAP(songPanel);
        JLabel title = new JLabel("Song Information");
        JLabel songName = new JLabel("Song Name");
        name = new JTextField();
        JLabel songArtist = new JLabel("Song Artist");
        artist = new JTextField();
        JLabel songGenre = new JLabel("Song Genre");
        genre = new JComboBox<>(genreOptions);
        JButton submit = new JButton("submit");

        songPanel.add(title);
        songPanel.add(songName);
        songPanel.add(name);
        songPanel.add(songArtist);
        songPanel.add(artist);
        songPanel.add(songGenre);
        songPanel.add(genre);
        songPanel.add(submit);

        submit.setActionCommand("submit song");
        submit.addActionListener(this::actionPerformed);

        frame.add(songPanel);
        frame.repaint();
        frame.revalidate();
    }


    // MODIFIES: this
    // EFFECTS: Creates a new panel displaying a JTextFields for users
    // to input artist information and add it to the library
    public void artistEntries() {
        artistPanel = new JPanel();
        setUpAP(artistPanel);
        JLabel title = new JLabel("Artist Information");
        JLabel artistName = new JLabel("Artist Name");
        name = new JTextField();
        JLabel artistGenre = new JLabel("Artist Genre");
        genre = new JComboBox<>(genreOptions);
        JButton submit = new JButton("submit");

        artistPanel.add(title);
        artistPanel.add(artistName);
        artistPanel.add(name);
        artistPanel.add(artistGenre);
        artistPanel.add(genre);
        artistPanel.add(submit);


        submit.setActionCommand("submit artist");
        submit.addActionListener(this::actionPerformed);

        frame.add(artistPanel);
        frame.repaint();
        frame.revalidate();
    }

    //EFFECTS: tallies the genre that is chosen
    public void tallyGenre(String genre) {
        if (!maLibrary.getGenreMap().containsKey(genre)) {
            maLibrary.addGenreToLibrary(genre, 1);
        } else {
            int i = maLibrary.getGenreMap().get(genre);
            i++;
            maLibrary.addGenreToLibrary(genre, i);
        }
    }

    // MODIFIES: this
    // EFFECTS: Helper function to set the entry functions
    // set up their panels
    private void setUpAP(JPanel albumPanel) {
        albumPanel.setBorder(BorderFactory.createEtchedBorder());
        albumPanel.setLayout(new BoxLayout(albumPanel, BoxLayout.PAGE_AXIS));
    }

    // MODIFIES: this
    // EFFECTS: Creates a new artist and sets the user
    // artist information to it, and sends it to library
    public void artistToLibrary() throws DuplicateException {
        if (!artistInLibrary(name)) {
            Artist artist = new Artist();
            tallyGenre((String) genre.getSelectedItem());
            artist.setArtistInformation(name.getText(), (String) genre.getSelectedItem());
            maLibrary.addArtistToLibrary(artist);
            frame.remove(artistPanel);
            frame.repaint();
            frame.revalidate();
        } else {
            throw new DuplicateException();
        }
    }

    // MODIFIES:
    // EFFECTS: Helper function to see if there already
    // exists an artist
    public Boolean artistInLibrary(JTextField textField) {
        List<String> artistNames = new ArrayList<>();
        for (Artist artistName : maLibrary.getArtistLibrary()) {
            artistNames.add(artistName.getArtistName());
        }

        for (String songName : artistNames) {
            if (songName.equals(textField.getText())) {
                return true;
            }
        }

        return false;
    }

    // MODIFIES: this
    // EFFECTS: Creates a new song and sets the user
    // song information to it, and sends it to library
    public void songToLibrary() {
        Song song = new Song();
        tallyGenre((String) genre.getSelectedItem());
        song.setSongName(name.getText());
        song.setSongArtist((String) genre.getSelectedItem(), artist.getText());
        maLibrary.addSongToLibrary(song);
        if (!artistInLibrary(artist)) {
            maLibrary.addArtistToLibrary(song.getArtistObject());
        }
        frame.remove(songPanel);
        frame.repaint();
        frame.revalidate();
    }

    // MODIFIES: this
    // EFFECTS: Creates a new album and sets the user
    // album information to it, and sends it to library
    public void albumToLibrary() {
        Album album = new Album();
        tallyGenre((String) genre.getSelectedItem());
        Artist albumArtist = new Artist();
        albumArtist.setArtistInformation(artist.getText(), (String) genre.getSelectedItem());
        album.setAlbumName(name.getText());
        album.setAlbumArtist(albumArtist);
        album.setAlbumSongs(songList);
        maLibrary.addAlbumToLibrary(album);
        if (!artistInLibrary(artist)) {
            maLibrary.addArtistToLibrary(albumArtist);
        }
        for (Song s : album.albumSongs) {
            maLibrary.addSongToLibrary(s);
        }
        frame.remove(albumPanel);
        frame.repaint();
        frame.revalidate();
    }

    // MODIFIES: this
    // EFFECTS: runs MusicManager Constructor
    public static void main(String[] args) throws IOException {
        new MusicManagementProgram();
    }

}