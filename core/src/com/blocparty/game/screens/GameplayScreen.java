package com.blocparty.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.blocparty.game.BlocParty;
import com.blocparty.game.actors.ExpandingTile;
import com.blocparty.game.ConfirmInterface;

import java.util.ArrayList;
import java.util.List;

public class GameplayScreen implements Screen {

    Stage stage;
    Skin skin;

    SpriteBatch batch;
    ShapeRenderer shapeBatch;
    private List<ParticleEffect> particleEffects;

    public static int INTENDED_WIDTH = 950;
    public static int INTENDED_HEIGHT = 500;
    
    float minScale = 1.0f;
	float maxScale = 1.0f;
    
    float x_coord = 0f;
    float y_coord = 0f;

    private int width;
    private int height;
    public static int boxWidth;
    public static int boxHeight;

    public static ExpandingTile[][] expandingTiles;

    float nextCircleTime;
    float timeElapsed;
    float totalTime = 0;

    private static final int ROW_COUNT = 2;
    private static final int COLUMN_COUNT = 4;
    
    public static int score = 0;
    public static int time = 0;
    
    public static Label scoreLabel;
    public static Label timeLabel;
    public static boolean gameOver = false;
    public static boolean gameOverPrompted = false;



    private void resetValues() {
    	gameOver = false;
		gameOverPrompted = false;
		score = 0;
        time = 0;
		
		totalTime  = 0;
		nextCircleTime = 0;
		timeElapsed = 0;

        particleEffects = new ArrayList<ParticleEffect>();
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

        for(int i = 0; i < particleEffects.size(); i++){
            ParticleEffect particleEffect = particleEffects.get(i);
            particleEffect.update(delta);
            if(particleEffect.isComplete()){
                particleEffects.remove(i);
                i--;
            }
        }

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

    public float getExpandTime() {
        return 25f / (float) Math.pow(totalTime + 40, 0.5f) + 1;
    }

    public static void updateScoreLabel() {
        scoreLabel.setText("Score: " + score);
    }

    @Override
    public void render(float delta) {

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            BlocParty.getInstance().setScreen(new MenuScreen());
        }

        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        drawRectangles();
        
        drawCircles();

        batch.begin();
        for(ParticleEffect particleEffect : particleEffects){
            particleEffect.draw(batch);
        }
        batch.end();

        if (!gameOver) {
            update(delta);
        } else {
            shapeBatch.begin(ShapeType.Filled);
            shapeBatch.setColor(Color.WHITE);
            shapeBatch.circle(x_coord, y_coord, 10 * minScale);
            shapeBatch.end();
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

    public void addParticleToDraw(ParticleEffect particleEffect){
        particleEffects.add(particleEffect);
    }

    private void updateSpawnTimes(float delta) {
        totalTime += Gdx.graphics.getDeltaTime();
        timeElapsed += Gdx.graphics.getDeltaTime();
        time = (int) totalTime;
        updateTimeLabel();
        if (timeElapsed > nextCircleTime) {
        	//spawn the new circle
        	//System.out.println("spawn");
        	spawnCircle();

        	//reset timers
        	timeElapsed = 0;
        	//time is 1.0 -> 2.0s?

        	//TODO
        	//nextCircleTime = (float) Math.random() * 1.0f + 1.0f;
        	nextCircleTime = generateNextSpawnTime();
        }
    }

    private void spawnCircle() {
        int column = (int) (Math.random() * 4);
        int row = (int) (Math.random() * 2);

        if (!expandingTiles[row][column].isActive()) {
            expandingTiles[row][column].activateTile(getExpandTime());
        }
    }

    private float generateNextSpawnTime() {

        float baseTime = 1.0f - (totalTime / 10.0f);
        if (baseTime < 0) {
            baseTime = 0;
        }

        float timeRange = 1.0f - (totalTime / 40.0f);
        if (timeRange < 0.5) {
            timeRange = 0.5f;
        }

        // nextTime
        return (float) Math.random() * timeRange + baseTime;
    }

    public static void updateTimeLabel(){
        timeLabel.setText("Time: " + time);
    }
    
    private void gameOverPrompt() {
    	gameOverPrompted = true;
    	BlocParty.getActionResolver().gameOver(score);
        BlocParty.getRequestHandler().confirm(new ConfirmInterface() {
            @Override
            public void yes() {
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        BlocParty.getInstance().setScreen(new MenuScreen());
                    }
                });
            }

            @Override
            public void no() {

            }
        }, score);
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

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("data/uiskin.json"));

        expandingTiles = new ExpandingTile[2][4];

        for (int i = 0; i < COLUMN_COUNT; i++) {
        	for (int j = 0; j < ROW_COUNT; j++) {
        		ExpandingTile tile = new ExpandingTile(i * boxWidth + boxWidth / 2, j * boxHeight + boxHeight / 2, boxWidth, boxHeight);
        		expandingTiles[j][i] = tile;
                stage.addActor(tile);
        	}
        }
        stage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                for (int i = 0; i < COLUMN_COUNT; i++) {
                    for (int j = 0; j < ROW_COUNT; j++) {
                        if(expandingTiles[j][i].equals(stage.hit(x, y, false))){
                            addParticleToDraw(expandingTiles[j][i].deactivateTile());
                            score++;
                            return true;
                        }
                    }
                }

                x_coord = x;
                y_coord = y;

                gameOver = true;
                return false;
            }
        });
        
        scoreLabel = new Label("Score: 0", skin);
        scoreLabel.setPosition(20, height - 40);
        scoreLabel.setFontScale(minScale);
        timeLabel = new Label("Time: 0", skin);
        timeLabel.setPosition(20, height - 80);
        timeLabel.setFontScale(minScale);
        //score.setPosition(20, 20);
        stage.addActor(scoreLabel);
        stage.addActor(timeLabel);
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
        Gdx.input.setCatchBackKey(true);
    }
}
