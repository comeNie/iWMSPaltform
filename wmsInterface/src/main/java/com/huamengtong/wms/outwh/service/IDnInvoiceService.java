package com.huamengtong.wms.outwh.service;

import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.outwh.TWmsDnInvoiceDTO;
import com.huamengtong.wms.entity.outwh.TWmsDnInvoiceEntity;

import java.util.List;

public interface IDnInvoiceService {

    PageResponse<List<TWmsDnInvoiceEntity>> getDnInvoice(TWmsDnInvoiceDTO invoiceDTO, DbShardVO dbShardVO);

    TWmsDnInvoiceEntity findByPrimaryKey(Long id,DbShardVO dbShardVO);

    MessageResult createDnInvoice(TWmsDnInvoiceDTO invoiceDTO,DbShardVO dbShardVO);

    MessageResult removeDnInvoice(Long id,DbShardVO dbShardVO);

    MessageResult modifyDnInvoice(TWmsDnInvoiceDTO invoiceDTO,DbShardVO dbShardVO);

    Long saveOrUpdateDnInvoice(TWmsDnInvoiceDTO invoice, DbShardVO dbShardVO);

    List<TWmsDnInvoiceEntity> findByPrimaryKey(List<Long> ids,DbShardVO dbShardVO);
}
