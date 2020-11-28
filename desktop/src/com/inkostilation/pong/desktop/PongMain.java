package com.inkostilation.pong.desktop;

import com.badlogic.gdx.ApplicationAdapter;
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

public class PongMain extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture img;
	private ShapeRenderer shapeRenderer;
	private Field field;

	private IEngine engine;

	@Override
	public void create () {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		img = new Texture("badlogic.jpg");
		engine = Network.getEngine();
		field = new Field();
	}

	@Override
	public void render () {
		engine.act();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//batch.begin();
		//batch.draw(img, 0, 0);
		//batch.end();
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//draw
		shapeRenderer.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		shapeRenderer.dispose();
		img.dispose();
	}
}
