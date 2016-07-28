package io.noim.daslabyrinth;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;


public class StartMenu implements Screen, ApplicationListener, InputProcessor {

    SpriteBatch batch;
    OrthographicCamera camera;
    Texture button, button_pushed, banner;
    String heading, play, ranking, settings;
    int ButtonWidth;
    float X;

    public DasLabyrinth main;
    public Settings Settings;
    public Ranking Ranking;

    public StartMenu(final DasLabyrinth main) {
        create();
        this.main = main;
    }

    public StartMenu(final Settings Settings) {
        this.Settings = Settings;
    }

    public StartMenu(final Ranking Ranking) {
        this.Ranking = Ranking;
    }

    public void create() {
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        ButtonWidth = (int) (Gdx.graphics.getWidth() * (float) 0.9);

        if (!DasLabyrinth.music.isPlaying() && DasLabyrinth.playMusic) {
            DasLabyrinth.music.play();
        } else {
            DasLabyrinth.music.stop();
        }

        banner = new Texture("banner.png");
        button = new Texture("button.png");
        button_pushed = new Texture("button_pushed.png");
        heading = "DAS LABYRINTH";
        play = "SPIEL STARTEN";
        ranking = "RANKING";
        settings = "EINSTELLUNGEN";
        X = Gdx.graphics.getWidth() / 2 - ButtonWidth / 2;
        DasLabyrinth.font.getData().setScale(scaleText(settings, DasLabyrinth.font, ButtonWidth - 40));
    }

    private void update() {
        if (Gdx.input.justTouched()) {
            DasLabyrinth.touchPosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(DasLabyrinth.touchPosition);
            if (DasLabyrinth.touchPosition.x >= (Gdx.graphics.getWidth() / 2 - ButtonWidth / 2) && DasLabyrinth.touchPosition.x <= (Gdx.graphics.getWidth() / 2 + ButtonWidth / 2) && DasLabyrinth.touchPosition.y >= (Gdx.graphics.getHeight() / 2 + (textHeight(play, DasLabyrinth.font) * 3)) && DasLabyrinth.touchPosition.y <= (Gdx.graphics.getHeight() / 2 + 2 * (textHeight(play, DasLabyrinth.font) * 3))) {
                DasLabyrinth.click();
                DasLabyrinth.music.stop();
                main.setScreen(new Playground(main));
            }
            if (DasLabyrinth.touchPosition.x >= (Gdx.graphics.getWidth() / 2 - ButtonWidth / 2) && DasLabyrinth.touchPosition.x <= (Gdx.graphics.getWidth() / 2 + ButtonWidth / 2) && DasLabyrinth.touchPosition.y >= (Gdx.graphics.getHeight() / 2 - (textHeight(play, DasLabyrinth.font) * 3) / 2) && DasLabyrinth.touchPosition.y <= (Gdx.graphics.getHeight() / 2 + (textHeight(play, DasLabyrinth.font) * 3) / 2)) {
                DasLabyrinth.click();
                //main.setScreen(new Ranking(main));
            }
            if (DasLabyrinth.touchPosition.x >= (Gdx.graphics.getWidth() / 2 - ButtonWidth / 2) && DasLabyrinth.touchPosition.x <= (Gdx.graphics.getWidth() / 2 + ButtonWidth / 2) && DasLabyrinth.touchPosition.y >= (Gdx.graphics.getHeight() / 2 - (textHeight(play, DasLabyrinth.font) * 3) * 2) && DasLabyrinth.touchPosition.y <= (Gdx.graphics.getHeight() / 2 - (textHeight(play, DasLabyrinth.font) * 3))) {
                DasLabyrinth.click();
                main.setScreen(new Settings(main));
            }
        }
    }

    public static float textWidth(String largestText, BitmapFont font) {
        GlyphLayout layout = new GlyphLayout(); //don't do this every frame! Store it as member
        layout.setText(font, largestText);
        float width = layout.width;// contains the width of the current set text
        return width;
    }

    public static float textHeight(String text, BitmapFont font) {
        GlyphLayout layout = new GlyphLayout(); //don't do this every frame! Store it as member
        layout.setText(font, text);
        float height = layout.height; // contains the height of the current set text
        return height;
    }

    public static float scaleText(String text, BitmapFont font, int targetWidth) {
        float textWidth = textWidth(text, font);
        float proportion = targetWidth / textWidth;
        return proportion;
    }

    private void draw() {
        batch.begin();

        batch.draw(DasLabyrinth.background, 0, 0, (float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight());

        batch.draw(banner, 0, ((float) 0.75 * Gdx.graphics.getHeight()), Gdx.graphics.getWidth(), (float) 0.2 * Gdx.graphics.getHeight());
        batch.draw(button, X, Gdx.graphics.getHeight() / 2 + (textHeight(play, DasLabyrinth.font) * 3), ButtonWidth, textHeight(play, DasLabyrinth.font) * 3);
        batch.draw(button, X, Gdx.graphics.getHeight() / 2 - (textHeight(play, DasLabyrinth.font) * 3) / 2, ButtonWidth, textHeight(ranking, DasLabyrinth.font) * 3);
        batch.draw(button, X, Gdx.graphics.getHeight() / 2 - (textHeight(play, DasLabyrinth.font) * 3) * 2, ButtonWidth, textHeight(settings, DasLabyrinth.font) * 3);

        DasLabyrinth.font.draw(batch, heading, 0, (float) 0.9185 * Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), 1, false);
        DasLabyrinth.font.draw(batch, play, X, Gdx.graphics.getHeight() / 2 + 2 * (textHeight(play, DasLabyrinth.font) * 3) - (((textHeight(play, DasLabyrinth.font) * 3) - textHeight(play, DasLabyrinth.font)) / 2), ButtonWidth, 1, false);
        DasLabyrinth.font.draw(batch, ranking, X, Gdx.graphics.getHeight() / 2 + ((textHeight(play, DasLabyrinth.font) * 3) / 2) - (((textHeight(play, DasLabyrinth.font) * 3) - textHeight(ranking, DasLabyrinth.font)) / 2), ButtonWidth, 1, false);
        DasLabyrinth.font.draw(batch, settings, X, Gdx.graphics.getHeight() / 2 - (textHeight(play, DasLabyrinth.font) * 3) - (((textHeight(play, DasLabyrinth.font) * 3) - textHeight(settings, DasLabyrinth.font)) / 2), ButtonWidth, 1, false);

        batch.end();
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
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

    public void callClass() {
        if (DasLabyrinth.whichClass == 0) {
            DasLabyrinth.music.stop();
            main.setScreen(new StartMenu(main));
        } else if (DasLabyrinth.whichClass == 1) {
            DasLabyrinth.music.stop();
            main.setScreen(new Playground(main));
        }
    }
}
