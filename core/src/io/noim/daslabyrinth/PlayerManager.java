package io.noim.daslabyrinth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
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
    private TextField textField;
    private int i = 0;

    private DasLabyrinth main;
    private Screen previousScreen;

    PlayerManager(DasLabyrinth main, GameField startField) {
        this.main = main;
        this.startField = startField;

        this.background = new Texture("background.png");
        this.button = new Texture("button.png");
        this.buttonPushed = new Texture("button_pushed.png");
        this.backButton = new Texture("back.png");
        this.textFont = new BitmapFont(Gdx.files.internal("Verdana.fnt"));
        this.textField = new TextField("Name", this.main.getSkin());
        this.stage.addActor(this.textField);
        this.textField.addListener(new InputListener() {
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                switch (keycode) {
                    case Input.Keys.ENTER:
                        addPlayer(textField.getText(), figures[players.size]);
                        return true;
                }
                return super.keyUp(event, keycode);
            }
        });
    }

    void addPlayer(String name, Texture figure) {
        if (!name.isEmpty()) {
            this.players.add(new Player(name, figure, this.startField));
            this.textField.setText("");
            this.textField.setBounds(Gdx.graphics.getWidth() / 2f - DasLabyrinth.ButtonWidth / 2f, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 10f * (this.players.size + 2), DasLabyrinth.ButtonWidth, Functions.textHeight("Spieler hinzufügen", this.textFont));
            if (this.players.size > 3) {
                this.textField.remove();
            }
        }
    }

    void moveCurrentPlayer(Playground playground, GameField nextGamefield) {
        if (this.players.get(this.activePlayer).movePlayer(playground, nextGamefield)) {
            this.nextPlayer();
        }
    }

    private void nextPlayer() {
        if (this.activePlayer == this.players.size - 1) {
            this.activePlayer = 0;
        } else {
            this.activePlayer++;
        }
    }

    public void resetAllScores() {
        for (int i = 0; i < players.size; i++) {
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
    void draw(SpriteBatch batch) {
        batch.draw(this.background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        if (this.players.size > 3) {
            batch.draw(this.backButton, Gdx.graphics.getWidth() / 20f, 29 * Gdx.graphics.getHeight() / 30f - backButton.getHeight() / 3f, Gdx.graphics.getWidth() / 10f, Gdx.graphics.getWidth() / 10f);
        }
        this.main.getFont().draw(batch, "Spieler", 0, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 10f, Gdx.graphics.getWidth(), 1, false);
        for (i = 0; i < this.players.size; i++) {
            this.textFont.draw(batch, this.players.get(i).getName(), Gdx.graphics.getWidth() / 10f, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 10f * (i + 2));
        }
        if (this.players.size < 4) {
            batch.draw(this.button, Gdx.graphics.getWidth() / 2f - DasLabyrinth.ButtonWidth / 2f, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 10f * (i + 3), DasLabyrinth.ButtonWidth, Functions.textHeight("Spieler hinzufügen", this.textFont) * 3);
            this.textFont.draw(batch, "Spieler hinzufügen", 0, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 10f * (i + 3) + Functions.textHeight("Spieler hinzufügen", this.textFont) * 2, Gdx.graphics.getWidth(), Align.center, false);
        }
    }

    @Override
    void update() {
    }

    @Override
    void touch(Vector3 touchPosition) {
        if (this.players.size > 3 && touchPosition.x > Gdx.graphics.getWidth() / 20 && touchPosition.x < 3 * Gdx.graphics.getWidth() / 20 && touchPosition.y > 29 * Gdx.graphics.getHeight() / 30 - backButton.getHeight() / 3 && touchPosition.y < 29 * Gdx.graphics.getHeight() / 30 - backButton.getHeight() / 3 + Gdx.graphics.getWidth() / 10) {
            this.main.click();
            this.main.setScreen(this.previousScreen);
        } else if (touchPosition.x > Gdx.graphics.getWidth() / 2 - DasLabyrinth.ButtonWidth / 2 && touchPosition.x < Gdx.graphics.getWidth() / 2 + DasLabyrinth.ButtonWidth / 2 && touchPosition.y > Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 10 * (i + 3) && touchPosition.y < Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 10 * (i + 3) + Functions.textHeight("Spieler hinzufügen", this.textFont) * 3) {
            if (this.players.size < 4) {
                this.main.click();
                this.addPlayer(this.textField.getText(), this.figures[this.players.size]);
            }
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
        switch (keycode) {
            case Input.Keys.BACK:
                this.main.setScreen(this.previousScreen);
                break;
        }
        return false;
    }
}
