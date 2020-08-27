
# Tips

### 查询数据库所有表名并以逗号分隔
```
SELECT GROUP_CONCAT(table_name  SEPARATOR ",") FROM information_schema.tables  WHERE TABLE_SCHEMA = 'seckill_product';
```
