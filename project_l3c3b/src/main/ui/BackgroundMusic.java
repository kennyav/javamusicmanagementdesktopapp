package ui;

import sun.audio.*;
import java.io.*;

// class for program music
public class BackgroundMusic {
    AudioPlayer backgroundMusicPlayer = AudioPlayer.player;
    AudioStream backgroundMusic;
    AudioData musicData;
    ContinuousAudioDataStream loopMusic = null;

    public BackgroundMusic() throws IOException {
        backgroundMusic = new AudioStream(new FileInputStream("smw_game_over.wav"));
        musicData = backgroundMusic.getData();
        loopMusic = new ContinuousAudioDataStream(musicData);

        backgroundMusicPlayer.start(loopMusic);
    }

    // MODIFIES: MusicManagerProgram
    // EFFECTS: creates a new AudioStream and plays it once for save and load button
    public void saveButtonMusic() throws IOException {
        AudioStream saveButtonMusic = new AudioStream(new FileInputStream("smw_save_menu.wav"));
        AudioPlayer saveMusicPlayer = AudioPlayer.player;
        saveMusicPlayer.start(saveButtonMusic);
    }

    // MODIFIES: MusicManagerProgram
    // EFFECTS: creates a new AudioStream and plays it once for submit button
    public void submitButtonMusic() throws IOException {
        AudioStream submitButtonMusic = new AudioStream(new FileInputStream("smw_princess_help.wav"));
        AudioPlayer submitMusicPlayer = AudioPlayer.player;
        submitMusicPlayer.start(submitButtonMusic);
    }

}
