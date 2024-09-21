-- root module

create table if not exists tab_root (
    id bigint primary key,
    idempotency_key text not null unique,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    deleted boolean not null
);

create table if not exists tab_root_entity_ref (
    id bigint primary key,
    idempotency_key text not null unique,
    root_id bigint not null references tab_root(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    qualifier text not null,
    entity_id bigint not null,
    deleted boolean not null
);

create index if not exists idx_root_entity_ref_root_id on tab_root_entity_ref(root_id);

-- pool module

create table if not exists tab_pool (
    id bigint primary key,
    idempotency_key text not null unique,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    deleted boolean not null
);

create table if not exists tab_pool_server (
    id bigint primary key,
    idempotency_key text not null unique,
    pool_id bigint not null references tab_pool(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    qualifier text not null,
    config json not null,
    deleted boolean not null
);

create table if not exists tab_pool_server_container (
    id bigint primary key,
    idempotency_key text not null unique,
    pool_id bigint not null references tab_pool(id) on delete restrict on update restrict,
    server_id bigint not null references tab_pool_server(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    runtime_id bigint not null,
    runtime_qualifier text not null,
    config json not null,
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
    config json not null,
    deleted boolean not null
);

create index if not exists idx_pool_server_pool_id on tab_pool_server(pool_id);
create index if not exists idx_pool_server_container_pool_id on tab_pool_server_container(pool_id);
create index if not exists idx_pool_server_container_server_id on tab_pool_server_container(server_id);
create index if not exists idx_pool_request_pool_id on tab_pool_request(pool_id);

-- user module

create table if not exists tab_user (
    id bigint primary key,
    idempotency_key text not null unique,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    role text not null,
    password_hash text not null,
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
    attributes json not null,
    profile json not null,
    deleted boolean not null
);

create index if not exists idx_user_player_user_id on tab_user_player(user_id);

-- client module

create table if not exists tab_client (
    id bigint primary key,
    idempotency_key text not null unique,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    user_id bigint not null,
    player_id bigint not null,
    tenant_id bigint not null,
    deployment_id bigint not null,
    deleted boolean not null
);

create table if not exists tab_client_message (
    id bigint primary key,
    idempotency_key text not null unique,
    client_id bigint not null references tab_client(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    qualifier text not null,
    body json not null,
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

create table if not exists tab_client_matchmaker_ref (
    id bigint primary key,
    idempotency_key text not null unique,
    client_id bigint not null references tab_client(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    matchmaker_id bigint not null,
    deleted boolean not null
);

create index if not exists idx_client_message_client_id on tab_client_message(client_id);
create index if not exists idx_client_runtime_ref_client_id on tab_client_runtime_ref(client_id);
create index if not exists idx_client_matchmaker_ref_client_id on tab_client_matchmaker_ref(client_id);

-- tenant module

create table if not exists tab_tenant (
    id bigint primary key,
    idempotency_key text not null unique,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    deleted boolean not null
);

create table if not exists tab_tenant_permission (
    id bigint primary key,
    idempotency_key text not null,
    tenant_id bigint not null references tab_tenant(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    user_id bigint not null,
    permission text not null,
    deleted boolean not null,
    unique(idempotency_key)
);

create table if not exists tab_tenant_project (
    id bigint primary key,
    idempotency_key text not null unique,
    tenant_id bigint not null references tab_tenant(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    deleted boolean not null
);

create table if not exists tab_tenant_project_permission (
    id bigint primary key,
    idempotency_key text not null,
    tenant_id bigint not null references tab_tenant(id) on delete restrict on update restrict,
    project_id bigint not null references tab_tenant_project(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    user_id bigint not null,
    permission text not null,
    deleted boolean not null,
    unique(idempotency_key)
);

create table if not exists tab_tenant_stage (
    id bigint primary key,
    idempotency_key text not null unique,
    tenant_id bigint not null references tab_tenant(id) on delete restrict on update restrict,
    project_id bigint not null references tab_tenant_project(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    secret text not null,
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
    config json not null,
    archive bytea not null,
    deleted boolean not null
);

create table if not exists tab_tenant_jenkins_request (
    id bigint primary key,
    idempotency_key text not null unique,
    tenant_id bigint not null references tab_tenant(id) on delete restrict on update restrict,
    version_id bigint not null references tab_tenant_version(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    qualifier text not null,
    build_number integer not null,
    deleted boolean not null
);

create table if not exists tab_tenant_image_ref (
    id bigint primary key,
    idempotency_key text not null unique,
    tenant_id bigint not null references tab_tenant(id) on delete restrict on update restrict,
    version_id bigint not null references tab_tenant_version(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    qualifier text not null,
    image_id text not null,
    deleted boolean not null
);

create table if not exists tab_tenant_deployment (
    id bigint primary key,
    idempotency_key text not null unique,
    tenant_id bigint not null references tab_tenant(id) on delete restrict on update restrict,
    stage_id bigint not null references tab_tenant_stage(id) on delete restrict on update restrict,
    version_id bigint not null references tab_tenant_version(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    deleted boolean not null
);

create table if not exists tab_tenant_lobby_request (
    id bigint primary key,
    idempotency_key text not null unique,
    tenant_id bigint not null references tab_tenant(id) on delete restrict on update restrict,
    deployment_id bigint not null references tab_tenant_deployment(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    lobby_id bigint not null,
    deleted boolean not null
);

create table if not exists tab_tenant_lobby_ref (
    id bigint primary key,
    idempotency_key text not null unique,
    tenant_id bigint not null references tab_tenant(id) on delete restrict on update restrict,
    deployment_id bigint not null references tab_tenant_deployment(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    lobby_id bigint not null,
    deleted boolean not null
);

create table if not exists tab_tenant_matchmaker_request (
    id bigint primary key,
    idempotency_key text not null unique,
    tenant_id bigint not null references tab_tenant(id) on delete restrict on update restrict,
    deployment_id bigint not null references tab_tenant_deployment(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    matchmaker_id bigint not null,
    deleted boolean not null
);

create table if not exists tab_tenant_matchmaker_ref (
    id bigint primary key,
    idempotency_key text not null unique,
    tenant_id bigint not null references tab_tenant(id) on delete restrict on update restrict,
    deployment_id bigint not null references tab_tenant_deployment(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    matchmaker_id bigint not null,
    deleted boolean not null
);

create index if not exists idx_tenant_permission_tenant_id on tab_tenant_permission(tenant_id);
create unique index idx_tenant_permission_uniqueness on tab_tenant_permission(tenant_id, user_id, permission) where deleted = false;
create index if not exists idx_tenant_project_tenant_id on tab_tenant_project(tenant_id);
create index if not exists idx_tenant_project_permission_tenant_id on tab_tenant_project_permission(tenant_id);
create index if not exists idx_tenant_project_permission_project_id on tab_tenant_project_permission(project_id);
create unique index idx_tenant_project_permission_uniqueness on tab_tenant_project_permission(project_id, user_id, permission) where deleted = false;
create index if not exists idx_tenant_stage_tenant_id on tab_tenant_stage(tenant_id);
create index if not exists idx_tenant_stage_project_id on tab_tenant_stage(project_id);
create index if not exists idx_tenant_stage_permission_tenant_id on tab_tenant_stage_permission(tenant_id);
create index if not exists idx_tenant_stage_permission_stage_id on tab_tenant_stage_permission(stage_id);
create unique index idx_tenant_stage_permission_uniqueness on tab_tenant_stage_permission(stage_id, user_id, permission) where deleted = false;
create index if not exists idx_tenant_version_tenant_id on tab_tenant_version(tenant_id);
create index if not exists idx_tenant_version_project_id on tab_tenant_version(project_id);
create index if not exists idx_tenant_jenkins_request_tenant_id on tab_tenant_jenkins_request(tenant_id);
create index if not exists idx_tenant_jenkins_request_version_id on tab_tenant_jenkins_request(version_id);
create index if not exists idx_tenant_image_ref_tenant_id on tab_tenant_image_ref(tenant_id);
create index if not exists idx_tenant_image_ref_version_id on tab_tenant_image_ref(version_id);
create index if not exists idx_tenant_deployment_tenant_id on tab_tenant_deployment(tenant_id);
create index if not exists idx_tenant_deployment_stage_id on tab_tenant_deployment(stage_id);
create index if not exists idx_tenant_deployment_version_id on tab_tenant_deployment(version_id);
create index if not exists idx_tenant_lobby_request_tenant_id on tab_tenant_lobby_request(tenant_id);
create index if not exists idx_tenant_lobby_request_deployment_id on tab_tenant_lobby_request(deployment_id);
create index if not exists idx_tenant_lobby_ref_tenant_id on tab_tenant_lobby_ref(tenant_id);
create index if not exists idx_tenant_lobby_ref_deployment_id on tab_tenant_lobby_ref(deployment_id);
create index if not exists idx_tenant_matchmaker_request_tenant_id on tab_tenant_matchmaker_request(tenant_id);
create index if not exists idx_tenant_matchmaker_request_deployment_id on tab_tenant_matchmaker_request(deployment_id);
create index if not exists idx_tenant_matchmaker_ref_tenant_id on tab_tenant_matchmaker_ref(tenant_id);
create index if not exists idx_tenant_matchmaker_ref_deployment_id on tab_tenant_matchmaker_ref(deployment_id);

-- lobby module

create table if not exists tab_lobby (
    id bigint primary key,
    idempotency_key text not null unique,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    tenant_id bigint not null,
    deployment_id bigint not null,
    runtime_id bigint not null,
    deleted boolean not null
);

create table if not exists tab_lobby_runtime_ref (
    id bigint primary key,
    idempotency_key text not null unique,
    lobby_id bigint not null references tab_lobby(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    runtime_id bigint not null,
    deleted boolean not null
);

create index if not exists idx_lobby_runtime_ref_lobby_id on tab_lobby_runtime_ref(lobby_id);

-- matchmaker module

create table if not exists tab_matchmaker (
    id bigint primary key,
    idempotency_key text not null unique,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    tenant_id bigint not null,
    deployment_id bigint not null,
    deleted boolean not null
);

create table if not exists tab_matchmaker_assignment (
    id bigint primary key,
    idempotency_key text not null unique,
    matchmaker_id bigint not null references tab_matchmaker(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    client_id bigint not null,
    deleted boolean not null
);

create table if not exists tab_matchmaker_command (
    id bigint primary key,
    idempotency_key text not null unique,
    matchmaker_id bigint not null references tab_matchmaker(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    qualifier text not null,
    body json not null,
    deleted boolean not null
);

create table if not exists tab_matchmaker_request (
    id bigint primary key,
    idempotency_key text not null unique,
    matchmaker_id bigint not null references tab_matchmaker(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    user_id bigint not null,
    client_id bigint not null,
    mode varchar(64) not null,
    config json not null,
    deleted boolean not null
);

create table if not exists tab_matchmaker_match (
    id bigint primary key,
    idempotency_key text not null unique,
    matchmaker_id bigint not null references tab_matchmaker(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    runtime_id bigint not null,
    config json not null,
    status text not null,
    deleted boolean not null
);

create table if not exists tab_matchmaker_match_runtime_ref (
    id bigint primary key,
    idempotency_key text not null unique,
    matchmaker_id bigint not null references tab_matchmaker(id) on delete restrict on update restrict,
    match_id bigint not null references tab_matchmaker_match(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    runtime_id bigint not null,
    deleted boolean not null
);

create table if not exists tab_matchmaker_match_client (
    id bigint primary key,
    idempotency_key text not null unique,
    matchmaker_id bigint not null references tab_matchmaker(id) on delete restrict on update restrict,
    match_id bigint not null references tab_matchmaker_match(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    user_id bigint not null,
    client_id bigint not null,
    group_name varchar(64) not null,
    config json not null,
    deleted boolean not null
);

create index if not exists idx_matchmaker_assignment_matchmaker_id on tab_matchmaker_assignment(matchmaker_id);
create index if not exists idx_matchmaker_command_matchmaker_id on tab_matchmaker_command(matchmaker_id);
create index if not exists idx_matchmaker_request_matchmaker_id on tab_matchmaker_request(matchmaker_id);
create index if not exists idx_matchmaker_match_matchmaker_id on tab_matchmaker_match(matchmaker_id);
create index if not exists idx_matchmaker_match_runtime_ref_matchmaker_id on tab_matchmaker_match_runtime_ref(matchmaker_id);
create index if not exists idx_matchmaker_match_runtime_ref_match_id on tab_matchmaker_match_runtime_ref(match_id);
create index if not exists idx_matchmaker_match_client_matchmaker_id on tab_matchmaker_match_client(matchmaker_id);
create index if not exists idx_matchmaker_match_client_match_id on tab_matchmaker_match_client(match_id);

-- runtime module

create table if not exists tab_runtime (
    id bigint primary key,
    idempotency_key text not null unique,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    tenant_id bigint not null,
    deployment_id bigint not null,
    qualifier text not null,
    user_id bigint not null,
    last_activity timestamp with time zone not null,
    config json not null,
    deleted boolean not null
);

create table if not exists tab_runtime_permission (
    id bigint primary key,
    idempotency_key text not null unique,
    runtime_id bigint not null references tab_runtime(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    user_id bigint not null,
    permission text not null,
    deleted boolean not null
);

create table if not exists tab_runtime_command (
    id bigint primary key,
    idempotency_key text not null unique,
    runtime_id bigint not null references tab_runtime(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    qualifier text not null,
    body json not null,
    deleted boolean not null
);

create table if not exists tab_runtime_assignment (
    id bigint primary key,
    idempotency_key text not null unique,
    runtime_id bigint not null references tab_runtime(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    client_id bigint not null,
    last_activity timestamp with time zone not null,
    config json not null,
    deleted boolean not null
);

create table if not exists tab_runtime_pool_server_container_ref (
    id bigint primary key,
    idempotency_key text not null unique,
    runtime_id bigint not null references tab_runtime(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    pool_id bigint not null,
    server_id bigint not null,
    container_id bigint not null,
    deleted boolean not null
);

create index if not exists idx_runtime_permission_runtime_id on tab_runtime_permission(runtime_id);
create unique index idx_runtime_permission_uniqueness on tab_runtime_permission(runtime_id, user_id, permission) where deleted = false;
create index if not exists idx_runtime_command_runtime_id on tab_runtime_command(runtime_id);
create index if not exists idx_runtime_assignment_runtime_id on tab_runtime_assignment(runtime_id);
create index if not exists idx_runtime_pool_server_container_ref_runtime_id on tab_runtime_pool_server_container_ref(runtime_id);