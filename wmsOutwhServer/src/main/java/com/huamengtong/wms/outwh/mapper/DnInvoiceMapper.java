package com.huamengtong.wms.outwh.mapper;

import com.huamengtong.wms.entity.outwh.TWmsDnInvoiceEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by mario on 2016/11/9.
 */
public interface DnInvoiceMapper {

    List<TWmsDnInvoiceEntity> queryDnInvoicePages (@Param("entity") TWmsDnInvoiceEntity  invoiceEntity,@Param("splitTableKey") String splitTableKey);

    Integer queryDnInvoicePageCount(@Param("entity") TWmsDnInvoiceEntity invoiceEntity,@Param("splitTableKey") String splitTableKey);

    TWmsDnInvoiceEntity selectByPrimaryKey(@Param("id") Long id,@Param("splitTableKey") String splitTableKey);

    Integer insertDnInvoice(@Param("entity") TWmsDnInvoiceEntity invoiceEntity,@Param("splitTableKey") String splitTableKey);

    Integer deleteByPrimaryKey(@Param("id") Long id,@Param("splitTableKey") String splitTableKey);

    Integer updateDnInvoice(@Param("entity") TWmsDnInvoiceEntity dnInvoiceEntity,@Param("splitTableKey") String splitTableKey);

    List<TWmsDnInvoiceEntity> selectByPrimaryKeyList(@Param("ids") List<Long> ids,@Param("splitTableKey") String splitTableKey);
}
