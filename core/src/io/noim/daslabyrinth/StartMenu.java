package io.noim.daslabyrinth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;

public class StartMenu extends Page {

    private Texture button, buttonPushed, banner;
    private float X;

    public DasLabyrinth main;

    StartMenu(final DasLabyrinth main) {
        this.main = main;
        banner = new Texture("banner.png");
        button = new Texture("button.png");
        buttonPushed = new Texture("button_pushed.png");
    }

    @Override
    public void create() {
        DasLabyrinth.pref.flush();
        DasLabyrinth.playMusic = DasLabyrinth.pref.getBoolean("Music", true);
        DasLabyrinth.playSounds = DasLabyrinth.pref.getBoolean("Sounds", true);
        DasLabyrinth.vibration = DasLabyrinth.pref.getBoolean("Vibration", true);

        if (DasLabyrinth.playMusic) {
            this.main.getMusic().play();
        } else {
            this.main.getMusic().stop();
        }
        X = Gdx.graphics.getWidth() / 2f - DasLabyrinth.ButtonWidth / 2f;
    }

    @Override
    void update() {
    }

    @Override
    void touch(Vector3 touchPosition) {
        if (touchPosition.x >= (Gdx.graphics.getWidth() / 2 - DasLabyrinth.ButtonWidth / 2) && touchPosition.x <= (Gdx.graphics.getWidth() / 2 + DasLabyrinth.ButtonWidth / 2) && touchPosition.y >= (Gdx.graphics.getHeight() / 2 + (Functions.textHeight(DasLabyrinth.playText, this.main.getFont()) * 3)) && touchPosition.y <= (Gdx.graphics.getHeight() / 2 + 2 * (Functions.textHeight(DasLabyrinth.playText, this.main.getFont()) * 3))) {
            this.main.click();
            this.main.getMusic().stop();
            main.setScreen(this.main.playground);
        }
        if (touchPosition.x >= (Gdx.graphics.getWidth() / 2 - DasLabyrinth.ButtonWidth / 2) && touchPosition.x <= (Gdx.graphics.getWidth() / 2 + DasLabyrinth.ButtonWidth / 2) && touchPosition.y >= (Gdx.graphics.getHeight() / 2 - (Functions.textHeight(DasLabyrinth.playText, this.main.getFont()) * 3) / 2) && touchPosition.y <= (Gdx.graphics.getHeight() / 2 + (Functions.textHeight(DasLabyrinth.playText, this.main.getFont()) * 3) / 2)) {
            this.main.click();
            main.setScreen(this.main.ranking);
        }
        if (touchPosition.x >= (Gdx.graphics.getWidth() / 2 - DasLabyrinth.ButtonWidth / 2) && touchPosition.x <= (Gdx.graphics.getWidth() / 2 + DasLabyrinth.ButtonWidth / 2) && touchPosition.y >= (Gdx.graphics.getHeight() / 2 - (Functions.textHeight(DasLabyrinth.playText, this.main.getFont()) * 3) * 2) && touchPosition.y <= (Gdx.graphics.getHeight() / 2 - (Functions.textHeight(DasLabyrinth.playText, this.main.getFont()) * 3))) {
            this.main.click();
            main.setScreen(this.main.settings);
        }
    }

    @Override
    void draw(SpriteBatch batch) {
        batch.draw(this.main.getBackground(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batch.draw(banner, 0, (0.75F * Gdx.graphics.getHeight()), Gdx.graphics.getWidth(), 0.2F * Gdx.graphics.getHeight());
        batch.draw(button, X, Gdx.graphics.getHeight() / 2f + (Functions.textHeight(DasLabyrinth.playText, this.main.getFont()) * 3), DasLabyrinth.ButtonWidth, Functions.textHeight(DasLabyrinth.playText, this.main.getFont()) * 3);
        batch.draw(button, X, Gdx.graphics.getHeight() / 2f - (Functions.textHeight(DasLabyrinth.playText, this.main.getFont()) * 3) / 2, DasLabyrinth.ButtonWidth, Functions.textHeight(DasLabyrinth.rankingText, this.main.getFont()) * 3);
        batch.draw(button, X, Gdx.graphics.getHeight() / 2f - (Functions.textHeight(DasLabyrinth.playText, this.main.getFont()) * 3) * 2, DasLabyrinth.ButtonWidth, Functions.textHeight(DasLabyrinth.settingsText, this.main.getFont()) * 3);

        this.main.getFont().draw(batch, DasLabyrinth.heading, 0, 0.9185F * Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), Align.center, false);
        this.main.getFont().draw(batch, DasLabyrinth.playText, 0, Gdx.graphics.getHeight() / 2f + 2 * (Functions.textHeight(DasLabyrinth.playText, this.main.getFont()) * 3) - (((Functions.textHeight(DasLabyrinth.playText, this.main.getFont()) * 3) - Functions.textHeight(DasLabyrinth.playText, this.main.getFont())) / 2), Gdx.graphics.getWidth(), Align.center, false);
        this.main.getFont().draw(batch, DasLabyrinth.rankingText, 0, Gdx.graphics.getHeight() / 2f + ((Functions.textHeight(DasLabyrinth.playText, this.main.getFont()) * 3) / 2) - (((Functions.textHeight(DasLabyrinth.playText, this.main.getFont()) * 3) - Functions.textHeight(DasLabyrinth.rankingText, this.main.getFont())) / 2), Gdx.graphics.getWidth(), Align.center, false);
        this.main.getFont().draw(batch, DasLabyrinth.settingsText, 0, Gdx.graphics.getHeight() / 2f - (Functions.textHeight(DasLabyrinth.playText, this.main.getFont()) * 3) - (((Functions.textHeight(DasLabyrinth.playText, this.main.getFont()) * 3) - Functions.textHeight(DasLabyrinth.settingsText, this.main.getFont())) / 2), Gdx.graphics.getWidth(), Align.center, false);
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
