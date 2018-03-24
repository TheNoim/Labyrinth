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
    Array<GameField> gameFields = new Array<GameField>();
    SpriteBatch batch;
    int screen_width;
    int screen_hight;
    double prozenthight = 0.1;
    public static Music music;
    Sound move;
    int startx;
    int heightandwidthperfield;
    int halffinalprozent;
    ShapeRenderer shaper;
    public static OrthographicCamera camera;
    Texture background;
    public static GameField newgf;
    Matrix4 originalMatrix = new Matrix4();
    boolean istmovingnewfield;
    Vector2 newfieldv = new Vector2();
    int newfieldw;
    Vector3 newfieldvector = new Vector3();
    boolean notallowedtotouch;
    int rendertimer = 0;
    Array<ImgButton> imgButtons = new Array<ImgButton>();
    boolean alreadypressed;
    Texture pfeil = new Texture("pfeil.gif");
    Texture rotatePfeil = new Texture("rotate.png");

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
        screen_width = Gdx.graphics.getHeight();
        screen_hight = Gdx.graphics.getWidth();
        background = new Texture("background.png");
        batch = new SpriteBatch();
        shaper = new ShapeRenderer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screen_hight, screen_width);
        DasLabyrinth.pref.flush();
        DasLabyrinth.playSounds = DasLabyrinth.pref.getBoolean("Sounds", true);
        DasLabyrinth.vibration = DasLabyrinth.pref.getBoolean("Vibration", true);
        music = Gdx.audio.newMusic(Gdx.files.internal("Epic Suspense.mp3"));
        move = Gdx.audio.newSound(Gdx.files.internal("move.mp3"));
        music.setLooping(true);
        if (DasLabyrinth.pref.getBoolean("Music", true)) {
            music.play();
        }
        gameFields = Functions.gameFields;
        int temp_a = 0;
        for (int i = 0; i < gameFields.size; i++) {
            if (gameFields.get(i).x == 1) {

            }
        }
        screen_width = Gdx.graphics.getHeight();
        screen_hight = Gdx.graphics.getWidth();
        double tenprozent = screen_hight * prozenthight;
        float round = Math.round(tenprozent);
        int finaltenprozent = ((Float) round).intValue();
        int availablehight = screen_hight - finaltenprozent;
        halffinalprozent = finaltenprozent / 2;
        heightandwidthperfield = availablehight / 4;
        int usedwidth = heightandwidthperfield * 5;
        int q = screen_width - usedwidth;
        startx = ((Long) Math.round(q / 1.1)).intValue();
        roboto = new BitmapFont(Gdx.files.internal("Roboto.fnt"));
        istmovingnewfield = false;
        int k = 1;
        for (int i = 0; i < 18; i++) {
            if (i <= 5) {
                for (int j = 0; j < Functions.gameFields.size; j++) {
                    if (Functions.gameFields.get(j).y == 1 && Functions.gameFields.get(j).x == i) {
                        Vector2 v = new Vector2();
                        v.x = Functions.gameFields.get(j).posx + heightandwidthperfield / 2;
                        v.y = Functions.gameFields.get(j).posy - 20;
                        ImgButton bt = new ImgButton(pfeil, v, batch, heightandwidthperfield / 4, heightandwidthperfield / 4, Richtung.Unten);
                        bt.gf = Functions.gameFields.get(j);
                        bt.shouldx = Functions.gameFields.get(j).x;
                        bt.shouldy = Functions.gameFields.get(j).y;
                        imgButtons.add(bt);
                    }
                }
            }
            if (i <= 10 && i >= 6) {
                for (int j = 0; j < Functions.gameFields.size; j++) {
                    if (Functions.gameFields.get(j).y == 5) {
                        Vector2 v = new Vector2();
                        ImgButton bt = new ImgButton(pfeil, v, batch, heightandwidthperfield / 4, heightandwidthperfield / 4, Richtung.Oben);
                        bt.gf = Functions.gameFields.get(j);
                        bt.shouldx = Functions.gameFields.get(j).x;
                        bt.shouldy = Functions.gameFields.get(j).y;
                        imgButtons.add(bt);
                    }
                }
            }
            if (i <= 15 && i >= 10) {
                for (int j = 0; j < Functions.gameFields.size; j++) {
                    if (Functions.gameFields.get(j).x == 1) {
                        Vector2 v = new Vector2();
                        ImgButton bt = new ImgButton(pfeil, v, batch, heightandwidthperfield / 4, heightandwidthperfield / 4, Richtung.Links);
                        bt.gf = Functions.gameFields.get(j);
                        bt.shouldx = Functions.gameFields.get(j).x;
                        bt.shouldy = Functions.gameFields.get(j).y;
                        imgButtons.add(bt);
                    }
                }
            }
            if (i >= 15) {
                for (int j = 0; j < Functions.gameFields.size; j++) {
                    if (Functions.gameFields.get(j).x == 4) {
                        Vector2 v = new Vector2();
                        ImgButton bt = new ImgButton(pfeil, v, batch, heightandwidthperfield / 4, heightandwidthperfield / 4, Richtung.Rechts);
                        bt.gf = Functions.gameFields.get(j);
                        bt.shouldx = Functions.gameFields.get(j).x;
                        bt.shouldy = Functions.gameFields.get(j).y;
                        imgButtons.add(bt);
                    }
                }
            }
        }
    }

    public void draw() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background, 0, 0, screen_hight, screen_width);
        int x = 1;
        int xx = startx;
        int yy = halffinalprozent;

        for (int i = 0; i < Functions.gameFields.size; i++) {
            if (Functions.gameFields.get(i).x == x && Functions.gameFields.get(i).y <= 5) {
                Matrix4 rotMatrix = new Matrix4();
                rotMatrix.translate(yy, xx, 0);
                rotMatrix.translate(heightandwidthperfield / 2, heightandwidthperfield / 2, 0);
                rotMatrix.rotate(0, 0, 1, 90.0f * Functions.gameFields.get(i).facing);
                rotMatrix.translate(-heightandwidthperfield / 2, -heightandwidthperfield / 2, 0);
                batch.setTransformMatrix(rotMatrix);
                batch.draw(Functions.gameFields.get(i).fieldTextureRegion, 0, 0, heightandwidthperfield, heightandwidthperfield);
                batch.setTransformMatrix(originalMatrix);
                Functions.gameFields.get(i).posx = yy;
                Functions.gameFields.get(i).posy = xx;

                if (Functions.gameFields.get(i).isTreasure) {
                    int tr_texture_width = Functions.gameFields.get(i).treasure.textureRegion.getRegionWidth();
                    int g = heightandwidthperfield - tr_texture_width;
                    int h = g / 2;
                    Functions.gameFields.get(i).treasure.position.x = yy + heightandwidthperfield / 4;
                    Functions.gameFields.get(i).treasure.position.y = xx + heightandwidthperfield / 4;
                    batch.draw(Functions.gameFields.get(i).treasure.textureRegion, Functions.gameFields.get(i).treasure.position.x, Functions.gameFields.get(i).treasure.position.y, heightandwidthperfield / 2, heightandwidthperfield / 2);
                }
                xx += heightandwidthperfield;
                if (Functions.gameFields.get(i).y == 5) {
                    x++;
                    yy += heightandwidthperfield;
                    xx = startx;
                }
            }
        }
        xx = startx;
        yy = halffinalprozent;
        if (newgf != null) {
            int dd = (int) Math.round(heightandwidthperfield * 1.5);
            roboto.setColor(Color.BLACK);
            roboto.getData().setScale((float) 1.5, (float) 1.5);
            roboto.draw(batch, "Your next Card !", yy, xx - dd - dd / 16);
            Matrix4 rotMatrix = new Matrix4();
            rotMatrix.translate(yy, xx - dd, 0);
            rotMatrix.translate(heightandwidthperfield / 2, heightandwidthperfield / 2, 0);
            rotMatrix.rotate(0, 0, 1, 90.0f * newgf.facing);
            rotMatrix.translate(-heightandwidthperfield / 2, -heightandwidthperfield / 2, 0);
            batch.setTransformMatrix(rotMatrix);
            batch.draw(newgf.fieldTextureRegion, 0, 0, heightandwidthperfield, heightandwidthperfield);
            newfieldv.x = yy;
            newfieldv.y = xx - dd;
            newfieldw = heightandwidthperfield;
            if (newgf.isTreasure) {
                rotMatrix.setToRotation(0, 0, 1, 0.0F);
                int tr_texture_width = newgf.treasure.textureRegion.getRegionWidth();
                int g = heightandwidthperfield - tr_texture_width;
                int h = g / 2;
                newgf.treasure.position.x = yy + heightandwidthperfield / 4;
                newgf.treasure.position.y = xx + heightandwidthperfield / 4;
                batch.draw(newgf.treasure.textureRegion, newgf.x + heightandwidthperfield / 4, newgf.y + heightandwidthperfield / 4, heightandwidthperfield / 2, heightandwidthperfield / 2);
            }
            batch.setTransformMatrix(originalMatrix);
        }
        for (int i = 0; i < imgButtons.size; i++) {
            imgButtons.get(i).draw();
        }
        batch.draw(rotatePfeil, 2 * yy + heightandwidthperfield, xx - Math.round(heightandwidthperfield * 1.25), heightandwidthperfield / 2, heightandwidthperfield / 2);
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
            if (imgButtons.get(i).rich == Richtung.Unten) {
                imgButtons.get(i).vec.x = imgButtons.get(i).gf.posx + heightandwidthperfield / 2;
                imgButtons.get(i).vec.x = imgButtons.get(i).vec.x - imgButtons.get(i).width / 2;
                imgButtons.get(i).vec.y = imgButtons.get(i).gf.posy - imgButtons.get(i).height;
            }
            if (imgButtons.get(i).rich == Richtung.Oben) {
                imgButtons.get(i).vec.x = imgButtons.get(i).gf.posx + heightandwidthperfield / 2;
                imgButtons.get(i).vec.x = imgButtons.get(i).vec.x - imgButtons.get(i).width / 2;
                imgButtons.get(i).vec.y = imgButtons.get(i).gf.posy + heightandwidthperfield;
            }
            if (imgButtons.get(i).rich == Richtung.Links) {
                imgButtons.get(i).vec.x = imgButtons.get(i).gf.posx - imgButtons.get(i).width;
                imgButtons.get(i).vec.y = imgButtons.get(i).gf.posy + heightandwidthperfield / 2;
                imgButtons.get(i).vec.y = imgButtons.get(i).vec.y - imgButtons.get(i).height;
                imgButtons.get(i).vec.y = imgButtons.get(i).vec.y + imgButtons.get(i).height / 2;
            }
            if (imgButtons.get(i).rich == Richtung.Rechts) {
                imgButtons.get(i).vec.x = imgButtons.get(i).gf.posx + heightandwidthperfield;
                imgButtons.get(i).vec.y = imgButtons.get(i).gf.posy;
                imgButtons.get(i).vec.y = imgButtons.get(i).vec.y + heightandwidthperfield / 4;
                imgButtons.get(i).vec.y = imgButtons.get(i).vec.y + imgButtons.get(i).height / 2;
            }
        }
        if (Gdx.input.isTouched()) {

            Vector3 touch = new Vector3();
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);
            if (touch.x >= newfieldv.x && touch.x <= newfieldv.x + newfieldw && touch.y >= newfieldv.y && touch.y <= newfieldv.y + newfieldw) {
                if (!notallowedtotouch) {
                    Functions.moveFields(0, 2, false, newgf, false);
                    notallowedtotouch = true;
                }
            } else if (touch.x >= (2 * halffinalprozent + heightandwidthperfield) && touch.x <= (2 * halffinalprozent + heightandwidthperfield + heightandwidthperfield / 2) && touch.y >= (startx - (int) Math.round(heightandwidthperfield * 1.25)) && touch.y <= (startx - (int) Math.round(heightandwidthperfield * 1.25) + heightandwidthperfield / 2)) {
                if (!notallowedtotouch) {
                    newgf.facing += 1;
                    notallowedtotouch = true;
                }
            }
            if (!alreadypressed) {
                for (int i = 0; i < imgButtons.size; i++) {
                    if (imgButtons.get(i).isClicked()) {
                        //Functions.moveFields(imgButtons.get(i).gf.x, imgButtons.get(i).gf.y, imgButtons.get(i).fromx, newgf, imgButtons.get(i).reverse);
                        imgButtons.get(i).move();
                        alreadypressed = true;
                        if (DasLabyrinth.playSounds) {
                            move.play();
                        }
                    }
                }
            }
        } else {
            notallowedtotouch = false;
            alreadypressed = false;
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

    public void t() {
        System.out.println("Test");
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
