package ir.mahdidev.digikala.controller.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.controller.activity.MainActivity;
import ir.mahdidev.digikala.controller.activity.ProductBasketActivity;
import ir.mahdidev.digikala.controller.activity.SearchActivity;
import ir.mahdidev.digikala.util.Pref;
import ir.mahdidev.digikala.viewmodel.CustomerViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    @BindView(R.id.register_txt)
    TextView registerTxt;
    @BindView(R.id.email_edt)
    TextInputEditText emailEdt;
    @BindView(R.id.loginDigikalaLinear)
    LinearLayout loginDigikala;
    @BindView(R.id.basket_img)
    ImageView basketImg;
    @BindView(R.id.basket_badge)
    TextView basketBadge;
    @OnClick(R.id.back_toolbar)
    void onBackClicked(){
        getActivity().finish();
    }
    @OnClick(R.id.search_img)
    void onSearchClicked(){
        startActivity(SearchActivity.newIntent(getActivity()));
    }
    public LoginFragment() {
    }

    private NavController navController;
    private CustomerViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        ButterKnife.bind(this , view);
        initViewModel();
        getDataFromUser();
        registerTxtFunction();
        backImgFunction();
        basketImgFunction();
    }

    private void basketImgFunction() {
        basketImg.setOnClickListener(view -> startActivity(ProductBasketActivity.newIntent(getActivity())));
        viewModel.getProductCount().observe(this , integer -> {
            if (integer>0){
                basketBadge.setVisibility(View.VISIBLE);
                basketBadge.setText(String.valueOf(integer));
            }else {
                basketBadge.setVisibility(View.INVISIBLE);
            }
        });
    }
    private void backImgFunction() {
    }

    private void getDataFromUser() {
        loginDigikala.setOnClickListener(view -> {
            String email = emailEdt.getText().toString().trim();
            if (!RegisterFragment.EMailValidation(email)){
                Toast.makeText(getActivity() , getString(R.string.wrong_email) , Toast.LENGTH_SHORT).show();
                return;
            }
            viewModel.getCustomer(email).observe(this , webServiceCustomerModels -> {
                if (!webServiceCustomerModels.isEmpty()){
                    if (webServiceCustomerModels.get(0).getCode()==400){
                        Toast.makeText(getActivity() , getString(R.string.fix_email), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (webServiceCustomerModels.get(0).getError()!=null){
                        Toast.makeText(getActivity() , "خطای اتصال به اینترنت" , Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Pref.saveCustomerModelToPref(getActivity() , webServiceCustomerModels.get(0));
                    Toast.makeText(getActivity() , getString(R.string.login_successfull ) ,
                            Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }else {
                    Toast.makeText(getActivity() , getString(R.string.fix_email)  , Toast.LENGTH_SHORT).show();
                }
            });
        });

    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(CustomerViewModel.class);
    }

    private void registerTxtFunction() {
        registerTxt.setOnClickListener(view ->
                navController.navigate(R.id.action_loginFragment_to_registerFragment));
    }
}
