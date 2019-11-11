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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    public static Repository instance;

    private MutableLiveData<HashMap<String, List>> productsAndCategoryListLiveData =
            new MutableLiveData<>();
    private MutableLiveData<List<WebserviceCategoryModel>> categoryListLiveData = new MutableLiveData<>();
    private MutableLiveData<List<WebserviceProductModel>> amazingSuggestionProductListLiveData = new MutableLiveData<>();
    private MutableLiveData<List<WebserviceProductModel>> mostNewestProductListLiveData = new MutableLiveData<>();
    private MutableLiveData<List<WebserviceProductModel>> mostRatingProductListLiveData = new MutableLiveData<>();
    private MutableLiveData<List<WebserviceProductModel>> mostVisitingProductListLiveData = new MutableLiveData<>();
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

    public MutableLiveData<List<WebserviceCategoryModel>> loadCategoryList() throws IOException {
        categoryListLiveData.postValue(RetrofitConfig.getRetrofit().create(RetrofitApi.class)
                .getAllCategories().execute().body());
        return categoryListLiveData;
    }
    public MutableLiveData<List<WebserviceCategoryModel>> getCategoryListLiveData(){
        return categoryListLiveData;
    }
    public MutableLiveData<List<WebserviceProductModel>>  getAmazingSuggestionProductListLiveData(int page){
        RetrofitConfig.getRetrofit().create(RetrofitApi.class).getAllAmazingSuggestionProduct(
                Const.OrderTag.MOST_NEWEST_PRODUCT , 19 ,page
        ).enqueue(new Callback<List<WebserviceProductModel>>() {
            @Override
            public void onResponse(Call<List<WebserviceProductModel>> call, Response<List<WebserviceProductModel>> response) {
                if (response.isSuccessful()){
                    amazingSuggestionProductListLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<WebserviceProductModel>> call, Throwable t) {

            }
        });
        return amazingSuggestionProductListLiveData;
    }
    public MutableLiveData<List<WebserviceProductModel>>  getMostNewestProductListLiveData(int page){
        RetrofitConfig.getRetrofit().create(RetrofitApi.class).getAllSortedProduct(Const.OrderTag.MOST_NEWEST_PRODUCT , page)
                .enqueue(new Callback<List<WebserviceProductModel>>() {
                    @Override
                    public void onResponse(Call<List<WebserviceProductModel>> call, Response<List<WebserviceProductModel>> response) {
                        if (response.isSuccessful()){
                            mostNewestProductListLiveData.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<WebserviceProductModel>> call, Throwable t) {

                    }
                });
        return mostNewestProductListLiveData;
    }
    public MutableLiveData<List<WebserviceProductModel>>  getMostRatingProductListLiveData(int page){
        RetrofitConfig.getRetrofit().create(RetrofitApi.class).getAllSortedProduct(Const.OrderTag.MOST_RATING_PRODUCT , page)
                .enqueue(new Callback<List<WebserviceProductModel>>() {
                    @Override
                    public void onResponse(Call<List<WebserviceProductModel>> call, Response<List<WebserviceProductModel>> response) {
                        if (response.isSuccessful()){
                            mostRatingProductListLiveData.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<WebserviceProductModel>> call, Throwable t) {

                    }
                });
        return mostRatingProductListLiveData;
    }
    public MutableLiveData<List<WebserviceProductModel>>  getMostVisitingProductListLiveData(int page){
        RetrofitConfig.getRetrofit().create(RetrofitApi.class).getAllSortedProduct(Const.OrderTag.MOST_VISITING_PRODUCT , page)
                .enqueue(new Callback<List<WebserviceProductModel>>() {
                    @Override
                    public void onResponse(Call<List<WebserviceProductModel>> call, Response<List<WebserviceProductModel>> response) {
                        if (response.isSuccessful()){
                            mostVisitingProductListLiveData.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<WebserviceProductModel>> call, Throwable t) {

                    }
                });
        return mostVisitingProductListLiveData;
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
