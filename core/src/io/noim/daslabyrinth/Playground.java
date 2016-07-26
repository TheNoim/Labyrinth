package io.noim.daslabyrinth;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class Playground extends ApplicationAdapter{

    Array<GameField> gameFields = new Array<GameField>();
    SpriteBatch batch;
    int playground_width;
    PlayGround pg;
    int screen_width;
    int screen_hight;
    int start_position_x;
    double prozenthight = 0.1;
    int hight_distance;
    Music music;
    int ytexturesizes;
    int startx;
    int heightandwidthperfield;
    int halffinalprozent;

    @Override
    public void create(){
        batch = new SpriteBatch();
        music = Gdx.audio.newMusic(Gdx.files.internal("Epic Suspense.mp3"));
        music.setLooping(true);
        music.play();
        Functions.generateRandomeField();
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
        startx = q / 2;
    }
    public void draw(){

        batch.begin();
        int x = 1;
        int xx = startx;
        int yy = halffinalprozent;
        for (GameField gf : gameFields){
            if (gf.x == x && gf.y <= 5){
                batch.draw(gf.fieldTextureRegion, yy, xx, heightandwidthperfield, heightandwidthperfield);
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

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
