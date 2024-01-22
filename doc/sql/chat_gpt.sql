/*
 Navicat Premium Data Transfer

 Source Server         : [生产]同城读写
 Source Server Type    : MySQL
 Source Server Version : 50730
 Source Host           : huaian.rds.dinggehuo.com:33060
 Source Schema         : chat_gpt

 Target Server Type    : MySQL
 Target Server Version : 50730
 File Encoding         : 65001

 Date: 16/12/2023 13:31:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for gpt_agreement
-- ----------------------------
DROP TABLE IF EXISTS `gpt_agreement`;
CREATE TABLE `gpt_agreement` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `title` varchar(250) NOT NULL DEFAULT '' COMMENT '标题',
  `type` smallint(6) DEFAULT '0' COMMENT '类型',
  `status` smallint(6) DEFAULT '0' COMMENT '状态 0 禁用 1 启用',
  `content` longtext COMMENT '内容',
  `deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='内容管理';

-- ----------------------------
-- Table structure for gpt_assistant
-- ----------------------------
DROP TABLE IF EXISTS `gpt_assistant`;
CREATE TABLE `gpt_assistant` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `title` varchar(250) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '角色名称',
  `icon` varchar(250) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '角色图标',
  `tag` varchar(250) NOT NULL DEFAULT '' COMMENT '标签',
  `main_model` smallint(6) DEFAULT '0' COMMENT '主模型',
  `description` varchar(512) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '角色描述',
  `first_message` varchar(512) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT 'AI打招呼',
  `system_prompt` varchar(512) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '系统提示词',
  `type_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '助手分类id',
  `sort` int(11) NOT NULL DEFAULT '1' COMMENT '排序',
  `status` smallint(6) NOT NULL DEFAULT '1' COMMENT '状态 0 禁用 1 启用',
  `deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `type_id` (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3382 DEFAULT CHARSET=utf8 COMMENT='AI助理功能';

-- ----------------------------
-- Table structure for gpt_assistant_type
-- ----------------------------
DROP TABLE IF EXISTS `gpt_assistant_type`;
CREATE TABLE `gpt_assistant_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '分类名称',
  `icon` varchar(128) NOT NULL DEFAULT '' COMMENT 'icon图标',
  `sort` int(1) NOT NULL DEFAULT '0' COMMENT '排序',
  `status` smallint(6) DEFAULT '1' COMMENT '状态 0 禁用 1 启用',
  `deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='助手分类';

-- ----------------------------
-- Table structure for gpt_chat
-- ----------------------------
DROP TABLE IF EXISTS `gpt_chat`;
CREATE TABLE `gpt_chat` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `chat_number` varchar(32) NOT NULL DEFAULT '' COMMENT '聊天编号',
  `assistant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '角色id',
  `uid` varchar(32) NOT NULL DEFAULT '' COMMENT '游客id',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `title` varchar(250) DEFAULT '' COMMENT '聊天摘要',
  `deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8 COMMENT='聊天摘要';

-- ----------------------------
-- Table structure for gpt_chat_message
-- ----------------------------
DROP TABLE IF EXISTS `gpt_chat_message`;
CREATE TABLE `gpt_chat_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `chat_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'chat_id',
  `message_id` varchar(64) NOT NULL DEFAULT '' COMMENT '消息id',
  `parent_message_id` varchar(128) NOT NULL DEFAULT '' COMMENT '回复消息id',
  `model` varchar(64) NOT NULL DEFAULT '' COMMENT '模型',
  `model_version` varchar(64) NOT NULL DEFAULT '' COMMENT '模型版本',
  `content` longtext CHARACTER SET utf8mb4 COMMENT '消息内容',
  `role` varchar(250) NOT NULL DEFAULT '' COMMENT '角色',
  `finish_reason` varchar(250) DEFAULT '' COMMENT '结束原因',
  `status` smallint(6) DEFAULT '1' COMMENT '状态 1 回复中 2正常 3 失败',
  `app_key` varchar(128) NOT NULL DEFAULT '' COMMENT '使用的key',
  `used_tokens` bigint(20) NOT NULL DEFAULT '0' COMMENT '使用token',
  `response` longtext COMMENT '响应全文',
  `deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=689 DEFAULT CHARSET=utf8 COMMENT='对话消息';

-- ----------------------------
-- Table structure for gpt_comb
-- ----------------------------
DROP TABLE IF EXISTS `gpt_comb`;
CREATE TABLE `gpt_comb` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `title` varchar(250) NOT NULL DEFAULT '' COMMENT '套餐名称',
  `type` smallint(6) DEFAULT '1' COMMENT '套餐类型 1 次数 2 天数',
  `num` int(11) DEFAULT '0' COMMENT '包含次数',
  `origin_price` decimal(10,2) DEFAULT '0.00' COMMENT '原价',
  `price` decimal(10,2) DEFAULT '0.00' COMMENT '价格',
  `status` smallint(6) DEFAULT '1' COMMENT '状态 0 禁用 1 启用',
  `deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员套餐';

INSERT INTO `gpt_comb` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `title`, `type`, `num`, `origin_price`, `price`, `status`, `deleted`) VALUES (1, 'System', '2023-05-04 11:37:10', 'System', '2023-12-27 03:06:08', '体验套餐', 1, 20, 2.00, 0.01, 1, 0);
INSERT INTO `gpt_comb` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `title`, `type`, `num`, `origin_price`, `price`, `status`, `deleted`) VALUES (2, 'System', '2023-05-04 11:52:08', 'System', '2023-12-27 03:06:09', '20次包', 1, 20, 10.00, 5.00, 1, 0);
INSERT INTO `gpt_comb` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `title`, `type`, `num`, `origin_price`, `price`, `status`, `deleted`) VALUES (3, 'System', '2023-05-04 11:52:36', 'System', '2023-12-27 03:06:10', '100次30天包', 1, 100, 50.00, 20.00, 1, 0);
INSERT INTO `gpt_comb` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `title`, `type`, `num`, `origin_price`, `price`, `status`, `deleted`) VALUES (4, 'System', '2023-05-04 11:53:08', 'System', '2023-12-27 03:06:12', '200次季度包', 1, 200, 100.00, 39.90, 1, 0);
INSERT INTO `gpt_comb` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `title`, `type`, `num`, `origin_price`, `price`, `status`, `deleted`) VALUES (5, 'System', '2023-05-04 11:53:36', 'System', '2023-12-27 03:06:14', '500次全年包', 1, 5000, 199.99, 99.99, 1, 0);

-- ----------------------------
-- Table structure for gpt_model
-- ----------------------------
DROP TABLE IF EXISTS `gpt_model`;
CREATE TABLE `gpt_model` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '模型名称',
  `icon` varchar(255) NOT NULL DEFAULT '' COMMENT '模型logo',
  `model` varchar(32) NOT NULL DEFAULT '' COMMENT '模型名称',
  `version` varchar(64) NOT NULL DEFAULT '' COMMENT '模型版本',
  `status` smallint(6) NOT NULL DEFAULT '1' COMMENT '状态 0 禁用 1 启用',
  `deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='大模型信息';

INSERT INTO `gpt_model` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `model`, `version`, `status`, `deleted`) VALUES (1, 'System', '2023-12-01 01:23:51', 'System', '2023-12-01 05:39:35', 'ChatGPT', '', 'CHAT_GPT', 'gpt-3.5-turbo-0613', 1, 0);
INSERT INTO `gpt_model` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `model`, `version`, `status`, `deleted`) VALUES (2, 'System', '2023-12-01 01:24:43', 'System', '2023-12-01 01:27:06', '文心一言', '', 'WENXIN', 'ERNIE_Bot_turbo', 1, 0);
INSERT INTO `gpt_model` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `model`, `version`, `status`, `deleted`) VALUES (3, 'System', '2023-12-01 01:25:18', 'System', '2023-12-01 01:26:11', '通义千问', '', 'QIANWEN', 'qwen-turbo', 1, 0);
INSERT INTO `gpt_model` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `model`, `version`, `status`, `deleted`) VALUES (4, 'System', '2023-12-01 01:25:29', 'System', '2023-12-01 01:25:58', '讯飞星火', '', 'SPARK', 'v2.1', 1, 0);
INSERT INTO `gpt_model` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `model`, `version`, `status`, `deleted`) VALUES (5, 'System', '2023-12-27 00:38:20', 'System', '2023-12-27 00:39:28', '智谱清言', '', 'ZHIPU', 'chatGLM_6b_SSE', 1, 0);

-- ----------------------------
-- Table structure for gpt_openkey
-- ----------------------------
DROP TABLE IF EXISTS `gpt_openkey`;
CREATE TABLE `gpt_openkey` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `app_id` varchar(64) NOT NULL DEFAULT '' COMMENT 'appid',
  `app_key` varchar(64) NOT NULL DEFAULT '' COMMENT 'app key',
  `app_secret` varchar(128) NOT NULL DEFAULT '' COMMENT 'app密钥',
  `total_tokens` bigint(20) NOT NULL DEFAULT '0' COMMENT '总额度',
  `used_tokens` bigint(20) NOT NULL DEFAULT '0' COMMENT '已用额度',
  `surplus_tokens` bigint(20) NOT NULL DEFAULT '0' COMMENT '剩余token',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '状态 0 禁用 1 启用',
  `model` varchar(32) NOT NULL DEFAULT '' COMMENT '模型',
  `remark` varchar(250) DEFAULT '' COMMENT '备注',
  `deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `app_key` (`app_key`),
  KEY `model` (`model`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='openai token';

INSERT INTO `gpt_openkey` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `app_id`, `app_key`, `app_secret`, `total_tokens`, `used_tokens`, `surplus_tokens`, `status`, `model`, `remark`, `deleted`) VALUES (1, 'admin', '2023-05-04 10:10:05', 'System', '2023-10-27 01:43:51', '', '', '', 5, 1559, -1554, 0, 'CHAT_GPT', 'master@gmail.com', 0);
INSERT INTO `gpt_openkey` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `app_id`, `app_key`, `app_secret`, `total_tokens`, `used_tokens`, `surplus_tokens`, `status`, `model`, `remark`, `deleted`) VALUES (2, 'System', '2023-09-07 09:20:16', 'System', '2023-12-26 09:00:24', '', '', '', 5, 7496, -7491, 1, 'CHAT_GPT', 'master@gmail.com', 0);
INSERT INTO `gpt_openkey` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `app_id`, `app_key`, `app_secret`, `total_tokens`, `used_tokens`, `surplus_tokens`, `status`, `model`, `remark`, `deleted`) VALUES (3, 'System', '2023-09-07 09:20:48', 'System', '2023-09-12 08:07:11', '', '', '', 0, 1033, -1033, 1, 'WENXIN', '', 0);
INSERT INTO `gpt_openkey` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `app_id`, `app_key`, `app_secret`, `total_tokens`, `used_tokens`, `surplus_tokens`, `status`, `model`, `remark`, `deleted`) VALUES (4, 'System', '2023-09-12 02:24:22', 'System', '2023-10-18 09:29:08', '', '', '', 100000, 31727, 68273, 1, 'QIANWEN', '', 0);
INSERT INTO `gpt_openkey` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `app_id`, `app_key`, `app_secret`, `total_tokens`, `used_tokens`, `surplus_tokens`, `status`, `model`, `remark`, `deleted`) VALUES (5, 'System', '2023-09-12 02:25:27', 'admin', '2023-12-26 14:53:18', '', '', '', 1998204, 7479, 1990725, 1, 'SPARK', '', 0);
INSERT INTO `gpt_openkey` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `app_id`, `app_key`, `app_secret`, `total_tokens`, `used_tokens`, `surplus_tokens`, `status`, `model`, `remark`, `deleted`) VALUES (6, 'System', '2023-12-27 00:37:59', 'System', '2023-12-27 01:23:44', '', '', '', 0, 0, 0, 1, 'ZHIPU', '', 0);

-- ----------------------------
-- Table structure for gpt_order
-- ----------------------------
DROP TABLE IF EXISTS `gpt_order`;
CREATE TABLE `gpt_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `success_time` datetime DEFAULT NULL COMMENT '支付成功时间',
  `trade_no` varchar(250) NOT NULL DEFAULT '' COMMENT '订单号',
  `transaction_id` varchar(250) DEFAULT '' COMMENT '渠道交易ID',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '下单用户',
  `comb_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '购买套餐',
  `price` decimal(10,2) DEFAULT '0.00' COMMENT '价格',
  `chanel` smallint(6) DEFAULT '-1' COMMENT '支付渠道 1 微信小程序 2、微信公众号 3、微信h5 4、微信扫码',
  `status` smallint(6) DEFAULT '-1' COMMENT '订单状态 1 待支付 2 支付成功 3 支付超时 4 已退款',
  `deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单';

-- ----------------------------
-- Table structure for gpt_redemption
-- ----------------------------
DROP TABLE IF EXISTS `gpt_redemption`;
CREATE TABLE `gpt_redemption` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `code` varchar(250) DEFAULT '' COMMENT '兑换码',
  `num` int(11) DEFAULT '0' COMMENT '可兑次数',
  `user_id` bigint(20) DEFAULT '0' COMMENT '兑换人',
  `recieve_time` int(10) DEFAULT NULL COMMENT '兑换时间',
  `status` int(1) DEFAULT '0' COMMENT '状态 0 未兑换 1 已兑换',
  `deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='兑换码';

-- ----------------------------
-- Table structure for gpt_upload_config
-- ----------------------------
DROP TABLE IF EXISTS `gpt_upload_config`;
CREATE TABLE `gpt_upload_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `title` varchar(250) DEFAULT NULL COMMENT '配置名称',
  `upload_replace` tinyint(4) DEFAULT '0' COMMENT '覆盖同名文件',
  `thumb_status` tinyint(4) DEFAULT '0' COMMENT '缩图开关',
  `thumb_width` varchar(250) DEFAULT '' COMMENT '缩放宽度',
  `thumb_height` varchar(250) DEFAULT '' COMMENT '缩放高度',
  `thumb_type` smallint(6) DEFAULT '0' COMMENT '缩图方式',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='缩略图配置';

-- ----------------------------
-- Table structure for gpt_upload_file
-- ----------------------------
DROP TABLE IF EXISTS `gpt_upload_file`;
CREATE TABLE `gpt_upload_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `filepath` varchar(255) DEFAULT '' COMMENT '图片路径',
  `hash` varchar(32) DEFAULT '' COMMENT '文件hash值',
  `disk` varchar(20) DEFAULT '' COMMENT '存储方式',
  `type` tinyint(10) DEFAULT NULL COMMENT '文件类型',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `hash` (`hash`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='文件管理';

-- ----------------------------
-- Table structure for gpt_user
-- ----------------------------
DROP TABLE IF EXISTS `gpt_user`;
CREATE TABLE `gpt_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `login_time` datetime DEFAULT NULL COMMENT '登录时间',
  `uid` varchar(255) NOT NULL DEFAULT '' COMMENT 'Uuid\n',
  `name` varchar(250) NOT NULL DEFAULT '' COMMENT '姓名',
  `nick_name` varchar(250) NOT NULL DEFAULT '' COMMENT '昵称',
  `tel` varchar(250) NOT NULL DEFAULT '' COMMENT '手机号',
  `password` varchar(250) NOT NULL DEFAULT '' COMMENT '密码',
  `avatar` varchar(250) NOT NULL DEFAULT '' COMMENT '头像',
  `openid` varchar(250) NOT NULL DEFAULT '' COMMENT 'openid',
  `unionid` varchar(250) NOT NULL DEFAULT '' COMMENT 'unionid',
  `ip` varchar(250) NOT NULL DEFAULT '' COMMENT '登录ip',
  `num` int(11) DEFAULT '0' COMMENT '调用次数',
  `share_id` bigint(20) DEFAULT '0' COMMENT '邀请人',
  `type` int(1) DEFAULT '1' COMMENT '用户类型 1 微信小程序 2 公众号 3 手机号',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态 0 禁用 1 启用\n',
  `deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COMMENT='会员用户';

SET FOREIGN_KEY_CHECKS = 1;
