/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50171
Source Host           : localhost:3306
Source Database       : netdisk

Target Server Type    : MYSQL
Target Server Version : 50171
File Encoding         : 65001

Date: 2016-09-13 10:05:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for hfile
-- ----------------------------
DROP TABLE IF EXISTS `hfile`;
CREATE TABLE `hfile` (
  `fid` varchar(64) NOT NULL DEFAULT '' COMMENT '文件UUID',
  `pid` varchar(64) DEFAULT NULL,
  `fpath` varchar(1024) DEFAULT NULL,
  `fname` varchar(64) DEFAULT NULL,
  `fmd5` varchar(64) DEFAULT NULL,
  `fsize` float DEFAULT NULL,
  `ftype` int(11) DEFAULT NULL COMMENT '0-dir,1-file',
  `addtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`fid`),
  KEY `idx_fmd5` (`fmd5`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hfile
-- ----------------------------
INSERT INTO `hfile` VALUES ('3925230a-39d6-48d5-ab8c-5d93a449ab03', '4f17d693-8ce9-470b-92e5-c7520417dd27', '/tt/940bf6f8-7598-433e-9234-5b987789521c_artifacts.xml', 'artifacts.xml', '09ed40a638246cebe2bf99e3ae8a303d', '125178', '1', '2015-01-08 15:12:11');
INSERT INTO `hfile` VALUES ('4edbfb6f-f967-473f-ab42-45678a740162', '0', '/t1', 't1', '-1', '-1', '0', '2016-09-12 10:43:06');
INSERT INTO `hfile` VALUES ('4f17d693-8ce9-470b-92e5-c7520417dd27', '0', '/tt', 'tt', '-1', '-1', '0', '2015-01-08 15:11:29');
INSERT INTO `hfile` VALUES ('9eb92ee4-891c-4056-8a45-c61769ef3b31', 'a3a1d874-425f-4104-a410-f20224e39d8d', '/tt/yy/b07d5c52-700d-40eb-8303-03a153a48e6a_eclipse.ini', 'eclipse.ini', '9f8148eb2e903e8ad02eb23c8c052e7e', '477', '1', '2015-01-08 15:43:32');
INSERT INTO `hfile` VALUES ('a38db1ad-9361-44f0-96ce-f3275069eb66', '4edbfb6f-f967-473f-ab42-45678a740162', '/t1/37d2d7dd-7b5d-4f1e-9e06-3d08297e26d7_Chrysanthemum.jpg', 'Chrysanthemum.jpg', '076e3caed758a1c18c91a0e9cae3368f', '879394', '1', '2016-09-12 11:17:53');
INSERT INTO `hfile` VALUES ('a3a1d874-425f-4104-a410-f20224e39d8d', '4f17d693-8ce9-470b-92e5-c7520417dd27', '/tt/yy', 'yy', '-1', '-1', '0', '2015-01-08 15:13:03');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userid` varchar(64) NOT NULL,
  `username` varchar(32) NOT NULL,
  `password` varchar(64) NOT NULL,
  `addTime` datetime NOT NULL,
  `logTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `salt` varchar(64) NOT NULL,
  `status` int(11) NOT NULL,
  `logIp` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`userid`),
  UNIQUE KEY `idx_uq_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1a0792aa-3080-426a-967d-24ad2ed9f2c6', 'kingsj', '5vUJnPjr/KaHxx/B9pOGS+CwFmaPNzxCpH8VLznO4NQ=', '2014-12-30 17:28:27', null, '1a0792aa-3080-426a-967d-24ad2ed9f2c6', '1', null);

-- ----------------------------
-- Table structure for user_file
-- ----------------------------
DROP TABLE IF EXISTS `user_file`;
CREATE TABLE `user_file` (
  `id` varchar(64) NOT NULL DEFAULT '',
  `userid` varchar(64) DEFAULT NULL,
  `fid` varchar(64) DEFAULT '' COMMENT '文件ID',
  `addTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_file
-- ----------------------------
INSERT INTO `user_file` VALUES ('0dbcfea8-a9db-4e60-9d82-6184c64b6cc8', '1a0792aa-3080-426a-967d-24ad2ed9f2c6', '9eb92ee4-891c-4056-8a45-c61769ef3b31', '2015-01-08 15:43:32');
INSERT INTO `user_file` VALUES ('54f2dd99-33b0-4c56-b977-ea40707c8de2', '1a0792aa-3080-426a-967d-24ad2ed9f2c6', '4edbfb6f-f967-473f-ab42-45678a740162', '2016-09-12 10:43:06');
INSERT INTO `user_file` VALUES ('72b3ebe0-e3b2-457b-ad10-41bd8cf7b9c2', '1a0792aa-3080-426a-967d-24ad2ed9f2c6', '4f17d693-8ce9-470b-92e5-c7520417dd27', '2015-01-08 15:11:29');
INSERT INTO `user_file` VALUES ('96b01443-cc66-430c-83cf-991ea8c2014c', '1a0792aa-3080-426a-967d-24ad2ed9f2c6', '3925230a-39d6-48d5-ab8c-5d93a449ab03', '2015-01-08 15:12:11');
INSERT INTO `user_file` VALUES ('ab9fb455-7d34-4bd2-891b-65b08b022a77', '1a0792aa-3080-426a-967d-24ad2ed9f2c6', 'a3a1d874-425f-4104-a410-f20224e39d8d', '2015-01-08 15:13:03');
INSERT INTO `user_file` VALUES ('dfbcc7aa-d486-4fec-99ed-56a06019f7e7', '1a0792aa-3080-426a-967d-24ad2ed9f2c6', 'a38db1ad-9361-44f0-96ce-f3275069eb66', '2016-09-12 11:17:53');

-- ----------------------------
-- Table structure for user_param
-- ----------------------------
DROP TABLE IF EXISTS `user_param`;
CREATE TABLE `user_param` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` varchar(64) DEFAULT NULL,
  `paramKey` varchar(64) DEFAULT NULL,
  `paramVal` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_param
-- ----------------------------
INSERT INTO `user_param` VALUES ('1', '1a0792aa-3080-426a-967d-24ad2ed9f2c6', 'DEFAULT_SAVE_PATH', 'E:\\');
