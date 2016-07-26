package io.noim.daslabyrinth;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

public class Playground extends ApplicationAdapter{

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

    @Override
    public void create(){
        screen_width = Gdx.graphics.getHeight();
        screen_hight = Gdx.graphics.getWidth();
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
            if (gf.x == 1){

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
        startx = q / 4;
    }
    public void draw(){
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        int x = 1;
        int xx = startx;
        int yy = halffinalprozent;
        for (GameField gf : gameFields){
            if (gf.x == x && gf.y <= 5){
                batch.draw(gf.fieldTextureRegion, yy, xx, heightandwidthperfield, heightandwidthperfield);
                if (gf.isTreasure){
                    if(gf.treasure != null){
                        int tr_texture_width = gf.treasure.textureRegion.getRegionWidth();
                        int g = heightandwidthperfield - tr_texture_width;
                        int h = g / 2;
                        gf.treasure.position.x = yy + heightandwidthperfield / 4;
                        gf.treasure.position.y = xx + heightandwidthperfield / 4;
                        batch.draw(gf.treasure.textureRegion, gf.treasure.position.x, gf.treasure.position.y, heightandwidthperfield / 2, heightandwidthperfield / 2);
                    }
                }
                xx += heightandwidthperfield;
                if (gf.y == 5){
                    x++;
                    yy += heightandwidthperfield;
                    xx = startx;
                }
            }
        }

        batch.end();
    }
    public void update(){
    }

    @Override
    public void render(){

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Functions.generateRandomeField();
        Functions.printField();
        update();
        draw();
    }
    @Override
    public void dispose () {
        batch.dispose();
    }

    class PlayGround {

        int width;
        int hight;

        public void PlayerGround(int width, int hight){
            this.width = width;
            this.hight = hight;
        }
    }
}
