package ir.mahdidev.digikala.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ir.mahdidev.digikala.database.ProductBasketModel;
import ir.mahdidev.digikala.networkmodel.CustomerRepository;
import ir.mahdidev.digikala.networkmodel.Repository;
import ir.mahdidev.digikala.networkmodel.order.WebServiceOrder;

public class ProductBasketViewModel extends AndroidViewModel {
    private Repository repository;
    private CustomerRepository customerRepository;

    public ProductBasketViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance();
        customerRepository = CustomerRepository.getInstance();
    }

    public LiveData<Integer> getProductCount(){
        return repository.getProductBasketCountDb();
    }

    public LiveData<List<ProductBasketModel>> getAllBasketProductDb(){
        return repository.getAllProductBasketDb();
    }

    public void deleteProduct(ProductBasketModel productBasketModel){
        repository.deleteProductBasketDb(productBasketModel);
    }

    public LiveData<WebServiceOrder> sendOrder(WebServiceOrder webServiceOrder){
        return customerRepository.sendOrder(webServiceOrder);
    }

    public void deleteAllProductsRows(){
        repository.deleteAllRows();
    }

    public void updateProductBasketDb(ProductBasketModel productBasketModel){
        repository.updateProductBasketDb(productBasketModel);
    }
}
