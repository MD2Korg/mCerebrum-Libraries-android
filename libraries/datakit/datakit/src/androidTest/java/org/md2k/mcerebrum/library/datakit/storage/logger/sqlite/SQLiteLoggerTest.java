package org.md2k.mcerebrum.library.datakit.storage.logger.sqlite;

import androidx.test.runner.AndroidJUnit4;
import android.util.SparseArray;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.md2k.mcerebrum.api.core.datakitapi.data.Data;
import org.md2k.mcerebrum.api.core.data.DataArray;
import org.md2k.mcerebrum.api.core.datakitapi.data.DataType;
import org.md2k.mcerebrum.api.core.datakitapi.data.SampleType;
import org.md2k.mcerebrum.api.core.datakitapi.datasource.DataSource;
import org.md2k.mcerebrum.api.core.datakitapi.datasource.DataSourceRegister;
import org.md2k.mcerebrum.api.core.datakitapi.datasource.DataSourceResult;
import org.md2k.mcerebrum.api.core.datakitapi.datasource.metadata.DataDescriptor;
import org.md2k.mcerebrum.api.core.time.DateTime;

import java.util.ArrayList;

import static androidx.test.InstrumentationRegistry.getTargetContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
@RunWith(AndroidJUnit4.class)
public class SQLiteLoggerTest {
    private SQLiteLogger sqLiteLogger;

    @Before
    public void setUp() throws Exception {
        sqLiteLogger = new SQLiteLogger(getTargetContext());
        sqLiteLogger.start();
    }

    @After
    public void tearDown() throws Exception {
        sqLiteLogger.stop();
        sqLiteLogger.delete();
    }

    @Test
    public void createDatabase() {
        assertNotNull(sqLiteLogger);
        boolean flag = false;
        for(int i =0;i<getTargetContext().databaseList().length;i++){
            if(getTargetContext().databaseList()[i].equals("sqlite.db"))
                flag=true;
        }
        assertTrue(flag);

/*
        ArrayList<String> list = sqLiteLogger.getTables();
        for (int i = 0; i < list.size(); i++) {
            long size = sqLiteLogger.getSize(list.get(i));
            System.out.println("table" + i + "= " + list.get(i) + " size = " + size);
            assertTrue(size == 0);
        }
        assertTrue(list.size() == 4);
*/

    }

    @Test
    public void insertDataSource() {
        assertEquals(0, sqLiteLogger.getSize(TableDataSource.TABLE_NAME));
        assertEquals(0, sqLiteLogger.getSize(TableMetaData.TABLE_NAME));
        DataSourceRegister d = DataSource.registerBuilder().setDataType(DataType.POINT).setSampleType(SampleType.INT_ARRAY).addDataDescriptor(DataDescriptor.builder().build()).setDataSourceType("D1").setApplicationType("A1").build();
        sqLiteLogger.insertOrUpdateDataSource((DataSource) d);
        assertEquals(1, sqLiteLogger.getSize(TableDataSource.TABLE_NAME));
        assertEquals(1, sqLiteLogger.getSize(TableMetaData.TABLE_NAME));
        d = DataSource.registerBuilder().setDataType(DataType.POINT).setSampleType(SampleType.INT_ARRAY).addDataDescriptor(DataDescriptor.builder().build()).setDataSourceType("D1").setApplicationType("A2").build();
        sqLiteLogger.insertOrUpdateDataSource((DataSource) d);
        assertEquals(2, sqLiteLogger.getSize(TableDataSource.TABLE_NAME));
        assertEquals(2, sqLiteLogger.getSize(TableMetaData.TABLE_NAME));
        d = DataSource.registerBuilder().setDataType(DataType.POINT).setSampleType(SampleType.INT_ARRAY).addDataDescriptor(DataDescriptor.builder().build()).setDataSourceType("D2").setApplicationType("A1").build();
        sqLiteLogger.insertOrUpdateDataSource((DataSource) d);
        assertEquals(3, sqLiteLogger.getSize(TableDataSource.TABLE_NAME));
        assertEquals(3, sqLiteLogger.getSize(TableMetaData.TABLE_NAME));
        d = DataSource.registerBuilder().setDataType(DataType.POINT).setSampleType(SampleType.INT_ARRAY).addDataDescriptor(DataDescriptor.builder().build()).setDataSourceType("D2").setApplicationType("A2").build();
        sqLiteLogger.insertOrUpdateDataSource((DataSource) d);
        assertEquals(4, sqLiteLogger.getSize(TableDataSource.TABLE_NAME));
        assertEquals(4, sqLiteLogger.getSize(TableMetaData.TABLE_NAME));
        d = DataSource.registerBuilder().setDataType(DataType.POINT).setSampleType(SampleType.INT_ARRAY).addDataDescriptor(DataDescriptor.builder().build()).setDataSourceType("D1").setApplicationType("A1").build();
        sqLiteLogger.insertOrUpdateDataSource((DataSource) d);
        assertEquals(4, sqLiteLogger.getSize(TableDataSource.TABLE_NAME));
        assertEquals(4, sqLiteLogger.getSize(TableMetaData.TABLE_NAME));

    }

