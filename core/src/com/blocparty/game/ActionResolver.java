package com.blocparty.game;

/**
 * Created by jkahn on 11/14/15.
 */
public interface ActionResolver {

    public boolean getSignedInGPGS();
    public void loginGPGS();
    public void gameStarted();
    public void gameOver(int score);
    public void submitScoreGPGS(int score);
    public void unlockAchievementGPGS(String achievementId);
    public void getLeaderboardGPGS();
    public void getAchievementsGPGS();

}
