package ir.mahdidev.digikala.controller.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.adapter.CustomerOrdersRecyclerViewAdapter;
import ir.mahdidev.digikala.networkmodel.customer.WebServiceCustomerModel;
import ir.mahdidev.digikala.networkmodel.order.WebServiceOrder;
import ir.mahdidev.digikala.util.Pref;
import ir.mahdidev.digikala.viewmodel.CustomerViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerOrdersFragment extends Fragment {

    @BindView(R.id.no_order_text)
    TextView noOrderText;
    @BindView(R.id.customer_orders_recyclerView)
    RecyclerView customerOrderRecyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @OnClick(R.id.back_toolbar)
    void onBackClicked(){
        navController.popBackStack();
    }

    public CustomerOrdersFragment() {
    }

    private CustomerOrdersRecyclerViewAdapter customerOrdersRecyclerViewAdapter;
    private CustomerViewModel customerViewModel;
    private WebServiceCustomerModel webServiceCustomerModel;
    private NavController navController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webServiceCustomerModel = Pref.getCustomerModelFromPref(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customer_orders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this , view);
        navController = Navigation.findNavController(view);
        initViewModel();
    }

    private void initViewModel() {
        customerViewModel = ViewModelProviders.of(this).get(CustomerViewModel.class);
        customerViewModel.getAllOrders(webServiceCustomerModel.getId())
                .observe(this , this::initRecyclerView);
    }

    private void initRecyclerView(List<WebServiceOrder> webServiceOrders) {
        progressBar.setVisibility(View.GONE);
        if (webServiceOrders.isEmpty()){
            noOrderText.setVisibility(View.VISIBLE);
        }
        if (customerOrdersRecyclerViewAdapter == null){
            customerOrdersRecyclerViewAdapter = new CustomerOrdersRecyclerViewAdapter(webServiceOrders , getActivity());
        }else {
            customerOrdersRecyclerViewAdapter.setWebServiceOrderList(webServiceOrders);
            customerOrdersRecyclerViewAdapter.notifyDataSetChanged();
        }
        customerOrderRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        customerOrderRecyclerView.setAdapter(customerOrdersRecyclerViewAdapter);
    }
}
