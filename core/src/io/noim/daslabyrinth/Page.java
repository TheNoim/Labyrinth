package io.noim.daslabyrinth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class Page implements Screen, InputProcessor {
    private InputMultiplexer multiplexer = new InputMultiplexer();
    private OrthographicCamera camera;
    private SpriteBatch batch;
    Stage stage;
    private Vector3 touchPosition = new Vector3();

    Page() {
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera();
        this.stage = new Stage();
        this.multiplexer.addProcessor(this);
        this.multiplexer.addProcessor(this.stage);
    }

    abstract void create();

    /**
     * @param batch batch for drawing
     */
    abstract void draw(SpriteBatch batch);

    /**
     * to update objects
     * called before #draw
     */
    abstract void update();


    /**
     * @param touchPosition vector of touch positions
     */
    abstract void touch(Vector3 touchPosition);

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.justTouched()) {
            this.touchPosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            this.camera.unproject(this.touchPosition);
            this.touch(this.touchPosition);
        }
        this.update();
        this.batch.begin();
        this.camera.update();
        this.batch.setProjectionMatrix(this.camera.combined);
        this.draw(this.batch);
        this.batch.end();
        this.stage.act();
        this.stage.draw();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this.multiplexer);
        Gdx.input.setCatchBackKey(true);
        this.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.create();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void dispose() {
        this.batch.dispose();
        this.stage.dispose();
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
