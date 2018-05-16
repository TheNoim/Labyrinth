package io.noim.daslabyrinth;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Player {


    protected GameField currentField;
    protected Array<Treasure> treasures = new Array<Treasure>();
    protected Vector2 position = new Vector2();
    protected int score;

    public Player(GameField currentField) {
        PlayerManager.players.add(this);
        this.currentField = currentField;
        this.score = 0;
    }

    public void movePlayer(GameField nextgamefield) {
        this.currentField = nextgamefield;
    }

    public void addScore(int s) {
        this.score += s;
        DasLabyrinth.pref.putInteger("Score", +1);
        DasLabyrinth.pref.flush();
    }

    public void setScore(int s) {
        this.score = s;
        DasLabyrinth.pref.putInteger("Score", s);
        DasLabyrinth.pref.flush();
    }
}
