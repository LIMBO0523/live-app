## 命令执行部分
mvn打包Docker镜像命令:
```shell
mvn install
```
此时会把docker镜像安装在本地上

本地环境进行容器的启动操作：
```shell
docker run -d
-p 9090:9090 
--name live-api
-v /tmp/logs/live-api:/tmp/logs/live-api
--add-host 'wsl:172.24.185.80'
--add-host 'localhost:127.0.0.1'
-e TZ=Asia/Shanghai
live-api-docker:latest
```
其中 wsl 为nacos、redis、rocketMQ和mysql的部署机器的ip地址
