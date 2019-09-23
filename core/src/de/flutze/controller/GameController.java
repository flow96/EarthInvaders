package de.flutze.controller;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.List;

import de.flutze.actors.Ship;
import de.flutze.controls.InputManager;
import de.flutze.network.Client;
import de.flutze.network.Server;
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


    GameController(){
        camera = new OrthographicCamera();
        viewport = new FitViewport(Const.WIDTH, Const.HEIGHT, camera);
        isMultiplayerGame = false;
        otherPlayers = new ArrayList<Ship>();
        inputManager = InputManager.getInstance();
    }


    public void update(float delta){

    }

    public void render(float delta){}

    public void resize(int width, int height){
        viewport.update(width, height, true);
    }

}
