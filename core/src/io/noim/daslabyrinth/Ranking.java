package io.noim.daslabyrinth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector3;

public class Ranking extends Page {

    public DasLabyrinth main;
    private Texture back;

    public Ranking(final DasLabyrinth main) {
        this.main = main;
        back = new Texture("back.png");
    }

    @Override
    void create() {
    }

    @Override
    void update() {
    }

    @Override
    void touch(Vector3 touchPosition) {
        if (touchPosition.x >= Gdx.graphics.getWidth() / 20 - (Gdx.graphics.getWidth() / 10 / 5) &&
                touchPosition.x <= Gdx.graphics.getWidth() / 20 + (Gdx.graphics.getWidth() / 10 / 5 * 6) &&
                touchPosition.y >= (Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 30) - (back.getHeight() / 3) - (Gdx.graphics.getWidth() / 10 / 5) &&
                touchPosition.y <= (Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 30) - (back.getHeight() / 3) + (Gdx.graphics.getWidth() / 10 / 5 * 6)) {
            this.main.click();
            callClass();
        }
    }

    @Override
    void draw(Batch batch) {
        batch.draw(main.getBackground(), 0, 0, (float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight());
        batch.draw(back, Gdx.graphics.getWidth() / 20f, (Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 30f) - (back.getHeight() / 3f), Gdx.graphics.getWidth() / 10f, Gdx.graphics.getWidth() / 10f);
        main.getFont().draw(batch, this.main.lang.get("ranking"), 0, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 10f, Gdx.graphics.getWidth(), 1, false);
    }

    @Override
    public void dispose() {
        super.dispose();
        this.back.dispose();
    }

    public void callClass() {
        if (DasLabyrinth.whichClass == 0) {
            main.setScreen(main.startMenu);
        } else if (DasLabyrinth.whichClass == 1) {
            if (this.main.getMusic().isPlaying()) {
                this.main.getMusic().pause();
            }
            main.setScreen(main.playground);
        }
    }
}
