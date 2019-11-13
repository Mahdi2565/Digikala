package ir.mahdidev.digikala.controller.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
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
import android.widget.Toast;

import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.adapter.CategoryRecyclerViewAdapter;
import ir.mahdidev.digikala.adapter.MainHorizontalRecyclerViewAdapter;
import ir.mahdidev.digikala.adapter.SliderProductAdapter;
import ir.mahdidev.digikala.networkmodel.category.WebserviceCategoryModel;
import ir.mahdidev.digikala.networkmodel.product.WebserviceProductModel;
import ir.mahdidev.digikala.util.Const;
import ir.mahdidev.digikala.util.MyApplication;
import ir.mahdidev.digikala.viewmodel.ProductViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment {

    @BindView(R.id.title_product_toolbar)
    TextView titleProductToolbar;
    @OnClick(R.id.back_toolbar)
    void onBackClick(){
        getActivity().finish();
    }
    @BindView(R.id.basket_img)
    ImageView basketImg;
    @BindView(R.id.imageSlider)
    SliderView sliderView;
    @BindView(R.id.amazing_suggestion_label)
    ImageView amazingSuggestionLabel;
    @BindView(R.id.amazing_suggestion_logo)
    ImageView amazingSuggestionLogo;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.parent_fragment_product)
    RelativeLayout parentFragmentProduct;
    @BindView(R.id.title_product)
    TextView titleProduct;
    @BindView(R.id.short_description)
    TextView shortDescriptionProduct;
    @BindView(R.id.description_txt)
    TextView descriptionProduct;
    @BindView(R.id.price_regular)
    TextView regularPrice;
    @BindView(R.id.sale_price)
    TextView salePrice;
    @BindView(R.id.category_product_fragment_recyclerView)
    RecyclerView categoryProductRecyclerView;
    @BindView(R.id.related_product_recyclerView)
    RecyclerView relatedProductRecyclerView;

    private boolean isAmazingSuggestion = false;
    private ProductViewModel viewModel;
    private SliderProductAdapter sliderProductAdapter;
    private CategoryRecyclerViewAdapter categoryRecyclerViewAdapter;
    private MainHorizontalRecyclerViewAdapter relatedProductsAdapter;
    public ProductFragment() {}

    public static ProductFragment newInstance() {
        Bundle args = new Bundle();
        ProductFragment fragment = new ProductFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this , view);
        initViewModel();

    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ProductViewModel.class);

        viewModel.getSingleProductLiveData().observe(this, webserviceProductModel -> {
            // TODO: 11/13/2019 handle when product not Arrivied
            if (viewModel.getSingleProductLiveData()==null || viewModel.getProductIdMutableLiveData().getValue()!=null){
                viewModel.loadSingleProductLiveData(viewModel.getProductIdMutableLiveData().getValue());
            }
            initprogressBar();
            isAmazingSuggest(webserviceProductModel);
            initSliderView(webserviceProductModel);
            initTitleDescriptionAndPrice(webserviceProductModel);
            initCategoryRecyclerView(webserviceProductModel);
            initRelatedProductCategoryRecyclerView(webserviceProductModel);
        });
    }

    private void initRelatedProductCategoryRecyclerView(WebserviceProductModel webserviceProductModel) {
        viewModel.getRelatedProducts(webserviceProductModel.getRelatedIds().toString()).observe(this, new Observer<List<WebserviceProductModel>>() {
            @Override
            public void onChanged(List<WebserviceProductModel> webserviceProductModels) {
                if (relatedProductsAdapter==null){
                    relatedProductsAdapter = new MainHorizontalRecyclerViewAdapter(webserviceProductModels , getActivity());
                    relatedProductRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity() , RecyclerView.HORIZONTAL , true));
                    relatedProductRecyclerView.setAdapter(relatedProductsAdapter);
                }else {
                    relatedProductsAdapter.setProductList(webserviceProductModels);
                    relatedProductsAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void initCategoryRecyclerView(WebserviceProductModel webserviceProductModel) {
        viewModel.getProductCategories(webserviceProductModel.getId()).observe(this, new Observer<List<WebserviceCategoryModel>>() {
            @Override
            public void onChanged(List<WebserviceCategoryModel> categoryModelList) {
                if (categoryRecyclerViewAdapter == null){
                    categoryRecyclerViewAdapter = new CategoryRecyclerViewAdapter(categoryModelList , getActivity() ,
                            Const.FROM_PRODUCT_FRAGMENT);
                    categoryProductRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity() , RecyclerView.HORIZONTAL , true));
                    categoryProductRecyclerView.setAdapter(categoryRecyclerViewAdapter);
                }else {
                    categoryRecyclerViewAdapter.setCategoryList(categoryModelList);
                    categoryRecyclerViewAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void initTitleDescriptionAndPrice(WebserviceProductModel webserviceProductModel) {

        if (webserviceProductModel.getShortDescription().isEmpty()){
            shortDescriptionProduct.setVisibility(View.INVISIBLE);
        }
        titleProduct.setText(webserviceProductModel.getName());
        shortDescriptionProduct.setText(webserviceProductModel.getShortDescription());
        descriptionProduct.setText(webserviceProductModel.getDescription());
        if (isAmazingSuggestion){
            regularPrice.setVisibility(View.VISIBLE);
            String regularPriceTxt = MyApplication.getInstance()
                    .getPersianNumber(Double.parseDouble(webserviceProductModel.getRegularPrice()))
                    + " تومان";
            regularPrice.setText(regularPriceTxt);
        }
        titleProductToolbar.setText(webserviceProductModel.getName());
        String priceTxt =  MyApplication.getInstance()
                .getPersianNumber(Double.parseDouble(webserviceProductModel.getPrice()))
                + " تومان";
        salePrice.setText(priceTxt);

    }

    private void initprogressBar() {
        progressBar.setVisibility(View.GONE);
        parentFragmentProduct.setVisibility(View.VISIBLE);
    }

    private void isAmazingSuggest(WebserviceProductModel webserviceProductModel) {
        if (!webserviceProductModel.getTags().isEmpty()){
                if (webserviceProductModel.getTags().get(0).getId()==Const.DISCOUNT_TAG){
                    isAmazingSuggestion = true;
                    amazingSuggestionLabel.setVisibility(View.VISIBLE);
                    amazingSuggestionLogo.setVisibility(View.VISIBLE);
                }
        }
    }

    private void initSliderView(WebserviceProductModel webserviceProductModel) {
        sliderProductAdapter = new SliderProductAdapter(webserviceProductModel , getActivity());
        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM);
        sliderView.setSliderAdapter(sliderProductAdapter);
    }
}
