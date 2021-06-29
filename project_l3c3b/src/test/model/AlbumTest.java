package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AlbumTest {
    Album testAlbum;

    @BeforeEach
    public void setup() {
        testAlbum = new Album();
    }

    @Test
    public void testFillAlbum() {
        List<String> songNames = new ArrayList<>();
        Artist artist = new Artist();
        artist.setArtistInformation("The Backseat Lovers", "Rock");
        songNames.add("Watch Your Mouth");
        songNames.add("Pool House");
        songNames.add("Intuition");
        songNames.add("Kilby Girl");
        songNames.add("Dugout");
        songNames.add("Davy Crochet");
        songNames.add("Maple Syrup");
        songNames.add("Olivia");
        songNames.add("Sinking Ship");
        testAlbum.setAlbumSongs(songNames);
        testAlbum.setAlbumArtist(artist);
        testAlbum.setAlbumName("When We Were Friends");

        assertEquals("Rock", testAlbum.getAlbumGenre());
        assertEquals("The Backseat Lovers", testAlbum.getAlbumArtist());
        assertEquals("When We Were Friends", testAlbum.getAlbumName());
        assertEquals(artist, testAlbum.getAlbumArtistObject());

    }

    @Test
    public void testSearchAlbum() {
        List<String> songNames = new ArrayList<>();
        Artist artist = new Artist();
        artist.setArtistInformation("The Backseat lovers", "Rock");
        songNames.add("Watch Your Mouth");
        songNames.add("Pool House");
        songNames.add("Intuition");
        songNames.add("Kilby Girl");
        songNames.add("Dugout");
        songNames.add("Davy Crochet");
        songNames.add("Maple Syrup");
        songNames.add("Olivia");
        songNames.add("Sinking Ship");
        testAlbum.setAlbumName("When We Were Friends");
        testAlbum.setAlbumArtist(artist);
        testAlbum.setAlbumSongs(songNames);

        assertEquals(testAlbum.albumSongs.get(1), testAlbum.getSong(1));
        assertEquals("When We Were Friends", testAlbum.getAlbumName());
        assertEquals(songNames, testAlbum.getListOfSongNames());

    }

}
