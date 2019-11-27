package ir.mahdidev.digikala.controller.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import ir.mahdidev.digikala.util.Const;
import ir.mahdidev.digikala.util.MyApplication;
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
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.empty_list)
    TextView emptyList;
    @BindView(R.id.basket_img)
    ImageView basketImg;
    public ProductsListFragment() {
    }
    private ListProductData listProductData;
    private ProductsListViewModel viewModel;
    private ProductsListRecyclerViewAdapter productsListRecyclerViewAdapter;
    private int productListPage = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_products_list, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() !=null){
            listProductData = (ListProductData) getArguments().getSerializable(Const.BundleKey.PRODUCT_LIST_DATA_BUNDLE_KEY);
        }
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
        basketImg.setOnClickListener(view ->
                startActivity(ProductBasketActivity.newIntent(getActivity())));
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ProductsListViewModel.class);
        viewModel.getProductCount().observe(this, integer -> {
            if (integer>0){
                basketBadge.setVisibility(View.VISIBLE);
                basketBadge.setText(String.valueOf(integer));
            }else {
                basketBadge.setVisibility(View.GONE);
            }
        });

        viewModel.getAllSortedProductsList(listProductData , productListPage).observe(this,
                webserviceProductModels -> {
                    progressBar.setVisibility(View.GONE);
                    initRecyclerView(webserviceProductModels);
                });
    }

    private void initRecyclerView(List<WebserviceProductModel> webserviceProductModels) {
        if (productsListRecyclerViewAdapter== null){
            productsListRecyclerViewAdapter = new ProductsListRecyclerViewAdapter(webserviceProductModels , getActivity());
            productsListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            productsListRecyclerView.setAdapter(productsListRecyclerViewAdapter);
        }else {
            productsListRecyclerViewAdapter.setProductList(webserviceProductModels);
            productsListRecyclerViewAdapter.notifyDataSetChanged();
        }
        if (productsListRecyclerViewAdapter.getProductsList().isEmpty()){
            emptyList.setVisibility(View.VISIBLE);
        }

        productsListRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == webserviceProductModels.size() - 1) {
                    viewModel.getAllSortedProductsList(listProductData,++productListPage);
                }
            }
        });
    }
}
