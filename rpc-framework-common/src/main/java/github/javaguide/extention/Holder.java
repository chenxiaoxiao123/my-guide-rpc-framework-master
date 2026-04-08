package github.javaguide.extention;

/**
 * 功能描述：带线程安全可见性的、能装任意类型数据的 “通用容器”，
 * 作用：在多线程环境下，安全地存一个值、取一个值。
 *
 * @author chenxiaoxiao
 * @createTime 2026/4/8 下午4:50
 */
public class Holder <T>{
    private volatile T value;

    public T get(){
        return value;
    }

    public void set(T value){
        this.value = value;
    }
}
