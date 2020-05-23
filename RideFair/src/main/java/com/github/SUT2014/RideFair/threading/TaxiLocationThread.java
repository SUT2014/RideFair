/* Kumaran D
     threading:TaxiLocationThread - Implementer thread for recording taxi location
     works off an Executor framework, pre-configured number of Worker threads are used.
 */

package com.github.SUT2014.RideFair.threading;

import com.github.SUT2014.RideFair.kafka.Consumer;
import com.github.SUT2014.RideFair.properties.PropertiesLoad;
import com.github.SUT2014.RideFair.utils.CurrentTrips;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.logging.log4j.Logger;

public class TaxiLocationThread implements Runnable {
    private final Logger LOGGER;
    private final CurrentTrips ct;
    public TaxiLocationThread(CurrentTrips ct, Logger LOGGER) {
            this.LOGGER = LOGGER;
            this.ct = ct;
        }
    @Override
    public void run() {
        consumeTaxiLocation();
    }

    private void consumeTaxiLocation() {
        try
        {
            LOGGER.info("Taxi Location thread " + Thread.currentThread().getName() + " started");
            CreateThread.createTaxiLocationWorkerPool();
            Consumer kafkac = new Consumer();
            //configure the non default Taxi Location specific config
            kafkac.configureTopic(PropertiesLoad.getTaxiLocationBootstrapServer(),
                    PropertiesLoad.getTaxiLocationGroupId(), PropertiesLoad.getTaxiLocationTopic());
            while(true) {
                ConsumerRecords<String,String> crs = kafkac.getNextRecords();
                for(ConsumerRecord record : crs){
                    TaxiLocationWorker tlw = new TaxiLocationWorker(ct, record.value().toString(), LOGGER);
                    CreateThread.getTaxiLocationWorkerPool().submit(tlw);
                }
            }
        }
        catch(Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        finally {
            LOGGER.error("Stopping WorkerThread Pool");
            CreateThread.getTaxiLocationWorkerPool().shutdown();
            while (!CreateThread.getTaxiLocationWorkerPool().isTerminated()) {
            }
            LOGGER.error("Stopping TaxiLocation Worker Thread");
        }
    }
}

