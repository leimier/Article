package util;


/**
 * Created by Dragon on 2016/11/28.
 *
 */
public class StringUtils {
    public static boolean isEmpty(String str){
        return null == str || str.equals("")
                || str.matches("\\s*");
    }

    public static String defaultValue(String content, String defaultValue){
        if (isEmpty((content))){
            return defaultValue;
        }
        return content;
    }

    /**
     * 把数据库字段名改为驼峰方式
     * @param column
     * @return
     */
    public static String columnToProperty(String column){
        // 如果字段为空，就返回空字符串
        if (isEmpty(column)) return "";

        // 获取字段长度
        Byte length = (byte) column.length();

        StringBuilder sb = new StringBuilder(length);
        int i = 0;
        for (int j = 0; j < length; j++){
            if (column.charAt(j) == '_'){
                while (column.charAt(j + 1) == '_'){
                    j += 1;
                }
                sb.append(("" + column.charAt(++j)).toUpperCase());
            }else {
                sb.append(column.charAt(j));
            }
        }
        return sb.toString();
    }

    /**
     * 将一个字符串的首字母改成大写
     * @param str
     * @return
     */
    public static String upperCaseFirstCharacter(String str){
        StringBuilder sb = new StringBuilder();
        char[] arr = str.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            if(i==0) sb.append((arr[i] + "").toUpperCase());
            else sb.append((arr[i]+""));
        }
        return sb.toString();
    }

}
