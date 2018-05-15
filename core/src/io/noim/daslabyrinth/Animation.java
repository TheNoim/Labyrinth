package io.noim.daslabyrinth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Animation implements Screen {
    OrthographicCamera camera;
    Texture banner = new Texture("banner.png");
    String heading = "DAS LABYRINTH";
    SpriteBatch batch;
    Boolean animate = false;

    public DasLabyrinth main;

    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (!DasLabyrinth.music.isPlaying() && DasLabyrinth.playMusic) {
            DasLabyrinth.music.play();
        } else {
            DasLabyrinth.music.stop();
        }
    }

    private void update() {

    }

    public Animation(final DasLabyrinth main) {
        create();
        this.main = main;
    }

    double yAnim = 0.5;
    double yDelta = 0.05;

    private void draw() {
        batch.begin();

        batch.draw(DasLabyrinth.background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (Gdx.input.justTouched()) {
            animate = true;
        }
        if (animate) {
            yAnim += yDelta; // += 0.005;
            yDelta *= 0.9;
            batch.draw(banner, 0, ((float) (yAnim - 0.1685) * Gdx.graphics.getHeight()), Gdx.graphics.getWidth(), 0.2F * Gdx.graphics.getHeight());

            DasLabyrinth.font.draw(batch, heading, 0, (float) yAnim * Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), 1, false);
            if (yAnim >= 0.9185) {
                main.setScreen(new StartMenu(main));
            }
        } else {
            batch.draw(banner, 0, 0.3315F * Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), 0.2F * Gdx.graphics.getHeight());
            DasLabyrinth.font.draw(batch, heading, 0, 0.5F * Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), 1, false);
        }
        batch.end();
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update();
        draw();
    }

    public void resize(int width, int height) {
        Functions.scaleWindow();
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
}
