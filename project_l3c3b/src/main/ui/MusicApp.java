package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.fail;
//credit to JSONSerializationDemo

public class MusicApp {

    private static final String JSON_STORE = "./data/library.json";
    public Song song;
    public Artist artist;
    public Album album;
    public Library library;
    private final JsonWriter jsonWriterLibrary;
    private final JsonReader jsonReaderLibrary;

    static String operation = "";
    static Scanner scanner;
    private static String input;


   // throws FileNotFoundException

    public MusicApp() {
        this.song = new Song();
        this.artist = new Artist();
        this.album = new Album();
        this.library = new Library();
        jsonWriterLibrary = new JsonWriter(JSON_STORE);
        jsonReaderLibrary = new JsonReader(JSON_STORE);
        //userInterface();
    }


    //main ui for user
    public void userInterface() {
        scanner = new Scanner(System.in);
        do {
            System.out.println("\nWelcome to your Music Manager");
            displayMenu();
            operation = scanner.nextLine();
            System.out.println("You selected: " + operation);
            processOperation(operation);
        } while (!operation.equals("q") && !operation.equals("Q"));
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("Select from:");
        System.out.println("\t1 -> add Song");
        System.out.println("\t2 -> add Artist");
        System.out.println("\t3 -> add Album");
        System.out.println("\t4 -> print Library");
        System.out.println("\t5 -> save Library");
        System.out.println("\t6 -> load Library");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processOperation(String operation) {
        if (operation.equals("1")) {
            song = songPath();
            editSong(song);
        } else if (operation.equals("2")) {
            artist = artistPath();
            editArtist(artist);
        } else if (operation.equals("3")) {
            album = albumPath();
            editAlbum(album);
        } else if (operation.equals("4")) {
            displayLibrary();
        } else if (operation.equals("5")) {
            saveLibrary(library);
        } else if (operation.equals("6")) {
            loadLibrary();
        } else {
            System.out.println("Invalid Selection");
        }
    }

    //steps the user takes to input a song
    public Song songPath() {
        Song song = new Song();
        String songName;
        String songArtist;
        String songGenre;
        System.out.println("Input the name of your Song:");
        songName = scanner.nextLine();
        System.out.println("Input song's Artist:");
        songArtist = scanner.nextLine();
        System.out.println("Input song's Genre:");
        songGenre = scanner.nextLine();
        song.setSongName(songName);
        song.setSongArtist(songGenre, songArtist);
        System.out.println("Song " + song.getSongName() + " by " + song.getArtistName() + " " + "has been added");
        artistFromSongAndAlbumToLibrary(song.getArtistObject());
        library.addSongToLibrary(song);
        return song;
    }

    //steps the user takes to input an artist
    public Artist artistPath() {
        Artist artist = new Artist();
        String artistName;
        String artistGenre;
        System.out.println("Input the name of Artist:");
        artistName = scanner.nextLine();
        System.out.println("What is the Artist's genre:");
        artistGenre = scanner.nextLine();
        artist.setArtistInformation(artistName, artistGenre);
        System.out.println("Artist: " + artist.getArtistName() + " " + "has been added");
        library.addArtistToLibrary(artist);
        return artist;
    }

    //steps the user takes to input an album
    public Album albumPath() {
        Album album = new Album();
        Artist artist = new Artist();
        String albumName;
        String albumGenre;
        String albumArtist;
        List<String> albumSongs;
        System.out.println("Input the name of Album:");
        albumName = scanner.nextLine();
        System.out.println("What is the genre of the Album:");
        albumGenre = scanner.nextLine();
        System.out.println("What is the Artist's name of Album:");
        albumArtist = scanner.nextLine();
        artist.setArtistInformation(albumArtist, albumGenre);
        albumSongs = setSongListInAlbum();
        album.setAlbumSongs(albumSongs);
        album.setAlbumArtist(artist);
        album.setAlbumName(albumName);
        System.out.println(albumName + ": " + albumSongs + " by " + album.getAlbumArtist() + " has been added");
        artistFromSongAndAlbumToLibrary(artist);
        albumSongToLibrary(album.albumSongs, artist);
        library.addAlbumToLibrary(album);
        return album;
    }

    public void artistFromSongAndAlbumToLibrary(Artist a) {
        library.addArtistToLibrary(a);
    }

    public void albumSongToLibrary(List<Song> songs, Artist a) {
        for (Song x: songs) {
            x.setSongArtist(a.getArtistGenre(), a.getArtistName());
            library.addSongToLibrary(x);
        }
    }

    // displays all the songs, artists, and albums that the user has input
    public void displayLibrary() {
        System.out.println("Artists: ");
        for (Artist next: library.getArtistLibrary()) {
            System.out.println("\t" + next.getArtistName());
        }
        System.out.println("Songs: ");
        for (Song next: library.getSongLibrary()) {
            System.out.println("\t" + next.getSongName() + " by " + next.getArtistName());
        }
        System.out.println("Albums: ");
        for (Album next: library.getAlbumLibrary()) {
            System.out.println("\t" + next.getAlbumName()  + " by " + next.getAlbumArtist());
            System.out.println("\t" + next.getListOfSongNames() + "\n");
        }

    }

    public void saveLibrary(Library l) {
        try {
            jsonWriterLibrary.open();
            jsonWriterLibrary.writeLibrary(l);
            jsonWriterLibrary.close();
            System.out.println("Saved Library to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    public void loadLibrary() {
        try {
            library = jsonReaderLibrary.readLibrary();
            System.out.println("Loaded Library");
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // edits the song a user is working on
    public static void editSong(Song song) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Would you like to edit your song input? (y/n)");
            if (scanner.nextLine().equals("y")) {
                System.out.println("Choose which category you would like to edit:");
                System.out.println("\t1 -> Song Name");
                System.out.println("\t2 -> Song Artist");
                System.out.println("\t3 -> Song Genre");
                String s = scanner.nextLine();
                if ("1".equals(s)) {
                    songNameChange(song);
                } else if ("2".equals(s)) {
                    artistNameChange(song.getArtistObject());
                } else if ("3".equals(s)) {
                    genreChange(song.getArtistObject());
                }
            } else {
                break;
            }
        }
    }

    // edits the artist a user is working on
    public static void editArtist(Artist artist) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Would you like to edit your artist input? (y/n)");
            if (scanner.nextLine().equals("y")) {
                System.out.println("Choose which category you would like to edit:");
                System.out.println("\t1 -> Artist Name");
                System.out.println("\t2 -> Artist Genre");
                String s = scanner.nextLine();
                if ("1".equals(s)) {
                    artistNameChange(artist);
                } else if ("2".equals(s)) {
                    genreChange(artist);
                }
            } else {
                break;
            }
        }
    }

    // edits an album a user is working on
    public static void editAlbum(Album album) {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Would you like to edit your album input? (y/n)");
            if (scanner.nextLine().equals("y")) {
                System.out.println("Choose which category you would like to edit:");
                System.out.println("\t1 -> Song in Album");
                System.out.println("\t2 -> Album Artist");
                System.out.println("\t3 -> Album Genre");
                int s = scanner.nextInt();
                if (s == 1) {
                    editSongInAlbum(album);
                } else if (s == 2) {
                    artistNameChange(album.getAlbumArtistObject());
                } else if (s == 3) {
                    genreChange(album.getAlbumArtistObject());
                }
            } else {
                break;
            }
        }
    }

