package com.huamengtong.wms.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Evan on 2016/9/19.
 */
public class AutoIdConstants {

    /**
     * ******************** db_csmart_sku数据库里的表******
     */
    public static final int TWmsSkuEntity = 2000;

    public static final int TWmsSkuCargoOwnerEntity = 2500;


    /**
     * ******************** db_csmart_inwh_0_0数据库里的表******
     */
    public static final int TWmsReceiptHeaderEntity = 3000;
    public static final int TWmsReceiptDetailEntity = 3010;

    public static final int TWmsAsnHeaderEntity = 4000;
    public static final int TWmsAsnDetailEntity = 4010;

    public static final int TWmsQcHeaderEntity=5000;
    public static final int TWmsQcDetailEntity=5010;
    public static final int TWmsQcCheckEntity=5020;

    public static final int TWmsInventoryEntity=8000;
    public static final int TWmsInventoryLogEntity=8010;
    public static final int TWmsProInventoryEntiy=8050;
    public static final int TWmsProInventoryLogEntity=8040;


    public static final int TWmsAdjustmentHeaderEntity=8030;
    public static final int TWmsAdjustmentDetailEntity=8040;

    public static  final int TWmsMoveEntity = 8020;

    public static final int  TWmsTransactionEntity = 6000;

    public static final int TWmsProPackageDetailEntity = 6030;
    public static final int TWmsProPackageEntity = 6040;

    public static final int TWmsProInvPackageEntity = 8070;
    public static final int TWmsProInvPackageDetailEntity = 8080;


    /**
     * ******************** db_csmart_outwh_0_0数据库里的表******
     */
    public static final int TwmsShipmentHeaderEntity = 6000;
    public static final int TwmsShipmentDetailEntity = 6010;

    public static final int TwmsShipmentHeaderEntity_Deliver = 6020;

    public static final int TWmsDnDetailEntity = 7000;
    public static final int TWmsDnHeaderEntity = 7010;

    public static final int TWmsDnInvoiceEntity = 9000;
    public static final int TWmsSaleOrderEntity = 9010;


    public static final int TWmsFrozenHeaderEntity = 9020;
    public static final int TWmsFrozenDetailEntity = 9030;

    public static final int TWmsUnfrozenHeaderEntity = 9040;
    public static final int TWmsUnfrozenDetailEntity = 9050;



    public static final int TWmsAllocateEntity = 10100;

    public static  final  int TWmsStocktakingHeaderEntity = 9060;
    public static  final  int TWmsStocktakingDetailEntity = 9070;


    public static  final  int TWmsWaveEntity = 9080;




    /**
     * 获取需要到autoid服务中得到id的表
     * @return
     */
    public static final Map<String, Integer> getMap() {//每添加一个
        Map<String, Integer> map = new HashMap<>();
        map.put("TWmsSkuEntity", TWmsSkuEntity);
        map.put("TWmsReceiptHeaderEntity",TWmsReceiptHeaderEntity);
        map.put("TWmsReceiptDetailEntity",TWmsReceiptDetailEntity);
        map.put("TWmsAsnHeaderEntity",TWmsAsnHeaderEntity);
        map.put("TWmsAsnDetailEntity",TWmsAsnDetailEntity);
        map.put("TWmsQcHeaderEntity",TWmsQcHeaderEntity);
        map.put("TWmsQcDetailEntity",TWmsQcDetailEntity);
        map.put("TWmsInventoryEntity",TWmsInventoryEntity);
        map.put("TWmsInventoryLogEntity",TWmsInventoryLogEntity);
        map.put("TwmsShipmentHeaderEntity",TwmsShipmentHeaderEntity);
        map.put("TwmsShipmentDetailEntity",TwmsShipmentDetailEntity);
        map.put("TWmsDnDetailEntity",TWmsDnDetailEntity);
        map.put("TWmsDnHeaderEntity",TWmsDnHeaderEntity);
        map.put("TWmsDnInvoiceEntity",TWmsDnInvoiceEntity);
        map.put("TWmsSaleOrderEntity",TWmsSaleOrderEntity);
        map.put("TWmsFrozenHeaderEntity",TWmsFrozenHeaderEntity);
        map.put("TWmsFrozenDetailEntity",TWmsFrozenDetailEntity);
        map.put("TWmsUnfrozenHeaderEntity",TWmsUnfrozenHeaderEntity);
        map.put("TWmsUnfrozenDetailEntity",TWmsUnfrozenDetailEntity);
        map.put("TWmsAdjustmentHeaderEntity",TWmsAdjustmentHeaderEntity);
        map.put("TWmsAllocateEntity",TWmsAllocateEntity);
        map.put("TWmsAdjustmentDetailEntity",TWmsAdjustmentDetailEntity);
        map.put("TWmsStocktakingHeaderEntity",TWmsStocktakingHeaderEntity);
        map.put("TWmsStocktakingDetailEntity",TWmsStocktakingDetailEntity);
        map.put("TWmsTransactionEntity",TWmsTransactionEntity);
        map.put("TwmsShipmentHeaderEntity_Deliver",TwmsShipmentHeaderEntity_Deliver);
        map.put("TWmsWaveEntity",TWmsWaveEntity);
        map.put("TWmsProInventoryEntiy",TWmsProInventoryEntiy);
        map.put("TWmsSkuCargoOwnerEntity",TWmsSkuCargoOwnerEntity);
        map.put("TWmsQcCheckEntity",TWmsQcCheckEntity);
        return map;
    }

}
