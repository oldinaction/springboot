drop table if exists user_class, user;

create table user_class (class_id bigint not null auto_increment, class_name varchar(255), primary key (class_id));
create table user(id bigint not null auto_increment, class_id bigint, username varchar(255), password varchar(255), sex integer, wx_code varchar(255), role_code varchar(255), primary key (id));

