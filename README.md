# spring-boot-mq
消息中间件message-queue

## 项目结构
* mq-rabbit 
* mq-rocket

### Windows下rocketmq项目启动流程
1. 下载地址[官网](http://rocketmq.apache.org/)
2. 解压缩之后设置系统环境变量
    - ROCKETMQ_HOME=D:\rocketmq-all-4.7.1-bin-release(解压路径)
    - NAMESRV_ADDR=127.0.0.1:9876(可能需要)
    - Path=%ROCKETMQ_HOME%\bin
3. 启动流程
    - start mqnamesrv.cmd
    - start mqbroker.cmd -n 127.0.0.1:9876 autoCreateTopicEnable=true
4. 安装rocketmq控制台
    - 下载地址 https://github.com/apache/rocketmq-externals
    - 修改rocketmq-console application.properties文件参数
    - server.port=8080
    - rocketmq.config.namesrvAddr=localhost:9876
    - 浏览器输入 locahost:8080即可访问
5. 连接测试（bin目录下）
    * 在cmd窗口输入消费者:
    - set NAMESRV_ADDR=localhost:9876
    - tools.cmd org.apache.rocketmq.example.quickstart.Consumer
    * 在cmd窗口输入生产者
    - set NAMESRV_ADDR=localhost:9876
    - tools.cmd org.apache.rocketmq.example.quickstart.Producer
### 注意
* 在实际开发过程中创建生产者很有可能会出现 no route info of Topic，估计原因是没有做第5步测试导致
* 虽然很多blog说查看broker.log日志，但是基本没问题，要么没有第2步，要么第5步