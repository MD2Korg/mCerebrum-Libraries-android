package org.md2k.mcerebrum.library.cerebralcortex;

import com.orhanobut.hawk.Hawk;

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
class ServerInfo {
    private static final String USER_NAME = "USER_NAME";
    private static final String PASSWORD = "PASSWORD";
    private static final String SERVER_ADDRESS = "SERVER_ADDRESS";
    private static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    private static final String LOGGED_IN = "LOGGED_IN";
    private static final String FILE_NAME = "FILE_NAME";
    private static final String FILE_SIZE = "FILE_SIZE";
    private static final String FILE_LAST_MODIFIED = "FILE_LAST_MODIFIED";

    protected void setUserName(String userName) {
        Hawk.put(USER_NAME, userName);
    }
    public String getUserName(){
        return Hawk.get(USER_NAME);
    }

    public void setPassword(String password) {
        Hawk.put(PASSWORD, password);
    }
    public String getPassword(){
        return Hawk.get(PASSWORD);
    }

    public void setServerAddress(String serverAddress) {
        Hawk.put(SERVER_ADDRESS, serverAddress);
    }

    public void setAccessToken(String accessToken) {
        Hawk.put(ACCESS_TOKEN,accessToken);
    }

    public void setLoggedIn(boolean b) {
        Hawk.put(LOGGED_IN,true);
    }

    public boolean getLoggedIn() {
        if(Hawk.contains(LOGGED_IN)){
            return Hawk.get(LOGGED_IN);
        }else return false;
    }

    public String getServerAddress() {
        return Hawk.get(SERVER_ADDRESS);
    }
    public String getAccessToken(){
        return Hawk.get(ACCESS_TOKEN);
    }

    public void setFileName(String name) {
        Hawk.put(FILE_NAME, name);
    }

    public void setFileSize(String size) {
        Hawk.put(FILE_SIZE, size);
    }

    public void setFileLastModified(String lastModified) {
        Hawk.put(FILE_LAST_MODIFIED,lastModified);
    }
    public String getFileName(){
        return Hawk.get(FILE_NAME);
    }
    public String getFileSize(){
        return Hawk.get(FILE_SIZE);
    }
    public String getFileLastModified(){
        return Hawk.get(FILE_LAST_MODIFIED);
    }
}
