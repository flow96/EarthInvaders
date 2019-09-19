package de.flutze.utils;

public class OffsetGenerator {


    private int speedRange;
    private float bouncing;
    private float offset;

    public OffsetGenerator(int randomSpeedRange){
        this.speedRange = randomSpeedRange;
        this.bouncing = 0;
        this.offset = 0;
    }


    public float getNext(float delta){
        float speed = (float)Math.random() * speedRange;
        bouncing = (bouncing + delta * speed) % (float)(Math.PI * 4);
        offset = (float)Math.cos(bouncing);
        return offset;
    }

    public float getOffset() {
        return offset;
    }
}
