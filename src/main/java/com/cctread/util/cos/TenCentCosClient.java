package com.cctread.util.cos;

import com.core.thread.CustomThreadFactory;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 腾讯云存储服务客户端
 *
 * @author caiteng
 * @version 1.0 创建时间：2018-01-31 13:58
 */
@Component
public class TenCentCosClient {

    private static final Logger log = LoggerFactory.getLogger(TenCentCosClient.class);
    /**
     * cos服务客户端
     */
    private static COSClient cosClient;
    /**
     * 开发者访问 cos 服务时拥有的用户维度唯一资源标识，用以标识资源
     */
    @Value(value = "${cos.AppId}")
    private String AppId;
    /**
     * 开发者拥有的项目身份识别 ID，用以身份认证
     */
    @Value(value = "${cos.SecretId}")
    private String SecretId;
    /**
     * 开发者拥有的项目身份密钥
     */
    @Value(value = "${cos.SecretKey}")
    private String SecretKey;
    /**
     * 服务器所属区域
     */
    @Value(value = "${cos.region_name}")
    private String region_name;

    /**
     * 线程池
     */
    private static ExecutorService threadPool;

    /**
     * 初始化云存储服务
     */
    @PostConstruct
    void initCOSClient() {
        log.info("*************************初始化对象云存储服务线程池*******************************");
        initThreadPool();
        log.info("*****************************初始化对象云存储服务*********************************");
        initCOS();
    }

    /**
     * 初始化线程池
     * corePoolSize - 线程池核心池的大小。
     * maximumPoolSize - 线程池的最大线程数。
     * keepAliveTime - 当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间。
     * unit - keepAliveTime 的时间单位。
     * workQueue - 用来储存等待执行任务的队列。
     * threadFactory - 线程工厂。
     * handler - 拒绝策略。
     */
    private void initThreadPool() {
        threadPool = new ThreadPoolExecutor(5, 50, 3L,
                TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(1024), new CustomThreadFactory());
    }

    /**
     * 初始化对象云存储服务客户端
     */
    private void initCOS() {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(SecretId, SecretKey);
        // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region(region_name));
        // 3 生成cos客户端
        cosClient = new COSClient(cred, clientConfig);
    }

    /**
     * 获取客户端
     */
    public static COSClient getCosClient() {
        return cosClient;
    }

    /**
     * 获取线程池
     */
    public static ExecutorService getThreadPool() {
        return threadPool;
    }
}
