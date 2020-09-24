/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.171.128
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : 192.168.171.128:3306
 Source Schema         : fshop_user

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 02/09/2020 20:34:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for su_address
-- ----------------------------
DROP TABLE IF EXISTS `su_address`;
CREATE TABLE `su_address`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户地址ID',
  `user_id` bigint(20) UNSIGNED NOT NULL COMMENT '用户ID',
  `postcode` smallint(6) NOT NULL COMMENT '邮编',
  `province` smallint(6) NOT NULL COMMENT '省',
  `city` smallint(6) NOT NULL COMMENT '市',
  `district` smallint(6) NOT NULL COMMENT '区',
  `address` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '详细地址',
  `is_default` tinyint(1) NOT NULL COMMENT '是否默认',
  `gmt_modified` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户地址表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for su_balance_log
-- ----------------------------
DROP TABLE IF EXISTS `su_balance_log`;
CREATE TABLE `su_balance_log`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '余额日志ID',
  `user_id` bigint(20) UNSIGNED NOT NULL COMMENT '用户ID',
  `source` tinyint(3) UNSIGNED NOT NULL DEFAULT 1 COMMENT '记录来源：1 订单，2 退货单',
  `source_id` bigint(20) UNSIGNED NOT NULL COMMENT '相关单据ID',
  `change_amount` decimal(8, 2) NOT NULL COMMENT '变动金额',
  `gmt_modified` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '余额日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for su_level
-- ----------------------------
DROP TABLE IF EXISTS `su_level`;
CREATE TABLE `su_level`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '级别ID',
  `level_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '级别名称',
  `min_point` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '最低积分',
  `max_point` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '最高积分',
  `gmt_modified` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `gmt_create` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户级别表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for su_login_log
-- ----------------------------
DROP TABLE IF EXISTS `su_login_log`;
CREATE TABLE `su_login_log`  (
  `login_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '登陆日志ID',
  `user_id` bigint(20) UNSIGNED NOT NULL COMMENT '登陆用户ID',
  `login_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '用户登陆时间',
  `login_ip` int(10) UNSIGNED NOT NULL COMMENT '登陆IP',
  `login_type` tinyint(4) NOT NULL COMMENT '登陆类型：0未成功，1成功',
  PRIMARY KEY (`login_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '登陆日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for su_point_log
-- ----------------------------
DROP TABLE IF EXISTS `su_point_log`;
CREATE TABLE `su_point_log`  (
  `point_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '积分日志ID',
  `user_id` bigint(20) UNSIGNED NOT NULL COMMENT '用户ID',
  `source` tinyint(3) UNSIGNED NOT NULL COMMENT '积分来源：0订单，1登陆，2活动',
  `refer_number` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '积分来源相关编号',
  `change_point` smallint(6) NOT NULL DEFAULT 0 COMMENT '变更积分数',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '积分日志生成时间',
  PRIMARY KEY (`point_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '积分日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for su_user
-- ----------------------------
DROP TABLE IF EXISTS `su_user`;
CREATE TABLE `su_user`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `login_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录名',
  `login_pwd` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录密码',
  `user_status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '用户状态',
  `gmt_modified` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for su_user_detail
-- ----------------------------
DROP TABLE IF EXISTS `su_user_detail`;
CREATE TABLE `su_user_detail`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户详情ID',
  `user_id` bigint(20) UNSIGNED NOT NULL COMMENT '用户ID',
  `level_id` tinyint(4) NOT NULL DEFAULT 1 COMMENT '级别：1 普通VIP，2 超级VIP',
  `real_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '真实姓名',
  `certificate_type` tinyint(4) NOT NULL DEFAULT 1 COMMENT '证件类型：1 身份证，2 军官证，3 护照',
  `certificate_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件号码',
  `phone` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `gender` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `point` int(11) NOT NULL DEFAULT 0 COMMENT '积分',
  `birthday` datetime(0) NULL DEFAULT NULL COMMENT '生日',
  `balance` decimal(8, 2) NOT NULL COMMENT '账户余额',
  `gmt_modified` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `gmt_create` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户详情表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
