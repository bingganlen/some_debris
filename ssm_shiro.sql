/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : ssm_shiro

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2020-08-17 10:17:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_permissions
-- ----------------------------
DROP TABLE IF EXISTS `sys_permissions`;
CREATE TABLE `sys_permissions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `permission` varchar(100) DEFAULT NULL COMMENT '权限编号',
  `description` varchar(100) DEFAULT NULL COMMENT '权限描述',
  `rid` bigint(20) DEFAULT NULL COMMENT '此权限关联角色的id',
  `available` tinyint(1) DEFAULT '0' COMMENT '是否锁定',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_sys_permissions_permission` (`permission`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8 COMMENT='用户与角色间的依赖关系   其中用户表与角色表是一对多的关系，一个用户可以拥有多个角色。';

-- ----------------------------
-- Records of sys_permissions
-- ----------------------------
INSERT INTO `sys_permissions` VALUES ('31', 'resource:create', '用户新增', '23', '0');
INSERT INTO `sys_permissions` VALUES ('32', 'user:update', '用户修改', '23', '0');
INSERT INTO `sys_permissions` VALUES ('33', 'user:delete', '用户删除', '23', '0');
INSERT INTO `sys_permissions` VALUES ('34', 'user:view', '用户查看', '23', '0');
INSERT INTO `sys_permissions` VALUES ('35', 'role:update', '角色更新', '21', '0');
INSERT INTO `sys_permissions` VALUES ('36', 'role:delete', '角色删除', '21', '0');
INSERT INTO `sys_permissions` VALUES ('37', 'role:create', '角色创建', '21', '0');
INSERT INTO `sys_permissions` VALUES ('38', 'role:view', '角色查看', '21', '0');
INSERT INTO `sys_permissions` VALUES ('39', 'permission:delete', '权限删除', '21', '0');
INSERT INTO `sys_permissions` VALUES ('40', 'permission:create', '权限创建', '21', '0');
INSERT INTO `sys_permissions` VALUES ('41', 'permission:view', '权限查看', '21', '0');
INSERT INTO `sys_permissions` VALUES ('42', 'project:manage', '项目管理', '27', '0');
INSERT INTO `sys_permissions` VALUES ('43', 'project:distribution', '项目任务分配', '27', '0');
INSERT INTO `sys_permissions` VALUES ('44', 'project:develop', '项目开发', '28', '0');
INSERT INTO `sys_permissions` VALUES ('45', 'project:maintain', '项目维护', '28', '0');
INSERT INTO `sys_permissions` VALUES ('46', 'security:maintain', '安全维护', '30', '0');
INSERT INTO `sys_permissions` VALUES ('47', 'security:develop', '安全功能设计', '30', '0');
INSERT INTO `sys_permissions` VALUES ('48', 'security:test', '安全测试', '31', '0');
INSERT INTO `sys_permissions` VALUES ('49', 'security:bug-test', 'BUG检测', '31', '0');

-- ----------------------------
-- Table structure for sys_roles
-- ----------------------------
DROP TABLE IF EXISTS `sys_roles`;
CREATE TABLE `sys_roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色编号',
  `role` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `description` varchar(100) DEFAULT NULL COMMENT '角色描述',
  `pid` bigint(20) DEFAULT NULL COMMENT '父节点',
  `available` tinyint(1) DEFAULT '0' COMMENT '是否锁定',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_sys_roles_role` (`role`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COMMENT='角色表中role角色名称一般为存储着类似user:create这种格式，Shiro在Realm中校验用户身份的时候会通过role这个字段值进行校验；description是此角色的描述信息，比如用户创建。\r\n其中pid表示父节点，就是说，当前的角色可能有上级节点，比如老师，这个角色可能就有父节点计科教师，如果存在父节点，这个字段值就是父级节点的ID，根据这个ID，在展示数据的时候就很方便的展示出其在哪个父节点下。';

-- ----------------------------
-- Records of sys_roles
-- ----------------------------
INSERT INTO `sys_roles` VALUES ('21', 'admin', '总经理', '0', '0');
INSERT INTO `sys_roles` VALUES ('22', 'personnel', '人事部', '0', '0');
INSERT INTO `sys_roles` VALUES ('23', 'personnel-resource', '人力资源部部长', '22', '0');
INSERT INTO `sys_roles` VALUES ('24', 'personnel-administration', '行政部部长', '22', '0');
INSERT INTO `sys_roles` VALUES ('26', 'technical', '技术部', '0', '0');
INSERT INTO `sys_roles` VALUES ('27', 'technical-development', '项目经理', '26', '0');
INSERT INTO `sys_roles` VALUES ('28', 'technical-maintenance', '项目组组长', '26', '0');
INSERT INTO `sys_roles` VALUES ('29', 'security', '安全部', '0', '0');
INSERT INTO `sys_roles` VALUES ('30', 'security-net', '网络安全部负责人', '29', '0');
INSERT INTO `sys_roles` VALUES ('31', 'security-test', '项目安全测试人员', '29', '0');

-- ----------------------------
-- Table structure for sys_roles_permissions
-- ----------------------------
DROP TABLE IF EXISTS `sys_roles_permissions`;
CREATE TABLE `sys_roles_permissions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色编号',
  `permission_id` bigint(20) DEFAULT NULL COMMENT '权限编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_roles_permissions
-- ----------------------------
INSERT INTO `sys_roles_permissions` VALUES ('1', '21', '35');
INSERT INTO `sys_roles_permissions` VALUES ('2', '21', '36');
INSERT INTO `sys_roles_permissions` VALUES ('3', '21', '37');
INSERT INTO `sys_roles_permissions` VALUES ('4', '21', '38');
INSERT INTO `sys_roles_permissions` VALUES ('5', '21', '39');
INSERT INTO `sys_roles_permissions` VALUES ('6', '21', '40');
INSERT INTO `sys_roles_permissions` VALUES ('7', '21', '41');
INSERT INTO `sys_roles_permissions` VALUES ('8', '23', '31');
INSERT INTO `sys_roles_permissions` VALUES ('9', '23', '32');
INSERT INTO `sys_roles_permissions` VALUES ('10', '23', '33');
INSERT INTO `sys_roles_permissions` VALUES ('11', '23', '34');
INSERT INTO `sys_roles_permissions` VALUES ('12', '27', '42');
INSERT INTO `sys_roles_permissions` VALUES ('13', '27', '43');
INSERT INTO `sys_roles_permissions` VALUES ('14', '28', '44');
INSERT INTO `sys_roles_permissions` VALUES ('15', '28', '45');
INSERT INTO `sys_roles_permissions` VALUES ('16', '30', '46');
INSERT INTO `sys_roles_permissions` VALUES ('17', '30', '47');
INSERT INTO `sys_roles_permissions` VALUES ('18', '31', '48');
INSERT INTO `sys_roles_permissions` VALUES ('19', '31', '49');

-- ----------------------------
-- Table structure for sys_users
-- ----------------------------
DROP TABLE IF EXISTS `sys_users`;
CREATE TABLE `sys_users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `username` varchar(100) DEFAULT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `salt` varchar(100) DEFAULT NULL COMMENT '盐值',
  `role_id` varchar(50) DEFAULT NULL COMMENT '角色列表',
  `locked` tinyint(1) DEFAULT '0' COMMENT '是否锁定',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_sys_users_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='admin  -->  123  密码是123\r\n用户表中至少包含以上的字段，主键id、用户名username、密码password、盐值salt（因为密码是经过Shiro加密的，需要通过盐值校验，由Shiro生成，不需要用户手动填写）、角色列表roleId（这个字段不是必须的，仅实现在展示用户信息的时候能同时展示用户当前角色）、是否锁定locked（决定当前账户是否是锁定的）。\r\n创建新的用户，仅需要输入用户名和密码即可，盐值由Shiro生成，角色列表和是否锁定都可以在后期管理。\r\n\r\n其中是否锁定字段类型为tinyint(1)，设置这种类型，数据库中实际存储的是int类型数据，一般是0和1，在使用Mybatis取这个字段的数据时，Mybatis会自动将tinyint(1)字段值为0的转换成false，将字段值为1以上的转换为true。';

-- ----------------------------
-- Records of sys_users
-- ----------------------------
INSERT INTO `sys_users` VALUES ('1', 'admin', '95657d3e3052fb39d70d610c70a9a575', '87cc486c53b49f72f5b96bb55d93bc7f', '超级管理员', '0');
INSERT INTO `sys_users` VALUES ('2', 'tycoding', '7de3848c92d39e98f7c74139f1a079d7', '6478ccf88032592fe9396f008408400b', '普通管理员', '0');
INSERT INTO `sys_users` VALUES ('3', '涂陌', '7247c372a4aae00c2f78239739384c0b', 'f0a35a4d99e23ad4a59616bd4d8eea02', '普通用户', '0');

-- ----------------------------
-- Table structure for sys_users_roles
-- ----------------------------
DROP TABLE IF EXISTS `sys_users_roles`;
CREATE TABLE `sys_users_roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户编号',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_users_roles
-- ----------------------------
INSERT INTO `sys_users_roles` VALUES ('1', '1', '21');
INSERT INTO `sys_users_roles` VALUES ('2', '2', '27');
INSERT INTO `sys_users_roles` VALUES ('3', '2', '30');
INSERT INTO `sys_users_roles` VALUES ('4', '3', '33');
INSERT INTO `sys_users_roles` VALUES ('5', '3', '34');
