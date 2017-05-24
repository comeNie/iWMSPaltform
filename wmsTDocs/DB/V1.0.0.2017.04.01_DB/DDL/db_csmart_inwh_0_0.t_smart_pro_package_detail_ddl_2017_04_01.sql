CREATE TABLE `db_csmart_inwh_0_0`.`t_smart_pro_package_detail` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `parent_id` bigint(20) NOT NULL COMMENT '头表Id',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户Id',
  `warehouse_id` bigint(20) NOT NULL COMMENT '仓库Id',
  `pro_inventory_id` bigint(20) NOT NULL COMMENT '包装产品id',
  `pro_inventory_qty` int(11) NOT NULL DEFAULT '1' COMMENT '包装产品数量',
  `pro_storage_room_id` bigint(20) NOT NULL COMMENT '产品库房来源',
  `create_user` varchar(20) NOT NULL COMMENT '创建人',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_user` varchar(20) NOT NULL COMMENT '修改人',
  `update_time` bigint(20) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='库内包装明细表'
