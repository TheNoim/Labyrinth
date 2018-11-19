package io.noim.daslabyrinth;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class Functions {
    private static GlyphLayout layout = new GlyphLayout();

    public static int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }

    public static boolean randomBoolean() {
        int ii = randomWithRange(0, 1);
        return ii == 1;
    }

    public static boolean randomBooleanT() {
        int ii = randomWithRange(0, 100);
        return ii <= 15;
    }

    public static float textWidth(String largestText, BitmapFont font) {
        layout.setText(font, largestText);
        return layout.width;
    }

    public static float textHeight(String text, BitmapFont font) {
        layout.setText(font, text);
        return layout.height;
    }

    public static float scaleText(String text, BitmapFont font, int targetWidth) {
        float textWidth = textWidth(text, font);
        return targetWidth / textWidth;
    }
}
