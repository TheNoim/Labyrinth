package io.noim.daslabyrinth.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import io.noim.daslabyrinth.DasLabyrinth;

import java.awt.*;

public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Das Labyrinth");
        int height = Math.round(0.9F * Toolkit.getDefaultToolkit().getScreenSize().height);
        config.setWindowedMode(Math.round(9 / 16f * height), height);
        config.setResizable(false);
        config.setWindowIcon(Files.FileType.Internal, "treasure.png");
        new Lwjgl3Application(new DasLabyrinth(), config);
    }
}
