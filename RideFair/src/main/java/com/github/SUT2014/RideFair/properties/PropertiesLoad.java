/*
 * Copyright (c) 2020.  Kumaran Devaneson
  * All rights reserved
 */

package com.github.SUT2014.RideFair.properties;

import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoad {
    private static int CONSUMER_MONITOR_INTERVAL,WORKER_MONITOR_INTERVAL;
    private static int TAXI_LOCATION_THREAD_POOL_SIZE, TRIP_THREAD_POOL_SIZE;
    private static String TAXI_LOCATION_BOOTSTRAP_SERVER, TAXI_LOCATION_GROUP_ID, TAXI_LOCATION_TOPIC;
    private static String TRIP_BOOTSTRAP_SERVER, TRIP_GROUP_ID, TRIP_TOPIC;

    public static boolean loadProp(Logger LOGGER){
        InputStream inStream = null;
        try {
            Properties prop = new Properties();
            inStream =  PropertiesLoad.class.getClassLoader().getResourceAsStream("config.properties");

            if (inStream == null){
                LOGGER.error("Unable to find config.properties");
                return false;
            }
            prop.load(inStream);
            CONSUMER_MONITOR_INTERVAL = Integer.parseInt(prop.getProperty("CONSUMER_MONITOR_INTERVAL"));
            WORKER_MONITOR_INTERVAL = Integer.parseInt(prop.getProperty("WORKER_MONITOR_INTERVAL"));
            TAXI_LOCATION_THREAD_POOL_SIZE = Integer.parseInt(prop.getProperty("TAXI_LOCATION_THREAD_POOL_SIZE"));
            TRIP_THREAD_POOL_SIZE = Integer.parseInt(prop.getProperty("TRIP_THREAD_POOL_SIZE"));
            TAXI_LOCATION_BOOTSTRAP_SERVER = prop.getProperty("TAXI_LOCATION_BOOTSTRAP_SERVER");
            TAXI_LOCATION_GROUP_ID = prop.getProperty("TAXI_LOCATION_GROUP_ID");
            TAXI_LOCATION_TOPIC = prop.getProperty("TAXI_LOCATION_TOPIC");
            TRIP_BOOTSTRAP_SERVER = prop.getProperty("TRIP_BOOTSTRAP_SERVER");
            TRIP_GROUP_ID = prop.getProperty("TRIP_GROUP_ID");
            TRIP_TOPIC = prop.getProperty("TRIP_TOPIC");
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return false;
        }
        finally {
            try {
                if(inStream != null)
                    inStream.close();
            } catch (IOException ex) {
                LOGGER.error(ex.getMessage(), ex);
            }
        }
        return true;
    }

    public static int getConsumerMonitorInterval(){
        return CONSUMER_MONITOR_INTERVAL;
    }
    public static int getWorkerMonitorInterval(){
        return WORKER_MONITOR_INTERVAL;
    }
    public static int getTaxiLocationThreadPoolSize(){
        return TAXI_LOCATION_THREAD_POOL_SIZE;
    }
    public static int getTripThreadPoolSize(){
        return TRIP_THREAD_POOL_SIZE;
    }
    public static String getTaxiLocationBootstrapServer() {
        return TAXI_LOCATION_BOOTSTRAP_SERVER;
    }
    public static String getTaxiLocationGroupId() {
        return TAXI_LOCATION_GROUP_ID;
    }
    public static String getTaxiLocationTopic() {
        return TAXI_LOCATION_TOPIC;
    }
    public static String getTripBootstrapServer() {
        return TRIP_BOOTSTRAP_SERVER;
    }
    public static String getTripGroupId() {
        return TRIP_GROUP_ID;
    }
    public static String getTripTopic() {
        return TRIP_TOPIC;
    }
}
