package io.noim.daslabyrinth;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class Playground implements Screen, InputProcessor {

    public DasLabyrinth main;

    public Playground(final DasLabyrinth main) {
        Functions.generateRandomField();
        this.main = main;
    }

    BitmapFont roboto;
    private Array<GameField> gameFields = new Array<GameField>();
    private SpriteBatch batch;
    static int Width = 4;
    static int Height = 5;
    int screenWidth;
    int screenHeight;
    double percentHeight = 0.1;
    static Music music;
    Sound move;
    int startX;
    int heightandwidthperfield;
    int halffinalprozent;
    ShapeRenderer shaper;
    static OrthographicCamera camera;
    Texture background;
    static GameField newgf;
    Matrix4 originalMatrix = new Matrix4();
    boolean isMovingNewField;
    Vector2 newfieldv = new Vector2();
    int newfieldw;
    boolean notallowedtotouch;
    Array<ImgButton> imgButtons = new Array<ImgButton>();
    boolean alreadypressed;
    Texture arrow = new Texture("pfeil.gif");
    Texture rotateArrow = new Texture("rotate.png");

    public void create() {
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);

        DasLabyrinth.whichClass = 1;
        Functions.generateRandomField();
        Functions.printField();
        int tcount = 0;
        for (int i = 0; i < Functions.gameFields.size; i++) {
            if (Functions.gameFields.get(i).isTreasure) {
                tcount++;
            }
        }
        if (tcount < Functions.treasurcountermax) {
            int rnd3 = Functions.randomWithRange(0, 100);
            if (rnd3 < 20) {
                newgf = new GameField(Functions.getTextureByType(Functions.randomWithRange(0, 3)), true, 0, 0, Functions.gameFields.size + 1, Functions.randomWithRange(0, 3), Functions.randomWithRange(0, 3));
                int rnd2 = Functions.randomWithRange(0, 100);
                if (rnd2 > 20) {
                    newgf.treasure = new Treasure(Functions.treasure_min, 3);
                } else {
                    newgf.treasure = new Treasure(Functions.treasure_max, 5);
                }
            } else {
                newgf = new GameField(Functions.getTextureByType(Functions.randomWithRange(0, 3)), false, 0, 0, Functions.gameFields.size + 1, Functions.randomWithRange(0, 3), Functions.randomWithRange(0, 3));
            }
        } else {
            newgf = new GameField(Functions.getTextureByType(Functions.randomWithRange(0, 3)), false, 0, 0, Functions.gameFields.size + 1, Functions.randomWithRange(0, 3), Functions.randomWithRange(0, 3));
        }
        screenWidth = Gdx.graphics.getHeight();
        screenHeight = Gdx.graphics.getWidth();
        background = new Texture("background.png");
        batch = new SpriteBatch();
        shaper = new ShapeRenderer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenHeight, screenWidth);
        DasLabyrinth.pref.flush();
        DasLabyrinth.playSounds = DasLabyrinth.pref.getBoolean("Sounds", true);
        DasLabyrinth.vibration = DasLabyrinth.pref.getBoolean("Vibration", true);
        music = Gdx.audio.newMusic(Gdx.files.internal("Epic Suspense.mp3"));
        move = Gdx.audio.newSound(Gdx.files.internal("Move.mp3"));
        music.setLooping(true);
        if (DasLabyrinth.pref.getBoolean("Music", true)) {
            music.play();
        }
        gameFields = Functions.gameFields;
        screenWidth = Gdx.graphics.getHeight();
        screenHeight = Gdx.graphics.getWidth();
        int finalTenPercent = (int) Math.round(screenHeight * percentHeight);
        halffinalprozent = finalTenPercent / 2;
        heightandwidthperfield = (screenHeight - finalTenPercent) / 4;
        startX = (int) Math.round((screenWidth - heightandwidthperfield * 5) / 1.1);
        roboto = new BitmapFont(Gdx.files.internal("Roboto.fnt"));
        isMovingNewField = false;

        // up
        for (int j = 0; j < Functions.gameFields.size; j++) {
            if (Functions.gameFields.get(j).y == 5) {
                Vector2 v = new Vector2();
                ImgButton bt = new ImgButton(arrow, v, heightandwidthperfield / 4, heightandwidthperfield / 4, Direction.Up);
                bt.gf = Functions.gameFields.get(j);
                bt.shouldx = Functions.gameFields.get(j).x;
                bt.shouldy = Functions.gameFields.get(j).y;
                imgButtons.add(bt);
            }
        }

        // down
        for (int j = 0; j < Functions.gameFields.size; j++) {
            if (Functions.gameFields.get(j).y == 1) {
                Vector2 v = new Vector2();
                v.x = Functions.gameFields.get(j).posx + heightandwidthperfield / 2;
                v.y = Functions.gameFields.get(j).posy - 20;
                ImgButton bt = new ImgButton(arrow, v, heightandwidthperfield / 4, heightandwidthperfield / 4, Direction.Down);
                bt.gf = Functions.gameFields.get(j);
                bt.shouldx = Functions.gameFields.get(j).x;
                bt.shouldy = Functions.gameFields.get(j).y;
                imgButtons.add(bt);
            }
        }

        // left
        for (int j = 0; j < Functions.gameFields.size; j++) {
            if (Functions.gameFields.get(j).x == 1) {
                Vector2 v = new Vector2();
                ImgButton bt = new ImgButton(arrow, v, heightandwidthperfield / 4, heightandwidthperfield / 4, Direction.Left);
                bt.gf = Functions.gameFields.get(j);
                bt.shouldx = Functions.gameFields.get(j).x;
                bt.shouldy = Functions.gameFields.get(j).y;
                imgButtons.add(bt);
            }
        }

        // right
        for (int j = 0; j < Functions.gameFields.size; j++) {
            if (Functions.gameFields.get(j).x == 4) {
                Vector2 v = new Vector2();
                ImgButton bt = new ImgButton(arrow, v, heightandwidthperfield / 4, heightandwidthperfield / 4, Direction.Right);
                bt.gf = Functions.gameFields.get(j);
                bt.shouldx = Functions.gameFields.get(j).x;
                bt.shouldy = Functions.gameFields.get(j).y;
                imgButtons.add(bt);
            }
        }
    }

    public void draw() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background, 0, 0, screenHeight, screenWidth);

        for (int i = 0; i < Functions.gameFields.size; i++) {
            Functions.gameFields.get(i).posx = (Functions.gameFields.get(i).x - 1) * heightandwidthperfield + halffinalprozent;
            Functions.gameFields.get(i).posy = (Functions.gameFields.get(i).y - 1) * heightandwidthperfield + startX;
            Matrix4 rotMatrix = new Matrix4();
            rotMatrix.translate(Functions.gameFields.get(i).posx, Functions.gameFields.get(i).posy, 0);
            rotMatrix.translate(heightandwidthperfield / 2, heightandwidthperfield / 2, 0);
            rotMatrix.rotate(0, 0, 1, 90.0f * Functions.gameFields.get(i).facing);
            rotMatrix.translate(-heightandwidthperfield / 2, -heightandwidthperfield / 2, 0);
            batch.setTransformMatrix(rotMatrix);
            batch.draw(Functions.gameFields.get(i).fieldTextureRegion, 0, 0, heightandwidthperfield, heightandwidthperfield);
            batch.setTransformMatrix(originalMatrix);

            if (Functions.gameFields.get(i).isTreasure) {
                Functions.gameFields.get(i).treasure.position.x = Functions.gameFields.get(i).posx + heightandwidthperfield / 4;
                Functions.gameFields.get(i).treasure.position.y = Functions.gameFields.get(i).posy + heightandwidthperfield / 4;
                batch.draw(Functions.gameFields.get(i).treasure.textureRegion, Functions.gameFields.get(i).treasure.position.x, Functions.gameFields.get(i).treasure.position.y, heightandwidthperfield / 2, heightandwidthperfield / 2);
            }
        }
        if (newgf != null) {
            int dd = Math.round(heightandwidthperfield * 1.5F);
            roboto.setColor(Color.BLACK);
            roboto.getData().setScale(1.5F, 1.5F);
            roboto.draw(batch, "Your next Card !", halffinalprozent, startX - dd - dd / 16);
            Matrix4 rotMatrix = new Matrix4();
            rotMatrix.translate(halffinalprozent, startX - dd, 0);
            rotMatrix.translate(heightandwidthperfield / 2, heightandwidthperfield / 2, 0);
            rotMatrix.rotate(0, 0, 1, 90.0f * newgf.facing);
            rotMatrix.translate(-heightandwidthperfield / 2, -heightandwidthperfield / 2, 0);
            batch.setTransformMatrix(rotMatrix);
            batch.draw(newgf.fieldTextureRegion, 0, 0, heightandwidthperfield, heightandwidthperfield);
            newfieldv.x = halffinalprozent;
            newfieldv.y = startX - dd;
            newfieldw = heightandwidthperfield;
            if (newgf.isTreasure) {
                rotMatrix.setToRotation(0, 0, 1, 0.0F);
                newgf.treasure.position.x = halffinalprozent + heightandwidthperfield / 4;
                newgf.treasure.position.y = startX + heightandwidthperfield / 4;
                batch.draw(newgf.treasure.textureRegion, newgf.x + heightandwidthperfield / 4, newgf.y + heightandwidthperfield / 4, heightandwidthperfield / 2, heightandwidthperfield / 2);
            }
            batch.setTransformMatrix(originalMatrix);
        }
        for (int i = 0; i < imgButtons.size; i++) {
            imgButtons.get(i).draw(this.batch);
        }
        batch.draw(rotateArrow, 2 * halffinalprozent + heightandwidthperfield, startX - Math.round(heightandwidthperfield * 1.25), heightandwidthperfield / 2, heightandwidthperfield / 2);
        batch.end();
    }

    public void update() {
        for (int i = 0; i < imgButtons.size; i++) {
            if (imgButtons.get(i).gf.x != imgButtons.get(i).shouldx || imgButtons.get(i).gf.y != imgButtons.get(i).shouldy) {
                for (int j = 0; j < gameFields.size; j++) {
                    if (gameFields.get(j).x == imgButtons.get(i).shouldx && gameFields.get(j).y == imgButtons.get(i).shouldy) {
                        imgButtons.get(i).gf = gameFields.get(j);
                    }
                }
            }
            if (imgButtons.get(i).direction == Direction.Down) {
                imgButtons.get(i).vec.x = imgButtons.get(i).gf.posx + heightandwidthperfield / 2;
                imgButtons.get(i).vec.x = imgButtons.get(i).vec.x - imgButtons.get(i).width / 2;
                imgButtons.get(i).vec.y = imgButtons.get(i).gf.posy - imgButtons.get(i).height;
            }
            if (imgButtons.get(i).direction == Direction.Up) {
                imgButtons.get(i).vec.x = imgButtons.get(i).gf.posx + heightandwidthperfield / 2;
                imgButtons.get(i).vec.x = imgButtons.get(i).vec.x - imgButtons.get(i).width / 2;
                imgButtons.get(i).vec.y = imgButtons.get(i).gf.posy + heightandwidthperfield;
            }
            if (imgButtons.get(i).direction == Direction.Left) {
                imgButtons.get(i).vec.x = imgButtons.get(i).gf.posx - imgButtons.get(i).width;
                imgButtons.get(i).vec.y = imgButtons.get(i).gf.posy + heightandwidthperfield / 2;
                imgButtons.get(i).vec.y = imgButtons.get(i).vec.y - imgButtons.get(i).height;
                imgButtons.get(i).vec.y = imgButtons.get(i).vec.y + imgButtons.get(i).height / 2;
            }
            if (imgButtons.get(i).direction == Direction.Right) {
                imgButtons.get(i).vec.x = imgButtons.get(i).gf.posx + heightandwidthperfield;
                imgButtons.get(i).vec.y = imgButtons.get(i).gf.posy;
                imgButtons.get(i).vec.y = imgButtons.get(i).vec.y + heightandwidthperfield / 4;
                imgButtons.get(i).vec.y = imgButtons.get(i).vec.y + imgButtons.get(i).height / 2;
            }
        }

        if (Gdx.input.justTouched()) {
            Vector3 touch = new Vector3();
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);
            if (touch.x >= (2 * halffinalprozent + heightandwidthperfield) && touch.x <= (2 * halffinalprozent + heightandwidthperfield + heightandwidthperfield / 2) && touch.y >= (startX - (int) Math.round(heightandwidthperfield * 1.25)) && touch.y <= (startX - (int) Math.round(heightandwidthperfield * 1.25) + heightandwidthperfield / 2)) {
                newgf.facing += 1;
            }
            for (int i = 0; i < imgButtons.size; i++) {
                if (imgButtons.get(i).isClicked()) {
                    imgButtons.get(i).move();
                    if (DasLabyrinth.playSounds) {
                        move.play();
                    }
                }
            }
        }
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Functions.generateRandomField();
        //Functions.printField();
        update();
        draw();
    }

    public void resize(int width, int height) {
        Functions.scaleWindow();
    }

    public void show() {
        create();
    }

    public void hide() {
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
