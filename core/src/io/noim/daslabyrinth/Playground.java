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
import com.badlogic.gdx.math.Vector3;
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
    public static Music music;
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

    public void create() {
        StartMenu.whichClass = 1;
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
        if (StartMenu.pref.getBoolean("Music", true)){
            music.play();
        }
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
        startx = (int) Math.round(q / 1.1);
        roboto = new BitmapFont(Gdx.files.internal("Roboto.fnt"));
        istmovingnewfield = false;
        int k = 1;
        for (int i = 0; i < 18; i++){
            if (i <= 5){
                for (GameField gf : Functions.gameFields){
                    if (gf.y == 1 && gf.x == i){
                        Vector2 v = new Vector2();
                        v.x = gf.posx + heightandwidthperfield / 2;
                        v.y = gf.posy - 20;
                        ImgButton bt = new ImgButton(pfeil, v, -90.0F,batch, heightandwidthperfield / 4, heightandwidthperfield /4, true, false);
                        bt.gf = gf;
                        bt.shouldx = k;
                        bt.shouldy = 1;
                        imgButtons.add(bt);
                        if (k == 5){
                            k = 0;
                        }
                        k++;
                    }
                }
            }
            if (i <= 10 && i >= 6){
                for (GameField gf : Functions.gameFields){
                    if (gf.y == 5){
                        Vector2 v = new Vector2();
                        ImgButton bt = new ImgButton(pfeil, v, 90.0F,batch, heightandwidthperfield / 4, heightandwidthperfield /4, true, true);
                        bt.gf = gf;
                        bt.shouldx = k;
                        bt.shouldy = 5;
                        imgButtons.add(bt);
                        if (k == 5){
                            k = 0;
                        }
                        k++;
                    }
                }
            }
            if (i <= 14 && i >= 11){
                for (GameField gf : Functions.gameFields){
                    if (gf.x == 1){
                        Vector2 v = new Vector2();
                        ImgButton bt = new ImgButton(pfeil, v, -180.0F,batch, heightandwidthperfield / 4, heightandwidthperfield /4, false, false);
                        bt.gf = gf;
                        bt.shouldx = 1;
                        bt.shouldy = k;
                        imgButtons.add(bt);
                        if (k == 4){
                            k = 0;
                        }
                        k++;
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

        for (GameField gf : gameFields) {
            if (gf.x == x && gf.y <= 5) {
                Matrix4 rotMatrix = new Matrix4();
                rotMatrix.translate(yy, xx, 0);
                rotMatrix.translate(heightandwidthperfield / 2, heightandwidthperfield / 2, 0);
                rotMatrix.rotate(0, 0, 1, 90.0f * gf.facing);
                rotMatrix.translate(-heightandwidthperfield / 2, -heightandwidthperfield / 2, 0);
                batch.setTransformMatrix(rotMatrix);
                batch.draw(gf.fieldTextureRegion, 0, 0, heightandwidthperfield, heightandwidthperfield);
                batch.setTransformMatrix(originalMatrix);
                gf.posx = yy;
                gf.posy = xx;

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
        for (ImgButton imb : imgButtons){
            imb.draw();
        }
        batch.end();
    }

    public void update() {
        for (ImgButton img : imgButtons){
            if (img.gf.x != img.shouldx || img.gf.y != img.shouldy){
                for (GameField ggf : gameFields){
                    if (ggf.x == img.shouldx && ggf.y == img.shouldy){
                        img.gf = ggf;
                    }
                }
            }
            if (img.shouldy == 1){
                img.vec.x = img.gf.posx + heightandwidthperfield / 2;
                img.vec.x = img.vec.x - img.width / 2;
                img.vec.y = img.gf.posy - img.height;
            }
            if (img.shouldy == 5){
                img.vec.x = img.gf.posx + heightandwidthperfield / 2;
                img.vec.x = img.vec.x - img.width / 2;
                img.vec.y = img.gf.posy + heightandwidthperfield;
            }
            if (img.shouldx == 1 && img.shouldy > 2){
                img.vec.x = img.gf.posx - img.width;
                img.vec.y = img.gf.posy + heightandwidthperfield / 2;
                img.vec.y = img.vec.y - img.height;
                img.vec.y = img.vec.y + img.height / 2;
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
            }
            if (!alreadypressed){
                for (ImgButton imgb : imgButtons){
                    if (imgb.isClicked()){
                        boolean rev;
                        Functions.moveFields(imgb.gf.x, imgb.gf.y, imgb.fromx, newgf, imgb.reverse);
                        alreadypressed = true;
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
    public void t(){
        System.out.println("Test");
    }

}
