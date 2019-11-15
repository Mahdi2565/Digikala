package ir.mahdidev.digikala.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductBasketDao {
    @Query("SELECT * FROM product_basket")
    LiveData<List<ProductBasketModel>> getAllProductBasket();

    @Query("SELECT * FROM product_basket WHERE id = :id")
    ProductBasketModel productBasketModel(int id);

    @Insert
    void insert(ProductBasketModel productBasketModel);
    @Update
    void update(ProductBasketModel productBasketModel);
    @Delete
    void delete(ProductBasketModel productBasketModel);

}
