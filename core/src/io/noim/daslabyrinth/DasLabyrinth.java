package io.noim.daslabyrinth;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DasLabyrinth extends Game {
	SpriteBatch batch;
	@Override
	public void create () {
		batch = new SpriteBatch();
		//Functions.generateRandomeField();
		//Functions.printField();
		setScreen(new Playground(this));
    }

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}


}
