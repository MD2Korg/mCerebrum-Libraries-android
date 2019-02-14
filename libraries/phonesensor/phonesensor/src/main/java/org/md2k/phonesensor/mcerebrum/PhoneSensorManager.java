package org.md2k.phonesensor.mcerebrum;

import android.content.Context;
import android.graphics.Bitmap;

import org.md2k.mcerebrumapi.core.data.MCData;
import org.md2k.mcerebrumapi.core.datakitapi.MCDataAPI;
import org.md2k.mcerebrumapi.core.datakitapi.ipc.authenticate.ConnectionCallback;
import org.md2k.mcerebrumapi.core.datakitapi.ipc.insert_datasource.Registration;
import org.md2k.phonesensor.MCSensorManager;
import org.md2k.phonesensor.SensorType;
import org.md2k.phonesensor.sensor.EventListener;
import org.md2k.phonesensor.sensor.ISensor;
import org.md2k.phonesensor.sensor.MCAbstractNativeSensor;
import org.md2k.phonesensor.sensor.WriteType;
import org.md2k.phonesensor.sensor.activity_type.MCActivityType;
import org.md2k.phonesensor.sensor.location.MCLocation;

import java.util.HashMap;
import java.util.Map;

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
final class PhoneSensorManager {
    private static PhoneSensorManager instance;
    private Context context;
    private boolean isRunning;
    private HashMap<String, EventListener> iSensorEvents;
    private ConnectionCallback connectionCallback = new ConnectionCallback() {
        @Override
        public void onSuccess() {
            Configuration config = Configuration.read(context);
            for (int i = 0; i < config.size(); i++) {
                SensorType sensorType = config.getSensorType(i);
                ISensor iSensor = setConfig(config, sensorType);
                final Registration r = MCDataAPI.registerDataSource(CreateDataSource.create(context, sensorType));
                EventListener eventListener = createListener(r);
                iSensorEvents.put(sensorType.name(), eventListener);
                iSensor.start(eventListener);
            }
        }

        @Override
        public void onError(int status) {
            stopBackground();
        }
    };

    protected static PhoneSensorManager getInstance(Context context) {
        if (instance == null)
            instance = new PhoneSensorManager(context.getApplicationContext());
        return instance;
    }

    private ISensor setConfig(Configuration sensor, SensorType sensorType) {
        ISensor iSensor = MCSensorManager.getInstance(context).getSensor(sensorType);
        switch (sensorType) {
            case ACCELEROMETER:
            case GYROSCOPE:
            case GRAVITY:
                ((MCAbstractNativeSensor) iSensor).setReadFrequency(sensor.getReadFrequency(sensorType), sensor.getReadTimeUnit(sensorType));
                if (sensor.getWriteType(sensorType) == WriteType.AS_RECEIVED)
                    ((MCAbstractNativeSensor) iSensor).setWriteAsReceived();
                else if (sensor.getWriteType(sensorType) == WriteType.FIXED)
                    ((MCAbstractNativeSensor) iSensor).setWriteFixed(sensor.getWriteFrequency(sensorType), sensor.getWriteTimeUnit(sensorType));
                else if (sensor.getWriteType(sensorType) == WriteType.ON_CHANGE) {
                    ((MCAbstractNativeSensor) iSensor).setWriteOnChange(sensor.getWriteOnChangeComparison(sensorType));
                }
                break;
            case ACTIVITY_TYPE:
                ((MCActivityType) iSensor).setReadFrequency(sensor.getReadFrequency(sensorType), sensor.getReadTimeUnit(sensorType));
                ((MCActivityType) iSensor).setWriteAsReceived();
                if (sensor.getWriteType(sensorType) == WriteType.AS_RECEIVED)
                    ((MCActivityType) iSensor).setWriteAsReceived();
                else if (sensor.getWriteType(sensorType) == WriteType.FIXED)
                    ((MCActivityType) iSensor).setWriteFixed(sensor.getWriteFrequency(sensorType), sensor.getWriteTimeUnit(sensorType));
                else if (sensor.getWriteType(sensorType) == WriteType.ON_CHANGE) {
                    ((MCActivityType) iSensor).setWriteOnChange(sensor.getWriteOnChangeComparison(sensorType));
                }
                break;
            case LOCATION:
                ((MCLocation) iSensor).setReadFrequency(sensor.getReadFrequency(sensorType), sensor.getReadTimeUnit(sensorType));
                ((MCLocation) iSensor).setWriteAsReceived();
                break;
        }
        return iSensor;
    }

    private PhoneSensorManager(Context context) {
        this.context = context;
        isRunning = false;
        MCDataAPI.init(context);
        iSensorEvents = new HashMap<>();
    }


    protected void startBackground(Object o) {
        if (isRunning) return;
        isRunning = true;
        MCDataAPI.connect(connectionCallback);
    }

    protected void stopBackground() {
        if (!isRunning) return;
        for (Map.Entry<String, EventListener> entry : iSensorEvents.entrySet()) {
            SensorType s = SensorType.valueOf(entry.getKey());
            MCSensorManager.getInstance(context).getSensor(s).stop(entry.getValue());
        }
        isRunning = false;
        MCDataAPI.disconnect(connectionCallback);
    }

    protected boolean isRunning() {
        return isRunning;
    }


    private EventListener createListener(final Registration r) {
        return new EventListener() {
            @Override
            public void onChange(long timestamp, Object sample) {
                MCData data = null;
                switch (r.getDataSource().getSampleType()) {
                    case INT_ARRAY:
                        data = MCData.createPointIntArray(timestamp, (int[]) sample);
                        break;
                    case BYTE_ARRAY:
                        data = MCData.createPointByteArray(timestamp, (byte[]) sample);
                        break;
                    case LONG_ARRAY:
                        data = MCData.createPointLongArray(timestamp, (long[]) sample);
                        break;
                    case DOUBLE_ARRAY:
                        data = MCData.createPointDoubleArray(timestamp, (double[]) sample);
                        break;
                    case STRING_ARRAY:
                        data = MCData.createPointStringArray(timestamp, (String[]) sample);
                        break;
                    case BOOLEAN_ARRAY:
                        data = MCData.createPointBooleanArray(timestamp, (boolean[]) sample);
                        break;
                    case ENUM_ARRAY:
//                        data = MCData.createPoint(timestamp, (enum[])sample);
                        break;
                    case OBJECT:
                        data = MCData.createPointObject(timestamp, sample);
                        break;
                    default:
                        data = null;
                        break;
                }
                if (data != null)
                    MCDataAPI.insertData(r, data);
            }
        };
    }
}
