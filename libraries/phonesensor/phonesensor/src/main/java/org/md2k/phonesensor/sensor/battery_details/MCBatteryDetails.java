package org.md2k.phonesensor.sensor.battery_details;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

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
public class MCBatteryDetails extends MCAbstractSensor {
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Read Battery
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            int temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);
            int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);
            int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, 0);
            int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);
            MCBatteryDetailsSample data = new MCBatteryDetailsSample(
                level, scale, temperature, voltage, getPlugged(plugged),getStatus(status), getHealth(health)
            );

            setSample(System.currentTimeMillis(), data);
        }
    };
    public void setWriteAsReceived(){
        super.setWriteAsReceived();
    }
    public void setWriteFixed(double writeFrequency, TimeUnit timeUnit){
        super.setWriteFixed(writeFrequency, timeUnit);
    }
    public void setWriteOnChange(Comparison comparison){
        super.setWriteOnChange(comparison);
    }

    private String getStatus(int status){
        switch (status){
            case BatteryManager.BATTERY_STATUS_CHARGING: return "CHARGING";
            case BatteryManager.BATTERY_STATUS_DISCHARGING: return "DISCHARGING";
            case BatteryManager.BATTERY_STATUS_FULL: return "FULL";
            case BatteryManager.BATTERY_STATUS_NOT_CHARGING: return "NOT_CHARGING";
            case BatteryManager.BATTERY_STATUS_UNKNOWN:
            default:
            return "UNKNOWN";
        }
    }
    private String getHealth(int health){
        switch (health){
            case BatteryManager.BATTERY_HEALTH_COLD: return "COLD";
            case BatteryManager.BATTERY_HEALTH_DEAD: return "DEAD";
            case BatteryManager.BATTERY_HEALTH_GOOD: return "GOOD";
            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE: return "OVER_VOLTAGE";
            case BatteryManager.BATTERY_HEALTH_OVERHEAT: return "OVERHEAT";
            case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE: return "UNSPECIFIED_FAILURE";
            case BatteryManager.BATTERY_HEALTH_UNKNOWN:
            default:
                return "UNKNOWN";
        }
    }

    private String getPlugged(int plugged){
        switch(plugged){
            case BatteryManager.BATTERY_PLUGGED_AC: return "AC";
            case BatteryManager.BATTERY_PLUGGED_USB: return "USB";
            case BatteryManager.BATTERY_PLUGGED_WIRELESS: return "WIRELESS";
            default: return "NONE";
        }
    }

    public MCBatteryDetails(Context context) {
        super(context, SensorType.BATTERY_DETAILS,null);
        setWriteFixed(1, TimeUnit.MINUTES);
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
    public HashMap<String, String> getSensorInfo() {
        BatteryManager b;
        HashMap<String, String> h = new HashMap<>();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            b = (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);
            int chargeCounter = b.getIntProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER);
            h.put("CAPACITY", Integer.toString(chargeCounter));
        }
        return h;
    }

    @Override
    protected boolean isChanged(Object prevSample, Object curSample, Comparison comparison) {
        MCBatteryDetailsSample p = (MCBatteryDetailsSample) prevSample;
        MCBatteryDetailsSample c = (MCBatteryDetailsSample) curSample;
        switch (comparison.getComparisonType()){
            case NOT_EQUAL: return isNotEqual(p,c);
            case SAMPLE_DIFFERENCE: return hasSampleDifference(p,c,comparison.getValue());
            default:return true;
        }
    }
    private boolean isNotEqual(MCBatteryDetailsSample p, MCBatteryDetailsSample c){
        if(!p.getHealth().equals(c.getHealth())) return true;
        if(!p.getPlugged().equals(c.getPlugged())) return true;
        if(!p.getStatus().equals(c.getStatus())) return true;
        if(p.getLevel()!=c.getLevel()) return true;
        if(p.getScale()!=c.getScale()) return true;
        if(p.getTemperature()!=c.getTemperature()) return true;
        return p.getVoltage() != c.getVoltage();
    }

    private boolean hasSampleDifference(MCBatteryDetailsSample p, MCBatteryDetailsSample c, double value){
        if(!p.getHealth().equals(c.getHealth())) return true;
        if(!p.getPlugged().equals(c.getPlugged())) return true;
        if(!p.getStatus().equals(c.getStatus())) return true;
        if(Math.abs(p.getLevel()-c.getLevel())>value) return true;
        if(Math.abs(p.getScale()-c.getScale())>value) return true;
        if(Math.abs(p.getTemperature()-c.getTemperature())>value) return true;
        return Math.abs(p.getVoltage() - c.getVoltage()) > value;
    }
}
