-- user module

create table if not exists tab_user (
    id bigint primary key,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    role text not null,
    password_hash text not null,
    deleted boolean not null
);

create table if not exists tab_user_token (
    id bigint primary key,
    user_id bigint not null references tab_user(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    expire timestamp with time zone not null,
    hash text not null,
    deleted boolean not null
);

create index if not exists idx_user_token_user_id on tab_user_token(user_id);

create table if not exists tab_user_player (
    id bigint primary key,
    user_id bigint not null references tab_user(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    tenant_id bigint not null,
    stage_id bigint not null,
    attributes json not null,
    profile json not null,
    deleted boolean not null,
    unique(user_id, stage_id)
);

create index if not exists idx_user_player_user_id on tab_user_player(user_id);

create table if not exists tab_user_client (
    id bigint primary key,
    user_id bigint not null references tab_user(id) on delete restrict on update restrict,
    player_id bigint not null references tab_user_player(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    server text not null,
    connection_id bigint not null,
    version_id bigint not null,
    default_matchmaker_id bigint not null,
    default_runtime_id bigint not null,
    deleted boolean not null,
    unique(connection_id)
);

create index if not exists idx_user_client_user_id on tab_user_client(user_id);
create index if not exists idx_user_client_player_id on tab_user_client(player_id);

-- tenant module

create table if not exists tab_tenant (
    id bigint primary key,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    deleted boolean not null
);

create table if not exists tab_tenant_permission (
    id bigint primary key,
    tenant_id bigint not null references tab_tenant(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    user_id bigint not null,
    permission text not null,
    deleted boolean not null,
    unique(tenant_id, user_id, permission)
);

create index if not exists idx_tenant_permission_tenant_id on tab_tenant_permission(tenant_id);

create table if not exists tab_tenant_project (
    id bigint primary key,
    tenant_id bigint not null references tab_tenant(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    deleted boolean not null
);

create index if not exists idx_tenant_project_tenant_id on tab_tenant_project(tenant_id);

create table if not exists tab_tenant_project_permission (
    id bigint primary key,
    tenant_id bigint not null references tab_tenant(id) on delete restrict on update restrict,
    project_id bigint not null references tab_tenant_project(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    user_id bigint not null,
    permission text not null,
    deleted boolean not null,
    unique(project_id, user_id, permission)
);

create index if not exists idx_tenant_project_permission_tenant_id on tab_tenant_project_permission(tenant_id);
create index if not exists idx_tenant_project_permission_project_id on tab_tenant_project_permission(project_id);

create table if not exists tab_tenant_stage (
    id bigint primary key,
    tenant_id bigint not null references tab_tenant(id) on delete restrict on update restrict,
    project_id bigint not null references tab_tenant_project(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    secret text not null,
    deleted boolean not null
);

create index if not exists idx_tenant_stage_tenant_id on tab_tenant_stage(tenant_id);
create index if not exists idx_tenant_stage_project_id on tab_tenant_stage(project_id);

create table if not exists tab_tenant_stage_permission (
    id bigint primary key,
    tenant_id bigint not null references tab_tenant(id) on delete restrict on update restrict,
    stage_id bigint not null references tab_tenant_stage(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    user_id bigint not null,
    permission text not null,
    deleted boolean not null,
    unique(stage_id, user_id, permission)
);

create index if not exists idx_tenant_stage_permission_tenant_id on tab_tenant_stage_permission(tenant_id);
create index if not exists idx_tenant_stage_permission_stage_id on tab_tenant_stage_permission(stage_id);

create table if not exists tab_tenant_version (
    id bigint primary key,
    tenant_id bigint not null references tab_tenant(id) on delete restrict on update restrict,
    stage_id bigint not null references tab_tenant_stage(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    config json not null,
    source_code json not null,
    deleted boolean not null
);

create index if not exists idx_tenant_version_tenant_id on tab_tenant_version(tenant_id);
create index if not exists idx_tenant_version_stage_id on tab_tenant_version(stage_id);

create table if not exists tab_tenant_version_matchmaker (
    id bigint primary key,
    tenant_id bigint not null references tab_tenant(id) on delete restrict on update restrict,
    version_id bigint not null references tab_tenant_version(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    matchmaker_id bigint not null,
    deleted boolean not null,
    unique(matchmaker_id)
);

create index if not exists idx_tenant_version_matchmaker_tenant_id on tab_tenant_version_matchmaker(tenant_id);
create index if not exists idx_tenant_version_matchmaker_version_id on tab_tenant_version_matchmaker(version_id);

create table if not exists tab_tenant_version_runtime (
    id bigint primary key,
    tenant_id bigint not null references tab_tenant(id) on delete restrict on update restrict,
    version_id bigint not null references tab_tenant_version(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    runtime_id bigint not null,
    deleted boolean not null,
    unique(runtime_id)
);

create index if not exists idx_tenant_version_runtime_tenant_id on tab_tenant_version_runtime(tenant_id);
create index if not exists idx_tenant_version_runtime_version_id on tab_tenant_version_runtime(version_id);

-- matchmaker module

create table if not exists tab_matchmaker (
    id bigint primary key,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    tenant_id bigint not null,
    version_id bigint not null,
    deleted boolean not null
);

create table if not exists tab_matchmaker_command (
    id bigint primary key,
    matchmaker_id bigint not null references tab_matchmaker(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    qualifier text not null,
    body json not null,
    deleted boolean not null
);

create index if not exists idx_matchmaker_command_matchmaker_id on tab_matchmaker_command(matchmaker_id);

create table if not exists tab_matchmaker_request (
    id bigint primary key,
    matchmaker_id bigint not null references tab_matchmaker(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    user_id bigint not null,
    client_id bigint not null,
    mode varchar(64) not null,
    config json not null,
    deleted boolean not null
);

create index if not exists idx_matchmaker_request_matchmaker_id on tab_matchmaker_request(matchmaker_id);

create table if not exists tab_matchmaker_match (
    id bigint primary key,
    matchmaker_id bigint not null references tab_matchmaker(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    runtime_id bigint not null,
    stopped boolean not null,
    config json not null,
    deleted boolean not null
);

create index if not exists idx_matchmaker_match_matchmaker_id on tab_matchmaker_match(matchmaker_id);

create table if not exists tab_matchmaker_match_command (
    id bigint primary key,
    matchmaker_id bigint not null references tab_matchmaker(id) on delete restrict on update restrict,
    match_id bigint not null references tab_matchmaker_match(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    qualifier text not null,
    body json not null,
    deleted boolean not null
);

create index if not exists idx_matchmaker_match_command_matchmaker_id on tab_matchmaker_match_command(matchmaker_id);
create index if not exists idx_matchmaker_match_command_match_id on tab_matchmaker_match_command(match_id);

create table if not exists tab_matchmaker_match_client (
    id bigint primary key,
    matchmaker_id bigint not null references tab_matchmaker(id) on delete restrict on update restrict,
    match_id bigint not null references tab_matchmaker_match(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    user_id bigint not null,
    client_id bigint not null,
    group_name varchar(64) not null,
    config json not null,
    deleted boolean not null,
    unique(match_id, user_id, client_id)
);

create index if not exists idx_matchmaker_match_client_matchmaker_id on tab_matchmaker_match_client(matchmaker_id);
create index if not exists idx_matchmaker_match_client_match_id on tab_matchmaker_match_client(match_id);

-- runtime module

create table if not exists tab_runtime (
    id bigint primary key,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    tenant_id bigint not null,
    version_id bigint not null,
    qualifier text not null,
    config json not null,
    deleted boolean not null
);

create table if not exists tab_runtime_permission (
    id bigint primary key,
    runtime_id bigint not null references tab_runtime(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    user_id bigint not null,
    permission text not null,
    deleted boolean not null,
    unique(runtime_id, user_id, permission)
);

create index if not exists idx_runtime_permission_runtime_id on tab_runtime_permission(runtime_id);

create table if not exists tab_runtime_command (
    id bigint primary key,
    runtime_id bigint not null references tab_runtime(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    qualifier text not null,
    body json not null,
    deleted boolean not null
);

create index if not exists idx_runtime_command_runtime_id on tab_runtime_command(runtime_id);

create table if not exists tab_runtime_client (
    id bigint primary key,
    runtime_id bigint not null references tab_runtime(id) on delete restrict on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    user_id bigint not null,
    client_id bigint not null,
    deleted boolean not null,
    unique(runtime_id, user_id, client_id)
);

create index if not exists idx_runtime_client_runtime_id on tab_runtime_client(runtime_id);