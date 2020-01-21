package ir.mahdidev.digikala.controller.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.eventbus.ListProductData;
import ir.mahdidev.digikala.util.Const;

public class SortProductDialogFragment extends DialogFragment implements View.OnClickListener {

    @BindView(R.id.radio_rating)
    RadioButton radioRating;
    @BindView(R.id.radio_visiting)
    RadioButton radioVisiting;
    @BindView(R.id.radio_newest)
    RadioButton radioNewest;
    @BindView(R.id.radio_price_desc)
    RadioButton radioDesc;
    @BindView(R.id.radio_price_asc)
    RadioButton radioAsc;

    private NavController navController;
    private ListProductData listProductData;
    public static int radioChecked = 4;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() !=null){
            listProductData = (ListProductData) getArguments().getSerializable(Const.BundleKey.PRODUCT_LIST_DATA_TO_SORT_FRAGMENT_BUNDLE_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sort_product_dialog_fragment , container , false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this , view);
        navController = NavHostFragment.findNavController(this);
        onClickRadio();
    }

    private void onClickRadio() {
        switch (radioChecked){
            case 0 :{
                radioAsc.setChecked(true);
                break;
            }
            case 1:{
                radioDesc.setChecked(true);
                break;
            }
            case 2:{
                radioVisiting.setChecked(true);
                break;
            }
            case 3:{
                radioRating.setChecked(true);
                break;
            }
            case 4:{
                radioNewest.setChecked(true);
                break;
            }
        }
        radioAsc.setOnClickListener(this);
        radioDesc.setOnClickListener(this);
        radioVisiting.setOnClickListener(this);
        radioRating.setOnClickListener(this);
        radioNewest.setOnClickListener(this);
    }
    private void setDataToList(ListProductData listProductData, int p, String desc, String orderBy) {
        listProductData.setOrder(desc);
        listProductData.setOrderBy(orderBy);
    }

    @Override
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Const.BundleKey.PRODUCT_LIST_DATA_BUNDLE_KEY , listProductData);
        switch (view.getId()){
            case R.id.radio_price_asc :{
                radioChecked = 0;
                setDataToList(listProductData, R.string.price_asc, "asc", Const.OrderTag.PRICE_PRODUCT);
                break;
            }
            case R.id.radio_price_desc:{
                radioChecked = 1;
                setDataToList(listProductData, R.string.price_desc, "desc", Const.OrderTag.PRICE_PRODUCT);
                break;
            }

            case R.id.radio_visiting: {
                radioChecked = 2;
                setDataToList(listProductData, R.string.most_visiting, "desc", Const.OrderTag.MOST_VISITING_PRODUCT);
                break;
            }
            case R.id.radio_rating: {
                radioChecked = 3;
                setDataToList(listProductData, R.string.most_rating, "desc", Const.OrderTag.MOST_RATING_PRODUCT);
                break;
            }
            case R.id.radio_newest: {
                radioChecked = 4;
                setDataToList(listProductData, R.string.most_newest, "desc", Const.OrderTag.MOST_NEWEST_PRODUCT);
                break;
            }

        }
        navController.navigate(R.id.action_sortProductDialogFragment_to_productsListFragment , bundle);
    }
}
