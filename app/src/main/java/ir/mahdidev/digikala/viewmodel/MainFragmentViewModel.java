package ir.mahdidev.digikala.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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

    public void loadCategory(){
        repository.loadCategoryListFromMainFragment();
    }

    public LiveData<List<WebserviceProductModel>> loadAmazingSuggestionListLiveData(int page){
        return repository.loadAmazingSuggestionProductListLiveData(page);
    }

    public LiveData<List<WebserviceProductModel>> loadMostNewestListLiveData(int page){
        return repository.loadMostNewestProductListLiveData(page);
    }

    public LiveData<List<WebserviceProductModel>> loadMostRatingListLiveData(int page){
        return repository.loadMostRatingProductListLiveData(page);
    }

    public LiveData<List<WebserviceProductModel>> loadMostVisitingListLiveData(int page){
        return repository.loadMostVisitingProductListLiveData(page);
    }

    public void setProductId(int productId){
        repository.setProductId(productId);
    }

    public MutableLiveData<WebserviceProductModel> loadSingleProduct(int produtId){
        return repository.getSingleProduct(produtId);
    }

    public LiveData<Integer> getProductCount(){
        return repository.getProductBasketCountDb();
    }

    public LiveData<List<WebserviceProductModel>> loadEspecialProducts(){
        return repository.loadEspecialProduct();
    }

    public LiveData<List<WebserviceProductModel>> getEspecialProducts(){
        return repository.getEspecialProductsMutabaleLiveData();
    }

    public LiveData<List<WebserviceProductModel>> getAmazingSuggestionListLiveData(){
        return repository.getAmazingSuggestionProductListLiveData();
    }

    public LiveData<List<WebserviceProductModel>> getMostNewestListLiveData(){
        return repository.getMostNewestProductListLiveData();
    }

    public LiveData<List<WebserviceProductModel>> getMostRatingListLiveData(){
        return repository.getMostRatingProductListLiveData();
    }

    public LiveData<List<WebserviceProductModel>> getMostVisitingListLiveData(){
        return repository.getMostVisitingProductListLiveData();
    }
}
