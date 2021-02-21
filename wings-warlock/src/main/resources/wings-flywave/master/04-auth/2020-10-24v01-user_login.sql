CREATE TABLE `win_user_basic` (
    `id`        BIGINT(20)   NOT NULL COMMENT '主键/user_id',
    `create_dt` DATETIME(3)  NOT NULL DEFAULT NOW(3) COMMENT '创建日时(系统)',
    `modify_dt` DATETIME(3)  NOT NULL DEFAULT '1000-01-01' ON UPDATE NOW(3) COMMENT '修改日时(系统)',
    `delete_dt` DATETIME(3)  NOT NULL DEFAULT '1000-01-01' COMMENT '标记删除',
    `commit_id` BIGINT(20)   NOT NULL COMMENT '提交id',
    `nick_name` VARCHAR(50)  NOT NULL DEFAULT '' COMMENT '用户昵称',
    `gender`    INT(11)      NOT NULL DEFAULT '0' COMMENT '用户性别/12001##:未知|先生|女士',
    `avatar`    VARCHAR(200) NOT NULL DEFAULT '' COMMENT '头像地址',
    `language`  CHAR(5)      NOT NULL DEFAULT 'zh_CN' COMMENT '使用语言/zh_CN:StandardLanguageEnum',
    `timezone`  INT(11)      NOT NULL DEFAULT '1010201' COMMENT '所在时区/10102##:StandardTimezoneEnum',
    `remark`    VARCHAR(500) NOT NULL DEFAULT '' COMMENT '备注',
    `status`    INT(11)      NOT NULL DEFAULT '0' COMMENT '用户状态/12002##:',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='120/用户基本表';

CREATE TABLE `win_user_anthn` (
    `create_dt`  DATETIME(3)   NOT NULL DEFAULT NOW(3) COMMENT '创建日时(系统)',
    `modify_dt`  DATETIME(3)   NOT NULL DEFAULT '1000-01-01' ON UPDATE NOW(3) COMMENT '修改日时(系统)',
    `delete_dt`  DATETIME(3)   NOT NULL DEFAULT '1000-01-01' COMMENT '标记删除',
    `commit_id`  BIGINT(20)    NOT NULL COMMENT '提交id',
    `user_id`    BIGINT(20)    NOT NULL DEFAULT '0' COMMENT '绑定用户/win_user_basic.id',
    `auth_type`  VARCHAR(10)   NOT NULL COMMENT '验证类型/wings.warlock.security.auth-type.*',
    `auth_name`  VARCHAR(200)  NOT NULL COMMENT '验证账号/身份辨识:邮箱|手机|union_id|api_key',
    `auth_pass`  VARCHAR(200)  NOT NULL DEFAULT '' COMMENT '验证密码/spring格式|api_secret',
    `auth_salt`  VARCHAR(50)   NOT NULL DEFAULT '' COMMENT '验证加盐/随机数',
    `auth_para1` VARCHAR(200)  NOT NULL DEFAULT '' COMMENT '验证参数1',
    `auth_para2` VARCHAR(200)  NOT NULL DEFAULT '' COMMENT '验证参数2',
    `auth_token` VARCHAR(2000) NOT NULL DEFAULT '' COMMENT '标记删除',
    `expired_dt` DATETIME(3)   NOT NULL DEFAULT '1000-01-01' COMMENT '过期时间',
    `error_cnt`  INT(11)       NOT NULL DEFAULT '0' COMMENT '错误计数',
    `status`     INT(11)       NOT NULL DEFAULT '0' COMMENT '状态/同用户状态:12002##:',
    PRIMARY KEY (`user_id`, `auth_type`),
    UNIQUE INDEX uq_type_name (`auth_type`, `auth_name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='121/用户验证表';

CREATE TABLE `win_user_login` (
    `id`        BIGINT(20)   NOT NULL COMMENT '主键/user_id',
    `user_id`   BIGINT(20)   NOT NULL DEFAULT '0' COMMENT '绑定用户/win_user_basic.id',
    `auth_type` VARCHAR(10)  NOT NULL COMMENT '验证类型/wings.warlock.security.auth-type.*',
    `login_ip`  VARCHAR(50)  NOT NULL DEFAULT '' COMMENT '登录IP',
    `login_dt`  DATETIME(3)  NOT NULL DEFAULT NOW(3) COMMENT '创建日时(系统)',
    `terminal`  VARCHAR(500) NOT NULL DEFAULT '' COMMENT '登录终端',
    `remark`    VARCHAR(500) NOT NULL DEFAULT '' COMMENT '备注信息',
    PRIMARY KEY (`id`),
    INDEX ix_user_id (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='122/用户登录表/只读，只登录成功';

-- -----------

INSERT IGNORE INTO `sys_light_sequence` (`seq_name`, `block_id`, `next_val`, `step_val`, `comments`)
VALUES ('win_user_basic', 0, 1000, 100, '###为静态用户');

--
REPLACE INTO `sys_constant_enum` (`id`, `type`, `code`, `hint`, `info`)
VALUES (1200100, 'user_gender', 'user_gender', '性别', 'classpath:/wings-tmpl/ConstantEnumTemplate.java'),
       (1200101, 'user_gender', 'male', '男', '通常'),
       (1200102, 'user_gender', 'female', '女', '通常'),
       (1200103, 'user_gender', 'unknown', '未知', '通常'),

       (1200200, 'user_status', 'user_status', '用户状态', 'classpath:/wings-tmpl/ConstantEnumTemplate.java'),
       (1200201, 'user_status', 'uninit', '新建', '未初始化'),
       (1200202, 'user_status', 'active', '正常', '正常活动'),
       (1200203, 'user_status', 'infirm', '薄弱', '薄弱账户'),
       (1200204, 'user_status', 'unsafe', '异动', '异动账户'),
       (1200205, 'user_status', 'danger', '危险', '危险账户'),
       (1200206, 'user_status', 'frozen', '冻结', '冻结账户'),
       (1200207, 'user_status', 'hidden', '隐藏', '隐藏账户');


INSERT INTO `win_user_basic` (`id`, `create_dt`, `commit_id`, `nick_name`, `gender`, `avatar`, `language`, `timezone`, `remark`, `status`)
VALUES (0, NOW(3), 0, 'nobody', 1200103, '', 'zh_CN', 1010201, '系统用户，无任何权限', 1200207),
       (1, NOW(3), 0, 'root', 1200103, '', 'zh_CN', 1010201, '超级用户，拥有所以权限', 1200202),
       (2, NOW(3), 0, 'daemon', 1200103, '', 'zh_CN', 1010201, '系统用户，执行后台任务', 1200207);

