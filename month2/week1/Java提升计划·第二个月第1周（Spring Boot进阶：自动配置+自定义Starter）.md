# Java提升计划·第二个月第1周（Spring Boot进阶：自动配置\+自定义Starter）

核心衔接：第一个月已完成Java核心、集合、JVM、并发编程基础，具备Java开发核心能力；第二个月重点聚焦Spring Boot进阶与物联网相关技术铺垫，本周核心目标是吃透Spring Boot自动配置原理，掌握自定义Starter开发，为后续整合物联网中间件（如MQTT客户端）打基础。

物联网关联点：Spring Boot是物联网后端开发的核心框架，自动配置能简化物联网设备接入、中间件整合的配置流程，自定义Starter可封装物联网常用功能（如设备连接、数据解析），提升开发效率。

## 一、核心目标

1. 吃透Spring Boot自动配置底层原理（@EnableAutoConfiguration、SpringFactoriesLoader），能口述自动配置完整流程。

2. 掌握自定义Spring Boot Starter的开发流程、核心注解，能独立开发一个简单的自定义Starter。

3. 结合第一个月的并发知识，理解Spring Boot中Bean的线程安全问题及解决方案。

4. 复盘user\-system项目，用Spring Boot自动配置优化项目配置，为后续整合物联网相关依赖做准备。

## 二、验收标准

1. 能独立分析Spring Boot自动配置的核心源码（@SpringBootApplication、AutoConfigurationImportSelector）。

2. 能独立开发一个自定义Starter（如简单的日志Starter、工具类Starter），并引入到user\-system项目中正常使用。

3. 能识别Spring Boot中Bean的线程安全隐患，并用第一个月学的synchronized/ReentrantLock解决。

4. 能优化user\-system项目的配置，移除冗余配置，利用自动配置简化项目启动流程。

## 三、每日打卡任务（附具体要求）

|日期|核心任务（附具体要求）|完成情况（打√）|备注（难点/收获/物联网关联）|
|---|---|---|---|
|Day1|1\. 复习Spring Boot基础：@SpringBootApplication注解的构成（@Configuration、@EnableAutoConfiguration、@ComponentScan），明确各注解的作用。2\. 学习@EnableAutoConfiguration注解的核心原理，理解“自动配置”的本质（自动导入配置类）。3\. 查看Spring Boot源码，找到AutoConfigurationImportSelector类，分析其selectImports\(\)方法的作用。|□|难点：源码理解，重点关注SpringFactoriesLoader\.loadFactoryNames\(\)方法；物联网关联：后续整合MQTT客户端，依赖Spring Boot自动配置简化配置。|
|Day2|1\. 深入学习Spring Boot自动配置流程：启动时扫描META\-INF/spring\.factories文件，加载自动配置类。2\. 实操：查看本地Spring Boot依赖中的spring\.factories文件，找到常见的自动配置类（如DataSourceAutoConfiguration）。3\. 学习自动配置的条件注解（@ConditionalOnClass、@ConditionalOnMissingBean等），理解“条件装配”的逻辑。|□|重点：条件注解的使用场景，后续自定义Starter需用到；收获：明白Spring Boot“约定大于配置”的底层逻辑。|
|Day3|1\. 学习自定义Spring Boot Starter的核心规范（命名规范、目录结构、配置文件）。2\. 搭建自定义Starter项目：创建maven项目，引入核心依赖（spring\-boot\-starter、spring\-boot\-autoconfigure）。3\. 编写自动配置类，使用@Configuration、@Conditional注解，定义一个简单的Bean（如工具类Bean）。|□|难点：Starter的依赖配置，避免依赖冲突；物联网关联：后续可开发“物联网设备连接Starter”，封装设备接入逻辑，复用性强。|
|Day4|1\. 完善自定义Starter：编写META\-INF/spring\.factories文件，注册自动配置类。2\. 将自定义Starter打包（mvn clean install），安装到本地maven仓库。3\. 在user\-system项目中引入自定义Starter，测试Bean是否能正常注入、使用。|□|重点：打包流程和依赖引入，确保Starter能正常复用；收获：掌握自定义组件的封装方法，适配物联网开发中“通用功能封装”的需求。|
|Day5|1\. 结合第一个月的并发知识，分析Spring Boot中Bean的线程安全问题（默认单例Bean，多线程共享时的安全隐患）。2\. 实操：在user\-system项目中，模拟多线程调用Controller中的Bean，演示线程安全问题。3\. 用synchronizedReentrantLock解决Bean的线程安全问题，测试验证效果。|□|重点：单例Bean的线程安全处理；物联网关联：物联网后端需处理多设备并发接入，Bean线程安全是核心需求。|
|Day6|1\. 优化user\-system项目：利用Spring Boot自动配置，移除冗余的XML配置、JavaConfig配置。2\. 完善项目的配置文件（application\.yml），分类管理配置（数据源、服务器端口、日志等）。3\. 测试优化后的项目，确保所有接口（/user/add、/user/list等）正常运行，GC日志正常。|□|收获：提升项目规范性，简化配置，为后续整合物联网中间件（如Redis、MQTT）预留配置空间。|
|Day7|1\. 整理本周核心知识点（脑图/笔记，按“自动配置原理→自定义Starter→Bean线程安全→项目优化”分类）。2\. 复盘本周代码：将自定义Starter、项目优化后的代码，整理到GitHub（新建“SpringBoot进阶”文件夹）。3\. 预习下周内容：Spring Boot AOP，了解AOP的核心概念（切面、通知、切入点），为后续日志、权限控制做准备。|□|检验标准：能口述“Spring Boot自动配置流程”\+“自定义Starter开发步骤”，能独立解决Bean线程安全问题。|