    @Test
    public void updateDataSource() {
        assertEquals(0, sqLiteLogger.getSize(TableDataSource.TABLE_NAME));
        assertEquals(0, sqLiteLogger.getSize(TableMetaData.TABLE_NAME));
        DataSourceRegister d = DataSource.registerBuilder().setDataType(DataType.POINT).setSampleType(SampleType.INT_ARRAY).addDataDescriptor(DataDescriptor.builder().build()).setDataSourceType("D1").setApplicationType("A1").build();
        sqLiteLogger.insertOrUpdateDataSource((DataSource) d);
        assertEquals(1, sqLiteLogger.getSize(TableDataSource.TABLE_NAME));
        assertEquals(1, sqLiteLogger.getSize(TableMetaData.TABLE_NAME));
        d = DataSource.registerBuilder().setDataType(DataType.POINT).setSampleType(SampleType.INT_ARRAY).addDataDescriptor(DataDescriptor.builder().build()).setDataSourceType("D1").setApplicationType("A2").build();
        sqLiteLogger.insertOrUpdateDataSource((DataSource) d);
        assertEquals(2, sqLiteLogger.getSize(TableDataSource.TABLE_NAME));
        assertEquals(2, sqLiteLogger.getSize(TableMetaData.TABLE_NAME));
        d = DataSource.registerBuilder().setDataType(DataType.POINT).setSampleType(SampleType.INT_ARRAY).addDataDescriptor(DataDescriptor.builder().setName("abc").build()).setDataSourceType("D1").setApplicationType("A1").build();
        sqLiteLogger.insertOrUpdateDataSource((DataSource) d);
        assertEquals(2, sqLiteLogger.getSize(TableDataSource.TABLE_NAME));
        assertEquals(3, sqLiteLogger.getSize(TableMetaData.TABLE_NAME));
        d = DataSource.registerBuilder().setDataType(DataType.POINT).setSampleType(SampleType.INT_ARRAY).addDataDescriptor(DataDescriptor.builder().setName("def").build()).setDataSourceType("D1").setApplicationType("A1").build();
        sqLiteLogger.insertOrUpdateDataSource((DataSource) d);
        assertEquals(2, sqLiteLogger.getSize(TableDataSource.TABLE_NAME));
        assertEquals(4, sqLiteLogger.getSize(TableMetaData.TABLE_NAME));
        d = DataSource.registerBuilder().setDataType(DataType.POINT).setSampleType(SampleType.INT_ARRAY).addDataDescriptor(DataDescriptor.builder().setName("def").build()).setDataSourceType("D1").setApplicationType("A1").build();
        sqLiteLogger.insertOrUpdateDataSource((DataSource) d);
        assertEquals(2, sqLiteLogger.getSize(TableDataSource.TABLE_NAME));
        assertEquals(4, sqLiteLogger.getSize(TableMetaData.TABLE_NAME));

    }

