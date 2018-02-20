package com.cannawen.reader.model.chapter;

import android.media.AudioManager;
import android.media.MediaPlayer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class AudioChapter extends Chapter {

    private MediaPlayer player;
    private String mp3UrlString;

    public AudioChapter(int index, String title, String urlString) {
        this.mp3UrlString = urlString;
        this.index = index;
        this.title = title;

//        try {
//            File appDirectory = new File(android.os.Environment.getExternalStorageDirectory(), "Reader");
//            if (!appDirectory.exists()) {
//                appDirectory.mkdirs();
//            }
//
//            File file = new File(appDirectory,title);
//            if (!file.exists()) {
//                URL url = new URL(urlString);
//
//                InputStream input = new BufferedInputStream(url.openStream());
//                OutputStream output = new FileOutputStream(file);
//
//                byte data[] = new byte[1024];
//                int count;
//                while ((count = input.read(data)) != -1) {
//                    output.write(data, 0, count);
//                }
//
//                output.flush();
//                output.close();
//                input.close();
//            }
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void readNow() {
        listener.startedChapter(index);
        try {
            player = new MediaPlayer();
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setDataSource(mp3UrlString);
            player.prepare();
            player.start();
        } catch (Exception e) {

        }
    }

    @Override
    public void stopReading() {
        if (player != null) {
            player.stop();
        }
    }
}
