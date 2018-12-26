package io.noim.daslabyrinth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;

public class StartMenu extends Page {

    public DasLabyrinth main;
    private Texture button, buttonPushed, banner;
    private float X;

    private GlyphLayout heading, play, ranking, settings;

    StartMenu(final DasLabyrinth main) {
        this.main = main;
        banner = new Texture("banner.png");
        button = new Texture("button.png");
        buttonPushed = new Texture("button_pushed.png");
        this.heading = new GlyphLayout(this.main.getFont(), this.main.lang.get("heading"), this.main.getFont().getColor(), Gdx.graphics.getWidth(), Align.center, false);
        this.play = new GlyphLayout(this.main.getFont(), this.main.lang.get("play"), this.main.getFont().getColor(), Gdx.graphics.getWidth(), Align.center, false);
        this.ranking = new GlyphLayout(this.main.getFont(), this.main.lang.get("ranking"), this.main.getFont().getColor(), Gdx.graphics.getWidth(), Align.center, false);
        this.settings = new GlyphLayout(this.main.getFont(), this.main.lang.get("settings"), this.main.getFont().getColor(), Gdx.graphics.getWidth(), Align.center, false);
    }

    @Override
    public void create() {
        if (DasLabyrinth.playMusic) {
            if (!this.main.getMusic().isPlaying()) {
                this.main.getMusic().play();
            }
        } else {
            if (this.main.getMusic().isPlaying()) {
                this.main.getMusic().pause();
            }
        }
        X = Gdx.graphics.getWidth() / 2f - DasLabyrinth.ButtonWidth / 2f;
    }

    @Override
    void update() {
    }

    @Override
    void touch(Vector3 touchPosition) {
        if (touchPosition.x >= (Gdx.graphics.getWidth() / 2 - DasLabyrinth.ButtonWidth / 2) && touchPosition.x <= (Gdx.graphics.getWidth() / 2 + DasLabyrinth.ButtonWidth / 2) && touchPosition.y >= (Gdx.graphics.getHeight() / 2 + (play.height * 3)) && touchPosition.y <= (Gdx.graphics.getHeight() / 2 + 2 * (play.height * 3))) {
            this.main.click();
            if (this.main.getMusic().isPlaying()) {
                this.main.getMusic().pause();
            }
            main.setScreen(this.main.playground);
        }
        if (touchPosition.x >= (Gdx.graphics.getWidth() / 2 - DasLabyrinth.ButtonWidth / 2) && touchPosition.x <= (Gdx.graphics.getWidth() / 2 + DasLabyrinth.ButtonWidth / 2) && touchPosition.y >= (Gdx.graphics.getHeight() / 2 - (play.height * 3) / 2) && touchPosition.y <= (Gdx.graphics.getHeight() / 2 + (play.height * 3) / 2)) {
            this.main.click();
            main.setScreen(this.main.ranking);
        }
        if (touchPosition.x >= (Gdx.graphics.getWidth() / 2 - DasLabyrinth.ButtonWidth / 2) && touchPosition.x <= (Gdx.graphics.getWidth() / 2 + DasLabyrinth.ButtonWidth / 2) && touchPosition.y >= (Gdx.graphics.getHeight() / 2 - (play.height * 3) * 2) && touchPosition.y <= (Gdx.graphics.getHeight() / 2 - (play.height * 3))) {
            this.main.click();
            main.setScreen(this.main.settings);
        }
    }

    @Override
    void draw(Batch batch) {
        batch.draw(this.main.getBackground(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batch.draw(banner, 0, (0.75F * Gdx.graphics.getHeight()), Gdx.graphics.getWidth(), 0.2F * Gdx.graphics.getHeight());
        batch.draw(button, X, Gdx.graphics.getHeight() / 2f + (play.height * 3), DasLabyrinth.ButtonWidth, play.height * 3);
        batch.draw(button, X, Gdx.graphics.getHeight() / 2f - (play.height * 3) / 2, DasLabyrinth.ButtonWidth, ranking.height * 3);
        batch.draw(button, X, Gdx.graphics.getHeight() / 2f - (play.height * 3) * 2, DasLabyrinth.ButtonWidth, settings.height * 3);

        this.main.getFont().draw(batch, heading, 0, 0.9185F * Gdx.graphics.getHeight());
        this.main.getFont().draw(batch, play, 0, Gdx.graphics.getHeight() / 2f + 2 * (play.height * 3) - (((play.height * 3) - play.height) / 2));
        this.main.getFont().draw(batch, ranking, 0, Gdx.graphics.getHeight() / 2f + ((ranking.height * 3) / 2) - (((play.height * 3) - ranking.height) / 2));
        this.main.getFont().draw(batch, settings, 0, Gdx.graphics.getHeight() / 2f - (settings.height * 3) - (((play.height * 3) - settings.height) / 2));
    }

    @Override
    public void dispose() {
        super.dispose();
        this.button.dispose();
        this.buttonPushed.dispose();
        this.banner.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        this.main.click();
        if (keycode == Input.Keys.BACK) {
            Gdx.app.exit();
        }
        return false;
    }
}
