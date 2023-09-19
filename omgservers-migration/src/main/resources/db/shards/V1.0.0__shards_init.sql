-- user module

create table if not exists tab_user (
    id bigint primary key,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    role text not null,
    password_hash text not null
);

create table if not exists tab_user_token (
    id bigint primary key,
    user_id bigint not null references tab_user(id) on delete cascade on update restrict,
    created timestamp with time zone not null,
    expire timestamp with time zone not null,
    hash text not null
);

create table if not exists tab_user_player (
    id bigint primary key,
    user_id bigint not null references tab_user(id) on delete cascade on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    tenant_id bigint not null,
    stage_id bigint not null,
    config json not null,
    unique(user_id, stage_id)
);

create table if not exists tab_user_client (
    id bigint primary key,
    user_id bigint not null references tab_user(id) on delete cascade on update restrict,
    player_id bigint not null references tab_user_player(id) on delete cascade on update restrict,
    created timestamp with time zone not null,
    server text not null,
    connection_id bigint not null,
    script_id bigint not null,
    unique(player_id, connection_id)
);

create table if not exists tab_user_attribute (
    id bigint primary key,
    user_id bigint not null references tab_user(id) on delete cascade on update restrict,
    player_id bigint not null references tab_user_player(id) on delete cascade on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    name varchar(64) not null,
    attribute_value varchar(64) not null,
    unique(player_id, name)
);

create table if not exists tab_user_object (
    id bigint primary key,
    user_id bigint not null references tab_user(id) on delete cascade on update restrict,
    player_id bigint not null references tab_user_player(id) on delete cascade on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    name varchar(64) not null,
    body bytea not null,
    unique(player_id, name)
);

-- tenant module

create table if not exists tab_tenant (
    id bigint primary key,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    config json not null
);

create table if not exists tab_tenant_permission (
    id bigint primary key,
    tenant_id bigint not null references tab_tenant(id) on delete cascade on update restrict,
    created timestamp with time zone not null,
    user_id bigint not null,
    permission text not null,
    unique(tenant_id, user_id, permission)
);

create table if not exists tab_tenant_project (
    id bigint primary key,
    tenant_id bigint not null references tab_tenant(id) on delete cascade on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    config json not null
);

create table if not exists tab_tenant_project_permission (
    id bigint primary key,
    tenant_id bigint not null references tab_tenant(id) on delete cascade on update restrict,
    project_id bigint not null references tab_tenant_project(id) on delete cascade on update restrict,
    created timestamp with time zone not null,
    user_id bigint not null,
    permission text not null,
    unique(project_id, user_id, permission)
);

create table if not exists tab_tenant_stage (
    id bigint primary key,
    tenant_id bigint not null references tab_tenant(id) on delete cascade on update restrict,
    project_id bigint not null references tab_tenant_project(id) on delete cascade on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    secret text not null,
    matchmaker_id bigint not null,
    config json not null
);

create table if not exists tab_tenant_stage_permission (
    id bigint primary key,
    tenant_id bigint not null references tab_tenant(id) on delete cascade on update restrict,
    stage_id bigint not null references tab_tenant_stage(id) on delete cascade on update restrict,
    created timestamp with time zone not null,
    user_id bigint not null,
    permission text not null,
    unique(stage_id, user_id, permission)
);

create table if not exists tab_tenant_version (
    id bigint primary key,
    tenant_id bigint not null references tab_tenant(id) on delete cascade on update restrict,
    stage_id bigint not null references tab_tenant_stage(id) on delete cascade on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    config json not null,
    source_code json not null,
    bytecode json not null,
    errors text
);

-- matchmaker module

create table if not exists tab_matchmaker (
    id bigint primary key,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    tenant_id bigint not null,
    stage_id bigint not null
);

create table if not exists tab_matchmaker_command (
    id bigint primary key,
    matchmaker_id bigint not null references tab_matchmaker(id) on delete cascade on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    qualifier text not null,
    body json not null,
    status text not null
);

create table if not exists tab_matchmaker_request (
    id bigint primary key,
    matchmaker_id bigint not null references tab_matchmaker(id) on delete cascade on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    user_id bigint not null,
    client_id bigint not null,
    mode varchar(64) not null,
    config json not null
);

create table if not exists tab_matchmaker_match (
    id bigint primary key,
    matchmaker_id bigint not null references tab_matchmaker(id) on delete cascade on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    runtime_id bigint not null,
    config json not null
);

create table if not exists tab_matchmaker_match_client (
    id bigint primary key,
    matchmaker_id bigint not null references tab_matchmaker(id) on delete cascade on update restrict,
    match_id bigint not null references tab_matchmaker_match(id) on delete cascade on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    user_id bigint not null,
    client_id bigint not null,
    unique(match_id, user_id, client_id)
);

-- runtime module

create table if not exists tab_runtime (
    id bigint primary key,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    tenant_id bigint not null,
    stage_id bigint not null,
    version_id bigint not null,
    matchmaker_id bigint not null,
    match_id bigint not null,
    type text not null,
    step bigint not null,
    script_id bigint not null,
    config json not null
);

create table if not exists tab_runtime_command (
    id bigint primary key,
    runtime_id bigint not null references tab_runtime(id) on delete cascade on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    qualifier text not null,
    body json not null,
    status text not null,
    step bigint
);

create table if not exists tab_runtime_grant (
    id bigint primary key,
    runtime_id bigint not null references tab_runtime(id) on delete cascade on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    entity_id bigint not null,
    permission text not null,
    unique(runtime_id, entity_id, permission)
);

-- script module

create table if not exists tab_script (
    id bigint primary key,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    tenant_id bigint not null,
    version_id bigint not null,
    type text not null,
    state json not null,
    config json not null
);
