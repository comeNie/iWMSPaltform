package com.huamengtong.wms.inventory.mapper;

import com.huamengtong.wms.entity.inventory.TWmsProInventoryEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface ProInventoryMapper {

    List<TWmsProInventoryEntity> queryProInventoryPages(@Param("entity") TWmsProInventoryEntity ProInventoryEntity, @Param("splitTableKey") String splitTableKey);

    Integer queryProInventoryPageCount(@Param("entity") TWmsProInventoryEntity ProInventoryEntity, @Param("splitTableKey") String splitTableKey);

    Integer insertProInventory(@Param("entity") TWmsProInventoryEntity ProInventoryEntity, @Param("splitTableKey") String splitTableKey);

    Integer updateProInventory(@Param("entity") TWmsProInventoryEntity ProInventoryEntity, @Param("splitTableKey") String splitTableKey);

    TWmsProInventoryEntity selectByPK(@Param("id") Long id, @Param("splitTableKey") String splitTableKey);

    Integer deleteProInventory(@Param("id") Long id, @Param("splitTableKey") String splitTableKey);

    List<TWmsProInventoryEntity> selectProInventoryListForReport(@Param("entity") TWmsProInventoryEntity ProInventoryEntity,@Param("list") List<Long> list,@Param("splitTableKey") String splitTableKey);

    Integer selectProInventoryPageCountForReport(@Param("entity") TWmsProInventoryEntity ProInventoryEntity,@Param("list") List<Long> list,@Param("splitTableKey") String splitTableKey);

    List<TWmsProInventoryEntity> selectProInventoryListBySku(@Param("skuId") Long skuId,@Param("cargoOwnerId")Long cargoOwnerId,
                                                             @Param("roomsList") List<Long> roomsList,@Param("splitTableKey") String splitTableKey);

    List getProInventoryGroupByList(@Param("skuIds") Set<Long> skuIds,@Param("storageRoomId")Long storageRoomId,@Param("cargoOwnerId")Long cargoOwnerId,@Param("splitTableKey") String splitTableKey);

    TWmsProInventoryEntity selectProInventoryBySku(@Param("skuId") Long skuId,@Param("cargoOwnerId")Long cargoOwnerId, @Param("storageRoomId") Long storageRoomId,
                                                   @Param("workGroupNo")String workGroupNo,@Param("splitTableKey") String splitTableKey);


    List<TWmsProInventoryEntity> selectByParentId(@Param("parentId") Long parentId,@Param("splitTableKey") String splitTableKey);

}
