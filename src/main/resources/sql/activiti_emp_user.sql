create table emp_user
(
	id int auto_increment comment '主键'
		primary key,
	user_name varchar(50) default '' not null comment '用户名',
	password varchar(50) default '' not null comment '密码',
	email varchar(50) default '' not null comment '邮箱',
	role varchar(30) default '' not null comment '角色',
	manager_id int default 0 not null comment '上级领导Id'
)
comment '员工用户表';

INSERT INTO activiti.emp_user (id, user_name, password, email, role, manager_id) VALUES (1, 'zhaoliu', '123', 'zl@qq.com', 'boss', 0);
INSERT INTO activiti.emp_user (id, user_name, password, email, role, manager_id) VALUES (2, 'qianwu', '123', 'qw@qq.com', 'manager', 1);
INSERT INTO activiti.emp_user (id, user_name, password, email, role, manager_id) VALUES (3, 'mazi', '123', 'mz@qq.com', 'manager', 1);
INSERT INTO activiti.emp_user (id, user_name, password, email, role, manager_id) VALUES (4, 'wanger', '123', 'we@qq.com', 'manager', 2);
INSERT INTO activiti.emp_user (id, user_name, password, email, role, manager_id) VALUES (5, 'lishi', '123', 'ls@qq.com', 'user', 2);
INSERT INTO activiti.emp_user (id, user_name, password, email, role, manager_id) VALUES (6, 'zhangsan', '123', 'zs@qq.com', 'user', 2);


create table stock_daily (
     id int not null auto_increment primary key,
     code varchar(50) not null default '' comment '股票代码',
     open_price varchar(50) not null  default '' comment '开肋价',
     heigh_price varchar(50) not null default '' comment '最大值',
     low_price varchar(50) not null default '' comment '最小值',
     close_prive varchar(50) not null default '' comment '收盘价',
     avg_price varchar(50) not null default '' comment '平均值',
     top_limit varchar(50) not null default '' comment '涨停价',
     down_limit varchar(50) not null  default '' comment '跌停价',
     date date  not null comment '日期',
     create_time datetime not null default current_timestamp comment '创建时间'
);

create table  prediction (
     id int not null auto_increment primary key,
     code varchar(50) not null default '' comment '股票代码',
     highest_price varchar(50)  not null default '' comment '最高价',
     second_high_price varchar(50) not null default '' comment '次高价',
     second_low_price varchar(50) not null default '' comment '次低价',
     lowest_price varchar(50) not null  default '' comment '最低价',
     date date not null  comment '预测哪天的股价',
     create_time datetime not null default current_timestamp comment '创建时间'
)