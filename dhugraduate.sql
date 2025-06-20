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

 Date: 18/06/2025 14:34:04
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
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统公告表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of announcement
-- ----------------------------
INSERT INTO `announcement` VALUES (11, '东华大学毕业生管理系统2025年第一号公告', '请同学们抓紧秋招机会！', 1, '2025-06-18 14:02:21', '2025-06-18 14:02:21', '无', '', 0, 0);
INSERT INTO `announcement` VALUES (12, '关于松江大学城联合招聘会的通知', '无', 1, '2025-06-18 14:02:44', '2025-06-18 14:02:44', '无', '', 0, 0);

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
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of company
-- ----------------------------
INSERT INTO `company` VALUES (1, 2, '字节跳动', '1');
INSERT INTO `company` VALUES (2, 3, '饿了么', '1');
INSERT INTO `company` VALUES (3, 4, '美团', '1');
INSERT INTO `company` VALUES (4, 5, '华为', '1');
INSERT INTO `company` VALUES (5, 6, '阿里巴巴', '1');

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department`  (
  `department_id` bigint NOT NULL AUTO_INCREMENT COMMENT '院系ID',
  `department_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '院系名称',
  PRIMARY KEY (`department_id`) USING BTREE,
  UNIQUE INDEX `name`(`department_name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 151 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '院系表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES (2, '信息科学与技术学院');
INSERT INTO `department` VALUES (4, '理学院');
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
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '毕业生就业登记表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of employment
-- ----------------------------
INSERT INTO `employment` VALUES (16, 123123322, 9, '待审核', '2025-06-18 14:03:50');
INSERT INTO `employment` VALUES (17, 123123322, 10, '待审核', '2025-06-18 14:03:52');
INSERT INTO `employment` VALUES (18, 123123323, 11, '已录用', '2025-06-18 14:05:41');
INSERT INTO `employment` VALUES (19, 123123323, 12, '待审核', '2025-06-18 14:05:42');
INSERT INTO `employment` VALUES (20, 123123324, 13, '已录用', '2025-06-18 14:06:13');
INSERT INTO `employment` VALUES (21, 123123325, 10, '已录用', '2025-06-18 14:06:41');
INSERT INTO `employment` VALUES (22, 123123326, 9, '已录用', '2025-06-18 14:06:48');
INSERT INTO `employment` VALUES (23, 123123326, 13, '已录用', '2025-06-18 14:06:50');
INSERT INTO `employment` VALUES (24, 123123327, 11, '已录用', '2025-06-18 14:06:59');
INSERT INTO `employment` VALUES (25, 123123327, 12, '待审核', '2025-06-18 14:07:18');
INSERT INTO `employment` VALUES (26, 123123328, 9, '待审核', '2025-06-18 14:07:37');
INSERT INTO `employment` VALUES (27, 123123330, 9, '待审核', '2025-06-18 14:16:09');
INSERT INTO `employment` VALUES (28, 123123330, 13, '已录用', '2025-06-18 14:16:12');

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
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '职业信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of job
-- ----------------------------
INSERT INTO `job` VALUES (9, '前端开发工程师', 2, 3, 1, '已通过', '上海', '9-8K', '至少有一段实习经验');
INSERT INTO `job` VALUES (10, '后端开发工程师', 2, 3, 2, '已通过', '南京', '', '熟悉Spring框架');
INSERT INTO `job` VALUES (11, '运维', 2, 4, 3, '已通过', '杭州/上海', '8-9K', '熟悉zookeeper');
INSERT INTO `job` VALUES (12, '外包OD', 2, 10, 4, '已通过', '上海/南京/杭州', '', '外派至其他公司');
INSERT INTO `job` VALUES (13, '客服', 4, 3, 5, '已通过', '居家', '7-8k', '有双休，五险一金');
INSERT INTO `job` VALUES (14, '测试岗位', 2, 5, 1, '待审核', '上海', '8-9K', '对接口进行测试');

-- ----------------------------
-- Table structure for job_type
-- ----------------------------
DROP TABLE IF EXISTS `job_type`;
CREATE TABLE `job_type`  (
  `type_id` bigint NOT NULL AUTO_INCREMENT COMMENT '类型ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '职业类型名称',
  PRIMARY KEY (`type_id`) USING BTREE,
  UNIQUE INDEX `name`(`name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 247 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '职业类型表' ROW_FORMAT = DYNAMIC;

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
  `major_id` bigint NOT NULL AUTO_INCREMENT COMMENT '专业ID',
  `major_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '专业名称',
  `department_id` bigint NULL DEFAULT NULL COMMENT '所属院系',
  PRIMARY KEY (`major_id`) USING BTREE,
  INDEX `department_id`(`department_id` ASC) USING BTREE,
  CONSTRAINT `major_ibfk_1` FOREIGN KEY (`department_id`) REFERENCES `department` (`department_id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1009 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '专业表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of major
-- ----------------------------
INSERT INTO `major` VALUES (1, '计算机科学与技术', 1);
INSERT INTO `major` VALUES (2, '软件工程', 1);
INSERT INTO `major` VALUES (3, '信息安全', 1);
INSERT INTO `major` VALUES (4, '大数据', 1);
INSERT INTO `major` VALUES (5, '智能科学', 1);
INSERT INTO `major` VALUES (6, '人工智能', 2);
INSERT INTO `major` VALUES (7, '通信学', 2);
INSERT INTO `major` VALUES (8, '智能纺织', 3);
INSERT INTO `major` VALUES (9, '材料学', 3);
INSERT INTO `major` VALUES (10, '物理学', 4);
INSERT INTO `major` VALUES (11, '数学', 4);

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
  CONSTRAINT `student_ibfk_2` FOREIGN KEY (`major_id`) REFERENCES `major` (`major_id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 123123355 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '毕业生表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (123123322, 1649, 8, 2026, '');
INSERT INTO `student` VALUES (123123323, 1650, 5, 2026, NULL);
INSERT INTO `student` VALUES (123123324, 1651, 9, 2026, NULL);
INSERT INTO `student` VALUES (123123325, 1652, 3, 2026, NULL);
INSERT INTO `student` VALUES (123123326, 1653, 4, 2026, NULL);
INSERT INTO `student` VALUES (123123327, 1654, 6, 2026, NULL);
INSERT INTO `student` VALUES (123123328, 1655, 10, 2026, NULL);
INSERT INTO `student` VALUES (123123329, 1656, 9, 2026, NULL);
INSERT INTO `student` VALUES (123123330, 1657, 11, 2026, NULL);
INSERT INTO `student` VALUES (123123331, 1658, 1, 2026, NULL);

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
) ENGINE = InnoDB AUTO_INCREMENT = 1684 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '123', 'admin', 'admin', '男', '123@1.com', '123', NULL);
INSERT INTO `user` VALUES (2, 'com1', '123', 'company', '张一鸣', '男', '123@1.com', '123', NULL);
INSERT INTO `user` VALUES (3, 'com2', '123', 'company', '胖猫', '男', '123@1.com', '123', NULL);
INSERT INTO `user` VALUES (4, 'com3', '123', 'company', '潭竹', '女', '123@1.com', '123', NULL);
INSERT INTO `user` VALUES (5, 'com4', '123', 'company', '任正非', '男', '123@1.com', '123', NULL);
INSERT INTO `user` VALUES (6, 'com5', '123', 'company', '范小勤', '男', '123@1.com', '123', NULL);
INSERT INTO `user` VALUES (1649, 'stu1', '123', 'student', '冯桂芳', '女', 'stu1@gmail.com', '18682828879', NULL);
INSERT INTO `user` VALUES (1650, 'stu2', '123', 'student', '罗林', '女', 'stu2@163.com', '18962839420', NULL);
INSERT INTO `user` VALUES (1651, 'stu3', '123', 'student', '窦超', '男', 'stu3@163.com', '18780884499', NULL);
INSERT INTO `user` VALUES (1652, 'stu4', '123', 'student', '罗强', '女', 'stu4@qq.com', '15983125711', NULL);
INSERT INTO `user` VALUES (1653, 'stu5', '123', 'student', '王欣', '男', 'stu5@gmail.com', '15673735581', NULL);
INSERT INTO `user` VALUES (1654, 'stu6', '123', 'student', '曹丽丽', '男', 'stu6@qq.com', '18817914215', NULL);
INSERT INTO `user` VALUES (1655, 'stu7', '123', 'student', '骆梅', '男', 'stu7@qq.com', '13852112676', NULL);
INSERT INTO `user` VALUES (1656, 'stu8', '123', 'student', '汪瑜', '女', 'stu8@gmail.com', '14549216556', NULL);
INSERT INTO `user` VALUES (1657, 'stu9', '123', 'student', '曹凤英', '男', 'stu9@126.com', '13972255279', NULL);
INSERT INTO `user` VALUES (1658, 'stu10', '123', 'student', '郑龙', '男', 'stu10@qq.com', '18571681950', NULL);

-- ----------------------------
-- View structure for company_job_summary
-- ----------------------------
DROP VIEW IF EXISTS `company_job_summary`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `company_job_summary` AS select `c`.`company_id` AS `company_id`,`c`.`company_name` AS `company_name`,count(`j`.`job_id`) AS `job_count`,sum(`j`.`demand_number`) AS `demand_count`,sum(ifnull(`e`.`hired_count`,0)) AS `approved_count`,sum(ifnull(`a`.`application_count`,0)) AS `application_count` from (((`company` `c` left join `job` `j` on((`c`.`company_id` = `j`.`company_id`))) left join (select `employment`.`job_id` AS `job_id`,count(0) AS `hired_count` from `employment` where (`employment`.`status` = '已录用') group by `employment`.`job_id`) `e` on((`j`.`job_id` = `e`.`job_id`))) left join (select `employment`.`job_id` AS `job_id`,count(0) AS `application_count` from `employment` group by `employment`.`job_id`) `a` on((`j`.`job_id` = `a`.`job_id`))) group by `c`.`company_id`,`c`.`company_name`;

-- ----------------------------
-- View structure for company_students
-- ----------------------------
DROP VIEW IF EXISTS `company_students`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `company_students` AS select `company`.`company_name` AS `company_name`,count(0) AS `student_count` from ((`employment` left join `job` on((`employment`.`job_id` = `job`.`job_id`))) left join `company` on((`company`.`company_id` = `job`.`company_id`))) group by `company`.`company_id`;

-- ----------------------------
-- View structure for department_info
-- ----------------------------
DROP VIEW IF EXISTS `department_info`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `department_info` AS select `d`.`department_id` AS `department_id`,`d`.`department_name` AS `department_name`,count(`s`.`student_id`) AS `total_students`,sum((`s`.`employment_status` = '就业')) AS `employed_students`,round(ifnull((sum((`s`.`employment_status` = '就业')) / nullif(count(`s`.`student_id`),0)),0),4) AS `employment_rate` from ((`department` `d` left join `major` `m` on((`d`.`department_id` = `m`.`department_id`))) left join `student_info` `s` on((`m`.`major_id` = `s`.`major_id`))) group by `d`.`department_id`,`d`.`department_name`;

-- ----------------------------
-- View structure for global_info
-- ----------------------------
DROP VIEW IF EXISTS `global_info`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `global_info` AS select (select count(0) from `student`) AS `total_students`,(select count(0) from `student_info` where (`student_info`.`employment_status` = '就业')) AS `employed_students`,(select count(0) from `student_info` where (`student_info`.`employment_status` = '待业')) AS `unemployed_students`,(select count(0) from `company`) AS `company_count`,(select sum(`job_info`.`demand_number`) from `job_info` where (`job_info`.`status` = '已通过')) AS `total_job_demand`,(select sum(`job_info`.`hired_number`) from `job_info`) AS `total_hired`,(select count(0) from `job_info` where (`job_info`.`status` = '已通过')) AS `job_count`,(select count(0) from `employment` where (`employment`.`status` = '已录用')) AS `total_applications`,(select count(0) from `announcement`) AS `announcement_count`;

-- ----------------------------
-- View structure for job_info
-- ----------------------------
DROP VIEW IF EXISTS `job_info`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `job_info` AS select `t`.`job_id` AS `job_id`,`t`.`title` AS `title`,`t`.`type_id` AS `type_id`,`t`.`demand_number` AS `demand_number`,`t`.`company_id` AS `company_id`,`t`.`status` AS `status`,`t`.`salary` AS `salary`,`t`.`location` AS `location`,`t`.`description` AS `description`,`t`.`hired_number` AS `hired_number`,(case when ((`t`.`demand_number` - `t`.`hired_number`) > 0) then '招聘中' else '已结束' end) AS `hire_status` from (select `j`.`job_id` AS `job_id`,`j`.`title` AS `title`,`j`.`type_id` AS `type_id`,`j`.`demand_number` AS `demand_number`,`j`.`company_id` AS `company_id`,`j`.`status` AS `status`,`j`.`salary` AS `salary`,`j`.`location` AS `location`,`j`.`description` AS `description`,(select count(0) from `employment` `e` where ((`e`.`job_id` = `j`.`job_id`) and (`e`.`status` = '已录用'))) AS `hired_number` from `job` `j`) `t`;

-- ----------------------------
-- View structure for student_employment_summary
-- ----------------------------
DROP VIEW IF EXISTS `student_employment_summary`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `student_employment_summary` AS select `s`.`student_id` AS `student_id`,`u`.`name` AS `student_name`,`s`.`employment_status` AS `employment_status`,count(`e`.`employment_id`) AS `total_applications`,sum((`e`.`status` = '已录用')) AS `offer_count` from ((`student_info` `s` left join `user` `u` on((`s`.`user_id` = `u`.`id`))) left join `employment` `e` on((`s`.`student_id` = `e`.`student_id`))) group by `s`.`student_id`,`u`.`name`,`s`.`employment_status`;

-- ----------------------------
-- View structure for student_info
-- ----------------------------
DROP VIEW IF EXISTS `student_info`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `student_info` AS select `s`.`student_id` AS `student_id`,`s`.`user_id` AS `user_id`,`s`.`major_id` AS `major_id`,`s`.`graduation_year` AS `graduation_year`,`s`.`resume_url` AS `resume_url`,`u`.`name` AS `name`,`m`.`major_name` AS `major`,`u`.`gender` AS `gender`,`u`.`phone` AS `phone`,`u`.`email` AS `email`,(case when exists(select 1 from `employment` `e` where ((`e`.`student_id` = `s`.`student_id`) and (`e`.`status` = '已录用'))) then '就业' else '待业' end) AS `employment_status` from ((`student` `s` left join `user` `u` on((`u`.`id` = `s`.`user_id`))) left join `major` `m` on((`m`.`major_id` = `s`.`major_id`)));

SET FOREIGN_KEY_CHECKS = 1;
