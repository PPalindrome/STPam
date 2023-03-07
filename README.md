# **STPam**

<img src="https://user-images.githubusercontent.com/90181056/222644873-5b7c8c3e-1502-4e39-8dff-70670693f09d.png" alt="image" style="zoom:80%;" /><img src="https://user-images.githubusercontent.com/90181056/222644925-4fbe5ca3-e03f-4bee-891c-0206193a65a1.png" alt="image" style="zoom:80%;" />



## What is this repository for?

STPam is an efficient software for intelligent analyzing and mining spatiotemporal processes based on multi-source big data

Front-end：Vue，Axios，ElementPlus

Back-end：SpringCloud, SpringBoot, SpringSecurity


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


## Installation

### Compatibility

STPam can run on browsers below. If you really need to support outdated browsers, please add [Babel](https://babeljs.io/) and Polyfill yourself.

Since Vue 3 no longer supports IE11, Element Plus does not support IE either.

Edge≥79；Chrome≥64；Firefox≥78

### Version

1.0.0

### Usage

(1) Prepare 3 or more GPU working nodes

(2) Configure HDFS

(3) Environment configuration (JDK must be 1.8, other environments can choose a higher version)

| Function                 | Name                  |    Version    |
| ------------------------ | --------------------- | :-----------: |
| Distributed  File System | HDFS                  |    2.10.1     |
| Microservice             | Spring  Cloud Alibaba | 2.1.2.RELEASE |
| Java                     | JDK                   |   1.8.0_333   |
| Python                   | python                |      3.8      |
| Virtual  Environment     | Anaconda              |       3       |
| GPU  Parallel Computing  | Cuda                  |     11.3      |

(4) Deploy the source code to the server

(5) Download and start nacos, sentinel, zipkin, seata ("Start Command")

(6) Follow the server ip to visit: http://YourServerIP:8070/

(7) First enter the visitor page, log in as an user or administrator and then you can use all of the functions.


## Function List

- Login & Registration
- User Management
- Secure authentication and authorization based on JWT & SpringSecurity
- Multisource big data upload, view, analysis and download
- Dynamic configuration and switching of database and HDFS
- Deep learning models uploading
- Online edit
- Deep learning model publishing as a web service
- Apply deep learning model services
- Distributed computing framework integration and management
- Ring All-Reduce Based GPU Communication Approach
- GPU Monitoring
- Spatiotemporal Process Distributed Analysis Mining
- Spatiotemporal Process Product Showcase
- Spatiotemporal Process Product Analysis


## Software functions are still being revised and improved


## Contact us

E-mail: phw1220@cug.edu.cn
