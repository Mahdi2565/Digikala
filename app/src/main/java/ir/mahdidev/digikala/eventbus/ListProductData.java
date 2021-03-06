package ir.mahdidev.digikala.eventbus;

import java.io.Serializable;
import java.util.List;

public class ListProductData implements Serializable {

    private String toolbarTitle;
    private int categoryId  ;
    private String orderBy;
    private String order;
    private String search;
    private String  attribute;
    private List<Integer>   attributeTerm;

    public ListProductData(String toolbarTitle, int categoryId, String orderBy, String order, String search) {
        this.toolbarTitle = toolbarTitle;
        this.categoryId = categoryId;
        this.orderBy = orderBy;
        this.order = order;
        this.search = search;
    }

    public ListProductData() {
    }

    public ListProductData(String toolbarTitle, String orderBy, String order, String search) {
        this.toolbarTitle = toolbarTitle;
        this.orderBy = orderBy;
        this.order = order;
        this.search = search;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public List<Integer> getAttributeTerm() {
        return attributeTerm;
    }

    public void setAttributeTerm(List<Integer> attributeTerm) {
        this.attributeTerm = attributeTerm;
    }

    public void setToolbarTitle(String toolbarTitle) {
        this.toolbarTitle = toolbarTitle;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public void setSearch(String search) {
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
