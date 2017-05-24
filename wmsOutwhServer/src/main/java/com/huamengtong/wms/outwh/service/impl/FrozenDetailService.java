package com.huamengtong.wms.outwh.service.impl;

import com.huamengtong.wms.client.IAutoIdClient;
import com.huamengtong.wms.constants.AutoIdConstants;
import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.service.BaseService;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.outwh.TWmsFrozenDetailDTO;
import com.huamengtong.wms.dto.outwh.TWmsFrozenHeaderDTO;
import com.huamengtong.wms.em.TicketStatusCode;
import com.huamengtong.wms.entity.inventory.TWmsInventoryEntity;
import com.huamengtong.wms.entity.outwh.TWmsFrozenDetailEntity;
import com.huamengtong.wms.entity.outwh.TWmsFrozenHeaderEntity;
import com.huamengtong.wms.inventory.service.IInventoryService;
import com.huamengtong.wms.outwh.mapper.FrozenDetailMapper;
import com.huamengtong.wms.outwh.service.IFrozenDetailService;
import com.huamengtong.wms.outwh.service.IFrozenHeaderService;
import com.huamengtong.wms.utils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class FrozenDetailService extends BaseService implements IFrozenDetailService {

    @Autowired
    FrozenDetailMapper frozenDetailMapper;

    @Autowired
    IAutoIdClient autoIdClient;


    @Autowired
    IFrozenHeaderService frozenHeaderService;

    @Autowired
    IInventoryService inventoryService;

    @Override
    public TWmsFrozenDetailEntity findByPrimaryKey(Long id, DbShardVO dbShardVO) {
        return frozenDetailMapper.selectByPrimaryKey(id,getSplitTableKey(dbShardVO));
    }

    @Override
    public MessageResult createFrozenDetail(TWmsFrozenDetailDTO frozenDetailDTO, DbShardVO dbShardVO) {
        if(frozenDetailDTO.getSkuId() == null || frozenDetailDTO.getSkuId()==0){
            return MessageResult.getMessage("E70012");
        }
        if (frozenDetailDTO.getStorageRoomId()==null || frozenDetailDTO.getStorageRoomId()==0){
            return MessageResult.getMessage("E70011");
        }
        if(frozenDetailDTO.getFactoringQty() == null || frozenDetailDTO.getFactoringQty() <= 0){
            return MessageResult.getMessage("E70013");
        }
        if(!(frozenDetailMapper.selectByFrozenIdAndSkuId(frozenDetailDTO.getFrozenId(),frozenDetailDTO.getSkuId(),frozenDetailDTO.getStorageRoomId(),getSplitTableKey(dbShardVO))==null)){
            return MessageResult.getMessage("E70015");
        }
        TWmsFrozenDetailEntity frozenDetailEntity= BeanUtils.copyBeanPropertyUtils(frozenDetailDTO,TWmsFrozenDetailEntity.class);
        frozenDetailEntity.setId(autoIdClient.getAutoId(AutoIdConstants.TWmsFrozenDetailEntity));
        frozenDetailEntity.setStatusCode(TicketStatusCode.INIT.toString());
        //主表在数据库中的数据
        TWmsFrozenHeaderEntity frozenHeaderEntity = frozenHeaderService.findByPrimaryKey(frozenDetailEntity.getFrozenId(),dbShardVO);
        TWmsInventoryEntity inventoryEntity = inventoryService.findInventoryFromCargoOwner(frozenDetailEntity.getSkuId(),frozenHeaderEntity.getCargoOwnerId(),frozenDetailEntity.getStorageRoomId(),changeInwhDbShareVO(dbShardVO));
        if(frozenDetailEntity.getFactoringQty()>(inventoryEntity.getOnhandQty()-inventoryEntity.getAllocatedQty()-inventoryEntity.getPickedQty()-inventoryEntity.getMortgagedQty()-inventoryEntity.getWorkingQty())){
            return MessageResult.getMessage("E70017",new Object[]{inventoryEntity.getOnhandQty()-inventoryEntity.getAllocatedQty()-inventoryEntity.getPickedQty()-inventoryEntity.getMortgagedQty()-inventoryEntity.getWorkingQty()});
        }
        createSummation(frozenHeaderEntity,frozenDetailEntity);//更新数据
        frozenDetailMapper.insertFrozenDetail(frozenDetailEntity,getSplitTableKey(dbShardVO));
        TWmsFrozenHeaderDTO frozenHeaderDTO = BeanUtils.copyBeanPropertyUtils(frozenHeaderEntity,TWmsFrozenHeaderDTO.class);
        frozenHeaderService.modifyFrozenHeader(frozenHeaderDTO,dbShardVO);//更新主表单数据
        return MessageResult.getSucMessage();
    }

    /**
     * 根据新增的数据对象来更新主表中数据
     * @param frozenHeaderEntity  数据库中的主表数据
     * @param frozenDetailEntity  创建的明细对象
     */
    private void createSummation(TWmsFrozenHeaderEntity frozenHeaderEntity,TWmsFrozenDetailEntity frozenDetailEntity){
        if(!(frozenDetailEntity.getTotalPrice()==null)){
            frozenHeaderEntity.setTotalAmount(frozenHeaderEntity.getTotalAmount().add(frozenDetailEntity.getTotalPrice()));
        }
        if(!(frozenDetailEntity.getFactoringQty()==null)){
            frozenHeaderEntity.setTotalQty(frozenHeaderEntity.getTotalQty()+frozenDetailEntity.getFactoringQty());
        }
        if (!(frozenDetailEntity.getNetWeight()==null)){
            frozenHeaderEntity.setTotalNetWeight(frozenHeaderEntity.getTotalNetWeight().add(frozenDetailEntity.getNetWeight()));
        }
        if(!(frozenDetailEntity.getGrossWeight()==null)){
            frozenHeaderEntity.setTotalGrossWeight(frozenHeaderEntity.getTotalGrossWeight().add(frozenDetailEntity.getGrossWeight()));
        }
        if(!(frozenDetailEntity.getVolume()==null)){
            frozenHeaderEntity.setTotalVolume(frozenHeaderEntity.getTotalVolume().add(frozenDetailEntity.getVolume()));
        }
        frozenHeaderEntity.setUpdateUser(frozenDetailEntity.getUpdateUser());
        frozenHeaderEntity.setUpdateTime(frozenDetailEntity.getUpdateTime());
    }

    @Override
    public MessageResult removeByPrimaryKey(Long id, DbShardVO dbShardVO) {
        if (id==null || id==0){
            return MessageResult.getMessage("E70001");
        }
        TWmsFrozenDetailEntity frozenDetailEntity = frozenDetailMapper.selectByPrimaryKey(id,getSplitTableKey(dbShardVO));//数据库中的明细
        TWmsFrozenHeaderEntity frozenHeaderEntity = frozenHeaderService.findByPrimaryKey(frozenDetailEntity.getFrozenId(),dbShardVO);//数据库中的主表数据
        //更新主表中数据
        frozenHeaderEntity.setTotalQty(frozenHeaderEntity.getTotalQty()-frozenDetailEntity.getFactoringQty());
        if(frozenDetailEntity.getTotalPrice() !=null ){
            frozenHeaderEntity.setTotalAmount(frozenHeaderEntity.getTotalAmount().subtract(frozenDetailEntity.getTotalPrice()));
        }
        frozenHeaderEntity.setTotalGrossWeight(frozenHeaderEntity.getTotalGrossWeight().subtract(frozenDetailEntity.getGrossWeight()));
        frozenHeaderEntity.setTotalNetWeight(frozenHeaderEntity.getTotalNetWeight().subtract(frozenDetailEntity.getNetWeight()));
        frozenHeaderEntity.setTotalVolume(frozenHeaderEntity.getTotalVolume().subtract(frozenDetailEntity.getVolume()));
        frozenHeaderEntity.setUpdateUser(frozenDetailEntity.getUpdateUser());
        frozenHeaderEntity.setUpdateTime(frozenDetailEntity.getUpdateTime());
        // 删除明细数据
        frozenDetailMapper.deleteFrozenDetail(id,getSplitTableKey(dbShardVO));
        //更新主表数据
        TWmsFrozenHeaderDTO frozenHeaderDTO = BeanUtils.copyBeanPropertyUtils(frozenHeaderEntity,TWmsFrozenHeaderDTO.class);
        frozenHeaderService.modifyFrozenHeader(frozenHeaderDTO,dbShardVO);
        return MessageResult.getSucMessage();
    }


    @Override
    public MessageResult removeByFrozenId(Long frozenId, DbShardVO dbShardVO) {
        Map<String,Object> map = new HashMap();
        map.put("frozenId",frozenId);
        map.put("splitTableKey",getSplitTableKey(dbShardVO));
        frozenDetailMapper.deleteByFrozenId(map);
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult modifyFrozenDetail(TWmsFrozenDetailDTO frozenDetailDTO, DbShardVO dbShardVO) {
        TWmsFrozenDetailEntity frozenDetailEntity= BeanUtils.copyBeanPropertyUtils(frozenDetailDTO,TWmsFrozenDetailEntity.class);
        //主表数据
        TWmsFrozenHeaderEntity frozenHeaderEntity = frozenHeaderService.findByPrimaryKey(frozenDetailEntity.getFrozenId(),dbShardVO);
        //数据库中子表数据
        TWmsFrozenDetailEntity frozenDetailEntity1 = frozenDetailMapper.selectByPrimaryKey(frozenDetailEntity.getId(),getSplitTableKey(dbShardVO));
        // update elements of header
        modifyElements(frozenHeaderEntity,frozenDetailEntity,frozenDetailEntity1);
        TWmsInventoryEntity inventoryEntity = inventoryService.findInventoryFromCargoOwner(frozenDetailEntity.getSkuId(),frozenHeaderEntity.getCargoOwnerId(),frozenDetailEntity.getStorageRoomId(),changeInwhDbShareVO(dbShardVO));
        if(frozenDetailEntity.getFactoringQty()>inventoryEntity.getOnhandQty()-inventoryEntity.getAllocatedQty()-inventoryEntity.getPickedQty()-inventoryEntity.getMortgagedQty()-inventoryEntity.getWorkingQty()){
            return MessageResult.getMessage("E70018",new Object[]{inventoryEntity.getOnhandQty()-inventoryEntity.getAllocatedQty()-inventoryEntity.getPickedQty()-inventoryEntity.getMortgagedQty()-inventoryEntity.getWorkingQty()});
        }
        frozenDetailMapper.updateFrozenDetail(frozenDetailEntity,getSplitTableKey(dbShardVO));
        //更新主表数据
        TWmsFrozenHeaderDTO frozenHeaderDTO = BeanUtils.copyBeanPropertyUtils(frozenHeaderEntity,TWmsFrozenHeaderDTO.class);
        frozenHeaderService.modifyFrozenHeader(frozenHeaderDTO,dbShardVO);
        return MessageResult.getSucMessage();
    }

    /**
     * 更新质押明细单据表中数据，主表数据一起更新
     * @param frozenHeaderEntity  主表单数据
     * @param frozenDetailEntity  更新后的明细数据
     * @param frozenDetailEntity1 表中现有数据
     */
    private void modifyElements(TWmsFrozenHeaderEntity frozenHeaderEntity,TWmsFrozenDetailEntity frozenDetailEntity,TWmsFrozenDetailEntity frozenDetailEntity1){
        // 总数量
        if(!(frozenDetailEntity.getFactoringQty()==null)){
            frozenHeaderEntity.setTotalQty(frozenHeaderEntity.getTotalQty()+frozenDetailEntity.getFactoringQty()-frozenDetailEntity1.getFactoringQty());
        }
        if(!(frozenDetailEntity.getTotalPrice()==null)){
            frozenHeaderEntity.setTotalAmount(frozenHeaderEntity.getTotalAmount().add(frozenDetailEntity.getTotalPrice()).subtract(frozenDetailEntity1.getTotalPrice()));
        }
        //总毛重
        if (!(frozenDetailEntity.getGrossWeight()==null)){
            frozenHeaderEntity.setTotalGrossWeight(frozenHeaderEntity.getTotalGrossWeight().add(frozenDetailEntity.getGrossWeight()).subtract(frozenDetailEntity1.getGrossWeight()));
        }
        //总净重
        if(!(frozenDetailEntity.getNetWeight()==null)){
            frozenHeaderEntity.setTotalNetWeight(frozenHeaderEntity.getTotalNetWeight().add(frozenDetailEntity.getNetWeight()).subtract(frozenDetailEntity1.getNetWeight()));
        }
        //总体积
        if(!(frozenDetailEntity.getVolume()==null)){
            frozenHeaderEntity.setTotalVolume(frozenHeaderEntity.getTotalVolume().add(frozenDetailEntity.getVolume()).subtract(frozenDetailEntity1.getVolume()));
        }
        frozenHeaderEntity.setUpdateUser(frozenDetailEntity.getUpdateUser());
        frozenHeaderEntity.setUpdateTime(frozenDetailEntity.getUpdateTime());
    }

    @Override
    public PageResponse<List> queryFrozenDetailByHeader(Map paraMap, DbShardVO dbShardVO) {
        paraMap.put("splitTableKey",getSplitTableKey(dbShardVO));
        List<TWmsFrozenDetailEntity> mapList = frozenDetailMapper.queryDetailPages(paraMap);
        Integer totalSize = frozenDetailMapper.queryDetailPageCount(paraMap);
        PageResponse<List> response = new PageResponse();
        response.setRows(mapList);
        response.setTotal(totalSize);
        return response;
    }

    @Override
    public List<TWmsFrozenDetailEntity> getFrozenDetailsByHeaderId(Long frozenId, DbShardVO dbShardVO) {
            List<TWmsFrozenDetailEntity> mapList = frozenDetailMapper.queryFrozenDetails(frozenId,getSplitTableKey(dbShardVO));
            if (CollectionUtils.isNotEmpty(mapList))
            {
                return mapList;
            }
        return null;
    }


    @Override
    public MessageResult updateFrozenDetailStatus(List<TWmsFrozenDetailEntity> frozenDetailEntityList, String operationCode,String operationUser, DbShardVO dbShardVO) {
        if(operationCode.equals(TicketStatusCode.SUBMIT.toString())){
            for (TWmsFrozenDetailEntity frozenDetailEntity:frozenDetailEntityList){
                frozenDetailEntity.setStatusCode(TicketStatusCode.SUBMIT.toString());
                frozenDetailEntity.setUpdateUser(operationUser);
                frozenDetailEntity.setUpdateTime(new Date().getTime());
                frozenDetailMapper.updateFrozenDetail(frozenDetailEntity,getSplitTableKey(dbShardVO));
            }
        }
        if(operationCode.equals(TicketStatusCode.REPEALED.toString())){
            for (TWmsFrozenDetailEntity frozenDetailEntity:frozenDetailEntityList){
                frozenDetailEntity.setStatusCode(TicketStatusCode.INIT.toString());
                frozenDetailEntity.setUpdateUser(operationUser);
                frozenDetailEntity.setUpdateTime(new Date().getTime());
                frozenDetailMapper.updateFrozenDetail(frozenDetailEntity,getSplitTableKey(dbShardVO));
            }
        }
        if(operationCode.equals(TicketStatusCode.REVIEWED.toString())){
            for (TWmsFrozenDetailEntity frozenDetailEntity:frozenDetailEntityList){
                frozenDetailEntity.setStatusCode(TicketStatusCode.REVIEWED.toString());
                frozenDetailEntity.setUpdateUser(operationUser);
                frozenDetailEntity.setUpdateTime(new Date().getTime());
                frozenDetailMapper.updateFrozenDetail(frozenDetailEntity,getSplitTableKey(dbShardVO));
            }
        }
        return MessageResult.getSucMessage();
    }


}
