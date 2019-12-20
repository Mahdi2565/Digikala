package ir.mahdidev.digikala.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.networkmodel.order.WebServiceOrder;
import ir.mahdidev.digikala.util.MyApplication;

public class CustomerOrdersRecyclerViewAdapter extends RecyclerView.Adapter<CustomerOrdersRecyclerViewAdapter.ViewHolder> {

    private List<WebServiceOrder> webServiceOrderList;
    private Context context;

    public CustomerOrdersRecyclerViewAdapter(List<WebServiceOrder> webServiceOrderList, Context context) {
        this.webServiceOrderList = webServiceOrderList;
        this.context = context;
    }

    public void setWebServiceOrderList(List<WebServiceOrder> webServiceOrderList) {
        this.webServiceOrderList = new ArrayList<>();
        this.webServiceOrderList.addAll(webServiceOrderList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.customer_order_item , parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WebServiceOrder webServiceOrder = webServiceOrderList.get(position);
        String orderId = "شماره سفارش: " + webServiceOrder.getId();
        String orderState = "وضعیت سفارش: " + webServiceOrder.getStatus();
        String totalOrderPrice = "جمع مبلغ سفارش: " + MyApplication.getInstance().getPersianNumber(Double.parseDouble(webServiceOrder.getTotal()))+ " تومان";
        holder.orderId.setText(orderId);
        holder.orderAddress.setText(webServiceOrder.getBilling().getAddress1());
        holder.orderState.setText(orderState);
        holder.totalOrderPrice.setText(totalOrderPrice);
    }

    @Override
    public int getItemCount() {
        return webServiceOrderList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.order_id)
        TextView orderId;
        @BindView(R.id.order_address)
        TextView orderAddress;
        @BindView(R.id.order_state)
        TextView orderState;
        @BindView(R.id.total_order_price)
        TextView totalOrderPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }
}
