INSERT INTO `db_csmart_main`.`t_smart_code_header`(`list_name`,`is_system`,`is_read_only`,`description`,`create_user`,`create_time`,`update_user`,`update_time`)
 VALUES ('QualityStatus','0','0','质检状态','admin',NOW(),'admin',NOW());
select id into @h_id from `db_csmart_main`.`t_smart_code_header` where list_name = 'QualityStatus';
INSERT INTO `db_csmart_main`.`t_smart_code_detail` ( `code_id`,`code_value`,`code_name`,`is_default`,`is_active`,`is_system`,`sequence`,`description`,`create_user`,`create_time`,`update_user`,`update_time`)
VALUES (@h_id,'Doing','质检中','0','1','0','0',NULL,'admin',NOW(),'admin',NOW()),
(@h_id,'Finished','已完成','0','1','0','0',NULL,'admin',NOW(),'admin',NOW()),
(@h_id,'Initial','未提交','0','1','0','0',NULL,'admin',NOW(),'admin',NOW()),
(@h_id,'Invalid','已作废','0','1','0','0',NULL,'admin',NOW(),'admin',NOW());


INSERT INTO `db_csmart_main`.`t_smart_code_header` (`list_name`, `is_system`, `is_read_only`, `description`, `create_user`, `create_time`, `update_user`, `update_time`)
values('MachiningType','0','0','加工类型','admin','1490755702294','admin','1490755702295');
select id into @h_id from `db_csmart_main`.`t_smart_code_header` where list_name = 'MachiningType';
INSERT INTO `db_csmart_main`.`t_smart_code_detail` (`code_id`, `code_value`, `code_name`, `is_default`, `is_active`, `is_system`, `sequence`, `description`, `create_user`, `create_time`, `update_user`, `update_time`)
values (@h_id,'consume','消耗','0','1','0','1','','admin','1490755757330','admin','1490755757331');
insert into `t_smart_code_detail` (`code_id`, `code_value`, `code_name`, `is_default`, `is_active`, `is_system`, `sequence`, `description`, `create_user`, `create_time`, `update_user`, `update_time`)
values (@h_id,'output','产出','0','1','0','2','','admin','1490755857782','admin','1490755857783');


INSERT INTO `db_csmart_main`.`t_smart_code_header` (`list_name`, `is_system`, `is_read_only`, `description`, `create_user`, `create_time`, `update_user`, `update_time`)
values ('HandleType','0','0','操作类型','admin','1490766528445','admin','1490766549322');
select id into @h_id from `db_csmart_main`.`t_smart_code_header` where list_name = 'HandleType';
INSERT INTO `t_smart_code_detail` (`code_id`, `code_value`, `code_name`, `is_default`, `is_active`, `is_system`, `sequence`, `description`, `create_user`, `create_time`, `update_user`, `update_time`)
values(@h_id,'machining','加工','0','1','0','1','','admin','1490766604238','admin','1490766604239');
insert into `t_smart_code_detail` (`code_id`, `code_value`, `code_name`, `is_default`, `is_active`, `is_system`, `sequence`, `description`, `create_user`, `create_time`, `update_user`, `update_time`)
values(@h_id,'packing','包装','0','1','0','2','','admin','1490766643048','admin','1490766643049');
insert into `t_smart_code_detail` (`code_id`, `code_value`, `code_name`, `is_default`, `is_active`, `is_system`, `sequence`, `description`, `create_user`, `create_time`, `update_user`, `update_time`)
values(@h_id,'proPackaging','加工包装','0','1','0','3','','admin','1490766702732','admin','1490766709480');


INSERT INTO `db_csmart_main`.`t_smart_warehouse` (`id`,`tenant_id`,`warehouse_no`,`warehouse_name`,`is_active`,`type_code`,`country`,`province`,`city`,`district`,`zip`,`address`,`contact`,`telephone`,`fax`,`email`,`description`,`is_del`,`create_user`,`create_time`,`update_user`,`update_time`) VALUES
(908,88,'HSFGC','呼市分公司库房',1,'CDC','中国','内蒙古','呼和浩特',NULL,NULL,'呼市沙尔沁',NULL,NULL,NULL,NULL,NULL,0,'admin',1488424174399,'admin',1488424174400),
(909,88,'HSPXZX','呼市配销中心仓库',1,'CDC','中国','内蒙古','呼和浩特',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'admin',1488424207106,'admin',1488424207106),
(1059,88,'HEBCZ','河北沧州仓',1,'CDC','中国','河北省','沧州市',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,'admin',1488424207106,'admin',1488424207106);