package ir.mahdidev.digikala.networkutil;

import java.util.List;

import ir.mahdidev.digikala.networkmodel.category.WebserviceCategoryModel;
import ir.mahdidev.digikala.networkmodel.product.WebserviceProductModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitApi {
    @GET("products")
    Call<List<WebserviceProductModel>> getAllSortedProduct
            (@Query("orderby") String sortType);
    @GET("products/categories")
    Call<List<WebserviceCategoryModel>> getAllCategories();
}
