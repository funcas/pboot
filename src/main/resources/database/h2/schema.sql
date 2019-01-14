CREATE TABLE oauth_access_token
(
  token_id          varchar(256) DEFAULT NULL,
  token             blob,
  authentication_id varchar(256) NOT NULL,
  user_name         varchar(256) DEFAULT NULL,
  client_id         varchar(256) DEFAULT NULL,
  authentication    blob,
  refresh_token     varchar(256) DEFAULT NULL,
  PRIMARY KEY (authentication_id)
);

CREATE TABLE oauth_client_details
(
  client_id               varchar(256) NOT NULL,
  resource_ids            varchar(256)  DEFAULT NULL,
  client_secret           varchar(256)  DEFAULT NULL,
  scope                   varchar(256)  DEFAULT NULL,
  authorized_grant_types  varchar(256)  DEFAULT NULL,
  web_server_redirect_uri varchar(256)  DEFAULT NULL,
  authorities             varchar(256)  DEFAULT NULL,
  access_token_validity   int(11)       DEFAULT NULL,
  refresh_token_validity  int(11)       DEFAULT NULL,
  additional_information  varchar(4096) DEFAULT NULL,
  autoapprove             varchar(256)  DEFAULT NULL,
  PRIMARY KEY (client_id)
);

CREATE TABLE oauth_client_token
(
  token_id          varchar(256) DEFAULT NULL,
  token             blob,
  authentication_id varchar(256) NOT NULL,
  user_name         varchar(256) DEFAULT NULL,
  client_id         varchar(256) DEFAULT NULL,
  PRIMARY KEY (authentication_id)
);

CREATE TABLE oauth_code
(
  code           varchar(256) DEFAULT NULL,
  authentication blob
);

CREATE TABLE oauth_refresh_token
(
  token_id       varchar(256) DEFAULT NULL,
  token          blob,
  authentication blob
);

CREATE TABLE tb_dictionary_category
(
  id     bigint(20)  NOT NULL,
  code   varchar(16) NOT NULL,
  name   varchar(32) NOT NULL,
  remark text,
  remove_able tinyint(1),
  edit_able tinyint(1),
  PRIMARY KEY (id),
  UNIQUE KEY uk_9qkei4dxobl1lm4oa0ys8c3nr (code)
);

CREATE TABLE tb_data_dictionary
(
  id             bigint(20)  NOT NULL,
  name           varchar(16) NOT NULL,
  code           varchar(32) NOT NULL,
  remark         text,
  type           varchar(32) NOT NULL,
  value          varchar(8)  NOT NULL,
  fk_category_id bigint(20)  NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uk_aunvlvmd6s3c2ddkcjf849d9 (code),
  KEY fk_layhfd1butuigsscgucmp2okd (fk_category_id),
  CONSTRAINT fk_layhfd1butuigsscgucmp2okd FOREIGN KEY (fk_category_id) REFERENCES tb_dictionary_category (id)
);

CREATE TABLE tb_resource
(
  id           bigint(20)  NOT NULL,
  component    varchar(64)  DEFAULT NULL,
  permission   varchar(64)  DEFAULT NULL,
  remark       text,
  sort         int(11)     NOT NULL,
  name         varchar(16) NOT NULL,
  type         int(2)      NOT NULL,
  value        varchar(256) DEFAULT NULL,
  fk_parent_id bigint(20)   DEFAULT NULL,
  icon         varchar(32)  DEFAULT NULL,
  PRIMARY KEY (id),
  KEY fk_k2heqvi9muk4cjyyd53r9y37x (fk_parent_id)
);

