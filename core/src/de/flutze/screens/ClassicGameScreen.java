package de.flutze.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import org.omg.CORBA.CODESET_INCOMPATIBLE;

import de.flutze.actors.BackgroundStars;
import de.flutze.actors.Player;
import de.flutze.hud.ClassicGameHud;
import de.flutze.utils.Const;
import de.flutze.utils.OffsetGenerator;

public class ClassicGameScreen implements Screen {


    private Batch batch;
    private Player player;
    private OrthographicCamera camera;
    private Viewport viewport;

    private ClassicGameHud gameHud;
    private BackgroundStars backgroundStars;

    private Texture earthTexture;

    private OffsetGenerator offsetGenerator;


    public ClassicGameScreen(Batch batch){
        this.batch = batch;
        offsetGenerator = new OffsetGenerator(2);
        player = new Player();
        camera = new OrthographicCamera();
        viewport = new FitViewport(Const.WIDTH, Const.HEIGHT, camera);

        gameHud = new ClassicGameHud(batch);
        backgroundStars = new BackgroundStars();

        earthTexture = new Texture("Earth/Earth1.png");

    }


    @Override
    public void show() {

        System.out.println("INIT COLOR: " + batch.getColor().a);
    }

    private void update(float delta){
        player.update(delta);
        backgroundStars.update(delta);


        viewport.getCamera().translate(0, .05f * offsetGenerator.getNext(delta), 0);
        viewport.getCamera().update();
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Apply viewport and camera matrix
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        // Start drawing
        batch.begin();
        backgroundStars.render(batch);
        batch.draw(earthTexture, -200, -8, Const.WIDTH + 350, 300);
        player.render(batch);
        batch.end();

        // Draw HUD on top with different viewport (needs to be done last!)
        gameHud.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        gameHud.resize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
