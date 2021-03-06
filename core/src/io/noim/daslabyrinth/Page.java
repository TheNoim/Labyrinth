package io.noim.daslabyrinth;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public abstract class Page implements Screen, InputProcessor {
    Stage stage;
    private final InputMultiplexer multiplexer = new InputMultiplexer();
    private final Vector3 touchPosition = new Vector3();

    Page() {
        this.stage = new Stage(new ScreenViewport());
        this.multiplexer.addProcessor(this);
        this.multiplexer.addProcessor(this.stage);
    }

    abstract void create();

    /**
     * @param batch batch for drawing
     */
    abstract void draw(Batch batch);

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
            this.stage.getCamera().unproject(this.touchPosition);
            this.touch(this.touchPosition);
        }
        this.update();

        this.stage.getBatch().begin();
        this.draw(this.stage.getBatch());
        this.stage.getBatch().end();

        this.stage.act(Gdx.graphics.getDeltaTime());
        this.stage.draw();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this.multiplexer);
		Gdx.input.setCatchKey(Input.Keys.BACK, true);
        this.create();
    }

    @Override
    public void resize(int width, int height) {
        this.stage.getViewport().update(width, height, true);
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
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
