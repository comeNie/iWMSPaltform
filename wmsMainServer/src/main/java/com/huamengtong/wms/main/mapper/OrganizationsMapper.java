package com.huamengtong.wms.main.mapper;

import com.huamengtong.wms.entity.main.TWmsOrganizationsEntity;

import java.util.List;

public interface OrganizationsMapper {

    Integer insertOrganization(TWmsOrganizationsEntity organizationsEntity);

    Integer updateOrganization(TWmsOrganizationsEntity organizationsEntity);

    Integer deleteOrganization(Long id);

    TWmsOrganizationsEntity selectById(Long id);

    List<TWmsOrganizationsEntity> queryOrganizationPages(TWmsOrganizationsEntity organizationsEntity);

    List<TWmsOrganizationsEntity> selectAll();

    Integer queryOrganizationPageCount(TWmsOrganizationsEntity organizationsEntity);
}
