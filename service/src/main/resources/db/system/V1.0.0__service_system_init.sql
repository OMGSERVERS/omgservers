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

create table if not exists tab_job (
    id bigint primary key,
    idempotency_key text not null,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    qualifier text not null,
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

create index if not exists idx_index_name on tab_index(name);
create index if not exists idx_service_account on tab_service_account(username);
create index if not exists idx_job_entity_id on tab_job(entity_id);