package de.flutze.utils;

import java.io.Serializable;
import java.util.Date;

public class Highscore implements Serializable {
    private Date date;
    private final long score;

    public Highscore(long score){
        this.score = score;
        this.date = new Date();
    }

    public long getScore() {
        return score;
    }

    public Date getDate() {
        return date;
    }
}
