package io.noim.daslabyrinth;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class Player {


    protected GameField currentfield;
    protected Array<Treasure> treasures = new Array<Treasure>();
    protected int treasurescount;
    protected Vector3 playervector = new Vector3();

    public Player(GameField currentfield, int treasurescount){
        PlayerManager.players.add(this);
        this.currentfield = currentfield;
        this.treasurescount = treasurescount;
    }

    public void movePlayer(GameField nextgamefield){
        this.currentfield = nextgamefield;

    }
}
