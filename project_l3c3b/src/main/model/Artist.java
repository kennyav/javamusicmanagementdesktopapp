package model;

//credit to JSONSerializationDemo

public class Artist {
    protected String artistName;
    protected String artistGenreName;

    //Construct an empty artist
    public Artist() {
        artistName = "";
        artistGenreName = "";
    }

    // getters
    public String getArtistName() {
        return artistName;
    }

    public String getArtistGenre() {
        return artistGenreName;
    }


    //EFFECTS: this
    //MODIFIES: sets value to artistName and artistGenreName
    public void setArtistInformation(String artist, String genre) {
        this.artistName = artist;
        this.artistGenreName = genre;
    }

    //EFFECTS: this
    //MODIFIES: if feature is equal to string, then this resets the value that value of edit
    public void editArtist(String feature, String edit) throws EmptyObjectException {
        if (artistName.equals("") || artistGenreName.equals("")) {
            throw new EmptyObjectException();
        }
        if (feature.equals("artist")) {
            artistName = edit;
        } else if (feature.equals("genre")) {
            artistGenreName = edit;
        }
    }

}
