package ir.mahdidev.digikala.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.database.CustomerAddressModel;
import ir.mahdidev.digikala.networkmodel.address.WebServiceAddress;
import ir.mahdidev.digikala.networkmodel.order.WebServiceOrder;
import ir.mahdidev.digikala.util.Const;

public class AddressRecyclerViewAdapter extends RecyclerView.Adapter<AddressRecyclerViewAdapter.ViewHolder> {

    private List<CustomerAddressModel> addressList;
    private Context context;
    private int adapterPosition;
    private int checkedPosition = -1;

    public AddressRecyclerViewAdapter(List<CustomerAddressModel> addressList, Context context, int adapterPosition) {
        this.addressList = addressList;
        this.context = context;
        this.adapterPosition = adapterPosition;
    }

    public void setAddressList(List<CustomerAddressModel> addressList) {
        this.addressList = new ArrayList<>();
        this.addressList.addAll(addressList);
    }

    public CustomerAddressModel getCustomerAddress(){
        if (checkedPosition != -1){
            return addressList.get(checkedPosition);
        }
        return null;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (adapterPosition == Const.FROM_ADDRESS_FRAGMENT){
            View addressFragment = LayoutInflater.from(context).inflate(R.layout.customer_address_item ,
                    parent , false);
            return new ViewHolder(addressFragment);
        }else {
            View finalFragmnet = LayoutInflater.from(context).inflate(R.layout.address_final_basket_item ,
                    parent , false);
            return new ViewHolder(finalFragmnet);
        }
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
        if (adapterPosition == Const.FROM_ADDRESS_FRAGMENT){
            holder.deleteAddress.setOnClickListener(view ->
                    addressRecyclerViewAdapterInterface.onDeleteAddressClicked(customerAddressModel));
        }else {
            //holder.selectAddress.setChecked(true);
            if (checkedPosition == -1){
                holder.selectAddress.setChecked(false);
            }else {
                if (checkedPosition == position){
                    holder.selectAddress.setChecked(true);
                }else {
                    holder.selectAddress.setChecked(false);
                }
            }
            holder.finalAddressBasketCardView.setOnClickListener(view -> {
                singleSelectFun(holder, position);
            });
            holder.selectAddress.setOnClickListener(view -> {
                singleSelectFun(holder , position);
            });
        }
    }

    private void singleSelectFun(@NonNull ViewHolder holder, int position) {
        holder.selectAddress.setChecked(true);
        if (checkedPosition != position){
            notifyItemChanged(checkedPosition);
            checkedPosition = position;
        }
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
        @Nullable
        @BindView(R.id.delete_address)
        TextView deleteAddress;
        @Nullable
        @BindView(R.id.select_address_radio_button)
        RadioButton selectAddress;
        @Nullable
        @BindView(R.id.final_address_basket_card_view)
        CardView finalAddressBasketCardView;
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
