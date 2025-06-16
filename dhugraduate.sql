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

 Date: 16/06/2025 23:04:01
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
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统公告表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of announcement
-- ----------------------------
INSERT INTO `announcement` VALUES (1, '东华大学就业管理系统2025年第2号', '祝同学们找到一个适合自己的牛圈！！！', 3, '2025-06-14 13:58:35', '2025-06-14 17:14:14', 'testAttachment', '/testAttachment.txt', 0, 0);
INSERT INTO `announcement` VALUES (2, '东华大学', '天下无不散的宴席', 3, '2025-06-24 13:59:08', '2025-06-24 13:59:08', NULL, NULL, 0, 0);
INSERT INTO `announcement` VALUES (7, '上海大学', '123123', 3, '2025-06-14 17:40:42', '2025-06-14 17:40:42', '2222', '', 1, 0);
INSERT INTO `announcement` VALUES (10, '有点累', 'debug de疯了', 3, '2025-06-16 01:30:34', '2025-06-16 01:30:34', '没有，别想了', '', 0, 0);

-- ----------------------------
-- Table structure for company
-- ----------------------------
DROP TABLE IF EXISTS `company`;
CREATE TABLE `company`  (
  `company_id` bigint NOT NULL AUTO_INCREMENT COMMENT '公司id',
  `user_id` bigint NOT NULL COMMENT '外键，关联到用户ID（HR的ID)',
  `company_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '公司名称',
  `company_intro` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '公司简介',
  PRIMARY KEY (`company_id`) USING BTREE,
  INDEX `company_ibfk_1`(`user_id` ASC) USING BTREE,
  CONSTRAINT `company_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of company
-- ----------------------------
INSERT INTO `company` VALUES (1, 3, '字节跳动', '心脏与字节只能有一个跳动');
INSERT INTO `company` VALUES (4, 5, '饿了么', '心脏与字节只能有一个跳动');
INSERT INTO `company` VALUES (5, 26, '携程', '心脏与字节只能有一个跳动');
INSERT INTO `company` VALUES (6, 28, '华为', '心脏与字节只能有一个跳动');
INSERT INTO `company` VALUES (7, 26, '阿里巴巴', '我不喜欢钱');
INSERT INTO `company` VALUES (9, 34, '123312', '132312132123');

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '院系ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '院系名称',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '院系表' ROW_FORMAT = DYNAMIC;

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
  `employment_id` bigint NOT NULL AUTO_INCREMENT COMMENT '就业登记ID',
  `student_id` bigint NOT NULL COMMENT '学生ID',
  `job_id` bigint NOT NULL COMMENT '职业ID',
  `status` enum('待审核','已录用','不录用') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '待审核' COMMENT '就业申请状态',
  `apply_date` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
  PRIMARY KEY (`employment_id`) USING BTREE,
  INDEX `student_id`(`student_id` ASC) USING BTREE,
  INDEX `job_id`(`job_id` ASC) USING BTREE,
  CONSTRAINT `employment_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `employment_ibfk_2` FOREIGN KEY (`job_id`) REFERENCES `job` (`job_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '毕业生就业登记表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of employment
-- ----------------------------
INSERT INTO `employment` VALUES (7, 70127, 2, '已录用', '2025-06-15 17:20:17');
INSERT INTO `employment` VALUES (8, 70127, 1, '已录用', '2025-06-19 17:20:17');
INSERT INTO `employment` VALUES (9, 10101, 1, '已录用', '2025-06-16 03:27:06');
INSERT INTO `employment` VALUES (11, 70127, 6, '待审核', '2025-06-16 15:00:17');
INSERT INTO `employment` VALUES (12, 10101, 6, '待审核', '2025-06-16 15:00:47');
INSERT INTO `employment` VALUES (13, 10101, 5, '待审核', '2025-06-16 15:00:48');
INSERT INTO `employment` VALUES (14, 10101, 2, '不录用', '2025-06-16 15:00:49');

-- ----------------------------
-- Table structure for job
-- ----------------------------
DROP TABLE IF EXISTS `job`;
CREATE TABLE `job`  (
  `job_id` bigint NOT NULL AUTO_INCREMENT COMMENT '职业ID',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '职位标题',
  `type_id` bigint NULL DEFAULT NULL COMMENT '职业类型ID',
  `demand_number` int NOT NULL COMMENT '需求数量',
  `company_id` bigint NOT NULL COMMENT '用人单位（公司ID）',
  `status` enum('待审核','已通过','不通过') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '待审核' COMMENT '审核状态',
  `location` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '工作地点',
  `salary` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '大概的薪资范围',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '职位描述',
  PRIMARY KEY (`job_id`) USING BTREE,
  INDEX `type_id`(`type_id` ASC) USING BTREE,
  INDEX `company_id`(`company_id` ASC) USING BTREE,
  CONSTRAINT `job_ibfk_1` FOREIGN KEY (`type_id`) REFERENCES `job_type` (`type_id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `job_ibfk_2` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '职业信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of job
-- ----------------------------
INSERT INTO `job` VALUES (1, '后端开发工程师', 2, 5, 1, '已通过', '上海', '9-10k', '宇宙的尽头');
INSERT INTO `job` VALUES (2, '前端开发工程师', 2, 2, 4, '已通过', '南京', '7-8k', '谁还干前端啊');
INSERT INTO `job` VALUES (5, '主播666', 1, 999, 1, '已通过', '杭州', '8-9k', '我管你这的那的');
INSERT INTO `job` VALUES (6, '岗位发布测试1', 1, 5, 4, '已通过', '上海', '11-14k', 'ssssss');
INSERT INTO `job` VALUES (7, '外包', 1, 99999, 6, '待审核', '上海', '6-7k', '外包的同学不准吃零食');

-- ----------------------------
-- Table structure for job_type
-- ----------------------------
DROP TABLE IF EXISTS `job_type`;
CREATE TABLE `job_type`  (
  `type_id` bigint NOT NULL AUTO_INCREMENT COMMENT '类型ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '职业类型名称',
  PRIMARY KEY (`type_id`) USING BTREE,
  UNIQUE INDEX `name`(`name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '职业类型表' ROW_FORMAT = DYNAMIC;

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
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '专业表' ROW_FORMAT = DYNAMIC;

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
  `resume_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '简历的url',
  PRIMARY KEY (`student_id`) USING BTREE,
  UNIQUE INDEX `user_id`(`user_id` ASC) USING BTREE,
  INDEX `major_id`(`major_id` ASC) USING BTREE,
  CONSTRAINT `student_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `student_ibfk_2` FOREIGN KEY (`major_id`) REFERENCES `major` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 123123322 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '毕业生表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (10101, 32, 3, 2025, NULL);
INSERT INTO `student` VALUES (70127, 2, 1, 2026, 'https://java-dhu.oss-cn-shanghai.aliyuncs.com/2025/06/resume/c5625dcb1c714b0296d42ba32160d9aa.pdf');
INSERT INTO `student` VALUES (123123321, 33, 1, 2026, NULL);

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
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (2, 'stu', '123456', 'student', '你好', '男', '22222@qq.com', '', NULL);
INSERT INTO `user` VALUES (3, 'lil', '123456', 'admin', '小马呀', '男', '123@qq.com', '123456', NULL);
INSERT INTO `user` VALUES (5, 'com', '123456', 'company', '相思', '男', '1', '1', NULL);
INSERT INTO `user` VALUES (22, '泥花', '123', 'company', '圣代', '男', '123', '123', NULL);
INSERT INTO `user` VALUES (23, '学生1', '123', 'student', '学生11', '男', '123@qq.com', '123', NULL);
INSERT INTO `user` VALUES (26, '达拉崩吧', '1234', 'company', '五', '男', '213', '123', NULL);
INSERT INTO `user` VALUES (28, '企业家', '123', 'company', '华尔街之狼', '男', '111', '222', NULL);
INSERT INTO `user` VALUES (30, '企业2', '123', 'company', '企业2', '女', '2134', '123231', NULL);
INSERT INTO `user` VALUES (31, '企业3', '123', 'company', '企业3', '男', '213', '123', NULL);
INSERT INTO `user` VALUES (32, '学生n', '123', 'student', '学生n', '女', '1234', '123213321', NULL);
INSERT INTO `user` VALUES (33, '好累好累的学生', '123456', 'student', '累累累', '女', '123213', '213123', NULL);
INSERT INTO `user` VALUES (34, '企业1', '123', 'company', '123', '男', '123321', '123123', NULL);

-- ----------------------------
-- View structure for company_job_summary
-- ----------------------------
DROP VIEW IF EXISTS `company_job_summary`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `company_job_summary` AS select `c`.`company_id` AS `company_id`,`c`.`company_name` AS `company_name`,count(`j`.`job_id`) AS `job_count`,sum(`j`.`demand_number`) AS `demand_count`,sum(ifnull(`e`.`hired_count`,0)) AS `approved_count`,sum(ifnull(`a`.`application_count`,0)) AS `application_count` from (((`company` `c` left join `job` `j` on((`c`.`company_id` = `j`.`company_id`))) left join (select `employment`.`job_id` AS `job_id`,count(0) AS `hired_count` from `employment` where (`employment`.`status` = '已录用') group by `employment`.`job_id`) `e` on((`j`.`job_id` = `e`.`job_id`))) left join (select `employment`.`job_id` AS `job_id`,count(0) AS `application_count` from `employment` group by `employment`.`job_id`) `a` on((`j`.`job_id` = `a`.`job_id`))) group by `c`.`company_id`,`c`.`company_name`;

-- ----------------------------
-- View structure for department_info
-- ----------------------------
DROP VIEW IF EXISTS `department_info`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `department_info` AS select `d`.`id` AS `department_id`,`d`.`name` AS `department_name`,count(`s`.`student_id`) AS `total_students`,sum((`s`.`employment_status` = '就业')) AS `employed_students`,round(ifnull((sum((`s`.`employment_status` = '就业')) / nullif(count(`s`.`student_id`),0)),0),4) AS `employment_rate` from ((`department` `d` left join `major` `m` on((`d`.`id` = `m`.`department_id`))) left join `student_info` `s` on((`m`.`id` = `s`.`major_id`))) group by `d`.`id`,`d`.`name`;

-- ----------------------------
-- View structure for global_info
-- ----------------------------
DROP VIEW IF EXISTS `global_info`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `global_info` AS select (select count(0) from `student`) AS `total_students`,(select count(0) from `student_info` where (`student_info`.`employment_status` = '就业')) AS `employed_students`,(select count(0) from `student_info` where (`student_info`.`employment_status` = '待业')) AS `unemployed_students`,(select count(0) from `company`) AS `company_count`,(select sum(`job`.`demand_number`) from `job`) AS `total_job_demand`,(select sum(`job_info`.`hired_number`) from `job_info`) AS `total_hired`,(select count(0) from `job`) AS `job_count`,(select count(0) from `employment` where (`employment`.`status` = '已录用')) AS `total_applications`,(select count(0) from `announcement`) AS `announcement_count`;

-- ----------------------------
-- View structure for job_info
-- ----------------------------
DROP VIEW IF EXISTS `job_info`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `job_info` AS select `j`.`job_id` AS `job_id`,`j`.`title` AS `title`,`j`.`type_id` AS `type_id`,`j`.`demand_number` AS `demand_number`,`j`.`company_id` AS `company_id`,`j`.`status` AS `status`,`j`.`salary` AS `salary`,`j`.`location` AS `location`,`j`.`description` AS `description`,(select count(0) from `employment` `e` where ((`e`.`job_id` = `j`.`job_id`) and (`e`.`status` = '已录用'))) AS `hired_number` from `job` `j`;

-- ----------------------------
-- View structure for student_employment_summary
-- ----------------------------
DROP VIEW IF EXISTS `student_employment_summary`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `student_employment_summary` AS select `s`.`student_id` AS `student_id`,`u`.`name` AS `student_name`,`s`.`employment_status` AS `employment_status`,count(`e`.`employment_id`) AS `total_applications`,sum((`e`.`status` = '已录用')) AS `offer_count` from ((`student_info` `s` left join `user` `u` on((`s`.`user_id` = `u`.`id`))) left join `employment` `e` on((`s`.`student_id` = `e`.`student_id`))) group by `s`.`student_id`,`u`.`name`,`s`.`employment_status`;

-- ----------------------------
-- View structure for student_info
-- ----------------------------
DROP VIEW IF EXISTS `student_info`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `student_info` AS select `s`.`student_id` AS `student_id`,`s`.`user_id` AS `user_id`,`s`.`major_id` AS `major_id`,`s`.`graduation_year` AS `graduation_year`,`s`.`resume_url` AS `resume_url`,`u`.`name` AS `name`,`m`.`name` AS `major`,`u`.`gender` AS `gender`,`u`.`phone` AS `phone`,(case when exists(select 1 from `employment` `e` where ((`e`.`student_id` = `s`.`student_id`) and (`e`.`status` = '已录用'))) then '就业' else '待业' end) AS `employment_status` from ((`student` `s` left join `user` `u` on((`u`.`id` = `s`.`user_id`))) left join `major` `m` on((`m`.`id` = `s`.`major_id`)));

SET FOREIGN_KEY_CHECKS = 1;
