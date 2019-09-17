package de.flutze.controls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class InputManager extends InputAdapter {

    private boolean rightDown, leftDown, shooting;
    private Pointer[] pointers = new Pointer[3];

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
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(pointer >= 3)
            return true;

        if(screenX <= Gdx.graphics.getWidth() / 2f) {
            pointers[pointer] = new Pointer(Action.LEFT, pointer);
            leftDown = true;
        }else if(screenX > Gdx.graphics.getWidth() / 2f) {
            pointers[pointer] = new Pointer(Action.RIGHT, pointer);
            rightDown = true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(pointer >= 3)
            return true;
        if(pointers[pointer].action == Action.LEFT)
            leftDown = false;
        else if(pointers[pointer].action == Action.RIGHT)
            rightDown = false;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if(pointer >= 3)
            return true;
        if(screenX <= Gdx.graphics.getWidth() / 2f && pointers[pointer].action == Action.RIGHT) {
            leftDown = true;
            rightDown = false;
            pointers[pointer].action = Action.LEFT;
        }else if(screenX > Gdx.graphics.getWidth() / 2f && pointers[pointer].action == Action.LEFT) {
            leftDown = false;
            rightDown = true;
            pointers[pointer].action = Action.RIGHT;
        }
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


    private class Pointer{
        Action action;
        int pointer;

        Pointer(Action action, int pointer){
            this.action = action;
            this.pointer = pointer;
        }
    }

    enum Action{
        RIGHT,
        LEFT,
        SHOOT
    }
}
