package com.blocparty.game.android;

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.blocparty.game.ActionResolver;
import com.blocparty.game.BlocParty;
import com.google.example.games.basegameutils.GameHelper;

public class AndroidLauncher extends AndroidApplication implements ActionResolver, GameHelper.GameHelperListener {

	private GameHelper gameHelper;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		BlocParty blocParty = BlocParty.getInstance(this);
		initialize(blocParty, config);

		if (gameHelper == null) {
			gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
			gameHelper.enableDebugLog(true);
		}
		gameHelper.setup(this);
	}

	@Override
	public void onStart(){
		super.onStart();
		gameHelper.onStart(this);
	}

	@Override
	public void onStop(){
		super.onStop();
		gameHelper.onStop();
	}

	@Override
	public void onActivityResult(int request, int response, Intent data) {
		super.onActivityResult(request, response, data);
		gameHelper.onActivityResult(request, response, data);
	}

	@Override
	public boolean getSignedInGPGS() {
		return gameHelper.isSignedIn();
	}

	@Override
	public void loginGPGS() {
		try {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					gameHelper.beginUserInitiatedSignIn();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void submitScoreGPGS(int score) {

	}

	@Override
	public void unlockAchievementGPGS(String achievementId) {

	}

	@Override
	public void getLeaderboardGPGS() {
		if (gameHelper.isSignedIn())
		{
		} else if (!gameHelper.isConnecting()) {
			loginGPGS();
		}
	}

	@Override
	public void getAchievementsGPGS() {

	}

	@Override
	public void onSignInFailed() {

	}

	@Override
	public void onSignInSucceeded() {

	}
}
