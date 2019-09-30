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
import com.badlogic.gdx.utils.viewport.Viewport;

import de.flutze.actors.BackgroundStars;
import de.flutze.controls.InputManager;
import de.flutze.utils.Const;

public class CreditsScreen implements Screen {


    private Batch batch;
    private BackgroundStars backgroundStars;
    private Game game;
    private Stage stage;
    private InputManager inputManager;
    protected Texture fontTexture;

    public CreditsScreen(Batch batch, BackgroundStars backgroundStars, Game game){
        this.batch = batch;
        this.backgroundStars = backgroundStars;
        this.game = game;
        this.inputManager = InputManager.getInstance();
        fontTexture = new Texture("Fonts/" + Const.FONT_NAME + ".png");
        fontTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Viewport viewport = new FitViewport(Const.WIDTH, Const.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        initStage();
    }

    @Override
    public void show() {

    }

    private void initStage(){
        Label lblCredits = new Label("CREDITS", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("Fonts/" + Const.FONT_NAME + ".fnt"), new TextureRegion(fontTexture)), Color.WHITE));
        Label lblCode = new Label("Programming: Florian Lutze", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("Fonts/" + Const.FONT_NAME + ".fnt"), new TextureRegion(fontTexture)), Color.WHITE));
        Label lblGraphics = new Label("Graphics: Florian Lutze", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("Fonts/" + Const.FONT_NAME + ".fnt"), new TextureRegion(fontTexture)), Color.WHITE));
        Label lblMusic = new Label("Music: https://patrickdearteaga.com & royal free music", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("Fonts/" + Const.FONT_NAME + ".fnt"), new TextureRegion(fontTexture)), Color.WHITE));
        Label lblSounds = new Label("Sounds: Made with BFXR", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("Fonts/" + Const.FONT_NAME + ".fnt"), new TextureRegion(fontTexture)), Color.WHITE));
        Label lblInfo = new Label("ESC to go back", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("Fonts/" + Const.FONT_NAME + ".fnt"), new TextureRegion(fontTexture)), Color.WHITE));

        lblCredits.setFontScale(.75f);
        lblCode.setFontScale(.4f);
        lblGraphics.setFontScale(.4f);
        lblMusic.setFontScale(.4f);
        lblSounds.setFontScale(.4f);
        lblInfo.setFontScale(.3f);

        lblCredits.setColor(Color.WHITE);
        lblCode.setColor(Color.WHITE);
        lblGraphics.setColor(Color.WHITE);
        lblMusic.setColor(Color.WHITE);
        lblSounds.setColor(Color.WHITE);

        lblInfo.setPosition(1, 0);


        Table table = new Table();
        table.setFillParent(true);
        table.center();

        table.add(lblCredits).expandX().center().padBottom(60);
        table.row();
        table.add(lblCode).expandX().center().padBottom(5);
        table.row();
        table.add(lblGraphics).expandX().center().padBottom(5);
        table.row();
        table.add(lblMusic).expandX().center().padBottom(5);
        table.row();
        table.add(lblSounds).expandX().center().padBottom(5);

        stage.addActor(lblInfo);


        //table.setDebug(true);
        stage.addActor(table);
    }


    private void handleInput(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            game.setScreen(new MainMenuScreen(game, batch, backgroundStars));
        }
    }

    @Override
    public void render(float delta) {
        handleInput();
        backgroundStars.update(delta);
        stage.act(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        backgroundStars.render(batch);
        batch.end();
        stage.getViewport().apply();
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
