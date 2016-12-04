package util;

import bean.User;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Created by Dragon on 2016/11/28.
 * 数据库工具类
 */
public class DataBaseUtils {
    private static String username;
    private static String password;
    private static String dataBaseName;

    // 类加载时就配置数据库
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        config("jdbc.properties");
    }

    /**
     * 配置数据库的基本信息
     * @param path 配置文件路径
     */
    public static void config(String path){
        InputStream inputStream = DataBaseUtils.class.getClassLoader().getResourceAsStream(path);
        Properties p = new Properties();
        try {
            p.load(inputStream);
            username = p.getProperty("db.username");
            password = p.getProperty("db.password");
            dataBaseName = p.getProperty("db.dataBaseName");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库连接
     * @return Connection
     */
    public static Connection getConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/"
                    + dataBaseName + "?useUnicode=true&characterEncoding=utf8&useSSL=true", username, password);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 关闭资源
     * @param connection
     * @param statement
     * @param rs
     */
    public static void closeConnection(Connection connection, PreparedStatement statement, ResultSet rs){
        try {
            if (rs!=null)rs.close();
            if (statement!=null)statement.close();
            if (connection!=null)connection.close();
        }catch (Exception e){
            e.fillInStackTrace();
        }
    }

    /**
     * DML表示—数据操纵语言，也就是SELECT，DELETE，UPDATE，INSERT。
     * DML操作
     * @param sql
     * @param objects
     */
    public static void update(String sql, Object...objects){
        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            for (int i = 0; i < objects.length; i++){
                statement.setObject(i+1, objects[i]);
            }
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeConnection(connection, statement, null);
        }
    }

    public static List<Map<String, Object>> queryForList(String sql, Object...objects){
        List<Map<String, Object>> result = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.prepareStatement(sql);
            for (int i = 0; i < objects.length; i++){
                statement.setObject(i+1, objects[i]);
            }

            rs = statement.executeQuery();
            while (rs.next()){
                ResultSetMetaData resultSetMetaData = rs.getMetaData();
                int count = resultSetMetaData.getColumnCount();  // 获取列数
                Map<String, Object> map = new HashMap<>();
                for (int i = 0; i < count; i++){
                    map.put(resultSetMetaData.getColumnName(i+1),
                            rs.getObject(resultSetMetaData.getColumnName(i+1)));
                }
                result.add(map);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeConnection(connection, statement, rs);
        }

        return result;
    }

    public static Map<String, Object> queryForMap(String sql, Object...objects) throws SQLException{
        Map<String, Object> result;
        List<Map<String, Object>> list = queryForList(sql, objects);
        if (list.size() != 1){
            return null;
        }
        result = list.get(0);
        return result;
    }

    /**
     * 查询数据，并返回一个JavaBean
     * @param sql
     * @param clazz
     * @param objects
     * @param <T>
     * @return
     */
    public static <T>T queryForBean(String sql, Class clazz, Object...objects){
        T obj = null;
        Map<String, Object> map = null;
        Field field = null;
        try {
            obj = (T) clazz.newInstance();  // 创建一个新的Bean实例
            map = queryForMap(sql, objects);
        }catch (InstantiationException | IllegalAccessException e){
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }
        if (map == null){
            return null;
        }

        for (String columnName : map.keySet()){
            Method method = null;
            String propertyName = StringUtils.columnToProperty(columnName);

            try {
                field = clazz.getDeclaredField(propertyName);
            }catch (NoSuchFieldException e){
                e.printStackTrace();
            }catch (SecurityException e){
                e.printStackTrace();
            }

            String fieldType = field.toString().split(" ")[1];  //获取该字段的类型
            Object value = map.get(columnName);
            if (value == null){
                continue;
            }
            String setMethodName = "set" + StringUtils.upperCaseFirstCharacter(propertyName);

            try {
                String valueType = value.getClass().getName();

                if (!fieldType.equalsIgnoreCase(valueType)){
                    if (fieldType.equalsIgnoreCase("java.lang.Integer")){
                        value = Integer.parseInt(String.valueOf(value));
                    }else if (fieldType.equalsIgnoreCase("java.lang.String")){
                        value = String.valueOf(value);
                    }else if (fieldType.equalsIgnoreCase("java.util.Date")){
                        valueType = "java.util.Date";
                        //将value转换成java.util.Date
                        String dateStr = String.valueOf(value);
                        Timestamp ts = Timestamp.valueOf(dateStr);
                        Date date = new Date(ts.getTime());
                        value = date;
                    }
                }
                //获取set方法
                method = clazz.getDeclaredMethod(setMethodName, Class.forName(fieldType));

                // 执行set方法
                method.invoke(obj, value);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return obj;
    }

    public static void main(String[] args){
//        String id = UUID.randomUUID() + "";
//        String createTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//        update("INSERT INTO t_user(id,username,password,sex,create_time,is_delete,address,telephone) "
//                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)", id,"无敌","abcdefg",1,createTime,0,"保密","保密");
        User user = DataBaseUtils.queryForBean("select * from t_user  limit 1", User.class);
        //System.out.println(user);
    }

}


