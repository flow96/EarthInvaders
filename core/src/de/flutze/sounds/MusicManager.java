package de.flutze.sounds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class MusicManager {
    /*
    Credits (Chiptronicle): https://patrickdearteaga.com.

     */

    private Music music;
    public final Sound shoot1, shoot2, shoot3, destroyed, uiFeedback, die, powerup, pickup;

    private static MusicManager instance = new MusicManager();

    public static MusicManager getInstance() {
        return instance;
    }

    private MusicManager(){
        music = Gdx.audio.newMusic(Gdx.files.internal("Music/InterstellarOdyssey.ogg"));
        shoot1 = Gdx.audio.newSound(Gdx.files.internal("Sounds/Laser_Shoot.wav"));
        shoot2 = Gdx.audio.newSound(Gdx.files.internal("Sounds/Laser_Shoot2.wav"));
        shoot3 = Gdx.audio.newSound(Gdx.files.internal("Sounds/Laser_Shoot3.wav"));
        uiFeedback = Gdx.audio.newSound(Gdx.files.internal("Sounds/Select.wav"));
        destroyed = Gdx.audio.newSound(Gdx.files.internal("Sounds/Destroyed.wav"));
        die = Gdx.audio.newSound(Gdx.files.internal("Sounds/Die.wav"));
        powerup = Gdx.audio.newSound(Gdx.files.internal("Sounds/Powerup.wav"));
        pickup = Gdx.audio.newSound(Gdx.files.internal("Sounds/Pickup.wav"));
    }

    public void dispose(){
        music.dispose();
        shoot1.dispose();
        shoot2.dispose();
        shoot3.dispose();
        uiFeedback.dispose();
        destroyed.dispose();
        die.dispose();
        powerup.dispose();
        pickup.dispose();
    }

    public void start(){
        if(!music.isPlaying()) {
            music.setVolume(.7f);
            music.setVolume(0f);
            music.setLooping(true);
            music.play();
        }
    }

}
