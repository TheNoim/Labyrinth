package io.noim.daslabyrinth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
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
    BitmapFont font_heading;
    BitmapFont font_text;
    String heading = "SETTINGS";
    Vector3 touchPosition = new Vector3();

    Texture checkBox1;
    Texture checkBox2;
    Texture back;
    boolean checkBox1Checked, checkBox2Checked;
    float checkBoxPosX;
    float checkBoxPos1Y;
    float checkBoxPos2Y;


    public void create() {
        checkBoxPosX = Gdx.graphics.getWidth() / 10;
        checkBoxPos1Y = Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 10 * 3;
        checkBoxPos2Y = Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 10 * 4;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        background = new Texture("background.png");
        font_heading = new BitmapFont(Gdx.files.internal("Labyrinth.fnt"));
        font_text = new BitmapFont(Gdx.files.internal("Verdana.fnt"));
        if (StartMenu.playMusic) {
            checkBox1 = new Texture("checkbox_checked.png");
            checkBox1Checked = true;
        } else {
            checkBox1 = new Texture("checkbox.png");
            checkBox1Checked = false;
        }
        if (StartMenu.playSounds) {
            checkBox2 = new Texture("checkbox_checked.png");
            checkBox2Checked = true;
        } else {
            checkBox2 = new Texture("checkbox.png");
            checkBox2Checked = false;
        }
        back = new Texture("back.png");
    }

    private void update() {
        if(Gdx.input.justTouched()) {
            touchPosition.set(Gdx.input.getX(),Gdx.input.getY(), 0);
            camera.unproject(touchPosition);
            if(touchPosition.x >= (checkBoxPosX -10) &&  touchPosition.x <= (checkBoxPosX + 60) && touchPosition.y >= (checkBoxPos1Y -10) && touchPosition.y <= (checkBoxPos1Y + 60)) {
                if (!checkBox1Checked) {
                    StartMenu.music.play();
                    checkBox1 = new Texture("checkbox_checked.png");
                    checkBox1Checked = true;
                    StartMenu.pref.putBoolean("Music", true);
                    StartMenu.pref.flush();
                } else {
                    StartMenu.music.pause();
                    checkBox1 = new Texture("checkbox.png");
                    checkBox1Checked = false;
                    StartMenu.pref.putBoolean("Music", false);
                    StartMenu.pref.flush();
                }
            }
            if(touchPosition.x >= (checkBoxPosX -10) &&  touchPosition.x <= (checkBoxPosX + 60) && touchPosition.y >= (checkBoxPos2Y -10) && touchPosition.y <= (checkBoxPos2Y + 60)) {
                if (!checkBox2Checked) {
                    checkBox2 = new Texture("checkbox_checked.png");
                    checkBox2Checked = true;
                    StartMenu.pref.putBoolean("Sounds", true);
                    StartMenu.pref.flush();
                } else {
                    checkBox2 = new Texture("checkbox.png");
                    checkBox2Checked = false;
                    StartMenu.pref.putBoolean("Sounds", false);
                    StartMenu.pref.flush();
                }
            }
            /*if(touchPosition.x >= (backPosX -10) &&  touchPosition.x <= (backPosX + 60) && touchPosition.y >= (backPos2Y -10) && touchPosition.y <= (checkBoxPos2Y + 60)) {
                if (!checkBox2Checked) {
                    checkBox2 = new Texture("checkbox_checked.png");
                    checkBox2Checked = true;
                    //pref.putInteger("Sounds", 1);
                    //pref.flush();
                } else {
                    checkBox2 = new Texture("checkbox.png");
                    checkBox2Checked = false;
                    //pref.putInteger("Sounds", 0);
                    //pref.flush();
                }
            }*/
        }
    }

    private void draw() {
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(),  Gdx.graphics.getHeight());
        batch.draw(checkBox1, checkBoxPosX, checkBoxPos1Y, Gdx.graphics.getWidth() / 10, Gdx.graphics.getWidth() / 10);
        batch.draw(checkBox2, checkBoxPosX, checkBoxPos2Y, Gdx.graphics.getWidth() / 10, Gdx.graphics.getWidth() / 10);
        batch.draw(back, Gdx.graphics.getWidth() / 20, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 30 - back.getHeight() / 3, Gdx.graphics.getWidth() / 10, Gdx.graphics.getWidth() / 10);
        font_heading.draw(batch, heading, Gdx.graphics.getWidth() - (Gdx.graphics.getWidth() / 20) * 17, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 10);
        font_text.draw(batch, "Musik", checkBoxPosX + 100, checkBoxPos1Y + Gdx.graphics.getWidth() / 10);
        font_text.draw(batch, "Sounds", checkBoxPosX + 100, checkBoxPos2Y + Gdx.graphics.getWidth() / 10);
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
