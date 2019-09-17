package de.flutze.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

import de.flutze.utils.Const;

public class BackgroundStars {

    private Texture star;
    private Star[] stars;
    private Random rnd;
    private final int starCount = 100;
    private final int speed = 50;

    public BackgroundStars() {
        star = new Texture("Particles/Particle.png");
        stars = new Star[starCount];
        rnd = new Random();

        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(new Vector2(rnd.nextInt(Const.WIDTH + 5), rnd.nextInt(Const.HEIGHT + 10)), rnd.nextInt(2) + 1
                    , (rnd.nextInt(6) + 1) / 10f + 0.2f, rnd.nextInt(600) + 400);
        }
    }


    public void update(float delta) {
        for (int i = 0; i < stars.length; i++) {
            stars[i].live--;

            if(stars[i].live < 20){
                stars[i].alpha -= .05f;
            }


            if (stars[i].position.y <= -10 || stars[i].live <= 0) {
                stars[i].position.y = Const.HEIGHT + 10;
                stars[i].position.x = rnd.nextInt(Const.WIDTH + 5);
                stars[i].alpha = (rnd.nextInt(6) + 1) / 10f + 0.2f;
                stars[i].live = rnd.nextInt(600) + 300;
            } else
                stars[i].position.y -= speed * delta;

        }
    }

    public void render(Batch batch) {
        final Color origColor = batch.getColor().cpy();
        for (int i = 0; i < stars.length; i++) {
            batch.setColor(255, 255, 255, stars[i].alpha);
            batch.draw(star, stars[i].position.x, stars[i].position.y, stars[i].size, stars[i].size);
        }
        batch.setColor(origColor);
    }


    private class Star{
        Vector2 position;
        int size;
        float alpha;
        int live;

        Star(Vector2 pos, int size, float alpha, int liveTime){
            this.position = pos;
            this.size = size;
            this.alpha = alpha;
            this.live = liveTime;
        }
    }

}
