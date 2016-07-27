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
		initialize(new Settings(), config);
=======
		initialize(new Playground(), config);
>>>>>>> 7f0b43e5335730637bf37f3b189aba37aad75236
	}
}
