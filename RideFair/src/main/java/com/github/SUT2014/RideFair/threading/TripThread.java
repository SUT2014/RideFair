/* Kumaran D
     threading:TripThread - Implementer thread for recording Trips
     works off an Executor framework, pre-configured number of Worker threads are used.
 */

package com.github.SUT2014.RideFair.threading;

import com.github.SUT2014.RideFair.kafka.Consumer;
import com.github.SUT2014.RideFair.properties.PropertiesLoad;
import com.github.SUT2014.RideFair.utils.CurrentTrips;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.logging.log4j.Logger;

public class TripThread implements Runnable {
    private final Logger LOGGER;
    private final CurrentTrips ct;

    public TripThread(CurrentTrips ct, Logger LOGGER) {
            this.ct = ct;
            this.LOGGER = LOGGER;
        }
    @Override
    public void run() {
        consumeTrips();
    }

    private void consumeTrips() {
        try
        {
            LOGGER.info("Trip thread " + Thread.currentThread().getName() + " started");
            CreateThread.createTripThreadPool();
            Consumer kafkac = new Consumer();
            //configure the non default Trip specific config
            kafkac.configureTopic(PropertiesLoad.getTripBootstrapServer(),
                    PropertiesLoad.getTripGroupId(), PropertiesLoad.getTripTopic());
             while(true) {
                 ConsumerRecords<String,String> crs = kafkac.getNextRecords();
                 for(ConsumerRecord record : crs){
                     TripWorker tw = new TripWorker(ct, record.value().toString(),LOGGER);
                     CreateThread.getTripWorkerPool().submit(tw);
                 }
            }
        }
        catch(Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        finally {
            LOGGER.error("Stopping Trip Worker Thread Pool");
            CreateThread.getTaxiLocationWorkerPool().shutdown();
            while (!CreateThread.getTaxiLocationWorkerPool().isTerminated()) {
            }
        }
    }
}

