package ir.mahdidev.digikala.util;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import java.text.NumberFormat;
import java.util.Locale;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.database.RoomConfig;
import ir.mahdidev.digikala.networkutil.ConnectivityReceiver;

public class MyApplication extends Application {
    private static MyApplication mInstance;
    private NumberFormat nf = NumberFormat.getInstance(new Locale("fa", "IR"));
    private RoomConfig roomDb;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initFont();
        initRoomDataBase();
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(Const.NOTIFICATION_CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void initRoomDataBase() {
       roomDb = RoomConfig.getInstance(this);
    }

    public RoomConfig getRoomDb() {
        return roomDb;
    }

    private void initFont() {
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("iran_sans_light.ttf")
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