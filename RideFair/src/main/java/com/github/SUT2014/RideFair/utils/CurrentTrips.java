/* Kumaran D
     Utils:CurrentTrips - Implementer class for TripInfo
 */
package com.github.SUT2014.RideFair.utils;

import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class CurrentTrips {
    private  Map<Long, TripInfo> ct = Collections.synchronizedSortedMap(new TreeMap<Long, TripInfo>());
    private final  Logger LOGGER;

    public CurrentTrips(Logger LOG){
        LOGGER = LOG;
    }
    //clear all trips
    public  void clearAll(){
        ct.clear();
    }
    //check if trip exists
    public  boolean tripExists(long driverId){
        return(ct.containsKey(driverId));
    }
    //remove trip, end trip , modify this method for DB persistence
    public  void endTrip(long driverId, long passengerId){
        if (tripExists(driverId)){
            TripInfo ti = ct.get(driverId);
            if (ti.getPassengerId() == passengerId){
                ti = ct.remove(driverId);
                LOGGER.debug("Ending Trip - DriverId:"+ driverId + " " + ti.toString());
            }
            else{
                //ti = ct.remove(driverId);
                LOGGER.debug("Received wrong PassengerId for the trip, ignore End - DriverId:"
                        + driverId + " " + ti.toString());
            }
        }
    }
    //remove trip, end trip , modify this method for DB persistence
    public  void endTrip(long driverId){
        TripInfo ti;
        if (tripExists(driverId)){
            ti = ct.remove(driverId);
            LOGGER.debug("Ending Trip - DriverId:"+ driverId + " " + ti.toString());
        }
    }

     //update trip , modify this method for DB persistence
    public  void updateTrip(long driverId, double currentLatitude, double currentLongitude){
        //DecimalFormat formatTwo = new DecimalFormat("0.00");
        if (tripExists(driverId)){
            TripInfo ti;
            double deltaDistance;
            GlobalPosition gp = new GlobalPosition(LOGGER);

            ti = ct.get(driverId);
            deltaDistance = (gp.distanceInKMs(ti.getCurrentLatitude(), ti.getCurrentLongitude(), currentLatitude, currentLongitude));
             //also check for special fares, later versions
            ti.setCurrentFare(ti.getCurrentFare()+(ti.getDefaultRate()*deltaDistance));
            //replace current position
            ti.setCurrentLatitude(currentLatitude);
            ti.setCurrentLongitude(currentLongitude);
            ti.setDistance(ti.getDistance() + deltaDistance);
            ct.replace(driverId, ti);
            LOGGER.debug("Updated Trip - DriverId: " + driverId + "  " + ti.toString());
        }
    }

    //add a trip, start trip , modify this method for DB persistence
    public  void startTrip(long driverId, TripInfo tripInfo){
        if (tripExists(driverId)){
            LOGGER.debug("Existing Trip..ending it - DriverId:" + driverId);
            endTrip(driverId);
        }
        ct.put(driverId, tripInfo);
        LOGGER.debug("Starting Trip - DriverId:" + driverId + " " + tripInfo.toString());
    }

    //main function to test other funcs
    /*public static void main(String[] args) {
        CurrentTrips ct = new CurrentTrips(LogManager.getLogger(CurrentTrips.class));
        TripInfo ti = new TripInfo(999, 51.503343,-0.576005,
                51.503343,-0.576005,10.5, 0);
        ct.startTrip(1, ti);
        ct.updateTrip(1, 51.503443,-0.576115);
        ct.updateTrip(1, 51.503663,-0.576335);
        ct.updateTrip(1, 51.504543,-0.576665);
        ct.endTrip(1);
    }*/
}
