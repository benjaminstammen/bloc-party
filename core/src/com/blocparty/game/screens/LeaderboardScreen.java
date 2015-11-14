package com.blocparty.game.screens;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.blocparty.game.ActionResolver;
import com.blocparty.game.BlocParty;

public class LeaderboardScreen implements Screen {

	Stage stage;
	Skin skin;

	private ActionResolver actionResolver;

	private LeaderboardScreen(ActionResolver resolver) {
		actionResolver = resolver;
		//singleton
	}

	public LeaderboardScreen() {
		makeItFit();
		this.actionResolver.getLeaderboardGPGS();
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



		TextButton levelScreen = new TextButton("Back to Main Menu", skin);
		levelScreen.setBounds(100, 100, 200, 40);
		levelScreen.getLabel().setFontScale(2,2);
		stage.addActor(levelScreen);
		levelScreen.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				BlocParty.getInstance().setScreen(new MenuScreen());
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
