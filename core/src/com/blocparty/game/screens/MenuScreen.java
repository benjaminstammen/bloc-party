package com.blocparty.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.blocparty.game.BlocParty;

public class MenuScreen implements Screen {
	
	private Stage stage;
	private Skin skin;
	private Table table;

	private TextButton playButton;
	private TextButton leadButton;
	private TextButton quitButton;

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		createGUI();
	}

	@Override
	public void show() {
		createGUI();
	}
	
	private void createGUI() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));

//		TextButton levelScreen = new TextButton("Game Play Screen", skin);
//		//x, y, width, height
//		levelScreen.setBounds(100, 100, 200, 40);
//		stage.addActor(levelScreen);


		table = new Table();
		table.setWidth(stage.getWidth());
		table.align(Align.center | Align.top);
		table.setPosition(0, Gdx.graphics.getHeight());

		playButton = new TextButton("Play", skin);
		playButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				BlocParty.getInstance().setScreen(new GameplayScreen());
			}
		});

		leadButton = new TextButton("Leaderboard", skin);
		leadButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				BlocParty.getInstance().setScreen(new LeaderboardScreen());
			}
		});

		quitButton = new TextButton("Exit", skin);
		quitButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.exit();
			}
		});

		table.padTop(50);
		table.add(playButton).padBottom(50);
		table.row();
		table.add(leadButton).padBottom(50);
		table.row();
		table.add(quitButton);

		stage.addActor(table);
	}
	
	
	
	//===========================================
	
	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();
	}
	@Override
	public void hide() {
		dispose();
	}
	@Override
	public void pause() {
	}
	@Override
	public void resume() {
	}
}
