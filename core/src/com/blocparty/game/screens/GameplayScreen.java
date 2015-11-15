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
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.blocparty.game.BlocParty;
import com.blocparty.game.screens.GameplayScreen.Box;
import com.blocparty.game.utilities.GameClickListener;


public class GameplayScreen implements Screen {

    Stage stage;
    Skin skin;

    SpriteBatch batch;
    ShapeRenderer shapeBatch;

    public static int INTENDED_WIDTH = 950;
    public static int INTENDED_HEIGHT = 500;
    
    float minScale = 1.0f;
	float maxScale = 1.0f;
    
    

    private int width;
    private int height;
    public static int boxWidth;
    public static int boxHeight;
    
    //public static ArrayList<Box> boxes;
    //row, column
    public static Box[][] boxes;
    
    
    //boolean adjusting = false;
	//float skyTime = 0.0f;
	//float transparencyAchieved = 0.0f;
    float nextCircleTime;
    float timeSoFar;
    float speed;
    float totalTime = 0;
    
    private static final int ROW_COUNT = 2;
    private static final int COLUMN_COUNT = 4;
    
    public static int score = 0;
    
    public static Label scoreLabel;
    
    public static boolean gameOver = false;
    public static boolean gameOverPrompted = false;
    
    
    public class Box {
    	public int row;
    	public int column;
    	
    	public float circleRadius = 20.0f;
    	public boolean hasCircle = false;
    }
    


    
    
    private float getGrowthRate() {
    	//totalTime
    	float growthRate = 10.0f + totalTime / 10.0f;
    	return growthRate;
    }
    
    //private static float getSpawnRate() {
    private float generateNextSpawnTime() {
    	//nextCircleTime = (float) Math.random() * 1.0f + 1.0f;
    	
    	//totalTime
    	//float spawnRate = 1.0f;
    	
    	float baseTime = 1.0f - (totalTime / 10.0f);
    	if (baseTime < 0) {
    		baseTime = 0;
    	}
    	
    	float timeRange = 1.0f - (totalTime / 40.0f);;
    	if (timeRange < 0.5) {
    		timeRange = 0.5f;
    	}
    	
    	
    	float nextTime = (float) Math.random() * timeRange + baseTime;
    	return nextTime;
    }
    
    
    
    
    private void resetValues() {
    	gameOver = false;
		gameOverPrompted = false;
		score = 0;
		
		totalTime  = 0;
		nextCircleTime = 0;
		timeSoFar = 0;
		//speed =?
    }
    
    public GameplayScreen() {
    	resetValues();
    	
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
        
        
        
        
        drawRectangles();
        
        drawCircles();
        
        
        float delta = Gdx.graphics.getDeltaTime();
        
        //updateTimes(delta);
        
        if (!gameOver) {
        	updateSpawnTimes(delta);
        	growCircles(delta);
        }
        
        
        checkCircleSize();
        
        
        if (gameOver && !gameOverPrompted) {
        	gameOverPrompt();
        }
        
        
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
    }
    
    
    private void drawRectangles() {
    	shapeBatch.begin(ShapeType.Line);
        shapeBatch.setColor(Color.BLACK);
        
        for (int i = 0; i < COLUMN_COUNT; i++) {
        	for (int j = 0; j < ROW_COUNT; j++) {
        		shapeBatch.rect(i * boxWidth, j * boxHeight, boxWidth, boxHeight);
        	}
        }
        
        shapeBatch.end();
    }
    
    private void drawCircles() {
    	shapeBatch.begin(ShapeType.Filled);
        shapeBatch.setColor(Color.BLACK);
        
        for (int c = 0; c < COLUMN_COUNT; c++) {
        	for (int r = 0; r < ROW_COUNT; r++) {
        		if (boxes[r][c].hasCircle) {
        			int x = c*boxWidth + boxWidth/2;
        			int y = r*boxHeight + boxHeight/2;
        			
        			shapeBatch.circle(x, y, boxes[r][c].circleRadius);
        		}
        	}
        }
        
        shapeBatch.end();
    }
    
