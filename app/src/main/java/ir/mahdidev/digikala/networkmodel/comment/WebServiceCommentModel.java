package ir.mahdidev.digikala.networkmodel.comment;

public class WebServiceCommentModel {

    private int id;
    private String date_created;
    private String date_created_gmt;
    private int product_id;
    private String status;
    private String reviewer;
    private String reviewer_email;
    private String review;
    private int rating;
    private boolean verified;

    public int getId() {
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
