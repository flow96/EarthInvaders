package de.flutze.controller;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import de.flutze.actors.BackgroundStars;
import de.flutze.actors.Ship;
import de.flutze.hud.ClassicGameHud;
import de.flutze.utils.OffsetGenerator;

public class ClassicGameController extends GameController{

    private BackgroundStars backgroundStars;
    private OffsetGenerator offsetGenerator;
    private ClassicGameHud hud;
    private Batch batch;


    public ClassicGameController(Batch batch, BackgroundStars stars){
        super();
        this.batch = batch;
        player = new Ship("Ships/Ship1.png");
        hud = new ClassicGameHud(batch, player);

        offsetGenerator = new OffsetGenerator(2);
        if(stars == null)
            backgroundStars = new BackgroundStars();
        else
            backgroundStars = stars;

        final int runCount = 14;
        player.addAction(Actions.repeat(runCount, new Action() {
            @Override
            public boolean act(float delta) {
                player.setPosition(player.getX(), player.getY() + (player.POS_Y / runCount));
                return true;
            }
        }));
    }

    private void handleInput(){
        if(inputManager.isLeftDown())
            player.moveLeft();
        if(inputManager.isRightDown())
            player.moveRight();
        if(inputManager.isShooting())
            player.shoot();
    }

    @Override
    public void update(float delta) {
        if(!player.hasActions())
            handleInput();

        backgroundStars.update(delta);
        player.act(delta);
        //viewport.getCamera().translate(0, .05f * offsetGenerator.getNext(delta), 0);
        viewport.getCamera().update();
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
        //batch.draw(earthTexture, -200, -8, Const.WIDTH + 350, 300);
        player.draw(batch, 1);
        batch.end();

        // Draw HUD on top with different viewport (needs to be done last!)
        hud.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        hud.resize(width, height);
    }
}
