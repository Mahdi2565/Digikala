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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.adapter.SubCategoryListRecyclerViewAdapter;
import ir.mahdidev.digikala.controller.activity.ProductsListActivity;
import ir.mahdidev.digikala.eventbus.ListProductData;
import ir.mahdidev.digikala.networkmodel.category.WebserviceCategoryModel;
import ir.mahdidev.digikala.util.Const;
import ir.mahdidev.digikala.viewmodel.CategoryListViewModel;

public class SubCategoryListFragment extends Fragment {

    @BindView(R.id.sub_category_recyclerView)
    RecyclerView subCategoryRecyclerView;

    private int categoryId;
    private SubCategoryListRecyclerViewAdapter subCategoryListRecyclerViewAdapter;
    private CategoryListViewModel viewModel;
    public SubCategoryListFragment() {
    }

    public static SubCategoryListFragment newInstance(int parentCategoryId) {
        Bundle args = new Bundle();
        args.putInt(Const.BundleKey.SUB_CATEGORY_ID , parentCategoryId);
        SubCategoryListFragment fragment = new SubCategoryListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() !=null){
            categoryId = getArguments().getInt(Const.BundleKey.SUB_CATEGORY_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sub_category_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this , view);
        initViewModel();
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(CategoryListViewModel.class);
            List<WebserviceCategoryModel> subCategoryList = new ArrayList<>();
            if (viewModel.getAllCategory().getValue() !=null){
                getSubCategory(viewModel.getAllCategory().getValue(), subCategoryList);
                initRecyclerView(subCategoryList);
            }
    }

    private void getSubCategory(List<WebserviceCategoryModel> categoryList, List<WebserviceCategoryModel> subCategoryList) {
        for (int i = 0; i < categoryList.size(); i++) {
            if (categoryList.get(i).getParent() == categoryId) {
                subCategoryList.add(categoryList.get(i));
            }
        }
    }

    private void initRecyclerView(List<WebserviceCategoryModel> categoryModelList) {
        if (subCategoryListRecyclerViewAdapter==null){
            subCategoryListRecyclerViewAdapter = new SubCategoryListRecyclerViewAdapter(categoryModelList
                    , getActivity());
        }else {
            subCategoryListRecyclerViewAdapter.setCategoryList(categoryModelList);
            subCategoryListRecyclerViewAdapter.notifyDataSetChanged();
        }
        subCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        subCategoryRecyclerView.setAdapter(subCategoryListRecyclerViewAdapter);
        subCategoryListRecyclerViewAdapter.setSubCategoryAdapterInterface(webserviceCategoryModel -> {
            startActivity(ProductsListActivity.newIntent(getActivity(),new ListProductData(webserviceCategoryModel.getName() ,
                    webserviceCategoryModel.getId() , Const.OrderTag.MOST_NEWEST_PRODUCT , "desc" ,
                    "")));
        });
    }
}
