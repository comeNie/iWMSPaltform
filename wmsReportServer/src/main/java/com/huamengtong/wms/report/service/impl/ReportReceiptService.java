package com.huamengtong.wms.report.service.impl;

import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.service.BaseService;
import com.huamengtong.wms.core.web.PageResponse;
import com.huamengtong.wms.dto.inwh.TWmsReceiptDetailDTO;
import com.huamengtong.wms.dto.report.TWmsReportReceiptDetailDTO;
import com.huamengtong.wms.entity.inwh.TWmsReceiptDetailEntity;
import com.huamengtong.wms.entity.inwh.TWmsReceiptHeaderEntity;
import com.huamengtong.wms.inwh.service.IReceiptDetailService;
import com.huamengtong.wms.inwh.service.IReceiptHeaderService;
import com.huamengtong.wms.report.service.IReportReceiptService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;


@Service
public class ReportReceiptService extends BaseService implements IReportReceiptService {


    @Autowired
    IReceiptDetailService receiptDetailService;

    @Autowired
    IReceiptHeaderService receiptHeaderService;


    public PageResponse<List<TWmsReportReceiptDetailDTO>> getReceiptDetailList(TWmsReportReceiptDetailDTO reportReceiptDetailDTO, DbShardVO dbShardVO){
        TWmsReceiptDetailDTO receiptDetailDTO = new TWmsReceiptDetailDTO();
        checkParamMeters(reportReceiptDetailDTO,receiptDetailDTO);
        List<TWmsReceiptHeaderEntity> receiptHeaderEntityList = null;
        List<Long> receiptIds = new ArrayList<>();
        if (reportReceiptDetailDTO.getCargoOwnerId() != null){
            receiptHeaderEntityList = receiptHeaderService.getIdsByCargoOwnerId(reportReceiptDetailDTO.getCargoOwnerId(),dbShardVO);
        }
        if(CollectionUtils.isNotEmpty(receiptHeaderEntityList)){
            for (TWmsReceiptHeaderEntity receiptHeaderEntity:receiptHeaderEntityList){
                receiptIds.add(receiptHeaderEntity.getId());
            }
        }
        receiptDetailDTO.setOffset(reportReceiptDetailDTO.getOffset());
        receiptDetailDTO.setPage(reportReceiptDetailDTO.getPage());
        receiptDetailDTO.setPageSize(reportReceiptDetailDTO.getPageSize());
        List<TWmsReceiptDetailEntity> receiptDetailEntityList = receiptDetailService.getReceiptDetailList(receiptDetailDTO,receiptIds,dbShardVO);
        if(CollectionUtils.isEmpty(receiptDetailEntityList)){
            return null;
        }
        if(CollectionUtils.isEmpty(receiptHeaderEntityList)){
            receiptIds = getReceiptHeaderIds(receiptDetailEntityList);
            receiptHeaderEntityList = receiptHeaderService.getHeaderListByIds(receiptIds,dbShardVO);
        }
        PageResponse<List<TWmsReportReceiptDetailDTO>> response = new PageResponse<>();
        response.setTotal(receiptDetailService.queryReceiptDetailPageCount(receiptDetailDTO,receiptIds,dbShardVO));
        response.setRows(getReportReceiptList(receiptDetailEntityList,receiptHeaderEntityList));
        return response;
    }

    private TWmsReportReceiptDetailDTO getReportReceiptDetail(TWmsReceiptHeaderEntity receiptHeaderEntity,TWmsReceiptDetailEntity receiptDetailEntity){
        TWmsReportReceiptDetailDTO reportReceiptDetailDTO = new TWmsReportReceiptDetailDTO();
        reportReceiptDetailDTO.setCargoOwnerId(receiptHeaderEntity.getCargoOwnerId());
        reportReceiptDetailDTO.setWarehouseId(receiptHeaderEntity.getWarehouseId());
        reportReceiptDetailDTO.setReceiptId(receiptHeaderEntity.getId());
        reportReceiptDetailDTO.setReceiptDetailId(receiptDetailEntity.getId());
        reportReceiptDetailDTO.setSkuId(receiptDetailEntity.getSkuId());
        reportReceiptDetailDTO.setSkuName(receiptDetailEntity.getSkuName());
        reportReceiptDetailDTO.setSku(receiptDetailEntity.getSku());
        reportReceiptDetailDTO.setSkuBarcode(receiptDetailEntity.getSkuBarcode());
        reportReceiptDetailDTO.setReceiptQty(receiptDetailEntity.getReceivedQty());
        reportReceiptDetailDTO.setStorageRoomId(receiptDetailEntity.getStorageRoomId());
        reportReceiptDetailDTO.setReceiptTime(receiptDetailEntity.getReceiptTime());
        return reportReceiptDetailDTO;
    }


