package github.javaguide.utils;

import java.util.Collection;

/**
 * 功能描述：集合工具类
 *
 * @author chenxiaoxiao
 * @createTime 2026/4/8 下午3:46
 */
public class ColletionUtil {
    public static boolean isEmpty(Collection<?> c){
        return c == null|| c.isEmpty();
    }
}
