/*
 Navicat Premium Data Transfer

 Source Server         : 服务器的mysql
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:3306
 Source Schema         : stpam

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 16/09/2022 17:30:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for AdminLoginParam
-- ----------------------------
DROP TABLE IF EXISTS `AdminLoginParam`;
CREATE TABLE `AdminLoginParam`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `private_key` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of AdminLoginParam
-- ----------------------------

-- ----------------------------
-- Table structure for dataset
-- ----------------------------
DROP TABLE IF EXISTS `dataset`;
CREATE TABLE `dataset`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '数据集id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '数据集名称',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '数据集路径',
  `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据集上传时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dataset
-- ----------------------------
INSERT INTO `dataset` VALUES (1, 'KTH Action-boxing', '/kth_action/boxing', '2022-09-15 20:20:50');
INSERT INTO `dataset` VALUES (2, 'KTH Action-handclapping', '/kth_action/handclapping', '2022-09-15 20:21:42');
INSERT INTO `dataset` VALUES (3, 'KTH Action-handwaving', '/kth_action/handwaving', '2022-09-15 20:21:42');
INSERT INTO `dataset` VALUES (4, 'KTH Action-jogging', '/kth_action/jogging', '2022-09-15 20:21:42');
INSERT INTO `dataset` VALUES (5, 'KTH Action-running', '/kth_action/running', '2022-09-15 20:21:42');
INSERT INTO `dataset` VALUES (6, 'KTH Action-walking', '/kth_action/walking', '2022-09-15 20:21:42');
INSERT INTO `dataset` VALUES (7, 'MNIST', '/moving-mnist-example', '2022-09-15 20:34:29');
INSERT INTO `dataset` VALUES (8, 'BAIR Robot', '/bair_robot/softmotion30_44k/train', '2022-09-15 20:35:44');
INSERT INTO `dataset` VALUES (9, '巴塘1998-2016降水数据', '2巴塘1998-2016.CSV', '2022-09-16 08:53:36');

-- ----------------------------
-- Table structure for datasource
-- ----------------------------
DROP TABLE IF EXISTS `datasource`;
CREATE TABLE `datasource`  (
  `datasourceID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '数据源id',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '连接信息',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `databasetype` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '数据库类型'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of datasource
-- ----------------------------

-- ----------------------------
-- Table structure for distributesetting
-- ----------------------------
DROP TABLE IF EXISTS `distributesetting`;
CREATE TABLE `distributesetting`  (
  `dId` int NOT NULL,
  `host` int NULL DEFAULT NULL,
  `dIp` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `dDate` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`dId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of distributesetting
-- ----------------------------

-- ----------------------------
-- Table structure for file
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file`  (
  `fileId` int NOT NULL,
  `pathName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `size` double NULL DEFAULT NULL,
  `download` int NULL DEFAULT NULL,
  `uploadTime` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`fileId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of file
-- ----------------------------

-- ----------------------------
-- Table structure for model
-- ----------------------------
DROP TABLE IF EXISTS `model`;
CREATE TABLE `model`  (
  `mid` int NOT NULL AUTO_INCREMENT COMMENT '模型id',
  `filename` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '模型文件名称',
  `mname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '模型名称',
  `version` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '模型版本',
  `author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '作者',
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '模型上传时间',
  PRIMARY KEY (`mid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of model
-- ----------------------------
INSERT INTO `model` VALUES (1, 'jsj_batang2.py', '金沙江降水预测', '1.0.0', 'phw', '2022-09-09 20:11:55');
INSERT INTO `model` VALUES (2, 'predrnn-pytorch-master.zip', 'PredRNN', '1.0.0', 'phw', '2022-09-15 16:33:54');
INSERT INTO `model` VALUES (22, 'SmaAt-UNet-master.zip', 'smat', '1.0.0', 'phw', '2022-09-16 11:59:52');

-- ----------------------------
-- Table structure for modelsetting
-- ----------------------------
DROP TABLE IF EXISTS `modelsetting`;
CREATE TABLE `modelsetting`  (
  `mId` int NOT NULL,
  `mName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `mIp` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `mDate` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`mId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of modelsetting
-- ----------------------------


-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户密码',
  `mail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用途',
  `enabled` tinyint(1) NULL DEFAULT NULL COMMENT '是否启用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------


-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `pID` int NOT NULL,
  `pName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `date` datetime NOT NULL,
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `application` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`pID`) USING BTREE,
  CONSTRAINT `dID` FOREIGN KEY (`pID`) REFERENCES `dataset` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `mID` FOREIGN KEY (`pID`) REFERENCES `model` (`mid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product
-- ----------------------------


SET FOREIGN_KEY_CHECKS = 1;
