package ir.mahdidev.digikala.controller.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.adapter.CategoryListViewpagerAdapter;
import ir.mahdidev.digikala.networkmodel.category.WebserviceCategoryModel;
import ir.mahdidev.digikala.util.Const;
import ir.mahdidev.digikala.viewmodel.CategoryListViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryListFragment extends Fragment {

    @OnClick(R.id.back_toolbar)
    void onBackClicked(){
        getActivity().finish();
    }
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager2 viewPager;

    private CategoryListViewpagerAdapter categoryListViewpagerAdapter;
    private CategoryListViewModel viewModel;
    private int categoryPosition;
    public CategoryListFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category_list, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() !=null){
            categoryPosition = getArguments().getInt(Const.BundleKey.CATEGORY_POSITION_BUNDLE_KEY);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this , view);
        initViewModel();
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(CategoryListViewModel.class);
        viewModel.getAllCategory().observe(this, this::initViewPager);
    }

    private void initViewPager(List<WebserviceCategoryModel> categoryModelList) {
     List<WebserviceCategoryModel> categoryList = new ArrayList<>();
        filterCategory(categoryModelList, categoryList);
        categoryListViewpagerAdapter = new CategoryListViewpagerAdapter(CategoryListFragment.this , categoryList);
     viewPager.setAdapter(categoryListViewpagerAdapter);
     viewPager.setOffscreenPageLimit(3);
     viewPager.setCurrentItem(categoryPosition ,true);
     initTabLayout(categoryList);
    }

    private void initTabLayout(List<WebserviceCategoryModel> categoryList) {
        new TabLayoutMediator(tabLayout , viewPager , (tab, position) ->
                tab.setText(categoryList.get(position).getName())).attach();

    }

    private void filterCategory(List<WebserviceCategoryModel> categoryModelList, List<WebserviceCategoryModel> categoryList) {
        for (WebserviceCategoryModel categoryModel : categoryModelList){
            if (categoryModel.getParent()==0){
                categoryList.add(categoryModel);
            }
        }
    }
}
