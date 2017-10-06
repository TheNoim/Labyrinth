package io.noim.daslabyrinth;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class DasLabyrinth extends Game {
    SpriteBatch batch;
    OrthographicCamera camera;
    public static Texture background;
    public static BitmapFont font;
    public static Boolean playMusic, playSounds, vibration;
    public static int whichClass;
    public static Music music;
    public static Sound treasure, click;
    public static Preferences pref;
    public static Vector3 touchPosition = new Vector3();
    public static int ButtonWidth;
    public static String heading, play, ranking, settings;

    @Override
    public void create() {
        batch = new SpriteBatch();
        whichClass = 0;

        heading = "DAS LABYRINTH";
        play = "SPIEL STARTEN";
        ranking = "RANKING";
        settings = "EINSTELLUNGEN";

        ButtonWidth = (int) (Gdx.graphics.getWidth() * (float) 0.9);

        pref = Gdx.app.getPreferences("labyrinth.dat");
        background = new Texture("background.png");
        font = new BitmapFont(Gdx.files.internal("Labyrinth.fnt"));
        treasure = Gdx.audio.newSound(Gdx.files.internal("treasure.wav"));
        click = Gdx.audio.newSound(Gdx.files.internal("blop.wav"));
        music = Gdx.audio.newMusic(Gdx.files.internal("Spooky Fun.mp3"));

        font.getData().setScale(Functions.scaleText(settings, font, ButtonWidth - 40));

        playMusic = pref.getBoolean("Music", true);
        playSounds = pref.getBoolean("Sounds", true);
        vibration = pref.getBoolean("Vibration", true);

        music.setLooping(true);

        if (!music.isPlaying() && playMusic) {
            music.play();
        } else {
            music.stop();
        }

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //Functions.generateRandomeField();
        //Functions.printField();
        setScreen(new Animation(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public static void click() {
        if (pref.getBoolean("Sounds", true)) {
            click.play();
        }
        if (pref.getBoolean("Vibration", true)) {
            Gdx.input.vibrate(new long[]{0, 100}, -1);
        }
    }

    public static void treasure() {
        if (pref.getBoolean("Sounds", true)) {
            treasure.play();
        }
    }
}
