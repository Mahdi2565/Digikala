package ir.mahdidev.digikala.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.networkmodel.attribute.WebServiceAttribute;

public class AttributeRecyclerViewAdapter extends RecyclerView.Adapter<AttributeRecyclerViewAdapter.ViewHolder> {

    private List<WebServiceAttribute> attributeList;
    private Context context;
    private int checkedPosition = -1;

    public AttributeRecyclerViewAdapter(List<WebServiceAttribute> attributeList, Context context) {
        this.attributeList = attributeList;
        this.context = context;
    }

    public void setAttributeList(List<WebServiceAttribute> attributeList) {
        this.attributeList = new ArrayList<>();
        this.attributeList.addAll(attributeList);
    }

    public WebServiceAttribute getAttributeModel() {
        if (checkedPosition != -1){
            return attributeList.get(checkedPosition);
        }
        return null;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.attribute_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WebServiceAttribute attribute = attributeList.get(position);
        holder.attributeText.setText(attribute.getName());
        if (checkedPosition == -1){
            selectAttribute(holder, context.getResources().getColor(R.color.attributeBackgroundColor), R.color.white);
        }else {
            if (checkedPosition == position){
                selectAttribute(holder, Color.WHITE, R.color.attributeBackgroundColor);

            }else {
                selectAttribute(holder, context.getResources().getColor(R.color.attributeBackgroundColor), R.color.white);
            }
        }
        holder.parentAttribute.setOnClickListener(view -> {
            attributeAdapterInterface.onAttributeClicked(attribute.getId());

            selectAttribute(holder, Color.WHITE, R.color.attributeBackgroundColor);

            if (checkedPosition != position){
                notifyItemChanged(checkedPosition);
                checkedPosition = position;
            }

        });
    }

    private void selectAttribute(@NonNull ViewHolder holder, int color, int p) {
        holder.parentAttribute.setBackgroundColor(color);
        holder.attributeText.setTextColor(context.getResources().getColor(p));
    }

    @Override
    public int getItemCount() {
        return attributeList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.attribute_textView)
        TextView attributeText;
        @BindView(R.id.parent_attribute)
        LinearLayout parentAttribute;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
            parentAttribute.setOnClickListener(view ->{
                WebServiceAttribute attribute = attributeList.get(getAdapterPosition());

                checkedPosition = getAdapterPosition();
                notifyDataSetChanged();
                attributeAdapterInterface.onAttributeClicked(attribute.getId());

                attribute.setSelected(!attribute.isSelected());
                parentAttribute.setBackgroundColor(attribute.isSelected() ? Color.WHITE :
                        context.getResources().getColor(R.color.attributeBackgroundColor));
                attributeText.setTextColor(attribute.isSelected()? context.getResources().getColor(R.color.attributeBackgroundColor):
                        context.getResources().getColor(R.color.white));
                checkedPosition =getAdapterPosition();
            });
        }
    }
    public interface AttributeAdapterInterface{
        void onAttributeClicked(int attributeId);
    }
    private AttributeAdapterInterface attributeAdapterInterface;
    public void setAttributeAdapterInterface (AttributeAdapterInterface attributeAdapterInterface){
        this.attributeAdapterInterface = attributeAdapterInterface;
    }
}
