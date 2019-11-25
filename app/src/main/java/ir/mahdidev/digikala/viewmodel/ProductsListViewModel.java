package ir.mahdidev.digikala.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ir.mahdidev.digikala.eventbus.ListProductData;
import ir.mahdidev.digikala.networkmodel.Repository;
import ir.mahdidev.digikala.networkmodel.product.WebserviceProductModel;

public class ProductsListViewModel extends AndroidViewModel {
    private Repository repository;
    public ProductsListViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance();
    }
    public LiveData<Integer> getProductCount(){
        return repository.getProductBasketCountDb();
    }
    public LiveData<List<WebserviceProductModel>> getAllSortedProductsList (int categoryId , String orderBy
            , String order , String search , int page){
        return repository.getSortedProductList(categoryId , orderBy , order , search , page);
    }
    public ListProductData listProductData (){
        return repository.getListProductDataMutableLiveData().getValue();
    }
}
