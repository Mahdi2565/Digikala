package ir.mahdidev.digikala.networkmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ir.mahdidev.digikala.database.CustomerAddressModel;
import ir.mahdidev.digikala.database.RoomConfig;
import ir.mahdidev.digikala.util.MyApplication;

public class CustomerRepository {
    private static CustomerRepository customerRepository;
    public static CustomerRepository getInstance(){
        if (customerRepository == null){
            customerRepository = new CustomerRepository();
        }
        return customerRepository;
    }

    private CustomerRepository(){
        roomConfig = MyApplication.getInstance().getRoomDb();
    }

    private RoomConfig roomConfig;

    public LiveData<List<CustomerAddressModel>> getAllCustomerAddress(int customerId){
        return roomConfig.customerAddressDao().getAllCustomerAddress(customerId);
    }
    public void inserCustomerAddress (CustomerAddressModel customerAddressModel){
        roomConfig.customerAddressDao().insert(customerAddressModel);
    }
    public void deleteCustomerAddress(CustomerAddressModel customerAddressModel){
        roomConfig.customerAddressDao().delete(customerAddressModel);
    }
    public void updateCustomerAddress(CustomerAddressModel customerAddressModel){
        roomConfig.customerAddressDao().update(customerAddressModel);
    }

}
