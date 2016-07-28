package io.noim.daslabyrinth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class Ranking implements Screen {

    SpriteBatch batch;
    public DasLabyrinth main;
    Texture background, back;
    Color font_color;
    String heading;
    Vector3 touchPosition = new Vector3();
    OrthographicCamera camera;
    BitmapFont font;

    public Ranking(final DasLabyrinth main) {
        create();
        this.main = main;
    }

    public void create() {
        heading = "RANKING";
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        background = new Texture("background.png");
        back = new Texture("back.png");
        font = new BitmapFont(Gdx.files.internal("Labyrinth.fnt"));
    }

    private void update() {
        if (Gdx.input.justTouched()) {
            touchPosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPosition);
            if(touchPosition.x >= Gdx.graphics.getWidth() / 20 - (Gdx.graphics.getWidth() / 10 / 5) &&
                    touchPosition.x <= Gdx.graphics.getWidth() / 20 + (Gdx.graphics.getWidth() / 10 / 5 * 6) &&
                    touchPosition.y >= (Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 30) - (back.getHeight() / 3) - (Gdx.graphics.getWidth() / 10 / 5) &&
                    touchPosition.y <= (Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 30) - (back.getHeight() / 3) + (Gdx.graphics.getWidth() / 10 / 5 * 6)) {
                StartMenu.click();
                callClass();
            }
        }
    }

    private void draw() {

        batch.begin();

        batch.draw(background, 0, 0, (float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight());
        batch.draw(back, Gdx.graphics.getWidth() / 20, (Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 30) - (back.getHeight() / 3), Gdx.graphics.getWidth() / 10, Gdx.graphics.getWidth() / 10);
        font.draw(batch, heading, 0, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 10, Gdx.graphics.getWidth(), 1, false);

        batch.end();
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update();
        draw();
    }

    public void resize(int width, int height) {
    }

    public void show() {
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

    public void callClass() {
        if (StartMenu.whichClass == 0) {
            StartMenu.music.stop();
            main.setScreen(new Save(main));
        } else if (StartMenu.whichClass == 1) {
            StartMenu.music.stop();
            main.setScreen(new Save(main));
        }
    }
}
