# Module relations schema

```mermaid
graph TD;
model --> exception
dto --> model;
common --> exception;
migration --> common;
base --> migration;
base --> dto;
gateway --> base;
user --> gateway;
tenant --> lua;
lua --> base;
runtime --> user;
developer --> user;
developer --> tenant;
admin --> user;
admin --> tenant;
matchmaker --> tenant;
script --> all;
handler --> all;
job --> all;
parent --> bom;
bom --> all
```