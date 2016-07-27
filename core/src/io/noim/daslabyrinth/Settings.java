package io.noim.daslabyrinth;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class Settings extends ApplicationAdapter {
    SpriteBatch batch;
    OrthographicCamera camera;
    Texture background;
    Music music;
    BitmapFont font;
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
        background = new Texture("figure3.png");
        music = Gdx.audio.newMusic(Gdx.files.internal("Spooky Fun.mp3"));
        music.setLooping(true);
        music.play();
        font = new BitmapFont(Gdx.files.internal("Labyrinth.fnt"));
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
        batch.draw(background, camera.position.x - background.getWidth() / 2, 0);
<<<<<<< HEAD
        batch.draw(checkBox1, checkBoxPosX, checkBoxPos1Y);
        batch.draw(checkBox2, checkBoxPosX, checkBoxPos2Y);
        font.draw(batch, heading, Gdx.graphics.getWidth() - (Gdx.graphics.getWidth() / 5) * 4, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 10);
=======
        batch.draw(checkBox1, checkBoxPosX, Gdx.graphics.getHeight() / 2);
        batch.draw(checkBox2, checkBoxPosX, Gdx.graphics.getHeight() / 2 - 150);
        //font.draw(batch, heading, Gdx.graphics.getWidth() / 2 - StartMenu.textWidth(heading, font), Gdx.graphics.getHeight() - StartMenu.textHeight(heading, font) - 200);
        batch.draw(checkBox1, checkBoxPosX, checkBoxPosY);
        batch.draw(checkBox2, checkBoxPosX, checkBoxPosY
                - 150);
        //font.draw(batch, heading, Gdx.graphics.getWidth() / 2 - StartMenu.textWidth(heading, font), Gdx.graphics.getHeight() - StartMenu.textHeight(heading, font) - 200);
>>>>>>> 5788fb2e2c562bc0d2934c48f536f49393f50ea0
        batch.end();
    }

    public void render() {
        Gdx.gl.glClearColor(1, (float) 0.25, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update();
        draw();
    }
}
