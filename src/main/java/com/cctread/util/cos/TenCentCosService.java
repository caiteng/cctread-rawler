package com.cctread.util.cos;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.*;
import com.qcloud.cos.transfer.Download;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.Upload;
import org.codehaus.groovy.runtime.powerassert.SourceText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.concurrent.ExecutorService;


/**
 * 腾讯云对象存储服务
 *
 * @author caic
 * @version 1.0 创建时间：2018-01-26 13:59
 */
@Component
public class TenCentCosService {


    /**
     * 桶名
     * bucket的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式
     */
    private static String bucketName = "cctread-1256010222";

    /**
     * 上传文件
     *
     * @param file 需要上传的文件
     * @param key  服务器文件路径
     */
    public void uploadFile(File file, String key) {
        // 简单文件上传, 最大支持 5 GB, 适用于小文件上传, 建议 20 M 以下的文件使用该接口
        // 大文件上传请参照 API 文档高级 API 上传
        //File localFile = new File("D:/test.txt");
        // 指定要上传到 cos 上的路径
        //String key = "/upload_single_demo.txt";
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
        PutObjectResult putObjectResult = getCosClient().putObject(putObjectRequest);
    }

    /**
     * 下载文件
     *
     * @param file 指定要下载到的本地路径
     * @param key  服务器文件路径
     */
    public void downloadFile(File file, String key) {
        // 指定要下载到的本地路径
        //File downFile = new File("E:/test.txt");
        //String key = "/upload_single_demo.txt";
        // 指定要下载的文件所在的 bucket 和路径
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
        ObjectMetadata downObjectMeta = getCosClient().getObject(getObjectRequest, file);
    }

    /**
     * 删除文件
     *
     * @param key 服务器文件路径
     */
    public void deleteFile(String key) {
        // 指定要删除的 bucket 和路径
        getCosClient().deleteObject(bucketName, key);
    }

    public void closeClient() {
        if (getCosClient() == null) {
            return;
        }
        // 关闭客户端(关闭后台线程)
        getCosClient().shutdown();
    }

    /**
     * 异步上传文件
     *
     * @param file 需要上传的文件
     * @param key  服务器文件路径
     */
    public void upload(File file, String key) {
        // 传入一个threadpool, 若不传入线程池, 默认TransferManager中会生成一个单线程的线程池。
        TransferManager transferManager = new TransferManager(getCosClient(), getThreadPool());
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
        // 本地文件上传
        Upload upload = transferManager.upload(putObjectRequest);
        // 等待传输结束（如果想同步的等待上传结束，则调用 waitForCompletion）
        try {
            UploadResult uploadResult = upload.waitForUploadResult();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 关闭 TransferManger
        transferManager.shutdownNow();
    }

    /**
     * 异步下载文件
     *
     * @param file 下载的文件
     * @param key  服务器文件路径
     */
    public void download(File file, String key) {
        // 传入一个threadpool, 若不传入线程池, 默认TransferManager中会生成一个单线程的线程池。
        TransferManager transferManager = new TransferManager(getCosClient(), getThreadPool());
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
        // 下载文件
        Download download = transferManager.download(getObjectRequest, file);
        // 等待传输结束（如果想同步的等待上传结束，则调用 waitForCompletion）
        try {
            download.waitForCompletion();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 关闭 TransferManger
        transferManager.shutdownNow();
    }

    /**
     * 下载
     *
     * @return
     * @throws IOException
     */
    public String getObject() throws IOException {
        // bucket的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式
        String key = "sss/test.txt";
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
        COSObject cosObject = getCosClient().getObject(getObjectRequest);
        COSObjectInputStream cosObjectInput = cosObject.getObjectContent();

        String str = inputStreamToString(cosObjectInput, "GBK");

        cosObjectInput.close();

        System.out.println(str);
        return str;


    }

    /**
     * 上传
     *
     * @param str
     * @return
     * @throws IOException
     */
    public String putObject(String str) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(str.getBytes("GBK"));

        ObjectMetadata objectMetadata = new ObjectMetadata();
        int count = byteArrayInputStream.available(); // 字节流的字节数
        // 设置输入流长度为 500
        objectMetadata.setContentLength(count);
        // 设置 Content type, 默认是 application/octet-stream
        objectMetadata.setContentType("application/text");

        String key = "sss/test.txt";
        PutObjectResult putObjectResult = getCosClient().putObject(bucketName, key, byteArrayInputStream, objectMetadata);
        String etag = putObjectResult.getETag();

        byteArrayInputStream.close();
        System.out.println(etag);
        return etag;
    }

    /**
     * 转为字符
     *
     * @param is
     * @param encoding
     * @return
     * @throws IOException
     */
    private static String inputStreamToString(InputStream is, String encoding) throws IOException {

        int count = is.available(); // 字节流的字节数
        /** 由于InputStream.read(byte[] b)方法并不能一次性读取太多字节,所以需要判断是否已读取完毕 **/
        byte[] b = new byte[count]; //
        int readCount = 0; // 已经成功读取的字节的个数
        while (readCount < count) {
            readCount += is.read(b, readCount, count - readCount);
        }
        return new String(b, 0, count, encoding);
    }



    public COSClient getCosClient() {
        return TenCentCosClient.getCosClient();
    }

    public ExecutorService getThreadPool() {
        return TenCentCosClient.getThreadPool();
    }
}
