package com.huamengtong.wms.api.remote.service;

import com.huamengtong.wms.api.dto.CreateAsnDTO;
import com.huamengtong.wms.core.web.MessageResult;

import java.util.List;

/**
 * Created by Edwin on 2016/11/8.
 */
public interface IAsnRemoteService {

    MessageResult createAsn(List<CreateAsnDTO> list);
}
