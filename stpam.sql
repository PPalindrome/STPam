/*
 Navicat Premium Data Transfer

 Source Server         : 服务器的mysql
 Source Server Type    : MySQL
 Source Server Version : 80032
 Source Host           : localhost:3306
 Source Schema         : stpam

 Target Server Type    : MySQL
 Target Server Version : 80032
 File Encoding         : 65001

 Date: 28/02/2023 15:55:27
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
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of AdminLoginParam
-- ----------------------------
INSERT INTO `AdminLoginParam` VALUES (1, NULL, NULL, 'MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKDjUILfN4FInfCDZGIFJLxrAwM3nnBUeP6/riFKgPuSB4TMmvlahcTjaWo/a140oc5Li3luLaLUrhq2eQve5GxzHYU1AgHG8YWQtlLgW8XZhGkNbiEdLVsWRlHEIO+CSupL2dMIHkas0TGPhYnObp1YXuMhAFULkq4rnNK1AyrlAgMBAAECgYAHksu+bV2Cg3WT1cuK5a0WBX+NzjdyGDfd3g4HWSd7MCOUd8EvzbgQTZgvvS6Dr7ySJnI5VBtn19UeQsqt6V8dPG4OR7kiuOFgic9QtejP0gOUtKrRfkoY29z0HOMpA+X44Gon332DH/DN5srbMM2A6SoOA9bNHPwuwDcnUV6aoQJBAO64wnO89dmZkmFox2wTVh/b7n9CUT+1ZUnBCkcYtxD5VXmF8434aAwBkKf+d48xrm5PsuSPlX+i1Fs8uGFJSFkCQQCsiGJZF7OMvnstUs/6lnZLlyihoOLxK1r1HU35ZlSRmYupVO2HztpxBOqLBclVgKuksGhsgJoOcX2rtIjQ3qVtAkAuRIT7pZ4IxKkIVCSCn+TLW/cHlzTkIiY1e0KgsPuaQJj+aqXf2EtiOFNvG/TUPg7Otoe3oNSa3lZGMKiTwn5RAkB9vP9/0ogmIIQAqgOsqo8X09fmwyvheylUzcCP7AXh/rcL9zsM6LHGAEE2hEcQqy9sXKwdtzOPJyLhSjcTkc+ZAkEA4596oH7YnH8KP6lRZG1Ajcr49gukgat1WU4UHZR2o/gnuVL5LDboXtrx9q7pGB6ztzyCFzXglOv39PhZZQohQA==');

-- ----------------------------
-- Table structure for dataset
-- ----------------------------
DROP TABLE IF EXISTS `dataset`;
CREATE TABLE `dataset`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '数据集id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '数据集名称',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '数据集路径',
  `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据集上传时间',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用途描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dataset
-- ----------------------------
INSERT INTO `dataset` VALUES (1, 'KTH Action-boxing', '/kth_action/boxing', '2022-09-15 20:20:50', '人类行为识别');
INSERT INTO `dataset` VALUES (2, 'KTH Action-handclapping', '/kth_action/handclapping', '2022-09-15 20:21:42', '人类行为识别');
INSERT INTO `dataset` VALUES (3, 'KTH Action-handwaving', '/kth_action/handwaving', '2022-09-15 20:21:42', '人类行为识别');
INSERT INTO `dataset` VALUES (4, 'KTH Action-jogging', '/kth_action/jogging', '2022-09-15 20:21:42', '人类行为识别');
INSERT INTO `dataset` VALUES (5, 'KTH Action-running', '/kth_action/running', '2022-09-15 20:21:42', '人类行为识别');
INSERT INTO `dataset` VALUES (6, 'KTH Action-walking', '/kth_action/walking', '2022-09-15 20:21:42', '人类行为识别');
INSERT INTO `dataset` VALUES (7, 'MNIST', '/moving-mnist-example', '2022-09-15 20:34:29', '手写数字图片');
INSERT INTO `dataset` VALUES (8, 'BAIR Robot', '/bair_robot/softmotion30_44k/train', '2022-09-15 20:35:44', '机器人动作视频');
INSERT INTO `dataset` VALUES (9, 'JSJ Rainfall-Runoff', '巴塘2000-2018.CSV', '2022-09-16 08:53:36', '金沙江2000-2018降雨径流');

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
INSERT INTO `datasource` VALUES ('test2', 'jdbc:mysql://localhost:3306/test2?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8&zeroDateTimeBehavior=convertToNull', 'root', 'root', 'mysql');
INSERT INTO `datasource` VALUES ('sos', 'jdbc:postgresql://localhost:5432/sos?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=convertToNull', 'postgres', 'postgres', 'postgresql');
INSERT INTO `datasource` VALUES ('test3', 'jdbc:mysql://localhost:3306/test3?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=convertToNull', 'root', 'root', 'mysql');
INSERT INTO `datasource` VALUES ('try1', 'jdbc:mysql://localhost:3306/test2?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=convertToNull', 'root', 'root', 'mysql');
INSERT INTO `datasource` VALUES ('try2', 'jdbc:mysql://localhost:3306/test2?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=convertToNull', 'root', 'root', 'mysql');
INSERT INTO `datasource` VALUES ('try3', 'jdbc:mysql://localhost:3306/test2?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=convertToNull', 'root', 'root', 'mysql');
INSERT INTO `datasource` VALUES ('water', 'jdbc:postgresql://localhost:5432/sos?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=convertToNull', 'postgres', 'postgres', 'postgresql');
INSERT INTO `datasource` VALUES ('1111', 'jdbc:mysql://45.63.124.81:3306/test2?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=convertToNull', 'admin', 'admin123456', 'mysql');
INSERT INTO `datasource` VALUES ('111111', 'jdbc:mysql://45.63.124.81:3306/test3?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=convertToNull', 'admin', 'admin123456', 'mysql');
INSERT INTO `datasource` VALUES ('postSOS', 'jdbc:postgresql://172.27.166.211:5432/sos?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=convertToNull', 'root', 'root', 'postgresql');
INSERT INTO `datasource` VALUES ('pSOS', 'jdbc:postgresql://172.27.166.211:5432/sos?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=convertToNull', 'root', 'root', 'postgresql');
INSERT INTO `datasource` VALUES ('0814', 'jdbc:postgresql://172.27.166.211:5432/sos?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=convertToNull', 'postgres', 'postgres', 'postgresql');
INSERT INTO `datasource` VALUES ('pMy', 'jdbc:mysql://172.27.166.211:3306/stpam?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=convertToNull', 'root', 'root', 'mysql');
INSERT INTO `datasource` VALUES ('myhost', 'jdbc:mysql://172.27.195.33:3306/test3?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=convertToNull', 'root', 'root', 'mysql');
INSERT INTO `datasource` VALUES ('testtt', 'jdbc:mysql://localhost:3306/test?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=convertToNull', 'admin', 'admin123456', 'mysql');
INSERT INTO `datasource` VALUES ('benji', 'jdbc:mysql://172.27.71.157:3306/test3?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=convertToNull', 'root', 'root', 'mysql');
INSERT INTO `datasource` VALUES ('benji2', 'jdbc:mysql://172.27.71.157:3306/test3?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=convertToNull', 'lianjie', 'lianjie', 'mysql');
INSERT INTO `datasource` VALUES ('shiyishi', 'jdbc:mysql://172.27.195.33:3306/test3?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=convertToNull', 'lianjie', 'lianjie', 'mysql');
INSERT INTO `datasource` VALUES ('bendi3', 'jdbc:mysql://172.27.195.33:3306/test3?useSSL=false&useUnicode=true&characterEncoding=utf8mb4&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=convertToNull', 'lianjie', 'lianjie', 'mysql');
INSERT INTO `datasource` VALUES ('bendi4', 'jdbc:mysql://172.27.195.33:3306/stpam?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=convertToNull', 'lianjie', 'lianjie', 'mysql');
INSERT INTO `datasource` VALUES ('linux1', 'jdbc:mysql://localhost:3306/stpam?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=convertToNull', 'admin', 'admin123456', 'mysql');
INSERT INTO `datasource` VALUES ('bendi5', 'jdbc:mysql://172.27.195.33:3306/stpam?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=convertToNull', 'root', 'root', 'mysql');
INSERT INTO `datasource` VALUES ('bendi6', 'jdbc:mysql://172.27.195.33:3306/stpam?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=convertToNull', 'lianjie', 'lianjie', 'mysql');
INSERT INTO `datasource` VALUES ('111', 'jdbc:mysql://172.27.195.33:3306/stpam?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=convertToNull', 'lianjie', 'lianjie', 'mysql');
INSERT INTO `datasource` VALUES ('', 'jdbc:://:/?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=convertToNull', '', '', '');
INSERT INTO `datasource` VALUES ('admin', 'jdbc:mysql://localhost:3006/?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=convertToNull', '', '', 'mysql');

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
INSERT INTO `distributesetting` VALUES (0, 1, 'http://localhost:8080/computing-1', '2022-04-24 00:00:00');
INSERT INTO `distributesetting` VALUES (1, 2, 'http://localhost:8080/computing-2', '2022-04-24 00:00:00');

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
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

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
INSERT INTO `modelsetting` VALUES (0, 'STP-Net', '	\r\nhttp://localhost:8080/stp-net', '2022-04-25 00:00:00');
INSERT INTO `modelsetting` VALUES (2, 'RS-Net', '	\r\nhttp://localhost:8080/rs-net', '2022-04-25 00:00:00');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户密码',
  `role` tinyint NOT NULL COMMENT '用户身份（1是管理员，0是用户）',
  `mail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '邮箱',
  `enabled` tinyint(1) NULL DEFAULT NULL COMMENT '是否启用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (12, 'phw', '$2a$10$WpcEx8afSwvb9uBu4Nthj.Uy9aWKs2gEB.oTcbTJ2hTTVlBT0kivu', 0, 'phw', 1);
INSERT INTO `user` VALUES (15, 'admin', '$2a$10$D1ouyhHFlrHYzMkZzlF1YeEAVLZa4AJDLKWBTlAmIcb/OtiO5/use', 1, 'admin', 1);

-- ----------------------------
-- Table structure for water
-- ----------------------------
DROP TABLE IF EXISTS `water`;
CREATE TABLE `water`  (
  `date` datetime NULL DEFAULT NULL,
  `position` double NULL DEFAULT NULL,
  `flow` double NULL DEFAULT NULL,
  `warning` double NULL DEFAULT NULL,
  `positionNor` double NULL DEFAULT NULL,
  `flowNor` double NULL DEFAULT NULL,
  `warningNor` double NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of water
-- ----------------------------
INSERT INTO `water` VALUES ('2016-11-10 10:00:00', 51, 3000, 57.5, 0.3228, 0.3284, 0.3333);
INSERT INTO `water` VALUES ('2017-11-10 14:09:22', 53, 2989, 57.5, 0.3354, 0.3272, 0.3333);
INSERT INTO `water` VALUES ('2021-11-10 18:10:04', 54, 3145, 57.5, 0.3418, 0.3443, 0.3333);

SET FOREIGN_KEY_CHECKS = 1;
