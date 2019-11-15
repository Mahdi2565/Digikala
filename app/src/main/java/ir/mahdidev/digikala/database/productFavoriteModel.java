package ir.mahdidev.digikala.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "product_favorite")
public class productFavoriteModel {

    public productFavoriteModel(int productId) {
        this.productId = productId;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int productId;

    public int getId() {
        return id;
    }

    public int getProductId() {
        return productId;
    }
}
