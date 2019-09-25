package de.flutze.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;

import de.flutze.controller.GameController;

public abstract class Hud {


    protected Stage stage;
    protected Texture fontTexture;
    protected GameController controller;
    protected boolean paused;

    public Hud(GameController controller){
        this.controller = controller;
    }


    public void update(float delta){

    }
    public void render(){

    }

    public void setPaused(boolean paused){
        this.paused = paused;
    }
}
