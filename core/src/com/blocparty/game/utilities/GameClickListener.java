package com.blocparty.game.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

//extends InputAdapter
//implements InputProcessor
public class GameClickListener extends InputAdapter {

    public GameClickListener () {
        //
    }


    @Override
    public boolean touchUp (int x, int y, int pointer, int button) {

        System.out.println("up");

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
