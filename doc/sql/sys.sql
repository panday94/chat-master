# oauth2中规定的数据表,需要手动创建,一般项目中提供服务接口插入,参数由用户定义,在请求时会自动查询服务器中对应的参数数据匹配认证
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`
(
    `client_id`               VARCHAR(256) NOT NULL DEFAULT '' COMMENT '客户端id',
    `client_secret`           VARCHAR(256) NULL DEFAULT '' COMMENT '客户端密钥',
    `resource_ids`            VARCHAR(256) NULL DEFAULT '' COMMENT '可访问资源id',
    `scope`                   VARCHAR(256) NULL DEFAULT '' COMMENT '权限范围',
    `authorized_grant_types`  VARCHAR(256) NULL DEFAULT 'password,refresh_token,authorization_code,implicit' COMMENT '授权模式 password,refresh_token,authorization_code,implicit',
    `web_server_redirect_uri` VARCHAR(256) NULL DEFAULT '' COMMENT '授权回调地址',
    `authorities`             VARCHAR(256) NULL DEFAULT '' COMMENT '权限值',
    `access_token_validity`   INT(11) NULL DEFAULT 86400 COMMENT '令牌过期秒数 默认一天',
    `refresh_token_validity`  INT(11) NULL DEFAULT 604800 COMMENT '刷新令牌过期秒数 默认一星期',
    `additional_information`  VARCHAR(4096) NULL DEFAULT '' COMMENT '附加说明',
    `autoapprove`             VARCHAR(256) NULL DEFAULT 'false' COMMENT '自动授权',
    `remark`                  VARCHAR(256) NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT '认证授权表';

-- 系统设置
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`          BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `create_user` VARCHAR(32)  NOT NULL DEFAULT 'System' COMMENT '创建人',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user` VARCHAR(32)  NOT NULL DEFAULT 'System' COMMENT '更新人',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `login_time`  DATETIME              DEFAULT NULL COMMENT '登录时间',
    `username`    VARCHAR(128) NOT NULL DEFAULT '' COMMENT '用户名/账号',
    `password`    VARCHAR(128) NOT NULL DEFAULT '' COMMENT '密码',
    `uid`         VARCHAR(128) NOT NULL DEFAULT '' COMMENT '用户标识',
    `name`        VARCHAR(128) NOT NULL DEFAULT '' COMMENT '姓名',
    `nick_name`   VARCHAR(128) NOT NULL DEFAULT '' COMMENT '昵称',
    `tel`         VARCHAR(32)  NOT NULL DEFAULT '' COMMENT '手机号',
    `avatar`      VARCHAR(128) NOT NULL DEFAULT '' COMMENT '头像',
    `email`       VARCHAR(32)  NOT NULL DEFAULT '' COMMENT '邮箱',
    `gender`      INT(1) NOT NULL DEFAULT -1 COMMENT '性别 0->女;1->男',
    `status`      INT(1) NOT NULL DEFAULT 1 COMMENT '状态 0->禁用;1->启用',
    `admind`      TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否管理员 0->否;1->是',
    `deleted`     INT(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0->未删除;1->已删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `username` (`username`),
    UNIQUE KEY `uid` (`uid`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  ROW_FORMAT = DYNAMIC COMMENT = '系统账号';
-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` (`id`, `username`, `password`, `uid`, `name`, `nick_name`, `tel`, `avatar`, `email`, `gender`,
                        `status`, `admind`, `deleted`)
VALUES (1, 'root', '$2a$10$ZJrHs1/UHizEmXbw1auMs.DR9pal5TVA.HoEQWQph7f63OUHozkbG',
        '2cb15d57-3b30-4b06-a04d-b8d32d234e8d',
        '超级管理员', '超级管理员', '13888888888',
        'https://img1.baidu.com/it/u=1706103831,1363884341&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500',
        '710957166@qq.com', 1,
        1, 1, 0);
INSERT INTO `sys_user` (`id`, `username`, `password`, `uid`, `name`, `nick_name`, `tel`, `avatar`, `email`, `gender`,
                        `status`, `admind`, `deleted`)
VALUES (2, 'admin', '$2a$10$ZJrHs1/UHizEmXbw1auMs.DR9pal5TVA.HoEQWQph7f63OUHozkbG',
        '2cb15d57-3b30-4b06-a04d-b8d32d234e9D',
        '管理员', '管理员', '13888888888',
        'https://img1.baidu.com/it/u=1706103831,1363884341&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500',
        '710957166@qq.com', 1,
        1, 1, 0);
COMMIT;

DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`
(
    `id`          BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `create_user` VARCHAR(32)  NOT NULL DEFAULT 'System' COMMENT '创建人',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user` VARCHAR(32)  NOT NULL DEFAULT 'System' COMMENT '更新人',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `name`        VARCHAR(128) NOT NULL DEFAULT '' COMMENT '部门名称',
    `sys_user_id` BIGINT                DEFAULT 0 COMMENT '部门负责人id',
    `parent_id`   BIGINT       NOT NULL DEFAULT 0 COMMENT '父节点id',
    `tree_path`   VARCHAR(32)  NOT NULL DEFAULT '' COMMENT '父节点路径 如: 1; 1-2; 1-2-3',
    `level`       INT(1) NOT NULL DEFAULT 1 COMMENT '节点等级',
    `sort`        INT(1) NOT NULL DEFAULT 0 COMMENT '排序',
    `status`      INT(1) NOT NULL DEFAULT 1 COMMENT '状态 0->禁用;1->启用',
    `deleted`     INT(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0->未删除;1->已删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  ROW_FORMAT = DYNAMIC COMMENT = '部门';
-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_dept` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `sys_user_id`,
                        `parent_id`, `tree_path`, `level`, `sort`, `status`, `deleted`)
VALUES (1, 'System', '2022-07-21 10:01:52', 'System', '2022-07-21 10:01:52', '集团', 1, 0, '1', 1, 0, 1, 0);
COMMIT;

DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post`
(
    `id`          BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `create_user` VARCHAR(32)  NOT NULL DEFAULT 'System' COMMENT '创建人',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user` VARCHAR(32)  NOT NULL DEFAULT 'System' COMMENT '更新人',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `name`        VARCHAR(128) NOT NULL DEFAULT '' COMMENT '岗位名称',
    `code`        VARCHAR(32)  NOT NULL DEFAULT '' COMMENT '岗位编码',
    `sort`        INT(1) NOT NULL DEFAULT 0 COMMENT '排序',
    `status`      INT(1) NOT NULL DEFAULT 1 COMMENT '状态 0->禁用;1->启用',
    `deleted`     INT(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0->未删除;1->已删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  ROW_FORMAT = DYNAMIC COMMENT = '岗位';

DROP TABLE IF EXISTS `sys_group`;
CREATE TABLE `sys_group`
(
    `id`          BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `create_user` VARCHAR(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
    `create_time` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user` VARCHAR(32) NOT NULL DEFAULT 'System' COMMENT '更新人',
    `update_time` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `name`        VARCHAR(32) NOT NULL DEFAULT '' COMMENT '用户组名称',
    `code`        VARCHAR(32) NOT NULL DEFAULT '' COMMENT '用户组编码',
    `sort`        INT(1) NOT NULL DEFAULT 0 COMMENT '排序',
    `status`      INT(1) NOT NULL DEFAULT 1 COMMENT '状态 0->禁用;1->启用',
    `deleted`     INT(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0->未删除;1->已删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  ROW_FORMAT = DYNAMIC COMMENT = '用户组';

DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `id`          BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `create_user` VARCHAR(32)  NOT NULL DEFAULT 'System' COMMENT '创建人',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user` VARCHAR(32)  NOT NULL DEFAULT 'System' COMMENT '更新人',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `name`        VARCHAR(128) NOT NULL DEFAULT '' COMMENT '名称',
    `code`        VARCHAR(128) NOT NULL DEFAULT '' COMMENT '角色编码',
    `data_scope`  VARCHAR(128) NOT NULL DEFAULT '' COMMENT '数据范围 1：所有数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限；5：仅本人数据权限',
    `remark`      VARCHAR(512) NOT NULL DEFAULT '' COMMENT '备注',
    `sort`        INT(1) NOT NULL DEFAULT 0 COMMENT '排序',
    `status`      INT(1) NOT NULL DEFAULT 1 COMMENT '状态 0->禁用;1->启用',
    `deleted`     INT(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0->未删除;1->已删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  ROW_FORMAT = DYNAMIC COMMENT = '角色';
-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `code`,
                        `data_scope`, `remark`, `sort`, `status`, `deleted`)
VALUES (1, 'System', '2022-02-17 11:29:18', 'System', '2022-02-17 20:32:03', '超级管理员', 'admin', '1', '', 0, 1, 0);
COMMIT;

DROP TABLE IF EXISTS `sys_user_dept`;
CREATE TABLE `sys_user_dept`
(
    `id`          BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `create_user` VARCHAR(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
    `create_time` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `sys_user_id` BIGINT      NOT NULL DEFAULT 0 COMMENT '用户id',
    `dept_id`     BIGINT      NOT NULL DEFAULT 0 COMMENT '用户部门id',
    `status`      INT(1) NOT NULL DEFAULT 1 COMMENT '状态 0->禁用;1->启用',
    PRIMARY KEY (`id`) USING BTREE,
    KEY           `sys_user_id` (`sys_user_id`),
    KEY           `dept_id` (`dept_id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  ROW_FORMAT = DYNAMIC COMMENT = '用户部门关联';
-- ----------------------------
-- Records of sys_user_dept
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_dept` (`id`, `create_user`, `create_time`, `sys_user_id`, `dept_id`, `status`)
VALUES (1, 'System', '2022-07-21 10:03:11', 1, 1, 1);
COMMIT;

DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post`
(
    `id`          BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `create_user` VARCHAR(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
    `create_time` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `sys_user_id` BIGINT      NOT NULL DEFAULT 0 COMMENT '用户id',
    `post_id`     BIGINT      NOT NULL DEFAULT 0 COMMENT '用户岗位id',
    `status`      INT(1) NOT NULL DEFAULT 1 COMMENT '状态 0->禁用;1->启用',
    PRIMARY KEY (`id`) USING BTREE,
    KEY           `sys_user_id` (`sys_user_id`),
    KEY           `post_id` (`post_id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  ROW_FORMAT = DYNAMIC COMMENT = '用户岗位关联';

DROP TABLE IF EXISTS `sys_user_group`;
CREATE TABLE `sys_user_group`
(
    `id`          BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `create_user` VARCHAR(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
    `create_time` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `sys_user_id` BIGINT      NOT NULL DEFAULT 0 COMMENT '用户id',
    `group_id`    BIGINT      NOT NULL DEFAULT 0 COMMENT '用户组id',
    `status`      INT(1) NOT NULL DEFAULT 1 COMMENT '状态 0->禁用;1->启用',
    PRIMARY KEY (`id`) USING BTREE,
    KEY           `sys_user_id` (`sys_user_id`),
    KEY           `group_id` (`group_id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  ROW_FORMAT = DYNAMIC COMMENT = '用户组关联';

DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`
(
    `id`          BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `create_user` VARCHAR(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
    `create_time` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `sys_user_id` BIGINT      NOT NULL DEFAULT 0 COMMENT '用户id',
    `role_id`     BIGINT      NOT NULL DEFAULT 0 COMMENT '角色id',
    `status`      INT(1) NOT NULL DEFAULT 1 COMMENT '状态 0->禁用;1->启用',
    PRIMARY KEY (`id`),
    KEY           `sys_user_id` (`sys_user_id`),
    KEY           `role_id` (`role_id`)
) COMMENT = '用户角色';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` (`id`, `create_user`, `create_time`, `sys_user_id`, `role_id`, `status`)
VALUES (1, 'System', '2022-02-17 15:12:19', 1, 1, 1);
COMMIT;

DROP TABLE IF EXISTS `sys_dept_role`;
CREATE TABLE `sys_dept_role`
(
    `id`          BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `create_user` VARCHAR(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
    `create_time` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `dept_id`     BIGINT      NOT NULL DEFAULT 0 COMMENT '部门id',
    `role_id`     BIGINT      NOT NULL DEFAULT 0 COMMENT '角色id',
    `status`      INT(1) NOT NULL DEFAULT 1 COMMENT '状态 0->禁用;1->启用',
    PRIMARY KEY (`id`),
    KEY           `dept_id` (`dept_id`),
    KEY           `role_id` (`role_id`)
) COMMENT = '部门角色';

DROP TABLE IF EXISTS `sys_group_role`;
CREATE TABLE `sys_group_role`
(
    `id`          BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `create_user` VARCHAR(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
    `create_time` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `group_id`    BIGINT      NOT NULL DEFAULT 0 COMMENT '用户组id',
    `role_id`     BIGINT      NOT NULL DEFAULT 0 COMMENT '角色id',
    `status`      INT(1) NOT NULL DEFAULT 1 COMMENT '状态 0->禁用;1->启用',
    PRIMARY KEY (`id`),
    KEY           `group_id` (`group_id`),
    KEY           `role_id` (`role_id`)
) COMMENT = '用户组角色';

DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource`
(
    `id`          BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `create_user` VARCHAR(32)  NOT NULL DEFAULT 'System' COMMENT '创建人',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user` VARCHAR(32)  NOT NULL DEFAULT 'System' COMMENT '更新人',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `name`        VARCHAR(128) NOT NULL DEFAULT '' COMMENT '资源名称',
    `title`       VARCHAR(128) NOT NULL DEFAULT '' COMMENT '标题名称',
    `code`        VARCHAR(128) NOT NULL DEFAULT '' COMMENT '菜单编码',
    `icon`        VARCHAR(32)  NOT NULL DEFAULT '' COMMENT '菜单图标',
    `path`        VARCHAR(64)  NOT NULL DEFAULT '' COMMENT '路由路径',
    `component`   VARCHAR(64)  NOT NULL DEFAULT '' COMMENT '组件路径',
    `query`       VARCHAR(64)  NOT NULL DEFAULT '' COMMENT '路由参数',
    `perms`       VARCHAR(256) NOT NULL DEFAULT '' COMMENT '权限字符串',
    `parent_id`   BIGINT       NOT NULL DEFAULT 0 COMMENT '父级id',
    `redirect`    int(1) NOT NULL DEFAULT 0 COMMENT '是否重定向 0：否 1: 是',
    `type`        INT(1) NOT NULL DEFAULT 1 COMMENT '类型：1：目录 2: 菜单 3：操作资源',
    `sort`        INT(1) NOT NULL DEFAULT 0 COMMENT '排序',
    `status`      INT(1) NOT NULL DEFAULT 1 COMMENT '状态 0->禁用;1->启用',
    `hidden`      INT(1) NOT NULL DEFAULT 0 COMMENT '是否隐藏 0->不隐藏;1->隐藏',
    `deleted`     INT(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0->未删除;1->已删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  ROW_FORMAT = DYNAMIC COMMENT = '资源';

-- ----------------------------
-- Records of sys_resource
-- ----------------------------
BEGIN;
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1, 'System', '2022-08-04 15:31:10', 'root', '2022-11-12 15:11:00', '系统管理', '', '', 'system', 'sys', '', '',
        '', 0, 0, 1, 5, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (2, 'System', '2022-08-04 15:31:10', 'root', '2022-08-04 15:31:10', '系统监控', '', '', 'monitor', 'monitor', '',
        '', '', 0, 0, 1, 6, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (3, 'System', '2022-08-04 15:31:10', 'root', '2023-04-28 08:05:40', '系统工具', '', '', 'tool', 'tool', '', '',
        '', 0, 0, 1, 7, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (4, 'System', '2022-08-04 15:31:10', 'admin', '2022-08-04 15:31:10', 'ChatMASTER', '', '', 'guide',
        'https://chat.panday94.xyz', '', '', '', 0, 1, 1, 8, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (100, 'System', '2022-08-04 15:31:10', 'admin', '2023-04-19 16:23:21', '用户管理', '', '', 'user', 'user',
        'sys/user/index', '', '', 1, 0, 2, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (101, 'System', '2022-08-04 15:31:10', 'admin', '2023-04-19 16:23:21', '角色管理', '', '', 'peoples', 'role',
        'sys/role/index', '', '', 1, 0, 2, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (102, 'System', '2022-08-04 15:31:10', 'admin', '2022-11-12 15:09:48', '菜单管理', '', '', 'tree-table',
        'resource', 'sys/resource/index', '', '', 1, 0, 2, 5, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (103, 'System', '2022-08-04 15:31:10', 'admin', '2023-04-19 16:23:21', '部门管理', '', '', 'tree', 'dept',
        'sys/dept/index', '', '', 1, 0, 2, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (104, 'System', '2022-08-04 15:31:10', 'admin', '2023-04-19 16:23:21', '岗位管理', '', '', 'post', 'post',
        'sys/post/index', '', '', 1, 0, 2, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (105, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:02:01', '字典管理', '', '', 'dict', 'dict',
        'sys/dict/index', '', '', 1, 0, 2, 6, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (106, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:02:06', '参数设置', '', '', 'edit', 'config',
        'sys/config/index', '', '', 1, 0, 2, 7, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (107, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:02:13', '通知公告', '', '', 'message', 'notice',
        'sys/notice/index', '', '', 1, 0, 2, 8, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (108, 'System', '2022-08-04 15:31:10', 'System', '2022-08-04 15:31:10', '日志列表', '', '', 'log', 'log', '', '',
        '', 2, 0, 1, 7, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (109, 'System', '2022-08-04 15:31:10', 'System', '2022-08-04 15:31:10', '在线用户', '', '', 'online', 'online',
        'monitor/online/index', '', '', 2, 0, 2, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (110, 'System', '2022-08-04 15:31:10', 'System', '2022-08-04 15:31:10', '定时任务', '', '', 'job', 'job',
        'monitor/job/index', '', '', 2, 0, 2, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (111, 'System', '2022-08-04 15:31:10', 'System', '2022-08-04 15:31:10', '数据监控', '', '', 'druid', 'druid',
        'monitor/druid/index', '', '', 2, 0, 2, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (112, 'System', '2022-08-04 15:31:10', 'System', '2022-08-04 15:31:10', '服务监控', '', '', 'server', 'server',
        'monitor/server/index', '', '', 2, 0, 2, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (113, 'System', '2022-08-04 15:31:10', 'System', '2022-08-04 15:31:10', '缓存监控', '', '', 'redis', 'cache',
        'monitor/cache/index', '', '', 2, 0, 2, 5, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (114, 'System', '2022-08-04 15:31:10', 'System', '2022-08-04 15:31:10', '缓存列表', '', '', 'redis-list',
        'cacheList', 'monitor/cache/list', '', '', 2, 0, 2, 6, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (115, 'System', '2022-08-04 15:31:10', 'System', '2023-04-28 08:05:40', '表单构建', '', '', 'build', 'build',
        'tool/build/index', '', '', 3, 0, 2, 1, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (116, 'System', '2022-08-04 15:31:10', 'System', '2023-04-28 08:05:40', '代码生成', '', '', 'code', 'gen',
        'tool/gen/index', '', '', 3, 0, 2, 2, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (117, 'System', '2022-08-04 15:31:10', 'System', '2023-04-28 08:05:40', '接口地址', '', '', 'guide',
        'http://yapi.dinggehuo.com', 'tool/swagger/index', '', '', 3, 1, 2, 3, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (500, 'System', '2022-08-04 15:31:10', 'System', '2022-08-04 15:31:10', '操作日志', '', '', 'form', 'operlog',
        'monitor/operlog/index', '', '', 108, 0, 2, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (501, 'System', '2022-08-04 15:31:10', 'System', '2022-08-04 15:31:10', '登录日志', '', '', 'logininfor',
        'logininfor', 'monitor/logininfor/index', '', '', 108, 0, 2, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1000, 'System', '2022-08-04 15:31:10', 'admin', '2022-11-12 15:03:25', '用户列表', '', '', '#', '#', '', '',
        'sys:user:list', 100, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1001, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '用户查询', '', '', '#', '#', '', '',
        'sys:user:query', 100, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1002, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '用户新增', '', '', '#', '#', '', '',
        'sys:user:save', 100, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1003, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '用户修改', '', '', '#', '#', '', '',
        'sys:user:update', 100, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1004, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '用户删除', '', '', '#', '#', '', '',
        'sys:user:remove', 100, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1005, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '用户导出', '', '', '#', '#', '', '',
        'sys:user:export', 100, 0, 3, 6, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1006, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '用户导入', '', '', '#', '#', '', '',
        'sys:user:import', 100, 0, 3, 7, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1007, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '重置密码', '', '', '#', '#', '', '',
        'sys:user:resetPwd', 100, 0, 3, 8, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1008, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '角色列表', '', '', '#', '#', '', '',
        'sys:role:list', 101, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1009, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '角色查询', '', '', '#', '#', '', '',
        'sys:role:query', 101, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1010, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '角色新增', '', '', '#', '#', '', '',
        'sys:role:save', 101, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1011, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '角色修改', '', '', '#', '#', '', '',
        'sys:role:update', 101, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1012, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '角色删除', '', '', '#', '#', '', '',
        'sys:role:remove', 101, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1013, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '角色导出', '', '', '#', '#', '', '',
        'sys:role:export', 101, 0, 3, 6, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1014, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '菜单列表', '', '', '#', '#', '', '',
        'sys:resource:list', 102, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1015, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '菜单查询', '', '', '#', '#', '', '',
        'sys:resource:query', 102, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1016, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '菜单新增', '', '', '#', '#', '', '',
        'sys:resource:save', 102, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1017, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '菜单修改', '', '', '#', '#', '', '',
        'sys:resource:update', 102, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1018, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '菜单删除', '', '', '#', '#', '', '',
        'sys:resource:remove', 102, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1019, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '部门列表', '', '', '#', '#', '', '',
        'sys:dept:list', 103, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1020, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '部门查询', '', '', '#', '#', '', '',
        'sys:dept:query', 103, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1021, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '部门新增', '', '', '#', '#', '', '',
        'sys:dept:save', 103, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1022, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '部门修改', '', '', '#', '#', '', '',
        'sys:dept:update', 103, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1023, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '部门删除', '', '', '#', '#', '', '',
        'sys:dept:remove', 103, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1024, 'System', '2022-08-04 15:31:10', 'admin', '2022-11-12 15:03:25', '岗位列表', '', '', '#', '#', '', '',
        'sys:post:list', 1024, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1025, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '岗位查询', '', '', '#', '#', '', '',
        'sys:post:query', 104, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1026, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '岗位新增', '', '', '#', '#', '', '',
        'sys:post:save', 104, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1027, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '岗位修改', '', '', '#', '#', '', '',
        'sys:post:update', 104, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1028, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '岗位删除', '', '', '#', '#', '', '',
        'sys:post:remove', 104, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1029, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '岗位导出', '', '', '#', '#', '', '',
        'sys:post:export', 104, 0, 3, 6, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1030, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '字典列表', '', '', '#', '#', '', '',
        'sys:dict:list', 105, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1031, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '字典查询', '', '', '#', '#', '', '',
        'sys:dict:query', 105, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1032, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '字典新增', '', '', '#', '#', '', '',
        'sys:dict:save', 105, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1033, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '字典修改', '', '', '#', '#', '', '',
        'sys:dict:update', 105, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1034, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '字典删除', '', '', '#', '#', '', '',
        'sys:dict:remove', 105, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1035, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '字典导出', '', '', '#', '#', '', '',
        'sys:dict:export', 105, 0, 3, 6, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1036, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '参数列表', '', '', '#', '#', '', '',
        'sys:config:list', 106, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1037, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '参数查询', '', '', '#', '#', '', '',
        'sys:config:query', 106, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1038, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '参数新增', '', '', '#', '#', '', '',
        'sys:config:save', 106, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1039, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '参数修改', '', '', '#', '#', '', '',
        'sys:config:update', 106, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1040, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '参数删除', '', '', '#', '#', '', '',
        'sys:config:remove', 106, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1041, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '参数导出', '', '', '#', '#', '', '',
        'sys:config:export', 106, 0, 3, 6, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1042, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '公告列表', '', '', '#', '#', '', '',
        'sys:notice:list', 107, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1043, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '公告查询', '', '', '#', '#', '', '',
        'sys:notice:query', 107, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1044, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '公告新增', '', '', '#', '#', '', '',
        'sys:notice:save', 107, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1045, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '公告修改', '', '', '#', '#', '', '',
        'sys:notice:update', 107, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1046, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '公告删除', '', '', '#', '#', '', '',
        'sys:notice:remove', 107, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1047, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '操作日志列表', '', '', '#', '#', '',
        '', 'sys:log:list', 500, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1048, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '操作清空', '', '', '#', '#', '', '',
        'sys:log:clean', 500, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1049, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '操作删除', '', '', '#', '#', '', '',
        'sys:log:remove', 500, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1050, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '日志导出', '', '', '#', '#', '', '',
        'sys:log:export', 500, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1051, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '登录日志列表', '', '', '#', '#', '',
        '', 'sys:loginlog:list', 501, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1052, 'System', '2022-08-04 15:31:10', 'admin', '2022-11-12 15:03:25', '登录清空', '', '', '#', '#', '', '',
        'sys:loginlog:clean', 501, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1053, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '登录删除', '', '', '#', '#', '', '',
        'sys:loginlog:remove', 501, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1054, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '日志导出', '', '', '#', '#', '', '',
        'sys:loginlog:export', 501, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1055, 'System', '2022-08-04 15:31:10', 'System', '2022-08-04 15:31:10', '在线列表', '', '', '#', '#', '', '',
        'monitor:online:list', 109, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1056, 'System', '2022-08-04 15:31:10', 'System', '2022-08-12 15:12:05', '强退用户', '', '', '#', '#', '', '',
        'monitor:online:forceLogout', 109, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1057, 'System', '2022-08-04 15:31:10', 'System', '2022-08-12 15:12:09', '任务列表', '', '', '#', '#', '', '',
        'monitor:job:list', 110, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1058, 'System', '2022-08-04 15:31:10', 'System', '2022-08-12 15:12:13', '任务查询', '', '', '#', '#', '', '',
        'monitor:job:query', 110, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1059, 'System', '2022-08-04 15:31:10', 'System', '2022-08-12 15:12:16', '任务新增', '', '', '#', '#', '', '',
        'monitor:job:save', 110, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1060, 'System', '2022-08-04 15:31:10', 'System', '2022-08-12 15:12:19', '任务修改', '', '', '#', '#', '', '',
        'monitor:job:update', 110, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1061, 'System', '2022-08-04 15:31:10', 'System', '2022-08-12 15:12:22', '任务删除', '', '', '#', '#', '', '',
        'monitor:job:remove', 110, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1062, 'System', '2022-08-04 15:31:10', 'System', '2022-08-12 15:12:25', '状态修改', '', '', '#', '#', '', '',
        'monitor:job:changeStatus', 110, 0, 3, 6, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1063, 'System', '2022-08-04 15:31:10', 'System', '2022-08-12 15:12:27', '任务导出', '', '', '#', '#', '', '',
        'monitor:job:export', 110, 0, 3, 7, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1064, 'System', '2022-08-04 15:31:10', 'System', '2023-04-28 08:05:44', '生成列表', '', '', '#', '#', '', '',
        'tool:gen:list', 116, 0, 3, 1, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1065, 'System', '2022-08-04 15:31:10', 'System', '2023-04-28 08:05:46', '生成查询', '', '', '#', '#', '', '',
        'tool:gen:query', 116, 0, 3, 2, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1066, 'System', '2022-08-04 15:31:10', 'System', '2023-04-28 08:05:49', '生成修改', '', '', '#', '#', '', '',
        'tool:gen:update', 116, 0, 3, 3, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1067, 'System', '2022-08-04 15:31:10', 'System', '2023-04-28 08:05:52', '生成删除', '', '', '#', '#', '', '',
        'tool:gen:remove', 116, 0, 3, 4, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1068, 'System', '2022-08-04 15:31:10', 'System', '2023-04-28 08:05:55', '导入代码', '', '', '#', '#', '', '',
        'tool:gen:import', 116, 0, 3, 5, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1069, 'System', '2022-08-04 15:31:10', 'System', '2023-04-28 08:06:01', '预览代码', '', '', '#', '#', '', '',
        'tool:gen:preview', 116, 0, 3, 6, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1070, 'System', '2022-08-04 15:31:10', 'System', '2023-04-28 08:05:58', '生成代码', '', '', '#', '#', '', '',
        'tool:gen:code', 116, 0, 3, 7, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1071, 'root', '2023-04-28 15:27:20', 'admin', '2023-04-28 15:27:20', '聊天管理', '', '', 'message', 'chat', '',
        '', '', 0, 0, 1, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1072, 'root', '2023-04-28 15:28:10', 'root', '2023-04-28 15:28:10', '聊天管理', '', '', '', 'chat',
        'gpt/chat/index', '', '', 1071, 0, 2, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1073, 'root', '2023-04-28 15:28:40', 'root', '2023-04-28 15:28:40', '消息管理', '', '', '', 'chat-message',
        'gpt/chat-message/index', '', '', 1071, 0, 2, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1074, 'root', '2023-04-28 15:33:57', 'root', '2023-04-28 15:33:57', '订单管理', '', '', 'money', 'order', '',
        '', '', 0, 0, 1, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1075, 'root', '2023-04-28 15:34:43', 'root', '2023-04-28 15:34:43', '订单管理', '', '', '', 'order',
        'gpt/order/index', '', '', 1074, 0, 2, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1076, 'root', '2023-04-28 15:35:23', 'root', '2023-04-28 15:35:23', '兑换码管理', '', '', '', 'redemption',
        'gpt/redemption/index', '', '', 1074, 0, 2, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1077, 'root', '2023-04-28 15:35:55', 'root', '2023-04-28 15:35:55', '会员中心', '', '', 'peoples', 'member', '',
        '', '', 0, 0, 1, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1078, 'root', '2023-04-28 15:36:12', 'root', '2023-04-28 15:36:12', '套餐配置', '', '', '', 'comb',
        'gpt/comb/index', '', '', 1077, 0, 1, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1079, 'root', '2023-04-28 15:36:28', 'root', '2023-04-28 15:36:28', '会员列表', '', '', '', 'member',
        'gpt/member/index', '', '', 1077, 0, 2, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1080, 'root', '2023-04-28 15:37:10', 'root', '2023-04-28 15:37:10', '配置中心', '', '', 'tool', 'config', '',
        '', '', 0, 0, 1, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1081, 'root', '2023-04-28 15:38:41', 'root', '2023-04-28 15:38:41', 'token管理', '', '', '', 'openkey',
        'gpt/openkey/index', '', '', 1080, 0, 2, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1082, 'root', '2023-04-28 15:43:30', 'root', '2023-04-28 15:43:30', '内容管理', '', '', '', 'content',
        'gpt/content/index', '', '', 1080, 0, 2, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1083, 'root', '2023-04-28 15:44:20', 'admin', '2023-04-28 15:44:20', '站点配置', '', '', '', 'base',
        'sys/base-config/index', '', '', 1080, 0, 2, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1084, 'root', '2023-04-28 15:44:35', 'root', '2023-12-26 06:56:53', '缩略图配置', '', '', '', 'upload',
        'gpt/upload-config/index', '', '', 1080, 0, 2, 5, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1085, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:04:45', 'AI助理功能列表', '', '', '#', '#', '',
        '', 'gpt:assistant:list', 1169, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1086, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:04:45', 'AI助理功能查询', '', '', '#', '#', '',
        '', 'gpt:assistant:query', 1169, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1087, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:04:45', 'AI助理功能新增', '', '', '#', '#', '',
        '', 'gpt:assistant:save', 1169, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1088, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:04:45', 'AI助理功能修改', '', '', '#', '#', '',
        '', 'gpt:assistant:update', 1169, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1089, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:04:45', 'AI助理功能删除', '', '', '#', '#', '',
        '', 'gpt:assistant:remove', 1169, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1090, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:04:45', 'AI助理功能审核', '', '', '#', '#', '',
        '', 'gpt:assistant:audit', 1169, 0, 3, 6, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1091, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:04:45', 'AI助理功能导出', '', '', '#', '#', '',
        '', 'gpt:assistant:export', 1169, 0, 3, 7, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1092, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:05', '基础配置列表', '', '', '#', '#', '',
        '', 'sys:base:config:list', 1083, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1093, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:05', '基础配置查询', '', '', '#', '#', '',
        '', 'sys:base:config:query', 1083, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1094, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:05', '基础配置新增', '', '', '#', '#', '',
        '', 'sys:base:config:save', 1083, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1095, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:06', '基础配置修改', '', '', '#', '#', '',
        '', 'sys:base:config:update', 1083, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1096, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:06', '基础配置删除', '', '', '#', '#', '',
        '', 'sys:base:config:remove', 1083, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1097, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:06', '基础配置审核', '', '', '#', '#', '',
        '', 'sys:base:config:audit', 1083, 0, 3, 6, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1098, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:06', '基础配置导出', '', '', '#', '#', '',
        '', 'sys:base:config:export', 1083, 0, 3, 7, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1099, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:21', '聊天摘要列表', '', '', '#', '#', '',
        '', 'gpt:chat:list', 1072, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1100, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:21', '聊天摘要查询', '', '', '#', '#', '',
        '', 'gpt:chat:query', 1072, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1101, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:21', '聊天摘要新增', '', '', '#', '#', '',
        '', 'gpt:chat:save', 1072, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1102, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:21', '聊天摘要修改', '', '', '#', '#', '',
        '', 'gpt:chat:update', 1072, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1103, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:21', '聊天摘要删除', '', '', '#', '#', '',
        '', 'gpt:chat:remove', 1072, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1104, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:21', '聊天摘要审核', '', '', '#', '#', '',
        '', 'gpt:chat:audit', 1072, 0, 3, 6, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1105, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:21', '聊天摘要导出', '', '', '#', '#', '',
        '', 'gpt:chat:export', 1072, 0, 3, 7, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1106, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:32', '对话消息列表', '', '', '#', '#', '',
        '', 'gpt:chat:message:list', 1073, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1107, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:32', '对话消息查询', '', '', '#', '#', '',
        '', 'gpt:chat:message:query', 1073, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1108, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:32', '对话消息新增', '', '', '#', '#', '',
        '', 'gpt:chat:message:save', 1073, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1109, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:32', '对话消息修改', '', '', '#', '#', '',
        '', 'gpt:chat:message:update', 1073, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1110, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:32', '对话消息删除', '', '', '#', '#', '',
        '', 'gpt:chat:message:remove', 1073, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1111, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:32', '对话消息审核', '', '', '#', '#', '',
        '', 'gpt:chat:message:audit', 1073, 0, 3, 6, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1112, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:32', '对话消息导出', '', '', '#', '#', '',
        '', 'gpt:chat:message:export', 1073, 0, 3, 7, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1113, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:48', '会员套餐列表', '', '', '#', '#', '',
        '', 'gpt:comb:list', 1078, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1114, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:48', '会员套餐查询', '', '', '#', '#', '',
        '', 'gpt:comb:query', 1078, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1115, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:48', '会员套餐新增', '', '', '#', '#', '',
        '', 'gpt:comb:save', 1078, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1116, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:48', '会员套餐修改', '', '', '#', '#', '',
        '', 'gpt:comb:update', 1078, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1117, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:48', '会员套餐删除', '', '', '#', '#', '',
        '', 'gpt:comb:remove', 1078, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1118, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:48', '会员套餐审核', '', '', '#', '#', '',
        '', 'gpt:comb:audit', 1078, 0, 3, 6, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1119, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:48', '会员套餐导出', '', '', '#', '#', '',
        '', 'gpt:comb:export', 1078, 0, 3, 7, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1120, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:00:29', '内容管理列表', '', '', '#', '#', '',
        '', 'gpt:content:list', 1082, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1121, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:00:29', '内容管理查询', '', '', '#', '#', '',
        '', 'gpt:content:query', 1082, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1122, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:00:29', '内容管理新增', '', '', '#', '#', '',
        '', 'gpt:content:save', 1082, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1123, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:00:29', '内容管理修改', '', '', '#', '#', '',
        '', 'gpt:content:update', 1082, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1124, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:00:29', '内容管理删除', '', '', '#', '#', '',
        '', 'gpt:content:remove', 1082, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1125, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:00:29', '内容管理审核', '', '', '#', '#', '',
        '', 'gpt:content:audit', 1082, 0, 3, 6, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1126, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:00:29', '内容管理导出', '', '', '#', '#', '',
        '', 'gpt:content:export', 1082, 0, 3, 7, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1127, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:57:11', '文件管理列表', '', '', '#', '#', '',
        '', 'gpt:file:list', 1170, 0, 3, 1, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1128, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:57:13', '文件管理查询', '', '', '#', '#', '',
        '', 'gpt:file:query', 1170, 0, 3, 2, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1129, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:57:16', '文件管理新增', '', '', '#', '#', '',
        '', 'gpt:file:save', 1170, 0, 3, 3, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1130, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:56:56', '文件管理修改', '', '', '#', '#', '',
        '', 'gpt:file:update', 1170, 0, 3, 4, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1131, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:56:59', '文件管理删除', '', '', '#', '#', '',
        '', 'gpt:file:remove', 1170, 0, 3, 5, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1132, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:57:02', '文件管理审核', '', '', '#', '#', '',
        '', 'gpt:file:audit', 1170, 0, 3, 6, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1133, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:57:05', '文件管理导出', '', '', '#', '#', '',
        '', 'gpt:file:export', 1170, 0, 3, 7, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1134, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:39:40', '会员用户列表', '', '', '#', '#', '',
        '', 'gpt:user:list', 1079, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1135, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:39:43', '会员用户查询', '', '', '#', '#', '',
        '', 'gpt:user:query', 1079, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1136, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:39:46', '会员用户新增', '', '', '#', '#', '',
        '', 'gpt:user:save', 1079, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1137, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:39:50', '会员用户修改', '', '', '#', '#', '',
        '', 'gpt:user:update', 1079, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1138, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:39:52', '会员用户删除', '', '', '#', '#', '',
        '', 'gpt:user:remove', 1079, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1139, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:39:55', '会员用户审核', '', '', '#', '#', '',
        '', 'gpt:user:audit', 1079, 0, 3, 6, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1140, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:40:00', '会员用户导出', '', '', '#', '#', '',
        '', 'gpt:user:export', 1079, 0, 3, 7, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1141, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:01:25', 'openai token列表', '', '', '#', '#',
        '', '', 'gpt:openkey:list', 1081, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1142, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:01:25', 'openai token查询', '', '', '#', '#',
        '', '', 'gpt:openkey:query', 1081, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1143, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:01:25', 'openai token新增', '', '', '#', '#',
        '', '', 'gpt:openkey:save', 1081, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1144, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:01:25', 'openai token修改', '', '', '#', '#',
        '', '', 'gpt:openkey:update', 1081, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1145, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:01:25', 'openai token删除', '', '', '#', '#',
        '', '', 'gpt:openkey:remove', 1081, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1146, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:01:25', 'openai token审核', '', '', '#', '#',
        '', '', 'gpt:openkey:audit', 1081, 0, 3, 6, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1147, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:01:25', 'openai token导出', '', '', '#', '#',
        '', '', 'gpt:openkey:export', 1081, 0, 3, 7, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1148, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:01:41', '订单列表', '', '', '#', '#', '', '',
        'gpt:order:list', 1075, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1149, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:01:41', '订单查询', '', '', '#', '#', '', '',
        'gpt:order:query', 1075, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1150, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:01:41', '订单新增', '', '', '#', '#', '', '',
        'gpt:order:save', 1075, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1151, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:01:41', '订单修改', '', '', '#', '#', '', '',
        'gpt:order:update', 1075, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1152, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:01:41', '订单删除', '', '', '#', '#', '', '',
        'gpt:order:remove', 1075, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1153, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:01:41', '订单审核', '', '', '#', '#', '', '',
        'gpt:order:audit', 1075, 0, 3, 6, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1154, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:01:41', '订单导出', '', '', '#', '#', '', '',
        'gpt:order:export', 1075, 0, 3, 7, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1155, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:02:05', '兑换码列表', '', '', '#', '#', '', '',
        'gpt:redemption:list', 1076, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1156, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:02:05', '兑换码查询', '', '', '#', '#', '', '',
        'gpt:redemption:query', 1076, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1157, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:02:05', '兑换码新增', '', '', '#', '#', '', '',
        'gpt:redemption:save', 1076, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1158, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:02:05', '兑换码修改', '', '', '#', '#', '', '',
        'gpt:redemption:update', 1076, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1159, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:02:05', '兑换码删除', '', '', '#', '#', '', '',
        'gpt:redemption:remove', 1076, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1160, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:02:05', '兑换码审核', '', '', '#', '#', '', '',
        'gpt:redemption:audit', 1076, 0, 3, 6, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1161, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:02:05', '兑换码导出', '', '', '#', '#', '', '',
        'gpt:redemption:export', 1076, 0, 3, 7, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1162, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:56:28', '缩略图配置列表', '', '', '#', '#', '',
        '', 'gpt:upload:config:list', 1084, 0, 3, 1, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1163, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:56:30', '缩略图配置查询', '', '', '#', '#', '',
        '', 'gpt:upload:config:query', 1084, 0, 3, 2, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1164, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:56:32', '缩略图配置新增', '', '', '#', '#', '',
        '', 'gpt:upload:config:save', 1084, 0, 3, 3, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1165, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:56:35', '缩略图配置修改', '', '', '#', '#', '',
        '', 'gpt:upload:config:update', 1084, 0, 3, 4, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1166, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:56:43', '缩略图配置删除', '', '', '#', '#', '',
        '', 'gpt:upload:config:remove', 1084, 0, 3, 5, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1167, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:56:37', '缩略图配置审核', '', '', '#', '#', '',
        '', 'gpt:upload:config:audit', 1084, 0, 3, 6, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1168, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:56:51', '缩略图配置导出', '', '', '#', '#', '',
        '', 'gpt:upload:config:export', 1084, 0, 3, 7, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1169, 'root', '2023-04-28 16:04:01', 'admin', '2023-04-28 16:04:01', '助手管理', '', '', '', 'index',
        'gpt/assistant/index', '', '', 1171, 0, 2, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1170, 'root', '2023-04-28 16:04:26', 'System', '2023-12-26 06:56:39', '文件列表', '', '', '', 'file',
        'gpt/file/index', '', '', 1080, 0, 2, 6, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1171, 'admin', '2023-11-22 11:05:52', 'admin', '2023-11-22 11:05:52', '助手中心', '', '', 'example',
        'assistant', '', '', '', 0, 0, 1, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1172, 'admin', '2023-11-22 11:07:51', 'System', '2023-11-22 11:07:51', '助手分类', '', '', '', 'type',
        'gpt/assistant-type/index', '', '', 1171, 0, 2, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1173, 'System', '2023-11-22 03:10:49', 'System', '2023-11-22 03:10:49', '助手分类列表', '', '', '#', '#', '',
        '', 'gpt:assistant:type:list', 1172, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1174, 'System', '2023-11-22 03:10:49', 'System', '2023-11-22 03:10:49', '助手分类查询', '', '', '#', '#', '',
        '', 'gpt:assistant:type:query', 1172, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1175, 'System', '2023-11-22 03:10:49', 'System', '2023-11-22 03:10:49', '助手分类新增', '', '', '#', '#', '',
        '', 'gpt:assistant:type:save', 1172, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1176, 'System', '2023-11-22 03:10:49', 'System', '2023-11-22 03:10:49', '助手分类修改', '', '', '#', '#', '',
        '', 'gpt:assistant:type:update', 1172, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1177, 'System', '2023-11-22 03:10:49', 'System', '2023-11-22 03:10:49', '助手分类删除', '', '', '#', '#', '',
        '', 'gpt:assistant:type:remove', 1172, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1178, 'System', '2023-11-22 03:10:49', 'System', '2023-11-22 03:10:49', '助手分类审核', '', '', '#', '#', '',
        '', 'gpt:assistant:type:audit', 1172, 0, 3, 6, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`,
                            `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`,
                            `status`, `hidden`, `deleted`)
VALUES (1179, 'System', '2023-11-22 03:10:49', 'System', '2023-11-22 03:10:49', '助手分类导出', '', '', '#', '#', '',
        '', 'gpt:assistant:type:export', 1172, 0, 3, 7, 1, 0, 0);
COMMIT;

DROP TABLE IF EXISTS `sys_role_resource`;
CREATE TABLE `sys_role_resource`
(
    `id`          BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `create_user` VARCHAR(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
    `create_time` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user` VARCHAR(32) NOT NULL DEFAULT 'System' COMMENT '更新人',
    `update_time` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `role_id`     BIGINT(20) NOT NULL DEFAULT 0 COMMENT '角色id',
    `resource_id` BIGINT(20) NOT NULL DEFAULT 0 COMMENT '资源权限id',
    `status`      INT(1) NOT NULL DEFAULT 1 COMMENT '状态 0->禁用;1->启用',
    PRIMARY KEY (`id`) USING BTREE,
    KEY           `role_id` (`role_id`),
    KEY           `resource_id` (`resource_id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  ROW_FORMAT = DYNAMIC COMMENT = '角色权限';

DROP TABLE IF EXISTS sys_dict_type;
CREATE TABLE sys_dict_type
(
    `id`          BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `create_user` VARCHAR(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
    `create_time` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user` VARCHAR(32) NOT NULL DEFAULT 'System' COMMENT '更新人',
    `update_time` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `name`        VARCHAR(128)         DEFAULT '' COMMENT '字典名称',
    `type`        VARCHAR(128)         DEFAULT '' COMMENT '字典类型',
    `status`      INT(1) DEFAULT 0 COMMENT '状态 0->禁用;1->启用',
    `deleted`     INT(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0->未删除;1->已删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE (`type`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = DYNAMIC COMMENT = '字典类型表';
-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `type`, `status`,
                             `deleted`)
VALUES (1, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '用户性别', 'sys_user_sex', 1, 0);
INSERT INTO `sys_dict_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `type`, `status`,
                             `deleted`)
VALUES (2, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '菜单状态', 'sys_show_hide', 1, 0);
INSERT INTO `sys_dict_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `type`, `status`,
                             `deleted`)
VALUES (3, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '系统开关', 'sys_normal_disable', 1, 0);
INSERT INTO `sys_dict_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `type`, `status`,
                             `deleted`)
VALUES (4, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '任务状态', 'sys_job_status', 1, 0);
INSERT INTO `sys_dict_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `type`, `status`,
                             `deleted`)
VALUES (5, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '任务分组', 'sys_job_group', 1, 0);
INSERT INTO `sys_dict_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `type`, `status`,
                             `deleted`)
VALUES (6, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '系统是否', 'sys_yes_no', 1, 0);
INSERT INTO `sys_dict_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `type`, `status`,
                             `deleted`)
VALUES (7, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '通知类型', 'sys_notice_type', 1, 0);
INSERT INTO `sys_dict_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `type`, `status`,
                             `deleted`)
VALUES (8, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '通知状态', 'sys_notice_status', 1, 0);
INSERT INTO `sys_dict_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `type`, `status`,
                             `deleted`)
VALUES (9, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '操作类型', 'sys_oper_type', 1, 0);
INSERT INTO `sys_dict_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `type`, `status`,
                             `deleted`)
VALUES (10, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '系统状态', 'sys_common_status', 1, 0);
INSERT INTO `chat_gpt`.`sys_dict_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`,
                                        `type`, `status`, `deleted`)
VALUES (11, 'admin', '2023-05-04 11:24:39', 'System', '2023-05-04 11:24:39', '内容类型', 'gpt_content_type', 1, 0);
INSERT INTO `chat_gpt`.`sys_dict_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`,
                                        `type`, `status`, `deleted`)
VALUES (12, 'admin', '2023-05-04 11:32:35', 'System', '2023-05-04 11:32:35', '套餐类型', 'gpt_comb_type', 1, 0);
INSERT INTO `chat_gpt`.`sys_dict_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`,
                                        `type`, `status`, `deleted`)
VALUES (13, 'admin', '2023-05-06 11:55:35', 'System', '2023-05-06 11:55:35', '用户类型', 'gpt_member_type', 1, 0);
INSERT INTO `chat_gpt`.`sys_dict_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`,
                                        `type`, `status`, `deleted`)
VALUES (14, 'admin', '2023-05-06 11:57:25', 'System', '2023-05-06 11:57:25', '聊天状态', 'gpt_chat_status', 1, 0);
INSERT INTO `chat_gpt`.`sys_dict_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`,
                                        `type`, `status`, `deleted`)
VALUES (15, 'admin', '2023-09-06 15:32:46', 'admin', '2023-09-06 15:32:46', 'gpt模型类型', 'gpt_model_type', 1, 0);
INSERT INTO `chat_gpt`.`sys_dict_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`,
                                        `type`, `status`, `deleted`)
VALUES (16, 'admin', '2024-01-22 09:15:45', 'admin', '2024-01-22 09:15:45', '上传类型', 'sys_oss_type', 1, 0);
INSERT INTO `chat_gpt`.`sys_dict_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`,
                                        `type`, `status`, `deleted`)
VALUES (17, 'admin', '2024-01-22 09:29:29', 'System', '2024-01-22 09:29:29', '短信类型', 'sys_sms_type', 1, 0);
COMMIT;

DROP TABLE IF EXISTS sys_dict;
CREATE TABLE sys_dict
(
    `id`          BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `create_user` VARCHAR(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
    `create_time` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user` VARCHAR(32) NOT NULL DEFAULT 'System' COMMENT '更新人',
    `update_time` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `label`       VARCHAR(128)         default '' comment '字典标签',
    `value`       VARCHAR(128)         default '' comment '字典键值',
    `dict_type`   VARCHAR(128)         default '' comment '字典类型',
    `css_class`   VARCHAR(128)         default '' comment '样式属性（其他样式扩展）',
    `list_class`  VARCHAR(128)         default '' comment '表格回显样式',
    `is_default`  INT(1) default 0 comment '是否默认 0->否 1->是',
    `sort`        INT(1) default 0 comment '字典排序',
    `status`      INT(1) default 1 comment '状态 0->禁用;1->启用',
    `deleted`     INT(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0->未删除;1->已删除',
    `remark`      VARCHAR(512)         DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE,
    KEY           `dict_type` (`dict_type`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = DYNAMIC COMMENT = '字典数据表';
-- ----------------------------
-- Records of sys_dict
-- ----------------------------
BEGIN;
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (1, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '男', '1', 'sys_user_sex', '', '', 1, 1, 1,
        0, '性别男');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (2, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '女', '0', 'sys_user_sex', '', '', 0, 2, 1,
        0, '性别女');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (3, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-15 14:35:41', '未知', '-1', 'sys_user_sex', '', '', 0, 3,
        1, 0, '性别未知');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (4, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-15 19:46:07', '显示', '0', 'sys_show_hide', '', 'primary',
        1, 1, 1, 0, '显示菜单');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (5, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-15 19:46:11', '隐藏', '1', 'sys_show_hide', '', 'danger',
        0, 2, 1, 0, '隐藏菜单');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (6, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-15 14:35:12', '启用', '1', 'sys_normal_disable', '',
        'primary', 1, 1, 1, 0, '正常状态');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (7, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-15 14:35:24', '禁用', '0', 'sys_normal_disable', '',
        'danger', 0, 2, 1, 0, '停用状态');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (8, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-15 19:46:29', '正常', '1', 'sys_job_status', '', 'primary',
        1, 1, 1, 0, '正常状态');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (9, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-15 19:46:37', '暂停', '0', 'sys_job_status', '', 'danger',
        0, 2, 1, 0, '停用状态');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (10, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '默认', 'DEFAULT', 'sys_job_group', '', '',
        1, 1, 1, 0, '默认分组');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (11, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '系统', 'SYSTEM', 'sys_job_group', '', '',
        0, 2, 1, 0, '系统分组');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (12, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-20 08:11:41', '是', '1', 'sys_yes_no', '', 'primary', 1,
        1, 1, 0, '系统默认是');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (13, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-20 08:11:45', '否', '0', 'sys_yes_no', '', 'danger', 0, 2,
        1, 0, '系统默认否');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (14, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '通知', '1', 'sys_notice_type', '',
        'warning', 1, 1, 1, 0, '通知');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (15, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '公告', '2', 'sys_notice_type', '',
        'success', 0, 2, 1, 0, '公告');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (16, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-15 19:46:44', '正常', '1', 'sys_notice_status', '',
        'primary', 1, 1, 1, 0, '正常状态');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (17, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-15 19:46:48', '关闭', '0', 'sys_notice_status', '',
        'danger', 0, 2, 1, 0, '关闭状态');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (18, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-21 00:29:45', '新增', 'INSERT', 'sys_oper_type', '',
        'info', 0, 1, 1, 0, '新增操作');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (19, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-21 00:29:49', '修改', 'UPDATE', 'sys_oper_type', '',
        'info', 0, 2, 1, 0, '修改操作');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (20, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-21 00:29:54', '删除', 'DELETE', 'sys_oper_type', '',
        'danger', 0, 3, 1, 0, '删除操作');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (21, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-21 00:29:59', '授权', 'GRANT', 'sys_oper_type', '',
        'primary', 0, 4, 1, 0, '授权操作');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (22, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-21 00:30:07', '导出', 'EXPORT', 'sys_oper_type', '',
        'warning', 0, 5, 1, 0, '导出操作');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (23, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-21 00:30:13', '导入', 'IMPORT', 'sys_oper_type', '',
        'warning', 0, 6, 1, 0, '导入操作');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (24, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-21 00:30:17', '强退', 'FORCE', 'sys_oper_type', '',
        'danger', 0, 7, 1, 0, '强退操作');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (25, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-21 00:30:23', '生成代码', 'GENCODE', 'sys_oper_type', '',
        'warning', 0, 8, 1, 0, '生成操作');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (26, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-21 00:30:28', '清空数据', 'CLEAN', 'sys_oper_type', '',
        'danger', 0, 9, 1, 0, '清空操作');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (27, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-15 19:46:51', '成功', '1', 'sys_common_status', '',
        'primary', 0, 1, 1, 0, '正常状态');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (28, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-15 19:46:55', '失败', '0', 'sys_common_status', '',
        'danger', 0, 2, 1, 0, '停用状态');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (29, 'admin', '2023-05-04 11:25:27', 'System', '2023-05-04 11:25:27', '用户协议', '1', 'gpt_content_type', '',
        'default', 0, 1, 1, 0, '');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (30, 'admin', '2023-05-04 11:25:35', 'admin', '2023-05-04 11:25:35', '隐私政策', '2', 'gpt_content_type', '',
        'default', 0, 2, 1, 0, '');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (31, 'admin', '2023-05-04 11:25:45', 'System', '2023-05-04 11:25:45', '使用指南', '3', 'gpt_content_type', '',
        'default', 0, 3, 1, 0, '');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (32, 'admin', '2023-05-04 11:32:45', 'System', '2023-05-04 11:32:45', '次数', '1', 'gpt_comb_type', '',
        'default', 0, 1, 1, 0, '');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (33, 'admin', '2023-05-04 11:32:53', 'System', '2023-05-04 11:32:53', '天数', '2', 'gpt_comb_type', '',
        'default', 0, 2, 1, 0, '');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (34, 'admin', '2023-05-06 11:55:48', 'System', '2023-05-06 11:55:48', '微信小程序', '1', 'gpt_member_type', '',
        'primary', 0, 1, 1, 0, '');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (35, 'admin', '2023-05-06 11:56:00', 'System', '2023-05-06 11:56:00', '公众号', '2', 'gpt_member_type', '',
        'success', 0, 2, 1, 0, '');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (36, 'admin', '2023-05-06 11:56:11', 'admin', '2023-05-06 11:56:11', '手机号', '3', 'gpt_member_type', '',
        'warning', 0, 3, 1, 0, '');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (37, 'admin', '2023-05-06 11:57:41', 'System', '2023-05-06 11:57:41', '回复中', '1', 'gpt_chat_status', '',
        'warning', 0, 1, 1, 0, '');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (38, 'admin', '2023-05-06 11:57:55', 'System', '2023-05-06 11:57:55', '回复成功', '2', 'gpt_chat_status', '',
        'success', 0, 2, 1, 0, '');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (39, 'admin', '2023-05-06 11:58:07', 'System', '2023-05-06 11:58:07', '回复失败', '3', 'gpt_chat_status', '',
        'danger', 0, 3, 1, 0, '');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (40, 'admin', '2023-09-06 15:33:28', 'admin', '2023-09-06 15:33:28', 'ChatGpt', 'CHAT_GPT', 'gpt_model_type', '',
        'primary', 0, 1, 1, 0, '');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (41, 'admin', '2024-01-22 09:15:57', 'admin', '2024-01-22 01:45:58', '本地上传', '1', 'sys_oss_type', '',
        'primary', 0, 1, 1, 0, '');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (42, 'admin', '2024-01-22 09:16:46', 'admin', '2024-01-22 01:46:06', '阿里OSS', '2', 'sys_oss_type', '',
        'success', 0, 2, 1, 0, '');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (43, 'admin', '2024-01-22 09:17:06', 'System', '2024-01-22 01:46:06', '腾讯COS', '3', 'sys_oss_type', '',
        'warning', 0, 3, 1, 0, '');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (44, 'admin', '2024-01-22 09:30:24', 'System', '2024-01-22 09:30:24', '阿里云SMS', '1', 'sys_sms_type', '',
        'primary', 0, 1, 1, 0, '');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (45, 'admin', '2024-01-22 09:30:37', 'System', '2024-01-22 09:30:37', '腾讯云SMS', '2', 'sys_sms_type', '',
        'success', 0, 2, 1, 0, '');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (46, 'admin', '2024-01-22 15:37:59', 'System', '2024-01-22 15:37:59', '文心一言', 'WENXIN', 'gpt_model_type', '',
        'success', 0, 2, 1, 0, '');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (47, 'admin', '2024-01-22 15:38:17', 'System', '2024-01-22 15:38:17', '通义千问', 'QIANWEN', 'gpt_model_type',
        '', 'info', 0, 3, 1, 0, '');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (48, 'admin', '2024-01-22 15:38:34', 'System', '2024-01-22 15:38:34', '讯飞星火', 'SPARK', 'gpt_model_type', '',
        'warning', 0, 4, 1, 0, '');
INSERT INTO `chat_gpt`.`sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`,
                                   `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`,
                                   `remark`)
VALUES (49, 'admin', '2024-01-22 15:39:03', 'admin', '2024-01-22 15:39:03', '智谱清言', 'ZHIPU', 'gpt_model_type', '',
        'danger', 0, 5, 1, 0, '');
COMMIT;

DROP TABLE IF EXISTS sys_config;
CREATE TABLE sys_config
(
    `id`           BIGINT(20) not null auto_increment comment '参数主键',
    `create_user`  VARCHAR(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
    `create_time`  DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user`  VARCHAR(32) NOT NULL DEFAULT 'System' COMMENT '更新人',
    `update_time`  DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `name`         VARCHAR(100)         DEFAULT '' COMMENT '参数名称',
    `config_key`   VARCHAR(100)         DEFAULT '' COMMENT '参数键名',
    `config_value` VARCHAR(500)         DEFAULT '' COMMENT '参数键值',
    `type`         INT(1) DEFAULT 1 COMMENT '系统内置（1是 0否）',
    `deleted`      INT(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0->未删除;1->已删除',
    `remark`       VARCHAR(512)         DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  ROW_FORMAT = DYNAMIC COMMENT = '参数配置表';

-- ----------------------------
-- Records of sys_config
-- ----------------------------
BEGIN;
INSERT INTO `sys_config` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `config_key`,
                          `config_value`, `type`, `deleted`, `remark`)
VALUES (1, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '主框架页-默认皮肤样式名称',
        'sys.index.skinName',
        'skin-blue', 1, 0, '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow');
INSERT INTO `sys_config` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `config_key`,
                          `config_value`, `type`, `deleted`, `remark`)
VALUES (2, 'admin', '2022-07-08 15:40:23', 'admin', '2022-07-08 15:40:22', '用户管理-账号初始密码',
        'sys.user.initPassword',
        '123456', 1, 0, '初始化密码 123456');
INSERT INTO `sys_config` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `config_key`,
                          `config_value`, `type`, `deleted`, `remark`)
VALUES (3, 'admin', '2022-07-08 15:40:23', 'admin', '2022-07-08 15:40:22', '主框架页-侧边栏主题', 'sys.index.sideTheme',
        'theme-dark', 1, 0, '深色主题theme-dark，浅色主题theme-light');
INSERT INTO `sys_config` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `config_key`,
                          `config_value`, `type`, `deleted`, `remark`)
VALUES (4, 'admin', '2022-07-08 15:40:23', 'admin', '2022-07-15 21:00:22', '账号自助-验证码开关',
        'sys.account.captchaOnOff',
        'true', 1, 0, '是否开启验证码功能（true开启，false关闭）');
INSERT INTO `sys_config` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `config_key`,
                          `config_value`, `type`, `deleted`, `remark`)
VALUES (5, 'admin', '2022-07-08 15:40:23', 'admin', '2022-07-08 15:40:22', '账号自助-是否开启用户注册功能',
        'sys.account.registerUser', 'false', 1, 0, '是否开启注册用户功能（true开启，false关闭）');
INSERT INTO `sys_config` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `config_key`,
                          `config_value`, `type`, `deleted`, `remark`)
VALUES (6, 'admin', '2022-08-02 17:31:37', 'admin', '2022-08-02 17:31:37', '账号自助-是否允许同时登录',
        'sys.account.allLogin',
        'false', 1, 0, '是否允许同时登录（true开启，false关闭）');
INSERT INTO `sys_config` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `config_key`,
                          `config_value`, `type`, `deleted`, `remark`)
VALUES (7, 'admin', '2022-08-16 15:58:54', 'admin', '2022-08-16 15:58:54', '即使通讯-是否开启IM模块', 'sys.module.IM',
        'false', 1,
        0, '是否开启IM模块（true开启，false关闭）');
INSERT INTO `sys_config` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`,
                          `config_key`, `config_value`, `type`, `deleted`, `remark`)
VALUES (8, 'admin', '2024-01-20 16:34:53', 'admin', '2024-01-20 16:34:53', '是否限制访问Chat', 'sys_chat_master',
        'true', 1, 0, '是否无限访问Chat');
COMMIT;

DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`
(
    `id`          BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `create_user` VARCHAR(32)  NOT NULL DEFAULT 'System' COMMENT '创建人',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user` VARCHAR(32)  NOT NULL DEFAULT 'System' COMMENT '更新人',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `type`        INT(11) NOT NULL DEFAULT 0 COMMENT '公告类型（1通知 2公告）',
    `title`       VARCHAR(128) NOT NULL DEFAULT '' COMMENT '公告标题',
    `agreement`   TEXT                  DEFAULT NULL COMMENT '公告内容',
    `status`      INT(1) NOT NULL DEFAULT 1 COMMENT '状态 0->禁用;1->启用',
    `deleted`     INT(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0->未删除;1->已删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  ROW_FORMAT = DYNAMIC COMMENT = '系统通知';

CREATE TABLE `sys_notice_read`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
    `create_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `sys_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
    `notice_id`   bigint(20) NOT NULL DEFAULT '0' COMMENT '通知id',
    `is_read`     int(1) NOT NULL DEFAULT '1' COMMENT '是否已读 0：未读；1：已读',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统通知已读状态';

DROP TABLE IF EXISTS `sys_base_config`;
CREATE TABLE `sys_base_config`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
    `create_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '更新人',
    `update_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `name`        varchar(50) NOT NULL DEFAULT '' COMMENT '配置键值',
    `data`        longtext COMMENT '配置内容',
    `deleted`     int(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0->未删除;1->已删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='基础配置';

INSERT INTO `chat_gpt`.`sys_base_config` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `data`, `deleted`) VALUES (1, 'admin', '2024-01-22 10:38:39', 'admin', '2024-01-22 14:29:27', 'baseInfo', '{\"contentSecurity\":0,\"copyright\":\"ChatMASTER\",\"descrip\":\"ChatMASTER，基于AI大模型api实现的ChatGPT服务，支持ChatGPT(3.5、4.0)模型，同时也支持国内文心一言(支持Stable-Diffusion-XL作图)、通义千问、讯飞星火、智谱清言(ChatGLM)等主流模型，支出同步响应及流式响应，完美呈现打印机效果。\",\"keywords\":[\"通义千问\",\"ChatGPT\",\"文心一言\",\"智谱清言\"],\"siteTitle\":\"ChatMASTER\",\"domain\":\"https://gpt.panday94.xyz\",\"proxyType\":1,\"siteLogo\":\"/files/default/2024/01/283083aa-aaf1-455c-ac79-f490aa385ed9.png\"}', 0);
INSERT INTO `chat_gpt`.`sys_base_config` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `data`, `deleted`) VALUES (2, 'admin', '2024-01-22 10:39:48', 'System', '2024-01-22 10:39:48', 'appInfo', '{\"isShare\":1,\"isSms\":0,\"homeNotice\":\"ChatMASTER，基于AI大模型api实现的ChatGPT服务，支持ChatGPT(3.5、4.0)模型，同时也支持国内文心一言(支持Stable-Diffusion-XL作图)、通义千问、讯飞星火、智谱清言(ChatGLM)等主流模型，支出同步响应及流式响应，完美呈现打印机效果。\",\"shareRewardNum\":\"20\",\"freeNum\":\"20\",\"h5Url\":\"https://gpt.panday94.xyz/h5\"}', 0);
INSERT INTO `chat_gpt`.`sys_base_config` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `data`, `deleted`) VALUES (3, 'admin', '2024-01-22 10:39:53', 'System', '2024-01-22 10:39:53', 'wxInfo', '{\"mpLogin\":0,\"mpPay\":0,\"maAppId\":\"xx\",\"maSecret\":\"xx\",\"mpAppId\":\"xx\",\"mpSecret\":\"xx\",\"mchNo\":\"xx\",\"v3Secret\":\"xx\"}', 0);
INSERT INTO `chat_gpt`.`sys_base_config` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `data`, `deleted`) VALUES (4, 'admin', '2024-01-22 10:40:04', 'admin', '2024-01-22 11:48:58', 'extraInfo', '{\"uploadSize\":\"xx\",\"ossType\":1,\"smsKeySecret\":\"xx\",\"smsType\":1,\"smsKeyId\":\"xx\"}', 0);


DROP TABLE IF EXISTS `sys_login_log`;
CREATE TABLE `sys_login_log`
(
    `id`            BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `create_user`   VARCHAR(32)  NOT NULL DEFAULT 'System' COMMENT '创建人',
    `create_time`   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `expire_time`   DATETIME COMMENT 'token过期时间',
    `sys_user_id`   BIGINT(20) NOT NULL DEFAULT 0 COMMENT '用户id',
    `session_id`    VARCHAR(128) NOT NULL DEFAULT '' COMMENT '会话标识',
    `username`      VARCHAR(64)  NOT NULL DEFAULT '' COMMENT '用户名',
    `ip`            VARCHAR(64)  NOT NULL DEFAULT '' COMMENT 'ip地址',
    `address`       VARCHAR(64)  NOT NULL DEFAULT '' COMMENT '登录地址',
    `domain`        VARCHAR(64)  NOT NULL DEFAULT '' COMMENT '域名',
    `browser`       VARCHAR(64)  NOT NULL DEFAULT '' COMMENT '浏览器类型',
    `os`            VARCHAR(64)  NOT NULL DEFAULT '' COMMENT '操作系统',
    `msg`           VARCHAR(512) NOT NULL DEFAULT '' COMMENT '登录信息',
    `authorization` TEXT COMMENT '身份标识',
    `user_agent`    TEXT COMMENT '浏览器类型',
    `status`        INT(1) NOT NULL DEFAULT 0 COMMENT '登录状态 0 失败 1 成功',
    `enabled`       INT(1) NOT NULL DEFAULT 1 COMMENT '是否启用 0 禁用 1 启用',
    `deleted`       INT(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0->未删除;1->已删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  ROW_FORMAT = DYNAMIC COMMENT = '登录日志';

DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`
(
    `id`             BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `create_user`    VARCHAR(32)  NOT NULL DEFAULT 'System' COMMENT '创建人',
    `create_time`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `sys_user_id`    BIGINT(20) NOT NULL DEFAULT 0 COMMENT '操作人id',
    `fk_id`          BIGINT(20) NOT NULL DEFAULT 0 COMMENT '操作记录id',
    `username`       VARCHAR(64)  NOT NULL DEFAULT '' COMMENT '操作人',
    `ip`             VARCHAR(64)  NOT NULL DEFAULT '' COMMENT '请求ip',
    `address`        VARCHAR(64)  NOT NULL DEFAULT '' COMMENT '操作地址',
    `domain`         VARCHAR(64)  NOT NULL DEFAULT '' COMMENT '域名',
    `browser`        VARCHAR(64)  NOT NULL DEFAULT '' COMMENT '浏览器类型',
    `os`             VARCHAR(64)  NOT NULL DEFAULT '' COMMENT '操作系统',
    `method`         VARCHAR(128) NOT NULL DEFAULT '' COMMENT '请求方法',
    `request_method` VARCHAR(64)  NOT NULL DEFAULT '' COMMENT '请求方式',
    `uri`            VARCHAR(64)  NOT NULL DEFAULT '' COMMENT '接口名称',
    `title`          VARCHAR(64)  NOT NULL DEFAULT '' COMMENT '操作模块',
    `business_type`  VARCHAR(64)  NOT NULL DEFAULT '' COMMENT '操作类型',
    `operation`      VARCHAR(512) NOT NULL DEFAULT '' COMMENT '操作内容',
    `time`           BIGINT(20) NOT NULL DEFAULT 0 COMMENT '请求耗时',
    `params`         TEXT COMMENT '请求参数',
    `result`         TEXT COMMENT '执行结果',
    `deleted`        INT(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0->未删除;1->已删除',
    PRIMARY KEY (`id`) USING BTREE,
    KEY              `sys_user_id` (`sys_user_id`),
    KEY              `fk_id` (`fk_id`),
    KEY              `title` (`title`),
    KEY              `business_type` (`business_type`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  ROW_FORMAT = DYNAMIC COMMENT = '系统日志';

DROP TABLE IF EXISTS `sys_audit_log`;
CREATE TABLE `sys_audit_log`
(
    `id`          BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `create_user` VARCHAR(32)  NOT NULL DEFAULT 'System' COMMENT '创建人',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `sys_user_id` BIGINT(20) NOT NULL DEFAULT 0 COMMENT '操作人id',
    `fk_id`       BIGINT(20) NOT NULL DEFAULT 0 COMMENT '审核记录id',
    `status`      INT(1) NOT NULL DEFAULT 1 COMMENT '审核状态 0->审核拒绝;1->审核通过',
    `type`        INT(11) NOT NULL DEFAULT 0 COMMENT '审核类型',
    `agreement`   VARCHAR(128) NOT NULL DEFAULT '' COMMENT '审核内容',
    `pic`         VARCHAR(128) NOT NULL DEFAULT '' COMMENT '图片',
    `remark`      VARCHAR(512) NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE,
    KEY           `sys_user_id` (`sys_user_id`),
    KEY           `fk_id` (`fk_id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  ROW_FORMAT = DYNAMIC COMMENT = '审核日志';

DROP TABLE IF EXISTS sys_job;
CREATE TABLE sys_job
(
    `id`              BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `create_user`     VARCHAR(32)  NOT NULL DEFAULT 'System' COMMENT '创建人',
    `create_time`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_user`     VARCHAR(32)  NOT NULL DEFAULT 'System' COMMENT '更新人',
    `update_time`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `name`            VARCHAR(64)  NOT NULL DEFAULT '' COMMENT '任务名称',
    `job_group`       VARCHAR(64)  NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
    `invoke_target`   VARCHAR(500) NOT NULL COMMENT '调用目标字符串',
    `cron_expression` VARCHAR(255) NOT NULL DEFAULT '' COMMENT 'cron执行表达式',
    `misfire_policy`  INT(1) NOT NULL DEFAULT 3 COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
    `concurrent`      INT(1) NOT NULL DEFAULT 0 COMMENT '是否并发执行（0禁止 1允许）',
    `status`          INT(1) NOT NULL DEFAULT 0 COMMENT '状态（0暂停 1正常）',
    `remark`          VARCHAR(500) NOT NULL DEFAULT '' COMMENT '备注信息',
    `deleted`         INT(1) NOT NULL DEFAULT 0 COMMENT '是否删除 0->未删除;1->已删除',
    PRIMARY KEY (`id`) USING BTREE,
    KEY               `name` (`name`),
    KEY               `job_group` (`job_group`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  ROW_FORMAT = DYNAMIC COMMENT = '定时任务调度表';

-- ----------------------------
-- Records of sys_job
-- ----------------------------
BEGIN;
INSERT INTO `sys_job` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `job_group`,
                       `invoke_target`, `cron_expression`, `misfire_policy`, `concurrent`, `status`, `remark`,
                       `deleted`)
VALUES (1, 'admin', '2022-07-08 15:40:23', 'admin', '2022-07-08 15:40:23', '系统默认（无参）', 'DEFAULT',
        'demoTask.testNoParams()', '0/10 * * * * ?', 3, 1, 0, '', 0);
INSERT INTO `sys_job` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `job_group`,
                       `invoke_target`, `cron_expression`, `misfire_policy`, `concurrent`, `status`, `remark`,
                       `deleted`)
VALUES (2, 'admin', '2022-07-08 15:40:23', 'admin', '2022-07-08 15:40:23', '系统默认（有参）', 'DEFAULT',
        'demoTask.testParams(\'master\')', '0/15 * * * * ?', 3, 1, 0, '', 0);
INSERT INTO `sys_job` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `job_group`,
                       `invoke_target`, `cron_expression`, `misfire_policy`, `concurrent`, `status`, `remark`,
                       `deleted`)
VALUES (3, 'admin', '2022-07-08 15:40:23', 'admin', '2022-07-08 15:40:23', '系统默认（多参）', 'DEFAULT',
        'demoTask.testMultipleParams(\'master\', true, 2000L, 316.50D, 100)', '0/20 * * * * ?', 3, 1, 0, '', 0);
COMMIT;

DROP TABLE IF EXISTS sys_job_log;
CREATE TABLE sys_job_log
(
    `id`             BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `create_user`    VARCHAR(32)  NOT NULL DEFAULT 'System' COMMENT '创建人',
    `create_time`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `job_id`         BIGINT(20) NOT NULL DEFAULT 0 COMMENT '任务ID',
    `name`           VARCHAR(64)  NOT NULL DEFAULT '' COMMENT '任务名称',
    `job_group`      VARCHAR(64)  NOT NULL DEFAULT '' COMMENT '任务组名',
    `invoke_target`  VARCHAR(500) NOT NULL DEFAULT '' COMMENT '调用目标字符串',
    `job_message`    VARCHAR(500) NOT NULL DEFAULT '' COMMENT '日志信息',
    `status`         CHAR(1)               DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
    `exception_info` VARCHAR(2000)         DEFAULT '' COMMENT '异常信息',
    `start_time`     DATETIME COMMENT '开始时间',
    `stop_time`      DATETIME COMMENT '结束时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  ROW_FORMAT = DYNAMIC COMMENT = '定时任务调度日志表';