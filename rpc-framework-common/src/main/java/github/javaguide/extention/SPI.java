package github.javaguide.extention;

import java.lang.annotation.*;

/**
 * 只作标记，不存值
 * 被它标记的接口，代表是一个 “可扩展接口” → 可以通过 SPI 机制，动态加载不同的实现类（插件化、可替换）。
 */

@Documented  //生成Java Doc文档时，会把这个注解显示出来
@Retention(RetentionPolicy.RUNTIME)  //生命周期:运行时保留，框架可以通过反射在运行时读到这个注解
@Target(ElementType.TYPE)  //只能标注在接口/类上
public @interface SPI {

}
