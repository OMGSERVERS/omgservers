create table if not exists tab_index (
    id bigint primary key,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    name text not null,
    config json not null,
    deleted boolean not null,
    unique(name)
);

create table if not exists tab_service_account (
    id bigint primary key,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    username text not null,
    password_hash text not null,
    deleted boolean not null,
    unique(username)
);

create table if not exists tab_container (
    id bigint primary key,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    entity_id bigint not null,
    qualifier text not null,
    image text not null,
    config json not null,
    deleted boolean not null,
    unique(entity_id)
);

create table if not exists tab_event (
    id bigint primary key,
    idempotency_key text not null unique,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    delayed timestamp with time zone not null,
    qualifier text not null,
    body json not null,
    status text not null,
    deleted boolean not null
);

create index if not exists idx_index_name on tab_index(name);
create index if not exists idx_service_account on tab_service_account(username);
create index if not exists idx_container_entity_id on tab_container(entity_id);