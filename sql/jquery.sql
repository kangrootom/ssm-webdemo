/*
Navicat MySQL Data Transfer

Source Server         : con_1
Source Server Version : 50519
Source Host           : localhost:3306
Source Database       : jquery

Target Server Type    : MYSQL
Target Server Version : 50519
File Encoding         : 65001

Date: 2018-12-05 19:08:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `dept`
-- ----------------------------
DROP TABLE IF EXISTS `dept`;
CREATE TABLE `dept` (
  `deptno` int(11) NOT NULL AUTO_INCREMENT,
  `dname` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`deptno`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dept
-- ----------------------------
INSERT INTO `dept` VALUES ('1', '测试部');
INSERT INTO `dept` VALUES ('2', '运维部');
INSERT INTO `dept` VALUES ('3', '行政部');

-- ----------------------------
-- Table structure for `emp`
-- ----------------------------
DROP TABLE IF EXISTS `emp`;
CREATE TABLE `emp` (
  `empno` int(11) NOT NULL AUTO_INCREMENT,
  `ename` varchar(10) DEFAULT NULL,
  `job` varchar(10) DEFAULT NULL,
  `hiredate` date DEFAULT NULL,
  `sal` double(10,2) DEFAULT NULL,
  `comm` double(4,2) DEFAULT NULL,
  `mgr` int(11) DEFAULT NULL,
  `deptno` int(11) DEFAULT NULL,
  PRIMARY KEY (`empno`),
  KEY `fk_emp_dept` (`deptno`),
  CONSTRAINT `fk_emp_dept` FOREIGN KEY (`deptno`) REFERENCES `dept` (`deptno`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of emp
-- ----------------------------
INSERT INTO `emp` VALUES ('1', '范德萨', '测试工程师', '2015-12-28', '10000.00', '1111.00', '2', '1');
INSERT INTO `emp` VALUES ('2', '芙蓉', '测试经理', '2014-05-02', '20000.00', '3000.00', '5', '1');
INSERT INTO `emp` VALUES ('3', 'jack', '运维经理', '2012-01-31', '8000.00', '1.00', '5', '2');
INSERT INTO `emp` VALUES ('4', 'wre', 'hr', '2013-02-02', '8100.00', '1000.00', '5', '3');
INSERT INTO `emp` VALUES ('5', 'manager', '总经理', '2011-07-27', '50000000.00', null, null, '3');

-- ----------------------------
-- Table structure for `users`
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', 'tom');
INSERT INTO `users` VALUES ('2', 'jack');
INSERT INTO `users` VALUES ('3', 'mack');
INSERT INTO `users` VALUES ('4', '特务');
INSERT INTO `users` VALUES ('5', '发多少');

-- ----------------------------
-- Procedure structure for `simpleproc`
-- ----------------------------
DROP PROCEDURE IF EXISTS `simpleproc`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `simpleproc`(out count int)
begin
	select count(id) into count from users;
end
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `hello`
-- ----------------------------
DROP FUNCTION IF EXISTS `hello`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `hello`(s char(20)) RETURNS char(50) CHARSET utf8
return concat('hello,',s,'!')
;;
DELIMITER ;