    @Test
    public void readDataSource() {
        ArrayList<DataSourceResult> dq;
        assertEquals(0, sqLiteLogger.getSize(TableDataSource.TABLE_NAME));
        assertEquals(0, sqLiteLogger.getSize(TableMetaData.TABLE_NAME));
        DataSourceRegister d = DataSource.registerBuilder().setDataType(DataType.POINT).setSampleType(SampleType.INT_ARRAY).addDataDescriptor(DataDescriptor.builder().build()).setDataSourceType("D1").setApplicationType("A1").build();
        sqLiteLogger.insertOrUpdateDataSource((DataSource) d);
        assertEquals(1, sqLiteLogger.getSize(TableDataSource.TABLE_NAME));
        assertEquals(1, sqLiteLogger.getSize(TableMetaData.TABLE_NAME));
        dq=sqLiteLogger.queryDataSource((DataSource) DataSource.queryBuilder().setDataSourceType("D1").setApplicationType("A1").build());
        assertEquals(1, dq.size());
        assertEquals("D1", dq.get(0).getDataSource().getDataSourceType());
        assertEquals("A1", dq.get(0).getDataSource().getApplicationType());

        d = DataSource.registerBuilder().setDataType(DataType.POINT).setSampleType(SampleType.INT_ARRAY).addDataDescriptor(DataDescriptor.builder().build()).setDataSourceType("D1").setApplicationType("A2").build();
        sqLiteLogger.insertOrUpdateDataSource((DataSource) d);
        assertEquals(2, sqLiteLogger.getSize(TableDataSource.TABLE_NAME));
        assertEquals(2, sqLiteLogger.getSize(TableMetaData.TABLE_NAME));
        dq=sqLiteLogger.queryDataSource((DataSource) DataSource.queryBuilder().setDataSourceType("D1").setApplicationType("A2").build());
        assertEquals(1, dq.size());
        assertEquals("D1", dq.get(0).getDataSource().getDataSourceType());
        assertEquals("A2", dq.get(0).getDataSource().getApplicationType());


        d = DataSource.registerBuilder().setDataType(DataType.POINT).setSampleType(SampleType.INT_ARRAY).addDataDescriptor(DataDescriptor.builder().setName("abc").build()).setDataSourceType("D1").setApplicationType("A1").build();
        sqLiteLogger.insertOrUpdateDataSource((DataSource) d);
        assertEquals(2, sqLiteLogger.getSize(TableDataSource.TABLE_NAME));
        assertEquals(3, sqLiteLogger.getSize(TableMetaData.TABLE_NAME));
        dq=sqLiteLogger.queryDataSource((DataSource) DataSource.queryBuilder().setDataSourceType("D1").setApplicationType("A1").build());
        assertEquals(1, dq.size());
        assertEquals("D1", dq.get(0).getDataSource().getDataSourceType());
        assertEquals("A1", dq.get(0).getDataSource().getApplicationType());
        assertEquals(1, dq.get(0).getDataSource().getDataDescriptors().size());
        assertEquals("abc", dq.get(0).getDataSource().getDataDescriptors().get(0).getName());


        d = DataSource.registerBuilder().setDataType(DataType.POINT).setSampleType(SampleType.INT_ARRAY).addDataDescriptor(DataDescriptor.builder().setName("def").build()).setDataSourceType("D1").setApplicationType("A1").build();
        sqLiteLogger.insertOrUpdateDataSource((DataSource) d);
        dq=sqLiteLogger.queryDataSource((DataSource) DataSource.queryBuilder().setDataSourceType("D1").setApplicationType("A1").build());
        assertEquals(2, sqLiteLogger.getSize(TableDataSource.TABLE_NAME));
        assertEquals(4, sqLiteLogger.getSize(TableMetaData.TABLE_NAME));
        assertEquals(1, dq.size());
        assertEquals("D1", dq.get(0).getDataSource().getDataSourceType());
        assertEquals("A1", dq.get(0).getDataSource().getApplicationType());
        assertEquals(1, dq.get(0).getDataSource().getDataDescriptors().size());
        assertEquals("def", dq.get(0).getDataSource().getDataDescriptors().get(0).getName());


        d = DataSource.registerBuilder().setDataType(DataType.POINT).setSampleType(SampleType.INT_ARRAY).addDataDescriptor(DataDescriptor.builder().setName("def").build()).setDataSourceType("D1").setApplicationType("A1").build();
        sqLiteLogger.insertOrUpdateDataSource((DataSource) d);
        dq=sqLiteLogger.queryDataSource((DataSource) DataSource.queryBuilder().setDataSourceType("D1").setApplicationType("A1").build());
        assertEquals(2, sqLiteLogger.getSize(TableDataSource.TABLE_NAME));
        assertEquals(4, sqLiteLogger.getSize(TableMetaData.TABLE_NAME));
        assertEquals(1, dq.size());
        assertEquals("D1", dq.get(0).getDataSource().getDataSourceType());
        assertEquals("A1", dq.get(0).getDataSource().getApplicationType());
        assertEquals(1, dq.get(0).getDataSource().getDataDescriptors().size());
        assertEquals("def", dq.get(0).getDataSource().getDataDescriptors().get(0).getName());

    }

