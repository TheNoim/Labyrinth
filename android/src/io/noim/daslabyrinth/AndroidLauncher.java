package io.noim.daslabyrinth;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
<<<<<<< HEAD
		initialize(new DasLabyrinth(), config);
=======
		initialize(new Settings(), config);
>>>>>>> 3f91aae2064926926fa37a86b21e397352967c5e
	}
}
