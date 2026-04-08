package github.javaguide.utils;

/**
 * 功能描述：获取CPU核心数，这种代码99% 出现在 Netty、RPC、网关、线程池、高性能框架里。
 *
 * @author chenxiaoxiao
 * @createTime 2026/4/8 下午4:23
 */
public class RuntimeUtil {

    /**
     * 获取CPU核心数，用来做线程池大小、并发数自动优化
     * @return CPU核心数
     */
    public static int cpus(){
        return Runtime.getRuntime().availableProcessors();
    }
}
