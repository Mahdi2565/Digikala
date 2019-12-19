
package ir.mahdidev.digikala.networkmodel.attribute;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WebServiceAttribute {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("order_by")
    @Expose
    private String orderBy;
    @SerializedName("has_archives")
    @Expose
    private Boolean hasArchives;
    @SerializedName("_links")
    @Expose
    private Links links;

    private boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public Boolean getHasArchives() {
        return hasArchives;
    }

    public void setHasArchives(Boolean hasArchives) {
        this.hasArchives = hasArchives;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

}
