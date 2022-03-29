package com.turing.b2c.seller.utils;

import org.csource.fastdfs.*;

import java.io.IOException;

/**
 * @author HHF-QAQ
 * @date 2021年12月15日 16:30
 * FastDFSClient的客户端
 */
public class FastDFSClient {

    /**
     * 声明封装图片的四个对象
     * @author HHF-OVO
     * @date 2021/12/15 16:36
     * @param null
     * @return null
     */
    private TrackerServer trackerServer = null;

    private TrackerClient trackerClient = null;

    private StorageClient1 storageClient = null;

    private StorageServer storageServer = null;

    /**
     * 构造FastDFS初始化,抛出异常MyException,只使用作为初始化。
     * @author HHF-OVO
     * @date 2021/12/15 16:40
     * @param conf
     * @return null
     */
    public FastDFSClient(String conf) throws IOException, Exception {
        //初始化FastDFS
        ClientGlobal.init(conf);
        //调度客户端
        trackerClient = new TrackerClient();
        //调度服务端
        trackerServer = trackerClient.getConnection();
        //储存服务端,声明服务端
        storageServer = null;
        //储存客户端。
        storageClient = new StorageClient1(trackerServer,storageServer);
    }

    /**
     * 文件上传，上传文件使用二进制，byte[]
     * @author HHF-OVO
     * @date 2021/12/15 16:45
     * @param fileContent
     * @param extName
     * @return java.lang.String
     */
    public String uploadFile(byte[] fileContent, String extName) throws Exception{
        return storageClient.upload_appender_file1(fileContent,extName,null);
    }
}
