package ir.mahdidev.digikala.networkmodel;

import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ir.mahdidev.digikala.networkmodel.category.WebserviceCategoryModel;
import ir.mahdidev.digikala.networkmodel.product.WebserviceProductModel;
import ir.mahdidev.digikala.networkutil.RetrofitApi;
import ir.mahdidev.digikala.networkutil.RetrofitConfig;
import ir.mahdidev.digikala.util.Const;

public class Repository {
    public static Repository instance;

    private MutableLiveData<HashMap<String, List>> productsAndCategoryListLiveData =
            new MutableLiveData<>();
    private MutableLiveData<Integer> productIdMutableLiveData = new MutableLiveData<>();

    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }

    public void setProductsList(HashMap<String, List> productsList) {
        productsAndCategoryListLiveData.setValue(productsList);
    }

    public MutableLiveData<HashMap<String, List>> getProductsAndCategoryListLiveData() {
        return productsAndCategoryListLiveData;
    }

    public HashMap<String, List> getProductsAndCategoryList() {
        HashMap<String, List> productAndCategoryListHashMap
                = new HashMap<>();
        try {
            List<WebserviceProductModel> mostVisitedProductList =
                    RetrofitConfig.getRetrofit().create(RetrofitApi.class)
                            .getAllSortedProduct("popularity").execute().body();

            List<WebserviceProductModel> mostRatingProductList =
                    RetrofitConfig.getRetrofit().create(RetrofitApi.class)
                            .getAllSortedProduct("rating").execute().body();

            List<WebserviceProductModel> mostNewestProductList =
                    RetrofitConfig.getRetrofit().create(RetrofitApi.class)
                            .getAllSortedProduct("date").execute().body();
            List<WebserviceCategoryModel> allCategories = RetrofitConfig.getRetrofit()
                    .create(RetrofitApi.class).getAllCategories().execute().body();
            List<WebserviceProductModel> amazingSuggestion = new ArrayList<>();

            // TODO: 11/10/2019 amazingSuggestionWithTag
            for (WebserviceProductModel products : mostNewestProductList){
                if (!products.getRegularPrice().equals(products.getPrice())){
                    amazingSuggestion.add(products);
                }
            }
            productAndCategoryListHashMap.put(Const.KeyList.MOST_VISITING_LIST, mostVisitedProductList);
            productAndCategoryListHashMap.put(Const.KeyList.MOST_RATING_LIST, mostRatingProductList);
            productAndCategoryListHashMap.put(Const.KeyList.MOST_NEWEST_LIST, mostNewestProductList);
            productAndCategoryListHashMap.put(Const.KeyList.CATEGORY_LIST, allCategories);
            productAndCategoryListHashMap.put(Const.KeyList.AMAZING_SUGGESTION , amazingSuggestion);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productAndCategoryListHashMap;
    }

    public List<WebserviceProductModel> getNextPageProduct(String orderBy, int page) {
        List<WebserviceProductModel> productModelList = new ArrayList<>();

        try {
           productModelList =  RetrofitConfig.getRetrofit().create(RetrofitApi.class).getNextPageSortedProduct(orderBy
                    , page).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productModelList;
    }
    public void setProductId(int productId){
        productIdMutableLiveData.setValue(productId);
    }

    public MutableLiveData<Integer> getProductIdMutableLiveData() {
        return productIdMutableLiveData;
    }
}
