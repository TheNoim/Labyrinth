package io.noim.daslabyrinth;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class Player {

    Texture figure;
    String name;
    protected GameField currentField;
    protected Array<Treasure> treasures = new Array<Treasure>();
    protected int score;

    public Player(String name, Texture figure, GameField startField) {
        this.name = name;
        this.figure = figure;
        this.currentField = startField;
        this.score = DasLabyrinth.pref.getInteger("Score" + this.name);
    }

    public void movePlayer(Playground playground, GameField nextGamefield) {
        if (this.canMove(playground, nextGamefield)) {
            this.currentField = nextGamefield;
            if (nextGamefield.hasTreasure) {
                this.treasures.add(nextGamefield.treasure);
                nextGamefield.hasTreasure = false;
                nextGamefield.treasure = null;
                playground.makeMoreTreasures(1);
                DasLabyrinth.click();
            }
        }
    }

    /**
     * Checking if the player can go to the gamefield with Breadth-first search
     *
     * @param playground    Where all gamefields are stored
     * @param nextGamefield The gamefield where the player wants to go
     * @return If the player can go to the gamefield
     */
    private boolean canMove(Playground playground, GameField nextGamefield) {
        Array<GameField> visited = new Array<GameField>();

        Array<GameField> frontier = new Array<GameField>();
        frontier.add(this.currentField);

        while (true) {
            Array<GameField> newFrontier = new Array<GameField>();

            for (GameField i : frontier) {
                if (i == nextGamefield) {
                    return true;
                }
                for (GameField newPos : new Array.ArrayIterator<GameField>(playground.gameFields)) {
                    if (newPos.y == i.y) {
                        if (newPos.x == i.x - 1) {
                            if (i.isWayInDirection(3) && newPos.isWayInDirection(1)) {
                                if (!visited.contains(newPos, true)) {
                                    newFrontier.add(newPos);
                                    visited.add(newPos);
                                }
                            }
                        } else if (newPos.x == i.x + 1) {
                            if (i.isWayInDirection(1) && newPos.isWayInDirection(3)) {
                                if (!visited.contains(newPos, true)) {
                                    newFrontier.add(newPos);
                                    visited.add(newPos);
                                }
                            }
                        }
                    } else if (newPos.x == i.x) {
                        if (newPos.y == i.y - 1) {
                            if (i.isWayInDirection(2) && newPos.isWayInDirection(0)) {
                                if (!visited.contains(newPos, true)) {
                                    newFrontier.add(newPos);
                                    visited.add(newPos);
                                }
                            }
                        } else if (newPos.y == i.y + 1) {
                            if (i.isWayInDirection(0) && newPos.isWayInDirection(2)) {
                                if (!visited.contains(newPos, true)) {
                                    newFrontier.add(newPos);
                                    visited.add(newPos);
                                }
                            }
                        }
                    }
                }
            }
            if (newFrontier.size == 0) {
                return false;
            }
            frontier = newFrontier;

        }
    }

    void addScore(int s) {
        this.score += s;
        DasLabyrinth.pref.putInteger("Score" + this.name, +1);
        DasLabyrinth.pref.flush();
    }

    void setScore(int s) {
        this.score = s;
        DasLabyrinth.pref.putInteger("Score" + this.name, s);
        DasLabyrinth.pref.flush();
    }
}
