package ir.mahdidev.digikala.controller.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.networkmodel.customer.WebServiceCustomerModel;
import ir.mahdidev.digikala.util.Pref;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerInfoFragment extends Fragment {

    @OnClick(R.id.back_toolbar)
    void onBackClicked(){
        getActivity().finish();
    }
    @OnClick(R.id.edit_customer_info)
    void editCustomerInfoClicked(){
    navController.navigate(R.id.action_customerInfoFragment_to_editCustomerInfoFragment);
    }
    @OnClick(R.id.customer_orders)
    void customerOrdersClicked(){
        navController.navigate(R.id.action_customerInfoFragment_to_customerOrdersFragment);
    }
    @OnClick(R.id.customer_user_address)
    void customerAddressClicked(){
        navController.navigate(R.id.action_customerInfoFragment_to_customerAddressFragment);
    }
    @OnClick(R.id.customer_log_out)
    void logOutCustomer(){
        Pref.logOutCustomer(getActivity());
        getActivity().finish();
    }
    @BindView(R.id.name_customer_txt)
    TextView customerName;
    @BindView(R.id.customer_email)
    TextView customerEmail;
    public CustomerInfoFragment() {
    }

    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customer_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        navController = Navigation.findNavController(view);
        customerInfo();
    }

    private void customerInfo() {
        WebServiceCustomerModel webServiceCustomerModel = Pref.getCustomerModelFromPref(getActivity());
        String customerNameTxt = webServiceCustomerModel.getFirstName() + " " + webServiceCustomerModel.getLastName();
        customerName.setText(customerNameTxt);
        customerEmail.setText(webServiceCustomerModel.getEmail());
    }
}
