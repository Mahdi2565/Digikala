package ir.mahdidev.digikala.networkmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ir.mahdidev.digikala.database.ProductBasketModel;
import ir.mahdidev.digikala.database.ProductFavoriteModel;
import ir.mahdidev.digikala.database.RoomConfig;
import ir.mahdidev.digikala.networkmodel.category.WebserviceCategoryModel;
import ir.mahdidev.digikala.networkmodel.comment.WebServiceCommentModel;
import ir.mahdidev.digikala.networkmodel.product.WebserviceProductModel;
import ir.mahdidev.digikala.networkutil.RetrofitApi;
import ir.mahdidev.digikala.networkutil.RetrofitConfig;
import ir.mahdidev.digikala.util.Const;
import ir.mahdidev.digikala.util.MyApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    public static Repository instance;
    private RoomConfig roomConfig;
    private MutableLiveData<List<WebserviceCategoryModel>> categoryListLiveData = new MutableLiveData<>();
    private MutableLiveData<List<WebserviceProductModel>> amazingSuggestionProductListLiveData = new MutableLiveData<>();
    private MutableLiveData<List<WebserviceProductModel>> mostNewestProductListLiveData = new MutableLiveData<>();
    private MutableLiveData<List<WebserviceProductModel>> mostRatingProductListLiveData = new MutableLiveData<>();
    private MutableLiveData<List<WebserviceProductModel>> mostVisitingProductListLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> productIdMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<WebserviceProductModel> singleProductMutableLiveData;
    private MutableLiveData<List<WebserviceCategoryModel>> productCategoriesMutableLiveData;
    private MutableLiveData<List<WebserviceProductModel>> relatedProductMutableLiveData;
    private MutableLiveData<List<WebserviceProductModel>> especialProductsMutabaleLiveData = new MutableLiveData<>();
    private MutableLiveData<List<WebServiceCommentModel>> commentProductsMutableLiveData = new MutableLiveData<>();

    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }

    private Repository(){
        roomConfig = MyApplication.getInstance().getRoomDb();
    }

    public LiveData<Integer> getProductBasketCountDb(){
        return roomConfig.productBasketDao().getProductCount();
    }
    public LiveData<List<ProductBasketModel>> getAllProductBasketDb(){
        return roomConfig.productBasketDao().getAllProductBasket();
    }
    public ProductBasketModel getSingleProductBaskerDb(int productId){
        return roomConfig.productBasketDao().getSingleProduct(productId);
    }
    public void insertProductBaskerDb(ProductBasketModel productBasketModel){
        roomConfig.productBasketDao().insert(productBasketModel);
    }
    public void updateProductBaskerDb(ProductBasketModel productBasketModel){
        roomConfig.productBasketDao().update(productBasketModel);
    }
    public void deleteProductBaskerDb(ProductBasketModel productBasketModel){
        roomConfig.productBasketDao().delete(productBasketModel);
    }

    public LiveData<List<ProductFavoriteModel>> getAllProductFavoriteDb(){
        return roomConfig.productFavoriteDao().getAllProductFavorite();
    }
    public ProductFavoriteModel getSingleProductFavorite(int productId){
        return roomConfig.productFavoriteDao().getSingleProduct(productId);
    }

    public void insertProductFavoriteDb(ProductFavoriteModel productFavoriteModel){
        roomConfig.productFavoriteDao().insert(productFavoriteModel);
    }
    public void deleteProductFavoriteDb(ProductFavoriteModel productFavoriteModel){
        roomConfig.productFavoriteDao().delete(productFavoriteModel);
    }

    public MutableLiveData<List<WebserviceCategoryModel>> loadCategoryList() throws IOException {
        categoryListLiveData.postValue(RetrofitConfig.getRetrofit().create(RetrofitApi.class)
                .getAllCategories().execute().body());
        return categoryListLiveData;
    }

    public MutableLiveData<List<WebserviceCategoryModel>> getCategoryListLiveData() {
        return categoryListLiveData;
    }
    public void loadCategoryListFromMainFragment(){
        RetrofitConfig.getRetrofit().create(RetrofitApi.class)
                .getAllCategories().enqueue(new Callback<List<WebserviceCategoryModel>>() {
            @Override
            public void onResponse(Call<List<WebserviceCategoryModel>> call, Response<List<WebserviceCategoryModel>> response) {
                if (response.isSuccessful()){
                    List<WebserviceCategoryModel> categoryList = new ArrayList<>(response.body());
                    categoryListLiveData.setValue(categoryList);
                }
            }

            @Override
            public void onFailure(Call<List<WebserviceCategoryModel>> call, Throwable t) {

            }
        });
    }

    public MutableLiveData<List<WebserviceProductModel>> getAmazingSuggestionProductListLiveData(int page) {
        RetrofitConfig.getRetrofit().create(RetrofitApi.class).getAllAmazingSuggestionProduct(
                Const.OrderTag.MOST_NEWEST_PRODUCT, 19, page
        ).enqueue(new Callback<List<WebserviceProductModel>>() {
            @Override
            public void onResponse(Call<List<WebserviceProductModel>> call, Response<List<WebserviceProductModel>> response) {
                if (response.isSuccessful()) {
                    amazingSuggestionProductListLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<WebserviceProductModel>> call, Throwable t) {

            }
        });
        return amazingSuggestionProductListLiveData;
    }

    public MutableLiveData<List<WebserviceProductModel>> getMostNewestProductListLiveData(int page) {
        RetrofitConfig.getRetrofit().create(RetrofitApi.class).getAllSortedProduct(Const.OrderTag.MOST_NEWEST_PRODUCT, page)
                .enqueue(new Callback<List<WebserviceProductModel>>() {
                    @Override
                    public void onResponse(Call<List<WebserviceProductModel>> call, Response<List<WebserviceProductModel>> response) {
                        if (response.isSuccessful()) {
                            mostNewestProductListLiveData.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<WebserviceProductModel>> call, Throwable t) {

                    }
                });
        return mostNewestProductListLiveData;
    }

    public MutableLiveData<List<WebserviceProductModel>> getMostRatingProductListLiveData(int page) {
        RetrofitConfig.getRetrofit().create(RetrofitApi.class).getAllSortedProduct(Const.OrderTag.MOST_RATING_PRODUCT, page)
                .enqueue(new Callback<List<WebserviceProductModel>>() {
                    @Override
                    public void onResponse(Call<List<WebserviceProductModel>> call, Response<List<WebserviceProductModel>> response) {
                        if (response.isSuccessful()) {
                            mostRatingProductListLiveData.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<WebserviceProductModel>> call, Throwable t) {

                    }
                });
        return mostRatingProductListLiveData;
    }

    public MutableLiveData<List<WebserviceProductModel>> getMostVisitingProductListLiveData(int page) {
        RetrofitConfig.getRetrofit().create(RetrofitApi.class).getAllSortedProduct(Const.OrderTag.MOST_VISITING_PRODUCT, page)
                .enqueue(new Callback<List<WebserviceProductModel>>() {
                    @Override
                    public void onResponse(Call<List<WebserviceProductModel>> call, Response<List<WebserviceProductModel>> response) {
                        if (response.isSuccessful()) {
                            mostVisitingProductListLiveData.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<WebserviceProductModel>> call, Throwable t) {

                    }
                });
        return mostVisitingProductListLiveData;
    }

    public void setProductId(int productId) {
        productIdMutableLiveData.setValue(productId);
    }

    public MutableLiveData<Integer> getProductIdMutableLiveData() {
        return productIdMutableLiveData;
    }

    public MutableLiveData<WebserviceProductModel> getSingleProduct(int productId) {
        singleProductMutableLiveData = new MutableLiveData<>();
        RetrofitConfig.getRetrofit().create(RetrofitApi.class).getSingleProduct(productId)
                .enqueue(new Callback<WebserviceProductModel>() {
                    @Override
                    public void onResponse(Call<WebserviceProductModel> call, Response<WebserviceProductModel> response) {
                        if (response.isSuccessful()) {
                            Log.e("TAG4", "response message " + response.message() + " message code " +
                                    response.code());
                            singleProductMutableLiveData.setValue(response.body());
                        } else {
                            Log.e("TAG4", "in else response message " + response.message() + " message code " +
                                    response.code());
                            singleProductMutableLiveData = null;
                        }
                    }

                    @Override
                    public void onFailure(Call<WebserviceProductModel> call, Throwable t) {
                        Log.e("TAG4", "on fail message " + t.getMessage());
                        singleProductMutableLiveData = null;
                    }
                });
        return singleProductMutableLiveData;
    }

    public MutableLiveData<WebserviceProductModel> getSingleProductMutableLiveData() {
        return singleProductMutableLiveData;
    }

    public MutableLiveData<List<WebserviceCategoryModel>> getProductCategories(int productId) {
        productCategoriesMutableLiveData = new MutableLiveData<>();
        RetrofitConfig.getRetrofit().create(RetrofitApi.class)
                .getProductCategories(productId)
                .enqueue(new Callback<List<WebserviceCategoryModel>>() {
                    @Override
                    public void onResponse(Call<List<WebserviceCategoryModel>> call, Response<List<WebserviceCategoryModel>> response) {
                        if (response.isSuccessful()) {
                            productCategoriesMutableLiveData.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<WebserviceCategoryModel>> call, Throwable t) {

                    }
                });
        return productCategoriesMutableLiveData;
    }

    public MutableLiveData<List<WebserviceProductModel>> getRelatedProduct(String... relatedProductIds) {
        relatedProductMutableLiveData = new MutableLiveData<>();
        RetrofitConfig.getRetrofit().create(RetrofitApi.class).getRelatedProduct(relatedProductIds)
                .enqueue(new Callback<List<WebserviceProductModel>>() {
                    @Override
                    public void onResponse(Call<List<WebserviceProductModel>> call, Response<List<WebserviceProductModel>> response) {
                        if (response.isSuccessful()) {
                            relatedProductMutableLiveData.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<WebserviceProductModel>> call, Throwable t) {

                    }
                });
        return relatedProductMutableLiveData;
    }
    public MutableLiveData<List<WebserviceProductModel>> getEspecialProduct(){
        especialProductsMutabaleLiveData = new MutableLiveData<>();
        // TODO: 11/18/2019 ESPECIAL TAG !!
        RetrofitConfig.getRetrofit().create(RetrofitApi.class).getEspecialProducts(19)
                .enqueue(new Callback<List<WebserviceProductModel>>() {
                    @Override
                    public void onResponse(Call<List<WebserviceProductModel>> call, Response<List<WebserviceProductModel>> response) {
                        if (response.isSuccessful()){
                            especialProductsMutabaleLiveData.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<WebserviceProductModel>> call, Throwable t) {

                    }
                });
        return especialProductsMutabaleLiveData;
    }

    public MutableLiveData<List<WebServiceCommentModel>> getCommentsProduct(int productId){
        RetrofitConfig.getRetrofit().create(RetrofitApi.class).getProductComment(productId)
                .enqueue(new Callback<List<WebServiceCommentModel>>() {
                    @Override
                    public void onResponse(Call<List<WebServiceCommentModel>> call, Response<List<WebServiceCommentModel>> response) {
                        if (response.isSuccessful()){
                            commentProductsMutableLiveData.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<WebServiceCommentModel>> call, Throwable t) {

                    }
                });
        return commentProductsMutableLiveData;
    }

}
