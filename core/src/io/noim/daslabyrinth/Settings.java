package io.noim.daslabyrinth;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Settings extends ApplicationAdapter {
    SpriteBatch batch;
    OrthographicCamera camera;
    Texture background;
    Music music;
    BitmapFont font;
    Color font_color;
    String heading = "TEST";


    @Override
    public void create(){
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //background = new Texture("");
        music = Gdx.audio.newMusic(Gdx.files.internal("Spooky Fun.mp3"));
        music.setLooping(true);
        music.play();
        font = new BitmapFont(Gdx.files.internal("Labyrinth.fnt"));
        float r = 119;
        float g = 179;
        float b = 212;
        float a = 0;
        font_color = new Color(r, g, b, a);
        font.setColor(font_color);
    }

    public void update() {

    }

    public void draw() {
        batch.draw(background, camera.position.x - background.getWidth() / 2, 0);
        font.draw(batch, heading, Gdx.graphics.getWidth() / 2 - StartMenu.textWidth(heading, font), Gdx.graphics.getHeight() - StartMenu.textHeight(heading, font) - 200);

    }

    public void render() {


        update();
        draw();
    }
}
