package io.noim.daslabyrinth;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;

import java.util.Arrays;

public class GameField implements Disposable {

    private final static boolean[][] WAYS = new boolean[][]{
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
    static int SizeInPixels = 0;
    private static Matrix4 originalMatrix = new Matrix4();
    protected int posX;
    protected int posY;
    private Texture fieldTexture;
    private TextureRegion fieldTextureRegion;
    private int type;
    private int x;
    private int y;
    private Treasure treasure;
    private byte facing;
    private Matrix4 rotMatrix;

    GameField(Texture fieldTexture, int x, int y, int type, byte facing) {
        this.fieldTexture = fieldTexture;
        this.fieldTextureRegion = new TextureRegion(this.fieldTexture);
        this.x = x;
        this.y = y;
        this.type = type;
        this.facing = facing;
        changeMatrix();
    }

    GameField(Texture fieldTexture, int x, int y, int type, byte facing, Treasure treasure) {
        this.fieldTexture = fieldTexture;
        this.fieldTextureRegion = new TextureRegion(this.fieldTexture);
        this.x = x;
        this.y = y;
        this.type = type;
        this.facing = facing;
        this.treasure = treasure;
        changeMatrix();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        if (this.x != x) {
            this.x = x;
            changeMatrix();
        }
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        if (this.y != y) {
            this.y = y;
            changeMatrix();
        }
    }

    public Treasure getTreasure() {
        return treasure;
    }

    void setTreasure(Treasure treasure) {
        this.treasure = treasure;
    }

    public boolean hasTreasure() {
        return this.treasure != null;
    }

    void removeTreasure() {
        this.treasure = null;
    }

    void turn() {
        if (this.facing > 2) {
            this.facing = 0;
        } else {
            ++this.facing;
        }
        changeMatrix();
    }

    boolean isClicked(Vector3 clickVector3, int size) {
        return clickVector3.x >= this.posX &&
                clickVector3.x <= this.posX + size &&
                clickVector3.y >= this.posY &&
                clickVector3.y <= this.posY + size;
    }

    /**
     * @param direction 0 => up, 1 => left, 2 => down, 3 => right
     * @return if in this direction is a way where the player can go
     */
    boolean isWayInDirection(int direction) {
        return WAYS[this.type][(direction - this.facing + WAYS[this.type].length) % WAYS[this.type].length];
    }

    /**
     * has to be called every time if the facing or the size or the coordinates are changed
     */
    private void changeMatrix() {
        this.posX = (this.x - 1) * SizeInPixels + MathUtils.round(Gdx.graphics.getWidth() / 20F);
        this.posY = (this.y - 1) * SizeInPixels + MathUtils.round((Gdx.graphics.getHeight() - SizeInPixels * 5F) / 1.1F);
        rotMatrix = new Matrix4();
        rotMatrix.translate(posX + SizeInPixels / 2f, posY + SizeInPixels / 2f, 0);
        rotMatrix.rotate(0, 0, 1, 90.0f * this.facing);
        rotMatrix.translate(-SizeInPixels / 2f, -SizeInPixels / 2f, 0);
    }

    void draw(Batch batch) {
        batch.setTransformMatrix(rotMatrix);
        batch.draw(this.fieldTextureRegion, 0, 0, SizeInPixels, SizeInPixels);
        batch.setTransformMatrix(originalMatrix);

        if (this.hasTreasure()) {
            this.getTreasure().position.x = posX + SizeInPixels / 4f;
            this.getTreasure().position.y = posY + SizeInPixels / 4f;
            batch.draw(this.getTreasure().textureRegion, this.getTreasure().position.x, this.getTreasure().position.y, SizeInPixels / 2f, SizeInPixels / 2f);
        }
    }

    @Override
    public void dispose() {
        this.fieldTexture.dispose();
        if (this.treasure != null)
            this.treasure.dispose();
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.x, this.y, this.type, this.facing});
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!obj.getClass().toString().equals(this.getClass().toString())) {
            return false;
        }
        GameField that = (GameField) obj;
        return this.x == that.x &&
                this.y == that.y &&
                this.type == that.type &&
                this.facing == that.facing;
    }

    @Override
    public String toString() {
        return "X: " + x + "Y: " + y + "Type: " + this.type + " facing: " + this.facing;
    }

    public static class Directions {
        public static final int UP = 0;
        public static final int LEFT = 1;
        public static final int DOWN = 2;
        public static final int RIGHT = 3;
    }
}
