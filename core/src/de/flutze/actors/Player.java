package de.flutze.actors;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;

import de.flutze.controls.InputManager;


public class Player {

    private PlayerShip ship;


    public Player() {
        ship = new PlayerShip("Ships/Ship1.png");
    }

    private void handleInput(float delta){
        if (ship != null) {
            if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                ship.moveLeft();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                ship.moveRight();
            }
            if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
                ship.shoot();
            }
        }
    }

    public void update(float delta) {
        handleInput(delta);

        ship.act(delta);
    }

    public void render(Batch batch) {
        if (ship != null) {
            ship.draw(batch, 1);
        }
    }

}
