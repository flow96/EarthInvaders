package de.flutze.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;

import de.flutze.actors.BackgroundStars;
import de.flutze.utils.Const;
import de.flutze.utils.HighscoreManager;

public class HighscoreScreen implements Screen {


    private Batch batch;
    private Stage stage;
    private Game game;
    private BackgroundStars backgroundStars;
    private Texture fontTexture;
    private HighscoreManager highscoreManager;

    public HighscoreScreen(Batch batch, BackgroundStars backgroundStars, Game game){
        this.batch = batch;
        this.backgroundStars = backgroundStars;
        this.game = game;
        this.highscoreManager = HighscoreManager.getInstance();
        fontTexture = new Texture("Fonts/" + Const.FONT_NAME + ".png");
        fontTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        initStage();
    }


    private void initStage() {
        Table table = new Table();
        table.setFillParent(true);
        table.center();

        stage = new Stage(new FitViewport(Const.WIDTH, Const.HEIGHT, new OrthographicCamera()), batch);

        Label lblTitle = new Label("HIGHSCORES" , new Label.LabelStyle(new BitmapFont(Gdx.files.internal("Fonts/" + Const.FONT_NAME + ".fnt"), new TextureRegion(fontTexture)), Color.WHITE));
        lblTitle.setFontScale(.7f);

        table.add(lblTitle).expandX().center().padBottom(50);
        table.row();

        for (int i = 0; i < highscoreManager.getScores().length; i++) {
            Label lblScore = new Label((i + 1) + ".    " + String.format("%05d", highscoreManager.getScores()[i].getScore()), new Label.LabelStyle(new BitmapFont(Gdx.files.internal("Fonts/" + Const.FONT_NAME + ".fnt"), new TextureRegion(fontTexture)), Color.WHITE));
            lblScore.setFontScale(.4f);
            table.add(lblScore).expandX().center().padTop(5);
            table.row();
        }
        stage.addActor(table);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
            game.setScreen(new MainMenuScreen(game, batch, backgroundStars));

        stage.act(delta);
        backgroundStars.update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        backgroundStars.render(batch);
        batch.end();
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
