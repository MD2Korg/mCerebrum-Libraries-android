package org.md2k.phonesensor;

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
public enum SensorType {
    ACCELEROMETER,
    ACCELEROMETER_LINEAR,
    ACCELEROMETER_UNCALIBRATED,
    GYROSCOPE,
    GYROSCOPE_UNCALIBRATED,
    GRAVITY,
    ROTATION_VECTOR,
    MAGNETOMETER,
    MAGNETOMETER_UNCALIBRATED,
    PROXIMITY,
    SIGNIFICANT_MOTION,
    RELATIVE_HUMIDITY,
    AIR_PRESSURE,
    AMBIENT_LIGHT,
    AMBIENT_TEMPERATURE,
    ACTIVITY_TYPE,
    GPS_STATUS,
    LOCATION,

/*
    BAROMETER,
    STEP_COUNTER,
    STEP_DETECT,
*/
    BATTERY,
    BATTERY_DETAILS,
/*
    CPU_USAGE,
    WIFI_STATUS,
    WIFI_DEVICE,
    BLUETOOTH,
*/
    BLUETOOTH_STATUS,
/*
    NETWORK_STATUS,
    NETWORK,
*/
    CHARGING_STATE,
    TELEPHONY,
    GAME_ROTATION_VECTOR,
    GEOMAGNETIC_ROTATION_VECTOR,
    //Location
/*
    GEOFENCE,
    WEATHER,
*/
}
