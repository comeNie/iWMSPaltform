-- 数据库业务表克隆脚本生成
SELECT table_name,CONCAT('create table ',table_schema, '.', REPLACE(table_name,'_1','_1'), ' like ', table_schema, '.', REPLACE(table_name,'_1',''),';') ddl
 FROM information_schema.`TABLES`
WHERE table_schema IN ('db_csmart_inwh_0_0','db_csmart_outwh_0_0')
AND table_name LIKE '%_1%';