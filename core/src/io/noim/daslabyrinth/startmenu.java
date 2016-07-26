package io.noim.daslabyrinth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StartMenu {
    SpriteBatch batch;
    OrthographicCamera camera;
    Texture background;
    Music music;
    BitmapFont font;

    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        background = new Texture("");
        music = Gdx.audio.newMusic(Gdx.files.internal("Spooky Fun.mp3"));
        music.setLooping(true);
        music.play();

        font = new BitmapFont(Gdx.files.internal("Labyrinth.fnt"));
        float r = 119;
        float g = 179;
        float b = 212;
        float a = 0;
        Color font_color = new Color(r, g, b, a);
        font.setColor(font_color);


    }

    private void draw() {
        Texture button = new Texture("button.png");
        batch.begin();

        batch.draw(background, camera.position.x - background.getWidth() / 2, 0);

        String heading = "Das verrückte\nLabyrinth";
        font.draw(batch, heading, 4, Gdx.graphics.getHeight() - 200);

        batch.draw(button, Gdx.graphics.getWidth() / 2 - button.getWidth() / 2, Gdx.graphics.getHeight() / 2 + button.getHeight());
        batch.draw(button, Gdx.graphics.getWidth() / 2 - button.getWidth() / 2, Gdx.graphics.getHeight() / 2 - button.getHeight() / 2);
        batch.draw(button, Gdx.graphics.getWidth() / 2 - button.getWidth() / 2, Gdx.graphics.getHeight() / 2 - button.getHeight() * (3 / 2));

        font.draw(batch, "SPIEL STARTEN", Gdx.graphics.getWidth() / 2 - button.getWidth() / 2, Gdx.graphics.getHeight() / 2 + button.getHeight());
        font.draw(batch, "RANKING", Gdx.graphics.getWidth() / 2 - button.getWidth() / 2, Gdx.graphics.getHeight() / 2 - button.getHeight() / 2);
        font.draw(batch, "EINSTELLUNGEN", Gdx.graphics.getWidth() / 2 - button.getWidth() / 2, Gdx.graphics.getHeight() / 2 - button.getHeight() * (3 / 2));

        batch.end();
    }
}
