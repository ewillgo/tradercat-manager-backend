-- 贸易猫系统

-- 账单表
create table t_trader_bills (
  id int not null auto_increment primary key comment '主键ID',
  user_id varchar(64) not null comment '用户ID',
  bills_type mediumint not null comment '账单类型',
  name varchar(255) not null comment '账单标题',
  price int not null default 0 comment '购买单价',
  quantity int not null comment '购买数量',
  remark varchar(255) null comment '备注',
  image varchar(255) null comment '票据图片',
  create_time datetime not null default current_timestamp comment '创建时间',
  update_time datetime null on update current_timestamp comment '更新时间'
) engine Innodb charset utf8;

create index idx_trader_bills_user_id on t_trader_bills(user_id);
create index idx_trader_bills_bills_type on t_trader_bills(bills_type);

-- -------- 信息收集 ----------

-- 新闻表
# create table t_news (
#   id int not null auto_increment primary key comment '主键ID',
#   title varchar(255) null comment '标题',
#   url varchar(1000) null comment '链接地址',
#   tag varchar(255) null comment '标签',
#   create_time datetime not null default current_timestamp comment '创建时间'
# ) engine Innodb charset utf8;
#
# create index idx_news_create_time on t_news(create_time);

-- -------- 爬虫相关表 ----------

-- 任务类型表
create table t_task_type (
  id int not null auto_increment primary key comment '主键ID',
  user_id int not null default 0 comment '用户ID',
  name varchar(255) not null comment '类型名称',
  description varchar(2000) null comment '类型描述',
  package_name varchar(255) not null comment 'java类全路径',
  status bit not null default 1 comment '状态 0-禁用; 1-启动',
  deleted bit not null default 0 comment '删除状态',
  create_time datetime not null default current_timestamp comment '创建时间',
  update_time datetime null on update current_timestamp comment '更新时间'
) engine InnoDB charset utf8;

create index idx_task_type_user_id on t_task_type(user_id);
create unique index uniq_task_type_name on t_task_type(name);

-- 任务表
create table t_task (
  id int not null auto_increment primary key comment '主键ID',
  user_id int not null comment '用户ID',
  type_id int not null comment '类型ID',
  name varchar(255) not null comment '任务名称',
  description varchar(1000) null comment '任务描述',
  execute_type tinyint not null default 0 comment '任务执行类型 0-即时; 1-定时',
  status tinyint not null default 0 comment '任务状态 0-停止; 1-运行中; 2-暂停',
  cron_expression varchar(50) null comment 'cron表达式',
  deleted bit not null default 0 comment '删除状态',
  create_time datetime not null default current_timestamp comment '创建时间',
  update_time datetime null on update current_timestamp comment '更新时间'
) engine InnoDB charset utf8;

create index idx_task_user_id on t_task(user_id);
create index idx_task_type_id on t_task(type_id);
create unique index uniq_task_name on t_task(name);
