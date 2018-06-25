package io.noim.daslabyrinth;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class Playground implements Screen, InputProcessor {

    public DasLabyrinth main;

    public Playground(final DasLabyrinth main) {
        this.main = main;
        background = new Texture("background.png");
        music = Gdx.audio.newMusic(Gdx.files.internal("Epic Suspense.mp3"));
        moveSound = Gdx.audio.newSound(Gdx.files.internal("move.mp3"));
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
    }

    private SpriteBatch batch;
    final int playgroundWidth = 4;
    final int playgroundHeight = 5;
    int screenWidth;
    int screenHeight;
    double percentHeight = 0.1;
    static Music music;
    Sound moveSound;
    int startX;
    int heightAndWidthPerField;
    int halffinalprozent;
    static OrthographicCamera camera;
    private Vector3 touch = new Vector3();
    private Texture background;
    GameField newGF;
    private Matrix4 originalMatrix = new Matrix4();
    private Array<ImgButton> imgButtons = new Array<ImgButton>(2 * this.playgroundWidth + 2 * this.playgroundHeight);
    Texture arrow = new Texture("pfeil.gif");
    Texture rotateArrow = new Texture("rotate.png");
    final Texture[] cards = new Texture[]{
            new Texture("labyrinth_cross.png"), //0
            new Texture("labyrinth_curve.png"), //1
            new Texture("labyrinth_straight.png"), //2
            new Texture("labyrinth_tcross.png") //3
    };
    private Texture treasure_min = new Texture("treasure.png");
    private Texture treasure_max = new Texture("treasure2.png");
    public final int minTreasureAmount = 3;
    public final int maxTreasureAmount = 5;
    Array<GameField> gameFields = new Array<GameField>(this.playgroundWidth * this.playgroundHeight);
    private int treasureCount = 0;
    private Texture[] figures = new Texture[]{
            new Texture("figure1.png"),
            new Texture("figure2.png"),
            new Texture("figure3.png"),
            new Texture("figure4.png"),
    };

    private void create() {
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);

        DasLabyrinth.whichClass = 1;
        screenWidth = Gdx.graphics.getHeight();
        screenHeight = Gdx.graphics.getWidth();
        halffinalprozent = (int) Math.round(screenHeight * percentHeight / 2F);
        heightAndWidthPerField = (int) Math.round((screenHeight - screenHeight * percentHeight) / 4F);
        startX = Math.round((screenWidth - heightAndWidthPerField * 5F) / 1.1F);
        if (this.gameFields.size != this.playgroundWidth * this.playgroundHeight) {
            generateRandomField();
            int typeNewGF = Functions.randomWithRange(0, 3);
            if (this.treasureCount < maxTreasureAmount) {
                int rnd3 = Functions.randomWithRange(0, 100);
                if (rnd3 < 20) {
                    newGF = new GameField(cards[typeNewGF], true, 0, 0, gameFields.size + 1, typeNewGF, Functions.randomWithRange(0, 3));
                    int rnd2 = Functions.randomWithRange(0, 100);
                    if (rnd2 > 20) {
                        newGF.treasure = new Treasure(treasure_min, 3);
                    } else {
                        newGF.treasure = new Treasure(treasure_max, 5);
                    }
                } else {
                    newGF = new GameField(cards[typeNewGF], false, 0, 0, gameFields.size + 1, typeNewGF, Functions.randomWithRange(0, 3));
                }
            } else {
                newGF = new GameField(cards[typeNewGF], false, 0, 0, gameFields.size + 1, typeNewGF, Functions.randomWithRange(0, 3));
            }
        }

        if (this.imgButtons.size != 2 * this.playgroundWidth + 2 * this.playgroundHeight) {
            // up
            for (int j = 0; j < gameFields.size; j++) {
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
            for (int j = 0; j < gameFields.size; j++) {
                if (gameFields.get(j).y == 1) {
                    Vector2 v = new Vector2();
                    v.x = gameFields.get(j).posX + heightAndWidthPerField / 2;
                    v.y = gameFields.get(j).posY - 20;
                    ImgButton bt = new ImgButton(arrow, v, heightAndWidthPerField / 4, heightAndWidthPerField / 4, ImgButton.Direction.Down);
                    bt.gf = gameFields.get(j);
                    bt.shouldX = gameFields.get(j).x;
                    bt.shouldY = gameFields.get(j).y;
                    imgButtons.add(bt);
                }
            }

            // left
            for (int j = 0; j < gameFields.size; j++) {
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
            for (int j = 0; j < gameFields.size; j++) {
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
            this.main.playerManager.setPreviousScreen(this.main, this);
            this.main.setScreen(this.main.playerManager);
        }

        screenWidth = Gdx.graphics.getHeight();
        screenHeight = Gdx.graphics.getWidth();
        camera.setToOrtho(false, screenHeight, screenWidth);
        DasLabyrinth.pref.flush();
        DasLabyrinth.playSounds = DasLabyrinth.pref.getBoolean("Sounds", true);
        DasLabyrinth.vibration = DasLabyrinth.pref.getBoolean("Vibration", true);
        music.setLooping(true);
        if (DasLabyrinth.pref.getBoolean("Music", true)) {
            music.play();
        }
    }

    private void draw() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background, 0, 0, screenHeight, screenWidth);

        for (int i = 0; i < gameFields.size; i++) {
            gameFields.get(i).posX = (gameFields.get(i).x - 1) * heightAndWidthPerField + halffinalprozent;
            gameFields.get(i).posY = (gameFields.get(i).y - 1) * heightAndWidthPerField + startX;
            Matrix4 rotMatrix = new Matrix4();
            rotMatrix.translate(gameFields.get(i).posX, gameFields.get(i).posY, 0);
            rotMatrix.translate(heightAndWidthPerField / 2, heightAndWidthPerField / 2, 0);
            rotMatrix.rotate(0, 0, 1, 90.0f * gameFields.get(i).facing);
            rotMatrix.translate(-heightAndWidthPerField / 2, -heightAndWidthPerField / 2, 0);
            batch.setTransformMatrix(rotMatrix);
            batch.draw(gameFields.get(i).fieldTextureRegion, 0, 0, heightAndWidthPerField, heightAndWidthPerField);
            batch.setTransformMatrix(originalMatrix);

            if (gameFields.get(i).hasTreasure) {
                gameFields.get(i).treasure.position.x = gameFields.get(i).posX + heightAndWidthPerField / 4;
                gameFields.get(i).treasure.position.y = gameFields.get(i).posY + heightAndWidthPerField / 4;
                batch.draw(gameFields.get(i).treasure.textureRegion, gameFields.get(i).treasure.position.x, gameFields.get(i).treasure.position.y, heightAndWidthPerField / 2, heightAndWidthPerField / 2);
            }
        }
        int dd = Math.round(heightAndWidthPerField * 1.5F);
        if (newGF != null) {
            Matrix4 rotMatrix = new Matrix4();
            rotMatrix.translate(halffinalprozent, startX - dd, 0);
            rotMatrix.translate(heightAndWidthPerField / 2, heightAndWidthPerField / 2, 0);
            rotMatrix.rotate(0, 0, 1, 90.0f * newGF.facing);
            rotMatrix.translate(-heightAndWidthPerField / 2, -heightAndWidthPerField / 2, 0);
            batch.setTransformMatrix(rotMatrix);
            batch.draw(newGF.fieldTextureRegion, 0, 0, heightAndWidthPerField, heightAndWidthPerField);
            if (newGF.hasTreasure) {
                rotMatrix.setToRotation(0, 0, 1, 0.0F);
                rotMatrix.translate(halffinalprozent, startX - dd, 0);
                batch.setTransformMatrix(rotMatrix);
                newGF.treasure.position.x = halffinalprozent + heightAndWidthPerField / 4;
                newGF.treasure.position.y = startX + heightAndWidthPerField / 4;
                batch.draw(newGF.treasure.textureRegion, newGF.x + heightAndWidthPerField / 4, newGF.y + heightAndWidthPerField / 4, heightAndWidthPerField / 2, heightAndWidthPerField / 2);
            }
            batch.setTransformMatrix(originalMatrix);
        }
        for (Player player : this.main.playerManager.players) {
            if (player.currentField == this.newGF) {
                batch.draw(player.figure, halffinalprozent + heightAndWidthPerField / 4, heightAndWidthPerField * 1.5F, heightAndWidthPerField / 2, heightAndWidthPerField / 2);
            } else {
                batch.draw(player.figure, (player.currentField.x - 1) * heightAndWidthPerField + halffinalprozent + heightAndWidthPerField / 4, (player.currentField.y - 1) * heightAndWidthPerField + startX + heightAndWidthPerField / 4, heightAndWidthPerField / 2, heightAndWidthPerField / 2);
            }
        }
        for (int i = 0; i < imgButtons.size; i++) {
            imgButtons.get(i).draw(this.batch);
        }
        batch.draw(rotateArrow, 2 * halffinalprozent + heightAndWidthPerField, startX - Math.round(heightAndWidthPerField * 1.25), heightAndWidthPerField / 2, heightAndWidthPerField / 2);
        batch.end();
    }

    private void update() {
        for (int i = 0; i < imgButtons.size; i++) {
            if (imgButtons.get(i).gf.x != imgButtons.get(i).shouldX || imgButtons.get(i).gf.y != imgButtons.get(i).shouldY) {
                for (int j = 0; j < gameFields.size; j++) {
                    if (gameFields.get(j).x == imgButtons.get(i).shouldX && gameFields.get(j).y == imgButtons.get(i).shouldY) {
                        imgButtons.get(i).gf = gameFields.get(j);
                    }
                }
            }
            switch (imgButtons.get(i).direction) {
                case Down:
                    imgButtons.get(i).position.x = imgButtons.get(i).gf.posX + heightAndWidthPerField / 2;
                    imgButtons.get(i).position.x = imgButtons.get(i).position.x - imgButtons.get(i).width / 2;
                    imgButtons.get(i).position.y = imgButtons.get(i).gf.posY - imgButtons.get(i).height;
                    break;
                case Up:
                    imgButtons.get(i).position.x = imgButtons.get(i).gf.posX + heightAndWidthPerField / 2;
                    imgButtons.get(i).position.x = imgButtons.get(i).position.x - imgButtons.get(i).width / 2;
                    imgButtons.get(i).position.y = imgButtons.get(i).gf.posY + heightAndWidthPerField;
                    break;
                case Left:
                    imgButtons.get(i).position.x = imgButtons.get(i).gf.posX - imgButtons.get(i).width;
                    imgButtons.get(i).position.y = imgButtons.get(i).gf.posY + heightAndWidthPerField / 2;
                    imgButtons.get(i).position.y = imgButtons.get(i).position.y - imgButtons.get(i).height;
                    imgButtons.get(i).position.y = imgButtons.get(i).position.y + imgButtons.get(i).height / 2;
                    break;
                case Right:
                    imgButtons.get(i).position.x = imgButtons.get(i).gf.posX + heightAndWidthPerField;
                    imgButtons.get(i).position.y = imgButtons.get(i).gf.posY;
                    imgButtons.get(i).position.y = imgButtons.get(i).position.y + heightAndWidthPerField / 4;
                    imgButtons.get(i).position.y = imgButtons.get(i).position.y + imgButtons.get(i).height / 2;
                    break;
            }
        }

        for (GameField gameField : this.gameFields) {
            if (Gdx.input.justTouched() && gameField.isClicked(this.heightAndWidthPerField)) {
                this.main.playerManager.moveCurrentPlayer(this, gameField);
            }
        }

        if (Gdx.input.justTouched()) {
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);
            if (touch.x >= (2 * halffinalprozent + heightAndWidthPerField) && touch.x <= (2 * halffinalprozent + heightAndWidthPerField + heightAndWidthPerField / 2) && touch.y >= (startX - (int) Math.round(heightAndWidthPerField * 1.25)) && touch.y <= (startX - (int) Math.round(heightAndWidthPerField * 1.25) + heightAndWidthPerField / 2)) {
                newGF.facing++;
            } else {
                for (int i = 0; i < imgButtons.size; i++) {
                    if (imgButtons.get(i).isClicked()) {
                        imgButtons.get(i).move(this);
                        if (DasLabyrinth.playSounds) {
                            moveSound.play();
                        }
                    }
                }
            }
        }
    }

    private void generateRandomField() {
        gameFields.clear();
        int x = 1;
        int y = 1;
        for (int i = 0; i < this.playgroundHeight * this.playgroundWidth; i++) {
            int type = Functions.randomWithRange(0, 3);
            GameField gf = new GameField(cards[type], Functions.randomBooleanT(), x, y, i, type, Functions.randomWithRange(0, 3));
            if (gf.hasTreasure) {
                this.treasureCount++;
                int rnd = Functions.randomWithRange(0, 100);
                if (rnd > 20) {
                    gf.treasure = new Treasure(treasure_min, 3);
                } else {
                    gf.treasure = new Treasure(treasure_max, 5);
                }
            }
            if (x == 1 && y == 1) {
                this.main.playerManager = new PlayerManager(gf);
            }
            gameFields.add(gf);
            if (y == this.playgroundHeight) {
                y = 1;
                x++;
            } else {
                y++;
            }
        }
        int acounter = 0;
        Array<GameField> atreasures = new Array<GameField>();
        for (int i = 0; i < gameFields.size; i++) {
            if (gameFields.get(i).hasTreasure) {
                acounter++;
                atreasures.add(gameFields.get(i));
            }
        }
        if (acounter > maxTreasureAmount) {
            int b = acounter - maxTreasureAmount;
            Array<GameField> btreasure = new Array<GameField>();
            for (int i = 0; i < b; i++) {
                btreasure.add(atreasures.get(i));
            }
            for (int i = 0; i < gameFields.size; i++) {
                for (int ii = 0; ii < btreasure.size; ii++) {
                    if (gameFields.get(i).index == btreasure.get(ii).index) {
                        gameFields.get(i).hasTreasure = false;
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
        for (int i = 0; i < b; i++) {
            int rnd = Functions.randomWithRange(0, gameFields.size - 1);
            if (gameFields.get(rnd).hasTreasure) {
                makeMoreTreasures(b - i);
            } else {
                gameFields.get(rnd).hasTreasure = true;
            }
            int rnd2 = Functions.randomWithRange(0, 100);
            if (rnd2 > 20) {
                gameFields.get(rnd).treasure = new Treasure(treasure_min, 3);
            } else {
                gameFields.get(rnd).treasure = new Treasure(treasure_max, 5);
            }
        }
    }

    GameField[][] GamefieldToArray() {
        GameField board[][] = new GameField[this.playgroundWidth][this.playgroundHeight];
        for (GameField field : this.gameFields) {
            board[field.x - 1][field.y - 1] = field;
        }
        return board;
    }

    void ArrayToGamefield(GameField[][] board) {
        gameFields.clear();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j].x = i + 1;
                board[i][j].y = j + 1;
                gameFields.add(board[i][j]);
            }
        }
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update();
        draw();
    }

    public void resize(int width, int height) {

    }

    public void show() {
        create();
    }

    public void hide() {
        music.pause();
    }

    public void pause() {
    }

    public void resume() {
    }

    public void dispose() {
        batch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        DasLabyrinth.click();
        if (keycode == Input.Keys.BACK) {
            main.setScreen(new StartMenu(main));
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
