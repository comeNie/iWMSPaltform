CREATE TABLE `db_csmart_inwh_0_0`.`t_smart_pro_inv_package` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `tenant_id` bigint(20) NOT NULL COMMENT 'tenant',
  `warehouse_id` bigint(20) NOT NULL COMMENT '仓库Id',
  `cargo_owner_id` bigint(20) NOT NULL COMMENT '货主Id',
  `work_group_no` varchar(20) DEFAULT NULL COMMENT '批次号',
  `type_code` varchar(20) NOT NULL COMMENT '单据类型',
  `status_code` varchar(20) NOT NULL COMMENT '单据状态',
  `create_user` varchar(20) NOT NULL COMMENT '创建人',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_user` varchar(20) NOT NULL COMMENT '修改人',
  `update_time` bigint(20) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='库内分选包装主表'
