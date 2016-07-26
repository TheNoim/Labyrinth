package io.noim.daslabyrinth;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Treasure {

    protected Texture texture;
    protected TextureRegion textureRegion;
    protected Vector2 position = new Vector2();
    protected int value;

    public Treasure(Texture texture, int value){
        this.value = value;
        this.texture = texture;
        this.textureRegion = new TextureRegion(texture);
    }
}
