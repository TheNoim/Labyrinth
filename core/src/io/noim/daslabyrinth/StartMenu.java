package io.noim.daslabyrinth;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StartMenu {
    SpriteBatch batch;
    OrthographicCamera camera;
    Texture background;
    Music music;
    BitmapFont font;
    Texture button;
    Color font_color;
    String heading;
    String play;
    String ranking;
    String settings;

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
        font_color = new Color(r, g, b, a);
        font.setColor(font_color);
        button = new Texture("button.png");
        String heading = "Das verrückte\nLabyrinth";
        play = "SPIEL STARTEN";
        ranking = "RANKING";
        settings = "EINSTELLUNGEN";
    }

    public float textWidth(String text, BitmapFont font){
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(font,text);
        return glyphLayout.width;
    }

    public float textHeight(String text, BitmapFont font){
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(font,text);
        return glyphLayout.height;
    }

    private void draw() {
        batch.begin();

        batch.draw(background, camera.position.x - background.getWidth() / 2, 0);

        font.draw(batch, heading, Gdx.graphics.getWidth() / 2 - textWidth(heading, font), Gdx.graphics.getHeight() - textHeight(heading, font) - 200);

        batch.draw(button, Gdx.graphics.getWidth() / 2 - button.getWidth() / 2, Gdx.graphics.getHeight() / 2 + button.getHeight());
        batch.draw(button, Gdx.graphics.getWidth() / 2 - button.getWidth() / 2, Gdx.graphics.getHeight() / 2 - button.getHeight() / 2);
        batch.draw(button, Gdx.graphics.getWidth() / 2 - button.getWidth() / 2, Gdx.graphics.getHeight() / 2 - button.getHeight() * (3 / 2));

        font.draw(batch, play, Gdx.graphics.getWidth() / 2 - button.getWidth() / 2, Gdx.graphics.getHeight() / 2 + button.getHeight());
        font.draw(batch, ranking, Gdx.graphics.getWidth() / 2 - button.getWidth() / 2, Gdx.graphics.getHeight() / 2 - button.getHeight() / 2);
        font.draw(batch, settings, Gdx.graphics.getWidth() / 2 - button.getWidth() / 2, Gdx.graphics.getHeight() / 2 - button.getHeight() * (3 / 2));

        batch.end();
    }
}
