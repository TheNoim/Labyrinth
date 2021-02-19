package io.noim.daslabyrinth.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import io.noim.daslabyrinth.DasLabyrinth;

public class HtmlLauncher extends GwtApplication {

    // USE THIS CODE FOR A FIXED SIZE APPLICATION
    // @Override
    // public GwtApplicationConfiguration getConfig() {
    //    return new GwtApplicationConfiguration(1080, 1920);
    // }
    // END CODE FOR FIXED SIZE APPLICATION

    // UNCOMMENT THIS CODE FOR A RESIZABLE APPLICATION
    // PADDING is to avoid scrolling in iframes, set to 20 if you have problems
    private static final int PADDING = 0;
    private GwtApplicationConfiguration cfg;

    @Override
    public GwtApplicationConfiguration getConfig() {
        int w = Window.getClientWidth() - PADDING;
        int h = Window.getClientHeight() - PADDING;
        int nW = Math.round((h * 9F) / 16F);
        if (nW > w) {
            h = Math.round(w * 16f / 9f);
        } else {
            w = nW;
        }
        cfg = new GwtApplicationConfiguration(w, h);
        Window.enableScrolling(false);
        Window.setMargin("0");
        Window.addResizeHandler(new ResizeListener());
        return cfg;
    }


    class ResizeListener implements ResizeHandler {
        @Override
        public void onResize(ResizeEvent event) {
            int width = event.getWidth() - PADDING;
            int height = event.getHeight() - PADDING;
            int newWidth = Math.round((height * 9F) / 16F);
            if (newWidth > width) {
                height = Math.round(width * 16f / 9f);
            } else {
                width = newWidth;
            }
            getRootPanel().setWidth("" + width + "px");
            getRootPanel().setHeight("" + height + "px");
            getApplicationListener().resize(width, height);
            Gdx.graphics.setWindowedMode(width, height);
        }
    }
    // END OF CODE FOR RESIZABLE APPLICATION

    @Override
    public ApplicationListener createApplicationListener() {
        return new DasLabyrinth();
    }
}
