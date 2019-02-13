package org.md2k.library.phonesensor.sensor.gps_status;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;

import org.md2k.library.phonesensor.sensor.Comparison;
import org.md2k.library.phonesensor.SensorType;
import org.md2k.library.phonesensor.enable.EnableCallback;
import org.md2k.library.phonesensor.enable.Enabler;
import org.md2k.library.phonesensor.enable.SensorEnabler;
import org.md2k.library.phonesensor.sensor.MCAbstractSensor;

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
public class MCGPSStatus extends MCAbstractSensor {
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (LocationManager.PROVIDERS_CHANGED_ACTION.equals(action)) {
                setSample(System.currentTimeMillis(), isOn());
            }
        }
    };
    public boolean isOn(){
        final LocationManager manager = (LocationManager) context.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
    public void turnOn(){
        turnOn(new EnableCallback() {
            @Override
            public void onSuccess() {
                //TODO:
            }

            @Override
            public void onError() {
                //TODO:
            }
        });
    }
    public void turnOn(EnableCallback enableCallback){
        Enabler enabler = new Enabler(context, SensorEnabler.GPS, enableCallback);
        enabler.requestEnable();
    }

    public void setWriteOnChange(){
        super.setWriteOnChange(Comparison.notEqual());
    }

    public MCGPSStatus(Context context) {
        super(context, SensorType.GPS_STATUS,new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION});
        setWriteOnChange();
    }

    @Override
    public void startSensing() {
        setSample(System.currentTimeMillis(), isOn());
        IntentFilter filter = new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION);
        context.registerReceiver(mBroadcastReceiver, filter);
    }

    @Override
    public void stopSensing() {
        context.unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    public boolean isSupported() {
        return true;
    }

    @Override
    public HashMap<String, String> getSensorInfo() {
        HashMap<String, String> h = new HashMap<>();
        return h;
    }

    @Override
    protected boolean isChanged(Object prevSample, Object curSample, Comparison comparison) {
        boolean p = (boolean) prevSample;
        boolean c = (boolean) curSample;
        return p != c;
    }

}
