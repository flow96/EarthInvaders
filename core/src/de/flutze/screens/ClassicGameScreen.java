package de.flutze.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import de.flutze.actors.BackgroundStars;
import de.flutze.controller.ClassicGameController;
import de.flutze.hud.ClassicGameHud;
import de.flutze.utils.Const;
import de.flutze.utils.OffsetGenerator;

public class ClassicGameScreen implements Screen {



    private ClassicGameController gameController;



    public ClassicGameScreen(Batch batch, BackgroundStars backgroundStars, Game game){
        gameController = new ClassicGameController(batch, backgroundStars, game);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        gameController.update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameController.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        gameController.resize(width, height);
    }

    @Override
    public void pause() {
        gameController.setPaused(true);
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        gameController.setPaused(true);
    }

    @Override
    public void dispose() {

    }
}
