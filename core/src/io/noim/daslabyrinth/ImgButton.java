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
    protected TextureRegion texr;
    protected Vector2 vec;
    protected int height;
    protected int width;
    protected GameField gf;
    protected int shouldx;
    protected int shouldy;
    protected Direction direction;

    public ImgButton(Texture tex, Vector2 vec, int height, int width, Direction direction) {
        this.tex = tex;
        this.texr = new TextureRegion(this.tex);
        this.vec = vec;
        this.height = height;
        this.width = width;
        this.direction = direction;
    }

    public void draw(SpriteBatch batch) {
        float x = this.vec.x;
        float y = this.vec.y;
        if (this.direction == Direction.Down) {
            batch.draw(this.texr, x, y, this.width / 2, this.height / 2, this.width, this.height, 1, 1, -90.0F);
        } else if (this.direction == Direction.Up) {
            batch.draw(this.texr, x, y, this.width / 2, this.height / 2, this.width, this.height, 1, 1, 90.0F);
        } else if (this.direction == Direction.Left) {
            batch.draw(this.texr, x, y, this.width / 2, this.height / 2, this.width, this.height, 1, 1, -180.0F);
        } else if (this.direction == Direction.Right) {
            batch.draw(this.texr, x, y, this.width / 2, this.height / 2, this.width, this.height, 1, 1, 0F);
        }
    }

    public boolean isClicked() {
        Vector3 t = new Vector3();
        t.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        Playground.camera.unproject(t);
        return t.x >= this.vec.x - 10 && t.x <= this.vec.x + this.width + 10 && t.y >= this.vec.y - 10 && t.y <= this.vec.y + this.height + 10;
    }

    public void move() {
        GameField[][] board = Functions.GamefieldToArray();
        GameField first;
        switch (this.direction) {
            case Down:
                first = board[this.shouldx - 1][board[0].length - 1];

                for (int i = board[0].length - 1; i > 0; i--) {
                    board[this.shouldx - 1][i] = board[this.shouldx - 1][i - 1];
                }

                board[this.shouldx - 1][0] = Playground.newgf;

                Playground.newgf = first;
                break;
            case Up:
                first = board[this.shouldx - 1][0];

                for (int i = 0; i < board[0].length - 1; i++) {
                    board[this.shouldx - 1][i] = board[this.shouldx - 1][i + 1];
                }

                board[this.shouldx - 1][board[0].length - 1] = Playground.newgf;

                Playground.newgf = first;
                break;
            case Left:
                first = board[board.length - 1][this.shouldy - 1];

                for (int i = board.length - 1; i > 0; i--) {
                    board[i][this.shouldy - 1] = board[i - 1][this.shouldy - 1];
                }

                board[0][this.shouldy - 1] = Playground.newgf;


                Playground.newgf = first;
                break;
            case Right:
                first = board[0][this.shouldy - 1];

                for (int i = 0; i < board.length - 1; i++) {
                    board[i][this.shouldy - 1] = board[i + 1][this.shouldy - 1];
                }

                board[board.length - 1][this.shouldy - 1] = Playground.newgf;


                Playground.newgf = first;
                break;
        }
        Functions.ArrayToGamefield(board);
    }
}
