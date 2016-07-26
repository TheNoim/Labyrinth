package io.noim.daslabyrinth;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
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
    Color font_color;
    String heading = "TEST";
    Vector3 touchPosition = new Vector3();

    Texture checkBox1;
    boolean checkBox1Checked = false;
    int checkBoxPosX;
    int checkBox1PosY;

    Texture checkBox2;
    boolean checkBox2Checked = false;
    int checkBox2PosY;

    @Override
    public void create(){
        /*checkBoxPosX = Gdx.graphics.getWidth() / 2 - checkBox1.getWidth() / 2;
        checkBox1PosY = Gdx.graphics.getHeight() / 2;
        checkBox2PosY = Gdx.graphics.getHeight() / 2 - 150;*/
        checkBox1 = new Texture("checkbox.png");
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        background = new Texture("figure3.png");
        music = Gdx.audio.newMusic(Gdx.files.internal("Spooky Fun.mp3"));
        music.setLooping(true);
        music.play();
        font = new BitmapFont(Gdx.files.internal("Labyrinth.fnt"));
        float r = 119;
        float g = 179;
        float b = 212;
        float a = 0;
        font_color = new Color(r, g, b, a);
        font.setColor(font_color);
    }

    private void update() {
        if(Gdx.input.justTouched()) {
            touchPosition.set(Gdx.input.getX(),Gdx.input.getY(), 0);
            camera.unproject(touchPosition);
            if (touchPosition.x >= checkBoxPosX -10 && touchPosition.x >= checkBoxPosX + 60 && touchPosition.y >= checkBox1PosY - 10 && touchPosition.y >= checkBox1PosY + 60) {
                if (!checkBox1Checked) {
                    checkBox1 = new Texture("checkbox_checked.png");
                    checkBox1Checked = true;
                } else {
                    checkBox1 = new Texture("checkbox.png");
                    checkBox1Checked = false;
                }
            } else if (touchPosition.x >= checkBoxPosX -10 && touchPosition.x >= checkBoxPosX + 60 && touchPosition.y >= checkBox2PosY - 10 && touchPosition.y >= checkBox2PosY + 60) {
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
        batch.draw(checkBox1, Gdx.graphics.getWidth() / 2 - checkBox1.getWidth() / 2, Gdx.graphics.getHeight() / 2, 50, 50);
        batch.draw(checkBox2, (Gdx.graphics.getWidth() / 2) - (checkBox2.getWidth() / 2), Gdx.graphics.getHeight() / 2, 50, 50);
        font.draw(batch, heading, Gdx.graphics.getWidth() / 2 - StartMenu.textWidth(heading, font), Gdx.graphics.getHeight() - StartMenu.textHeight(heading, font) - 200);
        batch.end();
    }

    public void render() {
        Gdx.gl.glClearColor(0, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update();
        draw();
    }
}
