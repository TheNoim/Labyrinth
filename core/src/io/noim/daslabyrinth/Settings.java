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
    BitmapFont font;
    String heading = "EINSTELLUNGEN";
    Vector3 touchPosition = new Vector3();

    Texture checkBox1;
    Texture checkBox2;
    boolean checkBox1Checked = false;
    boolean checkBox2Checked = false;
    float checkBoxPosX;
    float checkBoxPosY;

    @Override
    public void create() {
        checkBox1 = new Texture("checkbox.png");
        checkBox2 = new Texture("checkbox.png");
        checkBoxPosX = 100;
        checkBoxPosY = 700;
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
            if (touchPosition.x >= checkBoxPosX -10 && touchPosition.x >= checkBoxPosX + 60 && touchPosition.y >= checkBoxPosY - 10 && touchPosition.y >= checkBoxPosY + 60) {
                if (!checkBox1Checked) {
                    checkBox1 = new Texture("checkbox_checked.png");
                    checkBox1Checked = true;
                } else {
                    checkBox1 = new Texture("checkbox.png");
                    checkBox1Checked = false;
                }
            } else if (touchPosition.x >= checkBoxPosX -10 && touchPosition.x >= checkBoxPosX + 60 && touchPosition.y >= checkBoxPosY + 140 && touchPosition.y >= checkBoxPosY + 210) {
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
        batch.draw(checkBox1, checkBoxPosX, Gdx.graphics.getHeight() / 2);
        batch.draw(checkBox2, checkBoxPosX, Gdx.graphics.getHeight() / 2 - 150);
        //font.draw(batch, heading, Gdx.graphics.getWidth() / 2 - StartMenu.textWidth(heading, font), Gdx.graphics.getHeight() - StartMenu.textHeight(heading, font) - 200);
=======
        batch.draw(checkBox1, checkBoxPosX, checkBoxPosY);
        batch.draw(checkBox2, checkBoxPosX, checkBoxPosY
                - 150);
        font.draw(batch, heading, Gdx.graphics.getWidth() / 2 - StartMenu.textWidth(heading, font), Gdx.graphics.getHeight() - StartMenu.textHeight(heading, font) - 200);
>>>>>>> c7c77344a961bcd00e82f9e77a9e61fb2abea1a9
        batch.end();
    }

    public void render() {
        Gdx.gl.glClearColor(0, 1, (float) 0.5, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update();
        draw();
    }
}
