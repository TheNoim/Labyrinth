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
    protected boolean reverse;
    protected Richtung rich;

    public ImgButton(Texture tex, Vector2 vec, SpriteBatch batch, int height, int width, Richtung rich){
        this.tex = tex;
        this.texr = new TextureRegion(this.tex);
        this.vec = vec;
        this.batch = batch;
        this.height = height;
        this.width = width;
        this.rich = rich;
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
        if (this.rich == Richtung.Unten){
            batch.draw(this.texr, x, y, this.width / 2, this.height / 2, this.width, this.height, 1,1, -90.0F);
        } else if(this.rich == Richtung.Oben){
            batch.draw(this.texr, x, y, this.width / 2, this.height / 2, this.width, this.height, 1,1, 90.0F);
        } else if(this.rich == Richtung.Links){
            batch.draw(this.texr, x, y, this.width / 2, this.height / 2, this.width, this.height, 1,1, -180.0F);
        } else if(this.rich == Richtung.Rechts){
            batch.draw(this.texr, x, y, this.width / 2, this.height / 2, this.width, this.height, 1,1, 0F);
        }
        //batch.draw(this.texr, x, y, this.width / 2, this.height / 2, this.width, this.height, 1,1, this.rot);
    }

    public boolean isClicked(){
        Vector3 t = new Vector3();
        t.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        Playground.camera.unproject(t);
        if (t.x >= this.vec.x - 10 && t.x <= this.vec.x + this.width + 10 && t.y >= this.vec.y - 10 && t.y <= this.vec.y + this.height + 10){
            return true;
        }
        return false;
    }
    public void move(){
        if (this.rich == Richtung.Unten){
            Functions.moveFields(this.shouldx, 0, true, Playground.newgf, false);
        } else if(this.rich == Richtung.Oben){
            Functions.moveFields(this.shouldx, 0, true, Playground.newgf, true);
        } else if(this.rich == Richtung.Links){
            Functions.moveFields(0, this.shouldy, false, Playground.newgf, false);
        } else if(this.rich == Richtung.Rechts){
            Functions.moveFields(0, this.shouldy, false, Playground.newgf, true);
        } else {
            Functions.moveFields(this.shouldx, this.shouldy, this.fromx, this.gf, this.reverse);
        }
        //Functions.moveFields(this.shouldx, this.shouldy, this.fromx, this.gf, this.reverse);
    }
}
