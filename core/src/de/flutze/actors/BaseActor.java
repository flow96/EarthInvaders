package de.flutze.actors;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BaseActor extends Actor {


    protected Rectangle rectangle;
    protected int rectOffset = 2;


    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        setOrigin(width / 2f, height / 2f);
        rectangle = new Rectangle(getX() + rectOffset, getY() + rectOffset, width - 2 * rectOffset, height - 2 * rectOffset);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        rectangle.setPosition(x + rectOffset, y + rectOffset);
    }

    public Vector2 getPosition(){
        return new Vector2(getX() + getOriginX(), getY() + getOriginY());
    }

    protected void setRectOffset(int o){
        this.rectOffset = o;
        this.rectangle = new Rectangle(getX() + rectOffset, getY() + rectOffset, getWidth() - 2 * rectOffset, getHeight() - 2 * rectOffset);
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}
