package de.flutze.actors;


import com.badlogic.gdx.graphics.g2d.Batch;

import de.flutze.controls.InputManager;


public class Player {

    private PlayerShip ship;
    private InputManager inputManager;

    public Player() {
        ship = new PlayerShip("Ships/Ship1.png");
        inputManager = InputManager.getInstance();
    }

    private void handleInput(float delta){
        if (ship != null) {
            if (inputManager.isLeftDown()) {
                ship.moveLeft();
            }
            if (inputManager.isRightDown()) {
                ship.moveRight();
            }
            if(inputManager.isShooting()){
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
