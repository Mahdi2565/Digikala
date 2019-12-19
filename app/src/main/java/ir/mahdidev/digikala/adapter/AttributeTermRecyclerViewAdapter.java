package ir.mahdidev.digikala.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.networkmodel.attributeterm.WebServiceAttributeTerm;

public class AttributeTermRecyclerViewAdapter extends RecyclerView.Adapter<AttributeTermRecyclerViewAdapter.ViewHolder> {

    private List<WebServiceAttributeTerm> attributeTermList;
    private Context context;

    public AttributeTermRecyclerViewAdapter(List<WebServiceAttributeTerm> attributeTermList, Context context) {
        this.attributeTermList = attributeTermList;
        this.context = context;
    }

    public void setAttributeTermList(List<WebServiceAttributeTerm> attributeTermList) {
        this.attributeTermList = new ArrayList<>();
        this.attributeTermList.addAll(attributeTermList);
    }

    public List<WebServiceAttributeTerm> getAttributeTermList() {
        return attributeTermList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.attribute_term_item , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WebServiceAttributeTerm attributeTerm = attributeTermList.get(position);
        holder.attributeTermTextView.setText(attributeTerm.getName());
        holder.attributeTermChecked.setChecked(attributeTerm.isSelected());
        holder.parentAttribute.setOnClickListener(view -> {
            holder.attributeTermChecked.setChecked(!attributeTerm.isSelected());
            attributeTerm.setSelected(!attributeTerm.isSelected());
        });
    }

    @Override
    public int getItemCount() {
        return attributeTermList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.attribute_term_textView)
        TextView attributeTermTextView;
        @BindView(R.id.attribute_term_checked)
        CheckBox attributeTermChecked;
        @BindView(R.id.parent_attribute_term)
        RelativeLayout parentAttribute;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
