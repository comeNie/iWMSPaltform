DROP TABLE IF EXISTS `t_smart_qc_check`;
CREATE TABLE `db_csmart_inwh_0_0`.`t_smart_qc_check` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `qc_id` bigint(20) NOT NULL COMMENT '质检主表ID',
  `warehouse_id` bigint(20) NOT NULL COMMENT '仓库Id',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户ID',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '业务单据ID',
  `qc_detail_id` bigint(20) NOT NULL COMMENT '质检子表ID',
  `sku_id` bigint(20) NOT NULL COMMENT 'SKUID',
  `qc_qty` int(11) DEFAULT NULL COMMENT '质检数量',
  `is_qualified` tinyint(4) DEFAULT NULL COMMENT '合格标志',
  `un_qualified_reason` varchar(1024) DEFAULT NULL COMMENT '不合格原因',
  `description` varchar(512) DEFAULT NULL COMMENT '备注',
  `type_code` varchar(20) DEFAULT NULL COMMENT '质检单据类型',
  `status_code` varchar(255) NOT NULL COMMENT '状态（未提交/已提交）',
  `create_user` varchar(255) DEFAULT NULL COMMENT '记录创建者',
  `create_time` bigint(20) DEFAULT NULL COMMENT '记录创建时间',
  `update_user` varchar(255) DEFAULT NULL COMMENT '最后更新者',
  `update_time` bigint(20) DEFAULT NULL COMMENT '最后更新时间',
  `is_del` tinyint(4) NOT NULL DEFAULT '0' COMMENT '已删除',
  PRIMARY KEY (`id`),
  KEY `idx_qc_check_sku` (`qc_detail_id`,`sku_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='质检单检查记录表';