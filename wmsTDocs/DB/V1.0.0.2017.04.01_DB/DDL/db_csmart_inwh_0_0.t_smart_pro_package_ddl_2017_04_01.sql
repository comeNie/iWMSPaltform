CREATE TABLE `db_csmart_inwh_0_0`.`t_smart_pro_package` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `tenant_id` bigint(20) NOT NULL COMMENT 'tenant',
  `warehouse_id` bigint(20) NOT NULL COMMENT '仓库Id',
  `cargo_owner_id` bigint(20) NOT NULL COMMENT '货主Id',
  `storage_room_id` bigint(20) NOT NULL COMMENT '库房Id',
  `sku_id` bigint(20) NOT NULL COMMENT '商品Id',
  `spec` varchar(50) NOT NULL COMMENT '包装商品规格',
  `qty` int(11) NOT NULL DEFAULT '1' COMMENT '包装商品数量',
  `work_group_no` varchar(20) DEFAULT NULL COMMENT '包装批次号',
  `status_code` varchar(20) NOT NULL COMMENT '单据状态',
  `create_user` varchar(20) NOT NULL COMMENT '创建人',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_user` varchar(20) NOT NULL COMMENT '修改人',
  `update_time` bigint(20) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='库内包装信息表'