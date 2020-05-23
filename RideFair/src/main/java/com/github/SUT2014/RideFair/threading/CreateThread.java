/*
 * Copyright (c) 2020.  Kumaran Devaneson
 * All rights reserved
 */
package com.github.SUT2014.RideFair.threading;

import com.github.SUT2014.RideFair.properties.PropertiesLoad;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CreateThread {
    private static ExecutorService taxiLocationThread, TripThread;
    private static ThreadPoolExecutor taxiLocationWorkerPool, TripWorkerPool;
    private static Thread monitorThread;

    //Thread to consume Taxi Location
    public static void createTaxiLocationThread() {
        ThreadFactory customThreadFactory = new CustomThreadFactory()
                .setNamePrefix("TaxiLocationThread").setDaemon(false)
                .setPriority(Thread.MAX_PRIORITY).build();
        taxiLocationThread = Executors.newSingleThreadExecutor(customThreadFactory);
    }
    //Thread pool to consume Trip details
    public static void createTripThread() {
        ThreadFactory customThreadFactory = new CustomThreadFactory()
                .setNamePrefix("TripThread").setDaemon(false)
                .setPriority(Thread.MAX_PRIORITY).build();
        TripThread = Executors.newSingleThreadExecutor(customThreadFactory);
    }
    //Thread pool to process taxi location
    public static void createTaxiLocationWorkerPool() {
        ThreadFactory customThreadFactory = new CustomThreadFactory()
                .setNamePrefix("WorkerThread").setDaemon(false)
                .setPriority(Thread.MAX_PRIORITY).build();
        taxiLocationWorkerPool = new ThreadPoolExecutor(PropertiesLoad.getTaxiLocationThreadPoolSize(),
                PropertiesLoad.getTaxiLocationThreadPoolSize(), 60L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1000), customThreadFactory);
        taxiLocationWorkerPool.prestartAllCoreThreads();
    }
    //Thread pool to process Trip details
    public static void createTripThreadPool() {
        ThreadFactory customThreadFactory = new CustomThreadFactory()
                .setNamePrefix("WorkerThread").setDaemon(false)
                .setPriority(Thread.MAX_PRIORITY).build();
        TripWorkerPool = new ThreadPoolExecutor(PropertiesLoad.getTripThreadPoolSize(),
                PropertiesLoad.getTripThreadPoolSize(), 60L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(100000), customThreadFactory);
        TripWorkerPool.prestartAllCoreThreads();
    }
    public static void createMonitorThread(Monitor monitor) {
        monitorThread = new Thread(monitor);
        monitorThread.setName("MonitorThread");
    }

    public static ExecutorService getTaxiLocationThread() {
        return taxiLocationThread;
    }

    public static ThreadPoolExecutor getTaxiLocationWorkerPool() {
        return taxiLocationWorkerPool;
    }

    public static ExecutorService getTripThread() {
        return TripThread;
    }

    public static ThreadPoolExecutor getTripWorkerPool() {
        return TripWorkerPool;
    }
    public static Thread getMonitorThread() {
        return monitorThread;
    }
}
