package ir.mahdidev.digikala.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.networkmodel.category.WebserviceCategoryModel;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder> {

    private List<WebserviceCategoryModel> categoryList;
    private Context context;

    public CategoryRecyclerViewAdapter(List<WebserviceCategoryModel> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.category_horizontal_recyclerview_item
        , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titleCategory.setText(categoryList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleCategory;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleCategory = itemView.findViewById(R.id.title_category_horizontal_recyclerView);
        }
    }
}
