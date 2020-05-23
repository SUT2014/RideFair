/* Kumaran D
     threading:TripWorker - Worker thread for recording Trips.
 */
package com.github.SUT2014.RideFair.threading;

import com.github.SUT2014.RideFair.utils.CurrentTrips;
import com.github.SUT2014.RideFair.utils.TripInfo;
import org.apache.logging.log4j.Logger;

public class TripWorker implements Runnable{
    private final CurrentTrips ct;
    private final Logger LOGGER;
    private final String tripCSV;

    public TripWorker(CurrentTrips ct, String tripStr, Logger LOGGER){
        this.ct = ct;
        this.tripCSV = tripStr;
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
            // LOGGER.debug("This is what I received.." + tripCSV);
            String[] fields = tripCSV.split(",");
            if (fields[1].equalsIgnoreCase("START")){
                TripInfo ti = new TripInfo(Long.parseLong(fields[2]), Double.parseDouble(fields[3]),
                        Double.parseDouble(fields[4]), Double.parseDouble(fields[5]));
                //set default values.
                ti.setCurrentLatitude(ti.getStartLatitude());
                ti.setCurrentLongitude(ti.getStartLongitude());
                ti.setDistance(0);

                ct.startTrip(Long.parseLong(fields[0]), ti);
            }
            else if (fields[1].equalsIgnoreCase("END")){
                ct.endTrip(Long.parseLong(fields[0]));
            }
            else {
                LOGGER.error("Error in Trip Command" + tripCSV);
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }
}
