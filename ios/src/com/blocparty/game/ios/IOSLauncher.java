package com.blocparty.game.ios;

import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.uikit.UIAlertAction;
import org.robovm.apple.uikit.UIAlertActionStyle;
import org.robovm.apple.uikit.UIAlertController;
import org.robovm.apple.uikit.UIAlertControllerStyle;
import org.robovm.apple.uikit.UIApplication;
import org.robovm.apple.uikit.UIApplicationLaunchOptions;
import org.robovm.apple.uikit.UIViewController;
import org.robovm.objc.block.VoidBlock1;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import com.blocparty.game.ActionResolver;
import com.blocparty.game.BlocParty;
import com.blocparty.game.ConfirmInterface;
import com.blocparty.game.RequestHandler;

public class IOSLauncher extends IOSApplication.Delegate implements ActionResolver, RequestHandler {

    private UIViewController gameViewController;

    @Override
    protected IOSApplication createApplication() {
        IOSApplicationConfiguration config = new IOSApplicationConfiguration();
        config.orientationLandscape = true;
        config.orientationPortrait = false;
        BlocParty blocParty = BlocParty.getInstance(this, this);
        return new IOSApplication(blocParty, config);
    }

    @Override
    public boolean didFinishLaunching(final UIApplication application, final UIApplicationLaunchOptions launchOptions) {
        super.didFinishLaunching(application, launchOptions);
        gameViewController = application.getKeyWindow().getRootViewController();
        application.getKeyWindow().setRootViewController(gameViewController);
        application.getKeyWindow().addSubview(gameViewController.getView());
        application.getKeyWindow().makeKeyAndVisible();
        return false;
    }

    public static void main(String[] argv) {
        NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(argv, null, IOSLauncher.class);
        pool.close();
    }

    @Override
    public boolean getSignedInGPGS() {
        return false;
    }

    @Override
    public void loginGPGS() {

    }

    @Override
    public void gameOver(int score) {

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

    @Override
    public void confirm(ConfirmInterface confirmInterface, int score) {
        final ConfirmInterface cInterface = confirmInterface;
        UIAlertController alertController = new UIAlertController("Game Over",
                "You scored " + Integer.toString(score) + " point(s).", UIAlertControllerStyle.Alert);
        alertController.addAction(new UIAlertAction("Continue", UIAlertActionStyle.Default, new VoidBlock1<UIAlertAction>() {
            @Override
            public void invoke(UIAlertAction uiAlertAction) {
                cInterface.yes();
            }
        }));
        gameViewController.presentViewController(alertController, true, null);
    }
}