create database spring_init;

use spring_init;

create table authority (
    id bigint not null auto_increment,
    name varchar(255) UNIQUE not null,
    primary key (id)
)

create table role (
    id bigint not null auto_increment,
    name varchar(255) UNIQUE not null,
    primary key (id)
)

create table user (
    created_date datetime(6),
    id bigint not null auto_increment,
    password varchar(255) not null,
    username varchar(255) UNIQUE not null,
    primary key (id)
)

create table role_authority (
    authority_id bigint not null,
    role_id bigint not null,
    primary key (authority_id, role_id),
    FOREIGN KEY (role_id) REFERENCES role(id),
    FOREIGN KEY (authority_id) REFERENCES authority(id)
)

create table user_role (
    role_id bigint not null,
    user_id bigint not null,
    primary key (role_id, user_id),
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (role_id) REFERENCES role(id)
)
