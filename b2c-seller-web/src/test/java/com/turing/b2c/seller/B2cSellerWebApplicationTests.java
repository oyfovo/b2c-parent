package com.turing.b2c.seller;

import org.csource.fastdfs.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;


@RunWith(SpringRunner.class)
@SpringBootTest
class B2cSellerWebApplicationTests {

    @Test
    void contextLoads() throws IOException, Exception {
        //初始化，会抛出异常
        ClientGlobal.init("fdfs_client.conf");
        //创建一对TrackerClient客户端
        TrackerClient trackerClient = new TrackerClient();
        //创建一对TrackerClient服务端
        TrackerServer trackerServer = trackerClient.getConnection();
        //创建StorageServer的服务端
        StorageServer storageServer = null;
        //创建StorageServer的客户端
        StorageClient1 storageClient = new StorageClient1(trackerServer, storageServer);
        //使用存储客户端调用上传方法
        String s = storageClient.upload_appender_file1("C:\\Users\\H\\Desktop\\yezi.jpg", "jpg", null);
        System.out.println(s);
    }

}
