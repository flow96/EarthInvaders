package de.flutze.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.List;

import de.flutze.actors.Ship;
import de.flutze.controls.InputManager;
import de.flutze.network.Client;
import de.flutze.network.Server;
import de.flutze.sounds.MusicManager;
import de.flutze.utils.Const;

public abstract class GameController {

    // Multiplayer
    protected Server server;
    protected Client client;
    protected boolean isMultiplayerGame;
    protected List<Ship> otherPlayers;

    // Singleplayer
    protected Ship player;
    protected OrthographicCamera camera;
    protected Viewport viewport;
    protected InputManager inputManager;

    // General
    protected boolean paused;
    protected Game game;
    protected MusicManager musicManager;
    protected int score;


    public GameController(Game game){
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(Const.WIDTH, Const.HEIGHT, camera);
        isMultiplayerGame = false;
        otherPlayers = new ArrayList<Ship>();
        inputManager = InputManager.getInstance();
        musicManager = MusicManager.getInstance();
    }


    public void update(float delta){

    }

    public void render(float delta){}

    public void resize(int width, int height){
        viewport.update(width, height, true);
    }

    public void dispose(){
        player.dispose();
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public void exitGame(){

    }

    public Ship getPlayer() {
        return player;
    }

    public List<Ship> getOtherPlayers() {
        return otherPlayers;
    }

    public void gameOver(){}

    public int getScore(){
        return score;
    }

    public void increaseScore(int points){
        score += points;
    }
}
