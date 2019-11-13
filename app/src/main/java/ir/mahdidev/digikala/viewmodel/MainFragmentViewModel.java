package ir.mahdidev.digikala.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.List;

import ir.mahdidev.digikala.networkmodel.Repository;
import ir.mahdidev.digikala.networkmodel.category.WebserviceCategoryModel;
import ir.mahdidev.digikala.networkmodel.product.WebserviceProductModel;

public class MainFragmentViewModel extends AndroidViewModel {
    private Repository repository;
    public MainFragmentViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance();
    }
    public LiveData<List<WebserviceCategoryModel>> getCategoryListLiveData(){
        return repository.getCategoryListLiveData();
    }
    public LiveData<List<WebserviceProductModel>> getAmazingSuggestionListLiveData(int page){
        return repository.getAmazingSuggestionProductListLiveData(page);
    }
    public LiveData<List<WebserviceProductModel>> getMostNewestListLiveData(int page){
        return repository.getMostNewestProductListLiveData(page);
    }
    public LiveData<List<WebserviceProductModel>> getMostRatingListLiveData(int page){
        return repository.getMostRatingProductListLiveData(page);
    }
    public LiveData<List<WebserviceProductModel>> getMostVisitingListLiveData(int page){
        return repository.getMostVisitingProductListLiveData(page);
    }

    public void setProductId(int productId){
        repository.setProductId(productId);
    }
    public MutableLiveData<WebserviceProductModel> loadSingleProduct(int produtId){
        return repository.getSingleProduct(produtId);
    }
}
