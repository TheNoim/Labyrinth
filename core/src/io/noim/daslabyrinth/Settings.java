package io.noim.daslabyrinth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;


public class Settings extends Page {

    public DasLabyrinth main;
    float checkBoxPosX;
    float checkBoxPos1Y;
    float checkBoxPos2Y;
    float checkBoxPos3Y;
    float checkBoxSize;
    private Texture background;
    private BitmapFont font_text;
    private Texture checkBox, checkBox_checked;
    private Texture back;

    Settings(final DasLabyrinth main) {
        this.main = main;
        font_text = new BitmapFont(Gdx.files.internal("Verdana.fnt"));
        background = new Texture("background.png");
        checkBox_checked = new Texture("checkbox_checked.png");
        checkBox = new Texture("checkbox.png");
    }


    @Override
    void create() {
        font_text.getData().setScale(Functions.scaleText("Vibration", font_text, Gdx.graphics.getWidth() / 3));
        checkBoxSize = Gdx.graphics.getWidth() / 10f;
        checkBoxPosX = Gdx.graphics.getWidth() / 10f;
        checkBoxPos1Y = Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 10f * 3;
        checkBoxPos2Y = Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 10f * 4;
        checkBoxPos3Y = Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 10f * 5;
        back = new Texture("back.png");
    }

    @Override
    void update() {
    }

    @Override
    void touch(Vector3 touchPosition) {
        if (touchPosition.x > checkBoxPosX &&
                touchPosition.x < checkBoxPosX + checkBoxSize) {
            if (touchPosition.y > checkBoxPos1Y &&
                    touchPosition.y < checkBoxPos1Y + checkBoxSize) {
                DasLabyrinth.playMusic = !DasLabyrinth.playMusic;
                DasLabyrinth.pref.putBoolean("Music", DasLabyrinth.playMusic);
                DasLabyrinth.pref.flush();
                if (!DasLabyrinth.playMusic) {
                    this.main.getMusic().pause();
                } else {
                    if (!this.main.getMusic().isPlaying()) {
                        this.main.getMusic().play();
                    }
                }
                this.main.click();
            }
            if (touchPosition.y > checkBoxPos2Y &&
                    touchPosition.y < checkBoxPos2Y + checkBoxSize) {
                DasLabyrinth.playSounds = !DasLabyrinth.playSounds;
                DasLabyrinth.pref.putBoolean("Sounds", DasLabyrinth.playSounds);
                DasLabyrinth.pref.flush();
                this.main.click();
            }
            if (touchPosition.y > checkBoxPos3Y &&
                    touchPosition.y < checkBoxPos3Y + checkBoxSize) {
                DasLabyrinth.vibration = !DasLabyrinth.vibration;
                DasLabyrinth.pref.putBoolean("Vibration", DasLabyrinth.vibration);
                DasLabyrinth.pref.flush();
                this.main.click();
            }
        }
        if (touchPosition.x >= Gdx.graphics.getWidth() / 20f - (checkBoxSize / 5f) &&
                touchPosition.x <= Gdx.graphics.getWidth() / 20f + (checkBoxSize / 5f * 6) &&
                touchPosition.y >= (Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 30f) - (back.getHeight() / 3f) - (checkBoxSize / 5f) &&
                touchPosition.y <= (Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 30f) - (back.getHeight() / 3f) + (checkBoxSize / 5f * 6)) {
            this.main.click();
            callClass();
        }
    }

    @Override
    void draw(Batch batch) {
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        if (DasLabyrinth.playMusic) {
            batch.draw(checkBox_checked, checkBoxPosX, checkBoxPos1Y, checkBoxSize, checkBoxSize);
        } else {
            batch.draw(checkBox, checkBoxPosX, checkBoxPos1Y, checkBoxSize, checkBoxSize);
        }
        if (DasLabyrinth.playSounds) {
            batch.draw(checkBox_checked, checkBoxPosX, checkBoxPos2Y, checkBoxSize, checkBoxSize);
        } else {
            batch.draw(checkBox, checkBoxPosX, checkBoxPos2Y, checkBoxSize, checkBoxSize);
        }
        if (DasLabyrinth.vibration) {
            batch.draw(checkBox_checked, checkBoxPosX, checkBoxPos3Y, checkBoxSize, checkBoxSize);
        } else {
            batch.draw(checkBox, checkBoxPosX, checkBoxPos3Y, checkBoxSize, checkBoxSize);
        }
        batch.draw(back, Gdx.graphics.getWidth() / 20f, (Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 30f) - (back.getHeight() / 3f), Gdx.graphics.getWidth() / 10f, Gdx.graphics.getWidth() / 10f);
        this.main.getFont().draw(batch, this.main.lang.get("settings"), 0, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 10f, Gdx.graphics.getWidth(), 1, false);
        font_text.draw(batch, "Musik", checkBoxPosX + (Gdx.graphics.getWidth() / 5f), (checkBoxPos1Y + Gdx.graphics.getWidth() / 10f) - Gdx.graphics.getWidth() / 40f);
        font_text.draw(batch, "Sounds", checkBoxPosX + (Gdx.graphics.getWidth() / 5f), (checkBoxPos2Y + Gdx.graphics.getWidth() / 10f) - Gdx.graphics.getWidth() / 40f);
        font_text.draw(batch, "Vibration", checkBoxPosX + (Gdx.graphics.getWidth() / 5f), (checkBoxPos3Y + Gdx.graphics.getWidth() / 10f) - Gdx.graphics.getWidth() / 40f);
    }

    private void callClass() {
        this.main.setScreen(new Save(this.main));
    }

    @Override
    public boolean keyDown(int keycode) {
        this.main.click();
        if (keycode == Input.Keys.BACK) {
            callClass();
        }
        return false;
    }
}
