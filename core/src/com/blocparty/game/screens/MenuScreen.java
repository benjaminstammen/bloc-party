package com.blocparty.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.blocparty.game.BlocParty;

public class MenuScreen implements Screen {
	
	private Stage stage;
	private Skin skin;
	//private Table table;

	private TextButton playButton;
	private TextButton leadButton;

	private TextureAtlas buttonsAtlas;
	private NinePatch buttonUpNine;
	private TextButton.TextButtonStyle textButtonStyle;
	private TextButton textButton;
	private BitmapFont font;

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
		
		batch.draw(titleImage, colWidth * 3, rowHeight * 4);
		
		
		int x = colWidth * 2;
		int y = rowHeight * 5;
		int w = colWidth * 5;
		int h = rowHeight * 2;
		
		batch.draw(titleImage, colWidth * 3, rowHeight * 4);
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
		buttonsAtlas = new TextureAtlas(Gdx.files.internal("ui-green.atlas"));
		skin = new Skin();
		buttonUpNine = buttonsAtlas.createPatch("button_01");
		skin.addRegions(buttonsAtlas);

		//System.out.println("1");
		font = new BitmapFont();
		//System.out.println("2");
		font.setColor(0, 0, 0, 0);

		textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.up = new NinePatchDrawable(buttonUpNine);

		textButtonStyle.font = font;


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

		//System.out.println("Help me. . . I am scared!");
		playButton = new TextButton("Play", textButtonStyle);
		//System.out.println("I made it!");
		playButton.getLabel().setFontScale(4, 4);
		playButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				BlocParty.getInstance().setScreen(new GameplayScreen());
			}
		});
		playButton.setBounds(colWidth, rowHeight, colWidth * 3, rowHeight * 2);
		stage.addActor(playButton);

		leadButton = new TextButton("Leaderboard", textButtonStyle);
		leadButton.getLabel().setFontScale(4, 4);
		leadButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				BlocParty.getActionResolver().getLeaderboardGPGS();
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

	public TextureRegion getRegion(String region){
		return skin.getRegion(region);
	}
}
