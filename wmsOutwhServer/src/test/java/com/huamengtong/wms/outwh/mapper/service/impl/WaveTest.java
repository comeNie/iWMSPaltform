package com.huamengtong.wms.outwh.mapper.service.impl;

import com.huamengtong.wms.dto.outwh.TWmsDnHeaderDTO;
import com.huamengtong.wms.entity.outwh.TWmsWaveEntity;
import com.huamengtong.wms.outwh.mapper.common.SpringTxTestCase;
import com.huamengtong.wms.outwh.service.IWaveService;
import com.huamengtong.wms.outwh.service.impl.DnHeaderService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by could.hao on 2016/12/19.
 */
public class WaveTest extends SpringTxTestCase {

    @Autowired
    IWaveService waveService;

    @Test
    public void insertTest() {
        TWmsWaveEntity waveEntity = new TWmsWaveEntity();
        System.out.print("InsertSUCCESS~~~~~~~~~");
    }

    @Test
    public void selectsaleOrderByPrimaryKey(){
        waveService.findById(1002L,getDbshardVO());
        System.out.println("select Sucess~~~~~~~~~~~~!");
    }

    @Test
    public void deleteReceiptHeaderByPrimaryKey(){
        System.out.println("Delete Sucess~~~~~~~~~~~~!");
    }

    @Test
    public void UpdateTest() {
        System.out.print("InsertSUCCESS~~~~~~~~~");
    }
}
