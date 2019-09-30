package de.flutze.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.Random;

import de.flutze.controller.WaveController;
import de.flutze.utils.OffsetGenerator;

public class Enemy extends BaseActor {


    private TextureRegion texture;
    private Vector2 velocity;
    public boolean destroyed;

    private OffsetGenerator offsetGenerator;
    private int shootChance;
    private WaveController controller;
    private long lastShot;
    private int shootDelay;

    public final static int WIDTH = 25;
    public final static int HEIGHT = 25;
    public final EnemyType enemyType;

    public Enemy(Vector2 position, Vector2 velocity, EnemyType enemyType, int shootChance, WaveController controller) {
        this.velocity = velocity;
        this.destroyed = false;
        this.enemyType = enemyType;
        this.shootChance = shootChance;
        this.controller = controller;
        this.shootDelay = (200 - shootChance * 10) + (int)(Math.random() * 600);
        this.lastShot = System.currentTimeMillis();
        this.offsetGenerator = new OffsetGenerator(10);
        this.texture = new TextureRegion(new Texture("Enemies/" + enemyType.texture));
        setRectOffset(3);
        setSize(WIDTH, HEIGHT);
        setPosition(position.x, position.y);
    }

    public void act(float delta) {
        setPosition(getX() + velocity.x, getY() + velocity.y + .15f * offsetGenerator.getNext(delta));
        if(lastShot < System.currentTimeMillis() + shootDelay){
            int rnd = (int)(Math.random() * 11000);
            if(rnd < shootChance){
                shoot();
            }
        }
    }

    public void draw(Batch batch) {
        batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    public void shoot(){
        lastShot = System.currentTimeMillis();
        controller.spawnEnemyBullet(new Vector2(getX(), getY()));
    }


    public enum EnemyType {
        SHIP1(0),
        SHIP2(1),
        SHIP3(2);

        private final int type;
        public final int points;
        public final String texture;

        EnemyType(int type) {
            this.type = type;
            switch (type) {
                case 1:
                    this.points = 20;
                    this.texture = "Enemy2.png";
                    break;
                case 2:
                    this.points = 30;
                    this.texture = "Enemy3.png";
                    break;
                default:
                    this.points = 10;
                    this.texture = "Enemy1.png";
                    break;
            }
        }

    }
}
