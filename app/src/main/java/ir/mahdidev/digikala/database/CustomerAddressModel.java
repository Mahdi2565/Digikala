package ir.mahdidev.digikala.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "address_customer")
public class CustomerAddressModel {

    public CustomerAddressModel(int customerId, String titleAddress, String firstName, String lastName, String customerAddress, String mapAddress, String phoneNumber, String postalCode, String latitude, String longitude, String city, String state) {
        this.customerId = customerId;
        this.titleAddress = titleAddress;
        this.firstName = firstName;
        this.lastName = lastName;
        this.customerAddress = customerAddress;
        this.mapAddress = mapAddress;
        this.phoneNumber = phoneNumber;
        this.postalCode = postalCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
        this.province = state;
    }

    public CustomerAddressModel() {
    }

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int customerId;
    private String titleAddress;
    private String firstName;
    private String lastName;
    private String customerAddress;
    private String mapAddress;
    private String phoneNumber;
    private String postalCode;
    private String latitude;
    private String longitude;
    private String city;
    private String province;

    @Ignore
    private boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getMapAddress() {
        return mapAddress;
    }

    public void setMapAddress(String mapAddress) {
        this.mapAddress = mapAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getTitleAddress() {
        return titleAddress;
    }

    public void setTitleAddress(String titleAddress) {
        this.titleAddress = titleAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
