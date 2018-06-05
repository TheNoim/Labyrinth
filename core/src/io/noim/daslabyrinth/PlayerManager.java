package io.noim.daslabyrinth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
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
    private Vector3 touch = new Vector3();

    private DasLabyrinth dasLabyrinth;
    private Screen previousScreen;

    PlayerManager(GameField startField) {
        this.startField = startField;
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

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.camera.update();
        this.batch.setProjectionMatrix(this.camera.combined);
        this.batch.begin();
        this.batch.draw(this.background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.batch.draw(this.backButton, Gdx.graphics.getWidth() / 20, (Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 30) - (backButton.getHeight() / 3), Gdx.graphics.getWidth() / 10, Gdx.graphics.getWidth() / 10);
        DasLabyrinth.font.draw(batch, "Spieler", 0, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 10, Gdx.graphics.getWidth(), 1, false);
        int i = 0;
        for (; i < this.players.size; i++) {
            this.textFont.draw(this.batch, this.players.get(i).name, Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 10 * (i + 2));
        }
        this.batch.draw(this.button, Gdx.graphics.getWidth() / 2 - DasLabyrinth.ButtonWidth / 2, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 10 * (i + 3), DasLabyrinth.ButtonWidth, Functions.textHeight(DasLabyrinth.settings, DasLabyrinth.font) * 3);
        if (this.players.size < 4) {
            this.textFont.draw(batch, "Spieler hinzufügen", 0, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 10 * (i + 3) + Functions.textHeight(DasLabyrinth.settings, DasLabyrinth.font) * 2, DasLabyrinth.ButtonWidth, Align.center, false);
        }
        this.batch.end();
        //TODO Add button, back button
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