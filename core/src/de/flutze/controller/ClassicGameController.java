package de.flutze.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

import java.util.ArrayList;
import java.util.List;

import de.flutze.actors.BackgroundStars;
import de.flutze.actors.Bullet;
import de.flutze.actors.Ship;
import de.flutze.hud.ClassicGameHud;
import de.flutze.screens.MainMenuScreen;
import de.flutze.sounds.MusicManager;
import de.flutze.utils.Const;
import de.flutze.utils.OffsetGenerator;
import de.flutze.windows.PauseMenu;

public class ClassicGameController extends GameController {

    private BackgroundStars backgroundStars;
    private OffsetGenerator offsetGenerator;
    private ClassicGameHud hud;
    private Batch batch;
    private Texture earthTexture;
    private WaveController waveController;
    private boolean inputAllowed;
    private boolean gameOver;
    private Action cameraShakeAction;
    private Vector3 camPos;

    public ClassicGameController(Batch batch, BackgroundStars stars, Game game) {
        super(game);
        this.batch = batch;
        this.inputAllowed = false;
        this.gameOver = false;
        this.batch.setColor(255, 255, 255, 1);
        player = new Ship("Ships/Ship1.png");
        earthTexture = new Texture("Earth/Earth1.png");
        hud = new ClassicGameHud(batch, player, this);

        offsetGenerator = new OffsetGenerator(20);
        if (stars == null)
            backgroundStars = new BackgroundStars();
        else
            backgroundStars = stars;

        waveController = new WaveController(batch, this);

        final int runCount = 15;
        player.addAction(Actions.delay(.1f, Actions.sequence(Actions.repeat(runCount, new Action() {
            @Override
            public boolean act(float delta) {
                player.setPosition(player.getX(), player.getY() + (player.POS_Y / runCount));
                return true;
            }
        }), Actions.run(new Runnable() {
            @Override
            public void run() {
                inputAllowed = true;
            }
        }))));
    }

    private void handleInput() {
        if (inputManager.isLeftDown())
            player.moveLeft();
        if (inputManager.isRightDown())
            player.moveRight();
        if (inputManager.isShooting())
            player.shoot();
    }

    @Override
    public void update(float delta) {
        if (inputAllowed && !paused)
            handleInput();

        if (!paused) {
            if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                setPaused(true);
                return;
            }
            backgroundStars.update(delta);
            player.act(delta);
            for (int i = 0; i < otherPlayers.size(); i++) {
                otherPlayers.get(i).act(delta);
            }
            //viewport.getCamera().translate(0, .05f * offsetGenerator.getNext(delta), 0);
            viewport.getCamera().update();
            waveController.update(delta);

            if(cameraShakeAction != null)
                cameraShakeAction.act(delta);
        }
        hud.update(delta);
    }

    @Override
    public void render(float delta) {
        // Apply viewport and camera matrix
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        // Start drawing
        batch.begin();
        backgroundStars.render(batch);
        batch.draw(earthTexture, -200, -8, Const.WIDTH + 350, 300);
        player.draw(batch, 1);
        for (int i = 0; i < otherPlayers.size(); i++) {
            otherPlayers.get(i).act(delta);
        }
        batch.end();

        waveController.draw();

        hud.render();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        hud.resize(width, height);
        waveController.resize(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
        earthTexture.dispose();
    }

    @Override
    public void setPaused(boolean paused) {
        super.setPaused(paused);
        if (paused) {
            batch.setColor(255, 255, 255, .3f);
        } else {
            batch.setColor(255, 255, 255, 1);
        }
        hud.setPaused(paused);
        waveController.setPaused(paused);
    }

    @Override
    public void exitGame() {
        super.exitGame();
        // Reset batch colors
        game.setScreen(new MainMenuScreen(game, batch, backgroundStars));
    }

    @Override
    public void gameOver(){
        gameOver = true;
        game.setScreen(new MainMenuScreen(game, batch, backgroundStars));
        // Todo: notify multiplayer
    }

    @Override
    public void destroyShip(Ship ship) {
        if(ship == player){
            if(player.die()){
                waveController.showGameOver();
            }
        }
    }

    public void shakeCamera(){

    }

}
