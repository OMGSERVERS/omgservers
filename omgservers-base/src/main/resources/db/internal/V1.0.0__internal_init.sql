create table if not exists tab_index (
    id bigint primary key,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    name varchar(64) not null unique,
    version bigint not null,
    config json not null
);

create table if not exists tab_service_account (
    id bigint primary key,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    username varchar(64) not null unique,
    password_hash varchar(64) not null
);

create table if not exists tab_event (
    id bigint primary key,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    group_id bigint not null,
    qualifier text not null,
    body json not null,
    status text not null
);

create table if not exists tab_job (
    id bigint primary key,
    created timestamp with time zone not null,
    shard_key bigint not null,
    entity bigint not null,
    type text not null,
    unique(shard_key, entity)
);

create table if not exists tab_log (
    id bigint primary key,
    created timestamp with time zone not null,
    message text not null
);
