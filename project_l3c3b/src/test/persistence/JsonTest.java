package persistence;

import model.Song;
import model.Artist;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkLibraryComponents(List<String> librarySection, List<String> compareTo) {
        assertEquals(librarySection, compareTo);
    }
}
