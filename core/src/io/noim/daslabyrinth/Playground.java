package io.noim.daslabyrinth;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class Playground extends Page {

    public final int minTreasureAmount = 3;
    public final int maxTreasureAmount = 5;
    final int playgroundWidth = 4;
    final int playgroundHeight = 5;
    private final Texture[] cards = new Texture[]{
            new Texture("labyrinth_cross.png"), //0
            new Texture("labyrinth_curve.png"), //1
            new Texture("labyrinth_straight.png"), //2
            new Texture("labyrinth_tcross.png") //3
    };
    public DasLabyrinth main;
    int screenHeight;
    int screenWidth;
    int startX;
    int heightAndWidthPerField;
    int halffinalprozent;
    GameField newGF;
    Array<GameField> gameFields = new Array<GameField>(this.playgroundWidth * this.playgroundHeight);
    int treasureCount = 0;
    private Music music;
    private Sound moveSound;
    private Texture background;
    private Array<ImgButton> imgButtons = new Array<ImgButton>(2 * this.playgroundWidth + 2 * this.playgroundHeight);
    private Texture arrow = new Texture("pfeil.gif");
    private Texture rotateArrow = new Texture("rotate.png");
    private Texture treasure_min = new Texture("treasure.png");
    private Texture treasure_max = new Texture("treasure2.png");
    private Texture selected = new Texture("checkbox.png");

    Playground(final DasLabyrinth main) {
        this.main = main;
        background = new Texture("background.png");
        music = Gdx.audio.newMusic(Gdx.files.internal("Epic Suspense.mp3"));
        moveSound = Gdx.audio.newSound(Gdx.files.internal("move.mp3"));
    }

    void create() {
        DasLabyrinth.whichClass = 1;
        screenHeight = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();
        halffinalprozent = MathUtils.round(screenWidth / 20F);
        GameField.SizeInPixels = heightAndWidthPerField = MathUtils.round((screenWidth - screenWidth * 0.1f) / 4F);
        startX = MathUtils.round((screenHeight - heightAndWidthPerField * 5F) / 1.1F);
        if (this.gameFields.size != this.playgroundWidth * this.playgroundHeight) {
            generateRandomField();
            int typeNewGF = MathUtils.random(0, 3);

            newGF = new GameField(cards[typeNewGF], 1, -1, typeNewGF, (byte) MathUtils.random(0, 3));
            if (this.treasureCount < maxTreasureAmount && (MathUtils.randomBoolean(0.2F))) {
                ++this.treasureCount;
                if (MathUtils.randomBoolean(0.8F)) {
                    newGF.setTreasure(new Treasure(treasure_min, 3));
                } else {
                    newGF.setTreasure(new Treasure(treasure_max, 5));
                }
            }
        }

        if (this.imgButtons.size != 2 * this.playgroundWidth + 2 * this.playgroundHeight) {
            // up
            for (int j = 0; j < gameFields.size; ++j) {
                if (gameFields.get(j).getY() == 5) {
                    ImgButton bt = new ImgButton(arrow, new Vector2(), heightAndWidthPerField / 4, heightAndWidthPerField / 4, ImgButton.Direction.Up);
                    bt.gf = gameFields.get(j);
                    bt.shouldX = gameFields.get(j).getX();
                    bt.shouldY = gameFields.get(j).getY();
                    imgButtons.add(bt);
                }
            }

            // down
            for (int j = 0; j < gameFields.size; ++j) {
                if (gameFields.get(j).getY() == 1) {
                    Vector2 v = new Vector2();
                    v.x = gameFields.get(j).posX + heightAndWidthPerField / 2f;
                    v.y = gameFields.get(j).posY - 20;
                    ImgButton bt = new ImgButton(arrow, v, heightAndWidthPerField / 4, heightAndWidthPerField / 4, ImgButton.Direction.Down);
                    bt.gf = gameFields.get(j);
                    bt.shouldX = gameFields.get(j).getX();
                    bt.shouldY = gameFields.get(j).getY();
                    imgButtons.add(bt);
                }
            }

            // left
            for (int j = 0; j < gameFields.size; ++j) {
                if (gameFields.get(j).getX() == 1) {
                    ImgButton bt = new ImgButton(arrow, new Vector2(), heightAndWidthPerField / 4, heightAndWidthPerField / 4, ImgButton.Direction.Left);
                    bt.gf = gameFields.get(j);
                    bt.shouldX = gameFields.get(j).getX();
                    bt.shouldY = gameFields.get(j).getY();
                    imgButtons.add(bt);
                }
            }

            // right
            for (int j = 0; j < gameFields.size; ++j) {
                if (gameFields.get(j).getX() == 4) {
                    ImgButton bt = new ImgButton(arrow, new Vector2(), heightAndWidthPerField / 4, heightAndWidthPerField / 4, ImgButton.Direction.Right);
                    bt.gf = gameFields.get(j);
                    bt.shouldX = gameFields.get(j).getX();
                    bt.shouldY = gameFields.get(j).getY();
                    imgButtons.add(bt);
                }
            }
        }
        if (this.main.playerManager.players.size < 2) {
            this.main.playerManager.setPreviousScreen(this);
            this.main.setScreen(this.main.playerManager);
        }

        music.setLooping(true);
        if (DasLabyrinth.playMusic) {
            music.play();
        }
    }

    @Override
    void draw(Batch batch) {
        batch.draw(background, 0, 0, screenWidth, screenHeight);

        for (int i = 0; i < gameFields.size; ++i) {
            gameFields.get(i).draw(batch);
        }
        if (newGF != null) {
            this.newGF.draw(batch);
        }
        for (Player player : this.main.playerManager.players) {
            if (this.main.playerManager.getActivePlayer().equals(player)) {
                batch.draw(this.selected, (player.currentField.getX() - 1) * heightAndWidthPerField + halffinalprozent + heightAndWidthPerField / 4, (player.currentField.getY() - 1) * heightAndWidthPerField + startX + heightAndWidthPerField / 4, heightAndWidthPerField / 2f, heightAndWidthPerField / 2f); // TODO make beautiful
            }
            batch.draw(player.getFigure(), (player.currentField.getX() - 1) * heightAndWidthPerField + halffinalprozent + heightAndWidthPerField / 4, (player.currentField.getY() - 1) * heightAndWidthPerField + startX + heightAndWidthPerField / 4, heightAndWidthPerField / 2f, heightAndWidthPerField / 2f);
        }
        for (ImgButton button : this.imgButtons) {
            button.draw(batch);
        }
        batch.draw(rotateArrow, 1.25f * heightAndWidthPerField + MathUtils.round(screenWidth / 20F), -1.75f * heightAndWidthPerField + MathUtils.round((screenHeight - heightAndWidthPerField * 5F) / 1.1F), heightAndWidthPerField / 2f, heightAndWidthPerField / 2f);
    }

    @Override
    void update() {
        for (ImgButton button : this.imgButtons) {
            if (button.gf.getX() != button.shouldX || button.gf.getY() != button.shouldY) {
                for (GameField gameField : this.gameFields) {
                    if (gameField.getX() == button.shouldX && gameField.getY() == button.shouldY) {
                        button.gf = gameField;
						break;
                    }
                }
            }
            switch (button.getDirection()) {
                case Down:
                    button.position.x = button.gf.posX + heightAndWidthPerField / 2f;
                    button.position.x = button.position.x - button.width / 2f;
                    button.position.y = button.gf.posY - button.height;
                    break;
                case Up:
                    button.position.x = button.gf.posX + heightAndWidthPerField / 2f;
                    button.position.x = button.position.x - button.width / 2f;
                    button.position.y = button.gf.posY + heightAndWidthPerField;
                    break;
                case Left:
                    button.position.x = button.gf.posX - button.width;
                    button.position.y = button.gf.posY + heightAndWidthPerField / 2f;
                    button.position.y = button.position.y - button.height;
                    button.position.y = button.position.y + button.height / 2f;
                    break;
                case Right:
                    button.position.x = button.gf.posX + heightAndWidthPerField;
                    button.position.y = button.gf.posY;
                    button.position.y = button.position.y + heightAndWidthPerField / 4f;
                    button.position.y = button.position.y + button.height / 2f;
                    break;
            }
        }
    }

    @Override
    void touch(Vector3 touchPosition) {
        for (GameField gameField : this.gameFields) {
            if (gameField.isClicked(touchPosition, this.heightAndWidthPerField)) {
                this.main.playerManager.moveCurrentPlayer(this, gameField);
				break;
            }
        }

        if (touchPosition.x >= 1.25f * heightAndWidthPerField + MathUtils.round(screenWidth / 20F) &&
                touchPosition.x <= 1.75f * heightAndWidthPerField + MathUtils.round(screenWidth / 20F) &&
                touchPosition.y >= -1.75f * heightAndWidthPerField + MathUtils.round((screenHeight - heightAndWidthPerField * 5F) / 1.1F) &&
                touchPosition.y <= -1.25f * heightAndWidthPerField + MathUtils.round((screenHeight - heightAndWidthPerField * 5F) / 1.1F)) {
            this.newGF.turn();
        } else {
            Player activePlayer = this.main.playerManager.getActivePlayer();
            for (ImgButton button : this.imgButtons) {
                if (button.isClicked(touchPosition) && !activePlayer.movedGamefields()) {
                    button.move(this);
                    activePlayer.setMovedGamefields();
                    if (DasLabyrinth.playSounds) {
                        moveSound.play();
                    }
					break;
                }
            }
        }
    }

    private void generateRandomField() {
        gameFields.clear();
        int x = 1;
        int y = 1;
        boolean firstField = true;
        for (int i = 0; i < this.playgroundHeight * this.playgroundWidth; ++i) {
            int type = MathUtils.random(0, 3);
            GameField gf = new GameField(cards[type], x, y, type, (byte) MathUtils.random(0, 3));
            if (MathUtils.randomBoolean(0.15F)) {
                ++this.treasureCount;
                if (MathUtils.randomBoolean(0.8F)) {
                    gf.setTreasure(new Treasure(treasure_min, 3));
                } else {
                    gf.setTreasure(new Treasure(treasure_max, 5));
                }
            }
            if (firstField) {
                this.main.playerManager = new PlayerManager(this.main, gf);
                firstField = false;
            }
            gameFields.add(gf);
            if (y == this.playgroundHeight) {
                y = 1;
                ++x;
            } else {
                ++y;
            }
        }
        int acounter = 0;
        Array<GameField> atreasures = new Array<GameField>();
        for (GameField gameField : this.gameFields) {
            if (gameField.hasTreasure()) {
                ++acounter;
                atreasures.add(gameField);
            }
        }
        if (acounter > maxTreasureAmount) {
            int b = acounter - maxTreasureAmount;
            Array<GameField> btreasure = new Array<GameField>(b);
            for (int i = 0; i < b; ++i) {
                btreasure.add(atreasures.get(i));
            }
            for (GameField gameField : this.gameFields) {
                for (GameField gameFieldTreasure : btreasure) {
                    if (gameField.equals(gameFieldTreasure)) {
                        --this.treasureCount;
                        gameField.removeTreasure();
                    }
                }
            }
        }
        if (acounter < minTreasureAmount) {
            int b = minTreasureAmount - acounter;
            makeMoreTreasures(b);
        }
    }

    void makeMoreTreasures(int b) {
        Array<GameField> clearFields = new Array<GameField>();
        for (GameField gameField : new Array.ArrayIterator<GameField>(this.gameFields)) {
            if (!gameField.hasTreasure() && !this.main.playerManager.isPlayerOnGamefield(gameField)) {
                clearFields.add(gameField);
            }
        }
        for (int i = 0; i < b; ++i) {
            ++this.treasureCount;
            int rnd = MathUtils.random(0, clearFields.size - 1);
            if (MathUtils.randomBoolean(0.8F)) {
                clearFields.get(rnd).setTreasure(new Treasure(treasure_min, 3));
            } else {
                clearFields.get(rnd).setTreasure(new Treasure(treasure_max, 5));
            }
        }
    }

    GameField[][] GamefieldToArray() {
        GameField[][] board = new GameField[this.playgroundWidth][this.playgroundHeight];
        for (GameField field : this.gameFields) {
            board[field.getX() - 1][field.getY() - 1] = field;
        }
        return board;
    }

    void ArrayToGamefield(GameField[][] board) {
        gameFields.clear();
        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board[0].length; ++j) {
                board[i][j].setX(i + 1);
                board[i][j].setY(j + 1);
                gameFields.add(board[i][j]);
            }
        }
    }

    @Override
    public void hide() {
        super.hide();
        if (music.isPlaying()) {
            music.pause();
        }
    }

    @Override
    public void resume() {
        super.resume();
        if (DasLabyrinth.playMusic) {
            music.play();
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        this.screenHeight = Gdx.graphics.getHeight();
        this.screenWidth = Gdx.graphics.getWidth();
    }

    @Override
    public void dispose() {
        super.dispose();
        this.music.dispose();
        this.moveSound.dispose();
        this.background.dispose();
        this.arrow.dispose();
        this.rotateArrow.dispose();
        for (Texture card : this.cards) {
            card.dispose();
        }
        this.treasure_min.dispose();
        this.treasure_max.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        this.main.click();
        if (keycode == Input.Keys.BACK) {
            main.setScreen(this.main.startMenu);
            return true;
        }
        return false;
    }
}
