package io.noim.daslabyrinth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class Ranking extends Page {

    SpriteBatch batch;
    public DasLabyrinth main;
    private Texture background, back;
    String heading = "RANKING";
    Vector3 touchPosition = new Vector3();
    OrthographicCamera camera;
    BitmapFont font;

    public Ranking(final DasLabyrinth main) {
        this.main = main;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        background = new Texture("background.png");
        back = new Texture("back.png");
        font = new BitmapFont(Gdx.files.internal("Labyrinth.fnt"));
    }

    void create() {
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    void update() {
        if (Gdx.input.justTouched()) {
            touchPosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPosition);
            if (touchPosition.x >= Gdx.graphics.getWidth() / 20 - (Gdx.graphics.getWidth() / 10 / 5) &&
                    touchPosition.x <= Gdx.graphics.getWidth() / 20 + (Gdx.graphics.getWidth() / 10 / 5 * 6) &&
                    touchPosition.y >= (Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 30) - (back.getHeight() / 3) - (Gdx.graphics.getWidth() / 10 / 5) &&
                    touchPosition.y <= (Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 30) - (back.getHeight() / 3) + (Gdx.graphics.getWidth() / 10 / 5 * 6)) {
                this.main.click();
                callClass();
            }
        }
    }

    void draw() {
        batch.begin();

        batch.draw(background, 0, 0, (float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight());
        batch.draw(back, Gdx.graphics.getWidth() / 20, (Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 30) - (back.getHeight() / 3), Gdx.graphics.getWidth() / 10, Gdx.graphics.getWidth() / 10);
        font.draw(batch, heading, 0, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 10, Gdx.graphics.getWidth(), 1, false);

        batch.end();
    }

    public void resize(int width, int height) {

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
        if (DasLabyrinth.whichClass == 0) {
            this.main.getMusic().stop();
            main.setScreen(new StartMenu(main));
        } else if (DasLabyrinth.whichClass == 1) {
            this.main.getMusic().stop();
            main.setScreen(new Playground(main));
        }
    }
}