    private void updateSpawnTimes(float delta) {
        totalTime += Gdx.graphics.getDeltaTime();
        timeSoFar += Gdx.graphics.getDeltaTime();
        if (timeSoFar > nextCircleTime) {
        	//spawn the new circle
        	//System.out.println("spawn");
        	spawnCircle();
        	
        	
        	
        	//reset timers
        	timeSoFar = 0;
        	//time is 1.0 -> 2.0s?
        	
        	//TODO
        	//nextCircleTime = (float) Math.random() * 1.0f + 1.0f;
        	nextCircleTime = generateNextSpawnTime();
        }
    }
    
    private void growCircles(float delta) {
    	for (Box[] boxRC : GameplayScreen.boxes) {
    		for (Box b : boxRC) {
        		
    			if (b.hasCircle) {
    				//b.circleRadius = b.circleRadius + delta * 10.0f;
    				b.circleRadius = b.circleRadius + delta * getGrowthRate();
    			}
    			
    		}
    	}
    }
    
    
    private void spawnCircle() {
    	int column = (int) (Math.random() * 4);
    	int row = (int) (Math.random() * 2);
    	
    	if (!boxes[row][column].hasCircle) {
    		boxes[row][column].hasCircle = true;
        	boxes[row][column].circleRadius = 30;
    	}
    }
    
    
    public static void updateScoreLabel() {
    	scoreLabel.setText("Score: " + score);
    }
    
    
    private void checkCircleSize() {
    	for (Box[] boxRC : GameplayScreen.boxes) {
    		for (Box b : boxRC) {
    			if (b.hasCircle) {
    				if (b.circleRadius > boxWidth / 2) {
    					gameOver = true;
    				}
    			}
    		}
    	}
    }
    
    private void gameOverPrompt() {
    	gameOverPrompted = true;
    	BlocParty.getActionResolver().gameOver(score);
    	Dialog prompt = new Dialog("Game Over", skin, "dialog") {
			protected void result (Object object) {
				//GameClass.getInstance().setScreen(new LevelSelectScreen());
				//System.gc();
				BlocParty.getInstance().setScreen(new MenuScreen());
//				gameOver = false;
//				gameOverPrompted = false;
			}
		};
        System.out.println(minScale);
        prompt.text("Score: " + score);
		prompt.button("OK", null);
        //prompt.setScale(minScale);
        prompt.setSize(400f * minScale, 200f * minScale);
		prompt.show(stage);
    }
    
    
    
    private void makeItFit() {


        
    	
    	
    	float scaleW = (float) Gdx.graphics.getWidth() / (float) INTENDED_WIDTH;
    	float scaleH = (float) Gdx.graphics.getHeight() / (float) INTENDED_HEIGHT;
    	
    	
    	if (scaleW < scaleH) {
    		minScale = scaleW;
    		maxScale = scaleH;
    	} else {
    		minScale = scaleH;
    		maxScale = scaleW;
    	}
    	
    	
    	
    	
    	batch = new SpriteBatch();
        shapeBatch = new ShapeRenderer();

        //hud = GameScreenHUD.create(round);


        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        boxWidth = width / COLUMN_COUNT;
        boxHeight = height / ROW_COUNT;
        
        
        
        
        //boxes = new ArrayList<Box>();
        boxes = new Box[2][4];
        for (int i = 0; i < COLUMN_COUNT; i++) {
        	for (int j = 0; j < ROW_COUNT; j++) {
        		Box b = new Box();
        		b.row = j;
        		b.column = i;
        		//boxes.add(b);
        		boxes[j][i] = b;
        	}
        }
        
        

        
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(new GameClickListener());
        Gdx.input.setInputProcessor(multiplexer);
        
        
        scoreLabel = new Label("Score: 0", skin);
        scoreLabel.setPosition(20, height - 40);
        //score.setPosition(20, 20);
        stage.addActor(scoreLabel);
        
        
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
        BlocParty.getInstance().setScreen(new MenuScreen());
    }
    @Override
    public void show() {
    }
}
