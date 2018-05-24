package io.noim.daslabyrinth;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

public class GameField {

    public static class Directions {
        public static final int UP = 0;
        public static final int LEFT = 1;
        public static final int DOWN = 2;
        public static final int RIGHT = 3;
    }

    private static boolean[][] WAYS = new boolean[][]{
            { /* cross */
                    true, true, true, true /* {can go up, can go left, can go down, can go right} */
            },

            { /* curve */
                    false, false, true, true
            },

            { /* straight */
                    true, false, true, false
            },

            { /* tcross */
                    true, false, true, true
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
     * @param direction 0 => up, 1 => left, 2 => down, 3 => right
     * @return if in this direction is a way where the player can go
     */
    public boolean isWayInDirection(int direction) {
        return WAYS[this.type][(direction - this.facing + WAYS[this.type].length) % WAYS[this.type].length];
    }

    @Override
    public String toString() {
        return "Type " + this.type + " facing: " + this.facing;
    }
}
