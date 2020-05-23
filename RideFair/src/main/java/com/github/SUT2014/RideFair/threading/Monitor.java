/*
 * Copyright (c) 2020.  Kumaran Devaneson
  * All rights reserved
 */

package com.github.SUT2014.RideFair.threading;

import com.github.SUT2014.RideFair.properties.PropertiesLoad;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

public class Monitor implements Runnable
{
    private static int iConsumerMonInterval, iWorkerMonInterval;
    //private static int iTripMonInterval, iTripWorkerMonInterval;
    private static Logger LOGGER;
    private boolean bStopThread = false;

    public Monitor(Logger LOG) {
        LOGGER = LOG;
        iConsumerMonInterval = PropertiesLoad.getConsumerMonitorInterval();
        iWorkerMonInterval = PropertiesLoad.getWorkerMonitorInterval();
    }

    public void shutdown() {
        this.bStopThread = true;
    }
    //Log monitor output
    private void printConsumerStats(String str, ExecutorService es){
        if(es != null){
            LOGGER.info(str + " Status: " + ((CreateThread.getTaxiLocationThread().isShutdown()) ? "STOPPED" : "RUNNING"));
        }
    }
    //log worker stats
    private void printWorkerStats(String str, ThreadPoolExecutor tpe){
        if (tpe != null){
            LOGGER.info(
                    String.format("["+ str + " ThreadPool: %d/%d %s], Active: %d, Completed: %d, Total: %d, QueueSize: %d",
                            tpe.getPoolSize(),
                            tpe.getCorePoolSize(),
                            ((tpe.isShutdown())?"STOPPED":"RUNNING"),
                            tpe.getActiveCount(),
                            tpe.getCompletedTaskCount(),
                            tpe.getTaskCount(),
                            tpe.getQueue().size()));
        }
    }

    @Override
    public void run() {
        int iNumSeconds = iConsumerMonInterval;
        while(!bStopThread){
            try {
                if (iNumSeconds == iConsumerMonInterval) {
                    printConsumerStats("Taxi Location Thread: ", CreateThread.getTaxiLocationThread());
                    printConsumerStats("Trip Thread: ", CreateThread.getTripThread());
                    iNumSeconds = 0;
                }
                printWorkerStats("Taxi Location Worker", CreateThread.getTaxiLocationWorkerPool());
                printWorkerStats("Trip Worker", CreateThread.getTripWorkerPool());
                Thread.sleep(iWorkerMonInterval * 1000);
                iNumSeconds += iWorkerMonInterval;
            } catch (Exception ex) {
                LOGGER.error(ex.getMessage(), ex);
            }
        }
    }
}
