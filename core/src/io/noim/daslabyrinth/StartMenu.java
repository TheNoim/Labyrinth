package io.noim.daslabyrinth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;


public class StartMenu implements Screen {

    SpriteBatch batch;
    OrthographicCamera camera;
    public static Music music;
    public static Preferences pref;
    BitmapFont font;
    Texture background, button, button_pushed;
    Color font_color;
    String heading, play, ranking, settings;
    Vector3 touchPosition = new Vector3();
    int ButtonWidth;
    public DasLabyrinth main;
    public Settings Settings;
    public Ranking Ranking;
    public static Boolean playMusic, playSounds;

    public StartMenu(final DasLabyrinth main) {
        create();
        this.main = main;
    }

    public StartMenu(final Settings Settings) {
        this.Settings = Settings;
    }

    public StartMenu(final Ranking Ranking) {
        this.Ranking = Ranking;
    }

    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        background = new Texture("background.png");

        pref = Gdx.app.getPreferences("labyrinth.dat");
        playMusic = pref.getBoolean("Music", true);
        playSounds = pref.getBoolean("Sounds", true);

        music = Gdx.audio.newMusic(Gdx.files.internal("Spooky Fun.mp3"));
        music.setLooping(true);

        System.out.println(music.isPlaying());
        if(!music.isPlaying() && playMusic) {
            music.play();
        } else {
            music.stop();
        }


        font = new BitmapFont(Gdx.files.internal("Labyrinth.fnt"));
        font_color = new Color(119, 179, 212, 1);
        font.setColor(font_color);
        button = new Texture("button.png");
        button_pushed = new Texture("button_pushed.png");
        heading = "DAS LABYRINTH";
        play = "SPIEL STARTEN";
        ranking = "RANKING";
        settings = "EINSTELLUNGEN";
        ButtonWidth = (int) (Gdx.graphics.getWidth() * (float) 0.9);
    }

    private void update() {
        if (Gdx.input.justTouched()) {
            touchPosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPosition);
            if (touchPosition.x >= (Gdx.graphics.getWidth() / 2 - ButtonWidth / 2) && touchPosition.x <= (Gdx.graphics.getWidth() / 2 + ButtonWidth / 2) && touchPosition.y >= (Gdx.graphics.getHeight() / 2 + button.getHeight()) && touchPosition.y <= (Gdx.graphics.getHeight() / 2 + 2 * button.getHeight())) {
                music.stop();
                main.setScreen(new Playground(main));
            }
            if (touchPosition.x >= (Gdx.graphics.getWidth() / 2 - ButtonWidth / 2) && touchPosition.x <= (Gdx.graphics.getWidth() / 2 + ButtonWidth / 2) && touchPosition.y >= (Gdx.graphics.getHeight() / 2 - button.getHeight() / 2) && touchPosition.y <= (Gdx.graphics.getHeight() / 2 + button.getHeight() / 2)) {
                main.setScreen(new Ranking(main));
            }
            if (touchPosition.x >= (Gdx.graphics.getWidth() / 2 - ButtonWidth / 2) && touchPosition.x <= (Gdx.graphics.getWidth() / 2 + ButtonWidth / 2) && touchPosition.y >= (Gdx.graphics.getHeight() / 2 - button.getHeight() * 2) && touchPosition.y <= (Gdx.graphics.getHeight() / 2 - button.getHeight())) {
                main.setScreen(new Settings(main));
            }
        }
    }

    public static float textWidth(String text, BitmapFont font){
        GlyphLayout layout = new GlyphLayout(); //dont do this every frame! Store it as member
        layout.setText(font, text);
        float width = layout.width;// contains the width of the current set text
        //float height = layout.height; // contains the height of the current set text
        return width;
    }

    public static float scaleText(String text, BitmapFont font, int targetWidth) {
        float textWidth = textWidth(text, font);
        float proportion = targetWidth / textWidth;
        return proportion;
    }

    private void draw() {
        batch.begin();

        batch.draw(background, 0, 0, (float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight());

        batch.draw(button, Gdx.graphics.getWidth() / 2 - ButtonWidth / 2, Gdx.graphics.getHeight() / 2 + button.getHeight(), ButtonWidth, button.getHeight());
        batch.draw(button, Gdx.graphics.getWidth() / 2 - ButtonWidth / 2, Gdx.graphics.getHeight() / 2 - button.getHeight() / 2, ButtonWidth, button.getHeight());
        batch.draw(button, Gdx.graphics.getWidth() / 2 - ButtonWidth / 2, Gdx.graphics.getHeight() / 2 - button.getHeight() * 2, ButtonWidth, button.getHeight());

        font.getData().setScale(scaleText(settings, font, ButtonWidth-20));
        font.draw(batch, heading, 0, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 10, Gdx.graphics.getWidth(), 1, false);
        font.draw(batch, play, (Gdx.graphics.getWidth() / 2 - ButtonWidth / 2), Gdx.graphics.getHeight() / 2 + 2 * button.getHeight() - ((button.getHeight() - 46) / 2), ButtonWidth, 1, false);
        font.draw(batch, ranking, (Gdx.graphics.getWidth() / 2 - ButtonWidth / 2), Gdx.graphics.getHeight() / 2 + (button.getHeight() / 2) - ((button.getHeight() - 46) / 2), ButtonWidth, 1, false);
        font.draw(batch, settings, (Gdx.graphics.getWidth() / 2 - ButtonWidth / 2), Gdx.graphics.getHeight() / 2 - button.getHeight() - ((button.getHeight() - 46) / 2), ButtonWidth, 1, false);

        batch.end();
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update();
        draw();
    }

    public void resize(int width, int height) {
    }

    public void show() {
    }

    public void hide() {
    }

    public void pause() {
    }

    public void resume() {
    }

    public void dispose() {
        batch.dispose();
    }
}
