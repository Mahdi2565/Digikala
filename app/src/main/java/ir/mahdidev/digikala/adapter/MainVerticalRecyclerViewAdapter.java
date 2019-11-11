package ir.mahdidev.digikala.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.networkmodel.Repository;
import ir.mahdidev.digikala.networkmodel.category.WebserviceCategoryModel;
import ir.mahdidev.digikala.networkmodel.product.WebserviceProductModel;
import ir.mahdidev.digikala.util.Const;

public class MainVerticalRecyclerViewAdapter extends RecyclerView.Adapter<MainVerticalRecyclerViewAdapter.ViewHolder> {
    private static final int CATEGORY_VIEWTYPE = 1;
    private static final int PRODUCT_VIEWTYPE = 2;
    private static final int AMAZING_PRODUCT_VIEWTYPE = 3;
    private static int mostVisitPage = 1;
    private static int mostRatingPage = 1;
    private static int mostNewestPage = 1;
    private static int amazingSuggestionPage= 1;
    private String orderBy = "";
    private boolean isProductNewestFinished = false;
    private boolean isProductRatingFinished = false;
    private boolean isProductVisitingFinished = false;
    private boolean isAmazingSuggestionFinished = false;
    private HashMap<String, List> productAndCategoryHashMap;
    private Context context;
    private List<WebserviceProductModel> mostVisitingProductList = new ArrayList<>();
    private List<WebserviceProductModel> mostRatingProductList = new ArrayList<>();
    private List<WebserviceProductModel> mostNewestProductList = new ArrayList<>();
    private List<WebserviceCategoryModel> categoryList = new ArrayList<>();
    private List<WebserviceProductModel>  amazingSuggestion = new ArrayList<>();
    private MainHorizontalRecyclerViewAdapter horizontalRecyclerViewAdapterMostVisit;
    private MainHorizontalRecyclerViewAdapter horizontalRecyclerViewAdapterMostRate;
    private MainHorizontalRecyclerViewAdapter horizontalRecyclerViewAdapterMostNew;
    private MainHorizontalRecyclerViewAdapter horizontalRecyclerViewAdapterAmazingSuggestion;


    public MainVerticalRecyclerViewAdapter(HashMap<String, List> productAndCategoryList, Context context) {
        this.productAndCategoryHashMap = productAndCategoryList;
        this.context = context;
        retriveListFromHashMap(productAndCategoryList);

    }

    private void retriveListFromHashMap(HashMap<String, List> productAndCategoryList) {
        mostVisitingProductList.clear();
        mostRatingProductList.clear();
        mostNewestProductList.clear();
        categoryList.clear();
        amazingSuggestion.clear();
        mostVisitingProductList = productAndCategoryList.get(Const.KeyList.MOST_VISITING_LIST);
        mostRatingProductList = productAndCategoryList.get(Const.KeyList.MOST_RATING_LIST);
        mostNewestProductList = productAndCategoryList.get(Const.KeyList.MOST_NEWEST_LIST);
        amazingSuggestion     = productAndCategoryList.get(Const.KeyList.AMAZING_SUGGESTION);
        categoryList = productAndCategoryList.get(Const.KeyList.CATEGORY_LIST);
    }

