package io.noim.daslabyrinth;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

import java.util.Arrays;

public class GameField {

    private static boolean[][] WAYS = new boolean[][]{
            { /* cross */
                    true, true, true, true /* {can go up, can go right, can gp down, can go left} */
            },

            { /* curve */
                    false, true, true, false
            },

            { /* straight */
                    true, false, true, false
            },

            { /* tcross */
                    true, true, true, false
            },
    };

    protected int x;
    protected int y;
    protected int index;
    protected Texture fieldTexture;
    protected TextureRegion fieldTextureRegion;
    protected boolean hasTreasure;
    protected Treasure treasure;
    protected int type;
    protected int facing;
    protected int posX;
    protected int posY;
    private Vector3 clickVector3 = new Vector3();

    public GameField(Texture fieldTexture, boolean hasTreasure, int x, int y, int index, int type, int facing) {
        this.fieldTexture = fieldTexture;
        this.fieldTextureRegion = new TextureRegion(this.fieldTexture);
        this.hasTreasure = hasTreasure;
        this.x = x;
        this.y = y;
        this.index = index;
        this.type = type;
        this.facing = facing;
    }

    public GameField(Texture fieldTexture, boolean hasTreasure, int x, int y, int index, int type, int facing, Treasure treasure) {
        this.fieldTexture = fieldTexture;
        this.fieldTextureRegion = new TextureRegion(this.fieldTexture);
        this.hasTreasure = hasTreasure;
        this.x = x;
        this.y = y;
        this.index = index;
        this.type = type;
        this.facing = facing;
        this.treasure = treasure;
    }

    public boolean isClicked(int size) {
        clickVector3.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        Playground.camera.unproject(clickVector3);
        return clickVector3.x >= this.posX && clickVector3.x <= this.posX + size && clickVector3.y >= this.posY && clickVector3.y <= this.posY + size;
    }

    /**
     * @param direction 0 => up, 1 => right, 2 => down, 3 => left
     * @return if in this direction is a way where the player can go
     */
    public boolean isWayInDirection(int direction) {
        boolean[] rotatedWays = Arrays.copyOf(WAYS[this.type], WAYS[this.type].length); // get the right array for the card
        // turn the array for each time the card is turned
        for (int h = 0; h < this.facing; h++) {
            boolean first = rotatedWays[0];
            System.arraycopy(rotatedWays, 1, rotatedWays, 0, rotatedWays.length - 1);
            rotatedWays[rotatedWays.length - 1] = first;
        }
        return rotatedWays[direction];
    }

    @Override
    public String toString() {
        return "Type " + this.type + " facing: " + this.facing;
    }
}
