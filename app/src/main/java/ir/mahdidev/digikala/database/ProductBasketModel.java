package ir.mahdidev.digikala.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "product_basket")
public class ProductBasketModel {

    public ProductBasketModel(int productId, int productCount, String titleProduct,
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



    public int getProductId() {
        return productId;
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
    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public void setId(int id) {
        this.id = id;
    }

}
