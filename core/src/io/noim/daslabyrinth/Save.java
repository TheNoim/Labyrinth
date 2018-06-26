package io.noim.daslabyrinth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;


public class Save implements Screen {

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

    private void create() {
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        exit();
    }

    private void draw() {
        batch.begin();
        font_heading.draw(batch, "SAVING...", 0, Gdx.graphics.getHeight() / 2, Gdx.graphics.getWidth(), 1, false);
        batch.end();
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        draw();
    }

    public void resize(int width, int height) {

    }

    public void show() {
        create();
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
