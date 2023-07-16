-- user module

create table if not exists tab_user (
    id bigserial primary key,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    uuid uuid not null,
    role text not null,
    password_hash text not null,
    unique(uuid)
);

create table if not exists tab_user_token (
    id bigserial primary key,
    user_uuid uuid not null references tab_user(uuid) on delete cascade on update restrict,
    created timestamp with time zone not null,
    uuid uuid not null,
    expire timestamp with time zone not null,
    hash text not null,
    unique(uuid)
);

create table if not exists tab_user_player (
    id bigserial primary key,
    user_uuid uuid not null references tab_user(uuid) on delete cascade on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    uuid uuid not null,
    stage uuid not null,
    config json not null,
    unique(uuid),
    unique(user_uuid, stage)
);

create table if not exists tab_player_client (
    id bigserial primary key,
    player_uuid uuid not null references tab_user_player(uuid) on delete cascade on update restrict,
    created timestamp with time zone not null,
    uuid uuid not null,
    server text not null,
    connection_uuid uuid not null,
    unique(uuid),
    unique(connection_uuid)
);

create table if not exists tab_player_attribute (
    id bigserial primary key,
    player_uuid uuid not null references tab_user_player(uuid) on delete cascade on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    attribute_name varchar(64) not null,
    attribute_value varchar(64) not null,
    unique(player_uuid, attribute_name)
);

create table if not exists tab_player_object (
    id bigserial primary key,
    player_uuid uuid not null references tab_user_player(uuid) on delete cascade on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    uuid uuid not null,
    name varchar(64) not null,
    body bytea not null,
    unique(uuid),
    unique(player_uuid, name)
);

-- tenant module

create table if not exists tab_tenant (
    created timestamp with time zone not null,
    id bigserial primary key,
    modified timestamp with time zone not null,
    uuid uuid not null,
    config json not null,
    unique(uuid)
);

create table if not exists tab_tenant_permission (
    id bigserial primary key,
    tenant_uuid uuid not null references tab_tenant(uuid) on delete cascade on update restrict,
    created timestamp with time zone not null,
    user_uuid uuid not null,
    permission text not null,
    unique(tenant_uuid, user_uuid, permission)
);

create table if not exists tab_tenant_project (
    id bigserial primary key,
    tenant_uuid uuid not null references tab_tenant(uuid) on delete cascade on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    uuid uuid not null,
    owner uuid not null,
    config json not null,
    unique(uuid)
);

create table if not exists tab_project_permission (
    id bigserial primary key,
    project_uuid uuid not null references tab_tenant_project(uuid) on delete cascade on update restrict,
    created timestamp with time zone not null,
    user_uuid uuid not null,
    permission text not null,
    unique(project_uuid, user_uuid, permission)
);

create table if not exists tab_project_stage (
    id bigserial primary key,
    project_uuid uuid not null references tab_tenant_project(uuid) on delete cascade on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    uuid uuid not null,
    secret text not null,
    matchmaker uuid not null,
    config json not null,
    version uuid,
    unique(uuid)
);

create table if not exists tab_stage_permission (
    id bigserial primary key,
    stage_uuid uuid not null references tab_project_stage(uuid) on delete cascade on update restrict,
    created timestamp with time zone not null,
    user_uuid uuid not null,
    permission text not null,
    unique(stage_uuid, user_uuid, permission)
);

-- version module

create table if not exists tab_version (
    id bigserial primary key,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    uuid uuid not null,
    tenant uuid not null,
    stage uuid not null,
    stage_config json not null,
    source_code json not null,
    bytecode json not null,
    status text not null,
    errors text,
    unique(uuid)
);

-- matchmaker module

create table if not exists tab_matchmaker (
    id bigserial primary key,
    created timestamp with time zone not null,
    uuid uuid not null,
    tenant uuid not null,
    stage uuid not null,
    unique(uuid)
);

create table if not exists tab_matchmaker_request (
    id bigserial primary key,
    matchmaker_uuid uuid not null references tab_matchmaker(uuid) on delete cascade on update restrict,
    created timestamp with time zone not null,
    uuid uuid not null,
    config json not null,
    unique(uuid)
);

create table if not exists tab_matchmaker_match (
    id bigserial primary key,
    matchmaker_uuid uuid not null references tab_matchmaker(uuid) on delete cascade on update restrict,
    created timestamp with time zone not null,
    modified timestamp with time zone not null,
    uuid uuid not null,
    runtime uuid not null,
    config json not null,
    unique(uuid),
    unique(runtime)
);

-- runtime module

create table if not exists tab_runtime (
    id bigserial primary key,
    created timestamp with time zone not null,
    uuid uuid not null,
    matchmaker uuid not null,
    match_uuid uuid not null,
    config json not null,
    unique(uuid)
);