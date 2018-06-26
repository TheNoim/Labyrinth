package io.noim.daslabyrinth;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;

public class StartMenu implements Screen, ApplicationListener, InputProcessor {

    SpriteBatch batch;
    OrthographicCamera camera;
    private Texture button, buttonPushed, banner;
    private float X;

    public DasLabyrinth main;

    StartMenu(final DasLabyrinth main) {
        this.main = main;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        banner = new Texture("banner.png");
        button = new Texture("button.png");
        buttonPushed = new Texture("button_pushed.png");
    }

    public void create() {
        DasLabyrinth.pref.flush();
        DasLabyrinth.playMusic = DasLabyrinth.pref.getBoolean("Music", true);
        DasLabyrinth.playSounds = DasLabyrinth.pref.getBoolean("Sounds", true);
        DasLabyrinth.vibration = DasLabyrinth.pref.getBoolean("Vibration", true);

        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (DasLabyrinth.playMusic) {
            DasLabyrinth.music.play();
        } else {
            DasLabyrinth.music.stop();
        }
        X = Gdx.graphics.getWidth() / 2 - DasLabyrinth.ButtonWidth / 2;
    }

    private void update() {
        if (Gdx.input.justTouched()) {
            DasLabyrinth.touchPosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(DasLabyrinth.touchPosition);
            if (DasLabyrinth.touchPosition.x >= (Gdx.graphics.getWidth() / 2 - DasLabyrinth.ButtonWidth / 2) && DasLabyrinth.touchPosition.x <= (Gdx.graphics.getWidth() / 2 + DasLabyrinth.ButtonWidth / 2) && DasLabyrinth.touchPosition.y >= (Gdx.graphics.getHeight() / 2 + (Functions.textHeight(DasLabyrinth.playText, DasLabyrinth.font) * 3)) && DasLabyrinth.touchPosition.y <= (Gdx.graphics.getHeight() / 2 + 2 * (Functions.textHeight(DasLabyrinth.playText, DasLabyrinth.font) * 3))) {
                DasLabyrinth.click();
                DasLabyrinth.music.stop();
                main.setScreen(this.main.playground);
            }
            if (DasLabyrinth.touchPosition.x >= (Gdx.graphics.getWidth() / 2 - DasLabyrinth.ButtonWidth / 2) && DasLabyrinth.touchPosition.x <= (Gdx.graphics.getWidth() / 2 + DasLabyrinth.ButtonWidth / 2) && DasLabyrinth.touchPosition.y >= (Gdx.graphics.getHeight() / 2 - (Functions.textHeight(DasLabyrinth.playText, DasLabyrinth.font) * 3) / 2) && DasLabyrinth.touchPosition.y <= (Gdx.graphics.getHeight() / 2 + (Functions.textHeight(DasLabyrinth.playText, DasLabyrinth.font) * 3) / 2)) {
                DasLabyrinth.click();
                //main.setScreen(this.main.ranking);
            }
            if (DasLabyrinth.touchPosition.x >= (Gdx.graphics.getWidth() / 2 - DasLabyrinth.ButtonWidth / 2) && DasLabyrinth.touchPosition.x <= (Gdx.graphics.getWidth() / 2 + DasLabyrinth.ButtonWidth / 2) && DasLabyrinth.touchPosition.y >= (Gdx.graphics.getHeight() / 2 - (Functions.textHeight(DasLabyrinth.playText, DasLabyrinth.font) * 3) * 2) && DasLabyrinth.touchPosition.y <= (Gdx.graphics.getHeight() / 2 - (Functions.textHeight(DasLabyrinth.playText, DasLabyrinth.font) * 3))) {
                DasLabyrinth.click();
                main.setScreen(this.main.settings);
            }
        }
    }

    private void draw() {
        batch.begin();

        batch.draw(DasLabyrinth.background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batch.draw(banner, 0, (0.75F * Gdx.graphics.getHeight()), Gdx.graphics.getWidth(), 0.2F * Gdx.graphics.getHeight());
        batch.draw(button, X, Gdx.graphics.getHeight() / 2 + (Functions.textHeight(DasLabyrinth.playText, DasLabyrinth.font) * 3), DasLabyrinth.ButtonWidth, Functions.textHeight(DasLabyrinth.playText, DasLabyrinth.font) * 3);
        batch.draw(button, X, Gdx.graphics.getHeight() / 2 - (Functions.textHeight(DasLabyrinth.playText, DasLabyrinth.font) * 3) / 2, DasLabyrinth.ButtonWidth, Functions.textHeight(DasLabyrinth.rankingText, DasLabyrinth.font) * 3);
        batch.draw(button, X, Gdx.graphics.getHeight() / 2 - (Functions.textHeight(DasLabyrinth.playText, DasLabyrinth.font) * 3) * 2, DasLabyrinth.ButtonWidth, Functions.textHeight(DasLabyrinth.settingsText, DasLabyrinth.font) * 3);

        DasLabyrinth.font.draw(batch, DasLabyrinth.heading, 0, 0.9185F * Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), Align.center, false);
        DasLabyrinth.font.draw(batch, DasLabyrinth.playText, 0, Gdx.graphics.getHeight() / 2 + 2 * (Functions.textHeight(DasLabyrinth.playText, DasLabyrinth.font) * 3) - (((Functions.textHeight(DasLabyrinth.playText, DasLabyrinth.font) * 3) - Functions.textHeight(DasLabyrinth.playText, DasLabyrinth.font)) / 2), Gdx.graphics.getWidth(), Align.center, false);
        DasLabyrinth.font.draw(batch, DasLabyrinth.rankingText, 0, Gdx.graphics.getHeight() / 2 + ((Functions.textHeight(DasLabyrinth.playText, DasLabyrinth.font) * 3) / 2) - (((Functions.textHeight(DasLabyrinth.playText, DasLabyrinth.font) * 3) - Functions.textHeight(DasLabyrinth.rankingText, DasLabyrinth.font)) / 2), Gdx.graphics.getWidth(), Align.center, false);
        DasLabyrinth.font.draw(batch, DasLabyrinth.settingsText, 0, Gdx.graphics.getHeight() / 2 - (Functions.textHeight(DasLabyrinth.playText, DasLabyrinth.font) * 3) - (((Functions.textHeight(DasLabyrinth.playText, DasLabyrinth.font) * 3) - Functions.textHeight(DasLabyrinth.settingsText, DasLabyrinth.font)) / 2), Gdx.graphics.getWidth(), Align.center, false);

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

    @Override
    public void render() {

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

    @Override
    public boolean keyDown(int keycode) {
        DasLabyrinth.click();
        if (keycode == Input.Keys.BACK) {
            Gdx.app.exit();
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
