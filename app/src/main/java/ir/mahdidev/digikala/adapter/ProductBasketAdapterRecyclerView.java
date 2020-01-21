package ir.mahdidev.digikala.adapter;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.database.ProductBasketModel;
import ir.mahdidev.digikala.database.ProductFavoriteModel;
import ir.mahdidev.digikala.util.MyApplication;

public class ProductBasketAdapterRecyclerView<T> extends RecyclerView.Adapter {

    private List<T> list;
    private Context context;

    public ProductBasketAdapterRecyclerView(List<T> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setProductBasketList(List<T> list) {
        this.list = new ArrayList<>();
        this.list.addAll(list);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.product_basket_item,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {
        Object object = list.get(position);
        ProductBasketAdapterRecyclerView.ViewHolder holder = (ViewHolder) holder1;
        if (object instanceof ProductBasketModel) {
            ProductBasketModel productBasketModel = (ProductBasketModel) object;
            Picasso.get().load(productBasketModel.getImageSrc())
                    .placeholder(R.drawable.digikala_place_holder)
                    .into(holder.productImg);
            holder.titleProduct.setText(productBasketModel.getTitleProduct());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                holder.shortDescriptionProduct.setText(Html.fromHtml(productBasketModel.getShortDescription(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                holder.shortDescriptionProduct.setText(Html.fromHtml(productBasketModel.getShortDescription()));
            }

            initProductCountSpinner(productBasketModel, holder);

            String regularPrice = MyApplication.getInstance()
                    .getPersianNumber(Double.parseDouble(productBasketModel.getPrice()))
                    + " تومان";
            holder.price.setText(regularPrice);
            if (!productBasketModel.getPrice()
                    .equals(productBasketModel.getFinalPrice())) {
                holder.discountPrice.setVisibility(View.VISIBLE);
                holder.amazingSuggestionTxt.setVisibility(View.VISIBLE);
                int price = Integer.parseInt(productBasketModel.getPrice());
                int finalPrice = Integer.parseInt(productBasketModel.getFinalPrice());
                String discountPrice = MyApplication.getInstance()
                        .getPersianNumber(Double.parseDouble(String.valueOf(price - finalPrice))) + " تومان";
                holder.discountPrice.setText(discountPrice);
            } else {
                holder.discountPrice.setVisibility(View.GONE);
                holder.amazingSuggestionTxt.setVisibility(View.GONE);
            }
            String finalPrice = MyApplication.getInstance()
                    .getPersianNumber(Double.parseDouble(productBasketModel.getFinalPrice()))
                    + " تومان";
            holder.finalePrice.setText(finalPrice);
            holder.deleteProduct.setOnClickListener(view -> {
                productBasketAdapterInterface.onDeleteProductClicked(productBasketModel);
            });
            holder.productImg.setOnClickListener(view -> {
                productBasketAdapterInterface.onProductPictureClicked(productBasketModel.getProductId());
            });
        } else if (object instanceof ProductFavoriteModel) {
            ProductFavoriteModel productFavorite = (ProductFavoriteModel) object;
            Picasso.get().load(productFavorite.getImageSrc())
                    .placeholder(R.drawable.digikala_place_holder)
                    .into(holder.productImg);
            holder.titleProduct.setText(productFavorite.getTitleProduct());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                holder.shortDescriptionProduct.setText(Html.fromHtml(productFavorite.getShortDescription(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                holder.shortDescriptionProduct.setText(Html.fromHtml(productFavorite.getShortDescription()));
            }
//            holder.productCountSpinner.setText(MyApplication.getInstance()
//                    .getPersianNumber(productFavorite.getProductCount()));
            String regularPrice = MyApplication.getInstance()
                    .getPersianNumber(Double.parseDouble(productFavorite.getPrice()))
                    + " تومان";
            holder.price.setText(regularPrice);
            if (!productFavorite.getPrice()
                    .equals(productFavorite.getFinalPrice())) {
                holder.discountPrice.setVisibility(View.VISIBLE);
                holder.amazingSuggestionTxt.setVisibility(View.VISIBLE);
                int price = Integer.parseInt(productFavorite.getPrice());
                int finalPrice = Integer.parseInt(productFavorite.getFinalPrice());
                String discountPrice = MyApplication.getInstance()
                        .getPersianNumber(Double.parseDouble(String.valueOf(price - finalPrice))) + " تومان";
                holder.discountPrice.setText(discountPrice);
            } else {
                holder.discountPrice.setVisibility(View.GONE);
                holder.amazingSuggestionTxt.setVisibility(View.GONE);
            }
            String finalPrice = MyApplication.getInstance()
                    .getPersianNumber(Double.parseDouble(productFavorite.getFinalPrice()))
                    + " تومان";
            holder.finalePrice.setText(finalPrice);
            holder.deleteProduct.setOnClickListener(view -> {
                productBasketAdapterInterface.onDeleteProductClicked(productFavorite);
            });
            holder.productImg.setOnClickListener(view -> {
                productBasketAdapterInterface.onProductPictureClicked(productFavorite.getProductId());
            });
        }
    }

    private void initProductCountSpinner(ProductBasketModel productBasketModel, ViewHolder holder) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.product_count, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.productCountSpinner.setAdapter(adapter);
        holder.productCountSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (productBasketModel.getProductCount() != i + 1) {
                    productBasketModel.setProductCount(i + 1);
                    productBasketAdapterInterface.onProductCountChange(productBasketModel);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        holder.productCountSpinner.setSelection(productBasketModel.getProductCount() - 1);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.product_img)
        ImageView productImg;
        @BindView(R.id.title_product)
        TextView titleProduct;
        @BindView(R.id.short_description_product)
        TextView shortDescriptionProduct;
        @BindView(R.id.product_count_spinner)
        Spinner productCountSpinner;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.discount_price_txt)
        TextView discountPrice;
        @BindView(R.id.amazing_suggestion_txt)
        TextView amazingSuggestionTxt;
        @BindView(R.id.final_price)
        TextView finalePrice;
        @BindView(R.id.delete_product)
        TextView deleteProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface ProductBasketAdapterInterface<T> {
        void onDeleteProductClicked(T model);

        void onProductPictureClicked(int productId);

        void onProductCountChange(ProductBasketModel productBasketModel);
    }

    private ProductBasketAdapterInterface productBasketAdapterInterface;

    public void setProductBasketAdapterInterface(ProductBasketAdapterInterface productBasketAdapterInterface) {
        this.productBasketAdapterInterface = productBasketAdapterInterface;
    }
}
