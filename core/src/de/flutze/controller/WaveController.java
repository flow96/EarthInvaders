package de.flutze.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.List;

import de.flutze.actors.Enemy;
import de.flutze.utils.Const;

public class WaveController {

    private int currentWave;
    private float speed;
    private boolean movingRight;
    private List<Enemy> enemies;
    private Batch batch;
    private Texture fontTexture;

    private Stage stage;
    private Label lblWave;
    private Table table;
    private int timeOut = 1500;


    public WaveController(Batch batch){
        this.batch = batch;
        currentWave = 1;
        movingRight = true;
        speed = 20;
        enemies = new ArrayList<Enemy>();
        fontTexture = new Texture("Fonts/" + Const.FONT_NAME + ".png");
        fontTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        initStage();

        stage.addAction(Actions.delay(.6f, showWaveTable));
        stage.addAction(Actions.delay(3.5f, hideWaveTable));
    }

    private void initStage(){
        Viewport viewport = new FitViewport(Const.WIDTH, Const.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        Pixmap bgPixmap = new Pixmap(1,1, Pixmap.Format.RGBA8888);
        bgPixmap.setColor(1f, 1f, 1f, .15f);
        bgPixmap.fill();
        TextureRegionDrawable textureRegionDrawableBg = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));


        table = new Table();
        table.setSize(Const.WIDTH, 100);
        table.setPosition(0, Const.HEIGHT / 2f - 50);
        table.setBackground(textureRegionDrawableBg);

        lblWave = new Label("WAVE " + currentWave, new Label.LabelStyle(new BitmapFont(Gdx.files.internal("Fonts/" + Const.FONT_NAME + ".fnt"), new TextureRegion(fontTexture)), Color.WHITE));
        lblWave.setColor(Color.WHITE);
        lblWave.setFontScale(.6f);

        table.add(lblWave).center().expandX();
        table.setVisible(false);
        stage.addActor(table);
    }

    public void resize(int width, int height){
        stage.getViewport().update(width, height, true);
    }

    public void update(float delta){
        stage.act(delta);
    }

    public void draw(){
        stage.draw();
    }

    private Action showWaveTable = new Action() {
        @Override
        public boolean act(float delta) {
            table.setVisible(true);
            return true;
        }
    };
    private Action hideWaveTable = new Action() {
        @Override
        public boolean act(float delta) {
            table.setVisible(false);
            return true;
        }
    };
}
