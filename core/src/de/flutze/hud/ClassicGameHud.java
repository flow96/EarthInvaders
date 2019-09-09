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

public class ClassicGameHud {

    private Stage stage;
    private Texture fontTexture;
    private int score;
    Label lblScore;

    private final String FONTNAME = "space_font";

    public ClassicGameHud(OrthographicCamera camera, Batch batch){
        Viewport viewport = new FitViewport(800, 640, camera);
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

        lblScore.setFontScale(.4f);

        lblScore.setColor(Color.WHITE);

        table.add(lblScore).expandX().left().padTop(10).padLeft(30);

        // table.setDebug(true);
        stage.addActor(table);
    }

    public void render(float delta){
        stage.act(delta);
        stage.getViewport().apply();
        stage.draw();
    }
    public void resize(int width, int height){
        stage.getViewport().update(width, height, true);
    }
}
