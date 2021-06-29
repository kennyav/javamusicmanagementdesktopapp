package persistence;

import model.Album;
import model.Artist;
import model.Library;
import model.Song;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends persistence.JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            Album a = new Album();
            persistence.JsonWriter writer = new persistence.JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyAlbum() {
        try {
            Library l = new Library();
            persistence.JsonWriter writer = new persistence.JsonWriter("./data/testWriterEmptyLibrary.json");
            writer.open();
            writer.writeLibrary(l);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyLibrary.json");
            l = reader.readLibrary();
            assertEquals(0, l.getAlbumLibrary().size());
            assertEquals(0, l.getSongLibrary().size());
            assertEquals(0, l.getArtistLibrary().size());
        } catch (IOException e) {
            fail("IOException caught, should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralLibraryAlbum() {
        try {
            Library l = new Library();
            addSongsToGeneralLibrary(l);
            Artist a = addArtistToGeneralLibrary(l);
            addAlbumToGeneralLibrary(l, a);

            persistence.JsonWriter writer = new persistence.JsonWriter("./data/testWriterNormalLibrary.json");
            writer.open();
            writer.writeLibrary(l);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterNormalLibrary.json");
            l = reader.readLibrary();
            assertEquals(1, l.getAlbumLibrary().size());
            Album al = l.getAlbumLibrary().get(0);
            assertEquals("Name", al.getAlbumName());
            List<String> songs = al.getListOfSongNames();
            assertEquals(3, songs.size());
            assertEquals("Artist", al.getAlbumArtist());
            assertEquals("Genre", al.getAlbumGenre());
            assertEquals("Song1", songs.get(0));
            assertEquals("Song2", songs.get(1));
            assertEquals("Song3", songs.get(2));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralLibrarySongs() {
        try {
            Library l = new Library();
            addSongsToGeneralLibrary(l);
            Artist a = addArtistToGeneralLibrary(l);
            addAlbumToGeneralLibrary(l, a);

            persistence.JsonWriter writer = new persistence.JsonWriter("./data/testWriterNormalLibrary.json");
            writer.open();
            writer.writeLibrary(l);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterNormalLibrary.json");
            l = reader.readLibrary();
            assertEquals(3, l.getSongLibrary().size());
            Song s = l.getSongLibrary().get(0);
            assertEquals("Song1", s.getSongName());
            assertEquals("Artist", s.getArtistName());
            assertEquals("Genre", s.getSongGenre());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralLibraryArtist() {
        try {
            Library l = new Library();
            addSongsToGeneralLibrary(l);
            Artist a = addArtistToGeneralLibrary(l);
            addAlbumToGeneralLibrary(l, a);

            persistence.JsonWriter writer = new persistence.JsonWriter("./data/testWriterNormalLibrary.json");
            writer.open();
            writer.writeLibrary(l);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterNormalLibrary.json");
            l = reader.readLibrary();
            assertEquals(1, l.getArtistLibrary().size());
            assertEquals("Artist", a.getArtistName());
            assertEquals("Genre", a.getArtistGenre());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    private void addAlbumToGeneralLibrary(Library l, Artist a) {
        List<String> songList = new ArrayList<>();
        songList.add("Song1");
        songList.add("Song2");
        songList.add("Song3");

        Album album = new Album();
        album.setAlbumName("Name");
        album.setAlbumArtist(a);
        album.setAlbumSongs(songList);

        l.addAlbumToLibrary(album);
    }

    private Artist addArtistToGeneralLibrary(Library l) {
        Artist a = new Artist();
        a.setArtistInformation("Artist", "Genre");
        l.addArtistToLibrary(a);
        return a;
    }

    private void addSongsToGeneralLibrary(Library l) {
        for (int i = 1; i < 4; i++) {
            Song song = new Song();
            song.setSongArtist("Genre", "Artist");
            song.setSongName("Song" + i);
            l.addSongToLibrary(song);
        }
    }

}