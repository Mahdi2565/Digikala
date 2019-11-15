package ir.mahdidev.digikala.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "product_basket")
public class ProductBasketModel {

    public ProductBasketModel(int productId, int productCount) {
        this.productId = productId;
        this.productCount = productCount;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int productId;

    private int productCount;

    public int getId() {
        return id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }
}
