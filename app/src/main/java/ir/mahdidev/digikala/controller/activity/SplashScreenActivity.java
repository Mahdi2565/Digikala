package ir.mahdidev.digikala.controller.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.networkutil.ConnectivityReceiver;
import ir.mahdidev.digikala.util.MyApplication;

public class SplashScreenActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {
    private ConnectivityReceiver connectivityReceiver ;

    @Override
    protected void onResume() {
        super.onResume();
        checkInternetConnection();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);
    }


    private void checkInternetConnection() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

        connectivityReceiver = new ConnectivityReceiver();
            registerReceiver(connectivityReceiver, intentFilter);
        MyApplication.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (!isConnected){
            Intent intent = NoInternetConnectionActivity.newIntent(SplashScreenActivity.this);
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(connectivityReceiver);
        super.onDestroy();
    }

}
