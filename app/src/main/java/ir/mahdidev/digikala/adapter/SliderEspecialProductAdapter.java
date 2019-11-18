package ir.mahdidev.digikala.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.networkmodel.product.WebserviceProductModel;

public class SliderEspecialProductAdapter  extends SliderViewAdapter<SliderEspecialProductAdapter.SliderViewHolder> {

    private List<WebserviceProductModel> productList;
    private Context context;

    public SliderEspecialProductAdapter(List<WebserviceProductModel> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @Override
    public SliderViewHolder onCreateViewHolder(ViewGroup parent) {
        return new SliderEspecialProductAdapter.SliderViewHolder(LayoutInflater.from(context).inflate(R.layout.image_slider_layout_item ,
                parent ,false));
    }

    @Override
    public void onBindViewHolder(SliderViewHolder viewHolder, int position) {
        Picasso.get().load(productList.get(position).getImages().get(0).getSrc())
                .placeholder(R.drawable.digikala_place_holder).into(viewHolder.imageViewBackground);
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    public class SliderViewHolder extends SliderViewAdapter.ViewHolder{
        private ImageView imageViewBackground;
        public SliderViewHolder(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
        }
    }
}
