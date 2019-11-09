package ir.mahdidev.digikala.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.networkmodel.product.WebserviceProductModel;

public class MainHorizontalRecyclerViewAdapter extends RecyclerView.Adapter<MainHorizontalRecyclerViewAdapter.ViewHolder> {
    private static final int PRODUCT_WITHOUT_DISCOUNT = 0 ;
    private static final int PRODUCT_WITH_DISCOUNT = 1 ;
    private List<WebserviceProductModel> productList;
    private Context context;

    public MainHorizontalRecyclerViewAdapter(List<WebserviceProductModel> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.horizontal_recyclerview_item_discount_price
                        , parent , false));
        }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (productList.get(position).getPrice().equals("")) productList.remove(position);

                if (productList.get(position).getImages().isEmpty()){
                    holder.producImage.setImageResource(R.drawable.digikala_place_holder);
                }else {
                    Picasso.get().load(productList.get(position).getImages().get(0).getSrc())
                            .placeholder(R.drawable.digikala_place_holder)
                            .into(holder.producImage);
                }
                if (!productList.get(position).getRegularPrice().equals(productList.get(position).getPrice())) {
                    holder.priceRegular.setVisibility(View.VISIBLE);
                }else holder.priceRegular.setVisibility(View.GONE);
                holder.titleProduct.setText(productList.get(position).getName());
                String regularPrice = productList.get(position).getRegularPrice()+ " تومان";
                holder.priceRegular.setText(regularPrice);
                String price = productList.get(position).getPrice()+ " تومان";
                holder.salePrice.setText(price);


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView producImage;
        private TextView  titleProduct;
        private TextView  priceRegular;
        private TextView  salePrice;
        public ViewHolder(@NonNull View itemView ) {
            super(itemView);
                    producImage = itemView.findViewById(R.id.product_img_horizontal_recyclerView);
                    titleProduct = itemView.findViewById(R.id.title_product_horizontal_recyclerView);
                    priceRegular = itemView.findViewById(R.id.price_regular);
                    salePrice    = itemView.findViewById(R.id.sale_price);
            }
        }
    }