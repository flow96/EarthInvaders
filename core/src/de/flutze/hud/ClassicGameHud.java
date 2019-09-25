package de.flutze.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
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

import de.flutze.actors.Ship;
import de.flutze.controller.GameController;
import de.flutze.controls.InputManager;
import de.flutze.screens.MainMenuScreen;
import de.flutze.utils.Const;
import de.flutze.windows.PauseMenu;

public class ClassicGameHud extends Hud {

    private Texture healthBarInner, healthBarOuter;
    private int score;
    private Label lblScore;
    private Label lblFPS;
    private PauseMenu pauseMenu;

    private int[] fps = new int[12];
    private int fpsCounter = 0;
    private int avgFps = 0;

    private Ship player;
    private float bulletBar;
    private InputManager inputManager;

    public ClassicGameHud(Batch batch, Ship player, GameController controller) {
        super(controller);
        this.player = player;
        this.pauseMenu = new PauseMenu();
        this.inputManager = InputManager.getInstance();
        Viewport viewport = new FitViewport(Const.WIDTH, Const.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);
        fontTexture = new Texture("Fonts/" + Const.FONT_NAME + ".png");
        fontTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        healthBarInner = new Texture("UI/HealthBarInner.png");
        healthBarOuter = new Texture("UI/HealthBarOuter.png");

        initGameHud();
    }

    private void initGameHud() {
        Table table = new Table();
        table.top();
        table.setFillParent(true);
        lblScore = new Label("SCORE: " + score, new Label.LabelStyle(new BitmapFont(Gdx.files.internal("Fonts/" + Const.FONT_NAME + ".fnt"), new TextureRegion(fontTexture)), Color.WHITE));
        lblFPS = new Label("", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("Fonts/" + Const.FONT_NAME + ".fnt"), new TextureRegion(fontTexture)), Color.WHITE));

        lblScore.setFontScale(.4f);
        lblFPS.setFontScale(.3f);
        lblFPS.setVisible(false);

        table.add(lblScore).expandX().center().padTop(10);

        // table.setDebug(true);
        stage.addActor(table);

        // Add pause menu to stage
        stage.addActor(pauseMenu);
    }

    private void handleInput(){
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            pauseMenu.setSelectedLabel(pauseMenu.getSelectedLabel() + 1);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            pauseMenu.setSelectedLabel(pauseMenu.getSelectedLabel() - 1);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            switch (pauseMenu.getSelectedLabel()){
                case 0:
                    controller.setPaused(false);
                    break;
                case 1:
                    controller.exitGame();
                    break;
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            controller.setPaused(false);
        }
    }

    @Override
    public void update(float delta) {
        if(paused){
            handleInput();
        }
        // calcFPS(delta);
        stage.act(delta);

        bulletBar = (float) (healthBarOuter.getWidth() - 4) / player.maxBullets * (player.maxBullets - player.getBulletsCount());
    }

    @Override
    public void render() {
        TextureRegion ship = player.getShip();
        stage.getViewport().apply();
        stage.draw();
        stage.getBatch().begin();
        stage.getBatch().draw(healthBarOuter, 28, Const.HEIGHT - 30, healthBarOuter.getWidth(), healthBarOuter.getHeight());
        stage.getBatch().draw(healthBarInner, 30, Const.HEIGHT - 28, bulletBar, healthBarInner.getHeight());
        for (int i = 0; i < player.getLives(); i++) {
            stage.getBatch().draw(ship, Const.WIDTH - 30 - (i * (ship.getRegionWidth() + 14)), Const.HEIGHT - ship.getRegionHeight() - 12, 22, 22);
        }
        stage.getBatch().end();
    }


    private void calcFPS(float delta) {
        fps[fpsCounter] = (int) (1 / delta);
        if (fps[fpsCounter] < 29)
            System.out.println("Framedrop:" + fps[fpsCounter]);
        avgFps = 0;
        for (int i = 0; i < fps.length; i++) {
            avgFps += fps[i];
        }
        avgFps /= fps.length;
        fpsCounter = ++fpsCounter % fps.length;

        lblFPS.setText("" + avgFps);
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void setPaused(boolean paused) {
        super.setPaused(paused);
        pauseMenu.setVisible(paused);
        pauseMenu.setSelectedLabel(0);
        if (paused) {
            lblFPS.setColor(Color.GRAY);
            lblScore.setColor(Color.GRAY);
        } else {
            lblFPS.setColor(Color.WHITE);
            lblScore.setColor(Color.WHITE);
        }
    }
}
