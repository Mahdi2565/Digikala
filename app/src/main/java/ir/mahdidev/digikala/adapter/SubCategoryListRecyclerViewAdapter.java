package ir.mahdidev.digikala.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.networkmodel.category.WebserviceCategoryModel;

public class SubCategoryListRecyclerViewAdapter extends RecyclerView.Adapter<SubCategoryListRecyclerViewAdapter.ViewHolder> {

    private List<WebserviceCategoryModel> categoryList;
    private Context context;

    public SubCategoryListRecyclerViewAdapter(List<WebserviceCategoryModel> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;
    }
    public void setCategoryList(List<WebserviceCategoryModel> categoryList){
        this.categoryList = new ArrayList<>();
        this.categoryList.addAll(categoryList);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.sub_category_item ,
                parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.SubCategoryTitle.setText(categoryList.get(position).getName());
        Picasso.get().load(categoryList.get(position).getImage().getSrc()).placeholder
                (R.drawable.digikala_place_holder).into(holder.subCategoryImage);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.sub_category_title)
        TextView SubCategoryTitle;
        @BindView(R.id.sub_category_img)
        ImageView subCategoryImage;
        @BindView(R.id.sub_category_relative)
        RelativeLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }
}
