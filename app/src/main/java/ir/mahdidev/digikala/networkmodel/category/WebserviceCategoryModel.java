
package ir.mahdidev.digikala.networkmodel.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WebserviceCategoryModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("parent")
    @Expose
    private Integer parent;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("display")
    @Expose
    private String display;
    @SerializedName("image")
    @Expose
    private Image image;
    @SerializedName("menu_order")
    @Expose
    private Integer menuOrder;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("_links")
    @Expose
    private Links links;

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

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public Image getImage() {
        if (image==null) image= new Image();
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Integer getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(Integer menuOrder) {
        this.menuOrder = menuOrder;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

}
