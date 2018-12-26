package io.noim.daslabyrinth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;


public class Save extends Page {

    public DasLabyrinth main;

    private Texture background;
    private BitmapFont font_heading;


    public Save(final DasLabyrinth main) {
        this.main = main;
        background = new Texture("background.png");
        font_heading = new BitmapFont(Gdx.files.internal("Labyrinth.fnt"));
    }

    @Override
    void create() {
        exit();
    }

    @Override
    void draw(Batch batch) {
        font_heading.draw(batch, "SAVING...", 0, Gdx.graphics.getHeight() / 2f, Gdx.graphics.getWidth(), 1, false);
    }

    @Override
    void update() {
    }

    @Override
    void touch(Vector3 touchPosition) {
    }

    @Override
    public void dispose() {
        super.dispose();
        this.background.dispose();
        this.font_heading.dispose();
    }

    private void exit() {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (DasLabyrinth.whichClass == 0) {
                    main.setScreen(main.startMenu);
                } else if (DasLabyrinth.whichClass == 1) {
                    if (main.getMusic().isPlaying()) {
                        main.getMusic().pause();
                    }
                    main.setScreen(main.playground);
                }
            }
        }, 0.25F);

    }
}
