package model;

public class Song {
    protected String songName;
    protected Artist songArtistName;

    //Constructs an empty song
    public Song() {
        songArtistName = new Artist();
        songName = "";
    }

    // getters
    public String getSongGenre() {
        return songArtistName.getArtistGenre();
    }

    public String getSongName() {
        return songName;
    }

    public String getArtistName() {
        return songArtistName.getArtistName();
    }

    public Artist getArtistObject() {
        return songArtistName;
    }

    // EFFECTS: this
    // MODIFIES: sets the Artist Name and the Song Name
    public void setSongName(String name) {
        this.songName = name;
    }

    // EFFECTS: this
    // MODIFIES: sets the Artist Name and Genre
    public void setSongArtist(String genre, String artist) {
        this.songArtistName.setArtistInformation(artist, genre);
    }

    //REQUIRES: Non empty song
    //EFFECTS: this
    //MODIFIES: edits the most recently input song
    public void editSong(String cat, String edit) throws EmptyObjectException, IncorrectInputException {
        if (songArtistName.artistName.equals("") || songName.equals("")) {
            throw new EmptyObjectException();
        }
        if (cat.equals("genre")) {
            songArtistName.artistGenreName = edit;
        } else if (cat.equals("title")) {
            songName = edit;
        } else if (cat.equals("artist")) {
            songArtistName.artistName = edit;
        } else {
            throw new IncorrectInputException();
        }
    }
}
