/* Kumaran D
     Utils:SpecialFareSet - Implementer class for SpecialFares
 */
package com.github.SUT2014.RideFair.utils;

import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SpecialFareSet {
    //private Set<SpecialFare> spl = new HashSet<SpecialFare>();
    private Set<SpecialFare> spl = Collections.synchronizedSet(new HashSet<SpecialFare>());
    private final Logger LOGGER;

    public SpecialFareSet(Logger LOGGER){
        this.LOGGER = LOGGER;
    }
    //add a special fare entry, modify this method to write into a DB table
    public void addSpecialFare(double latitude, double longitude, double range, double price){
        spl.add(new SpecialFare(latitude, longitude, range, price));
    }

    //delete a special fare entry, modify this method to remove from a DB table
    public void deleteSpecialFare(double latitude, double longitude, double range, double price){
        spl.remove(new SpecialFare(latitude, longitude, range, price));
    }

    //clear all special fares
    public void clearAll(){
        spl.clear();
    }

    //get special fair
    // iterate through the set, check proximity and then return price
    public double returnProximityPrice(double latitude, double longitude){
        GlobalPosition gp = new GlobalPosition(this.LOGGER);
        synchronized (spl) {
            for (SpecialFare itr : spl) {
                if (gp.checkProximity(itr.getLatitude(), itr.getLongitude(), itr.getRange(), latitude, longitude)) {
                    LOGGER.debug("Special Fare applied: " + itr.toString());
                    return itr.getPrice();
                }
            }
        }
        return 0;
    }

    // to test the funcs
    /*public static void main(String[] args) {
        SpecialFareSet sfs = new SpecialFareSet(LogManager.getLogger(SpecialFareSet.class));
        sfs.addSpecialFare(51.503343,-0.576005, 1.0,1.5);
        sfs.addSpecialFare(51.490191,-0.526563, 2.0, 2.0);
        sfs.returnProximityPrice(51.473334, -0.52005);

    }*/
}
