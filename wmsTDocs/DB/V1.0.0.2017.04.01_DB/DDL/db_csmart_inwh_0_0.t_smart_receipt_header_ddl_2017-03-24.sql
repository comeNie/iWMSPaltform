ALTER TABLE `db_csmart_inwh_0_0`.`t_smart_receipt_header` ADD COLUMN `qc_status_code` VARCHAR (10) DEFAULT 'Initial'
COMMENT '质检状态' AFTER `receipt_status_code`