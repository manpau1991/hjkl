package de.uni_passau.sep.portalgolf.sound;

import java.io.OutputStream;
import java.io.PrintStream;

import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemException;
import paulscode.sound.codecs.CodecJOrbis;
import paulscode.sound.codecs.CodecWav;
import paulscode.sound.libraries.LibraryJavaSound;

/**
 * This class represents the Audio-Player which is used to play the sounds for
 * the game PortalGolf
 *
 */
public class SoundPlayer {
    private static SoundSystem soundSystem;

    /**
     * 
     * Define enums which represents the sounds-sources in the soundsystem.
     *
     */
    public enum SOUND {
        BACKGROUND, MOVE, ACCEPT, RANDOM, INTERACT, GAMEMUSIC, PAUSE, HOLED;
    }

    /**
     * sets the global volume of the soundPlayer
     * 
     * @param volume
     *            the new volume
     */
    public static void setGlobalVolume(int volume) {
        float setVolume = (float) volume / 100;
        soundSystem.setMasterVolume(setVolume);
    }

    /**
     * returns the actual global Volume of the soundPlayer
     * 
     * @return
     */
    public static int getGlobalVolume() {
        return (int) (soundSystem.getMasterVolume() * 100);
    }

    /**
     * Play the sound <code>sound</code> with standard loop(false) and volume
     * (globalVolume)
     * 
     * @param sound
     *            the sound to play
     */
    public static void playSound(SOUND sound) {
        playSound(sound, false);
    }

    /**
     * plays the sound <code>sound</code>
     * 
     * @param sound
     *            the sound
     * @param loop
     *            if it should be looped
     */
    public static void playSound(SOUND sound, boolean loop) {
        switch (sound) {
        case MOVE:
            soundSystem.quickPlay(true, "move.ogg", loop, 0, 0, 0,
                    SoundSystemConfig.ATTENUATION_ROLLOFF,
                    SoundSystemConfig.getDefaultRolloff());
            break;
        case BACKGROUND:
            soundSystem.play(sound.toString());
            break;
        case ACCEPT:
            soundSystem.quickPlay(true, "menu_accept.ogg", loop, 0, 0, 0,
                    SoundSystemConfig.ATTENUATION_ROLLOFF,
                    SoundSystemConfig.getDefaultRolloff());
            break;
        case RANDOM:
            soundSystem.quickPlay(true, "intro2.ogg", loop, 0, 0, 0,
                    SoundSystemConfig.ATTENUATION_ROLLOFF,
                    SoundSystemConfig.getDefaultRolloff());
            break;
        case PAUSE:
            soundSystem.play(sound.toString());
            break;
        case INTERACT:
            soundSystem.quickPlay(true, "menu_accept.ogg", loop, 0, 0, 0,
                    SoundSystemConfig.ATTENUATION_ROLLOFF,
                    SoundSystemConfig.getDefaultRolloff());
            break;
        case GAMEMUSIC:
            soundSystem.backgroundMusic(sound.toString(), "gameMusic.ogg",
                    true);
            break;
        case HOLED:
            soundSystem.quickPlay(true, "hole.ogg", loop, 0, 0, 0,
                    SoundSystemConfig.ATTENUATION_ROLLOFF,
                    SoundSystemConfig.getDefaultRolloff());
            break;
        default:
            break;
        }
    }

    /**
     * Stops the sound <code>sound</code>
     */
    public static void stopSound(SOUND sound) {
        if (soundSystem.playing(sound.toString())) {
            soundSystem.stop(sound.toString());
        }
    }

    /**
     * Stops the SoundPlayer
     */
    public static void stopPlayer() {
        soundSystem.cleanup();
    }

    /**
     * Initialize the SoundPlayer and the sound-resources
     */
    public static void initPlayer() {
        PrintStream originalStream = System.out;
        try (PrintStream dummyStream = new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
                // ignore output
            }
        })) {
            System.setOut(dummyStream);
        }
        SoundSystemConfig.setSoundFilesPackage("resources/audio/");
        try {
            SoundSystemConfig.addLibrary(LibraryJavaSound.class);
        } catch (SoundSystemException e1) {
            e1.printStackTrace();
        }
        try {
            SoundSystemConfig.setCodec("wav", CodecWav.class);
            SoundSystemConfig.setCodec("ogg", CodecJOrbis.class);
        } catch (SoundSystemException e1) {
            e1.printStackTrace();
        }
        soundSystem = new SoundSystem();
        soundSystem.newSource(true, SOUND.PAUSE.toString(),
                "looping_radio_mix.ogg", true, 0, 0, 0,
                SoundSystemConfig.ATTENUATION_ROLLOFF,
                SoundSystemConfig.getDefaultRolloff());
        soundSystem.newStreamingSource(true, SOUND.BACKGROUND.toString(),
                "portal2_background.ogg", true, 0, 0, 0,
                SoundSystemConfig.ATTENUATION_ROLLOFF,
                SoundSystemConfig.getDefaultRolloff());
        soundSystem.newStreamingSource(true, SOUND.MOVE.toString(), "move.ogg",
                false, 0, 0, 0, SoundSystemConfig.ATTENUATION_ROLLOFF,
                SoundSystemConfig.getDefaultRolloff());
        soundSystem.newStreamingSource(true, SOUND.ACCEPT.toString(),
                "menu_accept.ogg", false, 0, 0, 0,
                SoundSystemConfig.ATTENUATION_ROLLOFF,
                SoundSystemConfig.getDefaultRolloff());
        soundSystem.newStreamingSource(true, SOUND.RANDOM.toString(),
                "intro2.ogg", false, 0, 0, 0,
                SoundSystemConfig.ATTENUATION_ROLLOFF,
                SoundSystemConfig.getDefaultRolloff());
        soundSystem.newStreamingSource(true, SOUND.INTERACT.toString(),
                "menu_accept.ogg", false, 0, 0, 0,
                SoundSystemConfig.ATTENUATION_ROLLOFF,
                SoundSystemConfig.getDefaultRolloff());
        soundSystem.newStreamingSource(true, SOUND.GAMEMUSIC.toString(),
                "gameMusic.ogg", false, 0, 0, 0,
                SoundSystemConfig.ATTENUATION_ROLLOFF,
                SoundSystemConfig.getDefaultRolloff());
        System.setOut(originalStream);
    }
}
