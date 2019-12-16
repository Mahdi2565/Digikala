package ir.mahdidev.digikala.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ir.mahdidev.digikala.networkmodel.Repository;
import ir.mahdidev.digikala.networkmodel.customer.WebServiceCustomerModel;

public class CustomerViewModel extends AndroidViewModel {
    private Repository repository;
    public CustomerViewModel(@NonNull Application application) {
        super(application);
        repository =  Repository.getInstance();
    }
    public LiveData<WebServiceCustomerModel> registerCustomer(WebServiceCustomerModel webServiceCustomerModel){
        return repository.registerCustomer(webServiceCustomerModel);
    }
    public LiveData<List<WebServiceCustomerModel>> getCustomer(String email){
        return repository.getCustomer(email);
    }
    public LiveData<Integer> getProductCount(){
        return repository.getProductBasketCountDb();
    }
    public LiveData<WebServiceCustomerModel> updateCustomer(WebServiceCustomerModel webServiceCustomerModel){
        return repository.updateCustomer(webServiceCustomerModel);
    }
}
