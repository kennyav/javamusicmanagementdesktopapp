package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ArtistTest {
    Artist testArtist;

    @BeforeEach
    public void setup() {
        testArtist = new Artist();
    }

    @Test
    public void testAddArtist() {
        String artist = "Danny Orange";
        String genre = "Alternative Pop";

        testArtist.setArtistInformation(artist, genre);
        assertEquals(genre, testArtist.getArtistGenre());
        assertEquals(artist, testArtist.getArtistName());
    }

    @Test
    public void testEditArtistArtist() {
        String artist = "Danny Orange";
        String genre = "Alternative Pop";
        testArtist.setArtistInformation(artist, genre);
        try {
            testArtist.editArtist("artist","The Backseat Lovers");
        } catch (EmptyObjectException e) {
            fail("Caught EmptyArtistException");
        }

        assertEquals("The Backseat Lovers", testArtist.getArtistName());
    }

    @Test
    public void testEditArtistGenre() {
        String artist = "Danny Orange";
        String genre = "Alternative Pop";
        testArtist.setArtistInformation(artist, genre);
        try {
            testArtist.editArtist("genre", "Rock");
        } catch (EmptyObjectException e) {
            fail("Caught EmptyArtistException");
        }
        try {
            testArtist.editArtist("gen", "Rock");
        } catch (EmptyObjectException e) {
            fail("Caught EmptyArtistException");
        }

        assertEquals("Rock", testArtist.getArtistGenre());
    }

    @Test
    public void testCatchExceptionGenre() {
        testArtist.setArtistInformation("artist", "");
        try {
            testArtist.editArtist("genre", "Rock");
            fail("failed to catch Exception");
        } catch (EmptyObjectException e) {
           // expected
        }
    }

    @Test
    public void testCatchExceptionArtist() {
        testArtist.setArtistInformation("", "genre");
        try {
            testArtist.editArtist("artist", "Doesn't Matter");
            fail("failed to catch Exception");
        } catch (EmptyObjectException e) {
            // expected
        }
    }


}
