package org.md2k.library.datakit.storage.archiver.gzip;

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
public class GZipLogger/* implements ILogger */{
/*    class Type{
        int id;
        String name;
        Type(int id, String name){
            this.id=id;
            this.name = name;
        }
    }
    private Type METADATA=new Type(0,"_metadata");
    private Type DATA=new Type(0,"_data");
    private static final String FILE_EXTENSION = ".gz";
    private LoggerConfig loggerConfig;
    private SparseArray<String[]> fileList;

    public GZipLogger(LoggerConfig loggerConfig) {
        this.loggerConfig = loggerConfig;
    }

    @Override
    public void initialize() {
        fileList = new SparseArray<>();
        FileUtils.createOrExistsDir(loggerConfig.getPath());

    }

    @Override
    public void close() {

    }

    @Override
    public void deleteAll() {
        FileUtils.deleteDir(loggerConfig.getPath());
    }

    private String createIfNotValid(final _DataSource d, Type type) {
        if(SparseArray)
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File file) {
                String fileName = file.getName();
                return fileName.startsWith(d.uuid() + type);
            }
        };
        List<File> listFiles = FileUtils.listFilesInDirWithFilter(loggerConfig.getPath(), fileFilter);

        if (listFiles.size() == 0)
            return null;
        return ds + type + "_" + DateTime.toString(DateTime.getCurrentTime(), "yyyyMMddhhmmss") + FILE_EXTENSION;
        else if (listFiles.size() == 1)
            return listFiles.get(0).getName();
        else {
            ArrayList<String> listStr = new ArrayList<>();
            for (int i = 0; i < listFiles.size(); i++)
                listStr.addListener(listFiles.get(i).getName());
            Collections.reverse(listStr);
            return listStr.get(0);
        }
    }
    private String getLastFileName(_DataSource d, String type){
        return null;
    }

    private String createFileName(_DataSource dataSource, String type, String index) {
        return dataSource.uuid() + type + index + FILE_EXTENSION;
    }

    @Override
    public void insertDataSource(_DataSource d) {
        String fileName = createIfNotValid(d, FILE_METADATA);
        write(fileName, new String[]{d.toString()});
    }

    @Override
    public void updateDataSource(_DataSource d) {
        String fileName = createIfNotValid(d, FILE_METADATA);
        write(fileName, new String[]{d.toString()});
    }


    @Override
    public _DataSource[] queryDataSource(_DataSource d) {
        return new _DataSource[0];
    }

    @Override
    public void insertData(HashMap<_DataSource, Data[]> data) {
        for (Map.Entry<_DataSource, Data[]> entry : data.entrySet()) {
            _DataSource key = entry.getKey();
            Data[] value = entry.getValue();
            String[] valueStr = new String[value.length];
            for (int v = 0; v < value.length; v++)
                valueStr[v] = value[v].toString();
            write(key.uuid() + FILE_EXTENSION, valueStr);
        }
    }


    @Override
    public void insertSummary(int dsId, Data data) {

    }

    @Override
    public Data[] queryData(int dsId, int n) {
        return new Data[0];

    }

    @Override
    public Data[] queryData(int dsId, long startTimestamp, long endTimestamp) {
        return new Data[0];
    }

    @Override
    public int queryDataSize(int dsId, long startTimestamp, long endTimestamp) {
        return 0;
    }

    @Override
    public Data[] querySummary(int dsId, long startTimestamp, long endTimestamp) {
        return new Data[0];
    }

    private void write(String fileName, String[] values) {
        try {
            File outputFile = new File(loggerConfig.getPath() + File.separator + fileName);
            FileOutputStream output;
            output = new FileOutputStream(outputFile, true);
            Writer writer = new OutputStreamWriter(new GZIPOutputStream(output), "UTF-8");
            for (String value : values)
                writer.write(value);
            writer.close();
        } catch (Exception e) {

        }
    }*/
}
