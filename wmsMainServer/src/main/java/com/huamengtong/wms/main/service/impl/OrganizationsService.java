package com.huamengtong.wms.main.service.impl;

import com.huamengtong.wms.core.service.BaseService;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.TWmsOrganizationsDTO;
import com.huamengtong.wms.entity.main.TWmsOrganizationsEntity;
import com.huamengtong.wms.main.mapper.OrganizationsMapper;
import com.huamengtong.wms.main.service.IOrganizationsService;
import com.huamengtong.wms.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class OrganizationsService extends BaseService implements IOrganizationsService{

    @Autowired
    OrganizationsMapper organizationsMapper;


    @Override
    public MessageResult createOrganization(TWmsOrganizationsDTO organizationsDTO) {
        TWmsOrganizationsEntity organizationsEntity = BeanUtils.copyBeanPropertyUtils(organizationsDTO,TWmsOrganizationsEntity.class);
        organizationsMapper.insertOrganization(organizationsEntity);
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult modifyOrganization(TWmsOrganizationsDTO organizationsDTO) {
        TWmsOrganizationsEntity organizationsEntity = BeanUtils.copyBeanPropertyUtils(organizationsDTO, TWmsOrganizationsEntity.class);
       organizationsMapper.updateOrganization(organizationsEntity);

        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult removeOrganization(Long id) {
        organizationsMapper.deleteOrganization(id);
        return MessageResult.getSucMessage();
    }

    @Override
    public PageResponse<List<TWmsOrganizationsEntity>> queryOrganizationPages(TWmsOrganizationsDTO organizationsDTO) {
        TWmsOrganizationsEntity organizationsEntity = BeanUtils.copyBeanPropertyUtils(organizationsDTO,TWmsOrganizationsEntity.class);
        List<TWmsOrganizationsEntity> list = organizationsMapper.queryOrganizationPages(organizationsEntity);
        Integer total = organizationsMapper.queryOrganizationPageCount(organizationsEntity);
        PageResponse<List<TWmsOrganizationsEntity>> response = new PageResponse();
        response.setTotal(total);
        response.setRows(list);
        return response;
    }

    @Override
    public TWmsOrganizationsEntity findById(Long id) {
        return organizationsMapper.selectById(id);
    }

    @Override
    public List<TWmsOrganizationsEntity> queryAll() {
        return organizationsMapper.selectAll();
    }
}
