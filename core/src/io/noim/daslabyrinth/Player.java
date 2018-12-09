package io.noim.daslabyrinth;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import java.util.Arrays;

public class Player implements Disposable {

    protected GameField currentField;
    protected Array<Treasure> treasures = new Array<Treasure>();
    protected int score;
    private Texture figure;
    private String name;
    private boolean movedGamefields = false;

    public Player(String name, Texture figure, GameField startField) {
        this.name = name;
        this.figure = figure;
        this.currentField = startField;
        this.score = DasLabyrinth.pref.getInteger("Score" + this.name);
    }

    public Texture getFigure() {
        return figure;
    }

    public String getName() {
        return name;
    }

    void addScore(int scoreToAdd) {
        setScore(this.score + scoreToAdd);
    }

    void setScore(int score) {
        this.score = score;
        DasLabyrinth.pref.putInteger("Score" + this.name, score);
        DasLabyrinth.pref.flush();
    }

    void setMovedGamefields() {
        this.movedGamefields = true;
    }

    boolean movedGamefields() {
        return this.movedGamefields;
    }

    public boolean move(Playground playground, GameField newGameField) {
        if (this.canMove(playground, newGameField)) {
            this.currentField = newGameField;
            this.movedGamefields = false;
            if (newGameField.hasTreasure()) {
                this.treasures.add(newGameField.getTreasure());
                newGameField.removeTreasure();
                --playground.treasureCount;
                playground.main.treasure();
                if (playground.treasureCount < playground.minTreasureAmount) {
                    playground.makeMoreTreasures(MathUtils.random(
                            playground.minTreasureAmount - playground.treasureCount,
                            playground.maxTreasureAmount - playground.treasureCount
                    ));
                } else if (playground.treasureCount < playground.maxTreasureAmount) {
                    playground.makeMoreTreasures(
                            MathUtils.random(0, playground.maxTreasureAmount - playground.treasureCount)
                    );
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Checking if the player can go to the gamefield with Breadth-first search
     *
     * @param playground    Where all gamefields are stored
     * @param nextGamefield The gamefield where the player wants to go
     * @return If the player can go to the gamefield
     */
    private boolean canMove(Playground playground, GameField nextGamefield) {
        if (this.movedGamefields &&
                !this.currentField.equals(playground.newGF) &&
                !this.currentField.equals(nextGamefield)) {
            Array<GameField> visited = new Array<GameField>();

            Array<GameField> frontier = new Array<GameField>();
            frontier.add(this.currentField);

            while (true) {
                Array<GameField> newFrontier = new Array<GameField>();

                for (GameField i : new Array.ArrayIterator<GameField>(frontier)) {
                    if (i == nextGamefield) {
                        return true;
                    }
                    for (GameField newPos : new Array.ArrayIterator<GameField>(playground.gameFields)) {
                        if (!newPos.equals(playground.newGF)) {
                            boolean isNeighbourWithWay = false;
                            if (newPos.getY() == i.getY()) {
                                if (newPos.getX() == i.getX() - 1) {
                                    if (i.isWayInDirection(GameField.Directions.LEFT) &&
                                            newPos.isWayInDirection(GameField.Directions.RIGHT)) {
                                        isNeighbourWithWay = true;
                                    }
                                } else if (newPos.getX() == i.getX() + 1) {
                                    if (i.isWayInDirection(GameField.Directions.RIGHT) &&
                                            newPos.isWayInDirection(GameField.Directions.LEFT)) {
                                        isNeighbourWithWay = true;
                                    }
                                }
                            } else if (newPos.getX() == i.getX()) {
                                if (newPos.getY() == i.getY() - 1) {
                                    if (i.isWayInDirection(GameField.Directions.DOWN) &&
                                            newPos.isWayInDirection(GameField.Directions.UP)) {
                                        isNeighbourWithWay = true;
                                    }
                                } else if (newPos.getY() == i.getY() + 1) {
                                    if (i.isWayInDirection(GameField.Directions.UP) &&
                                            newPos.isWayInDirection(GameField.Directions.DOWN)) {
                                        isNeighbourWithWay = true;
                                    }
                                }
                            }
                            if (isNeighbourWithWay) {
                                if (!visited.contains(newPos, true)) {
                                    newFrontier.add(newPos);
                                    visited.add(newPos);
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
        } else {
            return false;
        }
    }

    @Override
    public void dispose() {
        this.figure.dispose();
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.name, this.currentField});
    }

    @Override
    public boolean equals(Object obj) {
        // is null
        if (obj == null)
            return false;

        // is same object
        if (obj == this)
            return true;

        // is same class name and path
        if (!obj.getClass().toString().equals(this.getClass().toString())) {
            return false;
        }

        // is the same GameField and playername
        Player playerToCompare = (Player) obj;
        return this.name.equals(playerToCompare.name) &&
                this.currentField.equals(playerToCompare.currentField);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
