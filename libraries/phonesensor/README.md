# mCerebrum-PhoneSensorLibrary-android
[![Build Status](https://travis-ci.org/MD2Korg/mCerebrum-PhoneSensorLibrary-android.svg?branch=master)](https://travis-ci.org/MD2Korg/mCerebrum-PhoneSensorLibrary-android)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/da1cc981ba0b4490af42ee32fdb7e27c)](https://app.codacy.com/app/monowar-hossain/mCerebrum-PhoneSensorLibrary-android?utm_source=github.com&utm_medium=referral&utm_content=MD2Korg/mCerebrum-PhoneSensorLibrary-android&utm_campaign=Badge_Grade_Dashboard)

An android library that records sensor events.


## Supported Sensors

##### Motion Sensors
|Sensors|Samples|Settings|
|----|----|----|
|Accelerometer|`double[3] -> x,y,z` |`readFrequency`, `writeFixedFrequency`, `writeOnChange`, `writeAsReceived`|
|Accelerometer (Linear)|`double[3] -> x,y,z` |`readFrequency`, `writeFixedFrequency`, `writeOnChange`, `writeAsReceived`|
|Accelerometer (Uncalibrated)|`double[6] -> x,y,z,x_b,y_b,z_b` |`readFrequency`, `writeFixedFrequency`, `writeOnChange`, `writeAsReceived`|
|Gyroscope|`double[3] -> x,y,z`|`readFrequency`, `writeFixedFrequency`, `writeOnChange`, `writeAsReceived`|
|Gyroscope (Uncalibrated)|`double[6] -> x,y,z,x_d,y_d,z_d`|`readFrequency`, `writeFixedFrequency`, `writeOnChange`, `writeAsReceived`|
|Gravity|`double[3] -> x,y,z`|`readFrequency`, `writeFixedFrequency`, `writeOnChange`, `writeAsReceived`|
|Rotation Vector|`double[3] -> x,y,z`|`readFrequency`, `writeFixedFrequency`, `writeOnChange`, `writeAsReceived`|
|Significant Motion|`double[1] -> 1.0=significant motion`|`writeAsReceived`|
|Step Counter||
|Step Detection||

##### Position Sensors
|Sensors|Samples|Settings Option|
|----|----|----|
|Magnetometer|`double[3] -> x,y,z`|`readFrequency`, `writeFixedFrequency`, `writeOnChange`, `writeAsReceived`|
|Magnetometer (Uncalibrated)|`double[6] -> x,y,z,x_b,y_b,z_b`|`readFrequency`, `writeFixedFrequency`, `writeOnChange`, `writeAsReceived`|
|Proximity|`double[1]->distance` |`readFrequency`, `writeFixedFrequency`, `writeOnChange`, `writeAsReceived`|
|Game Rotation Vector|`double[3] -> x,y,z`|`readFrequency`, `writeOnChange`, `writeAsReceived`, `writeFixed`|
|Geomagnetic Rotation Vector|`double[3] -> x,y,z`|`readFrequency`, `writeOnChange`, `writeAsReceived`, `writeFixed`|

##### Environment
|Sensors|Samples|Settings Option|
|----|----|----|
|Air Pressure|`double[1]->air pressure (hPa)` |`readFrequency`, `writeOnChange`, `writeAsReceived`, `writeFixed`|
|Ambient Light|`double[1]->light (lx)` |`readFrequency`, `writeFixedFrequency`,`writeOnChange`, `writeAsReceived`|
|Ambient Temperature|`double[1]->temperature(celsius)` |`readFrequency`, `writeFixedFrequency`, `writeOnChange`, `writeAsReceived`|
|Relative Humidity|`double[1]->rel_humidity(%)` |`readFrequency`, `writeFixedFrequency`, `writeOnChange`, `writeAsReceived`|
|Weather|`city, country, latitude, longitude, temperature, pressure, pressureSeaLevel, pressureGroundLevel, humidity, windSpeed, windDirectionDegree, sunriseTime, sunsetTime, cloudiness`|`readFrequency`,`writeAsReceived`|

##### Battery
|Sensors|Samples|Settings Option|
|----|----|----|
|Battery|`double[0]-> battery level (in %)` |`writeOnChange`, `writeAsReceived`,`writeFixed`|
|Battery Details|`double[7]->level, scale, temperature, voltage, plugged, status, health` |`writeOnChange`, `writeAsReceived`,`writeFixed`|
|Charging State|`double[1]->charging state (1->charging, 0->not charging` |`writeOnChange`, `writeAsReceived`,`writeFixed`|

##### Communication Sensors
|Sensors|Samples|Settings Option|
|----|----|----|
|Bluetooth Status|`double[0]-> 0=off,1=on`  |`writeOnChange, writeAsReceived, writeFixed`|
|Nearby bluetooth devices (Classic)|`TODO` |`TODO`|
|Nearby bluetooth devices (LE)|`TODO` |`TODO`|
|Wifi Status|`TODO` |`TODO`|
|Nearby wifi devices|`TODO` |`TODO`|
|Network Status|`TODO`|`TODO`|
|SMS|`timestamp,type,hash(phone number),message_len`|`writeAsReceived`|
##### Location Sensors
|Sensors|Samples|Settings Option|
|----|----|----|
|Activity Type|`mostProbableActivity, confidences[]`<br>`activityType=in_vehicle / on_bicycle / on_foot / still / tilting / walking / running / unknown` |`readFrequency, writeFixedFrequency, writeAsReceived, writeOnChange`|
|GPS Status|`status=true/false` |`readOnChange, writeOnChange`|

|Location|`double[6]=latitude,longitude,altitude,speed,bearing,accuracy` |`readFrequency, writeOnChange, writeAsReceived, writeFixed`|
|Geofence|`TODO` |`TODO`|

##### Audio Video
|Sensors|Samples|Settings Option|
|----|----|----|
|Record Audio|`TODO` |`TODO`|
|Record Image|`TODO` |`TODO`|
|Record Video|`TODO` |`TODO`|


### Setting up the dependency
The first step is to include mCerebrumAPI into your project for example, as a gradle compile dependency:

```groovy
implementation "org.md2k.library:phonesensor:<latest_version>"
```
(Please replace `<latest_version>` with this: [ ![Download](https://api.bintray.com/packages/md2korg/mCerebrum/core/images/download.svg) ](https://bintray.com/md2korg/mCerebrum/core/_latestVersion)
)

### How to use
The second is to initialize mCerebrumAPI once in `Application.onCreate():`
```java
Accelerometer a = (Accelerometer)SensorManager.getInstance(context, SensorType.ACCELEROMETER);
a.setReadFrequency(6.0, TimeUnit.SECONDS);
a.setWriteFixed(6.0, TimeUnit.SECONDS);

```


## Contributing
Please read our [Contributing Guidelines](https://md2k.org/software/under-the-hood/contributing) for details on the process for submitting pull requests to us.

We use the [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html).

Our [Code of Conduct](https://md2k.org/software/CodeofConduct) is the [Contributor Covenant](https://www.contributor-covenant.org/).

Bug reports can be submitted through [JIRA](https://md2korg.atlassian.net/secure/Dashboard.jspa).

Our discussion forum can be found [here](https://discuss.md2k.org/).

## Versioning

We use [Semantic Versioning](https://semver.org/) for versioning the software which is based on the following guidelines.

MAJOR.MINOR.PATCH (example: 3.0.12)

  1. MAJOR version when incompatible API changes are made,
  2. MINOR version when functionality is added in a backwards-compatible manner, and
  3. PATCH version when backwards-compatible bug fixes are introduced.

For the versions available, see [this repository's tags](https://github.com/MD2Korg/mCerebrum-API-android/tags).

## Contributors

Link to the [list of contributors](https://github.com/MD2Korg/mCerebrum-API-android/graphs/contributors) who participated in this project.

## License

This project is licensed under the BSD 2-Clause - see the [license](https://md2k.org/software-under-the-hood/software-uth-license) file for details.

## Acknowledgments

* [National Institutes of Health](https://www.nih.gov/) - [Big Data to Knowledge Initiative](https://datascience.nih.gov/bd2k)
  * Grants: R01MD010362, 1UG1DA04030901, 1U54EB020404, 1R01CA190329, 1R01DE02524, R00MD010468, 3UH2DA041713, 10555SC
* [National Science Foundation](https://www.nsf.gov/)
  * Grants: 1640813, 1722646
* [Intelligence Advanced Research Projects Activity](https://www.iarpa.gov/)
  * Contract: 2017-17042800006
