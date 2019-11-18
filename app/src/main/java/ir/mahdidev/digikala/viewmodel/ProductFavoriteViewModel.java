package ir.mahdidev.digikala.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ir.mahdidev.digikala.database.ProductFavoriteModel;
import ir.mahdidev.digikala.networkmodel.Repository;

public class ProductFavoriteViewModel extends AndroidViewModel {
    private Repository repository;
    public ProductFavoriteViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance();
    }
    public LiveData<List<ProductFavoriteModel>> getAllFavoriteProduct(){
        return repository.getAllProductFavoriteDb();
    }
    public void deleteFavoriteProduct(ProductFavoriteModel productFavoriteModel){
        repository.deleteProductFavoriteDb(productFavoriteModel);
    }
}
