package ir.mahdidev.digikala.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ir.mahdidev.digikala.database.ProductBasketModel;
import ir.mahdidev.digikala.networkmodel.Repository;

public class ProductBasketViewModel extends AndroidViewModel {
    private Repository repository;
    public ProductBasketViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance();
    }
    public LiveData<Integer> getProductCount(){
        return repository.getProductBasketCountDb();
    }
    public LiveData<List<ProductBasketModel>> getAllBasketProductDb(){
        return repository.getAllProductBasketDb();
    }
    public void deleteProduct(ProductBasketModel productBasketModel){
        repository.deleteProductBaskerDb(productBasketModel);
    }

}
