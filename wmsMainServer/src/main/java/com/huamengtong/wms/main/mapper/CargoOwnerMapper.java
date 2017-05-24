package com.huamengtong.wms.main.mapper;

import com.huamengtong.wms.entity.main.TWmsCargoOwnerEntity;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface CargoOwnerMapper {

    List<TWmsCargoOwnerEntity> queryOwnerPages(TWmsCargoOwnerEntity cargoOwnerEntity);

    Integer queryCargoOwnerPageCount(TWmsCargoOwnerEntity cargoOwnerEntity);

    Integer insertOwner(TWmsCargoOwnerEntity cargoOwnerEntity);

    Integer updateOwner(TWmsCargoOwnerEntity cargoOwnerEntity);

    Integer deleteOwner(@Param("id") Long id);

    TWmsCargoOwnerEntity queryOwnerById(@Param("id") Long id);

    TWmsCargoOwnerEntity selectCargoOwnerByCargoOwnerNo(String cargoOwnerNo);

    List<TWmsCargoOwnerEntity> selectALLCargoOwners(Map map);

    List<TWmsCargoOwnerEntity> selectByIds(@Param("ids") List<Long> ids);

}
