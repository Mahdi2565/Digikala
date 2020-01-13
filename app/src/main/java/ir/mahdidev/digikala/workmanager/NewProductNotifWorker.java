package ir.mahdidev.digikala.workmanager;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.controller.activity.ProductActivity;
import ir.mahdidev.digikala.networkmodel.product.WebserviceProductModel;
import ir.mahdidev.digikala.networkutil.RetrofitApi;
import ir.mahdidev.digikala.networkutil.RetrofitConfig;
import ir.mahdidev.digikala.util.Const;
import ir.mahdidev.digikala.util.Pref;
import retrofit2.Response;

public class NewProductNotifWorker extends Worker {

    public NewProductNotifWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        WebserviceProductModel webserviceProductModel = getLastProductId();
        int serverProductId = webserviceProductModel.getId();
        int clientProductId = Pref.getLastProductId(getApplicationContext());

        if (webserviceProductModel.getId() == null) return Result.retry();
        if (serverProductId > clientProductId){
            try {
                Bitmap productBitmap = Picasso.get().load(webserviceProductModel.getImages().get(0).getSrc()).get();
                createNotification(webserviceProductModel.getId() ,webserviceProductModel.getName() , productBitmap);
                Pref.saveLastProductId(getApplicationContext() , webserviceProductModel.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Result.success();
        }else {
            return Result.failure();
        }
    }
    private WebserviceProductModel getLastProductId(){
        try {
            Response<List<WebserviceProductModel>> productResponse = RetrofitConfig.getRetrofit().create(RetrofitApi.class).getsortedProductsList("desc", "date",
                    1, "", "", null).execute();

            if (productResponse.isSuccessful()){
                return productResponse.body().get(0);
            }else return null;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void createNotification(int productId , String productTitle , Bitmap productImage) {
        Intent resultIntent = new Intent(getApplicationContext(), ProductActivity.class);
        resultIntent.putExtra(Const.IntentKey.PRODUCT_ID_FORM_NOTIF_TO_PRODUCT_ACTIVITY , productId);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder newestProduct = new NotificationCompat.Builder(getApplicationContext() , Const.NOTIFICATION_CHANNEL_ID);
        newestProduct.setContentTitle(getApplicationContext().getString(R.string.new_product));
        newestProduct.setContentText("محصول " + productTitle + " اضافه شد");
        newestProduct.setSmallIcon(R.drawable.ic_digikala_notif);
        newestProduct.setAutoCancel(true);
        newestProduct.setBadgeIconType(NotificationCompat.BADGE_ICON_LARGE);
        newestProduct.setPriority(NotificationCompat.PRIORITY_HIGH);
        newestProduct.setColor(getApplicationContext().getResources().getColor(R.color.colorPrimary));
        if (productImage !=null){
            newestProduct.setStyle(new NotificationCompat.BigPictureStyle()
                    .bigPicture(productImage));
        }
        newestProduct.setContentIntent(resultPendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        notificationManager.notify(25, newestProduct.build());
    }

}
