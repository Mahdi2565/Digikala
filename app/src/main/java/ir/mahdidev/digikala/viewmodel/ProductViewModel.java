package ir.mahdidev.digikala.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ir.mahdidev.digikala.database.ProductBasketModel;
import ir.mahdidev.digikala.database.ProductFavoriteModel;
import ir.mahdidev.digikala.networkmodel.CustomerRepository;
import ir.mahdidev.digikala.networkmodel.Repository;
import ir.mahdidev.digikala.networkmodel.attribute.WebServiceAttribute;
import ir.mahdidev.digikala.networkmodel.attributeterm.WebServiceAttributeTerm;
import ir.mahdidev.digikala.networkmodel.category.WebserviceCategoryModel;
import ir.mahdidev.digikala.networkmodel.comment.WebServiceCommentModel;
import ir.mahdidev.digikala.networkmodel.product.WebserviceProductModel;
import ir.mahdidev.digikala.networkutil.RetrofitApi;
import ir.mahdidev.digikala.networkutil.RetrofitConfig;
import okhttp3.ResponseBody;

public class ProductViewModel extends AndroidViewModel {
    private Repository repository;
    private CustomerRepository customerRepository;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance();
        customerRepository = CustomerRepository.getInstance();
    }

    public MutableLiveData<Integer> getProductIdMutableLiveData() {
        return repository.getProductIdMutableLiveData();
    }

    public MutableLiveData<WebserviceProductModel> getSingleProductLiveData() {
        return repository.getSingleProductMutableLiveData();
    }

    public MutableLiveData<WebserviceProductModel> loadSingleProductLiveData(int productId) {
        return repository.getSingleProduct(productId);
    }

    public MutableLiveData<List<WebserviceCategoryModel>> getProductCategories(int productId) {
        return repository.getProductCategories(productId);
    }

    public MutableLiveData<List<WebserviceProductModel>> getRelatedProducts(String... relatedProductIds) {
        return repository.getRelatedProduct(relatedProductIds);
    }

    public LiveData<Integer> getProductCount() {
        return repository.getProductBasketCountDb();
    }

    public void insertBasketDb(ProductBasketModel productBasketModel) {
        repository.insertProductBaskerDb(productBasketModel);
    }

    public void updateBasketDb(ProductBasketModel productBasketModel) {
        repository.updateProductBaskerDb(productBasketModel);
    }

    public void insertFavoritetDb(ProductFavoriteModel productFavoriteModel) {
        repository.insertProductFavoriteDb(productFavoriteModel);
    }

    public void deleteFavoriteDb(ProductFavoriteModel productFavoriteModel) {
        repository.deleteProductFavoriteDb(productFavoriteModel);
    }

    public ProductFavoriteModel getSingleProductFavorite(int productId) {
        return repository.getSingleProductFavorite(productId);
    }

    public LiveData<List<WebServiceCommentModel>> getCommentsProduct(int productId) {
        return repository.getCommentsProduct(productId);
    }

    public LiveData<WebServiceCommentModel> sendCustomerComment(WebServiceCommentModel webServiceCommentModel) {
        return customerRepository.sendCustomerComment(webServiceCommentModel);
    }

    public LiveData<WebServiceCommentModel> deleteCustomerComment(int commentId) {
        return customerRepository.deleteComment(commentId);
    }

    public LiveData<List<WebServiceAttribute>> getAllAttributes() {
        return repository.getAllAttributes();
    }

    public LiveData<List<WebServiceAttributeTerm>> getAllAttributeTerm(int attributeId) {
        return repository.getAllAttributeTerms(attributeId);
    }

    public LiveData<WebServiceCommentModel> updateComment(WebServiceCommentModel webServiceCommentModel) {
        return repository.updateComment(webServiceCommentModel);
    }
}
