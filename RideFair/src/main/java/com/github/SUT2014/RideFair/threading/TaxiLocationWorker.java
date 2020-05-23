/* Kumaran D
     threading:TaxiLocationWorker - Worker thread for recording taxi location.
 */
package com.github.SUT2014.RideFair.threading;

import com.github.SUT2014.RideFair.utils.CurrentTrips;
import org.apache.logging.log4j.Logger;

public class TaxiLocationWorker implements Runnable{

    private final Logger LOGGER;
    private final String taxiLocationCSV;
    private final CurrentTrips ct;

    public TaxiLocationWorker(CurrentTrips ct, String taxiLocationCSV, Logger LOGGER){
        this.ct = ct;
        this.taxiLocationCSV = taxiLocationCSV;
        this.LOGGER = LOGGER;
    }

    @Override
    public void run() {
        try {
            processCommand();
        } catch (InterruptedException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    private void processCommand() throws InterruptedException{
        try {
            // LOGGER.debug("This is what I received.." + taxiLocationCSV);
            String[] fields = taxiLocationCSV.split(",");
            ct.updateTrip(Long.parseLong(fields[0]), Double.parseDouble(fields[1]), Double.parseDouble(fields[2]));
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }
}
