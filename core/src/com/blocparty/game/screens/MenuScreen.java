package com.blocparty.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.blocparty.game.BlocParty;

public class MenuScreen implements Screen {
	
	private Stage stage;
	private Skin skin;
	//private Table table;

	private TextButton playButton;
	private TextButton leadButton;
	
	Texture titleImage = new Texture("TitleText.PNG");
	SpriteBatch batch;

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		
		
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();
		
		
		
		int colWidth = width / 9;
		
		int rowHeight = height / 9;
		
		
		batch.begin();
		
		//batch.draw(titleImage, colWidth * 3, rowHeight * 4);
		
		
		int x = colWidth * 2;
		int y = rowHeight * 5;
		int w = colWidth * 5;
		int h = rowHeight * 2;
		
		//batch.draw(titleImage, colWidth * 3, rowHeight * 4);
		batch.draw(titleImage, x, y, w, h);
		
		batch.end();
		
		
		
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

//		table = new Table();
//		table.setFillParent(true);
//		table.setDebug(true);
		
		
		batch = new SpriteBatch();
		
		
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();
		
		
		int colWidth = width / 9;
		int rowHeight = height / 9;
		
		
		
		
		
//		Label titleLabel = new Label("Bloc Party", skin);
//		titleLabel.setFontScale(5);
//		//titleLabel.
//		titleLabel.setPosition(colWidth * 3, rowHeight * 5);
//		stage.addActor(titleLabel);
		
		
		

		playButton = new TextButton("Play", skin);
		playButton.getLabel().setFontScale(2, 2);
		playButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				BlocParty.getInstance().setScreen(new GameplayScreen());
			}
		});
		playButton.setBounds(colWidth, rowHeight, colWidth * 3, rowHeight * 2);
		stage.addActor(playButton);

		leadButton = new TextButton("Leaderboard", skin);
		leadButton.getLabel().setFontScale(2, 2);
		leadButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				//TODO
				//BlocParty.getActionResolver().getLeaderboardGPGS();
				//BlocParty.getInstance().setScreen(new LeaderboardScreen(BlocParty.getInstance().getActionResolver()));
			}
		});
		leadButton.setBounds(colWidth * 5, rowHeight, colWidth * 3, rowHeight * 2);
		stage.addActor(leadButton);

//		table.padTop(50);
//		table.add(playButton).height(100).width(200).padBottom(50);
//		table.row();
//		table.add(leadButton).height(100).width(200).padBottom(50);

		//stage.addActor(table);
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
