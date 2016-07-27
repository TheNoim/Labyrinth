package io.noim.daslabyrinth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class Settings implements Screen {

    public DasLabyrinth main;
    public Settings(final DasLabyrinth main){
        this.main = main;
    }
    SpriteBatch batch;
    OrthographicCamera camera;
    Texture background;
    Music music;
    Integer playMusic;
    Integer playSounds;
    BitmapFont font_heading;
    BitmapFont font_text;
    String heading = "SETTINGS";
    Vector3 touchPosition = new Vector3();

    Texture checkBox1;
    Texture checkBox2;
    boolean checkBox1Checked, checkBox2Checked;
    float checkBoxPosX;
    float checkBoxPos1Y;
    float checkBoxPos2Y;

    public Preferences pref;

    public void create() {
        checkBoxPosX = 75;
        checkBoxPos1Y = 800;
        checkBoxPos2Y = 650;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        background = new Texture("background.png");
        Preferences pref = Gdx.app.getPreferences("labyrinth.dat");
        playMusic = pref.getInteger("Music", 1);
        playSounds = pref.getInteger("Sounds", 1);
        music = Gdx.audio.newMusic(Gdx.files.internal("Spooky Fun.mp3"));
        music.setLooping(true);
        music.play();
        font_heading = new BitmapFont(Gdx.files.internal("Labyrinth.fnt"));
        font_text = new BitmapFont(Gdx.files.internal("Verdana.fnt"));
        if (playMusic == 1) {
            checkBox1 = new Texture("checkbox_checked.png");
            checkBox1Checked = true;
        } else {
            checkBox1 = new Texture("checkbox.png");
            checkBox1Checked = false;
        }
        if (playSounds == 1) {
            checkBox2 = new Texture("checkbox_checked.png");
            checkBox2Checked = true;
        } else {
            checkBox2 = new Texture("checkbox.png");
            checkBox2Checked = false;
        }
    }

    private void update() {
        if(Gdx.input.justTouched()) {
            touchPosition.set(Gdx.input.getX(),Gdx.input.getY(), 0);
            camera.unproject(touchPosition);
            if(touchPosition.x >= (checkBoxPosX -10) &&  touchPosition.x <= (checkBoxPosX + 60) && touchPosition.y >= (checkBoxPos1Y -10) && touchPosition.y <= (checkBoxPos1Y + 60)) {
                if (!checkBox1Checked) {
                    music.play();
                    checkBox1 = new Texture("checkbox_checked.png");
                    checkBox1Checked = true;
                    pref.putInteger("Music", 1);
                    pref.flush();
                } else {
                    music.pause();
                    checkBox1 = new Texture("checkbox.png");
                    checkBox1Checked = false;
                    pref.putInteger("Music", 0);
                    pref.flush();
                }
            }
            if(touchPosition.x >= (checkBoxPosX -10) &&  touchPosition.x <= (checkBoxPosX + 60) && touchPosition.y >= (checkBoxPos2Y -10) && touchPosition.y <= (checkBoxPos2Y + 60)) {
                if (!checkBox2Checked) {
                    checkBox2 = new Texture("checkbox_checked.png");
                    checkBox2Checked = true;
                    pref.putInteger("Sounds", 1);
                    pref.flush();
                } else {
                    checkBox2 = new Texture("checkbox.png");
                    checkBox2Checked = false;
                    pref.putInteger("Sounds", 0);
                    pref.flush();
                }
            }
        }
    }

    private void draw() {
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(),  Gdx.graphics.getHeight());
        batch.draw(checkBox1, checkBoxPosX, checkBoxPos1Y, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(checkBox2, checkBoxPosX, checkBoxPos2Y, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        font_heading.draw(batch, heading, Gdx.graphics.getWidth() - (Gdx.graphics.getWidth() / 20) * 17, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 10);
        font_text.draw(batch, "Musik", checkBoxPosX + 100, checkBoxPos1Y + 50);
        font_text.draw(batch, "Sounds", checkBoxPosX + 100, checkBoxPos2Y + 50);
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
}
