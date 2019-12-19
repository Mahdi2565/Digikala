package ir.mahdidev.digikala.networkmodel.comment;

public class WebServiceCommentModel {

    private Integer id;
    private String date_created;
    private String date_created_gmt;
    private int product_id;
    private String status;
    private String reviewer;
    private String reviewer_email;
    private String review;
    private int rating;
    private boolean verified;

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public void setReviewer_email(String reviewer_email) {
        this.reviewer_email = reviewer_email;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Integer getId() {
        return id;
    }

    public String getDate_created() {
        return date_created;
    }

    public String getDate_created_gmt() {
        return date_created_gmt;
    }

    public int getProduct_id() {
        return product_id;
    }

    public String getStatus() {
        return status;
    }

    public String getReviewer() {
        return reviewer;
    }

    public String getReviewer_email() {
        return reviewer_email;
    }

    public String getReview() {
        return review;
    }

    public int getRating() {
        return rating;
    }

    public boolean isVerified() {
        return verified;
    }
}
