package com.blocparty.game.android;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.blocparty.game.ActionResolver;
import com.blocparty.game.BlocParty;
import com.blocparty.game.ConfirmInterface;
import com.blocparty.game.RequestHandler;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;

public class AndroidLauncher extends AndroidApplication implements ActionResolver, GameHelper.GameHelperListener, RequestHandler {

	private GameHelper gameHelper;
    private View gameView;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        RelativeLayout layout = new RelativeLayout(this);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.numSamples = 2;
		BlocParty blocParty = BlocParty.getInstance(this, this);
		gameView = initializeForView(blocParty, config);
        layout.addView(gameView);

//        Button buyButton = new Button(this);
//        buyButton.setText("adsfadsfasdfa");
//        buyButton.setLayoutParams(new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT));
//
//        layout.addView(buyButton);
        setContentView(layout);

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
        editor.apply();
        if (getSignedInGPGS()) {
            Log.d("gameOver", "game finished");
            submitScoreGPGS(score);

            if (score == 0) unlockAchievementGPGS(Constants.ZERO_FINISH);

            if (score >= 50) unlockAchievementGPGS(Constants.GET_50);
            if (score >= 100) unlockAchievementGPGS(Constants.GET_100);
            if (score >= 500) unlockAchievementGPGS(Constants.GET_500);
            if (score >= 1000) unlockAchievementGPGS(Constants.GET_1000);

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
            Log.d("GetLeaderBoard", "Game Helper thinks we're signed in.");
            startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(), Constants.LEADERBOARD_ID), Constants.REQUEST_LEADERBOARD);
		} else if (!gameHelper.isConnecting()) {
            Log.d("GetLeaderBoard", "Game Helper thinks we're not signed in, nor are we trying.");
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

    @Override
    public void confirm(final ConfirmInterface confirmInterface, final int score) {
        gameView.post(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(AndroidLauncher.this, R.style.Theme_AppCompat_Dialog_Alert)
                        .setTitle("Game Over")
                        .setMessage("You scored " + Integer.toString(score) + " point(s).")
                        .setCancelable(false)
                        .setNeutralButton("Continue", new Dialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                confirmInterface.yes();
                                dialogInterface.cancel();
                            }
                }).create().show();
            }
        });
    }
}
