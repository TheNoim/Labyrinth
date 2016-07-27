package io.noim.daslabyrinth;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;


public class StartMenu extends ApplicationAdapter{
    SpriteBatch batch;
    OrthographicCamera camera;
    Texture background;
    Music music;
    BitmapFont font;
    Texture button;
    Texture button_pushed;
    Color font_color;
    String heading;
    String play;
    String ranking;
    String settings;
    Vector3 touchPosition = new Vector3();

    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //background = new Texture("");
        music = Gdx.audio.newMusic(Gdx.files.internal("Spooky Fun.mp3"));
        music.setLooping(true);
        music.play();

        font = new BitmapFont(Gdx.files.internal("Labyrinth.fnt"));
        font_color = new Color(119, 179, 212, 1);
        font.setColor(font_color);
        button = new Texture("button.png");
        button_pushed = new Texture("button_pushed.png");
        heading = "Das verrückte\nLabyrinth";
        play = "SPIEL STARTEN";
        ranking = "RANKING";
        settings = "EINSTELLUNGEN";
    }

    private void update() {
        if(Gdx.input.justTouched()) {
            touchPosition.set(Gdx.input.getX(),Gdx.input.getY(), 0);
            camera.unproject(touchPosition);
            if(touchPosition.x >= (Gdx.graphics.getWidth() / 2 - button.getWidth() / 2) &&  touchPosition.x <= (Gdx.graphics.getWidth() / 2 + button.getWidth() / 2) && touchPosition.y >= (Gdx.graphics.getHeight() / 2 + button.getHeight()) && touchPosition.y <= (Gdx.graphics.getHeight() / 2 + 2 * button.getHeight())){
                music.pause();

            }
        }
    }

    private void draw() {
        batch.begin();

        //batch.draw(background, camera.position.x - background.getWidth() / 2, 0);
        font.draw(batch, heading, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()  - 200);

        batch.draw(button, Gdx.graphics.getWidth() / 2 - button.getWidth() / 2, Gdx.graphics.getHeight() / 2 + button.getHeight());
        batch.draw(button, Gdx.graphics.getWidth() / 2 - button.getWidth() / 2, Gdx.graphics.getHeight() / 2 - button.getHeight() / 2);
        batch.draw(button, Gdx.graphics.getWidth() / 2 - button.getWidth() / 2, Gdx.graphics.getHeight() / 2 - button.getHeight() * 2);

        font.draw(batch, play, Gdx.graphics.getWidth() / 2 - button.getWidth() / 2, Gdx.graphics.getHeight() / 2 + 2 * button.getHeight() - ((button.getHeight() - 46) /2));
        font.draw(batch, ranking, Gdx.graphics.getWidth() / 2 - button.getWidth() / 2, Gdx.graphics.getHeight() / 2 + (button.getHeight() / 2)- ((button.getHeight() - 46) /2));
        font.draw(batch, settings, Gdx.graphics.getWidth() / 2 - button.getWidth() / 2, Gdx.graphics.getHeight() / 2 - button.getHeight() - ((button.getHeight() - 46) /2));

        batch.end();
    }

    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update();
        draw();
    }
}
