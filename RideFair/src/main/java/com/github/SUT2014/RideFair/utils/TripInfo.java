/* Kumaran D
     Utils:TripInfo - Attributes and methods required to store Trip Information.
 */
package com.github.SUT2014.RideFair.utils;

public class TripInfo {
    private long passengerId;
    private double startLatitude, startLongitude;
    private double currentLatitude, currentLongitude;
    private double defaultRate; //per kms
    private double currentFare;
    private double distance; // in kms

    public TripInfo(long userId, double startLatitude, double startLongitude, double defaultRate) {
        this.passengerId = userId;
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.defaultRate = defaultRate;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }

    public long getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(long passengerId) {
        this.passengerId = passengerId;
    }

    public double getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(double startLatitude) {
        this.startLatitude = startLatitude;
    }

    public double getStartLongitude() {
        return startLongitude;
    }

    public void setStartLongitude(double startLongitude) {
        this.startLongitude = startLongitude;
    }

    public double getCurrentLatitude() {
        return currentLatitude;
    }

    public void setCurrentLatitude(double currentLatitude) {
        this.currentLatitude = currentLatitude;
    }

    public double getCurrentLongitude() {
        return currentLongitude;
    }

    public void setCurrentLongitude(double currentLongitude) {
        this.currentLongitude = currentLongitude;
    }

    public double getDefaultRate() {
        return defaultRate;
    }

    public void setDefaultRate(double defaultRate) {
        this.defaultRate = defaultRate;
    }

    public double getCurrentFare() {
        return currentFare;
    }

    public void setCurrentFare(double currentFare) {
        this.currentFare = currentFare;
    }

    @Override
    public String toString() {
        return "TripInfo{" +
                "passengerId=" + passengerId +
                ", startLatitude=" + startLatitude +
                ", startLongitude=" + startLongitude +
                ", currentLatitude=" + currentLatitude +
                ", currentLongitude=" + currentLongitude +
                ", defaultRate=" + defaultRate +
                ", currentFare=" + currentFare +
                ", distance=" + distance +
                '}';
    }
}