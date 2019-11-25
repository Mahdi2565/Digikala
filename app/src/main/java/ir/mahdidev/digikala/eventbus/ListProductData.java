package ir.mahdidev.digikala.eventbus;

public class ListProductData {
    private String toolbarTitle;
    private int categoryId ;
    private String orderBy;
    private String order;
    private String search;

    public ListProductData(String toolbarTitle, int categoryId, String orderBy, String order, String search) {
        this.toolbarTitle = toolbarTitle;
        this.categoryId = categoryId;
        this.orderBy = orderBy;
        this.order = order;
        this.search = search;
    }

    public ListProductData(String toolbarTitle, String orderBy, String order, String search) {
        this.toolbarTitle = toolbarTitle;
        this.orderBy = orderBy;
        this.order = order;
        this.search = search;
    }

    public String getToolbarTitle() {
        return toolbarTitle;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public String getOrder() {
        return order;
    }

    public String getSearch() {
        return search;
    }
}
