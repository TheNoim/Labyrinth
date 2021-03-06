package io.noim.daslabyrinth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector3;

public class Animation extends Page {
    public DasLabyrinth main;
    private Texture banner;
    private boolean animate = false;
    private float yAnim = 0.5F;
    private float yDelta = 0.05F;

    Animation(final DasLabyrinth main) {
        this.main = main;
        this.banner = new Texture("banner.png");
    }

    @Override
    void create() {
    }

    @Override
    void draw(Batch batch) {
        batch.draw(this.main.getBackground(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(banner, 0, (yAnim - 0.1685F) * Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), 0.2F * Gdx.graphics.getHeight());
        this.main.getFont().draw(batch, this.main.lang.get("heading"), 0, yAnim * Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), 1, false);
    }

    @Override
    void update() {
        if (animate) {
            yAnim += yDelta; // += 0.005;
            yDelta *= 0.9F;
        }
        if (yAnim >= 0.9185F) {
            main.setScreen(main.startMenu);
        }
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
