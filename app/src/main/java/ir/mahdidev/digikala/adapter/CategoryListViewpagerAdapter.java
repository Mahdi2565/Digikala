package ir.mahdidev.digikala.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

import ir.mahdidev.digikala.controller.fragment.SubCategoryListFragment;
import ir.mahdidev.digikala.networkmodel.category.WebserviceCategoryModel;

public class CategoryListViewpagerAdapter extends FragmentPagerAdapter {
    private List<WebserviceCategoryModel> categoryList;
    public CategoryListViewpagerAdapter(List<WebserviceCategoryModel> categoryList , @NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.categoryList = categoryList;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return SubCategoryListFragment.newInstance(categoryList.get(position).getId());
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return categoryList.get(position).getName();
    }
}
