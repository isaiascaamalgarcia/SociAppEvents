# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table access_token (
  id                        integer auto_increment not null,
  token                     varchar(255),
  user_id                   integer,
  id_user                   integer,
  constraint pk_access_token primary key (id))
;

create table event (
  id                        integer auto_increment not null,
  name                      varchar(255),
  due_date                  datetime(6),
  description               varchar(255),
  host_id                   integer,
  constraint pk_event primary key (id))
;

create table user (
  id                        integer auto_increment not null,
  name                      varchar(255),
  email                     varchar(255),
  password                  varchar(255),
  constraint pk_user primary key (id))
;


create table event_user (
  event_id                       integer not null,
  user_id                        integer not null,
  constraint pk_event_user primary key (event_id, user_id))
;
alter table access_token add constraint fk_access_token_user_1 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_access_token_user_1 on access_token (user_id);
alter table event add constraint fk_event_host_2 foreign key (host_id) references user (id) on delete restrict on update restrict;
create index ix_event_host_2 on event (host_id);



alter table event_user add constraint fk_event_user_event_01 foreign key (event_id) references event (id) on delete restrict on update restrict;

alter table event_user add constraint fk_event_user_user_02 foreign key (user_id) references user (id) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table access_token;

drop table event;

drop table event_user;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

