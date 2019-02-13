package org.md2k.library.phonesensor.sensor.bluetooth;

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
public class BluetoothData {
    private String name;
    private String address;
    private int rssi;
    private String bluetoothType;
    private String deviceTypeMajor;
    private String deviceType;

    BluetoothData(String bluetoothType, String name, String address, int rssi, String deviceTypeMajor, String deviceType) {
        this.bluetoothType = bluetoothType;
        this.name = name;
        this.address = address;
        this.rssi = rssi;
        this.deviceTypeMajor = deviceTypeMajor;
        this.deviceType = deviceType;
    }


    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getRssi() {
        return rssi;
    }

    public String getBluetoothType() {
        return bluetoothType;
    }

    public String getDeviceTypeMajor() {
        return deviceTypeMajor;
    }

    public String getDeviceType() {
        return deviceType;
    }
}