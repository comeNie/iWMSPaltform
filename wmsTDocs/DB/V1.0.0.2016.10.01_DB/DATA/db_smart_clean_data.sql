-- 入库业务
DELETE FROM `t_smart_adjustment_detail`;
DELETE FROM `t_smart_adjustment_header`;
DELETE FROM `t_smart_asn_detail`;
DELETE FROM `t_smart_asn_header`;
DELETE FROM `t_smart_inventory`;
DELETE FROM `t_smart_inventory_log`;
DELETE FROM `t_smart_move`;
DELETE FROM `t_smart_pro_inventory`;
DELETE FROM `t_smart_pro_inventory_log`;
DELETE FROM `t_smart_qc_check`;
DELETE FROM `t_smart_qc_detail`;
DELETE FROM `t_smart_qc_header`;
DELETE FROM `t_smart_receipt_detail`;
DELETE FROM `t_smart_receipt_header`;
DELETE FROM `t_smart_stocktaking_detail`;
DELETE FROM `t_smart_stocktaking_header`;
DELETE FROM `t_smart_transaction`;

-- 出库业务
DELETE FROM `t_smart_allocate`;
DELETE FROM `t_smart_dn_detail`;
DELETE FROM `t_smart_dn_header`;
DELETE FROM `t_smart_dn_invoice`;
DELETE FROM `t_smart_frozen_detail`;
DELETE FROM `t_smart_frozen_header`;
DELETE FROM `t_smart_pick`;
DELETE FROM `t_smart_sale_order`;
DELETE FROM `t_smart_shipment_detail`;
DELETE FROM `t_smart_shipment_header`;
DELETE FROM `t_smart_unfrozen_detail`;
DELETE FROM `t_smart_unfrozen_header`;
DELETE FROM `t_smart_wave`;

-- 业务操作日志
DELETE FROM `t_smart_operation_log`;

-- 商品货主关系
DELETE FROM `t_smart_sku_cargo_owner`

