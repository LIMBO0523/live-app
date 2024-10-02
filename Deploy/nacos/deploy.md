
部署命令
```shell
docker run -d \
--name nacos \
-p 8848:8848 \
-p 9848:9848 \
-p 9849:9489 \
-e MODE=standalone \
-e NACOS_AUTH_ENABLE=true \
-e NACOS_AUTH_TOKEN=SecretKey012345678901234567890123456789012345678901234567890123456789 \
-e NACOS_AUTH_IDENTITY_KEY=identity_key \
-e NACOS_AUTH_IDENTITY_VALUE=identity_value \
-v /home/nacos/logs/:/home/nacos/logs \
-v /home/nacos/conf/:/home/nacos/conf \
-v /home/nacos/data/:/home/nacos/data \
--restart=always \
nacos/nacos-server:v2.4.0
```