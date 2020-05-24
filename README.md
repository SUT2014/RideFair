# RideFair

RideFair is a Pricing module for a Taxi Ride Management system.  It is written in Javan and uses Kafka as a messaging backbone.
Typical Requirements
  - The Taxi locations arrive on a Kafka topic, at a configurable rate, but ideally, each taxi checks in with its location every 10 seconds
  - Taxi Trip Start and End commands arrive on a second Kafka Topic at a configurable rate
  - This Module is a Multi-threaded application which reads through each of these topics and computes Taxi Fare for each trip.  

# Features!

  - The Topics, Number of threads to handle location update and Trip information can be configured.
  - The final fare, distance travelled are displayed at the end of each trip.
  - Uses latitude/longitude location delta to compute distance between each location updates
  - Test Data Generators using Python can be found in the Python area of the Repository

RideFair has been developed using the following tools/apps:

* [kafka] - Open Source Stream Processing Software platform
* [Java] - Core Java 
* [Log4j2] - Logging utility by Apache.
* [IntelliJ] - IDE
* [Python] - Test data generators were developed using Python

And of course RideFair itself is open source on on GitHub.

### Installation

Use Maven to build RideFair.  the pom file can be found along with the source.

Create Kafka Topics using the following commands...

```sh
$ kafka-topics.sh --zookeeper localhost:2181 --create --topic TRIP_TOPIC --partitions 3 --replication-factor 1
$ kafka-topics.sh --zookeeper localhost:2181 --create --topic TAXI_LOCATION_TOPIC --partitions 3 --replication-factor 1
```

Generated Logs...
```sh
2020-05-23 23:07:50,836 DEBUG com.github.SUT2014.RideFair.ridefair.rideFair [WorkerThread-426] Starting Trip - DriverId:95424 TripInfo{passengerId=95424, startLatitude=50.0108, startLongitude=0.1663, currentLatitude=50.0108, currentLongitude=0.1663, defaultRate=1.5, currentFare=0.0, distance=0.0}

2020-05-23 23:07:50,836 DEBUG com.github.SUT2014.RideFair.ridefair.rideFair [WorkerThread-1352] Updated Trip - DriverId: 81352  TripInfo{passengerId=81352, startLatitude=50.0115, startLongitude=0.1613, currentLatitude=50.013, currentLongitude=0.1925, defaultRate=1.5, currentFare=3.353510215763253, distance=2.235673477175502}
```


**Free Software, Hell Yeah!**