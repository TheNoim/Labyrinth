package io.noim.daslabyrinth;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class DasLabyrinth extends Game {
    private Texture background;
    private BitmapFont font;
    private Skin skin;
    static Boolean playMusic, playSounds, vibration;
    static int whichClass;
    private Music music;
    private Sound treasure, click;
    static Preferences pref;
    static int ButtonWidth;
    static String heading, playText, rankingText, settingsText;
    Animation animation;
    StartMenu startMenu;
    Playground playground;
    Ranking ranking;
    Settings settings;
    PlayerManager playerManager;

    @Override
    public void create() {
        this.animation = new Animation(this);
        this.startMenu = new StartMenu(this);
        this.playground = new Playground(this);
        this.ranking = new Ranking(this);
        this.settings = new Settings(this);

        pref = Gdx.app.getPreferences("labyrinth.dat");
        background = new Texture("background.png");
        font = new BitmapFont(Gdx.files.internal("Labyrinth.fnt"));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        treasure = Gdx.audio.newSound(Gdx.files.internal("treasure.wav"));
        click = Gdx.audio.newSound(Gdx.files.internal("blop.wav"));
        music = Gdx.audio.newMusic(Gdx.files.internal("Spooky Fun.mp3"));

        whichClass = 0;

        heading = "DAS LABYRINTH";
        playText = "SPIEL STARTEN";
        rankingText = "RANKING";
        settingsText = "EINSTELLUNGEN";

        ButtonWidth = Math.round(Gdx.graphics.getWidth() * 0.9F);

        font.getData().setScale(Functions.scaleText(settingsText, font, ButtonWidth - 40));

        playMusic = pref.getBoolean("Music", true);
        playSounds = pref.getBoolean("Sounds", true);
        vibration = pref.getBoolean("Vibration", true);

        music.setLooping(true);

        if (!music.isPlaying() && playMusic) {
            music.play();
        } else {
            music.stop();
        }
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
