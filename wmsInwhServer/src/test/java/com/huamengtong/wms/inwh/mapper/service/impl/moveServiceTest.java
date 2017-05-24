package com.huamengtong.wms.inwh.mapper.service.impl;

import com.huamengtong.wms.dto.inwh.TWmsMoveDTO;
import com.huamengtong.wms.inwh.mapper.common.SpringTxTestCase;
import com.huamengtong.wms.inwh.service.impl.MoveService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * Created by mario on 2016/11/18.
 */
public class moveServiceTest extends SpringTxTestCase {

    @Autowired
    MoveService moveService;

    @Test
    public void insertTest(){
        TWmsMoveDTO moveDTO = new TWmsMoveDTO();
        moveDTO.setTenantId(88l);
        moveDTO.setWarehouseId(88l);
        moveDTO.setCargoOwnerId(22l);
        moveDTO.setMoveReason("GGG");
        moveDTO.setDatasourceCode("GG");
        moveDTO.setStatusCode("init");
        moveDTO.setSkuId(2l);
        moveDTO.setFromRoomId(2l);
        moveDTO.setToRoomId(2l);
        moveDTO.setMovedQty(21);
        moveDTO.setIsDel(new Byte("1"));
        moveService.createMove(moveDTO,getDbshardVO());
        System.out.print("mario66666666666666666666666");
    }

    @Test
    public void selectMove(){
        System.out.print(moveService.findByPrimaryKey(1001L,getDbshardVO()));
        System.out.print("mario~~~~");
    }

    @Test
    public void deleteMove(){
        moveService.removeMove(1001L,getDbshardVO());
        System.out.print("mario666666666666666666666666");
    }

    @Test
    public void updateMove(){
        TWmsMoveDTO moveDTO = new TWmsMoveDTO();
        moveDTO.setId(2001l);
        moveDTO.setTenantId(88l);
        moveDTO.setWarehouseId(88l);
        moveDTO.setCargoOwnerId(22l);
        moveDTO.setMoveReason("GGG");
        moveDTO.setDatasourceCode("GG");
        moveDTO.setStatusCode("init");
        moveDTO.setSkuId(2l);
        moveDTO.setFromRoomId(2l);
        moveDTO.setToRoomId(2l);
        moveDTO.setMovedQty(21);
        moveDTO.setIsDel(new Byte("1"));
        moveService.modifyMove(moveDTO,getDbshardVO());
        System.out.print("mario66666666666666666666666");
    }
}
