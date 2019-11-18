package ir.mahdidev.digikala.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.networkmodel.product.WebserviceProductModel;

public class SliderProductAdapter  extends SliderViewAdapter<SliderProductAdapter.SliderProductViewHolder> {

    private WebserviceProductModel webserviceProductModel;
    private Context context;

    public SliderProductAdapter(WebserviceProductModel webserviceProductModel, Context context) {
        this.webserviceProductModel = webserviceProductModel;
        this.context = context;
    }

    @Override
    public SliderProductViewHolder onCreateViewHolder(ViewGroup parent) {
        return new SliderProductViewHolder(LayoutInflater.from(context).inflate(R.layout.image_slider_layout_item ,
                parent ,false));
    }

    @Override
    public void onBindViewHolder(SliderProductViewHolder viewHolder, int position) {
        Picasso.get().load(webserviceProductModel.getImages().get(position).getSrc())
                .placeholder(R.drawable.digikala_place_holder)
                .into(viewHolder.imageViewBackground);
    }

    @Override
    public int getCount() {
        return webserviceProductModel.getImages().size();
    }

    public class SliderProductViewHolder extends SliderViewAdapter.ViewHolder {
        private ImageView imageViewBackground;
        public SliderProductViewHolder(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
        }
    }
}
