package ir.mahdidev.digikala.controller.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.adapter.CommentsRecyclerViewAdapter;
import ir.mahdidev.digikala.networkmodel.comment.WebServiceCommentModel;
import ir.mahdidev.digikala.networkmodel.customer.WebServiceCustomerModel;
import ir.mahdidev.digikala.util.Const;
import ir.mahdidev.digikala.util.Pref;
import ir.mahdidev.digikala.viewmodel.ProductViewModel;
import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentsFragment extends Fragment {


    @OnClick(R.id.back_toolbar)
    void onBackClicked(){
        navController.navigateUp();
    }
    @BindView(R.id.comments_recyclerView)
    RecyclerView commentRecyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.empty_comment)
    TextView emptyComment;
    @BindView(R.id.parent_relative)
    RelativeLayout parentRelative ;
    @BindView(R.id.add_comment_fab)
    FloatingActionButton addCommentFab;
    public CommentsFragment() {
    }
    private NavController navController;
    private ProductViewModel viewModel;
    private CommentsRecyclerViewAdapter commentsRecyclerViewAdapter;
    private WebServiceCustomerModel webServiceCustomerModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getCustomerModel();
    }

    private void getCustomerModel() {
        webServiceCustomerModel = Pref.getCustomerModelFromPref(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comments, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        ButterKnife.bind(this , view);
        canWriteComment();
        initViewModel();

    }

    private void canWriteComment() {
        if (webServiceCustomerModel ==null)
            addCommentFab.hide();
        else
            addCommentFab.show();

        addCommentFab.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt(Const.BundleKey.COMMENT_FRAGMENT_POSITION , Const.COMMENT_FRAGMENT_ADD);
            navController.navigate(R.id.action_commentsFragment_to_addCommentDialogFragment , bundle);
        });
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        viewModel.getCommentsProduct(viewModel.getProductIdMutableLiveData().getValue()).observe(this, webServiceCommentModels -> {
            if (webServiceCommentModels.isEmpty()){
                emptyComment.setVisibility(View.VISIBLE);
                parentRelative.setVisibility(View.GONE);
            }else {
                emptyComment.setVisibility(View.GONE);
            }
            progressBar.setVisibility(View.GONE);
          initRecyclerView(webServiceCommentModels);
        });
    }

    private void initRecyclerView(List<WebServiceCommentModel> webServiceCommentModels) {
        if (commentsRecyclerViewAdapter == null){
            commentsRecyclerViewAdapter = new CommentsRecyclerViewAdapter(webServiceCommentModels ,
                    getActivity() , webServiceCustomerModel);
        }else {
            commentsRecyclerViewAdapter.setCommentList(webServiceCommentModels);
            commentsRecyclerViewAdapter.notifyDataSetChanged();
        }
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        commentRecyclerView.setAdapter(commentsRecyclerViewAdapter);
        commentsRecyclerViewAdapter.setCommentRecyclerViewInterface(new CommentsRecyclerViewAdapter.CommentRecyclerViewInterface() {
            @Override
            public void onDeleteClicked(int id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle(getActivity().getString(R.string.want_delete_comment));
                alert.setPositiveButton("بله", (dialogInterface, i) -> {
                    viewModel.deleteCustomerComment(id).observe(CommentsFragment.this, webServiceCommentModel -> {
                            if (webServiceCommentModel.getId() != null){
                                Toast.makeText(getActivity() , getActivity().getString(R.string.delete_your_comment),Toast.LENGTH_LONG).show();
                                navController.navigate(R.id.action_commentsFragment_self);
                            }else {
                                dialogInterface.cancel();
                                Toast.makeText(getActivity() , getActivity().getString(R.string.cant_delete_comment),Toast.LENGTH_LONG).show();
                            }

                    });
                });
                alert.setNegativeButton("خیر" , (dialogInterface, i) -> {
                    dialogInterface.cancel();
                }) ;
                alert.show();
            }

            @Override
            public void onEditClicked(WebServiceCommentModel webServiceCommentModel) {
                Bundle bundle = new Bundle();
                bundle.putInt(Const.BundleKey.COMMENT_FRAGMENT_POSITION , Const.COMMENT_FRAGMENT_EDIT);
                bundle.putSerializable(Const.BundleKey.COMMENT_MODEL_BUNDLE_KEY , webServiceCommentModel);
                navController.navigate(R.id.action_commentsFragment_to_addCommentDialogFragment , bundle);            }
        });
    }
}
