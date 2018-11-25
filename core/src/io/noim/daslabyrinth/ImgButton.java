package io.noim.daslabyrinth;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by nilsbergmann on 28.07.16.
 */
public class ImgButton implements Disposable {

    public enum Direction {
        Up, Down, Right, Left
    }

    protected Texture tex;
    protected TextureRegion texR;
    protected Vector2 position;
    protected int height;
    protected int width;
    protected GameField gf;
    protected int shouldX;
    protected int shouldY;
    protected Direction direction;

    public ImgButton(Texture tex, Vector2 position, int height, int width, Direction direction) {
        this.tex = tex;
        this.texR = new TextureRegion(this.tex);
        this.position = position;
        this.height = height;
        this.width = width;
        this.direction = direction;
    }

    public void draw(SpriteBatch batch) {
        switch (this.direction) {
            case Down:
                batch.draw(this.texR, this.position.x, this.position.y, this.width / 2f, this.height / 2f, this.width, this.height, 1, 1, -90.0F);
                break;
            case Up:
                batch.draw(this.texR, this.position.x, this.position.y, this.width / 2f, this.height / 2f, this.width, this.height, 1, 1, 90.0F);
                break;
            case Left:
                batch.draw(this.texR, this.position.x, this.position.y, this.width / 2f, this.height / 2f, this.width, this.height, 1, 1, -180.0F);
                break;
            case Right:
                batch.draw(this.texR, this.position.x, this.position.y, this.width / 2f, this.height / 2f, this.width, this.height, 1, 1, 0F);
                break;
        }
    }

    /**
     * @return if the cursor is on the button
     */
    public boolean isClicked(Vector3 t) {
        return t.x >= this.position.x - 10 && t.x <= this.position.x + this.width + 10 && t.y >= this.position.y - 10 && t.y <= this.position.y + this.height + 10;
    }

    /**
     * @param playground The playground with all fields and the new field
     */
    public void move(Playground playground) {
        GameField[][] board = playground.GamefieldToArray();
        GameField first;
        switch (this.direction) {
            case Down:
                first = board[this.shouldX - 1][board[0].length - 1];

                System.arraycopy(board[this.shouldX - 1], 0, board[this.shouldX - 1], 1, board[0].length - 1);

                board[this.shouldX - 1][0] = playground.newGF;

                playground.newGF = first;
                break;
            case Up:
                first = board[this.shouldX - 1][0];

                System.arraycopy(board[this.shouldX - 1], 1, board[this.shouldX - 1], 0, board[0].length - 1);

                board[this.shouldX - 1][board[0].length - 1] = playground.newGF;

                playground.newGF = first;
                break;
            case Left:
                first = board[board.length - 1][this.shouldY - 1];

                for (int i = board.length - 1; i > 0; i--) {
                    board[i][this.shouldY - 1] = board[i - 1][this.shouldY - 1];
                }

                board[0][this.shouldY - 1] = playground.newGF;

                playground.newGF = first;
                break;
            case Right:
                first = board[0][this.shouldY - 1];

                for (int i = 0; i < board.length - 1; i++) {
                    board[i][this.shouldY - 1] = board[i + 1][this.shouldY - 1];
                }

                board[board.length - 1][this.shouldY - 1] = playground.newGF;

                playground.newGF = first;
                break;
        }
        playground.ArrayToGamefield(board);
    }

    @Override
    public void dispose() {
        this.tex.dispose();
    }
}
