package org.md2k.phonesensor;

import android.content.Context;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

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
public class SettingsManager {
    private static final String DEFAULT_FILENAME = "phonesensor_default.json";
    private static final String FILENAME = "phonesensor.json";

    public static Settings readDefault(Context context){
        Settings settings = readFileInternal(context, DEFAULT_FILENAME);
        if(settings !=null) return settings;
        return readAsset(context, DEFAULT_FILENAME);
    }
    public static void deleteDefault(Context context){
        delete(context, DEFAULT_FILENAME);
    }
    public static void delete(Context context){
        delete(context, FILENAME);
    }
    private static void delete(Context context, String fileName){
        File dir = context.getFilesDir();
        File file = new File(dir, fileName);
        file.delete();
    }
    public static Settings read(Context context){
        Settings settings = readFileInternal(context, FILENAME);
        if(settings !=null) return settings;
        return readDefault(context);
    }

    public static void writeDefault(Context context, Settings settings) {
        writeFileInternal(context, DEFAULT_FILENAME, settings);
    }
    public static void write(Context context, Settings settings) {
        writeFileInternal(context, FILENAME, settings);
    }

    private static void writeFileInternal(Context context, String fileName, Settings settings) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE)));
            Gson gson = new Gson();
            String s = gson.toJson(settings);
            writer.write(s);
        } catch (IOException ignored) {
        }finally{
            if(writer!=null){
                try {
                    writer.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    private static Settings readFileInternal(Context context, String fileName) {
        Settings s = null;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(context.openFileInput(fileName)));
            Gson gson = new Gson();
            s = gson.fromJson(reader, Settings.class);
        } catch (IOException ignored) {
        }finally{
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException ignored) {
                }
            }
        }
        return s;
    }
    private static Settings readAsset(Context context, String fileName) {
        BufferedReader reader = null;
        Settings s=null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open(fileName)));
            Gson gson = new Gson();
            s = gson.fromJson(reader, Settings.class);

        } catch (IOException ignored) {
        }finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {
                }
            }
        }
        return s;
    }

}
