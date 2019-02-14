package org.md2k.phonesensor.sensor.charging_state;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import org.md2k.phonesensor.PermissionCallback;
import org.md2k.phonesensor.sensor.Comparison;
import org.md2k.phonesensor.SensorType;
import org.md2k.phonesensor.sensor.MCAbstractSensor;

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
public class MCChargingState extends MCAbstractSensor {
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            boolean result;
            if(plugged==0) result = false;
            else result =true;
            setSample(System.currentTimeMillis(), result);
        }
    };

    public MCChargingState(Context context) {
        super(context, SensorType.CHARGING_STATE,null);
        setWriteOnChange(Comparison.notEqual());
    }
    public void setWriteAsReceived(){
        super.setWriteAsReceived();
    }
    public void setWriteFixed(double writeFrequency, TimeUnit timeUnit){
        super.setWriteFixed(writeFrequency, timeUnit);
    }
    public void setWriteOnChange(Comparison comparison){
        super.setWriteOnChange(comparison);
    }

    @Override
    public void startSensing() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
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
    public boolean hasPermission() {
        return true;
    }

    @Override
    public void getPermission(Activity activity, PermissionCallback permissionCallback) {
        permissionCallback.onSuccess();
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
        return p!=c;
    }

}
