# 2024-01-09 增加内容类型字段
ALTER TABLE `chat_gpt`.`gpt_chat_message` ADD COLUMN `content_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '内容类型：text：文字 image : 图片' AFTER `content`;

# 2024-01-20 增加用户开启上下文字段和默认赠送次数
ALTER TABLE `chat_gpt`.`gpt_user` ADD COLUMN `context` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否开启上下文' AFTER `ip`;

ALTER TABLE `chat_gpt`.`gpt_user` MODIFY COLUMN `num` int(11) NULL DEFAULT 0 COMMENT '调用次数' AFTER `context`;

# 2024-03-14 增加助手表头像字段
ALTER TABLE `chat_gpt`.`gpt_assistant` MODIFY COLUMN `icon` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '角色图标' AFTER `avatar`;
