# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table tbl_category (
  id                        bigint auto_increment not null,
  category_code             varchar(255),
  category_name             varchar(255),
  comments                  varchar(255),
  constraint pk_tbl_category primary key (id))
;

create table tbl_category_details (
  id                        bigint auto_increment not null,
  category_code             varchar(255),
  category_sub_code         varchar(255),
  category_name             varchar(255),
  constraint pk_tbl_category_details primary key (id))
;

create table tbl_users (
  id                        bigint auto_increment not null,
  username                  varchar(255),
  name                      varchar(255),
  email                     varchar(255),
  password                  varchar(255),
  constraint pk_tbl_users primary key (id))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table tbl_category;

drop table tbl_category_details;

drop table tbl_users;

SET FOREIGN_KEY_CHECKS=1;