    /**
     * 校验参数，赋值
     * @param reportReceiptDetailDTO
     * @param receiptDetailDTO
     */
    private void checkParamMeters(TWmsReportReceiptDetailDTO reportReceiptDetailDTO,TWmsReceiptDetailDTO receiptDetailDTO){
        if (reportReceiptDetailDTO.getReceiptId() !=null){
            receiptDetailDTO.setReceiptId(reportReceiptDetailDTO.getReceiptId());
        }
        if(reportReceiptDetailDTO.getStorageRoomId() !=null){
            receiptDetailDTO.setStorageRoomId(reportReceiptDetailDTO.getStorageRoomId());
        }
        if (reportReceiptDetailDTO.getSkuName() !=null){
            receiptDetailDTO.setSkuName(reportReceiptDetailDTO.getSkuName());
        }
        if (reportReceiptDetailDTO.getReceiptStartTime() !=null){
            receiptDetailDTO.setReceiptStartTime(reportReceiptDetailDTO.getReceiptStartTime());
            if(reportReceiptDetailDTO.getReceiptEndTime() !=null){
                receiptDetailDTO.setReceiptEndTime(reportReceiptDetailDTO.getReceiptEndTime());
            }
        }
    }

    private List<Long> getReceiptHeaderIds(List<TWmsReceiptDetailEntity> receiptDetailEntityList){
        List<Long> receiptIds = new ArrayList<>();
        for (TWmsReceiptDetailEntity receiptDetailEntity:receiptDetailEntityList){
            receiptIds.add(receiptDetailEntity.getReceiptId());
        }
        return receiptIds;
    }

    private List<TWmsReportReceiptDetailDTO> getReportReceiptList(List<TWmsReceiptDetailEntity> receiptDetailEntityList,List<TWmsReceiptHeaderEntity> receiptHeaderEntityList){
        Map<Long,TWmsReceiptHeaderEntity> map = new HashMap<>();
        for (TWmsReceiptHeaderEntity receiptHeaderEntity1:receiptHeaderEntityList){
            map.put(receiptHeaderEntity1.getId(),receiptHeaderEntity1);
        }
        List<TWmsReportReceiptDetailDTO> reportReceiptDetailDTOList = new ArrayList<>();
        for (TWmsReceiptDetailEntity receiptDetailEntity:receiptDetailEntityList){
            TWmsReceiptHeaderEntity receiptHeaderEntity = map.get(receiptDetailEntity.getReceiptId());
            reportReceiptDetailDTOList.add(getReportReceiptDetail(receiptHeaderEntity,receiptDetailEntity));
        }
        return reportReceiptDetailDTOList;
    }


    @Override
    public PageResponse<List<TWmsReportReceiptDetailDTO>> getReceiptSummaryList(TWmsReportReceiptDetailDTO reportReceiptDetailDTO, DbShardVO dbShardVO) {
        TWmsReceiptDetailDTO receiptDetailDTO = new TWmsReceiptDetailDTO();
        checkParamMeters(reportReceiptDetailDTO,receiptDetailDTO);
        List<TWmsReceiptHeaderEntity> receiptHeaderEntityList = null;
        List<Long> receiptIds = new ArrayList<>();
        if (reportReceiptDetailDTO.getCargoOwnerId() != null){
            receiptHeaderEntityList = receiptHeaderService.getIdsByCargoOwnerId(reportReceiptDetailDTO.getCargoOwnerId(),dbShardVO);
        }
        if(CollectionUtils.isNotEmpty(receiptHeaderEntityList)){
            for (TWmsReceiptHeaderEntity receiptHeaderEntity:receiptHeaderEntityList){
                receiptIds.add(receiptHeaderEntity.getId());
            }
        }
        receiptDetailDTO.setOffset(reportReceiptDetailDTO.getOffset());
        receiptDetailDTO.setPage(reportReceiptDetailDTO.getPage());
        receiptDetailDTO.setPageSize(reportReceiptDetailDTO.getPageSize());
        List<TWmsReceiptDetailEntity> receiptDetailEntityList = receiptDetailService.getReceiptSummaryList(receiptDetailDTO,receiptIds,dbShardVO);
        if(CollectionUtils.isEmpty(receiptDetailEntityList)){
            return null;
        }
        if(CollectionUtils.isEmpty(receiptHeaderEntityList)){
            receiptIds = getReceiptHeaderIds(receiptDetailEntityList);
            receiptHeaderEntityList = receiptHeaderService.getHeaderListByIds(receiptIds,dbShardVO);
        }
        PageResponse<List<TWmsReportReceiptDetailDTO>> response = new PageResponse<>();
        response.setTotal(receiptDetailService.queryReceiptSummaryPageCount(receiptDetailDTO,receiptIds,dbShardVO));
        response.setRows(getReportReceiptList(receiptDetailEntityList,receiptHeaderEntityList));
        return response;
    }


    @Override
    public PageResponse<List<TWmsReportReceiptDetailDTO>> getReceiptDetailListByMap(Map map, DbShardVO dbShardVO) {
        PageResponse<List<TWmsReportReceiptDetailDTO>> response = new PageResponse<>();
        response.setTotal(receiptHeaderService.getReceiptDetailPageCount(map,dbShardVO));
        response.setRows(receiptHeaderService.getReceiptDetailList(map,dbShardVO));
        return response;
    }

    @Override
    public PageResponse<List<TWmsReportReceiptDetailDTO>> getReceiptSummaryListByMap(Map map, DbShardVO dbShardVO) {
        PageResponse<List<TWmsReportReceiptDetailDTO>> response = new PageResponse<>();
        response.setTotal(receiptHeaderService.getReceiptSummaryPageCount(map,dbShardVO));
        response.setRows(receiptHeaderService.getReceiptSummaryList(map,dbShardVO));
        return response;
    }
}
