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
}
