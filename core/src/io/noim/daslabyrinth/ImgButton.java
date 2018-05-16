package io.noim.daslabyrinth;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by nilsbergmann on 28.07.16.
 */
public class ImgButton {

    protected Texture tex;
    protected TextureRegion texR;
    protected Vector2 vec;
    protected int height;
    protected int width;
    protected GameField gf;
    protected int shouldX;
    protected int shouldY;
    protected Direction direction;

    public ImgButton(Texture tex, Vector2 vec, int height, int width, Direction direction) {
        this.tex = tex;
        this.texR = new TextureRegion(this.tex);
        this.vec = vec;
        this.height = height;
        this.width = width;
        this.direction = direction;
    }

    public void draw(SpriteBatch batch) {
        switch (this.direction) {
            case Down:
                batch.draw(this.texR, this.vec.x, this.vec.y, this.width / 2, this.height / 2, this.width, this.height, 1, 1, -90.0F);
                break;
            case Up:
                batch.draw(this.texR, this.vec.x, this.vec.y, this.width / 2, this.height / 2, this.width, this.height, 1, 1, 90.0F);
                break;
            case Left:
                batch.draw(this.texR, this.vec.x, this.vec.y, this.width / 2, this.height / 2, this.width, this.height, 1, 1, -180.0F);
                break;
            case Right:
                batch.draw(this.texR, this.vec.x, this.vec.y, this.width / 2, this.height / 2, this.width, this.height, 1, 1, 0F);
                break;
        }
    }

    public boolean isClicked() {
        Vector3 t = new Vector3();
        t.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        Playground.camera.unproject(t);
        return t.x >= this.vec.x - 10 && t.x <= this.vec.x + this.width + 10 && t.y >= this.vec.y - 10 && t.y <= this.vec.y + this.height + 10;
    }

    public GameField[][] move(GameField[][] board) {
        GameField first;
        switch (this.direction) {
            case Down:
                first = board[this.shouldX - 1][board[0].length - 1];

                for (int i = board[0].length - 1; i > 0; i--) {
                    board[this.shouldX - 1][i] = board[this.shouldX - 1][i - 1];
                }

                board[this.shouldX - 1][0] = Playground.newGF;

                Playground.newGF = first;
                break;
            case Up:
                first = board[this.shouldX - 1][0];

                for (int i = 0; i < board[0].length - 1; i++) {
                    board[this.shouldX - 1][i] = board[this.shouldX - 1][i + 1];
                }

                board[this.shouldX - 1][board[0].length - 1] = Playground.newGF;

                Playground.newGF = first;
                break;
            case Left:
                first = board[board.length - 1][this.shouldY - 1];

                for (int i = board.length - 1; i > 0; i--) {
                    board[i][this.shouldY - 1] = board[i - 1][this.shouldY - 1];
                }

                board[0][this.shouldY - 1] = Playground.newGF;


                Playground.newGF = first;
                break;
            case Right:
                first = board[0][this.shouldY - 1];

                for (int i = 0; i < board.length - 1; i++) {
                    board[i][this.shouldY - 1] = board[i + 1][this.shouldY - 1];
                }

                board[board.length - 1][this.shouldY - 1] = Playground.newGF;


                Playground.newGF = first;
                break;
        }
        return board;
    }
}
