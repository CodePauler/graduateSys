# 高校学生就业管理系统

## 项目简介
本项目包含前端（Vue3 + Vite）和后端（Spring Boot + MyBatis）两部分。
(1)	实现院系、专业、毕业生信息管理（设有就业标志，初值为‘待业’）；
(2)	实现职业类型、职业信息（职业号、类型号、需求数量、聘用数量、用人单位）登记；
(3)	实现毕业生就业登记（学号、职业号），自动修改相应学生的就业标志和职业的聘用数量，并保证聘用数量不大于需求数量；
(4)	创建存储过程查询毕业生的人数、待业人数、就业人数和就业率；
(5)	创建存储过程查询各专业的毕业生就业率；
(6)	创建check约束限制毕业生性别必须为‘男’或‘女’；
(7)	建立表间关系。

---

## 依赖软件
- **JDK 17** 及以上
- **Node.js 16+**（推荐 LTS 版本）
- **Maven 3.8+**（已内置 `mvnw` 脚本，无需全局安装）
- **MySQL 8.0+**
- 推荐开发工具：
  - VSCode（前端）
  - IntelliJ IDEA（后端）

---

## 数据库初始化
1. 使用 MySQL 创建数据库 `dhuGraduate`。
2. 执行根目录下的 `dhugraduate.sql` 脚本，初始化表结构和数据。
3. 修改 `src/main/resources/application.yml` 或 `application.properties`，配置数据库用户名、密码等信息。

---

## 总启动(前端依赖安装过了之后)

1. Windows下可用：
   ```sh
   .\start-all.bat
   ```
2. Linux下可用：
   ```bash
   # 待补bash文件
   ```


## 前端启动方式
1. 进入 `frontend` 目录：
   ```sh
   cd frontend
   ```
2. 安装依赖（未安装时/第一次默认不安装）
   ```sh
   npm install
   ```
3. 启动开发服务器：
   ```sh
   npm run dev
   ```
4. 访问本地地址（通常为 http://localhost:5173/ ）。

---

## 后端启动方式
1. 确保数据库已初始化并配置好连接信息。
2. 使用以下任一方式启动：

在项目根目录下执行：
```sh
./mvnw spring-boot:run
```
Windows 下可用：
```sh
mvnw.cmd spring-boot:run
```

## 其他说明

- 如遇依赖问题，优先检查 JDK、Node、Maven 版本。
