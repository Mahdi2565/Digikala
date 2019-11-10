package ir.mahdidev.digikala.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.networkmodel.category.WebserviceCategoryModel;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderViewHolder> {

    private List<WebserviceCategoryModel> categoryList;
    private Context context;

    public SliderAdapter(List<WebserviceCategoryModel> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;
        for (int i = 0 ; i <this.categoryList.size() ; i++){
            if (categoryList.get(i).getDescription().isEmpty()) this.categoryList.remove(i);
        }
    }

    @Override
    public SliderViewHolder onCreateViewHolder(ViewGroup parent) {
        return new SliderViewHolder(LayoutInflater.from(context ).inflate(R.layout.image_slider_layout_item ,
                parent ,false));
    }

    @Override
    public void onBindViewHolder(SliderViewHolder viewHolder, int position) {
        Picasso.get().load(categoryList.get(position).getImage().getSrc())
                .placeholder(R.drawable.digikala_place_holder)
                .into(viewHolder.imageViewBackground);
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    public class SliderViewHolder extends SliderViewAdapter.ViewHolder{
        private ImageView imageViewBackground;
        private TextView textViewDescription;
        public SliderViewHolder(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
        }
    }
}
