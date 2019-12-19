package ir.mahdidev.digikala.adapter;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import ir.mahdidev.digikala.networkmodel.customer.WebServiceCustomerModel;
import retrofit2.http.Body;

public class CommentsRecyclerViewAdapter extends RecyclerView.Adapter<CommentsRecyclerViewAdapter.ViewHolder> {

    private List<WebServiceCommentModel> commentList;
    private Context context;
    private WebServiceCustomerModel webServiceCustomerModel;

    public CommentsRecyclerViewAdapter(List<WebServiceCommentModel> commentList, Context context, WebServiceCustomerModel webServiceCustomerModel) {
        this.commentList = commentList;
        this.context = context;
        this.webServiceCustomerModel = webServiceCustomerModel;
    }

    public void setCommentList(List<WebServiceCommentModel> commentList){
        this.commentList = new ArrayList<>();
        this.commentList.addAll(commentList);
    }

    public void addNewComment(WebServiceCommentModel webServiceCommentModel){
        this.commentList.add(webServiceCommentModel);
        notifyDataSetChanged();
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.review.setText(Html.fromHtml(commentList.get(position).getReview(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.review.setText(Html.fromHtml(commentList.get(position).getReview()));
        }
        String ratingCommentTxt = "امتیاز: " + commentList.get(position).getRating() + " از 5";
        holder.ratingComment.setText(ratingCommentTxt);
        if (webServiceCustomerModel !=null){
            if (webServiceCustomerModel.getEmail().equals(commentList.get(position).getReviewer_email())){
                holder.editComment.setVisibility(View.VISIBLE);
                holder.deleteCommnet.setVisibility(View.VISIBLE);
            }else {
                holder.editComment.setVisibility(View.INVISIBLE);
                holder.deleteCommnet.setVisibility(View.INVISIBLE);
            }
        }

        holder.deleteCommnet.setOnClickListener(view -> {
            commentRecyclerViewInterface.onDeleteClicked(commentList.get(position).getId());
        });
        holder.editComment.setOnClickListener(view -> {
            commentRecyclerViewInterface.onEditClicked(commentList.get(position));
        });

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
        @BindView(R.id.rating_comment)
        TextView ratingComment;
        @BindView(R.id.edit_comment)
        ImageView editComment;
        @BindView(R.id.delete_comment)
        ImageView deleteCommnet;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }
    public interface CommentRecyclerViewInterface{
        void onDeleteClicked(int id);
        void onEditClicked(WebServiceCommentModel webServiceCommentModel);
    }
    public CommentRecyclerViewInterface commentRecyclerViewInterface;
    public void setCommentRecyclerViewInterface(CommentRecyclerViewInterface commentRecyclerViewInterface){
        this.commentRecyclerViewInterface = commentRecyclerViewInterface;
    }
}
