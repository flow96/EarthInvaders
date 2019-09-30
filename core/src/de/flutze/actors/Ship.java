package de.flutze.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import java.util.ArrayList;
import java.util.List;

import de.flutze.controls.InputManager;
import de.flutze.sounds.MusicManager;
import de.flutze.utils.Const;
import de.flutze.utils.OffsetGenerator;

public class Ship extends BaseActor {

    private TextureRegion ship;
    private Vector2 velocity;
    private List<Bullet> bullets;
    private long weaponCoolDown;
    private int shootDelay;
    private ParticleEffect effect;
    private OffsetGenerator offsetGenerator;

    public int maxBullets = 200;
    public final float POS_Y = 65;
    private int lives;
    private MusicManager musicManager;
    private boolean dead;


    public Ship(String ship) {
        this.ship = new TextureRegion(new Texture(ship));
        musicManager = MusicManager.getInstance();
        effect = new ParticleEffect();
        velocity = new Vector2();
        bullets = new ArrayList<Bullet>();
        shootDelay = 100;
        weaponCoolDown = 0;
        dead = false;
        lives = 3;
        offsetGenerator = new OffsetGenerator(15);

        effect.load(Gdx.files.internal("Particles/ShipParticle4.p"), Gdx.files.internal("Particles"));
        effect.getEmitters().first().setPosition(getX(), getY());
        effect.start();
        setRectOffset(3);
        setSize(26, 26);
        setPosition(Const.WIDTH / 2f - getOriginX(), 0);
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        if(!dead) {
            setPosition(getX() + velocity.x * delta, getY() + velocity.y * delta + .17f * offsetGenerator.getNext(delta));
            velocity.scl(.8f);
        }
        effect.update(delta);
        for (int i = 0; i < bullets.size(); i++) {
            if (bullets.get(i).getY() > Const.HEIGHT + 10) {
                bullets.get(i).dispose();
                bullets.remove(i);
                i--;
            } else
                bullets.get(i).act(delta);
        }
    }

    public void shoot() {
        if (!dead && maxBullets > bullets.size() && weaponCoolDown < System.currentTimeMillis()) {
            musicManager.shoot1.play(musicManager.SOUND_VOLUME);
            weaponCoolDown = System.currentTimeMillis() + shootDelay;
            bullets.add(new Bullet(new Vector2(getX() + getOriginX(), getY() + getHeight()), new Vector2(0, 400), "Bullets/Bullet1.png"));
        }
    }

    public int getBulletsCount() {
        return bullets.size();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(!dead) {
            effect.draw(batch);
            batch.draw(ship, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).draw(batch, parentAlpha);
        }
    }


    @Override
    public void setPosition(float x, float y) {
        if(x < 0)
            x = 0;
        if(x + getWidth() >= Const.WIDTH)
            x = Const.WIDTH - getWidth();
        super.setPosition(x, y);
        effect.getEmitters().first().setPosition(x + getOriginX(), y);
    }

    public void moveRight() {
        if (!dead && getX() + getWidth() < Const.WIDTH) {
            velocity.add(80, 0);
        }
    }

    public void moveLeft() {
        if (!dead && getX() > 0) {
            velocity.add(-80, 0);
        }
    }

    public void dispose(){
        ship.getTexture().dispose();
        effect.dispose();
    }

    public int getLives() {
        return lives;
    }

    public TextureRegion getShip() {
        return ship;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public boolean die(){
        if(!dead) {
            dead = true;
            musicManager.die.play(musicManager.SOUND_VOLUME);
            if (lives > 0) {
                lives--;
                respawn(1);
            } else {
                return true;
            }
        }
        return false;
    }
    private void respawn(float delay){
        addAction(Actions.delay(delay, Actions.run(new Runnable() {
            @Override
            public void run() {
                setPosition(Const.WIDTH / 2f - getOriginX(), POS_Y);
                effect.reset();
                bullets.clear();
                velocity.setZero();
                dead = false;
            }
        })));
    }
}
