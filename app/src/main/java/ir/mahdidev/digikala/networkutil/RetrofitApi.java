package ir.mahdidev.digikala.networkutil;

import androidx.cardview.widget.CardView;

import java.util.List;

import ir.mahdidev.digikala.networkmodel.address.WebServiceAddress;
import ir.mahdidev.digikala.networkmodel.attribute.WebServiceAttribute;
import ir.mahdidev.digikala.networkmodel.attributeterm.WebServiceAttributeTerm;
import ir.mahdidev.digikala.networkmodel.category.WebserviceCategoryModel;
import ir.mahdidev.digikala.networkmodel.comment.WebServiceCommentModel;
import ir.mahdidev.digikala.networkmodel.customer.WebServiceCustomerModel;
import ir.mahdidev.digikala.networkmodel.product.WebserviceProductModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitApi {
    @GET("products")
    Call<List<WebserviceProductModel>> getAllSortedProduct
            (@Query("orderby") String sortType , @Query("page") int page);

    @GET("products")
    Call<List<WebserviceProductModel>> getAllAmazingSuggestionProduct
            (@Query("orderby") String sortType , @Query("tag") int tagId ,
             @Query("page") int page);
    @GET("products")
    Call<List<WebserviceProductModel>> getEspecialProducts
            (@Query("tag") int especialTag);
    @GET("products/{id}")
    Call<WebserviceProductModel> getSingleProduct(@Path("id") int productId);
    @GET("products/categories")
    Call<List<WebserviceCategoryModel>> getAllCategories();

    @GET("products/categories")
    Call<List<WebserviceCategoryModel>> getProductCategories(@Query("product") int productId);

    @GET("products")
    Call<List<WebserviceProductModel>> getRelatedProduct (@Query("include") String... relatedProductId);

    @GET("products/reviews")
    Call<List<WebServiceCommentModel>> getProductComment (@Query("product") int productId);
    @GET("products")
    Call<List<WebserviceProductModel>> getsortedProductsListWithCategory
       (@Query("category") int category
     ,  @Query("order") String order , @Query("orderby") String orderBy , @Query("page") int page
    ,   @Query("search") String search, @Query("attribute") String attribute , @Query("attribute_term") List<Integer> attributeTerm);

    @GET("products")
    Call<List<WebserviceProductModel>> getsortedProductsList
            (@Query("order") String order ,
             @Query("orderby") String orderBy , @Query("page") int page
    , @Query("search") String search , @Query("attribute") String attribute , @Query("attribute_term") List<Integer> attributeTerm);
    @POST("customers")
    Call<WebServiceCustomerModel> registerCustomer(@Body WebServiceCustomerModel webServiceCustomerModel);
    @GET("customers")
    Call<List<WebServiceCustomerModel>> getCustomer(@Query("email") String email);
    @PUT("customers/{id}")
    Call<WebServiceCustomerModel> updateCustomer(@Path("id")int customerId , @Body WebServiceCustomerModel webServiceCustomerModel);
    @GET("fast-reverse")
    Call<WebServiceAddress> getCustomerAddress(@Query("lat")String latitude, @Query("lon") String longitude ,
                                               @Header("x-api-key") String apiKey);
    @POST("products/reviews")
    Call<WebServiceCommentModel> sendCustomerComment(@Body WebServiceCommentModel webServiceCommentModel);
    @DELETE("products/reviews/{id}")
    Call<WebServiceCommentModel> deleteCustomerComment(@Path("id") int id);
    @GET("products/attributes")
    Call<List<WebServiceAttribute>> getAllAttributes();
    @GET("products/attributes/{id}/terms")
    Call<List<WebServiceAttributeTerm>> getAllAttributeTerms(@Path("id") int attributeId);

}
