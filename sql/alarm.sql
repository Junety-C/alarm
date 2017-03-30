CREATE TABLE `tb_alarm` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` int(11) NOT NULL COMMENT '告警代号',
  `name` varchar(100) NOT NULL COMMENT '告警名称',
  `project_id` int(11) NOT NULL COMMENT '項目id',
  `module_id` int(11) NOT NULL COMMENT '模块id',
  `group_id` int(11) NOT NULL COMMENT '接收组id',
  `route_key` varchar(100) DEFAULT '' COMMENT '路由key',
  `config` varchar(512) DEFAULT NULL COMMENT '告警配置',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

CREATE TABLE `tb_alarm_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `report_id` bigint(20) NOT NULL COMMENT '日志编号',
  `code` int(11) NOT NULL COMMENT '告警代号',
  `alarm_name` varchar(100) DEFAULT NULL COMMENT '告警名称',
  `project_name` varchar(100) DEFAULT NULL COMMENT '项目名称',
  `module_name` varchar(100) DEFAULT NULL COMMENT '模块名称',
  `group_name` varchar(50) DEFAULT NULL COMMENT '接收组名称',
  `level` varchar(10) DEFAULT NULL COMMENT '告警级别',
  `receivers` varchar(500) DEFAULT NULL COMMENT '接收者',
  `content` varchar(512) NOT NULL COMMENT '告警内容',
  `ip` varchar(25) DEFAULT NULL COMMENT 'ip地址',
  `status` tinyint NOT NULL COMMENT '告警状态：0:创建、1:已发送、2:限频、3:测试',
  `delivery_status` varchar(30) DEFAULT NULL COMMENT '推送状态',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=523 DEFAULT CHARSET=utf8;

CREATE TABLE `tb_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '接收组名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

CREATE TABLE `tb_group_member` (
  `group_id` int(11) NOT NULL COMMENT '接收组id',
  `receiver_id` int(11) NOT NULL COMMENT '接收者id',
  PRIMARY KEY (`group_id`,`receiver_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tb_module` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_id` int(11) NOT NULL COMMENT '项目id',
  `name` varchar(100) NOT NULL COMMENT '模块名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

CREATE TABLE `tb_project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '项目名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

CREATE TABLE `tb_receiver` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) NOT NULL COMMENT '接收者姓名',
  `phone` varchar(30) DEFAULT NULL COMMENT '手机号码',
  `mail` varchar(30) DEFAULT NULL COMMENT '邮箱',
  `wechat` varchar(30) DEFAULT NULL COMMENT '微信号',
  `qq` varchar(30) DEFAULT NULL COMMENT 'QQ号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;


-- 初始化数据
insert into tb_project(id, name) values(1, '告警系统');

insert into tb_module(id, project_id, name) values(1, 1, '告警服务端'),(2, 1, '告警发送端'),(3, 1, '告警web端');

-- 替换成你的信息
insert into tb_receiver(id, name, phone, mail, wechat, qq) values(1, 'your name', 'phone', 'mail', 'wechat', 'qq');

insert into tb_group(id, name) values(1, '告警系统管理组');
insert into tb_group_member(group_id, receiver_id) VALUES(1, 1);

insert into tb_alarm(id, code, name, project_id, module_id, group_id, route_key, config) values
  (1, 1, '告警服务端异常', 1, 1, 1, 'alarm.server.*', '{"freq_limit":false,"mail":true,"wechat":true,"sms":false,"qq":true,"debug_interval":5,"debug_times":10,"info_interval":3,"info_times":10,"error_interval":1,"error_times":10}'),
  (2, 1, '告警发送异常', 1, 1, 1, 'alarm.sender.*', '{"freq_limit":false,"mail":true,"wechat":true,"sms":false,"qq":true,"debug_interval":5,"debug_times":10,"info_interval":3,"info_times":10,"error_interval":1,"error_times":10}'),
  (3, 1, '告警web端异常', 1, 1, 1, 'alarm.web.*', '{"freq_limit":false,"mail":true,"wechat":true,"sms":false,"qq":true,"debug_interval":5,"debug_times":10,"info_interval":3,"info_times":10,"error_interval":1,"error_times":10}');
