package ir.mahdidev.digikala.controller.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.controller.activity.ProductBasketActivity;
import ir.mahdidev.digikala.networkmodel.customer.Billing;
import ir.mahdidev.digikala.networkmodel.customer.WebServiceCustomerModel;
import ir.mahdidev.digikala.util.Pref;
import ir.mahdidev.digikala.viewmodel.CustomerViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditCustomerInfoFragment extends Fragment {

    @OnClick(R.id.back_toolbar)
    void onBackClick(){
        navController.popBackStack();
    }
    @BindView(R.id.basket_img)
    ImageView basketImg;
    @BindView(R.id.basket_badge)
    TextView basketBadge;
    @BindView(R.id.first_name_edt)
    TextInputEditText firstNameEdt;
    @BindView(R.id.last_name_edt)
    TextInputEditText lastNameEdt;
    @BindView(R.id.phone_number_edt)
    TextInputEditText phoneNumberEdt;
    @BindView(R.id.updateCustomer)
    RelativeLayout updateCustomerRelative;
    public EditCustomerInfoFragment() {
        // Required empty public constructor
    }

    private WebServiceCustomerModel webServiceCustomerModel;
    private CustomerViewModel viewModel;
    private NavController navController;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_customer_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this , view);
        navController = Navigation.findNavController(view);
        initViewModel();
        basketImgFunction();
        setDataToViews();
        updateCustomer();
    }

    private void setDataToViews() {
        webServiceCustomerModel = Pref.getCustomerModelFromPref(getActivity());
        firstNameEdt.setText(webServiceCustomerModel.getFirstName());
        lastNameEdt.setText(webServiceCustomerModel.getLastName());
        phoneNumberEdt.setText(webServiceCustomerModel.getBilling().getPhone());
    }

    private void updateCustomer() {
        updateCustomerRelative.setOnClickListener(view -> {
            String firstName = firstNameEdt.getText().toString().trim();
            String lastName = lastNameEdt.getText().toString().trim();
            String phoneNumber = phoneNumberEdt.getText().toString().trim();
            if (!firstName.isEmpty() && !lastName.isEmpty()){
                webServiceCustomerModel.setFirstName(firstName);
                webServiceCustomerModel.setLastName(lastName);
                webServiceCustomerModel.setBilling(new Billing(firstName , lastName,webServiceCustomerModel.getEmail() , phoneNumber ));
                viewModel.updateCustomer(webServiceCustomerModel).observe(EditCustomerInfoFragment.this , webServiceCustomerModel1 -> {
                    if (webServiceCustomerModel1 !=null){
                        Toast.makeText(getActivity(), getActivity().getResources()
                                        .getString(R.string.update_successfully)
                                , Toast.LENGTH_LONG).show();
                        Pref.saveCustomerModelToPref(getActivity() , webServiceCustomerModel1);
                        navController.popBackStack();
                    }else {
                        Toast.makeText(getActivity() , getActivity().getResources().getString(R.string.problem_occur) ,
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }


    private void basketImgFunction() {
        basketImg.setOnClickListener(view -> startActivity(ProductBasketActivity.newIntent(getActivity())));
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(CustomerViewModel.class);
        viewModel.getProductCount().observe(this , integer -> {
            if (integer>0){
                basketBadge.setVisibility(View.VISIBLE);
                basketBadge.setText(String.valueOf(integer));
            }else {
                basketBadge.setVisibility(View.INVISIBLE);
            }
        });
    }
}
