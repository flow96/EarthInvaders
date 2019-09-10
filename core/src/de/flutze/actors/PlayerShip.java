package de.flutze.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;
import java.util.List;

public class PlayerShip extends Actor {

    private TextureRegion ship;
    private Vector2 velocity;
    private List<Bullet> bullets;
    private long weaponCoolDown;
    private int shootDelay;
    private ParticleEffect effect;
    private float bouncing;


    public PlayerShip(String ship){
        this.ship = new TextureRegion(new Texture(ship));

        effect = new ParticleEffect();
        velocity = new Vector2();
        bullets = new ArrayList<Bullet>();
        shootDelay = 100;
        weaponCoolDown = 0;
        bouncing = 0;

        effect.load(Gdx.files.internal("Particles/ShipParticle4.p"), Gdx.files.internal("Particles"));
        effect.getEmitters().first().setPosition(getX(),getY());
        effect.start();
        setSize(26, 26);
        setPosition(Gdx.graphics.getWidth() / 2f + getOriginX(), 64);

    }


    @Override
    public void act(float delta) {
        super.act(delta);
        float speed = (float) Math.random() * 15;
        bouncing = (bouncing + delta * speed) % ((float)Math.PI * 4);
        setPosition(getX() + velocity.x * delta, getY() + velocity.y * delta + .15f * ((float) Math.cos(bouncing)));

        for (int i = 0; i < bullets.size(); i++) {
            if(bullets.get(i).getY() > Gdx.graphics.getHeight() + 10) {
                bullets.remove(i);
                i--;
            }else
                bullets.get(i).act(delta);

        }

        velocity.scl(.8f);
        effect.update(delta);
    }

    public void shoot(){
        if(weaponCoolDown < System.currentTimeMillis()){
            weaponCoolDown = System.currentTimeMillis() + shootDelay;
            bullets.add(new Bullet(new Vector2(getX() + getOriginX(), getY() + getHeight()), new Vector2(0, 350), "Bullets/Bullet1.png"));
        }
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        effect.draw(batch);
        batch.draw(ship, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).draw(batch, parentAlpha);
        }
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        setOrigin(width / 2, height / 2);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        effect.getEmitters().first().setPosition(x + getOriginX(), y);
    }

    public void moveRight(){
        //if(velocity.x < 1000000){
            velocity.add(80, 0);
        //}
    }

    public void moveLeft(){
        //if(velocity.x > -1000000){
            velocity.add(-80, 0);
        //}
    }

}
