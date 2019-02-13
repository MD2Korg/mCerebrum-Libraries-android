package org.md2k.library.phonesensor.mcerebrum;

import org.md2k.library.phonesensor.SensorType;
import org.md2k.library.phonesensor.sensor.Comparison;
import org.md2k.library.phonesensor.sensor.WriteType;

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
    public static final String sensorType = "sensorType";
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
/*
    public HashMap<String, String>[] getSensors() {
        return sensors;
    }*/
    int size(){
        if(sensors==null) return 0;
        else return sensors.length;
    }
    Sensor getSensor(int i){
        return new Sensor(sensors[i]);
    }
    public class Sensor{
        private HashMap<String, String> sensor;
        Sensor(HashMap<String, String> sensor){
            this.sensor= sensor;
        }
        public SensorType getType() {
            return SensorType.valueOf(sensor.get(sensorType));
        }

        public double getReadFrequency() {
            return Double.parseDouble(sensor.get(Configuration.READ_FREQUENCY));
        }

        public TimeUnit getReadTimeUnit() {
            return TimeUnit.valueOf(sensor.get(READ_TIME_UNIT));
        }

        public WriteType getWriteType() {
            return WriteType.valueOf(sensor.get(WRITE_TYPE));
        }

        public double getWriteFrequency() {
            return Double.parseDouble(sensor.get(Configuration.WRITE_FREQUENCY));
        }

        public TimeUnit getWriteTimeUnit() {
            return TimeUnit.valueOf(sensor.get(WRITE_TIME_UNIT));
        }
        public Comparison getWriteOnChangeComparison(){
            if(sensor.get(WRITE_ON_CHANGE_TYPE).equals(WRITE_ON_CHANGE_TYPE_OPTION_NOT_EQUAL))
                return Comparison.notEqual();
            else{
                return Comparison.sampleDiff(Double.parseDouble(sensor.get(WRITE_ON_CHANGE_VALUE)));
            }
        }

    }
    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Configuration))return false;
        Configuration a = (Configuration) obj;
        return a.equals(this);
    }

}
