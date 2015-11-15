package com.blocparty.game.screens;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.blocparty.game.BlocParty;
import com.blocparty.game.actors.ExpandingTile;
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
    public static ExpandingTile[][] expandingTiles;
    
    
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
    	
    	float timeRange = 1.0f - (totalTime / 40.0f);
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

    // This is where we update the model of the tiles and see if the game must end.
    public void update(float delta){

        updateSpawnTimes(delta);
        updateScoreLabel();

        for (int c = 0; c < COLUMN_COUNT; c++) {
            for (int r = 0; r < ROW_COUNT; r++) {
                if (expandingTiles[r][c].isActive()) {
                    expandingTiles[r][c].update(delta);
                }
                if(expandingTiles[r][c].isLoser()){
                    gameOver = true;
                    gameOverPrompt();
                }
            }
        }
    }

    private void updateSpawnTimes(float delta) {
        totalTime += Gdx.graphics.getDeltaTime();
        timeSoFar += Gdx.graphics.getDeltaTime();
        if (timeSoFar > nextCircleTime) {

            spawnCircle();

            //reset timers
            timeSoFar = 0;
            //time is 1.0 -> 2.0s?

            //TODO
            //nextCircleTime = (float) Math.random() * 1.0f + 1.0f;
            nextCircleTime = generateNextSpawnTime();
        }
    }

    public static void updateScoreLabel() {
        scoreLabel.setText("Score: " + score);
    }

    private void spawnCircle() {
        int column = (int) (Math.random() * 4);
        int row = (int) (Math.random() * 2);

        if (!expandingTiles[row][column].isActive()) {
            expandingTiles[row][column].activateTile(10);
        }
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        drawRectangles();
        
        drawCircles();
        
        if (!gameOver) {
        	updateSpawnTimes(delta);
            update(delta);
        }
        
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
        		if (expandingTiles[r][c].isActive()) {
                    expandingTiles[r][c].draw(shapeBatch);
        		}
        	}
        }
        
        shapeBatch.end();
    }
    
    private void gameOverPrompt() {
    	gameOverPrompted = true;
    	
    	Dialog prompt = new Dialog("Game Over", skin, "dialog") {
			protected void result (Object object) {
				//GameClass.getInstance().setScreen(new LevelSelectScreen());
				//System.gc();
				BlocParty.getInstance().setScreen(new MenuScreen());
//				gameOver = false;
//				gameOverPrompted = false;
			}
		};
		prompt.text("Score: " + score);
		prompt.button("OK", null);
		prompt.show(stage);
		prompt.setSize(200f, 100f);
    }

    private void makeItFit() {
    	float scaleW = Gdx.graphics.getWidth() / INTENDED_WIDTH;
    	float scaleH = Gdx.graphics.getHeight() / INTENDED_HEIGHT;
    	
    	
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

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
//        stage.addListener(new InputListener(){
//            @Override
//            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                for (int i = 0; i < COLUMN_COUNT; i++) {
//                    for (int j = 0; j < ROW_COUNT; j++) {
//                        if(expandingTiles[i][j].equals(stage.hit(x, y, false))){
//                            return true;
//                        }
//                    }
//                }
//
//                Gdx.app.exit();
//                return true;
//            }
//        });

        skin = new Skin(Gdx.files.internal("data/uiskin.json"));

        expandingTiles = new ExpandingTile[2][4];

        for (int i = 0; i < COLUMN_COUNT; i++) {
        	for (int j = 0; j < ROW_COUNT; j++) {
        		ExpandingTile tile = new ExpandingTile(boxWidth, boxHeight);
                tile.setPosition(i * boxWidth + boxWidth / 2, j * boxHeight + boxHeight / 2);
        		expandingTiles[j][i] = tile;
                stage.addActor(tile);
        	}
        }
        
//        InputMultiplexer multiplexer = new InputMultiplexer();
//        multiplexer.addProcessor(stage);
//        multiplexer.addProcessor(new InputAdapter(){
//             @Override
//             public boolean touchDown (int x, int y, int pointer, int button) {
//                 for (int i = 0; i < COLUMN_COUNT; i++) {
//                     for (int j = 0; j < ROW_COUNT; j++) {
//                         if (expandingTiles[i][j].equals(stage.hit(x, y, false))) {
//                             return true;
//                         }
//                     }
//                 }
//
//                 Gdx.app.exit();
//                 return true;
//             }
//         });

//        Gdx.input.setInputProcessor(multiplexer);
        Gdx.input.setInputProcessor(stage);
        
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
    }
    @Override
    public void show() {
    }
}
