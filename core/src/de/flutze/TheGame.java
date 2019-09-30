package de.flutze;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.flutze.screens.MainMenuScreen;
import de.flutze.sounds.MusicManager;

public class TheGame extends Game {
	SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		Gdx.input.setCursorCatched(true);
		setScreen(new MainMenuScreen(this, batch, null));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		if(batch != null)
			batch.dispose();
		MusicManager.getInstance().dispose();
	}
}
