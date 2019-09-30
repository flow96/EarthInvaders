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
import de.flutze.sounds.MusicManager;
import de.flutze.utils.Const;

public class WaveController {

    private int currentWave;
    private float speed;
    private List<Enemy> enemies;
    private Batch batch;
    private Texture fontTexture;

    private Stage stage;
    private Label lblWave;
    private Table table;
    private int timeOut = 1500;
    private int initLastX, initLastY;
    private boolean initWaveDone;
    private boolean gameOver;
    private boolean changedLastTick;
    private GameController gameController;
    private MusicManager musicManager;


    public WaveController(Batch batch, GameController gameController) {
        this.batch = batch;
        this.gameController = gameController;
        this.musicManager = MusicManager.getInstance();
        gameOver = false;
        changedLastTick = false;
        currentWave = 1;
        enemies = new ArrayList<Enemy>();
        fontTexture = new Texture("Fonts/" + Const.FONT_NAME + ".png");
        fontTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        initStage();

        showLabel(.5f);
        hideLabel(3.5f);
        initWave(3.3f);
    }

    private void initWave(float delay) {
        enemies = new ArrayList<Enemy>();
        initWaveDone = false;
        speed = 20 + currentWave * 2;
        initLastX = 0;
        initLastY = 0;
        stage.addAction(Actions.delay(delay, Actions.repeat(40, new Action() {
            @Override
            public boolean act(float delta) {
                Enemy.EnemyType type = Enemy.EnemyType.SHIP1;
                if (initLastY > 1 && initLastY < 4)
                    type = Enemy.EnemyType.SHIP2;
                if (initLastY > 4)
                    type = Enemy.EnemyType.SHIP3;

                enemies.add(new Enemy(new Vector2(initLastX * (Enemy.WIDTH + 12) + 20, initLastY * (Enemy.HEIGHT + 6) + 400), Vector2.Zero, type));
                initLastX++;
                enemies.add(new Enemy(new Vector2(initLastX * (Enemy.WIDTH + 12) + 20, initLastY * (Enemy.HEIGHT + 6) + 400), Vector2.Zero, type));
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
        if (initWaveDone && !gameOver) {
            boolean changeDirection = false;
            boolean waveFinished = true;
            for (int i = 0; i < enemies.size(); i++) {
                if (enemies.get(i) != null) {
                    waveFinished = false;
                    enemies.get(i).setPosition(enemies.get(i).getX() + speed * delta, enemies.get(i).getY());
                    enemies.get(i).act(delta);
                    if (enemies.get(i).getX() >= Const.WIDTH - 20 - enemies.get(i).getWidth() || enemies.get(i).getX() <= 20) {
                        changeDirection = true;
                    }
                    // Handle collisions
                    for (int j = 0; j < gameController.getPlayer().getBullets().size(); j++) {

                        if (gameController.getPlayer().getBullets().get(j).getRectangle().overlaps(enemies.get(i).getRectangle())) {
                            musicManager.destroyed.play(musicManager.SOUND_VOLUME);
                            gameController.getPlayer().getBullets().remove(j);
                            gameController.increaseScore(enemies.get(i).enemyType.points);
                            enemies.remove(i);
                            i--;
                            break;
                        }
                    }
                }
            }
            if (changeDirection && !changedLastTick) {
                changedLastTick = true;
                if (Math.abs(speed) < 500)
                    speed *= -1.33f;
                else
                    speed *= -1;
                for (int i = 0; i < enemies.size(); i++) {
                    if (enemies.get(i) != null) {
                        enemies.get(i).setPosition(enemies.get(i).getX(), enemies.get(i).getY() - 24);
                        if (enemies.get(i).getY() <= 85) {
                            gameOver = true;
                            lblWave.setText("GAME OVER");
                            showLabel(.2f);
                            hideLabel(3f);
                            stage.addAction(Actions.delay(4f, Actions.run(new Runnable() {
                                @Override
                                public void run() {
                                    gameController.gameOver();
                                }
                            })));
                        }
                    }
                }
            } else
                changedLastTick = false;
            if (waveFinished) {
                currentWave++;
                lblWave.setText("WAVE " + currentWave);
                showLabel(.5f);
                hideLabel(3.5f);
                initWave(3.3f);
            }
        }
    }

    public void draw() {
        final Color color = stage.getBatch().getColor().cpy();
        stage.getBatch().begin();
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i) != null)
                enemies.get(i).draw(batch);
        }

        stage.getBatch().end();
        // The table has its own color and applies it to the stage, so we override it again
        stage.draw();
        stage.getBatch().setColor(color);
    }

    private void showLabel(float delay) {
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

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void createExplosion(Vector2 pos){

    }
}
