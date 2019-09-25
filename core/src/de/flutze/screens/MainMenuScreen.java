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
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import de.flutze.actors.BackgroundStars;
import de.flutze.sounds.MusicManager;
import de.flutze.utils.Const;

public class MainMenuScreen implements Screen {

    private Texture fontTexture;
    private OrthographicCamera camera;
    private Stage stage;
    private Game theGame;
    private Label[] labels;
    private int selectedLabel;
    private Label.LabelStyle inactiveStyle, activeStyle;
    private float textScale;
    private BackgroundStars backgroundStars;
    private Batch batch;
    private boolean inputAllowed;
    private MusicManager musicManager;


    public MainMenuScreen(Game theGame, Batch batch) {
        this.theGame = theGame;
        this.batch = batch;
        this.selectedLabel = 0;
        this.inputAllowed = true;
        this.fontTexture = new Texture("Fonts/" + Const.FONT_NAME + ".png");
        this.fontTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.inactiveStyle = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("Fonts/" + Const.FONT_NAME + ".fnt"), new TextureRegion(fontTexture)), Color.GRAY);
        this.activeStyle = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("Fonts/" + Const.FONT_NAME + ".fnt"), new TextureRegion(fontTexture)), Color.WHITE);
        this.textScale = .55f;
        this.backgroundStars = new BackgroundStars();
        this.musicManager = MusicManager.getInstance();
        camera = new OrthographicCamera();
        Viewport viewport = new FitViewport(Const.WIDTH, Const.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        initGameHud();
    }


    private void initGameHud() {
        labels = new Label[5];
        labels[0] = new Label("EARTH INVADERS", activeStyle);
        labels[0].setFontScale(.85f);
        labels[0].setColor(Color.WHITE);


        labels[1] = new Label("START CLASSIC", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("Fonts/" + Const.FONT_NAME + ".fnt"), new TextureRegion(fontTexture)), Color.WHITE));
        labels[2] = new Label("HIGHSCORES", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("Fonts/" + Const.FONT_NAME + ".fnt"), new TextureRegion(fontTexture)), Color.WHITE));
        labels[3] = new Label("CREDITS", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("Fonts/" + Const.FONT_NAME + ".fnt"), new TextureRegion(fontTexture)), Color.WHITE));
        labels[4] = new Label("EXIT", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("Fonts/" + Const.FONT_NAME + ".fnt"), new TextureRegion(fontTexture)), Color.WHITE));

        labels[1].setFontScale(textScale);
        labels[2].setFontScale(textScale);
        labels[3].setFontScale(textScale);
        labels[4].setFontScale(textScale);

        labels[1].setColor(Color.WHITE);
        labels[2].setColor(Color.GRAY);
        labels[3].setColor(Color.GRAY);
        labels[4].setColor(Color.GRAY);

        Table table = new Table();
        table.top();
        table.top().add(labels[0]).expandX().padBottom(95).padTop(10);
        table.row();
        table.center();
        table.setFillParent(true);
        table.add(labels[1]).expandX().center();
        table.row();
        table.add(labels[2]).expandX().center().padTop(10);
        table.row();
        table.add(labels[3]).expandX().center().padTop(10);
        table.row();
        table.add(labels[4]).expandX().center().padTop(10);

        // table.setDebug(true);
        stage.addActor(table);
    }

    @Override
    public void show() {
        MusicManager.getInstance().start();
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
            selectedLabel = (selectedLabel + 1) % (labels.length - 1);
            changed = true;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            selectedLabel = (selectedLabel - 1) % labels.length;
            if (selectedLabel < 0)
                selectedLabel = labels.length - 2;
            changed = true;
        }
        if (changed) {
            musicManager.uiFeedback.play();
            for (int i = 1; i < labels.length; i++) {
                labels[i].setColor(Color.GRAY);
            }
            labels[selectedLabel+1].setColor(Color.WHITE);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (selectedLabel == 0) {
                inputAllowed = false;
                stage.addAction(Actions.repeat(19, Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        float speed = 42;
                        //lblTitle.setPosition(lblTitle.getX(), lblTitle.getY() + speed);
                        Color color = labels[0].getColor();
                        color.a = labels[0].getColor().a - (.1f);
                        labels[0].setColor(color);
                        labels[1].setPosition(labels[1].getX() + speed, labels[1].getY());
                        labels[2].setPosition(labels[2].getX() - speed, labels[2].getY());
                        labels[3].setPosition(labels[3].getX() + speed, labels[3].getY());
                        labels[4].setPosition(labels[4].getX() - speed, labels[4].getY());
                    }
                })));


                stage.addAction(Actions.after(Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        theGame.setScreen(new ClassicGameScreen(batch, backgroundStars, theGame));
                        // TODO: Show Highscore board
                    }
                })));
            } else if (selectedLabel == labels.length - 2) {
                Gdx.app.exit();
            }
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
        fontTexture.dispose();
    }
}
