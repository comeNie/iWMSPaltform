package com.huamengtong.wms.main.service;

import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.TWmsOrganizationsDTO;
import com.huamengtong.wms.entity.main.TWmsOrganizationsEntity;

import java.util.List;

public interface IOrganizationsService {

    MessageResult createOrganization(TWmsOrganizationsDTO organizationsDTO);

    MessageResult modifyOrganization(TWmsOrganizationsDTO organizationsDTO);

    MessageResult removeOrganization(Long id);

    PageResponse<List<TWmsOrganizationsEntity>> queryOrganizationPages(TWmsOrganizationsDTO organizationsDTO);

    TWmsOrganizationsEntity findById(Long id);

    List<TWmsOrganizationsEntity> queryAll();

}
