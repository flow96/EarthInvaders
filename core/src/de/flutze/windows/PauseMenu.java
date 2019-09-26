package de.flutze.windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import de.flutze.sounds.MusicManager;
import de.flutze.utils.Const;

public class PauseMenu extends Table {

    private Label lblContinue, lblExit, lblTitle;
    private Texture fontTexture;
    private int selectedLabel;

    public PauseMenu() {
        fontTexture = new Texture("Fonts/" + Const.FONT_NAME + ".png");
        fontTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        selectedLabel = 0;
        initHud();

        setVisible(false);
    }

    private void initHud() {
        lblTitle = new Label("GAME PAUSED", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("Fonts/" + Const.FONT_NAME + ".fnt"), new TextureRegion(fontTexture)), Color.WHITE));
        lblContinue = new Label("CONTINUE", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("Fonts/" + Const.FONT_NAME + ".fnt"), new TextureRegion(fontTexture)), Color.WHITE));
        lblExit = new Label("EXIT", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("Fonts/" + Const.FONT_NAME + ".fnt"), new TextureRegion(fontTexture)), Color.WHITE));

        lblTitle.setFontScale(.8f);
        lblContinue.setFontScale(.6f);
        lblExit.setFontScale(.6f);

        lblTitle.setColor(Color.WHITE);
        lblContinue.setColor(Color.WHITE);
        lblExit.setColor(Color.GRAY);

        this.center();
        this.setFillParent(true);
        this.add(lblTitle).expandX().padBottom(60);
        this.row();
        this.add(lblContinue).expandX().padBottom(20);
        this.row();
        this.add(lblExit).expandX();
    }

    public void setSelectedLabel(int i) {
        if(selectedLabel != i) {
            MusicManager.getInstance().uiFeedback.play(MusicManager.SOUND_VOLUME);
            if (i < 0) {
                i = 1;
            }

            selectedLabel = i % 2;
            switch (selectedLabel) {
                case 0:
                    lblExit.setColor(Color.GRAY);
                    lblContinue.setColor(Color.WHITE);
                    break;
                case 1:
                    lblExit.setColor(Color.WHITE);
                    lblContinue.setColor(Color.GRAY);
                    break;
            }
        }
    }

    public int getSelectedLabel() {
        return selectedLabel;
    }
}
