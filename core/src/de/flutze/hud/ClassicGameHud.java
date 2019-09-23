package de.flutze.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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

import de.flutze.actors.Ship;
import de.flutze.utils.Const;

public class ClassicGameHud {

    private Stage stage;
    private Texture fontTexture;
    private Texture healthBarInner, healthBarOuter;
    private int score;
    Label lblScore;
    Label lblFPS;

    private int[] fps = new int[12];
    private int fpsCounter = 0;
    int avgFps = 0;

    private Ship player;
    private float bulletBar;

    public ClassicGameHud(Batch batch, Ship player){
        this.player = player;
        Viewport viewport = new FitViewport(Const.WIDTH, Const.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);
        fontTexture = new Texture("Fonts/" + Const.FONT_NAME + ".png");
        fontTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        healthBarInner = new Texture("UI/HealthBarInner.png");
        healthBarOuter = new Texture("UI/HealthBarOuter.png");

        initGameHud();
    }

    private void initGameHud(){
        Table table = new Table();
        table.top();
        table.setFillParent(true);
        lblScore = new Label("SCORE: " + score, new Label.LabelStyle(new BitmapFont(Gdx.files.internal("Fonts/" + Const.FONT_NAME + ".fnt"), new TextureRegion(fontTexture)), Color.WHITE));
        lblFPS = new Label("", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("Fonts/" + Const.FONT_NAME + ".fnt"), new TextureRegion(fontTexture)), Color.WHITE));

        lblScore.setFontScale(.4f);
        lblFPS.setFontScale(.3f);

        lblScore.setColor(Color.WHITE);
        lblFPS.setColor(Color.WHITE);

        table.add(lblScore).expandX().left().padTop(10).padLeft(30);
        table.add(lblFPS).expandX().right().padTop(10).padRight(30);

        // table.setDebug(true);
        stage.addActor(table);
    }

    private void update(float delta){
        calcFPS(delta);
        stage.act(delta);

        bulletBar = (float)(healthBarOuter.getWidth() - 4) / player.maxBullets * (player.maxBullets - player.getBulletsCount());

    }

    public void render(float delta){
        update(delta);
        stage.getViewport().apply();
        stage.draw();
        stage.getBatch().begin();
        stage.getBatch().draw(healthBarOuter, 28, Const.HEIGHT - 55, healthBarOuter.getWidth(), healthBarOuter.getHeight());
        stage.getBatch().draw(healthBarInner, 30, Const.HEIGHT - 53, bulletBar, healthBarInner.getHeight());
        stage.getBatch().end();
    }


    private void calcFPS(float delta){
        fps[fpsCounter] = (int)(1/delta);
        if(fps[fpsCounter] < 29)
            System.out.println("Framedrop:" + fps[fpsCounter]);
        avgFps = 0;
        for (int i = 0; i < fps.length; i++) {
            avgFps += fps[i];
        }
        avgFps /= fps.length;
        fpsCounter = ++fpsCounter % fps.length;

        lblFPS.setText("" + avgFps);
    }
    public void resize(int width, int height){
        stage.getViewport().update(width, height, true);
    }
}
