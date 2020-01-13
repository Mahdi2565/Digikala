package ir.mahdidev.digikala.controller.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.adapter.ProductBasketAdapterRecyclerView;
import ir.mahdidev.digikala.controller.activity.LoginRegisterActivity;
import ir.mahdidev.digikala.database.ProductBasketModel;
import ir.mahdidev.digikala.eventbus.OnProductClickedMessage;
import ir.mahdidev.digikala.networkmodel.customer.WebServiceCustomerModel;
import ir.mahdidev.digikala.util.MyApplication;
import ir.mahdidev.digikala.util.Pref;
import ir.mahdidev.digikala.viewmodel.ProductBasketViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductBasketFragment extends Fragment {

    @BindView(R.id.basket_recyclerView)
    RecyclerView productRecyclerView;
    @BindView(R.id.basket_badge)
    TextView basketBadge;
    private WebServiceCustomerModel webServiceCustomerModel;

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
    @BindView(R.id.finalize_basket)
    LinearLayout finalizeBasket;
    private ProductBasketViewModel viewModel;
    private ProductBasketAdapterRecyclerView productBasketAdapterRecyclerView;
    private NavController navController;
    public ProductBasketFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webServiceCustomerModel = Pref.getCustomerModelFromPref(getActivity());
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
        navController = Navigation.findNavController(view);
        initViewModel();
        finalizeFunction();
    }
    private void finalizeFunction() {
        finalizeBasket.setOnClickListener(view -> {
            if (webServiceCustomerModel==null){
                startActivity(LoginRegisterActivity.newIntent(getActivity()));
                getActivity().finish();
                Toast.makeText(getActivity() , getString(R.string.login_first) , Toast.LENGTH_LONG).show();
            }else {
                navController.navigate(R.id.action_productBasketFragment_to_finalizeBasketFragment);
            }
        });
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
                   finalizeBasket.setVisibility(View.GONE);
                   basketLayout.setVisibility(View.GONE);
                   emptyBasketText.setVisibility(View.VISIBLE);
               }else {
                   finalizeBasket.setVisibility(View.VISIBLE);
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
        if (productBasketAdapterRecyclerView == null) {

            productBasketAdapterRecyclerView = new ProductBasketAdapterRecyclerView(productBasketModels, getActivity());

        } else {
            productBasketAdapterRecyclerView.setProductBasketList(productBasketModels);
            productBasketAdapterRecyclerView.notifyDataSetChanged();
        }
        productRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        productRecyclerView.setAdapter(productBasketAdapterRecyclerView);
        productBasketAdapterRecyclerView.setProductBasketAdapterInterface(new ProductBasketAdapterRecyclerView.ProductBasketAdapterInterface() {
            @Override
            public void onDeleteProductClicked(Object model) {
                createDeleteAlertDialog(model);
            }

            @Override
            public void onProductPictureClicked(int productId) {
                EventBus.getDefault().post(new OnProductClickedMessage(productId));
            }

            @Override
            public void onProductCountChange(ProductBasketModel productBasketModel) {
               viewModel.updateProductBasketDb(productBasketModel);
            }
        });
    }
    private void createDeleteAlertDialog( Object object) {

            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setMessage(getResources().getString(R.string.do_you_want_delete_product_from_basket));
            alert.setPositiveButton(getResources().getString(R.string.yes),
                    (dialogInterface, i) -> viewModel.deleteProduct((ProductBasketModel) object));
            alert.setNegativeButton(getResources().getString(R.string.no),
                    (dialogInterface, i) -> dialogInterface.cancel()
            );
            alert.show();


    }
}
