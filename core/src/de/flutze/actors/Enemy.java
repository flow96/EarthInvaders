package de.flutze.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Enemy extends Actor {


    private TextureRegion texture;
    private Vector2 velocity;
    public boolean destroyed;

    private Rectangle rectangle;
    private int rectOffset = 3;

    public Enemy(Vector2 position, Vector2 velocity, String enemyType){
        this.velocity = velocity;
        this.destroyed = false;
        this.texture = new TextureRegion(new Texture("Ships/" + enemyType));
        setSize(23, 23);
        setPosition(position.x, position.y);
    }

    public void act(float delta){
        setPosition(getX() + velocity.x, getY() + velocity.y);
    }

    public void draw(Batch batch){
        batch.draw(texture, getX(), getY(),getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
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
}
