package ir.mahdidev.digikala.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "product_favorite")
public class ProductFavoriteModel {
    public ProductFavoriteModel(int productId, int productCount, String titleProduct,
                                String shortDescription, String imageSrc
            , String price, String finalPrice) {
        this.productId = productId;
        this.productCount = productCount;
        this.titleProduct = titleProduct;
        this.shortDescription = shortDescription;
        this.imageSrc = imageSrc;
        this.price = price;
        this.finalPrice = finalPrice;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int productId;
    private int productCount;
    private String titleProduct;
    private String shortDescription;
    private String imageSrc;
    private String price;
    private String finalPrice;

    public int getProductCount() {
        return productCount;
    }

    public String getTitleProduct() {
        return titleProduct;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public String getPrice() {
        return price;
    }

    public String getFinalPrice() {
        return finalPrice;
    }

    public int getId() {
        return id;
    }

    public int getProductId() {
        return productId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
