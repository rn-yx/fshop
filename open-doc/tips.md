
# Tips

## MySQL
### 查询库所有表名并以逗号分隔
```
SELECT GROUP_CONCAT(table_name  SEPARATOR ",") FROM information_schema.tables  WHERE TABLE_SCHEMA = 'seckill_product';
```
### 查询表所有列名并用逗号分隔
```
SELECT GROUP_CONCAT(column_name SEPARATOR ",") FROM information_schema.columns WHERE TABLE_SCHEMA = 'db_name' AND TABLE_NAME = 'table_name'
```

