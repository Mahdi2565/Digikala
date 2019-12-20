package ir.mahdidev.digikala.networkmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ir.mahdidev.digikala.database.CustomerAddressModel;
import ir.mahdidev.digikala.database.RoomConfig;
import ir.mahdidev.digikala.networkmodel.address.WebServiceAddress;
import ir.mahdidev.digikala.networkmodel.comment.WebServiceCommentModel;
import ir.mahdidev.digikala.networkmodel.coupon.WebServiceCoupon;
import ir.mahdidev.digikala.networkmodel.order.WebServiceOrder;
import ir.mahdidev.digikala.networkutil.RetrofitApi;
import ir.mahdidev.digikala.networkutil.RetrofitConfig;
import ir.mahdidev.digikala.util.Const;
import ir.mahdidev.digikala.util.MyApplication;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private MutableLiveData<WebServiceAddress> customerAddressMutable = new MutableLiveData<>();
    private MutableLiveData<WebServiceCommentModel> sendCustomerCommentMutable = new MutableLiveData<>();
    private MutableLiveData<WebServiceCommentModel> deleteCommentMutable;
    private MutableLiveData<WebServiceOrder> sendOrderMutable;
    private MutableLiveData<List<WebServiceOrder>> getOrdersMutable;
    private MutableLiveData<List<WebServiceCoupon>> verifyCouponMutable;

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

    public void clearAddressData(){
        customerAddressMutable.setValue(new WebServiceAddress());
    }

    public MutableLiveData<WebServiceAddress> loadCustomerAddress(String latitude , String longitiude ){
        RetrofitConfig.getMapRetrofit().create(RetrofitApi.class).getCustomerAddress(latitude , longitiude
        , Const.RetrofitConst.GEOCODING_MAP_IR_API_KEY).enqueue(new Callback<WebServiceAddress>() {
            @Override
            public void onResponse(Call<WebServiceAddress> call, Response<WebServiceAddress> response) {
                if (response.isSuccessful()){
                    customerAddressMutable.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<WebServiceAddress> call, Throwable t) {

            }
        });
        return customerAddressMutable;
    }

    public MutableLiveData<WebServiceAddress> getCustomerAddressMutable() {
        return customerAddressMutable;
    }
    public MutableLiveData<WebServiceCommentModel> sendCustomerComment(WebServiceCommentModel webServiceCommentModel){

        RetrofitConfig.getRetrofit().create(RetrofitApi.class).sendCustomerComment(webServiceCommentModel)
                .enqueue(new Callback<WebServiceCommentModel>() {
                    @Override
                    public void onResponse(Call<WebServiceCommentModel> call, Response<WebServiceCommentModel> response) {
                        if (response.isSuccessful()){
                            sendCustomerCommentMutable.setValue(response.body());
                        }
                    }
                    @Override
                    public void onFailure(Call<WebServiceCommentModel> call, Throwable t) {
                    }
                });

        return sendCustomerCommentMutable;
    }

    public MutableLiveData<WebServiceCommentModel> deleteComment(int id){
        deleteCommentMutable = new MutableLiveData<>();
     RetrofitConfig.getRetrofit().create(RetrofitApi.class).deleteCustomerComment(id)
     .enqueue(new Callback<WebServiceCommentModel>() {
         @Override
         public void onResponse(Call<WebServiceCommentModel> call, Response<WebServiceCommentModel> response) {
             if (response.isSuccessful()){
                 deleteCommentMutable.setValue(response.body());
             }
         }

         @Override
         public void onFailure(Call<WebServiceCommentModel> call, Throwable t) {
         }
     });
     return deleteCommentMutable;
    }
    public MutableLiveData<WebServiceOrder> sendOrder(WebServiceOrder webServiceOrder){
        sendOrderMutable = new MutableLiveData<>();
        RetrofitConfig.getRetrofit().create(RetrofitApi.class).sendOrder(webServiceOrder)
                .enqueue(new Callback<WebServiceOrder>() {
                    @Override
                    public void onResponse(Call<WebServiceOrder> call, Response<WebServiceOrder> response) {
                        if (response.isSuccessful()){
                            sendOrderMutable.setValue(response.body());
                        }
                    }
                    @Override
                    public void onFailure(Call<WebServiceOrder> call, Throwable t) {

                    }
                });
        return sendOrderMutable;
    }
    public MutableLiveData<List<WebServiceOrder>> getAllOrders(int customerId){
        getOrdersMutable = new MutableLiveData<>();
        RetrofitConfig.getRetrofit().create(RetrofitApi.class).getAllOrders(customerId)
                .enqueue(new Callback<List<WebServiceOrder>>() {
                    @Override
                    public void onResponse(Call<List<WebServiceOrder>> call, Response<List<WebServiceOrder>> response) {
                        if (response.isSuccessful()){
                            getOrdersMutable.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<WebServiceOrder>> call, Throwable t) {

                    }
                });
        return getOrdersMutable;
    }
    public MutableLiveData<List<WebServiceCoupon>> verifyCoupon(String couponCode){
        verifyCouponMutable = new MutableLiveData<>();
        RetrofitConfig.getRetrofit().create(RetrofitApi.class).verifyCouponCode(couponCode)
                .enqueue(new Callback<List<WebServiceCoupon>>() {
                    @Override
                    public void onResponse(Call<List<WebServiceCoupon>> call, Response<List<WebServiceCoupon>> response) {
                        if (response.isSuccessful()){
                            verifyCouponMutable.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<WebServiceCoupon>> call, Throwable t) {

                    }
                });
        return verifyCouponMutable;
    }
}
