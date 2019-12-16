package ir.mahdidev.digikala.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CustomerAddressDao {

    @Query("SELECT * FROM address_customer WHERE customerId = :customerId")
    LiveData<List<CustomerAddressModel>> getAllCustomerAddress(int customerId);
    @Insert()
    void insert(CustomerAddressModel customerAddressModel);
    @Update
    void update(CustomerAddressModel customerAddressModel);
    @Delete
    void delete(CustomerAddressModel customerAddressModel);

}