    // searches through the album songs, if it is there
    // then the program calls edit song on the input song
    public static void editSongInAlbum(Album album) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select which song you would like to edit:");

        for (int i = 0; i < album.getListOfSongNames().size(); i++) {
            System.out.println("\t" + (i + 1) + " -> " + album.getListOfSongNames().get(i));
        }
        //int input = scanner.nextInt();
        songNameChange(album.getSong(scanner.nextInt() - 1));
    }


    // edits the song name to new user input
    public static void songNameChange(Song song) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What would you like to change song name to:");
        String input = scanner.nextLine();
        try {
            song.editSong("title", input);
        } catch (EmptyObjectException e) {
            System.out.println("Some song fields are empty, unable to edit song");
        } catch (IncorrectInputException e) {
            System.out.println("Incorrect compare input");
        }
        System.out.println("Song name has been changed to " + input);
    }

    // edits the artist name to new user input
    public static void artistNameChange(Artist artist) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What would you like to change the artist to:");
        input = scanner.nextLine();
        try {
            artist.editArtist("artist", input);
        } catch (EmptyObjectException e) {
            System.out.println("Artist is empty, cannot be edited!");
        }
        System.out.println("Artist has been changed to " + input);
    }

    // edits the genre to new user input
    public static void genreChange(Artist artist) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What would you like to change the genre to:");
        input = scanner.nextLine();
        try {
            artist.editArtist("genre", input);
        } catch (EmptyObjectException e) {
            System.out.println("Artist is empty, cannot be edited");
        }
        System.out.println("Genre has been changed to " + input);
    }

    // adds multiple songs to album, allowing multiple worded songs
    public static List<String> setSongListInAlbum() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("To add songs to the Album, type the song name then hit enter");
        System.out.println("When you are done type done");
        List<String> albumSongs = new ArrayList<>();
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("done")) {
                break;
            } else {
                albumSongs.add(input);
            }
        }
        return albumSongs;
    }
}
