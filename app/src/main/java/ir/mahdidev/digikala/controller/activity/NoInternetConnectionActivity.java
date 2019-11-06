package ir.mahdidev.digikala.controller.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.networkutil.ConnectivityReceiver;

public class NoInternetConnectionActivity extends AppCompatActivity {

    @BindView(R.id.check_network_txt)
    LinearLayout checkNetworkTxt;
    @BindView(R.id.try_again_btn)
    Button tryAgainBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet_connection);
        ButterKnife.bind(this);
        checkNetworkTxtFunction();
        tryAgainBtnFunction();
    }

    private void tryAgainBtnFunction() {
        tryAgainBtn.setOnClickListener(view -> {
            if (ConnectivityReceiver.isConnected(this)){
                finish();
            }
        });
    }

    public static Intent newIntent(Context context){
        return new Intent(context , NoInternetConnectionActivity.class);
    }
    public void openWifiSettings() {
        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    private void checkNetworkTxtFunction() {
        checkNetworkTxt.setOnClickListener(view -> openWifiSettings());
    }
    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
