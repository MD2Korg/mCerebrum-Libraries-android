package org.md2k.library.phonesensor.mcerebrum;

import android.content.Context;

import com.google.gson.Gson;

import org.md2k.mcerebrum.api.core.extensionapi.ConfigState;
import org.md2k.library.phonesensor.MCSensorManager;
import org.md2k.library.phonesensor.sensor.ISensor;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

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
class ConfigurationManager {
    protected static Configuration getConfigurationFromObject(Object param){
        try {
            Gson gson = new Gson();
            return gson.fromJson((String) param, Configuration.class);
        }catch (Exception e){
            return null;
        }
    }
    protected static String[] getRequiredPermissionList(Context context, Object param){
        ArrayList<String> arrayList = new ArrayList<>();
        Configuration c = getConfigurationFromObject(param);
        if(c==null|| c.size()==0) return new String[0];
        for(int i = 0; i<c.size(); i++){
            Configuration.Sensor s = c.getSensor(i);
            MCSensorManager mcSensorManager = MCSensorManager.getInstance(context);
            ISensor iSensor = MCSensorManager.getInstance(context).getSensor(s.getType());
            String[] list = iSensor.getPermissionList();
            if(list==null || list.length==0) continue;
            for (String aList : list)
                if (!arrayList.contains(aList))
                    arrayList.add(aList);
        }
        String[] result = new String[arrayList.size()];
        for(int i=0;i<arrayList.size();i++)
            result[i]=arrayList.get(i);
        return result;
    }
    protected static ConfigState getConfigurationState(Object currentConfig, Object defaultConfig){
        Configuration current = getConfigurationFromObject(currentConfig);
        Configuration def=getConfigurationFromObject(defaultConfig);
        if(current==null || current.size()==0) return ConfigState.NOT_CONFIGURED;
        if(def==null || def.size()==0){
            return ConfigState.CONFIGURED;
        }else{
            if(def.equals(current)) return ConfigState.CONFIGURED;
            else return ConfigState.PARTIALLY_CONFIGURED;
        }
    }
    protected static String readConfigurationFromAsset(Context context) {
        String str=null;
        try {
            InputStream is = context.getAssets().open("phonesensor.default.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            str = new String(buffer);
            return str;
        } catch (IOException ex) {
        }
        return str;
    }

}
