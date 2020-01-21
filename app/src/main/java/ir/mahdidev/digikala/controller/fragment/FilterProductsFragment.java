package ir.mahdidev.digikala.controller.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.adapter.AttributeRecyclerViewAdapter;
import ir.mahdidev.digikala.adapter.AttributeTermRecyclerViewAdapter;
import ir.mahdidev.digikala.eventbus.ListProductData;
import ir.mahdidev.digikala.networkmodel.attribute.WebServiceAttribute;
import ir.mahdidev.digikala.networkmodel.attributeterm.WebServiceAttributeTerm;
import ir.mahdidev.digikala.util.Const;
import ir.mahdidev.digikala.viewmodel.ProductViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterProductsFragment extends Fragment {

    @OnClick(R.id.back_toolbar)
    void onBackClicked(){
        navController.popBackStack();
    }
    @BindView(R.id.attributes_recyclerView)
    RecyclerView attributeRecyclerView;
    @BindView(R.id.attributes_term_recyclerView)
    RecyclerView attributeTermRecyclerView;
    @BindView(R.id.filter_btn)
    LinearLayout filterBtn;

    public FilterProductsFragment() {
    }

    private AttributeRecyclerViewAdapter attributeRecyclerViewAdapter;
    private AttributeTermRecyclerViewAdapter attributeTermRecyclerViewAdapter;
    private ProductViewModel viewModel;
    private NavController navController;
    private ListProductData listProductData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() !=null){
            listProductData = (ListProductData) getArguments().getSerializable(Const.BundleKey.PRODUCT_LIST_DATA_BUNDLE_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filter_products, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this ,view);
        navController = Navigation.findNavController(view);
        initViewModel();
        filterBtnFunction();
    }

    private void filterBtnFunction() {
        filterBtn.setOnClickListener(view -> {
            String attributeTxt;
            attributeTxt = attributeRecyclerViewAdapter.getAttributeModel().getSlug();

            List<Integer> attributeTermList = new ArrayList<>();
            if (attributeTermRecyclerViewAdapter == null) {
                Toast.makeText(getActivity(), "لطفا یک ویژگی انتخاب کنید", Toast.LENGTH_SHORT).show();
                return;
            }
                for (WebServiceAttributeTerm attributeTerm : attributeTermRecyclerViewAdapter.getAttributeTermList()) {
                    if (attributeTerm.isSelected()) {
                        attributeTermList.add(attributeTerm.getId());
                    }
                }
                if (attributeTermList.isEmpty()) {
                    Toast.makeText(getActivity(), "لطفا یک ویژگی انتخاب کنید", Toast.LENGTH_SHORT).show();
                    return;
                }

            listProductData.setAttribute(attributeTxt);
            listProductData.setAttributeTerm(attributeTermList);
            Bundle bundle = new Bundle();
            bundle.putSerializable(Const.BundleKey.PRODUCT_LIST_DATA_BUNDLE_KEY , listProductData);
            navController.navigate(R.id.action_filterProductsFragment_to_productsListFragment , bundle);
        });
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        viewModel.getAllAttributes().observe(this , this::initAttributeRecyclerView);
    }

    private void initAttributeRecyclerView(List<WebServiceAttribute> webServiceAttributes) {
        if (attributeRecyclerViewAdapter == null){
            attributeRecyclerViewAdapter = new AttributeRecyclerViewAdapter(webServiceAttributes , getActivity());
        }else {
            attributeRecyclerViewAdapter.setAttributeList(webServiceAttributes);
            attributeRecyclerViewAdapter.notifyDataSetChanged();
        }
        attributeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        attributeRecyclerView.setAdapter(attributeRecyclerViewAdapter);
        attributeRecyclerViewAdapter.setAttributeAdapterInterface(attributeId -> {
            viewModel.getAllAttributeTerm(attributeId).observe(FilterProductsFragment.this, webServiceAttributeTerms -> {
                initAttributeTermRecyclerView(webServiceAttributeTerms);
            });
        });
    }

    private void initAttributeTermRecyclerView(List<WebServiceAttributeTerm> webServiceAttributeTerms) {
        if (attributeTermRecyclerViewAdapter == null){
            attributeTermRecyclerViewAdapter = new AttributeTermRecyclerViewAdapter(webServiceAttributeTerms ,
                    getActivity());
        }else {
            attributeTermRecyclerViewAdapter.setAttributeTermList(webServiceAttributeTerms);
            attributeTermRecyclerViewAdapter.notifyDataSetChanged();
        }
        attributeTermRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        attributeTermRecyclerView.setAdapter(attributeTermRecyclerViewAdapter);
    }
}
