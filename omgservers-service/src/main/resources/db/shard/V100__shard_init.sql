create table if not exists tab_job (
    id bigint primary key,
    idempotency_key text not null unique,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    qualifier text not null,
    shard_key bigint not null,
    entity_id bigint not null,
    deleted boolean not null
);

create table if not exists tab_event (
    id bigint primary key,
    idempotency_key text not null unique,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    qualifier text not null,
    body jsonb not null,
    status text not null,
    deleted boolean not null
);
