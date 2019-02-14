package org.md2k.phonesensor.mcerebrum;

import android.content.Context;

import org.md2k.mcerebrumapi.core.file.MCFile;
import org.md2k.phonesensor.SensorType;
import org.md2k.phonesensor.sensor.Comparison;
import org.md2k.phonesensor.sensor.WriteType;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

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
public class Configuration {
    public static final String SENSOR_TYPE = "sensorType";
    public static final String READ_FREQUENCY = "readFrequency";
    public static final String READ_TIME_UNIT = "readTimeUnit";
    public static final String WRITE_TYPE = "writeType";
    public static final String WRITE_FREQUENCY = "writeFrequency";
    public static final String WRITE_TIME_UNIT = "readTimeUnit";
    public static final String WRITE_ON_CHANGE_TYPE = "writeOnChangeType";
    public static final String WRITE_ON_CHANGE_TYPE_OPTION_NOT_EQUAL = "not_equal";
    public static final String WRITE_ON_CHANGE_TYPE_OPTION_SAMPLE_DIFF = "sample_difference";
    public static final String WRITE_ON_CHANGE_VALUE = "writeOnChangeValue";

    private HashMap<String, String>[] sensors;
    private HashMap<String, String> get(SensorType sensorType){
        for (HashMap<String, String> sensor : sensors) {
            if (sensorType.name().equals(sensor.get(SENSOR_TYPE))) return sensor;
        }
        return null;
    }
    int size(){
        if(sensors==null) return 0;
        else return sensors.length;
    }
    public double getReadFrequency(SensorType sensorType) {
        return Double.parseDouble(get(sensorType).get(Configuration.READ_FREQUENCY));
    }

    public TimeUnit getReadTimeUnit(SensorType sensorType) {
        return TimeUnit.valueOf(get(sensorType).get(READ_TIME_UNIT));
    }

    public WriteType getWriteType(SensorType sensorType) {
        return WriteType.valueOf(get(sensorType).get(WRITE_TYPE));
    }

    public double getWriteFrequency(SensorType sensorType) {
        return Double.parseDouble(get(sensorType).get(Configuration.WRITE_FREQUENCY));
    }

    public TimeUnit getWriteTimeUnit(SensorType sensorType) {
        return TimeUnit.valueOf(get(sensorType).get(WRITE_TIME_UNIT));
    }
    public Comparison getWriteOnChangeComparison(SensorType sensorType){
        if(get(sensorType).get(WRITE_ON_CHANGE_TYPE).equals(WRITE_ON_CHANGE_TYPE_OPTION_NOT_EQUAL))
            return Comparison.notEqual();
        else{
            return Comparison.sampleDiff(Double.parseDouble(get(sensorType).get(WRITE_ON_CHANGE_VALUE)));
        }
    }

    private static String getConfigDirectory(Context context){
        String result = context.getFilesDir().getAbsoluteFile()+"config"+File.separator+Constants.ID;
        File f = new File(result);
        f.mkdirs();
        return result+File.separator;
    }
    public static Configuration read(Context context){
        String fileDir = getConfigDirectory(context);
        return MCFile.readJson(fileDir+"config.json", Configuration.class);
    }
    public static Configuration readDefault(Context context){
        String fileDir = getConfigDirectory(context);
        return MCFile.readJson(fileDir+"default_config.json", Configuration.class);
    }
    public static void write(Context context, Configuration configuration){
        String fileDir = getConfigDirectory(context);
        MCFile.writeJson(fileDir,"config.json", configuration);
    }

    public SensorType getSensorType(int i) {
        return SensorType.valueOf(sensors[i].get(SENSOR_TYPE));
    }
}
