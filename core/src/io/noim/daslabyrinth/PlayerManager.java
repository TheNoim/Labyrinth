package io.noim.daslabyrinth;

import com.badlogic.gdx.utils.Array;

public class PlayerManager {

    public static Array<Player> players = new Array<Player>();

    public static void resetAllScores(){
        for (Player p : players){
            p.setScore(0);
        }
    }
    public static boolean canMove(Player p, GameField nextgamefield){
        GameField current = p.currentfield;
        if (nextgamefield.x > current.x){
            switch (nextgamefield.facing){

            }
        }

        return false;
    }
}
