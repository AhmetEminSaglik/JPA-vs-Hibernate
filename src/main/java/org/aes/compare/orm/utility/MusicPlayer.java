package org.aes.compare.orm.utility;

import com.ahmeteminsaglik.MusicPlayerForConsoleApp;

public class MusicPlayer {
    private static MusicPlayerForConsoleApp musicPlayerForConsoleApp = new MusicPlayerForConsoleApp("D:\\projects\\intelijidea\\Hibernate-vs-Jpa\\src\\main\\resources\\music.wav");

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
        musicPlayerForConsoleApp.stop();
    }

    public void resume() {
        start();
    }
}
