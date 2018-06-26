package io.noim.daslabyrinth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;


public class Settings implements Screen, InputProcessor {

    public DasLabyrinth main;

    SpriteBatch batch;
    OrthographicCamera camera;
    private Texture background;
    private BitmapFont font_text;
    String heading = "SETTINGS";
    Vector3 touchPosition = new Vector3();

    private Texture checkBox, checkBox_checked;
    private Texture back;
    boolean checkBox1Checked, checkBox2Checked, checkBox3Checked;
    float checkBoxPosX;
    float checkBoxPos1Y;
    float checkBoxPos2Y;
    float checkBoxPos3Y;
    float checkBoxSize;

    Settings(final DasLabyrinth main) {
        this.main = main;
        font_text = new BitmapFont(Gdx.files.internal("Verdana.fnt"));
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        background = new Texture("background.png");
        checkBox_checked = new Texture("checkbox_checked.png");
        checkBox = new Texture("checkbox.png");
    }


    private void create() {
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);

        DasLabyrinth.pref.flush();
        DasLabyrinth.playMusic = DasLabyrinth.pref.getBoolean("Music", true);
        DasLabyrinth.playSounds = DasLabyrinth.pref.getBoolean("Sounds", true);
        DasLabyrinth.vibration = DasLabyrinth.pref.getBoolean("Vibration", true);

