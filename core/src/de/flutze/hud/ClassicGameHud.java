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

import de.flutze.utils.Const;

public class ClassicGameHud {

    private Stage stage;
    private Texture fontTexture;
    private int score;
    Label lblScore;
    Label lblFPS;

    private int[] fps = new int[12];
    private int fpsCounter = 0;
    int avgFps = 0;

    private final String FONTNAME = "space_font";

    public ClassicGameHud(Batch batch){
        Viewport viewport = new FitViewport(Const.WIDTH, Const.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);
        fontTexture = new Texture("Fonts/" + FONTNAME + ".png");
        fontTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        initGameHud();
    }

    private void initGameHud(){
        Table table = new Table();
        table.top();
        table.setFillParent(true);
        lblScore = new Label("SCORE: " + score, new Label.LabelStyle(new BitmapFont(Gdx.files.internal("Fonts/" + FONTNAME + ".fnt"), new TextureRegion(fontTexture)), Color.WHITE));
        lblFPS = new Label("", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("Fonts/" + FONTNAME + ".fnt"), new TextureRegion(fontTexture)), Color.WHITE));

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
    }

    public void render(float delta){
        update(delta);
        stage.getViewport().apply();
        stage.draw();
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
