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
		initialize(new Playground(), config);
=======
		initialize(new StartMenu(), config);
>>>>>>> c15773a720a49f2781015eb413077bbf3cf9c579
	}
}
