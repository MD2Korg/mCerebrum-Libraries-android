package org.md2k.mcerebrum.library.samsungwatch.stress;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;

import org.md2k.mcerebrum.library.samsungwatch.DataCallback;
import org.md2k.mcerebrum.library.samsungwatch.MySharedPreference;
import org.md2k.mcerebrum.library.samsungwatch.SensorType;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
public class StressManager {
    private static final String DIR_NAME = "Samsung Health/Download";
    private static final String FILE_NAME = "com.samsung.shealth.stress";
    private String[] keys;

    public void readFromSDCard(final Context context, final DataCallback dataCallback) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    ArrayList<StressData> dataAll = readAll(context);
                    ArrayList<Object> data = new ArrayList<>();
                    Collections.sort(dataAll);
                    long lastSyncTimestamp = MySharedPreference.getLastSyncedTimestamp(context);
                    for (int i = 0; dataAll != null && i < dataAll.size(); i++) {
                        if (dataAll.get(i).create_time <= lastSyncTimestamp) continue;
                        data.add(dataAll.get(i));
                        MySharedPreference.setLastSyncedTimestamp(context, dataAll.get(i).create_time);
                    }
                    deleteDirectory(context);
                    dataCallback.onReceived(SensorType.STRESS, data);
                } catch (Exception ignored) {
                }
            }
        };

        thread.start();
    }

    private void deleteDirectory(Context context) {
        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        if (root == null) return;
        String dirPath = root + "/" + DIR_NAME;
        if (!FileUtils.isFileExists(dirPath)) return;
        if (!FileUtils.isDir(dirPath)) return;
        FileUtils.deleteAllInDir(dirPath);
    }

    private ArrayList<StressData> readAll(Context context) {
        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        ArrayList<StressData> stressData = new ArrayList<>();
        if (root == null) return null;
        String dirPath = root + "/" + DIR_NAME;
        if (!FileUtils.isFileExists(dirPath)) return null;
        if (!FileUtils.isDir(dirPath)) return null;
        List<File> files = FileUtils.listFilesInDir(dirPath);
        for (int i = 0; i < files.size(); i++) {
            if (!FileUtils.isDir(files.get(i).getPath())) continue;
            List<File> files1 = FileUtils.listFilesInDir(files.get(i).getPath());
            Log.d("abc", "abc");
            for (int j = 0; j < files1.size(); j++) {
                String fileName = files1.get(j).getName();
                if (fileName.contains(FILE_NAME)) {
                    ArrayList<StressData> temp = read(files1.get(j).getAbsolutePath());
                    addIfNew(stressData, temp);
                    stressData.addAll(temp);
                }
            }
        }
        return stressData;
    }

    private void addIfNew(ArrayList<StressData> org, ArrayList<StressData> newData) {
        boolean flag;
        for (int i = 0; i < newData.size(); i++) {
            flag = false;
            for (int j = 0; j < org.size(); j++) {
                if (isEqual(org.get(j), newData.get(i))) {
                    flag = true;
                    break;
                }
            }
            if (!flag)
                org.add(newData.get(i));
        }
    }

    private boolean isEqual(StressData a, StressData b) {
        if (a.create_time != b.create_time) return false;
        if (a.start_time != b.start_time) return false;
        if (a.end_time != b.end_time) return false;
        if (a.update_time != b.update_time) return false;
        if (a.score != b.score) return false;
        if (a.min != b.min) return false;
        if (a.max != b.max) return false;
        if (a.binning_data != null && !a.binning_data.equals(b.binning_data)) return false;
        if (b.binning_data != null && !b.binning_data.equals(a.binning_data)) return false;

        if (a.deviceuuid != null && !a.deviceuuid.equals(b.deviceuuid)) return false;
        if (b.deviceuuid != null && !b.deviceuuid.equals(a.deviceuuid)) return false;

        if (a.pkg_name != null && !a.pkg_name.equals(b.pkg_name)) return false;
        if (b.pkg_name != null && !b.pkg_name.equals(a.pkg_name)) return false;

        if (a.time_offset != null && !a.time_offset.equals(b.time_offset)) return false;
        if (b.time_offset != null && !b.time_offset.equals(a.time_offset)) return false;

        if (a.tag_id != null && !a.tag_id.equals(b.tag_id)) return false;
        if (b.tag_id != null && !b.tag_id.equals(a.tag_id)) return false;

        if (a.custom != null && !a.custom.equals(b.custom)) return false;
        if (b.custom != null && !b.custom.equals(a.custom)) return false;

        if (a.comment != null && !a.comment.equals(b.comment)) return false;
        if (b.comment != null && !b.comment.equals(a.comment)) return false;

        if (a.datauuid != null && !a.datauuid.equals(b.datauuid)) return false;
        if (b.datauuid != null && !b.datauuid.equals(a.datauuid)) return false;

        if (a.algorithm != null && !a.algorithm.equals(b.algorithm)) return false;
        if (b.algorithm != null && !b.algorithm.equals(a.algorithm)) return false;
        return true;
    }

    private int getIndex(String key) {
        for (int i = 0; i < keys.length; i++) {
            if (keys[i].equalsIgnoreCase(key)) return i;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    private ArrayList<StressData> read(String filePath) {
        List<String> list = FileIOUtils.readFile2List(filePath);
        ArrayList<StressData> arrayList = new ArrayList<>();
        Log.d("abc", "abc");
        keys = list.get(1).split(",");
        for (int i = 2; i < list.size(); i++) {
            try {
                String[] splits = list.get(i).split(",");
//                if (splits.length != 16) continue;
                StressData stressData = new StressData();

                stressData.score = getDouble(splits[getIndex("score")]);
                stressData.min = getDouble(splits[getIndex("min")]);
                stressData.max = getDouble(splits[getIndex("max")]);

                stressData.algorithm = splits[getIndex("algorithm")];
                stressData.comment = splits[getIndex("comment")];
                stressData.tag_id = splits[getIndex("tag_id")];
                stressData.binning_data = splits[getIndex("binning_data")];
                stressData.deviceuuid = splits[getIndex("deviceuuid")];
                stressData.datauuid = splits[getIndex("datauuid")];
                stressData.custom = splits[getIndex("custom")];
                stressData.pkg_name = splits[getIndex("pkg_name")];
                stressData.time_offset = splits[getIndex("time_offset")];

                stressData.end_time = convertTime(splits[getIndex("end_time")]);
                stressData.start_time = convertTime(splits[getIndex("start_time")]);
                stressData.update_time = convertTime(splits[getIndex("update_time")]);
                stressData.create_time = convertTime(splits[getIndex("create_time")]);

                arrayList.add(stressData);
            } catch (Exception e) {
                Log.e("abc", "error reading file");
            }
        }
        return arrayList;
    }

    private double getDouble(String str) {
        if (str == null || str.trim().length() == 0) return -1;
        return Double.parseDouble(str);
    }

    private long convertTime(String str) {
        if (str == null || str.trim().length() == 0) return 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
        try {
            Date mDate = sdf.parse(str);
            return mDate.getTime();

        } catch (ParseException ignored) {
        }
        return 0;
    }
}
