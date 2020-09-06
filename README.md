
# FShop分布式/微服务秒杀系统

## 简介
FShop是一款基于Spring Boot + Mybatis + Dubbo + Zookeeper + RabbitMQ + Redis + Nginx等技术的分布式秒杀系统。FShop主要定位于高并发抢购业务系统快速建设， 提供下单、扣减库存、流量削峰、动静分离、热点隔离等核心问题解决方案。

项目博客：https://blog.csdn.net/Y_lxl

## 架构设计

![](https://img-blog.csdnimg.cn/20200905155732861.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1lfbHhs,size_16,color_FFFFFF,t_70#pic_center)

## 工程结构说明
```
fshop
├── fshop-basicservice
│   ├── basicservice-admin
│   ├── basicservice-config
│   └── basicservice-sso
├── fshop-component
│   ├── component-codegen
│   ├── component-core
│   ├── component-mysql
│   ├── component-rabbitmq
│   ├── component-redis
│   ├── component-swagger
│   └── component-utils
├── fshop-order
│   ├── fshop-order-facade
│   ├── fshop-order.iml
│   └── fshop-order-provider
├── fshop-pay
│   ├── fshop-pay-alipay
│   └── fshop-pay-wechat
├── fshop-product
│   ├── fshop-product-facade
│   └── fshop-product-provider
├── fshop-rest-service
└── fshop-user
     ├── fshop-user-facade
     └── fshop-user-provider
```

## 开发计划

* 秒杀核心业务开发（进行中）
* 高可用、高并发支持（即将开始）
* k8s部署支持（即将开始）

## 联系

* Email : rannuo1010@gmail.com
* 扫码加微信：<img src="https://img-blog.csdnimg.cn/20200905163751931.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1lfbHhs,size_16,color_FFFFFF,t_70#pic_center"  height="150" width="150">




