create table if not exists tab_node (
    id bigint primary key,
    modified timestamp with time zone not null,
    deleted boolean not null
);

insert into tab_node(id, modified, deleted) select generate_series(0, 4095), now(), false;