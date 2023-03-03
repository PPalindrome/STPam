# **STPam**

<img src="https://user-images.githubusercontent.com/90181056/222644873-5b7c8c3e-1502-4e39-8dff-70670693f09d.png" alt="image" style="zoom:80%;" /><img src="https://user-images.githubusercontent.com/90181056/222644925-4fbe5ca3-e03f-4bee-891c-0206193a65a1.png" alt="image" style="zoom:80%;" />



## 介绍

这是一个基于多源大数据的时空过程智能分析挖掘软件

An efficient software for intelligent analyzing and mining spatiotemporal processes based on multi-source big data

前端：Vue，Axios，ElementPlus

后台：SpringCloud, SpringBoot, SpringSecurity

## Quick Start

### Usage

Download files: /STPam/back/server , /STPam/front , /STPam/stpam.sql

For Windows

software required: nodejs, MySQL

IDE available: Intellij idea, JetBrains Webstorm

(1) import sql

use Navicat or other DBMS

(2) run StpamApplication 

```java
//location: /back/server/src/main/java/com/example/server/StpamApplication.java

public class StpamApplication {
    public static void main(String[] args) {
        SpringApplication.run(StpamApplication.class,args);
    }

    @Bean
//    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
```

 (3) run dev

```vue
//location: /front

npm run dev
```

(4) visit http://localhost:8070/

## Different roles

### visitor

![image](https://user-images.githubusercontent.com/90181056/222649795-c2e9c346-fc72-4f42-9e9e-a5d0587e259f.png)

### user

![image](https://user-images.githubusercontent.com/90181056/222649878-98f69521-5ab7-4745-97bd-144edff22205.png)

### administrator

![image](https://user-images.githubusercontent.com/90181056/222649894-e7bead7b-222a-43fb-bc6f-4bebb163e94d.png)

## 下载源码使用

1.准备3个及以上的GPU工作节点

2.配置HDFS

3.环境配置（JDK必须为1.8，其他环境可以选择更高版本）

| Function                 | Name                  |    Version    |
| ------------------------ | --------------------- | :-----------: |
| Distributed  File System | HDFS                  |    2.10.1     |
| Microservice             | Spring  Cloud Alibaba | 2.1.2.RELEASE |
| Java                     | JDK                   |   1.8.0_333   |
| Python                   | python                |      3.8      |
| Virtual  Environment     | Anaconda              |       3       |
| GPU  Parallel Computing  | Cuda                  |     11.3      |

4.将源码部署到服务器上

5.下载并启动nacos、sentinel、zipkin、seata（“启动命令”）

6.依照服务器地址访问：http://服务器地址:8070/

7.首先进入游客页面，以管理员身份登录后可使用全部功能

## 功能清单

- 登录注册
- 基于jwt+SpringSecurity的安全认证和授权
- 多源大数据上传、查看、分析、下载
- 动态配置和切换数据库和HDFS
- 深度学习模型上传
- 模型在线编辑
- 深度学习模型发布为Web服务
- 使用深度学习模型服务
- 分布式计算框架集成与管理
- 基于Ring All-Reduce的GPU通信方式
- GPU监控
- 时空过程分布式分析挖掘
- 时空过程产品展示
- 时空过程产品分析
- 用户管理

## 软件功能仍在不断修改完善中

## 联系我们

E-mail: phw1220@cug.edu.cn
