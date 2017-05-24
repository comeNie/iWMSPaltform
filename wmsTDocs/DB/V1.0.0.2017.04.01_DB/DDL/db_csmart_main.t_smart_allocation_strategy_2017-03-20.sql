CREATE TABLE `db_csmart_main`.`t_smart_allocation_strategy` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tenant_id` bigint(20) NOT NULL COMMENT '分表字段',
  `warehouse_id` bigint(20) NOT NULL COMMENT '仓库ID',
  `storage_room_id` varchar(200) DEFAULT NULL COMMENT '分配库房,多个使用逗号分隔',
  `strategy_name` varchar(50) NOT NULL COMMENT '策略名称',
  `is_active` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否激活',
  `is_default` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否默认',
  `is_whole_priority` tinyint(4) NOT NULL DEFAULT '0' COMMENT '整库优先',
  `order_field_code` varchar(255) NOT NULL COMMENT '排序字段',
  `direction_code` varchar(20) NOT NULL COMMENT '排序方向(FIFO/FILO)',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `create_user` varchar(20) DEFAULT NULL COMMENT '记录创建者',
  `create_time` bigint(20) DEFAULT '0' COMMENT '记录创建时间',
  `update_user` varchar(20) DEFAULT NULL COMMENT '最后修改者',
  `update_time` bigint(20) DEFAULT '0' COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='库存分配策略表'
