package com.huamengtong.wms.app.controller.main;

import com.huamengtong.wms.core.web.BaseController;
import com.huamengtong.wms.core.web.ResponseResult;
import com.huamengtong.wms.dto.TWmsStorageRoomDTO;
import com.huamengtong.wms.main.service.IStorageRoomService;
import com.wordnik.swagger.annotations.Api;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;
import java.util.Map;


@RestController
@RequestMapping("storage/room")
@Api(value = "storage/room" , description = "库房信息")
public class StorageRoomController extends BaseController {

    @Autowired
    private IStorageRoomService roomService;

    @RequestMapping(value = "",method = RequestMethod.GET)
    public ResponseResult getStorageRoomList(TWmsStorageRoomDTO roomDTO) throws Exception{
        return getSucResultData(roomService.queryStorageRoomPages(roomDTO));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public ResponseResult modifyStorageRoom(@RequestBody TWmsStorageRoomDTO roomDTO) throws Exception{
        roomDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        roomDTO.setUpdateTime(new Date().getTime());
        return getMessage(roomService.modifyStorageRoom(roomDTO));
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    public ResponseResult createStorageRoom(@RequestBody TWmsStorageRoomDTO roomDTO) throws Exception{
        roomDTO.setWarehouseId(getCurrentWarehouseId());
        roomDTO.setCreateUser(this.getSessionCurrentUser().getUserName());
        roomDTO.setCreateTime(new Date().getTime());
        roomDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        roomDTO.setUpdateTime(new Date().getTime());
        return getMessage(roomService.createStorageRoom(roomDTO));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public ResponseResult removeStorageRoom(@PathVariable Long id) throws  Exception{
        return getMessage(roomService.removeByPrimaryKey(id));
    }

    @RequestMapping(value = "/warehouseId/{warehouseId}",method = RequestMethod.GET)
    public ResponseResult getStorageRoomList(@PathVariable Long warehouseId,@RequestParam Map map ) throws Exception{
        TWmsStorageRoomDTO roomDTO = new TWmsStorageRoomDTO();
        roomDTO.setWarehouseId(warehouseId);
        if(map.containsKey("page")) {
            Integer page = MapUtils.getInteger(map, "page");
            roomDTO.setPage(page);
        }
        if(map.containsKey("pageSize")) {
            Integer pageSize = MapUtils.getInteger(map, "pageSize");
            roomDTO.setPageSize(pageSize);
        }
        return getSucResultData(roomService.queryStorageRoomPages(roomDTO));
    }
}