## 四、核心知识点速记（随时复习）

### 1\. Spring Boot自动配置核心

- @SpringBootApplication = @Configuration（标记配置类） \+ @EnableAutoConfiguration（开启自动配置） \+ @ComponentScan（扫描组件）。

- 自动配置流程：启动→@EnableAutoConfiguration→AutoConfigurationImportSelector→扫描META\-INF/spring\.factories→加载自动配置类→条件注解判断是否装配→注入Bean。

- 核心文件：META\-INF/spring\.factories，key为org\.springframework\.boot\.autoconfigure\.EnableAutoConfiguration，value为自动配置类全路径。

### 2\. 自定义Starter核心规范

- 命名规范：官方Starter命名为spring\-boot\-starter\-xxx，自定义Starter命名为xxx\-spring\-boot\-starter（避免与官方冲突）。

- 核心依赖：spring\-boot\-starter（基础依赖）、spring\-boot\-autoconfigure（自动配置核心依赖）、spring\-boot\-configuration\-processor（生成配置元数据，可选）。

- 核心步骤：创建配置类→编写spring\.factories→打包→引入使用。

### 3\. Spring Boot Bean线程安全

- 默认Bean是单例模式，多线程共享时，若Bean有成员变量，会存在线程安全问题；无成员变量（纯工具类），天然线程安全。

- 解决方式：① 用synchronized/ReentrantLock加锁；② 改为多例模式（@Scope\(\&\#34;prototype\&\#34;\)）；③ 使用ThreadLocal隔离线程数据。

## 五、项目融入小练习（结合user\-system\+物联网铺垫）

1\. 基于本周学习的自定义Starter，开发一个“user\-log\-starter”，封装用户操作日志功能（如记录接口调用时间、调用者、操作内容），引入到user\-system项目中，实现接口日志自动记录。

2\. 分析user\-system项目中Controller、Service层Bean的线程安全情况，对存在安全隐患的Bean（如有成员变量的Bean）进行优化，确保多线程下接口正常运行（贴合物联网多设备并发接入场景）。

3\. 优化项目配置，新增“自定义配置项”（如日志输出格式、接口超时时间），通过@ConfigurationProperties注解绑定配置，实现配置灵活修改（后续物联网开发中，可通过配置项灵活配置设备连接参数）。

> （注：文档部分内容可能由 AI 生成）
