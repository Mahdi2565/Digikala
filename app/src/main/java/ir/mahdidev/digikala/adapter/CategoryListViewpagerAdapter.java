package ir.mahdidev.digikala.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

import ir.mahdidev.digikala.controller.fragment.SubCategoryListFragment;
import ir.mahdidev.digikala.networkmodel.category.WebserviceCategoryModel;

public class CategoryListViewpagerAdapter extends FragmentStateAdapter {
    private List<WebserviceCategoryModel> categoryList;

    public CategoryListViewpagerAdapter(@NonNull Fragment fragment , List<WebserviceCategoryModel> categoryList) {
        super(fragment);
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return SubCategoryListFragment.newInstance(categoryList.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}
