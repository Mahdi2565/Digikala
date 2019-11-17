package ir.mahdidev.digikala.controller.fragment;


import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.adapter.ProductBasketAdapterRecyclerView;
import ir.mahdidev.digikala.database.ProductBasketModel;
import ir.mahdidev.digikala.networkmodel.product.WebserviceProductModel;
import ir.mahdidev.digikala.util.MyApplication;
import ir.mahdidev.digikala.viewmodel.ProductBasketViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductBasketFragment extends Fragment {

    @BindView(R.id.basket_recyclerView)
    RecyclerView productRecyclerView;
    @BindView(R.id.basket_badge)
    TextView basketBadge;
    @OnClick(R.id.back_toolbar)
    void onBackClicked(){
        getActivity().finish();
    }
    @BindView(R.id.sum_basket_price)
    TextView sumBasketPrice;
    @BindView(R.id.basket_relative)
    RelativeLayout basketLayout;
    @BindView(R.id.empty_basket_txt)
    TextView emptyBasketText;
    private ProductBasketViewModel viewModel;
    private ProductBasketAdapterRecyclerView productBasketAdapterRecyclerView;

    public ProductBasketFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_basket, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initViewModel();
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ProductBasketViewModel.class);
        viewModel.getProductCount().observe(this , integer -> {
                if (integer>0){
                    basketBadge.setVisibility(View.VISIBLE);
                    basketBadge.setText(String.valueOf(integer));
                }else {
                    basketBadge.setVisibility(View.GONE);
                }
            });
           viewModel.getAllBasketProductDb().observe(this , productBasketModels -> {
               if (productBasketModels.isEmpty()){
                   basketLayout.setVisibility(View.GONE);
                   emptyBasketText.setVisibility(View.VISIBLE);
               }else {
                   emptyBasketText.setVisibility(View.GONE);
                   basketLayout.setVisibility(View.VISIBLE);
               }
               initRecyclerView(productBasketModels);
               int productPrice;
               int sumPrice = 0;
               for (ProductBasketModel productBasketModel : productBasketModels){
                   productPrice= Integer.parseInt(productBasketModel.getFinalPrice());
                   sumPrice += productBasketModel.getProductCount() * productPrice;
               }
               String sumPriceTxt = MyApplication.getInstance()
                       .getPersianNumber(sumPrice)
                       + " تومان";
               sumBasketPrice.setText(sumPriceTxt);
           });
     }
    private void initRecyclerView( List<ProductBasketModel> productBasketModels) {
        if (productBasketAdapterRecyclerView==null){

            productBasketAdapterRecyclerView = new
                    ProductBasketAdapterRecyclerView(productBasketModels , getActivity());
            productRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            productRecyclerView.setAdapter(productBasketAdapterRecyclerView);
        }else {
            productBasketAdapterRecyclerView.setProductBasketList(productBasketModels);
            productBasketAdapterRecyclerView.notifyDataSetChanged();
        }
        productBasketAdapterRecyclerView.setProductBasketAdapterInterface(this::createDeleteAlertDialg);
    }

    private void createDeleteAlertDialg(ProductBasketModel productBasketModel) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setMessage(getResources().getString(R.string.do_you_want_delete_product_from_basket));
        alert.setPositiveButton(getResources().getString(R.string.yes), (dialogInterface, i) -> viewModel.deleteProduct(productBasketModel));
        alert.setNegativeButton(getResources().getString(R.string.no),
                (dialogInterface, i) -> dialogInterface.cancel()
        );
        alert.show();
    }
}
