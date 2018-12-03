package io.noim.daslabyrinth;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
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
    double percentHeight = 0.1;
    int startX;
    int heightAndWidthPerField;
    int halffinalprozent;
    GameField newGF;
    Array<GameField> gameFields = new Array<GameField>(this.playgroundWidth * this.playgroundHeight);
    private Music music;
    private Sound moveSound;
    private Texture background;
    private Matrix4 originalMatrix = new Matrix4();
    private Array<ImgButton> imgButtons = new Array<ImgButton>(2 * this.playgroundWidth + 2 * this.playgroundHeight);
    private Texture arrow = new Texture("pfeil.gif");
    private Texture rotateArrow = new Texture("rotate.png");
    private Texture treasure_min = new Texture("treasure.png");
    private Texture treasure_max = new Texture("treasure2.png");
    private int treasureCount = 0;

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
        halffinalprozent = (int) Math.round(screenWidth * percentHeight / 2F);
        heightAndWidthPerField = (int) Math.round((screenWidth - screenWidth * percentHeight) / 4F);
        startX = Math.round((screenHeight - heightAndWidthPerField * 5F) / 1.1F);
        if (this.gameFields.size != this.playgroundWidth * this.playgroundHeight) {
            generateRandomField();
            int typeNewGF = MathUtils.random(0, 3);
            if (this.treasureCount < maxTreasureAmount) {
                int rnd3 = MathUtils.random(0, 100);
                if (rnd3 < 20) {
                    newGF = new GameField(cards[typeNewGF], 0, 0, typeNewGF, MathUtils.random(0, 3));
                    int rnd2 = MathUtils.random(0, 100);
                    if (rnd2 > 20) {
                        newGF.addTreasure(new Treasure(treasure_min, 3));
                    } else {
                        newGF.addTreasure(new Treasure(treasure_max, 5));
                    }
                } else {
                    newGF = new GameField(cards[typeNewGF], 0, 0, typeNewGF, MathUtils.random(0, 3));
                }
            } else {
                newGF = new GameField(cards[typeNewGF], 0, 0, typeNewGF, MathUtils.random(0, 3));
            }
        }

        if (this.imgButtons.size != 2 * this.playgroundWidth + 2 * this.playgroundHeight) {
            // up
            for (int j = 0; j < gameFields.size; ++j) {
                if (gameFields.get(j).y == 5) {
                    Vector2 v = new Vector2();
                    ImgButton bt = new ImgButton(arrow, v, heightAndWidthPerField / 4, heightAndWidthPerField / 4, ImgButton.Direction.Up);
                    bt.gf = gameFields.get(j);
                    bt.shouldX = gameFields.get(j).x;
                    bt.shouldY = gameFields.get(j).y;
                    imgButtons.add(bt);
                }
            }

            // down
            for (int j = 0; j < gameFields.size; ++j) {
                if (gameFields.get(j).y == 1) {
                    Vector2 v = new Vector2();
                    v.x = gameFields.get(j).posX + heightAndWidthPerField / 2f;
                    v.y = gameFields.get(j).posY - 20;
                    ImgButton bt = new ImgButton(arrow, v, heightAndWidthPerField / 4, heightAndWidthPerField / 4, ImgButton.Direction.Down);
                    bt.gf = gameFields.get(j);
                    bt.shouldX = gameFields.get(j).x;
                    bt.shouldY = gameFields.get(j).y;
                    imgButtons.add(bt);
                }
            }

            // left
            for (int j = 0; j < gameFields.size; ++j) {
                if (gameFields.get(j).x == 1) {
                    Vector2 v = new Vector2();
                    ImgButton bt = new ImgButton(arrow, v, heightAndWidthPerField / 4, heightAndWidthPerField / 4, ImgButton.Direction.Left);
                    bt.gf = gameFields.get(j);
                    bt.shouldX = gameFields.get(j).x;
                    bt.shouldY = gameFields.get(j).y;
                    imgButtons.add(bt);
                }
            }

            // right
            for (int j = 0; j < gameFields.size; ++j) {
                if (gameFields.get(j).x == 4) {
                    Vector2 v = new Vector2();
                    ImgButton bt = new ImgButton(arrow, v, heightAndWidthPerField / 4, heightAndWidthPerField / 4, ImgButton.Direction.Right);
                    bt.gf = gameFields.get(j);
                    bt.shouldX = gameFields.get(j).x;
                    bt.shouldY = gameFields.get(j).y;
                    imgButtons.add(bt);
                }
            }
        }
        if (this.main.playerManager.players.size < 2) {
            this.main.playerManager.setPreviousScreen(this);
            this.main.setScreen(this.main.playerManager);
        }

        screenHeight = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();
        DasLabyrinth.pref.flush();
        DasLabyrinth.playSounds = DasLabyrinth.pref.getBoolean("Sounds", true);
        DasLabyrinth.vibration = DasLabyrinth.pref.getBoolean("Vibration", true);
        music.setLooping(true);
        if (DasLabyrinth.pref.getBoolean("Music", true)) {
            music.play();
        }
    }

    @Override
    void draw(SpriteBatch batch) {
        batch.draw(background, 0, 0, screenWidth, screenHeight);

        for (int i = 0; i < gameFields.size; ++i) {
            gameFields.get(i).posX = (gameFields.get(i).x - 1) * heightAndWidthPerField + halffinalprozent;
            gameFields.get(i).posY = (gameFields.get(i).y - 1) * heightAndWidthPerField + startX;
            Matrix4 rotMatrix = new Matrix4();
            rotMatrix.translate(gameFields.get(i).posX, gameFields.get(i).posY, 0);
            rotMatrix.translate(heightAndWidthPerField / 2f, heightAndWidthPerField / 2f, 0);
            rotMatrix.rotate(0, 0, 1, 90.0f * gameFields.get(i).facing);
            rotMatrix.translate(-heightAndWidthPerField / 2f, -heightAndWidthPerField / 2f, 0);
            batch.setTransformMatrix(rotMatrix);
            batch.draw(gameFields.get(i).fieldTextureRegion, 0, 0, heightAndWidthPerField, heightAndWidthPerField);
            batch.setTransformMatrix(originalMatrix);

            if (gameFields.get(i).hasTreasure()) {
                gameFields.get(i).getTreasure().position.x = gameFields.get(i).posX + heightAndWidthPerField / 4f;
                gameFields.get(i).getTreasure().position.y = gameFields.get(i).posY + heightAndWidthPerField / 4f;
                batch.draw(gameFields.get(i).getTreasure().textureRegion, gameFields.get(i).getTreasure().position.x, gameFields.get(i).getTreasure().position.y, heightAndWidthPerField / 2f, heightAndWidthPerField / 2f);
            }
        }
        int dd = Math.round(heightAndWidthPerField * 1.5F);
        if (newGF != null) {
            Matrix4 rotMatrix = new Matrix4();
            rotMatrix.translate(halffinalprozent, startX - dd, 0);
            rotMatrix.translate(heightAndWidthPerField / 2f, heightAndWidthPerField / 2f, 0);
            rotMatrix.rotate(0, 0, 1, 90.0f * newGF.facing);
            rotMatrix.translate(-heightAndWidthPerField / 2f, -heightAndWidthPerField / 2f, 0);
            batch.setTransformMatrix(rotMatrix);
            batch.draw(newGF.fieldTextureRegion, 0, 0, heightAndWidthPerField, heightAndWidthPerField);
            if (newGF.hasTreasure()) {
                rotMatrix.setToRotation(0, 0, 1, 0.0F);
                rotMatrix.translate(halffinalprozent, startX - dd, 0);
                batch.setTransformMatrix(rotMatrix);
                newGF.getTreasure().position.x = halffinalprozent + heightAndWidthPerField / 4f;
                newGF.getTreasure().position.y = startX + heightAndWidthPerField / 4f;
                batch.draw(newGF.getTreasure().textureRegion, newGF.x + heightAndWidthPerField / 4f, newGF.y + heightAndWidthPerField / 4f, heightAndWidthPerField / 2f, heightAndWidthPerField / 2f);
            }
            batch.setTransformMatrix(originalMatrix);
        }
        for (Player player : this.main.playerManager.players) {
            if (player.currentField.equals(this.newGF)) {
                batch.draw(player.getFigure(), halffinalprozent + heightAndWidthPerField / 4f, heightAndWidthPerField * 1.5F, heightAndWidthPerField / 2f, heightAndWidthPerField / 2f);
            } else {
                batch.draw(player.getFigure(), (player.currentField.x - 1) * heightAndWidthPerField + halffinalprozent + heightAndWidthPerField / 4, (player.currentField.y - 1) * heightAndWidthPerField + startX + heightAndWidthPerField / 4, heightAndWidthPerField / 2f, heightAndWidthPerField / 2f);
            }
        }
        for (int i = 0; i < imgButtons.size; ++i) {
            imgButtons.get(i).draw(batch);
        }
        batch.draw(rotateArrow, 2 * halffinalprozent + heightAndWidthPerField, startX - Math.round(heightAndWidthPerField * 1.25), heightAndWidthPerField / 2f, heightAndWidthPerField / 2f);
    }

    @Override
    void update() {
        for (int i = 0; i < imgButtons.size; ++i) {
            if (imgButtons.get(i).gf.x != imgButtons.get(i).shouldX || imgButtons.get(i).gf.y != imgButtons.get(i).shouldY) {
                for (int j = 0; j < gameFields.size; ++j) {
                    if (gameFields.get(j).x == imgButtons.get(i).shouldX && gameFields.get(j).y == imgButtons.get(i).shouldY) {
                        imgButtons.get(i).gf = gameFields.get(j);
                    }
                }
            }
            switch (imgButtons.get(i).direction) {
                case Down:
                    imgButtons.get(i).position.x = imgButtons.get(i).gf.posX + heightAndWidthPerField / 2f;
                    imgButtons.get(i).position.x = imgButtons.get(i).position.x - imgButtons.get(i).width / 2f;
                    imgButtons.get(i).position.y = imgButtons.get(i).gf.posY - imgButtons.get(i).height;
                    break;
                case Up:
                    imgButtons.get(i).position.x = imgButtons.get(i).gf.posX + heightAndWidthPerField / 2f;
                    imgButtons.get(i).position.x = imgButtons.get(i).position.x - imgButtons.get(i).width / 2f;
                    imgButtons.get(i).position.y = imgButtons.get(i).gf.posY + heightAndWidthPerField;
                    break;
                case Left:
                    imgButtons.get(i).position.x = imgButtons.get(i).gf.posX - imgButtons.get(i).width;
                    imgButtons.get(i).position.y = imgButtons.get(i).gf.posY + heightAndWidthPerField / 2f;
                    imgButtons.get(i).position.y = imgButtons.get(i).position.y - imgButtons.get(i).height;
                    imgButtons.get(i).position.y = imgButtons.get(i).position.y + imgButtons.get(i).height / 2f;
                    break;
                case Right:
                    imgButtons.get(i).position.x = imgButtons.get(i).gf.posX + heightAndWidthPerField;
                    imgButtons.get(i).position.y = imgButtons.get(i).gf.posY;
                    imgButtons.get(i).position.y = imgButtons.get(i).position.y + heightAndWidthPerField / 4f;
                    imgButtons.get(i).position.y = imgButtons.get(i).position.y + imgButtons.get(i).height / 2f;
                    break;
            }
        }
    }

    @Override
    void touch(Vector3 touchPosition) {
        for (GameField gameField : this.gameFields) {
            if (gameField.isClicked(touchPosition, this.heightAndWidthPerField)) {
                this.main.playerManager.moveCurrentPlayer(this, gameField);
            }
        }

        if (touchPosition.x >= (2 * halffinalprozent + heightAndWidthPerField) && touchPosition.x <= (2 * halffinalprozent + heightAndWidthPerField + heightAndWidthPerField / 2) && touchPosition.y >= (startX - (int) Math.round(heightAndWidthPerField * 1.25)) && touchPosition.y <= (startX - (int) Math.round(heightAndWidthPerField * 1.25) + heightAndWidthPerField / 2)) {
            ++newGF.facing;
        } else {
            for (int i = 0; i < imgButtons.size; ++i) {
                if (imgButtons.get(i).isClicked(touchPosition)) {
                    imgButtons.get(i).move(this);
                    if (DasLabyrinth.playSounds) {
                        moveSound.play();
                    }
                }
            }
        }
    }

    private void generateRandomField() {
        gameFields.clear();
        int x = 1;
        int y = 1;
        for (int i = 0; i < this.playgroundHeight * this.playgroundWidth; ++i) {
            int type = MathUtils.random(0, 3);
            GameField gf = new GameField(cards[type], x, y, type, MathUtils.random(0, 3));
            if (MathUtils.randomBoolean(0.15F)) {
                ++this.treasureCount;
                int rnd = MathUtils.random(0, 100);
                if (rnd > 20) {
                    gf.addTreasure(new Treasure(treasure_min, 3));
                } else {
                    gf.addTreasure(new Treasure(treasure_max, 5));
                }
            }
            if (x == 1 && y == 1) {
                this.main.playerManager = new PlayerManager(this.main, gf);
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
        for (int i = 0; i < gameFields.size; ++i) {
            if (gameFields.get(i).hasTreasure()) {
                ++acounter;
                atreasures.add(gameFields.get(i));
            }
        }
        if (acounter > maxTreasureAmount) {
            int b = acounter - maxTreasureAmount;
            Array<GameField> btreasure = new Array<GameField>();
            for (int i = 0; i < b; ++i) {
                btreasure.add(atreasures.get(i));
            }
            for (int i = 0; i < gameFields.size; ++i) {
                for (int ii = 0; ii < btreasure.size; ++ii) {
                    if (gameFields.get(i).equals(btreasure.get(ii))) {
                        gameFields.get(i).removeTreasure();
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
        for (int i = 0; i < b; ++i) {
            int rnd = MathUtils.random(0, gameFields.size - 1);
            if (gameFields.get(rnd).hasTreasure()) {
                makeMoreTreasures(b - i);
            }
            int rnd2 = MathUtils.random(0, 100);
            if (rnd2 > 20) {
                gameFields.get(rnd).addTreasure(new Treasure(treasure_min, 3));
            } else {
                gameFields.get(rnd).addTreasure(new Treasure(treasure_max, 5));
            }
        }
    }

    GameField[][] GamefieldToArray() {
        GameField[][] board = new GameField[this.playgroundWidth][this.playgroundHeight];
        for (GameField field : this.gameFields) {
            board[field.x - 1][field.y - 1] = field;
        }
        return board;
    }

    void ArrayToGamefield(GameField[][] board) {
        gameFields.clear();
        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board[0].length; ++j) {
                board[i][j].x = i + 1;
                board[i][j].y = j + 1;
                gameFields.add(board[i][j]);
            }
        }
    }

    @Override
    public void hide() {
        super.hide();
        music.pause();
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
        }
        return false;
    }
}
