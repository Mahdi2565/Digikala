package ir.mahdidev.digikala.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.HashMap;
import java.util.List;

import ir.mahdidev.digikala.networkmodel.Repository;

public class MainFragmentViewModel extends AndroidViewModel {
    private Repository repository;
    public MainFragmentViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance();
    }
    public LiveData<HashMap<String , List>> getProductAndCategoryList(){
        return repository.getProductsAndCategoryListLiveData();
    }
}
