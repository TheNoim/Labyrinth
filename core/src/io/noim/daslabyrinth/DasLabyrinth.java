package io.noim.daslabyrinth;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DasLabyrinth extends ApplicationAdapter {
	SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
<<<<<<< HEAD
=======
		Functions.generateRandomeField();
		Functions.printField();
		m = Gdx.audio.newMusic(Gdx.files.internal("Epic Suspense.mp3"));
		m.setLooping(true);
		m.play();
>>>>>>> f0f31e8e2061541fdf44fb0e42979ccd66a8c36f
    }

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}


}
