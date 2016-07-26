package io.noim.daslabyrinth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Array;

public class Playground {

    Array<GameField> gameFields = new Array<GameField>();
    int playground_width;
    PlayGround pg;
    int screen_width;
    int screen_hight;
    int start_position_x;
    double prozenthight = 0.1;
    int hight_distance;
    Music music;

    public void create(){
        music = Gdx.audio.newMusic(Gdx.files.internal("Epic Suspense.mp3"));
        music.setLooping(true);
        music.play();
        Functions.generateRandomeField();
        gameFields = Functions.gameFields;
        int temp_a = 0;
        for (GameField gf : gameFields) {
            if (gf.x == 1){
                int temp_width = gf.fieldTextureRegion.getRegionWidth();
                temp_a += temp_width;
            }
        }
        screen_width = Gdx.graphics.getWidth();
        screen_hight = Gdx.graphics.getHeight();
        int temp_b = screen_width - temp_a;
        start_position_x = temp_b / 2;
        double temp_c = screen_hight * prozenthight;
        long temp_d = Math.round(temp_c);
        hight_distance = (int) temp_d;
    }
    public void draw(){

    }
    public void update(){

    }

    public void render(){

        update();
        draw();
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
