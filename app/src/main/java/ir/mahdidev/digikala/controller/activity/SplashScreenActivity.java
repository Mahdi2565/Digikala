package ir.mahdidev.digikala.controller.activity;

import androidx.fragment.app.Fragment;

import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.controller.fragment.SplashScreenFragment;
import ir.mahdidev.digikala.networkutil.ConnectivityReceiver;
import ir.mahdidev.digikala.util.Const;

public class SplashScreenActivity extends SingleFragmentActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    @Override
    public Fragment createFragment() {
        return SplashScreenFragment.newInstance();
    }

    @Override
    public int setContentView() {
        return R.layout.activity_splash_screen;
    }

    @Override
    public int containerId() {
        return R.id.frame_layout;
    }

    @Override
    public String fragmentTag() {
        return Const.SPLASH_SCREEN_FRAGMENT_TAG;
    }

}
