package io.noim.daslabyrinth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;


public class Save extends Page {

    private DasLabyrinth main;


    public Save(final DasLabyrinth main) {
        this.main = main;
    }

    @Override
    void create() {
        exit();
    }

    @Override
    void draw(Batch batch) {
        main.getFont().draw(batch, "SAVING...", 0, Gdx.graphics.getHeight() / 2f, Gdx.graphics.getWidth(), 1, false);
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
        }, 0.5F);

    }
}
