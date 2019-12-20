package ir.mahdidev.digikala.networkmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ir.mahdidev.digikala.database.ProductBasketModel;
import ir.mahdidev.digikala.database.ProductFavoriteModel;
import ir.mahdidev.digikala.database.RoomConfig;
import ir.mahdidev.digikala.networkmodel.attribute.WebServiceAttribute;
import ir.mahdidev.digikala.networkmodel.attributeterm.WebServiceAttributeTerm;
import ir.mahdidev.digikala.networkmodel.category.WebserviceCategoryModel;
import ir.mahdidev.digikala.networkmodel.comment.WebServiceCommentModel;
import ir.mahdidev.digikala.networkmodel.customer.WebServiceCustomerModel;
import ir.mahdidev.digikala.networkmodel.product.WebserviceProductModel;
import ir.mahdidev.digikala.networkutil.RetrofitApi;
import ir.mahdidev.digikala.networkutil.RetrofitConfig;
import ir.mahdidev.digikala.util.Const;
import ir.mahdidev.digikala.util.MyApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private static Repository instance;
    private RoomConfig roomConfig;
    private MutableLiveData<List<WebserviceCategoryModel>> categoryListLiveData = new MutableLiveData<>();
    private MutableLiveData<List<WebserviceProductModel>> amazingSuggestionProductListLiveData = new MutableLiveData<>();
    private MutableLiveData<List<WebserviceProductModel>> mostNewestProductListLiveData = new MutableLiveData<>();
    private MutableLiveData<List<WebserviceProductModel>> mostRatingProductListLiveData = new MutableLiveData<>();
    private MutableLiveData<List<WebserviceProductModel>> mostVisitingProductListLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> productIdMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<WebserviceProductModel> singleProductMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<WebserviceCategoryModel>> productCategoriesMutableLiveData;
    private MutableLiveData<List<WebserviceProductModel>> relatedProductMutableLiveData;
    private MutableLiveData<List<WebserviceProductModel>> especialProductsMutabaleLiveData = new MutableLiveData<>();
    private MutableLiveData<List<WebServiceCommentModel>> commentProductsMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<WebserviceProductModel>> sortProductsListMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<WebServiceCustomerModel> registerCustomerMutable;
    private MutableLiveData<List<WebServiceCustomerModel>> getCustomerMutable;
    private MutableLiveData<WebServiceCustomerModel> updateCustomerMutable = new MutableLiveData<>();
    private MutableLiveData<List<WebServiceAttribute>> attributeProductsMutable;
    private MutableLiveData<List<WebServiceAttributeTerm>> attributeTermProductsMutable;
    private MutableLiveData<WebServiceCommentModel> updateCommentMutable;

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
    public void deleteAllRows(){
        roomConfig.productBasketDao().deleteAllRows();
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
        Response<List<WebserviceCategoryModel>> category = RetrofitConfig.getRetrofit().create(RetrofitApi.class)
                .getAllCategories().execute();
        categoryListLiveData.postValue(category.body());
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
                Const.OrderTag.MOST_NEWEST_PRODUCT, Const.DISCOUNT_TAG, page
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
                            singleProductMutableLiveData.setValue(response.body());
                        } else {
                            singleProductMutableLiveData = null;
                        }
                    }

                    @Override
                    public void onFailure(Call<WebserviceProductModel> call, Throwable t) {
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
        RetrofitConfig.getRetrofit().create(RetrofitApi.class).getEspecialProducts(Const.SPECIAL_TAG)
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
        commentProductsMutableLiveData = new MutableLiveData<>();
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
    public MutableLiveData<List<WebserviceProductModel>> getSortedProductList(int categoryId , String orderBy
            , String order , String search , int page , String attribute , List<Integer> attributeTerm){
        if (page==1) sortProductsListMutableLiveData = new MutableLiveData<>();
        if (categoryId==0){
            RetrofitConfig.getRetrofit().create(RetrofitApi.class).getsortedProductsList(order ,
                    orderBy , page , search , attribute , attributeTerm).enqueue(new Callback<List<WebserviceProductModel>>() {
                @Override
                public void onResponse(Call<List<WebserviceProductModel>> call, Response<List<WebserviceProductModel>> response) {
                    if (response.isSuccessful()){
                        sortProductsListMutableLiveData.setValue(response.body());

                    }
                }

                @Override
                public void onFailure(Call<List<WebserviceProductModel>> call, Throwable t) {

                }
            });
        }else {
            RetrofitConfig.getRetrofit().create(RetrofitApi.class).getsortedProductsListWithCategory(categoryId ,order ,
                    orderBy , page , search, attribute , attributeTerm).enqueue(new Callback<List<WebserviceProductModel>>() {
                @Override
                public void onResponse(Call<List<WebserviceProductModel>> call, Response<List<WebserviceProductModel>> response) {
                    if (response.isSuccessful()){
                        sortProductsListMutableLiveData.setValue(response.body());

                    }
                }

                @Override
                public void onFailure(Call<List<WebserviceProductModel>> call, Throwable t) {
                }
            });
        }
        return sortProductsListMutableLiveData;
    }
    public MutableLiveData<WebServiceCustomerModel> registerCustomer (WebServiceCustomerModel webServiceCustomerModel){
        registerCustomerMutable = new MutableLiveData<>();
        RetrofitConfig.getRetrofit().create(RetrofitApi.class).registerCustomer(webServiceCustomerModel)
                .enqueue(new Callback<WebServiceCustomerModel>() {
                    @Override
                    public void onResponse(Call<WebServiceCustomerModel> call, Response<WebServiceCustomerModel> response) {
                        if (response.isSuccessful()){
                         registerCustomerMutable.setValue(response.body());
                        }else if (response.code()==400){
                            registerCustomerMutable.setValue(new WebServiceCustomerModel(400));
                        }
                    }

                    @Override
                    public void onFailure(Call<WebServiceCustomerModel> call, Throwable t) {
                        registerCustomerMutable.setValue(new WebServiceCustomerModel(t));
                    }
                });
        return registerCustomerMutable;
    }
    public MutableLiveData<List<WebServiceCustomerModel>> getCustomer(String email){
        getCustomerMutable = new MutableLiveData<>();
        RetrofitConfig.getRetrofit().create(RetrofitApi.class).getCustomer(email)
                .enqueue(new Callback<List<WebServiceCustomerModel>>() {
                    @Override
                    public void onResponse(Call<List<WebServiceCustomerModel>> call, Response<List<WebServiceCustomerModel>> response) {
                        if (response.isSuccessful()){
                            getCustomerMutable.setValue(response.body());
                        }else if (response.code()==400){
                            List<WebServiceCustomerModel> customerModelList = new ArrayList<>();
                            customerModelList.add(new WebServiceCustomerModel(400));
                            getCustomerMutable.setValue(customerModelList);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<WebServiceCustomerModel>> call, Throwable t) {
                        List<WebServiceCustomerModel> customerModelList = new ArrayList<>();
                        customerModelList.add(new WebServiceCustomerModel(t));
                        getCustomerMutable.setValue(customerModelList);
                    }
                });
        return getCustomerMutable;
    }
    public MutableLiveData<WebServiceCustomerModel> updateCustomer(WebServiceCustomerModel webServiceCustomerModel){
        RetrofitConfig.getRetrofit().create(RetrofitApi.class).updateCustomer(webServiceCustomerModel.getId() , webServiceCustomerModel)
                .enqueue(new Callback<WebServiceCustomerModel>() {
                    @Override
                    public void onResponse(Call<WebServiceCustomerModel> call, Response<WebServiceCustomerModel> response) {
                        if (response.isSuccessful()){
                            updateCustomerMutable.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<WebServiceCustomerModel> call, Throwable t) {

                    }
                });
        return updateCustomerMutable;
    }
    public MutableLiveData<List<WebServiceAttribute>> getAllAttributes(){
        attributeProductsMutable = new MutableLiveData<>();
        RetrofitConfig.getRetrofit().create(RetrofitApi.class).getAllAttributes().enqueue(new Callback<List<WebServiceAttribute>>() {
            @Override
            public void onResponse(Call<List<WebServiceAttribute>> call, Response<List<WebServiceAttribute>> response) {
                if (response.isSuccessful()){
                    attributeProductsMutable.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<WebServiceAttribute>> call, Throwable t) {

            }
        });
        return attributeProductsMutable;
    }

    public MutableLiveData<List<WebServiceAttributeTerm>> getAllAttributeTerms(int attributeId){
        attributeTermProductsMutable = new MutableLiveData<>();
        RetrofitConfig.getRetrofit().create(RetrofitApi.class).getAllAttributeTerms(attributeId)
                .enqueue(new Callback<List<WebServiceAttributeTerm>>() {
                    @Override
                    public void onResponse(Call<List<WebServiceAttributeTerm>> call, Response<List<WebServiceAttributeTerm>> response) {
                        if (response.isSuccessful()){
                            attributeTermProductsMutable.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<WebServiceAttributeTerm>> call, Throwable t) {

                    }
                });
        return attributeTermProductsMutable;
    }
    public MutableLiveData<WebServiceCommentModel> updateComment(WebServiceCommentModel webServiceCommentModel){
        updateCommentMutable = new MutableLiveData<>();
        RetrofitConfig.getRetrofit().create(RetrofitApi.class).updateComment(webServiceCommentModel.getId() ,webServiceCommentModel)
                .enqueue(new Callback<WebServiceCommentModel>() {
                    @Override
                    public void onResponse(Call<WebServiceCommentModel> call, Response<WebServiceCommentModel> response) {
                        if (response.isSuccessful()){
                            updateCommentMutable.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<WebServiceCommentModel> call, Throwable t) {

                    }
                });
        return updateCommentMutable;
    }
}