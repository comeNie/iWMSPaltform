package com.huamengtong.wms.inwh.mapper;

import com.huamengtong.wms.entity.inwh.TWmsReceiptDetailEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface ReceiptDetailMapper {

    TWmsReceiptDetailEntity selectByPrimaryKey(@Param("id") Long id, @Param("splitTableKey") String splitTableKey);

    Integer deleteByPrimaryKey(@Param("id") Long id, @Param("splitTableKey") String splitTableKey);

    Integer insertReceiptDetail(@Param("entity") TWmsReceiptDetailEntity receiptDetailEntity, @Param("splitTableKey") String splitTableKey);

    Integer updateReceiptDetail(@Param("entity") TWmsReceiptDetailEntity receiptDetailEntity, @Param("splitTableKey") String splitTableKey);

    List<TWmsReceiptDetailEntity> queryDetailsPages(Map map);

    Integer queryDetailPageCount(Map map);

    List<TWmsReceiptDetailEntity> queryReceiptDetails(@Param("headerId") Long HeaderId,@Param("splitTableKey") String splitTableKey);

    Integer deleteReceiptDetails(@Param("receiptId") Long receiptId,@Param("splitTableKey") String splitTableKey);

    List<TWmsReceiptDetailEntity> queryReceiptDetailPages(@Param("entity") TWmsReceiptDetailEntity receiptDetailEntity,@Param("headerIds") List<Long> headerIds,@Param("splitTableKey") String splitTableKey);

    Integer queryReceiptDetailsPageCount(@Param("entity") TWmsReceiptDetailEntity receiptDetailEntity,@Param("headerIds") List<Long> headerIds,@Param("splitTableKey") String splitTableKey);

    List<TWmsReceiptDetailEntity> queryReceiptSummaryPages(@Param("entity") TWmsReceiptDetailEntity receiptDetailEntity,@Param("headerIds") List<Long> headerIds,@Param("splitTableKey") String splitTableKey);

    Integer queryReceiptSummaryPageCount(@Param("entity") TWmsReceiptDetailEntity receiptDetailEntity,@Param("headerIds") List<Long> headerIds,@Param("splitTableKey") String splitTableKey);

    TWmsReceiptDetailEntity selectByAsnDetailId(@Param("asnDetailId") Long asnDetailId ,@Param("storageRoomId") String storageRoomId ,
                                                @Param("splitTableKey") String splitTableKey);
}
