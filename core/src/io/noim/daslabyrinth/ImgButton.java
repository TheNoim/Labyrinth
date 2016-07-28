package io.noim.daslabyrinth;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by nilsbergmann on 28.07.16.
 */
public class ImgButton {

    protected Texture tex;
    protected TextureRegion texr;
    protected Vector2 vec;
    protected float rot;
    protected SpriteBatch batch;
    protected int height;
    protected int width;
    protected GameField gf;
    protected int shouldx;
    protected int shouldy;
    protected boolean fromx;

    public ImgButton(Texture tex, Vector2 vec, float rot, SpriteBatch batch, int height, int width, boolean fromx){
        this.tex = tex;
        this.texr = new TextureRegion(this.tex);
        this.vec = vec;
        this.rot = rot;
        this.batch = batch;
        this.height = height;
        this.width = width;
        this.fromx = fromx;
    }

    public void draw(){
        float x = this.vec.x;
        float y = this.vec.y;
        /*
        Matrix4 orgMatrix = new Matrix4();
        Matrix4 rotMatrix = new Matrix4();
        rotMatrix.translate(y, x, 0);
        rotMatrix.translate(this.height / 2, this.width / 2, 0);
        rotMatrix.rotate(0, 0, 1, this.rot);
        rotMatrix.translate(-this.height / 2, -this.width / 2, 0);
        batch.setTransformMatrix(rotMatrix);
        batch.draw(this.texr, 0, 0, this.height, this.width);
        batch.setTransformMatrix(orgMatrix);
        */
        batch.draw(this.texr, x, y, this.width / 2, this.height / 2, this.width, this.height, 1,1, this.rot);
    }

    public boolean isClicked(){
        Vector3 t = new Vector3();
        t.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        Playground.camera.unproject(t);
        if (t.x >= this.vec.x && t.x <= this.vec.x + this.width && t.y >= this.vec.y && t.y <= this.vec.y + this.height){
            return true;
        }
        return false;
    }
}
