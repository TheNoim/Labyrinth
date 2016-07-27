package io.noim.daslabyrinth;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class Player {


    protected GameField currentfield;
    protected Array<Treasure> treasures = new Array<Treasure>();
    protected int treasurescount;
    protected Vector3 playervector = new Vector3();
    protected int score;

    Array<Player> players = new Array<Player>();

    public Player(GameField currentfield, int treasurescount){
        PlayerManager.players.add(this);
        this.currentfield = currentfield;
        this.treasurescount = treasurescount;
        this.score = 0;
        players.add(this);
    }

    public void movePlayer(GameField nextgamefield){
        this.currentfield = nextgamefield;
    }
    public void addScore(int s){
        this.score += s;
    }
}
