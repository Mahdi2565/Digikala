package ir.mahdidev.digikala.util;

import android.app.Application;

import java.text.NumberFormat;
import java.util.Locale;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.networkutil.ConnectivityReceiver;

public class MyApplication extends Application {
    private static MyApplication mInstance;
    private NumberFormat nf = NumberFormat.getInstance(new Locale("fa", "IR"));

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initFont();

    }

    private void initFont() {
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("byekan_font.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

    public String getPersianNumber(double i){
        return nf.format(i);
    }
}