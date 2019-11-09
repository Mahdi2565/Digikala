package ir.mahdidev.digikala.networkmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import ir.mahdidev.digikala.networkmodel.product.WebserviceProductModel;

public class Repository {
    public static Repository instance;
    public static Repository getInstance(){
        if (instance == null ){
            instance = new Repository();
        }
        return instance;
    }
    private MutableLiveData<List<WebserviceProductModel>> mostVisitedProduct;
    private MutableLiveData<List<WebserviceProductModel>> mostNewestProduct;
    private MutableLiveData<List<WebserviceProductModel>> mostRatingProduct;

    public void setMostVisitedProduct(List<WebserviceProductModel> webserviceProductList){
        mostVisitedProduct = new MutableLiveData<>();
        if (webserviceProductList == null){
            mostVisitedProduct.setValue(null);
        }else {
            mostVisitedProduct.setValue(webserviceProductList);
        }
    }

    public void setMostRatingProduct(List<WebserviceProductModel> webserviceProductList){
        mostRatingProduct = new MutableLiveData<>();
        if (webserviceProductList == null){
            mostRatingProduct.setValue(null);
        }else {
            mostRatingProduct.setValue(webserviceProductList);
        }
    }

    public void setMostNewestProduct(List<WebserviceProductModel> webserviceProductList){
        mostNewestProduct = new MutableLiveData<>();
        if (webserviceProductList == null){
            mostNewestProduct.setValue(null);
        }else {
            mostNewestProduct.setValue(webserviceProductList);
        }

    }

    public MutableLiveData<List<WebserviceProductModel>> getMostVisitedProduct() {
        return mostVisitedProduct;
    }

    public MutableLiveData<List<WebserviceProductModel>> getMostNewestProduct() {
        return mostNewestProduct;
    }

    public MutableLiveData<List<WebserviceProductModel>> getMostRatingProduct() {
        return mostRatingProduct;
    }
}
