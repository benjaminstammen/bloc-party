package com.blocparty.game.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.blocparty.game.screens.GameplayScreen;
import com.blocparty.game.screens.GameplayScreen.Box;

//extends InputAdapter
//implements InputProcessor
public class GameClickListener extends InputAdapter {

    public GameClickListener () {
        //
    }


    @Override
    public boolean touchUp (int x, int y, int pointer, int button) {

        
    	y = Gdx.graphics.getHeight() - y;
    	
    	
    	//System.out.println("up");
        
    	
    	//int radius = GameplayScreen.boxWidth / 2;
    	
    	//this can be made more efficient with mod
    	for (Box[] boxRC : GameplayScreen.boxes) {
    		for (Box b : boxRC) {
    			int centerX = b.column * GameplayScreen.boxWidth + GameplayScreen.boxWidth / 2;
        		int centerY = b.row * GameplayScreen.boxHeight + GameplayScreen.boxHeight / 2;
        		float radius = b.circleRadius;
        		
        		if ((x-centerX)*(x-centerX) + (y-centerY)*(y-centerY) < radius*radius) {
        			//System.out.println(b.row);
        			//System.out.println(b.column);
        			
        			//clicked a circle
        			if (b.hasCircle) {
        				b.hasCircle = false;
        				
        				GameplayScreen.score++;
        				GameplayScreen.updateScoreLabel();
        				
        				//System.out.println(GameplayScreen.score);
        			}
        		}
    		}
    	}
    	
        

        return false;
    }


    private void validClick(int x, int y) {
//		Grid gr = Grid.getInstance();
//		Block clickedBlock = gr.getClosestBlock(x, y);
//		if (!round.currentPlayer.isNPC) {
//			round.blockClicked(clickedBlock);
//		}
    }
	/**/

}
