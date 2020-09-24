/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.171.128
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : 192.168.171.128:3306
 Source Schema         : fshop_order

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 02/09/2020 20:34:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for so_cart
-- ----------------------------
DROP TABLE IF EXISTS `so_cart`;
CREATE TABLE `so_cart`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '购物车ID',
  `user_id` bigint(20) UNSIGNED NOT NULL COMMENT '用户ID',
  `product_id` bigint(20) UNSIGNED NOT NULL COMMENT '商品ID',
  `product_num` int(11) NOT NULL COMMENT '商品数量',
  `product_price` decimal(8, 2) NOT NULL COMMENT '商品价格',
  `gmt_modified` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '购物车表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for so_order_detail
-- ----------------------------
DROP TABLE IF EXISTS `so_order_detail`;
CREATE TABLE `so_order_detail`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '订单明细ID',
  `order_id` bigint(20) UNSIGNED NOT NULL COMMENT '订单ID',
  `warehouse_id` bigint(20) UNSIGNED NOT NULL COMMENT '仓库ID',
  `product_id` bigint(20) UNSIGNED NOT NULL COMMENT '商品ID',
  `product_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称',
  `product_num` int(11) NOT NULL DEFAULT 1 COMMENT '商品数量',
  `product_price` decimal(8, 2) NULL DEFAULT NULL COMMENT '商品单价',
  `product_weight` float NULL DEFAULT NULL COMMENT '商品重量',
  `average_cost` decimal(8, 2) NULL DEFAULT NULL COMMENT '平均成本',
  `discount_amount` decimal(8, 2) NULL DEFAULT NULL COMMENT '优惠金额',
  `gmt_modified` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for so_order_master
-- ----------------------------
DROP TABLE IF EXISTS `so_order_master`;
CREATE TABLE `so_order_master`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主订单ID',
  `order_sn` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单编号',
  `user_id` bigint(20) UNSIGNED NOT NULL COMMENT '买家用户ID',
  `order_amount` decimal(8, 2) NOT NULL COMMENT '订单金额',
  `discount_amount` decimal(8, 2) NULL DEFAULT NULL COMMENT '优惠金额',
  `payment_amount` decimal(8, 2) NULL DEFAULT NULL COMMENT '支付金额',
  `payment_method` tinyint(4) NULL DEFAULT NULL COMMENT '支付方式：1现金，2余额，3网银，4支付宝，5微信',
  `payment_time` datetime(0) NULL DEFAULT NULL COMMENT '支付时间',
  `receiver` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货人',
  `receive_time` datetime(0) NULL DEFAULT NULL COMMENT '收货时间',
  `province` smallint(6) NULL DEFAULT NULL COMMENT '省',
  `city` smallint(6) NULL DEFAULT NULL COMMENT '市',
  `district` smallint(6) NULL DEFAULT NULL COMMENT '区',
  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '详细地址',
  `shipping_company` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '快递公司',
  `shipping_sn` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '快递单号',
  `shipping_amount` decimal(8, 2) NULL DEFAULT NULL COMMENT '运费金额',
  `shipping_time` datetime(0) NULL DEFAULT NULL COMMENT '发货时间',
  `order_status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '订单状态',
  `order_point` int(10) UNSIGNED NULL DEFAULT 0 COMMENT '订单积分',
  `invoice_head` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票抬头',
  `gmt_modified` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_order_sn`(`order_sn`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '主订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for so_order_sub
-- ----------------------------
DROP TABLE IF EXISTS `so_order_sub`;
CREATE TABLE `so_order_sub`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '子订单ID',
  `order_master_id` bigint(20) UNSIGNED NOT NULL COMMENT '主订单ID',
  `order_sn` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单编号',
  `user_id` bigint(20) UNSIGNED NOT NULL COMMENT '买家用户ID',
  `order_money` decimal(8, 2) NOT NULL COMMENT '订单金额',
  `district_money` decimal(8, 2) NOT NULL COMMENT '优惠金额',
  `payment_money` decimal(8, 2) NOT NULL COMMENT '支付金额',
  `payment_method` tinyint(4) NOT NULL COMMENT '支付方式：1现金，2余额，3网银，4支付宝，5微信',
  `payment_time` datetime(0) NULL DEFAULT NULL COMMENT '支付时间',
  `receiver` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收货人',
  `receive_time` datetime(0) NULL DEFAULT NULL COMMENT '收货时间',
  `province` smallint(6) NOT NULL COMMENT '省',
  `city` smallint(6) NOT NULL COMMENT '市',
  `district` smallint(6) NOT NULL COMMENT '区',
  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '详细地址',
  `shipping_company` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '快递公司',
  `shipping_sn` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '快递单号',
  `shipping_money` decimal(8, 2) NOT NULL COMMENT '运费金额',
  `shipping_time` datetime(0) NULL DEFAULT NULL COMMENT '发货时间',
  `order_status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '订单状态',
  `order_point` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '订单积分',
  `invoice_head` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发票抬头',
  `gmt_modified` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_order_sn`(`order_sn`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '子订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for so_shipping
-- ----------------------------
DROP TABLE IF EXISTS `so_shipping`;
CREATE TABLE `so_shipping`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '物流公司ID',
  `company_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '物流公司名称',
  `company_contact` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '物流公司联系人',
  `company_phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '物流公司联系电话',
  `delivery_price` decimal(8, 2) NOT NULL COMMENT '配送价格',
  `gmt_modified` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '物流公司表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for so_warehouse
-- ----------------------------
DROP TABLE IF EXISTS `so_warehouse`;
CREATE TABLE `so_warehouse`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '仓库ID',
  `warehouse_sn` char(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '仓库编码',
  `warehoust_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '仓库名称',
  `warehouse_contact` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '仓库联系人',
  `warehouse_phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '仓库电话',
  `province` smallint(6) NULL DEFAULT NULL COMMENT '省',
  `city` smallint(6) NULL DEFAULT NULL COMMENT '市',
  `distrct` smallint(6) NULL DEFAULT NULL COMMENT '区',
  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '详细地址',
  `warehouse_status` tinyint(4) NULL DEFAULT 1 COMMENT '仓库状态：0禁用，1启用',
  `gmt_modified` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '仓库表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for so_warehouse_stock
-- ----------------------------
DROP TABLE IF EXISTS `so_warehouse_stock`;
CREATE TABLE `so_warehouse_stock`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '仓库库存ID',
  `warehouse_id` bigint(20) UNSIGNED NOT NULL COMMENT '仓库ID',
  `product_id` bigint(20) UNSIGNED NOT NULL COMMENT '商品ID',
  `current_num` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '当前数量',
  `lock_num` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '占用数量',
  `transit_num` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '在途数据',
  `average_cost` decimal(8, 2) NOT NULL COMMENT '移动加权成本',
  `gmt_modified` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '仓库商品库存表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
