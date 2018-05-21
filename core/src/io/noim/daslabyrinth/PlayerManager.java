package io.noim.daslabyrinth;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class PlayerManager {

    Array<Player> players = new Array<Player>();
    private int activePlayer = 0;

    public PlayerManager(GameField startField) {
        Texture[] figures = new Texture[]{
                new Texture("figure1.png"),
                new Texture("figure2.png"),
                new Texture("figure3.png"),
                new Texture("figure4.png"),
        };
        this.players.add(new Player("Simon", figures[0], startField));
    }

    void moveCurrentPlayer(Playground playground, GameField nextGamefield) {
        this.players.get(this.activePlayer).movePlayer(playground, nextGamefield);
        this.nextPlayer();
    }

    void nextPlayer() {
        if (this.activePlayer == this.players.size - 1) {
            this.activePlayer = 0;
        } else {
            this.activePlayer++;
        }
    }

    public void resetAllScores() {
        for (int i = 0; i < players.size; i++) {
            players.get(i).treasures.clear();
            players.get(i).setScore(0);
        }
    }
}