package ir.mahdidev.digikala.controller.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.adapter.ProductBasketAdapterRecyclerView;
import ir.mahdidev.digikala.database.ProductBasketModel;
import ir.mahdidev.digikala.database.ProductFavoriteModel;
import ir.mahdidev.digikala.eventbus.OnProductClickedMessage;
import ir.mahdidev.digikala.viewmodel.ProductFavoriteViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteProductFragment extends Fragment {

    @BindView(R.id.favorite_product_recyclerView)
    RecyclerView favoriteProductRecyclerView;
    @BindView(R.id.empty_favorite_txt)
    TextView emptyFavoriteTxt;

    @OnClick(R.id.back_toolbar)
    void onBackClicked(){
        getActivity().finish();
    }
    private ProductFavoriteViewModel viewModel;
    private ProductBasketAdapterRecyclerView productBasketAdapterRecyclerView;
    public FavoriteProductFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this , view);
        initViewModel();
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ProductFavoriteViewModel.class);
        viewModel.getAllFavoriteProduct().observe(this, this::initRecyclerView);
    }

    private void initRecyclerView(List<ProductFavoriteModel> productFavoriteModels) {
        if (productFavoriteModels.isEmpty()){
            emptyFavoriteTxt.setVisibility(View.VISIBLE);
        }else {
            emptyFavoriteTxt.setVisibility(View.GONE);
        }
        if (productBasketAdapterRecyclerView== null){
            productBasketAdapterRecyclerView = new ProductBasketAdapterRecyclerView(productFavoriteModels
            , getActivity());
            favoriteProductRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            favoriteProductRecyclerView.setAdapter(productBasketAdapterRecyclerView);
        }else {
            productBasketAdapterRecyclerView.setProductBasketList(productFavoriteModels);
            productBasketAdapterRecyclerView.notifyDataSetChanged();
        }
        productBasketAdapterRecyclerView.setProductBasketAdapterInterface(new ProductBasketAdapterRecyclerView.ProductBasketAdapterInterface() {
            @Override
            public void onDeleteProductClicked(Object model) {
                createDeleteAlertDialog((ProductFavoriteModel) model);
            }

            @Override
            public void onProductPictureClicked(int productId) {
                EventBus.getDefault().post(new OnProductClickedMessage(productId));
            }

            @Override
            public void onProductCountChange(ProductBasketModel productBasketModel) {

            }
        });
    }
    private void createDeleteAlertDialog(ProductFavoriteModel productFavoriteModel) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setMessage(getResources().getString(R.string.do_you_want_delete_product_from_favorite));
        alert.setPositiveButton(getResources().getString(R.string.yes), (dialogInterface, i) ->
                viewModel.deleteFavoriteProduct(productFavoriteModel));
        alert.setNegativeButton(getResources().getString(R.string.no),
                (dialogInterface, i) -> dialogInterface.cancel()
        );
        alert.show();
    }
}
