package io.noim.daslabyrinth;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

public class DasLabyrinth extends Game {
    static Boolean playMusic, playSounds, vibration;
    static int whichClass;
    static Preferences pref;
    static int ButtonWidth;
    Animation animation;
    StartMenu startMenu;
    Playground playground;
    Ranking ranking;
    Settings settings;
    PlayerManager playerManager;
    I18NBundle lang;
    private Texture background;
    private BitmapFont font;
    private Skin skin;
    private Music music;
    private Sound treasure, click;

    @Override
    public void create() {
        pref = Gdx.app.getPreferences("labyrinth.dat");
        background = new Texture("background.png");
        font = new BitmapFont(Gdx.files.internal("Labyrinth.fnt"));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        treasure = Gdx.audio.newSound(Gdx.files.internal("treasure.wav"));
        click = Gdx.audio.newSound(Gdx.files.internal("blop.wav"));
        music = Gdx.audio.newMusic(Gdx.files.internal("Spooky Fun.mp3"));

        whichClass = 0;

        this.lang = I18NBundle.createBundle(Gdx.files.internal("i18n/Labyrinth"), Locale.getDefault());

        ButtonWidth = Math.round(Gdx.graphics.getWidth() * 0.9F);

        font.getData().setScale(Functions.scaleText(this.lang.get("heading"), font, ButtonWidth - 40));

        playMusic = pref.getBoolean("Music", true);
        playSounds = pref.getBoolean("Sounds", true);
        vibration = pref.getBoolean("Vibration", true);

        music.setLooping(true);

        if (playMusic) {
            music.play();
        }

        this.animation = new Animation(this);
        this.startMenu = new StartMenu(this);
        this.playground = new Playground(this);
        this.ranking = new Ranking(this);
        this.settings = new Settings(this);
        setScreen(this.animation);
    }

    @Override
    public void dispose() {
        super.dispose();
        this.background.dispose();
        this.font.dispose();
        this.skin.dispose();
        this.music.dispose();
        this.treasure.dispose();
        this.click.dispose();
    }

    public Skin getSkin() {
        return skin;
    }

    /**
     * @return background texture
     */
    public Texture getBackground() {
        return background;
    }

    /**
     * @return main font
     */
    public BitmapFont getFont() {
        return font;
    }

    /**
     * @return music
     */
    public Music getMusic() {
        return music;
    }

    /**
     * Play the click sound and/or vibrate (if enabled)
     */
    void click() {
        if (pref.getBoolean("Sounds", true)) {
            click.play();
        }
        if (pref.getBoolean("Vibration", true)) {
            Gdx.input.vibrate(new long[]{0, 100}, -1);
        }
    }

    /**
     * Play the sound for a collected treasure (if enabled)
     */
    public void treasure() {
        if (pref.getBoolean("Sounds", true)) {
            treasure.play();
        }
    }
}
