package com.blocparty.game;

import com.badlogic.gdx.Game;
import com.blocparty.game.screens.MainScreen;

//a singleton, controller for the screens, and will possibly be a container for variables shared across screens...
//although variables shared across screens could possibly be a singleton as well
public class BlocParty extends Game {


	private static BlocParty GAME = null;

	private BlocParty() {
		//singleton
	}
	public static BlocParty getInstance() {
		if (GAME == null) {
			GAME = new BlocParty();
		}
		return GAME;
	}


	@Override
	public void create() {
		this.setScreen(new MainScreen());
	}

}
