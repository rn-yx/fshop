# 高并发秒杀系统 FShop

## 简介
FShop是一款基于Spring Boot + Mybatis + Dubbo + Zookeeper + RabbitMQ + Redis + Nginx等技术的高并发秒杀系统。FShop主要定位
于高并发抢购业务系统快速建设， 提供下单、扣减库存、流量削峰、动静分离、热点隔离等核心问题解决方案。

项目博客：https://blog.csdn.net/Y_lxl

# 秒杀业务流程
![秒杀业务流程图](https://img-blog.csdnimg.cn/20200923221825317.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1lfbHhs,size_16,color_FFFFFF,t_70#pic_center)

## 技术架构

![技术架构图](https://img-blog.csdnimg.cn/20200905155732861.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1lfbHhs,size_16,color_FFFFFF,t_70#pic_center)

## 工程结构说明
```
fshop
├── basis-service       公共基础服务
│   ├── alipayservice-api          支付宝支付服务API
│   ├── alipayservice-server       支付宝支付服务
│   ├── messageservice-api         三方消息推送服务API
│   ├── messageservice-server      三方消息推送服务
│   ├── wechatservice-api          微信支付服务API
│   └── wechatservice-server       微信支付服务
├── business-service    基础业务服务
│   ├── orderservice-api           订单服务API
│   ├── orderservice-server        订单服务
│   ├── productservice-api         商品服务API
│   ├── productservice-server      商品服务
│   ├── userservice-api            用户服务API
│   └── userservice-server         用户服务
├── convergent-service  聚合业务服务
│   └── seckillservice-server      秒杀服务
├── job                 定时任务
│   ├── compensation-job           补偿job
│   └── scheduler-job              调度job
└── open-component      通用组件
     ├── component-codegen          代码生成器组件
     ├── component-core             核心服务组件
     ├── component-mysql            MySQL组件
     ├── component-rabbitmq         RabbitMQ组件
     ├── component-redis            Redis组件
     ├── component-swagger          Swagger组件
     └── component-utils            公共工具包
```
服务划分说明如下：

* 公共基础服务：负责某一个方面的基础业务（没有什么领域业务逻辑在里面），可以是自治的处理某一个方面的基础业务，也可以和
外部通讯实现某一个方面的功能，服务之间是不会相互调用的，但是会被聚合业务服务和基础业务服务调用。

* 基础业务服务：某一个领域业务相关的服务。此类服务之间是允许相互调用的，在这个层次的服务拥有大量的业务逻辑。

* 聚合业务服务：高层次的串起来整个流程的具有完整业务形态的业务服务。一般而言，聚合业务服务代表了独立的业务流程，它们之间
是不会进行相互调用的，但是它们一定会调用大量的各类基础业务服务。这个层次的服务的业务逻辑更多是在表达业务流程的复杂性和
差异性，不会涉及到具体怎么处理的细节逻辑。

## 开发计划

* 秒杀核心业务开发（进行中）
* 高可用、高并发支持（即将开始）
* k8s部署支持（技术中）

## 交流讨论

* Email : rannuo1010@gmail.com
* 扫码加微信：<img src="https://img-blog.csdnimg.cn/20200905163751931.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1lfbHhs,size_16,color_FFFFFF,t_70#pic_center"  height="150" width="150">




