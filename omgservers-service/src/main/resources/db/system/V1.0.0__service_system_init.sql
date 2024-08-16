create table if not exists tab_index (
    id bigint primary key,
    idempotency_key text not null,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    config json not null,
    deleted boolean not null
);

create table if not exists tab_job (
    id bigint primary key,
    idempotency_key text not null,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    qualifier text not null,
    shard_key bigint not null,
    entity_id bigint not null,
    deleted boolean not null,
    unique(idempotency_key)
);

create table if not exists tab_event (
    id bigint primary key,
    idempotency_key text not null,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    delayed timestamp with time zone not null,
    qualifier text not null,
    body json not null,
    status text not null,
    deleted boolean not null,
    unique(idempotency_key)
);

create index if not exists idx_job_entity_id on tab_job(entity_id);