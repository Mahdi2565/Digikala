package ir.mahdidev.digikala.controller.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.controller.activity.MapActivity;
import ir.mahdidev.digikala.database.CustomerAddressModel;
import ir.mahdidev.digikala.networkmodel.customer.WebServiceCustomerModel;
import ir.mahdidev.digikala.util.Pref;
import ir.mahdidev.digikala.viewmodel.CustomerViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class addCustomerAddressFragment extends Fragment {

    @OnClick(R.id.back_toolbar)
    void onBackClicked() {
        navController.popBackStack();
    }

    @BindView(R.id.title_address_edt)
    TextInputEditText titleAddressEdt;
    @BindView(R.id.first_name_edt)
    TextInputEditText firstNameEdt;
    @BindView(R.id.last_name_edt)
    TextInputEditText lastNameEdt;
    @BindView(R.id.phone_number_edt)
    TextInputEditText phoneNumberEdt;
    @BindView(R.id.postal_code_edt)
    TextInputEditText postalCodeEdt;
    @BindView(R.id.customer_user_address)
    TextInputEditText customerAddressEdt;
    @BindView(R.id.customer_map_address)
    TextInputEditText mapAddressEdt;
    @BindView(R.id.select_location_cardView)
    CardView selectLocationCardView;
    @BindView(R.id.add_address_relative)
    RelativeLayout addAddressRelative;

    public addCustomerAddressFragment() {
    }

    private WebServiceCustomerModel webServiceCustomerModel;
    private NavController navController;
    private CustomerViewModel viewModel;
    private String titleAddress;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String postalCode;
    private String customerAddress;
    private String mapAddress;
    private String latitude;
    private String longitude;
    private String city;
    private String province;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_customer_address, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();
        getCustomerModel();
        viewModel.clearCustomerAddress();
    }

    private void getCustomerModel() {
        webServiceCustomerModel = Pref.getCustomerModelFromPref(getActivity());
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(CustomerViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        navController = Navigation.findNavController(view);
        selectLocationCardViewFunction();
        getDataFromUser();
        getDataFromMap();
        addDataToDb();
    }

    private void getDataFromUser() {
        titleAddress = titleAddressEdt.getText().toString().trim();
        firstName = firstNameEdt.getText().toString().trim();
        lastName = lastNameEdt.getText().toString().trim();
        phoneNumber = phoneNumberEdt.getText().toString().trim();
        postalCode = postalCodeEdt.getText().toString().trim();
        customerAddress = customerAddressEdt.getText().toString().trim();
    }

    private void getDataFromMap() {
        viewModel.getCustomerAddress().observe(this, webServiceAddress -> {
            if (webServiceAddress.getAddressCompact() != null) {
                mapAddress = webServiceAddress.getAddressCompact();
                latitude = webServiceAddress.getGeom().getCoordinates().get(1);
                longitude = webServiceAddress.getGeom().getCoordinates().get(0);
                city = webServiceAddress.getCity();
                province = webServiceAddress.getProvince();
                mapAddressEdt.setVisibility(View.VISIBLE);
                mapAddressEdt.setText(mapAddress);
            }
        });
    }

    private void selectLocationCardViewFunction() {
        selectLocationCardView.setOnClickListener(view ->
                startActivity(MapActivity.newIntent(getActivity())));
    }

    private void addDataToDb() {
        addAddressRelative.setOnClickListener(view -> {
            getDataFromUser();
            if (mapAddress == null) {
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.select_location), Toast.LENGTH_LONG).show();
            } else if (customerAddress == null) {
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.select_customer_address), Toast.LENGTH_LONG).show();
            } else {
                viewModel.insertCustomerAddress(new CustomerAddressModel(webServiceCustomerModel.getId(), titleAddress, firstName, lastName,
                        customerAddress, mapAddress,
                        phoneNumber, postalCode, latitude, longitude, city, province));
                navController.popBackStack();
            }
        });
    }
}
