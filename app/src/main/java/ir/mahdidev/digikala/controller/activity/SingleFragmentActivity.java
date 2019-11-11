package ir.mahdidev.digikala.controller.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import ir.mahdidev.digikala.networkutil.ConnectivityReceiver;
import ir.mahdidev.digikala.util.MyApplication;

public abstract class SingleFragmentActivity extends AppCompatActivity
        implements ConnectivityReceiver.ConnectivityReceiverListener{
    private ConnectivityReceiver connectivityReceiver;

    public abstract Fragment createFragment();
    public abstract int setContentView();
    public abstract int containerId();
    public abstract String  fragmentTag();
    @Override
    protected void onResume() {
        super.onResume();
        checkInternetConnection();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContentView());

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTag());
        if (fragment == null)
            fragmentManager
                    .beginTransaction()
                    .replace(containerId(), createFragment())
                    .commit();
    }
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (!isConnected){
            Intent intent = NoInternetConnectionActivity.newIntent(SingleFragmentActivity.this);
            startActivity(intent);
        }
    }
    private void checkInternetConnection() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

        connectivityReceiver = new ConnectivityReceiver();
        registerReceiver(connectivityReceiver, intentFilter);
        MyApplication.getInstance().setConnectivityListener(this);
    }



    @Override
    protected void onDestroy() {
        if (connectivityReceiver!=null)
        unregisterReceiver(connectivityReceiver);
        super.onDestroy();
    }
}
