/* Kumaran D
     Utils:GlobalPosition - Methods to implement global positioning functions using latitudes and longitudes
 */

package com.github.SUT2014.RideFair.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GlobalPosition {
    private final Logger LOGGER;

    public GlobalPosition(Logger LOGGER) {
        this.LOGGER = LOGGER;
    }

    //distance between two sets of latitudes and longitudes
    public double distanceInKMs (double startLat, double startLong, double endLat, double endLong){
        double RADIUS_OF_EARTH_KM = 6371;

        if (startLat == endLat && startLong == endLong){
            return 0;
        }
        double latDistance = Math.toRadians(endLat - startLat);
        double lngDistance = Math.toRadians(endLong - startLong);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(endLat)) * Math.cos(Math.toRadians(startLat))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return (RADIUS_OF_EARTH_KM * c);
    }
    //check proximity of a given location to a standard location.  Range is in kms
    public boolean checkProximity (double baseLat, double baseLong, double range, double userLat, double userLong){
        double distance = distanceInKMs(baseLat, baseLong, userLat, userLong);
        return (distance <= range);
    }
    //main to test the funcs
    public static void main(String[] args) {
        GlobalPosition gp = new GlobalPosition(LogManager.getLogger(GlobalPosition.class));
        gp.LOGGER.debug(gp.distanceInKMs(51.503343,-0.576005, 51.490191,-0.526563));
        gp.LOGGER.debug(gp.checkProximity(51.503343,-0.576005, 1.0,51.490191,-0.526563));
    }
}