    public void setProductAndCategoryHashMap(HashMap<String, List> productAndCategoryHashMap) {
        retriveListFromHashMap(productAndCategoryHashMap);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return CATEGORY_VIEWTYPE;
        } else if (position == 1){
            return AMAZING_PRODUCT_VIEWTYPE;
        }else return PRODUCT_VIEWTYPE;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case CATEGORY_VIEWTYPE: {
                return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.category_horizontal_recyclerview,
                        parent, false), viewType);
            }
            case PRODUCT_VIEWTYPE: {
                return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.product_vertical_reyclerview_item,
                        parent, false), viewType);
            }
            case AMAZING_PRODUCT_VIEWTYPE :{
                return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.amazing_suggestion_product_vertical_reyclerview_item
                , parent , false) , viewType);
            }
            default:
                return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.product_vertical_reyclerview_item,
                        parent, false), viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case CATEGORY_VIEWTYPE: {
                CategoryRecyclerViewAdapter adapter = new CategoryRecyclerViewAdapter(categoryList, context);
                holder.categoryRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true));
                holder.categoryRecyclerView.setAdapter(adapter);
                break;
            }
            case AMAZING_PRODUCT_VIEWTYPE:{
                if (position==1){
                    horizontalRecyclerViewAdapterAmazingSuggestion =
                            new MainHorizontalRecyclerViewAdapter(amazingSuggestion, context);
                    holder.mainHorizontalRecyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, true));
                    holder.mainHorizontalRecyclerView.setAdapter(horizontalRecyclerViewAdapterAmazingSuggestion);
                    holder.mainHorizontalRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                            super.onScrollStateChanged(recyclerView, newState);
                        }

                        @Override
                        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                            if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == amazingSuggestion.size() - 1) {
                                orderBy = Const.AMAZING_SUGGESTION_ORDER;
                                if (!isAmazingSuggestionFinished) {
                                    Log.e("TAG4" , "HERE!!!!");
                                    AsyncTask asyncTask = new AsyncTask();
                                    asyncTask.execute(++amazingSuggestionPage);
                                }
                            }
                        }
                    });

                }
                break;
            }
            case PRODUCT_VIEWTYPE: {
                switch (position) {
                    case 2: {
                        holder.titleProducts.setText(context.getResources().getString(R.string.most_newest));
                        horizontalRecyclerViewAdapterMostNew =
                                new MainHorizontalRecyclerViewAdapter(mostNewestProductList, context);
                        holder.mainHorizontalRecyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, true));
                        holder.mainHorizontalRecyclerView.setAdapter(horizontalRecyclerViewAdapterMostNew);
                        holder.mainHorizontalRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                            @Override
                            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                super.onScrollStateChanged(recyclerView, newState);
                            }

                            @Override
                            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                super.onScrolled(recyclerView, dx, dy);

                                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                                if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == mostNewestProductList.size() - 1) {
                                    orderBy = Const.OrderTag.MOST_NEWEST_PRODUCT;
                                    if (!isProductNewestFinished) {
                                        AsyncTask asyncTask = new AsyncTask();
                                        asyncTask.execute(++mostNewestPage);
                                    }
                                }
                            }
                        });
                        break;
                    }

                    case 3: {
                        holder.titleProducts.setText(context.getResources().getString(R.string.most_rating));

                        horizontalRecyclerViewAdapterMostRate =
                                new MainHorizontalRecyclerViewAdapter(mostRatingProductList, context);

                        holder.mainHorizontalRecyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, true));
                        holder.mainHorizontalRecyclerView.setAdapter(horizontalRecyclerViewAdapterMostRate);
                        holder.mainHorizontalRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                            @Override
                            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                super.onScrollStateChanged(recyclerView, newState);
                            }

                            @Override
                            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                super.onScrolled(recyclerView, dx, dy);

                                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                                if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == mostRatingProductList.size() - 1) {
                                    orderBy = Const.OrderTag.MOST_RATING_PRODUCT;
                                    if (!isProductRatingFinished) {
                                        AsyncTask asyncTask = new AsyncTask();
                                        asyncTask.execute(++mostRatingPage);
                                    }
                                }
                            }
                        });
                        break;
                    }

                    case 4: {
                        holder.titleProducts.setText(context.getResources().getString(R.string.most_visiting));

                        horizontalRecyclerViewAdapterMostVisit =
                                new MainHorizontalRecyclerViewAdapter(mostVisitingProductList, context);

                        holder.mainHorizontalRecyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, true));
                        holder.mainHorizontalRecyclerView.setAdapter(horizontalRecyclerViewAdapterMostVisit);
                        holder.mainHorizontalRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                            @Override
                            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                super.onScrollStateChanged(recyclerView, newState);
                            }

                            @Override
                            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                super.onScrolled(recyclerView, dx, dy);

                                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                                if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == mostVisitingProductList.size() - 1) {
                                    orderBy = Const.OrderTag.MOST_VISITING_PRODUCT;
                                    if (!isProductVisitingFinished) {
                                        AsyncTask asyncTask = new AsyncTask();
                                        asyncTask.execute(++mostVisitPage);
                                    }
                                }
                            }
                        });
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

        private ImageView amazingSuggestionImg;

        public ViewHolder(@NonNull View itemView, int viewType) {
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
                case AMAZING_PRODUCT_VIEWTYPE:{
                    mainHorizontalRecyclerView = itemView.findViewById(R.id.main_horizontal_recyclerView);
                    amazingSuggestionImg = itemView.findViewById(R.id.amazing_suggestion_img);
                    break;
                }
            }
        }
    }

    public class AsyncTask extends android.os.AsyncTask<Integer, Void, List<WebserviceProductModel>> {

        @Override
        protected List<WebserviceProductModel> doInBackground(Integer... page) {
            if (orderBy.equals(Const.AMAZING_SUGGESTION_ORDER)) orderBy = Const.OrderTag.MOST_NEWEST_PRODUCT;
            return Repository.getInstance().getNextPageProduct(orderBy, page[0]);
        }

        @Override
        protected void onPostExecute(List<WebserviceProductModel> webserviceProductModels) {
            super.onPostExecute(webserviceProductModels);

            if (amazingSuggestionPage>1) orderBy = Const.AMAZING_SUGGESTION_ORDER;
            if (webserviceProductModels.isEmpty()) {
                // TODO: 11/10/2019  
                switch (orderBy) {
                    case Const.OrderTag.MOST_NEWEST_PRODUCT: {
                            isProductNewestFinished = true;
                        break;
                    }
                    case Const.OrderTag.MOST_RATING_PRODUCT: {
                            isProductRatingFinished = true;
                        break;
                    }
                    case Const.OrderTag.MOST_VISITING_PRODUCT: {
                            isProductVisitingFinished = true;
                        break;
                    }
                    case Const.AMAZING_SUGGESTION_ORDER :{
                        isAmazingSuggestionFinished = true;
                        break;
                    }
                }
            }
            switch (orderBy) {

                case Const.OrderTag.MOST_NEWEST_PRODUCT : {
                //    horizontalRecyclerViewAdapterMostNew.setProductList(webserviceProductModels);
                    horizontalRecyclerViewAdapterMostNew.notifyDataSetChanged();
                    break;
                }
                case Const.OrderTag.MOST_RATING_PRODUCT: {
                  //  horizontalRecyclerViewAdapterMostRate.setProductList(webserviceProductModels);
                    horizontalRecyclerViewAdapterMostRate.notifyDataSetChanged();
                    break;
                }
                case Const.OrderTag.MOST_VISITING_PRODUCT: {
                //    horizontalRecyclerViewAdapterMostVisit.setProductList(webserviceProductModels);
                    horizontalRecyclerViewAdapterMostVisit.notifyDataSetChanged();
                    break;
                }
                case Const.AMAZING_SUGGESTION_ORDER :{
                 //   horizontalRecyclerViewAdapterAmazingSuggestion.setProductList(amazingSuggestion);
                    horizontalRecyclerViewAdapterAmazingSuggestion.notifyDataSetChanged();
                    break;
                }
            }
        }
    }
}
