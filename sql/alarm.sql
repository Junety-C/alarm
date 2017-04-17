
CREATE TABLE `tb_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `account` varchar(64) NOT NULL COMMENT '用户名',
  `name` varchar(16) NOT NULL COMMENT '姓名',
  `identification` varchar(40) NOT NULL COMMENT '唯一标识',
  `type` tinyint NOT NULL DEFAULT '1' COMMENT '0管理员，1普通用户，2其他用户，-1无效',
  `mail` varchar(32) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(16) DEFAULT NULL COMMENT '手机号码',
  `wechat` varchar(32) DEFAULT NULL COMMENT '微信号',
  `qq` varchar(16) DEFAULT NULL COMMENT 'QQ号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uidx_user_name` (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

CREATE TABLE `tb_project` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL COMMENT '项目名称',
  `creater` varchar(16) NOT NULL COMMENT '创建者',
  `create_time` bigint NOT NULL COMMENT '创建时间',
  `comment` varchar(512) NOT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '项目表';

CREATE TABLE `tb_project_member` (
  `project_id` int NOT NULL COMMENT '项目id',
  `user_id` int NOT NULL COMMENT '用户id',
  `type` tinyint NOT NULL DEFAULT '1' COMMENT '0管理员，1成员',
  PRIMARY KEY (`project_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '项目成员表';

CREATE TABLE `tb_module` (
  `id` int NOT NULL AUTO_INCREMENT,
  `project_id` int NOT NULL COMMENT '项目id',
  `name` varchar(64) NOT NULL COMMENT '模块名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '模块表';

CREATE TABLE `tb_group` (
  `id` int NOT NULL AUTO_INCREMENT,
  `project_id` int NOT NULL COMMENT '项目id',
  `name` varchar(64) NOT NULL COMMENT '接收组名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '接收组表';

CREATE TABLE `tb_group_member` (
  `group_id` int NOT NULL COMMENT '接收组id',
  `user_id` int NOT NULL COMMENT '用户id',
  PRIMARY KEY (`group_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '接收组成员表';

CREATE TABLE `tb_alarm` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` int NOT NULL COMMENT '告警代号',
  `name` varchar(128) NOT NULL COMMENT '告警名称',
  `project_id` int NOT NULL COMMENT '項目id',
  `module_id` int NOT NULL COMMENT '模块id',
  `group_id` int NOT NULL COMMENT '接收组id',
  `route_key` varchar(128) DEFAULT '' COMMENT '路由key',
  `config` varchar(512) DEFAULT NULL COMMENT '告警配置',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='告警配置表';

CREATE TABLE `tb_alarm_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `report_id` bigint NOT NULL COMMENT '日志编号',
  `code` int NOT NULL COMMENT '告警代号',
  `alarm_name` varchar(128) COMMENT '告警名称',
  `project_name` varchar(64) COMMENT '项目名称',
  `module_name` varchar(64) COMMENT '模块名称',
  `group_name` varchar(64) COMMENT '接收组名称',
  `level` varchar(10) COMMENT '告警级别',
  `receivers` varchar(512) COMMENT '接收者',
  `content` varchar(512) NOT NULL COMMENT '告警内容',
  `ip` varchar(32) DEFAULT NULL COMMENT 'ip地址',
  `status` tinyint NOT NULL COMMENT '告警状态：0:创建、1:已发送、2:限频、3:测试',
  `delivery_status` varchar(32) DEFAULT NULL COMMENT '推送状态',
  `create_time` bigint NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '告警日志表';

-- TODO 更改成管理员的信息
insert into tb_user(id, account, name, identification, type, mail, phone, wechat, qq)
values(1, '--your account--', '--your name--', '9ad46aaf-d7d2-4095-a2a0-c27ee796d8e1', 0,
          '--your mail--', '--your phone--', '--your wechat--', '--your qq--');

-- TODO 更改成管理员的信息
insert into tb_project(id, name, creater, create_time, comment) values(1, '告警系统', '--your account--', unix_timestamp(now())*1000, '告警系统');

insert into tb_project_member(project_id, user_id, type) values(1, 1, 0);

insert into tb_module(id, project_id, name) values(1, 1, '告警服务端'),(2, 1, '告警发送端'),(3, 1, '告警web端');

insert into tb_group(id, project_id, name) values(1, 1, '告警系统管理组');

insert into tb_group_member(group_id, user_id) VALUES(1, 1);

insert into tb_alarm(id, code, name, project_id, module_id, group_id, route_key, config) values
  (1, 1, '告警服务端异常', 1, 1, 1, 'alarm.server.*', '{"freq_limit":true,"mail":true,"wechat":false,"sms":false,"qq":false,"debug_interval":5,"debug_times":10,"info_interval":3,"info_times":10,"error_interval":1,"error_times":10}'),
  (2, 1, '告警发送异常', 1, 1, 1, 'alarm.sender.*', '{"freq_limit":true,"mail":true,"wechat":false,"sms":false,"qq":false,"debug_interval":5,"debug_times":10,"info_interval":3,"info_times":10,"error_interval":1,"error_times":10}'),
  (3, 1, '告警web端异常', 1, 1, 1, 'alarm.web.*', '{"freq_limit":true,"mail":true,"wechat":false,"sms":false,"qq":false,"debug_interval":5,"debug_times":10,"info_interval":3,"info_times":10,"error_interval":1,"error_times":10}');

