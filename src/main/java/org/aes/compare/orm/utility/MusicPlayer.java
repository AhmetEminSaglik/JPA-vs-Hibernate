package org.aes.compare.orm.utility;

import com.ahmeteminsaglik.MusicPlayerForConsoleApp;

public class MusicPlayer {
    private static MusicPlayerForConsoleApp musicPlayerForConsoleApp = new MusicPlayerForConsoleApp("src/main/resources/music.wav");

    public void start() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                musicPlayerForConsoleApp.start();
            }
        });
        thread.start();
    }

    public void pause() {
        musicPlayerForConsoleApp.pauseMusic();
    }

    public void resume() {
        musicPlayerForConsoleApp.resumeMusic();
    }
}
