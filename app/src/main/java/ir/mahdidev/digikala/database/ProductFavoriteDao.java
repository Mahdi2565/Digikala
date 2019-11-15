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
    LiveData<List<ProductFavoriteDao>> getAllProductFavorite();

    @Query("SELECT * FROM product_favorite WHERE id = :id")
    ProductFavoriteDao productFavoriteDao(int id);

    @Insert
    void insert(ProductFavoriteDao productFavoriteDao);
    @Update
    void update(ProductFavoriteDao productFavoriteDao);
    @Delete
    void delete(ProductFavoriteDao productFavoriteDao);
}
