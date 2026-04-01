# GitHub\-JVM/README\.md

\#\#\# JVM 核心学习代码仓库

本仓库为JVM基础学习核心实操代码，涵盖\*\*类加载验证、GC垃圾回收、JVM命令实操、OOM内存溢出模拟\*\*等核心内容，代码均适配JDK8，可直接运行，配套完整注释和运行参数说明。

## 代码目录说明

所有核心Java源码均在\`src/\`目录下，类名见名知意，按JVM学习模块分类，对应学习实操场景：

```plain text
GitHub-JVM/
  ├── src/                # 核心实操源码（所有.java文件）
  └── README.md           # 仓库说明&代码使用指南
```

## 核心代码清单\&amp;使用说明

### 一、类加载机制验证

|文件名|核心功能|运行说明|
|---|---|---|
|\`ClassInitiationTest\.java\`|验证类初始化触发时机（new对象/调用静态方法/反射等）|直接运行主方法，无需额外JVM参数|

### 二、GC垃圾回收核心实操

|文件名|核心功能|运行说明|
|---|---|---|
|\`GarbageJudgeTest\.java\`|验证垃圾判定算法（循环引用\+可达性分析）|建议添加JVM参数：\`\-XX:\+PrintGCDetails\`，查看GC日志|
|\`GCAlgorithmTest\.java\`|模拟新生代/老年代GC算法（复制\+标记\-整理）|建议添加JVM参数：\`\-Xmx100m \-Xms100m \-XX:\+PrintGCDetails\`|
|\`MarkSweepFragmentTest\.java\`|模拟标记\-清除算法的内存碎片问题|必须添加JVM参数：\`\-Xmx50m \-Xms50m \-XX:\+PrintGCDetails\`，会触发OOM|
|\`SerialGCTest\.java\`|实操SerialGC收集器（单线程，新生代\+老年代）|必须添加JVM参数：\`\-Xmx50m \-Xms50m \-XX:\+UseSerialGC \-XX:\+PrintGCDetails\`|
|\`ParallelGCTest\.java\`|实操ParallelGC收集器（JDK8默认，多线程）|必须添加JVM参数：\`\-Xmx100m \-Xms100m \-XX:\+UseParallelGC \-XX:\+PrintGCDetails\`|
|\`CMSTest\.java\`|实操CMS收集器（多线程，低停顿，仅老年代）|必须添加JVM参数：\`\-Xmx100m \-Xms100m \-XX:\+UseConcMarkSweepGC \-XX:\+PrintGCDetails\`|

### 三、OOM内存溢出模拟

|文件名|核心功能|运行说明|
|---|---|---|
|\`HeapOOMTest\.java\`|模拟堆溢出（最常见OOM类型，循环创建对象不释放）|必须添加JVM参数：\`\-Xmx20m \-Xms20m\`，强制限制堆内存触发溢出|

## 关键JVM参数速查

### 1\. 堆内存配置

- \-Xmx20m：设置堆\*\*最大内存\*\*为20M

- \-Xms20m：设置堆\*\*初始内存\*\*为20M（建议与\-Xmx一致，避免内存频繁扩容）

### 2\. GC收集器指定

- \-XX:\+UseSerialGC：使用SerialGC单线程收集器

- \-XX:\+UseParallelGC：使用ParallelGC多线程收集器（JDK8默认）

- \-XX:\+UseConcMarkSweepGC：使用CMS多线程低停顿收集器

### 3\. 日志相关

- \-XX:\+PrintGCDetails：打印详细GC日志（含内存区域、GC算法、耗时等）

### 4\. 元空间（方法区）配置

- \-XX:MetaspaceSize=128m：设置元空间初始阈值

- \-XX:MaxMetaspaceSize=256m：设置元空间最大内存

### 5\. 栈内存配置

- \-Xss1m：设置单个线程的栈内存大小为1M

## 配套JVM常用命令

本仓库代码实操配套JVM核心命令，用于监控GC状态、分析内存问题，直接在CMD/终端执行：

1. **jps**：查找Java进程PID
        

    - 基础命令：\`jps\`

    - 推荐命令：\`jps \-l\`（显示主类全路径，避免进程混淆）

2. **jstat**：实时监控GC状态
        

    - 核心命令：\`jstat \-gc \[进程PID\] 1000 5\`

    - 说明：每1000毫秒（1秒）查询一次，共查询5次，输出堆/新生代/老年代GC指标

3. **jmap**：生成内存快照（分析OOM/内存泄漏）
        

    - 核心命令：\`jmap \-dump:format=b,file=gc\.hprof \[进程PID\]\`

    - 说明：生成\.hprof格式内存快照文件，可通过JVisualVM/Mat工具打开分析

## 备注

1. 所有代码均为\*\*基础实操demo\*\*，用于理解JVM核心原理，非生产环境代码；

2. 运行带JVM参数的代码时，在IDEA中通过「Run → Edit Configurations → VM options」输入参数；

3. 若需扩展其他JVM功能（如栈溢出/元空间溢出模拟），可在本仓库基础上按相同命名规范新增代码。

\-\-\-

更新时间：JVM基础学习阶段  

适配环境：JDK8 \+ Windows10/Mac/Linux

> （注：文档部分内容可能由 AI 生成）
