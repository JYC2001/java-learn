# Spring Boot进阶 本周总结与下周预习

# 一、本周核心知识点整理（按要求分类）

## （一）Spring Boot 自动配置原理

核心逻辑：遵循“约定大于配置”，通过条件装配机制，根据类路径、配置文件等自动向IOC容器注入Bean，彻底简化手动配置操作。

### 1\. 核心注解（@SpringBootApplication 三大组成）

- @SpringBootConfiguration：标记当前类为配置类，等同于@Configuration，可定义Bean；

- @ComponentScan：自动扫描当前包及子包下所有带@Controller、@Service、@Component等注解的Bean，无需手动配置扫描路径；

- @EnableAutoConfiguration：开启自动配置核心开关，是自动配置的核心注解。

### 2\. 自动配置核心流程（可直接口述）

1. 项目启动时，@EnableAutoConfiguration注解触发加载 `resources/META\-INF/spring/org\.springframework\.boot\.autoconfigure\.AutoConfiguration\.imports` 文件；

2. 读取文件中所有自动配置类，通过条件注解筛选出满足条件的配置类（不满足条件的自动过滤）；

3. 筛选后的自动配置类，通过@Bean注解向IOC容器注入所需组件，完成自动配置，无需手动编写配置代码。

### 3\. 关键条件注解（常用，适配自定义Starter）

- @ConditionalOnClass：类路径下存在指定类时，当前配置类才生效（如存在MQTT客户端类，才加载MQTT自动配置）；

- @ConditionalOnMissingBean：IOC容器中不存在指定Bean时生效，确保自定义Bean优先于自动配置Bean；

- @ConditionalOnProperty：配置文件中存在指定属性（如iot\.mqtt\.enabled=true）时生效，灵活控制配置是否启用。

## （二）自定义Starter开发（物联网通用封装）

开发原则：遵循Spring Boot Starter命名规范（自定义：xxx\-spring\-boot\-starter），核心是“封装通用功能，实现自动装配”，适配物联网项目中通用功能（如MQTT连接）的复用需求。

### 1\. 核心开发步骤（5步，可直接口述）

1. 新建Maven模块，引入核心依赖：spring\-boot\-starter（基础依赖）、spring\-boot\-autoconfigure（自动配置核心依赖）；

