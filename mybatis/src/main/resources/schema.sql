drop table if exists group_info, user_info;

create table group_info (group_id bigint not null auto_increment, group_name varchar(255), primary key (group_id));
create table user_info(id bigint not null auto_increment, group_id bigint, nick_name varchar(255), hobby varchar(255), primary key (id));

