package github.javaguide.factory;

import github.javaguide.extention.Holder;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 获取单例对象的工厂类
 *
 * @author chenxiaoxiao
 * @createTime 2026/4/8 下午6:23
 */
@Slf4j
public class SingletonFactory {
    private static final Map<String, Object> OBJECT_MAP = new ConcurrentHashMap<>();
    private static final Object lock = new Object();

    private static final Map<String, Holder<Object>> OBJECT_MAP_NEW = new HashMap<>();

    private SingletonFactory(){}

    public static<T> T getInstance(Supplier<T> constructor, Class<T> c) {
        if (c == null){
            throw new IllegalArgumentException("class cannot be null");
        }
        String key = c.getName();

        //1、第一次检查：快速读取缓存（无锁）
        Holder<Object> holder = OBJECT_MAP_NEW.get(key);
        if (holder != null && holder.get() != null){
            // 1.1 holder保证了可见性，从而不会使用没有初始化的对象
            return c.cast(holder.get());
        }

        //2、同步快：确保只有一个线程创建实例
        synchronized (lock){
            //3、第二次检查：防止其他线程已创建holder
            holder = OBJECT_MAP_NEW.computeIfAbsent(key,k->new Holder<>());

            //4、创建实例（此处不需要再次检查holder.get(),因为锁保证了互斥性）
            if (holder.get() == null){
                try {
                    //4.1 创建对象
                    T instance = constructor.get();
                    //4.2 放入到map里面
                    holder.set(instance);
                }catch (Exception e){
                    throw new RuntimeException("创建示例失败",e);
                }
            }
        }
        return c.cast(holder.get());
    }

    public static <T> T getInstance(Consumer<T> initConsumer, Class<T> c){
        if (c==null){
            throw new IllegalArgumentException("Class cannot be null");
        }
        String key = c.getName();

        //1、第一次检查：快速读取缓存（无锁)
        Holder<Object> holder = OBJECT_MAP_NEW.get(key);
        if (holder != null && holder.get()!=null){
            // 1.1 holder保证了可见性，从而不会使用没有初始化的对象
            return c.cast(holder.get());
        }

        //2、同步块，确保只有一个线程创建实例
        synchronized (lock){
            //3、第二次检查：防止其他线程已创建holder
            holder = OBJECT_MAP_NEW.computeIfAbsent(key,k->new Holder<>());

            //4、创建实例（此处不需要再次检查holder.get(),因为锁保证了互斥性）
            if (holder.get() == null){
                try {
                    //4.1创建对象
                    T instance = c.getDeclaredConstructor().newInstance();
                    //4.2 初始化对象
                    initConsumer.accept(instance);
                    // 4.3 放入map(holder里面)
                    holder.set(instance);
                } catch (Exception e) {
                    throw new RuntimeException("创建实例失败：" , e);
                }
            }
        }
        return c.cast(holder.get());
    }

    public static <T> T getInstance(Class<T> c){
        if (c == null){
            throw new IllegalArgumentException("Class cannot be null");
        }
        String key = c.getName();

        //1、 第一次检查：快速读取缓存（无锁）
        Holder<Object> holder = OBJECT_MAP_NEW.get(key);
        if (holder.get()!=null && holder!=null){
            return c.cast(holder.get());
        }

        //2\同步块：确保只有一个线程创建实例
        synchronized (lock){
            //3、第二次检查
            holder = OBJECT_MAP_NEW.get(key);
            //4、创建实例
            if (holder.get()==null){
                try{
                    Constructor<T> constructor = c.getDeclaredConstructor();
                    constructor.setAccessible(true);
                    T instance = constructor.newInstance();
                    //放入map
                    holder.set(instance);

                }catch (Exception e){
                    throw new RuntimeException("创建实例失败："+e);
                }
            }
        }
        return c.cast(holder.get());
    }





}
