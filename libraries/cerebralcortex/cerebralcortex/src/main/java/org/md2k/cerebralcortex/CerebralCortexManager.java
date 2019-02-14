package org.md2k.cerebralcortex;
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

import android.content.Context;
import android.os.Looper;

import com.orhanobut.hawk.Hawk;

import org.md2k.cerebralcortex.cerebralcortexwebapi.CCWebAPICalls;
import org.md2k.cerebralcortex.cerebralcortexwebapi.interfaces.CerebralCortexWebApi;
import org.md2k.cerebralcortex.cerebralcortexwebapi.models.AuthResponse;
import org.md2k.cerebralcortex.cerebralcortexwebapi.models.MinioObjectStats;
import org.md2k.cerebralcortex.cerebralcortexwebapi.utils.ApiUtils;
import org.md2k.cerebralcortex.exception.MCExceptionConfigNotFound;
import org.md2k.cerebralcortex.exception.MCExceptionInternetConnection;
import org.md2k.cerebralcortex.exception.MCExceptionInvalidLogin;
import org.md2k.cerebralcortex.exception.MCExceptionNotLoggedIn;
import org.md2k.cerebralcortex.exception.MCExceptionServerDown;

import java.io.File;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class CerebralCortexManager {

    private Context context;
    private static CerebralCortexManager instance;
    private ServerInfo serverInfo;
    private CCWebAPICalls ccWebAPICalls;
    public static CerebralCortexManager getInstance(Context context){
        if(instance==null)
            instance = new CerebralCortexManager(context);
        return instance;
    }
    private CerebralCortexManager(Context context){
        this.context = context;
        Hawk.init(context).build();
        serverInfo=new ServerInfo();
    }
    private void loginExecute(final String serverAddress, final String userName, final String password, final CerebralCortexCallback cerebralCortexCallback){
        try {
        checkInternetConnection();
        checkServerUp(serverAddress);
        CerebralCortexWebApi ccService=ApiUtils.getCCService(serverAddress);
        ccWebAPICalls = new CCWebAPICalls(ccService);

        AuthResponse authResponse = ccWebAPICalls.authenticateUser(userName, password);
        if(authResponse==null)
            cerebralCortexCallback.onError(new MCExceptionInvalidLogin());
        else {
            serverInfo.setUserName(userName);
            serverInfo.setPassword(password);
            serverInfo.setServerAddress(serverAddress);
            serverInfo.setAccessToken(authResponse.getAccessToken());
            serverInfo.setLoggedIn(true);
            cerebralCortexCallback.onSuccess(true);
        }
    } catch (MCExceptionInternetConnection mcExceptionInternetConnection) {
        cerebralCortexCallback.onError(mcExceptionInternetConnection);
    } catch (MCExceptionServerDown mcExceptionServerDown) {
        cerebralCortexCallback.onError(mcExceptionServerDown);
    }

    }

    public void login(final String serverAddress, final String userName, final String password, final CerebralCortexCallback cerebralCortexCallback) {
        if(Looper.myLooper()==Looper.getMainLooper()){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    loginExecute(serverAddress, userName, password, cerebralCortexCallback);
                }
            }).start();

        }else{
            loginExecute(serverAddress, userName, password, cerebralCortexCallback);
        }
    }

    public void logout() {
        serverInfo.setLoggedIn(false);
    }


    public boolean isLoggedIn() {
        return serverInfo.getLoggedIn();
    }

    public ArrayList<FileInfo> getConfigurationFiles() throws MCExceptionInternetConnection, MCExceptionNotLoggedIn, MCExceptionServerDown {
        checkInternetConnection();
        checkLoginStatus();
        checkServerUp(serverInfo.getServerAddress());
        ArrayList<FileInfo> list = new ArrayList<>();
        List<MinioObjectStats> objectList = ccWebAPICalls.getObjectsInBucket(serverInfo.getAccessToken(), "configuration");
        if(objectList==null || objectList.size()==0)
            objectList= ccWebAPICalls.getObjectsInBucket(getAccessTokenWithLogin(serverInfo.getServerAddress(), serverInfo.getUserName(), serverInfo.getPassword()), "configuration");
        for(int i = 0; objectList!=null && i<objectList.size();i++) {
            FileInfo f = new FileInfo();
            f.setName(objectList.get(i).getObjectName());
            f.setSize(objectList.get(i).getSize());
            f.setLastModified(objectList.get(i).getLastModified());
            list.add(f);
        }
        return list;
    }
    private FileInfo getConfigurationFile(String fileName) throws MCExceptionInternetConnection, MCExceptionNotLoggedIn, MCExceptionServerDown, MCExceptionConfigNotFound {
        ArrayList<FileInfo> fileInfos = getConfigurationFiles();
        for(int i=0;i<fileInfos.size();i++){
            if(fileInfos.get(i).getName().equals(fileName)) return fileInfos.get(i);
        }
        throw new MCExceptionConfigNotFound();
    }
    private void downloadExecute(String fileName, CerebralCortexCallback cerebralCortexCallback){
        try {
//            Utils.init(context);
            checkInternetConnection();
            checkLoginStatus();
            checkServerUp(serverInfo.getServerAddress());
            FileInfo f = getConfigurationFile(fileName);
            String tempFileDir = context.getFilesDir().getAbsolutePath()+File.separator+"temp";
            String configFileDir = context.getFilesDir().getAbsolutePath()+File.separator+"config";
            File file = new File(tempFileDir);
            file.mkdirs();
            file = new File(configFileDir);
            file.mkdirs();
            boolean res = ccWebAPICalls.downloadMinioObject(serverInfo.getAccessToken(), "configuration", fileName, tempFileDir, fileName);
            if (!res)
                res = ccWebAPICalls.downloadMinioObject(getAccessTokenWithLogin(serverInfo.getServerAddress(), serverInfo.getUserName(), serverInfo.getPassword()), "configuration", fileName, tempFileDir, fileName);
            if (res) {
//                ZipUtils.unzipFile(tempFileDir+File.separator+fileName, configFileDir);
                serverInfo.setFileName(f.getName());
                serverInfo.setFileSize(f.getSize());
                serverInfo.setFileLastModified(f.getLastModified());
                cerebralCortexCallback.onSuccess(tempFileDir+ File.separator+fileName);
            }
        } catch (MCExceptionServerDown mcExceptionServerDown) {
            cerebralCortexCallback.onError(mcExceptionServerDown);
        } catch (MCExceptionNotLoggedIn mcExceptionNotLoggedIn) {
            cerebralCortexCallback.onError(mcExceptionNotLoggedIn);
        } catch (MCExceptionInternetConnection mcExceptionInternetConnection) {
            cerebralCortexCallback.onError(mcExceptionInternetConnection);
        } catch (MCExceptionConfigNotFound mcExceptionConfigNotFound) {
            cerebralCortexCallback.onError(mcExceptionConfigNotFound);
        } /*catch (IOException e) {
            cerebralCortexCallback.onError(new MCExceptionInvalidConfig());
        }*/
    }
    public void downloadConfigurationFile(final String fileName, final CerebralCortexCallback cerebralCortexCallback) {
        if(Looper.myLooper()==Looper.getMainLooper()){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    downloadExecute(fileName, cerebralCortexCallback);
                }
            }).start();

        }else{
            downloadExecute(fileName, cerebralCortexCallback);
        }
    }

    public boolean hasConfigurationUpdateAvailable() throws MCExceptionInternetConnection, MCExceptionNotLoggedIn, MCExceptionServerDown, MCExceptionConfigNotFound {
        checkInternetConnection();
        checkLoginStatus();
        checkServerUp(serverInfo.getServerAddress());
        FileInfo f = getConfigurationFile(serverInfo.getFileName());
        return !f.getLastModified().equals(serverInfo.getFileLastModified());
    }
    private String getAccessTokenWithLogin(String serverAddress, String userName, String password) throws MCExceptionNotLoggedIn {
        CerebralCortexWebApi ccService=ApiUtils.getCCService(serverAddress);
        ccWebAPICalls = new CCWebAPICalls(ccService);
        AuthResponse authResponse = ccWebAPICalls.authenticateUser(userName, password);
        if(authResponse==null) throw new MCExceptionNotLoggedIn();
        serverInfo.setAccessToken(authResponse.getAccessToken());
        return authResponse.getAccessToken();
    }

    private void checkInternetConnection() throws MCExceptionInternetConnection {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            if(ipAddr.toString().equals("")){
                throw new MCExceptionInternetConnection();
            }
        } catch (Exception e) {
            throw new MCExceptionInternetConnection();
        }

    }
    private void checkServerUp(String serverName) throws MCExceptionServerDown {
        try {

            String s=serverName;
            s = s.replace("https://","");
            s = s.replace("http://","");
            InetAddress ipAddr = InetAddress.getByName(s);
            //You can replace it with your name
            if(ipAddr.toString().equals("")){
                throw new MCExceptionServerDown();
            }

        } catch (Exception e) {
            throw new MCExceptionServerDown();
        }
    }

    private void checkLoginStatus() throws MCExceptionNotLoggedIn {
        if(!isLoggedIn()) throw new MCExceptionNotLoggedIn();
    }
}
