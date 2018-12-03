package io.noim.daslabyrinth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class Ranking extends Page {

    public DasLabyrinth main;
    String heading = "RANKING";
    private Texture background, back;
    private BitmapFont font;

    public Ranking(final DasLabyrinth main) {
        this.main = main;
        background = new Texture("background.png");
        back = new Texture("back.png");
        font = new BitmapFont(Gdx.files.internal("Labyrinth.fnt"));
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
    void draw(SpriteBatch batch) {
        batch.draw(background, 0, 0, (float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight());
        batch.draw(back, Gdx.graphics.getWidth() / 20f, (Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 30f) - (back.getHeight() / 3f), Gdx.graphics.getWidth() / 10f, Gdx.graphics.getWidth() / 10f);
        font.draw(batch, heading, 0, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 10f, Gdx.graphics.getWidth(), 1, false);
    }

    @Override
    public void dispose() {
        super.dispose();
        this.background.dispose();
        this.back.dispose();
        this.font.dispose();
    }

    public void callClass() {
        if (DasLabyrinth.whichClass == 0) {
            this.main.getMusic().stop();
            main.setScreen(main.startMenu);
        } else if (DasLabyrinth.whichClass == 1) {
            this.main.getMusic().stop();
            main.setScreen(main.playground);
        }
    }
}
