package com.huamengtong.wms.inwh.mapper.service.impl;

import com.huamengtong.wms.dto.inwh.TWmsQcCheckDTO;
import com.huamengtong.wms.entity.inwh.TWmsQcCheckEntity;
import com.huamengtong.wms.inwh.mapper.common.SpringTxTestCase;
import com.huamengtong.wms.inwh.service.IQcCheckService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by could.hao on 2017/3/21.
 */
public class QcCheckTest extends SpringTxTestCase {

    @Autowired
    IQcCheckService qcCheckService;
    @Test
    public void queryPage(){
        TWmsQcCheckDTO qcCheckDTO = new TWmsQcCheckDTO();
        qcCheckService.selectQcCheckPage(qcCheckDTO,getDbshardVO());
    }
    @Test
    public void queryById(){
        TWmsQcCheckEntity qcCheckEntity = qcCheckService.selectByPrimaryKey(1L,getDbshardVO());
    }
}
