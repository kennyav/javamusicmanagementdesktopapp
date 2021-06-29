package persistence;

import model.Album;
import model.Artist;
import model.Library;
import model.Song;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Library l = reader.readLibrary();
            fail("IOException expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testReaderEmptyLibrary() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyLibrary.json");
        try {
            Library l = reader.readLibrary();
            assertEquals(0, l.getAlbumLibrary().size());
            assertEquals(0, l.getSongLibrary().size());
            assertEquals(0, l.getArtistLibrary().size());
        } catch (IOException e) {
            fail("File could not be read");
        }
    }

    @Test
    void testReaderGeneralLibraryAlbum() {
        JsonReader reader = new JsonReader("./data/testReaderNormalLibrary.json");
        try {
            Library l = reader.readLibrary();
            Album a = l.getAlbumLibrary().get(0);
            assertEquals("Name", a.getAlbumName());
            List<String> songs = a.getListOfSongNames();
            assertEquals(3, songs.size());
            assertEquals("Artist", a.getAlbumArtist());
            assertEquals("Genre", a.getAlbumGenre());
            assertEquals("Song1", songs.get(0));
            assertEquals("Song2", songs.get(1));
            assertEquals("Song3", songs.get(2));
            //checkLibraryComponents(l.getAlbumLibrary(), );

        } catch (IOException e) {
            fail("File could not be read");
        }
    }

    @Test
    void testReaderGeneralLibrarySong() {
        JsonReader reader = new JsonReader("./data/testReaderNormalLibrary.json");
        try {
            Library l = reader.readLibrary();
            Song s = l.getSongLibrary().get(0);
            assertEquals("Song1", s.getSongName());
            assertEquals("Artist", s.getArtistName());
            assertEquals("Genre", s.getSongGenre());
        } catch (IOException e) {
            fail("File could not be read");
        }
    }

    @Test
    void testReaderGeneralLibraryArtist() {
        JsonReader reader = new JsonReader("./data/testReaderNormalLibrary.json");
        try {
            Library l = reader.readLibrary();
            Artist a = l.getArtistLibrary().get(0);
            assertEquals("Artist", a.getArtistName());
            assertEquals("Genre", a.getArtistGenre());
        } catch (IOException e) {
            fail("File could not be read");
        }
    }

    @Test
    void testReaderGeneralLibraryGenre() {
        JsonReader reader = new JsonReader("./data/testReaderNormalLibrary.json");
        try {
            Library l = reader.readLibrary();
            Map<String, Integer> genreMap = l.getGenreMap();
            assertTrue(genreMap.containsKey("Rap"));
            assertTrue(genreMap.containsValue(1));
        } catch (IOException e) {
            fail("File could not be read");
        }
    }
}