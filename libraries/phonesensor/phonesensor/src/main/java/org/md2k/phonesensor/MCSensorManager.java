package org.md2k.phonesensor;

import android.content.Context;

import org.md2k.phonesensor.sensor.battery.MCBattery;
import org.md2k.phonesensor.sensor.gravity.MCGravity;
import org.md2k.phonesensor.sensor.ISensor;
import org.md2k.phonesensor.sensor.accelerometer.MCAccelerometer;
import org.md2k.phonesensor.sensor.accelerometer_linear.MCAccelerometerLinear;
import org.md2k.phonesensor.sensor.accelerometer_uncalibrated.MCAccelerometerUncalibrated;
import org.md2k.phonesensor.sensor.activity_type.MCActivityType;
import org.md2k.phonesensor.sensor.air_pressure.MCAirPressure;
import org.md2k.phonesensor.sensor.ambient_light.MCAmbientLight;
import org.md2k.phonesensor.sensor.ambient_temperature.MCAmbientTemperature;
import org.md2k.phonesensor.sensor.battery_details.MCBatteryDetails;
import org.md2k.phonesensor.sensor.bluetooth_status.MCBluetoothStatus;
import org.md2k.phonesensor.sensor.charging_state.MCChargingState;
import org.md2k.phonesensor.sensor.game_rotation_vector.MCGameRotationVector;
import org.md2k.phonesensor.sensor.geometric_rotation_vector.MCGeomagneticRotationVector;
import org.md2k.phonesensor.sensor.gps_status.MCGPSStatus;
import org.md2k.phonesensor.sensor.gyroscope.MCGyroscope;
import org.md2k.phonesensor.sensor.gyroscope_uncalibrated.MCGyroscopeUncalibrated;
import org.md2k.phonesensor.sensor.location.MCLocation;
import org.md2k.phonesensor.sensor.magnetometer.MCMagnetometer;
import org.md2k.phonesensor.sensor.magnetometer_uncalibrated.MCMagnetometerUncalibrated;
import org.md2k.phonesensor.sensor.proximity.MCProximity;
import org.md2k.phonesensor.sensor.relative_humidity.MCRelativeHumidity;
import org.md2k.phonesensor.sensor.rotation_vector.MCRotationVector;
import org.md2k.phonesensor.sensor.significant_motion.MCSignificantMotion;

import java.util.HashMap;

/*
 * Copyright (c) 2016, The University of Memphis, MD2K Center
 * - Syed Monowar Hossain <monowar.hossain@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
public class MCSensorManager {
    private static MCSensorManager instance;
    private HashMap<SensorType, ISensor> sensors;

    public static MCSensorManager getInstance(Context context) {
        if (instance == null)
            instance = new MCSensorManager(context.getApplicationContext());
        return instance;
    }

    private MCSensorManager(Context context) {
        sensors = new HashMap<>();
        sensors.put(SensorType.ACCELEROMETER, new MCAccelerometer(context));
        sensors.put(SensorType.ACCELEROMETER_LINEAR, new MCAccelerometerLinear(context));
        sensors.put(SensorType.ACCELEROMETER_UNCALIBRATED, new MCAccelerometerUncalibrated(context));
        sensors.put(SensorType.AIR_PRESSURE, new MCAirPressure(context));
        sensors.put(SensorType.GYROSCOPE, new MCGyroscope(context));
        sensors.put(SensorType.GYROSCOPE_UNCALIBRATED, new MCGyroscopeUncalibrated(context));

        sensors.put(SensorType.GRAVITY, new MCGravity(context));
        sensors.put(SensorType.MAGNETOMETER, new MCMagnetometer(context));
        sensors.put(SensorType.MAGNETOMETER_UNCALIBRATED, new MCMagnetometerUncalibrated(context));
        sensors.put(SensorType.ROTATION_VECTOR, new MCRotationVector(context));
        sensors.put(SensorType.GEOMAGNETIC_ROTATION_VECTOR, new MCGeomagneticRotationVector(context));
        sensors.put(SensorType.GAME_ROTATION_VECTOR, new MCGameRotationVector(context));
        sensors.put(SensorType.PROXIMITY, new MCProximity(context));
        sensors.put(SensorType.AMBIENT_LIGHT, new MCAmbientLight(context));
        sensors.put(SensorType.AMBIENT_TEMPERATURE, new MCAmbientTemperature(context));

        sensors.put(SensorType.RELATIVE_HUMIDITY, new MCRelativeHumidity(context));
/*        sensors.put(SensorType.BAROMETER, null);
        sensors.put(SensorType.STEP_COUNTER, null);
        sensors.put(SensorType.STEP_DETECT, null);
*/
        sensors.put(SensorType.SIGNIFICANT_MOTION, new MCSignificantMotion(context));
        //Battery
        sensors.put(SensorType.BATTERY, new MCBattery(context));
        sensors.put(SensorType.BATTERY_DETAILS,new MCBatteryDetails(context));
        sensors.put(SensorType.CHARGING_STATE, new MCChargingState(context));
        //Location
        sensors.put(SensorType.GPS_STATUS, new MCGPSStatus(context));
        sensors.put(SensorType.LOCATION, new MCLocation(context));
        sensors.put(SensorType.ACTIVITY_TYPE, new MCActivityType(context));
/*        sensors.put(SensorType.CPU_USAGE, null);
        sensors.put(SensorType.WIFI, null);
*/
        sensors.put(SensorType.BLUETOOTH_STATUS, new MCBluetoothStatus(context));
/*        sensors.put(SensorType.BLUETOOTH, null);
        sensors.put(SensorType.NETWORK_STATUS, null);
        sensors.put(SensorType.NETWORK, null);
        sensors.put(SensorType.TELEPHONY, null);
        sensors.put(SensorType.WIFI_DEVICE, null);

        sensors.put(SensorType.WEATHER, new MCWeather(context));
*/
    }
    public <T extends ISensor> T  getSensor(SensorType sensorType){
        return (T) sensors.get(sensorType);
    }
}