    @Test
    public void queryDataSource() {
        DataSourceRegister d;
        DataSource dq;
        ArrayList<DataSourceResult> ds;
        assertEquals(0, sqLiteLogger.getSize(TableDataSource.TABLE_NAME));
        assertEquals(0, sqLiteLogger.getSize(TableMetaData.TABLE_NAME));
         d = DataSource.registerBuilder().setDataType(DataType.POINT).setSampleType(SampleType.INT_ARRAY).addDataDescriptor(DataDescriptor.builder().build()).setDataSourceType("D1").setApplicationType("A1").build();
        sqLiteLogger.insertOrUpdateDataSource((DataSource) d);
         d = DataSource.registerBuilder().setDataType(DataType.POINT).setSampleType(SampleType.INT_ARRAY).addDataDescriptor(DataDescriptor.builder().build()).setDataSourceType("D1").setApplicationType("A2").build();
        sqLiteLogger.insertOrUpdateDataSource((DataSource) d);
         d = DataSource.registerBuilder().setDataType(DataType.POINT).setSampleType(SampleType.INT_ARRAY).addDataDescriptor(DataDescriptor.builder().build()).setDataSourceType("D2").setApplicationType("A1").build();
        sqLiteLogger.insertOrUpdateDataSource((DataSource) d);
         d = DataSource.registerBuilder().setDataType(DataType.POINT).setSampleType(SampleType.INT_ARRAY).addDataDescriptor(DataDescriptor.builder().build()).setDataSourceType("D2").setApplicationType("A2").build();
        sqLiteLogger.insertOrUpdateDataSource((DataSource) d);
         dq = (DataSource) DataSource.queryBuilder().setDataSourceType("AA").build();
        ds = sqLiteLogger.queryDataSource(dq);
        assertEquals(0, ds.size());
        dq = (DataSource) DataSource.queryBuilder().setDataSourceType("D1").setApplicationType("A1").build();
        ds = sqLiteLogger.queryDataSource(dq);
        assertEquals(1, ds.size());
        dq = (DataSource) DataSource.queryBuilder().setDataSourceType("D1").build();
        ds = sqLiteLogger.queryDataSource(dq);
        assertEquals(2, ds.size());
        dq = (DataSource) DataSource.queryBuilder().setApplicationType("A2").build();
        ds = sqLiteLogger.queryDataSource(dq);
        assertEquals(2, ds.size());
        dq= (DataSource) DataSource.queryBuilder().build();
        ds = sqLiteLogger.queryDataSource(dq);
        assertEquals(4, ds.size());
    }


