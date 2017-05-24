package com.huamengtong.wms.app.controller.inwh;

import com.huamengtong.wms.core.formwork.db.util.DbShareField;
import com.huamengtong.wms.core.web.BaseController;
import com.huamengtong.wms.core.web.ResponseResult;
import com.huamengtong.wms.dto.inwh.TWmsMoveDTO;
import com.huamengtong.wms.inwh.service.IMoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by mario on 2016/11/18.
 */
@RestController
@RequestMapping(value = "/inventory/move")
public class MoveController extends BaseController {

    @Autowired
    IMoveService moveService;


    @RequestMapping(value = "",method = RequestMethod.GET)
    public ResponseResult getMoveList(TWmsMoveDTO moveDTO) throws Exception{
        return getSucResultData(moveService.getMove(moveDTO,getDbShardVO(DbShareField.IN_WH)));
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    public ResponseResult createMove(@RequestBody TWmsMoveDTO moveDTO) throws Exception{
        moveDTO.setTenantId(getCurrentTenantId());
        moveDTO.setWarehouseId(getCurrentWarehouseId());
        moveDTO.setCreateUser(this.getSessionCurrentUser().getUserName());
        moveDTO.setCreateTime(new Date().getTime());
        moveDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        moveDTO.setUpdateTime(new Date().getTime());
        return  getMessage(moveService.createMove(moveDTO,getDbShardVO(DbShareField.IN_WH)));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public ResponseResult removeMove(@PathVariable Long id)throws  Exception{
        return getMessage(moveService.removeMove(id,getDbShardVO(DbShareField.IN_WH)));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public ResponseResult modifyMove(@RequestBody TWmsMoveDTO moveDTO) throws Exception {
        moveDTO.setWarehouseId(getCurrentWarehouseId());
        moveDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        moveDTO.setUpdateTime(new Date().getTime());
        return  getMessage(moveService.modifyMove(moveDTO,getDbShardVO(DbShareField.IN_WH)));
    }

    /***
     * 移库提交
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/submit/{id}",method = RequestMethod.PUT)
    public ResponseResult submitMove(@PathVariable Long id) throws  Exception{
        return getMessage(moveService.executeSubmitMove(id,this.getSessionCurrentUser().getUserName(),getDbShardVO(DbShareField.IN_WH)));
    }

}
