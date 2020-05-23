/* Kumaran D
     Utils:SpecialFare - Attributes and methods required to store Special Fares.
 */
package com.github.SUT2014.RideFair.utils;

public class SpecialFare {
    private double latitude, longitude;
    private double range, price;

    public SpecialFare(double latitude, double longitude, double range, double price) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.range = range;
        this.price = price;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getRange() {
        return range;
    }

    public double getPrice() {
        return price;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setRange(double range) {
        this.range = range;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "SpecialFare{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", range=" + range +
                ", price=" + price +
                '}';
    }

    //object equals
    @Override
    public boolean equals(Object obj){
        if (obj instanceof SpecialFare){
            SpecialFare objinst = (SpecialFare) obj;
            return this.latitude == objinst.latitude && this.longitude == objinst.longitude
                    && this.range == objinst.range && this.price == objinst.price;
        }
        return false;
    }
}
