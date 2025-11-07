/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author chanw
 */
public class Rider {
    int riderID;
    String riderName;
    String riderPlateNumber;
    String riderPhone;

    public Rider() {
    }

    public Rider(int riderID, String riderName, String riderPlateNumber, String riderPhone) {
        this.riderID = riderID;
        this.riderName = riderName;
        this.riderPlateNumber = riderPlateNumber;
        this.riderPhone = riderPhone;
    }

    public int getRiderID() {
        return riderID;
    }

    public void setRiderID(int riderID) {
        this.riderID = riderID;
    }

    public String getRiderName() {
        return riderName;
    }

    public void setRiderName(String riderName) {
        this.riderName = riderName;
    }

    public String getRiderPlateNumber() {
        return riderPlateNumber;
    }

    public void setRiderPlateNumber(String riderPlateNumber) {
        this.riderPlateNumber = riderPlateNumber;
    }

    public String getRiderPhone() {
        return riderPhone;
    }

    public void setRiderPhone(String riderPhone) {
        this.riderPhone = riderPhone;
    }
    
    
}
