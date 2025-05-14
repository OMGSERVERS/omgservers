-- alias module

create table if not exists tab_alias (
    id bigint primary key,
    idempotency_key text not null unique,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    qualifier text not null,
    shard_key text not null,
    uniqueness_group bigint not null,
    entity_id bigint not null,
    alias_value text not null,
    deleted boolean not null
);

create unique index idx_alias_uniqueness on tab_alias(qualifier, shard_key, uniqueness_group, alias_value) where deleted = false;

-- pool module

create table if not exists tab_pool (
    id bigint primary key,
    idempotency_key text not null unique,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    config jsonb not null,
    deleted boolean not null
);

create table if not exists tab_pool_command (
    id bigint primary key,
    idempotency_key text not null unique,
    pool_id bigint not null references tab_pool(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    qualifier text not null,
    body jsonb not null,
    deleted boolean not null
);

create table if not exists tab_pool_request (
    id bigint primary key,
    idempotency_key text not null unique,
    pool_id bigint not null references tab_pool(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    runtime_id bigint not null,
    runtime_qualifier text not null,
    config jsonb not null,
    deleted boolean not null
);

create table if not exists tab_pool_server (
    id bigint primary key,
    idempotency_key text not null unique,
    pool_id bigint not null references tab_pool(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    qualifier text not null,
    config jsonb not null,
    status text not null,
    deleted boolean not null
);

create table if not exists tab_pool_container (
    id bigint primary key,
    idempotency_key text not null unique,
    pool_id bigint not null references tab_pool(id) on delete restrict on update restrict,
    server_id bigint not null references tab_pool_server(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    runtime_id bigint not null,
    runtime_qualifier text not null,
    config jsonb not null,
    deleted boolean not null
);

-- user module

create table if not exists tab_user (
    id bigint primary key,
    idempotency_key text not null unique,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    role text not null,
    password_hash text not null,
    config jsonb not null,
    deleted boolean not null
);

create table if not exists tab_user_player (
    id bigint primary key,
    idempotency_key text not null unique,
    user_id bigint not null references tab_user(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    tenant_id bigint not null,
    stage_id bigint not null,
    profile jsonb not null,
    deleted boolean not null
);

-- client module

create table if not exists tab_client (
    id bigint primary key,
    idempotency_key text not null unique,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    user_id bigint not null,
    player_id bigint not null,
    deployment_id bigint not null,
    config jsonb not null,
    deleted boolean not null
);

create table if not exists tab_client_message (
    id bigint primary key,
    idempotency_key text not null unique,
    client_id bigint not null references tab_client(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    qualifier text not null,
    body jsonb not null,
    deleted boolean not null
);

create table if not exists tab_client_runtime_ref (
    id bigint primary key,
    idempotency_key text not null unique,
    client_id bigint not null references tab_client(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    runtime_id bigint not null,
    deleted boolean not null
);

-- tenant module

create table if not exists tab_tenant (
    id bigint primary key,
    idempotency_key text not null unique,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    config jsonb not null,
    deleted boolean not null
);

create table if not exists tab_tenant_permission (
    id bigint primary key,
    idempotency_key text not null unique,
    tenant_id bigint not null references tab_tenant(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    user_id bigint not null,
    permission text not null,
    deleted boolean not null
);

create table if not exists tab_tenant_project (
    id bigint primary key,
    idempotency_key text not null unique,
    tenant_id bigint not null references tab_tenant(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    config jsonb not null,
    deleted boolean not null
);

create table if not exists tab_tenant_project_permission (
    id bigint primary key,
    idempotency_key text not null unique,
    tenant_id bigint not null references tab_tenant(id) on delete restrict on update restrict,
    project_id bigint not null references tab_tenant_project(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    user_id bigint not null,
    permission text not null,
    deleted boolean not null
);

create table if not exists tab_tenant_stage (
    id bigint primary key,
    idempotency_key text not null unique,
    tenant_id bigint not null references tab_tenant(id) on delete restrict on update restrict,
    project_id bigint not null references tab_tenant_project(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    config jsonb not null,
    deleted boolean not null
);

create table if not exists tab_tenant_stage_permission (
    id bigint primary key,
    idempotency_key text not null unique,
    tenant_id bigint not null references tab_tenant(id) on delete restrict on update restrict,
    stage_id bigint not null references tab_tenant_stage(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    user_id bigint not null,
    permission text not null,
    deleted boolean not null
);

create table if not exists tab_tenant_version (
    id bigint primary key,
    idempotency_key text not null unique,
    tenant_id bigint not null references tab_tenant(id) on delete restrict on update restrict,
    project_id bigint not null references tab_tenant_project(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    config jsonb not null,
    deleted boolean not null
);

create table if not exists tab_tenant_deployment_resource (
    id bigint primary key,
    idempotency_key text not null unique,
    tenant_id bigint not null references tab_tenant(id) on delete restrict on update restrict,
    stage_id bigint not null references tab_tenant_stage(id) on delete restrict on update restrict,
    version_id bigint not null references tab_tenant_version(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    deployment_id bigint not null,
    status text not null,
    deleted boolean not null
);

create table if not exists tab_tenant_deployment_ref (
    id bigint primary key,
    idempotency_key text not null unique,
    tenant_id bigint not null references tab_tenant(id) on delete restrict on update restrict,
    stage_id bigint not null references tab_tenant_stage(id) on delete restrict on update restrict,
    version_id bigint not null references tab_tenant_version(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    deployment_id bigint not null,
    deleted boolean not null
);

create table if not exists tab_tenant_image (
    id bigint primary key,
    idempotency_key text not null unique,
    tenant_id bigint not null references tab_tenant(id) on delete restrict on update restrict,
    version_id bigint not null references tab_tenant_version(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    qualifier text not null,
    image_id text not null,
    config jsonb not null,
    deleted boolean not null
);

create unique index idx_tenant_permission_uniqueness on tab_tenant_permission(tenant_id, user_id, permission) where deleted = false;
create unique index idx_tenant_project_permission_uniqueness on tab_tenant_project_permission(project_id, user_id, permission) where deleted = false;
create unique index idx_tenant_stage_permission_uniqueness on tab_tenant_stage_permission(stage_id, user_id, permission) where deleted = false;

-- deployment module

create table if not exists tab_deployment (
    id bigint primary key,
    idempotency_key text not null unique,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    tenant_id bigint not null,
    stage_id bigint not null,
    version_id bigint not null,
    config jsonb not null,
    deleted boolean not null
);

create table if not exists tab_deployment_command (
    id bigint primary key,
    idempotency_key text not null unique,
    deployment_id bigint not null references tab_deployment(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    qualifier text not null,
    body jsonb not null,
    deleted boolean not null
);

create table if not exists tab_deployment_request (
    id bigint primary key,
    idempotency_key text not null unique,
    deployment_id bigint not null references tab_deployment(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    client_id bigint not null,
    deleted boolean not null
);

create table if not exists tab_deployment_lobby_resource (
    id bigint primary key,
    idempotency_key text not null unique,
    deployment_id bigint not null references tab_deployment(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    lobby_id bigint not null,
    status text not null,
    deleted boolean not null
);

create table if not exists tab_deployment_lobby_assignment (
    id bigint primary key,
    idempotency_key text not null unique,
    deployment_id bigint not null references tab_deployment(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    client_id bigint not null,
    lobby_id bigint not null,
    deleted boolean not null
);

create table if not exists tab_deployment_matchmaker_resource (
    id bigint primary key,
    idempotency_key text not null unique,
    deployment_id bigint not null references tab_deployment(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    matchmaker_id bigint not null,
    status text not null,
    deleted boolean not null
);

create table if not exists tab_deployment_matchmaker_assignment (
    id bigint primary key,
    idempotency_key text not null unique,
    deployment_id bigint not null references tab_deployment(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    client_id bigint not null,
    matchmaker_id bigint not null,
    deleted boolean not null
);

-- lobby module

create table if not exists tab_lobby (
    id bigint primary key,
    idempotency_key text not null unique,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    deployment_id bigint not null,
    runtime_id bigint not null,
    config jsonb not null,
    deleted boolean not null
);

-- matchmaker module

create table if not exists tab_matchmaker (
    id bigint primary key,
    idempotency_key text not null unique,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    deployment_id bigint not null,
    config jsonb not null,
    deleted boolean not null
);

create table if not exists tab_matchmaker_command (
    id bigint primary key,
    idempotency_key text not null unique,
    matchmaker_id bigint not null references tab_matchmaker(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    qualifier text not null,
    body jsonb not null,
    deleted boolean not null
);

create table if not exists tab_matchmaker_request (
    id bigint primary key,
    idempotency_key text not null unique,
    matchmaker_id bigint not null references tab_matchmaker(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    client_id bigint not null,
    mode text not null,
    config jsonb not null,
    deleted boolean not null
);

create table if not exists tab_matchmaker_match_resource (
    id bigint primary key,
    idempotency_key text not null unique,
    matchmaker_id bigint not null references tab_matchmaker(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    match_id bigint not null,
    mode text not null,
    status text not null,
    deleted boolean not null
);

create table if not exists tab_matchmaker_match_assignment (
    id bigint primary key,
    idempotency_key text not null unique,
    matchmaker_id bigint not null references tab_matchmaker(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    match_id bigint not null,
    client_id bigint not null,
    group_name varchar(64) not null,
    config jsonb not null,
    deleted boolean not null
);

-- match module

create table if not exists tab_match (
    id bigint primary key,
    idempotency_key text not null unique,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    matchmaker_id bigint not null,
    runtime_id bigint not null,
    config jsonb not null,
    deleted boolean not null
);

-- runtime module

create table if not exists tab_runtime (
    id bigint primary key,
    idempotency_key text not null unique,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    deployment_id bigint not null,
    qualifier text not null,
    user_id bigint not null,
    config jsonb not null,
    deleted boolean not null
);

create table if not exists tab_runtime_command (
    id bigint primary key,
    idempotency_key text not null unique,
    runtime_id bigint not null references tab_runtime(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    qualifier text not null,
    body jsonb not null,
    deleted boolean not null
);

create table if not exists tab_runtime_message (
    id bigint primary key,
    idempotency_key text not null unique,
    runtime_id bigint not null references tab_runtime(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    qualifier text not null,
    body jsonb not null,
    deleted boolean not null
);

create table if not exists tab_runtime_assignment (
    id bigint primary key,
    idempotency_key text not null unique,
    runtime_id bigint not null references tab_runtime(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    client_id bigint not null,
    config jsonb not null,
    deleted boolean not null
);
