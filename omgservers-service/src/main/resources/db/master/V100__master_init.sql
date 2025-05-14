create table if not exists tab_node (
    id bigint primary key,
    modified timestamp with time zone not null,
    deleted boolean not null
);

insert into tab_node(id, modified, deleted) select generate_series(0, 4095), now(), false;

create table if not exists tab_index (
    id bigint primary key,
    idempotency_key text not null unique,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    config jsonb not null,
    deleted boolean not null
);

create table if not exists tab_entity (
    id bigint primary key,
    idempotency_key text not null unique,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    qualifier text not null,
    entity_id bigint not null,
    deleted boolean not null
);