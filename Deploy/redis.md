
创建redis配置文件目录
```shell
mkdir -p /home/redis/conf
touch /mydata/redis/conf/redis.conf
```
启动redis容器
```shell
docker run -d \
-p 6379:6379 \
--name redis \
-v /home/redis/data:/data \
-v /home/redis/conf/redis.conf:/etc/redis/redis.conf \
redis redis-server /etc/redis/redis.conf
```