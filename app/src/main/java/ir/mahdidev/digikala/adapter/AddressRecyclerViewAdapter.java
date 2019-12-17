package ir.mahdidev.digikala.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.database.CustomerAddressModel;

public class AddressRecyclerViewAdapter extends RecyclerView.Adapter<AddressRecyclerViewAdapter.ViewHolder> {

    private List<CustomerAddressModel> addressList;
    private Context context;

    public AddressRecyclerViewAdapter(List<CustomerAddressModel> addressList, Context context) {
        this.addressList = addressList;
        this.context = context;
    }

    public void setAddressList(List<CustomerAddressModel> addressList) {
        this.addressList = new ArrayList<>();
        this.addressList.addAll(addressList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.customer_address_item ,
                parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CustomerAddressModel customerAddressModel = addressList.get(position);
        String address = "آدرس: " + customerAddressModel.getCustomerAddress();
        String fullName = customerAddressModel.getFirstName() + " " + customerAddressModel.getLastName();
        String phoneNumber = "شماره تماس: " + customerAddressModel.getPhoneNumber();
        holder.titleAddress.setText(customerAddressModel.getTitleAddress());
        holder.customerAddress.setText(address);
        holder.fullCustomerName.setText(fullName);
        holder.customerPhoneNumber.setText(phoneNumber);
        holder.deleteAddress.setOnClickListener(view ->
                addressRecyclerViewAdapterInterface.onDeleteAddressClicked(customerAddressModel));
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.title_address)
        TextView titleAddress;
        @BindView(R.id.full_name_customer)
        TextView fullCustomerName;
        @BindView(R.id.customer_user_address)
        TextView customerAddress;
        @BindView(R.id.customer_phone)
        TextView customerPhoneNumber;
        @BindView(R.id.delete_address)
        TextView deleteAddress;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }
    public interface addressRecyclerViewAdapterInterface{
        void onDeleteAddressClicked(CustomerAddressModel customerAddressModel);
    }
    private addressRecyclerViewAdapterInterface addressRecyclerViewAdapterInterface;
    public void setAddressRecyclerViewAdapterInterface (addressRecyclerViewAdapterInterface addressRecyclerViewAdapterInterface){
        this.addressRecyclerViewAdapterInterface = addressRecyclerViewAdapterInterface;
    }
}
