
package ir.mahdidev.digikala.networkmodel.address;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WebServiceAddress {

    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("postal_address")
    @Expose
    private String postalAddress;
    @SerializedName("address_compact")
    @Expose
    private String addressCompact;
    @SerializedName("primary")
    @Expose
    private String primary;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("poi")
    @Expose
    private String poi;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("province")
    @Expose
    private String province;
    @SerializedName("county")
    @Expose
    private String county;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("rural_district")
    @Expose
    private String ruralDistrict;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("village")
    @Expose
    private String village;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("neighbourhood")
    @Expose
    private String neighbourhood;
    @SerializedName("last")
    @Expose
    private String last;
    @SerializedName("plaque")
    @Expose
    private String plaque;
    @SerializedName("postal_code")
    @Expose
    private String postalCode;
    @SerializedName("geom")
    @Expose
    private Geom geom;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getAddressCompact() {
        return addressCompact;
    }

    public void setAddressCompact(String addressCompact) {
        this.addressCompact = addressCompact;
    }

    public String getPrimary() {
        return primary;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoi() {
        return poi;
    }

    public void setPoi(String poi) {
        this.poi = poi;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getRuralDistrict() {
        return ruralDistrict;
    }

    public void setRuralDistrict(String ruralDistrict) {
        this.ruralDistrict = ruralDistrict;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getPlaque() {
        return plaque;
    }

    public void setPlaque(String plaque) {
        this.plaque = plaque;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Geom getGeom() {
        return geom;
    }

    public void setGeom(Geom geom) {
        this.geom = geom;
    }

}
