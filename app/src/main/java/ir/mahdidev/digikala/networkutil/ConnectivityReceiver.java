package ir.mahdidev.digikala.networkutil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import ir.mahdidev.digikala.util.MyApplication;

public class ConnectivityReceiver
        extends BroadcastReceiver {

    public static ConnectivityReceiverListener connectivityReceiverListener;

    public ConnectivityReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent arg1) {

        boolean isConnected = haveNetwork(context);

        if (connectivityReceiverListener != null) {
            connectivityReceiverListener.onNetworkConnectionChanged(isConnected);
        }
    }


    public static boolean isConnected(Context context) {

        return haveNetwork(context);
    }


    public interface ConnectivityReceiverListener {
        void onNetworkConnectionChanged(boolean isConnected);
    }
    private static boolean haveNetwork(Context context) {
        boolean has_wifi = false;
        boolean has_mobile_data = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos= connectivityManager.getAllNetworkInfo();
        for(NetworkInfo info: networkInfos){
            if(info.getTypeName().equalsIgnoreCase("Wifi")){
                if(info.isConnected()){
                    has_wifi=true;
                }
            }
            if(info.getTypeName().equalsIgnoreCase("Mobile")){
                if(info.isConnected()){
                    has_mobile_data=true;
                }
            }
        }
        return has_wifi || has_mobile_data;

}
}