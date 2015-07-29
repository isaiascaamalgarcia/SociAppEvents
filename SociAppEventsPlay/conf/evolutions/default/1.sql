# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table access_token (
  id                        integer not null,
  token                     varchar(255),
  user_id                   integer,
  id_user                   integer,
  constraint pk_access_token primary key (id))
;

create table event (
  id                        integer not null,
  name                      varchar(255),
  day                       timestamp,
  description               varchar(255),
  host_id                   integer,
  constraint pk_event primary key (id))
;

create table photo (
  id                        integer not null,
  title                     varchar(255),
  description               varchar(255),
  base64                    TEXT,
  type                      varchar(255),
  date_photo                timestamp,
  user_id                   integer,
  constraint pk_photo primary key (id))
;

create table user (
  id                        integer not null,
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
create sequence access_token_seq;

create sequence event_seq;

create sequence photo_seq;

create sequence user_seq;

alter table access_token add constraint fk_access_token_user_1 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_access_token_user_1 on access_token (user_id);
alter table event add constraint fk_event_host_2 foreign key (host_id) references user (id) on delete restrict on update restrict;
create index ix_event_host_2 on event (host_id);
alter table photo add constraint fk_photo_user_3 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_photo_user_3 on photo (user_id);



alter table event_user add constraint fk_event_user_event_01 foreign key (event_id) references event (id) on delete restrict on update restrict;

alter table event_user add constraint fk_event_user_user_02 foreign key (user_id) references user (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists access_token;

drop table if exists event;

drop table if exists event_user;

drop table if exists photo;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists access_token_seq;

drop sequence if exists event_seq;

drop sequence if exists photo_seq;

drop sequence if exists user_seq;

