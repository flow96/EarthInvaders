package de.flutze.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
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
    private Enemy[][] enemies;
    private Batch batch;
    private Texture fontTexture;

    private Stage stage;
    private Label lblWave;
    private Table table;
    private int timeOut = 1500;
    private int initLastX, initLastY;
    private boolean initWaveDone;


    public WaveController(Batch batch) {
        this.batch = batch;
        currentWave = 1;
        speed = 20;
        enemies = new Enemy[5][15];
        fontTexture = new Texture("Fonts/" + Const.FONT_NAME + ".png");
        fontTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        initStage();

        showLabel(.5f);
        hideLabel(3.5f);
        initWave(3.3f);
    }

    private void initWave(float delay) {
        enemies = new Enemy[5][16];
        initWaveDone = false;
        initLastX = 0;
        initLastY = 0;
        stage.addAction(Actions.delay(delay, Actions.repeat(40, new Action() {
            @Override
            public boolean act(float delta) {
                enemies[initLastY][initLastX] = new Enemy(new Vector2(initLastX * (23 + 10) + 20, initLastY * (23 + 5) + 400), Vector2.Zero, "Ship1.png");
                initLastX++;
                enemies[initLastY][initLastX] = new Enemy(new Vector2(initLastX * (23 + 10) + 20, initLastY * (23 + 5) + 400), Vector2.Zero, "Ship1.png");
                initLastX++;
                if (initLastX % 16 == 0) {
                    initLastX = 0;
                    initLastY++;
                }
                return true;
            }
        })));
        stage.addAction(Actions.delay(delay, new Action() {
            @Override
            public boolean act(float delta) {
                lblWave.setText("START!");
                return true;
            }
        }));
        stage.addAction(Actions.after(Actions.run(new Runnable() {
            @Override
            public void run() {
                initWaveDone = true;
            }
        })));
    }

    private void initStage() {
        Viewport viewport = new FitViewport(Const.WIDTH, Const.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        Pixmap bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        bgPixmap.setColor(1f, 1f, 1f, .1f);
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
        stage.addActor(table);

        table.addAction(Actions.alpha(0));
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void update(float delta) {
        stage.act(delta);
        if (initWaveDone) {
            boolean changeDirection = false;
            boolean waveFinished = true;
            for (int i = 0; i < enemies.length; i++) {
                for (int j = 0; j < enemies[i].length; j++) {
                    if (enemies[i][j] != null) {
                        waveFinished = false;
                        enemies[i][j].setPosition(enemies[i][j].getX() + speed * delta, enemies[i][j].getY());
                        if (enemies[i][j].getX() >= Const.WIDTH - 20 - enemies[i][j].getWidth() || enemies[i][j].getX() <= 20) {
                            changeDirection = true;
                        }
                    }
                }
            }
            if (changeDirection) {
                if (Math.abs(speed) < 600)
                    speed *= -4.4f;
                else
                    speed *= -1;
                for (int i = 0; i < enemies.length; i++) {
                    for (int j = 0; j < enemies[i].length; j++) {
                        if (enemies[i][j] != null) {
                            enemies[i][j].setPosition(enemies[i][j].getX(), enemies[i][j].getY() - 11);
                        }
                    }
                }
            }
            if (waveFinished) {
                currentWave++;
                showLabel(.5f);
                hideLabel(3.5f);
                initWave(3.3f);
            }
        }
    }

    public void draw() {
        final Color color = stage.getBatch().getColor().cpy();
        stage.getBatch().begin();
        for (int i = 0; i < enemies.length; i++) {
            for (int j = 0; j < enemies[i].length; j++) {
                if (enemies[i][j] != null)
                    enemies[i][j].draw(batch);
            }
        }

        stage.getBatch().end();
        // The table has its own color and applies it to the stage, so we override it again
        stage.draw();
        stage.getBatch().setColor(color);
    }

    private void showLabel(float delay) {
        lblWave.setText("WAVE " + currentWave);
        table.addAction(Actions.delay(delay, Actions.fadeIn(.8f)));

    }

    private void hideLabel(float delay) {
        table.addAction(Actions.delay(delay, Actions.fadeOut(.5f)));
    }

    public void setPaused(boolean b) {
        if (b && table.isVisible()) {
            table.setVisible(false);
        } else if (!b && table.getColor().a != 0) {
            table.setVisible(true);
        }
    }
}
