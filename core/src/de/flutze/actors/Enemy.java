package de.flutze.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import de.flutze.utils.OffsetGenerator;

public class Enemy extends Actor {


    private TextureRegion texture;
    private Vector2 velocity;
    public boolean destroyed;

    private Rectangle rectangle;
    private int rectOffset = 3;
    private OffsetGenerator offsetGenerator;

    public final static int WIDTH = 25;
    public final static int HEIGHT = 25;
    public final EnemyType enemyType;

    public Enemy(Vector2 position, Vector2 velocity, EnemyType enemyType) {
        this.velocity = velocity;
        this.destroyed = false;
        this.enemyType = enemyType;
        this.offsetGenerator = new OffsetGenerator(10);
        this.texture = new TextureRegion(new Texture("Enemies/" + enemyType.texture));
        setSize(WIDTH, HEIGHT);
        setPosition(position.x, position.y);
    }

    public void act(float delta) {
        setPosition(getX() + velocity.x, getY() + velocity.y + .15f * offsetGenerator.getNext(delta));
    }

    public void draw(Batch batch) {
        batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }


    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        setOrigin(width / 2f, height / 2f);
        rectangle = new Rectangle(getX() + rectOffset, getY() + rectOffset, width - 2 * rectOffset, height - 2 * rectOffset);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        rectangle.setPosition(x + rectOffset, y + rectOffset);
    }

    public Rectangle getRectangle() {
        return rectangle;
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
