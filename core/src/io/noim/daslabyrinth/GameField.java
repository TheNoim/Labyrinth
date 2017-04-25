package io.noim.daslabyrinth;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameField {

    protected int x;
    protected int y;
    protected int index;
    protected Texture fieldTexture;
    protected TextureRegion fieldTextureRegion;
    protected boolean isTreasure;
    protected Treasure treasure;
    protected int type;
    protected int facing;
    protected int posx;
    protected int posy;

    public GameField(Texture fieldTexture, boolean isTreasure, int x, int y, int index, int type, int facing) {
        this.fieldTexture = fieldTexture;
        this.fieldTextureRegion = new TextureRegion(this.fieldTexture);
        this.isTreasure = isTreasure;
        this.x = x;
        this.y = y;
        this.index = index;
        this.type = type;
        this.facing = facing;
    }

    public GameField(Texture fieldTexture, boolean isTreasure, int x, int y, int index, int type, int facing, Treasure treasure) {
        this.fieldTexture = fieldTexture;
        this.fieldTextureRegion = new TextureRegion(this.fieldTexture);
        this.isTreasure = isTreasure;
        this.x = x;
        this.y = y;
        this.index = index;
        this.type = type;
        this.facing = facing;
        this.treasure = treasure;
    }
}
