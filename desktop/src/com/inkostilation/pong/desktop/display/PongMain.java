package com.inkostilation.pong.desktop.display;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.inkostilation.pong.desktop.network.Network;
import com.inkostilation.pong.engine.Ball;
import com.inkostilation.pong.engine.Field;
import com.inkostilation.pong.engine.IEngine;
import com.inkostilation.pong.engine.Paddle;

public class PongMain extends Game {

	@Override
	public void create () {
		setScreen(new PongScreen());
	}

	@Override
	public void render () {
		Network.getEngine().act();
	}
	
	@Override
	public void dispose () {

	}
}
