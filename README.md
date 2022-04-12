# b2c-parent
我的项目
使用项目的一些启动项：
使用了Zookeeper服务，Redis，Rebbitmq

开启Zookeeper服务
cd /root/zookeeper-3.4.13/bin ./zkServer.sh start

开启trackerd服务
service fdfs_trackerd restart
关闭
service fdfs_trackerd stop

开启storaged服务
service fdfs_storaged restart
关闭
service fdfs_trackerd stop

开启Nginx
/usr/local/nginx/sbin/nginx/ ./nginx

启动Redis
redis-server ~/redis-5.0.2/redis.conf #通过指定配置文件的方式启动
后台启动
redis-server /usr/local/redis-5.0.2/redis.conf 
关闭redis服务
redis-cli shutdown 

后台启动elasticsearch，后台启动，（启动时候得先切换成el用户！！！）
ES_JAVA_OPTS="-Xms256m -Xmx256m" ./elasticsearch

RabbitMQ安装
service rabbitmq-server start #启动
service rabbitmq-server stop #停止
service rabbitmq-server restart #重启

加密后数据源密钥：M+NNa+0EXyTY8NUm1Wo8+URJTsRokNLTCPb2fVL+k9RQpuIluFQ/eyrH4P8ARNWbOjRuYSRi9OI+RY+d0rh4QA==
