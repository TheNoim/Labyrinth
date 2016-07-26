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
                System.out.println("X" + gameFields.size);
                int temp_width = gf.fieldTextureRegion.getRegionWidth();
                temp_a += temp_width;
            }
        }
        System.out.println("Width:" + temp_a);
        screen_width = Gdx.graphics.getWidth();
        screen_hight = Gdx.graphics.getHeight();
        int temp_b = screen_width - temp_a;
        start_position_x = temp_b / 2;
        double temp_c = screen_hight * prozenthight;
        long temp_d = Math.round(temp_c);
        hight_distance = (int) temp_d;
        int rest = screen_hight - hight_distance;
        ytexturesizes = rest / 4;
    }
    public void draw(){

        batch.begin();
        int x = 1;
        float xf = start_position_x;
        for (GameField gf : gameFields){
            if (x <= 5){
                batch.draw(gf.fieldTextureRegion, xf, hight_distance, 100, ytexturesizes);
                xf += gf.fieldTextureRegion.getRegionWidth();
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
