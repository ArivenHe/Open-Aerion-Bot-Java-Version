# Open-Aerion-Bot (空管模拟机交流群 - 专用版本)

这是一个基于 Java 21 开发的航空主题 QQ 群机器人。项目旨在为模拟飞行爱好者和专业用户提供便捷的航空气象查询、航路查询以及自动化 ATIS 通播生成服务。

---

## 🚀 项目特性

*   **高性能核心**：基于 Java 21 构建，利用虚拟线程等现代特性提升并发处理能力。
*   **QQ 平台对接**：集成 `bot-qqpd-java` SDK，稳定连接 QQ 开放平台，支持群聊与频道消息互动。
*   **气象数据解析**：
    *   **METAR 查询**：支持全球机场 METAR 实时报文查询与详细解析（风况、能见度、温度/露点、修正海压等）。
    *   **ATIS 通播生成**：**独家功能**。根据实时 METAR 自动生成中英双语的机场通播（ATIS）文本，包含通播代码（Alpha-Zulu）、云层状况（云量/云高/云型）、CAVOK 判定等专业细节。
*   **航路查询**：内置导航数据库，支持机场间航路规划查询。
*   **优雅架构**：采用模块化设计（Controller-Service-DAO），使用 Lombok 简化代码，Logback 管理日志。

---

## 🛠️ 技术栈

| 技术 | 说明 |
| --- | --- |
| **JDK** | 21 (LTS) |
| **构建工具** | Maven |
| **机器人框架** | [bot-qqpd-java](https://github.com/Kloping/bot-qqpd-java) |
| **气象解析** | [metarParser](https://github.com/mivek/metarParser) |
| **数据库** | SQLite (用于存储导航数据) |
| **日志** | Logback-classic |

---

## 📝 指令列表

机器人支持以下指令（在群聊或频道中发送）：

### 1. 气象查询
获取指定机场的实时气象报文及解析数据。
*   **指令**: `/气象 <机场四字代码>`
*   **示例**: `/气象 ZBAA`
*   **输出**: 包含 METAR 原文、发布时间、风向风速、能见度、温度露点、修正海压等。

### 2. ATIS 通播生成
生成模拟飞行可用的中英双语进离场通播文本。
*   **指令**: `/通波 <机场四字代码>`
*   **示例**: `/通波 ZGGG`
*   **输出**:
    > 广州白云国际机场情报通播 Alpha，0800 协调世界时，地面风向 360 度，风速 03 MPS，能见度 10 公里，少云 3000 英尺，温度 20 摄氏度，露点 15 摄氏度，修正海压 1018 hPa，首次与管制员联络时报告你已收到通波 Alpha。
    >
    > Guangzhou Baiyun International Airport information Alpha, 0800 UTC, wind 360 degrees at 03 MPS, visibility 10000 meters, Few 3000 feet, temperature 20 degree Celsius, dew point 15 degree Celsius, corrected altimeter setting 1018 hPa, advise on initial contact you have information Alpha.

### 3. 航路查询
查询两个机场之间的推荐航路。
*   **指令**: `/航路 <出发机场> <到达机场>` 
*   **示例**: `/航路 ZBAA ZSPD`

---

## 📥 快速开始

### 环境要求
*   JDK 21+
*   Maven 3.8+
*   SQLite

### 运行步骤
1.  **克隆项目**
    ```bash
    git clone <repository-url>
    cd Open-Aerion-Bot-Java-Version
    ```

2.  **配置认证信息**
    修改配置文件或在启动参数中设置 QQ 机器人的 `AppID`、`Token` 和 `AppSecret`。

3.  **编译与运行**
    ```bash
    mvn clean compile exec:java
    ```


### 快速使用
1. 在release页面下载最新版本;
2. 上传至服务器
3. 修改 **config/application.properties**中的配置
4. 导航数据更新上传**little_navmap_navigraph.sqlite**至文件目录即可
---

## 📂 项目结构

```
src/main/java/com/ariven/
├── controller/       # 指令控制器 (Switch/Weather/Route)
├── service/          # 业务逻辑接口与实现 (WeatherService/FlightRouteService)
├── pojo/             # 数据实体
├── vo/               # 视图对象 (MetarVO 等)
├── utils/            # 工具类 (Config/Geo)
├── db/               # 数据库管理
└── Main.java         # 程序入口
```

---

## 🤝 贡献

欢迎提交 Issue 或 Pull Request 来改进代码、修复 Bug 或增加新功能！
