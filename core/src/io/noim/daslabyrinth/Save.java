package io.noim.daslabyrinth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;


public class Save implements Screen {

    public DasLabyrinth main;

    public Save(final DasLabyrinth main){
        this.main = main;
    }
    SpriteBatch batch;
    OrthographicCamera camera;
    Texture background;
    BitmapFont font_heading;
    BitmapFont font_text;
    String heading = "SAVE...";


    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        background = new Texture("background.png");
        font_heading = new BitmapFont(Gdx.files.internal("Labyrinth.fnt"));
        font_text = new BitmapFont(Gdx.files.internal("Verdana.fnt"));
        exit();
    }

    private void update() { }

    private void draw() {
        batch.begin();
        font_heading.draw(batch, heading, 0, Gdx.graphics.getHeight() / 2, Gdx.graphics.getWidth(), 1, false);
        batch.end();
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update();
        draw();
    }
    public void resize(int width, int height) {  }

    public void show() {
        create();
    }
    public void hide() {  }
    public void pause() {  }
    public void resume() {  }
    public void dispose() {
        batch.dispose();
    }


    private void exit() {
        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {if (StartMenu.whichClass == 0) {
                main.setScreen(new StartMenu(main));
            } else if (StartMenu.whichClass == 1) {
                main.setScreen(new Playground(main));
            }
            }
        }, (float) 0.25);

    }
}
