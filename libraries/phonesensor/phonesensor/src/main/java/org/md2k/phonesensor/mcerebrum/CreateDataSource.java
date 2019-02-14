package org.md2k.phonesensor.mcerebrum;

import android.content.Context;

import org.md2k.mcerebrumapi.core.data.MCDataType;
import org.md2k.mcerebrumapi.core.data.MCSampleType;
import org.md2k.mcerebrumapi.core.datakitapi.datasource.DataSource;
import org.md2k.mcerebrumapi.core.datakitapi.datasource.DataSourceRegister;
import org.md2k.mcerebrumapi.core.datakitapi.datasource.constants.DataSourceType;
import org.md2k.mcerebrumapi.core.datakitapi.datasource.constants.PlatformType;
import org.md2k.mcerebrumapi.core.datakitapi.datasource.metadata.DataDescriptor;
import org.md2k.mcerebrumapi.core.datakitapi.datasource.metadata.DataSourceMetaData;
import org.md2k.mcerebrumapi.core.datakitapi.datasource.unit.MCUnit;
import org.md2k.phonesensor.MCSensorManager;
import org.md2k.phonesensor.SensorType;

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
class CreateDataSource {
    protected static DataSourceRegister create(Context context, SensorType sensorType){
        switch (sensorType){
            case ACCELEROMETER:
                return accelerometer(context);
            case ACCELEROMETER_LINEAR:
                return accelerometerLinear(context);
            case ACCELEROMETER_UNCALIBRATED:
                return accelerometerUncalibrated(context);
            case BATTERY:
                return battery(context);
            case GYROSCOPE:
                return gyroscope(context);
            case GYROSCOPE_UNCALIBRATED:
                return gyroscopeUncalibrated(context);
            case GRAVITY:
                return gravity(context);
            default:
                return null;
        }
    }
    private static DataSourceRegister accelerometer(Context context){
        HashMap<String, String> sensorInfo = MCSensorManager.getInstance(context).getSensor(SensorType.ACCELEROMETER).getSensorInfo();
        if(sensorInfo==null) sensorInfo = new HashMap<>();
        return DataSource.registerBuilder()
                .setDataType(MCDataType.POINT)
                .setSampleType(MCSampleType.DOUBLE_ARRAY)
                .addDataDescriptor(DataDescriptor.builder().setName("Accelerometer X").setDescription("Acceleration force along the x axis (including gravity) in m/s^2").setUnit(MCUnit.METER_PER_SECOND_SQUARED).build())
                .addDataDescriptor(DataDescriptor.builder().setName("Accelerometer Y").setDescription("Acceleration force along the y axis (including gravity) in m/s^2").setUnit(MCUnit.METER_PER_SECOND_SQUARED).build())
                .addDataDescriptor(DataDescriptor.builder().setName("Accelerometer Z").setDescription("Acceleration force along the z axis (including gravity) in m/s^2").setUnit(MCUnit.METER_PER_SECOND_SQUARED).build())
                .setDataSourceType(DataSourceType.ACCELEROMETER)
                .setPlatformType(PlatformType.PHONE)
                .setDataSourceMetaData(DataSourceMetaData.builder()
                        .setName("Accelerometer")
                        .setDescription("Measures the acceleration force in m/s2 that is applied to a device on all three physical axes (x, y, and z), including the force of gravity in m/^2.")
                        .setMetaData(sensorInfo)
                        .build())
                .build();
    }
    private static DataSourceRegister accelerometerLinear(Context context){
        HashMap<String, String> sensorInfo = MCSensorManager.getInstance(context).getSensor(SensorType.ACCELEROMETER_LINEAR).getSensorInfo();
        if(sensorInfo==null) sensorInfo = new HashMap<>();
        return DataSource.registerBuilder()
                .setDataType(MCDataType.POINT)
                .setSampleType(MCSampleType.DOUBLE_ARRAY)
                .addDataDescriptor(DataDescriptor.builder().setName("Accelerometer X").setDescription("Acceleration force along the x axis (excluding gravity) in m/s^2").setUnit(MCUnit.METER_PER_SECOND_SQUARED).build())
                .addDataDescriptor(DataDescriptor.builder().setName("Accelerometer Y").setDescription("Acceleration force along the y axis (excluding gravity) in m/s^2").setUnit(MCUnit.METER_PER_SECOND_SQUARED).build())
                .addDataDescriptor(DataDescriptor.builder().setName("Accelerometer Z").setDescription("Acceleration force along the z axis (excluding gravity) in m/s^2").setUnit(MCUnit.METER_PER_SECOND_SQUARED).build())
                .setDataSourceType(DataSourceType.ACCELEROMETER_LINEAR)
                .setPlatformType(PlatformType.PHONE)
                .setDataSourceMetaData(DataSourceMetaData.builder()
                        .setName("Accelerometer (Linear)")
                        .setDescription("Measures the acceleration force in m/s2 that is applied to a device on all three physical axes (x, y, and z), including the force of gravity in m/^2.")
                        .setMetaData(sensorInfo)
                        .build())
                .build();
    }
    private static DataSourceRegister accelerometerUncalibrated(Context context){
        HashMap<String, String> sensorInfo = MCSensorManager.getInstance(context).getSensor(SensorType.ACCELEROMETER_UNCALIBRATED).getSensorInfo();
        if(sensorInfo==null) sensorInfo = new HashMap<>();
        return DataSource.registerBuilder()
                .setDataType(MCDataType.POINT)
                .setSampleType(MCSampleType.DOUBLE_ARRAY)
                .addDataDescriptor(DataDescriptor.builder().setName("Accelerometer X").setDescription("Measured acceleration along the X axis without any bias compensation in m/s^2").setUnit(MCUnit.METER_PER_SECOND_SQUARED).build())
                .addDataDescriptor(DataDescriptor.builder().setName("Accelerometer Y").setDescription("Measured acceleration along the Y axis without any bias compensation. in m/s^2").setUnit(MCUnit.METER_PER_SECOND_SQUARED).build())
                .addDataDescriptor(DataDescriptor.builder().setName("Accelerometer Z").setDescription("Measured acceleration along the Z axis without any bias compensation. in m/s^2").setUnit(MCUnit.METER_PER_SECOND_SQUARED).build())
                .addDataDescriptor(DataDescriptor.builder().setName("Bias X").setDescription("Measured acceleration along the X axis with estimated bias compensation in m/s^2").setUnit(MCUnit.METER_PER_SECOND_SQUARED).build())
                .addDataDescriptor(DataDescriptor.builder().setName("Bias Y").setDescription("Measured acceleration along the Y axis with estimated bias compensation in m/s^2").setUnit(MCUnit.METER_PER_SECOND_SQUARED).build())
                .addDataDescriptor(DataDescriptor.builder().setName("Bias Z").setDescription("Measured acceleration along the Z axis with estimated bias compensation. in m/s^2").setUnit(MCUnit.METER_PER_SECOND_SQUARED).build())
                .setDataSourceType(DataSourceType.ACCELEROMETER_UNCALIBRATED)
                .setPlatformType(PlatformType.PHONE)
                .setDataSourceMetaData(DataSourceMetaData.builder()
                        .setName("Accelerometer (Uncalibrated)")
                        .setDescription("Measures the acceleration force in m/s2 that is applied to a device on all three physical axes (x, y, and z), without bias compensation in m/^2.")
                        .setMetaData(sensorInfo)
                        .build())
                .build();
    }
    private static DataSourceRegister battery(Context context){
        HashMap<String, String> sensorInfo = MCSensorManager.getInstance(context).getSensor(SensorType.BATTERY).getSensorInfo();
        if(sensorInfo==null) sensorInfo = new HashMap<>();
        return DataSource.registerBuilder()
                .setDataType(MCDataType.POINT)
                .setSampleType(MCSampleType.DOUBLE_ARRAY)
                .addDataDescriptor(DataDescriptor.builder().setName("Battery Level").setDescription("Battery Level in percentage").setUnit(MCUnit.PERCENTAGE).build())
                .setDataSourceType(DataSourceType.BATTERY)
                .setPlatformType(PlatformType.PHONE)
                .setDataSourceMetaData(DataSourceMetaData.builder()
                        .setName("Battery Level")
                        .setDescription("Measures battery level of the phone in percentage")
                        .setMetaData(sensorInfo)
                        .build())
                .build();
    }
    private static DataSourceRegister gyroscope(Context context){
        HashMap<String, String> sensorInfo = MCSensorManager.getInstance(context).getSensor(SensorType.GYROSCOPE).getSensorInfo();
        if(sensorInfo==null) sensorInfo = new HashMap<>();
        return DataSource.registerBuilder()
                .setDataType(MCDataType.POINT)
                .setSampleType(MCSampleType.DOUBLE_ARRAY)
                .addDataDescriptor(DataDescriptor.builder().setName("Gyroscope X").setDescription("Rate of rotation around the x axis in rad/s").setUnit(MCUnit.RADIAN_PER_SECOND).build())
                .addDataDescriptor(DataDescriptor.builder().setName("Gyroscope Y").setDescription("Rate of rotation around the y axis in rad/s").setUnit(MCUnit.RADIAN_PER_SECOND).build())
                .addDataDescriptor(DataDescriptor.builder().setName("Gyroscope Z").setDescription("Rate of rotation around the z axis in rad/s").setUnit(MCUnit.RADIAN_PER_SECOND).build())
                .setDataSourceType(DataSourceType.GYROSCOPE)
                .setPlatformType(PlatformType.PHONE)
                .setDataSourceMetaData(DataSourceMetaData.builder()
                        .setName("Gyroscope")
                        .setDescription("Measures the acceleration force in m/s2 that is applied to a device on all three physical axes (x, y, and z), including the force of gravity in m/^2.")
                        .setMetaData(sensorInfo)
                        .build())
                .build();
    }
    private static DataSourceRegister gyroscopeUncalibrated(Context context){
        HashMap<String, String> sensorInfo = MCSensorManager.getInstance(context).getSensor(SensorType.GYROSCOPE_UNCALIBRATED).getSensorInfo();
        if(sensorInfo==null) sensorInfo = new HashMap<>();
        return DataSource.registerBuilder()
                .setDataType(MCDataType.POINT)
                .setSampleType(MCSampleType.DOUBLE_ARRAY)
                .addDataDescriptor(DataDescriptor.builder().setName("Gyroscope X").setDescription("Rate of rotation (without drift compensation) around the x axis in rad/s").setUnit(MCUnit.RADIAN_PER_SECOND).build())
                .addDataDescriptor(DataDescriptor.builder().setName("Gyroscope Y").setDescription("Rate of rotation (without drift compensation) around the y axis in rad/s").setUnit(MCUnit.RADIAN_PER_SECOND).build())
                .addDataDescriptor(DataDescriptor.builder().setName("Gyroscope Z").setDescription("Rate of rotation (without drift compensation) around the z axis in rad/s").setUnit(MCUnit.RADIAN_PER_SECOND).build())
                .addDataDescriptor(DataDescriptor.builder().setName("Bias X").setDescription("Estimated drift around the x axis in rad/s").setUnit(MCUnit.RADIAN_PER_SECOND).build())
                .addDataDescriptor(DataDescriptor.builder().setName("Bias Y").setDescription("Estimated drift around the y axis in rad/s").setUnit(MCUnit.RADIAN_PER_SECOND).build())
                .addDataDescriptor(DataDescriptor.builder().setName("Bias Z").setDescription("Estimated drift around the z axis in rad/s").setUnit(MCUnit.RADIAN_PER_SECOND).build())
                .setDataSourceType(DataSourceType.GYROSCOPE_UNCALIBRATED)
                .setPlatformType(PlatformType.PHONE)
                .setDataSourceMetaData(DataSourceMetaData.builder()
                        .setName("Gyroscope (Uncalibrated)")
                        .setDescription("Measures the acceleration force in m/s2 that is applied to a device on all three physical axes (x, y, and z), without bias compensation in m/^2.")
                        .setMetaData(sensorInfo)
                        .build())
                .build();
    }
    private static DataSourceRegister gravity(Context context){
        HashMap<String, String> sensorInfo = MCSensorManager.getInstance(context).getSensor(SensorType.GRAVITY).getSensorInfo();
        if(sensorInfo==null) sensorInfo = new HashMap<>();
        return DataSource.registerBuilder()
                .setDataType(MCDataType.POINT)
                .setSampleType(MCSampleType.DOUBLE_ARRAY)
                .addDataDescriptor(DataDescriptor.builder().setName("Gyroscope X").setDescription("Force of gravity along the x axis in m/s^2").setUnit(MCUnit.METER_PER_SECOND_SQUARED).build())
                .addDataDescriptor(DataDescriptor.builder().setName("Gyroscope Y").setDescription("Force of gravity along the y axis in m/s^2").setUnit(MCUnit.METER_PER_SECOND_SQUARED).build())
                .addDataDescriptor(DataDescriptor.builder().setName("Gyroscope Z").setDescription("Force of gravity along the z axis in m/s^2").setUnit(MCUnit.METER_PER_SECOND_SQUARED).build())
                .setDataSourceType(DataSourceType.GRAVITY)
                .setPlatformType(PlatformType.PHONE)
                .setDataSourceMetaData(DataSourceMetaData.builder()
                        .setName("Gravity")
                        .setDescription("Measures the acceleration force in m/s2 that is applied to a device on all three physical axes (x, y, and z), without bias compensation in m/^2.")
                        .setMetaData(sensorInfo)
                        .build())
                .build();
    }

}
