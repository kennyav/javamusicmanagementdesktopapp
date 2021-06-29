package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Library implements Writable {
    List<Album> albumLibrary;
    List<Song> songLibrary;
    List<Artist> artistLibrary;
    Map<String, Integer> genreMap;

    public Library() {
        albumLibrary = new ArrayList<>();
        songLibrary = new ArrayList<>();
        artistLibrary = new ArrayList<>();
        genreMap = new HashMap<>();
    }

    //getters
    public List<Album> getAlbumLibrary() {
        return albumLibrary;
    }

    public List<Song> getSongLibrary() {
        return songLibrary;
    }

    public List<Artist> getArtistLibrary() {
        return artistLibrary;
    }

    public Map<String, Integer> getGenreMap() {
        return genreMap;
    }

    public void addGenreToLibrary(String g, int i) {
        genreMap.put(g, i);
    }

    public void addAlbumToLibrary(Album a) {
        albumLibrary.add(a);
    }

    public void addSongToLibrary(Song s) {
        songLibrary.add(s);
    }

    public void addArtistToLibrary(Artist a) {
        artistLibrary.add(a);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("albumLibrary", saveAlbum());
        json.put("artistLibrary", saveArtist());
        json.put("songLibrary", saveSongs());
        json.put("genreLibrary", genreMap);
        return json;
    }

    private JSONArray saveAlbum() {
        JSONArray jsonArray = new JSONArray();

        for (Album a: albumLibrary) {
            JSONObject json = new JSONObject();
            json.put("albumName", a.getAlbumName());
            json.put("albumArtist", a.getAlbumArtist());
            json.put("albumGenre", a.getAlbumGenre());
            json.put("albumSongs", albumSongsToJson(a));
            jsonArray.put(json);
        }
        return jsonArray;
    }

    // EFFECTS: returns things in this album as a JSON array
    private JSONArray albumSongsToJson(Album a) {
        JSONArray jsonArray = new JSONArray();
        List<String> songs = a.getListOfSongNames();

        for (String s : songs) {
            jsonArray.put(s);
        }

        return jsonArray;
    }


    private JSONArray saveArtist() {
        JSONArray jsonArray = new JSONArray();

        for (Artist a: artistLibrary) {
            JSONObject json = new JSONObject();
            json.put("artistName", a.getArtistName());
            json.put("artistGenre", a.getArtistGenre());
            jsonArray.put(json);
        }
        return jsonArray;
    }

    private JSONArray saveSongs() {
        JSONArray jsonArray = new JSONArray();

        for (Song s : songLibrary) {
            JSONObject json = new JSONObject();
            json.put("songName", s.getSongName());
            json.put("songArtist", s.getArtistName());
            json.put("songGenre", s.getSongGenre());
            jsonArray.put(json);
        }
        return jsonArray;
    }

}
