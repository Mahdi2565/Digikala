package ir.mahdidev.digikala.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import ir.mahdidev.digikala.networkmodel.customer.WebServiceCustomerModel;


public class Pref {
    public static void saveCustomerModelToPref(Context context , WebServiceCustomerModel webServiceCustomerModel){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Const.PrefKey.PREFNAME ,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonObject = gson.toJson(webServiceCustomerModel);
        sharedPreferences.edit().putString(Const.PrefKey.EMAIL_PREF_KEY , jsonObject ).apply();
    }
    public static WebServiceCustomerModel getCustomerModelFromPref(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Const.PrefKey.PREFNAME ,Context.MODE_PRIVATE);
        Gson gson=new Gson();
        WebServiceCustomerModel webServiceCustomerModel =
                gson.fromJson(sharedPreferences.getString(Const.PrefKey.EMAIL_PREF_KEY ,
                "")
                , WebServiceCustomerModel.class);
        return webServiceCustomerModel;
    }
    public static void logOutCustomer(Context context ){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Const.PrefKey.PREFNAME ,Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }
    public static void saveLastProductId(Context context , int productId){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Const.PrefKey.PREFNAME ,Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(Const.PrefKey.PRODUCT_ID_PREF_KEY , productId).apply();
    }
    public static int getLastProductId(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Const.PrefKey.PREFNAME ,Context.MODE_PRIVATE);
        return sharedPreferences.getInt(Const.PrefKey.PRODUCT_ID_PREF_KEY , 0);
    }
    public static void saveNotifRadioButtonId(Context context , int radioButtonId){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Const.PrefKey.PREFNAME ,Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(Const.PrefKey.NOTIF_RADIO_BUTTON_ID , radioButtonId).apply();
    }
    public static void saveNotifCustomTime(Context context , int customTime){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Const.PrefKey.PREFNAME ,Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(Const.PrefKey.NOTIF_CUSTOM_EDIT_TEXT , customTime).apply();
    }
    public static int getNotifRadioButton(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Const.PrefKey.PREFNAME ,Context.MODE_PRIVATE);
        return sharedPreferences.getInt(Const.PrefKey.NOTIF_RADIO_BUTTON_ID , 0);
    }
    public static int getNotifCustomTime(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Const.PrefKey.PREFNAME ,Context.MODE_PRIVATE);
        return sharedPreferences.getInt(Const.PrefKey.NOTIF_CUSTOM_EDIT_TEXT , 0);
    }
}