    @Test
    public void queryDataByN() {
        DataSource d = (DataSource) DataSource.registerBuilder().setDataType(DataType.POINT).setSampleType(SampleType.INT_ARRAY).addDataDescriptor(DataDescriptor.builder().build()).setDataSourceType("D1").setApplicationType("A1").build();
        DataSource d1 = (DataSource) DataSource.registerBuilder().setDataType(DataType.POINT).setSampleType(SampleType.INT_ARRAY).addDataDescriptor(DataDescriptor.builder().build()).setDataSourceType("D2").setApplicationType("A1").build();
        DataSourceResult ds = sqLiteLogger.insertOrUpdateDataSource(d);
        DataSourceResult ds1 = sqLiteLogger.insertOrUpdateDataSource(d1);
        assertEquals(2, sqLiteLogger.getSize(TableDataSource.TABLE_NAME));
        SparseArray<DataArray> sa=new SparseArray<>();
        Data dp1 = Data.createPoint(DateTime.getCurrentTime()-5*60*1000L, new int[]{5,0,0});
        Data dp2 = Data.createPoint(DateTime.getCurrentTime()-4*60*1000L, new int[]{4,0,0});
        Data dp3 = Data.createPoint(DateTime.getCurrentTime()-3*60*1000L, new int[]{3,0,0});
        Data dp4 = Data.createPoint(DateTime.getCurrentTime()-2*60*1000L, new int[]{1,0,0});
        Data dp5 = Data.createPoint(DateTime.getCurrentTime()-1*60*1000L, new int[]{1,0,0});
        Data dp6 = Data.createPoint(DateTime.getCurrentTime(), new int[]{0,0,0});
        DataArray dataArray1 = new DataArray();
        DataArray dataArray2 = new DataArray();
        dataArray1.add(new Data[]{dp1, dp2, dp3, dp4, dp5, dp6});
        dataArray2.add(new Data[]{dp1, dp3, dp5});
        sa.put(ds.getDsId(), dataArray1);
        sa.put(ds1.getDsId(), dataArray2);
        sqLiteLogger.insertData(sa);
        ArrayList<Data> data = sqLiteLogger.queryData(ds.getDsId(), 10);
        assertEquals(6, data.size());
        assertTrue(data.get(0).getTimestamp()<data.get(1).getTimestamp());

        int[] a = (int[]) data.get(0).getSample();
        assertEquals(3, a.length);
        assertEquals(5, a[0]);
        data = sqLiteLogger.queryData(ds.getDsId(), 1);
        assertEquals(1, data.size());
        data = sqLiteLogger.queryData(ds.getDsId(), 2);
        assertEquals(2, data.size());
        data = sqLiteLogger.queryData(ds.getDsId(), 6);
        assertEquals(6, data.size());
    }

    @Test
    public void queryDataByTime() {
        DataSource _d = (DataSource) DataSource.registerBuilder().setDataType(DataType.POINT).setSampleType(SampleType.INT_ARRAY).addDataDescriptor(DataDescriptor.builder().build()).setDataSourceType("D1").setApplicationType("A1").build();
        DataSource _d1 = (DataSource) DataSource.registerBuilder().setDataType(DataType.POINT).setSampleType(SampleType.INT_ARRAY).addDataDescriptor(DataDescriptor.builder().build()).setDataSourceType("D2").setApplicationType("A1").build();

        DataSourceResult d = sqLiteLogger.insertOrUpdateDataSource(_d);
        DataSourceResult d1 = sqLiteLogger.insertOrUpdateDataSource(_d1);
        assertEquals(2, sqLiteLogger.getSize(TableDataSource.TABLE_NAME));
        SparseArray<DataArray> sa=new SparseArray<>();
        Data dp1 = Data.createPoint(DateTime.getCurrentTime()-5*60*1000L, new int[]{5,0,0});
        Data dp2 = Data.createPoint(DateTime.getCurrentTime()-4*60*1000L, new int[]{4,0,0});
        Data dp3 = Data.createPoint(DateTime.getCurrentTime()-3*60*1000L, new int[]{3,0,0});
        Data dp4 = Data.createPoint(DateTime.getCurrentTime()-2*60*1000L, new int[]{1,0,0});
        Data dp5 = Data.createPoint(DateTime.getCurrentTime()-1*60*1000L, new int[]{1,0,0});
        Data dp6 = Data.createPoint(DateTime.getCurrentTime(), new int[]{0,0,0});
        DataArray dataArray=new DataArray();
        dataArray.add(new Data[]{dp1, dp2, dp3, dp4, dp5, dp6});
        sa.put(d.getDsId(), dataArray);
        sqLiteLogger.insertData(sa);
        ArrayList<Data> ds = sqLiteLogger.queryData(d.getDsId(), DateTime.getCurrentTime()-2*60*1000L-30*1000L, DateTime.getCurrentTime());
        assertEquals(3, ds.size());
        assertTrue(ds.get(0).getTimestamp()<ds.get(1).getTimestamp());
        ds = sqLiteLogger.queryData(d.getDsId(), DateTime.getCurrentTime()-10*60*1000L-30*1000L, DateTime.getCurrentTime());
        assertEquals(6, ds.size());
        ds = sqLiteLogger.queryData(d.getDsId(), DateTime.getCurrentTime()-30*1000L, DateTime.getCurrentTime());
        assertEquals(1, ds.size());
        ds = sqLiteLogger.queryData(d.getDsId(), DateTime.getCurrentTime()-1, DateTime.getCurrentTime());
        assertEquals(0, ds.size());
    }

