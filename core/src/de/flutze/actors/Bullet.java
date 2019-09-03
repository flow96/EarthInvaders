package de.flutze.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Bullet extends Actor {

    private TextureRegion bullet;
    private Vector2 velocity;


    public Bullet(Vector2 position, Vector2 velocity, String bullet){
        this.bullet = new TextureRegion(new Texture(bullet));
        setSize(16, 16);
        setPosition(position.x - getOriginX(), position.y - getOriginY());
        this.velocity = velocity;
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        setOrigin(width / 2, height / 2);
    }

    @Override
    public void act(float delta) {
        setPosition(getX() + velocity.x * delta, getY() + velocity.y * delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(bullet, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
}
