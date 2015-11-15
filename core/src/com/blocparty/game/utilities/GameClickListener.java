package com.blocparty.game.utilities;

import com.badlogic.gdx.InputAdapter;

//extends InputAdapter
//implements InputProcessor
public class GameClickListener extends InputAdapter {

    public GameClickListener () {
        //
    }


    @Override
    public boolean touchDown (int x, int y, int pointer, int button) {

        
//    	y = Gdx.graphics.getHeight() - y;
//
//
//    	//System.out.println("up");
//
//
//    	//int radius = GameplayScreen.boxWidth / 2;
//
//    	boolean hitCircle = false;
//
//    	//this can be made more efficient with mod
//    	for (Box[] boxRC : GameplayScreen.boxes) {
//    		for (Box b : boxRC) {
//    			int centerX = b.column * GameplayScreen.boxWidth + GameplayScreen.boxWidth / 2;
//        		int centerY = b.row * GameplayScreen.boxHeight + GameplayScreen.boxHeight / 2;
//        		float radius = b.circleRadius;
//
//        		if ((x-centerX)*(x-centerX) + (y-centerY)*(y-centerY) < radius*radius) {
//        			//System.out.println(b.row);
//        			//System.out.println(b.column);
//
//        			//clicked a circle
//        			if (b.hasCircle && !GameplayScreen.gameOver) {
//        				b.hasCircle = false;
//
//        				GameplayScreen.score++;
//        				GameplayScreen.updateScoreLabel();
//
//        				hitCircle = true;
//
//        				//System.out.println(GameplayScreen.score);
//        			}
//        		}
//    		}
//    	}
//
//        if (!hitCircle) {
//        	GameplayScreen.gameOver = true;
//        }

        return false;
    }
}
