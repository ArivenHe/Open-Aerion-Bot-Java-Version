
---

# qqBotJavaVersion

这是一个基于 Java 21 开发的 QQ 群机器人项目。该项目集成了航空气象数据解析功能，能够为用户提供专业的 METAR 数据查询与解析服务。

---

## 🚀 项目特性

* **高性能核心**：采用 Java 21 开发，充分利用现代 JDK 的性能优势。
* **QQ 频道集成**：基于 `bot-qqpd-java` SDK，实现与 QQ 频道协议的快速对接。
* **航空气象支持**：集成 `metarParser`，支持解析全球机场的 METAR 报文（包括天气状况、风速、能见度、云层等）。
* **优雅日志管理**：使用 Logback 结合 SLF4J，提供结构清晰的运行日志。
* **开发辅助**：使用 Lombok 插件，简化代码，保持项目整洁。

---

## 🛠️ 技术栈

| 技术 | 说明 |
| --- | --- |
| **JDK** | 21 |
| **构建工具** | Maven 4.0.0+ |
| **机器人 SDK** | [bot-qqpd-java](https://www.google.com/search?q=https://github.com/Kloping/bot-qqpd-java) |
| **气象解析** | [metarParser](https://www.google.com/search?q=https://github.com/mivek/metarParser) |
| **日志框架** | Logback-classic |

---

## 📦 依赖配置

项目主要依赖如下（详见 `pom.xml`）：

```xml
<dependency>
    <groupId>io.github.kloping</groupId>
    <artifactId>bot-qqpd-java</artifactId>
    <version>1.5.3-R1</version>
</dependency>

<dependency>
    <groupId>io.github.mivek</groupId>
    <artifactId>metarParser-services</artifactId>
    <version>2.20.3</version>
</dependency>

```

---

## 📥 安装与运行

### 1. 环境准备

确保你的开发环境已安装：

* **JDK 21**
* **Maven 3.8+**
* 一个主流的 IDE（如 IntelliJ IDEA）

### 2. 克隆项目

```bash
git clone <你的项目仓库地址>
cd qqBotJavaVersion

```

### 3. 编译

```bash
mvn clean compile

```

### 4. 配置与启动

1. 在项目中配置你的 QQ 机器人 `AppID` 和 `Token`（通常在配置文件或启动类中）。
2. 运行主程序类启动机器人。

---

## 📝 使用示例 (METAR 解析)

在机器人逻辑中，你可以轻松调用解析服务：

```java
// 示例：解析一段 METAR 报文
MetarService service = MetarService.getInstance();
Metar metar = service.decode("LFPG 160900Z 23012KT 9999 FEW025 15/10 Q1015");
// 机器人即可返回解析后的风速、能见度等友好信息

```

---

## 🤝 贡献指南

欢迎提交 Issue 或 Pull Request 来完善这个项目。

---
