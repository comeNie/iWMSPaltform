CREATE TABLE `db_csmart_inwh_0_0`.`t_smart_pro_inv_package_detail` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `parent_id` bigint(20) NOT NULL COMMENT '头表Id',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户Id',
  `warehouse_id` bigint(20) NOT NULL COMMENT '仓库Id',
  `inv_package_id` bigint(20) NOT NULL COMMENT '产品id',
  `inv_package_qty` int(11) NOT NULL DEFAULT '0' COMMENT '产品数量',
  `storage_room_id` bigint(20) NOT NULL COMMENT '产品库房',
  `spec` varchar(50) NOT NULL COMMENT '商品规格',
  `unit_type` varchar(20) NOT NULL DEFAULT '0' COMMENT '加工单位',
  `type_code` varchar(20) NOT NULL DEFAULT '0' COMMENT '加工类型',
  `create_user` varchar(20) NOT NULL COMMENT '创建人',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_user` varchar(20) NOT NULL COMMENT '修改人',
  `update_time` bigint(20) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='库内分选包装明细表'
