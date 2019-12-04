package ir.mahdidev.digikala.adapter;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.eventbus.OnProductClickedMessage;
import ir.mahdidev.digikala.networkmodel.product.WebserviceProductModel;
import ir.mahdidev.digikala.util.MyApplication;

public class ProductsListRecyclerViewAdapter extends RecyclerView.Adapter<ProductsListRecyclerViewAdapter.ViewHolder> {

    private List<WebserviceProductModel> productsList;
    private Context context;

    public ProductsListRecyclerViewAdapter(List<WebserviceProductModel> productsList, Context context) {
        this.productsList = productsList;
        this.context = context;
    }
    public void setProductList(List<WebserviceProductModel> productsList ) {
        this.productsList.addAll(productsList);
    }

    public List<WebserviceProductModel> getProductsList() {
        return productsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.products_list_item ,
                parent ,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(productsList.get(position).getImages().get(0).getSrc()).
                placeholder(R.drawable.digikala_place_holder).into(holder.productImage);

        holder.titleProduct.setText(productsList.get(position).getName());
        if (!productsList.get(position).getShortDescription().isEmpty()){
            holder.shortDescription.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                holder.shortDescription.setText(Html.fromHtml(productsList.get(position).getShortDescription(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                holder.shortDescription.setText(Html.fromHtml(productsList.get(position).getShortDescription()));
            }
        }else holder.shortDescription.setVisibility(View.INVISIBLE);

        if (!productsList.get(position).getRegularPrice().equals(productsList.get(position).getPrice())) {
            holder.regularPrice.setVisibility(View.VISIBLE);
            holder.amazingSuggestionLable.setVisibility(View.VISIBLE);
        }else{
            holder.regularPrice.setVisibility(View.INVISIBLE);
            holder.amazingSuggestionLable.setVisibility(View.GONE);
        }

        holder.titleProduct.setText(productsList.get(position).getName());
        String regularPrice = MyApplication.getInstance()
                .getPersianNumber(Double.parseDouble(productsList.get(position).getRegularPrice()))
                + " تومان";
        holder.regularPrice.setText(regularPrice);
        String price =  MyApplication.getInstance()
                .getPersianNumber(Double.parseDouble(productsList.get(position).getPrice()))
                + " تومان";
        holder.salePrice.setText(price);

        holder.productCardView.setOnClickListener(view -> {
            EventBus.getDefault()
                    .post(new OnProductClickedMessage(productsList.get(position).getId()));
        });

    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title_product)
        TextView titleProduct;
        @BindView(R.id.short_description)
        TextView shortDescription;
        @BindView(R.id.price_regular)
        TextView regularPrice;
        @BindView(R.id.sale_price)
        TextView salePrice;
        @BindView(R.id.product_img)
        ImageView productImage;
        @BindView(R.id.amazing_suggestion_label)
        ImageView amazingSuggestionLable;
        @BindView(R.id.product_cardView)
        CardView productCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }
}
