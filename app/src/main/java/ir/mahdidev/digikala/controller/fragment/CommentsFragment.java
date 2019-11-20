package ir.mahdidev.digikala.controller.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.adapter.CommentsRecyclerViewAdapter;
import ir.mahdidev.digikala.networkmodel.comment.WebServiceCommentModel;
import ir.mahdidev.digikala.util.Const;
import ir.mahdidev.digikala.viewmodel.ProductViewModel;

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

    public CommentsFragment() {
    }

    private int productId;
    private NavController navController;
    private ProductViewModel viewModel;
    private CommentsRecyclerViewAdapter commentsRecyclerViewAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() !=null){
            productId = getArguments().getInt(Const.BundleKey.PRODUCT_ID_COMMENT );
        }
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
        initViewModel();
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        viewModel.getCommentsProduct(productId).observe(this, webServiceCommentModels -> {
            if (webServiceCommentModels.isEmpty()){
                emptyComment.setVisibility(View.VISIBLE);
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
                    getActivity());
            commentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            commentRecyclerView.setAdapter(commentsRecyclerViewAdapter);
        }else {
            commentsRecyclerViewAdapter.setCommentList(webServiceCommentModels);
            commentsRecyclerViewAdapter.notifyDataSetChanged();
        }
    }
}
