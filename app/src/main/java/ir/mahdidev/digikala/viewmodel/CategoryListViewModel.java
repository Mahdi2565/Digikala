package ir.mahdidev.digikala.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ir.mahdidev.digikala.networkmodel.Repository;
import ir.mahdidev.digikala.networkmodel.category.WebserviceCategoryModel;

public class CategoryListViewModel extends AndroidViewModel {
    private Repository repository;
    public CategoryListViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance();
    }
    public LiveData<List<WebserviceCategoryModel>> getAllCategory(){
        return repository.getCategoryListLiveData();
    }
}
