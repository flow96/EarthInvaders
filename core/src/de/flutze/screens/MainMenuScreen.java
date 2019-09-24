package de.flutze.screens;

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
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import de.flutze.TheGame;
import de.flutze.actors.BackgroundStars;
import de.flutze.utils.Const;

public class MainMenuScreen implements Screen {

    private Texture fontTexture;
    private OrthographicCamera camera;
    private Stage stage;
    private TheGame theGame;
    private Label lblStartGame, lblHighscores, lblExit, lblTitle;
    private int selectedLabel;
    private Label.LabelStyle inactiveStyle, activeStyle;
    private float activeScale, inactiveScale;
    private BackgroundStars backgroundStars;
    private Batch batch;
    private boolean inputAllowed;


    public MainMenuScreen(TheGame theGame, Batch batch) {
        this.theGame = theGame;
        this.batch = batch;
        this.selectedLabel = 0;
        this.inputAllowed = true;
        this.fontTexture = new Texture("Fonts/" + Const.FONT_NAME + ".png");
        this.fontTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.inactiveStyle = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("Fonts/" + Const.FONT_NAME + ".fnt"), new TextureRegion(fontTexture)), Color.GRAY);
        this.activeStyle = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("Fonts/" + Const.FONT_NAME + ".fnt"), new TextureRegion(fontTexture)), Color.WHITE);
        this.activeScale = .6f;
        this.inactiveScale = .6f;
        this.backgroundStars = new BackgroundStars();
        camera = new OrthographicCamera();
        Viewport viewport = new FitViewport(Const.WIDTH, Const.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        initGameHud();
    }


    private void initGameHud() {

        lblTitle = new Label("EARTH INVADERS", activeStyle);
        lblTitle.setFontScale(.8f);

        Table table = new Table();
        table.top();
        table.top().add(lblTitle).expandX().padBottom(95).padTop(10);
        table.row();
        table.center();
        table.setFillParent(true);
        lblStartGame = new Label("START CLASSIC", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("Fonts/" + Const.FONT_NAME + ".fnt"), new TextureRegion(fontTexture)), Color.WHITE));
        lblHighscores = new Label("HIGHSCORES", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("Fonts/" + Const.FONT_NAME + ".fnt"), new TextureRegion(fontTexture)), Color.GRAY));
        lblExit = new Label("EXIT", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("Fonts/" + Const.FONT_NAME + ".fnt"), new TextureRegion(fontTexture)), Color.GRAY));

        lblStartGame.setFontScale(activeScale);
        lblHighscores.setFontScale(inactiveScale);
        lblExit.setFontScale(inactiveScale);

        table.add(lblStartGame).expandX().center();
        table.row();
        table.add(lblHighscores).expandX().center().padTop(10);
        table.row();
        table.add(lblExit).expandX().center().padTop(10);

        // table.setDebug(true);
        stage.addActor(table);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        if (inputAllowed)
            handleInput();

        stage.act(delta);
        backgroundStars.update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        backgroundStars.render(batch);
        batch.end();

        stage.getViewport().apply();
        stage.draw();
    }

    private void handleInput() {
        boolean changed = false;
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            selectedLabel = (selectedLabel + 1) % 3;
            changed = true;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            selectedLabel = (selectedLabel - 1) % 3;
            if(selectedLabel < 0)
                selectedLabel = 2;
            changed = true;
        }
        if (changed) {
            System.out.println("" + (lblStartGame.getStyle().equals(lblHighscores.getStyle())));
            switch (selectedLabel) {
                case 0:
                    //lblStartGame.setStyle(activeStyle);
                    lblStartGame.setFontScale(activeScale);
                    //lblHighscores.setStyle(inactiveStyle);
                    lblStartGame.getStyle().fontColor.a = 0;
                    lblHighscores.setFontScale(inactiveScale);
                    //lblExit.setStyle(inactiveStyle);
                    lblExit.setFontScale(inactiveScale);
                    break;
                case 1:
//                    lblStartGame.setStyle(inactiveStyle);
                    lblStartGame.setFontScale(inactiveScale);
//                    lblHighscores.setStyle(activeStyle);
                    lblHighscores.setFontScale(activeScale);
//                    lblExit.setStyle(inactiveStyle);
                    lblExit.setFontScale(inactiveScale);
                    break;
                case 2:
//                    lblStartGame.setStyle(inactiveStyle);
                    lblStartGame.setFontScale(inactiveScale);
//                    lblHighscores.setStyle(inactiveStyle);
                    lblHighscores.setFontScale(inactiveScale);
//                    lblExit.setStyle(activeStyle);
                    lblExit.setFontScale(activeScale);
                    break;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            inputAllowed = false;
            stage.addAction(Actions.repeat(19, Actions.run(new Runnable() {
                @Override
                public void run() {
                    float speed = 42;
                    //lblTitle.setPosition(lblTitle.getX(), lblTitle.getY() + speed);
                    lblTitle.getStyle().fontColor.a -= (10 / 100f);
                    lblStartGame.setPosition(lblStartGame.getX() + speed, lblStartGame.getY());
                    lblHighscores.setPosition(lblHighscores.getX() - speed, lblHighscores.getY());
                    lblExit.setPosition(lblExit.getX(), lblExit.getY() - speed);
                }
            })));


            stage.addAction(Actions.after(Actions.run(new Runnable() {
                @Override
                public void run() {
                    theGame.setScreen(new ClassicGameScreen(batch, backgroundStars));
                }
            })));
        }
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