package com.blocparty.game.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.blocparty.game.ActionResolver;
import com.blocparty.game.BlocParty;
import com.google.android.gms.games.Games;
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
        gameHelper.setMaxAutoSignInAttempts(Integer.MAX_VALUE);
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
    public void gameOver(int score)
    {
        SharedPreferences prefs = getSharedPreferences(Constants.PREFS, MODE_PRIVATE);
        int gameCount = prefs.getInt(Constants.GAME_COUNT, 0);
        gameCount++;
        SharedPreferences.Editor editor = getSharedPreferences(Constants.PREFS, MODE_PRIVATE).edit();
        editor.putInt(Constants.GAME_COUNT, gameCount);
        editor.commit();
        if (getSignedInGPGS()) {
            Log.d("gameOver", "game finished");
            submitScoreGPGS(score);
            if (score >= 50) unlockAchievementGPGS(Constants.GET_50);
            if (score >= 100) unlockAchievementGPGS(Constants.GET_100);
            if (score >= 500) unlockAchievementGPGS(Constants.GET_500);

            if (gameCount >= 1) unlockAchievementGPGS(Constants.PLAY_1);
            if (gameCount >= 10) unlockAchievementGPGS(Constants.PLAY_10);
        }
    }

	@Override
	public void submitScoreGPGS(int score) {
        Games.Leaderboards.submitScore(gameHelper.getApiClient(), Constants.LEADERBOARD_ID, score);
	}

	@Override
	public void unlockAchievementGPGS(String achievementId) {
        Games.Achievements.unlock(gameHelper.getApiClient(), achievementId);
	}

	@Override
	public void getLeaderboardGPGS() {
        Log.d("GetLeaderBoard", "Getting leaderboard.");
		if (gameHelper.isSignedIn()) {
            Log.d("GetLeaderBoard", "Game Helper things we're signed in.");
            startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(), Constants.LEADERBOARD_ID), Constants.REQUEST_LEADERBOARD);
		} else if (!gameHelper.isConnecting()) {
            Log.d("GetLeaderBoard", "Game Helper things we're signed in.");
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
