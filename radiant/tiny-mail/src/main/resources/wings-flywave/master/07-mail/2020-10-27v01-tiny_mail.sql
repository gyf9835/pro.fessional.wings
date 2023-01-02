CREATE TABLE `win_mail_sender` (
    `id`         BIGINT(20)    NOT NULL COMMENT '主键/task_id',
    `create_dt`  DATETIME(3)   NOT NULL DEFAULT NOW(3) COMMENT '创建日时(系统)',
    `modify_dt`  DATETIME(3)   NOT NULL DEFAULT '1000-01-01' ON UPDATE NOW(3) COMMENT '修改日时(系统)',
    `delete_dt`  DATETIME(3)   NOT NULL DEFAULT '1000-01-01' COMMENT '标记删除',
    `commit_id`  BIGINT(20)    NOT NULL COMMENT '提交id',
    `mail_apps`  VARCHAR(500)  NOT NULL DEFAULT '' COMMENT '所属程序，逗号分隔，推荐一个，使用spring.application.name',
    `mail_runs`  VARCHAR(100)  NOT NULL DEFAULT '' COMMENT '执行模式，RunMode(product|test|develop|local)，逗号分隔忽略大小写，默认所有',
    `mail_from`  VARCHAR(200)  NOT NULL DEFAULT '' COMMENT '邮件发送者',
    `mail_to`    VARCHAR(500)  NOT NULL DEFAULT '' COMMENT '邮件收件人，逗号分隔',
    `mail_cc`    VARCHAR(500)  NOT NULL DEFAULT '' COMMENT '邮件抄送人，逗号分隔',
    `mail_bcc`   VARCHAR(500)  NOT NULL DEFAULT '' COMMENT '邮件暗送人，逗号分隔',
    `mail_reply` VARCHAR(200)  NOT NULL DEFAULT '' COMMENT '邮件回复',
    `mail_subj`  VARCHAR(400)  NOT NULL DEFAULT '' COMMENT '邮件标题',
    `mail_text`  TEXT          NULL     DEFAULT NULL COMMENT '邮件正文',
    `mail_html`  TINYINT(1)    NOT NULL DEFAULT '1' COMMENT '是否为HTML邮件',
    `mail_file`  VARCHAR(2000) NOT NULL DEFAULT '' COMMENT '邮件附件路径，逗号分隔',
    `tmpl_path`  VARCHAR(400)  NOT NULL DEFAULT '' COMMENT '邮件模板路径',
    `tmpl_para`  TEXT          NULL     DEFAULT NULL COMMENT '邮件模板参数，JSON格式',
    `mark_type`  VARCHAR(400)  NOT NULL DEFAULT '' COMMENT '标记类型',
    `mark_word`  VARCHAR(400)  NOT NULL DEFAULT '' COMMENT '标记关键词',
    `last_send`  DATETIME(3)   NOT NULL DEFAULT '1000-01-01' COMMENT '上次发送（UTC时区）',
    `last_fail`  TEXT          NULL     DEFAULT NULL COMMENT '上次失败信息',
    `last_cost`  INT(11)       NOT NULL DEFAULT '0' COMMENT '上次发送耗时毫秒数',
    `next_send`  DATETIME(3)   NOT NULL DEFAULT '1000-01-01' COMMENT '下次发送（UTC时区）',
    `next_lock`  INT(11)       NOT NULL DEFAULT '0' COMMENT '发送执行的乐观锁',
    `sums_send`  INT(11)       NOT NULL DEFAULT '0' COMMENT '合计发送次数',
    `sums_fail`  INT(11)       NOT NULL DEFAULT '0' COMMENT '合计失败次数',
    PRIMARY KEY (`id`),
    INDEX ix_next_send (`next_send`),
    INDEX ix_sums_send (`sums_send`),
    INDEX ix_mark_word (`mark_word`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='124/邮件发送';