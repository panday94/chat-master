# 2024-01-09 增加内容类型字段
ALTER TABLE `chat_gpt`.`gpt_chat_message` ADD COLUMN `content_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '内容类型：text：文字 image : 图片' AFTER `content`;

# 2024-01-20 增加用户开启上下文字段和默认赠送次数
ALTER TABLE `chat_gpt`.`gpt_user` ADD COLUMN `context` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否开启上下文' AFTER `ip`;

ALTER TABLE `chat_gpt`.`gpt_user` MODIFY COLUMN `num` int(11) NULL DEFAULT 0 COMMENT '调用次数' AFTER `context`;

# 2024-03-14 增加助手表头像字段
ALTER TABLE `chat_gpt`.`gpt_assistant` ADD COLUMN `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '头像' AFTER `update_time`;
ALTER TABLE `chat_gpt`.`gpt_assistant` MODIFY COLUMN `icon` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '角色图标' AFTER `avatar`;

# 2024-09-14 增加本地模型langchain-chat支持
ALTER TABLE `gpt_model` ADD COLUMN `local_model_type` int(1) NOT NULL DEFAULT '0' COMMENT '地模型类型：1、Langchian；2、ollama；3、Giteeai' AFTER `model`;
ALTER TABLE `gpt_model` ADD COLUMN `model_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '模型接口' AFTER `local_model_type`;
ALTER TABLE `gpt_model` ADD COLUMN `knowledge` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '知识库名称' AFTER `model_url`;
INSERT INTO `sys_dict_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `type`, `status`, `deleted`) VALUES (18, 'admin', '2024-09-14 15:50:12', 'System', '2024-09-14 15:50:12', '本地模型', 'gpt_local_model', 1, 0);
INSERT INTO `gpt_model` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `model`, `local_model_type`, `model_url`, `knowledge`, `version`, `status`, `deleted`) VALUES (8, 'admin', '2024-09-14 16:02:10', 'admin', '2024-09-14 17:55:03', '本地模型', '', 'LocalLM', 1, 'http://192.168.110.141:7861', '', 'chatglm3-6b', 1, 0);
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (53, 'admin', '2024-07-19 15:30:50', 'admin', '2024-07-19 15:30:50', '本地模型', 'LocalLM', 'gpt_model_type', '', 'info', 0, 99, 1, 0, '');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (54, 'admin', '2024-09-14 15:50:34', 'admin', '2024-09-14 15:50:34', 'Langchain', '1', 'gpt_local_model', '', 'default', 0, 1, 1, 0, '');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (55, 'admin', '2024-09-14 15:50:52', 'admin', '2024-09-14 15:50:52', 'Ollama', '2', 'gpt_local_model', '', 'default', 0, 2, 1, 0, '');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (56, 'admin', '2024-09-14 15:51:20', 'admin', '2024-09-14 15:51:20', 'GiteeAI', '3', 'gpt_local_model', '', 'default', 0, 3, 1, 0, '');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (57, 'admin', '2024-09-19 15:58:02', 'System', '2024-09-19 15:58:02', '扣子', '4', 'gpt_local_model', '', 'default', 0, 4, 1, 0, '');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (58, 'admin', '2024-09-19 15:58:13', 'System', '2024-09-19 15:58:13', 'FastGPT', '5', 'gpt_local_model', '', 'default', 0, 5, 1, 0, '');

# 2025-02-20 增加豆包模型
INSERT INTO `gpt_model` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `model`, `local_model_type`, `model_url`, `knowledge`, `version`, `sort`, `status`, `deleted`) VALUES (9, 'System', '2025-02-20 15:10:45', 'System', '2025-02-20 15:12:37', '豆包', '', 'Doubao', 0, '', '', '', 0, 1, 0);
INSERT INTO `gpt_openkey` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `model`, `app_id`, `app_key`, `app_secret`, `total_tokens`, `used_tokens`, `surplus_tokens`, `status`, `remark`, `deleted`) VALUES (9, 'System', '2025-02-20 15:12:42', 'System', '2025-02-20 15:13:19', 'Doubao', '', '', '', 100000, 0, 0, 1, '', 0);
INSERT INTO `sys_dict` (`create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES ('System', '2024-10-09 10:33:20', 'System', '2024-10-09 10:34:22', 'Claude', 'Claude', 'gpt_model_type', '', 'success', 0, 8, 1, 0, '');
INSERT INTO `sys_dict` (`create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES ('System', '2025-02-17 12:05:41', 'System', '2025-02-17 12:05:58', 'Dify', '6', 'gpt_local_model', '', 'default', 0, 6, 1, 0, '');
INSERT INTO `sys_dict` (`create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES ('System', '2025-02-17 12:06:19', 'System', '2025-02-17 12:06:39', 'LinkAI', '7', 'gpt_local_model', '', 'default', 0, 7, 1, 0, '');
INSERT INTO `sys_dict` (`create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES ('System', '2025-02-17 12:06:52', 'System', '2025-02-17 12:07:33', '豆包', 'Doubao', 'gpt_model_type', '', 'primary', 0, 9, 1, 0, '');
INSERT INTO `sys_dict` (`create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES ('System', '2025-02-17 12:07:04', 'System', '2025-02-17 12:07:36', 'DeepSeek', 'DeepSeek', 'gpt_model_type', '', 'Error', 0, 10, 1, 0, '');