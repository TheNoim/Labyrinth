package io.noim.daslabyrinth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Animation extends Page {
    private OrthographicCamera camera;
    private Texture banner;
    private String heading = "DAS LABYRINTH";
    private SpriteBatch batch;
    private boolean animate = false;

    public DasLabyrinth main;

    Animation(final DasLabyrinth main) {
        this.main = main;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        this.banner = new Texture("banner.png");
    }

    void create() {
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (!this.main.getMusic().isPlaying() && DasLabyrinth.playMusic) {
            this.main.getMusic().play();
        } else {
            this.main.getMusic().stop();
        }
    }

    private double yAnim = 0.5;
    private double yDelta = 0.05;

    void draw() {
        batch.begin();

        batch.draw(this.main.getBackground(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (Gdx.input.justTouched()) {
            animate = true;
        }
        if (animate) {
            yAnim += yDelta; // += 0.005;
            yDelta *= 0.9;
            batch.draw(banner, 0, ((float) (yAnim - 0.1685) * Gdx.graphics.getHeight()), Gdx.graphics.getWidth(), 0.2F * Gdx.graphics.getHeight());

            this.main.getFont().draw(batch, heading, 0, (float) yAnim * Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), 1, false);
            if (yAnim >= 0.9185) {
                main.setScreen(main.startMenu);
            }
        } else {
            batch.draw(banner, 0, 0.3315F * Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), 0.2F * Gdx.graphics.getHeight());
            this.main.getFont().draw(batch, heading, 0, 0.5F * Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), 1, false);
        }
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
}
