package io.noim.daslabyrinth;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class PlayerManager {

    Array<Player> players = new Array<Player>();
    private int activePlayer = 0;
    private GameField startField;

    PlayerManager(GameField startField) {
        this.startField = startField;
    }

    void addPlayer(String name, Texture figure) {
        this.players.add(new Player(name, figure, this.startField));
    }

    void moveCurrentPlayer(Playground playground, GameField nextGamefield) {
        if (this.players.get(this.activePlayer).movePlayer(playground, nextGamefield)) {
            this.nextPlayer();
        }
    }

    private void nextPlayer() {
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