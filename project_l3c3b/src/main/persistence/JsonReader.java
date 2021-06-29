package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import model.Album;
import model.Artist;
import model.Library;
import model.Song;
import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads library from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Library readLibrary() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseLibrary(jsonObject);
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private Library parseLibrary(JSONObject jsonObject) {
        Library l = new Library();
        addAlbum(l, jsonObject);
        addSongs(l, jsonObject);
        addArtist(l, jsonObject);
        addGenreL(l, jsonObject);
        return l;
    }

    private void addGenreL(Library l, JSONObject json) {
        JSONObject genre = json.getJSONObject("genreLibrary");
        for (Map.Entry<String, Object> entry : genre.toMap().entrySet()) {
            l.addGenreToLibrary(entry.getKey(), (Integer) entry.getValue());
        }
    }

    private void addAlbum(Library l, JSONObject json) {
        JSONArray album = json.getJSONArray("albumLibrary");
        for (Object a: album) {
            Album albumToLib = parseAlbum((JSONObject) a);
            l.addAlbumToLibrary(albumToLib);
        }
    }

    private void addSongs(Library l, JSONObject json) {
        JSONArray songs = json.getJSONArray("songLibrary");
        for (Object s: songs) {
            Song songToLib = parseSong((JSONObject) s);
            l.addSongToLibrary(songToLib);
        }
    }

    private void addArtist(Library l, JSONObject json) {
        JSONArray artists = json.getJSONArray("artistLibrary");
        for (Object a: artists) {
            Artist artistToLib = parseArtist((JSONObject) a);
            l.addArtistToLibrary(artistToLib);
        }

    }

    // EFFECTS: parses workroom from JSON object and returns it
    private Album parseAlbum(JSONObject jsonObject) {
        Album a = new Album();
        addAlbumName(a, jsonObject);
        addAlbumArtist(a, jsonObject);
        addAlbumSongs(a, jsonObject);

        return a;
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private Song parseSong(JSONObject jsonObject) {
        Song s = new Song();
        addSongName(s, jsonObject);
        addSongArtist(s, jsonObject);

        return s;
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private Artist parseArtist(JSONObject jsonObject) {
        Artist artist = new Artist();
        addArtistNameAndGenre(artist, jsonObject);
        return artist;
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }


    // MODIFIES: a
    // EFFECTS: parses album name from JSON object and adds them to a
    private void addAlbumName(Album a, JSONObject jsonObject) {
        String albumName = jsonObject.getString("albumName");
        a.setAlbumName(albumName);
    }

    // MODIFIES: a
    // EFFECTS: parses album artist from JSON object and adds them to a
    private void addAlbumArtist(Album a, JSONObject jsonObject) {
        String albumArtistName = jsonObject.getString("albumArtist");
        String albumArtistGenre = jsonObject.getString("albumGenre");
        Artist artist = new Artist();
        artist.setArtistInformation(albumArtistName, albumArtistGenre);

        a.setAlbumArtist(artist);
    }

    // MODIFIES: wr
    // EFFECTS: parses list of song names from JSON object and adds them to album
    private void addAlbumSongs(Album a, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("albumSongs");
        List<String> songList = new ArrayList<>();
        for (Object json : jsonArray) {
            songList.add(json.toString());
        }
        a.setAlbumSongs(songList);
    }


    // MODIFIES: a
    // EFFECTS: parses album name from JSON object and adds them to a
    private void addSongName(Song s, JSONObject jsonObject) {
        String songName = jsonObject.getString("songName");
        s.setSongName(songName);
    }

    // MODIFIES: a
    // EFFECTS: parses album artist from JSON object and adds them to a
    private void addSongArtist(Song s, JSONObject jsonObject) {
        String artistName = jsonObject.getString("songArtist");
        String artistGenre = jsonObject.getString("songGenre");
        s.setSongArtist(artistGenre, artistName);
    }


    // MODIFIES: a
    // EFFECTS: parses album name from JSON object and adds them to a
    private void addArtistNameAndGenre(Artist a, JSONObject jsonObject) {
        String artistName = jsonObject.getString("artistName");
        String artistGenre = jsonObject.getString("artistGenre");
        a.setArtistInformation(artistName, artistGenre);
    }

}
