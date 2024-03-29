package de.flutze.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Bullet extends BaseActor {

    private TextureRegion bullet;
    private Vector2 velocity;
    private ParticleEffect effect;


    public Bullet(Vector2 position, Vector2 velocity, String bullet){
        this.bullet = new TextureRegion(new Texture(bullet));

        effect = new ParticleEffect();
        effect.load(Gdx.files.internal("Particles/BulletParticle.p"), Gdx.files.internal("Particles"));

        setRectOffset(1);
        setSize(16, 16);
        setPosition(position.x - getOriginX(), position.y - getOriginY());
        this.velocity = velocity;

        effect.start();
    }


    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        effect.getEmitters().first().setPosition(x + getOriginX(), y);
    }

    @Override
    public void act(float delta) {
        setPosition(getX() + velocity.x * delta, getY() + velocity.y * delta);
        effect.update(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(bullet, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        effect.draw(batch);
    }


    public void dispose(){
        bullet.getTexture().dispose();
    }
}
