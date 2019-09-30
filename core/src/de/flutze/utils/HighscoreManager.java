package de.flutze.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class HighscoreManager {

    private Highscore[] scores = new Highscore[10];
    private final File scoreFile;

    private static HighscoreManager instance;

    public static HighscoreManager getInstance(){
        if(instance == null)
            instance = new HighscoreManager();
        return instance;
    }

    private HighscoreManager(){
        scoreFile = new File("./data.bin");
        loadScores();
    }

    private void loadScores(){
        if(!scoreFile.exists()) {
            for (int i = 0; i < scores.length; i++) {
                scores[i] = new Highscore(0);
            }
            saveScores();
        }else{
            try {
                FileInputStream fileInput = new FileInputStream(scoreFile);
                ObjectInputStream in = new ObjectInputStream(fileInput);
                scores = (Highscore[]) in.readObject();
                in.close();
                fileInput.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveScores(){
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(scoreFile);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(scores);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public boolean isTopTenScore(long score){
        for (int i = 0; i < scores.length; i++) {
            if(score > scores[i].getScore()) {
                for (int j = scores.length - 1; j > i; j--) {
                    scores[j] = scores[j - 1];
                }
                scores[i] = new Highscore(score);
                saveScores();
                return true;
            }
        }
        return false;
    }

    public boolean isNewHighscore(long score){
        if(score > scores[0].getScore()){
            isTopTenScore(score);
            return true;
        }
        return false;
    }

    public Highscore[] getScores() {
        return scores;
    }
}
