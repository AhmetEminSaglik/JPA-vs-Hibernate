package org.aes.compare.orm.utility;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.InputStream;

public class MusicPlayer {
    private static final String path = "/music.mp3";
    private static Player player;
    public void start() {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream in = getClass().getResourceAsStream(path);
                    player = new Player(in);
                    player.play();

                } catch (JavaLayerException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();
    }

    public void pause() {
        if (player != null) {
            player.close();
        }
    }

    public void resume() {
        start();
    }
}