        font_text.getData().setScale(Functions.scaleText("Vibration", font_text, Gdx.graphics.getWidth() / 3));
        checkBoxSize = Gdx.graphics.getWidth() / 10;
        checkBoxPosX = Gdx.graphics.getWidth() / 10;
        checkBoxPos1Y = Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 10 * 3;
        checkBoxPos2Y = Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 10 * 4;
        checkBoxPos3Y = Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 10 * 5;
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        checkBox1Checked = DasLabyrinth.playMusic;
        checkBox2Checked = DasLabyrinth.playSounds;
        checkBox3Checked = DasLabyrinth.vibration;
        back = new Texture("back.png");
    }

    private void update() {
        if (Gdx.input.justTouched()) {
            touchPosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPosition);
            if (touchPosition.x >= checkBoxPosX - (checkBoxSize / 5) &&
                    touchPosition.x <= checkBoxPosX + (checkBoxSize / 5 * 6) &&
                    touchPosition.y >= checkBoxPos1Y - (checkBoxSize / 5) &&
                    touchPosition.y <= checkBoxPos1Y + (checkBoxSize / 5 * 6)) {
                if (!checkBox1Checked) {
                    if (DasLabyrinth.whichClass == 0) {
                        DasLabyrinth.music.play();
                    } else if (DasLabyrinth.whichClass == 1) {
                        Playground.music.play();
                    }
                    checkBox1Checked = true;
                    DasLabyrinth.pref.putBoolean("Music", true);
                    DasLabyrinth.pref.flush();
                    DasLabyrinth.click();
                } else {
                    if (DasLabyrinth.whichClass == 0) {
                        DasLabyrinth.music.pause();
                    } else if (DasLabyrinth.whichClass == 1) {
                        Playground.music.pause();
                    }
                    checkBox1Checked = false;
                    DasLabyrinth.pref.putBoolean("Music", false);
                    DasLabyrinth.pref.flush();
                    DasLabyrinth.click();
                }
            }
            if (touchPosition.x >= checkBoxPosX - (checkBoxSize / 5) &&
                    touchPosition.x <= checkBoxPosX + (checkBoxSize / 5 * 6) &&
                    touchPosition.y >= checkBoxPos2Y - (checkBoxSize / 5) &&
                    touchPosition.y <= checkBoxPos2Y + (checkBoxSize / 5 * 6)) {
                if (!checkBox2Checked) {
                    checkBox2Checked = true;
                    DasLabyrinth.pref.putBoolean("Sounds", true);
                    DasLabyrinth.pref.flush();
                    DasLabyrinth.click();
                } else {
                    checkBox2Checked = false;
                    DasLabyrinth.pref.putBoolean("Sounds", false);
                    DasLabyrinth.pref.flush();
                    DasLabyrinth.click();
                }
            }
            if (touchPosition.x >= checkBoxPosX - (checkBoxSize / 5) &&
                    touchPosition.x <= checkBoxPosX + (checkBoxSize / 5 * 6) &&
                    touchPosition.y >= checkBoxPos3Y - (checkBoxSize / 5) &&
                    touchPosition.y <= checkBoxPos3Y + (checkBoxSize / 5 * 6)) {
                if (!checkBox3Checked) {
                    checkBox3Checked = true;
                    DasLabyrinth.pref.putBoolean("Vibration", true);
                    DasLabyrinth.pref.flush();
                    DasLabyrinth.click();
                } else {
                    checkBox3Checked = false;
                    DasLabyrinth.pref.putBoolean("Vibration", false);
                    DasLabyrinth.pref.flush();
                    DasLabyrinth.click();
                }
            }
            if (touchPosition.x >= Gdx.graphics.getWidth() / 20 - (checkBoxSize / 5) &&
                    touchPosition.x <= Gdx.graphics.getWidth() / 20 + (checkBoxSize / 5 * 6) &&
                    touchPosition.y >= (Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 30) - (back.getHeight() / 3) - (checkBoxSize / 5) &&
                    touchPosition.y <= (Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 30) - (back.getHeight() / 3) + (checkBoxSize / 5 * 6)) {
                DasLabyrinth.click();
                callClass();
            }
        }
    }

    private void draw() {
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        if (checkBox1Checked) {
            batch.draw(checkBox_checked, checkBoxPosX, checkBoxPos1Y, checkBoxSize, checkBoxSize);
        } else {
            batch.draw(checkBox, checkBoxPosX, checkBoxPos1Y, checkBoxSize, checkBoxSize);
        }
        if (checkBox2Checked) {
            batch.draw(checkBox_checked, checkBoxPosX, checkBoxPos2Y, checkBoxSize, checkBoxSize);
        } else {
            batch.draw(checkBox, checkBoxPosX, checkBoxPos2Y, checkBoxSize, checkBoxSize);
        }
        if (checkBox3Checked) {
            batch.draw(checkBox_checked, checkBoxPosX, checkBoxPos3Y, checkBoxSize, checkBoxSize);
        } else {
            batch.draw(checkBox, checkBoxPosX, checkBoxPos3Y, checkBoxSize, checkBoxSize);
        }
        batch.draw(back, Gdx.graphics.getWidth() / 20, (Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 30) - (back.getHeight() / 3), Gdx.graphics.getWidth() / 10, Gdx.graphics.getWidth() / 10);
        DasLabyrinth.font.draw(batch, heading, 0, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 10, Gdx.graphics.getWidth(), 1, false);
        font_text.draw(batch, "Musik", checkBoxPosX + (Gdx.graphics.getWidth() / 5), (checkBoxPos1Y + Gdx.graphics.getWidth() / 10) - Gdx.graphics.getWidth() / 40);
        font_text.draw(batch, "Sounds", checkBoxPosX + (Gdx.graphics.getWidth() / 5), (checkBoxPos2Y + Gdx.graphics.getWidth() / 10) - Gdx.graphics.getWidth() / 40);
        font_text.draw(batch, "Vibration", checkBoxPosX + (Gdx.graphics.getWidth() / 5), (checkBoxPos3Y + Gdx.graphics.getWidth() / 10) - Gdx.graphics.getWidth() / 40);
        batch.end();
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update();
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

    private void callClass() {
        DasLabyrinth.music.stop();
        main.setScreen(new Save(main));
    }

    @Override
    public boolean keyDown(int keycode) {
        DasLabyrinth.click();
        if (keycode == Input.Keys.BACK) {
            callClass();
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
