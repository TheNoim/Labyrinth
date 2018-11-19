package io.noim.daslabyrinth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;


public class Save extends Page {

    public DasLabyrinth main;

    SpriteBatch batch;
    OrthographicCamera camera;
    private Texture background;
    private BitmapFont font_heading;


    public Save(final DasLabyrinth main) {
        this.main = main;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        background = new Texture("background.png");
        font_heading = new BitmapFont(Gdx.files.internal("Labyrinth.fnt"));
    }

    void create() {
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        exit();
    }

    void draw() {
        batch.begin();
        font_heading.draw(batch, "SAVING...", 0, Gdx.graphics.getHeight() / 2, Gdx.graphics.getWidth(), 1, false);
        batch.end();
    }

    @Override
    void update() {

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


    private void exit() {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (DasLabyrinth.whichClass == 0) {
                    main.setScreen(main.startMenu);
                } else if (DasLabyrinth.whichClass == 1) {
                    main.setScreen(main.playground);
                }
            }
        }, 0.25F);

    }
}
