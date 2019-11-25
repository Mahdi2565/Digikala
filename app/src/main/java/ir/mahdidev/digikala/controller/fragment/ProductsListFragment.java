package ir.mahdidev.digikala.controller.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.adapter.ProductsListRecyclerViewAdapter;
import ir.mahdidev.digikala.controller.activity.ProductBasketActivity;
import ir.mahdidev.digikala.eventbus.ListProductData;
import ir.mahdidev.digikala.networkmodel.product.WebserviceProductModel;
import ir.mahdidev.digikala.viewmodel.ProductsListViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsListFragment extends Fragment {

    @BindView(R.id.title_toolbar)
    TextView titleToolbar;
    @OnClick(R.id.back_toolbar)
    void onBackClicked(){
        getActivity().finish();
    }
    @BindView(R.id.basket_badge)
    TextView basketBadge;
    @BindView(R.id.products_list_recyclerView)
    RecyclerView productsListRecyclerView;

    public ProductsListFragment() {
    }
    private ListProductData listProductData;
    private ProductsListViewModel viewModel;
    private ProductsListRecyclerViewAdapter productsListRecyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_products_list, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this , view);
        initViewModel();
        initToolbar();
        basketBadgeFunction();
    }

    private void initToolbar() {
        titleToolbar.setText(listProductData.getToolbarTitle());
    }

    private void basketBadgeFunction() {
        basketBadge.setOnClickListener(view ->
                startActivity(ProductBasketActivity.newIntent(getActivity())));
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ProductsListViewModel.class);
        listProductData = viewModel.listProductData();
        viewModel.getProductCount().observe(this, integer -> {
            if (integer>0){
                basketBadge.setVisibility(View.VISIBLE);
                basketBadge.setText(String.valueOf(integer));
            }else {
                basketBadge.setVisibility(View.GONE);
            }
        });
        viewModel.getAllSortedProductsList(listProductData.getCategoryId() , listProductData.getOrderBy() ,
                listProductData.getOrder() , listProductData.getSearch() , 1).observe(this,
                webserviceProductModels -> {
                    initRecyclerView(webserviceProductModels);
                });
    }

    private void initRecyclerView(List<WebserviceProductModel> webserviceProductModels) {
        if (productsListRecyclerViewAdapter== null){
            productsListRecyclerViewAdapter = new ProductsListRecyclerViewAdapter(webserviceProductModels , getActivity());
            productsListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            productsListRecyclerView.setAdapter(productsListRecyclerViewAdapter);
        }
    }
}
