package ir.mahdidev.digikala.controller.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.adapter.AddressRecyclerViewAdapter;
import ir.mahdidev.digikala.database.CustomerAddressModel;
import ir.mahdidev.digikala.networkmodel.customer.WebServiceCustomerModel;
import ir.mahdidev.digikala.util.Const;
import ir.mahdidev.digikala.util.Pref;
import ir.mahdidev.digikala.viewmodel.CustomerViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerAddressFragment extends Fragment {

    @OnClick(R.id.back_toolbar)
    void onBackClicked(){
        navController.popBackStack();
    }
    @BindView(R.id.add_address_fab)
    FloatingActionButton addAddressFab;
    @BindView(R.id.customer_address_recyclerView)
    RecyclerView addressRecyclerView;
    @BindView(R.id.no_address_txt)
    TextView noAddressTxt;

    public CustomerAddressFragment() {
    }

    private NavController navController;
    private CustomerViewModel viewModel;
    private WebServiceCustomerModel webServiceCustomerModel;
    private AddressRecyclerViewAdapter addressRecyclerViewAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customer_address, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getCustomerModelFromPref();
        initViewModel();
    }

    private void getCustomerModelFromPref() {
        webServiceCustomerModel = Pref.getCustomerModelFromPref(getActivity());
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(CustomerViewModel.class);
    }

    private void initRecyclerView(List<CustomerAddressModel> customerAddressModels) {
        if (customerAddressModels.isEmpty()){
            noAddressTxt.setVisibility(View.VISIBLE);
        }else {
            noAddressTxt.setVisibility(View.GONE);
        }
        if (addressRecyclerViewAdapter==null){
            addressRecyclerViewAdapter = new AddressRecyclerViewAdapter(customerAddressModels , getActivity() , Const.FROM_ADDRESS_FRAGMENT);
        }else {
            addressRecyclerViewAdapter.setAddressList(customerAddressModels);
            addressRecyclerViewAdapter.notifyDataSetChanged();
        }
        addressRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        addressRecyclerView.setAdapter(addressRecyclerViewAdapter);
        addressRecyclerViewAdapter.setAddressRecyclerViewAdapterInterface(customerAddressModel -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setTitle("آیا مایل به حذف این آدرس هستید؟");
            alert.setPositiveButton("بله", (dialogInterface, i) ->
                    viewModel.deleteCustomer(customerAddressModel));
            alert.setNegativeButton("خیر" , ((dialogInterface, i) ->
                    dialogInterface.cancel()));
            alert.show();
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this , view);
        navController = Navigation.findNavController(view);
        addAddressFabFunction();
        getAllCustomerAddress();
    }

    private void getAllCustomerAddress() {
        viewModel.getAllCustomerAddressDb(webServiceCustomerModel.getId())
                .observe(this, this::initRecyclerView);
    }

    private void addAddressFabFunction() {
        addAddressFab.setOnClickListener(view ->
                navController.navigate(R.id.action_customerAddressFragment_to_addCustomerAddressFragment));
    }
}
