package github.javaguide.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * 功能描述：你给它一个配置文件名，它去项目的配置目录找到这个文件
 * ，读完后返回给你一个键值对对象，让你能轻松获取配置信息。
 *
 * @author chenxiaoxiao
 * @createTime 2026/4/8 下午3:50
 */
@Slf4j
public final class PropertiesFileUtil {
    private PropertiesFileUtil(){}

    /**
     * 读取配置文件
     * @param fileName
     * @return
     */
    public static Properties readPropertiesFile(String fileName){
        //获取项目根路径
        URL url = Thread.currentThread().getContextClassLoader().getResource("");
        String rpcConfigPath = "";
        if (url != null){
            rpcConfigPath = url.getPath() + fileName;
        }
        Properties properties = null;
        //读取文件
        try (InputStreamReader inputStreamReader = new InputStreamReader(
                new FileInputStream(rpcConfigPath) , StandardCharsets.UTF_8
        )){
            //文件为空
            if (inputStreamReader==null){
                log.error("文件不存在，为空，请检查！");
            }
            //加载配置到properti对象
            properties = new Properties();
            //把文件里的内容自动加载成键值对，可以通过：properties.getProperty() 获取值
            properties.load(inputStreamReader);

        } catch (IOException e) {
            log.error("occur exception when read properties file [{}]",fileName);
        }
        return properties;
    }
}