CREATE TABLE tb_unit
(
  id          bigint(20) NOT NULL COMMENT '编号',
  parent_id   bigint(20)      DEFAULT NULL COMMENT '父级编号',
  org_code    varchar(2000)   DEFAULT NULL COMMENT '部门编号',
  name        varchar(100)    DEFAULT NULL COMMENT '名称',
  sort        int(11)         DEFAULT NULL COMMENT '排序',
  area_id     varchar(64)     DEFAULT NULL COMMENT '归属区域',
  code        varchar(100)    DEFAULT NULL COMMENT '区域编码',
  type        tinyint(1)      DEFAULT NULL COMMENT '机构类型',
  master      bigint(20)      DEFAULT NULL COMMENT '负责人',
  phone       varchar(16)     DEFAULT NULL COMMENT '电话',
  state       tinyint(1)      DEFAULT NULL COMMENT '是否启用',
  creator_id  bigint(20)      DEFAULT NULL COMMENT '创建者',
  ctime       datetime        DEFAULT NULL COMMENT '创建时间',
  modifier_id bigint(20)      DEFAULT NULL COMMENT '更新者',
  mtime       timestamp  NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  remarks     varchar(255)    DEFAULT NULL COMMENT '备注信息',
  del_flag    tinyint(1)         DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (id),
  KEY idx_org_parent_id (parent_id),
  KEY idx_org_del_flag (del_flag)
);

CREATE TABLE tb_user
(
  id          bigint(20)   NOT NULL AUTO_INCREMENT,
  username    varchar(16)  NOT NULL,
  password    varchar(256) NOT NULL,
  email       varchar(64)  DEFAULT NULL,
  nickname    varchar(16)  NOT NULL,
  avatar      varchar(255) DEFAULT NULL,
  phone       varchar(16)  DEFAULT NULL,
  state       int(2)       NOT NULL,
  sex         tinyint(1)   DEFAULT NULL,
  birthday    varchar(10)  DEFAULT NULL,
  address     varchar(128) DEFAULT NULL,
  unit_id     bigint(20)   DEFAULT NULL,
  company_id  bigint(20)   DEFAULT NULL,
  ctime       datetime     DEFAULT NULL,
  creator_id  bigint(20)   DEFAULT NULL,
  mtime       datetime     DEFAULT NULL,
  modifier_id bigint(20)   DEFAULT NULL,
  del_flag    tinyint(1)   DEFAULT '0',
  openid    varchar(255)   default null,
  PRIMARY KEY (id)
);

CREATE TABLE tb_group
(
  id         bigint(20)  NOT NULL,
  name       varchar(16) NOT NULL,
  remark     text,
  org_id     bigint(20) DEFAULT NULL,
  data_scope tinyint(2) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uk_byw2jrrrxrueqimkmgj3o842j (name)
);

CREATE TABLE tb_group_resource
(
  fk_resource_id bigint(20) NOT NULL,
  fk_group_id    bigint(20) NOT NULL,
  PRIMARY KEY (fk_resource_id, fk_group_id),
  KEY fk_q82fpmfh128qxoeyymrkg71e2 (fk_group_id),
  CONSTRAINT fk_3tjs4wt3vvoibo1fvcvog5srd FOREIGN KEY (fk_resource_id) REFERENCES tb_resource (id),
  CONSTRAINT fk_q82fpmfh128qxoeyymrkg71e2 FOREIGN KEY (fk_group_id) REFERENCES tb_group (id)
);

CREATE TABLE tb_group_unit
(
  group_id bigint(20) NOT NULL COMMENT '角色编号',
  unit_id  bigint(20) NOT NULL COMMENT '机构编号',
  PRIMARY KEY (group_id, unit_id)
);

CREATE TABLE tb_group_user
(
  fk_group_id bigint(20) NOT NULL,
  fk_user_id  bigint(20) NOT NULL,
  PRIMARY KEY (fk_group_id, fk_user_id),
  KEY fk_7k068ltfepa1q75qtmvxuawk (fk_user_id),
  CONSTRAINT fk_7k068ltfepa1q75qtmvxuawk FOREIGN KEY (fk_user_id) REFERENCES tb_user (id),
  CONSTRAINT fk_rgmkki7dggfag6ow6eivljmwv FOREIGN KEY (fk_group_id) REFERENCES tb_group (id)
);

