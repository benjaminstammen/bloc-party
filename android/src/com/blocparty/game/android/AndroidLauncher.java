package com.blocparty.game.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.blocparty.game.ActionResolver;
import com.blocparty.game.BlocParty;

public class AndroidLauncher extends AndroidApplication implements ActionResolver {//, GameHelperListener {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		BlocParty blocParty = BlocParty.getInstance(this);
		initialize(blocParty, config);
	}

	@Override
	public boolean getSignedInGPGS() {
		return false;
	}

	@Override
	public void loginGPGS() {

	}

	@Override
	public void submitScoreGPGS(int score) {

	}

	@Override
	public void unlockAchievementGPGS(String achievementId) {

	}

	@Override
	public void getLeaderboardGPGS() {

	}

	@Override
	public void getAchievementsGPGS() {

	}
}
