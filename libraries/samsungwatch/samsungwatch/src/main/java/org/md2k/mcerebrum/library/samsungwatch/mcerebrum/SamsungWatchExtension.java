package org.md2k.mcerebrum.library.samsungwatch.mcerebrum;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import org.md2k.mcerebrum.api.core.data.MCValue;
import org.md2k.mcerebrum.api.core.extensionapi.library.ExtensionCallback;
import org.md2k.mcerebrum.api.core.extensionapi.library.IBackgroundProcess;
import org.md2k.mcerebrum.api.core.extensionapi.library.IPermission;
import org.md2k.mcerebrum.api.core.extensionapi.library.IPermissionListWithConfig;
import org.md2k.mcerebrum.api.core.extensionapi.library.MCExtensionAPILibrary;

import java.util.List;

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
public class SamsungWatchExtension {
    private static String[] permissions= new String[]{
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN
    };

    public static MCExtensionAPILibrary createExtensionAPI(final Context context) {
        return MCExtensionAPILibrary.builder()
                .setId(org.md2k.mcerebrum.library.samsungwatch.BuildConfig.APPLICATION_ID)
                .setName("Samsung Watch")
                .setDescription("Library for collecting samsung watch data")
                .setIcon(null)
                .setVersion(org.md2k.mcerebrum.library.samsungwatch.BuildConfig.VERSION_CODE, org.md2k.mcerebrum.library.samsungwatch.BuildConfig.VERSION_NAME)
                .setPermissionList(permissions)
                .setPermissionListWithConfig(new IPermissionListWithConfig() {
                    @Override
                    public String[] getPermissionList(Object config) {
                        return new String[0];
                    }
                })
                .build();
    }
}