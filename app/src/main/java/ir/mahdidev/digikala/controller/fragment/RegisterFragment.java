package ir.mahdidev.digikala.controller.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.controller.activity.MainActivity;
import ir.mahdidev.digikala.networkmodel.customer.Billing;
import ir.mahdidev.digikala.networkmodel.customer.Shipping;
import ir.mahdidev.digikala.networkmodel.customer.WebServiceCustomerModel;
import ir.mahdidev.digikala.util.Const;
import ir.mahdidev.digikala.util.Pref;
import ir.mahdidev.digikala.viewmodel.CustomerViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    @BindView(R.id.email_edt)
    TextInputEditText emailEdt;
    @BindView(R.id.username_edt)
    TextInputEditText usernameEdt;
    @BindView(R.id.first_name_edt)
    TextInputEditText firstNameEdt;
    @BindView(R.id.last_name_edt)
    TextInputEditText lastNameEdt;
    @BindView(R.id.password_edt)
    TextInputEditText passwordEdt;
    @BindView(R.id.RegisterImgDigikalaLinear)
    LinearLayout registerCustomer;
    @BindView(R.id.login_button_content)
    LinearLayout loginLinearContent;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;


    public RegisterFragment() {
    }

    private CustomerViewModel viewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initViewModel();
        getDataFromUser();
    }
    private void getDataFromUser() {
        registerCustomer.setOnClickListener(view -> {
            String email = emailEdt.getText().toString().trim();
            String username = usernameEdt.getText().toString().trim();
            String password = passwordEdt.getText().toString().trim();
            String firstName = firstNameEdt.getText().toString();
            String lastName  = lastNameEdt.getText().toString();
            if (!EMailValidation(email)){
                Toast.makeText(getActivity() , getString(R.string.wrong_email) , Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.length() < 6) {
                Toast.makeText(getActivity(), getString(R.string.wrong_password), Toast.LENGTH_SHORT).show();
                return;
            }
            if (email.isEmpty() && username.isEmpty() && password.isEmpty() && firstName.isEmpty()
                    && lastName.isEmpty()){
                Toast.makeText(getActivity(), getString(R.string.fill_all_field), Toast.LENGTH_SHORT).show();
                return;
            }
            sendDataToServer(email, username, firstName, lastName);
        });
    }

    private void sendDataToServer(String email, String username, String firstName, String lastName) {
        WebServiceCustomerModel webServiceCustomerModel = new WebServiceCustomerModel();
        webServiceCustomerModel.setEmail(email);
        webServiceCustomerModel.setUsername(username);
        webServiceCustomerModel.setFirstName(firstName);
        webServiceCustomerModel.setLastName(lastName);
        webServiceCustomerModel.setBilling(new Billing(firstName , lastName , email , ""));
        webServiceCustomerModel.setShipping(new Shipping(firstName , lastName));
        viewModel.registerCustomer(webServiceCustomerModel).observe(RegisterFragment.this,
                webServiceCustomerModel1 -> {
                   if (webServiceCustomerModel1.getUsername()!=null){
                       Pref.saveCustomerModelToPref(getActivity() , webServiceCustomerModel1);
                       Toast.makeText(getActivity() , getString(R.string.register_successfull)  ,
                               Toast.LENGTH_SHORT).show();
                               startActivity(MainActivity.newIntent(getActivity()));
                       getActivity().finishAffinity();
                   }else if (webServiceCustomerModel1.getError() !=null){
                       Toast.makeText(getActivity() , getString(R.string.problem_occur) + webServiceCustomerModel1
                       .getError().getMessage() , Toast.LENGTH_SHORT).show();
                   }else if (webServiceCustomerModel1.getCode()==400){
                       Toast.makeText(getActivity() , getString(R.string.repeat_email) ,
                               Toast.LENGTH_SHORT).show();
                   }
                });
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(CustomerViewModel.class);
    }
    public static boolean EMailValidation(String emailstring) {
        if (null == emailstring || emailstring.length() == 0) {
            return false;
        }
        Pattern emailPattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher emailMatcher = emailPattern.matcher(emailstring);
        return emailMatcher.matches();
    }
}
