/*
 Navicat Premium Dump SQL

 Source Server         : mysql-1
 Source Server Type    : MySQL
 Source Server Version : 80039 (8.0.39)
 Source Host           : localhost:3306
 Source Schema         : dhugraduate

 Target Server Type    : MySQL
 Target Server Version : 80039 (8.0.39)
 File Encoding         : 65001

 Date: 11/06/2025 15:15:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for company
-- ----------------------------
DROP TABLE IF EXISTS `company`;
CREATE TABLE `company`  (
  `company_id` int NOT NULL AUTO_INCREMENT COMMENT '公司（HR）id',
  `user_id` int NOT NULL COMMENT '外键，关联到用户ID',
  `company_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '公司名称',
  `company_intro` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '公司简介',
  PRIMARY KEY (`company_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of company
-- ----------------------------
INSERT INTO `company` VALUES (1, 19, '哈哈哈哈', '水水水水水');
INSERT INTO `company` VALUES (2, 20, '哈哈哈哈', '水水水水水');
INSERT INTO `company` VALUES (3, 21, '哈哈哈哈', '水水水水水');
INSERT INTO `company` VALUES (4, 22, '秒杀上大', '暴打华理');

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '院系ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '院系名称',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '院系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES (1, '计算机科学与技术学院');

-- ----------------------------
-- Table structure for employment
-- ----------------------------
DROP TABLE IF EXISTS `employment`;
CREATE TABLE `employment`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '就业登记ID',
  `student_id` bigint NOT NULL COMMENT '学生ID',
  `job_id` bigint NOT NULL COMMENT '职业ID',
  `status` enum('待审核','已录用','未录用') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '待审核' COMMENT '就业申请状态',
  `apply_date` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `student_id`(`student_id` ASC) USING BTREE,
  INDEX `job_id`(`job_id` ASC) USING BTREE,
  CONSTRAINT `employment_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `employment_ibfk_2` FOREIGN KEY (`job_id`) REFERENCES `job` (`job_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '毕业生就业登记表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of employment
-- ----------------------------

-- ----------------------------
-- Table structure for job
-- ----------------------------
DROP TABLE IF EXISTS `job`;
CREATE TABLE `job`  (
  `job_id` bigint NOT NULL AUTO_INCREMENT COMMENT '职业ID',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '职位标题',
  `type_id` bigint NULL DEFAULT NULL COMMENT '职业类型ID',
  `demand_number` int NOT NULL COMMENT '需求数量',
  `hired_number` int NULL DEFAULT 0 COMMENT '聘用数量',
  `company_id` bigint NOT NULL COMMENT '用人单位（公司ID）',
  `status` enum('待审核','已通过','未通过') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '待审核' COMMENT '审核状态',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '职位描述',
  PRIMARY KEY (`job_id`) USING BTREE,
  INDEX `type_id`(`type_id` ASC) USING BTREE,
  INDEX `company_id`(`company_id` ASC) USING BTREE,
  CONSTRAINT `job_ibfk_1` FOREIGN KEY (`type_id`) REFERENCES `job_type` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `job_ibfk_2` FOREIGN KEY (`company_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '职业信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of job
-- ----------------------------

-- ----------------------------
-- Table structure for job_type
-- ----------------------------
DROP TABLE IF EXISTS `job_type`;
CREATE TABLE `job_type`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '类型ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '职业类型名称',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '职业类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of job_type
-- ----------------------------

-- ----------------------------
-- Table structure for major
-- ----------------------------
DROP TABLE IF EXISTS `major`;
CREATE TABLE `major`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '专业ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '专业名称',
  `department_id` bigint NULL DEFAULT NULL COMMENT '所属院系',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `department_id`(`department_id` ASC) USING BTREE,
  CONSTRAINT `major_ibfk_1` FOREIGN KEY (`department_id`) REFERENCES `department` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '专业表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of major
-- ----------------------------
INSERT INTO `major` VALUES (1, '计算机科学', 1);
INSERT INTO `major` VALUES (2, '软件工程', 1);
INSERT INTO `major` VALUES (3, '电子信息', 1);

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `student_id` bigint NOT NULL AUTO_INCREMENT COMMENT '学号（毕业生ID）',
  `user_id` bigint NOT NULL COMMENT '关联用户ID',
  `major_id` bigint NULL DEFAULT NULL COMMENT '所学专业ID',
  `graduation_year` int NULL DEFAULT NULL COMMENT '毕业年份',
  `employment_status` enum('待业','就业') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '待业' COMMENT '就业标志',
  PRIMARY KEY (`student_id`) USING BTREE,
  UNIQUE INDEX `user_id`(`user_id` ASC) USING BTREE,
  INDEX `major_id`(`major_id` ASC) USING BTREE,
  CONSTRAINT `student_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `student_ibfk_2` FOREIGN KEY (`major_id`) REFERENCES `major` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 123133 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '毕业生表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (123132, 16, 2, 2025, '待业');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码（加密）',
  `role` enum('student','teacher','company','admin') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户角色',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '真实姓名',
  `gender` enum('男','女') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '性别',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '电话',
  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC) USING BTREE,
  CONSTRAINT `user_chk_1` CHECK (`gender` in (_utf8mb4'男',_utf8mb4'女'))
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'alice123', 'password123', 'student', 'Alice', '女', 'alice@example.com', '13812345678', NULL);
INSERT INTO `user` VALUES (2, '小www', '123456', 'student', '吴济楠', '男', '231370127@mail.dhu.edu.cn', '', NULL);
INSERT INTO `user` VALUES (3, 'lil', '123456', 'admin', '马应龙', '男', '123@qq.com', '123456', NULL);
INSERT INTO `user` VALUES (4, 'w', '1', 'student', '管理员', '男', '1@qq.com', '123', NULL);
INSERT INTO `user` VALUES (5, 'xiaowu', '123', 'student', '吴', '男', '1', '1', NULL);
INSERT INTO `user` VALUES (11, '时代少年团', '123', 'student', '马嘉祺', '男', '123', '123', NULL);
INSERT INTO `user` VALUES (12, '123312', '312132', 'student', '312', '男', '132312', '132132', NULL);
INSERT INTO `user` VALUES (13, '马嘉祺qqqq', '312132', 'student', '312', '男', '132312', '132132', NULL);
INSERT INTO `user` VALUES (14, '马嘉祺2', '312132', 'student', '312', '男', '132312', '132132', NULL);
INSERT INTO `user` VALUES (15, '马嘉祺3', '312132', 'student', '312', '男', '132312', '132132', NULL);
INSERT INTO `user` VALUES (16, '马嘉祺4', '312132', 'student', '312', '男', '132312', '132132', NULL);
INSERT INTO `user` VALUES (17, '时代少年团1', '123', 'company', '喜欢你', '男', '2222', '3333', NULL);
INSERT INTO `user` VALUES (18, '时代少年团3333', '123', 'company', '喜欢你', '男', '2222', '3333', NULL);
INSERT INTO `user` VALUES (19, '偶像练习生', 'cxz', 'company', '车型库', '男', '213', '213', NULL);
INSERT INTO `user` VALUES (20, '偶像练习生22', 'cxz', 'company', '车型库', '男', '213', '213', NULL);
INSERT INTO `user` VALUES (21, '偶像练习生4', 'cxz', 'company', '车型库', '男', '213', '213', NULL);
INSERT INTO `user` VALUES (22, '东华大学', '123', 'company', '洞花', '男', '123', '123', NULL);

SET FOREIGN_KEY_CHECKS = 1;
