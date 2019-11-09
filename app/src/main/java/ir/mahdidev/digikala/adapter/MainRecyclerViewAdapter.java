package ir.mahdidev.digikala.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.networkmodel.category.WebserviceCategoryModel;
import ir.mahdidev.digikala.networkmodel.product.WebserviceProductModel;
import ir.mahdidev.digikala.util.Const;

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.ViewHolder> {
    private static final int CATEGORY_VIEWTYPE = 1;
    private static final int PRODUCT_VIEWTYPE = 2;
    private HashMap<String, List> productAndCategoryHashMap;
    private Context context;
    private List<WebserviceProductModel> mostVisitingProductList;
    private List<WebserviceProductModel> mostRatingProductList;
    private List<WebserviceProductModel> mostNewestProductList;
    private List<WebserviceCategoryModel> categoryList;

    public MainRecyclerViewAdapter(HashMap<String, List> productAndCategoryList, Context context) {
        this.productAndCategoryHashMap = productAndCategoryList;
        this.context = context;
        mostVisitingProductList = productAndCategoryList.get(Const.KeyList.MOST_VISITING_LIST);
        mostRatingProductList = productAndCategoryList.get(Const.KeyList.MOST_RATING_LIST);
        mostNewestProductList = productAndCategoryList.get(Const.KeyList.MOST_NEWEST_LIST);
        categoryList = productAndCategoryList.get(Const.KeyList.CATEGORY_LIST);

    }

    public void setProductAndCategoryHashMap(HashMap<String, List> productAndCategoryHashMap) {
        this.productAndCategoryHashMap.clear();
        this.productAndCategoryHashMap.putAll(productAndCategoryHashMap);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return CATEGORY_VIEWTYPE;
        } else return PRODUCT_VIEWTYPE;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case CATEGORY_VIEWTYPE: {
                return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.category_horizontal_recyclerview,
                        parent, false) , viewType);
            }
            case PRODUCT_VIEWTYPE: {
                return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.product_vertical_reyclerview_item,
                        parent, false) , viewType);
            }
            default:
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.product_vertical_reyclerview_item,
                    parent, false) , viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case CATEGORY_VIEWTYPE: {
                CategoryRecyclerViewAdapter adapter = new CategoryRecyclerViewAdapter(categoryList , context);
                holder.categoryRecyclerView.setLayoutManager(new LinearLayoutManager(context , LinearLayoutManager.HORIZONTAL , true));
                holder.categoryRecyclerView.setAdapter(adapter);
                break;
            }
            case PRODUCT_VIEWTYPE: {
                switch (position){
                    case 1: {
                        holder.titleProducts.setText(context.getResources().getString(R.string.most_newest));
                        MainHorizontalRecyclerViewAdapter
                        horizontalRecyclerViewAdapter =
                                new MainHorizontalRecyclerViewAdapter(mostNewestProductList , context);
                        holder.mainHorizontalRecyclerView.setLayoutManager(new LinearLayoutManager(context , RecyclerView.HORIZONTAL , true));
                        holder.mainHorizontalRecyclerView.setAdapter(horizontalRecyclerViewAdapter);
                        break;
                    }

                    case 2: {
                        holder.titleProducts.setText(context.getResources().getString(R.string.most_rating));
                        MainHorizontalRecyclerViewAdapter
                                horizontalRecyclerViewAdapter =
                                new MainHorizontalRecyclerViewAdapter(mostRatingProductList , context);

                        holder.mainHorizontalRecyclerView.setLayoutManager(new LinearLayoutManager(context , RecyclerView.HORIZONTAL , true));
                        holder.mainHorizontalRecyclerView.setAdapter(horizontalRecyclerViewAdapter);
                        break;
                    }

                    case 3: {
                        holder.titleProducts.setText(context.getResources().getString(R.string.most_visiting));
                        MainHorizontalRecyclerViewAdapter
                                horizontalRecyclerViewAdapter =
                                new MainHorizontalRecyclerViewAdapter(mostVisitingProductList , context);

                        holder.mainHorizontalRecyclerView.setLayoutManager(new LinearLayoutManager(context , RecyclerView.HORIZONTAL , true));
                        holder.mainHorizontalRecyclerView.setAdapter(horizontalRecyclerViewAdapter);
                        break;
                    }
                }
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return productAndCategoryHashMap.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView categoryRecyclerView;

        private TextView titleProducts;
        private RecyclerView mainHorizontalRecyclerView;

        public ViewHolder(@NonNull View itemView ,  int viewType) {
            super(itemView);
            switch (viewType) {
                case CATEGORY_VIEWTYPE: {
                    categoryRecyclerView = itemView.findViewById(R.id.category_horizontal_recyclerView);
                    break;
                }
                case PRODUCT_VIEWTYPE: {
                    titleProducts = itemView.findViewById(R.id.title_horizontal);
                    mainHorizontalRecyclerView = itemView.findViewById(R.id.main_horizontal_recyclerView);
                    break;
                }
            }
        }
    }
}
