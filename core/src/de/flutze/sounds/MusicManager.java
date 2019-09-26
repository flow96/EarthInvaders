package de.flutze.sounds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class MusicManager {
    /*
    Credits (Chiptronicle): https://patrickdearteaga.com.

     */

    public final Music music1, music2, music3;
    public final Sound shoot1, shoot2, shoot3, destroyed, uiFeedback, die, powerup, pickup;
    public static float SOUND_VOLUME = 1;

    private Music playingMusic;
    private int repeatCounter;

    private static MusicManager instance = new MusicManager();

    public static MusicManager getInstance() {
        return instance;
    }

    private MusicManager() {
        music1 = Gdx.audio.newMusic(Gdx.files.internal("Music/InterstellarOdyssey.ogg"));
        music2 = Gdx.audio.newMusic(Gdx.files.internal("Music/Chiptronical.ogg"));
        music3 = Gdx.audio.newMusic(Gdx.files.internal("Music/SpaceGamerLoop.mp3"));
        playingMusic = music1;

        shoot1 = Gdx.audio.newSound(Gdx.files.internal("Sounds/Laser_Shoot.wav"));
        shoot2 = Gdx.audio.newSound(Gdx.files.internal("Sounds/Laser_Shoot2.wav"));
        shoot3 = Gdx.audio.newSound(Gdx.files.internal("Sounds/Laser_Shoot3.wav"));
        uiFeedback = Gdx.audio.newSound(Gdx.files.internal("Sounds/Select.wav"));
        destroyed = Gdx.audio.newSound(Gdx.files.internal("Sounds/Destroyed.wav"));
        die = Gdx.audio.newSound(Gdx.files.internal("Sounds/Die.wav"));
        powerup = Gdx.audio.newSound(Gdx.files.internal("Sounds/Powerup.wav"));
        pickup = Gdx.audio.newSound(Gdx.files.internal("Sounds/Pickup.wav"));

        setMusicVolume(.5f);
        SOUND_VOLUME = 1;
    }

    public void dispose() {
        music1.dispose();
        music2.dispose();
        music3.dispose();

        shoot1.dispose();
        shoot2.dispose();
        shoot3.dispose();
        uiFeedback.dispose();
        destroyed.dispose();
        die.dispose();
        powerup.dispose();
        pickup.dispose();
    }

    public void setMusicVolume(float v) {
        music1.setVolume(v);
        music2.setVolume(v);
        music3.setVolume(v);
    }

    public void start() {
        if (!playingMusic.isPlaying()) {
            int rnd = (int) (Math.random() * 3);
            System.out.println(rnd);


            switch (rnd) {
                case 0:
                    playingMusic = music1;
                    break;
                case 1:
                    playingMusic = music2;
                    break;
                case 2:
                default:
                    playingMusic = music3;
                    break;
            }

            playingMusic.play();

            playingMusic.setOnCompletionListener(new Music.OnCompletionListener() {
                @Override
                public void onCompletion(Music music) {
                    if (music == music3 && repeatCounter <= 1) {
                        playingMusic.play();
                        repeatCounter++;
                    } else {
                        playingMusic.stop();
                        start();
                        repeatCounter = 0;
                    }
                }
            });
        }
    }
}
