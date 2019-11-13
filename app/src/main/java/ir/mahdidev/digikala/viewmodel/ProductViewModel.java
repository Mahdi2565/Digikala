package ir.mahdidev.digikala.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ir.mahdidev.digikala.networkmodel.Repository;
import ir.mahdidev.digikala.networkmodel.category.WebserviceCategoryModel;
import ir.mahdidev.digikala.networkmodel.product.WebserviceProductModel;

public class ProductViewModel extends AndroidViewModel {
    private Repository repository;
    public ProductViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance();
    }

    public MutableLiveData<Integer> getProductIdMutableLiveData() {
        return repository.getProductIdMutableLiveData();
    }
    public MutableLiveData<WebserviceProductModel> getSingleProductLiveData(){
        return repository.getSingleProductMutableLiveData();
    }
    public MutableLiveData<WebserviceProductModel> loadSingleProductLiveData(int productId){
        return repository.getSingleProduct(productId);
    }
    public MutableLiveData<List<WebserviceCategoryModel>> getProductCategories(int productId){
        return repository.getProductCategories(productId);
    }
    public MutableLiveData<List<WebserviceProductModel>>  getRelatedProducts(String... relatedProductIds){
     return repository.getRelatedProduct(relatedProductIds);
    }
}
