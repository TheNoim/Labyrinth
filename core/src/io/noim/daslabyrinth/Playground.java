package io.noim.daslabyrinth;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Playground implements Screen {

    public DasLabyrinth main;

    public Playground(final DasLabyrinth main) {
        Functions.generateRandomeField();
        this.main = main;
    }

    BitmapFont roboto;
    Array<GameField> gameFields = new Array<GameField>();
    SpriteBatch batch;
    int screen_width;
    int screen_hight;
    double prozenthight = 0.1;
    Music music;
    int startx;
    int heightandwidthperfield;
    int halffinalprozent;
    ShapeRenderer shaper;
    OrthographicCamera camera;
    Texture background;
    GameField newgf;
    Matrix4 originalMatrix = new Matrix4();
    boolean istmovingnewfield;
    Vector2 newfieldv = new Vector2();
    int newfieldw;

    public void create() {
        Functions.generateRandomeField();
        Functions.printField();
        int tcount = 0;
        for (GameField gff : Functions.gameFields) {
            if (gff.isTreasure) {
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
        music = Gdx.audio.newMusic(Gdx.files.internal("Epic Suspense.mp3"));
        music.setLooping(true);
        music.play();
        gameFields = Functions.gameFields;
        int temp_a = 0;
        for (GameField gf : gameFields) {
            if (gf.x == 1) {

            }
        }
        screen_width = Gdx.graphics.getHeight();
        screen_hight = Gdx.graphics.getWidth();
        double tenprozent = screen_hight * prozenthight;
        float round = Math.round(tenprozent);
        int finaltenprozent = (int) round;
        int availablehight = screen_hight - finaltenprozent;
        halffinalprozent = finaltenprozent / 2;
        heightandwidthperfield = availablehight / 4;
        int usedwidth = heightandwidthperfield * 5;
        int q = screen_width - usedwidth;
        startx = (int) Math.round(q / 1.07);
        roboto = new BitmapFont(Gdx.files.internal("Roboto.fnt"));
        istmovingnewfield = false;
    }

    public void draw() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background, 0, 0, screen_hight, screen_width);
        int x = 1;
        int xx = startx;
        int yy = halffinalprozent;
        if (newgf != null) {
            int dd = (int) Math.round(heightandwidthperfield * 1.5);
            batch.draw(newgf.fieldTextureRegion, yy, xx - dd, heightandwidthperfield, heightandwidthperfield);
            newfieldv.x = yy;
            newfieldv.y = xx;
            newfieldw = heightandwidthperfield;
            roboto.setColor(Color.BLACK);
            roboto.getData().setScale((float) 1.5, (float) 1.5);
            roboto.draw(batch, "Your next Card !", yy, xx - dd - dd / 16);
        }
        for (GameField gf : gameFields) {
            if (gf.x == x && gf.y <= 5) {
                Matrix4 rotMatrix = new Matrix4();
                rotMatrix.translate(yy, xx , 0);
                rotMatrix.translate(heightandwidthperfield / 2, heightandwidthperfield / 2, 0);
                rotMatrix.rotate(0, 0, 1, 90.0f * gf.facing);
                rotMatrix.translate(-heightandwidthperfield / 2, -heightandwidthperfield / 2, 0);
                batch.setTransformMatrix(rotMatrix);
                batch.draw(gf.fieldTextureRegion, 0, 0, heightandwidthperfield, heightandwidthperfield);
                batch.setTransformMatrix(originalMatrix);
                if (gf.isTreasure) {
                    int tr_texture_width = gf.treasure.textureRegion.getRegionWidth();
                    int g = heightandwidthperfield - tr_texture_width;
                    int h = g / 2;
                    gf.treasure.position.x = yy + heightandwidthperfield / 4;
                    gf.treasure.position.y = xx + heightandwidthperfield / 4;
                    batch.draw(gf.treasure.textureRegion, gf.treasure.position.x, gf.treasure.position.y, heightandwidthperfield / 2, heightandwidthperfield / 2);
                }
                xx += heightandwidthperfield;
                if (gf.y == 5) {
                    x++;
                    yy += heightandwidthperfield;
                    xx = startx;
                }
            }
        }
        batch.end();
    }

    public void update() {
        if (Gdx.input.isTouched()){
            if (istmovingnewfield){

            } else {
                Vector2 touch = new Vector2();
                touch.x = Gdx.input.getX();
                touch.y = Gdx.input.getY();
                if (touch.x <= newfieldv.x && touch.x >= newfieldv.x - newfieldw && touch.y >= newfieldv.y && touch.y <= newfieldv.y + newfieldw){
                    System.out.println("Test");
                    System.out.println(newfieldv.x + "  " + newfieldv.y);
                }
            }
        }
    }

    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Functions.generateRandomeField();
        //Functions.printField();
        update();
        draw();
    }

    public void resize(int width, int height) {
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

}
