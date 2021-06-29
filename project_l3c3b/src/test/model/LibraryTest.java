package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class LibraryTest {
    private Library l;
    private Album testAlbum;
    private Song testSong;

    @BeforeEach
    void setUp() {
        testAlbum = new Album();
        testSong = new Song();
        l = new Library();
        Artist artist = new Artist();
        List<String> songNames = new ArrayList<>();

        artist.setArtistInformation("The Backseat Lovers", "Rock");
        songNames.add("Watch Your Mouth");
        testAlbum.setAlbumSongs(songNames);
        testAlbum.setAlbumArtist(artist);
        testAlbum.setAlbumName("When We Were Friends");
        testSong.setSongName("Song1");
        testSong.setSongArtist("Genre", "Artist");


        l.addAlbumToLibrary(testAlbum);
        l.addArtistToLibrary(artist);
        l.addSongToLibrary(testSong);
    }

    @Test
    public void testLibraryAlbum() {
        Album a = l.getAlbumLibrary().get(0);
        assertEquals("The Backseat Lovers", a.getAlbumArtist());
        assertEquals("Rock", a.getAlbumGenre());
        assertEquals("When We Were Friends", a.getAlbumName());
        assertEquals("Watch Your Mouth", a.getListOfSongNames().get(0));

    }

    @Test
    public void testLibrarySong() {
        Song s = l.getSongLibrary().get(0);
        assertEquals("Song1", s.getSongName());
        assertEquals("Artist", s.getArtistName());
        assertEquals("Genre", s.getSongGenre());
    }

    @Test
    public void testLibraryArtist() {
        Artist a = l.getArtistLibrary().get(0);
        assertEquals("The Backseat Lovers", a.getArtistName());
        assertEquals("Rock", a.getArtistGenre());
    }

    @Test
    public void testLibraryGenre() {
        l.addGenreToLibrary("Key", 2);
        assertTrue(l.getGenreMap().containsKey("Key"));
        assertFalse(l.getGenreMap().containsKey("Ked"));
        assertTrue(l.getGenreMap().containsValue(2));
        assertFalse(l.getGenreMap().containsValue(3));
    }


}
