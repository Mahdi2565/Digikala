package ir.mahdidev.digikala.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductFavoriteDao {

    @Query("SELECT * FROM product_favorite")
    LiveData<List<ProductFavoriteModel>> getAllProductFavorite();

    @Query("SELECT * FROM product_favorite WHERE productId = :productId")
    ProductFavoriteModel getSingleProduct(int productId);

    @Insert
    void insert(ProductFavoriteModel productFavoriteDao);
    @Update
    void update(ProductFavoriteModel productFavoriteDao);
    @Delete
    void delete(ProductFavoriteModel productFavoriteDao);
}