CREATE TABLE tb_operating_record
(
  id               bigint(20) NOT NULL,
  end_date         datetime  DEFAULT NULL,
  fk_user_id       varchar(32)  DEFAULT NULL,
  operating_target varchar(512) DEFAULT NULL,
  start_date       datetime  DEFAULT NULL,
  username         varchar(32)  DEFAULT NULL,
  func             varchar(128) DEFAULT NULL,
  ip               varchar(64)  DEFAULT NULL,
  method           varchar(256) DEFAULT NULL,
  module           varchar(128) DEFAULT NULL,
  remark           varchar(2000) DEFAULT NULL,
  state            int(11)      DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE tb_quartz_job
(
  id              bigint(20)   NOT NULL,
  job_class_name  varchar(128) NOT NULL,
  parameter       varchar(128) DEFAULT NULL,
  cron_expression varchar(16)  DEFAULT NULL,
  state           tinyint(1)   DEFAULT NULL,
  remark          varchar(200) DEFAULT NULL,
  ctime           datetime     DEFAULT NULL,
  created         bigint(20)   DEFAULT NULL,
  del_flag        tinyint(1)   DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE QRTZ_JOB_DETAILS
(
  SCHED_NAME        VARCHAR(120) NOT NULL,
  JOB_NAME          VARCHAR(200) NOT NULL,
  JOB_GROUP         VARCHAR(200) NOT NULL,
  DESCRIPTION       VARCHAR(250) NULL,
  JOB_CLASS_NAME    VARCHAR(250) NOT NULL,
  IS_DURABLE        VARCHAR(1)   NOT NULL,
  IS_NONCONCURRENT  VARCHAR(1)   NOT NULL,
  IS_UPDATE_DATA    VARCHAR(1)   NOT NULL,
  REQUESTS_RECOVERY VARCHAR(1)   NOT NULL,
  JOB_DATA          BLOB         NULL,
  PRIMARY KEY (SCHED_NAME, JOB_NAME, JOB_GROUP)
);

CREATE TABLE QRTZ_TRIGGERS
(
  SCHED_NAME     VARCHAR(120) NOT NULL,
  TRIGGER_NAME   VARCHAR(200) NOT NULL,
  TRIGGER_GROUP  VARCHAR(200) NOT NULL,
  JOB_NAME       VARCHAR(200) NOT NULL,
  JOB_GROUP      VARCHAR(200) NOT NULL,
  DESCRIPTION    VARCHAR(250) NULL,
  NEXT_FIRE_TIME BIGINT(13)   NULL,
  PREV_FIRE_TIME BIGINT(13)   NULL,
  PRIORITY       INTEGER      NULL,
  TRIGGER_STATE  VARCHAR(16)  NOT NULL,
  TRIGGER_TYPE   VARCHAR(8)   NOT NULL,
  START_TIME     BIGINT(13)   NOT NULL,
  END_TIME       BIGINT(13)   NULL,
  CALENDAR_NAME  VARCHAR(200) NULL,
  MISFIRE_INSTR  SMALLINT(2)  NULL,
  JOB_DATA       BLOB         NULL,
  PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME, JOB_NAME, JOB_GROUP)
    REFERENCES QRTZ_JOB_DETAILS (SCHED_NAME, JOB_NAME, JOB_GROUP)
);

CREATE TABLE QRTZ_SIMPLE_TRIGGERS
(
  SCHED_NAME      VARCHAR(120) NOT NULL,
  TRIGGER_NAME    VARCHAR(200) NOT NULL,
  TRIGGER_GROUP   VARCHAR(200) NOT NULL,
  REPEAT_COUNT    BIGINT(7)    NOT NULL,
  REPEAT_INTERVAL BIGINT(12)   NOT NULL,
  TIMES_TRIGGERED BIGINT(10)   NOT NULL,
  PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
    REFERENCES QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

CREATE TABLE QRTZ_CRON_TRIGGERS
(
  SCHED_NAME      VARCHAR(120) NOT NULL,
  TRIGGER_NAME    VARCHAR(200) NOT NULL,
  TRIGGER_GROUP   VARCHAR(200) NOT NULL,
  CRON_EXPRESSION VARCHAR(120) NOT NULL,
  TIME_ZONE_ID    VARCHAR(80),
  PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
    REFERENCES QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

CREATE TABLE QRTZ_SIMPROP_TRIGGERS
(
  SCHED_NAME    VARCHAR(120)   NOT NULL,
  TRIGGER_NAME  VARCHAR(200)   NOT NULL,
  TRIGGER_GROUP VARCHAR(200)   NOT NULL,
  STR_PROP_1    VARCHAR(512)   NULL,
  STR_PROP_2    VARCHAR(512)   NULL,
  STR_PROP_3    VARCHAR(512)   NULL,
  INT_PROP_1    INT            NULL,
  INT_PROP_2    INT            NULL,
  LONG_PROP_1   BIGINT         NULL,
  LONG_PROP_2   BIGINT         NULL,
  DEC_PROP_1    NUMERIC(13, 4) NULL,
  DEC_PROP_2    NUMERIC(13, 4) NULL,
  BOOL_PROP_1   VARCHAR(1)     NULL,
  BOOL_PROP_2   VARCHAR(1)     NULL,
  PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
    REFERENCES QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

CREATE TABLE QRTZ_BLOB_TRIGGERS
(
  SCHED_NAME    VARCHAR(120) NOT NULL,
  TRIGGER_NAME  VARCHAR(200) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  BLOB_DATA     BLOB         NULL,
  PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
  INDEX (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
    REFERENCES QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

CREATE TABLE QRTZ_CALENDARS
(
  SCHED_NAME    VARCHAR(120) NOT NULL,
  CALENDAR_NAME VARCHAR(200) NOT NULL,
  CALENDAR      BLOB         NOT NULL,
  PRIMARY KEY (SCHED_NAME, CALENDAR_NAME)
);

CREATE TABLE QRTZ_PAUSED_TRIGGER_GRPS
(
  SCHED_NAME    VARCHAR(120) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  PRIMARY KEY (SCHED_NAME, TRIGGER_GROUP)
);

CREATE TABLE QRTZ_FIRED_TRIGGERS
(
  SCHED_NAME        VARCHAR(120) NOT NULL,
  ENTRY_ID          VARCHAR(95)  NOT NULL,
  TRIGGER_NAME      VARCHAR(200) NOT NULL,
  TRIGGER_GROUP     VARCHAR(200) NOT NULL,
  INSTANCE_NAME     VARCHAR(200) NOT NULL,
  FIRED_TIME        BIGINT(13)   NOT NULL,
  SCHED_TIME        BIGINT(13)   NOT NULL,
  PRIORITY          INTEGER      NOT NULL,
  STATE             VARCHAR(16)  NOT NULL,
  JOB_NAME          VARCHAR(200) NULL,
  JOB_GROUP         VARCHAR(200) NULL,
  IS_NONCONCURRENT  VARCHAR(1)   NULL,
  REQUESTS_RECOVERY VARCHAR(1)   NULL,
  PRIMARY KEY (SCHED_NAME, ENTRY_ID)
);

CREATE TABLE QRTZ_SCHEDULER_STATE
(
  SCHED_NAME        VARCHAR(120) NOT NULL,
  INSTANCE_NAME     VARCHAR(200) NOT NULL,
  LAST_CHECKIN_TIME BIGINT(13)   NOT NULL,
  CHECKIN_INTERVAL  BIGINT(13)   NOT NULL,
  PRIMARY KEY (SCHED_NAME, INSTANCE_NAME)
);

CREATE TABLE QRTZ_LOCKS
(
  SCHED_NAME VARCHAR(120) NOT NULL,
  LOCK_NAME  VARCHAR(40)  NOT NULL,
  PRIMARY KEY (SCHED_NAME, LOCK_NAME)
);