    @Test
    public void countData() {
        SparseArray<DataArray> sa=new SparseArray<>();
        Data dp1 = Data.createPoint(DateTime.getCurrentTime()-2*60*1000L, new int[]{5,0,0});
        Data dp2 = Data.createPoint(DateTime.getCurrentTime()- 60 * 1000L, new int[]{4,0,0});
        Data dp3 = Data.createPoint(DateTime.getCurrentTime(), new int[]{3,0,0});
        DataSource _d = (DataSource) DataSource.registerBuilder().setDataType(DataType.POINT).setSampleType(SampleType.INT_ARRAY).addDataDescriptor(DataDescriptor.builder().build()).setDataSourceType("D1").setApplicationType("A1").build();
        DataSource _d1 = (DataSource) DataSource.registerBuilder().setDataType(DataType.POINT).setSampleType(SampleType.INT_ARRAY).addDataDescriptor(DataDescriptor.builder().build()).setDataSourceType("D2").setApplicationType("A1").build();

        DataSourceResult d = sqLiteLogger.insertOrUpdateDataSource(_d);
        DataSourceResult d1 = sqLiteLogger.insertOrUpdateDataSource(_d1);
        assertEquals(2, sqLiteLogger.getSize(TableDataSource.TABLE_NAME));

        int count = sqLiteLogger.queryDataCount(d.getDsId(), DateTime.getYesterday(), DateTime.getCurrentTime());
        assertEquals(0, count);
        DataArray dataArray = new DataArray();
        dataArray.add(new Data[]{dp1});

        sa.put(d.getDsId(), dataArray);
        sqLiteLogger.insertData(sa);
        count = sqLiteLogger.queryDataCount(d.getDsId(), DateTime.getYesterday(), DateTime.getCurrentTime());
        assertTrue(count==1);

         dataArray = new DataArray();
        dataArray.add(new Data[]{dp1, dp2});

        sa.put(d.getDsId(), dataArray);
        sqLiteLogger.insertData(sa);
        count = sqLiteLogger.queryDataCount(d.getDsId(), DateTime.getYesterday(), DateTime.getCurrentTime());
        assertTrue(count==3);

        dataArray = new DataArray();
        dataArray.add(new Data[]{dp1, dp2,dp3});


        sa.put(d.getDsId(), dataArray);
        sqLiteLogger.insertData(sa);
        count = sqLiteLogger.queryDataCount(d.getDsId(), DateTime.getYesterday(), DateTime.getCurrentTime());
        assertTrue(count==6);

        count = sqLiteLogger.queryDataCount(d.getDsId(), DateTime.getCurrentTime()-2*60*1000, DateTime.getCurrentTime());
        assertTrue(count==3);

        count = sqLiteLogger.queryDataCount(d.getDsId(), DateTime.getCurrentTime()-5*60*1000, DateTime.getCurrentTime());
        assertTrue(count==6);

        count = sqLiteLogger.queryDataCount(d.getDsId(), DateTime.getCurrentTime()-10, DateTime.getCurrentTime());
        assertTrue(count==0);
    }


}

