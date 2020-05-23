/* Kumaran D
     ridefair:rideFair - Main application class
 */

package com.github.SUT2014.RideFair.ridefair;

import com.github.SUT2014.RideFair.properties.PropertiesLoad;
import com.github.SUT2014.RideFair.threading.CreateThread;
import com.github.SUT2014.RideFair.threading.Monitor;
import com.github.SUT2014.RideFair.threading.TaxiLocationThread;
import com.github.SUT2014.RideFair.threading.TripThread;
import com.github.SUT2014.RideFair.utils.CurrentTrips;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//main rideFair class, this is where it all starts
public class rideFair {
    private static final Logger LOGGER = LogManager.getLogger(rideFair.class);
    private Monitor monitor;

    //starting monitor thread
    public void startMonitorThread(){
        try{
            monitor = new Monitor(LOGGER);
            CreateThread.createMonitorThread(monitor);
            CreateThread.getMonitorThread().start();
        }
        catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }

    }
    //main func
    public static void main(String[] args) {
        rideFair rf = new rideFair();
        CurrentTrips ct = new CurrentTrips(LOGGER);
        LOGGER.debug("There is no spoon");
        try {
            if (PropertiesLoad.loadProp(LOGGER)) {
                //start Taxi Location Thread
                CreateThread.createTaxiLocationThread();
                CreateThread.getTaxiLocationThread().submit(new TaxiLocationThread(ct, LOGGER));
                //start Trip Thread
                CreateThread.createTripThread();
                CreateThread.getTripThread().submit(new TripThread(ct, LOGGER));
                //start monitor thread
                rf.startMonitorThread();
                while (true) {
                    Thread.sleep(PropertiesLoad.getConsumerMonitorInterval() * 1000);
                }

            }
        }
        catch (Exception ex){
            LOGGER.error(ex.getMessage(), ex);
        }
        finally {
            LOGGER.error("Stopping Monitor Thread");
            rf.monitor.shutdown();
        }
    }
}
