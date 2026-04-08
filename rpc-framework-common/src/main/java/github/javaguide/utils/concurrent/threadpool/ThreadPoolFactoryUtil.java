package github.javaguide.utils.concurrent.threadpool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import github.javaguide.extention.SPI;
import lombok.extern.slf4j.Slf4j;
import sun.nio.ch.ThreadPool;

import java.lang.management.ThreadInfo;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 功能描述：创建线程池工具类
 *
 * @author chenxiaoxiao
 * @createTime 2026/4/8 下午7:44
 */
@Slf4j
public final class ThreadPoolFactoryUtil {

    /**
     * 通过 threadNamePrefix 来区分不同线程池（我们可以把相同 threadNamePrefix 的线程池看作是为同一业务场景服务）。
     * key: threadNamePrefix
     * value: threadPool
     */
    private static final Map<String, ExecutorService> THREAD_POOLS = new ConcurrentHashMap<>();

    private ThreadPoolFactoryUtil(){}

    public static ExecutorService createCustomThreadPoolIsAbsent(String threadNamePrefix){
        CustomThreadPoolConfig customThreadPoolConfig = new CustomThreadPoolConfig();
        return createCustomThreadPoolIsAbsent(customThreadPoolConfig,threadNamePrefix,false);
    }

    private static ExecutorService createCustomThreadPoolIsAbsent(CustomThreadPoolConfig customThreadPoolConfig, String threadNamePrefix, Boolean daemon) {
        ExecutorService threadPool = THREAD_POOLS.computeIfAbsent(threadNamePrefix,k -> createThreadPool(customThreadPoolConfig,threadNamePrefix,daemon));
        //如果threadPool被shutDown的话，就重新创建一个
        if (threadPool.isShutdown() || threadPool.isTerminated()){
            THREAD_POOLS.remove(threadNamePrefix);
            threadPool = createThreadPool(customThreadPoolConfig,threadNamePrefix,daemon);
            THREAD_POOLS.put(threadNamePrefix,threadPool);
        }
        return threadPool;
    }

    private static ExecutorService createThreadPool(CustomThreadPoolConfig customThreadPoolConfig, String threadNamePrefix, Boolean daemon) {
        ThreadFactory threadFactory = createThreadFactory(threadNamePrefix,daemon);
        return new ThreadPoolExecutor(
                customThreadPoolConfig.getCorePoolsize(),customThreadPoolConfig.getMaximumPoolsize(),
                customThreadPoolConfig.getKeepAliveTime(),customThreadPoolConfig.getUnit(),
                customThreadPoolConfig.getWorkQueue(),
                threadFactory);
    }

    /**
     * 创建 ThreadFactory 。如果threadNamePrefix不为空则使用自建ThreadFactory，否则使用defaultThreadFactory
     *
     * @param threadNamePrefix 作为创建的线程名字的前缀
     * @param daemon           指定是否为 Daemon Thread(守护线程)
     * @return ThreadFactory
     */
    private static ThreadFactory createThreadFactory(String threadNamePrefix, Boolean daemon) {
        if (threadNamePrefix != null){
            if (daemon != null){
                return new ThreadFactoryBuilder()
                        .setNameFormat(threadNamePrefix + "-%d")
                        .setDaemon(daemon).build();
            }else {
                return new ThreadFactoryBuilder().setNameFormat(threadNamePrefix+"-d%").build();
            }
        }
        return Executors.defaultThreadFactory();
    }
    /**
     * 打印线程池的状态
     *
     * @param threadPool 线程池对象
     */
    public static void printTreadPoolStatus(ThreadPoolExecutor threadPool){
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1,createThreadFactory("print-thread-pool-status",false));
        scheduledExecutorService.scheduleAtFixedRate(
                ()->{
                    log.info("======== ThreadPool Status =========");
                    log.info("ThreadPool Size: [{}]", threadPool.getPoolSize());
                    log.info("Active Threads: [{}]", threadPool.getActiveCount());
                    log.info("Number of Tasks : [{}]", threadPool.getCompletedTaskCount());
                    log.info("Number of Tasks in Queue: {}", threadPool.getQueue().size());
                    log.info("===========================================");
                },0,1,TimeUnit.SECONDS
        );

    }



}
