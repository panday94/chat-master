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
-- Records of gpt_assistant_type
-- ----------------------------
BEGIN;
INSERT INTO `gpt_assistant_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `sort`, `status`, `deleted`) VALUES (1, 'admin', '2023-11-22 11:15:20', 'System', '2023-12-08 06:49:53', '创意写作', 'icon-park-outline:picture', 0, 1, 0);
INSERT INTO `gpt_assistant_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `sort`, `status`, `deleted`) VALUES (2, 'admin', '2023-11-22 11:15:29', 'System', '2023-12-08 06:59:13', '学生', 'icon-park-outline:picture', 0, 1, 0);
INSERT INTO `gpt_assistant_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `sort`, `status`, `deleted`) VALUES (3, 'admin', '2023-11-22 11:15:36', 'System', '2023-12-08 07:01:36', '职场人', 'icon-park-outline:picture', 0, 1, 0);
INSERT INTO `gpt_assistant_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `sort`, `status`, `deleted`) VALUES (4, 'admin', '2023-11-22 11:15:45', 'System', '2023-12-08 07:03:08', '家长', 'icon-park-outline:picture', 0, 1, 0);
INSERT INTO `gpt_assistant_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `sort`, `status`, `deleted`) VALUES (5, 'admin', '2023-11-22 11:15:51', 'System', '2023-12-08 07:04:18', '媒体人', 'icon-park-outline:picture', 0, 1, 0);
INSERT INTO `gpt_assistant_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `sort`, `status`, `deleted`) VALUES (6, 'admin', '2023-11-22 11:16:00', 'System', '2023-12-08 07:05:28', '老师', 'icon-park-outline:picture', 0, 1, 0);
INSERT INTO `gpt_assistant_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `sort`, `status`, `deleted`) VALUES (7, 'admin', '2023-11-22 11:16:13', 'System', '2023-12-08 07:06:31', '程序员', 'icon-park-outline:picture', 0, 1, 0);
INSERT INTO `gpt_assistant_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `sort`, `status`, `deleted`) VALUES (8, 'admin', '2023-11-22 11:16:24', 'System', '2023-11-22 08:50:37', '摸鱼大师', 'icon-park-outline:picture', 0, 1, 0);
INSERT INTO `gpt_assistant_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `sort`, `status`, `deleted`) VALUES (9, 'admin', '2023-11-22 11:16:51', 'admin', '2023-12-08 07:08:50', '生活家', 'icon-park-outline:picture', 1, 1, 0);
INSERT INTO `gpt_assistant_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `sort`, `status`, `deleted`) VALUES (10, 'System', '2023-12-08 07:10:00', 'System', '2023-12-08 07:10:04', '营销人', 'icon-park-outline:picture', 0, 1, 0);
INSERT INTO `gpt_assistant_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `sort`, `status`, `deleted`) VALUES (11, 'System', '2023-12-08 07:11:30', 'System', '2023-12-08 07:11:34', 'AI作画', 'icon-park-outline:picture', 0, 1, 0);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
