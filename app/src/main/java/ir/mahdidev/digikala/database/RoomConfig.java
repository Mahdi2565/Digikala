package ir.mahdidev.digikala.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = {ProductFavoriteModel.class ,ProductBasketModel.class} , version = 1, exportSchema = false)
public abstract class RoomConfig extends RoomDatabase {
    public abstract ProductBasketDao productBasketDao();
    public abstract ProductFavoriteDao productFavoriteDao();
    public abstract CustomerAddressDao customerAddressDao();

    private static volatile RoomConfig instance;
    private static final Object LOCK = new Object();

    public static RoomConfig getInstance(Context context) {
        if (instance == null){
            synchronized (LOCK){
                if (instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext() , RoomConfig.class ,
                            "digikala.db")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return instance;
    }
}
