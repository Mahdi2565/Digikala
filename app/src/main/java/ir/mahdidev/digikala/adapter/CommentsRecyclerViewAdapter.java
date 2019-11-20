package ir.mahdidev.digikala.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.mahdidev.digikala.R;
import ir.mahdidev.digikala.networkmodel.comment.WebServiceCommentModel;

public class CommentsRecyclerViewAdapter extends RecyclerView.Adapter<CommentsRecyclerViewAdapter.ViewHolder> {

    private List<WebServiceCommentModel> commentList;
    private Context context;

    public CommentsRecyclerViewAdapter(List<WebServiceCommentModel> commentList, Context context) {
        this.commentList = commentList;
        this.context = context;
    }

    public void setCommentList(List<WebServiceCommentModel> commentList){
        this.commentList = new ArrayList<>();
        this.commentList.addAll(commentList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.comments_item , parent
        , false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.nameReviewer.setText(commentList.get(position).getReviewer());
        holder.dateReview.setText(commentList.get(position).getDate_created());
        holder.review.setText(commentList.get(position).getReview());
        holder.ratingBar.setRating(commentList.get(position).getRating());

    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.name_reviewer)
        TextView nameReviewer;
        @BindView(R.id.date_review)
        TextView dateReview;
        @BindView(R.id.review_txt)
        TextView review;
        @BindView(R.id.comment_rating_bar)
        AppCompatRatingBar ratingBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }
}
