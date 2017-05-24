package com.huamengtong.wms.api.remote.service.impl;

import com.huamengtong.wms.api.dto.CreateAsnDTO;
import com.huamengtong.wms.api.remote.service.IAsnRemoteService;
import com.huamengtong.wms.constants.GlobalConstants;
import com.huamengtong.wms.core.formwork.db.domain.CurrentUserEntity;
import com.huamengtong.wms.core.formwork.db.splitdb.ShareDbUtil;
import com.huamengtong.wms.core.formwork.db.util.DbShareField;
import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.web.MessageResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AsnRemoteService implements IAsnRemoteService {

    @Override
    public MessageResult createAsn(List<CreateAsnDTO> list) {

        return null;
    }

    protected DbShardVO getDbShardVO(Long warehouseId, DbShareField...source) {
        CurrentUserEntity currentUserEntity = new CurrentUserEntity();
        currentUserEntity.setTenantId(GlobalConstants.DEFAULT_TENANT_ID);
        currentUserEntity.setUserName(GlobalConstants.GLOBAL_USER_NAME);
        return ShareDbUtil.getDbShardVO(currentUserEntity,warehouseId,source);
    }
}
