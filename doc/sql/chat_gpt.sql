/*
 Navicat Premium Data Transfer

 Source Server         : 文淼测试
 Source Server Type    : MySQL
 Source Server Version : 50740
 Source Host           : 47.122.65.70:3306
 Source Schema         : chat_gpt

 Target Server Type    : MySQL
 Target Server Version : 50740
 File Encoding         : 65001

 Date: 19/05/2024 21:57:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for gpt_chat
-- ----------------------------
DROP TABLE IF EXISTS `gpt_chat`;
CREATE TABLE `gpt_chat`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `chat_number` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '聊天编号',
  `assistant_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '角色id',
  `uid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '游客id',
  `user_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '用户id',
  `title` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '聊天摘要',
  `deleted` int(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '聊天摘要' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gpt_chat
-- ----------------------------
INSERT INTO `gpt_chat` VALUES (1, '13866668888', '2024-05-19 09:51:47', 'System', '2024-05-19 09:51:47', '1792010229769175040', 0, '', 1, '你好', 0);
INSERT INTO `gpt_chat` VALUES (2, '13866668888', '2024-05-19 10:05:18', 'System', '2024-05-19 10:05:18', '1792013629982375936', 0, '', 1, '你好', 0);

-- ----------------------------
-- Table structure for gpt_chat_message
-- ----------------------------
DROP TABLE IF EXISTS `gpt_chat_message`;
CREATE TABLE `gpt_chat_message`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `chat_id` bigint(20) NOT NULL DEFAULT 0 COMMENT 'chat_id',
  `message_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '消息id',
  `parent_message_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '回复消息id',
  `model` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '模型',
  `model_version` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '模型版本',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '消息内容',
  `content_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '内容类型：text：文字 image : 图片',
  `role` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '角色',
  `finish_reason` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '结束原因',
  `status` smallint(6) NULL DEFAULT 1 COMMENT '状态 1 回复中 2正常 3 失败',
  `app_key` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '使用的key',
  `used_tokens` bigint(20) NOT NULL DEFAULT 0 COMMENT '使用token',
  `response` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '响应全文',
  `deleted` int(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '对话消息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gpt_chat_message
-- ----------------------------
INSERT INTO `gpt_chat_message` VALUES (1, '13866668888', '2024-05-19 09:51:48', 1, '93a988fb-fe4d-4318-889e-9ff617af814d', '', 'SPARK', '', '你好', 'text', 'user', '', 3, '', 0, NULL, 0);
INSERT INTO `gpt_chat_message` VALUES (2, '13866668888', '2024-05-19 09:52:26', 1, 'da49c455-c326-492e-8857-446a5d2dd90b', '', 'SPARK', '', '你好', 'text', 'user', '', 1, '', 0, NULL, 0);
INSERT INTO `gpt_chat_message` VALUES (3, '13866668888', '2024-05-19 09:53:15', 1, '638a3b45-0c7f-4d8d-a819-e290b3e079ba', '', 'SPARK', '', '你好', 'text', 'user', '', 2, '', 0, NULL, 0);
INSERT INTO `gpt_chat_message` VALUES (4, 'System', '2024-05-19 09:53:17', 1, 'cht000b10f4@dx18f8e8d9c1fb8f3540', '638a3b45-0c7f-4d8d-a819-e290b3e079ba', 'SPARK', 'v2.1', '你好！有什么我可以帮助你的吗？', 'text', 'assistant', '[finish]', 2, '', 18, NULL, 0);
INSERT INTO `gpt_chat_message` VALUES (5, '13866668888', '2024-05-19 09:53:34', 1, 'fe9285b7-1e1f-485a-addb-c7eca58b83f0', '', 'SPARK', '', '你在哪里', 'text', 'user', '', 2, '', 0, NULL, 0);
INSERT INTO `gpt_chat_message` VALUES (6, 'System', '2024-05-19 09:53:36', 1, 'cht000b1443@dx18f8e8de35bb8f2540', 'fe9285b7-1e1f-485a-addb-c7eca58b83f0', 'SPARK', 'v2.1', '我是一个计算机程序，没有实际的物理位置。我可以在任何支持我的设备上运行，例如电脑、手机、平板电脑等等。只要您有网络连接，就可以与我进行交互和交流。', 'text', 'assistant', '[finish]', 2, '', 50, NULL, 0);
INSERT INTO `gpt_chat_message` VALUES (7, '13866668888', '2024-05-19 10:02:44', 1, '8641a437-5022-458e-88f9-c754573548ec', '', 'SPARK', '', '你在哪里', 'text', 'user', '', 2, '', 0, NULL, 0);
INSERT INTO `gpt_chat_message` VALUES (8, 'System', '2024-05-19 10:02:46', 1, 'cht000b11c8@dx18f8e9648c4b8f3540', '8641a437-5022-458e-88f9-c754573548ec', 'SPARK', 'v2.1', '我是一个人工智能语言模型没有实际的位置。我存在于计算机服务器中通过互联网与用户进行交互。', 'text', 'assistant', '[finish]', 2, '', 31, NULL, 0);
INSERT INTO `gpt_chat_message` VALUES (9, '13866668888', '2024-05-19 21:51:50', 2, '9dd764ed-e9f3-4407-80c1-850c721e14b4', '', 'SPARK', '', '你好', 'text', 'user', '', 2, '', 0, NULL, 0);
INSERT INTO `gpt_chat_message` VALUES (10, 'System', '2024-05-19 21:51:51', 2, 'cht000b7a33@dx18f911f7d9bb8f2540', '9dd764ed-e9f3-4407-80c1-850c721e14b4', 'SPARK', 'v2.1', '你好！有什么我可以帮助你的吗？', 'text', 'assistant', '[finish]', 2, '', 18, NULL, 0);

-- ----------------------------
-- Table structure for gpt_comb
-- ----------------------------
DROP TABLE IF EXISTS `gpt_comb`;
CREATE TABLE `gpt_comb`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `title` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '套餐名称',
  `type` smallint(6) NULL DEFAULT 1 COMMENT '套餐类型 1 次数 2 天数',
  `num` int(11) NULL DEFAULT 0 COMMENT '包含次数',
  `origin_price` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '原价',
  `price` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '价格',
  `status` smallint(6) NULL DEFAULT 1 COMMENT '状态 0 禁用 1 启用',
  `deleted` int(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '会员套餐' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gpt_comb
-- ----------------------------
INSERT INTO `gpt_comb` VALUES (1, 'System', '2023-05-04 11:37:10', 'System', '2023-12-27 03:06:08', '体验套餐', 1, 20, 2.00, 0.01, 1, 0);
INSERT INTO `gpt_comb` VALUES (2, 'System', '2023-05-04 11:52:08', 'System', '2023-12-27 03:06:09', '20次包', 1, 20, 10.00, 5.00, 1, 0);
INSERT INTO `gpt_comb` VALUES (3, 'System', '2023-05-04 11:52:36', 'System', '2023-12-27 03:06:10', '100次30天包', 1, 100, 50.00, 20.00, 1, 0);
INSERT INTO `gpt_comb` VALUES (4, 'System', '2023-05-04 11:53:08', 'System', '2023-12-27 03:06:12', '200次季度包', 1, 200, 100.00, 39.90, 1, 0);
INSERT INTO `gpt_comb` VALUES (5, 'System', '2023-05-04 11:53:36', 'admin', '2024-05-19 09:31:28', '500次全年包', 1, 5000, 199.99, 99.99, 1, 0);

-- ----------------------------
-- Table structure for gpt_model
-- ----------------------------
DROP TABLE IF EXISTS `gpt_model`;
CREATE TABLE `gpt_model`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '模型名称',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '模型logo',
  `model` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '模型名称',
  `version` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '模型版本',
  `status` smallint(6) NOT NULL DEFAULT 1 COMMENT '状态 0 禁用 1 启用',
  `deleted` int(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '大模型信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gpt_model
-- ----------------------------
INSERT INTO `gpt_model` VALUES (1, 'System', '2023-12-01 01:23:51', 'System', '2024-05-19 09:28:03', 'ChatGPT', '', 'CHAT_GPT', 'gpt-3.5-turbo-0613', 0, 1);
INSERT INTO `gpt_model` VALUES (2, 'System', '2023-12-01 01:24:43', 'System', '2024-05-19 09:28:01', '文心一言', '', 'WENXIN', 'ERNIE_Bot_turbo', 0, 1);
INSERT INTO `gpt_model` VALUES (3, 'System', '2023-12-01 01:25:18', 'System', '2024-05-19 09:27:58', '通义千问', '', 'QIANWEN', 'qwen-turbo', 0, 1);
INSERT INTO `gpt_model` VALUES (4, 'System', '2023-12-01 01:25:29', 'System', '2024-05-19 09:53:07', '讯飞星火', '', 'SPARK', 'v2.1', 1, 0);
INSERT INTO `gpt_model` VALUES (5, 'System', '2023-12-27 00:38:20', 'admin', '2024-05-19 09:27:55', '智谱清言', '', 'ZHIPU', 'chatGLM_6b_SSE', 0, 1);

-- ----------------------------
-- Table structure for gpt_openkey
-- ----------------------------
DROP TABLE IF EXISTS `gpt_openkey`;
CREATE TABLE `gpt_openkey`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `app_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'appid',
  `app_key` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'app key对应openai的token',
  `app_secret` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'app密钥',
  `total_tokens` bigint(20) NOT NULL DEFAULT 0 COMMENT '总额度',
  `used_tokens` bigint(20) NOT NULL DEFAULT 0 COMMENT '已用额度',
  `surplus_tokens` bigint(20) NOT NULL DEFAULT 0 COMMENT '剩余token',
  `status` int(1) NOT NULL DEFAULT 1 COMMENT '状态 0 禁用 1 启用',
  `model` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '模型',
  `remark` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注',
  `deleted` int(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `app_key`(`app_key`) USING BTREE,
  INDEX `model`(`model`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'openai token' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gpt_openkey
-- ----------------------------
INSERT INTO `gpt_openkey` VALUES (1, 'admin', '2023-05-04 10:10:05', 'System', '2024-05-19 09:31:15', '', '', '', 100000, 0, 0, 0, 'CHAT_GPT', 'master@gmail.com', 1);
INSERT INTO `gpt_openkey` VALUES (2, 'System', '2023-09-07 09:20:48', 'System', '2024-05-19 09:31:13', '', '', '', 100000, 0, 0, 0, 'WENXIN', '', 1);
INSERT INTO `gpt_openkey` VALUES (3, 'System', '2023-09-12 02:24:22', 'System', '2024-05-19 09:31:05', '', '', '', 100000, 0, 0, 0, 'QIANWEN', '', 1);
INSERT INTO `gpt_openkey` VALUES (4, 'System', '2023-09-12 02:25:27', 'master', '2024-05-19 09:50:31', '2775045a', '0dc5e7198f1353360edf2d18c625bc96', 'MDhlMWM1YmNhMjk4YzJkNjQ2Nzc3ZGQz', 100000, 0, 0, 1, 'SPARK', '', 0);
INSERT INTO `gpt_openkey` VALUES (5, 'System', '2023-12-27 00:37:59', 'System', '2024-05-19 09:31:01', '', '', '', 100000, 0, 0, 0, 'ZHIPU', '', 1);

-- ----------------------------
-- Table structure for gpt_order
-- ----------------------------
DROP TABLE IF EXISTS `gpt_order`;
CREATE TABLE `gpt_order`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `success_time` datetime(0) NULL DEFAULT NULL COMMENT '支付成功时间',
  `trade_no` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '订单号',
  `transaction_id` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '渠道交易ID',
  `user_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '下单用户',
  `comb_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '购买套餐',
  `price` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '价格',
  `chanel` smallint(6) NULL DEFAULT -1 COMMENT '支付渠道 1 微信小程序 2、微信公众号 3、微信h5 4、微信扫码',
  `status` smallint(6) NULL DEFAULT -1 COMMENT '订单状态 1 待支付 2 支付成功 3 支付超时 4 已退款',
  `deleted` int(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gpt_order
-- ----------------------------
INSERT INTO `gpt_order` VALUES (1, '13866668888', '2024-05-19 09:20:08', 'System', '2024-05-19 09:39:40', '2024-05-19 09:20:08', '20240519092007179200226291941301', '', 1, 1, 0.01, 3, 2, 0);
INSERT INTO `gpt_order` VALUES (2, '13866668888', '2024-05-19 09:20:23', 'System', '2024-05-19 09:39:40', '2024-05-19 09:20:23', '20240519092023179200232690771511', '', 1, 1, 0.01, 3, 2, 0);
INSERT INTO `gpt_order` VALUES (3, '13866668888', '2024-05-19 09:20:27', 'System', '2024-05-19 09:39:40', '2024-05-19 09:20:27', '20240519092027179200234476286731', '', 1, 5, 99.99, 3, 2, 0);
INSERT INTO `gpt_order` VALUES (4, '13866668888', '2024-05-19 09:20:34', 'System', '2024-05-19 09:39:40', '2024-05-19 09:20:34', '20240519092033179200237166513361', '', 1, 1, 0.01, 3, 2, 0);
INSERT INTO `gpt_order` VALUES (5, '13866668888', '2024-05-19 09:22:08', 'System', '2024-05-19 09:39:40', '2024-05-19 09:22:08', '20240519092208179200276802686111', '', 1, 1, 0.01, 3, 2, 0);
INSERT INTO `gpt_order` VALUES (6, '13866668888', '2024-05-19 09:25:03', 'System', '2024-05-19 09:39:40', '2024-05-19 09:25:03', '20240519092502179200349917793481', '', 1, 1, 0.01, 3, 2, 0);
INSERT INTO `gpt_order` VALUES (7, '13866668888', '2024-05-19 09:25:33', 'System', '2024-05-19 09:39:40', '2024-05-19 09:25:33', '20240519092533179200362706226321', '', 1, 2, 5.00, 3, 2, 0);
INSERT INTO `gpt_order` VALUES (8, '13866668888', '2024-05-19 09:25:45', 'System', '2024-05-19 09:39:40', '2024-05-19 09:25:45', '20240519092544179200367621950631', '', 1, 1, 0.01, 3, 2, 0);
INSERT INTO `gpt_order` VALUES (9, '13866668888', '2024-05-19 10:05:30', 'System', '2024-05-19 10:05:30', '2024-05-19 10:05:30', '20240519100530179201368051115671', '', 1, 1, 0.01, 3, 2, 0);
INSERT INTO `gpt_order` VALUES (10, '13866668888', '2024-05-19 19:15:43', 'System', '2024-05-19 19:15:43', '2024-05-19 19:15:43', '20240519191542179215214605408671', '', 1, 1, 0.01, 3, 2, 0);
INSERT INTO `gpt_order` VALUES (11, '13866668888', '2024-05-19 21:52:04', 'System', '2024-05-19 21:52:04', '2024-05-19 21:52:04', '20240519215204179219149458571241', '', 1, 2, 5.00, 3, 2, 0);

-- ----------------------------
-- Table structure for gpt_user
-- ----------------------------
DROP TABLE IF EXISTS `gpt_user`;
CREATE TABLE `gpt_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `login_time` datetime(0) NULL DEFAULT NULL COMMENT '登录时间',
  `uid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'Uuid\n',
  `name` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '姓名',
  `nick_name` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '昵称',
  `tel` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '手机号',
  `password` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '密码',
  `avatar` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '头像',
  `openid` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'openid',
  `unionid` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'unionid',
  `ip` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '登录ip',
  `context` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否开启上下文',
  `num` int(11) NULL DEFAULT 0 COMMENT '调用次数',
  `share_id` bigint(20) NULL DEFAULT 0 COMMENT '邀请人',
  `type` int(1) NULL DEFAULT 1 COMMENT '用户类型 1 微信小程序 2 公众号 3 手机号',
  `status` int(1) NOT NULL DEFAULT 0 COMMENT '状态 0 禁用 1 启用\n',
  `deleted` int(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '会员用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gpt_user
-- ----------------------------
INSERT INTO `gpt_user` VALUES (1, 'System', '2024-05-12 17:35:36', 'System', '2024-05-19 10:05:09', '2024-05-12 17:35:36', 'fb161d02-87ec-4651-8237-c6f0b6dff41c', '用户7JOwBoz2', '用户7JOwBoz2', '13866668888', '$2a$10$UYXDZq6AJo60Att/6E3dh.rLBdGgbUdV7IDILimFmXWzRKn6GgOJ.', '', '', '', '', 0, 59, 0, 3, 0, 0);
INSERT INTO `gpt_user` VALUES (2, 'System', '2024-05-13 14:58:45', 'System', '2024-05-13 14:58:45', '2024-05-13 14:58:45', '541a234d-71dd-424d-924d-5b07f3235b01', '测试', '测试', '13888888888', '$2a$10$TnbLDOiR9Wydzb50TIBwuuGRmQVRzc843pqU.a.KqEuwpogS7sdWO', '', '', '', '', 0, 0, 0, 3, 0, 0);

-- ----------------------------
-- Table structure for sys_base_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_base_config`;
CREATE TABLE `sys_base_config`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '配置键值',
  `data` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '配置内容',
  `deleted` int(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '基础配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_base_config
-- ----------------------------
INSERT INTO `sys_base_config` VALUES (1, 'admin', '2024-01-22 10:38:39', 'admin', '2024-05-19 09:29:54', 'baseInfo', '{\"contentSecurity\":0,\"copyright\":\"\",\"descrip\":\"言启未来\",\"keywords\":[\"通义千问\",\"ChatGPT\",\"文心一言\",\"智谱清言\"],\"siteTitle\":\"言启未来\",\"domain\":\"http://127.0.0.1\",\"proxyType\":1,\"siteLogo\":\"\"}', 0);
INSERT INTO `sys_base_config` VALUES (2, 'admin', '2024-01-22 10:39:48', 'admin', '2024-05-19 09:29:20', 'appInfo', '{\"h5Url\":\"\",\"isSms\":0,\"homeNotice\":\"\",\"isGptLimit\":0,\"isShare\":1,\"shareRewardNum\":\"20\",\"freeNum\":\"5\"}', 0);
INSERT INTO `sys_base_config` VALUES (3, 'admin', '2024-01-22 10:39:53', 'System', '2024-01-22 10:39:53', 'wxInfo', '{\"mpLogin\":0,\"mpPay\":0,\"maAppId\":\"xx\",\"maSecret\":\"xx\",\"mpAppId\":\"xx\",\"mpSecret\":\"xx\",\"mchNo\":\"xx\",\"v3Secret\":\"xx\"}', 0);
INSERT INTO `sys_base_config` VALUES (4, 'admin', '2024-01-22 10:40:04', 'admin', '2024-03-18 14:51:25', 'extraInfo', '{\"uploadSize\":\"100\",\"bucketName\":\"\",\"ossType\":1,\"registerTemplate\":\"\",\"endpoint\":\"\",\"smsKeySecret\":\"\",\"ossKeySecret\":\"\",\"ossKeyId\":\"\",\"smsAppId\":\"\",\"smsType\":0,\"smsRegionId\":\"\",\"smsKeyId\":\"\",\"smsSign\":\"\"}', 0);

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '参数键值',
  `type` int(1) NULL DEFAULT 1 COMMENT '系统内置（1是 0否）',
  `deleted` int(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0->未删除;1->已删除',
  `remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '参数配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (1, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '主框架页-默认皮肤样式名称', 'sys.index.skinName', 'skin-blue', 1, 0, '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow');
INSERT INTO `sys_config` VALUES (2, 'admin', '2022-07-08 15:40:23', 'admin', '2022-07-08 15:40:22', '用户管理-账号初始密码', 'sys.user.initPassword', '123456', 1, 0, '初始化密码 123456');
INSERT INTO `sys_config` VALUES (3, 'admin', '2022-07-08 15:40:23', 'admin', '2022-07-08 15:40:22', '主框架页-侧边栏主题', 'sys.index.sideTheme', 'theme-dark', 1, 0, '深色主题theme-dark，浅色主题theme-light');
INSERT INTO `sys_config` VALUES (4, 'admin', '2022-07-08 15:40:23', 'admin', '2022-07-15 21:00:22', '账号自助-验证码开关', 'sys.account.captchaOnOff', 'true', 1, 0, '是否开启验证码功能（true开启，false关闭）');
INSERT INTO `sys_config` VALUES (5, 'admin', '2022-07-08 15:40:23', 'admin', '2022-07-08 15:40:22', '账号自助-是否开启用户注册功能', 'sys.account.registerUser', 'false', 1, 0, '是否开启注册用户功能（true开启，false关闭）');
INSERT INTO `sys_config` VALUES (6, 'admin', '2022-08-02 17:31:37', 'admin', '2022-08-02 17:31:37', '账号自助-是否允许同时登录', 'sys.account.allLogin', 'false', 1, 0, '是否允许同时登录（true开启，false关闭）');
INSERT INTO `sys_config` VALUES (7, 'admin', '2022-08-16 15:58:54', 'admin', '2022-08-16 15:58:54', '即使通讯-是否开启IM模块', 'sys.module.IM', 'false', 1, 0, '是否开启IM模块（true开启，false关闭）');

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '部门名称',
  `sys_user_id` bigint(20) NULL DEFAULT 0 COMMENT '部门负责人id',
  `parent_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '父节点id',
  `tree_path` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '父节点路径 如: 1; 1-2; 1-2-3',
  `level` int(1) NOT NULL DEFAULT 1 COMMENT '节点等级',
  `sort` int(1) NOT NULL DEFAULT 0 COMMENT '排序',
  `status` int(1) NOT NULL DEFAULT 1 COMMENT '状态 0->禁用;1->启用',
  `deleted` int(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1, 'System', '2022-07-21 10:01:52', 'System', '2022-07-21 10:01:52', '集团', 1, 0, '1', 1, 0, 1, 0);

-- ----------------------------
-- Table structure for sys_dept_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept_role`;
CREATE TABLE `sys_dept_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `dept_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '部门id',
  `role_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '角色id',
  `status` int(1) NOT NULL DEFAULT 1 COMMENT '状态 0->禁用;1->启用',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept_role
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `label` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典标签',
  `value` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '表格回显样式',
  `is_default` int(1) NULL DEFAULT 0 COMMENT '是否默认 0->否 1->是',
  `sort` int(1) NULL DEFAULT 0 COMMENT '字典排序',
  `status` int(1) NULL DEFAULT 1 COMMENT '状态 0->禁用;1->启用',
  `deleted` int(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0->未删除;1->已删除',
  `remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `dict_type`(`dict_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 51 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (1, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '男', '1', 'sys_user_sex', '', '', 1, 1, 1, 0, '性别男');
INSERT INTO `sys_dict` VALUES (2, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '女', '0', 'sys_user_sex', '', '', 0, 2, 1, 0, '性别女');
INSERT INTO `sys_dict` VALUES (3, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-15 14:35:41', '未知', '-1', 'sys_user_sex', '', '', 0, 3, 1, 0, '性别未知');
INSERT INTO `sys_dict` VALUES (4, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-15 19:46:07', '显示', '0', 'sys_show_hide', '', 'primary', 1, 1, 1, 0, '显示菜单');
INSERT INTO `sys_dict` VALUES (5, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-15 19:46:11', '隐藏', '1', 'sys_show_hide', '', 'danger', 0, 2, 1, 0, '隐藏菜单');
INSERT INTO `sys_dict` VALUES (6, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-15 14:35:12', '启用', '1', 'sys_normal_disable', '', 'primary', 1, 1, 1, 0, '正常状态');
INSERT INTO `sys_dict` VALUES (7, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-15 14:35:24', '禁用', '0', 'sys_normal_disable', '', 'danger', 0, 2, 1, 0, '停用状态');
INSERT INTO `sys_dict` VALUES (8, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-15 19:46:29', '正常', '1', 'sys_job_status', '', 'primary', 1, 1, 1, 0, '正常状态');
INSERT INTO `sys_dict` VALUES (9, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-15 19:46:37', '暂停', '0', 'sys_job_status', '', 'danger', 0, 2, 1, 0, '停用状态');
INSERT INTO `sys_dict` VALUES (10, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '默认', 'DEFAULT', 'sys_job_group', '', '', 1, 1, 1, 0, '默认分组');
INSERT INTO `sys_dict` VALUES (11, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '系统', 'SYSTEM', 'sys_job_group', '', '', 0, 2, 1, 0, '系统分组');
INSERT INTO `sys_dict` VALUES (12, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-20 08:11:41', '是', '1', 'sys_yes_no', '', 'primary', 1, 1, 1, 0, '系统默认是');
INSERT INTO `sys_dict` VALUES (13, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-20 08:11:45', '否', '0', 'sys_yes_no', '', 'danger', 0, 2, 1, 0, '系统默认否');
INSERT INTO `sys_dict` VALUES (14, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '通知', '1', 'sys_notice_type', '', 'warning', 1, 1, 1, 0, '通知');
INSERT INTO `sys_dict` VALUES (15, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '公告', '2', 'sys_notice_type', '', 'success', 0, 2, 1, 0, '公告');
INSERT INTO `sys_dict` VALUES (16, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-15 19:46:44', '正常', '1', 'sys_notice_status', '', 'primary', 1, 1, 1, 0, '正常状态');
INSERT INTO `sys_dict` VALUES (17, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-15 19:46:48', '关闭', '0', 'sys_notice_status', '', 'danger', 0, 2, 1, 0, '关闭状态');
INSERT INTO `sys_dict` VALUES (18, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-21 00:29:45', '新增', 'INSERT', 'sys_oper_type', '', 'info', 0, 1, 1, 0, '新增操作');
INSERT INTO `sys_dict` VALUES (19, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-21 00:29:49', '修改', 'UPDATE', 'sys_oper_type', '', 'info', 0, 2, 1, 0, '修改操作');
INSERT INTO `sys_dict` VALUES (20, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-21 00:29:54', '删除', 'DELETE', 'sys_oper_type', '', 'danger', 0, 3, 1, 0, '删除操作');
INSERT INTO `sys_dict` VALUES (21, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-21 00:29:59', '授权', 'GRANT', 'sys_oper_type', '', 'primary', 0, 4, 1, 0, '授权操作');
INSERT INTO `sys_dict` VALUES (22, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-21 00:30:07', '导出', 'EXPORT', 'sys_oper_type', '', 'warning', 0, 5, 1, 0, '导出操作');
INSERT INTO `sys_dict` VALUES (23, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-21 00:30:13', '导入', 'IMPORT', 'sys_oper_type', '', 'warning', 0, 6, 1, 0, '导入操作');
INSERT INTO `sys_dict` VALUES (24, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-21 00:30:17', '强退', 'FORCE', 'sys_oper_type', '', 'danger', 0, 7, 1, 0, '强退操作');
INSERT INTO `sys_dict` VALUES (25, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-21 00:30:23', '生成代码', 'GENCODE', 'sys_oper_type', '', 'warning', 0, 8, 1, 0, '生成操作');
INSERT INTO `sys_dict` VALUES (26, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-21 00:30:28', '清空数据', 'CLEAN', 'sys_oper_type', '', 'danger', 0, 9, 1, 0, '清空操作');
INSERT INTO `sys_dict` VALUES (27, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-15 19:46:51', '成功', '1', 'sys_common_status', '', 'primary', 0, 1, 1, 0, '正常状态');
INSERT INTO `sys_dict` VALUES (28, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-15 19:46:55', '失败', '0', 'sys_common_status', '', 'danger', 0, 2, 1, 0, '停用状态');
INSERT INTO `sys_dict` VALUES (29, 'admin', '2023-05-04 11:25:27', 'System', '2023-05-04 11:25:27', '用户协议', '1', 'gpt_content_type', '', 'default', 0, 1, 1, 0, '');
INSERT INTO `sys_dict` VALUES (30, 'admin', '2023-05-04 11:25:35', 'admin', '2023-05-04 11:25:35', '隐私政策', '2', 'gpt_content_type', '', 'default', 0, 2, 1, 0, '');
INSERT INTO `sys_dict` VALUES (31, 'admin', '2023-05-04 11:25:45', 'System', '2023-05-04 11:25:45', '使用指南', '3', 'gpt_content_type', '', 'default', 0, 3, 1, 0, '');
INSERT INTO `sys_dict` VALUES (32, 'admin', '2023-05-04 11:32:45', 'System', '2023-05-04 11:32:45', '次数', '1', 'gpt_comb_type', '', 'default', 0, 1, 1, 0, '');
INSERT INTO `sys_dict` VALUES (33, 'admin', '2023-05-04 11:32:53', 'System', '2023-05-04 11:32:53', '天数', '2', 'gpt_comb_type', '', 'default', 0, 2, 1, 0, '');
INSERT INTO `sys_dict` VALUES (34, 'admin', '2023-05-06 11:55:48', 'System', '2023-05-06 11:55:48', '微信小程序', '1', 'gpt_member_type', '', 'primary', 0, 1, 1, 0, '');
INSERT INTO `sys_dict` VALUES (35, 'admin', '2023-05-06 11:56:00', 'System', '2023-05-06 11:56:00', '公众号', '2', 'gpt_member_type', '', 'success', 0, 2, 1, 0, '');
INSERT INTO `sys_dict` VALUES (36, 'admin', '2023-05-06 11:56:11', 'admin', '2023-05-06 11:56:11', '手机号', '3', 'gpt_member_type', '', 'warning', 0, 3, 1, 0, '');
INSERT INTO `sys_dict` VALUES (37, 'admin', '2023-05-06 11:57:41', 'System', '2023-05-06 11:57:41', '回复中', '1', 'gpt_chat_status', '', 'warning', 0, 1, 1, 0, '');
INSERT INTO `sys_dict` VALUES (38, 'admin', '2023-05-06 11:57:55', 'System', '2023-05-06 11:57:55', '回复成功', '2', 'gpt_chat_status', '', 'success', 0, 2, 1, 0, '');
INSERT INTO `sys_dict` VALUES (39, 'admin', '2023-05-06 11:58:07', 'System', '2023-05-06 11:58:07', '回复失败', '3', 'gpt_chat_status', '', 'danger', 0, 3, 1, 0, '');
INSERT INTO `sys_dict` VALUES (40, 'admin', '2023-09-06 15:33:28', 'admin', '2023-09-06 15:33:28', 'ChatGpt', 'CHAT_GPT', 'gpt_model_type', '', 'primary', 0, 1, 1, 0, '');
INSERT INTO `sys_dict` VALUES (41, 'admin', '2024-01-22 09:15:57', 'admin', '2024-01-22 01:45:58', '本地上传', '1', 'sys_oss_type', '', 'primary', 0, 1, 1, 0, '');
INSERT INTO `sys_dict` VALUES (42, 'admin', '2024-01-22 09:16:46', 'admin', '2024-01-22 01:46:06', '阿里OSS', '2', 'sys_oss_type', '', 'success', 0, 2, 1, 0, '');
INSERT INTO `sys_dict` VALUES (43, 'admin', '2024-01-22 09:17:06', 'System', '2024-01-22 01:46:06', '腾讯COS', '3', 'sys_oss_type', '', 'warning', 0, 3, 1, 0, '');
INSERT INTO `sys_dict` VALUES (44, 'admin', '2024-01-22 09:30:24', 'admin', '2024-01-22 09:30:24', '无', '0', 'sys_sms_type', '', 'info', 0, 1, 1, 0, '');
INSERT INTO `sys_dict` VALUES (45, 'admin', '2024-01-22 09:30:37', 'admin', '2024-01-22 09:30:37', '阿里云SMS', '1', 'sys_sms_type', '', 'success', 0, 1, 1, 0, '');
INSERT INTO `sys_dict` VALUES (46, 'admin', '2024-01-22 15:37:59', 'System', '2024-03-13 20:51:59', '腾讯云SMS', '2', 'sys_sms_type', '', 'success', 0, 2, 1, 0, '');
INSERT INTO `sys_dict` VALUES (47, 'admin', '2024-01-22 15:37:59', 'System', '2024-03-14 11:09:34', '文心一言', 'WENXIN', 'gpt_model_type', '', 'success', 0, 2, 1, 0, '');
INSERT INTO `sys_dict` VALUES (48, 'admin', '2024-01-22 15:38:17', 'System', '2024-03-14 11:09:37', '通义千问', 'QIANWEN', 'gpt_model_type', '', 'info', 0, 3, 1, 0, '');
INSERT INTO `sys_dict` VALUES (49, 'admin', '2024-01-22 15:38:34', 'System', '2024-03-14 11:09:39', '讯飞星火', 'SPARK', 'gpt_model_type', '', 'warning', 0, 4, 1, 0, '');
INSERT INTO `sys_dict` VALUES (50, 'admin', '2024-01-22 15:39:03', 'admin', '2024-03-14 11:09:44', '智谱清言', 'ZHIPU', 'gpt_model_type', '', 'danger', 0, 5, 1, 0, '');

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典名称',
  `type` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典类型',
  `status` int(1) NULL DEFAULT 0 COMMENT '状态 0->禁用;1->启用',
  `deleted` int(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `type`(`type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '用户性别', 'sys_user_sex', 1, 0);
INSERT INTO `sys_dict_type` VALUES (2, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '菜单状态', 'sys_show_hide', 1, 0);
INSERT INTO `sys_dict_type` VALUES (3, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '系统开关', 'sys_normal_disable', 1, 0);
INSERT INTO `sys_dict_type` VALUES (4, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '任务状态', 'sys_job_status', 1, 0);
INSERT INTO `sys_dict_type` VALUES (5, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '任务分组', 'sys_job_group', 1, 0);
INSERT INTO `sys_dict_type` VALUES (6, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '系统是否', 'sys_yes_no', 1, 0);
INSERT INTO `sys_dict_type` VALUES (7, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '通知类型', 'sys_notice_type', 1, 0);
INSERT INTO `sys_dict_type` VALUES (8, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '通知状态', 'sys_notice_status', 1, 0);
INSERT INTO `sys_dict_type` VALUES (9, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '操作类型', 'sys_oper_type', 1, 0);
INSERT INTO `sys_dict_type` VALUES (10, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '系统状态', 'sys_common_status', 1, 0);
INSERT INTO `sys_dict_type` VALUES (11, 'admin', '2023-05-04 11:24:39', 'System', '2023-05-04 11:24:39', '内容类型', 'gpt_content_type', 1, 0);
INSERT INTO `sys_dict_type` VALUES (12, 'admin', '2023-05-04 11:32:35', 'System', '2023-05-04 11:32:35', '套餐类型', 'gpt_comb_type', 1, 0);
INSERT INTO `sys_dict_type` VALUES (13, 'admin', '2023-05-06 11:55:35', 'System', '2023-05-06 11:55:35', '用户类型', 'gpt_member_type', 1, 0);
INSERT INTO `sys_dict_type` VALUES (14, 'admin', '2023-05-06 11:57:25', 'System', '2023-05-06 11:57:25', '聊天状态', 'gpt_chat_status', 1, 0);
INSERT INTO `sys_dict_type` VALUES (15, 'admin', '2023-09-06 15:32:46', 'admin', '2023-09-06 15:32:46', 'gpt模型类型', 'gpt_model_type', 1, 0);
INSERT INTO `sys_dict_type` VALUES (16, 'admin', '2024-01-22 09:15:45', 'admin', '2024-01-22 09:15:45', '上传类型', 'sys_oss_type', 1, 0);
INSERT INTO `sys_dict_type` VALUES (17, 'admin', '2024-01-22 09:29:29', 'System', '2024-01-22 09:29:29', '短信类型', 'sys_sms_type', 1, 0);

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `sys_user_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '操作人id',
  `fk_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '操作记录id',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '操作人',
  `ip` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '请求ip',
  `address` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '操作地址',
  `domain` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '域名',
  `browser` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '操作系统',
  `method` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '请求方法',
  `request_method` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '请求方式',
  `uri` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '接口名称',
  `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '操作模块',
  `business_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '操作类型',
  `operation` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '操作内容',
  `time` bigint(20) NOT NULL DEFAULT 0 COMMENT '请求耗时',
  `params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '请求参数',
  `result` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '执行结果',
  `deleted` int(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sys_user_id`(`sys_user_id`) USING BTREE,
  INDEX `fk_id`(`fk_id`) USING BTREE,
  INDEX `title`(`title`) USING BTREE,
  INDEX `business_type`(`business_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 107 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES (1, 'System', '2024-05-18 21:43:52', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/4', '资源模块', 'DELETE', '资源模块信息删除', 219, '[4]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (2, 'System', '2024-05-18 21:43:55', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/2', '资源模块', 'DELETE', '资源模块信息删除', 98, '[2]', '{\"msg\":\"菜单下存在子菜单，无法删除\",\"code\":600,\"success\":false}', 0);
INSERT INTO `sys_log` VALUES (3, 'System', '2024-05-18 21:43:59', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/109', '资源模块', 'DELETE', '资源模块信息删除', 225, '[109]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (4, 'System', '2024-05-18 21:44:05', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1056', '资源模块', 'DELETE', '资源模块信息删除', 224, '[1056]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (5, 'System', '2024-05-18 21:44:09', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/112', '资源模块', 'DELETE', '资源模块信息删除', 220, '[112]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (6, 'System', '2024-05-18 21:44:12', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/111', '资源模块', 'DELETE', '资源模块信息删除', 200, '[111]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (7, 'System', '2024-05-18 21:44:14', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/113', '资源模块', 'DELETE', '资源模块信息删除', 200, '[113]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (8, 'System', '2024-05-18 21:44:18', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/114', '资源模块', 'DELETE', '资源模块信息删除', 194, '[114]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (9, 'System', '2024-05-18 21:44:21', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/108', '资源模块', 'DELETE', '资源模块信息删除', 188, '[108]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (10, 'System', '2024-05-18 21:44:27', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1047', '资源模块', 'DELETE', '资源模块信息删除', 191, '[1047]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (11, 'System', '2024-05-18 21:44:29', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1048', '资源模块', 'DELETE', '资源模块信息删除', 194, '[1048]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (12, 'System', '2024-05-18 21:44:32', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1051', '资源模块', 'DELETE', '资源模块信息删除', 191, '[1051]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (13, 'System', '2024-05-18 21:44:47', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1055', '资源模块', 'DELETE', '资源模块信息删除', 205, '[1055]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (14, 'System', '2024-05-18 21:44:51', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1049', '资源模块', 'DELETE', '资源模块信息删除', 202, '[1049]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (15, 'System', '2024-05-18 21:44:54', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1052', '资源模块', 'DELETE', '资源模块信息删除', 197, '[1052]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (16, 'System', '2024-05-18 21:44:57', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1053', '资源模块', 'DELETE', '资源模块信息删除', 200, '[1053]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (17, 'System', '2024-05-18 21:45:01', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1050', '资源模块', 'DELETE', '资源模块信息删除', 190, '[1050]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (18, 'System', '2024-05-18 21:45:03', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1054', '资源模块', 'DELETE', '资源模块信息删除', 192, '[1054]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (19, 'System', '2024-05-18 21:45:07', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/110', '资源模块', 'DELETE', '资源模块信息删除', 204, '[110]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (20, 'System', '2024-05-18 21:45:10', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1057', '资源模块', 'DELETE', '资源模块信息删除', 194, '[1057]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (21, 'System', '2024-05-18 21:45:14', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1058', '资源模块', 'DELETE', '资源模块信息删除', 191, '[1058]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (22, 'System', '2024-05-18 21:45:17', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1059', '资源模块', 'DELETE', '资源模块信息删除', 193, '[1059]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (23, 'System', '2024-05-18 21:45:20', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1060', '资源模块', 'DELETE', '资源模块信息删除', 192, '[1060]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (24, 'System', '2024-05-18 21:45:25', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1061', '资源模块', 'DELETE', '资源模块信息删除', 192, '[1061]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (25, 'System', '2024-05-18 21:45:29', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1062', '资源模块', 'DELETE', '资源模块信息删除', 196, '[1062]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (26, 'System', '2024-05-18 21:45:32', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1063', '资源模块', 'DELETE', '资源模块信息删除', 196, '[1063]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (27, 'System', '2024-05-18 21:45:35', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/2', '资源模块', 'DELETE', '资源模块信息删除', 196, '[2]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (28, 'System', '2024-05-18 21:45:43', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/104', '资源模块', 'DELETE', '资源模块信息删除', 197, '[104]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (29, 'System', '2024-05-18 21:45:46', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1025', '资源模块', 'DELETE', '资源模块信息删除', 188, '[1025]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (30, 'System', '2024-05-18 21:45:50', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1026', '资源模块', 'DELETE', '资源模块信息删除', 198, '[1026]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (31, 'System', '2024-05-18 21:45:53', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1027', '资源模块', 'DELETE', '资源模块信息删除', 190, '[1027]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (32, 'System', '2024-05-18 21:45:58', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1028', '资源模块', 'DELETE', '资源模块信息删除', 197, '[1028]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (33, 'System', '2024-05-18 21:46:00', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1029', '资源模块', 'DELETE', '资源模块信息删除', 195, '[1029]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (34, 'System', '2024-05-18 21:46:12', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/107', '资源模块', 'DELETE', '资源模块信息删除', 200, '[107]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (35, 'System', '2024-05-18 21:46:17', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1042', '资源模块', 'DELETE', '资源模块信息删除', 196, '[1042]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (36, 'System', '2024-05-18 21:46:19', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1043', '资源模块', 'DELETE', '资源模块信息删除', 188, '[1043]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (37, 'System', '2024-05-18 21:46:22', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1044', '资源模块', 'DELETE', '资源模块信息删除', 194, '[1044]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (38, 'System', '2024-05-18 21:46:25', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1045', '资源模块', 'DELETE', '资源模块信息删除', 193, '[1045]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (39, 'System', '2024-05-18 21:46:29', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1046', '资源模块', 'DELETE', '资源模块信息删除', 194, '[1046]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (40, 'System', '2024-05-18 21:47:12', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1120', '资源模块', 'DELETE', '资源模块信息删除', 187, '[1120]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (41, 'System', '2024-05-18 21:47:16', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1121', '资源模块', 'DELETE', '资源模块信息删除', 188, '[1121]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (42, 'System', '2024-05-18 21:47:18', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1122', '资源模块', 'DELETE', '资源模块信息删除', 187, '[1122]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (43, 'System', '2024-05-18 21:47:20', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1124', '资源模块', 'DELETE', '资源模块信息删除', 181, '[1124]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (44, 'System', '2024-05-18 21:47:23', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1123', '资源模块', 'DELETE', '资源模块信息删除', 180, '[1123]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (45, 'System', '2024-05-18 21:47:25', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1125', '资源模块', 'DELETE', '资源模块信息删除', 184, '[1125]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (46, 'System', '2024-05-18 21:47:28', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1126', '资源模块', 'DELETE', '资源模块信息删除', 181, '[1126]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (47, 'System', '2024-05-18 21:47:31', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1082', '资源模块', 'DELETE', '资源模块信息删除', 179, '[1082]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (48, 'System', '2024-05-18 21:51:28', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1169', '资源模块', 'DELETE', '资源模块信息删除', 188, '[1169]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (49, 'System', '2024-05-18 21:51:30', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1172', '资源模块', 'DELETE', '资源模块信息删除', 195, '[1172]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (50, 'System', '2024-05-18 21:51:34', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1171', '资源模块', 'DELETE', '资源模块信息删除', 413, '[1171]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (51, 'System', '2024-05-18 21:55:16', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1071', '资源模块', 'DELETE', '资源模块信息删除', 203, '[1071]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (52, 'System', '2024-05-18 21:55:20', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1074', '资源模块', 'DELETE', '资源模块信息删除', 195, '[1074]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (53, 'System', '2024-05-18 21:55:24', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1077', '资源模块', 'DELETE', '资源模块信息删除', 190, '[1077]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (54, 'System', '2024-05-18 21:56:00', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1080', '资源模块', 'DELETE', '资源模块信息删除', 196, '[1080]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (55, 'root', '2024-05-18 21:56:10', 1, 1072, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.updateResource()', 'PUT', '/chat-master/resource', '资源模块', 'UPDATE', '资源模块信息修改', 155, '[{\"component\":\"gpt/chat/index\",\"hidden\":0,\"icon\":\"chart\",\"id\":1072,\"name\":\"聊天管理\",\"needsOperator\":true,\"operater\":\"root\",\"operaterId\":1,\"parentId\":0,\"path\":\"chat\",\"perms\":\"\",\"query\":\"\",\"redirect\":0,\"sort\":1,\"status\":1,\"title\":\"\",\"type\":2}]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (56, 'root', '2024-05-18 21:56:19', 1, 1075, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.updateResource()', 'PUT', '/chat-master/resource', '资源模块', 'UPDATE', '资源模块信息修改', 147, '[{\"component\":\"gpt/order/index\",\"hidden\":0,\"icon\":\"form\",\"id\":1075,\"name\":\"订单管理\",\"needsOperator\":true,\"operater\":\"root\",\"operaterId\":1,\"parentId\":0,\"path\":\"order\",\"perms\":\"\",\"query\":\"\",\"redirect\":0,\"sort\":1,\"status\":1,\"title\":\"\",\"type\":2}]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (57, 'root', '2024-05-18 21:56:31', 1, 1079, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.updateResource()', 'PUT', '/chat-master/resource', '资源模块', 'UPDATE', '资源模块信息修改', 149, '[{\"component\":\"gpt/member/index\",\"hidden\":0,\"icon\":\"user\",\"id\":1079,\"name\":\"用户列表\",\"needsOperator\":true,\"operater\":\"root\",\"operaterId\":1,\"parentId\":0,\"path\":\"member\",\"perms\":\"\",\"query\":\"\",\"redirect\":0,\"sort\":1,\"status\":1,\"title\":\"\",\"type\":2}]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (58, 'root', '2024-05-18 21:56:43', 1, 1180, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.updateResource()', 'PUT', '/chat-master/resource', '资源模块', 'UPDATE', '资源模块信息修改', 150, '[{\"component\":\"gpt/model/index\",\"hidden\":0,\"icon\":\"nested\",\"id\":1180,\"name\":\"大模型信息\",\"needsOperator\":true,\"operater\":\"root\",\"operaterId\":1,\"parentId\":0,\"path\":\"model\",\"perms\":\"\",\"query\":\"\",\"redirect\":0,\"sort\":1,\"status\":1,\"title\":\"\",\"type\":2}]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (59, 'root', '2024-05-18 21:56:51', 1, 1073, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.updateResource()', 'PUT', '/chat-master/resource', '资源模块', 'UPDATE', '资源模块信息修改', 148, '[{\"component\":\"gpt/chat-message/index\",\"hidden\":0,\"icon\":\"email\",\"id\":1073,\"name\":\"消息管理\",\"needsOperator\":true,\"operater\":\"root\",\"operaterId\":1,\"parentId\":0,\"path\":\"chat-message\",\"perms\":\"\",\"query\":\"\",\"redirect\":0,\"sort\":2,\"status\":1,\"title\":\"\",\"type\":2}]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (60, 'root', '2024-05-18 21:57:05', 1, 1076, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.updateResource()', 'PUT', '/chat-master/resource', '资源模块', 'UPDATE', '资源模块信息修改', 146, '[{\"component\":\"gpt/redemption/index\",\"hidden\":0,\"icon\":\"lock\",\"id\":1076,\"name\":\"兑换码管理\",\"needsOperator\":true,\"operater\":\"root\",\"operaterId\":1,\"parentId\":0,\"path\":\"redemption\",\"perms\":\"\",\"query\":\"\",\"redirect\":0,\"sort\":2,\"status\":1,\"title\":\"\",\"type\":2}]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (61, 'root', '2024-05-18 21:57:13', 1, 1078, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.updateResource()', 'PUT', '/chat-master/resource', '资源模块', 'UPDATE', '资源模块信息修改', 146, '[{\"component\":\"gpt/comb/index\",\"hidden\":0,\"icon\":\"post\",\"id\":1078,\"name\":\"套餐配置\",\"needsOperator\":true,\"operater\":\"root\",\"operaterId\":1,\"parentId\":0,\"path\":\"comb\",\"perms\":\"\",\"query\":\"\",\"redirect\":0,\"sort\":2,\"status\":1,\"title\":\"\",\"type\":1}]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (62, 'root', '2024-05-18 21:57:22', 1, 1081, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.updateResource()', 'PUT', '/chat-master/resource', '资源模块', 'UPDATE', '资源模块信息修改', 143, '[{\"component\":\"gpt/openkey/index\",\"hidden\":0,\"icon\":\"icon\",\"id\":1081,\"name\":\"token管理\",\"needsOperator\":true,\"operater\":\"root\",\"operaterId\":1,\"parentId\":0,\"path\":\"openkey\",\"perms\":\"\",\"query\":\"\",\"redirect\":0,\"sort\":2,\"status\":1,\"title\":\"\",\"type\":2}]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (63, 'root', '2024-05-18 21:57:36', 1, 1083, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.updateResource()', 'PUT', '/chat-master/resource', '资源模块', 'UPDATE', '资源模块信息修改', 151, '[{\"component\":\"sys/base-config/index\",\"hidden\":0,\"icon\":\"example\",\"id\":1083,\"name\":\"站点配置\",\"needsOperator\":true,\"operater\":\"root\",\"operaterId\":1,\"parentId\":0,\"path\":\"base\",\"perms\":\"\",\"query\":\"\",\"redirect\":0,\"sort\":4,\"status\":1,\"title\":\"\",\"type\":2}]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (64, 'root', '2024-05-18 21:58:08', 1, 1078, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.updateResource()', 'PUT', '/chat-master/resource', '资源模块', 'UPDATE', '资源模块信息修改', 130, '[{\"component\":\"gpt/comb/index\",\"hidden\":0,\"icon\":\"post\",\"id\":1078,\"name\":\"套餐配置\",\"needsOperator\":true,\"operater\":\"root\",\"operaterId\":1,\"parentId\":0,\"path\":\"comb\",\"perms\":\"\",\"query\":\"\",\"redirect\":0,\"sort\":2,\"status\":1,\"title\":\"\",\"type\":2}]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (65, 'System', '2024-05-18 21:58:40', 1, 0, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1076', '资源模块', 'DELETE', '资源模块信息删除', 217, '[1076]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (66, 'root', '2024-05-18 21:59:00', 1, 1073, 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.updateResource()', 'PUT', '/chat-master/resource', '资源模块', 'UPDATE', '资源模块信息修改', 134, '[{\"component\":\"gpt/chat-message/index\",\"hidden\":0,\"icon\":\"email\",\"id\":1073,\"name\":\"消息管理\",\"needsOperator\":true,\"operater\":\"root\",\"operaterId\":1,\"parentId\":0,\"path\":\"chat-message\",\"perms\":\"\",\"query\":\"\",\"redirect\":0,\"sort\":1,\"status\":1,\"title\":\"\",\"type\":2}]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (67, 'System', '2024-05-19 09:27:54', 2, 0, 'admin', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.gpt.ModelController.removeModelByIds()', 'DELETE', '/chat-master/gpt/model/5', '默认', 'DELETE', '默认信息删除', 110, '[[5]]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (68, 'System', '2024-05-19 09:27:57', 2, 0, 'admin', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.gpt.ModelController.removeModelByIds()', 'DELETE', '/chat-master/gpt/model/3', '默认', 'DELETE', '默认信息删除', 109, '[[3]]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (69, 'System', '2024-05-19 09:28:00', 2, 0, 'admin', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.gpt.ModelController.removeModelByIds()', 'DELETE', '/chat-master/gpt/model/2', '默认', 'DELETE', '默认信息删除', 108, '[[2]]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (70, 'System', '2024-05-19 09:28:02', 2, 0, 'admin', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.gpt.ModelController.removeModelByIds()', 'DELETE', '/chat-master/gpt/model/1', '默认', 'DELETE', '默认信息删除', 116, '[[1]]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (71, 'System', '2024-05-19 09:28:13', 2, 0, 'admin', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1180', '资源模块', 'DELETE', '资源模块信息删除', 200, '[1180]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (72, 'System', '2024-05-19 09:28:16', 2, 0, 'admin', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1181', '资源模块', 'DELETE', '资源模块信息删除', 200, '[1181]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (73, 'System', '2024-05-19 09:28:19', 2, 0, 'admin', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1182', '资源模块', 'DELETE', '资源模块信息删除', 204, '[1182]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (74, 'System', '2024-05-19 09:28:21', 2, 0, 'admin', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1183', '资源模块', 'DELETE', '资源模块信息删除', 197, '[1183]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (75, 'System', '2024-05-19 09:28:24', 2, 0, 'admin', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1184', '资源模块', 'DELETE', '资源模块信息删除', 200, '[1184]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (76, 'System', '2024-05-19 09:28:27', 2, 0, 'admin', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1185', '资源模块', 'DELETE', '资源模块信息删除', 199, '[1185]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (77, 'System', '2024-05-19 09:28:29', 2, 0, 'admin', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1186', '资源模块', 'DELETE', '资源模块信息删除', 200, '[1186]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (78, 'System', '2024-05-19 09:28:31', 2, 0, 'admin', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1187', '资源模块', 'DELETE', '资源模块信息删除', 202, '[1187]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (79, 'System', '2024-05-19 09:28:45', 2, 0, 'admin', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1073', '资源模块', 'DELETE', '资源模块信息删除', 217, '[1073]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (80, 'admin', '2024-05-19 09:29:19', 2, 0, 'admin', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.BaseConfigController.saveBaseConfig()', 'POST', '/chat-master/sys/base-config', '默认', 'INSERT', '默认信息添加/修改', 135, '[{\"data\":\"{\\\"h5Url\\\":\\\"\\\",\\\"isSms\\\":0,\\\"homeNotice\\\":\\\"确保合法合规使用，在运营过程中产生的一切问题后果自负，与作者无关。!\\\",\\\"isGptLimit\\\":0,\\\"isShare\\\":1,\\\"shareRewardNum\\\":\\\"20\\\",\\\"freeNum\\\":\\\"5\\\"}\",\"name\":\"appInfo\",\"needsOperator\":true,\"operater\":\"admin\",\"operaterId\":2}]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (81, 'admin', '2024-05-19 09:29:20', 2, 0, 'admin', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.BaseConfigController.saveBaseConfig()', 'POST', '/chat-master/sys/base-config', '默认', 'INSERT', '默认信息添加/修改', 130, '[{\"data\":\"{\\\"h5Url\\\":\\\"\\\",\\\"isSms\\\":0,\\\"homeNotice\\\":\\\"\\\",\\\"isGptLimit\\\":0,\\\"isShare\\\":1,\\\"shareRewardNum\\\":\\\"20\\\",\\\"freeNum\\\":\\\"5\\\"}\",\"name\":\"appInfo\",\"needsOperator\":true,\"operater\":\"admin\",\"operaterId\":2}]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (82, 'admin', '2024-05-19 09:29:36', 2, 0, 'admin', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.BaseConfigController.saveBaseConfig()', 'POST', '/chat-master/sys/base-config', '默认', 'INSERT', '默认信息添加/修改', 135, '[{\"data\":\"{\\\"contentSecurity\\\":0,\\\"copyright\\\":\\\"© 2023 ChatMASTER 苏ICP备66668888号 江苏Master科技有限公司\\\",\\\"descrip\\\":\\\"ChatMASTER，基于AI大模型api实现的ChatGPT服务，支持ChatGPT(3.5、4.0)模型，同时也支持国内文心一言(支持Stable-Diffusion-XL作图)、通义千问、讯飞星火、智谱清言(ChatGLM)等主流模型，支出同步响应及流式响应，完美呈现打印机效果。\\\",\\\"keywords\\\":[\\\"通义千问\\\",\\\"ChatGPT\\\",\\\"文心一言\\\",\\\"智谱清言\\\"],\\\"siteTitle\\\":\\\"言启未来\\\",\\\"domain\\\":\\\"https://gpt.panday94.xyz\\\",\\\"proxyType\\\":1,\\\"siteLogo\\\":\\\"\\\"}\",\"name\":\"baseInfo\",\"needsOperator\":true,\"operater\":\"admin\",\"operaterId\":2}]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (83, 'admin', '2024-05-19 09:29:38', 2, 0, 'admin', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.BaseConfigController.saveBaseConfig()', 'POST', '/chat-master/sys/base-config', '默认', 'INSERT', '默认信息添加/修改', 132, '[{\"data\":\"{\\\"contentSecurity\\\":0,\\\"copyright\\\":\\\"© 2023 ChatMASTER 苏ICP备66668888号 江苏Master科技有限公司\\\",\\\"descrip\\\":\\\"言启未来\\\",\\\"keywords\\\":[\\\"通义千问\\\",\\\"ChatGPT\\\",\\\"文心一言\\\",\\\"智谱清言\\\"],\\\"siteTitle\\\":\\\"言启未来\\\",\\\"domain\\\":\\\"https://gpt.panday94.xyz\\\",\\\"proxyType\\\":1,\\\"siteLogo\\\":\\\"\\\"}\",\"name\":\"baseInfo\",\"needsOperator\":true,\"operater\":\"admin\",\"operaterId\":2}]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (84, 'admin', '2024-05-19 09:29:41', 2, 0, 'admin', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.BaseConfigController.saveBaseConfig()', 'POST', '/chat-master/sys/base-config', '默认', 'INSERT', '默认信息添加/修改', 137, '[{\"data\":\"{\\\"contentSecurity\\\":0,\\\"copyright\\\":\\\"\\\",\\\"descrip\\\":\\\"言启未来\\\",\\\"keywords\\\":[\\\"通义千问\\\",\\\"ChatGPT\\\",\\\"文心一言\\\",\\\"智谱清言\\\"],\\\"siteTitle\\\":\\\"言启未来\\\",\\\"domain\\\":\\\"https://gpt.panday94.xyz\\\",\\\"proxyType\\\":1,\\\"siteLogo\\\":\\\"\\\"}\",\"name\":\"baseInfo\",\"needsOperator\":true,\"operater\":\"admin\",\"operaterId\":2}]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (85, 'admin', '2024-05-19 09:29:54', 2, 0, 'admin', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.BaseConfigController.saveBaseConfig()', 'POST', '/chat-master/sys/base-config', '默认', 'INSERT', '默认信息添加/修改', 135, '[{\"data\":\"{\\\"contentSecurity\\\":0,\\\"copyright\\\":\\\"\\\",\\\"descrip\\\":\\\"言启未来\\\",\\\"keywords\\\":[\\\"通义千问\\\",\\\"ChatGPT\\\",\\\"文心一言\\\",\\\"智谱清言\\\"],\\\"siteTitle\\\":\\\"言启未来\\\",\\\"domain\\\":\\\"http://127.0.0.1\\\",\\\"proxyType\\\":1,\\\"siteLogo\\\":\\\"\\\"}\",\"name\":\"baseInfo\",\"needsOperator\":true,\"operater\":\"admin\",\"operaterId\":2}]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (86, 'System', '2024-05-19 09:30:16', 2, 0, 'admin', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1083', '资源模块', 'DELETE', '资源模块信息删除', 200, '[1083]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (87, 'System', '2024-05-19 09:30:19', 2, 0, 'admin', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1092', '资源模块', 'DELETE', '资源模块信息删除', 208, '[1092]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (88, 'System', '2024-05-19 09:30:21', 2, 0, 'admin', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1106', '资源模块', 'DELETE', '资源模块信息删除', 203, '[1106]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (89, 'System', '2024-05-19 09:30:25', 2, 0, 'admin', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1093', '资源模块', 'DELETE', '资源模块信息删除', 199, '[1093]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (90, 'System', '2024-05-19 09:30:28', 2, 0, 'admin', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1107', '资源模块', 'DELETE', '资源模块信息删除', 200, '[1107]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (91, 'System', '2024-05-19 09:30:31', 2, 0, 'admin', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1094', '资源模块', 'DELETE', '资源模块信息删除', 197, '[1094]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (92, 'System', '2024-05-19 09:30:34', 2, 0, 'admin', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1108', '资源模块', 'DELETE', '资源模块信息删除', 199, '[1108]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (93, 'System', '2024-05-19 09:30:37', 2, 0, 'admin', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1095', '资源模块', 'DELETE', '资源模块信息删除', 193, '[1095]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (94, 'System', '2024-05-19 09:30:38', 2, 0, 'admin', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1109', '资源模块', 'DELETE', '资源模块信息删除', 199, '[1109]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (95, 'System', '2024-05-19 09:30:41', 2, 0, 'admin', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1096', '资源模块', 'DELETE', '资源模块信息删除', 200, '[1096]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (96, 'System', '2024-05-19 09:30:43', 2, 0, 'admin', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1110', '资源模块', 'DELETE', '资源模块信息删除', 206, '[1110]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (97, 'System', '2024-05-19 09:30:46', 2, 0, 'admin', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1097', '资源模块', 'DELETE', '资源模块信息删除', 200, '[1097]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (98, 'System', '2024-05-19 09:30:47', 2, 0, 'admin', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1111', '资源模块', 'DELETE', '资源模块信息删除', 203, '[1111]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (99, 'System', '2024-05-19 09:30:49', 2, 0, 'admin', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1098', '资源模块', 'DELETE', '资源模块信息删除', 193, '[1098]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (100, 'System', '2024-05-19 09:30:51', 2, 0, 'admin', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.sys.ResourceController.deleteMenuById()', 'DELETE', '/chat-master/resource/1112', '资源模块', 'DELETE', '资源模块信息删除', 196, '[1112]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (101, 'System', '2024-05-19 09:31:01', 2, 0, 'admin', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.gpt.OpenkeyController.removeOpenkeyByIds()', 'DELETE', '/chat-master/gpt/openkey/5', '默认', 'DELETE', '默认信息删除', 106, '[[5]]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (102, 'System', '2024-05-19 09:31:05', 2, 0, 'admin', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.gpt.OpenkeyController.removeOpenkeyByIds()', 'DELETE', '/chat-master/gpt/openkey/3', '默认', 'DELETE', '默认信息删除', 110, '[[3]]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (103, 'System', '2024-05-19 09:31:13', 2, 0, 'admin', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.gpt.OpenkeyController.removeOpenkeyByIds()', 'DELETE', '/chat-master/gpt/openkey/2', '默认', 'DELETE', '默认信息删除', 112, '[[2]]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (104, 'System', '2024-05-19 09:31:14', 2, 0, 'admin', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.gpt.OpenkeyController.removeOpenkeyByIds()', 'DELETE', '/chat-master/gpt/openkey/1', '默认', 'DELETE', '默认信息删除', 107, '[[1]]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (105, 'admin', '2024-05-19 09:31:25', 2, 5, 'admin', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.gpt.CombController.updateComb()', 'PUT', '/chat-master/gpt/comb', '默认', 'UPDATE', '默认信息修改', 136, '[{\"days\":0,\"id\":5,\"needsOperator\":true,\"num\":5000,\"operater\":\"admin\",\"operaterId\":2,\"originPrice\":199.99,\"price\":99.99,\"status\":0,\"title\":\"500次全年包\",\"type\":1}]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);
INSERT INTO `sys_log` VALUES (106, 'admin', '2024-05-19 09:31:28', 2, 5, 'admin', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', 'com.master.chat.controller.gpt.CombController.updateComb()', 'PUT', '/chat-master/gpt/comb', '默认', 'UPDATE', '默认信息修改', 122, '[{\"days\":0,\"id\":5,\"needsOperator\":true,\"num\":5000,\"operater\":\"admin\",\"operaterId\":2,\"originPrice\":199.99,\"price\":99.99,\"status\":1,\"title\":\"500次全年包\",\"type\":1}]', '{\"msg\":\"操作成功\",\"code\":200,\"success\":true}', 0);

-- ----------------------------
-- Table structure for sys_login_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_log`;
CREATE TABLE `sys_login_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `expire_time` datetime(0) NULL DEFAULT NULL COMMENT 'token过期时间',
  `sys_user_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '用户id',
  `session_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '会话标识',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `ip` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'ip地址',
  `address` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '登录地址',
  `domain` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '域名',
  `browser` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '操作系统',
  `msg` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '登录信息',
  `authorization` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '身份标识',
  `user_agent` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '浏览器类型',
  `status` int(1) NOT NULL DEFAULT 0 COMMENT '登录状态 0 失败 1 成功',
  `enabled` int(1) NOT NULL DEFAULT 1 COMMENT '是否启用 0 禁用 1 启用',
  `deleted` int(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '登录日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_login_log
-- ----------------------------
INSERT INTO `sys_login_log` VALUES (1, 'System', '2024-05-18 21:38:04', '2024-05-25 21:38:04', 2, '2f19b332-e988-4659-8865-c82b33609b51', 'admin', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', '登录成功', 'eyJhbGciOiJIUzUxMiJ9.eyJ1aWQiOiIyY2IxNWQ1Ny0zYjMwLTRiMDYtYTA0ZC1iOGQzMmQyMzRlOUQiLCJzdWIiOiJhZG1pbiIsInJvbGUiOjEsImlkIjoyLCJzZXNzaW9uSWQiOiIyZjE5YjMzMi1lOTg4LTQ2NTktODg2NS1jODJiMzM2MDliNTEiLCJleHAiOjE3MTY2NDQyODMsInVzZXJuYW1lIjoiYWRtaW4iLCJhZG1pbmQiOnRydWV9.ybjp4T3Uo5_DNAugupPwQCjoOArlh4glewdKldg9usi2_8LxuZWI_ndOW2-GXXA94oM7fOrQLHerNV_3mKBm9Q', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0.0.0 Safari/537.36 Edg/125.0.0.0', 1, 0, 0);
INSERT INTO `sys_login_log` VALUES (2, 'System', '2024-05-18 21:39:09', '2024-05-25 21:39:09', 1, '238aa577-b2d8-448a-af63-360ac2361515', 'root', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', '登录成功', 'eyJhbGciOiJIUzUxMiJ9.eyJ1aWQiOiIyY2IxNWQ1Ny0zYjMwLTRiMDYtYTA0ZC1iOGQzMmQyMzRlOGQiLCJzdWIiOiJyb290Iiwicm9sZSI6MSwiaWQiOjEsInNlc3Npb25JZCI6IjIzOGFhNTc3LWIyZDgtNDQ4YS1hZjYzLTM2MGFjMjM2MTUxNSIsImV4cCI6MTcxNjY0NDM0OSwidXNlcm5hbWUiOiJyb290IiwiYWRtaW5kIjp0cnVlfQ.fq4WHxniCbSjbQWm_B_g5WIX1s_HNsgBLsrKgbsrqjnEZP75cYAK7FOT1bNLT04MSKKokdAN09-7rw9eIxxJTw', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0.0.0 Safari/537.36 Edg/125.0.0.0', 1, 0, 0);
INSERT INTO `sys_login_log` VALUES (3, 'System', '2024-05-19 09:27:37', '2024-05-26 09:27:36', 2, '08a91978-1006-47ed-9c30-f676282852ba', 'admin', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', '登录成功', 'eyJhbGciOiJIUzUxMiJ9.eyJ1aWQiOiIyY2IxNWQ1Ny0zYjMwLTRiMDYtYTA0ZC1iOGQzMmQyMzRlOUQiLCJzdWIiOiJhZG1pbiIsInJvbGUiOjEsImlkIjoyLCJzZXNzaW9uSWQiOiIwOGE5MTk3OC0xMDA2LTQ3ZWQtOWMzMC1mNjc2MjgyODUyYmEiLCJleHAiOjE3MTY2ODY4NTYsInVzZXJuYW1lIjoiYWRtaW4iLCJhZG1pbmQiOnRydWV9.RzmWihuQgvOYdUHP6Y8SD2al1G3MevrhlGMdwPAPnMFAOp-pFqI6w7-C0r1pf309fFYfWrRXck3bE-TfgqAyOg', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0.0.0 Safari/537.36 Edg/125.0.0.0', 1, 0, 0);
INSERT INTO `sys_login_log` VALUES (4, 'System', '2024-05-19 10:06:35', '2024-05-26 10:06:35', 2, '4b607c0c-4a5f-470f-b1ea-49a0b29a292d', 'admin', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', '登录成功', 'eyJhbGciOiJIUzUxMiJ9.eyJ1aWQiOiIyY2IxNWQ1Ny0zYjMwLTRiMDYtYTA0ZC1iOGQzMmQyMzRlOUQiLCJzdWIiOiJhZG1pbiIsInJvbGUiOjEsImlkIjoyLCJzZXNzaW9uSWQiOiI0YjYwN2MwYy00YTVmLTQ3MGYtYjFlYS00OWEwYjI5YTI5MmQiLCJleHAiOjE3MTY2ODkxOTQsInVzZXJuYW1lIjoiYWRtaW4iLCJhZG1pbmQiOnRydWV9.w1y3rX6c8O-u8heICKdNvmTTyHsQmTwezN-vY-a9VrMHD3y0kKji0B8Eg-iEOv3Z-XW78_V5qqcbKnbwyI7KAg', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0.0.0 Safari/537.36 Edg/125.0.0.0', 1, 0, 0);
INSERT INTO `sys_login_log` VALUES (5, 'System', '2024-05-19 19:16:06', '2024-05-26 19:16:06', 2, '0c6485b6-6d88-4e95-b99f-6c80af18b135', 'admin', '127.0.0.1', '内网IP', 'http://localhost:8088', 'MSEdge', 'Windows 10 or Windows Server 2016', '登录成功', 'eyJhbGciOiJIUzUxMiJ9.eyJ1aWQiOiIyY2IxNWQ1Ny0zYjMwLTRiMDYtYTA0ZC1iOGQzMmQyMzRlOUQiLCJzdWIiOiJhZG1pbiIsInJvbGUiOjEsImlkIjoyLCJzZXNzaW9uSWQiOiIwYzY0ODViNi02ZDg4LTRlOTUtYjk5Zi02YzgwYWYxOGIxMzUiLCJleHAiOjE3MTY3MjIxNjUsInVzZXJuYW1lIjoiYWRtaW4iLCJhZG1pbmQiOnRydWV9.dNvnbFI4nYZOMYOFuOsYIbMXP2O7kizteB_JvbthDwEJfH8tldVPGbdBTIS9RNojVsYXuJ6vXcFreqtJef2X5A', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0.0.0 Safari/537.36 Edg/125.0.0.0', 1, 1, 0);

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `type` int(11) NOT NULL DEFAULT 0 COMMENT '公告类型（1通知 2公告）',
  `title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '公告标题',
  `agreement` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '公告内容',
  `status` int(1) NOT NULL DEFAULT 1 COMMENT '状态 0->禁用;1->启用',
  `deleted` int(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统通知' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_notice
-- ----------------------------

-- ----------------------------
-- Table structure for sys_notice_read
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice_read`;
CREATE TABLE `sys_notice_read`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `sys_user_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '用户id',
  `notice_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '通知id',
  `is_read` int(1) NOT NULL DEFAULT 1 COMMENT '是否已读 0：未读；1：已读',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统通知已读状态' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_notice_read
-- ----------------------------

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '岗位名称',
  `code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '岗位编码',
  `sort` int(1) NOT NULL DEFAULT 0 COMMENT '排序',
  `status` int(1) NOT NULL DEFAULT 1 COMMENT '状态 0->禁用;1->启用',
  `deleted` int(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '岗位' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_post
-- ----------------------------

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '资源名称',
  `title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '标题名称',
  `code` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '菜单编码',
  `icon` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '菜单图标',
  `path` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '路由路径',
  `component` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '组件路径',
  `query` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '路由参数',
  `perms` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '权限字符串',
  `parent_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '父级id',
  `redirect` int(1) NOT NULL DEFAULT 0 COMMENT '是否重定向 0：否 1: 是',
  `type` int(1) NOT NULL DEFAULT 1 COMMENT '类型：1：目录 2: 菜单 3：操作资源',
  `sort` int(1) NOT NULL DEFAULT 0 COMMENT '排序',
  `status` int(1) NOT NULL DEFAULT 1 COMMENT '状态 0->禁用;1->启用',
  `hidden` int(1) NOT NULL DEFAULT 0 COMMENT '是否隐藏 0->不隐藏;1->隐藏',
  `deleted` int(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1188 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '资源' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_resource
-- ----------------------------
INSERT INTO `sys_resource` VALUES (1, 'System', '2022-08-04 15:31:10', 'root', '2022-11-12 15:11:00', '系统管理', '', '', 'system', 'sys', '', '', '', 0, 0, 1, 5, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (2, 'System', '2022-08-04 15:31:10', 'root', '2024-05-18 21:45:35', '系统监控', '', '', 'monitor', 'monitor', '', '', '', 0, 0, 1, 6, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (3, 'System', '2022-08-04 15:31:10', 'root', '2023-04-28 08:05:40', '系统工具', '', '', 'tool', 'tool', '', '', '', 0, 0, 1, 7, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (4, 'System', '2022-08-04 15:31:10', 'admin', '2024-05-18 21:43:52', 'ChatMASTER', '', '', 'guide', 'https://chat.panday94.xyz', '', '', '', 0, 1, 1, 8, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (100, 'System', '2022-08-04 15:31:10', 'admin', '2023-04-19 16:23:21', '用户管理', '', '', 'user', 'user', 'sys/user/index', '', '', 1, 0, 2, 1, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (101, 'System', '2022-08-04 15:31:10', 'admin', '2023-04-19 16:23:21', '角色管理', '', '', 'peoples', 'role', 'sys/role/index', '', '', 1, 0, 2, 2, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (102, 'System', '2022-08-04 15:31:10', 'admin', '2022-11-12 15:09:48', '菜单管理', '', '', 'tree-table', 'resource', 'sys/resource/index', '', '', 1, 0, 2, 5, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (103, 'System', '2022-08-04 15:31:10', 'admin', '2023-04-19 16:23:21', '部门管理', '', '', 'tree', 'dept', 'sys/dept/index', '', '', 1, 0, 2, 3, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (104, 'System', '2022-08-04 15:31:10', 'admin', '2024-05-18 21:45:43', '岗位管理', '', '', 'post', 'post', 'sys/post/index', '', '', 1, 0, 2, 4, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (105, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:02:01', '字典管理', '', '', 'dict', 'dict', 'sys/dict/index', '', '', 1, 0, 2, 6, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (106, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:02:06', '参数设置', '', '', 'edit', 'config', 'sys/config/index', '', '', 1, 0, 2, 7, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (107, 'System', '2022-08-04 15:31:10', 'System', '2024-05-18 21:46:12', '通知公告', '', '', 'message', 'notice', 'sys/notice/index', '', '', 1, 0, 2, 8, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (108, 'System', '2022-08-04 15:31:10', 'System', '2024-05-18 21:44:21', '日志列表', '', '', 'log', 'log', '', '', '', 2, 0, 1, 7, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (109, 'System', '2022-08-04 15:31:10', 'System', '2024-05-18 21:43:59', '在线用户', '', '', 'online', 'online', 'monitor/online/index', '', '', 2, 0, 2, 1, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (110, 'System', '2022-08-04 15:31:10', 'System', '2024-05-18 21:45:07', '定时任务', '', '', 'job', 'job', 'monitor/job/index', '', '', 2, 0, 2, 2, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (111, 'System', '2022-08-04 15:31:10', 'System', '2024-05-18 21:44:12', '数据监控', '', '', 'druid', 'druid', 'monitor/druid/index', '', '', 2, 0, 2, 3, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (112, 'System', '2022-08-04 15:31:10', 'System', '2024-05-18 21:44:09', '服务监控', '', '', 'server', 'server', 'monitor/server/index', '', '', 2, 0, 2, 4, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (113, 'System', '2022-08-04 15:31:10', 'System', '2024-05-18 21:44:14', '缓存监控', '', '', 'redis', 'cache', 'monitor/cache/index', '', '', 2, 0, 2, 5, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (114, 'System', '2022-08-04 15:31:10', 'System', '2024-05-18 21:44:18', '缓存列表', '', '', 'redis-list', 'cacheList', 'monitor/cache/list', '', '', 2, 0, 2, 6, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (115, 'System', '2022-08-04 15:31:10', 'System', '2023-04-28 08:05:40', '表单构建', '', '', 'build', 'build', 'tool/build/index', '', '', 3, 0, 2, 1, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (116, 'System', '2022-08-04 15:31:10', 'System', '2023-04-28 08:05:40', '代码生成', '', '', 'code', 'gen', 'tool/gen/index', '', '', 3, 0, 2, 2, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (117, 'System', '2022-08-04 15:31:10', 'System', '2023-04-28 08:05:40', '接口地址', '', '', 'guide', 'http://yapi.dinggehuo.com', 'tool/swagger/index', '', '', 3, 1, 2, 3, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (500, 'System', '2022-08-04 15:31:10', 'System', '2024-05-18 21:44:21', '操作日志', '', '', 'form', 'operlog', 'monitor/operlog/index', '', '', 108, 0, 2, 1, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (501, 'System', '2022-08-04 15:31:10', 'System', '2024-05-18 21:44:21', '登录日志', '', '', 'logininfor', 'logininfor', 'monitor/logininfor/index', '', '', 108, 0, 2, 2, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1000, 'System', '2022-08-04 15:31:10', 'admin', '2022-11-12 15:03:25', '用户列表', '', '', '#', '#', '', '', 'sys:user:list', 100, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1001, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '用户查询', '', '', '#', '#', '', '', 'sys:user:query', 100, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1002, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '用户新增', '', '', '#', '#', '', '', 'sys:user:save', 100, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1003, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '用户修改', '', '', '#', '#', '', '', 'sys:user:update', 100, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1004, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '用户删除', '', '', '#', '#', '', '', 'sys:user:remove', 100, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1005, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '用户导出', '', '', '#', '#', '', '', 'sys:user:export', 100, 0, 3, 6, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1006, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '用户导入', '', '', '#', '#', '', '', 'sys:user:import', 100, 0, 3, 7, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1007, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '重置密码', '', '', '#', '#', '', '', 'sys:user:resetPwd', 100, 0, 3, 8, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1008, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '角色列表', '', '', '#', '#', '', '', 'sys:role:list', 101, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1009, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '角色查询', '', '', '#', '#', '', '', 'sys:role:query', 101, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1010, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '角色新增', '', '', '#', '#', '', '', 'sys:role:save', 101, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1011, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '角色修改', '', '', '#', '#', '', '', 'sys:role:update', 101, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1012, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '角色删除', '', '', '#', '#', '', '', 'sys:role:remove', 101, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1013, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '角色导出', '', '', '#', '#', '', '', 'sys:role:export', 101, 0, 3, 6, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1014, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '菜单列表', '', '', '#', '#', '', '', 'sys:resource:list', 102, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1015, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '菜单查询', '', '', '#', '#', '', '', 'sys:resource:query', 102, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1016, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '菜单新增', '', '', '#', '#', '', '', 'sys:resource:save', 102, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1017, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '菜单修改', '', '', '#', '#', '', '', 'sys:resource:update', 102, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1018, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '菜单删除', '', '', '#', '#', '', '', 'sys:resource:remove', 102, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1019, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '部门列表', '', '', '#', '#', '', '', 'sys:dept:list', 103, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1020, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '部门查询', '', '', '#', '#', '', '', 'sys:dept:query', 103, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1021, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '部门新增', '', '', '#', '#', '', '', 'sys:dept:save', 103, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1022, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '部门修改', '', '', '#', '#', '', '', 'sys:dept:update', 103, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1023, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '部门删除', '', '', '#', '#', '', '', 'sys:dept:remove', 103, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1024, 'System', '2022-08-04 15:31:10', 'admin', '2022-11-12 15:03:25', '岗位列表', '', '', '#', '#', '', '', 'sys:post:list', 1024, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1025, 'System', '2022-08-04 15:31:10', 'System', '2024-05-18 21:45:46', '岗位查询', '', '', '#', '#', '', '', 'sys:post:query', 104, 0, 3, 2, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1026, 'System', '2022-08-04 15:31:10', 'System', '2024-05-18 21:45:50', '岗位新增', '', '', '#', '#', '', '', 'sys:post:save', 104, 0, 3, 3, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1027, 'System', '2022-08-04 15:31:10', 'System', '2024-05-18 21:45:53', '岗位修改', '', '', '#', '#', '', '', 'sys:post:update', 104, 0, 3, 4, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1028, 'System', '2022-08-04 15:31:10', 'System', '2024-05-18 21:45:58', '岗位删除', '', '', '#', '#', '', '', 'sys:post:remove', 104, 0, 3, 5, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1029, 'System', '2022-08-04 15:31:10', 'System', '2024-05-18 21:46:00', '岗位导出', '', '', '#', '#', '', '', 'sys:post:export', 104, 0, 3, 6, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1030, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '字典列表', '', '', '#', '#', '', '', 'sys:dict:list', 105, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1031, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '字典查询', '', '', '#', '#', '', '', 'sys:dict:query', 105, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1032, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '字典新增', '', '', '#', '#', '', '', 'sys:dict:save', 105, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1033, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '字典修改', '', '', '#', '#', '', '', 'sys:dict:update', 105, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1034, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '字典删除', '', '', '#', '#', '', '', 'sys:dict:remove', 105, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1035, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '字典导出', '', '', '#', '#', '', '', 'sys:dict:export', 105, 0, 3, 6, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1036, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '参数列表', '', '', '#', '#', '', '', 'sys:config:list', 106, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1037, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '参数查询', '', '', '#', '#', '', '', 'sys:config:query', 106, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1038, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '参数新增', '', '', '#', '#', '', '', 'sys:config:save', 106, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1039, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '参数修改', '', '', '#', '#', '', '', 'sys:config:update', 106, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1040, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '参数删除', '', '', '#', '#', '', '', 'sys:config:remove', 106, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1041, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '参数导出', '', '', '#', '#', '', '', 'sys:config:export', 106, 0, 3, 6, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1042, 'System', '2022-08-04 15:31:10', 'System', '2024-05-18 21:46:17', '公告列表', '', '', '#', '#', '', '', 'sys:notice:list', 107, 0, 3, 1, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1043, 'System', '2022-08-04 15:31:10', 'System', '2024-05-18 21:46:19', '公告查询', '', '', '#', '#', '', '', 'sys:notice:query', 107, 0, 3, 2, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1044, 'System', '2022-08-04 15:31:10', 'System', '2024-05-18 21:46:22', '公告新增', '', '', '#', '#', '', '', 'sys:notice:save', 107, 0, 3, 3, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1045, 'System', '2022-08-04 15:31:10', 'System', '2024-05-18 21:46:25', '公告修改', '', '', '#', '#', '', '', 'sys:notice:update', 107, 0, 3, 4, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1046, 'System', '2022-08-04 15:31:10', 'System', '2024-05-18 21:46:29', '公告删除', '', '', '#', '#', '', '', 'sys:notice:remove', 107, 0, 3, 5, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1047, 'System', '2022-08-04 15:31:10', 'System', '2024-05-18 21:44:27', '操作日志列表', '', '', '#', '#', '', '', 'sys:log:list', 500, 0, 3, 1, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1048, 'System', '2022-08-04 15:31:10', 'System', '2024-05-18 21:44:29', '操作清空', '', '', '#', '#', '', '', 'sys:log:clean', 500, 0, 3, 1, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1049, 'System', '2022-08-04 15:31:10', 'System', '2024-05-18 21:44:51', '操作删除', '', '', '#', '#', '', '', 'sys:log:remove', 500, 0, 3, 2, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1050, 'System', '2022-08-04 15:31:10', 'System', '2024-05-18 21:45:01', '日志导出', '', '', '#', '#', '', '', 'sys:log:export', 500, 0, 3, 3, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1051, 'System', '2022-08-04 15:31:10', 'System', '2024-05-18 21:44:32', '登录日志列表', '', '', '#', '#', '', '', 'sys:loginlog:list', 501, 0, 3, 1, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1052, 'System', '2022-08-04 15:31:10', 'admin', '2024-05-18 21:44:54', '登录清空', '', '', '#', '#', '', '', 'sys:loginlog:clean', 501, 0, 3, 2, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1053, 'System', '2022-08-04 15:31:10', 'System', '2024-05-18 21:44:57', '登录删除', '', '', '#', '#', '', '', 'sys:loginlog:remove', 501, 0, 3, 2, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1054, 'System', '2022-08-04 15:31:10', 'System', '2024-05-18 21:45:03', '日志导出', '', '', '#', '#', '', '', 'sys:loginlog:export', 501, 0, 3, 3, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1055, 'System', '2022-08-04 15:31:10', 'System', '2024-05-18 21:44:47', '在线列表', '', '', '#', '#', '', '', 'monitor:online:list', 109, 0, 3, 1, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1056, 'System', '2022-08-04 15:31:10', 'System', '2024-05-18 21:44:05', '强退用户', '', '', '#', '#', '', '', 'monitor:online:forceLogout', 109, 0, 3, 4, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1057, 'System', '2022-08-04 15:31:10', 'System', '2024-05-18 21:45:11', '任务列表', '', '', '#', '#', '', '', 'monitor:job:list', 110, 0, 3, 1, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1058, 'System', '2022-08-04 15:31:10', 'System', '2024-05-18 21:45:14', '任务查询', '', '', '#', '#', '', '', 'monitor:job:query', 110, 0, 3, 2, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1059, 'System', '2022-08-04 15:31:10', 'System', '2024-05-18 21:45:17', '任务新增', '', '', '#', '#', '', '', 'monitor:job:save', 110, 0, 3, 3, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1060, 'System', '2022-08-04 15:31:10', 'System', '2024-05-18 21:45:20', '任务修改', '', '', '#', '#', '', '', 'monitor:job:update', 110, 0, 3, 4, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1061, 'System', '2022-08-04 15:31:10', 'System', '2024-05-18 21:45:25', '任务删除', '', '', '#', '#', '', '', 'monitor:job:remove', 110, 0, 3, 5, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1062, 'System', '2022-08-04 15:31:10', 'System', '2024-05-18 21:45:29', '状态修改', '', '', '#', '#', '', '', 'monitor:job:changeStatus', 110, 0, 3, 6, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1063, 'System', '2022-08-04 15:31:10', 'System', '2024-05-18 21:45:32', '任务导出', '', '', '#', '#', '', '', 'monitor:job:export', 110, 0, 3, 7, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1064, 'System', '2022-08-04 15:31:10', 'System', '2023-04-28 08:05:44', '生成列表', '', '', '#', '#', '', '', 'tool:gen:list', 116, 0, 3, 1, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1065, 'System', '2022-08-04 15:31:10', 'System', '2023-04-28 08:05:46', '生成查询', '', '', '#', '#', '', '', 'tool:gen:query', 116, 0, 3, 2, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1066, 'System', '2022-08-04 15:31:10', 'System', '2023-04-28 08:05:49', '生成修改', '', '', '#', '#', '', '', 'tool:gen:update', 116, 0, 3, 3, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1067, 'System', '2022-08-04 15:31:10', 'System', '2023-04-28 08:05:52', '生成删除', '', '', '#', '#', '', '', 'tool:gen:remove', 116, 0, 3, 4, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1068, 'System', '2022-08-04 15:31:10', 'System', '2023-04-28 08:05:55', '导入代码', '', '', '#', '#', '', '', 'tool:gen:import', 116, 0, 3, 5, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1069, 'System', '2022-08-04 15:31:10', 'System', '2023-04-28 08:06:01', '预览代码', '', '', '#', '#', '', '', 'tool:gen:preview', 116, 0, 3, 6, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1070, 'System', '2022-08-04 15:31:10', 'System', '2023-04-28 08:05:58', '生成代码', '', '', '#', '#', '', '', 'tool:gen:code', 116, 0, 3, 7, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1071, 'root', '2023-04-28 15:27:20', 'admin', '2024-05-18 21:55:16', '聊天管理', '', '', 'message', 'chat', '', '', '', 0, 0, 1, 1, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1072, 'root', '2023-04-28 15:28:10', 'root', '2024-05-18 21:54:52', '聊天管理', '', '', 'chart', 'chat', 'gpt/chat/index', '', '', 0, 0, 2, 1, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1073, 'root', '2023-04-28 15:28:40', 'root', '2024-05-19 10:04:20', '消息管理', '', '', 'email', 'chat-message', 'gpt/chat-message/index', '', '', 0, 0, 2, 1, 1, 1, 0);
INSERT INTO `sys_resource` VALUES (1074, 'root', '2023-04-28 15:33:57', 'root', '2024-05-18 21:55:20', '订单管理', '', '', 'money', 'order', '', '', '', 0, 0, 1, 2, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1075, 'root', '2023-04-28 15:34:43', 'root', '2024-05-18 21:54:58', '订单管理', '', '', 'form', 'order', 'gpt/order/index', '', '', 0, 0, 2, 1, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1076, 'root', '2023-04-28 15:35:23', 'root', '2024-05-18 21:58:40', '兑换码管理', '', '', 'lock', 'redemption', 'gpt/redemption/index', '', '', 0, 0, 2, 2, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1077, 'root', '2023-04-28 15:35:55', 'root', '2024-05-18 21:55:24', '会员中心', '', '', 'peoples', 'member', '', '', '', 0, 0, 1, 3, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1078, 'root', '2023-04-28 15:36:12', 'root', '2024-05-18 21:55:03', '套餐配置', '', '', 'post', 'comb', 'gpt/comb/index', '', '', 0, 0, 2, 2, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1079, 'root', '2023-04-28 15:36:28', 'root', '2024-05-18 21:55:03', '用户列表', '', '', 'user', 'member', 'gpt/member/index', '', '', 0, 0, 2, 1, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1080, 'root', '2023-04-28 15:37:10', 'root', '2024-05-18 21:56:00', '配置中心', '', '', 'tool', 'config', '', '', '', 0, 0, 1, 4, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1081, 'root', '2023-04-28 15:38:41', 'root', '2024-05-18 21:55:07', 'token管理', '', '', 'icon', 'openkey', 'gpt/openkey/index', '', '', 0, 0, 2, 2, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1082, 'root', '2023-04-28 15:43:30', 'root', '2024-05-18 21:55:07', '内容管理', '', '', '', 'content', 'gpt/content/index', '', '', 0, 0, 2, 3, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1083, 'root', '2023-04-28 15:44:20', 'root', '2024-05-19 09:30:16', '站点配置', '', '', 'example', 'base', 'sys/base-config/index', '', '', 0, 0, 2, 4, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1084, 'root', '2023-04-28 15:44:35', 'root', '2024-05-18 21:55:08', '缩略图配置', '', '', '', 'upload', 'gpt/upload-config/index', '', '', 0, 0, 2, 5, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1085, 'System', '2023-04-28 07:57:29', 'System', '2024-05-18 21:50:47', 'AI助理功能列表', '', '', '#', '#', '', '', 'gpt:assistant:list', 1169, 0, 3, 1, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1086, 'System', '2023-04-28 07:57:29', 'System', '2024-05-18 21:50:47', 'AI助理功能查询', '', '', '#', '#', '', '', 'gpt:assistant:query', 1169, 0, 3, 2, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1087, 'System', '2023-04-28 07:57:29', 'System', '2024-05-18 21:50:47', 'AI助理功能新增', '', '', '#', '#', '', '', 'gpt:assistant:save', 1169, 0, 3, 3, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1088, 'System', '2023-04-28 07:57:29', 'System', '2024-05-18 21:50:47', 'AI助理功能修改', '', '', '#', '#', '', '', 'gpt:assistant:update', 1169, 0, 3, 4, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1089, 'System', '2023-04-28 07:57:29', 'System', '2024-05-18 21:50:47', 'AI助理功能删除', '', '', '#', '#', '', '', 'gpt:assistant:remove', 1169, 0, 3, 5, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1090, 'System', '2023-04-28 07:57:29', 'System', '2024-05-18 21:50:47', 'AI助理功能审核', '', '', '#', '#', '', '', 'gpt:assistant:audit', 1169, 0, 3, 6, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1091, 'System', '2023-04-28 07:57:29', 'System', '2024-05-18 21:50:47', 'AI助理功能导出', '', '', '#', '#', '', '', 'gpt:assistant:export', 1169, 0, 3, 7, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1092, 'System', '2023-04-28 07:57:29', 'System', '2024-05-19 09:30:19', '基础配置列表', '', '', '#', '#', '', '', 'sys:base:config:list', 1083, 0, 3, 1, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1093, 'System', '2023-04-28 07:57:29', 'System', '2024-05-19 09:30:25', '基础配置查询', '', '', '#', '#', '', '', 'sys:base:config:query', 1083, 0, 3, 2, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1094, 'System', '2023-04-28 07:57:29', 'System', '2024-05-19 09:30:31', '基础配置新增', '', '', '#', '#', '', '', 'sys:base:config:save', 1083, 0, 3, 3, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1095, 'System', '2023-04-28 07:57:29', 'System', '2024-05-19 09:30:37', '基础配置修改', '', '', '#', '#', '', '', 'sys:base:config:update', 1083, 0, 3, 4, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1096, 'System', '2023-04-28 07:57:29', 'System', '2024-05-19 09:30:41', '基础配置删除', '', '', '#', '#', '', '', 'sys:base:config:remove', 1083, 0, 3, 5, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1097, 'System', '2023-04-28 07:57:29', 'System', '2024-05-19 09:30:46', '基础配置审核', '', '', '#', '#', '', '', 'sys:base:config:audit', 1083, 0, 3, 6, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1098, 'System', '2023-04-28 07:57:29', 'System', '2024-05-19 09:30:49', '基础配置导出', '', '', '#', '#', '', '', 'sys:base:config:export', 1083, 0, 3, 7, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1099, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:21', '聊天摘要列表', '', '', '#', '#', '', '', 'gpt:chat:list', 1072, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1100, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:21', '聊天摘要查询', '', '', '#', '#', '', '', 'gpt:chat:query', 1072, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1101, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:21', '聊天摘要新增', '', '', '#', '#', '', '', 'gpt:chat:save', 1072, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1102, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:21', '聊天摘要修改', '', '', '#', '#', '', '', 'gpt:chat:update', 1072, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1103, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:21', '聊天摘要删除', '', '', '#', '#', '', '', 'gpt:chat:remove', 1072, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1104, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:21', '聊天摘要审核', '', '', '#', '#', '', '', 'gpt:chat:audit', 1072, 0, 3, 6, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1105, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:21', '聊天摘要导出', '', '', '#', '#', '', '', 'gpt:chat:export', 1072, 0, 3, 7, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1106, 'System', '2023-04-28 07:57:29', 'System', '2024-05-19 09:30:22', '对话消息列表', '', '', '#', '#', '', '', 'gpt:chat:message:list', 1073, 0, 3, 1, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1107, 'System', '2023-04-28 07:57:29', 'System', '2024-05-19 09:30:29', '对话消息查询', '', '', '#', '#', '', '', 'gpt:chat:message:query', 1073, 0, 3, 2, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1108, 'System', '2023-04-28 07:57:29', 'System', '2024-05-19 09:30:34', '对话消息新增', '', '', '#', '#', '', '', 'gpt:chat:message:save', 1073, 0, 3, 3, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1109, 'System', '2023-04-28 07:57:29', 'System', '2024-05-19 09:30:39', '对话消息修改', '', '', '#', '#', '', '', 'gpt:chat:message:update', 1073, 0, 3, 4, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1110, 'System', '2023-04-28 07:57:29', 'System', '2024-05-19 09:30:44', '对话消息删除', '', '', '#', '#', '', '', 'gpt:chat:message:remove', 1073, 0, 3, 5, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1111, 'System', '2023-04-28 07:57:29', 'System', '2024-05-19 09:30:48', '对话消息审核', '', '', '#', '#', '', '', 'gpt:chat:message:audit', 1073, 0, 3, 6, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1112, 'System', '2023-04-28 07:57:29', 'System', '2024-05-19 09:30:51', '对话消息导出', '', '', '#', '#', '', '', 'gpt:chat:message:export', 1073, 0, 3, 7, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1113, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:48', '会员套餐列表', '', '', '#', '#', '', '', 'gpt:comb:list', 1078, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1114, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:48', '会员套餐查询', '', '', '#', '#', '', '', 'gpt:comb:query', 1078, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1115, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:48', '会员套餐新增', '', '', '#', '#', '', '', 'gpt:comb:save', 1078, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1116, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:48', '会员套餐修改', '', '', '#', '#', '', '', 'gpt:comb:update', 1078, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1117, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:48', '会员套餐删除', '', '', '#', '#', '', '', 'gpt:comb:remove', 1078, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1118, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:48', '会员套餐审核', '', '', '#', '#', '', '', 'gpt:comb:audit', 1078, 0, 3, 6, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1119, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:48', '会员套餐导出', '', '', '#', '#', '', '', 'gpt:comb:export', 1078, 0, 3, 7, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1120, 'System', '2023-04-28 07:57:29', 'System', '2024-05-18 21:47:12', '内容管理列表', '', '', '#', '#', '', '', 'gpt:content:list', 1082, 0, 3, 1, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1121, 'System', '2023-04-28 07:57:29', 'System', '2024-05-18 21:47:16', '内容管理查询', '', '', '#', '#', '', '', 'gpt:content:query', 1082, 0, 3, 2, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1122, 'System', '2023-04-28 07:57:29', 'System', '2024-05-18 21:47:18', '内容管理新增', '', '', '#', '#', '', '', 'gpt:content:save', 1082, 0, 3, 3, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1123, 'System', '2023-04-28 07:57:29', 'System', '2024-05-18 21:47:23', '内容管理修改', '', '', '#', '#', '', '', 'gpt:content:update', 1082, 0, 3, 4, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1124, 'System', '2023-04-28 07:57:29', 'System', '2024-05-18 21:47:20', '内容管理删除', '', '', '#', '#', '', '', 'gpt:content:remove', 1082, 0, 3, 5, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1125, 'System', '2023-04-28 07:57:29', 'System', '2024-05-18 21:47:25', '内容管理审核', '', '', '#', '#', '', '', 'gpt:content:audit', 1082, 0, 3, 6, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1126, 'System', '2023-04-28 07:57:29', 'System', '2024-05-18 21:47:28', '内容管理导出', '', '', '#', '#', '', '', 'gpt:content:export', 1082, 0, 3, 7, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1127, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:57:11', '文件管理列表', '', '', '#', '#', '', '', 'gpt:file:list', 1170, 0, 3, 1, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1128, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:57:13', '文件管理查询', '', '', '#', '#', '', '', 'gpt:file:query', 1170, 0, 3, 2, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1129, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:57:16', '文件管理新增', '', '', '#', '#', '', '', 'gpt:file:save', 1170, 0, 3, 3, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1130, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:56:56', '文件管理修改', '', '', '#', '#', '', '', 'gpt:file:update', 1170, 0, 3, 4, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1131, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:56:59', '文件管理删除', '', '', '#', '#', '', '', 'gpt:file:remove', 1170, 0, 3, 5, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1132, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:57:02', '文件管理审核', '', '', '#', '#', '', '', 'gpt:file:audit', 1170, 0, 3, 6, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1133, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:57:05', '文件管理导出', '', '', '#', '#', '', '', 'gpt:file:export', 1170, 0, 3, 7, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1134, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:39:40', '会员用户列表', '', '', '#', '#', '', '', 'gpt:user:list', 1079, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1135, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:39:43', '会员用户查询', '', '', '#', '#', '', '', 'gpt:user:query', 1079, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1136, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:39:46', '会员用户新增', '', '', '#', '#', '', '', 'gpt:user:save', 1079, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1137, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:39:50', '会员用户修改', '', '', '#', '#', '', '', 'gpt:user:update', 1079, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1138, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:39:52', '会员用户删除', '', '', '#', '#', '', '', 'gpt:user:remove', 1079, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1139, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:39:55', '会员用户审核', '', '', '#', '#', '', '', 'gpt:user:audit', 1079, 0, 3, 6, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1140, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:40:00', '会员用户导出', '', '', '#', '#', '', '', 'gpt:user:export', 1079, 0, 3, 7, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1141, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:01:25', 'openai token列表', '', '', '#', '#', '', '', 'gpt:openkey:list', 1081, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1142, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:01:25', 'openai token查询', '', '', '#', '#', '', '', 'gpt:openkey:query', 1081, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1143, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:01:25', 'openai token新增', '', '', '#', '#', '', '', 'gpt:openkey:save', 1081, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1144, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:01:25', 'openai token修改', '', '', '#', '#', '', '', 'gpt:openkey:update', 1081, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1145, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:01:25', 'openai token删除', '', '', '#', '#', '', '', 'gpt:openkey:remove', 1081, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1146, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:01:25', 'openai token审核', '', '', '#', '#', '', '', 'gpt:openkey:audit', 1081, 0, 3, 6, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1147, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:01:25', 'openai token导出', '', '', '#', '#', '', '', 'gpt:openkey:export', 1081, 0, 3, 7, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1148, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:01:41', '订单列表', '', '', '#', '#', '', '', 'gpt:order:list', 1075, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1149, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:01:41', '订单查询', '', '', '#', '#', '', '', 'gpt:order:query', 1075, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1150, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:01:41', '订单新增', '', '', '#', '#', '', '', 'gpt:order:save', 1075, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1151, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:01:41', '订单修改', '', '', '#', '#', '', '', 'gpt:order:update', 1075, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1152, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:01:41', '订单删除', '', '', '#', '#', '', '', 'gpt:order:remove', 1075, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1153, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:01:41', '订单审核', '', '', '#', '#', '', '', 'gpt:order:audit', 1075, 0, 3, 6, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1154, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:01:41', '订单导出', '', '', '#', '#', '', '', 'gpt:order:export', 1075, 0, 3, 7, 1, 0, 0);
INSERT INTO `sys_resource` VALUES (1155, 'System', '2023-04-28 07:57:29', 'System', '2024-05-18 21:58:35', '兑换码列表', '', '', '#', '#', '', '', 'gpt:redemption:list', 1076, 0, 3, 1, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1156, 'System', '2023-04-28 07:57:29', 'System', '2024-05-18 21:58:35', '兑换码查询', '', '', '#', '#', '', '', 'gpt:redemption:query', 1076, 0, 3, 2, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1157, 'System', '2023-04-28 07:57:29', 'System', '2024-05-18 21:58:35', '兑换码新增', '', '', '#', '#', '', '', 'gpt:redemption:save', 1076, 0, 3, 3, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1158, 'System', '2023-04-28 07:57:29', 'System', '2024-05-18 21:58:35', '兑换码修改', '', '', '#', '#', '', '', 'gpt:redemption:update', 1076, 0, 3, 4, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1159, 'System', '2023-04-28 07:57:29', 'System', '2024-05-18 21:58:35', '兑换码删除', '', '', '#', '#', '', '', 'gpt:redemption:remove', 1076, 0, 3, 5, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1160, 'System', '2023-04-28 07:57:29', 'System', '2024-05-18 21:58:35', '兑换码审核', '', '', '#', '#', '', '', 'gpt:redemption:audit', 1076, 0, 3, 6, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1161, 'System', '2023-04-28 07:57:29', 'System', '2024-05-18 21:58:35', '兑换码导出', '', '', '#', '#', '', '', 'gpt:redemption:export', 1076, 0, 3, 7, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1162, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:56:28', '缩略图配置列表', '', '', '#', '#', '', '', 'gpt:upload:config:list', 1084, 0, 3, 1, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1163, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:56:30', '缩略图配置查询', '', '', '#', '#', '', '', 'gpt:upload:config:query', 1084, 0, 3, 2, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1164, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:56:32', '缩略图配置新增', '', '', '#', '#', '', '', 'gpt:upload:config:save', 1084, 0, 3, 3, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1165, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:56:35', '缩略图配置修改', '', '', '#', '#', '', '', 'gpt:upload:config:update', 1084, 0, 3, 4, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1166, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:56:43', '缩略图配置删除', '', '', '#', '#', '', '', 'gpt:upload:config:remove', 1084, 0, 3, 5, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1167, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:56:37', '缩略图配置审核', '', '', '#', '#', '', '', 'gpt:upload:config:audit', 1084, 0, 3, 6, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1168, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:56:51', '缩略图配置导出', '', '', '#', '#', '', '', 'gpt:upload:config:export', 1084, 0, 3, 7, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1169, 'root', '2023-04-28 16:04:01', 'admin', '2024-05-18 21:51:28', '助手管理', '', '', '', 'index', 'gpt/assistant/index', '', '', 1171, 0, 2, 2, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1170, 'root', '2023-04-28 16:04:26', 'System', '2023-12-26 06:56:39', '文件列表', '', '', '', 'file', 'gpt/file/index', '', '', 1080, 0, 2, 6, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1171, 'admin', '2023-11-22 11:05:52', 'admin', '2024-05-18 21:51:34', '助手中心', '', '', 'example', 'assistant', '', '', '', 0, 0, 1, 3, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1172, 'admin', '2023-11-22 11:07:51', 'System', '2024-05-18 21:51:30', '助手分类', '', '', '', 'type', 'gpt/assistant-type/index', '', '', 1171, 0, 2, 1, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1173, 'System', '2023-11-22 03:10:49', 'System', '2024-05-18 21:51:11', '助手分类列表', '', '', '#', '#', '', '', 'gpt:assistant:type:list', 1172, 0, 3, 1, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1174, 'System', '2023-11-22 03:10:49', 'System', '2024-05-18 21:51:12', '助手分类查询', '', '', '#', '#', '', '', 'gpt:assistant:type:query', 1172, 0, 3, 2, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1175, 'System', '2023-11-22 03:10:49', 'System', '2024-05-18 21:51:12', '助手分类新增', '', '', '#', '#', '', '', 'gpt:assistant:type:save', 1172, 0, 3, 3, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1176, 'System', '2023-11-22 03:10:49', 'System', '2024-05-18 21:51:12', '助手分类修改', '', '', '#', '#', '', '', 'gpt:assistant:type:update', 1172, 0, 3, 4, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1177, 'System', '2023-11-22 03:10:49', 'System', '2024-05-18 21:51:12', '助手分类删除', '', '', '#', '#', '', '', 'gpt:assistant:type:remove', 1172, 0, 3, 5, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1178, 'System', '2023-11-22 03:10:49', 'System', '2024-05-18 21:51:12', '助手分类审核', '', '', '#', '#', '', '', 'gpt:assistant:type:audit', 1172, 0, 3, 6, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1179, 'System', '2023-11-22 03:10:49', 'System', '2024-05-18 21:51:12', '助手分类导出', '', '', '#', '#', '', '', 'gpt:assistant:type:export', 1172, 0, 3, 7, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1180, 'System', '2024-03-18 13:52:44', 'root', '2024-05-19 09:28:13', '大模型信息', '', '', 'nested', 'model', 'gpt/model/index', '', '', 0, 0, 2, 1, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1181, 'System', '2024-03-18 13:52:45', 'System', '2024-05-19 09:28:16', '大模型信息列表', '', '', '#', '#', '', '', 'gpt:model:list', 1180, 0, 3, 1, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1182, 'System', '2024-03-18 13:52:45', 'System', '2024-05-19 09:28:19', '大模型信息查询', '', '', '#', '#', '', '', 'gpt:model:query', 1180, 0, 3, 2, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1183, 'System', '2024-03-18 13:52:45', 'System', '2024-05-19 09:28:22', '大模型信息新增', '', '', '#', '#', '', '', 'gpt:model:save', 1180, 0, 3, 3, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1184, 'System', '2024-03-18 13:52:45', 'System', '2024-05-19 09:28:25', '大模型信息修改', '', '', '#', '#', '', '', 'gpt:model:update', 1180, 0, 3, 4, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1185, 'System', '2024-03-18 13:52:45', 'System', '2024-05-19 09:28:28', '大模型信息删除', '', '', '#', '#', '', '', 'gpt:model:remove', 1180, 0, 3, 5, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1186, 'System', '2024-03-18 13:52:45', 'System', '2024-05-19 09:28:30', '大模型信息审核', '', '', '#', '#', '', '', 'gpt:model:audit', 1180, 0, 3, 6, 1, 0, 1);
INSERT INTO `sys_resource` VALUES (1187, 'System', '2024-03-18 13:52:45', 'System', '2024-05-19 09:28:32', '大模型信息导出', '', '', '#', '#', '', '', 'gpt:model:export', 1180, 0, 3, 7, 1, 0, 1);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '名称',
  `code` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '角色编码',
  `data_scope` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '数据范围 1：所有数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限；5：仅本人数据权限',
  `remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  `sort` int(1) NOT NULL DEFAULT 0 COMMENT '排序',
  `status` int(1) NOT NULL DEFAULT 1 COMMENT '状态 0->禁用;1->启用',
  `deleted` int(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, 'System', '2022-02-17 11:29:18', 'System', '2022-02-17 20:32:03', '超级管理员', 'admin', '1', '', 0, 1, 0);

-- ----------------------------
-- Table structure for sys_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_resource`;
CREATE TABLE `sys_role_resource`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `role_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '角色id',
  `resource_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '资源权限id',
  `status` int(1) NOT NULL DEFAULT 1 COMMENT '状态 0->禁用;1->启用',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE,
  INDEX `resource_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色权限' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_resource
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `login_time` datetime(0) NULL DEFAULT NULL COMMENT '登录时间',
  `username` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户名/账号',
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '密码',
  `uid` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户标识',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '姓名',
  `nick_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '昵称',
  `tel` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '手机号',
  `avatar` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '头像',
  `email` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '邮箱',
  `gender` int(1) NOT NULL DEFAULT -1 COMMENT '性别 0->女;1->男',
  `status` int(1) NOT NULL DEFAULT 1 COMMENT '状态 0->禁用;1->启用',
  `admind` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否管理员 0->否;1->是',
  `deleted` int(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  UNIQUE INDEX `uid`(`uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统账号' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'System', '2024-03-04 10:29:35', 'System', '2024-05-18 21:39:09', '2024-05-18 21:39:09', 'root', '$2a$10$ZJrHs1/UHizEmXbw1auMs.DR9pal5TVA.HoEQWQph7f63OUHozkbG', '2cb15d57-3b30-4b06-a04d-b8d32d234e8d', '超级管理员', '超级管理员', '13888888888', 'https://img1.baidu.com/it/u=1706103831,1363884341&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500', '710957166@qq.com', 1, 1, 1, 0);
INSERT INTO `sys_user` VALUES (2, 'System', '2024-03-04 10:29:35', 'System', '2024-05-19 19:16:06', '2024-05-19 19:16:06', 'admin', '$2a$10$ZJrHs1/UHizEmXbw1auMs.DR9pal5TVA.HoEQWQph7f63OUHozkbG', '2cb15d57-3b30-4b06-a04d-b8d32d234e9D', '管理员', '管理员', '13888888888', 'https://img1.baidu.com/it/u=1706103831,1363884341&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500', '710957166@qq.com', 1, 1, 1, 0);

-- ----------------------------
-- Table structure for sys_user_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_dept`;
CREATE TABLE `sys_user_dept`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `sys_user_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '用户id',
  `dept_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '用户部门id',
  `status` int(1) NOT NULL DEFAULT 1 COMMENT '状态 0->禁用;1->启用',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sys_user_id`(`sys_user_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户部门关联' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_dept
-- ----------------------------
INSERT INTO `sys_user_dept` VALUES (1, 'System', '2022-07-21 10:03:11', 1, 1, 1);

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `sys_user_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '用户id',
  `post_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '用户岗位id',
  `status` int(1) NOT NULL DEFAULT 1 COMMENT '状态 0->禁用;1->启用',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sys_user_id`(`sys_user_id`) USING BTREE,
  INDEX `post_id`(`post_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户岗位关联' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_post
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `sys_user_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '用户id',
  `role_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '角色id',
  `status` int(1) NOT NULL DEFAULT 1 COMMENT '状态 0->禁用;1->启用',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sys_user_id`(`sys_user_id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 'System', '2022-02-17 15:12:19', 1, 1, 1);

SET FOREIGN_KEY_CHECKS = 1;
