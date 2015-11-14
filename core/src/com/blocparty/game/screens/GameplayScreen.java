package com.blocparty.game.screens;



import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.blocparty.game.utilities.GameClickListener;


public class GameplayScreen implements Screen {

    Stage stage;
    Skin skin;

    SpriteBatch batch;
    ShapeRenderer shapeBatch;

    private final static int BTN_PANEL_LEFT_MARGIN = 200;
    private final static int BTN_PANEL_BOTTOM = 200;

    private int width;
    private int height;
    public static int boxWidth;
    public static int boxHeight;
    
    public static ArrayList<Box> boxes;
    //Box[][] thing;
    
    public class Box {
    	public int row;
    	public int column;
    	
    	public int circleRadius = 20;
    }
    


    public GameplayScreen() {
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


        shapeBatch.begin(ShapeType.Line);

        shapeBatch.setColor(Color.BLACK);
        //shapeBatch.rect(0, 0, 100, 50);

        
        
        for (int i = 0; i < 4; i++) {
        	for (int j = 0; j < 2; j++) {
        		shapeBatch.rect(i * boxWidth, j * boxHeight, boxWidth, boxHeight);
        	}
        }
        


        shapeBatch.end();



//		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
//		stage.draw();
    }

    private void makeItFit() {


        batch = new SpriteBatch();
        shapeBatch = new ShapeRenderer();

        //hud = GameScreenHUD.create(round);


        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        boxWidth = width / 4;
        boxHeight = height / 2;
        
        
        
        
        boxes = new ArrayList<Box>();
        for (int i = 0; i < 4; i++) {
        	for (int j = 0; j < 2; j++) {
        		Box b = new Box();
        		b.row = j;
        		b.column = i;
        		boxes.add(b);
        	}
        }
        
        





        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(new GameClickListener());
        Gdx.input.setInputProcessor(multiplexer);



//		stage = new Stage();
//		Gdx.input.setInputProcessor(stage);
//		skin = new Skin(Gdx.files.internal("data/uiskin.json"));

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
