
-- 入库通知单
DELETE FROM `db_csmart_inwh_0_0`.`t_smart_asn_header` WHERE id = 3019 LIMIT 1;
DELETE FROM `db_csmart_inwh_0_0`.`t_smart_asn_detail` WHERE id IN(4026,7038) LIMIT 2;


-- 质检单
DELETE FROM `db_csmart_inwh_0_0`.`t_smart_qc_header` WHERE id = 1011 LIMIT 1;
DELETE FROM `db_csmart_inwh_0_0`.`t_smart_qc_detail` WHERE id IN(10043,1056) LIMIT 2;

-- 入库单
DELETE FROM `db_csmart_inwh_0_0`.`t_smart_receipt_header` WHERE id = 15029 LIMIT 1;
DELETE FROM `db_csmart_inwh_0_0`.`t_smart_receipt_detail` WHERE id = 3051 LIMIT 1;

-- 库存
DELETE FROM `db_csmart_inwh_0_0`.`t_smart_inventory` WHERE id = 9010 LIMIT 1;
-- 库存日志
DELETE FROM `db_csmart_inwh_0_0`.`t_smart_inventory_log` WHERE order_id = 1011 AND type_code = 'Qc';


-- 出库通知单
DELETE FROM `db_csmart_outwh_0_0`.`t_smart_dn_header` WHERE id = 1024 LIMIT 1;
DELETE FROM `db_csmart_outwh_0_0`.`t_smart_dn_detail` WHERE id = 7014 LIMIT 1;

-- 订单表
DELETE FROM `db_csmart_outwh_0_0`.`t_smart_sale_order` WHERE id = 9021 LIMIT 1;

-- 出库单
DELETE FROM `db_csmart_outwh_0_0`.`t_smart_shipment_header` WHERE id = 4019 LIMIT 1;
DELETE FROM `db_csmart_outwh_0_0`.`t_smart_shipment_detail` WHERE id = 1021 LIMIT 1;


-- 盘点
DELETE FROM `db_csmart_outwh_0_0`.`t_smart_stocktaking_header` WHERE id = 2033 LIMIT 1;
DELETE FROM `db_csmart_outwh_0_0`.`t_smart_stocktaking_detail` WHERE id IN (5037,7032) LIMIT 2;
