# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table access_token (
  id                        integer auto_increment not null,
  token                     varchar(255),
  user_id                   integer,
  constraint pk_access_token primary key (id))
;

create table user (
  id                        integer auto_increment not null,
  name                      varchar(255),
  email                     varchar(255),
  password                  varchar(255),
  constraint pk_user primary key (id))
;

alter table access_token add constraint fk_access_token_user_1 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_access_token_user_1 on access_token (user_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table access_token;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

