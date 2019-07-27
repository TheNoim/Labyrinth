package io.noim.daslabyrinth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

public class PlayerManager extends Page {

    Array<Player> players = new Array<Player>();
    private int activePlayer = 0;
    private GameField startField;

    //For the screen
    private Texture background;
    private Texture button;
    private Texture buttonPushed;
    private Texture backButton;
    private Texture[] figures = new Texture[]{
            new Texture("figure1.png"),
            new Texture("figure2.png"),
            new Texture("figure3.png"),
            new Texture("figure4.png"),
    };
    private BitmapFont textFont;
    private VerticalGroup playerGroup;
    private TextField textField;
    private TextButton addPlayerButton;

    private DasLabyrinth main;
    private Screen previousScreen;

    PlayerManager(final DasLabyrinth main, GameField startField) {
        this.main = main;
        this.startField = startField;

        this.background = new Texture("background.png");
        this.button = new Texture("button.png");
        this.buttonPushed = new Texture("button_pushed.png");
        this.backButton = new Texture("back.png");
        this.textFont = new BitmapFont(Gdx.files.internal("Verdana.fnt"));
        this.textField = new TextField("Name", this.main.getSkin());
        this.playerGroup = new VerticalGroup();
        this.playerGroup.setFillParent(true);
        this.playerGroup.pad(2 * Gdx.graphics.getHeight() / 10f, Gdx.graphics.getWidth() / 2f - DasLabyrinth.ButtonWidth / 2f, 0, Gdx.graphics.getWidth() / 2f - DasLabyrinth.ButtonWidth / 2f);
        this.stage.addActor(this.playerGroup);
        this.textField.addListener(new InputListener() {
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ENTER) {
                    addPlayer(textField.getText(), figures[players.size]);
                    return true;
                }
                return super.keyUp(event, keycode);
            }
        });
        // TODO background image smaller than font
        this.playerGroup.addActor(this.textField);
        this.addPlayerButton = new TextButton("Spieler hinzufügen", this.main.getSkin());
        this.addPlayerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (players.size < 4) {
                    main.click();
                    addPlayer(textField.getText(), figures[players.size]);
                }
            }
        });
        // TODO Button to big
        this.playerGroup.addActor(this.addPlayerButton);
    }

    void addPlayer(String name, Texture figure) {
        if (name.isEmpty())
            return;

        for (Player player : this.players) {
            if (player.getName().equalsIgnoreCase(name))
                return;
        }

        Player p = new Player(name, figure, this.startField);
        this.players.add(p);
        HorizontalGroup group = new HorizontalGroup();
        /*Image f = new Image(p.getFigure()); // TODO to big
        f.setSize(GameField.SizeInPixels, GameField.SizeInPixels);
        group.addActor(f);*/
        group.addActor(new Label(p.getName(), this.main.getSkin()));
        this.playerGroup.addActorAt(this.playerGroup.getChildren().size - 2, group);
        this.textField.setText("");

        if (this.players.size > 3) {
            this.textField.remove();
            this.addPlayerButton.remove();
        }
    }

    void moveCurrentPlayer(Playground playground, GameField nextGamefield) {
        if (this.players.get(this.activePlayer).move(playground, nextGamefield)) {
            this.nextPlayer();
        }
    }

    /**
     * @return the active player
     */
    Player getActivePlayer() {
        return this.players.get(activePlayer);
    }

    /**
     * @param gameField
     * @return
     */
    boolean isPlayerOnGamefield(GameField gameField) {
        for (Player player : new Array.ArrayIterator<Player>(this.players)) {
            if (player.currentField.equals(gameField)) {
                return true;
            }
        }
        return false;
    }

    /**
     * sets the next player as active
     */
    private void nextPlayer() {
        if (this.activePlayer == this.players.size - 1) {
            this.activePlayer = 0;
        } else {
            ++this.activePlayer;
        }
    }

    public void resetAllScores() {
        for (int i = 0; i < players.size; ++i) {
            players.get(i).treasures.clear();
            players.get(i).setScore(0);
        }
    }

    void setPreviousScreen(Screen screen) {
        this.previousScreen = screen;
    }

    @Override
    void create() {
        this.textFont.getData().setScale(Functions.scaleText("Vibration", this.textFont, Gdx.graphics.getWidth() / 3));
        this.textField.setBounds(Gdx.graphics.getWidth() / 2f - DasLabyrinth.ButtonWidth / 2f, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 10f * (this.players.size + 2), DasLabyrinth.ButtonWidth, Functions.textHeight("Spieler hinzufügen", this.textFont));
    }

    @Override
    void draw(Batch batch) {
        batch.draw(this.background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        if (this.players.size > 1) {
            batch.draw(this.backButton, Gdx.graphics.getWidth() / 20f, 29 * Gdx.graphics.getHeight() / 30f - backButton.getHeight() / 3f, Gdx.graphics.getWidth() / 10f, Gdx.graphics.getWidth() / 10f);
        }
        this.main.getFont().draw(batch, "Spieler", 0, 9 * Gdx.graphics.getHeight() / 10f, Gdx.graphics.getWidth(), 1, false);
    }

    @Override
    void update() {
    }

    @Override
    void touch(Vector3 touchPosition) {
        if (this.players.size > 3 && touchPosition.x > Gdx.graphics.getWidth() / 20 && touchPosition.x < 3 * Gdx.graphics.getWidth() / 20 && touchPosition.y > 29 * Gdx.graphics.getHeight() / 30 - backButton.getHeight() / 3 && touchPosition.y < 29 * Gdx.graphics.getHeight() / 30 - backButton.getHeight() / 3 + Gdx.graphics.getWidth() / 10) {
            this.main.click();
            this.main.setScreen(this.previousScreen);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        this.background.dispose();
        this.button.dispose();
        this.buttonPushed.dispose();
        this.backButton.dispose();
        for (Texture figure : this.figures) {
            figure.dispose();
        }
        this.textFont.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.BACK) {
            this.main.setScreen(this.previousScreen);
            return true;
        }
        return false;
    }
}
