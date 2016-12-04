package test;

import util.DataBaseUtils;

import java.sql.Connection;


/**
 * Created by Dragon on 2016/11/28.
 * 测试读取数据库配置文件
 */
public class TestProperties {
    public static void main(String[] args){
        Connection conn = DataBaseUtils.getConnection();
        System.out.print(conn);
    }
}
