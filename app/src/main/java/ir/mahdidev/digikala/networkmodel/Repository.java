package ir.mahdidev.digikala.networkmodel;

import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.List;

public class Repository {
    public static Repository instance;
    public static Repository getInstance(){
        if (instance == null ){
            instance = new Repository();
        }
        return instance;
    }

    private MutableLiveData<HashMap<String  , List>> productsAndCategoryListLiveData;

    public void setProductsList(HashMap<String , List> productsList){
        productsAndCategoryListLiveData = new MutableLiveData<>();
        productsAndCategoryListLiveData.setValue(productsList);
    }

    public MutableLiveData<HashMap<String, List>> getProductsAndCategoryListLiveData() {
        return productsAndCategoryListLiveData;
    }
}
