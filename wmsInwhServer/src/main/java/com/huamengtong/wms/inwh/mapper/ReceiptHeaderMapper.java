package com.huamengtong.wms.inwh.mapper;

import com.huamengtong.wms.dto.report.TWmsReportReceiptDetailDTO;
import com.huamengtong.wms.entity.inwh.TWmsReceiptHeaderEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ReceiptHeaderMapper {

    List<TWmsReceiptHeaderEntity> queryReceiptHeaderPages(@Param("entity") TWmsReceiptHeaderEntity receiptHeaderEntity,@Param("splitTableKey") String splitTableKey);

    Integer queryReceiptHeaderPageCount(@Param("entity") TWmsReceiptHeaderEntity receiptHeaderEntity,@Param("splitTableKey") String splitTableKey);

    TWmsReceiptHeaderEntity selectByPrimaryKey(@Param("id") Long id,@Param("splitTableKey") String splitTableKey);

    Integer deleteByPrimaryKey(@Param("id") Long id,@Param("splitTableKey") String splitTableKey);

    Integer insertReceiptHeader(@Param("entity") TWmsReceiptHeaderEntity receiptHeaderEntity,@Param("splitTableKey") String splitTableKey);

    Integer updateReceiptHeader(@Param("entity") TWmsReceiptHeaderEntity receiptHeaderEntity,@Param("splitTableKey") String splitTableKey);

    TWmsReceiptHeaderEntity selectByAsnId(@Param("asnId") Long asnId,@Param("splitTableKey") String splitTableKey);

    List<TWmsReceiptHeaderEntity> getHeaderListByIds(@Param("ids") List<Long> ids,@Param("splitTableKey") String splitTableKey);

    List<TWmsReceiptHeaderEntity> getIdsByCargoOwnerId(@Param("cargoOwnerId") Long cargoOwnerId, @Param("splitTableKey") String splitTableKey);

    List<TWmsReportReceiptDetailDTO> getReceiptDetailList(Map map);

    Integer queryReceiptDetailPageCount(Map map);

    List<TWmsReportReceiptDetailDTO> getReceiptSummaryList(Map map);

    Integer getReceiptSummaryPageCount(Map map);


}
