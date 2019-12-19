package ir.mahdidev.digikala.controller.fragment;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.networkmodel.comment.WebServiceCommentModel;
import ir.mahdidev.digikala.networkmodel.customer.WebServiceCustomerModel;
import ir.mahdidev.digikala.util.Pref;
import ir.mahdidev.digikala.viewmodel.ProductViewModel;

public class AddCommentDialogFragment extends DialogFragment {

    @BindView(R.id.name_reviewer)
    TextView nameReviewer;
    @BindView(R.id.email_reviewer)
    TextView emailReviewer;
    @BindView(R.id.rate_review)
    SeekBar rateReviewSeekBar;
    @BindView(R.id.review_edt)
    TextInputEditText reviewEdt;
    @BindView(R.id.register_review)
    MaterialButton registerComment;

    private WebServiceCustomerModel webServiceCustomerModel;
    private int rateReview;
    private String reviewTxt;
    private ProductViewModel viewModel;
    private NavController navController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webServiceCustomerModel = Pref.getCustomerModelFromPref(getActivity());
        initViewModel();
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_comment_dialog_fragment , container ,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().getContext().getTheme().applyStyle(R.style.MyAlertDialog , true);
        ButterKnife.bind(this , view);
        navController = NavHostFragment.findNavController(this);
        getDataFromUser();
        sendDataToServer();
    }

    private void sendDataToServer() {
        registerComment.setOnClickListener(view -> {
            getDataFromUser();
            WebServiceCommentModel webServiceCommentModel = new WebServiceCommentModel();
            webServiceCommentModel.setProduct_id(viewModel.getProductIdMutableLiveData().getValue());
            webServiceCommentModel.setRating(rateReview);
            webServiceCommentModel.setReview(reviewTxt);
            webServiceCommentModel.setReviewer_email(webServiceCustomerModel.getEmail());
            webServiceCommentModel.setReviewer(webServiceCustomerModel.getFirstName() + " " + webServiceCustomerModel.getLastName());
            webServiceCommentModel.setStatus("approved");
            webServiceCommentModel.setVerified(true);
           viewModel.sendCustomerComment(webServiceCommentModel).observe(AddCommentDialogFragment.this ,
                   webServiceCommentModel1 -> {
               if (webServiceCommentModel1.getId()!=null){
                   navController.navigate(R.id.action_addCommentDialogFragment_to_commentsFragment);
               }
           });
        });
    }

    private void getDataFromUser() {
        String customerName = webServiceCustomerModel.getFirstName() + " " + webServiceCustomerModel.getLastName();
        String customerEmail = webServiceCustomerModel.getEmail();
        nameReviewer.setText(customerName);
        emailReviewer.setText(customerEmail);
        rateReview = rateReviewSeekBar.getProgress();
        reviewTxt = reviewEdt.getText().toString().trim();
    }

    public void onResume() {
        Window window = getDialog().getWindow();
        Point size = new Point();
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        window.setLayout((int) (size.x * 0.80), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        super.onResume();
    }

}
