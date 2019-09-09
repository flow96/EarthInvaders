package de.flutze.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import de.flutze.actors.Player;
import de.flutze.hud.ClassicGameHud;

public class ClassicGameScreen implements Screen {


    private Batch batch;
    private Player player;
    private OrthographicCamera camera;
    private Viewport viewport;
    private ClassicGameHud gameHud;


    public ClassicGameScreen(Batch batch){
        this.batch = batch;
        player = new Player();
        camera = new OrthographicCamera();
        viewport = new FitViewport(800, 640, camera);

        gameHud = new ClassicGameHud(camera, batch);
    }


    @Override
    public void show() {

    }

    private void update(float delta){
        player.update(delta);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw game
        viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        player.render(batch);
        batch.end();

        // Draw HUD on top
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
