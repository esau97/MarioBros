package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.util.Constants;

public class MarioBrosGame extends Game {
	@Override
	public void create() {
		showInitialScreen();
	}


	public void showInitialScreen() {
		setScreen(new InitialScreen(this));
	}

	public void showGameplayScreen(Constants.Difficulty difficulty) {
		setScreen(new GameplayScreen(this,difficulty));
	}
}
