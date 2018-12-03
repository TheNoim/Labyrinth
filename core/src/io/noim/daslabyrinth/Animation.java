package io.noim.daslabyrinth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class Animation extends Page {
    public DasLabyrinth main;
    private Texture banner;
    private String heading = "DAS LABYRINTH";
    private boolean animate = false;
    private double yAnim = 0.5;
    private double yDelta = 0.05;

    Animation(final DasLabyrinth main) {
        this.main = main;
        this.banner = new Texture("banner.png");
    }

    @Override
    void create() {
        if (!this.main.getMusic().isPlaying() && DasLabyrinth.playMusic) {
            this.main.getMusic().play();
        } else {
            this.main.getMusic().stop();
        }
    }

    @Override
    void draw(SpriteBatch batch) {
        batch.draw(this.main.getBackground(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (animate) {
            yAnim += yDelta; // += 0.005;
            yDelta *= 0.9;

            if (yAnim >= 0.9185) {
                main.setScreen(main.startMenu);
            }
            batch.draw(banner, 0, ((float) (yAnim - 0.1685) * Gdx.graphics.getHeight()), Gdx.graphics.getWidth(), 0.2F * Gdx.graphics.getHeight());
            this.main.getFont().draw(batch, heading, 0, (float) yAnim * Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), 1, false);
        } else {
            batch.draw(banner, 0, 0.3315F * Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), 0.2F * Gdx.graphics.getHeight());
            this.main.getFont().draw(batch, heading, 0, 0.5F * Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), 1, false);
        }
    }

    @Override
    void update() {
    }

    @Override
    void touch(Vector3 touchPosition) {
        animate = true;
    }

    @Override
    public void dispose() {
        super.dispose();
        this.banner.dispose();
    }
}
