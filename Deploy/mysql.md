
运行容器
```shell
$ docker run -d \
 --name mysql \
 -p 3306:3306 \
 -e MYSQL_ROOT_PASSWORD=990523 \
 mysql:8.0.13
```
主从复制
https://cloud.tencent.com/developer/article/2171401