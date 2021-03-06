package io.noim.daslabyrinth;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class Treasure implements Disposable {

    protected TextureRegion textureRegion;
    protected Vector2 position = new Vector2();
    protected int value;

    public Treasure(Texture texture, int value) {
        this.value = value;
        this.textureRegion = new TextureRegion(texture);
    }

    @Override
    public void dispose() {
        this.textureRegion.getTexture().dispose();
    }
}
