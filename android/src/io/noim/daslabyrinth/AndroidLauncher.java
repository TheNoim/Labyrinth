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
<<<<<<< HEAD
		initialize(new Settings(), config);
=======
<<<<<<< HEAD
		initialize(new Playground(), config);
=======
		initialize(new StartMenu(), config);
>>>>>>> db696dbce3dd7690caf9af0c323b413bb84c5d5a
>>>>>>> 0f4aa827734d85f05e2da2edf2b24bd43ffe1944
>>>>>>> bbfffb93b7ff33c09228e9b9ea70f20324bedc7f
	}
}
