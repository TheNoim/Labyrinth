package io.noim.daslabyrinth.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import io.noim.daslabyrinth.DasLabyrinth;

import java.awt.*;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Das Labyrinth";
        config.height = Math.round(0.9F * Toolkit.getDefaultToolkit().getScreenSize().height);
        config.resizable = false;
        config.addIcon("treasure.png", Files.FileType.Internal);
        new LwjglApplication(new DasLabyrinth(), config);
    }
}
