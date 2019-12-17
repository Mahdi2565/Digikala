package ir.mahdidev.digikala.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ir.mahdidev.digikala.database.CustomerAddressModel;
import ir.mahdidev.digikala.networkmodel.CustomerRepository;
import ir.mahdidev.digikala.networkmodel.Repository;
import ir.mahdidev.digikala.networkmodel.address.WebServiceAddress;
import ir.mahdidev.digikala.networkmodel.customer.WebServiceCustomerModel;

public class CustomerViewModel extends AndroidViewModel {
    private Repository repository;
    private CustomerRepository customerRepository;
    public CustomerViewModel(@NonNull Application application) {
        super(application);
        repository =  Repository.getInstance();
        customerRepository = CustomerRepository.getInstance();
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
    public LiveData<List<CustomerAddressModel>> getAllCustomerAddressDb(int customerId){
        return customerRepository.getAllCustomerAddress(customerId);
    }
    public void insertCustomerAddress(CustomerAddressModel customerAddressModel){
        customerRepository.inserCustomerAddress(customerAddressModel);
    }
    public void deleteCustomer(CustomerAddressModel customerAddressModel){
        customerRepository.deleteCustomerAddress(customerAddressModel);
    }

    public LiveData<WebServiceAddress> loadCustomerAddress(String latitude , String longitiude){
       return customerRepository.loadCustomerAddress(latitude , longitiude);
    }

    public LiveData<WebServiceAddress> getCustomerAddress( ){
        return customerRepository.getCustomerAddressMutable();
    }
    public void clearCustomerAddress(){
        customerRepository.clearAddressData();
    }
}
