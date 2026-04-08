package github.javaguide.utils;

/**
 * 功能描述：String 工具类
 *
 * @author chenxiaoxiao
 * @createTime 2026/4/8 下午4:26
 */
public class StringUtil {

    /**
     * 用来做参数的非空判断，只要全是空白就会返回，比empty好用
     * @param s
     * @return
     */
    public static boolean isBlank(String s){
        if (s==null || s.length()==0){
            return true;
        }
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isWhitespace(s.charAt(i))){
                return false;
            }
        }
        return true;
    }
}
