package de.flutze.controls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class InputManager implements InputProcessor {

    private boolean rightDown, leftDown, shooting;

    private static InputManager instance = new InputManager();

    private InputManager() {
        Gdx.input.setInputProcessor(this);
    }

    public static InputManager getInstance() {
        return instance;
    }


    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                leftDown = true;
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                rightDown = true;
                break;
            case Input.Keys.SPACE:
                shooting = true;
                break;
        }


        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                leftDown = false;
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                rightDown = false;
                break;
            case Input.Keys.SPACE:
                shooting = false;
                break;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public boolean isRightDown() {
        return rightDown;
    }

    public boolean isLeftDown() {
        return leftDown;
    }

    public boolean isShooting() {
        return shooting;
    }
}
