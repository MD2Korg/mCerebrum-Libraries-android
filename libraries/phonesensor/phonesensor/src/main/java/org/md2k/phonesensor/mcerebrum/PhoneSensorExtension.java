package org.md2k.phonesensor.mcerebrum;

import android.content.Context;

import org.md2k.mcerebrumapi.core.extensionapi.ConfigState;
import org.md2k.mcerebrumapi.core.extensionapi.library.ExtensionCallback;
import org.md2k.mcerebrumapi.core.extensionapi.library.IBackgroundProcess;
import org.md2k.mcerebrumapi.core.extensionapi.library.IConfigure;
import org.md2k.mcerebrumapi.core.extensionapi.library.IPermissionList;
import org.md2k.mcerebrumapi.core.extensionapi.library.MCExtensionAPILibrary;
import org.md2k.phonesensor.MCSensorManager;
import org.md2k.phonesensor.sensor.ISensor;

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
public class PhoneSensorExtension {
    public static MCExtensionAPILibrary createExtensionAPI(final Context context) {
        return MCExtensionAPILibrary.builder()
                .setId(Constants.ID)
                .setName(Constants.NAME)
                .setDescription(Constants.DESCRIPTION)
                .setIcon(null)
                .setVersion(Constants.VERSION_CODE, Constants.VERSION_NAME)
                .setPermissionList(getPermissions(context))
                .setConfigurationInterface(new IConfigure() {
                    @Override
                    public <T> ConfigState getConfigurationState() {
                        return getState(context);
                    }

                    @Override
                    public void setConfiguration(ExtensionCallback extensionCallback) {
                         extensionCallback.onSuccess(null);
                    }
                })
                .setBackgroundExecutionInterface(new IBackgroundProcess() {
                    @Override
                    public void start(Object param) {
                        //TODO:
                        PhoneSensorManager.getInstance(context).startBackground(param);
//                        phoneSensorManager.startBackground(param);
                    }

                    @Override
                    public void stop() {
                        PhoneSensorManager.getInstance(context).stopBackground();
                    }

                    @Override
                    public boolean isRunning() {
                        return PhoneSensorManager.getInstance(context).isRunning();
                    }
                })
                .build();
    }
    protected static String[] getPermissions(Context context){
        ArrayList<String> arrayList = new ArrayList<>();
        Configuration c = Configuration.read(context);
        if(c==null|| c.size()==0) return new String[0];
        for(int i = 0; i<c.size(); i++){
            ISensor iSensor = MCSensorManager.getInstance(context).getSensor(c.getSensorType(i));
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
    protected static ConfigState getState(Context context){
        Configuration current = Configuration.read(context);
        Configuration def=Configuration.readDefault(context);
        if(current==null || current.size()==0) return ConfigState.NOT_CONFIGURED;
        if(def==null || def.size()==0){
            return ConfigState.CONFIGURED;
        }else{
            if(def.equals(current)) return ConfigState.CONFIGURED;
            else return ConfigState.PARTIALLY_CONFIGURED;
        }
    }

}
