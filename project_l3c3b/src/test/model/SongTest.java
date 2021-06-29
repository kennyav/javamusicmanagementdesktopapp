package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//test class for song
public class SongTest {
    Song testSong;

    @BeforeEach
    public void setup() {
        testSong = new Song();
    }

    @Test
    public void testAddSong() {
        String song = "Expensive";
        String artist = "Danny Orange";
        String genre = "Alternative Pop";

        testSong.setSongName(song);
        testSong.setSongArtist(genre, artist);
        assertEquals(genre, testSong.getSongGenre());
        assertEquals(artist, testSong.getArtistName());
        assertEquals(song, testSong.getSongName());
    }

    @Test
    public void testEditSongArtist() {
        String song = "Not Fair";
        String artist = "Post Malone";
        String genre = "Hip-Hop";
        testSong.setSongName(song);
        testSong.setSongArtist(genre, artist);
        try {
            testSong.editSong("artist", "The Kid Laroi ft. Corban");
        } catch (EmptyObjectException e) {
            fail("Caught EmptyObjectException");
        } catch (IncorrectInputException e) {
            fail("Caught IncorrectInputException");
        }
        try {
            testSong.editSong("artt", "The Kid Laroi ft. Corban");
            fail("failed to catch Exception");
        } catch (EmptyObjectException e) {
            fail("Caught EmptyObjectException, not expected");
        } catch (IncorrectInputException e) {
            // expected
        }

        assertEquals("The Kid Laroi ft. Corban", testSong.getArtistName());

    }

    @Test
    public void testEditSongTitle() {
        String song = "Not Fair";
        String artist = "Post Malone";
        String genre = "Hip-Hop";
        testSong.setSongName(song);
        testSong.setSongArtist(genre, artist);
        try {
            testSong.editSong("title", "Myself");
        } catch (EmptyObjectException e) {
            fail("Caught EmptyObjectException");
        } catch (IncorrectInputException e) {
            fail("Caught IncorrectInputException");
        }

        assertEquals("Myself", testSong.getSongName());
        assertEquals(testSong.songArtistName, testSong.getArtistObject());


    }

    @Test
    public void testEditSongGenre() {
        String song = "Not Fair";
        String artist = "Post Malone";
        String genre = "Hip-Hop";
        testSong.setSongName(song);
        testSong.setSongArtist(genre, artist);
        try {
            testSong.editSong("genre", "Pop");
        } catch (EmptyObjectException e) {
            fail("Caught EmptyObjectException");
        } catch (IncorrectInputException e) {
            fail("Caught IncorrectInputException");
        }

        assertEquals("Pop", testSong.getSongGenre());
    }

    @Test
    public void testEmptySong() {
        testSong.setSongArtist("genre", "artist");
        try {
            testSong.editSong("genre", "Pop");
            fail("failed to catch Exception");
        } catch (EmptyObjectException e) {
            // expected
        } catch (IncorrectInputException e) {
            fail("Caught IncorrectInputException");
        }
    }

    @Test
    public void testEmptySongArtist() {
        testSong.setSongName("name");
        try {
            testSong.editSong("genre", "Pop");
            fail("failed to catch Exception");
        } catch (EmptyObjectException e) {
            // expected
        } catch (IncorrectInputException e) {
            fail("Caught IncorrectInputException");
        }
    }

}