2. 编写配置绑定类：用@ConfigurationProperties\(prefix = \&\#34;xxx\&\#34;\)注解，绑定配置文件中指定前缀的属性（如MqttProperties绑定iot\.mqtt前缀的配置）；

3. 编写自动配置类：用@Configuration \+ @EnableConfigurationProperties注解，通过@Bean注解注入核心工具类（如MqttAutoConfiguration注入MqttClientUtil）；

4. 配置自动扫描：在resources/META\-INF/spring/目录下，创建AutoConfiguration\.imports文件，写入自动配置类的全类名，确保Spring能扫描到；

5. 打包发布：执行mvn clean install命令，将Starter安装到本地Maven仓库，其他项目直接引入依赖即可实现自动装配。

### 2\. 核心注意点

- 工具类避免硬编码，所有可配置项（如MQTT的broker地址、clientId）通过配置绑定类读取，提升灵活性；

- 合理使用条件注解，确保Starter的兼容性（如只有引入MQTT依赖，才加载相关配置）；

- 打包时确保配置文件（AutoConfiguration\.imports）路径正确，否则自动配置无法生效。

## （三）Bean线程安全问题（物联网高并发核心）

### 1\. 根本原因

Spring Boot中Bean默认是单例模式（整个项目只有一个实例），多线程并发访问并修改Bean中的**可修改成员变量**时，会出现数据错乱、脏读、重复计数等问题（如物联网多设备并发接入时，设备在线数统计错乱）。

### 2\. 安全判断标准

- 安全场景：单例Bean中无成员变量，或只有不可修改的成员变量（final修饰），或仅使用局部变量（局部变量存在于线程栈，不共享）；

- 不安全场景：单例Bean中有可修改的成员变量（如计数器、设备状态、共享连接），且被多线程同时操作。

### 3\. 核心解决方案（可独立实操）

|解决方案|用法|优点|适用场景|
|---|---|---|---|
|synchronized|修饰方法或代码块，同一时间只有一个线程能进入执行|JDK原生支持，用法简单，无需额外引入依赖|低并发场景、业务逻辑简单（如简单计数器）|
|ReentrantLock|手动调用lock\(\)加锁，finally中调用unlock\(\)释放锁|灵活（支持尝试加锁、公平锁），高并发性能好，可避免死锁|物联网高并发场景（多设备并发接入）、复杂业务逻辑|

### 4\. 避坑要点

- ReentrantLock必须在finally中释放锁，防止方法抛出异常后锁未释放，导致死锁；

- 避免在synchronized修饰的方法中加入耗时操作，影响并发性能；

- 无需给所有Bean加锁，仅对有可修改成员变量的单例Bean做线程安全处理即可。

## （四）Spring Boot 项目优化（去冗余\+规范化）

优化核心：移除冗余配置，统一配置管理，利用Spring Boot自动配置简化开发，为后续整合物联网中间件（Redis、MQTT）预留配置空间，提升项目可维护性。

### 1\. 配置层优化（核心）

- 删除冗余配置：移除所有XML配置（如applicationContext\.xml、spring\-mvc\.xml、mybatis\-config\.xml）、自定义JavaConfig类（如DataSourceConfig、WebConfig），全部依赖Spring Boot自动配置；

- 统一配置管理：用application\.yml替代application\.properties，按“服务器配置→数据源配置→MyBatis配置→日志配置→物联网中间件预留配置”分类，结构清晰，便于维护；

- 日志与GC配置：配置日志文件输出（指定路径和格式）、GC日志（记录JVM垃圾回收情况），便于后续问题排查和JVM调优。

### 2\. 依赖层优化

引入Spring Boot官方Starter（如mybatis\-spring\-boot\-starter、spring\-boot\-starter\-jdbc），避免手动引入零散依赖，利用自动配置简化依赖管理，减少版本冲突。

### 3\. 代码层优化

- Bean注入：使用构造器注入或@Resource注解自动注入，无需手动创建Bean实例；

- Mapper简化：使用MyBatis注解版（@Select、@Insert），减少XML映射文件的编写，提升开发效率；

- 接口规范：统一接口前缀（如/user、/iot），返回结果简洁规范，便于测试和对接。

# 二、本周代码复盘（GitHub整理步骤）

## 1\. 本地代码整理（先规范结构）

新建本地文件夹，命名为“SpringBoot进阶”，按模块分类整理代码，确保结构清晰，便于GitHub提交和后续查阅，具体结构如下：

```plain text
SpringBoot进阶/
├── 01-自定义MQTT-Starter/  # 自定义Starter完整代码
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/jyc/iot/  # 配置类、工具类、配置绑定类
│   │   │   └── resources/        # AutoConfiguration.imports配置文件
│   │   └── test/  # 可选，Starter测试代码
│   └── pom.xml  # Starter的Maven依赖配置
├── 02-优化后项目/  # iot-business-demo（替代user-system）优化后代码
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/jyc/iot/  # 启动类、Controller、Service、Mapper、实体类
│   │   │   └── resources/        # application.yml（分类配置）
│   │   └── test/  # 可选，接口测试代码
│   └── pom.xml  # 项目依赖（含自定义Starter、MySQL、MyBatis等）
└── README.md  # 说明文档（必写）
    └── 记录模块功能、启动方式、核心知识点、检验标准相关内容
```

### 2\. README\.md 核心内容（简化版，可直接复制）

\# SpringBoot进阶
\#\# 本周内容：自定义Starter\+Bean线程安全\+项目优化
\#\#\# 模块说明
1\. 01\-自定义MQTT\-Starter：封装MQTT通用功能，支持自动装配，适配物联网设备连接场景；
2\. 02\-优化后项目：基于Spring Boot自动配置，移除冗余配置，包含用户模块（/user/add、/user/list）和MQTT测试接口。
\#\#\# 启动方式
1\. 先执行自定义Starter的mvn clean install，安装到本地Maven仓库；
2\. 启动优化后项目，访问接口测试（需配置MySQL数据库）；
3\. 查看gc\.log和日志文件，验证配置生效。
\#\#\# 核心知识点
\- Spring Boot自动配置流程、自定义Starter开发步骤；
\- Bean线程安全问题及解决方案；
\- 项目优化要点（去冗余、配置规范化）。

## 2\. GitHub 推送步骤（新手友好，IDEA实操）

1. GitHub新建仓库：登录GitHub，新建仓库（建议命名SpringBoot\-Advanced），勾选“Add a README file”，点击创建；

2. IDEA导入本地项目：打开IDEA，导入“SpringBoot进阶”文件夹，右键项目 → Git → Initialize Repository，初始化本地Git仓库；

3. 暂存文件：右键项目 → Git → Add，将所有文件加入暂存区（确保无遗漏）；

4. 提交本地仓库：右键项目 → Git → Commit File，填写提交信息（如“feat: 整理自定义Starter\+项目优化代码”），点击提交；

5. 关联GitHub仓库：点击IDEA顶部Git → Push → Remote，添加GitHub仓库的HTTPS地址（仓库页面复制），点击Push，完成代码推送。

### 3\. 提交规范（可选，提升仓库规范性）

提交信息遵循统一规范，便于后续复盘：

- feat: 新增功能（如“feat: 完成自定义MQTT Starter开发”）；

- opt: 优化功能（如“opt: 优化项目配置，移除冗余XML”）；

- docs: 完善文档（如“docs: 补充README说明文档”）。

# 三、下周内容预习（Spring Boot AOP）

核心目标：了解AOP核心概念，掌握基础用法，为后续物联网项目的日志记录、权限控制、异常处理铺垫。

## （一）AOP 核心概念（5个必记，通俗理解）

AOP（面向切面编程）：在不修改原有业务代码的前提下，将日志、权限、事务等通用逻辑封装为“切面”，在指定时机切入到业务方法中，实现通用逻辑与业务逻辑的分离，提升代码复用性和可维护性。

|核心术语|通俗含义|类比理解|
|---|---|---|
|切面（Aspect）|通用逻辑的封装（如日志切面、权限切面），是一个类，用@Aspect标记|手机贴膜（通用功能，不影响手机本身功能）|
|连接点（JoinPoint）|业务中可被切入的点（Spring中仅支持方法级，如方法执行、异常抛出）|手机屏幕（贴膜的可操作位置）|
|切入点（Pointcut）|筛选出的具体要切入的连接点，用表达式匹配（如指定包下的所有方法）|仅给手机正面屏幕贴膜（筛选具体位置）|
|通知（Advice）|切面的具体执行逻辑，指定切入时机（如方法执行前打印日志）|贴膜的动作（擦屏幕→贴贴膜→压平）|
|目标对象（Target）|被切面增强的业务对象（如UserService、MqttClientUtil）|手机（被贴膜的对象）|

## （二）5种通知类型（切入时机，核心重点）

通知是切面的核心，决定通用逻辑在目标方法的哪个阶段执行，均为注解形式，Spring Boot中无需额外配置，引入依赖后即可使用：

- @Before（前置通知）：目标方法执行前执行（如接口请求前校验权限、打印请求参数）；

- @After（后置通知）：目标方法执行后执行（无论是否抛出异常，如方法执行后关闭资源）；

- @AfterReturning（返回通知）：目标方法正常返回后执行（如接口执行成功后打印返回结果）；

- @AfterThrowing（异常通知）：目标方法抛出异常后执行（如捕获异常并记录日志、发送告警）；

- @Around（环绕通知）：包裹目标方法，可在方法执行前、后、异常时执行逻辑（功能最强大，如记录方法执行耗时）。

## （三）核心注解与切入点表达式

### 1\. 核心注解

- @Aspect：标记当前类为切面，需配合@Component注解将切面注入IOC容器；

- @Pointcut：定义切入点，用表达式匹配目标方法，可复用（避免重复编写表达式）；

- @EnableAspectJAutoProxy：开启AOP自动配置（Spring Boot中无需手动添加，引入spring\-boot\-starter\-aop依赖后自动开启）。

### 2\. 常用切入点表达式（execution表达式）

语法：`execution\(访问修饰符 包名\.类名\.方法名\(参数类型\)\)`，支持通配符\*（任意）、\.\.（任意包/任意参数），常用示例：

- 匹配com\.jyc\.iot\.service包下所有类的所有方法：`execution\(\* com\.jyc\.iot\.service\.\*\.\*\(\.\.\)\)`；

- 匹配com\.jyc\.iot\.controller包下所有类的GET请求方法（方法名以get开头）：`execution\(\* com\.jyc\.iot\.controller\.\*\.get\*\(\.\.\)\)`；

- 匹配所有带@Log自定义注解的方法（后续日志控制常用）：`@Pointcut\(\&\#34;@annotation\(com\.jyc\.iot\.anno\.Log\)\&\#34;\)`。

## （四）物联网场景AOP应用（提前了解）

AOP是物联网后端开发的核心技术，后续主要用于：

- 日志记录：记录设备接入、接口请求的详细信息（请求参数、返回结果、执行耗时），无需在每个接口重复写日志；

- 权限控制：对设备接口、管理接口做统一权限校验（如设备是否已注册、管理员是否有操作权限）；

- 异常处理：统一捕获物联网接口的异常（如MQTT连接异常、设备离线异常），统一返回规范结果；

- 性能监控：记录设备数据采集、指令下发等方法的执行耗时，排查性能瓶颈。

## （五）预习小目标（确保后续快速上手）

1. 记住AOP的5个核心概念和5种通知类型；

2. 熟悉execution切入点表达式的基本语法，能写出简单的匹配规则；

3. 了解@Aspect、@Pointcut、@Around注解的基本作用，能看懂简单的切面代码。

# 四、本周检验标准（达标指南）

## 1\. 口述“Spring Boot 自动配置流程”（简洁规范，达标版本）

项目启动时，@EnableAutoConfiguration注解会加载AutoConfiguration\.imports文件，获取所有自动配置类；通过条件注解过滤掉不满足条件的配置类，剩下的配置类通过@Bean向IOC容器注入组件，最终完成自动配置，无需手动编写配置。

## 2\. 口述“自定义Starter开发步骤”（5步核心，达标版本）

第一步，新建Maven模块，引入spring\-boot\-starter和autoconfigure核心依赖；第二步，编写@ConfigurationProperties配置绑定类，绑定配置文件属性；第三步，编写@Configuration自动配置类，注入核心工具Bean；第四步，在resources/META\-INF/spring下配置AutoConfiguration\.imports文件，写入自动配置类全类名；第五步，执行mvn clean install打包到本地Maven，其他项目引入依赖即可自动装配。

## 3\. 独立解决Bean线程安全问题（达标标准）

第一步，判断Bean是否安全：单例Bean有可修改的成员变量→不安全，无成员变量或仅用局部变量→安全；第二步，选择解决方案：低并发场景用synchronized修饰方法，物联网高并发场景用ReentrantLock（lock加锁，finally中解锁），确保数据不错乱。

> （注：文档部分内容可能由 AI 生成）
