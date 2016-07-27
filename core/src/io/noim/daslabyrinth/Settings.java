package io.noim.daslabyrinth;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class Settings extends ApplicationAdapter {
    SpriteBatch batch;
    OrthographicCamera camera;
    Texture background;
    Music music;
    BitmapFont font_heading;
    BitmapFont font_text;
    String heading = "SETTINGS";
    Vector3 touchPosition = new Vector3();

    Texture checkBox1;
    Texture checkBox2;
    boolean checkBox1Checked = false;
    boolean checkBox2Checked = false;
    float checkBoxPosX;
    float checkBoxPos1Y;
    float checkBoxPos2Y;

    @Override
    public void create() {
        checkBox1 = new Texture("checkbox.png");
        checkBox2 = new Texture("checkbox.png");
        checkBoxPosX = 75;
        checkBoxPos1Y = 800;
        checkBoxPos2Y = 650;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        background = new Texture("background.png");
        music = Gdx.audio.newMusic(Gdx.files.internal("Spooky Fun.mp3"));
        music.setLooping(true);
        music.play();
        font_heading = new BitmapFont(Gdx.files.internal("Labyrinth.fnt"));
        font_text = new BitmapFont(Gdx.files.internal("Verdana.fnt"));
    }

    private void update() {
        if(Gdx.input.justTouched()) {
            touchPosition.set(Gdx.input.getX(),Gdx.input.getY(), 0);
            camera.unproject(touchPosition);
            if(touchPosition.x >= (checkBoxPosX -10) &&  touchPosition.x <= (checkBoxPosX + 60) && touchPosition.y >= (checkBoxPos1Y -10) && touchPosition.y <= (checkBoxPos1Y + 60)) {
                if (!checkBox1Checked) {
                    checkBox1 = new Texture("checkbox_checked.png");
                    checkBox1Checked = true;
                } else {
                    checkBox1 = new Texture("checkbox.png");
                    checkBox1Checked = false;
                }
            }
            if(touchPosition.x >= (checkBoxPosX -10) &&  touchPosition.x <= (checkBoxPosX + 60) && touchPosition.y >= (checkBoxPos2Y -10) && touchPosition.y <= (checkBoxPos2Y + 60)) {
                if (!checkBox2Checked) {
                    checkBox2 = new Texture("checkbox_checked.png");
                    checkBox2Checked = true;
                } else {
                    checkBox2 = new Texture("checkbox.png");
                    checkBox2Checked = false;
                }
            }
        }
    }

    private void draw() {
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(),  Gdx.graphics.getHeight());
        batch.draw(checkBox1, checkBoxPosX, checkBoxPos1Y);
        batch.draw(checkBox2, checkBoxPosX, checkBoxPos2Y);
        font_heading.draw(batch, heading, Gdx.graphics.getWidth() - (Gdx.graphics.getWidth() / 20) * 17, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 10);
        font_text.draw(batch, "Musik", checkBoxPosX + 100, checkBoxPos1Y + 50);
        font_text.draw(batch, "Sounds", checkBoxPosX + 100, checkBoxPos2Y + 50);
        batch.end();
    }

    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update();
        draw();
    }
}
