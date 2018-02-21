package com.cannawen.reader.media;

/**
 * Allows {@link ../activity/MainActivity} to control media playback of {@link ../model/Chapter}.
 */
public interface PlayerAdapter {

    void init();

    void release();

    boolean isPlaying();

    void play();

    void reset();

    void pause();

    void initializeProgressCallback();

    void seekTo(int position);
}
