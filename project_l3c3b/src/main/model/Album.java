package model;

import java.util.ArrayList;
import java.util.List;


public class Album {
    private String albumName;
    private Artist albumArtist;
    public List<Song> albumSongs;

    //creates and empty album
    public Album() {
        albumName = "";
        albumArtist = new Artist();
        albumSongs = new ArrayList<>();
    }

    // getters
    public String getAlbumName() {
        return albumName;
    }

    public String getAlbumArtist() {
        return albumArtist.getArtistName();
    }

    public Artist getAlbumArtistObject() {
        return albumArtist;
    }

    public String getAlbumGenre() {
        return albumArtist.getArtistGenre();
    }

    //Gets list of song titles
    public List<String> getListOfSongNames() {
        List<String> songs = new ArrayList<>();
        for (Song next : albumSongs) {
            songs.add(next.getSongName());
        }
        return songs;
    }

    //EFFECTS: this
    //MODIFIES: returns selected song
    public Song getSong(int i) {
        return albumSongs.get(i);
    }

    // EFFECTS: this
    // MODIFIES: adds Album name to Album
    public void setAlbumName(String name) {
        albumName = name;
    }

    // EFFECTS: this
    // MODIFIES: adds artist to Album
    public void setAlbumArtist(Artist artist) {
        albumArtist = artist;
    }

    //EFFECTS: this
    //MODIFIES: adds songs in a list of strings, an artist object, and an album name
    public void setAlbumSongs(List<String> songs) {
        for (String next : songs) {
            Song song = new Song();
            song.setSongName(next);
            song.setSongArtist(getAlbumGenre(), getAlbumArtist());
            albumSongs.add(song);
        }
    }

}
