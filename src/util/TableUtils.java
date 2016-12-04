package util;

import annotation.Column;
import annotation.Table;

import java.lang.reflect.Field;
import java.util.UUID;

/**
 * Created by Dragon on 2016/11/28.
 *
 */
public class TableUtils {
    public static String getCreateTableSQL(Class<?> clazz){
        StringBuilder sb = new StringBuilder();
        // 获取表名
        Table table = clazz.getAnnotation(Table.class);
        String tableName = table.tableName();

        sb.append("DROP TABLE IF EXISTS ").append(tableName).append(";\n");
        sb.append("create table ");
        sb.append(tableName).append("(\n");String sql = "INSERT INTO t_article(id,header,name,content,author,"
                + "description,is_published,is_delete,create_time,update_time"
                + ",user_id,category_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?) ";
        String id = UUID.randomUUID().toString(); //主键
        String header = "Java Web实用技术";
        String name  = "如何将MyEclipse项目导入eclipse";
        String content = "我们经常会在网上下载一些开源项目，或者从别的地方迁移一些项目进来，但经常会发现导入后各种报错。这是初学java肯定会遇到的问题，本文对一些常见的处理方案做一个总结。（本文将MyEclipse项目导入eclipse的过程为例，其他情况也可参考这个流程）";
        String author = "Jack";
        String description = "解决项目导入的冲突问题...";
        int isPublished = 1 ;
        int isDelete = 0;
        String create_time = "2016-10-19 10:43:10";
        String update_time = "2016-10-19 10:43:10";
        String userId = "319600c3-550a-4f9f-80cf-deebe2376528";
        int categoryId = 2;

        Field[] fields = clazz.getDeclaredFields();
        String primaryKey = "";
        // 遍历所有字段
        for (Field f : fields){
            Column column = (Column) f.getAnnotations()[0];
            String field = column.field();
            String type = column.type();
            boolean defaultNull = column.defaultNull();

            sb.append("\t").append(field).append(" ").append(type);
            if (defaultNull){
                if (type.toUpperCase().equals("TIMESTAMP")){
                    sb.append(",\n");
                }else {
                    sb.append(" DEFAULT NULL,\n");
                }
            }else {
                sb.append(" NOT NULL,\n");
                if (column.primayKey()){
                    primaryKey = "PRIMARY KEY (" + field + ")";
                }
            }
        }

        if (!StringUtils.isEmpty(primaryKey)){
            sb.append("\t").append(primaryKey);
        }
        sb.append("\n) DEFAULT CHARSET=utf8");

        return sb.toString();
    }
}
