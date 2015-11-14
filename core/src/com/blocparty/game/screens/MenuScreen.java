package com.blocparty.game.screens;



import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuScreen implements Screen {
	
	Stage stage;
	Skin skin;
	
	
	
	public MenuScreen() {
		makeItFit();
	}
	
	@Override
	public void resize(int width, int height) {
		makeItFit();
	}
	
	@Override
	public void render(float arg0) {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
	}
	
	private void makeItFit() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		
		
		
		
		TextButton levelScreen = new TextButton("Game Play Screen", skin);
		//x, y, width, height
		levelScreen.setBounds(100, 100, 200, 40);
		stage.addActor(levelScreen);
		levelScreen.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				GameClass.getInstance().setScreen(new GameplayScreen());
			}
		});
		
		
	}
	
	
	
	//===========================================
	
	@Override
	public void dispose() {
	}
	@Override
	public void hide() {
	}
	@Override
	public void pause() {
	}
	@Override
	public void resume() {
	}
	@Override
	public void show() {
	}
}
