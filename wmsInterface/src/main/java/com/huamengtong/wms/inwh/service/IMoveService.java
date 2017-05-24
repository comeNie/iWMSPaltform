package com.huamengtong.wms.inwh.service;

import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.inwh.TWmsMoveDTO;
import com.huamengtong.wms.entity.inwh.TWmsMoveEntity;

import java.util.List;

/**
 * Created by mario on 2016/11/18.
 */
public interface IMoveService {

    PageResponse<List<TWmsMoveEntity>> getMove(TWmsMoveDTO moveDTO, DbShardVO dbShardVO);

    TWmsMoveEntity findByPrimaryKey(Long id,DbShardVO dbShardVO);

    MessageResult createMove(TWmsMoveDTO moveDTO,DbShardVO dbShardVO);

    MessageResult removeMove(Long id,DbShardVO dbShardVO);

    MessageResult modifyMove(TWmsMoveDTO moveDTO,DbShardVO dbShardVO);

    MessageResult executeSubmitMove(Long id, String operationUser, DbShardVO dbShardVO);

}
