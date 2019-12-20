package ir.mahdidev.digikala.controller.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.adapter.AddressRecyclerViewAdapter;
import ir.mahdidev.digikala.adapter.MainHorizontalRecyclerViewAdapter;
import ir.mahdidev.digikala.database.CustomerAddressModel;
import ir.mahdidev.digikala.database.ProductBasketModel;
import ir.mahdidev.digikala.networkmodel.coupon.WebServiceCoupon;
import ir.mahdidev.digikala.networkmodel.customer.Billing;
import ir.mahdidev.digikala.networkmodel.customer.Shipping;
import ir.mahdidev.digikala.networkmodel.customer.WebServiceCustomerModel;
import ir.mahdidev.digikala.networkmodel.order.CouponLine;
import ir.mahdidev.digikala.networkmodel.order.LineItem;
import ir.mahdidev.digikala.networkmodel.order.WebServiceOrder;
import ir.mahdidev.digikala.networkmodel.product.Image;
import ir.mahdidev.digikala.networkmodel.product.WebserviceProductModel;
import ir.mahdidev.digikala.util.Const;
import ir.mahdidev.digikala.util.Pref;
import ir.mahdidev.digikala.viewmodel.CustomerViewModel;
import ir.mahdidev.digikala.viewmodel.ProductBasketViewModel;
import ir.mahdidev.digikala.viewmodel.ProductViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class FinalizeBasketFragment extends Fragment {

    @BindView(R.id.register_coupons)
    Button registerCoupons;
    @BindView(R.id.coupons_edt)
    TextInputEditText couponsEdt;
    @BindView(R.id.register_order)
    LinearLayout registerOrder;
    @BindView(R.id.order_note_edt)
    TextInputEditText orderNoteEdt;
    @BindView(R.id.customer_address_recyclerView)
    RecyclerView customerAddressRecyclerView;
    @BindView(R.id.products_recyclerView)
    RecyclerView productsRecyclerView;
    @OnClick(R.id.back_toolbar)
    void onBackClicked(){
        navController.popBackStack();
    }

    public FinalizeBasketFragment() {
    }

    private MainHorizontalRecyclerViewAdapter mainHorizontalRecyclerViewAdapter;
    private AddressRecyclerViewAdapter addressRecyclerViewAdapter;
    private ProductBasketViewModel viewModel;
    private CustomerViewModel customerViewModel;
    private WebServiceCustomerModel webServiceCustomerModel;
    private NavController navController;
    private List<LineItem> listItem = new ArrayList<>();
    private List<CouponLine> couponLines = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: 12/20/2019 isUserLoggedIn
        webServiceCustomerModel = Pref.getCustomerModelFromPref(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_finalize_basket, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this , view);
        navController = Navigation.findNavController(view);
        initViewModel();
        applyCoupons();
        sendOrderToServer();
    }

    private void applyCoupons() {
        registerCoupons.setOnClickListener(view -> {
            String codeCoupons = couponsEdt.getText().toString().trim();
            customerViewModel.verifyCoupon(codeCoupons).observe(this , webServiceCoupons -> {
                if (!webServiceCoupons.isEmpty()){
                    Toast.makeText(getActivity() , "کد تخفیف شما تایید شد" , Toast.LENGTH_LONG).show();
                    for (WebServiceCoupon webServiceCoupon : webServiceCoupons){
                        couponLines.add(new CouponLine( webServiceCoupon.getCode() ,
                                webServiceCoupon.getAmount()));
                    }
                }
            });
        });
    }

    private void sendOrderToServer() {
        registerOrder.setOnClickListener(view -> {
            String orderNote = orderNoteEdt.getText().toString().trim();
            CustomerAddressModel customerAddressModel = addressRecyclerViewAdapter.getCustomerAddress();
            ir.mahdidev.digikala.networkmodel.order.Shipping shipping = new ir.mahdidev.digikala.networkmodel.order.Shipping(customerAddressModel.getFirstName() , customerAddressModel.getLastName() ,
                    customerAddressModel.getLatitude() + " " + customerAddressModel.getLongitude() ,
                    customerAddressModel.getCustomerAddress() , customerAddressModel.getMapAddress() , customerAddressModel.getCity()
            , customerAddressModel.getProvince() , customerAddressModel.getPostalCode() , "ایران");
            ir.mahdidev.digikala.networkmodel.order.Billing billing = new ir.mahdidev.digikala.networkmodel.order.Billing(customerAddressModel.getFirstName() , customerAddressModel.getLastName() ,
                    customerAddressModel.getLatitude() + " " + customerAddressModel.getLongitude() , customerAddressModel.getCustomerAddress() ,
                    customerAddressModel.getMapAddress() , customerAddressModel.getCity() , customerAddressModel.getProvince() ,
                    customerAddressModel.getPostalCode() , "ایران" , webServiceCustomerModel.getEmail() , customerAddressModel.getPhoneNumber());
            WebServiceOrder webServiceOrder = new WebServiceOrder();
            webServiceOrder.setBilling(billing);
            webServiceOrder.setShipping(shipping);
            webServiceOrder.setPaymentMethod("basc");
            webServiceOrder.setPaymentMethodTitle("Direct Bank Transfer");
            webServiceOrder.setLineItems(listItem);
            webServiceOrder.setCustomerNote(orderNote);
            webServiceOrder.setCustomerId(webServiceCustomerModel.getId());
            webServiceOrder.setCouponLines(couponLines);
            viewModel.sendOrder(webServiceOrder).observe(this , orderResult -> {
                if (orderResult.getId() != null){
                    Toast.makeText(getActivity() , "سفارش شما به شماره سفارش " + orderResult.getId() + " ثبت شد" , Toast.LENGTH_LONG).show();
                    getActivity().finish();
                    viewModel.deleteAllProductsRows();
                }
            });
        });
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ProductBasketViewModel.class);
        customerViewModel = ViewModelProviders.of(this).get(CustomerViewModel.class);
        viewModel.getAllBasketProductDb().observe(this, this::iniProductsRecyclerView);
        customerViewModel.getAllCustomerAddressDb(webServiceCustomerModel.getId()).observe(this , this::initAddressRecyclerView);
    }

    private void initAddressRecyclerView(List<CustomerAddressModel> customerAddressModels) {
        if (addressRecyclerViewAdapter == null){
            addressRecyclerViewAdapter = new AddressRecyclerViewAdapter(customerAddressModels , getActivity() , Const.FROM_FINALIZE_BASKET_FRAGMENT);
        }else {
            addressRecyclerViewAdapter.setAddressList(customerAddressModels);
            addressRecyclerViewAdapter.notifyDataSetChanged();
        }
        customerAddressRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity() , RecyclerView.HORIZONTAL , true));
        customerAddressRecyclerView.setAdapter(addressRecyclerViewAdapter);
    }

    private void iniProductsRecyclerView(List<ProductBasketModel> productBasketModels) {

        List<WebserviceProductModel> productModelList = new ArrayList<>();
        for (ProductBasketModel productBasketModel : productBasketModels){
            listItem.add(new LineItem(productBasketModel.getProductId() , productBasketModel.getProductCount()));
            WebserviceProductModel webserviceProductModel = new WebserviceProductModel();
            webserviceProductModel.setId(productBasketModel.getProductId());
            webserviceProductModel.setName(productBasketModel.getTitleProduct());
            webserviceProductModel.setShortDescription(productBasketModel.getShortDescription());
            List<Image> images = new ArrayList<>();
            Image image = new Image();
            image.setSrc(productBasketModel.getImageSrc());
            webserviceProductModel.setImages(images);
            webserviceProductModel.setRegularPrice(productBasketModel.getPrice());
            webserviceProductModel.setPrice(productBasketModel.getFinalPrice());
            productModelList.add(webserviceProductModel);
        }
        if (mainHorizontalRecyclerViewAdapter == null){
            mainHorizontalRecyclerViewAdapter = new MainHorizontalRecyclerViewAdapter( productModelList, getActivity());
        }else {
            mainHorizontalRecyclerViewAdapter.setProductList(productModelList);
            mainHorizontalRecyclerViewAdapter.notifyDataSetChanged();
        }
        productsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity() , RecyclerView.HORIZONTAL , true));
        productsRecyclerView.setAdapter(mainHorizontalRecyclerViewAdapter);
    }
}
