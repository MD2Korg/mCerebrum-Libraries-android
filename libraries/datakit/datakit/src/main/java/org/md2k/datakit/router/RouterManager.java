package org.md2k.datakit.router;

import android.util.SparseArray;

import org.md2k.mcerebrumapi.core.data.DataArray;
import org.md2k.mcerebrumapi.core.datakitapi.datasource.DataSource;
import org.md2k.mcerebrumapi.core.datakitapi.datasource.DataSourceResult;
import org.md2k.mcerebrumapi.core.datakitapi.ipc.IDataKitRemoteCallback;
import org.md2k.datakit.router.data.RouterData;
import org.md2k.datakit.router.datasource.RouterDataSource;

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
public class RouterManager {
    private RouterData routerData;
    private RouterDataSource routerDataSource;

    public RouterManager(){
        this.routerData = new RouterData();
        this.routerDataSource = new RouterDataSource();
    }
    public void start(){
        routerDataSource.start();
        routerData.start();
    }
    public void stop(){
        routerDataSource.stop();
        routerData.stop();
    }
    public void publish(SparseArray<DataArray> data){
        routerData.publish(data);
    }
    public void subscribe(int dsId, IDataKitRemoteCallback iDataKitRemoteCallback){
        routerData.subscribe(dsId, iDataKitRemoteCallback);
    }
    public void unsubscribe(IDataKitRemoteCallback iDataKitRemoteCallback){
        routerData.unsubscribe(iDataKitRemoteCallback);
        routerDataSource.unsubscribe(iDataKitRemoteCallback);
    }
    public void publish(DataSourceResult dataSourceResult){
        routerDataSource.publish(dataSourceResult);
    }
    public void subscribe(DataSource dataSource, IDataKitRemoteCallback iDataKitRemoteCallback){
        routerDataSource.subscribe(dataSource, iDataKitRemoteCallback);
    }
}
