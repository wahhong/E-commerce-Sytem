package model;

import java.math.BigInteger;

public class Delivery {
    private int deliveryID;
    private BigInteger orderID;
    private String userName;
    private String userPhone;
    private String orderAddress;
    private Integer riderID;
    private String deliveryStatus;
    private String orderShippingDate;
    private String orderCompleteDate;

    public Delivery() {}

    public Delivery(String userName, String userPhone, String orderAddress, String deliveryStatus) {
        this.userName = userName;
        this.userPhone = userPhone;
        this.orderAddress = orderAddress;
        this.deliveryStatus = deliveryStatus;
    }
    
    public Delivery(BigInteger orderID, String userName, String userPhone, String orderAddress, int riderID, String deliveryStatus, String orderShippingDate, String orderCompleteDate) {
        this.orderID = orderID;
        this.userName = userName;
        this.userPhone = userPhone;
        this.orderAddress = orderAddress;
        this.riderID = riderID;
        this.deliveryStatus = deliveryStatus;
        this.orderShippingDate = orderShippingDate;
        this.orderCompleteDate = orderCompleteDate;
    }

    public int getDeliveryID() {
        return deliveryID;
    }

    public void setDeliveryID(int deliveryID) {
        this.deliveryID = deliveryID;
    }

    public BigInteger getOrderID() {
        return orderID;
    }

    public void setOrderID(BigInteger orderID) {
        this.orderID = orderID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public int getRiderID() {
        return riderID;
    }

    public void setRiderID(int riderID) {
        this.riderID = riderID;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getOrderShippingDate() {
        return orderShippingDate;
    }

    public void setOrderShippingDate(String orderShippingDate) {
        this.orderShippingDate = orderShippingDate;
    }

    public String getOrderCompleteDate() {
        return orderCompleteDate;
    }

    public void setOrderCompleteDate(String orderCompleteDate) {
        this.orderCompleteDate = orderCompleteDate;
    }
}
