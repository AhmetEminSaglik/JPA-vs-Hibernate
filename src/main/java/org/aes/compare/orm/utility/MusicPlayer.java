package org.aes.compare.orm.utility;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.InputStream;

public class MusicPlayer {
    private static final String path = "/music.mp3";
    private Player player;
    private Thread playerThread;
    private volatile boolean isPlaying = false;

    public void start() {
        if (isPlaying) {
            return;
        }

        isPlaying = true;
        playerThread = new Thread(() -> {
            while (isPlaying) { // Loop to keep playing the audio
                try (InputStream in = getClass().getResourceAsStream(path)) {
                    player = new Player(in);
                    player.play();
                } catch (JavaLayerException e) {
                    System.err.println("Error playing audio: " + e.getMessage());
                } catch (Exception e) {
                    System.err.println("An unexpected error occurred: " + e.getMessage());
                }
            }
            isPlaying = false;
        });
        playerThread.start();
    }

    public void pause() {
        if (player != null) {
            player.close();
            isPlaying = false;
        }
    }

    public void resume() {
        if (!isPlaying) {
            start();
        }
    }
}