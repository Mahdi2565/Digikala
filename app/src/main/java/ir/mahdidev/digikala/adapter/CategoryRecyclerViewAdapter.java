package ir.mahdidev.digikala.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.networkmodel.category.WebserviceCategoryModel;
import ir.mahdidev.digikala.util.Const;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder> {

    private List<WebserviceCategoryModel> categoryList;
    private Context context;
    private int adapterLocation ;

    public CategoryRecyclerViewAdapter(List<WebserviceCategoryModel> categoryList, Context context ,
                                       int adapterLocation) {
        this.categoryList = categoryList;
        this.context = context;
        this.adapterLocation = adapterLocation;
    }

    public void setCategoryList(List<WebserviceCategoryModel> categoryList) {
        this.categoryList.clear();
        this.categoryList.addAll(categoryList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mainView = null;
        View productFragmentView;
        if (adapterLocation== Const.FROM_MAIN_FRAGMENT){
             mainView = LayoutInflater.from(context).inflate(R.layout.category_horizontal_recyclerview_item
                    , parent , false);
            return new ViewHolder(mainView);
        }else if (adapterLocation==Const.FROM_PRODUCT_FRAGMENT){
             productFragmentView = LayoutInflater.from(context).inflate(R.layout.category_product_fragmnet_horizontal_recyclerview_item
                    , parent , false);
            return new ViewHolder(productFragmentView);
        }
        return  new ViewHolder(mainView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titleCategory.setText(categoryList.get(position).getName());
        holder.parentCardView.setOnClickListener(view ->{

                categoryRecyclerViewAdapterInterface.onCategoryClicked(position, categoryList.get(position));

    });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title_category_horizontal_recyclerView)
        TextView titleCategory;
        @BindView(R.id.category_cardView)
        CardView parentCardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }
    public interface CategoryRecyclerViewAdapterInterface{
        void onCategoryClicked(int position , WebserviceCategoryModel webserviceCategoryModel);
    }
    public CategoryRecyclerViewAdapterInterface categoryRecyclerViewAdapterInterface;
    public void setCategoryRecyclerViewAdapterInterface(
            CategoryRecyclerViewAdapterInterface categoryRecyclerViewAdapterInterface){
        this.categoryRecyclerViewAdapterInterface = categoryRecyclerViewAdapterInterface;
    }

}
