package ir.mahdidev.digikala.controller.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
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
import android.widget.TextView;

import com.smarteist.autoimageslider.SliderView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.adapter.CategoryRecyclerViewAdapter;
import ir.mahdidev.digikala.adapter.MainHorizontalRecyclerViewAdapter;
import ir.mahdidev.digikala.adapter.MainVerticalRecyclerViewAdapter;
import ir.mahdidev.digikala.adapter.SliderAdapter;
import ir.mahdidev.digikala.controller.activity.ProductActivity;
import ir.mahdidev.digikala.eventbus.OnProductClickedMessage;
import ir.mahdidev.digikala.networkmodel.Repository;
import ir.mahdidev.digikala.networkmodel.category.WebserviceCategoryModel;
import ir.mahdidev.digikala.networkmodel.product.WebserviceProductModel;
import ir.mahdidev.digikala.util.Const;
import ir.mahdidev.digikala.viewmodel.MainFragmentViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    @BindView(R.id.category_recyclerView)
    RecyclerView categoryRecyclerView;
    @BindView(R.id.amazing_siggestion_recyclerView)
    RecyclerView amazingSuggestionRecyclerView;
    @BindView(R.id.newest_product_recyclerView)
    RecyclerView newestProductRecyclerView;
    @BindView(R.id.most_rating_product_recyclerView)
    RecyclerView ratingProductRecyclerView;
    @BindView(R.id.most_visited_product_recyclerView)
    RecyclerView visiting_productRecyclerView;
    @BindView(R.id.imageSlider)
    SliderView sliderView;
    @BindView(R.id.title_newest_product_horizontal)
    TextView titleNewestproduct;
    @BindView(R.id.title_rating_product_horizontal)
    TextView titleRatingProduct;
    @BindView(R.id.title_visiting_product_horizontal)
    TextView titleVisitingProduct;

    private SliderAdapter sliderAdapter;
    private MainFragmentViewModel viewModel;
    private MainVerticalRecyclerViewAdapter adapter;

    private CategoryRecyclerViewAdapter categoryRecyclerViewAdapter;
    private MainHorizontalRecyclerViewAdapter amazingSuggestionRecyclerViewAdapter;
    private MainHorizontalRecyclerViewAdapter newestProductRecyclerViewAdapter;
    private MainHorizontalRecyclerViewAdapter ratingProductRecyclerViewAdapter;
    private MainHorizontalRecyclerViewAdapter visitingProductRecyclerViewAdapter;

    private static int visitingPage = 1;
    private static int newestPage = 1;
    private static int amazingSuggestionPage = 1;
    private static int ratingPage = 1;
    private static boolean configChangeAmazing = false;
    private static boolean configChangeNewest = false;
    private static boolean configChangeRating = false;
    private static boolean configChangeVisiting = false;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this , view);
        initViewModel();

    }

    private void initSliderView( List<WebserviceCategoryModel> categoryModelList) {
        sliderAdapter = new SliderAdapter( categoryModelList, getActivity());
        sliderView.setSliderAdapter(sliderAdapter);
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainFragmentViewModel.class);

        viewModel.getCategoryListLiveData().observe(this , webserviceCategoryModels ->{
            initSliderView(webserviceCategoryModels);
            initCategoryRecyclerView(webserviceCategoryModels);

        });
        viewModel.getAmazingSuggestionListLiveData(amazingSuggestionPage).observe(this , this::initamazingSuggestionRecyclerView);
        viewModel.getMostNewestListLiveData(newestPage).observe(this , this::initNewestProductRecyclerView);
        viewModel.getMostRatingListLiveData(ratingPage).observe(this , this::initRatingProductRecyclerView);
        viewModel.getMostVisitingListLiveData(visitingPage).observe(this , this::initVisitingRecyclerView);
    }

    private void initVisitingRecyclerView(List<WebserviceProductModel> webserviceProductModels) {
        titleVisitingProduct.setText(getResources().getString(R.string.most_visiting));
        if (visitingProductRecyclerViewAdapter == null){
            visitingProductRecyclerViewAdapter = new MainHorizontalRecyclerViewAdapter(webserviceProductModels , getActivity());
            visiting_productRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, true));
            visiting_productRecyclerView.setAdapter(visitingProductRecyclerViewAdapter);
            visiting_productRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == webserviceProductModels.size() - 1) {
                        if (configChangeVisiting) visitingPage = 0;
                        viewModel.getMostVisitingListLiveData(++visitingPage);
                    }
                }
            });
        }
        else {
            if (configChangeVisiting){
                visitingProductRecyclerViewAdapter.updateProductList(webserviceProductModels);
                visitingProductRecyclerViewAdapter.notifyDataSetChanged();
                configChangeVisiting = !configChangeVisiting;
            }else {
                visitingProductRecyclerViewAdapter.setProductList(webserviceProductModels);
                visitingProductRecyclerViewAdapter.notifyDataSetChanged();
            }

        }
    }

    private void initRatingProductRecyclerView(List<WebserviceProductModel> webserviceProductModels) {
        titleRatingProduct.setText(getResources().getString(R.string.most_rating));
        if (ratingProductRecyclerViewAdapter == null){
            ratingProductRecyclerViewAdapter = new MainHorizontalRecyclerViewAdapter(webserviceProductModels , getActivity());
            ratingProductRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, true));
            ratingProductRecyclerView.setAdapter(ratingProductRecyclerViewAdapter);
            ratingProductRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == webserviceProductModels.size() - 1) {
                        if (configChangeRating) ratingPage = 0;
                        viewModel.getMostRatingListLiveData(++ratingPage);
                    }
                }
            });
        }
        else {
            if (configChangeRating){
                ratingProductRecyclerViewAdapter.updateProductList(webserviceProductModels);
                ratingProductRecyclerViewAdapter.notifyDataSetChanged();
                configChangeRating = !configChangeRating;
            }else {
                ratingProductRecyclerViewAdapter.setProductList(webserviceProductModels);
                ratingProductRecyclerViewAdapter.notifyDataSetChanged();
            }

        }
    }

    private void initNewestProductRecyclerView(List<WebserviceProductModel> webserviceProductModels) {
            titleNewestproduct.setText(getResources().getString(R.string.most_newest));
        if (newestProductRecyclerViewAdapter == null){
            newestProductRecyclerViewAdapter = new MainHorizontalRecyclerViewAdapter(webserviceProductModels , getActivity());
            newestProductRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, true));
            newestProductRecyclerView.setAdapter(newestProductRecyclerViewAdapter);
            newestProductRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == webserviceProductModels.size() - 1) {
                        if (configChangeNewest) newestPage = 0;
                        viewModel.getMostNewestListLiveData(++newestPage);
                    }
                }
            });
        }
        else {
            if (configChangeNewest){
                newestProductRecyclerViewAdapter.updateProductList(webserviceProductModels);
                newestProductRecyclerViewAdapter.notifyDataSetChanged();
                configChangeNewest = !configChangeNewest;
            }else {
                newestProductRecyclerViewAdapter.setProductList(webserviceProductModels);
                newestProductRecyclerViewAdapter.notifyDataSetChanged();
            }

        }
    }

    private void initamazingSuggestionRecyclerView(List<WebserviceProductModel> webserviceProductModels) {
        if (amazingSuggestionRecyclerViewAdapter==null){
            amazingSuggestionRecyclerViewAdapter = new MainHorizontalRecyclerViewAdapter(webserviceProductModels , getActivity());
            amazingSuggestionRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, true));
            amazingSuggestionRecyclerView.setAdapter(amazingSuggestionRecyclerViewAdapter);
            amazingSuggestionRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == webserviceProductModels.size() - 1) {
                        if (configChangeAmazing) amazingSuggestionPage = 0;
                        viewModel.getAmazingSuggestionListLiveData(++amazingSuggestionPage);
                    }
                }
            });
        }
        else {
            if (configChangeAmazing){
                amazingSuggestionRecyclerViewAdapter.updateProductList(webserviceProductModels);
                amazingSuggestionRecyclerViewAdapter.notifyDataSetChanged();
                configChangeAmazing = !configChangeAmazing;
            }else {
                amazingSuggestionRecyclerViewAdapter.setProductList(webserviceProductModels);
                amazingSuggestionRecyclerViewAdapter.notifyDataSetChanged();
            }

        }
    }

    private void initCategoryRecyclerView(List<WebserviceCategoryModel> webserviceCategoryModels) {
        if (categoryRecyclerViewAdapter==null){
            categoryRecyclerViewAdapter = new CategoryRecyclerViewAdapter(webserviceCategoryModels , getActivity());
            categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true));
            categoryRecyclerView.setAdapter(categoryRecyclerViewAdapter);
        }
    }


    @Subscribe
    public void onProductClicked(OnProductClickedMessage message){
        Repository.getInstance().setProductId(message.getProductId());
        Intent intent = ProductActivity.newIntent(getActivity() , message.getProductId());
        startActivity(intent);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        configChangeAmazing = true;
        configChangeNewest = true;
        configChangeRating = true;
        configChangeVisiting = true;
    }
}
