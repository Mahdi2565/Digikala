package ir.mahdidev.digikala.controller.fragment;

import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.networkmodel.comment.WebServiceCommentModel;
import ir.mahdidev.digikala.networkmodel.customer.WebServiceCustomerModel;
import ir.mahdidev.digikala.util.Const;
import ir.mahdidev.digikala.util.Pref;
import ir.mahdidev.digikala.viewmodel.ProductViewModel;

public class AddEditCommentDialogFragment extends DialogFragment {

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

    private WebServiceCustomerModel webServiceCustomerModel = new WebServiceCustomerModel();
    private int rateReview;
    private String reviewTxt;
    private ProductViewModel viewModel;
    private NavController navController;
    private int fragmentPosition;
    private WebServiceCommentModel webServiceCommentModel ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webServiceCustomerModel = Pref.getCustomerModelFromPref(getActivity());
        initViewModel();
        if (getArguments() !=null){
            fragmentPosition = getArguments().getInt(Const.BundleKey.COMMENT_FRAGMENT_POSITION);
            webServiceCommentModel = (WebServiceCommentModel) getArguments().getSerializable(Const.BundleKey.COMMENT_MODEL_BUNDLE_KEY);
        }
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
        setDataToViews();
        sendDataToServer();
    }

    private void setDataToViews() {
        if (fragmentPosition==Const.COMMENT_FRAGMENT_EDIT){
            nameReviewer.setText(webServiceCommentModel.getReviewer());
            emailReviewer.setText(webServiceCommentModel.getReviewer_email());
            rateReviewSeekBar.setProgress(webServiceCommentModel.getRating());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                reviewEdt.setText(Html.fromHtml(webServiceCommentModel.getReview(), Html.FROM_HTML_MODE_COMPACT));
            }else {
                reviewEdt.setText(Html.fromHtml(webServiceCommentModel.getReview()));
            }
        }
    }

    private void sendDataToServer() {
        registerComment.setOnClickListener(view -> {

            getDataFromUser();

            if (fragmentPosition==Const.COMMENT_FRAGMENT_ADD){
                webServiceCommentModel = new WebServiceCommentModel();
                setDataToModel();
                viewModel.sendCustomerComment(webServiceCommentModel).observe(AddEditCommentDialogFragment.this ,
                        webServiceCommentModel1 -> {
                            if (webServiceCommentModel1.getId()!=null){
                                Toast.makeText(getActivity() , getActivity().getResources().getString(R.string.publish_comment ), Toast.LENGTH_LONG).show();
                                navController.navigate(R.id.action_addCommentDialogFragment_to_commentsFragment);
                            }
                        });
            }else {
                setDataToModel();
                viewModel.updateComment(webServiceCommentModel).observe(AddEditCommentDialogFragment.this , commentEditResponse -> {
                    if (commentEditResponse.getId() == null){
                        Toast.makeText(getActivity() , getActivity().getResources().getString(R.string.cant_update_comment), Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getActivity() , getActivity().getResources().getString(R.string.update_comment), Toast.LENGTH_LONG).show();
                        navController.navigate(R.id.action_addCommentDialogFragment_to_commentsFragment);
                    }
                });
            }
        });
    }

    private void setDataToModel() {
        webServiceCommentModel.setProduct_id(viewModel.getProductIdMutableLiveData().getValue());
        webServiceCommentModel.setRating(rateReview);
        webServiceCommentModel.setReview(reviewTxt);
        webServiceCommentModel.setReviewer_email(webServiceCustomerModel.getEmail());
        webServiceCommentModel.setReviewer(webServiceCustomerModel.getFirstName() + " " + webServiceCustomerModel.getLastName());
        webServiceCommentModel.setStatus("approved");
        webServiceCommentModel.setVerified(true);
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
