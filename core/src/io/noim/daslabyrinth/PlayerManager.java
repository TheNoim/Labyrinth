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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

public class PlayerManager implements Screen, InputProcessor {

    Array<Player> players = new Array<Player>();
    private int activePlayer = 0;
    private GameField startField;

    //For the screen
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Texture background;
    private Texture button;
    private Texture buttonPushed;
    private Texture backButton;
    private BitmapFont textFont;
    private int i = 0;

    private DasLabyrinth dasLabyrinth;
    private Screen previousScreen;

    PlayerManager(GameField startField) {
        this.startField = startField;

        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.batch = new SpriteBatch();
        this.background = new Texture("background.png");
        this.button = new Texture("button.png");
        this.buttonPushed = new Texture("button_pushed.png");
        this.backButton = new Texture("back.png");
        this.textFont = new BitmapFont(Gdx.files.internal("Verdana.fnt"));
        this.textFont.getData().setScale(Functions.scaleText("Vibration", this.textFont, Gdx.graphics.getWidth() / 3));
    }

    void addPlayer(String name, Texture figure) {
        this.players.add(new Player(name, figure, this.startField));
    }

    void moveCurrentPlayer(Playground playground, GameField nextGamefield) {
        if (this.players.get(this.activePlayer).movePlayer(playground, nextGamefield)) {
            this.nextPlayer();
        }
    }

    private void nextPlayer() {
        if (this.activePlayer == this.players.size - 1) {
            this.activePlayer = 0;
        } else {
            this.activePlayer++;
        }
    }

    public void resetAllScores() {
        for (int i = 0; i < players.size; i++) {
            players.get(i).treasures.clear();
            players.get(i).setScore(0);
        }
    }

    void setPreviousScreen(DasLabyrinth dasLabyrinth, Screen screen) {
        this.dasLabyrinth = dasLabyrinth;
        this.previousScreen = screen;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.justTouched()) {
            DasLabyrinth.touchPosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(DasLabyrinth.touchPosition);
            if (DasLabyrinth.touchPosition.x > Gdx.graphics.getWidth() / 20 && DasLabyrinth.touchPosition.x < 3 * Gdx.graphics.getWidth() / 20 && DasLabyrinth.touchPosition.y > 29 * Gdx.graphics.getHeight() / 30 - backButton.getHeight() / 3 && DasLabyrinth.touchPosition.y < 29 * Gdx.graphics.getHeight() / 30 - backButton.getHeight() / 3 + Gdx.graphics.getWidth() / 10) {
                DasLabyrinth.click();
                this.dasLabyrinth.setScreen(this.previousScreen);
            } else if (DasLabyrinth.touchPosition.x > Gdx.graphics.getWidth() / 2 - DasLabyrinth.ButtonWidth / 2 && DasLabyrinth.touchPosition.x < Gdx.graphics.getWidth() / 2 + DasLabyrinth.ButtonWidth / 2 && DasLabyrinth.touchPosition.y > Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 10 * (i + 3) && DasLabyrinth.touchPosition.y < Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 10 * (i + 3) + Functions.textHeight("Spieler hinzufügen", this.textFont) * 3) {
                if (this.players.size < 4) {
                    DasLabyrinth.click();
                    // TODO get a chosen player name and figure and create a new Player
                }
            }
        }

        this.camera.update();
        this.batch.setProjectionMatrix(this.camera.combined);
        this.batch.begin();
        this.batch.draw(this.background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.batch.draw(this.backButton, Gdx.graphics.getWidth() / 20, 29 * Gdx.graphics.getHeight() / 30 - backButton.getHeight() / 3, Gdx.graphics.getWidth() / 10, Gdx.graphics.getWidth() / 10);
        DasLabyrinth.font.draw(batch, "Spieler", 0, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 10, Gdx.graphics.getWidth(), 1, false);
        for (; i < this.players.size; i++) {
            this.textFont.draw(this.batch, this.players.get(i).name, Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 10 * (i + 2));
        }
        if (this.players.size < 4) {
            this.batch.draw(this.button, Gdx.graphics.getWidth() / 2 - DasLabyrinth.ButtonWidth / 2, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 10 * (i + 3), DasLabyrinth.ButtonWidth, Functions.textHeight("Spieler hinzufügen", this.textFont) * 3);
            this.textFont.draw(this.batch, "Spieler hinzufügen", 0, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 10 * (i + 3) + Functions.textHeight("Spieler hinzufügen", this.textFont) * 2, Gdx.graphics.getWidth(), Align.center, false);
        }
        this.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.BACK:
                this.dasLabyrinth.setScreen(this.previousScreen);
                break;
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