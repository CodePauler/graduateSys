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

 Date: 14/06/2025 21:52:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for announcement
-- ----------------------------
DROP TABLE IF EXISTS `announcement`;
CREATE TABLE `announcement`  (
  `announcement_id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '公告标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '公告内容',
  `creator_id` bigint NOT NULL COMMENT '发布者ID（管理员）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `attachment_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '附件原文件名',
  `attachment_url` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '附件存储路径',
  `is_pinned` tinyint NULL DEFAULT 0 COMMENT '是否置顶（0-否 1-是）',
  `view_count` int NULL DEFAULT 0 COMMENT '浏览量',
  PRIMARY KEY (`announcement_id`) USING BTREE,
  INDEX `publisher_id`(`creator_id` ASC) USING BTREE,
  CONSTRAINT `announcement_ibfk_1` FOREIGN KEY (`creator_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统公告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of announcement
-- ----------------------------
INSERT INTO `announcement` VALUES (1, '东华大学就业管理系统2025年第2号', '祝同学们找到一个适合自己的牛圈！！！', 3, '2025-06-14 13:58:35', '2025-06-14 17:14:14', 'testAttachment', '/testAttachment.txt', 0, 0);
INSERT INTO `announcement` VALUES (2, '东华大学', '天下无不散的宴席', 3, '2025-06-24 13:59:08', '2025-06-24 13:59:08', NULL, NULL, 0, 0);
INSERT INTO `announcement` VALUES (7, '上海大学', '123123', 3, '2025-06-14 17:40:42', '2025-06-14 17:40:42', '2222', '', 1, 0);

-- ----------------------------
-- Table structure for company
-- ----------------------------
DROP TABLE IF EXISTS `company`;
CREATE TABLE `company`  (
  `company_id` bigint NOT NULL AUTO_INCREMENT COMMENT '公司id',
  `user_id` int NOT NULL COMMENT '外键，关联到用户ID（HR的ID)',
  `company_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '公司名称',
  `company_intro` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '公司简介',
  PRIMARY KEY (`company_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of company
-- ----------------------------
INSERT INTO `company` VALUES (1, 3, '字节跳动', '心脏与字节只能有一个跳动');
INSERT INTO `company` VALUES (4, 22, '饿了么', '心脏与字节只能有一个跳动');
INSERT INTO `company` VALUES (5, 26, '携程', '心脏与字节只能有一个跳动');
INSERT INTO `company` VALUES (6, 28, '华为', '心脏与字节只能有一个跳动');
INSERT INTO `company` VALUES (7, 26, '阿里巴巴', '我不喜欢钱');
INSERT INTO `company` VALUES (8, 29, 'jumpTrading', '神秘公司');

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '院系ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '院系名称',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '院系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES (2, '信息科学与技术学院');
INSERT INTO `department` VALUES (3, '纺织学院');
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
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '毕业生就业登记表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of employment
-- ----------------------------
INSERT INTO `employment` VALUES (1, 1, 1, '已录用', '2025-06-12 21:56:50');

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
  `status` enum('待审核','已通过','不通过') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '待审核' COMMENT '审核状态',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '职位描述',
  PRIMARY KEY (`job_id`) USING BTREE,
  INDEX `type_id`(`type_id` ASC) USING BTREE,
  INDEX `company_id`(`company_id` ASC) USING BTREE,
  CONSTRAINT `job_ibfk_1` FOREIGN KEY (`type_id`) REFERENCES `job_type` (`type_id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `job_ibfk_2` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '职业信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of job
-- ----------------------------
INSERT INTO `job` VALUES (1, '后端开发工程师', 2, 5, 1, 1, '待审核', '宇宙的尽头');
INSERT INTO `job` VALUES (2, '前端开发工程师', 2, 2, 1, 4, '待审核', '谁还干前端啊');
INSERT INTO `job` VALUES (5, '主播666', 1, 999, 999, 1, '待审核', '我管你这的那的');

-- ----------------------------
-- Table structure for job_type
-- ----------------------------
DROP TABLE IF EXISTS `job_type`;
CREATE TABLE `job_type`  (
  `type_id` bigint NOT NULL AUTO_INCREMENT COMMENT '类型ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '职业类型名称',
  PRIMARY KEY (`type_id`) USING BTREE,
  UNIQUE INDEX `name`(`name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '职业类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of job_type
-- ----------------------------
INSERT INTO `job_type` VALUES (2, '专业技术类');
INSERT INTO `job_type` VALUES (4, '传统服务与商业类');
INSERT INTO `job_type` VALUES (1, '党政机关与公共管理类');
INSERT INTO `job_type` VALUES (7, '农业与乡村振兴类');
INSERT INTO `job_type` VALUES (6, '基础建设类');
INSERT INTO `job_type` VALUES (5, '工业与制造类');
INSERT INTO `job_type` VALUES (8, '新兴职业类');
INSERT INTO `job_type` VALUES (3, '现代服务类');

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
) ENGINE = InnoDB AUTO_INCREMENT = 2132122 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '毕业生表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (1, 23, 1, 2026, '就业');
INSERT INTO `student` VALUES (2132121, 32, 3, 2025, '待业');

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
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (2, '小www22', '123456', 'student', '吴济楠', '男', '231370127@mail.dhu.edu.cn', '', NULL);
INSERT INTO `user` VALUES (3, 'lil', '123456', 'admin', '小马呀', '男', '123@qq.com', '123456', NULL);
INSERT INTO `user` VALUES (5, 'xiaowu', '123', 'student', 'wwwwwwww', '男', '1', '1', NULL);
INSERT INTO `user` VALUES (22, '东华大学', '123', 'company', '圣代', '男', '123', '123', NULL);
INSERT INTO `user` VALUES (23, '学生1', '123', 'student', '学生11', '男', '123@qq.com', '123', NULL);
INSERT INTO `user` VALUES (26, '达拉崩吧', '1234', 'company', '五', '男', '213', '123', NULL);
INSERT INTO `user` VALUES (28, '企业家', '123', 'company', '华尔街之狼', '男', '111', '222', NULL);
INSERT INTO `user` VALUES (29, '是学生', '123', 'student', '使学生', '男', '222', '222', NULL);
INSERT INTO `user` VALUES (30, '企业2', '123', 'company', '企业2', '女', '2134', '123231', NULL);
INSERT INTO `user` VALUES (31, '企业3', '123', 'company', '企业3', '男', '213', '123', NULL);
INSERT INTO `user` VALUES (32, '学生n', '123', 'student', '学生n', '女', '1234', '123213321', NULL);

SET FOREIGN_KEY_CHECKS = 1;
