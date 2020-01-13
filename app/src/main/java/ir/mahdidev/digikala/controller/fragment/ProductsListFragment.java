package ir.mahdidev.digikala.controller.fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.nio.file.Path;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.adapter.ProductsListRecyclerViewAdapter;
import ir.mahdidev.digikala.controller.activity.MainActivity;
import ir.mahdidev.digikala.controller.activity.ProductBasketActivity;
import ir.mahdidev.digikala.controller.activity.SearchActivity;
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
    void onBackClicked() {
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
    @BindView(R.id.search_img)
    ImageView searchImg;
    @BindView(R.id.sort_relative)
    RelativeLayout sortRelative;
    @BindView(R.id.sub_sort_txt)
    TextView subSortTxt;
    @BindView(R.id.filter_relative)
    RelativeLayout filterRelative;

    public ProductsListFragment() {
    }

    private ListProductData listProductData;
    private ProductsListViewModel viewModel;
    private ProductsListRecyclerViewAdapter productsListRecyclerViewAdapter;
    private int productListPage = 1;
    private NavController navController;

    private boolean isListEmpty = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_products_list, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            listProductData = (ListProductData) getArguments().getSerializable(Const.BundleKey.PRODUCT_LIST_DATA_BUNDLE_KEY);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        progressBar.setVisibility(View.VISIBLE);
        SortProductDialogFragment.radioChecked = 4;
        navController = Navigation.findNavController(view);
        initViewModel();
        initToolbar();
        basketBadgeFunction();
        searchImgFunction();
        sortProductDialog();
        subSortTxtFunction();
        filterFunction();
    }

    private void filterFunction() {
        filterRelative.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Const.BundleKey.PRODUCT_LIST_DATA_BUNDLE_KEY, listProductData);
            navController.navigate(R.id.action_productsListFragment_to_filterProductsFragment, bundle);
        });

    }

    private void subSortTxtFunction() {
        if (listProductData.getOrderBy().equals(Const.OrderTag.MOST_NEWEST_PRODUCT)) {
            subSortTxt.setText(R.string.most_newest);
            SortProductDialogFragment.radioChecked = 4;
        } else if (listProductData.getOrderBy().equals(Const.OrderTag.MOST_VISITING_PRODUCT)) {
            SortProductDialogFragment.radioChecked = 2;
            subSortTxt.setText(R.string.most_visiting);
        } else if (listProductData.getOrderBy().equals(Const.OrderTag.MOST_RATING_PRODUCT)) {
            SortProductDialogFragment.radioChecked = 3;
            subSortTxt.setText(R.string.most_rating);
        } else if (listProductData.getOrderBy().equals(Const.OrderTag.PRICE_PRODUCT) &&
                listProductData.getOrder().equals("asc")) {
            SortProductDialogFragment.radioChecked = 0;
            subSortTxt.setText(R.string.price_asc);
        } else if (listProductData.getOrderBy().equals(Const.OrderTag.PRICE_PRODUCT) &&
                listProductData.getOrder().equals("desc")) {
            SortProductDialogFragment.radioChecked = 1;
            subSortTxt.setText(R.string.price_desc);
        }
    }

    private void sortProductDialog() {
        sortRelative.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Const.BundleKey.PRODUCT_LIST_DATA_TO_SORT_FRAGMENT_BUNDLE_KEY,
                    listProductData);
            navController.navigate(R.id.action_productsListFragment_to_sortProductDialogFragment, bundle);
        });
    }

    private void searchImgFunction() {
        searchImg.setOnClickListener(view ->
                startActivity(SearchActivity.newIntent(getActivity())));
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
            if (integer > 0) {
                basketBadge.setVisibility(View.VISIBLE);
                basketBadge.setText(String.valueOf(integer));
            } else {
                basketBadge.setVisibility(View.GONE);
            }
        });

        viewModel.getAllSortedProductsList(listProductData, productListPage).observe(this,
                webserviceProductModels -> {
                    if (webserviceProductModels.isEmpty()) {
                        isListEmpty = true;
                    }
                    progressBar.setVisibility(View.GONE);
                    initRecyclerView(webserviceProductModels);
                });
    }

    private void initRecyclerView(List<WebserviceProductModel> webserviceProductModels) {
        if (productsListRecyclerViewAdapter == null) {
            productsListRecyclerViewAdapter = new ProductsListRecyclerViewAdapter(webserviceProductModels, getActivity());
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
                        if (isListEmpty) return;
                        viewModel.getAllSortedProductsList(listProductData, ++productListPage);
                    }
                }
            });
            productsListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            productsListRecyclerView.setAdapter(productsListRecyclerViewAdapter);
        } else {
            productsListRecyclerViewAdapter.setProductList(webserviceProductModels);
            productsListRecyclerViewAdapter.notifyDataSetChanged();
        }
        if (productsListRecyclerViewAdapter.getProductsList().isEmpty()) {
            emptyList.setVisibility(View.VISIBLE);
        } else {
            emptyList.setVisibility(View.GONE);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        productsListRecyclerView = null;
        productsListRecyclerViewAdapter = null;
    }
}
